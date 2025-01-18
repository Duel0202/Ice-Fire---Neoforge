package com.github.alexthe666.iceandfire.client.particle;

import com.github.alexthe666.iceandfire.entity.EntityCockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class CockatriceBeamRender {
   public static final RenderType TEXTURE_BEAM = RenderType.m_110458_(new ResourceLocation("iceandfire:textures/models/cockatrice/beam.png"));

   private static void vertex(VertexConsumer p_229108_0_, Matrix4f p_229108_1_, Matrix3f p_229108_2_, float p_229108_3_, float p_229108_4_, float p_229108_5_, int p_229108_6_, int p_229108_7_, int p_229108_8_, float p_229108_9_, float p_229108_10_) {
      p_229108_0_.m_252986_(p_229108_1_, p_229108_3_, p_229108_4_, p_229108_5_).m_6122_(p_229108_6_, p_229108_7_, p_229108_8_, 255).m_7421_(p_229108_9_, p_229108_10_).m_86008_(OverlayTexture.f_118083_).m_85969_(15728880).m_252939_(p_229108_2_, 0.0F, 1.0F, 0.0F).m_5752_();
   }

   public static void render(Entity entityIn, Entity targetEntity, PoseStack matrixStackIn, MultiBufferSource bufferIn, float partialTicks) {
      float f = 1.0F;
      if (entityIn instanceof EntityCockatrice) {
         f = ((EntityCockatrice)entityIn).getAttackAnimationScale(partialTicks);
      }

      float f1 = (float)entityIn.m_9236_().m_46467_() + partialTicks;
      float f2 = f1 * 0.5F % 1.0F;
      float f3 = entityIn.m_20192_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0D, (double)f3, 0.0D);
      Vec3 Vector3d = getPosition(targetEntity, (double)targetEntity.m_20206_() * 0.5D, partialTicks);
      Vec3 Vector3d1 = getPosition(entityIn, (double)f3, partialTicks);
      Vec3 Vector3d2 = Vector3d.m_82546_(Vector3d1);
      float f4 = (float)(Vector3d2.m_82553_() + 1.0D);
      Vector3d2 = Vector3d2.m_82541_();
      float f5 = (float)Math.acos(Vector3d2.f_82480_);
      float f6 = (float)Math.atan2(Vector3d2.f_82481_, Vector3d2.f_82479_);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252961_(1.5707964F - f6));
      matrixStackIn.m_252781_(Axis.f_252529_.m_252961_(f5));
      int i = true;
      float f7 = f1 * 0.05F * -1.5F;
      float f8 = f * f;
      int j = 64 + (int)(f8 * 191.0F);
      int k = 32 + (int)(f8 * 191.0F);
      int l = 128 - (int)(f8 * 64.0F);
      float f9 = 0.2F;
      float f10 = 0.282F;
      float f11 = Mth.m_14089_(f7 + 2.3561945F) * 0.282F;
      float f12 = Mth.m_14031_(f7 + 2.3561945F) * 0.282F;
      float f13 = Mth.m_14089_(f7 + 0.7853982F) * 0.282F;
      float f14 = Mth.m_14031_(f7 + 0.7853982F) * 0.282F;
      float f15 = Mth.m_14089_(f7 + 3.926991F) * 0.282F;
      float f16 = Mth.m_14031_(f7 + 3.926991F) * 0.282F;
      float f17 = Mth.m_14089_(f7 + 5.4977875F) * 0.282F;
      float f18 = Mth.m_14031_(f7 + 5.4977875F) * 0.282F;
      float f19 = Mth.m_14089_(f7 + 3.1415927F) * 0.2F;
      float f20 = Mth.m_14031_(f7 + 3.1415927F) * 0.2F;
      float f21 = Mth.m_14089_(f7 + 0.0F) * 0.2F;
      float f22 = Mth.m_14031_(f7 + 0.0F) * 0.2F;
      float f23 = Mth.m_14089_(f7 + 1.5707964F) * 0.2F;
      float f24 = Mth.m_14031_(f7 + 1.5707964F) * 0.2F;
      float f25 = Mth.m_14089_(f7 + 4.712389F) * 0.2F;
      float f26 = Mth.m_14031_(f7 + 4.712389F) * 0.2F;
      float f27 = 0.0F;
      float f28 = 0.4999F;
      float f29 = -1.0F + f2;
      float f30 = f4 * 2.5F + f29;
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(TEXTURE_BEAM);
      Pose matrixstack$entry = matrixStackIn.m_85850_();
      Matrix4f matrix4f = matrixstack$entry.m_252922_();
      Matrix3f matrix3f = matrixstack$entry.m_252943_();
      vertex(ivertexbuilder, matrix4f, matrix3f, f19, f4, f20, j, k, l, 0.4999F, f30);
      vertex(ivertexbuilder, matrix4f, matrix3f, f19, 0.0F, f20, j, k, l, 0.4999F, f29);
      vertex(ivertexbuilder, matrix4f, matrix3f, f21, 0.0F, f22, j, k, l, 0.0F, f29);
      vertex(ivertexbuilder, matrix4f, matrix3f, f21, f4, f22, j, k, l, 0.0F, f30);
      vertex(ivertexbuilder, matrix4f, matrix3f, f23, f4, f24, j, k, l, 0.4999F, f30);
      vertex(ivertexbuilder, matrix4f, matrix3f, f23, 0.0F, f24, j, k, l, 0.4999F, f29);
      vertex(ivertexbuilder, matrix4f, matrix3f, f25, 0.0F, f26, j, k, l, 0.0F, f29);
      vertex(ivertexbuilder, matrix4f, matrix3f, f25, f4, f26, j, k, l, 0.0F, f30);
      float f31 = 0.0F;
      if (entityIn.f_19797_ % 2 == 0) {
         f31 = 0.5F;
      }

      vertex(ivertexbuilder, matrix4f, matrix3f, f11, f4, f12, j, k, l, 0.5F, f31 + 0.5F);
      vertex(ivertexbuilder, matrix4f, matrix3f, f13, f4, f14, j, k, l, 1.0F, f31 + 0.5F);
      vertex(ivertexbuilder, matrix4f, matrix3f, f17, f4, f18, j, k, l, 1.0F, f31);
      vertex(ivertexbuilder, matrix4f, matrix3f, f15, f4, f16, j, k, l, 0.5F, f31);
      matrixStackIn.m_85849_();
   }

   private static Vec3 getPosition(Entity LivingEntityIn, double p_177110_2_, float p_177110_4_) {
      double d0 = LivingEntityIn.f_19790_ + (LivingEntityIn.m_20185_() - LivingEntityIn.f_19790_) * (double)p_177110_4_;
      double d1 = p_177110_2_ + LivingEntityIn.f_19791_ + (LivingEntityIn.m_20186_() - LivingEntityIn.f_19791_) * (double)p_177110_4_;
      double d2 = LivingEntityIn.f_19792_ + (LivingEntityIn.m_20189_() - LivingEntityIn.f_19792_) * (double)p_177110_4_;
      return new Vec3(d0, d1, d2);
   }
}
