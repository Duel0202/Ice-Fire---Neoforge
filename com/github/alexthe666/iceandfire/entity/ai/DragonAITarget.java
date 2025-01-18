package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.api.FoodUtils;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class DragonAITarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final EntityDragonBase dragon;

   public DragonAITarget(EntityDragonBase entityIn, Class<T> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
      super(entityIn, classTarget, 3, checkSight, false, targetSelector);
      this.m_7021_(EnumSet.of(Flag.TARGET));
      this.dragon = entityIn;
   }

   public boolean m_8036_() {
      if (this.dragon.getCommand() != 1 && this.dragon.getCommand() != 2 && !this.dragon.m_5803_()) {
         if (!this.dragon.m_21824_() && this.dragon.lookingForRoostAIFlag) {
            return false;
         } else {
            if (this.f_26050_ != null && !this.f_26050_.getClass().equals(this.dragon.getClass())) {
               if (!super.m_8036_()) {
                  return false;
               }

               float dragonSize = Math.max(this.dragon.m_20205_(), this.dragon.m_20205_() * this.dragon.getRenderSize());
               if (dragonSize >= this.f_26050_.m_20205_()) {
                  if (this.f_26050_ instanceof Player && !this.dragon.m_21824_()) {
                     return true;
                  }

                  if (this.f_26050_ instanceof EntityDragonBase) {
                     EntityDragonBase dragon = (EntityDragonBase)this.f_26050_;
                     if (dragon.m_269323_() != null && this.dragon.m_269323_() != null && this.dragon.m_21830_(dragon.m_269323_())) {
                        return false;
                     }

                     return !dragon.isModelDead();
                  }

                  if (this.f_26050_ instanceof Player && this.dragon.m_21824_()) {
                     return false;
                  }

                  if (!this.dragon.m_21830_(this.f_26050_) && FoodUtils.getFoodPoints(this.f_26050_) > 0 && this.dragon.canMove() && (this.dragon.getHunger() < 90 || !this.dragon.m_21824_() && this.f_26050_ instanceof Player)) {
                     return this.dragon.m_21824_() ? DragonUtils.canTameDragonAttack(this.dragon, this.f_26050_) : true;
                  }
               }
            }

            return false;
         }
      } else {
         return false;
      }
   }

   @NotNull
   protected AABB m_7255_(double targetDistance) {
      return this.dragon.m_20191_().m_82377_(targetDistance, targetDistance, targetDistance);
   }

   protected double m_7623_() {
      AttributeInstance iattributeinstance = this.f_26135_.m_21051_(Attributes.f_22277_);
      return iattributeinstance == null ? 64.0D : iattributeinstance.m_22135_();
   }
}
