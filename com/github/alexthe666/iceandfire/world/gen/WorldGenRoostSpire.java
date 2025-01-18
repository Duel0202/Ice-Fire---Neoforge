package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;

public class WorldGenRoostSpire {
   public boolean generate(LevelAccessor worldIn, RandomSource rand, BlockPos position) {
      int height = 5 + rand.m_188503_(5);
      Direction bumpDirection = Direction.NORTH;

      for(int i = 0; i < height; ++i) {
         worldIn.m_7731_(position.m_6630_(i), ((Block)IafBlockRegistry.CRACKLED_STONE.get()).m_49966_(), 2);
         if (rand.m_188499_()) {
            bumpDirection = bumpDirection.m_122427_();
         }

         int offset = 1;
         if (i < 4) {
            worldIn.m_7731_(position.m_6630_(i).m_122012_(), ((Block)IafBlockRegistry.CRACKLED_GRAVEL.get()).m_49966_(), 2);
            worldIn.m_7731_(position.m_6630_(i).m_122019_(), ((Block)IafBlockRegistry.CRACKLED_GRAVEL.get()).m_49966_(), 2);
            worldIn.m_7731_(position.m_6630_(i).m_122029_(), ((Block)IafBlockRegistry.CRACKLED_GRAVEL.get()).m_49966_(), 2);
            worldIn.m_7731_(position.m_6630_(i).m_122024_(), ((Block)IafBlockRegistry.CRACKLED_GRAVEL.get()).m_49966_(), 2);
            offset = 2;
         }

         if (i < height - 2) {
            worldIn.m_7731_(position.m_6630_(i).m_5484_(bumpDirection, offset), ((Block)IafBlockRegistry.CRACKLED_COBBLESTONE.get()).m_49966_(), 2);
         }
      }

      return true;
   }
}
