package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.JumpGoal;
import net.minecraft.world.phys.Vec3;

public class DeathWormAIJump extends JumpGoal {
   private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
   private final EntityDeathWorm dolphin;
   private final int chance;
   private boolean inWater;
   private int jumpCooldown;

   public DeathWormAIJump(EntityDeathWorm dolphin, int p_i50329_2_) {
      this.dolphin = dolphin;
      this.chance = p_i50329_2_;
   }

   public boolean m_8036_() {
      if (this.jumpCooldown > 0) {
         --this.jumpCooldown;
      }

      if (this.dolphin.m_217043_().m_188503_(this.chance) == 0 && !this.dolphin.m_20160_() && this.dolphin.m_5448_() == null) {
         Direction direction = this.dolphin.m_6374_();
         int i = direction.m_122429_();
         int j = direction.m_122431_();
         BlockPos blockpos = this.dolphin.m_20183_();
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
      return this.dolphin.m_9236_().m_8055_(blockpos).m_204336_(BlockTags.f_13029_);
   }

   private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
      return this.dolphin.m_9236_().m_8055_(pos.m_7918_(dx * scale, 1, dz * scale)).m_60795_() && this.dolphin.m_9236_().m_8055_(pos.m_7918_(dx * scale, 2, dz * scale)).m_60795_();
   }

   public boolean m_8045_() {
      double d0 = this.dolphin.m_20184_().f_82480_;
      return this.jumpCooldown > 0 && (d0 * d0 >= 0.029999999329447746D || this.dolphin.m_146909_() == 0.0F || Math.abs(this.dolphin.m_146909_()) >= 10.0F || !this.dolphin.isInSand()) && !this.dolphin.m_20096_();
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      Direction direction = this.dolphin.m_6374_();
      float up = (this.dolphin.m_6134_() > 3.0F ? 0.7F : 0.4F) + this.dolphin.m_217043_().m_188501_() * 0.4F;
      this.dolphin.m_20256_(this.dolphin.m_20184_().m_82520_((double)direction.m_122429_() * 0.6D, (double)up, (double)direction.m_122431_() * 0.6D));
      this.dolphin.m_21573_().m_26573_();
      this.dolphin.setWormJumping(30);
      this.jumpCooldown = this.dolphin.m_217043_().m_188503_(65) + 32;
   }

   public void m_8041_() {
      this.dolphin.m_146926_(0.0F);
   }

   public void m_8037_() {
      boolean flag = this.inWater;
      if (!flag) {
         this.inWater = this.dolphin.m_9236_().m_8055_(this.dolphin.m_20183_()).m_204336_(BlockTags.f_13029_);
      }

      Vec3 vector3d = this.dolphin.m_20184_();
      if (vector3d.f_82480_ * vector3d.f_82480_ < 0.10000000149011612D && this.dolphin.m_146909_() != 0.0F) {
         this.dolphin.m_146926_(Mth.m_14189_(this.dolphin.m_146909_(), 0.0F, 0.2F));
      } else {
         double d0 = vector3d.m_165924_();
         double d1 = Math.signum(-vector3d.f_82480_) * Math.acos(d0 / vector3d.m_82553_()) * 57.2957763671875D;
         this.dolphin.m_146926_((float)d1);
      }

   }
}
