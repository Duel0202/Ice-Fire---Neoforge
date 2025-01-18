package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class SirenAIWander extends RandomStrollGoal {
   private final EntitySiren siren;

   public SirenAIWander(EntitySiren creatureIn, double speedIn) {
      super(creatureIn, speedIn);
      this.siren = creatureIn;
   }

   public boolean m_8036_() {
      return !this.siren.m_20069_() && !this.siren.isSinging() && super.m_8036_();
   }

   public boolean m_8045_() {
      return !this.siren.m_20069_() && !this.siren.isSinging() && super.m_8045_();
   }
}
