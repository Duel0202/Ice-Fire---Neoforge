package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityPixie;
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

public class PixieAIFollowOwner extends Goal {
   private final EntityPixie tameable;
   Level world;
   float maxDist;
   float minDist;
   private LivingEntity owner;
   private int timeToRecalcPath;
   private float oldWaterCost;

   public PixieAIFollowOwner(EntityPixie tameableIn, double followSpeedIn, float minDistIn, float maxDistIn) {
      this.tameable = tameableIn;
      this.world = tameableIn.m_9236_();
      this.minDist = minDistIn;
      this.maxDist = maxDistIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      LivingEntity LivingEntity = this.tameable.m_269323_();
      if (LivingEntity == null) {
         return false;
      } else if (LivingEntity instanceof Player && LivingEntity.m_5833_()) {
         return false;
      } else if (this.tameable.isPixieSitting()) {
         return false;
      } else if (this.tameable.m_20280_(LivingEntity) < (double)(this.minDist * this.minDist)) {
         return false;
      } else {
         this.owner = LivingEntity;
         return true;
      }
   }

   public boolean m_8045_() {
      return !this.tameable.isPixieSitting() && this.tameable.m_20280_(this.owner) > (double)(this.maxDist * this.maxDist);
   }

   public void m_8056_() {
      this.timeToRecalcPath = 0;
      this.oldWaterCost = this.tameable.m_21439_(BlockPathTypes.WATER);
      this.tameable.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   public void m_8041_() {
      this.owner = null;
      this.tameable.m_21441_(BlockPathTypes.WATER, this.oldWaterCost);
      this.tameable.slowSpeed = false;
   }

   private boolean isEmptyBlock(BlockPos pos) {
      BlockState BlockState = this.world.m_8055_(pos);
      return BlockState.m_60795_() || !BlockState.m_60815_();
   }

   public void m_8037_() {
      this.tameable.m_21563_().m_24960_(this.owner, 10.0F, (float)this.tameable.m_8132_());
      if (!this.tameable.isPixieSitting() && --this.timeToRecalcPath <= 0) {
         this.timeToRecalcPath = 10;
         this.tameable.m_21566_().m_6849_(this.owner.m_20185_(), this.owner.m_20186_() + (double)this.owner.m_20192_(), this.owner.m_20189_(), 0.25D);
         this.tameable.slowSpeed = true;
         if (!this.tameable.m_21523_() && this.tameable.m_20280_(this.owner) >= 50.0D) {
            int i = Mth.m_14107_(this.owner.m_20185_()) - 2;
            int j = Mth.m_14107_(this.owner.m_20189_()) - 2;
            int k = Mth.m_14107_(this.owner.m_20191_().f_82289_);

            for(int l = 0; l <= 4; ++l) {
               for(int i1 = 0; i1 <= 4; ++i1) {
                  if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isEmptyBlock(new BlockPos(i + l, k, j + i1)) && this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1))) {
                     this.tameable.m_7678_((double)((float)(i + l) + 0.5F), (double)k + 1.5D, (double)((float)(j + i1) + 0.5F), this.tameable.m_146908_(), this.tameable.m_146909_());
                     return;
                  }
               }
            }
         }
      }

   }
}
