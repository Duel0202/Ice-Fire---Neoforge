package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class MyrmexAIWander extends WaterAvoidingRandomStrollGoal {
   protected EntityMyrmexBase myrmex;

   public MyrmexAIWander(EntityMyrmexBase myrmex, double speed) {
      super(myrmex, speed);
      this.myrmex = myrmex;
   }

   public boolean m_8036_() {
      return this.myrmex.canMove() && this.myrmex.shouldWander() && super.m_8036_();
   }
}
