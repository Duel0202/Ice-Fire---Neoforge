package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class SirenAIFindWaterTarget extends Goal {
   private final EntitySiren mob;

   public SirenAIFindWaterTarget(EntitySiren mob) {
      this.mob = mob;
   }

   public boolean m_8036_() {
      if (!this.mob.m_20069_()) {
         return false;
      } else {
         if (this.mob.m_217043_().m_188501_() < 0.5F) {
            Path path = this.mob.m_21573_().m_26570_();
            if (path != null && path.m_77395_() != null) {
               this.mob.m_21573_().m_26573_();
            }

            if (this.mob.m_21573_().m_26571_()) {
               Vec3 vec3 = this.findWaterTarget();
               if (vec3 != null) {
                  this.mob.m_21573_().m_26519_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 1.0D);
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean m_8045_() {
      return false;
   }

   public Vec3 findWaterTarget() {
      if (this.mob.m_5448_() != null && this.mob.m_5448_().m_6084_()) {
         BlockPos blockpos1 = this.mob.m_5448_().m_20183_();
         return new Vec3((double)blockpos1.m_123341_(), (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_());
      } else {
         List<Vec3> water = new ArrayList();
         List<Vec3> singTargets = new ArrayList();
         int posX = (int)this.mob.m_20185_();
         int posY = (int)this.mob.m_20186_();
         int posZ = (int)this.mob.m_20189_();

         for(int x = posX - 5; x < posX + 5; ++x) {
            for(int y = posY - 5; y < posY + 5; ++y) {
               for(int z = posZ - 5; z < posZ + 5; ++z) {
                  if (this.mob.wantsToSing() && this.mob.m_9236_().m_8055_(new BlockPos(x, y, z)).m_280296_() && this.mob.m_9236_().m_46859_(new BlockPos(x, y + 1, z)) && this.mob.isDirectPathBetweenPoints(this.mob.m_20182_(), new Vec3((double)x, (double)(y + 1), (double)z))) {
                     singTargets.add(new Vec3((double)x, (double)(y + 1), (double)z));
                  }

                  if (this.mob.m_9236_().m_8055_(new BlockPos(x, y, z)).m_60713_(Blocks.f_49990_) && this.mob.isDirectPathBetweenPoints(this.mob.m_20182_(), new Vec3((double)x, (double)y, (double)z))) {
                     water.add(new Vec3((double)x, (double)y, (double)z));
                  }
               }
            }
         }

         if (!singTargets.isEmpty()) {
            return (Vec3)singTargets.get(this.mob.m_217043_().m_188503_(singTargets.size()));
         } else if (!water.isEmpty()) {
            return (Vec3)water.get(this.mob.m_217043_().m_188503_(water.size()));
         } else {
            return null;
         }
      }
   }
}
