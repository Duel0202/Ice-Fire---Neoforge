package com.github.alexthe666.iceandfire.entity.props;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ChainData {
   @Nullable
   public List<Entity> chainedTo;
   @Nullable
   private List<Integer> chainedToIds;
   @Nullable
   private List<UUID> chainedToUUIDs;
   private boolean isInitialized;
   private boolean triggerClientUpdate;

   public void tickChain(LivingEntity entity) {
      if (!this.isInitialized) {
         this.initialize(entity.m_9236_());
      }

      if (this.chainedTo != null) {
         Iterator var2 = this.chainedTo.iterator();

         while(var2.hasNext()) {
            Entity chain = (Entity)var2.next();
            double distance = (double)chain.m_20270_(entity);
            if (distance > 7.0D) {
               double x = (chain.m_20185_() - entity.m_20185_()) / distance;
               double y = (chain.m_20186_() - entity.m_20186_()) / distance;
               double z = (chain.m_20189_() - entity.m_20189_()) / distance;
               entity.m_20256_(entity.m_20184_().m_82520_(x * Math.abs(x) * 0.4D, y * Math.abs(y) * 0.2D, z * Math.abs(z) * 0.4D));
            }
         }

      }
   }

   public List<Entity> getChainedTo() {
      return (List)Objects.requireNonNullElse(this.chainedTo, Collections.emptyList());
   }

   public void clearChains() {
      if (this.chainedTo != null) {
         this.chainedTo = null;
         this.triggerClientUpdate = true;
      }
   }

   public void attachChain(Entity chain) {
      if (this.chainedTo == null) {
         this.chainedTo = new ArrayList();
      } else if (this.chainedTo.contains(chain)) {
         return;
      }

      this.chainedTo.add(chain);
      this.triggerClientUpdate = true;
   }

   public void removeChain(Entity chain) {
      if (this.chainedTo != null) {
         this.chainedTo.remove(chain);
         this.triggerClientUpdate = true;
         if (this.chainedTo.isEmpty()) {
            this.chainedTo = null;
         }

      }
   }

   public boolean isChainedTo(Entity toCheck) {
      return this.chainedTo != null && !this.chainedTo.isEmpty() ? this.chainedTo.contains(toCheck) : false;
   }

   public void serialize(CompoundTag tag) {
      CompoundTag chainedData = new CompoundTag();
      ListTag uuids = new ListTag();
      if (this.chainedTo != null) {
         int[] ids = new int[this.chainedTo.size()];

         for(int i = 0; i < this.chainedTo.size(); ++i) {
            Entity entity = (Entity)this.chainedTo.get(i);
            ids[i] = entity.m_19879_();
            uuids.add(NbtUtils.m_129226_(entity.m_20148_()));
         }

         chainedData.m_128385_("chainedToIds", ids);
         chainedData.m_128365_("chainedToUUIDs", uuids);
      }

      tag.m_128365_("chainedData", chainedData);
   }

   public void deserialize(CompoundTag tag) {
      CompoundTag chainedData = tag.m_128469_("chainedData");
      int[] loadedChainedToIds = chainedData.m_128465_("chainedToIds");
      ListTag uuids = chainedData.m_128437_("chainedToUUIDs", 11);
      this.isInitialized = false;
      if (loadedChainedToIds.length > 0) {
         this.chainedToIds = new ArrayList();
         int[] var5 = loadedChainedToIds;
         int var6 = loadedChainedToIds.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            int loadedChainedToId = var5[var7];
            this.chainedToIds.add(loadedChainedToId);
         }
      } else {
         this.chainedToIds = null;
      }

      if (!uuids.isEmpty()) {
         this.chainedToUUIDs = new ArrayList();
         Iterator var9 = uuids.iterator();

         while(var9.hasNext()) {
            Tag uuid = (Tag)var9.next();
            this.chainedToUUIDs.add(NbtUtils.m_129233_(uuid));
         }
      } else {
         this.chainedToUUIDs = null;
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

   private void initialize(Level level) {
      List<Entity> entities = new ArrayList();
      Iterator var4;
      Entity entity;
      if (this.chainedToUUIDs != null && level instanceof ServerLevel) {
         ServerLevel serverLevel = (ServerLevel)level;
         var4 = this.chainedToUUIDs.iterator();

         while(var4.hasNext()) {
            UUID uuid = (UUID)var4.next();
            entity = serverLevel.m_8791_(uuid);
            if (entity != null) {
               entities.add(entity);
            }
         }

         this.triggerClientUpdate = true;
      } else if (this.chainedToIds != null) {
         var4 = this.chainedToIds.iterator();

         while(var4.hasNext()) {
            int id = (Integer)var4.next();
            if (id != -1) {
               entity = level.m_6815_(id);
               if (entity != null) {
                  entities.add(entity);
               }
            }
         }
      }

      if (!entities.isEmpty()) {
         this.chainedTo = entities;
      } else {
         this.chainedTo = null;
      }

      this.chainedToIds = null;
      this.chainedToUUIDs = null;
      this.isInitialized = true;
   }
}
