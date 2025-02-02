package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;

public class ModelUtils {
   private static void setRotateAngle(AdvancedModelBox model, float x, float y, float z) {
      model.rotateAngleX = x;
      model.rotateAngleY = y;
      model.rotateAngleZ = z;
   }

   public static void rotate(ModelAnimator ModelAnimator, AdvancedModelBox box, float x, float y, float z) {
      ModelAnimator.rotate(box, (float)Math.toRadians((double)x), (float)Math.toRadians((double)y), (float)Math.toRadians((double)z));
   }

   public static void rotateFrom(ModelAnimator ModelAnimator, AdvancedModelBox box, float x, float y, float z) {
      ModelAnimator.rotate(box, (float)Math.toRadians((double)x) - box.rotateAngleX, (float)Math.toRadians((double)y) - box.rotateAngleY, (float)Math.toRadians((double)z) - box.rotateAngleZ);
   }

   public static void rotateFromRadians(ModelAnimator ModelAnimator, AdvancedModelBox box, float x, float y, float z) {
      ModelAnimator.rotate(box, x - box.rotateAngleX, y - box.rotateAngleY, z - box.rotateAngleZ);
   }
}
