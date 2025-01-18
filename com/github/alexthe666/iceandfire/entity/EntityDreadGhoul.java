package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.entity.ai.DreadAITargetNonDread;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IDreadMob;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityDreadGhoul extends EntityDreadMob implements IAnimatedEntity, IVillagerFear, IAnimalFear {
   private static final EntityDataAccessor<Float> SCALE;
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Integer> SCREAMS;
   private static final float INITIAL_WIDTH = 0.6F;
   private static final float INITIAL_HEIGHT = 1.8F;
   public static Animation ANIMATION_SPAWN;
   public static Animation ANIMATION_SLASH;
   private int animationTick;
   private Animation currentAnimation;
   private int hostileTicks = 0;
   private float firstWidth = 1.0F;
   private float firstHeight = 1.0F;

   public EntityDreadGhoul(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 30.0D).m_22268_(Attributes.f_22279_, 0.35D).m_22268_(Attributes.f_22281_, 5.0D).m_22268_(Attributes.f_22277_, 128.0D).m_22268_(Attributes.f_22284_, 4.0D);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0D, true));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.5D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[]{IDreadMob.class}));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return DragonUtils.canHostilesTarget(entity);
         }
      }));
      this.f_21346_.m_25352_(3, new DreadAITargetNonDread(this, LivingEntity.class, false, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return DragonUtils.canHostilesTarget(entity);
         }
      }));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(SCREAMS, 0);
      this.f_19804_.m_135372_(SCALE, 1.0F);
   }

   public float getSize() {
      return (Float)this.f_19804_.m_135370_(SCALE);
   }

   public void setSize(float scale) {
      this.f_19804_.m_135381_(SCALE, scale);
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_SLASH);
      }

      return true;
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity attackTarget = this.m_5448_();
      if (Math.abs(this.firstWidth - 0.6F * this.getSize()) > 0.01F || Math.abs(this.firstHeight - 1.8F * this.getSize()) > 0.01F) {
         this.firstWidth = 0.6F * this.getSize();
         this.firstHeight = 1.8F * this.getSize();
      }

      if (this.getAnimation() == ANIMATION_SPAWN && this.getAnimationTick() < 30) {
         BlockState belowBlock = this.m_9236_().m_8055_(this.m_20183_().m_7495_());
         if (belowBlock.m_60734_() != Blocks.f_50016_) {
            for(int i = 0; i < 5; ++i) {
               this.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, belowBlock), this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20191_().f_82289_, this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D);
            }
         }

         this.m_20334_(0.0D, this.m_20184_().f_82480_, this.m_20184_().f_82481_);
      }

      if (attackTarget != null && this.m_20270_(attackTarget) < 4.0F && this.m_142582_(attackTarget)) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_SLASH);
         }

         this.m_21391_(attackTarget, 360.0F, 80.0F);
         if (this.getAnimation() == ANIMATION_SLASH && (this.getAnimationTick() == 9 || this.getAnimationTick() == 19)) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
            attackTarget.m_147240_(0.25D, this.m_20185_() - attackTarget.m_20185_(), this.m_20189_() - attackTarget.m_20189_());
         }
      }

      if (!this.m_9236_().f_46443_) {
         if (this.m_5448_() != null) {
            ++this.hostileTicks;
            if (this.getScreamStage() == 0) {
               if (this.hostileTicks > 20) {
                  this.setScreamStage(1);
               }
            } else if (this.f_19797_ % 20 < 10) {
               this.setScreamStage(1);
            } else {
               this.setScreamStage(2);
            }
         } else {
            if (this.getScreamStage() > 0) {
               if (this.f_19797_ % 20 < 10 && this.getScreamStage() == 2) {
                  this.setScreamStage(1);
               } else {
                  this.setScreamStage(0);
               }
            }

            this.hostileTicks = 0;
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128405_("ScreamStage", this.getScreamStage());
      compound.m_128350_("DreadScale", this.getSize());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.setScreamStage(compound.m_128451_("ScreamStage"));
      this.setSize(compound.m_128457_("DreadScale"));
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public int getScreamStage() {
      return (Integer)this.f_19804_.m_135370_(SCREAMS);
   }

   public void setScreamStage(int screamStage) {
      this.f_19804_.m_135381_(SCREAMS, screamStage);
   }

   public float m_6134_() {
      return this.getSize();
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setAnimation(ANIMATION_SPAWN);
      this.setVariant(this.f_19796_.m_188503_(3));
      return data;
   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_SPAWN, ANIMATION_SLASH};
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean shouldFear() {
      return true;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.DREAD_GHOUL_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_12608_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_12603_;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      this.m_5496_(SoundEvents.f_12614_, 0.15F, 1.0F);
   }

   public float m_6100_() {
      return super.m_6100_() * 0.7F;
   }

   static {
      SCALE = SynchedEntityData.m_135353_(EntityDreadGhoul.class, EntityDataSerializers.f_135029_);
      VARIANT = SynchedEntityData.m_135353_(EntityDreadGhoul.class, EntityDataSerializers.f_135028_);
      SCREAMS = SynchedEntityData.m_135353_(EntityDreadGhoul.class, EntityDataSerializers.f_135028_);
      ANIMATION_SPAWN = Animation.create(40);
      ANIMATION_SLASH = Animation.create(25);
   }
}
