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

public class ParticlePixieDust extends TextureSheetParticle {
   private static final ResourceLocation PIXIE_DUST = new ResourceLocation("iceandfire:textures/particles/pixie_dust.png");
   float reddustParticleScale;

   public ParticlePixieDust(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46349_8_, float p_i46349_9_, float p_i46349_10_) {
      this(worldIn, xCoordIn, yCoordIn, zCoordIn, 1.0F, p_i46349_8_, p_i46349_9_, p_i46349_10_);
   }

   protected ParticlePixieDust(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float scale, float red, float green, float blue) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
      this.f_107215_ *= 0.10000000149011612D;
      this.f_107216_ *= 0.10000000149011612D;
      this.f_107217_ *= 0.10000000149011612D;
      float f = (float)Math.random() * 0.4F + 0.6F;
      this.f_107227_ = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * red * f;
      this.f_107228_ = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * green * f;
      this.f_107229_ = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * blue * f;
      this.f_107663_ *= scale;
      this.reddustParticleScale = this.f_107663_;
      this.f_107225_ = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
      this.f_107225_ = (int)((float)this.f_107225_ * scale);
   }

   public void m_5744_(@NotNull VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Vec3 inerp = renderInfo.m_90583_();
      float scaley = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_ * 32.0F;
      scaley = Mth.m_14036_(scaley, 0.0F, 1.0F);
      this.f_107663_ = this.reddustParticleScale * scaley;
      float width = this.f_107663_ * 0.09F;
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
      RenderSystem.setShaderTexture(0, PIXIE_DUST);
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

   public void onUpdate() {
      this.f_107209_ = this.f_107212_;
      this.f_107210_ = this.f_107213_;
      this.f_107211_ = this.f_107214_;
      if (this.f_107224_++ >= this.f_107225_) {
         this.m_107274_();
      }

      this.m_6257_(this.f_107215_, this.f_107216_, this.f_107217_);
      if (this.f_107213_ == this.f_107210_) {
         this.f_107215_ *= 1.1D;
         this.f_107217_ *= 1.1D;
      }

      this.f_107215_ *= 0.9599999785423279D;
      this.f_107216_ *= 0.9599999785423279D;
      this.f_107217_ *= 0.9599999785423279D;
      if (this.f_107218_) {
         this.f_107215_ *= 0.699999988079071D;
         this.f_107217_ *= 0.699999988079071D;
      }

   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }
}
