package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;

public class MyrmexAIDefendHive extends TargetGoal {
   EntityMyrmexBase myrmex;
   LivingEntity villageAgressorTarget;

   public MyrmexAIDefendHive(EntityMyrmexBase myrmex) {
      super(myrmex, false, true);
      this.myrmex = myrmex;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      MyrmexHive village = this.myrmex.getHive();
      if (this.myrmex.canMove() && village != null) {
         this.villageAgressorTarget = village.findNearestVillageAggressor(this.myrmex);
         if (this.m_26150_(this.villageAgressorTarget, TargetingConditions.f_26872_)) {
            return true;
         } else if (this.f_26135_.m_217043_().m_188503_(20) == 0) {
            this.villageAgressorTarget = village.getNearestTargetPlayer(this.myrmex, this.myrmex.m_9236_());
            return this.m_26150_(this.villageAgressorTarget, TargetingConditions.f_26872_);
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void m_8056_() {
      this.myrmex.m_6710_(this.villageAgressorTarget);
      super.m_8056_();
   }
}
