package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderChain {
   private static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/misc/chain_link.png");

   public static void render(LivingEntity entityLivingIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int lightIn, List<Entity> chainedTo) {
      Iterator var6 = chainedTo.iterator();

      while(var6.hasNext()) {
         Entity chainTarget = (Entity)var6.next();
         if (chainTarget == null) {
            IceAndFire.LOGGER.warn("Found null value in list of target entities");
         } else {
            try {
               renderLink(entityLivingIn, partialTicks, matrixStackIn, bufferIn, lightIn, chainTarget);
            } catch (Exception var9) {
               IceAndFire.LOGGER.warn("Could not render chain link for {} connected to {}", entityLivingIn.toString(), chainTarget.toString());
            }
         }
      }

   }

   public static <E extends Entity> void renderLink(LivingEntity entityLivingIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int lightIn, E chainTarget) {
      float f3 = entityLivingIn.m_20206_() * 0.4F;
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0D, (double)f3, 0.0D);
      Vec3 vector3d = getPosition(chainTarget, (double)chainTarget.m_20206_() * 0.5D, partialTicks);
      Vec3 vector3d1 = getPosition(entityLivingIn, (double)f3, partialTicks);
      Vec3 vector3d2 = vector3d.m_82546_(vector3d1);
      float f4 = (float)(vector3d2.m_82553_() + 0.0D);
      vector3d2 = vector3d2.m_82541_();
      float f5 = (float)Math.acos(vector3d2.f_82480_);
      float f6 = (float)Math.atan2(vector3d2.f_82481_, vector3d2.f_82479_);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252961_(1.5707964F - f6));
      matrixStackIn.m_252781_(Axis.f_252529_.m_252961_(f5));
      float f7 = -1.0F;
      int j = 255;
      int k = 255;
      int l = 255;
      float f19 = 0.0F;
      float f20 = 0.2F;
      float f21 = 0.0F;
      float f22 = -0.2F;
      float f23 = Mth.m_14089_(f7 + 1.5707964F) * 0.2F;
      float f24 = Mth.m_14031_(f7 + 1.5707964F) * 0.2F;
      float f25 = Mth.m_14089_(f7 + 4.712389F) * 0.2F;
      float f26 = Mth.m_14031_(f7 + 4.712389F) * 0.2F;
      float f29 = 0.0F;
      float f30 = f4 + f29;
      float f32 = 0.75F;
      float f31 = f4 + f32;
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110458_(getTexture()));
      Pose matrixstack$entry = matrixStackIn.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_252922_();
      Matrix3f matrix3f = matrixstack$entry.m_252943_();
      matrixStackIn.m_85836_();
      vertex(ivertexbuilder, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f31, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f32, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f32, lightIn);
      vertex(ivertexbuilder, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f31, lightIn);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }

   private static void vertex(VertexConsumer p_229108_0_, Matrix4f p_229108_1_, Matrix3f p_229108_2_, float p_229108_3_, float p_229108_4_, float p_229108_5_, int p_229108_6_, int p_229108_7_, int p_229108_8_, float p_229108_9_, float p_229108_10_, int packedLight) {
      p_229108_0_.m_252986_(p_229108_1_, p_229108_3_, p_229108_4_, p_229108_5_).m_6122_(p_229108_6_, p_229108_7_, p_229108_8_, 255).m_7421_(p_229108_9_, p_229108_10_).m_86008_(OverlayTexture.f_118083_).m_85969_(packedLight).m_252939_(p_229108_2_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   private static Vec3 getPosition(Entity LivingEntityIn, double p_177110_2_, float p_177110_4_) {
      double d0 = LivingEntityIn.f_19790_ + (LivingEntityIn.m_20185_() - LivingEntityIn.f_19790_) * (double)p_177110_4_;
      double d1 = p_177110_2_ + LivingEntityIn.f_19791_ + (LivingEntityIn.m_20186_() - LivingEntityIn.f_19791_) * (double)p_177110_4_;
      double d2 = LivingEntityIn.f_19792_ + (LivingEntityIn.m_20189_() - LivingEntityIn.f_19792_) * (double)p_177110_4_;
      return new Vec3(d0, d1, d2);
   }

   public static ResourceLocation getTexture() {
      return TEXTURE;
   }
}
