package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageDragonSyncFire {
   public int dragonId;
   public double posX;
   public double posY;
   public double posZ;
   public int syncType;

   public MessageDragonSyncFire(int dragonId, double posX, double posY, double posZ, int syncType) {
      this.dragonId = dragonId;
      this.posX = posX;
      this.posY = posY;
      this.posZ = posZ;
      this.syncType = syncType;
   }

   public MessageDragonSyncFire() {
   }

   public static MessageDragonSyncFire read(FriendlyByteBuf buf) {
      return new MessageDragonSyncFire(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
   }

   public static void write(MessageDragonSyncFire message, FriendlyByteBuf buf) {
      buf.writeInt(message.dragonId);
      buf.writeDouble(message.posX);
      buf.writeDouble(message.posY);
      buf.writeDouble(message.posZ);
      buf.writeInt(message.syncType);
   }

   public static class Handler {
      public static void handle(MessageDragonSyncFire message, Supplier<Context> contextSupplier) {
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
                  dragon.stimulateFire(message.posX, message.posY, message.posZ, message.syncType);
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
