package com.github.alexthe666.iceandfire.client.model.util;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class HideableLayer<T extends Entity, M extends EntityModel<T>, C extends RenderLayer<T, M>> extends RenderLayer<T, M> {
   public boolean hidden = false;
   C layerRenderer;

   public HideableLayer(C layerRenderer, RenderLayerParent<T, M> entityRendererIn) {
      super(entityRendererIn);
      this.layerRenderer = layerRenderer;
   }

   public void m_6494_(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, @NotNull T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!this.hidden) {
         this.layerRenderer.m_6494_(matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
      }

   }
}
