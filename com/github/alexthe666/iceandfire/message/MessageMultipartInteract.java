package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageMultipartInteract {
   public int creatureID;
   public float dmg;

   public MessageMultipartInteract(int creatureID, float dmg) {
      this.creatureID = creatureID;
      this.dmg = dmg;
   }

   public MessageMultipartInteract() {
   }

   public static MessageMultipartInteract read(FriendlyByteBuf buf) {
      return new MessageMultipartInteract(buf.readInt(), buf.readFloat());
   }

   public static void write(MessageMultipartInteract message, FriendlyByteBuf buf) {
      buf.writeInt(message.creatureID);
      buf.writeFloat(message.dmg);
   }

   public static class Handler {
      public static void handle(MessageMultipartInteract message, Supplier<Context> contextSupplier) {
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
                     if (message.dmg > 0.0F) {
                        livingEntity.m_6469_(((Player)player).m_9236_().m_269111_().m_269333_((LivingEntity)player), message.dmg);
                     } else {
                        livingEntity.m_6096_((Player)player, InteractionHand.MAIN_HAND);
                     }
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
