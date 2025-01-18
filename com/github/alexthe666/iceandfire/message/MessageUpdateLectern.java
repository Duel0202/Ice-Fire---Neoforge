package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityLectern;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.api.distmarker.Dist;
import net.neoforge.fml.DistExecutor;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageUpdateLectern {
   public long blockPos;
   public int selectedPages1;
   public int selectedPages2;
   public int selectedPages3;
   public boolean updateStack;
   public int pageOrdinal;

   public MessageUpdateLectern(long blockPos, int selectedPages1, int selectedPages2, int selectedPages3, boolean updateStack, int pageOrdinal) {
      this.blockPos = blockPos;
      this.selectedPages1 = selectedPages1;
      this.selectedPages2 = selectedPages2;
      this.selectedPages3 = selectedPages3;
      this.updateStack = updateStack;
      this.pageOrdinal = pageOrdinal;
   }

   public MessageUpdateLectern() {
   }

   public static MessageUpdateLectern read(FriendlyByteBuf buf) {
      return new MessageUpdateLectern(buf.readLong(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readBoolean(), buf.readInt());
   }

   public static void write(MessageUpdateLectern message, FriendlyByteBuf buf) {
      buf.writeLong(message.blockPos);
      buf.writeInt(message.selectedPages1);
      buf.writeInt(message.selectedPages2);
      buf.writeInt(message.selectedPages3);
      buf.writeBoolean(message.updateStack);
      buf.writeInt(message.pageOrdinal);
   }

   public static class Handler {
      public static void handle(MessageUpdateLectern message, Supplier<Context> ctx) {
         ((Context)ctx.get()).enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> {
               return () -> {
                  handlePacket(message, ctx);
               };
            });
         });
         ((Context)ctx.get()).enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
               return () -> {
                  handlePacket(message, ctx);
               };
            });
         });
         ((Context)ctx.get()).setPacketHandled(true);
      }

      public static void handlePacket(MessageUpdateLectern message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               BlockPos pos = BlockPos.m_122022_(message.blockPos);
               if (((Player)player).m_9236_().m_46805_(pos)) {
                  BlockEntity patt3286$temp = ((Player)player).m_9236_().m_7702_(pos);
                  if (patt3286$temp instanceof TileEntityLectern) {
                     TileEntityLectern lectern = (TileEntityLectern)patt3286$temp;
                     if (message.updateStack) {
                        ItemStack bookStack = lectern.m_8020_(0);
                        if (bookStack.m_41720_() == IafItemRegistry.BESTIARY.get()) {
                           EnumBestiaryPages.addPage(EnumBestiaryPages.fromInt(message.pageOrdinal), bookStack);
                        }

                        lectern.randomizePages(bookStack, lectern.m_8020_(1));
                     } else {
                        lectern.selectedPages[0] = EnumBestiaryPages.fromInt(message.selectedPages1);
                        lectern.selectedPages[1] = EnumBestiaryPages.fromInt(message.selectedPages2);
                        lectern.selectedPages[2] = EnumBestiaryPages.fromInt(message.selectedPages3);
                     }
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
