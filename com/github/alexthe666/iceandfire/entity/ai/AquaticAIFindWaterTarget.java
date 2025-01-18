package com.github.alexthe666.iceandfire.entity.ai;

import java.util.Comparator;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class AquaticAIFindWaterTarget extends Goal {
   protected AquaticAIFindWaterTarget.Sorter fleePosSorter;
   private final Mob mob;

   public AquaticAIFindWaterTarget(Mob mob, int range, boolean avoidAttacker) {
      this.mob = mob;
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.fleePosSorter = new AquaticAIFindWaterTarget.Sorter(mob);
   }

   public boolean m_8036_() {
      if (this.mob.m_20069_() && !this.mob.m_20159_() && !this.mob.m_20160_()) {
         Path path = this.mob.m_21573_().m_26570_();
         if (this.mob.m_217043_().m_188501_() < 0.15F || path != null && path.m_77395_() != null && this.mob.m_20275_((double)path.m_77395_().f_77271_, (double)path.m_77395_().f_77272_, (double)path.m_77395_().f_77273_) < 3.0D) {
            if (path != null && path.m_77395_() != null || !this.mob.m_21573_().m_26571_() && !this.isDirectPathBetweenPoints(this.mob, this.mob.m_20182_(), new Vec3((double)path.m_77395_().f_77271_, (double)path.m_77395_().f_77272_, (double)path.m_77395_().f_77273_))) {
               this.mob.m_21573_().m_26573_();
            }

            if (this.mob.m_21573_().m_26571_()) {
               BlockPos vec3 = this.findWaterTarget();
               if (vec3 != null) {
                  this.mob.m_21573_().m_26519_((double)vec3.m_123341_(), (double)vec3.m_123342_(), (double)vec3.m_123343_(), 1.0D);
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public BlockPos findWaterTarget() {
      BlockPos blockpos = BlockPos.m_274561_((double)this.mob.m_146903_(), this.mob.m_20191_().f_82289_, (double)this.mob.m_146907_());
      if (this.mob.m_5448_() != null && this.mob.m_5448_().m_6084_()) {
         return this.mob.m_5448_().m_20183_();
      } else {
         for(int i = 0; i < 10; ++i) {
            BlockPos blockpos1 = blockpos.m_7918_(this.mob.m_217043_().m_188503_(20) - 10, this.mob.m_217043_().m_188503_(6) - 3, this.mob.m_217043_().m_188503_(20) - 10);
            if (this.mob.m_9236_().m_8055_(blockpos1).m_60713_(Blocks.f_49990_)) {
               return blockpos1;
            }
         }

         return null;
      }
   }

   public boolean isDirectPathBetweenPoints(Entity entity, Vec3 vec1, Vec3 vec2) {
      return this.mob.m_9236_().m_45547_(new ClipContext(vec1, vec2, Block.COLLIDER, Fluid.NONE, entity)).m_6662_() == Type.MISS;
   }

   public class Sorter implements Comparator<BlockPos> {
      private BlockPos pos;

      public Sorter(Entity theEntityIn) {
         this.pos = theEntityIn.m_20183_();
      }

      public int compare(BlockPos p_compare_1_, BlockPos p_compare_2_) {
         this.pos = AquaticAIFindWaterTarget.this.mob.m_20183_();
         double d0 = this.pos.m_123331_(p_compare_1_);
         double d1 = this.pos.m_123331_(p_compare_2_);
         return Double.compare(d1, d0);
      }
   }
}
