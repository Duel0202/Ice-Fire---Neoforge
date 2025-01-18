package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.util.IDreadMob;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;

public class EntityDreadHorse extends SkeletonHorse implements IDreadMob {
   protected static final EntityDataAccessor<Optional<UUID>> COMMANDER_UNIQUE_ID;

   public EntityDreadHorse(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public static Builder bakeAttributes() {
      return m_30627_().m_22268_(Attributes.f_22276_, 25.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22284_, 4.0D);
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

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.m_146762_(24000);
      return data;
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

   public Entity getCommander() {
      try {
         UUID uuid = this.getCommanderId();
         return uuid == null ? null : this.m_9236_().m_46003_(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   static {
      COMMANDER_UNIQUE_ID = SynchedEntityData.m_135353_(EntityDreadHorse.class, EntityDataSerializers.f_135041_);
   }
}
