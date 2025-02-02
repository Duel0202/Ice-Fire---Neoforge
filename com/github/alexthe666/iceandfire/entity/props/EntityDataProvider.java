package com.github.alexthe666.iceandfire.entity.props;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ICapabilitySerializable;
import net.neoforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityDataProvider implements ICapabilitySerializable<CompoundTag> {
   public static final Map<Integer, LazyOptional<EntityData>> SERVER_CACHE = new HashMap();
   public static final Map<Integer, LazyOptional<EntityData>> CLIENT_CACHE = new HashMap();
   private final EntityData data = new EntityData();
   private final LazyOptional<EntityData> instance = LazyOptional.of(() -> {
      return this.data;
   });

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction side) {
      return capability == CapabilityHandler.ENTITY_DATA_CAPABILITY ? this.instance.cast() : LazyOptional.empty();
   }

   public void deserializeNBT(CompoundTag tag) {
      ((EntityData)this.instance.orElseThrow(() -> {
         return new IllegalArgumentException("Capability instance was not present");
      })).deserialize(tag);
   }

   public CompoundTag serializeNBT() {
      return ((EntityData)this.instance.orElseThrow(() -> {
         return new IllegalArgumentException("Capability instance was not present");
      })).serialize();
   }

   public static LazyOptional<EntityData> getCapability(Entity entity) {
      if (entity instanceof LivingEntity) {
         int key = entity.m_19879_();
         Map<Integer, LazyOptional<EntityData>> sidedCache = entity.m_9236_().m_5776_() ? CLIENT_CACHE : SERVER_CACHE;
         LazyOptional<EntityData> capability = (LazyOptional)sidedCache.get(key);
         if (capability == null) {
            capability = entity.getCapability(CapabilityHandler.ENTITY_DATA_CAPABILITY);
            capability.addListener((ignored) -> {
               sidedCache.remove(key);
            });
            if (capability.isPresent()) {
               sidedCache.put(key, capability);
            }
         }

         return capability;
      } else {
         return LazyOptional.empty();
      }
   }

   public static void removeCachedEntry(Entity entity) {
      if (entity instanceof LivingEntity) {
         int key = entity.m_19879_();
         if (entity.m_9236_().m_5776_()) {
            if (entity == CapabilityHandler.getLocalPlayer()) {
               CLIENT_CACHE.clear();
            } else {
               CLIENT_CACHE.remove(key);
            }
         } else {
            SERVER_CACHE.remove(key);
         }
      }

   }
}
