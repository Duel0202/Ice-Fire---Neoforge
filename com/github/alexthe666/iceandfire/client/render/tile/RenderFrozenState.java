package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.client.render.IafRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

public class RenderFrozenState {
   private static final ResourceLocation TEXTURE_0 = new ResourceLocation("textures/block/frosted_ice_0.png");
   private static final ResourceLocation TEXTURE_1 = new ResourceLocation("textures/block/frosted_ice_1.png");
   private static final ResourceLocation TEXTURE_2 = new ResourceLocation("textures/block/frosted_ice_2.png");
   private static final ResourceLocation TEXTURE_3 = new ResourceLocation("textures/block/frosted_ice_3.png");

   public static void render(LivingEntity entity, PoseStack matrixStack, MultiBufferSource bufferIn, int light, int frozenTicks) {
      float sideExpand = -0.125F;
      float sideExpandY = 0.325F;
      AABB axisalignedbb1 = new AABB((double)(-entity.m_20205_() / 2.0F - sideExpand), 0.0D, (double)(-entity.m_20205_() / 2.0F - sideExpand), (double)(entity.m_20205_() / 2.0F + sideExpand), (double)(entity.m_20206_() + sideExpandY), (double)(entity.m_20205_() / 2.0F + sideExpand));
      matrixStack.m_85836_();
      renderMovingAABB(axisalignedbb1, matrixStack, bufferIn, light, 255, frozenTicks);
      matrixStack.m_85849_();
   }

   private static ResourceLocation getIceTexture(int ticksFrozen) {
      if (ticksFrozen < 100) {
         if (ticksFrozen < 50) {
            return ticksFrozen < 20 ? TEXTURE_3 : TEXTURE_2;
         } else {
            return TEXTURE_1;
         }
      } else {
         return TEXTURE_0;
      }
   }

   public static void renderMovingAABB(AABB boundingBox, PoseStack stack, MultiBufferSource bufferIn, int light, int alpha, int frozenTicks) {
      RenderType rendertype = IafRenderType.getIce(getIceTexture(frozenTicks));
      VertexConsumer vertexbuffer = bufferIn.m_6299_(rendertype);
      Matrix4f matrix4f = stack.m_85850_().m_252922_();
      float maxX = (float)boundingBox.f_82291_ * 0.425F;
      float minX = (float)boundingBox.f_82288_ * 0.425F;
      float maxY = (float)boundingBox.f_82292_ * 0.425F;
      float minY = (float)boundingBox.f_82289_ * 0.425F;
      float maxZ = (float)boundingBox.f_82293_ * 0.425F;
      float minZ = (float)boundingBox.f_82290_ * 0.425F;
      float maxU = maxZ - minZ;
      float maxV = maxY - minY;
      float minU = minZ - maxZ;
      float minV = minY - maxY;
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82289_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(minU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82292_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(minU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82292_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82289_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82289_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82292_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82292_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82289_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(-1.0F, 0.0F, 0.0F).m_5752_();
      maxU = maxX - minX;
      maxV = maxY - minY;
      minU = minX - maxX;
      minV = minY - maxY;
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82289_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(minU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82292_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(minU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82292_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82289_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, -1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82289_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82292_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82292_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82289_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 0.0F, 1.0F).m_5752_();
      maxU = maxZ - minZ;
      maxV = maxX - minX;
      minU = minZ - maxZ;
      minV = minX - maxX;
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82292_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82292_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82292_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82292_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, 1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82289_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82288_, (float)boundingBox.f_82289_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, minV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82289_, (float)boundingBox.f_82290_).m_6122_(255, 255, 255, alpha).m_7421_(maxU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
      vertexbuffer.m_252986_(matrix4f, (float)boundingBox.f_82291_, (float)boundingBox.f_82289_, (float)boundingBox.f_82293_).m_6122_(255, 255, 255, alpha).m_7421_(minU, maxV).m_86008_(OverlayTexture.f_118083_).m_85969_(light).m_5601_(0.0F, -1.0F, 0.0F).m_5752_();
   }
}
