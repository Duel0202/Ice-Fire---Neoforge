package com.github.alexthe666.iceandfire.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ContainerPodium extends AbstractContainerMenu {
   public final Container podium;

   public ContainerPodium(int i, Inventory playerInventory) {
      this(i, new SimpleContainer(1), playerInventory, new SimpleContainerData(0));
   }

   public ContainerPodium(int id, Container furnaceInventory, Inventory playerInventory, ContainerData vars) {
      super((MenuType)IafContainerRegistry.PODIUM_CONTAINER.get(), id);
      this.podium = furnaceInventory;
      furnaceInventory.m_5856_(playerInventory.f_35978_);
      byte b0 = 51;
      this.m_38897_(new Slot(furnaceInventory, 0, 80, 20));

      int i;
      for(i = 0; i < 3; ++i) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, i * 18 + b0));
         }
      }

      for(i = 0; i < 9; ++i) {
         this.m_38897_(new Slot(playerInventory, i, 8 + i * 18, 58 + b0));
      }

   }

   public boolean m_6875_(@NotNull Player playerIn) {
      return this.podium.m_6542_(playerIn);
   }

   @NotNull
   public ItemStack m_7648_(@NotNull Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index < this.podium.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.podium.m_6643_(), this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 0, this.podium.m_6643_(), false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }
      }

      return itemstack;
   }

   public void m_6877_(@NotNull Player playerIn) {
      super.m_6877_(playerIn);
      this.podium.m_5785_(playerIn);
   }
}
