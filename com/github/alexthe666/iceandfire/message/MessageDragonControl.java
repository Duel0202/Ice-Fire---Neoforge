package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityAmphithere;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageDragonControl {
   public int dragonId;
   public byte controlState;
   public int armor_type;
   private double posX;
   private double posY;
   private double posZ;

   public MessageDragonControl(int dragonId, byte controlState, double posX, double posY, double posZ) {
      this.dragonId = dragonId;
      this.controlState = controlState;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
   }

   public MessageDragonControl() {
   }

   public static MessageDragonControl read(FriendlyByteBuf buf) {
      return new MessageDragonControl(buf.readInt(), buf.readByte(), buf.readDouble(), buf.readDouble(), buf.readDouble());
   }

   public static void write(MessageDragonControl message, FriendlyByteBuf buf) {
      buf.writeInt(message.dragonId);
      buf.writeByte(message.controlState);
      buf.writeDouble(message.posX);
      buf.writeDouble(message.posY);
      buf.writeDouble(message.posZ);
   }

   private double getPosX() {
      return this.posX;
   }

   private double getPosY() {
      return this.posY;
   }

   private double getPosZ() {
      return this.posZ;
   }

   public static class Handler {
      public static void handle(MessageDragonControl message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               Entity entity = ((Player)player).m_9236_().m_6815_(message.dragonId);
               if (ServerEvents.isRidingOrBeingRiddenBy(entity, (Entity)player)) {
                  if (entity instanceof EntityDragonBase) {
                     EntityDragonBase dragon = (EntityDragonBase)entity;
                     if (dragon.m_21830_((LivingEntity)player)) {
                        dragon.setControlState(message.controlState);
                     }
                  } else if (entity instanceof EntityHippogryph) {
                     EntityHippogryph hippogryph = (EntityHippogryph)entity;
                     if (hippogryph.m_21830_((LivingEntity)player)) {
                        hippogryph.setControlState(message.controlState);
                     }
                  } else if (entity instanceof EntityHippocampus) {
                     EntityHippocampus hippo = (EntityHippocampus)entity;
                     if (hippo.m_21830_((LivingEntity)player)) {
                        hippo.setControlState(message.controlState);
                     }

                     hippo.m_6034_(message.getPosX(), message.getPosY(), message.getPosZ());
                  } else if (entity instanceof EntityDeathWorm) {
                     EntityDeathWorm deathWorm = (EntityDeathWorm)entity;
                     deathWorm.setControlState(message.controlState);
                     deathWorm.m_6034_(message.getPosX(), message.getPosY(), message.getPosZ());
                  } else if (entity instanceof EntityAmphithere) {
                     EntityAmphithere amphithere = (EntityAmphithere)entity;
                     if (amphithere.m_21830_((LivingEntity)player)) {
                        amphithere.setControlState(message.controlState);
                     }

                     amphithere.m_6034_(message.getPosX(), message.getPosY(), message.getPosZ());
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
