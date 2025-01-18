package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelDreadLichSkull;
import com.github.alexthe666.iceandfire.entity.EntityDragonLightningCharge;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderDragonLightningCharge extends EntityRenderer<EntityDragonLightningCharge> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/lightningdragon/charge.png");
   public static final ResourceLocation TEXTURE_CORE = new ResourceLocation("iceandfire:textures/models/lightningdragon/charge_core.png");
   private static final ModelDreadLichSkull MODEL_SPIRIT = new ModelDreadLichSkull();

   public RenderDragonLightningCharge(Context context) {
      super(context);
   }

   public void render(EntityDragonLightningCharge entity, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      float f = (float)entity.f_19797_ + partialTicks;
      float yaw = entity.f_19859_ + (entity.m_146908_() - entity.f_19859_) * partialTicks;
      VertexConsumer ivertexbuilder2 = bufferIn.m_6299_(RenderType.m_110488_(TEXTURE_CORE));
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110436_(TEXTURE, f * 0.01F, f * 0.01F));
      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(0.0F, 0.5F, 0.0F);
      matrixStackIn.m_252880_(0.0F, -0.25F, 0.0F);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(yaw - 180.0F));
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(f * 20.0F));
      matrixStackIn.m_252880_(0.0F, 0.25F, 0.0F);
      MODEL_SPIRIT.m_7695_(matrixStackIn, ivertexbuilder2, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(0.0F, 0.5F, 0.0F);
      matrixStackIn.m_252880_(0.0F, -0.25F, 0.0F);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(yaw - 180.0F));
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(f * 15.0F));
      matrixStackIn.m_252880_(0.0F, 0.25F, 0.0F);
      matrixStackIn.m_85841_(1.5F, 1.5F, 1.5F);
      MODEL_SPIRIT.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(0.0F, 0.75F, 0.0F);
      matrixStackIn.m_252880_(0.0F, -0.25F, 0.0F);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(yaw - 180.0F));
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(f * 10.0F));
      matrixStackIn.m_252880_(0.0F, 0.75F, 0.0F);
      matrixStackIn.m_85841_(2.5F, 2.5F, 2.5F);
      MODEL_SPIRIT.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private float interpolateValue(float start, float end, float pct) {
      return start + (end - start) * pct;
   }

   @Nullable
   public ResourceLocation getTextureLocation(@NotNull EntityDragonLightningCharge entity) {
      return TEXTURE;
   }
}
