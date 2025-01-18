package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageUpdatePixieHouse {
   public long blockPos;
   public boolean hasPixie;
   public int pixieType;

   public MessageUpdatePixieHouse(long blockPos, boolean hasPixie, int pixieType) {
      this.blockPos = blockPos;
      this.hasPixie = hasPixie;
      this.pixieType = pixieType;
   }

   public MessageUpdatePixieHouse() {
   }

   public static MessageUpdatePixieHouse read(FriendlyByteBuf buf) {
      return new MessageUpdatePixieHouse(buf.readLong(), buf.readBoolean(), buf.readInt());
   }

   public static void write(MessageUpdatePixieHouse message, FriendlyByteBuf buf) {
      buf.writeLong(message.blockPos);
      buf.writeBoolean(message.hasPixie);
      buf.writeInt(message.pixieType);
   }

   public static class Handler {
      public static void handle(MessageUpdatePixieHouse message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               BlockPos pos = BlockPos.m_122022_(message.blockPos);
               BlockEntity blockEntity = ((Player)player).m_9236_().m_7702_(pos);
               if (blockEntity instanceof TileEntityPixieHouse) {
                  TileEntityPixieHouse house = (TileEntityPixieHouse)blockEntity;
                  house.hasPixie = message.hasPixie;
                  house.pixieType = message.pixieType;
               } else if (blockEntity instanceof TileEntityJar) {
                  TileEntityJar jar = (TileEntityJar)blockEntity;
                  jar.hasPixie = message.hasPixie;
                  jar.pixieType = message.pixieType;
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
