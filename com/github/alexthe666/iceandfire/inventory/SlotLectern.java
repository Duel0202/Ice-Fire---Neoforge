package com.github.alexthe666.iceandfire.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SlotLectern extends Slot {
   public SlotLectern(Container inv, int slotIndex, int xPosition, int yPosition) {
      super(inv, slotIndex, xPosition, yPosition);
   }

   public void m_6654_() {
      this.f_40218_.m_6596_();
   }

   public void m_142406_(@NotNull Player playerIn, @NotNull ItemStack stack) {
      this.m_5845_(stack);
      super.m_142406_(playerIn, stack);
   }

   protected void m_7169_(@NotNull ItemStack stack, int amount) {
      this.m_5845_(stack);
   }

   protected void m_5845_(@NotNull ItemStack stack) {
   }
}
