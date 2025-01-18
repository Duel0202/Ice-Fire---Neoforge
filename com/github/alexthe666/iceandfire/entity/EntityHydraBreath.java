package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.IDragonProjectile;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityHydraBreath extends Fireball implements IDragonProjectile {
   public EntityHydraBreath(EntityType<? extends Fireball> t, Level worldIn) {
      super(t, worldIn);
   }

   public EntityHydraBreath(EntityType<? extends Fireball> t, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ) {
      super(t, posX, posY, posZ, accelX, accelY, accelZ, worldIn);
   }

   public EntityHydraBreath(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.HYDRA_BREATH.get(), worldIn);
   }

   public EntityHydraBreath(EntityType<? extends Fireball> t, Level worldIn, EntityHydra shooter, double accelX, double accelY, double accelZ) {
      super(t, shooter, accelX, accelY, accelZ, worldIn);
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.02D;
      this.f_36814_ = accelY / d0 * 0.02D;
      this.f_36815_ = accelZ / d0 * 0.02D;
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected boolean m_5931_() {
      return false;
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      return false;
   }

   public float m_6143_() {
      return 0.0F;
   }

   public boolean m_6087_() {
      return false;
   }

   public void m_8119_() {
      this.m_20095_();
      if (this.f_19797_ > 30) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      Entity shootingEntity = this.m_19749_();
      if (this.m_9236_().f_46443_ || (shootingEntity == null || shootingEntity.m_6084_()) && this.m_9236_().m_46805_(this.m_20183_())) {
         this.m_6075_();
         if (this.m_5931_()) {
            this.m_20254_(1);
         }

         HitResult raytraceresult = ProjectileUtil.m_278158_(this, (x$0) -> {
            return this.m_5603_(x$0);
         });
         if (raytraceresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.m_6532_(raytraceresult);
         }

         Vec3 Vector3d = this.m_20184_();
         double d0 = this.m_20185_() + Vector3d.f_82479_;
         double d1 = this.m_20186_() + Vector3d.f_82480_;
         double d2 = this.m_20189_() + Vector3d.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.m_6884_();
         int i;
         if (this.m_9236_().f_46443_) {
            for(i = 0; i < 15; ++i) {
               IceAndFire.PROXY.spawnParticle(EnumParticles.Hydra, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_()) - (double)this.m_20205_() * 0.5D, this.m_20186_() - 0.5D, this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_()) - (double)this.m_20205_() * 0.5D, 0.1D, 1.0D, 0.1D);
            }
         }

         this.m_20256_(Vector3d.m_82520_(this.f_36813_, this.f_36814_, this.f_36815_).m_82490_((double)f));
         this.f_36813_ *= 0.949999988079071D;
         this.f_36814_ *= 0.949999988079071D;
         this.f_36815_ *= 0.949999988079071D;
         this.m_5997_(this.f_36813_, this.f_36814_, this.f_36815_);
         if (this.m_20069_()) {
            for(i = 0; i < 4; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123795_, this.m_20185_() - this.m_20184_().f_82479_ * 0.25D, this.m_20186_() - this.m_20184_().f_82480_ * 0.25D, this.m_20189_() - this.m_20184_().f_82481_ * 0.25D, this.m_20184_().f_82479_, this.m_20184_().f_82480_, this.m_20184_().f_82481_);
            }
         }

         this.m_6034_(d0, d1, d2);
         this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }

   public boolean handleWaterMovement() {
      return true;
   }

   protected void m_6532_(@NotNull HitResult movingObject) {
      this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_);
      Entity shootingEntity = this.m_19749_();
      if (!this.m_9236_().f_46443_ && movingObject.m_6662_() == Type.ENTITY) {
         Entity entity = ((EntityHitResult)movingObject).m_82443_();
         if (entity != null && entity instanceof EntityHydraHead) {
            return;
         }

         if (shootingEntity != null && shootingEntity instanceof EntityHydra) {
            EntityHydra dragon = (EntityHydra)shootingEntity;
            if (dragon.m_7307_(entity) || dragon.m_7306_(entity)) {
               return;
            }

            entity.m_6469_(this.m_9236_().m_269111_().m_269333_(dragon), 2.0F);
            if (entity instanceof LivingEntity) {
               ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19614_, 60, 0));
            }
         }
      }

   }
}
