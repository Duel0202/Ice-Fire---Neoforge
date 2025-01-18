package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.BlockDragonforgeBricks;
import com.github.alexthe666.iceandfire.block.BlockDragonforgeCore;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.inventory.ContainerDragonForge;
import com.github.alexthe666.iceandfire.message.MessageUpdateDragonforge;
import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
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
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.items.IItemHandler;
import net.neoforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

public class TileEntityDragonforge extends BaseContainerBlockEntity implements WorldlyContainer {
   private static final int[] SLOTS_TOP = new int[]{0, 1};
   private static final int[] SLOTS_BOTTOM = new int[]{2};
   private static final int[] SLOTS_SIDES = new int[]{0, 1};
   private static final Direction[] HORIZONTALS;
   public int fireType;
   public int cookTime;
   public int lastDragonFlameTimer = 0;
   LazyOptional<? extends IItemHandler>[] handlers;
   private NonNullList<ItemStack> forgeItemStacks;
   private boolean prevAssembled;
   private boolean canAddFlameAgain;

   public TileEntityDragonforge(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.DRAGONFORGE_CORE.get(), pos, state);
      this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH});
      this.forgeItemStacks = NonNullList.m_122780_(3, ItemStack.f_41583_);
      this.canAddFlameAgain = true;
   }

   public TileEntityDragonforge(BlockPos pos, BlockState state, int fireType) {
      super((BlockEntityType)IafTileEntityRegistry.DRAGONFORGE_CORE.get(), pos, state);
      this.handlers = SidedInvWrapper.create(this, new Direction[]{Direction.UP, Direction.DOWN, Direction.NORTH});
      this.forgeItemStacks = NonNullList.m_122780_(3, ItemStack.f_41583_);
      this.canAddFlameAgain = true;
      this.fireType = fireType;
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TileEntityDragonforge entityDragonforge) {
      boolean flag = entityDragonforge.isBurning();
      boolean flag1 = false;
      entityDragonforge.fireType = entityDragonforge.getFireType(entityDragonforge.m_58900_().m_60734_());
      if (entityDragonforge.lastDragonFlameTimer > 0) {
         --entityDragonforge.lastDragonFlameTimer;
      }

      entityDragonforge.updateGrills(entityDragonforge.assembled());
      if (!level.f_46443_) {
         if (entityDragonforge.prevAssembled != entityDragonforge.assembled()) {
            BlockDragonforgeCore.setState(entityDragonforge.fireType, entityDragonforge.prevAssembled, level, pos);
         }

         entityDragonforge.prevAssembled = entityDragonforge.assembled();
         if (!entityDragonforge.assembled()) {
            return;
         }
      }

      if (entityDragonforge.cookTime > 0 && entityDragonforge.canSmelt() && entityDragonforge.lastDragonFlameTimer == 0) {
         --entityDragonforge.cookTime;
      }

      if (entityDragonforge.m_8020_(0).m_41619_() && !level.f_46443_) {
         entityDragonforge.cookTime = 0;
      }

      if (!entityDragonforge.f_58857_.f_46443_) {
         if (entityDragonforge.isBurning()) {
            if (entityDragonforge.canSmelt()) {
               ++entityDragonforge.cookTime;
               if (entityDragonforge.cookTime >= entityDragonforge.getMaxCookTime()) {
                  entityDragonforge.cookTime = 0;
                  entityDragonforge.smeltItem();
                  flag1 = true;
               }
            } else if (entityDragonforge.cookTime > 0) {
               IceAndFire.sendMSGToAll(new MessageUpdateDragonforge(pos.m_121878_(), entityDragonforge.cookTime));
               entityDragonforge.cookTime = 0;
            }
         } else if (!entityDragonforge.isBurning() && entityDragonforge.cookTime > 0) {
            entityDragonforge.cookTime = Mth.m_14045_(entityDragonforge.cookTime - 2, 0, entityDragonforge.getMaxCookTime());
         }

         if (flag != entityDragonforge.isBurning()) {
            flag1 = true;
         }
      }

      if (flag1) {
         entityDragonforge.m_6596_();
      }

      if (!entityDragonforge.canAddFlameAgain) {
         entityDragonforge.canAddFlameAgain = true;
      }

   }

   public int m_6643_() {
      return this.forgeItemStacks.size();
   }

   public boolean m_7983_() {
      Iterator var1 = this.forgeItemStacks.iterator();

      ItemStack itemstack;
      do {
         if (!var1.hasNext()) {
            return true;
         }

         itemstack = (ItemStack)var1.next();
      } while(itemstack.m_41619_());

      return false;
   }

   private void updateGrills(boolean grill) {
      Direction[] var2 = HORIZONTALS;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Direction facing = var2[var4];
         BlockPos grillPos = this.m_58899_().m_121945_(facing);
         if (this.grillMatches(this.f_58857_.m_8055_(grillPos).m_60734_())) {
            BlockState grillState = (BlockState)this.getGrillBlock().m_49966_().m_61124_(BlockDragonforgeBricks.GRILL, grill);
            if (this.f_58857_.m_8055_(grillPos) != grillState) {
               this.f_58857_.m_46597_(grillPos, grillState);
            }
         }
      }

   }

   public Block getGrillBlock() {
      Block var10000;
      switch(this.fireType) {
      case 1:
         var10000 = (Block)IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get();
         break;
      case 2:
         var10000 = (Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get();
         break;
      default:
         var10000 = (Block)IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get();
      }

      return var10000;
   }

   public boolean grillMatches(Block block) {
      boolean var10000;
      switch(this.fireType) {
      case 0:
         var10000 = block == IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get();
         break;
      case 1:
         var10000 = block == IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get();
         break;
      case 2:
         var10000 = block == IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get();
         break;
      default:
         var10000 = false;
      }

      return var10000;
   }

   @NotNull
   public ItemStack m_8020_(int index) {
      return (ItemStack)this.forgeItemStacks.get(index);
   }

   @NotNull
   public ItemStack m_7407_(int index, int count) {
      return ContainerHelper.m_18969_(this.forgeItemStacks, index, count);
   }

   @NotNull
   public ItemStack m_8016_(int index) {
      return ContainerHelper.m_18966_(this.forgeItemStacks, index);
   }

   public void m_6836_(int index, ItemStack stack) {
      ItemStack itemstack = (ItemStack)this.forgeItemStacks.get(index);
      boolean flag = !stack.m_41619_() && ItemStack.m_41656_(stack, itemstack) && ItemStack.m_41728_(stack, itemstack);
      this.forgeItemStacks.set(index, stack);
      if (stack.m_41613_() > this.m_6893_()) {
         stack.m_41764_(this.m_6893_());
      }

      if (index == 0 && !flag || this.cookTime > this.getMaxCookTime()) {
         this.cookTime = 0;
         this.m_6596_();
      }

   }

   public void m_142466_(@NotNull CompoundTag compound) {
      super.m_142466_(compound);
      this.forgeItemStacks = NonNullList.m_122780_(this.m_6643_(), ItemStack.f_41583_);
      ContainerHelper.m_18980_(compound, this.forgeItemStacks);
      this.cookTime = compound.m_128451_("CookTime");
   }

   public void m_183515_(CompoundTag compound) {
      compound.m_128405_("CookTime", (short)this.cookTime);
      ContainerHelper.m_18973_(compound, this.forgeItemStacks);
   }

   public int m_6893_() {
      return 64;
   }

   public boolean isBurning() {
      return this.cookTime > 0;
   }

   public int getFireType(Block block) {
      if (block != IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get() && block != IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get()) {
         if (block != IafBlockRegistry.DRAGONFORGE_ICE_CORE.get() && block != IafBlockRegistry.DRAGONFORGE_ICE_CORE_DISABLED.get()) {
            return block != IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE.get() && block != IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get() ? 0 : 2;
         } else {
            return 1;
         }
      } else {
         return 0;
      }
   }

   public String getTypeID() {
      String var10000;
      switch(this.getFireType(this.m_58900_().m_60734_())) {
      case 0:
         var10000 = "fire";
         break;
      case 1:
         var10000 = "ice";
         break;
      case 2:
         var10000 = "lightning";
         break;
      default:
         var10000 = "";
      }

      return var10000;
   }

   public int getMaxCookTime() {
      return (Integer)this.getCurrentRecipe().map(DragonForgeRecipe::getCookTime).orElse(100);
   }

   private Block getDefaultOutput() {
      return this.fireType == 1 ? (Block)IafBlockRegistry.DRAGON_ICE.get() : (Block)IafBlockRegistry.ASH.get();
   }

   private ItemStack getCurrentResult() {
      Optional<DragonForgeRecipe> recipe = this.getCurrentRecipe();
      return (ItemStack)recipe.map(DragonForgeRecipe::getResultItem).orElseGet(() -> {
         return new ItemStack(this.getDefaultOutput());
      });
   }

   public Optional<DragonForgeRecipe> getCurrentRecipe() {
      return this.f_58857_.m_7465_().m_44015_((RecipeType)IafRecipeRegistry.DRAGON_FORGE_TYPE.get(), this, this.f_58857_);
   }

   public List<DragonForgeRecipe> getRecipes() {
      return this.f_58857_.m_7465_().m_44013_((RecipeType)IafRecipeRegistry.DRAGON_FORGE_TYPE.get());
   }

   public boolean canSmelt() {
      ItemStack cookStack = (ItemStack)this.forgeItemStacks.get(0);
      if (cookStack.m_41619_()) {
         return false;
      } else {
         ItemStack forgeRecipeOutput = this.getCurrentResult();
         if (forgeRecipeOutput.m_41619_()) {
            return false;
         } else {
            ItemStack outputStack = (ItemStack)this.forgeItemStacks.get(2);
            if (!outputStack.m_41619_() && !ItemStack.m_41656_(outputStack, forgeRecipeOutput)) {
               return false;
            } else {
               int calculatedOutputCount = outputStack.m_41613_() + forgeRecipeOutput.m_41613_();
               return calculatedOutputCount <= this.m_6893_() && calculatedOutputCount <= outputStack.m_41741_();
            }
         }
      }
   }

   public boolean m_6542_(@NotNull Player player) {
      if (player.m_9236_().m_7702_(this.f_58858_) != this) {
         return false;
      } else {
         return player.m_20275_((double)this.f_58858_.m_123341_() + 0.5D, (double)this.f_58858_.m_123342_() + 0.5D, (double)this.f_58858_.m_123343_() + 0.5D) <= 64.0D;
      }
   }

   public void smeltItem() {
      if (this.canSmelt()) {
         ItemStack cookStack = (ItemStack)this.forgeItemStacks.get(0);
         ItemStack bloodStack = (ItemStack)this.forgeItemStacks.get(1);
         ItemStack outputStack = (ItemStack)this.forgeItemStacks.get(2);
         ItemStack output = this.getCurrentResult();
         if (outputStack.m_41619_()) {
            this.forgeItemStacks.set(2, output.m_41777_());
         } else {
            outputStack.m_41769_(output.m_41613_());
         }

         cookStack.m_41774_(1);
         bloodStack.m_41774_(1);
      }
   }

   public boolean m_7013_(int index, @NotNull ItemStack stack) {
      boolean var10000;
      switch(index) {
      case 0:
         var10000 = true;
         break;
      case 1:
         var10000 = this.getRecipes().stream().anyMatch((item) -> {
            return item.isValidBlood(stack);
         });
         break;
      default:
         var10000 = false;
      }

      return var10000;
   }

   @NotNull
   public int[] m_7071_(@NotNull Direction side) {
      if (side == Direction.DOWN) {
         return SLOTS_BOTTOM;
      } else {
         return side == Direction.UP ? SLOTS_TOP : SLOTS_SIDES;
      }
   }

   public boolean m_7155_(int index, @NotNull ItemStack itemStackIn, Direction direction) {
      return this.m_7013_(index, itemStackIn);
   }

   public boolean m_7157_(int index, @NotNull ItemStack stack, @NotNull Direction direction) {
      if (direction == Direction.DOWN && index == 1) {
         Item item = stack.m_41720_();
         return item == Items.f_42447_ || item == Items.f_42446_;
      } else {
         return true;
      }
   }

   public void m_6211_() {
      this.forgeItemStacks.clear();
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
      if (!this.f_58859_ && facing != null && capability == ForgeCapabilities.ITEM_HANDLER) {
         if (facing == Direction.UP) {
            return this.handlers[0].cast();
         } else {
            return facing == Direction.DOWN ? this.handlers[1].cast() : this.handlers[2].cast();
         }
      } else {
         return super.getCapability(capability, facing);
      }
   }

   @NotNull
   protected Component m_6820_() {
      return Component.m_237115_("container.dragonforge_fire" + DragonType.getNameFromInt(this.fireType));
   }

   public void transferPower(int i) {
      if (!this.f_58857_.f_46443_) {
         if (this.canSmelt()) {
            if (this.canAddFlameAgain) {
               this.cookTime = Math.min(this.getMaxCookTime() + 1, this.cookTime + i);
               this.canAddFlameAgain = false;
            }
         } else {
            this.cookTime = 0;
         }

         IceAndFire.sendMSGToAll(new MessageUpdateDragonforge(this.f_58858_.m_121878_(), this.cookTime));
      }

      this.lastDragonFlameTimer = 40;
   }

   private boolean checkBoneCorners(BlockPos pos) {
      return this.doesBlockEqual(pos.m_122012_().m_122029_(), (Block)IafBlockRegistry.DRAGON_BONE_BLOCK.get()) && this.doesBlockEqual(pos.m_122012_().m_122024_(), (Block)IafBlockRegistry.DRAGON_BONE_BLOCK.get()) && this.doesBlockEqual(pos.m_122019_().m_122029_(), (Block)IafBlockRegistry.DRAGON_BONE_BLOCK.get()) && this.doesBlockEqual(pos.m_122019_().m_122024_(), (Block)IafBlockRegistry.DRAGON_BONE_BLOCK.get());
   }

   private boolean checkBrickCorners(BlockPos pos) {
      return this.doesBlockEqual(pos.m_122012_().m_122029_(), this.getBrick()) && this.doesBlockEqual(pos.m_122012_().m_122024_(), this.getBrick()) && this.doesBlockEqual(pos.m_122019_().m_122029_(), this.getBrick()) && this.doesBlockEqual(pos.m_122019_().m_122024_(), this.getBrick());
   }

   private boolean checkBrickSlots(BlockPos pos) {
      return this.doesBlockEqual(pos.m_122012_(), this.getBrick()) && this.doesBlockEqual(pos.m_122029_(), this.getBrick()) && this.doesBlockEqual(pos.m_122024_(), this.getBrick()) && this.doesBlockEqual(pos.m_122019_(), this.getBrick());
   }

   private boolean checkY(BlockPos pos) {
      return this.doesBlockEqual(pos.m_7494_(), this.getBrick()) && this.doesBlockEqual(pos.m_7495_(), this.getBrick());
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

   public boolean assembled() {
      return this.checkBoneCorners(this.f_58858_.m_7495_()) && this.checkBrickSlots(this.f_58858_.m_7495_()) && this.checkBrickCorners(this.f_58858_) && this.atleastThreeAreBricks(this.f_58858_) && this.checkY(this.f_58858_) && this.checkBoneCorners(this.f_58858_.m_7494_()) && this.checkBrickSlots(this.f_58858_.m_7494_());
   }

   private Block getBrick() {
      Block var10000;
      switch(this.fireType) {
      case 0:
         var10000 = (Block)IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get();
         break;
      case 1:
         var10000 = (Block)IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get();
         break;
      default:
         var10000 = (Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get();
      }

      return var10000;
   }

   private boolean doesBlockEqual(BlockPos pos, Block block) {
      return this.f_58857_.m_8055_(pos).m_60734_() == block;
   }

   private boolean atleastThreeAreBricks(BlockPos pos) {
      int count = 0;
      Direction[] var3 = HORIZONTALS;
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Direction facing = var3[var5];
         if (this.f_58857_.m_8055_(pos.m_121945_(facing)).m_60734_() == this.getBrick()) {
            ++count;
         }
      }

      return count > 2;
   }

   @Nullable
   public AbstractContainerMenu m_7208_(int id, @NotNull Inventory playerInventory, @NotNull Player player) {
      return new ContainerDragonForge(id, this, playerInventory, new SimpleContainerData(0));
   }

   @NotNull
   protected AbstractContainerMenu m_6555_(int id, @NotNull Inventory player) {
      return new ContainerDragonForge(id, this, player, new SimpleContainerData(0));
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
