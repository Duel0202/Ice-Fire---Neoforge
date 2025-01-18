package com.github.alexthe666.iceandfire.inventory;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class HippocampusContainerMenu extends AbstractContainerMenu {
   private final Container hippocampusInventory;
   private final EntityHippocampus hippocampus;
   private final Player player;

   public HippocampusContainerMenu(int i, Inventory playerInventory) {
      this(i, new SimpleContainer(18), playerInventory, (EntityHippocampus)null);
   }

   public HippocampusContainerMenu(int id, Container hippoInventory, Inventory playerInventory, EntityHippocampus hippocampus) {
      super((MenuType)IafContainerRegistry.HIPPOCAMPUS_CONTAINER.get(), id);
      this.hippocampusInventory = hippoInventory;
      if (hippocampus == null && IceAndFire.PROXY.getReferencedMob() instanceof EntityHippocampus) {
         hippocampus = (EntityHippocampus)IceAndFire.PROXY.getReferencedMob();
      }

      this.hippocampus = hippocampus;
      this.player = playerInventory.f_35978_;
      this.hippocampusInventory.m_5856_(this.player);
      this.m_38897_(new Slot(this.hippocampusInventory, 0, 8, 18) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return stack.m_41720_() == Items.f_42450_ && !this.m_6657_();
         }

         public boolean m_6659_() {
            return true;
         }
      });
      this.m_38897_(new Slot(this.hippocampusInventory, 1, 8, 36) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return stack.m_41720_() == Blocks.f_50087_.m_5456_() && !this.m_6657_();
         }

         public boolean m_6659_() {
            return true;
         }
      });
      this.m_38897_(new Slot(this.hippocampusInventory, 2, 8, 52) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return EntityHippocampus.getIntFromArmor(stack) != 0;
         }

         public int m_6641_() {
            return 1;
         }

         public boolean m_6659_() {
            return true;
         }
      });
      int j1;
      int k1;
      if (this.hippocampus.isChested()) {
         for(j1 = 0; j1 < 3; ++j1) {
            for(k1 = 0; k1 < hippocampus.getInventoryColumns(); ++k1) {
               this.m_38897_(new Slot(hippoInventory, 3 + k1 + j1 * hippocampus.getInventoryColumns(), 80 + k1 * 18, 18 + j1 * 18));
            }
         }
      }

      for(j1 = 0; j1 < 3; ++j1) {
         for(k1 = 0; k1 < 9; ++k1) {
            this.m_38897_(new Slot(this.player.m_150109_(), k1 + j1 * 9 + 9, 8 + k1 * 18, 102 + j1 * 18 + -18));
         }
      }

      for(j1 = 0; j1 < 9; ++j1) {
         this.m_38897_(new Slot(this.player.m_150109_(), j1, 8 + j1 * 18, 142));
      }

   }

   @NotNull
   public ItemStack m_7648_(@NotNull Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         int containerSize = this.hippocampusInventory.m_6643_();
         if (index < containerSize) {
            if (!this.m_38903_(itemstack1, containerSize, this.f_38839_.size(), true)) {
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
         } else if (this.m_38853_(0).m_5857_(itemstack1)) {
            if (!this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (containerSize <= 3 || !this.m_38903_(itemstack1, 3, containerSize, false)) {
            int j = containerSize + 27;
            int k = j + 9;
            if (index >= j && index < k) {
               if (!this.m_38903_(itemstack1, containerSize, j, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (index >= containerSize && index < j) {
               if (!this.m_38903_(itemstack1, j, k, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (!this.m_38903_(itemstack1, j, j, false)) {
               return ItemStack.f_41583_;
            }

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

   public boolean m_6875_(@NotNull Player playerIn) {
      return !this.hippocampus.hasInventoryChanged(this.hippocampusInventory) && this.hippocampusInventory.m_6542_(playerIn) && this.hippocampus.m_6084_() && this.hippocampus.m_20270_(playerIn) < 8.0F;
   }

   public void m_6877_(@NotNull Player playerIn) {
      super.m_6877_(playerIn);
      this.hippocampusInventory.m_5785_(playerIn);
   }
}
