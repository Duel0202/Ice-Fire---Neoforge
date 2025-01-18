package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IDragonProjectile;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class EntityDragonCharge extends Fireball implements IDragonProjectile {
   public EntityDragonCharge(EntityType<? extends Fireball> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityDragonCharge(EntityType<? extends Fireball> type, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ) {
      super(type, posX, posY, posZ, accelX, accelY, accelZ, worldIn);
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.07D;
      this.f_36814_ = accelY / d0 * 0.07D;
      this.f_36815_ = accelZ / d0 * 0.07D;
   }

   public EntityDragonCharge(EntityType<? extends Fireball> type, Level worldIn, EntityDragonBase shooter, double accelX, double accelY, double accelZ) {
      super(type, shooter, accelX, accelY, accelZ, worldIn);
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.07D;
      this.f_36814_ = accelY / d0 * 0.07D;
      this.f_36815_ = accelZ / d0 * 0.07D;
   }

   public void m_8119_() {
      Entity shootingEntity = this.m_19749_();
      if (this.m_9236_().f_46443_ || (shootingEntity == null || shootingEntity.m_6084_()) && this.m_9236_().m_46805_(this.m_20183_())) {
         super.m_6075_();
         HitResult raytraceresult = ProjectileUtil.m_278158_(this, this::canHitMob);
         if (raytraceresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.m_6532_(raytraceresult);
         }

         this.m_20101_();
         Vec3 vector3d = this.m_20184_();
         double d0 = this.m_20185_() + vector3d.f_82479_;
         double d1 = this.m_20186_() + vector3d.f_82480_;
         double d2 = this.m_20189_() + vector3d.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.m_6884_();
         if (this.m_20069_()) {
            for(int i = 0; i < 4; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123795_, this.m_20185_() - this.m_20184_().f_82479_ * 0.25D, this.m_20186_() - this.m_20184_().f_82480_ * 0.25D, this.m_20189_() - this.m_20184_().f_82481_ * 0.25D, this.m_20184_().f_82479_, this.m_20184_().f_82480_, this.m_20184_().f_82481_);
            }

            f = 0.8F;
         }

         this.m_20256_(vector3d.m_82520_(this.f_36813_, this.f_36814_, this.f_36815_).m_82490_((double)f));
         this.m_9236_().m_7106_(this.m_5967_(), this.m_20185_(), this.m_20186_() + 0.5D, this.m_20189_(), 0.0D, 0.0D, 0.0D);
         this.m_6034_(d0, d1, d2);
      } else {
         this.m_142687_(RemovalReason.DISCARDED);
      }

   }

   protected void m_6532_(@NotNull HitResult movingObject) {
      Entity shootingEntity = this.m_19749_();
      if (!this.m_9236_().f_46443_) {
         if (movingObject.m_6662_() == Type.ENTITY) {
            Entity entity = ((EntityHitResult)movingObject).m_82443_();
            if (entity instanceof IDragonProjectile) {
               return;
            }

            EntityDragonBase dragon;
            if (shootingEntity != null && shootingEntity instanceof EntityDragonBase) {
               dragon = (EntityDragonBase)shootingEntity;
               if (dragon.m_7307_(entity) || dragon.m_7306_(entity) || dragon.isPart(entity)) {
                  return;
               }
            }

            if (entity == null || !(entity instanceof IDragonProjectile) && entity != shootingEntity && shootingEntity instanceof EntityDragonBase) {
               dragon = (EntityDragonBase)shootingEntity;
               if (shootingEntity != null && (entity == shootingEntity || entity instanceof TamableAnimal && ((EntityDragonBase)shootingEntity).m_21830_(((EntityDragonBase)shootingEntity).m_269323_()))) {
                  return;
               }

               if (dragon != null) {
                  dragon.randomizeAttacks();
               }

               this.m_142687_(RemovalReason.DISCARDED);
            }

            if (entity != null && !(entity instanceof IDragonProjectile) && !entity.m_7306_(shootingEntity)) {
               if (shootingEntity != null && (entity.m_7306_(shootingEntity) || shootingEntity instanceof EntityDragonBase && entity instanceof TamableAnimal && ((EntityDragonBase)shootingEntity).m_269323_() == ((TamableAnimal)entity).m_269323_())) {
                  return;
               }

               if (shootingEntity instanceof EntityDragonBase) {
                  float damageAmount = this.getDamage() * (float)((EntityDragonBase)shootingEntity).getDragonStage();
                  EntityDragonBase shootingDragon = (EntityDragonBase)shootingEntity;
                  Entity cause = shootingDragon.getRidingPlayer() != null ? shootingDragon.getRidingPlayer() : shootingDragon;
                  DamageSource source = this.causeDamage((Entity)cause);
                  entity.m_6469_(source, damageAmount);
                  if (entity instanceof LivingEntity && ((LivingEntity)entity).m_21223_() == 0.0F) {
                     ((EntityDragonBase)shootingEntity).randomizeAttacks();
                  }
               }

               if (shootingEntity instanceof LivingEntity) {
                  this.m_19970_((LivingEntity)shootingEntity, entity);
               }

               this.m_142687_(RemovalReason.DISCARDED);
            }
         }

         if (movingObject.m_6662_() != Type.MISS) {
            if (shootingEntity instanceof EntityDragonBase && DragonUtils.canGrief((EntityDragonBase)shootingEntity)) {
               this.destroyArea(this.m_9236_(), BlockPos.m_274561_(this.m_20185_(), this.m_20186_(), this.m_20189_()), (EntityDragonBase)shootingEntity);
            }

            this.m_142687_(RemovalReason.DISCARDED);
         }
      }

   }

   public abstract DamageSource causeDamage(@Nullable Entity var1);

   public abstract void destroyArea(Level var1, BlockPos var2, EntityDragonBase var3);

   public abstract float getDamage();

   public boolean m_6087_() {
      return false;
   }

   protected boolean canHitMob(Entity hitMob) {
      Entity shooter = this.m_19749_();
      return hitMob != this && super.m_5603_(hitMob) && shooter != null && !hitMob.m_7307_(shooter) && !(hitMob instanceof EntityDragonPart);
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      return false;
   }

   public float m_6143_() {
      return 0.0F;
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }
}
