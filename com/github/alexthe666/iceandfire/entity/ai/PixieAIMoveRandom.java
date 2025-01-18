package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityPixie;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class PixieAIMoveRandom extends Goal {
   BlockPos target;
   EntityPixie pixie;
   RandomSource random;

   public PixieAIMoveRandom(EntityPixie entityPixieIn) {
      this.pixie = entityPixieIn;
      this.random = entityPixieIn.m_217043_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      this.target = EntityPixie.getPositionRelativetoGround(this.pixie, this.pixie.m_9236_(), this.pixie.m_20185_() + (double)this.random.m_188503_(15) - 7.0D, this.pixie.m_20189_() + (double)this.random.m_188503_(15) - 7.0D, this.random);
      return !this.pixie.isOwnerClose() && !this.pixie.isPixieSitting() && this.isDirectPathBetweenPoints(this.pixie.m_20183_(), this.target) && !this.pixie.m_21566_().m_24995_() && this.random.m_188503_(4) == 0 && this.pixie.getHousePos() == null;
   }

   protected boolean isDirectPathBetweenPoints(BlockPos posVec31, BlockPos posVec32) {
      return this.pixie.m_9236_().m_45547_(new ClipContext(new Vec3((double)posVec31.m_123341_() + 0.5D, (double)posVec31.m_123342_() + 0.5D, (double)posVec31.m_123343_() + 0.5D), new Vec3((double)posVec32.m_123341_() + 0.5D, (double)posVec32.m_123342_() + (double)this.pixie.m_20206_() * 0.5D, (double)posVec32.m_123343_() + 0.5D), Block.COLLIDER, Fluid.NONE, this.pixie)).m_6662_() == Type.MISS;
   }

   public boolean m_8045_() {
      return false;
   }

   public void m_8037_() {
      if (!this.isDirectPathBetweenPoints(this.pixie.m_20183_(), this.target)) {
         this.target = EntityPixie.getPositionRelativetoGround(this.pixie, this.pixie.m_9236_(), this.pixie.m_20185_() + (double)this.random.m_188503_(15) - 7.0D, this.pixie.m_20189_() + (double)this.random.m_188503_(15) - 7.0D, this.random);
      }

      if (this.pixie.m_9236_().m_46859_(this.target)) {
         this.pixie.m_21566_().m_6849_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 0.25D);
         if (this.pixie.m_5448_() == null) {
            this.pixie.m_21563_().m_24950_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 180.0F, 20.0F);
         }
      }

   }
}
