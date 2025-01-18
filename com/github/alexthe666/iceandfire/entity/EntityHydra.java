package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.EntityUtil;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IMultipartEntity;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityHydra extends Monster implements IAnimatedEntity, IMultipartEntity, IVillagerFear, IAnimalFear, IHasCustomizableAttributes {
   public static final int HEADS = 9;
   public static final double HEAD_HEALTH_THRESHOLD = 20.0D;
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Integer> HEAD_COUNT;
   private static final EntityDataAccessor<Integer> SEVERED_HEAD;
   private static final float[][] ROTATE;
   public boolean[] isStriking = new boolean[9];
   public float[] strikingProgress = new float[9];
   public float[] prevStrikeProgress = new float[9];
   public boolean[] isBreathing = new boolean[9];
   public float[] speakingProgress = new float[9];
   public float[] prevSpeakingProgress = new float[9];
   public float[] breathProgress = new float[9];
   public float[] prevBreathProgress = new float[9];
   public int[] breathTicks = new int[9];
   public float[] headDamageTracker = new float[9];
   private int animationTick;
   private Animation currentAnimation;
   private EntityHydraHead[] headBoxes = new EntityHydraHead[81];
   private int strikeCooldown = 0;
   private int breathCooldown = 0;
   private int lastHitHead = 0;
   private int prevHeadCount = -1;
   private int regrowHeadCooldown = 0;
   private boolean onlyRegrowOneHeadNotTwo = false;
   private float headDamageThreshold = 20.0F;

   public EntityHydra(EntityType<EntityHydra> type, Level worldIn) {
      super(type, worldIn);
      this.resetParts();
      this.headDamageThreshold = Math.max(5.0F, (float)IafConfig.hydraMaxHealth * 0.08F);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.hydraMaxHealth).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, 3.0D).m_22268_(Attributes.f_22284_, 1.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.hydraMaxHealth);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new MeleeAttackGoal(this, 1.0D, true));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, false, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity) && !(entity instanceof EntityMutlipartPart) && !(entity instanceof Enemy) || entity instanceof IBlacklistedFromStatues && ((IBlacklistedFromStatues)entity).canBeTurnedToStone();
         }
      }));
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      return false;
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity attackTarget = this.m_5448_();
      int i;
      if (attackTarget != null && this.m_142582_(attackTarget)) {
         i = this.f_19796_.m_188503_(this.getHeadCount());
         if (!this.isBreathing[i] && !this.isStriking[i]) {
            if (this.m_20270_(attackTarget) < 6.0F) {
               if (this.strikeCooldown == 0 && this.strikingProgress[i] == 0.0F) {
                  this.isBreathing[i] = false;
                  this.isStriking[i] = true;
                  this.m_9236_().m_7605_(this, (byte)(40 + i));
                  this.strikeCooldown = 3;
               }
            } else if (this.f_19796_.m_188499_() && this.breathCooldown == 0) {
               this.isBreathing[i] = true;
               this.isStriking[i] = false;
               this.m_9236_().m_7605_(this, (byte)(50 + i));
               this.breathCooldown = 15;
            }
         }
      }

      for(i = 0; i < 9; ++i) {
         boolean striking = this.isStriking[i];
         boolean breathing = this.isBreathing[i];
         this.prevStrikeProgress[i] = this.strikingProgress[i];
         if (striking && this.strikingProgress[i] > 9.0F) {
            this.isStriking[i] = false;
            if (attackTarget != null && this.m_20270_(attackTarget) < 6.0F) {
               attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
               attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 100, 3, false, false));
               attackTarget.m_147240_(0.25D, this.m_20185_() - attackTarget.m_20185_(), this.m_20189_() - attackTarget.m_20189_());
            }
         }

         int var10002;
         if (breathing) {
            if (this.f_19797_ % 7 == 0 && attackTarget != null && i < this.getHeadCount()) {
               Vec3 Vector3d = this.m_20252_(1.0F);
               if (this.f_19796_.m_188501_() < 0.2F) {
                  this.m_5496_(IafSoundRegistry.HYDRA_SPIT, this.m_6121_(), this.m_6100_());
               }

               double headPosX = this.headBoxes[i].m_20185_() + Vector3d.f_82479_;
               double headPosY = this.headBoxes[i].m_20186_() + 1.2999999523162842D;
               double headPosZ = this.headBoxes[i].m_20189_() + Vector3d.f_82481_;
               double d2 = attackTarget.m_20185_() - headPosX + this.f_19796_.m_188583_() * 0.4D;
               double d3 = attackTarget.m_20186_() + (double)attackTarget.m_20192_() - headPosY + this.f_19796_.m_188583_() * 0.4D;
               double d4 = attackTarget.m_20189_() - headPosZ + this.f_19796_.m_188583_() * 0.4D;
               EntityHydraBreath entitylargefireball = new EntityHydraBreath((EntityType)IafEntityRegistry.HYDRA_BREATH.get(), this.m_9236_(), this, d2, d3, d4);
               entitylargefireball.m_6034_(headPosX, headPosY, headPosZ);
               if (!this.m_9236_().f_46443_) {
                  this.m_9236_().m_7967_(entitylargefireball);
               }
            }

            if (this.isBreathing[i] && (attackTarget == null || !attackTarget.m_6084_() || this.breathTicks[i] > 60) && !this.m_9236_().f_46443_) {
               this.isBreathing[i] = false;
               this.breathTicks[i] = 0;
               this.breathCooldown = 15;
               this.m_9236_().m_7605_(this, (byte)(60 + i));
            }

            var10002 = this.breathTicks[i]++;
         } else {
            this.breathTicks[i] = 0;
         }

         float[] var10000;
         if (striking && this.strikingProgress[i] < 10.0F) {
            var10000 = this.strikingProgress;
            var10000[i] += 2.5F;
         } else if (!striking && this.strikingProgress[i] > 0.0F) {
            var10000 = this.strikingProgress;
            var10000[i] -= 2.5F;
         }

         this.prevSpeakingProgress[i] = this.speakingProgress[i];
         if (this.speakingProgress[i] > 0.0F) {
            var10000 = this.speakingProgress;
            var10000[i] -= 0.1F;
         }

         this.prevBreathProgress[i] = this.breathProgress[i];
         if (breathing && this.breathProgress[i] < 10.0F) {
            var10002 = this.breathProgress[i]++;
         } else if (!breathing && this.breathProgress[i] > 0.0F) {
            var10002 = this.breathProgress[i]--;
         }
      }

      if (this.strikeCooldown > 0) {
         --this.strikeCooldown;
      }

      if (this.breathCooldown > 0) {
         --this.breathCooldown;
      }

      if (this.getHeadCount() == 1 && this.getSeveredHead() != -1) {
         this.setSeveredHead(-1);
      }

      if (this.getHeadCount() == 1 && !this.m_6060_()) {
         this.setHeadCount(2);
         this.setSeveredHead(1);
         this.onlyRegrowOneHeadNotTwo = true;
      }

      if (this.getSeveredHead() != -1 && this.getSeveredHead() < this.getHeadCount()) {
         this.setSeveredHead(Mth.m_14045_(this.getSeveredHead(), 0, this.getHeadCount() - 1));
         ++this.regrowHeadCooldown;
         if (this.regrowHeadCooldown >= 100) {
            this.headDamageTracker[this.getSeveredHead()] = 0.0F;
            this.setSeveredHead(-1);
            if (this.m_6060_()) {
               this.setHeadCount(this.getHeadCount() - 1);
            } else {
               this.m_5496_(IafSoundRegistry.HYDRA_REGEN_HEAD, this.m_6121_(), this.m_6100_());
               if (!this.onlyRegrowOneHeadNotTwo) {
                  this.setHeadCount(this.getHeadCount() + 1);
               }
            }

            this.onlyRegrowOneHeadNotTwo = false;
            this.regrowHeadCooldown = 0;
         }
      } else {
         this.regrowHeadCooldown = 0;
      }

   }

   public void resetParts() {
      this.clearParts();
      this.headBoxes = new EntityHydraHead[18];

      for(int i = 0; i < this.getHeadCount(); ++i) {
         float maxAngle = 5.0F;
         this.headBoxes[i] = new EntityHydraHead(this, 3.2F, ROTATE[this.getHeadCount() - 1][i] * 1.1F, 1.0F, 0.75F, 1.75F, 1.0F, i, false);
         this.headBoxes[9 + i] = new EntityHydraHead(this, 2.1F, ROTATE[this.getHeadCount() - 1][i] * 1.1F, 1.0F, 0.75F, 0.75F, 1.0F, i, true);
         this.headBoxes[i].m_20359_(this);
         this.headBoxes[9 + i].m_20359_(this);
         this.headBoxes[i].setParent(this);
         this.headBoxes[9 + i].setParent(this);
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.prevHeadCount != this.getHeadCount()) {
         this.resetParts();
      }

      float partY = 1.0F - this.f_267362_.m_267731_() * 0.5F;

      int level;
      for(level = 0; level < this.getHeadCount(); ++level) {
         this.headBoxes[level].m_6034_(this.headBoxes[level].m_20185_(), this.m_20186_() + (double)partY, this.headBoxes[level].m_20189_());
         EntityUtil.updatePart(this.headBoxes[level], this);
         this.headBoxes[9 + level].m_6034_(this.headBoxes[9 + level].m_20185_(), this.m_20186_() + (double)partY, this.headBoxes[9 + level].m_20189_());
         EntityUtil.updatePart(this.headBoxes[10], this);
      }

      if (this.getHeadCount() > 1 && !this.m_6060_() && this.m_21223_() < this.m_21233_() && this.f_19797_ % 30 == 0) {
         level = this.getHeadCount() - 1;
         if (this.getSeveredHead() != -1) {
            --level;
         }

         this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 30, level, false, false));
      }

      if (this.m_6060_()) {
         this.m_21195_(MobEffects.f_19605_);
      }

      this.prevHeadCount = this.getHeadCount();
   }

   private void clearParts() {
      EntityHydraHead[] var1 = this.headBoxes;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Entity entity = var1[var3];
         if (entity != null) {
            entity.m_142687_(RemovalReason.DISCARDED);
         }
      }

   }

   public void m_142687_(@NotNull RemovalReason reason) {
      this.clearParts();
      super.m_142687_(reason);
   }

   protected void m_6677_(@NotNull DamageSource source) {
      this.speakingProgress[this.f_19796_.m_188503_(this.getHeadCount())] = 1.0F;
      super.m_6677_(source);
   }

   public void m_8032_() {
      this.speakingProgress[this.f_19796_.m_188503_(this.getHeadCount())] = 1.0F;
      super.m_8032_();
   }

   public int m_8100_() {
      return 100 / this.getHeadCount();
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128405_("HeadCount", this.getHeadCount());
      compound.m_128405_("SeveredHead", this.getSeveredHead());

      for(int i = 0; i < 9; ++i) {
         compound.m_128350_("HeadDamage" + i, this.headDamageTracker[i]);
      }

   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.setHeadCount(compound.m_128451_("HeadCount"));
      this.setSeveredHead(compound.m_128451_("SeveredHead"));

      for(int i = 0; i < 9; ++i) {
         this.headDamageTracker[i] = compound.m_128457_("HeadDamage" + i);
      }

      this.setConfigurableAttributes();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(HEAD_COUNT, 3);
      this.f_19804_.m_135372_(SEVERED_HEAD, -1);
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      if (this.lastHitHead > this.getHeadCount()) {
         this.lastHitHead = this.getHeadCount() - 1;
      }

      int headIndex = this.lastHitHead;
      float[] var10000 = this.headDamageTracker;
      var10000[headIndex] += amount;
      if (this.headDamageTracker[headIndex] > this.headDamageThreshold && (this.getSeveredHead() == -1 || this.getSeveredHead() >= this.getHeadCount())) {
         this.headDamageTracker[headIndex] = 0.0F;
         this.regrowHeadCooldown = 0;
         this.setSeveredHead(headIndex);
         this.m_5496_(SoundEvents.f_12004_, this.m_6121_(), this.m_6100_());
      }

      if (this.m_21223_() <= amount + 5.0F && this.getHeadCount() > 1 && !source.m_269533_(DamageTypeTags.f_268738_)) {
         amount = 0.0F;
      }

      return super.m_6469_(source, amount);
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
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
      return new Animation[0];
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public int getHeadCount() {
      return Mth.m_14045_((Integer)this.f_19804_.m_135370_(HEAD_COUNT), 1, 9);
   }

   public void setHeadCount(int count) {
      this.f_19804_.m_135381_(HEAD_COUNT, Mth.m_14045_(count, 1, 9));
   }

   public int getSeveredHead() {
      return Mth.m_14045_((Integer)this.f_19804_.m_135370_(SEVERED_HEAD), -1, 9);
   }

   public void setSeveredHead(int count) {
      this.f_19804_.m_135381_(SEVERED_HEAD, Mth.m_14045_(count, -1, 9));
   }

   public void m_7822_(byte id) {
      int index;
      if (id >= 40 && id <= 48) {
         index = id - 40;
         this.isStriking[Mth.m_14045_(index, 0, 8)] = true;
      } else if (id >= 50 && id <= 58) {
         index = id - 50;
         this.isBreathing[Mth.m_14045_(index, 0, 8)] = true;
      } else if (id >= 60 && id <= 68) {
         index = id - 60;
         this.isBreathing[Mth.m_14045_(index, 0, 8)] = false;
      } else {
         super.m_7822_(id);
      }

   }

   public boolean m_7301_(MobEffectInstance potioneffectIn) {
      return potioneffectIn.m_19544_() != MobEffects.f_19614_ && super.m_7301_(potioneffectIn);
   }

   public void onHitHead(float damage, int headIndex) {
      this.lastHitHead = headIndex;
   }

   public void triggerHeadFlags(int index) {
      this.lastHitHead = index;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.HYDRA_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.HYDRA_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.HYDRA_DIE;
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityHydra.class, EntityDataSerializers.f_135028_);
      HEAD_COUNT = SynchedEntityData.m_135353_(EntityHydra.class, EntityDataSerializers.f_135028_);
      SEVERED_HEAD = SynchedEntityData.m_135353_(EntityHydra.class, EntityDataSerializers.f_135028_);
      ROTATE = new float[][]{{0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, {10.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, {10.0F, 0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, {25.0F, 10.0F, -10.0F, -25.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F}, {30.0F, 15.0F, 0.0F, -15.0F, -30.0F, 0.0F, 0.0F, 0.0F, 0.0F}, {40.0F, 25.0F, 5.0F, -5.0F, -25.0F, -40.0F, 0.0F, 0.0F, 0.0F}, {40.0F, 30.0F, 15.0F, 0.0F, -15.0F, -30.0F, -40.0F, 0.0F, 0.0F}, {45.0F, 30.0F, 20.0F, 5.0F, -5.0F, -20.0F, -30.0F, -45.0F, 0.0F}, {50.0F, 37.0F, 25.0F, 15.0F, 0.0F, -15.0F, -25.0F, -37.0F, -50.0F}};
   }
}
