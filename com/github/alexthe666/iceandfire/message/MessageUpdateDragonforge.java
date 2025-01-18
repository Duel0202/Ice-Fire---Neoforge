package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageUpdateDragonforge {
   public long blockPos;
   public int cookTime;

   public MessageUpdateDragonforge(long blockPos, int houseType) {
      this.blockPos = blockPos;
      this.cookTime = houseType;
   }

   public MessageUpdateDragonforge() {
   }

   public static MessageUpdateDragonforge read(FriendlyByteBuf buf) {
      return new MessageUpdateDragonforge(buf.readLong(), buf.readInt());
   }

   public static void write(MessageUpdateDragonforge message, FriendlyByteBuf buf) {
      buf.writeLong(message.blockPos);
      buf.writeInt(message.cookTime);
   }

   public static class Handler {
      public static void handle(MessageUpdateDragonforge message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               BlockPos pos = BlockPos.m_122022_(message.blockPos);
               BlockEntity patt1807$temp = ((Player)player).m_9236_().m_7702_(pos);
               if (patt1807$temp instanceof TileEntityDragonforge) {
                  TileEntityDragonforge forge = (TileEntityDragonforge)patt1807$temp;
                  forge.cookTime = message.cookTime;
                  if (message.cookTime > 0) {
                     forge.lastDragonFlameTimer = 40;
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
