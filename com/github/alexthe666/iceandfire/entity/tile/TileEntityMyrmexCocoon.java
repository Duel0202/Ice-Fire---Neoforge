package com.github.alexthe666.iceandfire.entity.tile;

import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityMyrmexCocoon extends RandomizableContainerBlockEntity {
   private NonNullList<ItemStack> chestContents;

   public TileEntityMyrmexCocoon(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.MYRMEX_COCOON.get(), pos, state);
      this.chestContents = NonNullList.m_122780_(18, ItemStack.f_41583_);
   }

   public int m_6643_() {
      return 18;
   }

   public boolean m_7983_() {
      Iterator var1 = this.chestContents.iterator();

      ItemStack itemstack;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var1.next();
      } while(itemstack.m_41619_());

      return false;
   }

   public void m_142466_(@NotNull CompoundTag compound) {
      super.m_142466_(compound);
      this.chestContents = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      if (!this.m_59631_(compound)) {
         ContainerHelper.m_18980_(compound, this.chestContents);
      }

   }

   public void m_183515_(@NotNull CompoundTag compound) {
      if (!this.m_59634_(compound)) {
         ContainerHelper.m_18973_(compound, this.chestContents);
      }

   }

   @NotNull
   protected Component m_6820_() {
      return Component.m_237115_("container.myrmex_cocoon");
   }

   @NotNull
   protected AbstractContainerMenu m_6555_(int id, @NotNull Inventory player) {
      return new ChestMenu(MenuType.f_39958_, id, player, this, 2);
   }

   @Nullable
   public AbstractContainerMenu m_7208_(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
      return new ChestMenu(MenuType.f_39958_, id, playerInventory, this, 2);
   }

   public int m_6893_() {
      return 64;
   }

   @NotNull
   protected NonNullList<ItemStack> m_7086_() {
      return this.chestContents;
   }

   protected void m_6520_(@NotNull NonNullList<ItemStack> itemsIn) {
   }

   public void m_5856_(Player player) {
      this.m_59640_((Player)null);
      player.m_9236_().m_7785_((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), SoundEvents.f_12387_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
   }

   public void m_5785_(Player player) {
      this.m_59640_((Player)null);
      player.m_9236_().m_7785_((double)this.f_58858_.m_123341_(), (double)this.f_58858_.m_123342_(), (double)this.f_58858_.m_123343_(), SoundEvents.f_12388_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
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

   public boolean isFull(ItemStack heldStack) {
      Iterator var2 = this.chestContents.iterator();

      ItemStack itemstack;
      do {
         if (!var2.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var2.next();
      } while(!itemstack.m_41619_() && (heldStack == null || heldStack.m_41619_() || !ItemStack.m_41656_(itemstack, heldStack) || itemstack.m_41613_() + heldStack.m_41613_() >= itemstack.m_41741_()));

      return false;
   }
}
