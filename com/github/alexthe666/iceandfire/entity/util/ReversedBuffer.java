package com.github.alexthe666.iceandfire.entity.util;

import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class ReversedBuffer {
   private int yawTimer;
   private float yawVariation;
   private int pitchTimer;
   private float pitchVariation;
   private float prevYawVariation;
   private float prevPitchVariation;

   public void resetRotations() {
      this.yawVariation = 0.0F;
      this.pitchVariation = 0.0F;
      this.prevYawVariation = 0.0F;
      this.prevPitchVariation = 0.0F;
   }

   public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (entity.f_20883_ != entity.f_20884_ && Mth.m_14154_(this.yawVariation) < maxAngle) {
         this.yawVariation += (entity.f_20884_ - entity.f_20883_) / divisor;
      }

      if (this.yawVariation > 0.7F * angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.m_14154_(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            ++this.yawTimer;
         }
      } else if (this.yawVariation < -0.7F * angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation += angleDecrement;
            if (Mth.m_14154_(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            ++this.yawTimer;
         }
      }

   }

   public void calculateChainWaveBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevPitchVariation = this.pitchVariation;
      if (entity.m_146909_() != entity.f_19860_ && Mth.m_14154_(this.pitchVariation) < maxAngle) {
         this.pitchVariation += (entity.f_19860_ - entity.m_146909_()) / divisor;
      }

      if (this.pitchVariation > 0.7F * angleDecrement) {
         if (this.pitchTimer > bufferTime) {
            this.pitchVariation -= angleDecrement;
            if (Mth.m_14154_(this.pitchVariation) < angleDecrement) {
               this.pitchVariation = 0.0F;
               this.pitchTimer = 0;
            }
         } else {
            ++this.pitchTimer;
         }
      } else if (this.pitchVariation < -0.7F * angleDecrement) {
         if (this.pitchTimer > bufferTime) {
            this.pitchVariation += angleDecrement;
            if (Mth.m_14154_(this.pitchVariation) < angleDecrement) {
               this.pitchVariation = 0.0F;
               this.pitchTimer = 0;
            }
         } else {
            ++this.pitchTimer;
         }
      }

   }

   public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainSwingBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void calculateChainWaveBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainWaveBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void applyChainSwingBuffer(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleY -= rotateAmount;
      }

   }

   public void applyChainWaveBuffer(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleX -= rotateAmount;
      }

   }

   private float getPartialTicks() {
      return Minecraft.m_91087_().m_91296_();
   }
}
