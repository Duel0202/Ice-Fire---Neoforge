package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.neoforge.network.PlayMessages.SpawnEntity;

public class EntityDragonFireCharge extends EntityDragonCharge {
   public EntityDragonFireCharge(EntityType<? extends Fireball> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityDragonFireCharge(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.FIRE_DRAGON_CHARGE.get(), worldIn);
   }

   public EntityDragonFireCharge(EntityType<? extends Fireball> type, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ) {
      super(type, worldIn, posX, posY, posZ, accelX, accelY, accelZ);
   }

   public EntityDragonFireCharge(EntityType type, Level worldIn, EntityDragonBase shooter, double accelX, double accelY, double accelZ) {
      super(type, worldIn, shooter, accelX, accelY, accelZ);
   }

   public boolean m_6087_() {
      return true;
   }

   public void m_8119_() {
      for(int i = 0; i < 4; ++i) {
         this.m_9236_().m_7106_(ParticleTypes.f_123744_, this.m_20185_() + (this.f_19796_.m_188500_() - 0.5D) * (double)this.m_20205_(), this.m_20186_() + (this.f_19796_.m_188500_() - 0.5D) * (double)this.m_20205_(), this.m_20189_() + (this.f_19796_.m_188500_() - 0.5D) * (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
      }

      if (this.m_20069_()) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      if (this.m_5931_()) {
         this.m_20254_(1);
      }

      super.m_8119_();
   }

   public DamageSource causeDamage(@Nullable Entity cause) {
      return IafDamageRegistry.causeDragonFireDamage(cause);
   }

   public void destroyArea(Level world, BlockPos center, EntityDragonBase destroyer) {
      IafDragonDestructionManager.destroyAreaCharge(world, center, destroyer);
   }

   public float getDamage() {
      return (float)IafConfig.dragonAttackDamageFire;
   }
}
