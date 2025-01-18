package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;

public class SirenAIVanillaSwimming extends Goal {
   private final EntitySiren entity;

   public SirenAIVanillaSwimming(EntitySiren entityIn) {
      this.entity = entityIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
      if (entityIn.m_21573_() instanceof GroundPathNavigation) {
         entityIn.m_21573_().m_7008_(true);
      }

   }

   public boolean m_8036_() {
      return (this.entity.m_20069_() || this.entity.m_20077_()) && this.entity.wantsToSing();
   }

   public void m_8037_() {
      if (this.entity.m_217043_().m_188501_() < 0.8F) {
         this.entity.m_21569_().m_24901_();
      }

   }
}
