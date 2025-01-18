package com.github.alexthe666.iceandfire.entity.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class HippocampusAIWander extends RandomStrollGoal {
   public HippocampusAIWander(PathfinderMob creatureIn, double speedIn) {
      super(creatureIn, speedIn);
   }

   public boolean m_8036_() {
      return (!(this.f_25725_ instanceof TamableAnimal) || !((TamableAnimal)this.f_25725_).m_21827_()) && !this.f_25725_.m_20069_() && super.m_8036_();
   }

   public boolean m_8045_() {
      return (!(this.f_25725_ instanceof TamableAnimal) || !((TamableAnimal)this.f_25725_).m_21827_()) && !this.f_25725_.m_20069_() && super.m_8045_();
   }
}
