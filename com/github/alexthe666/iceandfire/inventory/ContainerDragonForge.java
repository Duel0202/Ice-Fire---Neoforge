package com.github.alexthe666.iceandfire.inventory;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.FurnaceResultSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ContainerDragonForge extends AbstractContainerMenu {
   private final Container tileFurnace;
   public int fireType;
   protected final Level world;

   public ContainerDragonForge(int i, Inventory playerInventory) {
      this(i, new SimpleContainer(3), playerInventory, new SimpleContainerData(0));
   }

   public ContainerDragonForge(int id, Container furnaceInventory, Inventory playerInventory, ContainerData vars) {
      super((MenuType)IafContainerRegistry.DRAGON_FORGE_CONTAINER.get(), id);
      this.tileFurnace = furnaceInventory;
      this.world = playerInventory.f_35978_.m_9236_();
      if (furnaceInventory instanceof TileEntityDragonforge) {
         this.fireType = ((TileEntityDragonforge)furnaceInventory).fireType;
      } else if (IceAndFire.PROXY.getRefrencedTE() instanceof TileEntityDragonforge) {
         this.fireType = ((TileEntityDragonforge)IceAndFire.PROXY.getRefrencedTE()).fireType;
      }

      this.m_38897_(new Slot(furnaceInventory, 0, 68, 34));
      this.m_38897_(new Slot(furnaceInventory, 1, 86, 34));
      this.m_38897_(new FurnaceResultSlot(playerInventory.f_35978_, furnaceInventory, 2, 148, 35));

      int k;
      for(k = 0; k < 3; ++k) {
         for(int j = 0; j < 9; ++j) {
            this.m_38897_(new Slot(playerInventory, j + k * 9 + 9, 8 + j * 18, 84 + k * 18));
         }
      }

      for(k = 0; k < 9; ++k) {
         this.m_38897_(new Slot(playerInventory, k, 8 + k * 18, 142));
      }

   }

   public boolean m_6875_(@NotNull Player playerIn) {
      return this.tileFurnace.m_6542_(playerIn);
   }

   @NotNull
   public ItemStack m_7648_(@NotNull Player playerIn, int index) {
      ItemStack itemstack = ItemStack.f_41583_;
      Slot slot = (Slot)this.f_38839_.get(index);
      if (slot != null && slot.m_6657_()) {
         ItemStack itemstack1 = slot.m_7993_();
         itemstack = itemstack1.m_41777_();
         if (index == 2) {
            if (!this.m_38903_(itemstack1, 3, 39, true)) {
               return ItemStack.f_41583_;
            }

            slot.m_40234_(itemstack1, itemstack);
         } else if (index != 1 && index != 0) {
            if (this.fireType == 0) {
               if (!this.m_38903_(itemstack1, 0, 1, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (index < 30) {
               if (!this.m_38903_(itemstack1, 30, 39, false)) {
                  return ItemStack.f_41583_;
               }
            } else if (index < 39 && !this.m_38903_(itemstack1, 3, 30, false)) {
               return ItemStack.f_41583_;
            }
         } else if (!this.m_38903_(itemstack1, 3, 39, false)) {
            return ItemStack.f_41583_;
         }

         if (itemstack1.m_41619_()) {
            slot.m_5852_(ItemStack.f_41583_);
         } else {
            slot.m_6654_();
         }

         if (itemstack1.m_41613_() == itemstack.m_41613_()) {
            return ItemStack.f_41583_;
         }

         slot.m_142406_(playerIn, itemstack1);
      }

      return itemstack;
   }
}
