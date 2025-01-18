package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.util.IFlapable;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class IFChainBuffer {
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

   private boolean compareDouble(double a, double b) {
      double c = a - b;
      return Math.abs(c - 1.0D) <= 0.01D;
   }

   public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (!this.compareDouble((double)entity.f_20883_, (double)entity.f_20884_) && Mth.m_14154_(this.yawVariation) < maxAngle) {
         this.yawVariation += Mth.m_14036_((entity.f_20884_ - entity.f_20883_) / divisor, -maxAngle, maxAngle);
      }

      if (this.yawVariation > angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.m_14154_(this.yawVariation) < angleDecrement) {
               this.yawVariation = angleDecrement;
               this.yawTimer = 0;
            }
         } else {
            ++this.yawTimer;
         }
      } else if (this.yawVariation < -1.0F * angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation += angleDecrement;
            if (Mth.m_14154_(this.yawVariation) < angleDecrement) {
               this.yawVariation = angleDecrement;
               this.yawTimer = 0;
            }
         } else {
            ++this.yawTimer;
         }
      }

   }

   public void calculateChainPitchBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevPitchVariation = entity.f_19860_;
      this.pitchVariation = entity.m_146909_();
   }

   public void calculateChainWaveBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevPitchVariation = this.pitchVariation;
      if (!(Math.abs(entity.m_146909_()) > maxAngle)) {
         if (!this.compareDouble((double)entity.m_146909_(), (double)entity.f_19860_) && Mth.m_14154_(this.pitchVariation) < maxAngle) {
            this.pitchVariation += Mth.m_14036_((entity.f_19860_ - entity.m_146909_()) / divisor, -maxAngle, maxAngle);
         }

         if (this.pitchVariation > angleDecrement) {
            if (this.pitchTimer > bufferTime) {
               this.pitchVariation -= angleDecrement;
               if (Mth.m_14154_(this.pitchVariation) < angleDecrement) {
                  this.pitchVariation = 0.0F;
                  this.pitchTimer = 0;
               }
            } else {
               ++this.pitchTimer;
            }
         } else if (this.pitchVariation < -1.0F * angleDecrement) {
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
   }

   public void calculateChainFlapBuffer(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (!this.compareDouble((double)entity.f_20883_, (double)entity.f_20884_) && Mth.m_14154_(this.yawVariation) < maxAngle) {
         this.yawVariation += Mth.m_14036_((entity.f_20884_ - entity.f_20883_) / divisor, -maxAngle, maxAngle);
         if (entity instanceof IFlapable && (double)Math.abs(entity.f_20884_ - entity.f_20883_) > 15.0D) {
            ((IFlapable)entity).flapWings();
         }
      }

      if (this.yawVariation > angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.m_14154_(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            ++this.yawTimer;
         }
      } else if (this.yawVariation < -1.0F * angleDecrement) {
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

   public void calculateChainFlapBufferHead(float maxAngle, int bufferTime, float angleDecrement, float divisor, LivingEntity entity) {
      this.prevYawVariation = this.yawVariation;
      if (!this.compareDouble((double)entity.f_20886_, (double)entity.f_20885_) && Mth.m_14154_(this.yawVariation) < maxAngle) {
         this.yawVariation += Mth.m_14036_((entity.f_20885_ - entity.f_20886_) / divisor, -maxAngle, maxAngle);
         if (entity instanceof IFlapable && (double)Math.abs(entity.f_20885_ - entity.f_20886_) > 15.0D) {
            ((IFlapable)entity).flapWings();
         }
      }

      if (this.yawVariation > angleDecrement) {
         if (this.yawTimer > bufferTime) {
            this.yawVariation -= angleDecrement;
            if (Mth.m_14154_(this.yawVariation) < angleDecrement) {
               this.yawVariation = 0.0F;
               this.yawTimer = 0;
            }
         } else {
            ++this.yawTimer;
         }
      } else if (this.yawVariation < -1.0F * angleDecrement) {
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

   public void calculateChainSwingBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainSwingBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void calculateChainWaveBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainWaveBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void calculateChainFlapBuffer(float maxAngle, int bufferTime, float angleDecrement, LivingEntity entity) {
      this.calculateChainFlapBuffer(maxAngle, bufferTime, angleDecrement, 1.0F, entity);
   }

   public void applyChainSwingBuffer(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleY += rotateAmount;
      }

   }

   public void applyChainWaveBuffer(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleX += rotateAmount;
      }

   }

   public void applyChainFlapBuffer(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleZ += rotateAmount;
      }

   }

   public void applyChainFlapBufferReverse(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleZ -= rotateAmount * 0.5F;
      }

   }

   public void applyChainSwingBufferReverse(BasicModelPart... boxes) {
      float rotateAmount = 0.017453292F * Mth.m_14179_(this.getPartialTicks(), this.prevYawVariation, this.yawVariation) / (float)boxes.length;
      BasicModelPart[] var3 = boxes;
      int var4 = boxes.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart box = var3[var5];
         box.rotateAngleY -= rotateAmount;
      }

   }

   public void applyChainWaveBufferReverse(BasicModelPart... boxes) {
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
