package com.github.alexthe666.iceandfire.entity.props;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class MiscData {
   public int loveTicks;
   public int lungeTicks;
   public boolean hasDismounted;
   @Nullable
   public List<LivingEntity> targetedByScepter;
   @Nullable
   private List<Integer> targetedByScepterIds;
   private boolean isInitialized;
   private boolean triggerClientUpdate;

   public void tickMisc(LivingEntity entity) {
      if (!this.isInitialized) {
         this.initialize(entity.m_9236_());
      }

      if (this.loveTicks > 0) {
         --this.loveTicks;
         if (this.loveTicks == 0) {
            this.triggerClientUpdate = true;
            return;
         }

         if (entity instanceof Mob) {
            Mob mob = (Mob)entity;
            mob.m_6598_((Player)null);
            mob.m_6703_((LivingEntity)null);
            mob.m_6710_((LivingEntity)null);
            mob.m_21561_(false);
         }

         this.createLoveParticles(entity);
      }

   }

   public List<LivingEntity> getTargetedByScepter() {
      return (List)Objects.requireNonNullElse(this.targetedByScepter, Collections.emptyList());
   }

   public void addScepterTarget(LivingEntity target) {
      if (this.targetedByScepter == null) {
         this.targetedByScepter = new ArrayList();
      } else if (this.targetedByScepter.contains(target)) {
         return;
      }

      this.targetedByScepter.add(target);
      this.triggerClientUpdate = true;
   }

   public void removeScepterTarget(LivingEntity target) {
      if (this.targetedByScepter != null) {
         this.targetedByScepter.remove(target);
         this.triggerClientUpdate = true;
      }
   }

   public void setLoveTicks(int loveTicks) {
      this.loveTicks = loveTicks;
      this.triggerClientUpdate = true;
   }

   public void setLungeTicks(int lungeTicks) {
      this.lungeTicks = lungeTicks;
      this.triggerClientUpdate = true;
   }

   public void setDismounted(boolean hasDismounted) {
      this.hasDismounted = hasDismounted;
      this.triggerClientUpdate = true;
   }

   public void serialize(CompoundTag tag) {
      CompoundTag miscData = new CompoundTag();
      miscData.m_128405_("loveTicks", this.loveTicks);
      miscData.m_128405_("lungeTicks", this.lungeTicks);
      miscData.m_128379_("hasDismounted", this.hasDismounted);
      if (this.targetedByScepter != null) {
         int[] ids = new int[this.targetedByScepter.size()];

         for(int i = 0; i < this.targetedByScepter.size(); ++i) {
            ids[i] = ((LivingEntity)this.targetedByScepter.get(i)).m_19879_();
         }

         tag.m_128385_("targetedByScepterIds", ids);
      }

      tag.m_128365_("miscData", miscData);
   }

   public void deserialize(CompoundTag tag) {
      CompoundTag miscData = tag.m_128469_("miscData");
      this.loveTicks = miscData.m_128451_("loveTicks");
      this.lungeTicks = miscData.m_128451_("lungeTicks");
      this.hasDismounted = miscData.m_128471_("hasDismounted");
      int[] loadedChainedToIds = miscData.m_128465_("targetedByScepterIds");
      this.isInitialized = false;
      if (loadedChainedToIds.length > 0) {
         this.targetedByScepterIds = new ArrayList();
         int[] var4 = loadedChainedToIds;
         int var5 = loadedChainedToIds.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            int loadedChainedToId = var4[var6];
            this.targetedByScepterIds.add(loadedChainedToId);
         }
      }

   }

   public boolean doesClientNeedUpdate() {
      if (this.triggerClientUpdate) {
         this.triggerClientUpdate = false;
         return true;
      } else {
         return false;
      }
   }

   private void createLoveParticles(LivingEntity entity) {
      if (entity.m_217043_().m_188503_(7) == 0) {
         for(int i = 0; i < 5; ++i) {
            entity.m_9236_().m_7106_(ParticleTypes.f_123750_, entity.m_20185_() + (entity.m_217043_().m_188500_() - 0.5D) * 3.0D, entity.m_20186_() + (entity.m_217043_().m_188500_() - 0.5D) * 3.0D, entity.m_20189_() + (entity.m_217043_().m_188500_() - 0.5D) * 3.0D, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   private void initialize(Level level) {
      List<LivingEntity> entities = new ArrayList();
      if (this.targetedByScepterIds != null) {
         Iterator var3 = this.targetedByScepterIds.iterator();

         while(var3.hasNext()) {
            int id = (Integer)var3.next();
            if (id != -1) {
               Entity entity = level.m_6815_(id);
               if (entity instanceof LivingEntity) {
                  LivingEntity livingEntity = (LivingEntity)entity;
                  entities.add(livingEntity);
               }
            }
         }
      }

      if (!entities.isEmpty()) {
         this.targetedByScepter = entities;
      } else {
         this.targetedByScepter = null;
      }

      this.targetedByScepterIds = null;
      this.isInitialized = true;
   }
}
