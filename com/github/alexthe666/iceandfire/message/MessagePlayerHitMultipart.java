package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHydra;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessagePlayerHitMultipart {
   public int creatureID;
   public int extraData;

   public MessagePlayerHitMultipart(int creatureID) {
      this.creatureID = creatureID;
      this.extraData = 0;
   }

   public MessagePlayerHitMultipart(int creatureID, int extraData) {
      this.creatureID = creatureID;
      this.extraData = extraData;
   }

   public MessagePlayerHitMultipart() {
   }

   public static MessagePlayerHitMultipart read(FriendlyByteBuf buf) {
      return new MessagePlayerHitMultipart(buf.readInt(), buf.readInt());
   }

   public static void write(MessagePlayerHitMultipart message, FriendlyByteBuf buf) {
      buf.writeInt(message.creatureID);
      buf.writeInt(message.extraData);
   }

   public static class Handler {
      public static void handle(MessagePlayerHitMultipart message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               Entity entity = ((Player)player).m_9236_().m_6815_(message.creatureID);
               if (entity instanceof LivingEntity) {
                  LivingEntity livingEntity = (LivingEntity)entity;
                  double dist = (double)((Player)player).m_20270_(livingEntity);
                  if (dist < 100.0D) {
                     ((Player)player).m_5706_(livingEntity);
                     if (livingEntity instanceof EntityHydra) {
                        EntityHydra hydra = (EntityHydra)livingEntity;
                        hydra.triggerHeadFlags(message.extraData);
                     }
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
