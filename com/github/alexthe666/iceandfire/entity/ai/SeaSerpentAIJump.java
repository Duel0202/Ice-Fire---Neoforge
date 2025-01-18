package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;

public class SeaSerpentAIJump extends JumpGoal {
   private static final int[] JUMP_DISTANCES = new int[]{0, 2, 4, 5, 6, 7};
   private final EntitySeaSerpent serpent;
   private final int chance;
   private boolean inWater;

   public SeaSerpentAIJump(EntitySeaSerpent dolphin, int chance) {
      this.serpent = dolphin;
      this.chance = chance;
   }

   public boolean m_8036_() {
      if (this.serpent.m_217043_().m_188503_(this.chance) == 0 && this.serpent.m_5448_() == null && this.serpent.jumpCooldown == 0) {
         Direction direction = this.serpent.m_6374_();
         int i = direction.m_122429_();
         int j = direction.m_122431_();
         BlockPos blockpos = this.serpent.m_20183_();
         int[] var5 = JUMP_DISTANCES;
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            int k = var5[var7];
            if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
      BlockPos blockpos = pos.m_7918_(dx * scale, 0, dz * scale);
      return this.serpent.m_9236_().m_6425_(blockpos).m_205070_(FluidTags.f_13131_) && !this.serpent.m_9236_().m_8055_(blockpos).m_280555_();
   }

   private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
      return this.serpent.m_9236_().m_8055_(pos.m_7918_(dx * scale, 1, dz * scale)).m_60795_() && this.serpent.m_9236_().m_8055_(pos.m_7918_(dx * scale, 2, dz * scale)).m_60795_();
   }

   public boolean m_8045_() {
      double d0 = this.serpent.m_20184_().f_82480_;
      return this.serpent.jumpCooldown > 0 && (d0 * d0 >= 0.029999999329447746D || this.serpent.m_146909_() == 0.0F || Math.abs(this.serpent.m_146909_()) >= 10.0F || !this.serpent.m_20069_()) && !this.serpent.m_20096_();
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      Direction direction = this.serpent.m_6374_();
      float up = 1.0F + this.serpent.m_217043_().m_188501_() * 0.8F;
      this.serpent.m_20256_(this.serpent.m_20184_().m_82520_((double)direction.m_122429_() * 0.6D, (double)up, (double)direction.m_122431_() * 0.6D));
      this.serpent.setJumpingOutOfWater(true);
      this.serpent.m_21573_().m_26573_();
      this.serpent.jumpCooldown = this.serpent.m_217043_().m_188503_(100) + 100;
   }

   public void m_8041_() {
      this.serpent.setJumpingOutOfWater(false);
      this.serpent.m_146926_(0.0F);
   }

   public void m_8037_() {
      boolean flag = this.inWater;
      if (!flag) {
         FluidState fluidstate = this.serpent.m_9236_().m_6425_(this.serpent.m_20183_());
         this.inWater = fluidstate.m_205070_(FluidTags.f_13131_);
      }

      if (this.inWater && !flag) {
         this.serpent.m_5496_(SoundEvents.f_11805_, 1.0F, 1.0F);
      }

      Vec3 vector3d = this.serpent.m_20184_();
      if (vector3d.f_82480_ * vector3d.f_82480_ < 0.10000000149011612D && this.serpent.m_146909_() != 0.0F) {
         this.serpent.m_146926_(Mth.m_14189_(this.serpent.m_146909_(), 0.0F, 0.2F));
      } else {
         double d0 = vector3d.m_165924_();
         double d1 = Math.signum(-vector3d.f_82480_) * Math.acos(d0 / vector3d.m_82553_()) * 57.2957763671875D;
         this.serpent.m_146926_((float)d1);
      }

   }
}
