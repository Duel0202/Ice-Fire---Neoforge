package com.github.alexthe666.iceandfire.pathfinding;

import com.github.alexthe666.citadel.server.entity.collision.CustomCollisionsNavigator;
import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

public class PathNavigateCyclops extends CustomCollisionsNavigator {
   public PathNavigateCyclops(EntityCyclops LivingEntityIn, Level worldIn) {
      super(LivingEntityIn, worldIn);
   }

   protected PathFinder m_5532_(int i) {
      this.f_26508_ = new WalkNodeEvaluator();
      this.f_26508_.m_77351_(true);
      this.f_26508_.m_77358_(true);
      return new PathFinder(this.f_26508_, i);
   }
}
