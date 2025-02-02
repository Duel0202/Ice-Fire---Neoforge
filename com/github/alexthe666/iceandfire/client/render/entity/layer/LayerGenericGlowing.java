package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class LayerGenericGlowing<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
   private final LivingEntityRenderer render;
   private final ResourceLocation texture;

   public LayerGenericGlowing(LivingEntityRenderer renderIn, ResourceLocation texture) {
      super(renderIn);
      this.render = renderIn;
      this.texture = texture;
   }

   public boolean shouldCombineTextures() {
      return true;
   }

   public void render(@NotNull PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, @NotNull LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      RenderType eyes = RenderType.m_110488_(this.texture);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(eyes);
      this.m_117386_().m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
