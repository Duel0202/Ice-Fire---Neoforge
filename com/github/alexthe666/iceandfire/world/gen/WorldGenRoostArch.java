package com.github.alexthe666.iceandfire.world.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class WorldGenRoostArch {
   private static final Direction[] HORIZONTALS;
   private final Block block;

   public WorldGenRoostArch(Block block) {
      this.block = block;
   }

   public boolean generate(LevelAccessor worldIn, RandomSource rand, BlockPos position) {
      int height = 3 + rand.m_188503_(3);
      int width = Math.min(3, height - 2);
      Direction direction = HORIZONTALS[rand.m_188503_(HORIZONTALS.length - 1)];
      boolean diagonal = rand.m_188499_();

      for(int i = 0; i < height; ++i) {
         worldIn.m_7731_(position.m_6630_(i), this.block.m_49966_(), 2);
      }

      BlockPos offsetPos = position;
      int placedWidths = 0;

      for(int i = 0; i < width; ++i) {
         offsetPos = position.m_6630_(height).m_5484_(direction, i);
         if (diagonal) {
            offsetPos = position.m_6630_(height).m_5484_(direction, i).m_5484_(direction.m_122427_(), i);
         }

         if (placedWidths < width - 1 || rand.m_188499_()) {
            worldIn.m_7731_(offsetPos, this.block.m_49966_(), 2);
         }

         ++placedWidths;
      }

      while(worldIn.m_46859_(offsetPos.m_7495_()) && offsetPos.m_123342_() > 0) {
         worldIn.m_7731_(offsetPos.m_7495_(), this.block.m_49966_(), 2);
         offsetPos = offsetPos.m_7495_();
      }

      return true;
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
