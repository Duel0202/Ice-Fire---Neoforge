package com.github.alexthe666.iceandfire.inventory;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
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

public class ContainerHippogryph extends AbstractContainerMenu {
   private final Container hippogryphInventory;
   private final EntityHippogryph hippogryph;
   private final Player player;

   public ContainerHippogryph(int i, Inventory playerInventory) {
      this(i, new SimpleContainer(18), playerInventory, (EntityHippogryph)null);
   }

   public ContainerHippogryph(int id, Container ratInventory, Inventory playerInventory, EntityHippogryph hippogryph) {
      super((MenuType)IafContainerRegistry.HIPPOGRYPH_CONTAINER.get(), id);
      this.hippogryphInventory = ratInventory;
      if (hippogryph == null && IceAndFire.PROXY.getReferencedMob() instanceof EntityHippogryph) {
         hippogryph = (EntityHippogryph)IceAndFire.PROXY.getReferencedMob();
      }

      this.hippogryph = hippogryph;
      this.player = playerInventory.f_35978_;
      this.hippogryphInventory.m_5856_(this.player);
      this.m_38897_(new Slot(this.hippogryphInventory, 0, 8, 18) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return stack.m_41720_() == Items.f_42450_ && !this.m_6657_();
         }

         public void m_6654_() {
            if (ContainerHippogryph.this.hippogryph != null) {
               ContainerHippogryph.this.hippogryph.refreshInventory();
            }

         }

         public boolean m_6659_() {
            return true;
         }
      });
      this.m_38897_(new Slot(this.hippogryphInventory, 1, 8, 36) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return stack.m_41720_() == Blocks.f_50087_.m_5456_() && !this.m_6657_();
         }

         public void m_6654_() {
            if (ContainerHippogryph.this.hippogryph != null) {
               ContainerHippogryph.this.hippogryph.refreshInventory();
            }

         }

         public boolean m_6659_() {
            return true;
         }
      });
      this.m_38897_(new Slot(this.hippogryphInventory, 2, 8, 52) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return EntityHippogryph.getIntFromArmor(stack) != 0;
         }

         public int m_6641_() {
            return 1;
         }

         public void m_6654_() {
            if (ContainerHippogryph.this.hippogryph != null) {
               ContainerHippogryph.this.hippogryph.refreshInventory();
            }

         }

         public boolean m_6659_() {
            return true;
         }
      });

      int j1;
      int k1;
      for(j1 = 0; j1 < 3; ++j1) {
         for(k1 = 0; k1 < 5; ++k1) {
            this.m_38897_(new Slot(this.hippogryphInventory, 3 + k1 + j1 * 5, 80 + k1 * 18, 18 + j1 * 18) {
               public boolean m_6659_() {
                  return ContainerHippogryph.this.hippogryph != null && ContainerHippogryph.this.hippogryph.isChested();
               }

               public boolean m_5857_(@NotNull ItemStack stack) {
                  return ContainerHippogryph.this.hippogryph != null && ContainerHippogryph.this.hippogryph.isChested();
               }
            });
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
         if (index < this.hippogryphInventory.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.hippogryphInventory.m_6643_(), this.f_38839_.size(), true)) {
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
         } else if (this.hippogryphInventory.m_6643_() <= 3 || !this.m_38903_(itemstack1, 3, this.hippogryphInventory.m_6643_(), false)) {
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
      return this.hippogryphInventory.m_6542_(playerIn) && this.hippogryph.m_6084_() && this.hippogryph.m_20270_(playerIn) < 8.0F;
   }

   public void m_6877_(@NotNull Player playerIn) {
      super.m_6877_(playerIn);
      this.hippogryphInventory.m_5785_(playerIn);
   }
}
