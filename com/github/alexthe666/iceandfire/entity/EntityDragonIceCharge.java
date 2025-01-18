package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.Level;
import net.neoforge.network.PlayMessages.SpawnEntity;

public class EntityDragonIceCharge extends EntityDragonCharge {
   public EntityDragonIceCharge(EntityType<? extends Fireball> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityDragonIceCharge(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.ICE_DRAGON_CHARGE.get(), worldIn);
   }

   public EntityDragonIceCharge(EntityType<? extends Fireball> type, Level worldIn, double posX, double posY, double posZ, double accelX, double accelY, double accelZ) {
      super(type, worldIn, posX, posY, posZ, accelX, accelY, accelZ);
   }

   public EntityDragonIceCharge(EntityType<? extends Fireball> type, Level worldIn, EntityDragonBase shooter, double accelX, double accelY, double accelZ) {
      super(type, worldIn, shooter, accelX, accelY, accelZ);
   }

   public void m_8119_() {
      if (this.m_9236_().f_46443_) {
         for(int i = 0; i < 10; ++i) {
            IceAndFire.PROXY.spawnParticle(EnumParticles.DragonIce, this.m_20185_() + this.f_19796_.m_188500_() * 1.0D * (double)(this.f_19796_.m_188499_() ? -1 : 1), this.m_20186_() + this.f_19796_.m_188500_() * 1.0D * (double)(this.f_19796_.m_188499_() ? -1 : 1), this.m_20189_() + this.f_19796_.m_188500_() * 1.0D * (double)(this.f_19796_.m_188499_() ? -1 : 1), 0.0D, 0.0D, 0.0D);
         }
      }

      super.m_8119_();
   }

   public DamageSource causeDamage(@Nullable Entity cause) {
      return IafDamageRegistry.causeDragonIceDamage(cause);
   }

   public void destroyArea(Level world, BlockPos center, EntityDragonBase destroyer) {
      IafDragonDestructionManager.destroyAreaCharge(world, center, destroyer);
   }

   public float getDamage() {
      return (float)IafConfig.dragonAttackDamageIce;
   }

   protected boolean m_5931_() {
      return false;
   }
}
