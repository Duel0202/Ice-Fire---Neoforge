package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityPixieCharge extends Fireball {
   public int ticksInAir;
   private final float[] rgb;

   public EntityPixieCharge(EntityType<? extends Fireball> t, Level worldIn) {
      super(t, worldIn);
      this.rgb = EntityPixie.PARTICLE_RGB[this.f_19796_.m_188503_(EntityPixie.PARTICLE_RGB.length - 1)];
   }

   public EntityPixieCharge(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.PIXIE_CHARGE.get(), worldIn);
   }

   public EntityPixieCharge(EntityType<? extends Fireball> t, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ) {
      super(t, posX, posY, posZ, accelX, accelY, accelZ, worldIn);
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.07D;
      this.f_36814_ = accelY / d0 * 0.07D;
      this.f_36815_ = accelZ / d0 * 0.07D;
      this.rgb = EntityPixie.PARTICLE_RGB[this.f_19796_.m_188503_(EntityPixie.PARTICLE_RGB.length - 1)];
   }

   public EntityPixieCharge(EntityType<? extends Fireball> t, Level worldIn, Player shooter, double accelX, double accelY, double accelZ) {
      super(t, shooter, accelX, accelY, accelZ, worldIn);
      double d0 = Math.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
      this.f_36813_ = accelX / d0 * 0.07D;
      this.f_36814_ = accelY / d0 * 0.07D;
      this.f_36815_ = accelZ / d0 * 0.07D;
      this.rgb = EntityPixie.PARTICLE_RGB[this.f_19796_.m_188503_(EntityPixie.PARTICLE_RGB.length - 1)];
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   protected boolean m_5931_() {
      return false;
   }

   public boolean m_6087_() {
      return false;
   }

   public void m_8119_() {
      Entity shootingEntity = this.m_19749_();
      if (this.m_9236_().f_46443_) {
         for(int i = 0; i < 5; ++i) {
            IceAndFire.PROXY.spawnParticle(EnumParticles.If_Pixie, this.m_20185_() + this.f_19796_.m_188500_() * 0.15000000596046448D * (double)(this.f_19796_.m_188499_() ? -1 : 1), this.m_20186_() + this.f_19796_.m_188500_() * 0.15000000596046448D * (double)(this.f_19796_.m_188499_() ? -1 : 1), this.m_20189_() + this.f_19796_.m_188500_() * 0.15000000596046448D * (double)(this.f_19796_.m_188499_() ? -1 : 1), (double)this.rgb[0], (double)this.rgb[1], (double)this.rgb[2]);
         }
      }

      this.m_20095_();
      if (this.f_19797_ > 30) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      if (this.m_9236_().f_46443_ || (shootingEntity == null || shootingEntity.m_6084_()) && this.m_9236_().m_46805_(this.m_20183_())) {
         this.m_6075_();
         if (this.m_5931_()) {
            this.m_20254_(1);
         }

         ++this.ticksInAir;
         HitResult raytraceresult = ProjectileUtil.m_278158_(this, (x$0) -> {
            return this.m_5603_(x$0);
         });
         if (raytraceresult.m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, raytraceresult)) {
            this.m_6532_(raytraceresult);
         }

         Vec3 vector3d = this.m_20184_();
         double d0 = this.m_20185_() + vector3d.f_82479_;
         double d1 = this.m_20186_() + vector3d.f_82480_;
         double d2 = this.m_20189_() + vector3d.f_82481_;
         ProjectileUtil.m_37284_(this, 0.2F);
         float f = this.m_6884_();
         this.m_20256_(vector3d.m_82520_(this.f_36813_, this.f_36814_, this.f_36815_).m_82490_((double)f));
         this.f_36813_ *= 0.949999988079071D;
         this.f_36814_ *= 0.949999988079071D;
         this.f_36815_ *= 0.949999988079071D;
         this.m_5997_(this.f_36813_, this.f_36814_, this.f_36815_);
         ++this.ticksInAir;
         if (this.m_20069_()) {
            for(int i = 0; i < 4; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123795_, this.m_20185_() - this.m_20184_().f_82479_ * 0.25D, this.m_20186_() - this.m_20184_().f_82480_ * 0.25D, this.m_20189_() - this.m_20184_().f_82481_ * 0.25D, this.m_20184_().f_82479_, this.m_20184_().f_82480_, this.m_20184_().f_82481_);
            }
         }

         this.m_6034_(d0, d1, d2);
         this.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
      }

   }

   protected void m_6532_(@NotNull HitResult movingObject) {
      boolean flag = false;
      Entity shootingEntity = this.m_19749_();
      if (!this.m_9236_().f_46443_ && movingObject.m_6662_() == Type.ENTITY && !((EntityHitResult)movingObject).m_82443_().m_7306_(shootingEntity)) {
         Entity entity = ((EntityHitResult)movingObject).m_82443_();
         if (shootingEntity != null && shootingEntity.equals(entity)) {
            flag = true;
         } else {
            if (entity instanceof LivingEntity) {
               ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19620_, 100, 0));
               ((LivingEntity)entity).m_7292_(new MobEffectInstance(MobEffects.f_19619_, 100, 0));
               entity.m_6469_(this.m_9236_().m_269111_().m_269104_(shootingEntity, (Entity)null), 5.0F);
            }

            if (this.m_9236_().f_46443_) {
               for(int i = 0; i < 20; ++i) {
                  IceAndFire.PROXY.spawnParticle(EnumParticles.If_Pixie, this.m_20185_() + this.f_19796_.m_188500_() * 1.0D * (double)(this.f_19796_.m_188499_() ? -1 : 1), this.m_20186_() + this.f_19796_.m_188500_() * 1.0D * (double)(this.f_19796_.m_188499_() ? -1 : 1), this.m_20189_() + this.f_19796_.m_188500_() * 1.0D * (double)(this.f_19796_.m_188499_() ? -1 : 1), (double)this.rgb[0], (double)this.rgb[1], (double)this.rgb[2]);
               }
            }

            if ((shootingEntity == null || !(shootingEntity instanceof Player) || !((Player)shootingEntity).m_7500_()) && this.f_19796_.m_188503_(3) == 0) {
               this.m_5552_(new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get(), 1), 0.45F);
            }
         }

         if (!flag && this.f_19797_ > 4) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }

   }
}
