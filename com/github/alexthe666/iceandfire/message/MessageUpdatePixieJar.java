package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageUpdatePixieJar {
   public long blockPos;
   public boolean isProducing;

   public MessageUpdatePixieJar(long blockPos, boolean isProducing) {
      this.blockPos = blockPos;
      this.isProducing = isProducing;
   }

   public MessageUpdatePixieJar() {
   }

   public static MessageUpdatePixieJar read(FriendlyByteBuf buf) {
      return new MessageUpdatePixieJar(buf.readLong(), buf.readBoolean());
   }

   public static void write(MessageUpdatePixieJar message, FriendlyByteBuf buf) {
      buf.writeLong(message.blockPos);
      buf.writeBoolean(message.isProducing);
   }

   public static class Handler {
      public static void handle(MessageUpdatePixieJar message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               BlockPos pos = BlockPos.m_122022_(message.blockPos);
               BlockEntity patt1805$temp = ((Player)player).m_9236_().m_7702_(pos);
               if (patt1805$temp instanceof TileEntityJar) {
                  TileEntityJar jar = (TileEntityJar)patt1805$temp;
                  jar.hasProduced = message.isProducing;
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
