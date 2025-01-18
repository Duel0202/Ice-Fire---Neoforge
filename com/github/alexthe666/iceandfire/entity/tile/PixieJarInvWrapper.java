package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.items.IItemHandler;
import net.neoforge.items.IItemHandlerModifiable;

public class PixieJarInvWrapper implements IItemHandlerModifiable {
   private final TileEntityJar tile;

   public PixieJarInvWrapper(TileEntityJar tile) {
      this.tile = tile;
   }

   public static LazyOptional<IItemHandler> create(TileEntityJar trashCan) {
      return LazyOptional.of(() -> {
         return new PixieJarInvWrapper(trashCan);
      });
   }

   public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
   }

   public int getSlots() {
      return 1;
   }

   @Nonnull
   public ItemStack getStackInSlot(int slot) {
      return this.tile.hasProduced ? new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get()) : ItemStack.f_41583_;
   }

   @Nonnull
   public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
      return stack;
   }

   @Nonnull
   public ItemStack extractItem(int slot, int amount, boolean simulate) {
      if (this.tile.hasProduced) {
         if (!simulate) {
            this.tile.hasProduced = false;
         }

         return new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get());
      } else {
         return ItemStack.f_41583_;
      }
   }

   public int getSlotLimit(int slot) {
      return 1;
   }

   public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
      return false;
   }
}
