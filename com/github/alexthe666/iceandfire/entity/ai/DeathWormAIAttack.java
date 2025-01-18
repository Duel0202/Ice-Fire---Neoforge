package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class DeathWormAIAttack extends Goal {
   private final EntityDeathWorm worm;
   private int jumpCooldown = 0;

   public DeathWormAIAttack(EntityDeathWorm worm) {
      this.worm = worm;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if (this.jumpCooldown > 0) {
         --this.jumpCooldown;
      }

      return this.worm.m_5448_() != null && !this.worm.m_20160_() && (this.worm.m_20096_() || this.worm.isInSandStrict()) && this.jumpCooldown <= 0;
   }

   public boolean m_8045_() {
      return this.worm.m_5448_() != null && this.worm.m_5448_().m_6084_();
   }

   public boolean m_6767_() {
      return false;
   }

   public void m_8056_() {
      LivingEntity target = this.worm.m_5448_();
      if (target != null) {
         if (this.worm.isInSand()) {
            BlockPos topSand;
            for(topSand = this.worm.m_20183_(); this.worm.m_9236_().m_8055_(topSand.m_7494_()).m_204336_(BlockTags.f_13029_); topSand = topSand.m_7494_()) {
            }

            this.worm.m_6034_(this.worm.m_20185_(), (double)((float)topSand.m_123342_() + 0.5F), this.worm.m_20189_());
         }

         if (this.shouldJump()) {
            this.jumpAttack();
         } else {
            this.worm.m_21573_().m_5624_(target, 1.0D);
         }
      }

   }

   public boolean shouldJump() {
      LivingEntity target = this.worm.m_5448_();
      if (target != null) {
         double distanceXZ = this.worm.m_20275_(target.m_20185_(), this.worm.m_20186_(), target.m_20189_());
         float distanceXZSqrt = (float)Math.sqrt(distanceXZ);
         double d0 = this.worm.m_20184_().f_82480_;
         if (distanceXZSqrt < 12.0F && distanceXZSqrt > 2.0F) {
            return this.jumpCooldown <= 0 && (d0 * d0 >= 0.029999999329447746D || this.worm.m_146909_() == 0.0F || Math.abs(this.worm.m_146909_()) >= 10.0F || !this.worm.m_20069_()) && !this.worm.m_20096_();
         }
      }

      return false;
   }

   public void jumpAttack() {
      LivingEntity target = this.worm.m_5448_();
      if (target != null) {
         this.worm.m_21391_(target, 260.0F, 30.0F);
         double smoothX = Mth.m_14008_(Math.abs(target.m_20185_() - this.worm.m_20185_()), 0.0D, 1.0D);
         double smoothZ = Mth.m_14008_(Math.abs(target.m_20189_() - this.worm.m_20189_()), 0.0D, 1.0D);
         double d0 = (target.m_20185_() - this.worm.m_20185_()) * 0.2D * smoothX;
         double d2 = (target.m_20189_() - this.worm.m_20189_()) * 0.2D * smoothZ;
         float up = (this.worm.m_6134_() > 3.0F ? 0.8F : 0.5F) + this.worm.m_217043_().m_188501_() * 0.5F;
         this.worm.m_20256_(this.worm.m_20184_().m_82520_(d0 * 0.3D, (double)up, d2 * 0.3D));
         this.worm.m_21573_().m_26573_();
         this.worm.setWormJumping(20);
         this.jumpCooldown = this.worm.m_217043_().m_188503_(32) + 64;
      }
   }

   public void m_8041_() {
      this.worm.m_146926_(0.0F);
   }

   public void m_8037_() {
      if (this.jumpCooldown > 0) {
         --this.jumpCooldown;
      }

      LivingEntity target = this.worm.m_5448_();
      if (target != null && this.worm.m_142582_(target) && this.worm.m_20270_(target) < 3.0F) {
         this.worm.m_7327_(target);
      }

      Vec3 vector3d = this.worm.m_20184_();
      if (vector3d.f_82480_ * vector3d.f_82480_ < 0.10000000149011612D && this.worm.m_146909_() != 0.0F) {
         this.worm.m_146926_(Mth.m_14189_(this.worm.m_146909_(), 0.0F, 0.2F));
      } else {
         double d0 = vector3d.m_165924_();
         double d1 = Math.signum(-vector3d.f_82480_) * Math.acos(d0 / vector3d.m_82553_()) * 57.2957763671875D;
         this.worm.m_146926_((float)d1);
      }

      if (this.shouldJump()) {
         this.jumpAttack();
      } else if (this.worm.m_21573_().m_26571_()) {
         this.worm.m_21573_().m_5624_(target, 1.0D);
      }

   }
}
