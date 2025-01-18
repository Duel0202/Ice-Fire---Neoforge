package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityAmphithere;
import java.util.EnumSet;
import java.util.Objects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;

public class AmphithereAIAttackMelee extends Goal {
   protected EntityAmphithere attacker;
   protected int attackTick;
   double speedTowardsTarget;
   boolean longMemory;
   Path path;
   private int delayCounter;
   private double targetX;
   private double targetY;
   private double targetZ;
   private int failedPathFindingPenalty = 0;
   private final boolean canPenalize = false;

   public AmphithereAIAttackMelee(EntityAmphithere amphithere, double speedIn, boolean useLongMemory) {
      this.attacker = amphithere;
      this.speedTowardsTarget = speedIn;
      this.longMemory = useLongMemory;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.TARGET));
   }

   public boolean m_8036_() {
      LivingEntity LivingEntity = this.attacker.m_5448_();
      if (!this.attacker.canMove()) {
         return false;
      } else if (LivingEntity == null) {
         return false;
      } else if (!LivingEntity.m_6084_()) {
         return false;
      } else {
         this.path = this.attacker.m_21573_().m_6570_(LivingEntity, 0);
         if (this.path != null) {
            return true;
         } else {
            return this.getAttackReachSqr(LivingEntity) >= this.attacker.m_20275_(LivingEntity.m_20185_(), LivingEntity.m_20191_().f_82289_, LivingEntity.m_20189_());
         }
      }
   }

   public boolean m_8045_() {
      LivingEntity living = this.attacker.m_5448_();
      if (living == null) {
         return false;
      } else if (!living.m_6084_()) {
         return false;
      } else if (!this.longMemory) {
         return !this.attacker.m_21573_().m_26571_();
      } else if (!this.attacker.m_21444_(living.m_20183_())) {
         return false;
      } else {
         return !(living instanceof Player) || !living.m_5833_() && !((Player)living).m_7500_();
      }
   }

   public void m_8056_() {
      if (this.attacker.isFlying()) {
         this.attacker.m_21566_().m_6849_(this.targetX, this.targetY, this.targetZ, 0.10000000149011612D);
      } else {
         this.attacker.m_21573_().m_26536_(this.path, this.speedTowardsTarget);
      }

      this.delayCounter = 0;
   }

   public void m_8041_() {
      LivingEntity LivingEntity = this.attacker.m_5448_();
      if (LivingEntity instanceof Player && (LivingEntity.m_5833_() || ((Player)LivingEntity).m_7500_())) {
         this.attacker.m_6710_((LivingEntity)null);
      }

      this.attacker.m_21573_().m_26573_();
   }

   public void m_8037_() {
      LivingEntity LivingEntity = this.attacker.m_5448_();
      if (this.attacker.isFlying()) {
         this.attacker.m_21566_().m_6849_(LivingEntity.m_20185_(), LivingEntity.m_20186_() + (double)LivingEntity.m_20192_(), LivingEntity.m_20189_(), 0.1D);
      }

      this.attacker.m_21563_().m_24960_(LivingEntity, 30.0F, 30.0F);
      double d0 = this.attacker.m_20275_(LivingEntity.m_20185_(), LivingEntity.m_20191_().f_82289_, LivingEntity.m_20189_());
      --this.delayCounter;
      if ((this.longMemory || this.attacker.m_21574_().m_148306_(LivingEntity)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || LivingEntity.m_20275_(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.m_217043_().m_188501_() < 0.05F)) {
         this.targetX = LivingEntity.m_20185_();
         this.targetY = LivingEntity.m_20191_().f_82289_;
         this.targetZ = LivingEntity.m_20189_();
         this.delayCounter = 4 + this.attacker.m_217043_().m_188503_(7);
         Objects.requireNonNull(this);
         if (d0 > 1024.0D) {
            this.delayCounter += 10;
         } else if (d0 > 256.0D) {
            this.delayCounter += 5;
         }

         if (!this.attacker.m_21573_().m_5624_(LivingEntity, this.speedTowardsTarget)) {
            this.delayCounter += 15;
         }
      }

      this.attackTick = Math.max(this.attackTick - 1, 0);
      this.checkAndPerformAttack(LivingEntity, d0);
   }

   protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
      double d0 = this.getAttackReachSqr(enemy);
      if (distToEnemySqr <= d0) {
         this.attackTick = 20;
         this.attacker.m_6674_(InteractionHand.MAIN_HAND);
         this.attacker.m_7327_(enemy);
      }

   }

   protected double getAttackReachSqr(LivingEntity attackTarget) {
      return (double)(this.attacker.m_20205_() * 2.0F * this.attacker.m_20205_() * 2.0F + attackTarget.m_20205_());
   }
}
