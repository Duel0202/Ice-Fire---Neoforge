package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.entity.ai.DreadAIRideHorse;
import com.github.alexthe666.iceandfire.entity.ai.DreadAITargetNonDread;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IDreadMob;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.recipe.IafBannerPatterns;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.BannerPatterns;
import net.minecraft.world.level.block.entity.BannerPattern.Builder;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityDreadKnight extends EntityDreadMob implements IAnimatedEntity, IVillagerFear, IAnimalFear {
   public static final ItemStack SHIELD = generateShield();
   private static final EntityDataAccessor<Integer> VARIANT;
   public static Animation ANIMATION_SPAWN;
   private int animationTick;
   private Animation currentAnimation;

   public EntityDreadKnight(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   private static ItemStack generateShield() {
      ItemStack itemstack = new ItemStack(Items.f_42669_);
      CompoundTag compoundnbt = itemstack.m_41698_("BlockEntityTag");
      ListTag listnbt = (new Builder()).m_222705_(BannerPatterns.f_222726_, DyeColor.CYAN).m_222708_(Holder.m_205709_((BannerPattern)IafBannerPatterns.PATTERN_DREAD.get()), DyeColor.WHITE).m_58587_();
      compoundnbt.m_128365_("Patterns", listnbt);
      ItemStack shield = new ItemStack(Items.f_42740_, 1);
      shield.m_41751_(itemstack.m_41783_());
      return shield;
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22281_, 2.0D).m_22268_(Attributes.f_22277_, 128.0D).m_22268_(Attributes.f_22284_, 20.0D);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new DreadAIRideHorse(this));
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

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
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

         this.m_20334_(0.0D, this.m_20184_().f_82480_, this.m_20184_().f_82481_);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   protected void m_213945_(RandomSource pRandom, DifficultyInstance pDifficulty) {
      super.m_213945_(pRandom, pDifficulty);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)IafItemRegistry.DREAD_KNIGHT_SWORD.get()));
      if (this.f_19796_.m_188499_()) {
         this.m_8061_(EquipmentSlot.OFFHAND, SHIELD.m_41777_());
      }

      this.setArmorVariant(this.f_19796_.m_188503_(3));
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
      compound.m_128405_("ArmorVariant", this.getArmorVariant());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setArmorVariant(compound.m_128451_("ArmorVariant"));
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public int getArmorVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setArmorVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
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

   public double m_6049_() {
      return -0.6D;
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

   public float m_6100_() {
      return super.m_6100_() * 0.75F;
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityDreadKnight.class, EntityDataSerializers.f_135028_);
      ANIMATION_SPAWN = Animation.create(40);
   }
}
