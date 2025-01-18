package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.message.MessageMultipartInteract;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class EntityMutlipartPart extends Entity {
   private static final EntityDataAccessor<Optional<UUID>> PARENT_UUID;
   private static final EntityDataAccessor<Float> SCALE_WIDTH;
   private static final EntityDataAccessor<Float> SCALE_HEIGHT;
   private static final EntityDataAccessor<Float> PART_YAW;
   public EntityDimensions multipartSize;
   protected float radius;
   protected float angleYaw;
   protected float offsetY;
   protected float damageMultiplier;

   protected EntityMutlipartPart(EntityType<?> t, Level world) {
      super(t, world);
      this.multipartSize = t.m_20680_();
   }

   protected void m_7378_(@NotNull CompoundTag compound) {
   }

   protected void m_7380_(@NotNull CompoundTag compound) {
   }

   protected void m_5841_() {
   }

   public EntityMutlipartPart(EntityType<?> t, Entity parent, float radius, float angleYaw, float offsetY, float sizeX, float sizeY, float damageMultiplier) {
      super(t, parent.m_9236_());
      this.setParent(parent);
      this.setScaleX(sizeX);
      this.setScaleY(sizeY);
      this.radius = radius;
      this.angleYaw = (angleYaw + 90.0F) * 0.017453292F;
      this.offsetY = offsetY;
      this.damageMultiplier = damageMultiplier;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 2.0D).m_22268_(Attributes.f_22279_, 0.1D);
   }

   @NotNull
   public EntityDimensions m_6972_(@NotNull Pose poseIn) {
      return new EntityDimensions(this.getScaleX(), this.getScaleY(), false);
   }

   protected void m_8097_() {
      this.f_19804_.m_135372_(PARENT_UUID, Optional.empty());
      this.f_19804_.m_135372_(SCALE_WIDTH, 0.5F);
      this.f_19804_.m_135372_(SCALE_HEIGHT, 0.5F);
      this.f_19804_.m_135372_(PART_YAW, 0.0F);
   }

   @Nullable
   public UUID getParentId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(PARENT_UUID)).orElse((Object)null);
   }

   public void setParentId(@Nullable UUID uniqueId) {
      this.f_19804_.m_135381_(PARENT_UUID, Optional.ofNullable(uniqueId));
   }

   private float getScaleX() {
      return (Float)this.f_19804_.m_135370_(SCALE_WIDTH);
   }

   private void setScaleX(float scale) {
      this.f_19804_.m_135381_(SCALE_WIDTH, scale);
   }

   private float getScaleY() {
      return (Float)this.f_19804_.m_135370_(SCALE_HEIGHT);
   }

   private void setScaleY(float scale) {
      this.f_19804_.m_135381_(SCALE_HEIGHT, scale);
   }

   public float getPartYaw() {
      return (Float)this.f_19804_.m_135370_(PART_YAW);
   }

   private void setPartYaw(float yaw) {
      this.f_19804_.m_135381_(PART_YAW, yaw % 360.0F);
   }

   public void m_8119_() {
      this.f_19798_ = false;
      if (this.f_19797_ > 10) {
         Entity parent = this.getParent();
         this.m_6210_();
         if (parent != null && !this.m_9236_().f_46443_) {
            float renderYawOffset = parent.m_146908_();
            if (parent instanceof LivingEntity) {
               renderYawOffset = ((LivingEntity)parent).f_20883_;
            }

            if (this.isSlowFollow()) {
               this.m_6034_(parent.f_19854_ + (double)(this.radius * Mth.m_14089_((float)((double)renderYawOffset * 0.017453292519943295D + (double)this.angleYaw))), parent.f_19855_ + (double)this.offsetY, parent.f_19856_ + (double)(this.radius * Mth.m_14031_((float)((double)renderYawOffset * 0.017453292519943295D + (double)this.angleYaw))));
               double d0 = parent.m_20185_() - this.m_20185_();
               double d1 = parent.m_20186_() - this.m_20186_();
               double d2 = parent.m_20189_() - this.m_20189_();
               float f2 = -((float)(Mth.m_14136_(d1, (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2))) * 57.2957763671875D));
               this.m_146926_(this.limitAngle(this.m_146909_(), f2, 5.0F));
               this.m_5834_();
               this.m_146922_(renderYawOffset);
               this.setPartYaw(this.m_146908_());
               if (!this.m_9236_().f_46443_) {
                  this.collideWithNearbyEntities();
               }
            } else {
               this.m_6034_(parent.m_20185_() + (double)(this.radius * Mth.m_14089_((float)((double)renderYawOffset * 0.017453292519943295D + (double)this.angleYaw))), parent.m_20186_() + (double)this.offsetY, parent.m_20189_() + (double)(this.radius * Mth.m_14031_((float)((double)renderYawOffset * 0.017453292519943295D + (double)this.angleYaw))));
               this.m_5834_();
            }

            if (!this.m_9236_().f_46443_) {
               this.collideWithNearbyEntities();
            }

            if (parent.m_213877_() && !this.m_9236_().f_46443_) {
               this.m_142687_(RemovalReason.DISCARDED);
            }
         } else if (this.f_19797_ > 20 && !this.m_9236_().f_46443_) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }

      super.m_8119_();
   }

   protected boolean isSlowFollow() {
      return false;
   }

   protected float limitAngle(float sourceAngle, float targetAngle, float maximumChange) {
      float f = Mth.m_14177_(targetAngle - sourceAngle);
      if (f > maximumChange) {
         f = maximumChange;
      }

      if (f < -maximumChange) {
         f = -maximumChange;
      }

      float f1 = sourceAngle + f;
      if (f1 < 0.0F) {
         f1 += 360.0F;
      } else if (f1 > 360.0F) {
         f1 -= 360.0F;
      }

      return f1;
   }

   public void m_142687_(@NotNull RemovalReason reason) {
      super.m_142687_(RemovalReason.DISCARDED);
   }

   public Entity getParent() {
      UUID id = this.getParentId();
      if (id != null) {
         Level var3 = this.m_9236_();
         if (var3 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var3;
            return serverLevel.m_8791_(id);
         }
      }

      return null;
   }

   public void setParent(Entity entity) {
      this.setParentId(entity.m_20148_());
   }

   public boolean m_7306_(@NotNull Entity entity) {
      return this == entity || this.getParent() == entity;
   }

   public boolean m_6087_() {
      return true;
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void collideWithNearbyEntities() {
      List<Entity> entities = this.m_9236_().m_45933_(this, this.m_20191_().m_82363_(0.20000000298023224D, 0.0D, 0.20000000298023224D));
      Entity parent = this.getParent();
      if (parent != null) {
         entities.stream().filter((entity) -> {
            return entity != parent && !sharesRider(parent, entity) && !(entity instanceof EntityMutlipartPart) && entity.m_6094_();
         }).forEach((entity) -> {
            entity.m_7334_(parent);
         });
      }

   }

   public static boolean sharesRider(Entity parent, Entity entityIn) {
      Iterator var2 = parent.m_20197_().iterator();

      Entity entity;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         entity = (Entity)var2.next();
         if (entity.equals(entityIn)) {
            return true;
         }
      } while(!sharesRider(entity, entityIn));

      return true;
   }

   @NotNull
   public InteractionResult m_6096_(@NotNull Player player, @NotNull InteractionHand hand) {
      Entity parent = this.getParent();
      if (this.m_9236_().f_46443_ && parent != null) {
         IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageMultipartInteract(parent.m_19879_(), 0.0F));
      }

      return parent != null ? parent.m_6096_(player, hand) : InteractionResult.PASS;
   }

   public boolean m_6469_(@NotNull DamageSource source, float damage) {
      Entity parent = this.getParent();
      if (this.m_9236_().f_46443_ && source.m_7639_() instanceof Player && parent != null) {
         IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageMultipartInteract(parent.m_19879_(), damage * this.damageMultiplier));
      }

      return parent != null && parent.m_6469_(source, damage * this.damageMultiplier);
   }

   public boolean m_6673_(@NotNull DamageSource source) {
      return source.m_276093_(DamageTypes.f_268671_) || source.m_276093_(DamageTypes.f_268722_) || source.m_276093_(DamageTypes.f_268612_) || source.m_276093_(DamageTypes.f_268659_) || source.m_276093_(DamageTypes.f_268546_) || source.m_269533_(DamageTypeTags.f_268745_) || super.m_6673_(source);
   }

   public boolean shouldContinuePersisting() {
      return this.isAddedToWorld() || this.m_213877_();
   }

   static {
      PARENT_UUID = SynchedEntityData.m_135353_(EntityMutlipartPart.class, EntityDataSerializers.f_135041_);
      SCALE_WIDTH = SynchedEntityData.m_135353_(EntityMutlipartPart.class, EntityDataSerializers.f_135029_);
      SCALE_HEIGHT = SynchedEntityData.m_135353_(EntityMutlipartPart.class, EntityDataSerializers.f_135029_);
      PART_YAW = SynchedEntityData.m_135353_(EntityMutlipartPart.class, EntityDataSerializers.f_135029_);
   }
}
