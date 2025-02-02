package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.ListModel;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ModelBanner extends ListModel {
   public final BasicModelPart flag = getModelRender();
   public final BasicModelPart pole = new BasicModelPart(64, 64, 44, 0);
   public final BasicModelPart bar;

   public ModelBanner() {
      this.pole.addBox(-1.0F, -30.0F, -1.0F, 2.0F, 42.0F, 2.0F, 0.0F);
      this.bar = new BasicModelPart(64, 64, 0, 42);
      this.bar.addBox(-10.0F, -32.0F, -1.0F, 20.0F, 2.0F, 2.0F, 0.0F);
   }

   public static BasicModelPart getModelRender() {
      BasicModelPart modelrenderer = new BasicModelPart(64, 64, 0, 0);
      modelrenderer.addBox(-10.0F, 0.0F, -2.0F, 20.0F, 40.0F, 1.0F, 0.0F);
      return modelrenderer;
   }

   public void m_6973_(@NotNull Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   @NotNull
   public Iterable<BasicModelPart> m_6195_() {
      return ImmutableList.of(this.flag, this.pole, this.bar);
   }
}
