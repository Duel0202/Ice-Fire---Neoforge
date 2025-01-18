package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityAmphithereArrow extends AbstractArrow {
   public EntityAmphithereArrow(EntityType<? extends AbstractArrow> type, Level worldIn) {
      super(type, worldIn);
      this.m_36781_(2.5D);
   }

   public EntityAmphithereArrow(EntityType<? extends AbstractArrow> type, Level worldIn, double x, double y, double z) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_(2.5D);
   }

   public EntityAmphithereArrow(SpawnEntity spawnEntity, Level world) {
      this((EntityType)IafEntityRegistry.AMPHITHERE_ARROW.get(), world);
   }

   public EntityAmphithereArrow(EntityType type, LivingEntity shooter, Level worldIn) {
      super(type, shooter, worldIn);
      this.m_36781_(2.5D);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_8119_() {
      super.m_8119_();
      if ((this.f_19797_ == 1 || this.f_19797_ % 70 == 0) && !this.f_36703_ && !this.m_20096_()) {
         this.m_5496_(IafSoundRegistry.AMPHITHERE_GUST, 1.0F, 1.0F);
      }

      if (this.m_9236_().f_46443_ && !this.f_36703_) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         double d3 = 10.0D;
         double xRatio = this.m_20184_().f_82479_ * (double)this.m_20205_();
         double zRatio = this.m_20184_().f_82481_ * (double)this.m_20205_();
         this.m_9236_().m_7106_(ParticleTypes.f_123796_, this.m_20185_() + xRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d0 * 10.0D, this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - d1 * 10.0D, this.m_20189_() + zRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d2 * 10.0D, d0, d1, d2);
      }

   }

   protected void m_7761_(LivingEntity living) {
      living.f_19812_ = true;
      double xRatio = this.m_20184_().f_82479_;
      double zRatio = this.m_20184_().f_82481_;
      float strength = -1.4F;
      float f = Mth.m_14116_((float)(xRatio * xRatio + zRatio * zRatio));
      living.m_20256_(living.m_20184_().m_82542_(0.5D, 1.0D, 0.5D).m_82492_(xRatio / (double)f * (double)strength, 0.0D, zRatio / (double)f * (double)strength).m_82520_(0.0D, 0.6D, 0.0D));
      this.spawnExplosionParticle();
   }

   public void spawnExplosionParticle() {
      if (this.m_9236_().f_46443_) {
         for(int height = 0; height < 1 + this.f_19796_.m_188503_(2); ++height) {
            for(int i = 0; i < 20; ++i) {
               double d0 = this.f_19796_.m_188583_() * 0.02D;
               double d1 = this.f_19796_.m_188583_() * 0.02D;
               double d2 = this.f_19796_.m_188583_() * 0.02D;
               double d3 = 10.0D;
               double xRatio = this.m_20184_().f_82479_ * (double)this.m_20205_();
               double zRatio = this.m_20184_().f_82481_ * (double)this.m_20205_();
               this.m_9236_().m_7106_(ParticleTypes.f_123796_, this.m_20185_() + xRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d0 * d3, this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - d1 * d3, this.m_20189_() + zRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d2 * d3, d0, d1, d2);
            }
         }
      } else {
         this.m_9236_().m_7605_(this, (byte)20);
      }

   }

   public void m_7822_(byte id) {
      if (id == 20) {
         this.spawnExplosionParticle();
      } else {
         super.m_7822_(id);
      }

   }

   @NotNull
   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)IafItemRegistry.AMPHITHERE_ARROW.get());
   }
}
