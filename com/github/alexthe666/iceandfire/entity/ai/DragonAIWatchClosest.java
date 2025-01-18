package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class DragonAIWatchClosest extends LookAtPlayerGoal {
   public DragonAIWatchClosest(PathfinderMob LivingEntityIn, Class<? extends LivingEntity> watchTargetClass, float maxDistance) {
      super(LivingEntityIn, watchTargetClass, maxDistance);
   }

   public boolean m_8036_() {
      return this.f_25512_ instanceof EntityDragonBase && ((EntityDragonBase)this.f_25512_).getAnimation() == EntityDragonBase.ANIMATION_SHAKEPREY ? false : super.m_8036_();
   }

   public boolean m_8045_() {
      return this.f_25512_ instanceof EntityDragonBase && !((EntityDragonBase)this.f_25512_).canMove() ? false : super.m_8045_();
   }
}
