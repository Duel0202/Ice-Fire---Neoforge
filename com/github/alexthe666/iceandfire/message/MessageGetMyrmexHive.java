package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageGetMyrmexHive {
   public CompoundTag hive;

   public MessageGetMyrmexHive(CompoundTag hive) {
      this.hive = hive;
   }

   public MessageGetMyrmexHive() {
   }

   public static MessageGetMyrmexHive read(FriendlyByteBuf buf) {
      return new MessageGetMyrmexHive(buf.m_130260_());
   }

   public static void write(MessageGetMyrmexHive message, FriendlyByteBuf buf) {
      buf.m_130079_(message.hive);
   }

   public static class Handler {
      public static void handle(MessageGetMyrmexHive message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            MyrmexHive serverHive = MyrmexHive.fromNBT(message.hive);
            CompoundTag tag = new CompoundTag();
            serverHive.writeVillageDataToNBT(tag);
            serverHive.readVillageDataFromNBT(tag);
            IceAndFire.PROXY.setReferencedHive(serverHive);
            if (player != null) {
               MyrmexHive realHive = MyrmexWorldData.get(((Player)player).m_9236_()).getHiveFromUUID(serverHive.hiveUUID);
               realHive.readVillageDataFromNBT(serverHive.toNBT());
            }

         });
         context.setPacketHandled(true);
      }
   }
}
