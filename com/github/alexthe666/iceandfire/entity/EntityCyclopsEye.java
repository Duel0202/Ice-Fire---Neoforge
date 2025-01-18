package com.github.alexthe666.iceandfire.entity;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforge.network.PlayMessages.SpawnEntity;

public class EntityCyclopsEye extends EntityMutlipartPart {
   public EntityCyclopsEye(EntityType<?> t, Level world) {
      super(t, world);
   }

   public EntityCyclopsEye(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.CYCLOPS_MULTIPART.get(), worldIn);
   }

   public EntityCyclopsEye(LivingEntity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super((EntityType)IafEntityRegistry.CYCLOPS_MULTIPART.get(), parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
   }

   public boolean m_6469_(DamageSource source, float damage) {
      Entity parent = this.getParent();
      if (parent instanceof EntityCyclops && source.m_276093_(DamageTypes.f_268739_)) {
         ((EntityCyclops)parent).onHitEye(source, damage);
         return true;
      } else {
         return parent != null && parent.m_6469_(source, damage);
      }
   }
}
