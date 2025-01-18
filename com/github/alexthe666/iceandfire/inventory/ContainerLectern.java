package com.github.alexthe666.iceandfire.inventory;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityLectern;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemBestiary;
import com.github.alexthe666.iceandfire.message.MessageUpdateLectern;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
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

public class ContainerLectern extends AbstractContainerMenu {
   private final Container tileFurnace;
   private final int[] possiblePagesInt;

   public ContainerLectern(int i, Inventory playerInventory) {
      this(i, new SimpleContainer(2), playerInventory, new SimpleContainerData(0));
   }

   public ContainerLectern(int id, Container furnaceInventory, Inventory playerInventory, ContainerData vars) {
      super((MenuType)IafContainerRegistry.IAF_LECTERN_CONTAINER.get(), id);
      this.possiblePagesInt = new int[3];
      this.tileFurnace = furnaceInventory;
      this.m_38897_(new SlotLectern(furnaceInventory, 0, 15, 47) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return super.m_5857_(stack) && !stack.m_41619_() && stack.m_41720_() instanceof ItemBestiary;
         }
      });
      this.m_38897_(new Slot(furnaceInventory, 1, 35, 47) {
         public boolean m_5857_(@NotNull ItemStack stack) {
            return super.m_5857_(stack) && !stack.m_41619_() && stack.m_41720_() == IafItemRegistry.MANUSCRIPT.get();
         }
      });

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

   private static int getPageField(int i) {
      if (IceAndFire.PROXY.getRefrencedTE() instanceof TileEntityLectern) {
         TileEntityLectern lectern = (TileEntityLectern)IceAndFire.PROXY.getRefrencedTE();
         return lectern.selectedPages[i] == null ? -1 : lectern.selectedPages[i].ordinal();
      } else {
         return -1;
      }
   }

   public void m_38946_() {
      super.m_38946_();
   }

   public void onUpdate() {
      this.possiblePagesInt[0] = getPageField(0);
      this.possiblePagesInt[1] = getPageField(1);
      this.possiblePagesInt[2] = getPageField(2);
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
         if (index < this.tileFurnace.m_6643_()) {
            if (!this.m_38903_(itemstack1, this.tileFurnace.m_6643_(), this.f_38839_.size(), true)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(0).m_5857_(itemstack1) && !this.m_38853_(0).m_6657_()) {
            if (!this.m_38903_(itemstack1, 0, 1, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.m_38853_(1).m_5857_(itemstack1) && !this.m_38853_(1).m_6657_()) {
            if (!this.m_38903_(itemstack1, 1, 2, false)) {
               return ItemStack.f_41583_;
            }
         } else if (this.tileFurnace.m_6643_() <= 5 || !this.m_38903_(itemstack1, 5, this.tileFurnace.m_6643_(), false)) {
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

   public int getManuscriptAmount() {
      ItemStack itemstack = this.tileFurnace.m_8020_(1);
      return !itemstack.m_41619_() && itemstack.m_41720_() == IafItemRegistry.MANUSCRIPT.get() ? itemstack.m_41613_() : 0;
   }

   public EnumBestiaryPages[] getPossiblePages() {
      this.possiblePagesInt[0] = getPageField(0);
      this.possiblePagesInt[1] = getPageField(1);
      this.possiblePagesInt[2] = getPageField(2);
      EnumBestiaryPages[] pages = new EnumBestiaryPages[3];
      if (this.tileFurnace.m_8020_(0).m_41720_() == IafItemRegistry.BESTIARY.get()) {
         if (this.possiblePagesInt[0] < 0) {
            pages[0] = null;
         } else {
            pages[0] = EnumBestiaryPages.values()[Math.min(EnumBestiaryPages.values().length, this.possiblePagesInt[0])];
         }

         if (this.possiblePagesInt[1] < 0) {
            pages[1] = null;
         } else {
            pages[1] = EnumBestiaryPages.values()[Math.min(EnumBestiaryPages.values().length, this.possiblePagesInt[1])];
         }

         if (this.possiblePagesInt[2] < 0) {
            pages[2] = null;
         } else {
            pages[2] = EnumBestiaryPages.values()[Math.min(EnumBestiaryPages.values().length, this.possiblePagesInt[2])];
         }
      }

      return pages;
   }

   public boolean m_6366_(Player playerIn, int id) {
      this.possiblePagesInt[0] = getPageField(0);
      this.possiblePagesInt[1] = getPageField(1);
      this.possiblePagesInt[2] = getPageField(2);
      ItemStack bookStack = this.tileFurnace.m_8020_(0);
      ItemStack manuscriptStack = this.tileFurnace.m_8020_(1);
      int i = 3;
      if (!playerIn.m_9236_().f_46443_ && !playerIn.m_7500_()) {
         manuscriptStack.m_41774_(i);
         if (manuscriptStack.m_41619_()) {
            this.tileFurnace.m_6836_(1, ItemStack.f_41583_);
         }

         return false;
      } else if ((manuscriptStack.m_41619_() || manuscriptStack.m_41613_() < i || manuscriptStack.m_41720_() != IafItemRegistry.MANUSCRIPT.get()) && !playerIn.m_7500_()) {
         return false;
      } else if (this.possiblePagesInt[id] > 0 && !bookStack.m_41619_()) {
         EnumBestiaryPages page = this.getPossiblePages()[Mth.m_14045_(id, 0, 2)];
         if (page != null) {
            if (bookStack.m_41720_() == IafItemRegistry.BESTIARY.get()) {
               this.tileFurnace.m_6836_(0, bookStack);
               if (IceAndFire.PROXY.getRefrencedTE() instanceof TileEntityLectern) {
                  if (!playerIn.m_9236_().f_46443_) {
                     if (bookStack.m_41720_() == IafItemRegistry.BESTIARY.get()) {
                        EnumBestiaryPages.addPage(EnumBestiaryPages.fromInt(page.ordinal()), bookStack);
                     }

                     Container var8 = this.tileFurnace;
                     if (var8 instanceof TileEntityLectern) {
                        TileEntityLectern entityLectern = (TileEntityLectern)var8;
                        entityLectern.randomizePages(bookStack, manuscriptStack);
                     }
                  } else {
                     IceAndFire.sendMSGToServer(new MessageUpdateLectern(IceAndFire.PROXY.getRefrencedTE().m_58899_().m_121878_(), 0, 0, 0, true, page.ordinal()));
                  }
               }
            }

            this.tileFurnace.m_6596_();
            this.m_6199_(this.tileFurnace);
            playerIn.m_9236_().m_5594_((Player)null, playerIn.m_20183_(), IafSoundRegistry.BESTIARY_PAGE, SoundSource.BLOCKS, 1.0F, playerIn.m_9236_().f_46441_.m_188501_() * 0.1F + 0.9F);
         }

         this.onUpdate();
         return true;
      } else {
         return false;
      }
   }
}
