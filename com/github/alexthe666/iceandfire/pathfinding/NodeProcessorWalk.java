package com.github.alexthe666.iceandfire.pathfinding;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.NotNull;

public class NodeProcessorWalk extends WalkNodeEvaluator {
   public void m_6028_(@NotNull PathNavigationRegion p_225578_1_, @NotNull Mob p_225578_2_) {
      super.m_6028_(p_225578_1_, p_225578_2_);
   }

   public void setEntitySize(float width, float height) {
      this.f_77315_ = Mth.m_14143_(width + 1.0F);
      this.f_77316_ = Mth.m_14143_(height + 1.0F);
      this.f_77317_ = Mth.m_14143_(width + 1.0F);
   }
}
