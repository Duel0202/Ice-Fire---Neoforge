package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.ai.StymphalianBirdAIAirTarget;
import com.github.alexthe666.iceandfire.entity.ai.StymphalianBirdAIFlee;
import com.github.alexthe666.iceandfire.entity.ai.StymphalianBirdAITarget;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.entity.util.StymphalianBirdFlock;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class EntityStymphalianBird extends Monster implements IAnimatedEntity, Enemy, IVillagerFear, IAnimalFear {
   public static final Predicate<Entity> STYMPHALIAN_PREDICATE = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity entity) {
         return entity instanceof EntityStymphalianBird;
      }
   };
   private static final int FLIGHT_CHANCE_PER_TICK = 100;
   private static final EntityDataAccessor<Optional<UUID>> VICTOR_ENTITY;
   private static final EntityDataAccessor<Boolean> FLYING;
   public static Animation ANIMATION_PECK;
   public static Animation ANIMATION_SHOOT_ARROWS;
   public static Animation ANIMATION_SPEAK;
   public float flyProgress;
   public BlockPos airTarget;
   public StymphalianBirdFlock flock;
   private int animationTick;
   private Animation currentAnimation;
   private boolean isFlying;
   private int flyTicks;
   private int launchTicks;
   private boolean aiFlightLaunch = false;
   private int airBorneCounter;

   public EntityStymphalianBird(EntityType<? extends Monster> t, Level worldIn) {
      super(t, worldIn);
   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new StymphalianBirdAIFlee(this, 10.0F));
      this.f_21345_.m_25352_(3, new MeleeAttackGoal(this, 1.5D, false));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new StymphalianBirdAIAirTarget(this));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new StymphalianBirdAITarget(this, LivingEntity.class, true));
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 24.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, IafConfig.myrmexBaseAttackStrength * 2.0D).m_22268_(Attributes.f_22277_, (double)Math.min(2048, IafConfig.stymphalianBirdTargetSearchLength)).m_22268_(Attributes.f_22284_, 4.0D);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VICTOR_ENTITY, Optional.empty());
      this.f_19804_.m_135372_(FLYING, Boolean.FALSE);
   }

   public int m_213860_() {
      return 10;
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().f_46443_ && this.m_9236_().m_46791_() == Difficulty.PEACEFUL) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      if (this.getVictorId() != null) {
         tag.m_128362_("VictorUUID", this.getVictorId());
      }

      tag.m_128379_("Flying", this.isFlying());
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      UUID s;
      if (tag.m_128403_("VictorUUID")) {
         s = tag.m_128342_("VictorUUID");
      } else {
         String s1 = tag.m_128461_("VictorUUID");
         s = OldUsersConverter.m_11083_(this.m_20194_(), s1);
      }

      if (s != null) {
         try {
            this.setVictorId(s);
         } catch (Throwable var4) {
         }
      }

      this.setFlying(tag.m_128471_("Flying"));
   }

   public boolean isFlying() {
      return this.m_9236_().f_46443_ ? (this.isFlying = (Boolean)this.f_19804_.m_135370_(FLYING)) : this.isFlying;
   }

   public void setFlying(boolean flying) {
      this.f_19804_.m_135381_(FLYING, flying);
      if (!this.m_9236_().f_46443_) {
         this.isFlying = flying;
      }

   }

   public void m_6667_(DamageSource cause) {
      if (cause.m_7639_() != null && cause.m_7639_() instanceof LivingEntity && !this.m_9236_().f_46443_) {
         this.setVictorId(cause.m_7639_().m_20148_());
         if (this.flock != null) {
            this.flock.setFearTarget((LivingEntity)cause.m_7639_());
         }
      }

      super.m_6667_(cause);
   }

   protected void m_6153_() {
      super.m_6153_();
   }

   @Nullable
   public UUID getVictorId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(VICTOR_ENTITY)).orElse((Object)null);
   }

   public void setVictorId(@Nullable UUID uuid) {
      this.f_19804_.m_135381_(VICTOR_ENTITY, Optional.ofNullable(uuid));
   }

   @Nullable
   public LivingEntity getVictor() {
      try {
         UUID uuid = this.getVictorId();
         return uuid == null ? null : this.m_9236_().m_46003_(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public void setVictor(LivingEntity player) {
      this.setVictorId(player.m_20148_());
   }

   public boolean isVictor(LivingEntity entityIn) {
      return entityIn == this.getVictor();
   }

   public boolean isTargetBlocked(Vec3 target) {
      return this.m_9236_().m_45547_(new ClipContext(target, this.m_20299_(1.0F), Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_PECK);
      }

      return true;
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_5448_() != null && (this.m_5448_() instanceof Player && ((Player)this.m_5448_()).m_7500_() || this.getVictor() != null && this.isVictor(this.m_5448_()))) {
         this.m_6710_((LivingEntity)null);
      }

      double dist;
      if (this.flock == null) {
         StymphalianBirdFlock otherFlock = StymphalianBirdFlock.getNearbyFlock(this);
         if (otherFlock == null) {
            this.flock = StymphalianBirdFlock.createFlock(this);
         } else {
            this.flock = otherFlock;
            this.flock.addToFlock(this);
         }
      } else {
         if (!this.flock.isLeader(this)) {
            dist = this.m_20280_(this.flock.getLeader());
            if (dist > 360.0D) {
               this.setFlying(true);
               this.f_21344_.m_26573_();
               this.airTarget = StymphalianBirdAIAirTarget.getNearbyAirTarget(this.flock.getLeader());
               this.aiFlightLaunch = false;
            } else if (!this.flock.getLeader().isFlying()) {
               this.setFlying(false);
               this.airTarget = null;
               this.aiFlightLaunch = false;
            }

            if (this.m_20096_() && dist < 40.0D && this.getAnimation() != ANIMATION_SHOOT_ARROWS) {
               this.setFlying(false);
            }
         }

         this.flock.update();
      }

      if (!this.m_9236_().f_46443_ && this.m_5448_() != null && this.m_5448_().m_6084_()) {
         dist = this.m_20280_(this.m_5448_());
         if (this.getAnimation() == ANIMATION_PECK && this.getAnimationTick() == 7) {
            if (dist < 1.5D) {
               this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            }

            if (this.m_20096_()) {
               this.setFlying(false);
            }
         }

         if (this.getAnimation() != ANIMATION_PECK && this.getAnimation() != ANIMATION_SHOOT_ARROWS && dist > 3.0D && dist < 225.0D) {
            this.setAnimation(ANIMATION_SHOOT_ARROWS);
         }

         if (this.getAnimation() == ANIMATION_SHOOT_ARROWS) {
            LivingEntity target = this.m_5448_();
            this.m_21391_(target, 360.0F, 360.0F);
            if (this.isFlying()) {
               this.m_146922_(this.f_20883_);
               if ((this.getAnimationTick() == 7 || this.getAnimationTick() == 14) && this.isDirectPathBetweenPoints(this, this.m_20182_(), target.m_20182_())) {
                  this.m_5496_(IafSoundRegistry.STYMPHALIAN_BIRD_ATTACK, 1.0F, 1.0F);

                  for(int i = 0; i < 4; ++i) {
                     float wingX = (float)(this.m_20185_() + (double)(0.9F * Mth.m_14089_((float)((double)(this.m_146908_() + (float)(180 * (i % 2))) * 3.141592653589793D / 180.0D))));
                     float wingZ = (float)(this.m_20189_() + (double)(0.9F * Mth.m_14031_((float)((double)(this.m_146908_() + (float)(180 * (i % 2))) * 3.141592653589793D / 180.0D))));
                     float wingY = (float)(this.m_20186_() + 1.0D);
                     double d0 = target.m_20185_() - (double)wingX;
                     double d1 = target.m_20191_().f_82289_ - (double)wingY;
                     double d2 = target.m_20189_() - (double)wingZ;
                     double d3 = Math.sqrt(d0 * d0 + d2 * d2);
                     EntityStymphalianFeather entityarrow = new EntityStymphalianFeather((EntityType)IafEntityRegistry.STYMPHALIAN_FEATHER.get(), this.m_9236_(), this);
                     entityarrow.m_6034_((double)wingX, (double)wingY, (double)wingZ);
                     entityarrow.m_6686_(d0, d1 + d3 * 0.10000000298023223D, d2, 1.6F, (float)(14 - this.m_9236_().m_46791_().m_19028_() * 4));
                     this.m_5496_(SoundEvents.f_12382_, 1.0F, 1.0F / (this.m_217043_().m_188501_() * 0.4F + 0.8F));
                     this.m_9236_().m_7967_(entityarrow);
                  }
               }
            } else {
               this.setFlying(true);
            }
         }
      }

      boolean flying = this.isFlying() && !this.m_20096_() || this.airBorneCounter > 10 || this.getAnimation() == ANIMATION_SHOOT_ARROWS;
      if (flying && this.flyProgress < 20.0F) {
         ++this.flyProgress;
      } else if (!flying && this.flyProgress > 0.0F) {
         --this.flyProgress;
      }

      if (!this.isFlying() && this.airTarget != null && this.m_20096_() && !this.m_9236_().f_46443_) {
         this.airTarget = null;
      }

      if (this.isFlying() && this.m_5448_() == null) {
         this.flyAround();
      } else if (this.m_5448_() != null) {
         this.flyTowardsTarget();
      }

      if (!this.m_9236_().f_46443_ && this.doesWantToLand() && !this.aiFlightLaunch && this.getAnimation() != ANIMATION_SHOOT_ARROWS) {
         this.setFlying(false);
         this.airTarget = null;
      }

      if (!this.m_9236_().f_46443_ && this.m_20229_(0.0D, 0.0D, 0.0D) && !this.isFlying()) {
         this.setFlying(true);
         this.launchTicks = 0;
         this.flyTicks = 0;
         this.aiFlightLaunch = true;
      }

      if (!this.m_9236_().f_46443_ && this.m_20096_() && this.isFlying() && !this.aiFlightLaunch && this.getAnimation() != ANIMATION_SHOOT_ARROWS) {
         this.setFlying(false);
         this.airTarget = null;
      }

      if (!this.m_9236_().f_46443_ && (this.flock == null || this.flock != null && this.flock.isLeader(this)) && this.m_217043_().m_188503_(100) == 0 && !this.isFlying() && this.m_20197_().isEmpty() && !this.m_6162_() && this.m_20096_()) {
         this.setFlying(true);
         this.launchTicks = 0;
         this.flyTicks = 0;
         this.aiFlightLaunch = true;
      }

      if (!this.m_9236_().f_46443_) {
         if (this.aiFlightLaunch && this.launchTicks < 40) {
            ++this.launchTicks;
         } else {
            this.launchTicks = 0;
            this.aiFlightLaunch = false;
         }

         if (this.isFlying()) {
            ++this.flyTicks;
         } else {
            this.flyTicks = 0;
         }
      }

      if (!this.m_20096_()) {
         ++this.airBorneCounter;
      } else {
         this.airBorneCounter = 0;
      }

      if (this.getAnimation() == ANIMATION_SHOOT_ARROWS && !this.isFlying() && !this.m_9236_().f_46443_) {
         this.setFlying(true);
         this.aiFlightLaunch = true;
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean isDirectPathBetweenPoints(Entity entity, Vec3 vec1, Vec3 vec2) {
      return this.m_9236_().m_45547_(new ClipContext(vec1, vec2, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   public void flyAround() {
      if (this.airTarget != null && this.isFlying()) {
         if (!this.isTargetInAir() || this.flyTicks > 6000 || !this.isFlying()) {
            this.airTarget = null;
         }

         this.flyTowardsTarget();
      }

   }

   public void flyTowardsTarget() {
      if (this.airTarget != null && this.isTargetInAir() && this.isFlying() && this.getDistanceSquared(new Vec3((double)this.airTarget.m_123341_(), this.m_20186_(), (double)this.airTarget.m_123343_())) > 3.0F) {
         double targetX = (double)this.airTarget.m_123341_() + 0.5D - this.m_20185_();
         double targetY = (double)Math.min(this.airTarget.m_123342_(), 256) + 1.0D - this.m_20186_();
         double targetZ = (double)this.airTarget.m_123343_() + 0.5D - this.m_20189_();
         double motionX = (Math.signum(targetX) * 0.5D - this.m_20184_().f_82479_) * 0.100000000372529D * (double)this.getFlySpeed(false);
         double motionY = (Math.signum(targetY) * 0.5D - this.m_20184_().f_82480_) * 0.100000000372529D * (double)this.getFlySpeed(true);
         double motionZ = (Math.signum(targetZ) * 0.5D - this.m_20184_().f_82481_) * 0.100000000372529D * (double)this.getFlySpeed(false);
         this.m_20256_(this.m_20184_().m_82520_(motionX, motionY, motionZ));
         float angle = (float)(Math.atan2(this.m_20184_().f_82481_, this.m_20184_().f_82479_) * 180.0D / 3.141592653589793D) - 90.0F;
         float rotation = Mth.m_14177_(angle - this.m_146908_());
         this.f_20902_ = 0.5F;
         this.f_19859_ = this.m_146908_();
         this.m_146922_(this.m_146908_() + rotation);
         if (!this.isFlying()) {
            this.setFlying(true);
         }
      } else {
         this.airTarget = null;
      }

      if (this.airTarget != null && this.isTargetInAir() && this.isFlying() && this.getDistanceSquared(new Vec3((double)this.airTarget.m_123341_(), this.m_20186_(), (double)this.airTarget.m_123343_())) < 3.0F && this.doesWantToLand()) {
         this.setFlying(false);
      }

   }

   private float getFlySpeed(boolean y) {
      float speed = 2.0F;
      if (this.flock != null && !this.flock.isLeader(this) && this.m_20280_(this.flock.getLeader()) > 10.0D) {
         speed = 4.0F;
      }

      if (this.getAnimation() == ANIMATION_SHOOT_ARROWS && !y) {
         speed = (float)((double)speed * 0.05D);
      }

      return speed;
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public void m_8032_() {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.m_8032_();
   }

   protected void m_6677_(@NotNull DamageSource source) {
      if (this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_SPEAK);
      }

      super.m_6677_(source);
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.STYMPHALIAN_BIRD_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.STYMPHALIAN_BIRD_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.STYMPHALIAN_BIRD_DIE;
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.m_21051_(Attributes.f_22277_).m_22100_((double)IafConfig.stymphalianBirdTargetSearchLength);
      return spawnDataIn;
   }

   public void m_6710_(LivingEntity entity) {
      if (!this.isVictor(entity) || entity == null) {
         super.m_6710_(entity);
         if (this.flock != null && this.flock.isLeader(this) && entity != null) {
            this.flock.onLeaderAttack(entity);
         }

      }
   }

   public float getDistanceSquared(Vec3 Vector3d) {
      float f = (float)(this.m_20185_() - Vector3d.f_82479_);
      float f1 = (float)(this.m_20186_() - Vector3d.f_82480_);
      float f2 = (float)(this.m_20189_() - Vector3d.f_82481_);
      return f * f + f1 * f1 + f2 * f2;
   }

   protected boolean isTargetInAir() {
      return this.airTarget != null && (this.m_9236_().m_8055_(this.airTarget).m_60795_() || this.m_9236_().m_8055_(this.airTarget).m_60795_());
   }

   public boolean doesWantToLand() {
      if (this.flock != null && !this.flock.isLeader(this) && this.flock.getLeader() != null) {
         return this.flock.getLeader().doesWantToLand();
      } else {
         return this.flyTicks > 500 || this.flyTicks > 40 && this.flyProgress == 0.0F;
      }
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
      return new Animation[]{NO_ANIMATION, ANIMATION_PECK, ANIMATION_SHOOT_ARROWS, ANIMATION_SPEAK};
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return IafConfig.stympahlianBirdAttackAnimals;
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   static {
      VICTOR_ENTITY = SynchedEntityData.m_135353_(EntityStymphalianBird.class, EntityDataSerializers.f_135041_);
      FLYING = SynchedEntityData.m_135353_(EntityStymphalianBird.class, EntityDataSerializers.f_135035_);
      ANIMATION_PECK = Animation.create(20);
      ANIMATION_SHOOT_ARROWS = Animation.create(30);
      ANIMATION_SPEAK = Animation.create(10);
   }
}
