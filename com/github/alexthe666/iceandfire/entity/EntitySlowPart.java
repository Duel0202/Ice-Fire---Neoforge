package com.github.alexthe666.iceandfire.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforge.network.PlayMessages.SpawnEntity;

public class EntitySlowPart extends EntityMutlipartPart {
   public EntitySlowPart(EntityType<?> t, Level world) {
      super(t, world);
   }

   public EntitySlowPart(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.SLOW_MULTIPART.get(), worldIn);
   }

   public EntitySlowPart(EntityType<?> t, LivingEntity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super(t, parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
   }

   public EntitySlowPart(Entity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super((EntityType)IafEntityRegistry.SLOW_MULTIPART.get(), parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
   }

   protected boolean isSlowFollow() {
      return true;
   }
}
