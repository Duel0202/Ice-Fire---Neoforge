package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.mojang.blaze3d.vertex.PoseStack;

public abstract class ModelMyrmexBase<T extends EntityMyrmexBase> extends ModelDragonBase<T> {
   private static final ModelMyrmexLarva LARVA_MODEL = new ModelMyrmexLarva();
   private static final ModelMyrmexPupa PUPA_MODEL = new ModelMyrmexPupa();

   public void postRenderArm(float scale, PoseStack stackIn) {
      BasicModelPart[] var3 = this.getHeadParts();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BasicModelPart renderer = var3[var5];
         renderer.translateRotate(stackIn);
      }

   }

   public abstract BasicModelPart[] getHeadParts();
}
