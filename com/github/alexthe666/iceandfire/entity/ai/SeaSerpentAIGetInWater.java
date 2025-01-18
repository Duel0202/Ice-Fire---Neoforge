package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import java.util.EnumSet;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class SeaSerpentAIGetInWater extends Goal {
   private final EntitySeaSerpent creature;
   private BlockPos targetPos;

   public SeaSerpentAIGetInWater(EntitySeaSerpent creature) {
      this.creature = creature;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      if ((this.creature.jumpCooldown == 0 || this.creature.m_20096_()) && !this.creature.m_9236_().m_6425_(this.creature.m_20183_()).m_205070_(FluidTags.f_13131_)) {
         this.targetPos = this.generateTarget();
         return this.targetPos != null;
      } else {
         return false;
      }
   }

   public void m_8056_() {
      if (this.targetPos != null) {
         this.creature.m_21573_().m_26519_((double)this.targetPos.m_123341_(), (double)this.targetPos.m_123342_(), (double)this.targetPos.m_123343_(), 1.5D);
      }

   }

   public boolean m_8045_() {
      return !this.creature.m_21573_().m_26571_() && this.targetPos != null && !this.creature.m_9236_().m_6425_(this.creature.m_20183_()).m_205070_(FluidTags.f_13131_);
   }

   public BlockPos generateTarget() {
      BlockPos blockpos = null;
      int range = true;

      for(int i = 0; i < 15; ++i) {
         BlockPos blockpos1;
         for(blockpos1 = this.creature.m_20183_().m_7918_(ThreadLocalRandom.current().nextInt(16) - 8, 3, ThreadLocalRandom.current().nextInt(16) - 8); this.creature.m_9236_().m_46859_(blockpos1) && blockpos1.m_123342_() > 1; blockpos1 = blockpos1.m_7495_()) {
         }

         if (this.creature.m_9236_().m_6425_(blockpos1).m_205070_(FluidTags.f_13131_)) {
            blockpos = blockpos1;
         }
      }

      return blockpos;
   }
}
