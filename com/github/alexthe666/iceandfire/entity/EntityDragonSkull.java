package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IDeadMob;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityDragonSkull extends Animal implements IBlacklistedFromStatues, IDeadMob {
   private static final EntityDataAccessor<Integer> DRAGON_TYPE;
   private static final EntityDataAccessor<Integer> DRAGON_AGE;
   private static final EntityDataAccessor<Integer> DRAGON_STAGE;
   private static final EntityDataAccessor<Float> DRAGON_DIRECTION;
   public final float minSize = 0.3F;
   public final float maxSize = 8.58F;

   public EntityDragonSkull(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.f_19811_ = true;
   }

   public boolean m_6898_(@NotNull ItemStack stack) {
      return false;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, 0.0D);
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean m_6673_(DamageSource i) {
      return i.m_7639_() != null && super.m_6673_(i);
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
      this.m_20088_().m_135372_(DRAGON_TYPE, 0);
      this.m_20088_().m_135372_(DRAGON_AGE, 0);
      this.m_20088_().m_135372_(DRAGON_STAGE, 0);
      this.m_20088_().m_135372_(DRAGON_DIRECTION, 0.0F);
   }

   public float getYaw() {
      return (Float)this.m_20088_().m_135370_(DRAGON_DIRECTION);
   }

   public void setYaw(float var1) {
      this.m_20088_().m_135381_(DRAGON_DIRECTION, var1);
   }

   public int getDragonType() {
      return (Integer)this.m_20088_().m_135370_(DRAGON_TYPE);
   }

   public void setDragonType(int var1) {
      this.m_20088_().m_135381_(DRAGON_TYPE, var1);
   }

   public int getStage() {
      return (Integer)this.m_20088_().m_135370_(DRAGON_STAGE);
   }

   public void setStage(int var1) {
      this.m_20088_().m_135381_(DRAGON_STAGE, var1);
   }

   public int getDragonAge() {
      return (Integer)this.m_20088_().m_135370_(DRAGON_AGE);
   }

   public void setDragonAge(int var1) {
      this.m_20088_().m_135381_(DRAGON_AGE, var1);
   }

   public SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return null;
   }

   public boolean m_6469_(@NotNull DamageSource var1, float var2) {
      this.turnIntoItem();
      return super.m_6469_(var1, var2);
   }

   public void turnIntoItem() {
      if (!this.m_213877_()) {
         this.m_142687_(RemovalReason.DISCARDED);
         ItemStack stack = new ItemStack(this.getDragonSkullItem());
         stack.m_41751_(new CompoundTag());
         stack.m_41783_().m_128405_("Stage", this.getStage());
         stack.m_41783_().m_128405_("DragonAge", this.getDragonAge());
         if (!this.m_9236_().f_46443_) {
            this.m_5552_(stack, 0.0F);
         }

      }
   }

   public Item getDragonSkullItem() {
      switch(this.getDragonType()) {
      case 0:
         return (Item)IafItemRegistry.DRAGON_SKULL_FIRE.get();
      case 1:
         return (Item)IafItemRegistry.DRAGON_SKULL_ICE.get();
      case 2:
         return (Item)IafItemRegistry.DRAGON_SKULL_LIGHTNING.get();
      default:
         return (Item)IafItemRegistry.DRAGON_SKULL_FIRE.get();
      }
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      return null;
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      if (player.m_6144_()) {
         this.setYaw(player.m_146908_());
      }

      return super.m_6071_(player, hand);
   }

   public void m_7378_(CompoundTag compound) {
      this.setDragonType(compound.m_128451_("Type"));
      this.setStage(compound.m_128451_("Stage"));
      this.setDragonAge(compound.m_128451_("DragonAge"));
      this.setYaw(compound.m_128457_("DragonYaw"));
      super.m_7378_(compound);
   }

   public void m_7380_(CompoundTag compound) {
      compound.m_128405_("Type", this.getDragonType());
      compound.m_128405_("Stage", this.getStage());
      compound.m_128405_("DragonAge", this.getDragonAge());
      compound.m_128350_("DragonYaw", this.getYaw());
      super.m_7380_(compound);
   }

   public float getDragonSize() {
      float step = -0.06624F;
      if (this.getDragonAge() > 125) {
         Objects.requireNonNull(this);
         return 0.3F + step * 125.0F;
      } else {
         Objects.requireNonNull(this);
         return 0.3F + step * (float)this.getDragonAge();
      }
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

   public int getDragonStage() {
      return Math.max(this.getStage(), 1);
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   static {
      DRAGON_TYPE = SynchedEntityData.m_135353_(EntityDragonSkull.class, EntityDataSerializers.f_135028_);
      DRAGON_AGE = SynchedEntityData.m_135353_(EntityDragonSkull.class, EntityDataSerializers.f_135028_);
      DRAGON_STAGE = SynchedEntityData.m_135353_(EntityDragonSkull.class, EntityDataSerializers.f_135028_);
      DRAGON_DIRECTION = SynchedEntityData.m_135353_(EntityDragonSkull.class, EntityDataSerializers.f_135029_);
   }
}
