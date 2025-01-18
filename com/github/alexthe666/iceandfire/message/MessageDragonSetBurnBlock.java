package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageDragonSetBurnBlock {
   public int dragonId;
   public boolean breathingFire;
   public int posX;
   public int posY;
   public int posZ;

   public MessageDragonSetBurnBlock(int dragonId, boolean breathingFire, BlockPos pos) {
      this.dragonId = dragonId;
      this.breathingFire = breathingFire;
      this.posX = pos.m_123341_();
      this.posY = pos.m_123342_();
      this.posZ = pos.m_123343_();
   }

   public static MessageDragonSetBurnBlock read(FriendlyByteBuf buf) {
      return new MessageDragonSetBurnBlock(buf.readInt(), buf.readBoolean(), new BlockPos(buf.readInt(), buf.readInt(), buf.readInt()));
   }

   public static void write(MessageDragonSetBurnBlock message, FriendlyByteBuf buf) {
      buf.writeInt(message.dragonId);
      buf.writeBoolean(message.breathingFire);
      buf.writeInt(message.posX);
      buf.writeInt(message.posY);
      buf.writeInt(message.posZ);
   }

   public static class Handler {
      public static void handle(MessageDragonSetBurnBlock message, Supplier<Context> contextSupplier) {
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
                  dragon.setBreathingFire(message.breathingFire);
                  dragon.burningTarget = new BlockPos(message.posX, message.posY, message.posZ);
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
