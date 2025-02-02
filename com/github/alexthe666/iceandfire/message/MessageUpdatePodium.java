package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.citadel.server.message.PacketBufferUtils;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPodium;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageUpdatePodium {
   public long blockPos;
   public ItemStack heldStack;

   public MessageUpdatePodium(long blockPos, ItemStack heldStack) {
      this.blockPos = blockPos;
      this.heldStack = heldStack;
   }

   public MessageUpdatePodium() {
   }

   public static MessageUpdatePodium read(FriendlyByteBuf buf) {
      return new MessageUpdatePodium(buf.readLong(), PacketBufferUtils.readItemStack(buf));
   }

   public static void write(MessageUpdatePodium message, FriendlyByteBuf buf) {
      buf.writeLong(message.blockPos);
      PacketBufferUtils.writeItemStack(buf, message.heldStack);
   }

   public static class Handler {
      public static void handle(MessageUpdatePodium message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               BlockPos pos = BlockPos.m_122022_(message.blockPos);
               BlockEntity patt1944$temp = ((Player)player).m_9236_().m_7702_(pos);
               if (patt1944$temp instanceof TileEntityPodium) {
                  TileEntityPodium podium = (TileEntityPodium)patt1944$temp;
                  podium.m_6836_(0, message.heldStack);
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
