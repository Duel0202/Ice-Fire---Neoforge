package com.github.alexthe666.iceandfire.inventory;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.item.ItemDragonArmor;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ContainerDragon extends AbstractContainerMenu {
   private final Container dragonInventory;
   private final EntityDragonBase dragon;

   public ContainerDragon(int i, Inventory playerInventory) {
      this(i, new SimpleContainer(5), playerInventory, (EntityDragonBase)null);
   }

   public ContainerDragon(int id, Container dragonInventory, Inventory playerInventory, EntityDragonBase rat) {
      super((MenuType)IafContainerRegistry.DRAGON_CONTAINER.get(), id);
      this.dragonInventory = dragonInventory;
      this.dragon = rat;
      byte b0 = 3;
      dragonInventory.m_5856_(playerInventory.f_35978_);
      int i = (b0 - 4) * 18;
      this.m_38897_(new Slot(dragonInventory, 0, 8, 54) {
         public void m_6654_() {
            this.f_40218_.m_6596_();
         }

         public boolean m_5857_(ItemStack stack) {
            return super.m_5857_(stack) && stack.m_41720_() instanceof BannerItem;
         }
      });
      this.m_38897_(new Slot(dragonInventory, 1, 8, 18) {
         public void m_6654_() {
            this.f_40218_.m_6596_();
         }

         public boolean m_5857_(ItemStack stack) {
            return super.m_5857_(stack) && !stack.m_41619_() && stack.m_41720_() instanceof ItemDragonArmor && ((ItemDragonArmor)stack.m_41720_()).dragonSlot == 0;
         }
      });
      this.m_38897_(new Slot(dragonInventory, 2, 8, 36) {
         public void m_6654_() {
            this.f_40218_.m_6596_();
         }

         public boolean m_5857_(ItemStack stack) {
            return super.m_5857_(stack) && !stack.m_41619_() && stack.m_41720_() instanceof ItemDragonArmor && ((ItemDragonArmor)stack.m_41720_()).dragonSlot == 1;
         }
      });
      this.m_38897_(new Slot(dragonInventory, 3, 153, 18) {
         public void m_6654_() {
            this.f_40218_.m_6596_();
         }

         public boolean m_5857_(ItemStack stack) {
            return super.m_5857_(stack) && !stack.m_41619_() && stack.m_41720_() instanceof ItemDragonArmor && ((ItemDragonArmor)stack.m_41720_()).dragonSlot == 2;
         }
      });
      this.m_38897_(new Slot(dragonInventory, 4, 153, 36) {
         public void m_6654_() {
            this.f_40218_.m_6596_();
         }

         public boolean m_5857_(ItemStack stack) {
            return super.m_5857_(stack) && !stack.m_41619_() && stack.m_41720_() instanceof ItemDragonArmor && ((ItemDragonArmor)stack.m_41720_()).dragonSlot == 3;
         }
      });

      int j;
      for(j = 0; j < 3; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.m_38897_(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 150 + j * 18 + i));
         }
      }

      for(j = 0; j < 9; ++j) {
         this.m_38897_(new Slot(playerInventory, j, 8 + j * 18, 208 + i));
      }

   }

   public boolean m_6875_(@NotNull Player playerIn) {
      return !this.dragon.hasInventoryChanged(this.dragonInventory) && this.dragonInventory.m_6542_(playerIn) && this.dragon.m_6084_() && this.dragon.m_20270_(playerIn) < 8.0F;
   }

   @NotNull
   public ItemStack m_7648_(@NotNull Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index < this.dragonInventory.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.dragonInventory.m_6643_(), this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(1).m_5857_(itemstack1) && !this.m_38853_(1).m_6657_()) {
            if (!this.m_38903_(itemstack1, 1, 2, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(2).m_5857_(itemstack1) && !this.m_38853_(2).m_6657_()) {
            if (!this.m_38903_(itemstack1, 2, 3, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(3).m_5857_(itemstack1) && !this.m_38853_(3).m_6657_()) {
            if (!this.m_38903_(itemstack1, 3, 4, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(4).m_5857_(itemstack1) && !this.m_38853_(4).m_6657_()) {
            if (!this.m_38903_(itemstack1, 4, 5, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(0).m_5857_(itemstack1)) {
            if (!this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.dragonInventory.m_6643_() <= 5 || !this.m_38903_(itemstack1, 5, this.dragonInventory.m_6643_(), false)) {
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
      this.dragonInventory.m_5785_(playerIn);
   }
}
