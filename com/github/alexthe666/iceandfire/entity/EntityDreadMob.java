package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.util.IDreadMob;
import com.github.alexthe666.iceandfire.entity.util.IHumanoid;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public class EntityDreadMob extends Monster implements IDreadMob {
   protected static final EntityDataAccessor<Optional<UUID>> COMMANDER_UNIQUE_ID;

   public EntityDreadMob(EntityType<? extends Monster> t, Level worldIn) {
      super(t, worldIn);
   }

   public static Entity necromancyEntity(LivingEntity entity) {
      Entity lichSummoned = null;
      float readInScale;
      if (entity.m_6336_() == MobType.f_21642_) {
         Entity lichSummoned = new EntityDreadScuttler((EntityType)IafEntityRegistry.DREAD_SCUTTLER.get(), entity.m_9236_());
         readInScale = entity.m_20205_() / 1.5F;
         if (entity.m_9236_() instanceof ServerLevelAccessor) {
            ((EntityDreadScuttler)lichSummoned).m_6518_((ServerLevelAccessor)entity.m_9236_(), entity.m_9236_().m_6436_(entity.m_20183_()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
         }

         ((EntityDreadScuttler)lichSummoned).setSize(readInScale);
         return lichSummoned;
      } else if (!(entity instanceof Zombie) && !(entity instanceof IHumanoid)) {
         if (entity.m_6336_() != MobType.f_21641_ && !(entity instanceof AbstractSkeleton) && !(entity instanceof Player)) {
            if (entity instanceof AbstractHorse) {
               Entity lichSummoned = new EntityDreadHorse((EntityType)IafEntityRegistry.DREAD_HORSE.get(), entity.m_9236_());
               return lichSummoned;
            } else if (entity instanceof Animal) {
               Entity lichSummoned = new EntityDreadBeast((EntityType)IafEntityRegistry.DREAD_BEAST.get(), entity.m_9236_());
               readInScale = entity.m_20205_() / 1.2F;
               if (entity.m_9236_() instanceof ServerLevelAccessor) {
                  ((EntityDreadBeast)lichSummoned).m_6518_((ServerLevelAccessor)entity.m_9236_(), entity.m_9236_().m_6436_(entity.m_20183_()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
               }

               ((EntityDreadBeast)lichSummoned).setSize(readInScale);
               return lichSummoned;
            } else {
               return lichSummoned;
            }
         } else {
            Entity lichSummoned = new EntityDreadThrall((EntityType)IafEntityRegistry.DREAD_THRALL.get(), entity.m_9236_());
            EntityDreadThrall thrall = (EntityDreadThrall)lichSummoned;
            if (entity.m_9236_() instanceof ServerLevelAccessor) {
               thrall.m_6518_((ServerLevelAccessor)entity.m_9236_(), entity.m_9236_().m_6436_(entity.m_20183_()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
            }

            thrall.setCustomArmorHead(false);
            thrall.setCustomArmorChest(false);
            thrall.setCustomArmorLegs(false);
            thrall.setCustomArmorFeet(false);
            EquipmentSlot[] var3 = EquipmentSlot.values();
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               EquipmentSlot slot = var3[var5];
               thrall.m_8061_(slot, entity.m_6844_(slot));
            }

            return thrall;
         }
      } else {
         lichSummoned = new EntityDreadGhoul((EntityType)IafEntityRegistry.DREAD_GHOUL.get(), entity.m_9236_());
         readInScale = entity.m_20205_() / 0.6F;
         if (entity.m_9236_() instanceof ServerLevelAccessor) {
            ((EntityDreadGhoul)lichSummoned).m_6518_((ServerLevelAccessor)entity.m_9236_(), entity.m_9236_().m_6436_(entity.m_20183_()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
         }

         ((EntityDreadGhoul)lichSummoned).setSize(readInScale);
         return lichSummoned;
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(COMMANDER_UNIQUE_ID, Optional.empty());
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      if (this.getCommanderId() != null) {
         compound.m_128362_("CommanderUUID", this.getCommanderId());
      }

   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      UUID uuid;
      if (compound.m_128403_("CommanderUUID")) {
         uuid = compound.m_128342_("CommanderUUID");
      } else {
         String s = compound.m_128461_("CommanderUUID");
         uuid = OldUsersConverter.m_11083_(this.m_20194_(), s);
      }

      if (uuid != null) {
         try {
            this.setCommanderId(uuid);
         } catch (Throwable var4) {
         }
      }

   }

   public boolean m_7307_(@NotNull Entity entityIn) {
      return entityIn instanceof IDreadMob || super.m_7307_(entityIn);
   }

   @Nullable
   public UUID getCommanderId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(COMMANDER_UNIQUE_ID)).orElse((Object)null);
   }

   public void setCommanderId(@Nullable UUID uuid) {
      this.f_19804_.m_135381_(COMMANDER_UNIQUE_ID, Optional.ofNullable(uuid));
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.m_9236_().f_46443_ && this.getCommander() instanceof EntityDreadLich) {
         EntityDreadLich lich = (EntityDreadLich)this.getCommander();
         if (lich.m_5448_() != null && lich.m_5448_().m_6084_()) {
            this.m_6710_(lich.m_5448_());
         }
      }

   }

   public Entity getCommander() {
      try {
         UUID uuid = this.getCommanderId();
         LivingEntity player = uuid == null ? null : this.m_9236_().m_46003_(uuid);
         if (player != null) {
            return player;
         } else {
            if (!this.m_9236_().f_46443_) {
               Entity entity = this.m_9236_().m_7654_().m_129880_(this.m_9236_().m_46472_()).m_8791_(uuid);
               if (entity instanceof LivingEntity) {
                  return entity;
               }
            }

            return null;
         }
      } catch (IllegalArgumentException var4) {
         return null;
      }
   }

   public void onKillEntity(LivingEntity LivingEntityIn) {
      Entity commander = this instanceof EntityDreadLich ? this : this.getCommander();
      if (commander != null && !(LivingEntityIn instanceof EntityDragonBase)) {
         Entity summoned = necromancyEntity(LivingEntityIn);
         if (summoned != null) {
            summoned.m_20359_(LivingEntityIn);
            if (!this.m_9236_().f_46443_) {
               this.m_9236_().m_7967_(summoned);
            }

            if (commander instanceof EntityDreadLich) {
               ((EntityDreadLich)commander).setMinionCount(((EntityDreadLich)commander).getMinionCount() + 1);
            }

            if (summoned instanceof EntityDreadMob) {
               ((EntityDreadMob)summoned).setCommanderId(((Entity)commander).m_20148_());
            }
         }
      }

   }

   public void m_142687_(@NotNull RemovalReason reason) {
      if (!this.m_213877_() && this.getCommander() != null && this.getCommander() instanceof EntityDreadLich) {
         EntityDreadLich lich = (EntityDreadLich)this.getCommander();
         lich.setMinionCount(lich.getMinionCount() - 1);
      }

      super.m_142687_(reason);
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   static {
      COMMANDER_UNIQUE_ID = SynchedEntityData.m_135353_(EntityDreadMob.class, EntityDataSerializers.f_135041_);
   }
}
