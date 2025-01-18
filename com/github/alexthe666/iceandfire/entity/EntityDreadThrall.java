package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.entity.ai.DreadAITargetNonDread;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IDreadMob;
import com.github.alexthe666.iceandfire.entity.util.IHasArmorVariant;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityDreadThrall extends EntityDreadMob implements IAnimatedEntity, IVillagerFear, IAnimalFear, IHasArmorVariant {
   private static final EntityDataAccessor<Boolean> CUSTOM_ARMOR_HEAD;
   private static final EntityDataAccessor<Boolean> CUSTOM_ARMOR_CHEST;
   private static final EntityDataAccessor<Boolean> CUSTOM_ARMOR_LEGS;
   private static final EntityDataAccessor<Boolean> CUSTOM_ARMOR_FEET;
   private static final EntityDataAccessor<Integer> CUSTOM_ARMOR_INDEX;
   public static Animation ANIMATION_SPAWN;
   private int animationTick;
   private Animation currentAnimation;

   public EntityDreadThrall(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0D, true));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[]{IDreadMob.class}));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return DragonUtils.canHostilesTarget(entity);
         }
      }));
      this.f_21346_.m_25352_(3, new DreadAITargetNonDread(this, LivingEntity.class, false, new Predicate<LivingEntity>() {
         public boolean apply(LivingEntity entity) {
            return entity instanceof LivingEntity && DragonUtils.canHostilesTarget(entity);
         }
      }));
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0D).m_22268_(Attributes.f_22279_, 0.2D).m_22268_(Attributes.f_22281_, 2.0D).m_22268_(Attributes.f_22277_, 128.0D).m_22268_(Attributes.f_22284_, 2.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(CUSTOM_ARMOR_INDEX, 0);
      this.f_19804_.m_135372_(CUSTOM_ARMOR_HEAD, false);
      this.f_19804_.m_135372_(CUSTOM_ARMOR_CHEST, false);
      this.f_19804_.m_135372_(CUSTOM_ARMOR_LEGS, false);
      this.f_19804_.m_135372_(CUSTOM_ARMOR_FEET, false);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == ANIMATION_SPAWN && this.getAnimationTick() < 30) {
         BlockState belowBlock = this.m_9236_().m_8055_(this.m_20183_().m_7495_());
         if (belowBlock.m_60734_() != Blocks.f_50016_) {
            for(int i = 0; i < 5; ++i) {
               this.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, belowBlock), this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20191_().f_82289_, this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D);
            }
         }

         this.m_20334_(0.0D, this.m_20184_().f_82480_, 0.0D);
      }

      if (this.m_21205_().m_41720_() == Items.f_42411_) {
         this.m_21008_(InteractionHand.MAIN_HAND, new ItemStack(Items.f_42500_));
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   protected void m_213945_(RandomSource randomSource, @NotNull DifficultyInstance difficulty) {
      super.m_213945_(randomSource, difficulty);
      if (this.f_19796_.m_188501_() < 0.75F) {
         double chance = (double)this.f_19796_.m_188501_();
         if (chance < 0.0024999999441206455D) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_SWORD.get()));
         }

         if (chance < 0.009999999776482582D) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42388_));
         }

         if (chance < 0.10000000149011612D) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack(Items.f_42383_));
         }

         if (chance < 0.75D) {
            this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)IafItemRegistry.DREAD_SWORD.get()));
         }
      }

      if (this.f_19796_.m_188501_() < 0.75F) {
         this.m_8061_(EquipmentSlot.HEAD, new ItemStack(Items.f_42464_));
         this.setCustomArmorHead(this.f_19796_.m_188503_(8) != 0);
      }

      if (this.f_19796_.m_188501_() < 0.75F) {
         this.m_8061_(EquipmentSlot.CHEST, new ItemStack(Items.f_42465_));
         this.setCustomArmorChest(this.f_19796_.m_188503_(8) != 0);
      }

      if (this.f_19796_.m_188501_() < 0.75F) {
         this.m_8061_(EquipmentSlot.LEGS, new ItemStack(Items.f_42466_));
         this.setCustomArmorLegs(this.f_19796_.m_188503_(8) != 0);
      }

      if (this.f_19796_.m_188501_() < 0.75F) {
         this.m_8061_(EquipmentSlot.FEET, new ItemStack(Items.f_42467_));
         this.setCustomArmorFeet(this.f_19796_.m_188503_(8) != 0);
      }

      this.setBodyArmorVariant(this.f_19796_.m_188503_(8));
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setAnimation(ANIMATION_SPAWN);
      this.m_213945_(worldIn.m_213780_(), difficultyIn);
      return data;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("ArmorVariant", this.getBodyArmorVariant());
      compound.m_128379_("HasCustomHelmet", this.hasCustomArmorHead());
      compound.m_128379_("HasCustomChestplate", this.hasCustomArmorChest());
      compound.m_128379_("HasCustomLeggings", this.hasCustomArmorLegs());
      compound.m_128379_("HasCustomBoots", this.hasCustomArmorFeet());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setBodyArmorVariant(compound.m_128451_("ArmorVariant"));
      this.setCustomArmorHead(compound.m_128471_("HasCustomHelmet"));
      this.setCustomArmorChest(compound.m_128471_("HasCustomChestplate"));
      this.setCustomArmorLegs(compound.m_128471_("HasCustomLeggings"));
      this.setCustomArmorFeet(compound.m_128471_("HasCustomBoots"));
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public boolean hasCustomArmorHead() {
      return (Boolean)this.f_19804_.m_135370_(CUSTOM_ARMOR_HEAD);
   }

   public void setCustomArmorHead(boolean head) {
      this.f_19804_.m_135381_(CUSTOM_ARMOR_HEAD, head);
   }

   public boolean hasCustomArmorChest() {
      return (Boolean)this.f_19804_.m_135370_(CUSTOM_ARMOR_CHEST);
   }

   public void setCustomArmorChest(boolean head) {
      this.f_19804_.m_135381_(CUSTOM_ARMOR_CHEST, head);
   }

   public boolean hasCustomArmorLegs() {
      return (Boolean)this.f_19804_.m_135370_(CUSTOM_ARMOR_LEGS);
   }

   public void setCustomArmorLegs(boolean head) {
      this.f_19804_.m_135381_(CUSTOM_ARMOR_LEGS, head);
   }

   public boolean hasCustomArmorFeet() {
      return (Boolean)this.f_19804_.m_135370_(CUSTOM_ARMOR_FEET);
   }

   public void setCustomArmorFeet(boolean head) {
      this.f_19804_.m_135381_(CUSTOM_ARMOR_FEET, head);
   }

   public int getBodyArmorVariant() {
      return (Integer)this.f_19804_.m_135370_(CUSTOM_ARMOR_INDEX);
   }

   public void setBodyArmorVariant(int variant) {
      this.f_19804_.m_135381_(CUSTOM_ARMOR_INDEX, variant);
   }

   public int getLegArmorVariant() {
      return 0;
   }

   public void setLegArmorVariant(int variant) {
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_SPAWN};
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean shouldFear() {
      return true;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return SoundEvents.f_12451_;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_12453_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_12452_;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      this.m_5496_(SoundEvents.f_12454_, 0.15F, 1.0F);
   }

   static {
      CUSTOM_ARMOR_HEAD = SynchedEntityData.m_135353_(EntityDreadThrall.class, EntityDataSerializers.f_135035_);
      CUSTOM_ARMOR_CHEST = SynchedEntityData.m_135353_(EntityDreadThrall.class, EntityDataSerializers.f_135035_);
      CUSTOM_ARMOR_LEGS = SynchedEntityData.m_135353_(EntityDreadThrall.class, EntityDataSerializers.f_135035_);
      CUSTOM_ARMOR_FEET = SynchedEntityData.m_135353_(EntityDreadThrall.class, EntityDataSerializers.f_135035_);
      CUSTOM_ARMOR_INDEX = SynchedEntityData.m_135353_(EntityDreadThrall.class, EntityDataSerializers.f_135028_);
      ANIMATION_SPAWN = Animation.create(40);
   }
}
