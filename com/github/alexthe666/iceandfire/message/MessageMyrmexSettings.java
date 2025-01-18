package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageMyrmexSettings {
   public int queenID;
   public boolean reproduces;
   public boolean deleteRoom;
   public long roomToDelete;

   public MessageMyrmexSettings(int queenID, boolean repoduces, boolean deleteRoom, long roomToDelete) {
      this.queenID = queenID;
      this.reproduces = repoduces;
      this.deleteRoom = deleteRoom;
      this.roomToDelete = roomToDelete;
   }

   public static MessageMyrmexSettings read(FriendlyByteBuf buf) {
      return new MessageMyrmexSettings(buf.readInt(), buf.readBoolean(), buf.readBoolean(), buf.readLong());
   }

   public static void write(MessageMyrmexSettings message, FriendlyByteBuf buf) {
      buf.writeInt(message.queenID);
      buf.writeBoolean(message.reproduces);
      buf.writeBoolean(message.deleteRoom);
      buf.writeLong(message.roomToDelete);
   }

   public static class Handler {
      public static void handle(MessageMyrmexSettings message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               Entity entity = ((Player)player).m_9236_().m_6815_(message.queenID);
               if (entity instanceof EntityMyrmexBase) {
                  EntityMyrmexBase myrmex = (EntityMyrmexBase)entity;
                  MyrmexHive hive = myrmex.getHive();
                  if (hive != null) {
                     hive.reproduces = message.reproduces;
                     if (message.deleteRoom) {
                        hive.removeRoom(BlockPos.m_122022_(message.roomToDelete));
                     }
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
