package com.github.alexthe666.iceandfire.entity.util;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class DragonPositionGenerator {
   public static Vec3 findRandomTargetBlock(Mob MobEntityIn, int xz, int y, @Nullable Vec3 targetVec3) {
      Vec3 vec = generateRandomPos(MobEntityIn, xz, y, targetVec3, false);
      return vec == null ? MobEntityIn.m_20182_() : vec;
   }

   @Nullable
   public static Vec3 generateRandomPos(Mob mob, int xz, int y, @Nullable Vec3 vec, boolean skipWater) {
      PathNavigation pathnavigate = mob.m_21573_();
      RandomSource random = mob.m_217043_();
      boolean flag;
      if (mob.m_21536_()) {
         double d0 = mob.m_21534_().m_203198_((double)Mth.m_14107_(mob.m_20185_()), (double)Mth.m_14107_(mob.m_20186_()), (double)Mth.m_14107_(mob.m_20189_())) + 4.0D;
         double d1 = (double)(mob.m_21535_() + (float)xz);
         flag = d0 < d1 * d1;
      } else {
         flag = false;
      }

      boolean flag1 = false;
      float f = -99999.0F;
      int k1 = 0;
      int i = 0;
      int j = 0;

      for(int k = 0; k < 10; ++k) {
         int l = random.m_188503_(2 * xz + 1) - xz;
         int i1 = random.m_188503_(2 * y + 1) - y;
         int j1 = random.m_188503_(2 * xz + 1) - xz;
         if (vec == null || (double)l * vec.f_82479_ + (double)j1 * vec.f_82481_ >= 0.0D) {
            BlockPos blockpos1;
            if (mob.m_21536_() && xz > 1) {
               blockpos1 = mob.m_21534_();
               if (mob.m_20185_() > (double)blockpos1.m_123341_()) {
                  l -= random.m_188503_(xz / 2);
               } else {
                  l += random.m_188503_(xz / 2);
               }

               if (mob.m_20189_() > (double)blockpos1.m_123343_()) {
                  j1 -= random.m_188503_(xz / 2);
               } else {
                  j1 += random.m_188503_(xz / 2);
               }
            }

            blockpos1 = new BlockPos(l + mob.m_146903_(), i1 + mob.m_146904_(), j1 + mob.m_146907_());
            if ((!flag || mob.m_21444_(blockpos1)) && pathnavigate.m_6342_(blockpos1)) {
               if (skipWater) {
                  blockpos1 = moveAboveSolid(blockpos1, mob);
                  if (isWaterDestination(blockpos1, mob)) {
                     continue;
                  }
               }

               float f1 = 0.0F;
               if (f1 > f) {
                  f = f1;
                  k1 = l;
                  i = i1;
                  j = j1;
                  flag1 = true;
               }
            }
         }
      }

      if (flag1) {
         return new Vec3((double)k1 + mob.m_20185_(), (double)i + mob.m_20186_(), (double)j + mob.m_20189_());
      } else {
         return null;
      }
   }

   private static BlockPos moveAboveSolid(BlockPos pos, Mob mob) {
      if (!mob.m_9236_().m_8055_(pos).m_280296_()) {
         return pos;
      } else {
         BlockPos blockpos;
         for(blockpos = pos.m_7494_(); blockpos.m_123342_() < mob.m_9236_().m_151558_() && mob.m_9236_().m_8055_(blockpos).m_280296_(); blockpos = blockpos.m_7494_()) {
         }

         return blockpos;
      }
   }

   private static boolean isWaterDestination(BlockPos pos, Mob mob) {
      return mob.m_9236_().m_8055_(pos).m_60713_(Blocks.f_49990_);
   }
}
