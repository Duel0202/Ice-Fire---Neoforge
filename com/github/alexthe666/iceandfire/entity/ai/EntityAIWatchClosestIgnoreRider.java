package com.github.alexthe666.iceandfire.entity.ai;

import java.util.Iterator;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;

public class EntityAIWatchClosestIgnoreRider extends LookAtPlayerGoal {
   LivingEntity entity;

   public EntityAIWatchClosestIgnoreRider(Mob entity, Class<? extends LivingEntity> type, float dist) {
      super(entity, type, dist);
   }

   public boolean m_8036_() {
      return super.m_8036_() && this.f_25513_ != null && isRidingOrBeingRiddenBy(this.f_25513_, this.entity);
   }

   public static boolean isRidingOrBeingRiddenBy(Entity first, Entity entityIn) {
      Iterator var2 = first.m_20197_().iterator();

      Entity entity;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         entity = (Entity)var2.next();
         if (entity.equals(entityIn)) {
            return true;
         }
      } while(!isRidingOrBeingRiddenBy(entity, entityIn));

      return true;
   }
}
