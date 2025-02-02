package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.client.model.util.HideableModelRenderer;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.Entity;

public class ModelDreadLichSkull extends AdvancedEntityModel {
   public HideableModelRenderer bipedHead;
   public HideableModelRenderer bipedHeadwear;

   public ModelDreadLichSkull() {
      this(0.0F);
   }

   public ModelDreadLichSkull(float modelSize) {
      this.texHeight = 32;
      this.texWidth = 64;
      this.bipedHead = new HideableModelRenderer(this, 0, 0);
      this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize - 0.5F);
      this.bipedHead.setPos(0.0F, 0.0F, 0.0F);
      this.bipedHeadwear = new HideableModelRenderer(this, 32, 0);
      this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelSize);
      this.bipedHeadwear.setPos(0.0F, 0.0F, 0.0F);
      this.updateDefaultPose();
   }

   public void m_6973_(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.bipedHead, this.bipedHeadwear);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.bipedHead, this.bipedHeadwear);
   }
}
