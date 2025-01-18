package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexSwarmer;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class MyrmexAISummonerHurtTarget extends TargetGoal {
   EntityMyrmexSwarmer tameable;
   LivingEntity attacker;
   private int timestamp;

   public MyrmexAISummonerHurtTarget(EntityMyrmexSwarmer theEntityMyrmexSwarmerIn) {
      super(theEntityMyrmexSwarmerIn, false);
      this.tameable = theEntityMyrmexSwarmerIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      LivingEntity living = this.tameable.getSummoner();
      if (living == null) {
         return false;
      } else {
         this.attacker = living.m_21214_();
         int i = living.m_21215_();
         return i != this.timestamp && this.m_26150_(this.attacker, TargetingConditions.f_26872_) && this.tameable.shouldAttackEntity(this.attacker, living);
      }
   }

   public void m_8056_() {
      this.f_26135_.m_6710_(this.attacker);
      LivingEntity LivingEntity = this.tameable.getSummoner();
      if (LivingEntity != null) {
         this.timestamp = LivingEntity.m_21215_();
      }

      super.m_8056_();
   }
}
