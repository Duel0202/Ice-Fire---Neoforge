package com.github.alexthe666.iceandfire.world.gen;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class WorldGenCaveStalactites {
   private final Block block;
   private int maxHeight = 3;

   public WorldGenCaveStalactites(Block block, int maxHeight) {
      this.block = block;
      this.maxHeight = maxHeight;
   }

   public boolean generate(LevelAccessor worldIn, RandomSource rand, BlockPos position) {
      int height = this.maxHeight + rand.m_188503_(3);

      for(int i = 0; i < height; ++i) {
         if (i < height / 2) {
            worldIn.m_7731_(position.m_6625_(i).m_122012_(), this.block.m_49966_(), 2);
            worldIn.m_7731_(position.m_6625_(i).m_122029_(), this.block.m_49966_(), 2);
            worldIn.m_7731_(position.m_6625_(i).m_122019_(), this.block.m_49966_(), 2);
            worldIn.m_7731_(position.m_6625_(i).m_122024_(), this.block.m_49966_(), 2);
         }

         worldIn.m_7731_(position.m_6625_(i), this.block.m_49966_(), 2);
      }

      return true;
   }
}
