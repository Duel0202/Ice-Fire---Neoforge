package com.github.alexthe666.iceandfire.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class EntityAIAttackMeleeNoCooldown extends MeleeAttackGoal {
   public EntityAIAttackMeleeNoCooldown(PathfinderMob creature, double speed, boolean memory) {
      super(creature, speed, memory);
   }

   public void m_8037_() {
      if (this.f_25540_.m_5448_() != null) {
         super.m_8037_();
      }

   }
}
