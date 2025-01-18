package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.inventory.ContainerPodium;
import com.github.alexthe666.iceandfire.item.ItemDragonEgg;
import com.github.alexthe666.iceandfire.item.ItemMyrmexEgg;
import com.github.alexthe666.iceandfire.message.MessageUpdatePodium;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.items.IItemHandler;
import net.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

public class TileEntityPodium extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final int[] slotsTop = new int[]{0};
   public int ticksExisted;
   public int prevTicksExisted;
   IItemHandler handlerUp;
   IItemHandler handlerDown;
   LazyOptional<? extends IItemHandler>[] handlers;
   private NonNullList<ItemStack> stacks;

   public TileEntityPodium(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.PODIUM.get(), pos, state);
      this.handlerUp = new SidedInvWrapper(this, Direction.UP);
      this.handlerDown = new SidedInvWrapper(this, Direction.DOWN);
      this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.UP, Direction.DOWN});
      this.stacks = NonNullList.m_122780_(1, ItemStack.f_41583_);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TileEntityPodium entityPodium) {
      entityPodium.prevTicksExisted = entityPodium.ticksExisted++;
   }

   public AABB getRenderBoundingBox() {
      return new AABB(this.f_58858_, this.f_58858_.m_7918_(1, 3, 1));
   }

   public int m_6643_() {
      return this.stacks.size();
   }

   @NotNull
   public ItemStack m_8020_(int index) {
      return (ItemStack)this.stacks.get(index);
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
            if (((ItemStack)this.stacks.get(index)).m_41619_()) {
               this.stacks.set(index, ItemStack.f_41583_);
            }

            return itemstack;
         }
      } else {
         return ItemStack.f_41583_;
      }
   }

   public ItemStack getStackInSlotOnClosing(int index) {
      if (!((ItemStack)this.stacks.get(index)).m_41619_()) {
         ItemStack itemstack = (ItemStack)this.stacks.get(index);
         this.stacks.set(index, itemstack);
         return itemstack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public void m_6836_(int index, @NotNull ItemStack stack) {
      this.stacks.set(index, stack);
      if (!stack.m_41619_() && stack.m_41613_() > this.m_6893_()) {
         stack.m_41764_(this.m_6893_());
      }

      this.m_183515_(this.m_5995_());
      if (!this.f_58857_.f_46443_) {
         IceAndFire.sendMSGToAll(new MessageUpdatePodium(this.m_58899_().m_121878_(), (ItemStack)this.stacks.get(0)));
      }

   }

   public void m_142466_(@NotNull CompoundTag compound) {
      super.m_142466_(compound);
      this.stacks = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      ContainerHelper.m_18980_(compound, this.stacks);
   }

   public void m_183515_(@NotNull CompoundTag compound) {
      ContainerHelper.m_18973_(compound, this.stacks);
   }

   public void m_5856_(@NotNull Player player) {
   }

   public void m_5785_(@NotNull Player player) {
   }

   public boolean m_7155_(int index, @NotNull ItemStack stack, Direction direction) {
      return index != 0 || stack.m_41720_() instanceof ItemDragonEgg || stack.m_41720_() instanceof ItemMyrmexEgg;
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
   public int[] m_7071_(@NotNull Direction side) {
      return slotsTop;
   }

   public boolean m_7157_(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
      return false;
   }

   public boolean m_8077_() {
      return false;
   }

   public boolean m_7013_(int index, @NotNull ItemStack stack) {
      return false;
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
   public ItemStack m_8016_(int index) {
      return ItemStack.f_41583_;
   }

   @NotNull
   public Component m_5446_() {
      return this.m_6820_();
   }

   @NotNull
   protected Component m_6820_() {
      return Component.m_237115_("block.iceandfire.podium");
   }

   @NotNull
   protected AbstractContainerMenu m_6555_(int id, @NotNull Inventory player) {
      return null;
   }

   public boolean m_7983_() {
      for(int i = 0; i < this.m_6643_(); ++i) {
         if (!this.m_8020_(i).m_41619_()) {
            return false;
         }
      }

      return true;
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
      return new ContainerPodium(id, this, playerInventory, new SimpleContainerData(0));
   }
}
