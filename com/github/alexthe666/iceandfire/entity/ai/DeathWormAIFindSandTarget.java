package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class DeathWormAIFindSandTarget extends Goal {
   private final EntityDeathWorm mob;
   private int range;

   public DeathWormAIFindSandTarget(EntityDeathWorm mob, int range) {
      this.mob = mob;
      this.range = range;
   }

   public boolean m_8036_() {
      if (this.mob.m_5448_() != null) {
         return false;
      } else if (this.mob.isInSand() && !this.mob.m_20159_() && !this.mob.m_20160_()) {
         if (this.mob.m_217043_().m_188501_() < 0.5F) {
            Path path = this.mob.m_21573_().m_26570_();
            if (path != null) {
               this.mob.m_21573_().m_26573_();
            }

            if (this.mob.m_21573_().m_26571_()) {
               BlockPos vec3 = this.findSandTarget();
               if (vec3 != null) {
                  this.mob.m_21566_().m_6849_((double)vec3.m_123341_(), (double)vec3.m_123342_(), (double)vec3.m_123343_(), 1.0D);
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

   public BlockPos findSandTarget() {
      if (this.mob.m_5448_() != null && this.mob.m_5448_().m_6084_()) {
         BlockPos blockpos1 = this.mob.m_5448_().m_20183_();
         return new BlockPos(blockpos1.m_123341_(), blockpos1.m_123342_() - 1, blockpos1.m_123343_());
      } else {
         List<BlockPos> sand = new ArrayList();
         int x;
         int y;
         int z;
         if (this.mob.m_21824_() && this.mob.getWormHome() != null) {
            this.range = 25;

            for(x = this.mob.getWormHome().m_123341_() - this.range; x < this.mob.getWormHome().m_123341_() + this.range; ++x) {
               for(y = this.mob.getWormHome().m_123342_() - this.range; y < this.mob.getWormHome().m_123342_() + this.range; ++y) {
                  for(z = this.mob.getWormHome().m_123343_() - this.range; z < this.mob.getWormHome().m_123343_() + this.range; ++z) {
                     if (this.mob.m_9236_().m_8055_(new BlockPos(x, y, z)).m_204336_(BlockTags.f_13029_) && this.isDirectPathBetweenPoints(this.mob, this.mob.m_20182_(), new Vec3((double)x, (double)y, (double)z))) {
                        sand.add(new BlockPos(x, y, z));
                     }
                  }
               }
            }
         } else {
            for(x = (int)this.mob.m_20185_() - this.range; x < (int)this.mob.m_20185_() + this.range; ++x) {
               for(y = (int)this.mob.m_20186_() - this.range; y < (int)this.mob.m_20186_() + this.range; ++y) {
                  for(z = (int)this.mob.m_20189_() - this.range; z < (int)this.mob.m_20189_() + this.range; ++z) {
                     if (this.mob.m_9236_().m_8055_(new BlockPos(x, y, z)).m_204336_(BlockTags.f_13029_) && this.isDirectPathBetweenPoints(this.mob, this.mob.m_20182_(), new Vec3((double)x, (double)y, (double)z))) {
                        sand.add(new BlockPos(x, y, z));
                     }
                  }
               }
            }
         }

         return !sand.isEmpty() ? (BlockPos)sand.get(this.mob.m_217043_().m_188503_(sand.size())) : null;
      }
   }

   public boolean isDirectPathBetweenPoints(Entity entity, Vec3 vec1, Vec3 vec2) {
      return true;
   }
}
