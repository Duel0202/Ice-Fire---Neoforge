package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityCockatrice;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;

public class CockatriceAIFollowOwner extends FollowOwnerGoal {
   EntityCockatrice cockatrice;

   public CockatriceAIFollowOwner(EntityCockatrice cockatrice, double speed, float minDist, float maxDist) {
      super(cockatrice, speed, minDist, maxDist, true);
      this.cockatrice = cockatrice;
   }

   public boolean m_8036_() {
      return super.m_8036_() && this.cockatrice.getCommand() == 2;
   }
}
