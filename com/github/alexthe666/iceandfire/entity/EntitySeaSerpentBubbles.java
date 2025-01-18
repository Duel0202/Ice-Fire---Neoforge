package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.IDragonProjectile;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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

public class EntitySeaSerpentBubbles extends Fireball implements IDragonProjectile {
   public EntitySeaSerpentBubbles(EntityType<? extends Fireball> t, Level worldIn) {
      super(t, worldIn);
   }

   public EntitySeaSerpentBubbles(EntityType<? extends Fireball> t, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ) {
      super(t, posX, posY, posZ, accelX, accelY, accelZ, worldIn);
   }

   public EntitySeaSerpentBubbles(SpawnEntity spawnEntity, Level world) {
      this((EntityType)IafEntityRegistry.SEA_SERPENT_BUBBLES.get(), world);
   }

   public EntitySeaSerpentBubbles(EntityType<? extends Fireball> t, Level worldIn, EntitySeaSerpent shooter, double accelX, double accelY, double accelZ) {
      super(t, shooter, accelX, accelY, accelZ, worldIn);
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.1D;
      this.f_36814_ = accelY / d0 * 0.1D;
      this.f_36815_ = accelZ / d0 * 0.1D;
   }

   public boolean m_6087_() {
      return false;
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected boolean m_5931_() {
      return false;
   }

   public void m_8119_() {
      Entity shootingEntity = this.m_19749_();
      if (this.f_19797_ > 400) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      this.autoTarget();
      this.f_36813_ *= 0.949999988079071D;
      this.f_36814_ *= 0.949999988079071D;
      this.f_36815_ *= 0.949999988079071D;
      this.m_5997_(this.f_36813_, this.f_36814_, this.f_36815_);
      if (this.m_9236_().f_46443_ || (shootingEntity == null || !shootingEntity.m_6084_()) && this.m_9236_().m_46805_(this.m_20183_())) {
         this.m_6075_();
         HitResult raytraceresult = ProjectileUtil.m_278158_(this, this::m_5603_);
         if (raytraceresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.m_6532_(raytraceresult);
         }

         Vec3 Vector3d = this.m_20184_();
         double d0 = this.m_20185_() + Vector3d.f_82479_;
         double d1 = this.m_20186_() + Vector3d.f_82480_;
         double d2 = this.m_20189_() + Vector3d.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.m_6884_();
         if (this.m_9236_().f_46443_) {
            for(int i = 0; i < 3; ++i) {
               IceAndFire.PROXY.spawnParticle(EnumParticles.Serpent_Bubble, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_()) - (double)this.m_20205_() * 0.5D, this.m_20186_() - 0.5D, this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_()) - (double)this.m_20205_() * 0.5D, 0.0D, 0.0D, 0.0D);
            }
         }

         this.m_20256_(Vector3d.m_82520_(this.f_36813_, this.f_36814_, this.f_36815_).m_82490_((double)f));
         this.m_6034_(d0, d1, d2);
         this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

      this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      if (this.f_19797_ > 20 && !this.m_20070_()) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

   }

   protected boolean m_5603_(@NotNull Entity entityIn) {
      return super.m_5603_(entityIn) && !(entityIn instanceof EntityMutlipartPart) && !(entityIn instanceof EntitySeaSerpentBubbles);
   }

   public void autoTarget() {
      if (this.m_9236_().f_46443_) {
         Entity shootingEntity = this.m_19749_();
         if (shootingEntity instanceof EntitySeaSerpent && ((EntitySeaSerpent)shootingEntity).m_5448_() != null) {
            Entity target = ((EntitySeaSerpent)shootingEntity).m_5448_();
            double d2 = target.m_20185_() - this.m_20185_();
            double d3 = target.m_20186_() - this.m_20186_();
            double d4 = target.m_20189_() - this.m_20189_();
            double d0 = Math.sqrt(d2 * d2 + d3 * d3 + d4 * d4);
            this.f_36813_ = d2 / d0 * 0.1D;
            this.f_36814_ = d3 / d0 * 0.1D;
            this.f_36815_ = d4 / d0 * 0.1D;
         } else if (this.f_19797_ > 20) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }

   }

   public boolean handleWaterMovement() {
      return true;
   }

   @NotNull
   protected ParticleOptions m_5967_() {
      return ParticleTypes.f_123795_;
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      return false;
   }

   public float m_6143_() {
      return 0.0F;
   }

   protected void m_6532_(@NotNull HitResult movingObject) {
      boolean flag = this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_);
      if (!this.m_9236_().f_46443_ && movingObject.m_6662_() == Type.ENTITY) {
         Entity entity = ((EntityHitResult)movingObject).m_82443_();
         if (entity != null && entity instanceof EntitySlowPart) {
            return;
         }

         Entity shootingEntity = this.m_19749_();
         if (shootingEntity != null && shootingEntity instanceof EntitySeaSerpent) {
            EntitySeaSerpent dragon = (EntitySeaSerpent)shootingEntity;
            if (dragon.m_7307_(entity) || dragon.m_7306_(entity)) {
               return;
            }

            entity.m_6469_(this.m_9236_().m_269111_().m_269333_(dragon), 6.0F);
         }
      }

   }
}
