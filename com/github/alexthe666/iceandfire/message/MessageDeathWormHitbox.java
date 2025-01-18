package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageDeathWormHitbox {
   public int deathWormId;
   public float scale;

   public MessageDeathWormHitbox(int deathWormId, float scale) {
      this.deathWormId = deathWormId;
      this.scale = scale;
   }

   public MessageDeathWormHitbox() {
   }

   public static MessageDeathWormHitbox read(FriendlyByteBuf buf) {
      return new MessageDeathWormHitbox(buf.readInt(), buf.readFloat());
   }

   public static void write(MessageDeathWormHitbox message, FriendlyByteBuf buf) {
      buf.writeInt(message.deathWormId);
      buf.writeFloat(message.scale);
   }

   public static class Handler {
      public static void handle(MessageDeathWormHitbox message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               Entity entity = ((Player)player).m_9236_().m_6815_(message.deathWormId);
               if (entity instanceof EntityDeathWorm) {
                  EntityDeathWorm deathWorm = (EntityDeathWorm)entity;
                  deathWorm.initSegments(message.scale);
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
