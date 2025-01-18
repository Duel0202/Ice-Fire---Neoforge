package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IDeadMob;
import com.github.alexthe666.iceandfire.enums.EnumSkullType;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityMobSkull extends Animal implements IBlacklistedFromStatues, IDeadMob {
   private static final EntityDataAccessor<Float> SKULL_DIRECTION;
   private static final EntityDataAccessor<Integer> SKULL_ENUM;

   public EntityMobSkull(EntityType t, Level worldIn) {
      super(t, worldIn);
      this.f_19811_ = true;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, 0.0D);
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6673_(DamageSource i) {
      return i.m_7639_() != null;
   }

   public boolean m_21525_() {
      return true;
   }

   public boolean isOnWall() {
      return this.m_9236_().m_46859_(this.m_20183_().m_7495_());
   }

   public void onUpdate() {
      this.f_20884_ = 0.0F;
      this.f_20886_ = 0.0F;
      this.f_20883_ = 0.0F;
      this.f_20885_ = 0.0F;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(SKULL_DIRECTION, 0.0F);
      this.m_20088_().m_135372_(SKULL_ENUM, 0);
   }

   public float getYaw() {
      return (Float)this.m_20088_().m_135370_(SKULL_DIRECTION);
   }

   public void setYaw(float var1) {
      this.m_20088_().m_135381_(SKULL_DIRECTION, var1);
   }

   private int getEnumOrdinal() {
      return (Integer)this.m_20088_().m_135370_(SKULL_ENUM);
   }

   private void setEnumOrdinal(int var1) {
      this.m_20088_().m_135381_(SKULL_ENUM, var1);
   }

   public EnumSkullType getSkullType() {
      return EnumSkullType.values()[Mth.m_14045_(this.getEnumOrdinal(), 0, EnumSkullType.values().length - 1)];
   }

   public void setSkullType(EnumSkullType skullType) {
      this.setEnumOrdinal(skullType.ordinal());
   }

   public boolean m_6469_(@NotNull DamageSource var1, float var2) {
      this.turnIntoItem();
      return super.m_6469_(var1, var2);
   }

   public void turnIntoItem() {
      if (!this.m_213877_()) {
         this.m_142687_(RemovalReason.DISCARDED);
         ItemStack stack = new ItemStack((ItemLike)this.getSkullType().skull_item.get(), 1);
         if (!this.m_9236_().f_46443_) {
            this.m_5552_(stack, 0.0F);
         }

      }
   }

   public boolean m_6898_(@NotNull ItemStack stack) {
      return false;
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      if (player.m_6144_()) {
         this.setYaw(player.m_146908_());
      }

      return super.m_6071_(player, hand);
   }

   public void m_7378_(CompoundTag compound) {
      this.setYaw(compound.m_128457_("SkullYaw"));
      this.setEnumOrdinal(compound.m_128451_("SkullType"));
      super.m_7378_(compound);
   }

   public void m_7380_(CompoundTag compound) {
      compound.m_128350_("SkullYaw", this.getYaw());
      compound.m_128405_("SkullType", this.getEnumOrdinal());
      super.m_7380_(compound);
   }

   public boolean m_6094_() {
      return false;
   }

   protected void m_7324_(@NotNull Entity entity) {
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   public boolean isMobDead() {
      return true;
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      return null;
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   static {
      SKULL_DIRECTION = SynchedEntityData.m_135353_(EntityMobSkull.class, EntityDataSerializers.f_135029_);
      SKULL_ENUM = SynchedEntityData.m_135353_(EntityMobSkull.class, EntityDataSerializers.f_135028_);
   }
}
