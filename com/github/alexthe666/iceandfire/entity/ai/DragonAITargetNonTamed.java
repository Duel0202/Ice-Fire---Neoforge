package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
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

public class DragonAITargetNonTamed<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
   private final EntityDragonBase dragon;

   public DragonAITargetNonTamed(EntityDragonBase entityIn, Class<T> classTarget, boolean checkSight, Predicate<LivingEntity> targetSelector) {
      super(entityIn, classTarget, 5, checkSight, false, targetSelector);
      this.m_7021_(EnumSet.of(Flag.TARGET));
      this.dragon = entityIn;
   }

   public boolean m_8036_() {
      if (this.dragon.m_21824_()) {
         return false;
      } else if (this.dragon.lookingForRoostAIFlag) {
         return false;
      } else {
         boolean canUse = super.m_8036_();
         boolean isSleeping = this.dragon.m_5803_();
         if (canUse) {
            if (isSleeping && this.f_26050_ instanceof Player) {
               return this.dragon.m_20280_(this.f_26050_) <= 16.0D;
            } else {
               return !isSleeping;
            }
         } else {
            return false;
         }
      }
   }

   @NotNull
   protected AABB m_7255_(double targetDistance) {
      return this.dragon.m_20191_().m_82377_(targetDistance, targetDistance, targetDistance);
   }

   protected double m_7623_() {
      AttributeInstance iattributeinstance = this.f_26135_.m_21051_(Attributes.f_22277_);
      return iattributeinstance == null ? 128.0D : iattributeinstance.m_22135_();
   }
}
