package com.github.alexthe666.iceandfire.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforge.network.PlayMessages.SpawnEntity;

public class EntityDragonPart extends EntityMutlipartPart {
   private EntityDragonBase dragon;

   public EntityDragonPart(EntityType<?> t, Level world) {
      super(t, world);
   }

   public EntityDragonPart(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.DRAGON_MULTIPART.get(), worldIn);
   }

   public EntityDragonPart(EntityType<?> type, EntityDragonBase dragon, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super(type, dragon, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
      this.dragon = dragon;
   }

   public EntityDragonPart(EntityDragonBase parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super((EntityType)IafEntityRegistry.DRAGON_MULTIPART.get(), parent, radius, angleYaw, offsetY, sizeX, sizeY, damageMultiplier);
      this.dragon = parent;
   }

   public void collideWithNearbyEntities() {
   }
}
