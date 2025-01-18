package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlockDragonforgeCore extends BaseEntityBlock implements IDragonProof, INoTab {
   private static boolean keepInventory;
   private final int isFire;
   private final boolean activated;

   public BlockDragonforgeCore(int isFire, boolean activated) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283906_).m_60988_().m_60913_(40.0F, 500.0F).m_60918_(SoundType.f_56743_).m_60953_((state) -> {
         return activated ? 15 : 0;
      }));
      this.isFire = isFire;
      this.activated = activated;
   }

   static String name(int dragonType, boolean activated) {
      return "dragonforge_%s_core%s".formatted(new Object[]{DragonType.getNameFromInt(dragonType), activated ? "" : "_disabled"});
   }

   public static void setState(int dragonType, boolean active, Level worldIn, BlockPos pos) {
      BlockEntity tileentity = worldIn.m_7702_(pos);
      keepInventory = true;
      if (active) {
         if (dragonType == 0) {
            worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get()).m_49966_(), 3);
            worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get()).m_49966_(), 3);
         } else if (dragonType == 1) {
            worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE.get()).m_49966_(), 3);
            worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE.get()).m_49966_(), 3);
         } else if (dragonType == 2) {
            worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE.get()).m_49966_(), 3);
            worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE.get()).m_49966_(), 3);
         }
      } else if (dragonType == 0) {
         worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get()).m_49966_(), 3);
         worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get()).m_49966_(), 3);
      } else if (dragonType == 1) {
         worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE_DISABLED.get()).m_49966_(), 3);
         worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE_DISABLED.get()).m_49966_(), 3);
      } else if (dragonType == 2) {
         worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get()).m_49966_(), 3);
         worldIn.m_7731_(pos, ((Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get()).m_49966_(), 3);
      }

      keepInventory = false;
      if (tileentity != null) {
         tileentity.m_6339_();
         worldIn.m_151523_(tileentity);
      }

   }

   @NotNull
   public PushReaction getPistonPushReaction(@NotNull BlockState state) {
      return PushReaction.BLOCK;
   }

   @NotNull
   public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
      if (!player.m_6144_()) {
         if (worldIn.f_46443_) {
            IceAndFire.PROXY.setRefrencedTE(worldIn.m_7702_(pos));
         } else {
            MenuProvider inamedcontainerprovider = this.m_7246_(state, worldIn, pos);
            if (inamedcontainerprovider != null) {
               player.m_5893_(inamedcontainerprovider);
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   public ItemStack getItem(Level worldIn, BlockPos pos, BlockState state) {
      if (this.isFire == 0) {
         return new ItemStack(((Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get()).m_5456_());
      } else if (this.isFire == 1) {
         return new ItemStack(((Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE_DISABLED.get()).m_5456_());
      } else {
         return this.isFire == 2 ? new ItemStack(((Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get()).m_5456_()) : new ItemStack(((Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get()).m_5456_());
      }
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   public void m_6810_(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
      BlockEntity tileentity = worldIn.m_7702_(pos);
      if (tileentity instanceof TileEntityDragonforge) {
         Containers.m_19002_(worldIn, pos, (TileEntityDragonforge)tileentity);
         worldIn.m_46717_(pos, this);
         worldIn.m_46747_(pos);
      }

   }

   public int m_6782_(@NotNull BlockState blockState, Level worldIn, @NotNull BlockPos pos) {
      return AbstractContainerMenu.m_38918_(worldIn.m_7702_(pos));
   }

   public boolean m_7278_(@NotNull BlockState state) {
      return true;
   }

   public boolean shouldBeInTab() {
      return !this.activated;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
      return m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.DRAGONFORGE_CORE.get(), TileEntityDragonforge::tick);
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityDragonforge(pos, state, this.isFire);
   }
}
