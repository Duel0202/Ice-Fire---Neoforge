package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.phys.Vec3;

public class SeaSerpentAIRandomSwimming extends RandomStrollGoal {
   public SeaSerpentAIRandomSwimming(PathfinderMob creature, double speed, int chance) {
      super(creature, speed, chance, false);
   }

   public boolean m_8036_() {
      if (!this.f_25725_.m_20160_() && this.f_25725_.m_5448_() == null) {
         if (!this.f_25731_ && this.f_25725_.m_217043_().m_188503_(this.f_25730_) != 0) {
            return false;
         } else {
            Vec3 vector3d = this.m_7037_();
            if (vector3d == null) {
               return false;
            } else {
               this.f_25726_ = vector3d.f_82479_;
               this.f_25727_ = vector3d.f_82480_;
               this.f_25728_ = vector3d.f_82481_;
               this.f_25731_ = false;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   @Nullable
   protected Vec3 m_7037_() {
      if (((EntitySeaSerpent)this.f_25725_).jumpCooldown <= 0) {
         Vec3 vector3d = this.findSurfaceTarget(this.f_25725_, 32, 16);
         return vector3d != null ? vector3d.m_82520_(0.0D, 1.0D, 0.0D) : null;
      } else {
         BlockPos blockpos = null;
         Random random = ThreadLocalRandom.current();
         int range = true;

         for(int i = 0; i < 15; ++i) {
            BlockPos blockpos1;
            for(blockpos1 = this.f_25725_.m_20183_().m_7918_(random.nextInt(16) - 8, random.nextInt(16) - 8, random.nextInt(16) - 8); this.f_25725_.m_9236_().m_46859_(blockpos1) && this.f_25725_.m_9236_().m_6425_(blockpos1).m_76178_() && blockpos1.m_123342_() > 1; blockpos1 = blockpos1.m_7495_()) {
            }

            if (this.f_25725_.m_9236_().m_6425_(blockpos1).m_205070_(FluidTags.f_13131_)) {
               blockpos = blockpos1;
            }
         }

         return blockpos == null ? null : new Vec3((double)blockpos.m_123341_() + 0.5D, (double)blockpos.m_123342_() + 0.5D, (double)blockpos.m_123343_() + 0.5D);
      }
   }

   private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
      BlockPos blockpos = pos.m_7918_(dx * scale, 0, dz * scale);
      return this.f_25725_.m_9236_().m_6425_(blockpos).m_205070_(FluidTags.f_13131_) && !this.f_25725_.m_9236_().m_8055_(blockpos).m_280555_();
   }

   private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
      return this.f_25725_.m_9236_().m_8055_(pos.m_7918_(dx * scale, 1, dz * scale)).m_60795_() && this.f_25725_.m_9236_().m_8055_(pos.m_7918_(dx * scale, 2, dz * scale)).m_60795_();
   }

   private Vec3 findSurfaceTarget(PathfinderMob creature, int i, int i1) {
      BlockPos upPos;
      for(upPos = creature.m_20183_(); creature.m_9236_().m_6425_(upPos).m_205070_(FluidTags.f_13131_); upPos = upPos.m_7494_()) {
      }

      return this.isAirAbove(upPos.m_7495_(), 0, 0, 0) && this.canJumpTo(upPos.m_7495_(), 0, 0, 0) ? new Vec3((double)((float)upPos.m_123341_() + 0.5F), (double)((float)upPos.m_123342_() + 3.5F), (double)((float)upPos.m_123343_() + 0.5F)) : null;
   }
}
