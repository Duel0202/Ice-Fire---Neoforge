package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class GhostAICharge extends Goal {
   private final EntityGhost ghost;
   public boolean firstPhase = true;
   public Vec3 moveToPos = null;
   public Vec3 offsetOf;

   public GhostAICharge(EntityGhost ghost) {
      this.offsetOf = Vec3.f_82478_;
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.ghost = ghost;
   }

   public boolean m_8036_() {
      return this.ghost.m_5448_() != null && !this.ghost.isCharging();
   }

   public boolean m_8045_() {
      return this.ghost.m_5448_() != null && this.ghost.m_5448_().m_6084_();
   }

   public void m_8056_() {
      this.ghost.setCharging(true);
   }

   public void m_8041_() {
      this.firstPhase = true;
      this.moveToPos = null;
      this.ghost.setCharging(false);
   }

   public void m_8037_() {
      LivingEntity target = this.ghost.m_5448_();
      if (target != null) {
         if (this.ghost.getAnimation() == IAnimatedEntity.NO_ANIMATION && (double)this.ghost.m_20270_(target) < 1.4D) {
            this.ghost.setAnimation(EntityGhost.ANIMATION_HIT);
         }

         if (this.firstPhase) {
            if (this.moveToPos == null) {
               BlockPos moveToPos = DragonUtils.getBlockInTargetsViewGhost(this.ghost, target);
               this.moveToPos = Vec3.m_82512_(moveToPos);
            } else {
               this.ghost.m_21573_().m_26519_(this.moveToPos.f_82479_ + 0.5D, this.moveToPos.f_82480_ + 0.5D, this.moveToPos.f_82481_ + 0.5D, 1.0D);
               if (this.ghost.m_20238_(this.moveToPos.m_82520_(0.5D, 0.5D, 0.5D)) < 9.0D) {
                  if (this.ghost.getAnimation() == IAnimatedEntity.NO_ANIMATION) {
                     this.ghost.setAnimation(EntityGhost.ANIMATION_SCARE);
                  }

                  this.firstPhase = false;
                  this.moveToPos = null;
                  this.offsetOf = target.m_20182_().m_82546_(this.ghost.m_20182_()).m_82541_();
               }
            }
         } else {
            Vec3 fin = target.m_20182_();
            this.moveToPos = new Vec3(fin.f_82479_, target.m_20186_() + (double)(target.m_20192_() / 2.0F), fin.f_82481_);
            this.ghost.m_21573_().m_5624_(target, 1.2000000476837158D);
            if (this.ghost.m_20238_(this.moveToPos.m_82520_(0.5D, 0.5D, 0.5D)) < 3.0D) {
               this.m_8041_();
            }
         }
      }

   }
}
