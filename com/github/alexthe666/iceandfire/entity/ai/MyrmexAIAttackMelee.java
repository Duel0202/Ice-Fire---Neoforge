package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class MyrmexAIAttackMelee extends Goal {
   protected EntityMyrmexBase myrmex;
   private int attackTick;
   private final double speedTowardsTarget;
   private final boolean longMemory;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;
   private int failedPathFindingPenalty = 0;
   private final boolean canPenalize = false;
   private PathResult attackPath;

   public MyrmexAIAttackMelee(EntityMyrmexBase dragon, double speedIn, boolean useLongMemory) {
      this.myrmex = dragon;
      this.speedTowardsTarget = speedIn;
      this.longMemory = useLongMemory;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      LivingEntity LivingEntity = this.myrmex.m_5448_();
      if (!(this.myrmex.m_21573_() instanceof AdvancedPathNavigate)) {
         return false;
      } else if (LivingEntity instanceof Player && this.myrmex.getHive() != null && !this.myrmex.getHive().isPlayerReputationLowEnoughToFight(LivingEntity.m_20148_())) {
         return false;
      } else if (LivingEntity == null) {
         return false;
      } else if (!LivingEntity.m_6084_()) {
         return false;
      } else if (!this.myrmex.canMove()) {
         return false;
      } else {
         this.attackPath = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToLivingEntity(LivingEntity, this.speedTowardsTarget);
         if (this.attackPath != null) {
            return true;
         } else {
            return this.getAttackReachSqr(LivingEntity) >= this.myrmex.m_20275_(LivingEntity.m_20185_(), LivingEntity.m_20191_().f_82289_, LivingEntity.m_20189_());
         }
      }
   }

   public boolean m_8045_() {
      LivingEntity LivingEntity = this.myrmex.m_5448_();
      if (this.myrmex.m_21214_() != null && this.myrmex.m_21214_().m_6084_()) {
         LivingEntity = this.myrmex.m_21214_();
      }

      if ((LivingEntity == null || LivingEntity.m_6084_()) && this.myrmex.m_21573_() instanceof AdvancedPathNavigate) {
         return LivingEntity != null && LivingEntity.m_6084_() && (!(LivingEntity instanceof Player) || !LivingEntity.m_5833_() && !((Player)LivingEntity).m_7500_());
      } else {
         this.m_8041_();
         return false;
      }
   }

   public void m_8056_() {
      this.delayCounter = 0;
   }

   public void m_8041_() {
      LivingEntity LivingEntity = this.myrmex.m_5448_();
      if (LivingEntity instanceof Player && (LivingEntity.m_5833_() || ((Player)LivingEntity).m_7500_())) {
         this.myrmex.m_6710_((LivingEntity)null);
         this.myrmex.m_21335_((Entity)null);
      }

   }

   public void m_8037_() {
      LivingEntity entity = this.myrmex.m_5448_();
      if (entity != null) {
         this.myrmex.m_21573_().m_5624_(entity, this.speedTowardsTarget);
         double d0 = this.myrmex.m_20275_(entity.m_20185_(), entity.m_20191_().f_82289_, entity.m_20189_());
         double d1 = this.getAttackReachSqr(entity);
         --this.delayCounter;
         if ((this.longMemory || this.myrmex.m_21574_().m_148306_(entity)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entity.m_20275_(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.myrmex.m_217043_().m_188501_() < 0.05F)) {
            this.targetX = entity.m_20185_();
            this.targetY = entity.m_20191_().f_82289_;
            this.targetZ = entity.m_20189_();
            this.delayCounter = 4 + this.myrmex.m_217043_().m_188503_(7);
            Objects.requireNonNull(this);
            if (d0 > 1024.0D) {
               this.delayCounter += 10;
            } else if (d0 > 256.0D) {
               this.delayCounter += 5;
            }

            if (this.myrmex.canMove()) {
               this.delayCounter += 15;
            }
         }

         this.attackTick = Math.max(this.attackTick - 1, 0);
         if (d0 <= d1 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.myrmex.m_6674_(InteractionHand.MAIN_HAND);
            this.myrmex.m_7327_(entity);
         }
      }

   }

   protected double getAttackReachSqr(LivingEntity attackTarget) {
      return (double)(this.myrmex.m_20205_() * 2.0F * this.myrmex.m_20205_() * 2.0F + attackTarget.m_20205_());
   }
}
