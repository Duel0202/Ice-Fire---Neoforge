package com.github.alexthe666.iceandfire.client.particle;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ParticleDreadTorch extends TextureSheetParticle {
   private static final ResourceLocation SNOWFLAKE = new ResourceLocation("iceandfire:textures/particles/snowflake_0.png");
   private static final ResourceLocation SNOWFLAKE_BIG = new ResourceLocation("iceandfire:textures/particles/snowflake_1.png");
   private final boolean big;

   public ParticleDreadTorch(ClientLevel world, double x, double y, double z, double motX, double motY, double motZ, float size) {
      super(world, x, y, z, motX, motY, motZ);
      this.m_107264_(x, y, z);
      this.f_107216_ += 0.01D;
      this.big = this.f_107223_.m_188499_();
   }

   public void m_5744_(@NotNull VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Vec3 inerp = renderInfo.m_90583_();
      this.f_107663_ = 0.125F * (float)(this.f_107225_ - this.f_107224_);
      this.f_107663_ *= 0.09F;
      this.f_107215_ *= 0.75D;
      this.f_107216_ *= 0.75D;
      this.f_107217_ *= 0.75D;
      if (this.f_107224_ > this.m_107273_()) {
         this.m_107274_();
      }

      Vec3 Vector3d = renderInfo.m_90583_();
      float f = (float)(Mth.m_14139_((double)partialTicks, this.f_107209_, this.f_107212_) - Vector3d.m_7096_());
      float f1 = (float)(Mth.m_14139_((double)partialTicks, this.f_107210_, this.f_107213_) - Vector3d.m_7098_());
      float f2 = (float)(Mth.m_14139_((double)partialTicks, this.f_107211_, this.f_107214_) - Vector3d.m_7094_());
      Quaternionf quaternion;
      if (this.f_107231_ == 0.0F) {
         quaternion = renderInfo.m_253121_();
      } else {
         quaternion = new Quaternionf(renderInfo.m_253121_());
         float f3 = Mth.m_14179_(partialTicks, this.f_107204_, this.f_107231_);
         quaternion.mul(Axis.f_252403_.m_252961_(f3));
      }

      Vector3f vector3f1 = new Vector3f(-1.0F, -1.0F, 0.0F);
      quaternion.transform(vector3f1);
      Vector3f[] avector3f = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
      float f4 = this.m_5902_(partialTicks);

      for(int i = 0; i < 4; ++i) {
         Vector3f vector3f = avector3f[i];
         vector3f = quaternion.transform(vector3f);
         vector3f.mul(f4);
         vector3f.add(f, f1, f2);
      }

      float f7 = 0.0F;
      float f8 = 1.0F;
      float f5 = 0.0F;
      float f6 = 1.0F;
      RenderSystem.setShaderTexture(0, this.big ? SNOWFLAKE_BIG : SNOWFLAKE);
      int j = this.m_6355_(partialTicks);
      Tesselator tessellator = Tesselator.m_85913_();
      BufferBuilder vertexbuffer = tessellator.m_85915_();
      vertexbuffer.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85813_);
      vertexbuffer.m_5483_((double)avector3f[0].x(), (double)avector3f[0].y(), (double)avector3f[0].z()).m_7421_(f8, f6).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      vertexbuffer.m_5483_((double)avector3f[1].x(), (double)avector3f[1].y(), (double)avector3f[1].z()).m_7421_(f8, f5).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      vertexbuffer.m_5483_((double)avector3f[2].x(), (double)avector3f[2].y(), (double)avector3f[2].z()).m_7421_(f7, f5).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      vertexbuffer.m_5483_((double)avector3f[3].x(), (double)avector3f[3].y(), (double)avector3f[3].z()).m_7421_(f7, f6).m_85950_(this.f_107227_, this.f_107228_, this.f_107229_, this.f_107230_).m_85969_(j).m_5752_();
      Tesselator.m_85913_().m_85914_();
   }

   public int m_6355_(float partialTick) {
      return 240;
   }

   public int getFXLayer() {
      return 3;
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }
}
