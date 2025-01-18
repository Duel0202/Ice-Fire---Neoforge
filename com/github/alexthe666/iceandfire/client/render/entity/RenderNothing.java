package com.github.alexthe666.iceandfire.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class RenderNothing<T extends Entity> extends EntityRenderer<T> {
   public RenderNothing(Context context) {
      super(context);
   }

   public void m_7392_(@NotNull T entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   public boolean m_5523_(@NotNull T livingEntityIn, @NotNull Frustum camera, double camX, double camY, double camZ) {
      return !this.f_114476_.m_114377_() ? false : super.m_5523_(livingEntityIn, camera, camX, camY, camZ);
   }

   @NotNull
   public ResourceLocation m_5478_(@NotNull Entity entity) {
      return null;
   }
}
