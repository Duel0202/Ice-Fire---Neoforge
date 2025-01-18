package com.github.alexthe666.iceandfire.world.gen;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class WorldGenRoostBoulder {
   private final Block block;
   private final int startRadius;
   private final boolean replaceAir;

   public WorldGenRoostBoulder(Block blockIn, int startRadiusIn, boolean replaceAir) {
      this.block = blockIn;
      this.startRadius = startRadiusIn;
      this.replaceAir = replaceAir;
   }

   public boolean generate(LevelAccessor worldIn, RandomSource rand, BlockPos position) {
      for(; position.m_123342_() > 3; position = position.m_7495_()) {
         if (!worldIn.m_46859_(position.m_7495_())) {
            Block block = worldIn.m_8055_(position.m_7495_()).m_60734_();
            if (block == Blocks.f_50034_ || block == Blocks.f_50493_ || block == Blocks.f_50069_) {
               break;
            }
         }
      }

      if (position.m_123342_() <= 3) {
         return false;
      } else {
         int i1 = this.startRadius;
         int i = 0;

         label47:
         while(i1 >= 0 && i < 3) {
            int j = i1 + rand.m_188503_(2);
            int k = i1 + rand.m_188503_(2);
            int l = i1 + rand.m_188503_(2);
            float f = (float)(j + k + l) * 0.333F + 0.5F;
            Iterator var10 = ((Set)BlockPos.m_121990_(position.m_7918_(-j, -k, -l), position.m_7918_(j, k, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            while(true) {
               BlockPos blockpos;
               do {
                  do {
                     if (!var10.hasNext()) {
                        position = position.m_7918_(-(i1 + 1) + rand.m_188503_(2 + i1 * 2), -rand.m_188503_(2), -(i1 + 1) + rand.m_188503_(2 + i1 * 2));
                        ++i;
                        continue label47;
                     }

                     blockpos = (BlockPos)var10.next();
                  } while(!(blockpos.m_123331_(position) <= (double)(f * f)));
               } while(!this.replaceAir && !worldIn.m_8055_(blockpos).m_60815_());

               worldIn.m_7731_(blockpos, this.block.m_49966_(), 2);
            }
         }

         return true;
      }
   }
}
