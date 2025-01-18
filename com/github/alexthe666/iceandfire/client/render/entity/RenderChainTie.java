package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelChainTie;
import com.github.alexthe666.iceandfire.entity.EntityChainTie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderChainTie extends EntityRenderer<EntityChainTie> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/misc/chain_tie.png");
   private final ModelChainTie leashKnotModel = new ModelChainTie();

   public RenderChainTie(Context context) {
      super(context);
   }

   public void render(@NotNull EntityChainTie entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(0.0F, 0.5F, 0.0F);
      matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
      this.leashKnotModel.setupAnim(entityIn, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110458_(TEXTURE));
      this.leashKnotModel.m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EntityChainTie entity) {
      return TEXTURE;
   }
}
