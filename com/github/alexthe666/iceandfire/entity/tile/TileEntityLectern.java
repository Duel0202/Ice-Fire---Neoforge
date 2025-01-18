package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.inventory.ContainerLectern;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemBestiary;
import com.github.alexthe666.iceandfire.message.MessageUpdateLectern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.util.Mth;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.items.IItemHandler;
import net.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

public class TileEntityLectern extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final int[] slotsTop = new int[]{0};
   private static final int[] slotsSides = new int[]{1};
   private static final int[] slotsBottom = new int[]{0};
   private static final Random RANDOM = new Random();
   private static final ArrayList<EnumBestiaryPages> EMPTY_LIST = new ArrayList();
   public final ContainerData furnaceData = new ContainerData() {
      public int m_6413_(int index) {
         return 0;
      }

      public void m_8050_(int index, int value) {
      }

      public int m_6499_() {
         return 0;
      }
   };
   public float pageFlip;
   public float pageFlipPrev;
   public float pageHelp1;
   public float pageHelp2;
   public EnumBestiaryPages[] selectedPages = new EnumBestiaryPages[3];
   LazyOptional<? extends IItemHandler>[] handlers;
   private final Random localRand;
   private NonNullList<ItemStack> stacks;

   public TileEntityLectern(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.IAF_LECTERN.get(), pos, state);
      this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.UP, Direction.DOWN});
      this.localRand = new Random();
      this.stacks = NonNullList.m_122780_(3, ItemStack.f_41583_);
   }

   public static void bookAnimationTick(Level p_155504_, BlockPos p_155505_, BlockState p_155506_, TileEntityLectern p_155507_) {
      float f1 = p_155507_.pageHelp1;

      do {
         p_155507_.pageHelp1 += (float)(RANDOM.nextInt(4) - RANDOM.nextInt(4));
      } while(f1 == p_155507_.pageHelp1);

      p_155507_.pageFlipPrev = p_155507_.pageFlip;
      float f = (p_155507_.pageHelp1 - p_155507_.pageFlip) * 0.04F;
      float f3 = 0.02F;
      f = Mth.m_14036_(f, -f3, f3);
      p_155507_.pageHelp2 += (f - p_155507_.pageHelp2) * 0.9F;
      p_155507_.pageFlip += p_155507_.pageHelp2;
   }

   public int m_6643_() {
      return 2;
   }

   @NotNull
   public ItemStack m_8020_(int index) {
      return (ItemStack)this.stacks.get(index);
   }

   private List<EnumBestiaryPages> getPossiblePages() {
      List<EnumBestiaryPages> list = EnumBestiaryPages.possiblePages((ItemStack)this.stacks.get(0));
      return (List)(list != null && !list.isEmpty() ? list : EMPTY_LIST);
   }

   @NotNull
   public ItemStack m_7407_(int index, int count) {
      if (!((ItemStack)this.stacks.get(index)).m_41619_()) {
         ItemStack itemstack;
         if (((ItemStack)this.stacks.get(index)).m_41613_() <= count) {
            itemstack = (ItemStack)this.stacks.get(index);
            this.stacks.set(index, ItemStack.f_41583_);
            return itemstack;
         } else {
            itemstack = ((ItemStack)this.stacks.get(index)).m_41620_(count);
            if (((ItemStack)this.stacks.get(index)).m_41613_() == 0) {
               this.stacks.set(index, ItemStack.f_41583_);
            }

            return itemstack;
         }
      } else {
         return ItemStack.f_41583_;
      }
   }

   public void m_6836_(int index, ItemStack stack) {
      boolean isSame = !stack.m_41619_() && ItemStack.m_41656_(stack, (ItemStack)this.stacks.get(index)) && ItemStack.m_41728_(stack, (ItemStack)this.stacks.get(index));
      this.stacks.set(index, stack);
      if (!stack.m_41619_() && stack.m_41613_() > this.m_6893_()) {
         stack.m_41764_(this.m_6893_());
      }

      if (!isSame) {
         this.m_6596_();
         if (((ItemStack)this.stacks.get(1)).m_41619_()) {
            this.selectedPages[0] = null;
            this.selectedPages[1] = null;
            this.selectedPages[2] = null;
            IceAndFire.sendMSGToAll(new MessageUpdateLectern(this.f_58858_.m_121878_(), -1, -1, -1, false, 0));
         } else {
            this.selectedPages = this.randomizePages(this.m_8020_(0), this.m_8020_(1));
         }
      }

   }

   public EnumBestiaryPages[] randomizePages(ItemStack bestiary, ItemStack manuscript) {
      if (!this.f_58857_.f_46443_) {
         if (bestiary.m_41720_() == IafItemRegistry.BESTIARY.get()) {
            List<EnumBestiaryPages> possibleList = this.getPossiblePages();
            this.localRand.setSeed(this.f_58857_.m_46467_());
            Collections.shuffle(possibleList, this.localRand);
            if (!possibleList.isEmpty()) {
               this.selectedPages[0] = (EnumBestiaryPages)possibleList.get(0);
            } else {
               this.selectedPages[0] = null;
            }

            if (possibleList.size() > 1) {
               this.selectedPages[1] = (EnumBestiaryPages)possibleList.get(1);
            } else {
               this.selectedPages[1] = null;
            }

            if (possibleList.size() > 2) {
               this.selectedPages[2] = (EnumBestiaryPages)possibleList.get(2);
            } else {
               this.selectedPages[2] = null;
            }
         }

         int page1 = this.selectedPages[0] == null ? -1 : this.selectedPages[0].ordinal();
         int page2 = this.selectedPages[1] == null ? -1 : this.selectedPages[1].ordinal();
         int page3 = this.selectedPages[2] == null ? -1 : this.selectedPages[2].ordinal();
         IceAndFire.sendMSGToAll(new MessageUpdateLectern(this.f_58858_.m_121878_(), page1, page2, page3, false, 0));
      }

      return this.selectedPages;
   }

   public void m_142466_(@NotNull CompoundTag compound) {
      super.m_142466_(compound);
      this.stacks = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      ContainerHelper.m_18980_(compound, this.stacks);
   }

   public void m_183515_(@NotNull CompoundTag compound) {
      super.m_183515_(compound);
      ContainerHelper.m_18973_(compound, this.stacks);
   }

   public void m_5856_(@NotNull Player player) {
   }

   public void m_5785_(@NotNull Player player) {
   }

   public boolean m_7013_(int index, ItemStack stack) {
      if (stack.m_41619_()) {
         return false;
      } else if (index == 0) {
         return stack.m_41720_() instanceof ItemBestiary;
      } else if (index == 1) {
         return stack.m_41720_() == IafItemRegistry.MANUSCRIPT.get();
      } else {
         return false;
      }
   }

   public int m_6893_() {
      return 64;
   }

   public boolean m_6542_(@NotNull Player player) {
      return true;
   }

   public void m_6211_() {
      this.stacks.clear();
   }

   @NotNull
   public Component m_7755_() {
      return Component.m_237115_("block.iceandfire.lectern");
   }

   public boolean m_7157_(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
      return false;
   }

   public boolean m_8077_() {
      return false;
   }

   @NotNull
   public int[] m_7071_(@NotNull Direction side) {
      return side == Direction.DOWN ? slotsBottom : (side == Direction.UP ? slotsTop : slotsSides);
   }

   public boolean m_7155_(int index, @NotNull ItemStack itemStackIn, Direction direction) {
      return this.m_7013_(index, itemStackIn);
   }

   @NotNull
   public ItemStack m_8016_(int index) {
      return ItemStack.f_41583_;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
      this.m_142466_(packet.m_131708_());
   }

   @NotNull
   public CompoundTag m_5995_() {
      return this.m_187480_();
   }

   @NotNull
   protected Component m_6820_() {
      return this.m_7755_();
   }

   @NotNull
   protected AbstractContainerMenu m_6555_(int id, @NotNull Inventory player) {
      return null;
   }

   public boolean m_7983_() {
      Iterator var1 = this.stacks.iterator();

      ItemStack itemstack;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var1.next();
      } while(itemstack.m_41619_());

      return false;
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
      if (!this.f_58859_ && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
         return facing == Direction.DOWN ? this.handlers[1].cast() : this.handlers[0].cast();
      } else {
         return super.getCapability(capability, facing);
      }
   }

   @Nullable
   public AbstractContainerMenu m_7208_(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
      return new ContainerLectern(id, this, playerInventory, this.furnaceData);
   }
}
