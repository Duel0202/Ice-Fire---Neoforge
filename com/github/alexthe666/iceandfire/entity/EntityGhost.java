package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.ai.GhostAICharge;
import com.github.alexthe666.iceandfire.entity.ai.GhostPathNavigator;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IHumanoid;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
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
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityGhost extends Monster implements IAnimatedEntity, IVillagerFear, IAnimalFear, IHumanoid, IBlacklistedFromStatues, IHasCustomizableAttributes {
   private static final EntityDataAccessor<Integer> COLOR;
   private static final EntityDataAccessor<Boolean> CHARGING;
   private static final EntityDataAccessor<Boolean> IS_DAYTIME_MODE;
   private static final EntityDataAccessor<Boolean> WAS_FROM_CHEST;
   private static final EntityDataAccessor<Integer> DAYTIME_COUNTER;
   public static Animation ANIMATION_SCARE;
   public static Animation ANIMATION_HIT;
   private int animationTick;
   private Animation currentAnimation;

   public EntityGhost(EntityType<EntityGhost> type, Level worldIn) {
      super(type, worldIn);
      ANIMATION_SCARE = Animation.create(30);
      ANIMATION_HIT = Animation.create(10);
      this.f_21342_ = new EntityGhost.MoveHelper(this);
   }

   @NotNull
   protected ResourceLocation m_7582_() {
      return this.wasFromChest() ? BuiltInLootTables.f_78712_ : this.m_6095_().m_20677_();
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.GHOST_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.GHOST_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.GHOST_DIE;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.ghostMaxHealth).m_22268_(Attributes.f_22277_, 64.0D).m_22268_(Attributes.f_22279_, 0.15D).m_22268_(Attributes.f_22281_, IafConfig.ghostAttackStrength).m_22268_(Attributes.f_22284_, 1.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.ghostMaxHealth);
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.ghostAttackStrength);
   }

   public boolean m_7301_(MobEffectInstance potioneffectIn) {
      return potioneffectIn.m_19544_() != MobEffects.f_19614_ && potioneffectIn.m_19544_() != MobEffects.f_19615_ && super.m_7301_(potioneffectIn);
   }

   public boolean m_6673_(@NotNull DamageSource source) {
      return super.m_6673_(source) || source.m_269533_(DamageTypeTags.f_268745_) || source.m_276093_(DamageTypes.f_268612_) || source.m_276093_(DamageTypes.f_268585_) || source.m_276093_(DamageTypes.f_268722_) || source.m_276093_(DamageTypes.f_268659_) || source.m_276093_(DamageTypes.f_268526_) || source.m_276093_(DamageTypes.f_268469_);
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level worldIn) {
      return new GhostPathNavigator(this, worldIn);
   }

   public boolean isCharging() {
      return (Boolean)this.f_19804_.m_135370_(CHARGING);
   }

   public void setCharging(boolean moving) {
      this.f_19804_.m_135381_(CHARGING, moving);
   }

   public boolean isDaytimeMode() {
      return (Boolean)this.f_19804_.m_135370_(IS_DAYTIME_MODE);
   }

   public void setDaytimeMode(boolean moving) {
      this.f_19804_.m_135381_(IS_DAYTIME_MODE, moving);
   }

   public boolean wasFromChest() {
      return (Boolean)this.f_19804_.m_135370_(WAS_FROM_CHEST);
   }

   public void setFromChest(boolean moving) {
      this.f_19804_.m_135381_(WAS_FROM_CHEST, moving);
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   protected void m_7324_(@NotNull Entity entity) {
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new RestrictSunGoal(this));
      this.f_21345_.m_25352_(3, new FleeSunGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new GhostAICharge(this));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F) {
         public boolean m_8045_() {
            return this.f_25513_ != null && this.f_25513_ instanceof Player && ((Player)this.f_25513_).m_7500_() ? false : super.m_8045_();
         }
      });
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 0.6D) {
         public boolean m_8036_() {
            this.f_25730_ = 60;
            return super.m_8036_();
         }
      });
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Player.class, 10, false, false, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity.m_6084_();
         }
      }));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, false, false, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity) && DragonUtils.isVillager(entity);
         }
      }));
   }

   public void m_8107_() {
      super.m_8107_();
      this.f_19794_ = true;
      if (!this.m_9236_().f_46443_) {
         boolean day = this.m_21527_() && !this.wasFromChest();
         if (day) {
            if (!this.isDaytimeMode()) {
               this.setAnimation(ANIMATION_SCARE);
            }

            this.setDaytimeMode(true);
         } else {
            this.setDaytimeMode(false);
            this.setDaytimeCounter(0);
         }

         if (this.isDaytimeMode()) {
            this.m_20256_(Vec3.f_82478_);
            this.setDaytimeCounter(this.getDaytimeCounter() + 1);
            if (this.getDaytimeCounter() >= 100) {
               this.m_6842_(true);
            }
         } else {
            this.m_6842_(this.m_21023_(MobEffects.f_19609_));
            this.setDaytimeCounter(0);
         }
      } else if (this.getAnimation() == ANIMATION_SCARE && this.getAnimationTick() == 3 && !this.isHauntedShoppingList() && this.f_19796_.m_188503_(3) == 0) {
         this.m_5496_(IafSoundRegistry.GHOST_JUMPSCARE, this.m_6121_(), this.m_6100_());
         if (this.m_9236_().f_46443_) {
            IceAndFire.PROXY.spawnParticle(EnumParticles.Ghost_Appearance, this.m_20185_(), this.m_20186_(), this.m_20189_(), (double)this.m_19879_(), 0.0D, 0.0D);
         }
      }

      if (this.getAnimation() == ANIMATION_HIT && this.m_5448_() != null && (double)this.m_20270_(this.m_5448_()) < 1.4D && this.getAnimationTick() >= 4 && this.getAnimationTick() < 6) {
         this.m_5496_(IafSoundRegistry.GHOST_ATTACK, this.m_6121_(), this.m_6100_());
         this.m_7327_(this.m_5448_());
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean m_21525_() {
      return this.isDaytimeMode() || super.m_21525_();
   }

   public boolean m_20067_() {
      return this.isDaytimeMode() || super.m_20067_();
   }

   protected boolean m_21527_() {
      if (this.m_9236_().m_46461_() && !this.m_9236_().f_46443_) {
         float f = (float)this.m_9236_().m_45517_(LightLayer.BLOCK, this.m_20183_());
         BlockPos blockpos = this.m_20202_() instanceof Boat ? (new BlockPos(this.m_146903_(), this.m_146904_(), this.m_146907_())).m_7494_() : new BlockPos(this.m_146903_(), this.m_146904_() + 4, this.m_146907_());
         return f > 0.5F && this.m_9236_().m_45527_(blockpos);
      } else {
         return false;
      }
   }

   public boolean m_20068_() {
      return true;
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (itemstack != null && itemstack.m_41720_() == IafItemRegistry.MANUSCRIPT.get() && !this.isHauntedShoppingList()) {
         this.setColor(-1);
         this.m_5496_(IafSoundRegistry.BESTIARY_PAGE, 1.0F, 1.0F);
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         return super.m_6071_(player, hand);
      }
   }

   public void m_7023_(@NotNull Vec3 vec) {
      if (this.isDaytimeMode()) {
         super.m_7023_(Vec3.f_82478_);
      } else {
         super.m_7023_(vec);
      }
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setColor(this.f_19796_.m_188503_(3));
      if (this.f_19796_.m_188503_(200) == 0) {
         this.setColor(-1);
      }

      return spawnDataIn;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(COLOR, 0);
      this.m_20088_().m_135372_(CHARGING, false);
      this.m_20088_().m_135372_(IS_DAYTIME_MODE, false);
      this.m_20088_().m_135372_(WAS_FROM_CHEST, false);
      this.m_20088_().m_135372_(DAYTIME_COUNTER, 0);
   }

   public int getColor() {
      return Mth.m_14045_((Integer)this.m_20088_().m_135370_(COLOR), -1, 2);
   }

   public void setColor(int color) {
      this.m_20088_().m_135381_(COLOR, color);
   }

   public int getDaytimeCounter() {
      return (Integer)this.m_20088_().m_135370_(DAYTIME_COUNTER);
   }

   public void setDaytimeCounter(int counter) {
      this.m_20088_().m_135381_(DAYTIME_COUNTER, counter);
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setColor(compound.m_128451_("Color"));
      this.setDaytimeMode(compound.m_128471_("DaytimeMode"));
      this.setDaytimeCounter(compound.m_128451_("DaytimeCounter"));
      this.setFromChest(compound.m_128471_("FromChest"));
      this.setConfigurableAttributes();
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Color", this.getColor());
      compound.m_128379_("DaytimeMode", this.isDaytimeMode());
      compound.m_128405_("DaytimeCounter", this.getDaytimeCounter());
      compound.m_128379_("FromChest", this.wasFromChest());
   }

   public boolean isHauntedShoppingList() {
      return this.getColor() == -1;
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
      return new Animation[]{NO_ANIMATION, ANIMATION_SCARE, ANIMATION_HIT};
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return false;
   }

   static {
      COLOR = SynchedEntityData.m_135353_(EntityGhost.class, EntityDataSerializers.f_135028_);
      CHARGING = SynchedEntityData.m_135353_(EntityGhost.class, EntityDataSerializers.f_135035_);
      IS_DAYTIME_MODE = SynchedEntityData.m_135353_(EntityGhost.class, EntityDataSerializers.f_135035_);
      WAS_FROM_CHEST = SynchedEntityData.m_135353_(EntityGhost.class, EntityDataSerializers.f_135035_);
      DAYTIME_COUNTER = SynchedEntityData.m_135353_(EntityGhost.class, EntityDataSerializers.f_135028_);
   }

   class MoveHelper extends MoveControl {
      EntityGhost ghost;

      public MoveHelper(EntityGhost ghost) {
         super(ghost);
         this.ghost = ghost;
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.MOVE_TO) {
            Vec3 vec3d = new Vec3(this.m_25000_() - this.ghost.m_20185_(), this.m_25001_() - this.ghost.m_20186_(), this.m_25002_() - this.ghost.m_20189_());
            double d0 = vec3d.m_82553_();
            double edgeLength = this.ghost.m_20191_().m_82309_();
            if (d0 < edgeLength) {
               this.f_24981_ = Operation.WAIT;
               this.ghost.m_20256_(this.ghost.m_20184_().m_82490_(0.5D));
            } else {
               this.ghost.m_20256_(this.ghost.m_20184_().m_82549_(vec3d.m_82490_(this.f_24978_ * 0.5D * 0.05D / d0)));
               if (this.ghost.m_5448_() == null) {
                  Vec3 vec3d1 = this.ghost.m_20184_();
                  this.ghost.m_146922_(-((float)Mth.m_14136_(vec3d1.f_82479_, vec3d1.f_82481_)) * 57.295776F);
                  this.ghost.f_20883_ = this.ghost.m_146908_();
               } else {
                  double d4 = this.ghost.m_5448_().m_20185_() - this.ghost.m_20185_();
                  double d5 = this.ghost.m_5448_().m_20189_() - this.ghost.m_20189_();
                  this.ghost.m_146922_(-((float)Mth.m_14136_(d4, d5)) * 57.295776F);
                  this.ghost.f_20883_ = this.ghost.m_146908_();
               }
            }
         }

      }
   }
}
