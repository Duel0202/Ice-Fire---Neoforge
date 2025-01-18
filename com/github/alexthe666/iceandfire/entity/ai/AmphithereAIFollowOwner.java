package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityAmphithere;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class AmphithereAIFollowOwner extends Goal {
   private final EntityAmphithere ampithere;
   private final double followSpeed;
   Level world;
   float maxDist;
   float minDist;
   private LivingEntity owner;
   private int timeToRecalcPath;
   private float oldWaterCost;

   public AmphithereAIFollowOwner(EntityAmphithere ampithereIn, double followSpeedIn, float minDistIn, float maxDistIn) {
      this.ampithere = ampithereIn;
      this.world = ampithereIn.m_9236_();
      this.followSpeed = followSpeedIn;
      this.minDist = minDistIn;
      this.maxDist = maxDistIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      LivingEntity LivingEntity = this.ampithere.m_269323_();
      if (this.ampithere.getCommand() != 2) {
         return false;
      } else if (LivingEntity == null) {
         return false;
      } else if (LivingEntity instanceof Player && LivingEntity.m_5833_()) {
         return false;
      } else if (this.ampithere.m_21827_()) {
         return false;
      } else if (this.ampithere.m_20280_(LivingEntity) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.owner = LivingEntity;
         return true;
      }
   }

   public boolean m_8045_() {
      return !this.noPath() && this.ampithere.m_20280_(this.owner) > (double)(this.maxDist * this.maxDist) && !this.ampithere.m_21827_();
   }

   private boolean noPath() {
      return !this.ampithere.isFlying() ? this.ampithere.m_21573_().m_26571_() : false;
   }

   public void m_8056_() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.ampithere.m_21439_(BlockPathTypes.WATER);
      this.ampithere.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.owner = null;
      this.ampithere.m_21573_().m_26573_();
      this.ampithere.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
   }

   public void m_8037_() {
      this.ampithere.m_21563_().m_24960_(this.owner, 10.0F, (float)this.ampithere.m_8132_());
      if (!this.ampithere.m_21827_() && --this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         this.tryMoveTo();
         if (!this.ampithere.m_21523_() && !this.ampithere.m_20159_() && this.ampithere.m_20280_(this.owner) >= 144.0D) {
            int i = Mth.m_14107_(this.owner.m_20185_()) - 2;
            int j = Mth.m_14107_(this.owner.m_20189_()) - 2;
            int k = Mth.m_14107_(this.owner.m_20191_().f_82289_);

            for(int l = 0; l <= 4; ++l) {
               for(int i1 = 0; i1 <= 4; ++i1) {
                  if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.canTeleportToBlock(new BlockPos(i, j, k))) {
                     this.ampithere.m_7678_((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.ampithere.m_146908_(), this.ampithere.m_146909_());
                     this.ampithere.m_21573_().m_26573_();
                     return;
                  }
               }
            }
         }
      }

   }

   protected boolean canTeleportToBlock(BlockPos pos) {
      BlockState blockstate = this.world.m_8055_(pos);
      return blockstate.m_60643_(this.world, pos, this.ampithere.m_6095_()) && this.world.m_46859_(pos.m_7494_()) && this.world.m_46859_(pos.m_6630_(2));
   }

   private boolean tryMoveTo() {
      if (!this.ampithere.isFlying()) {
         return this.ampithere.m_21573_().m_5624_(this.owner, this.followSpeed);
      } else {
         this.ampithere.m_21566_().m_6849_(this.owner.m_20185_(), this.owner.m_20186_() + (double)this.owner.m_20192_() + 5.0D + (double)this.ampithere.m_217043_().m_188503_(8), this.owner.m_20189_(), 0.25D);
         return true;
      }
   }
}
