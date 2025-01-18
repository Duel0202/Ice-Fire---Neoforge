package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class GorgonAIStareAttack extends Goal {
   private final EntityGorgon entity;
   private final double moveSpeedAmp;
   private final float maxAttackDistance;
   private int seeTime;
   private boolean strafingClockwise;
   private boolean strafingBackwards;
   private int strafingTime = -1;

   public GorgonAIStareAttack(EntityGorgon gorgon, double speedAmplifier, int delay, float maxDistance) {
      this.entity = gorgon;
      this.moveSpeedAmp = speedAmplifier;
      this.maxAttackDistance = maxDistance * maxDistance;
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
   }

   public boolean m_8036_() {
      return this.entity.m_5448_() != null;
   }

   public boolean m_8045_() {
      return this.m_8036_() || !this.entity.m_21573_().m_26571_();
   }

   public void m_8041_() {
      super.m_8041_();
      this.seeTime = 0;
      this.entity.m_5810_();
   }

   public void m_8037_() {
      LivingEntity LivingEntity = this.entity.m_5448_();
      if (LivingEntity != null) {
         if (EntityGorgon.isStoneMob(LivingEntity)) {
            this.entity.m_6710_((LivingEntity)null);
            this.m_8041_();
            return;
         }

         this.entity.m_21563_().m_24950_(LivingEntity.m_20185_(), LivingEntity.m_20186_() + (double)LivingEntity.m_20192_(), LivingEntity.m_20189_(), (float)this.entity.m_8085_(), (float)this.entity.m_8132_());
         double d0 = this.entity.m_20275_(LivingEntity.m_20185_(), LivingEntity.m_20191_().f_82289_, LivingEntity.m_20189_());
         boolean flag = this.entity.m_21574_().m_148306_(LivingEntity);
         boolean flag1 = this.seeTime > 0;
         if (flag != flag1) {
            this.seeTime = 0;
         }

         if (flag) {
            ++this.seeTime;
         } else {
            --this.seeTime;
         }

         if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20) {
            this.entity.m_21573_().m_26573_();
            ++this.strafingTime;
         } else {
            this.entity.m_21573_().m_5624_(LivingEntity, this.moveSpeedAmp);
            this.strafingTime = -1;
         }

         if (this.strafingTime >= 20) {
            if ((double)this.entity.m_217043_().m_188501_() < 0.3D) {
               this.strafingClockwise = !this.strafingClockwise;
            }

            if ((double)this.entity.m_217043_().m_188501_() < 0.3D) {
               this.strafingBackwards = !this.strafingBackwards;
            }

            this.strafingTime = 0;
         }

         if (this.strafingTime > -1) {
            if (d0 > (double)(this.maxAttackDistance * 0.75F)) {
               this.strafingBackwards = false;
            } else if (d0 < (double)(this.maxAttackDistance * 0.25F)) {
               this.strafingBackwards = true;
            }

            this.entity.m_21566_().m_24988_(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
            this.entity.m_21563_().m_24950_(LivingEntity.m_20185_(), LivingEntity.m_20186_() + (double)LivingEntity.m_20192_(), LivingEntity.m_20189_(), (float)this.entity.m_8085_(), (float)this.entity.m_8132_());
            this.entity.forcePreyToLook(LivingEntity);
         } else {
            this.entity.m_21563_().m_24960_(LivingEntity, 30.0F, 30.0F);
            this.entity.forcePreyToLook(LivingEntity);
         }
      }

   }
}
