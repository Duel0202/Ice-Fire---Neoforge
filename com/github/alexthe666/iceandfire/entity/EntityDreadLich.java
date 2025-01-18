package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.ai.DreadAITargetNonDread;
import com.github.alexthe666.iceandfire.entity.ai.DreadLichAIStrife;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IDreadMob;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
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
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityDreadLich extends EntityDreadMob implements IAnimatedEntity, IVillagerFear, IAnimalFear, RangedAttackMob {
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Integer> MINION_COUNT;
   public static Animation ANIMATION_SPAWN;
   public static Animation ANIMATION_SUMMON;
   private final DreadLichAIStrife aiArrowAttack = new DreadLichAIStrife(this, 1.0D, 20, 15.0F);
   private final MeleeAttackGoal aiAttackOnCollide = new MeleeAttackGoal(this, 1.0D, false);
   private int animationTick;
   private Animation currentAnimation;
   private int fireCooldown = 0;
   private int minionCooldown = 0;

   public EntityDreadLich(EntityType<? extends EntityDreadMob> type, Level worldIn) {
      super(type, worldIn);
   }

   public static boolean canLichSpawnOn(EntityType<? extends Mob> typeIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
      BlockPos blockpos = pos.m_7495_();
      return reason == MobSpawnType.SPAWNER || worldIn.m_8055_(blockpos).m_60643_(worldIn, blockpos, typeIn) && randomIn.m_188503_(IafConfig.lichSpawnChance) == 0;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
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
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 50.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, 1.0D).m_22268_(Attributes.f_22277_, 128.0D).m_22268_(Attributes.f_22284_, 2.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(MINION_COUNT, 0);
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

      if (this.m_9236_().f_46443_ && this.getAnimation() == ANIMATION_SUMMON) {
         double d0 = 0.0D;
         double d1 = 0.0D;
         double d2 = 0.0D;
         float f = this.f_20883_ * 0.017453292F + Mth.m_14089_((float)this.f_19797_ * 0.6662F) * 0.25F;
         float f1 = Mth.m_14089_(f);
         float f2 = Mth.m_14031_(f);
         IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, this.m_20185_() + (double)f1 * 0.6D, this.m_20186_() + 1.8D, this.m_20189_() + (double)f2 * 0.6D, d0, d1, d2);
         IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, this.m_20185_() - (double)f1 * 0.6D, this.m_20186_() + 1.8D, this.m_20189_() - (double)f2 * 0.6D, d0, d1, d2);
      }

      if (this.fireCooldown > 0) {
         --this.fireCooldown;
      }

      if (this.minionCooldown > 0) {
         --this.minionCooldown;
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   protected void m_213945_(RandomSource pRandom, DifficultyInstance pDifficulty) {
      super.m_213945_(pRandom, pDifficulty);
      this.m_8061_(EquipmentSlot.MAINHAND, new ItemStack((ItemLike)IafItemRegistry.LICH_STAFF.get()));
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setAnimation(ANIMATION_SPAWN);
      this.m_213945_(worldIn.m_213780_(), difficultyIn);
      this.setVariant(this.f_19796_.m_188503_(5));
      this.setCombatTask();
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
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128405_("MinionCount", this.getMinionCount());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.setMinionCount(compound.m_128451_("MinionCount"));
      this.setCombatTask();
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public int getMinionCount() {
      return (Integer)this.f_19804_.m_135370_(MINION_COUNT);
   }

   public void setMinionCount(int minions) {
      this.f_19804_.m_135381_(MINION_COUNT, minions);
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_SPAWN, ANIMATION_SUMMON};
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

   public void m_8061_(@NotNull EquipmentSlot slotIn, @NotNull ItemStack stack) {
      super.m_8061_(slotIn, stack);
      if (!this.m_9236_().f_46443_ && slotIn == EquipmentSlot.MAINHAND) {
         this.setCombatTask();
      }

   }

   public void setCombatTask() {
      if (this.m_9236_() != null && !this.m_9236_().f_46443_) {
         this.f_21345_.m_25363_(this.aiAttackOnCollide);
         this.f_21345_.m_25363_(this.aiArrowAttack);
         ItemStack itemstack = this.m_21205_();
         if (itemstack.m_41720_() == IafItemRegistry.LICH_STAFF.get()) {
            int i = 100;
            this.aiArrowAttack.setAttackCooldown(i);
            this.f_21345_.m_25352_(4, this.aiArrowAttack);
         } else {
            this.f_21345_.m_25352_(4, this.aiAttackOnCollide);
         }
      }

   }

   public void m_6504_(@NotNull LivingEntity target, float distanceFactor) {
      boolean flag = false;
      double d1;
      if (this.getMinionCount() < 5 && this.minionCooldown == 0) {
         this.setAnimation(ANIMATION_SUMMON);
         this.m_5496_(IafSoundRegistry.DREAD_LICH_SUMMON, this.m_6121_(), this.m_6100_());
         Mob minion = this.getRandomNewMinion();
         int x = (int)this.m_20185_() - 5 + this.f_19796_.m_188503_(10);
         int z = (int)this.m_20189_() - 5 + this.f_19796_.m_188503_(10);
         d1 = this.getHeightFromXZ(x, z);
         minion.m_7678_((double)x + 0.5D, d1, (double)z + 0.5D, this.m_146908_(), this.m_146909_());
         minion.m_6710_(target);
         Level currentLevel = this.m_9236_();
         if (currentLevel instanceof ServerLevelAccessor) {
            minion.m_6518_((ServerLevelAccessor)currentLevel, currentLevel.m_6436_(this.m_20183_()), MobSpawnType.MOB_SUMMONED, (SpawnGroupData)null, (CompoundTag)null);
         }

         if (minion instanceof EntityDreadMob) {
            ((EntityDreadMob)minion).setCommanderId(this.m_20148_());
         }

         if (!currentLevel.f_46443_) {
            currentLevel.m_7967_(minion);
         }

         this.minionCooldown = 100;
         this.setMinionCount(this.getMinionCount() + 1);
         flag = true;
      }

      if (this.fireCooldown == 0 && !flag) {
         this.m_6674_(InteractionHand.MAIN_HAND);
         this.m_5496_(SoundEvents.f_12609_, this.m_6121_(), this.m_6100_());
         EntityDreadLichSkull skull = new EntityDreadLichSkull((EntityType)IafEntityRegistry.DREAD_LICH_SKULL.get(), this.m_9236_(), this, 6.0D);
         double d0 = target.m_20185_() - this.m_20185_();
         d1 = target.m_20191_().f_82289_ + (double)(target.m_20206_() * 2.0F) - skull.m_20186_();
         double d2 = target.m_20189_() - this.m_20189_();
         double d3 = Math.sqrt((double)((float)(d0 * d0 + d2 * d2)));
         skull.m_6686_(d0, d1 + d3 * 0.20000000298023224D, d2, 0.0F, (float)(14 - this.m_9236_().m_46791_().m_19028_() * 4));
         this.m_9236_().m_7967_(skull);
         this.fireCooldown = 100;
      }

   }

   private Mob getRandomNewMinion() {
      float chance = this.f_19796_.m_188501_();
      if (chance > 0.5F) {
         return new EntityDreadThrall((EntityType)IafEntityRegistry.DREAD_THRALL.get(), this.m_9236_());
      } else if (chance > 0.35F) {
         return new EntityDreadGhoul((EntityType)IafEntityRegistry.DREAD_GHOUL.get(), this.m_9236_());
      } else {
         return (Mob)(chance > 0.15F ? new EntityDreadBeast((EntityType)IafEntityRegistry.DREAD_BEAST.get(), this.m_9236_()) : new EntityDreadScuttler((EntityType)IafEntityRegistry.DREAD_SCUTTLER.get(), this.m_9236_()));
      }
   }

   private double getHeightFromXZ(int x, int z) {
      BlockPos thisPos;
      for(thisPos = new BlockPos(x, (int)(this.m_20186_() + 7.0D), z); this.m_9236_().m_46859_(thisPos) && thisPos.m_123342_() > 2; thisPos = thisPos.m_7495_()) {
      }

      double height = (double)thisPos.m_123342_() + 1.0D;
      return height;
   }

   public boolean m_7307_(Entity entityIn) {
      return entityIn instanceof IDreadMob || super.m_7307_(entityIn);
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
      VARIANT = SynchedEntityData.m_135353_(EntityDreadLich.class, EntityDataSerializers.f_135028_);
      MINION_COUNT = SynchedEntityData.m_135353_(EntityDreadLich.class, EntityDataSerializers.f_135028_);
      ANIMATION_SPAWN = Animation.create(40);
      ANIMATION_SUMMON = Animation.create(15);
   }
}
