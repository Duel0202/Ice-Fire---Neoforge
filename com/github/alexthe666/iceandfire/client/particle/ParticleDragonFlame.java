package com.github.alexthe666.iceandfire.client.particle;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import com.mojang.math.Axis;
import javax.annotation.Nullable;
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

public class ParticleDragonFlame extends TextureSheetParticle {
   private static final ResourceLocation DRAGONFLAME = new ResourceLocation("iceandfire:textures/particles/dragon_flame.png");
   private final float dragonSize;
   private final double initialX;
   private final double initialY;
   private final double initialZ;
   private double targetX;
   private double targetY;
   private double targetZ;
   private final int touchedTime;
   private final float speedBonus;
   @Nullable
   private EntityDragonBase dragon;

   public ParticleDragonFlame(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float dragonSize) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
      this.touchedTime = 0;
      this.initialX = xCoordIn;
      this.initialY = yCoordIn;
      this.initialZ = zCoordIn;
      this.targetX = xCoordIn + (double)((this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 1.75F * dragonSize);
      this.targetY = yCoordIn + (double)((this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 1.75F * dragonSize);
      this.targetZ = zCoordIn + (double)((this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 1.75F * dragonSize);
      this.m_107264_(this.f_107212_, this.f_107213_, this.f_107214_);
      this.dragonSize = dragonSize;
      this.speedBonus = this.f_107223_.m_188501_() * 0.015F;
   }

   public ParticleDragonFlame(ClientLevel world, double x, double y, double z, double motX, double motY, double motZ, EntityDragonBase entityDragonBase, int startingAge) {
      this(world, x, y, z, motX, motY, motZ, Mth.m_14036_(entityDragonBase.getRenderSize() * 0.08F, 0.55F, 3.0F));
      this.dragon = entityDragonBase;
      this.targetX = this.dragon.burnParticleX + (double)(this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 3.5D;
      this.targetY = this.dragon.burnParticleY + (double)(this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 3.5D;
      this.targetZ = this.dragon.burnParticleZ + (double)(this.f_107223_.m_188501_() - this.f_107223_.m_188501_()) * 3.5D;
      this.f_107212_ = x;
      this.f_107213_ = y;
      this.f_107214_ = z;
      this.f_107224_ = startingAge;
   }

   public int m_107273_() {
      return this.dragon == null ? 10 : 30;
   }

   public void m_5744_(@NotNull VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      Vec3 inerp = renderInfo.m_90583_();
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
      RenderSystem.setShaderTexture(0, DRAGONFLAME);
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

   public void m_5989_() {
      super.m_5989_();
      if (this.dragon == null) {
         float distX = (float)(this.initialX - this.f_107212_);
         float distZ = (float)(this.initialZ - this.f_107214_);
         this.f_107215_ += (double)(distX * -0.01F * this.dragonSize * this.f_107223_.m_188501_());
         this.f_107217_ += (double)(distZ * -0.01F * this.dragonSize * this.f_107223_.m_188501_());
         this.f_107216_ += (double)(0.015F * this.f_107223_.m_188501_());
      } else {
         double d2 = this.targetX - this.initialX;
         double d3 = this.targetY - this.initialY;
         double d4 = this.targetZ - this.initialZ;
         Math.sqrt(d2 * d2 + d3 * d3 + d4 * d4);
         float speed = 0.015F + this.speedBonus;
         this.f_107215_ += d2 * (double)speed;
         this.f_107216_ += d3 * (double)speed;
         this.f_107217_ += d4 * (double)speed;
      }

   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }
}
