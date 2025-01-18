package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class DragonAIReturnToRoost extends Goal {
   private final EntityDragonBase dragon;

   public DragonAIReturnToRoost(EntityDragonBase entityIn, double movementSpeedIn) {
      this.dragon = entityIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      return this.dragon.canMove() && this.dragon.lookingForRoostAIFlag && (this.dragon.m_5448_() == null || !this.dragon.m_5448_().m_6084_()) && this.dragon.m_21534_() != null && DragonUtils.isInHomeDimension(this.dragon) && this.dragon.getDistanceSquared(Vec3.m_82512_(this.dragon.m_21534_())) > this.dragon.m_20205_() * this.dragon.m_20205_();
   }

   public void m_8037_() {
      if (this.dragon.m_21534_() != null) {
         double dist = Math.sqrt((double)this.dragon.getDistanceSquared(Vec3.m_82512_(this.dragon.m_21534_())));
         double xDist = Math.abs(this.dragon.m_20185_() - (double)this.dragon.m_21534_().m_123341_() - 0.5D);
         double zDist = Math.abs(this.dragon.m_20189_() - (double)this.dragon.m_21534_().m_123343_() - 0.5D);
         double xzDist = Math.sqrt(xDist * xDist + zDist * zDist);
         if (dist < (double)this.dragon.m_20205_()) {
            this.dragon.setFlying(false);
            this.dragon.setHovering(false);
            this.dragon.m_21573_().m_26519_((double)this.dragon.m_21534_().m_123341_(), (double)this.dragon.m_21534_().m_123342_(), (double)this.dragon.m_21534_().m_123343_(), 1.0D);
         } else {
            double yAddition = (double)(15 + this.dragon.m_217043_().m_188503_(3));
            if (xzDist < 40.0D) {
               yAddition = 0.0D;
               if (this.dragon.m_20096_()) {
                  this.dragon.setFlying(false);
                  this.dragon.setHovering(false);
                  this.dragon.flightManager.setFlightTarget(Vec3.m_82514_(this.dragon.m_21534_(), yAddition));
                  this.dragon.m_21573_().m_26519_((double)this.dragon.m_21534_().m_123341_(), (double)this.dragon.m_21534_().m_123342_(), (double)this.dragon.m_21534_().m_123343_(), 1.0D);
                  return;
               }
            }

            if (!this.dragon.isFlying() && !this.dragon.isHovering() && xzDist > 40.0D) {
               this.dragon.setHovering(true);
            }

            if (this.dragon.isFlying()) {
               this.dragon.flightManager.setFlightTarget(Vec3.m_82514_(this.dragon.m_21534_(), yAddition));
               this.dragon.m_21573_().m_26519_((double)this.dragon.m_21534_().m_123341_(), yAddition + (double)this.dragon.m_21534_().m_123342_(), (double)this.dragon.m_21534_().m_123343_(), 1.0D);
            }

            this.dragon.flyTicks = 0;
         }
      }

   }

   public boolean m_8045_() {
      return this.m_8036_();
   }
}
