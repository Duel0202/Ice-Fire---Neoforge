package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import net.minecraft.world.entity.Entity;

public abstract class ModelDragonBase<T extends Entity> extends AdvancedEntityModel<T> implements ICustomStatueModel {
   public void rotate(ModelAnimator animator, AdvancedModelBox model, float x, float y, float z) {
      animator.rotate(model, (float)Math.toRadians((double)x), (float)Math.toRadians((double)y), (float)Math.toRadians((double)z));
   }

   public void rotateMinus(ModelAnimator animator, AdvancedModelBox model, float x, float y, float z) {
      animator.rotate(model, (float)Math.toRadians((double)x) - model.defaultRotationX, (float)Math.toRadians((double)y) - model.defaultRotationY, (float)Math.toRadians((double)z) - model.defaultRotationZ);
   }

   public void progressRotationInterp(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ, float max) {
      model.rotateAngleX += progress * (rotX - model.defaultRotationX) / max;
      model.rotateAngleY += progress * (rotY - model.defaultRotationY) / max;
      model.rotateAngleZ += progress * (rotZ - model.defaultRotationZ) / max;
   }

   public void progressPositionInterp(AdvancedModelBox model, float progress, float x, float y, float z, float max) {
      model.rotationPointX += progress * x / max;
      model.rotationPointY += progress * y / max;
      model.rotationPointZ += progress * z / max;
   }

   public void progressRotation(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ) {
      model.rotateAngleX += progress * (rotX - model.defaultRotationX) / 20.0F;
      model.rotateAngleY += progress * (rotY - model.defaultRotationY) / 20.0F;
      model.rotateAngleZ += progress * (rotZ - model.defaultRotationZ) / 20.0F;
   }

   public void progressRotationPrev(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ) {
      model.rotateAngleX += progress * rotX / 20.0F;
      model.rotateAngleY += progress * rotY / 20.0F;
      model.rotateAngleZ += progress * rotZ / 20.0F;
   }

   public void progressPosition(AdvancedModelBox model, float progress, float x, float y, float z) {
      model.rotationPointX += progress * (x - model.defaultPositionX) / 20.0F;
      model.rotationPointY += progress * (y - model.defaultPositionY) / 20.0F;
      model.rotationPointZ += progress * (z - model.defaultPositionZ) / 20.0F;
   }

   public void progressPositionPrev(AdvancedModelBox model, float progress, float x, float y, float z) {
      model.rotationPointX += progress * x / 20.0F;
      model.rotationPointY += progress * y / 20.0F;
      model.rotationPointZ += progress * z / 20.0F;
   }

   public void setRotateAngle(AdvancedModelBox modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public void faceTarget(float yaw, float pitch, float rotationDivisor, AdvancedModelBox... boxes) {
      float actualRotationDivisor = rotationDivisor * (float)boxes.length;
      float yawAmount = yaw * 3.1415927F / 180.0F / actualRotationDivisor;
      float pitchAmount = pitch * 3.1415927F / 180.0F / actualRotationDivisor;
      AdvancedModelBox[] var8 = boxes;
      int var9 = boxes.length;

      for(int var10 = 0; var10 < var9; ++var10) {
         AdvancedModelBox box = var8[var10];
         box.rotateAngleY += yawAmount;
         box.rotateAngleX += pitchAmount;
      }

   }
}
