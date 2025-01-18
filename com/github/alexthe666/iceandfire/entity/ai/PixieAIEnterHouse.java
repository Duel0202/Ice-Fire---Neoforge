package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityPixie;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class PixieAIEnterHouse extends Goal {
   EntityPixie pixie;
   RandomSource random;

   public PixieAIEnterHouse(EntityPixie entityPixieIn) {
      this.pixie = entityPixieIn;
      this.random = entityPixieIn.m_217043_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.pixie.isOwnerClose() && !this.pixie.m_21566_().m_24995_() && !this.pixie.isPixieSitting() && this.random.m_188503_(20) == 0 && this.pixie.ticksUntilHouseAI == 0) {
         BlockPos blockpos1 = EntityPixie.findAHouse(this.pixie, this.pixie.m_9236_());
         return !blockpos1.toString().equals(this.pixie.m_20183_().toString());
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8037_() {
      for(int i = 0; i < 3; ++i) {
         BlockPos blockpos1 = EntityPixie.findAHouse(this.pixie, this.pixie.m_9236_());
         this.pixie.m_21566_().m_6849_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.5D, (double)blockpos1.m_123343_() + 0.5D, 0.25D);
         this.pixie.setHousePosition(blockpos1);
         if (this.pixie.m_5448_() == null) {
            this.pixie.m_21563_().m_24950_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.5D, (double)blockpos1.m_123343_() + 0.5D, 180.0F, 20.0F);
         }
      }

   }
}
