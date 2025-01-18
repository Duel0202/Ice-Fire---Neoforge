package com.github.alexthe666.iceandfire.world.gen;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class WorldGenRoostPile {
   private final Block block;

   public WorldGenRoostPile(Block block) {
      this.block = block;
   }

   public boolean generate(LevelAccessor worldIn, RandomSource rand, BlockPos position) {
      int radius = rand.m_188503_(4);
      int layers = radius;

      for(int i = 0; i < layers; ++i) {
         int j = radius - i;
         int l = radius - i;
         float f = (float)(j + l) * 0.333F + 0.5F;
         BlockPos up = position.m_6630_(i);
         Iterator var11 = ((Set)BlockPos.m_121990_(up.m_7918_(-j, 0, -l), up.m_7918_(j, 0, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

         while(var11.hasNext()) {
            BlockPos blockpos = (BlockPos)var11.next();
            if (blockpos.m_123331_(position) <= (double)(f * f)) {
               worldIn.m_7731_(blockpos, this.block.m_49966_(), 2);
            }
         }
      }

      return true;
   }
}
