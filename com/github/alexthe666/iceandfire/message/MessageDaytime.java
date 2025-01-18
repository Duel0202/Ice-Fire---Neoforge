package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageDaytime {
   public int dragonId;
   public boolean isDay;

   public MessageDaytime(int dragonId, boolean isDay) {
      this.dragonId = dragonId;
      this.isDay = isDay;
   }

   public MessageDaytime() {
   }

   public static MessageDaytime read(FriendlyByteBuf buf) {
      return new MessageDaytime(buf.readInt(), buf.readBoolean());
   }

   public static void write(MessageDaytime message, FriendlyByteBuf buf) {
      buf.writeInt(message.dragonId);
      buf.writeBoolean(message.isDay);
   }

   public static class Handler {
      public static void handle(MessageDaytime message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               Entity entity = ((Player)player).m_9236_().m_6815_(message.dragonId);
               if (entity instanceof EntityDragonBase) {
                  EntityDragonBase dragon = (EntityDragonBase)entity;
                  dragon.isDaytime = message.isDay;
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
