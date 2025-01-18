package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import java.util.function.Supplier;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageSpawnParticleAt {
   private double x;
   private double y;
   private double z;
   private int particleType;

   public MessageSpawnParticleAt() {
   }

   public MessageSpawnParticleAt(double x, double y, double z, int particleType) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.particleType = particleType;
   }

   public static MessageSpawnParticleAt read(FriendlyByteBuf buf) {
      return new MessageSpawnParticleAt(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
   }

   public static void write(MessageSpawnParticleAt message, FriendlyByteBuf buf) {
      buf.writeDouble(message.x);
      buf.writeDouble(message.y);
      buf.writeDouble(message.z);
      buf.writeInt(message.particleType);
   }

   public static class Handler {
      public static void handle(MessageSpawnParticleAt message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               ItemStack mainHand = ((Player)player).m_21205_();
               if (!mainHand.m_41619_() && mainHand.m_41720_() == IafItemRegistry.DRAGON_DEBUG_STICK.get()) {
                  ((Player)player).m_9236_().m_7106_(ParticleTypes.f_123762_, message.x, message.y, message.z, 0.0D, 0.0D, 0.0D);
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
