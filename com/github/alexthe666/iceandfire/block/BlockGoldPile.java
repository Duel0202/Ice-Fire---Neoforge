package com.github.alexthe666.iceandfire.block;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockGoldPile extends Block {
   public static final IntegerProperty LAYERS = IntegerProperty.m_61631_("layers", 1, 8);
   protected static final VoxelShape[] SHAPES = new VoxelShape[]{Shapes.m_83040_(), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.m_49796_(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};

   public BlockGoldPile() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283762_).m_60913_(0.3F, 1.0F).m_60977_().m_60918_(IafBlockRegistry.SOUND_TYPE_GOLD));
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(LAYERS, 1));
   }

   public boolean m_7357_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, PathComputationType type) {
      switch(type) {
      case LAND:
         return (Integer)state.m_61143_(LAYERS) < 5;
      case WATER:
         return false;
      case AIR:
         return false;
      default:
         return false;
      }
   }

   @NotNull
   public VoxelShape m_5940_(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return SHAPES[(Integer)state.m_61143_(LAYERS)];
   }

   @NotNull
   public VoxelShape m_5939_(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return SHAPES[(Integer)state.m_61143_(LAYERS) - 1];
   }

   public boolean m_7923_(@NotNull BlockState state) {
      return true;
   }

   public boolean m_7898_(@NotNull BlockState state, LevelReader worldIn, BlockPos pos) {
      BlockState blockstate = worldIn.m_8055_(pos.m_7495_());
      Block block = blockstate.m_60734_();
      if (block != Blocks.f_50126_ && block != Blocks.f_50354_ && block != Blocks.f_50375_) {
         if (block != Blocks.f_50719_ && block != Blocks.f_50135_) {
            return Block.m_49918_(blockstate.m_60812_(worldIn, pos.m_7495_()), Direction.UP) || block instanceof BlockGoldPile && (Integer)blockstate.m_61143_(LAYERS) == 8;
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   /** @deprecated */
   @Deprecated
   public boolean canEntitySpawn(BlockState state, Entity entityIn) {
      return false;
   }

   @NotNull
   public BlockState m_7417_(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
      return !stateIn.m_60710_(worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : super.m_7417_(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   @Nullable
   public BlockState m_5573_(BlockPlaceContext context) {
      BlockState blockstate = context.m_43725_().m_8055_(context.m_8083_());
      if (blockstate.m_60734_() == this) {
         int i = (Integer)blockstate.m_61143_(LAYERS);
         return (BlockState)blockstate.m_61124_(LAYERS, Math.min(8, i + 1));
      } else {
         return super.m_5573_(context);
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{LAYERS});
   }

   @NotNull
   public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player playerIn, @NotNull InteractionHand handIn, @NotNull BlockHitResult resultIn) {
      ItemStack item = playerIn.m_150109_().m_36056_();
      if (!item.m_41619_() && item.m_41720_() != null && item.m_41720_() == this.m_5456_() && !item.m_41619_() && (Integer)state.m_61143_(LAYERS) < 8) {
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(LAYERS, (Integer)state.m_61143_(LAYERS) + 1), 3);
         if (!playerIn.m_7500_()) {
            item.m_41774_(1);
            if (item.m_41619_()) {
               playerIn.m_150109_().m_6836_(playerIn.m_150109_().f_35977_, ItemStack.f_41583_);
            } else {
               playerIn.m_150109_().m_6836_(playerIn.m_150109_().f_35977_, item);
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }
}
