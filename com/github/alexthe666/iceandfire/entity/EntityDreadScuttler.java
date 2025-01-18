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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
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

public class EntityDreadScuttler extends EntityDreadMob implements IAnimatedEntity, IVillagerFear, IAnimalFear {
   private static final EntityDataAccessor<Float> SCALE;
   private static final EntityDataAccessor<Byte> CLIMBING;
   private static final float INITIAL_WIDTH = 1.5F;
   private static final float INITIAL_HEIGHT = 1.3F;
   public static Animation ANIMATION_SPAWN;
   public static Animation ANIMATION_BITE;
   private int animationTick;
   private Animation currentAnimation;
   private float firstWidth = -1.0F;
   private float firstHeight = -1.0F;

   public EntityDreadScuttler(EntityType type, Level worldIn) {
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
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, 0.34D).m_22268_(Attributes.f_22281_, 7.0D).m_22268_(Attributes.f_22277_, 12.0D).m_22268_(Attributes.f_22284_, 10.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(CLIMBING, (byte)0);
      this.f_19804_.m_135372_(SCALE, 1.0F);
   }

   public float getSize() {
      return Float.valueOf((Float)this.f_19804_.m_135370_(SCALE));
   }

   public void setSize(float scale) {
      this.f_19804_.m_135381_(SCALE, scale);
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128350_("Scale", this.getSize());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setSize(compound.m_128457_("Scale"));
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_BITE);
      }

      return true;
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity attackTarget = this.m_5448_();
      if (Math.abs(this.firstWidth - 1.5F * this.getSize()) > 0.01F || Math.abs(this.firstHeight - 1.3F * this.getSize()) > 0.01F) {
         this.firstWidth = 1.5F * this.getSize();
         this.firstHeight = 1.3F * this.getSize();
      }

      if (!this.m_9236_().f_46443_) {
         this.setBesideClimbableBlock(this.f_19862_);
      }

      if (this.getAnimation() == ANIMATION_SPAWN && this.getAnimationTick() < 30) {
         BlockState belowBlock = this.m_9236_().m_8055_(this.m_20183_().m_7495_());
         if (belowBlock.m_60734_() != Blocks.f_50016_) {
            for(int i = 0; i < 5; ++i) {
               this.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, belowBlock), this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20191_().f_82289_, this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D);
            }
         }

         this.m_20334_(0.0D, this.m_20184_().f_82480_, 0.0D);
      }

      if (attackTarget != null && this.m_20270_(attackTarget) < 4.0F && this.m_142582_(attackTarget)) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_BITE);
         }

         this.m_21391_(attackTarget, 360.0F, 80.0F);
         if (this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 6) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
            attackTarget.m_147240_(0.25D, this.m_20185_() - attackTarget.m_20185_(), this.m_20189_() - attackTarget.m_20189_());
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean m_6147_() {
      return this.isBesideClimbableBlock();
   }

   public void setInWeb() {
   }

   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   public boolean m_7301_(MobEffectInstance potioneffectIn) {
      return potioneffectIn.m_19544_() != MobEffects.f_19614_ && super.m_7301_(potioneffectIn);
   }

   public boolean isBesideClimbableBlock() {
      return ((Byte)this.f_19804_.m_135370_(CLIMBING) & 1) != 0;
   }

   public void setBesideClimbableBlock(boolean climbing) {
      byte b0 = (Byte)this.f_19804_.m_135370_(CLIMBING);
      if (climbing) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 &= -2;
      }

      this.f_19804_.m_135381_(CLIMBING, b0);
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setAnimation(ANIMATION_SPAWN);
      this.setSize(0.5F + this.f_19796_.m_188501_() * 1.15F);
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
      return new Animation[]{ANIMATION_SPAWN, ANIMATION_BITE};
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean shouldFear() {
      return true;
   }

   public Entity getCommander() {
      return null;
   }

   public boolean m_7307_(Entity entityIn) {
      return entityIn instanceof IDreadMob || super.m_7307_(entityIn);
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return SoundEvents.f_12432_;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_12434_;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return SoundEvents.f_12433_;
   }

   public float m_6100_() {
      return super.m_6100_() * 0.7F;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      this.m_5496_(IafSoundRegistry.MYRMEX_WALK, 0.25F, 1.0F);
   }

   public float m_6134_() {
      return this.getSize();
   }

   static {
      SCALE = SynchedEntityData.m_135353_(EntityDreadScuttler.class, EntityDataSerializers.f_135029_);
      CLIMBING = SynchedEntityData.m_135353_(EntityDreadScuttler.class, EntityDataSerializers.f_135027_);
      ANIMATION_SPAWN = Animation.create(40);
      ANIMATION_BITE = Animation.create(15);
   }
}
