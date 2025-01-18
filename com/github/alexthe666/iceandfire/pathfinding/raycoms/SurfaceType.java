package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.MagmaBlock;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.VoxelShape;

public enum SurfaceType {
   WALKABLE,
   DROPABLE,
   NOT_PASSABLE,
   FLYABLE;

   public static SurfaceType getSurfaceType(BlockGetter world, BlockState blockState, BlockPos pos) {
      Block block = blockState.m_60734_();
      if (!(block instanceof FenceBlock) && !(block instanceof FenceGateBlock) && !(block instanceof WallBlock) && !(block instanceof FireBlock) && !(block instanceof CampfireBlock) && !(block instanceof BambooStalkBlock) && !(block instanceof BambooSaplingBlock) && !(block instanceof DoorBlock) && !(block instanceof MagmaBlock) && !(block instanceof PowderSnowBlock)) {
         if (block instanceof TrapDoorBlock && !(Boolean)blockState.m_61143_(TrapDoorBlock.f_57514_)) {
            return WALKABLE;
         } else {
            VoxelShape shape = blockState.m_60808_(world, pos);
            if (shape.m_83297_(Axis.Y) > 1.0D) {
               return NOT_PASSABLE;
            } else {
               FluidState fluid = world.m_6425_(pos);
               if (blockState.m_60734_() != Blocks.f_49991_ && (fluid == null || fluid.m_76178_() || fluid.m_76152_() != Fluids.f_76195_ && fluid.m_76152_() != Fluids.f_76194_)) {
                  if (isWater(world, pos, blockState, fluid)) {
                     return WALKABLE;
                  } else if (!(block instanceof SignBlock) && !(block instanceof VineBlock)) {
                     return (!blockState.m_280296_() || !(shape.m_83297_(Axis.X) - shape.m_83288_(Axis.X) > 0.75D) || !(shape.m_83297_(Axis.Z) - shape.m_83288_(Axis.Z) > 0.75D)) && (blockState.m_60734_() != Blocks.f_50125_ || (Integer)blockState.m_61143_(SnowLayerBlock.f_56581_) <= 1) && !(block instanceof CarpetBlock) ? DROPABLE : WALKABLE;
                  } else {
                     return DROPABLE;
                  }
               } else {
                  return NOT_PASSABLE;
               }
            }
         }
      } else {
         return NOT_PASSABLE;
      }
   }

   public static boolean isWater(LevelReader world, BlockPos pos) {
      return isWater(world, pos, (BlockState)null, (FluidState)null);
   }

   public static boolean isWater(BlockGetter world, BlockPos pos, @Nullable BlockState pState, @Nullable FluidState pFluidState) {
      BlockState state = pState;
      if (pState == null) {
         state = world.m_8055_(pos);
      }

      if (state.m_60815_()) {
         return false;
      } else if (state.m_60734_() == Blocks.f_49990_) {
         return true;
      } else {
         FluidState fluidState = pFluidState;
         if (pFluidState == null) {
            fluidState = world.m_6425_(pos);
         }

         if (fluidState.m_76178_()) {
            return false;
         } else if ((state.m_60734_() instanceof TrapDoorBlock || state.m_60734_() instanceof HorizontalDirectionalBlock) && state.m_61138_(TrapDoorBlock.f_57514_) && !(Boolean)state.m_61143_(TrapDoorBlock.f_57514_) && state.m_61138_(TrapDoorBlock.f_57515_) && state.m_61143_(TrapDoorBlock.f_57515_) == Half.TOP) {
            return false;
         } else {
            Fluid fluid = fluidState.m_76152_();
            return fluid == Fluids.f_76193_ || fluid == Fluids.f_76192_;
         }
      }
   }

   // $FF: synthetic method
   private static SurfaceType[] $values() {
      return new SurfaceType[]{WALKABLE, DROPABLE, NOT_PASSABLE, FLYABLE};
   }
}
