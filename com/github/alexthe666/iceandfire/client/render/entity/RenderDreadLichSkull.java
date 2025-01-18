package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelDreadLichSkull;
import com.github.alexthe666.iceandfire.entity.EntityDreadLichSkull;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderDreadLichSkull extends EntityRenderer<EntityDreadLichSkull> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/dread/dread_lich_skull.png");
   private static final ModelDreadLichSkull MODEL_SPIRIT = new ModelDreadLichSkull();

   public RenderDreadLichSkull(Context context) {
      super(context);
   }

   public void render(EntityDreadLichSkull entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      float f = 0.0625F;
      if (entity.f_19797_ > 3) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(1.5F, -1.5F, 1.5F);
         float yaw = entity.f_19859_ + (entity.m_146908_() - entity.f_19859_) * partialTicks;
         matrixStackIn.m_252880_(0.0F, 0.0F, 0.0F);
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(yaw - 180.0F));
         VertexConsumer ivertexbuilder = ItemRenderer.m_115211_(bufferIn, RenderType.m_110488_(TEXTURE), false, false);
         MODEL_SPIRIT.m_7695_(matrixStackIn, ivertexbuilder, 240, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
      }

      super.m_7392_(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   private float interpolateValue(float start, float end, float pct) {
      return start + (end - start) * pct;
   }

   @Nullable
   public ResourceLocation getTextureLocation(@NotNull EntityDreadLichSkull entity) {
      return TEXTURE;
   }
}
