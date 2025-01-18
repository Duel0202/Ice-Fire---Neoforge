package com.github.alexthe666.iceandfire.entity.props;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntitySiren;
import com.github.alexthe666.iceandfire.entity.util.IHearsSiren;
import java.util.UUID;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class SirenData {
   @Nullable
   public EntitySiren charmedBy;
   public int charmTime;
   public boolean isCharmed;
   @Nullable
   private UUID charmedByUUID;
   private int charmedById;
   private boolean isInitialized;
   private boolean triggerClientUpdate;

   public void tickCharmed(LivingEntity holder) {
      if (holder instanceof Player || holder instanceof AbstractVillager || holder instanceof IHearsSiren) {
         if (!this.isInitialized) {
            this.initialize(holder.m_9236_());
         }

         if (this.charmedBy != null) {
            label71: {
               if (this.charmedBy.isActuallySinging()) {
                  if (EntitySiren.isWearingEarplugs(holder) || this.charmTime > IafConfig.sirenMaxSingTime) {
                     this.charmedBy.singCooldown = IafConfig.sirenTimeBetweenSongs;
                     this.clearCharm();
                     return;
                  }

                  if (!this.charmedBy.m_6084_() || holder.m_20270_(this.charmedBy) > 64.0F) {
                     break label71;
                  }

                  if (holder instanceof Player) {
                     Player player = (Player)holder;
                     if (player.m_7500_() || player.m_5833_()) {
                        break label71;
                     }
                  }

                  if (holder.m_20270_(this.charmedBy) < 5.0F) {
                     this.charmedBy.singCooldown = IafConfig.sirenTimeBetweenSongs;
                     this.charmedBy.setSinging(false);
                     this.charmedBy.m_6710_(holder);
                     this.charmedBy.m_21561_(true);
                     this.charmedBy.triggerOtherSirens(holder);
                     this.clearCharm();
                     return;
                  }

                  this.isCharmed = true;
                  ++this.charmTime;
                  if (holder.m_217043_().m_188503_(7) == 0) {
                     for(int i = 0; i < 5; ++i) {
                        holder.m_9236_().m_7106_(ParticleTypes.f_123750_, holder.m_20185_() + (holder.m_217043_().m_188500_() - 0.5D) * 3.0D, holder.m_20186_() + (holder.m_217043_().m_188500_() - 0.5D) * 3.0D, holder.m_20189_() + (holder.m_217043_().m_188500_() - 0.5D) * 3.0D, 0.0D, 0.0D, 0.0D);
                     }
                  }

                  if (holder.f_19862_) {
                     holder.m_6862_(true);
                  }

                  double motionXAdd = (Math.signum(this.charmedBy.m_20185_() - holder.m_20185_()) * 0.5D - holder.m_20184_().f_82479_) * 0.100000000372529D;
                  double motionYAdd = (Math.signum(this.charmedBy.m_20186_() - holder.m_20186_() + 1.0D) * 0.5D - holder.m_20184_().f_82480_) * 0.100000000372529D;
                  double motionZAdd = (Math.signum(this.charmedBy.m_20189_() - holder.m_20189_()) * 0.5D - holder.m_20184_().f_82481_) * 0.100000000372529D;
                  holder.m_20256_(holder.m_20184_().m_82520_(motionXAdd, motionYAdd, motionZAdd));
                  if (holder.m_20159_()) {
                     holder.m_8127_();
                  }

                  if (!(holder instanceof Player)) {
                     double x = this.charmedBy.m_20185_() - holder.m_20185_();
                     double y = this.charmedBy.m_20186_() - 1.0D - holder.m_20186_();
                     double z = this.charmedBy.m_20189_() - holder.m_20189_();
                     double radius = Math.sqrt(x * x + z * z);
                     float xRot = (float)(-(Mth.m_14136_(y, radius) * 57.29577951308232D));
                     float yRot = (float)(Mth.m_14136_(z, x) * 57.29577951308232D) - 90.0F;
                     holder.m_146926_(this.updateRotation(holder.m_146909_(), xRot, 30.0F));
                     holder.m_146922_(this.updateRotation(holder.m_146908_(), yRot, 30.0F));
                  }
               }

               return;
            }

            this.clearCharm();
         }
      }
   }

   public void setCharmed(Entity entity) {
      if (entity instanceof EntitySiren) {
         EntitySiren siren = (EntitySiren)entity;
         this.charmedBy = siren;
         this.isCharmed = true;
         this.triggerClientUpdate = true;
      }
   }

   public void clearCharm() {
      this.charmTime = 0;
      this.isCharmed = false;
      this.charmedBy = null;
      this.triggerClientUpdate = true;
   }

   public void serialize(CompoundTag tag) {
      CompoundTag sirenData = new CompoundTag();
      if (this.charmedBy != null) {
         sirenData.m_128365_("charmedByUUID", NbtUtils.m_129226_(this.charmedBy.m_20148_()));
         sirenData.m_128405_("charmedById", this.charmedBy.m_19879_());
      } else {
         sirenData.m_128405_("charmedById", -1);
      }

      sirenData.m_128405_("charmTime", this.charmTime);
      sirenData.m_128379_("isCharmed", this.isCharmed);
      tag.m_128365_("sirenData", sirenData);
   }

   public void deserialize(CompoundTag tag) {
      CompoundTag sirenData = tag.m_128469_("sirenData");
      Tag uuidTag = sirenData.m_128423_("charmedByUUID");
      if (uuidTag != null) {
         this.charmedByUUID = NbtUtils.m_129233_(uuidTag);
      }

      this.charmedById = sirenData.m_128451_("charmedById");
      this.charmTime = sirenData.m_128451_("charmTime");
      this.isCharmed = sirenData.m_128471_("isCharmed");
      this.isInitialized = false;
   }

   public boolean doesClientNeedUpdate() {
      if (this.triggerClientUpdate) {
         this.triggerClientUpdate = false;
         return true;
      } else {
         return false;
      }
   }

   private float updateRotation(float angle, float targetAngle, float maxIncrease) {
      float f = Mth.m_14177_(targetAngle - angle);
      if (f > maxIncrease) {
         f = maxIncrease;
      }

      if (f < -maxIncrease) {
         f = -maxIncrease;
      }

      return angle + f;
   }

   private void initialize(Level level) {
      this.charmedBy = null;
      Entity entity;
      EntitySiren siren;
      if (this.charmedByUUID != null && level instanceof ServerLevel) {
         ServerLevel serverLevel = (ServerLevel)level;
         entity = serverLevel.m_8791_(this.charmedByUUID);
         if (entity instanceof EntitySiren) {
            siren = (EntitySiren)entity;
            this.triggerClientUpdate = true;
            this.charmedByUUID = null;
            this.charmedBy = siren;
         }
      } else if (this.charmedById != -1) {
         entity = level.m_6815_(this.charmedById);
         if (entity instanceof EntitySiren) {
            siren = (EntitySiren)entity;
            this.charmedBy = siren;
         }
      }

      this.isInitialized = true;
   }
}
