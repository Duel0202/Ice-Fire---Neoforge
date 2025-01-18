package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.ai.FlyingAITarget;
import com.github.alexthe666.iceandfire.entity.ai.SeaSerpentAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.SeaSerpentAIGetInWater;
import com.github.alexthe666.iceandfire.entity.ai.SeaSerpentAIJump;
import com.github.alexthe666.iceandfire.entity.ai.SeaSerpentAIMeleeJump;
import com.github.alexthe666.iceandfire.entity.ai.SeaSerpentAIRandomSwimming;
import com.github.alexthe666.iceandfire.entity.ai.SeaSerpentPathNavigator;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.EntityUtil;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IMultipartEntity;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumSeaSerpent;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.common.IPlantable;
import org.jetbrains.annotations.NotNull;

public class EntitySeaSerpent extends Animal implements IAnimatedEntity, IMultipartEntity, IVillagerFear, IAnimalFear, IHasCustomizableAttributes {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_SPEAK = Animation.create(15);
   public static final Animation ANIMATION_ROAR = Animation.create(40);
   public static final int TIME_BETWEEN_ROARS = 300;
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Float> SCALE;
   private static final EntityDataAccessor<Boolean> JUMPING;
   private static final EntityDataAccessor<Boolean> BREATHING;
   private static final EntityDataAccessor<Boolean> ANCIENT;
   private static final Predicate<Entity> NOT_SEA_SERPENT;
   private static final Predicate<Entity> NOT_SEA_SERPENT_IN_WATER;
   public int swimCycle;
   public float jumpProgress = 0.0F;
   public float wantJumpProgress = 0.0F;
   public float jumpRot = 0.0F;
   public float prevJumpRot = 0.0F;
   public float breathProgress = 0.0F;
   public boolean attackDecision = false;
   private int animationTick;
   private Animation currentAnimation;
   private EntityMutlipartPart[] segments = new EntityMutlipartPart[9];
   private float lastScale;
   private boolean isLandNavigator;
   private boolean changedSwimBehavior = false;
   public int jumpCooldown = 0;
   private int ticksSinceRoar = 0;
   private boolean isBreathing;
   private final float[] tailYaw = new float[5];
   private final float[] prevTailYaw = new float[5];
   private final float[] tailPitch = new float[5];
   private final float[] prevTailPitch = new float[5];

   public EntitySeaSerpent(EntityType<EntitySeaSerpent> t, Level worldIn) {
      super(t, worldIn);
      this.switchNavigator(false);
      this.f_19811_ = true;
      this.resetParts(1.0F);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
   }

   private static BlockPos clampBlockPosToWater(Entity entity, Level world, BlockPos pos) {
      BlockPos topY = new BlockPos(pos.m_123341_(), entity.m_146904_(), pos.m_123343_());

      BlockPos bottomY;
      for(bottomY = new BlockPos(pos.m_123341_(), entity.m_146904_(), pos.m_123343_()); isWaterBlock(world, topY) && topY.m_123342_() < world.m_151558_(); topY = topY.m_7494_()) {
      }

      while(isWaterBlock(world, bottomY) && bottomY.m_123342_() > 0) {
         bottomY = bottomY.m_7495_();
      }

      return new BlockPos(pos.m_123341_(), Mth.m_14045_(pos.m_123342_(), bottomY.m_123342_() + 1, topY.m_123342_() - 1), pos.m_123343_());
   }

   public static boolean isWaterBlock(Level world, BlockPos pos) {
      return world.m_6425_(pos).m_205070_(FluidTags.f_13131_);
   }

   @NotNull
   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public boolean m_6000_(double x, double y, double z) {
      return true;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new SeaSerpentAIGetInWater(this));
      this.f_21345_.m_25352_(1, new SeaSerpentAIMeleeJump(this));
      this.f_21345_.m_25352_(1, new SeaSerpentAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(2, new SeaSerpentAIRandomSwimming(this, 1.0D, 2));
      this.f_21345_.m_25352_(3, new SeaSerpentAIJump(this, 4));
      this.f_21345_.m_25352_(4, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21346_.m_25352_(1, (new HurtByTargetGoal(this, new Class[]{EntityMutlipartPart.class})).m_26044_(new Class[0]));
      this.f_21346_.m_25352_(2, new FlyingAITarget(this, LivingEntity.class, 150, false, false, NOT_SEA_SERPENT_IN_WATER));
      this.f_21346_.m_25352_(3, new FlyingAITarget(this, Player.class, 0, false, false, NOT_SEA_SERPENT));
   }

   public int m_213860_() {
      return this.isAncient() ? 30 : 15;
   }

   public void m_6138_() {
      List<Entity> entities = this.m_9236_().m_45933_(this, this.m_20191_().m_82363_(0.20000000298023224D, 0.0D, 0.20000000298023224D));
      entities.stream().filter((entity) -> {
         return !(entity instanceof EntityMutlipartPart) && entity.m_6094_();
      }).forEach((entity) -> {
         entity.m_7334_(this);
      });
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new GroundPathNavigation(this, this.m_9236_());
         this.f_21344_.m_7008_(true);
         this.isLandNavigator = true;
      } else {
         this.f_21342_ = new EntitySeaSerpent.SwimmingMoveHelper(this);
         this.f_21344_ = new SeaSerpentPathNavigator(this, this.m_9236_());
         this.isLandNavigator = false;
      }

   }

   public boolean isDirectPathBetweenPoints(BlockPos pos) {
      Vec3 vector3d = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
      Vec3 bector3d1 = new Vec3((double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.5D, (double)pos.m_123343_() + 0.5D);
      return this.m_9236_().m_45547_(new ClipContext(vector3d, bector3d1, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.seaSerpentBaseHealth).m_22268_(Attributes.f_22279_, 0.15D).m_22268_(Attributes.f_22281_, 1.0D).m_22268_(Attributes.f_22277_, (double)Math.min(2048, IafConfig.dragonTargetSearchLength)).m_22268_(Attributes.f_22284_, 3.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.seaSerpentBaseHealth);
      this.m_21051_(Attributes.f_22277_).m_22100_((double)Math.min(2048, IafConfig.dragonTargetSearchLength));
      this.updateAttributes();
   }

   public void resetParts(float scale) {
      this.clearParts();
      this.segments = new EntityMutlipartPart[9];

      for(int i = 0; i < this.segments.length; ++i) {
         Object parentToSet;
         if (i > 3) {
            parentToSet = i <= 4 ? this : this.segments[i - 1];
            this.segments[i] = new EntitySlowPart((Entity)parentToSet, 0.5F * scale, 180.0F, 0.0F, 0.5F * scale, 0.5F * scale, 1.0F);
         } else {
            parentToSet = i == 0 ? this : this.segments[i - 1];
            this.segments[i] = new EntitySlowPart((Entity)parentToSet, -0.4F * scale, 180.0F, 0.0F, 0.45F * scale, 0.4F * scale, 1.0F);
         }

         this.segments[i].m_20359_(this);
      }

   }

   public void onUpdateParts() {
      EntityMutlipartPart[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EntityMutlipartPart entity = var1[var3];
         EntityUtil.updatePart(entity, this);
      }

   }

   private void clearParts() {
      EntityMutlipartPart[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EntityMutlipartPart entity = var1[var3];
         if (entity != null) {
            entity.m_142687_(RemovalReason.DISCARDED);
         }
      }

   }

   public void m_142687_(@NotNull RemovalReason reason) {
      this.clearParts();
      super.m_142687_(reason);
   }

   @NotNull
   public EntityDimensions m_6972_(@NotNull Pose poseIn) {
      return this.m_6095_().m_20680_().m_20388_(this.m_6134_());
   }

   public float m_6134_() {
      return this.getSeaSerpentScale();
   }

   public void m_6210_() {
      super.m_6210_();
      float scale = this.getSeaSerpentScale();
      if (scale != this.lastScale) {
         this.resetParts(this.getSeaSerpentScale());
      }

      this.lastScale = scale;
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(ANIMATION_BITE);
         return true;
      } else {
         return false;
      }
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.jumpCooldown > 0) {
         --this.jumpCooldown;
      }

      this.m_6210_();
      this.onUpdateParts();
      if (this.m_20069_()) {
         this.spawnParticlesAroundEntity(ParticleTypes.f_123795_, this, (int)this.getSeaSerpentScale());
      }

      if (!this.m_9236_().f_46443_ && this.m_9236_().m_46791_() == Difficulty.PEACEFUL) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      if (this.m_5448_() != null && !this.m_5448_().m_6084_()) {
         this.m_6710_((LivingEntity)null);
      }

      int i;
      for(i = 0; i < this.tailYaw.length; ++i) {
         this.prevTailYaw[i] = this.tailYaw[i];
      }

      for(i = 0; i < this.tailPitch.length; ++i) {
         this.prevTailPitch[i] = this.tailPitch[i];
      }

      this.tailYaw[0] = this.f_20883_;
      this.tailPitch[0] = this.m_146909_();

      for(i = 1; i < this.tailYaw.length; ++i) {
         this.tailYaw[i] = this.prevTailYaw[i - 1];
      }

      for(i = 1; i < this.tailPitch.length; ++i) {
         this.tailPitch[i] = this.prevTailPitch[i - 1];
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public float getPieceYaw(int index, float partialTicks) {
      return index < this.segments.length && index >= 0 ? this.prevTailYaw[index] + (this.tailYaw[index] - this.prevTailYaw[index]) * partialTicks : 0.0F;
   }

   public float getPiecePitch(int index, float partialTicks) {
      return index < this.segments.length && index >= 0 ? this.prevTailPitch[index] + (this.tailPitch[index] - this.prevTailPitch[index]) * partialTicks : 0.0F;
   }

   private void spawnParticlesAroundEntity(ParticleOptions type, Entity entity, int count) {
      for(int i = 0; i < count; ++i) {
         int x = (int)Math.round(entity.m_20185_() + (double)(this.f_19796_.m_188501_() * entity.m_20205_() * 2.0F) - (double)entity.m_20205_());
         int y = (int)Math.round(entity.m_20186_() + 0.5D + (double)(this.f_19796_.m_188501_() * entity.m_20206_()));
         int z = (int)Math.round(entity.m_20189_() + (double)(this.f_19796_.m_188501_() * entity.m_20205_() * 2.0F) - (double)entity.m_20205_());
         if (this.m_9236_().m_8055_(new BlockPos(x, y, z)).m_60713_(Blocks.f_49990_)) {
            this.m_9236_().m_7106_(type, (double)x, (double)y, (double)z, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   private void spawnSlamParticles(ParticleOptions type) {
      for(int i = 0; (float)i < this.getSeaSerpentScale() * 3.0F; ++i) {
         for(int i1 = 0; i1 < 5; ++i1) {
            double motionX = this.m_217043_().m_188583_() * 0.07D;
            double motionY = this.m_217043_().m_188583_() * 0.07D;
            double motionZ = this.m_217043_().m_188583_() * 0.07D;
            float radius = 1.25F * this.getSeaSerpentScale();
            float angle = 0.017453292F * this.f_20883_ + (float)i1 * 1.0F;
            double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
            double extraY = 0.800000011920929D;
            double extraZ = (double)(radius * Mth.m_14089_(angle));
            if (this.m_9236_().f_46443_) {
               this.m_9236_().m_6493_(type, true, this.m_20185_() + extraX, this.m_20186_() + extraY, this.m_20189_() + extraZ, motionX, motionY, motionZ);
            }
         }
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(SCALE, 0.0F);
      this.f_19804_.m_135372_(JUMPING, false);
      this.f_19804_.m_135372_(BREATHING, false);
      this.f_19804_.m_135372_(ANCIENT, false);
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128405_("TicksSinceRoar", this.ticksSinceRoar);
      compound.m_128405_("JumpCooldown", this.jumpCooldown);
      compound.m_128350_("Scale", this.getSeaSerpentScale());
      compound.m_128379_("JumpingOutOfWater", this.isJumpingOutOfWater());
      compound.m_128379_("AttackDecision", this.attackDecision);
      compound.m_128379_("Breathing", this.isBreathing());
      compound.m_128379_("Ancient", this.isAncient());
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.ticksSinceRoar = compound.m_128451_("TicksSinceRoar");
      this.jumpCooldown = compound.m_128451_("JumpCooldown");
      this.setSeaSerpentScale(compound.m_128457_("Scale"));
      this.setJumpingOutOfWater(compound.m_128471_("JumpingOutOfWater"));
      this.attackDecision = compound.m_128471_("AttackDecision");
      this.setBreathing(compound.m_128471_("Breathing"));
      this.setAncient(compound.m_128471_("Ancient"));
      this.setConfigurableAttributes();
   }

   private void updateAttributes() {
      this.m_21051_(Attributes.f_22279_).m_22100_(Math.min(0.25D, 0.15D * (double)this.getSeaSerpentScale() * (double)this.getAncientModifier()));
      this.m_21051_(Attributes.f_22281_).m_22100_(Math.max(4.0D, IafConfig.seaSerpentAttackStrength * (double)this.getSeaSerpentScale() * (double)this.getAncientModifier()));
      this.m_21051_(Attributes.f_22276_).m_22100_(Math.max(10.0D, IafConfig.seaSerpentBaseHealth * (double)this.getSeaSerpentScale() * (double)this.getAncientModifier()));
      this.m_21051_(Attributes.f_22277_).m_22100_((double)Math.min(2048, IafConfig.dragonTargetSearchLength));
      this.m_5634_(30.0F * this.getSeaSerpentScale());
   }

   private float getAncientModifier() {
      return this.isAncient() ? 1.5F : 1.0F;
   }

   public float getSeaSerpentScale() {
      return (Float)this.f_19804_.m_135370_(SCALE);
   }

   private void setSeaSerpentScale(float scale) {
      this.f_19804_.m_135381_(SCALE, scale);
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public boolean isJumpingOutOfWater() {
      return (Boolean)this.f_19804_.m_135370_(JUMPING);
   }

   public void setJumpingOutOfWater(boolean jump) {
      this.f_19804_.m_135381_(JUMPING, jump);
   }

   public boolean isAncient() {
      return (Boolean)this.f_19804_.m_135370_(ANCIENT);
   }

   public void setAncient(boolean ancient) {
      this.f_19804_.m_135381_(ANCIENT, ancient);
   }

   public boolean isBreathing() {
      if (this.m_9236_().f_46443_) {
         boolean breathing = (Boolean)this.f_19804_.m_135370_(BREATHING);
         this.isBreathing = breathing;
         return breathing;
      } else {
         return this.isBreathing;
      }
   }

   public void setBreathing(boolean breathing) {
      this.f_19804_.m_135381_(BREATHING, breathing);
      if (!this.m_9236_().f_46443_) {
         this.isBreathing = breathing;
      }

   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.m_9236_().f_46443_ && this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      boolean breathing = this.isBreathing() && this.getAnimation() != ANIMATION_BITE && this.getAnimation() != ANIMATION_ROAR;
      boolean jumping = !this.m_20069_() && !this.m_20096_() && this.m_20184_().f_82480_ >= 0.0D;
      boolean wantJumping = false;
      boolean ground = !this.m_20069_() && this.m_20096_();
      boolean prevJumping = this.isJumpingOutOfWater();
      ++this.ticksSinceRoar;
      ++this.jumpCooldown;
      this.prevJumpRot = this.jumpRot;
      if (this.ticksSinceRoar > 300 && this.isAtSurface() && this.getAnimation() != ANIMATION_BITE && this.jumpProgress == 0.0F && !this.isJumpingOutOfWater()) {
         this.setAnimation(ANIMATION_ROAR);
         this.ticksSinceRoar = 0;
      }

      if (this.getAnimation() == ANIMATION_ROAR && this.getAnimationTick() == 1) {
         this.m_5496_(IafSoundRegistry.SEA_SERPENT_ROAR, this.m_6121_() + 1.0F, 1.0F);
      }

      if (this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 5) {
         this.m_5496_(IafSoundRegistry.SEA_SERPENT_BITE, this.m_6121_(), 1.0F);
      }

      if (this.isJumpingOutOfWater() && isWaterBlock(this.m_9236_(), this.m_20183_().m_6630_(2))) {
         this.setJumpingOutOfWater(false);
      }

      if (this.swimCycle < 38) {
         this.swimCycle += 2;
      } else {
         this.swimCycle = 0;
      }

      if (breathing && this.breathProgress < 20.0F) {
         this.breathProgress += 0.5F;
      } else if (!breathing && this.breathProgress > 0.0F) {
         this.breathProgress -= 0.5F;
      }

      if (jumping && this.jumpProgress < 10.0F) {
         this.jumpProgress += 0.5F;
      } else if (!jumping && this.jumpProgress > 0.0F) {
         this.jumpProgress -= 0.5F;
      }

      if (wantJumping && this.wantJumpProgress < 10.0F) {
         this.wantJumpProgress += 2.0F;
      } else if (!wantJumping && this.wantJumpProgress > 0.0F) {
         this.wantJumpProgress -= 2.0F;
      }

      if (this.isJumpingOutOfWater() && this.jumpRot < 1.0F) {
         this.jumpRot += 0.1F;
      } else if (!this.isJumpingOutOfWater() && this.jumpRot > 0.0F) {
         this.jumpRot -= 0.1F;
      }

      if (prevJumping && !this.isJumpingOutOfWater()) {
         this.m_5496_(IafSoundRegistry.SEA_SERPENT_SPLASH, 5.0F, 0.75F);
         this.spawnSlamParticles(ParticleTypes.f_123795_);
         this.doSplashDamage();
      }

      if (!ground && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (ground && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      this.m_146926_(Mth.m_14036_((float)this.m_20184_().f_82480_ * 20.0F, -90.0F, 90.0F));
      if (this.changedSwimBehavior) {
         this.changedSwimBehavior = false;
      }

      if (!this.m_9236_().f_46443_) {
         if (this.attackDecision) {
            this.setBreathing(false);
         }

         if (this.m_5448_() != null && this.getAnimation() != ANIMATION_ROAR) {
            if (!this.attackDecision) {
               if (!this.m_5448_().m_20069_() || !this.m_142582_(this.m_5448_()) || this.m_20270_(this.m_5448_()) < 30.0F * this.getSeaSerpentScale()) {
                  this.attackDecision = true;
               }

               if (!this.attackDecision) {
                  this.shoot(this.m_5448_());
               }
            } else if (this.m_20280_(this.m_5448_()) > (double)(200.0F * this.getSeaSerpentScale())) {
               this.attackDecision = false;
            }
         } else {
            this.setBreathing(false);
         }
      }

      if (this.getAnimation() == ANIMATION_BITE && this.m_5448_() != null && (this.isTouchingMob(this.m_5448_()) || this.m_20280_(this.m_5448_()) < 50.0D)) {
         this.hurtMob(this.m_5448_());
      }

      this.breakBlock();
      if (!this.m_9236_().f_46443_ && this.m_20159_() && this.m_20201_() instanceof Boat) {
         Boat boat = (Boat)this.m_20201_();
         boat.m_142687_(RemovalReason.KILLED);
         this.m_8127_();
      }

   }

   private boolean isAtSurface() {
      BlockPos pos = this.m_20183_();
      return isWaterBlock(this.m_9236_(), pos.m_7495_()) && !isWaterBlock(this.m_9236_(), pos.m_7494_());
   }

   private void doSplashDamage() {
      double getWidth = 2.0D * (double)this.getSeaSerpentScale();
      List<Entity> list = this.m_9236_().m_6249_(this, this.m_20191_().m_82377_(getWidth, getWidth * 0.5D, getWidth), NOT_SEA_SERPENT);
      Iterator var4 = list.iterator();

      while(var4.hasNext()) {
         Entity entity = (Entity)var4.next();
         if (entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity)) {
            entity.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            this.destroyBoat(entity);
            double xRatio = this.m_20185_() - entity.m_20185_();
            double zRatio = this.m_20189_() - entity.m_20189_();
            float f = Mth.m_14116_((float)(xRatio * xRatio + zRatio * zRatio));
            float strength = 0.3F * this.getSeaSerpentScale();
            entity.m_20256_(entity.m_20184_().m_82542_(0.5D, 1.0D, 0.5D));
            entity.m_20256_(entity.m_20184_().m_82520_(xRatio / (double)f * (double)strength, (double)strength, zRatio / (double)f * (double)strength));
         }
      }

   }

   public void destroyBoat(Entity sailor) {
      if (sailor.m_20202_() != null && sailor.m_20202_() instanceof Boat && !this.m_9236_().f_46443_) {
         Boat boat = (Boat)sailor.m_20202_();
         boat.m_142687_(RemovalReason.KILLED);
         if (this.m_9236_().m_46469_().m_46207_(GameRules.f_46137_)) {
            int j;
            for(j = 0; j < 3; ++j) {
               boat.m_5552_(new ItemStack(boat.m_28554_().m_38434_().m_5456_()), 0.0F);
            }

            for(j = 0; j < 2; ++j) {
               boat.m_19983_(new ItemStack(Items.f_42398_));
            }
         }
      }

   }

   private boolean isPreyAtSurface() {
      if (this.m_5448_() != null) {
         BlockPos pos = this.m_5448_().m_20183_();
         return !isWaterBlock(this.m_9236_(), pos.m_6630_((int)Math.ceil((double)this.m_5448_().m_20206_())));
      } else {
         return false;
      }
   }

   private void hurtMob(LivingEntity entity) {
      if (this.getAnimation() == ANIMATION_BITE && entity != null && this.getAnimationTick() == 6) {
         this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         this.attackDecision = this.m_217043_().m_188499_();
      }

   }

   public void moveJumping() {
      float velocity = 0.5F;
      double x = (double)(-Mth.m_14031_(this.m_146908_() * 0.017453292F) * Mth.m_14089_(this.m_146909_() * 0.017453292F));
      double z = (double)(Mth.m_14089_(this.m_146908_() * 0.017453292F) * Mth.m_14089_(this.m_146909_() * 0.017453292F));
      float f = Mth.m_14116_((float)(x * x + z * z));
      x /= (double)f;
      z /= (double)f;
      x *= (double)velocity;
      z *= (double)velocity;
      this.m_20334_(x, this.m_20184_().f_82480_, z);
   }

   public boolean isTouchingMob(Entity entity) {
      if (this.m_20191_().m_82363_(1.0D, 1.0D, 1.0D).m_82381_(entity.m_20191_())) {
         return true;
      } else {
         EntityMutlipartPart[] var2 = this.segments;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Entity segment = var2[var4];
            if (segment.m_20191_().m_82363_(1.0D, 1.0D, 1.0D).m_82381_(entity.m_20191_())) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean m_6040_() {
      return true;
   }

   public void breakBlock() {
      if (IafConfig.seaSerpentGriefing) {
         for(int a = (int)Math.round(this.m_20191_().f_82288_) - 2; a <= (int)Math.round(this.m_20191_().f_82291_) + 2; ++a) {
            for(int b = (int)Math.round(this.m_20191_().f_82289_) - 1; b <= (int)Math.round(this.m_20191_().f_82292_) + 2 && b <= 127; ++b) {
               for(int c = (int)Math.round(this.m_20191_().f_82290_) - 2; c <= (int)Math.round(this.m_20191_().f_82293_) + 2; ++c) {
                  BlockPos pos = new BlockPos(a, b, c);
                  BlockState state = this.m_9236_().m_8055_(pos);
                  FluidState fluidState = this.m_9236_().m_6425_(pos);
                  net.minecraft.world.level.block.Block block = state.m_60734_();
                  if (!state.m_60795_() && !state.m_60808_(this.m_9236_(), pos).m_83281_() && (state.m_60734_() instanceof IPlantable || state.m_60734_() instanceof LeavesBlock) && fluidState.m_76178_() && block != Blocks.f_50016_ && !this.m_9236_().f_46443_) {
                     this.m_9236_().m_46961_(pos, true);
                  }
               }
            }
         }
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setVariant(this.m_217043_().m_188503_(7));
      boolean ancient = this.m_217043_().m_188503_(16) == 1;
      if (ancient) {
         this.setAncient(true);
         this.setSeaSerpentScale(6.0F + this.m_217043_().m_188501_() * 3.0F);
      } else {
         this.setSeaSerpentScale(1.5F + this.m_217043_().m_188501_() * 4.0F);
      }

      this.updateAttributes();
      return spawnDataIn;
   }

   public void onWorldSpawn(RandomSource random) {
      this.setVariant(random.m_188503_(7));
      boolean ancient = random.m_188503_(15) == 1;
      if (ancient) {
         this.setAncient(true);
         this.setSeaSerpentScale(6.0F + random.m_188501_() * 3.0F);
      } else {
         this.setSeaSerpentScale(1.5F + random.m_188501_() * 4.0F);
      }

      this.updateAttributes();
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      return null;
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
      return new Animation[]{ANIMATION_BITE, ANIMATION_ROAR, ANIMATION_SPEAK};
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.SEA_SERPENT_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.SEA_SERPENT_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.SEA_SERPENT_DIE;
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

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean isBlinking() {
      return this.f_19797_ % 50 > 43;
   }

   private void shoot(LivingEntity entity) {
      if (!this.attackDecision) {
         if (!this.m_20069_()) {
            this.setBreathing(false);
            this.attackDecision = true;
         }

         if (this.isBreathing()) {
            if (this.f_19797_ % 40 == 0) {
               this.m_5496_(IafSoundRegistry.SEA_SERPENT_BREATH, 4.0F, 1.0F);
            }

            if (this.f_19797_ % 10 == 0) {
               this.m_146922_(this.f_20883_);
               float f1 = 0.0F;
               float f2 = 0.0F;
               float f3 = 0.0F;
               float headPosX = f1 + (float)(this.segments[0].m_20185_() + (double)(1.3F * this.getSeaSerpentScale() * Mth.m_14089_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
               float headPosZ = f2 + (float)(this.segments[0].m_20189_() + (double)(1.3F * this.getSeaSerpentScale() * Mth.m_14031_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
               float headPosY = f3 + (float)(this.segments[0].m_20186_() + (double)(0.2F * this.getSeaSerpentScale()));
               double d2 = entity.m_20185_() - (double)headPosX;
               double d3 = entity.m_20186_() - (double)headPosY;
               double d4 = entity.m_20189_() - (double)headPosZ;
               float inaccuracy = 1.0F;
               d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               EntitySeaSerpentBubbles entitylargefireball = new EntitySeaSerpentBubbles((EntityType)IafEntityRegistry.SEA_SERPENT_BUBBLES.get(), this.m_9236_(), this, d2, d3, d4);
               entitylargefireball.m_6034_((double)headPosX, (double)headPosY, (double)headPosZ);
               if (!this.m_9236_().f_46443_) {
                  this.m_9236_().m_7967_(entitylargefireball);
               }

               if (!entity.m_6084_() || entity == null) {
                  this.setBreathing(false);
                  this.attackDecision = this.m_217043_().m_188499_();
               }
            }
         } else {
            this.setBreathing(true);
         }
      }

      this.m_21391_(entity, 360.0F, 360.0F);
   }

   public EnumSeaSerpent getEnum() {
      switch(this.getVariant()) {
      case 1:
         return EnumSeaSerpent.BRONZE;
      case 2:
         return EnumSeaSerpent.DEEPBLUE;
      case 3:
         return EnumSeaSerpent.GREEN;
      case 4:
         return EnumSeaSerpent.PURPLE;
      case 5:
         return EnumSeaSerpent.RED;
      case 6:
         return EnumSeaSerpent.TEAL;
      default:
         return EnumSeaSerpent.BLUE;
      }
   }

   public void m_7023_(@NotNull Vec3 vec) {
      if (this.m_21515_() && this.m_20069_()) {
         this.m_19920_(this.m_6113_(), vec);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
         if (this.m_5448_() == null) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.005D, 0.0D));
         }
      } else {
         super.m_7023_(vec);
      }

   }

   public boolean m_214076_(@NotNull ServerLevel world, @NotNull LivingEntity entity) {
      this.attackDecision = this.m_217043_().m_188499_();
      return this.attackDecision;
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public int m_6056_() {
      return 1000;
   }

   public void onJumpHit(LivingEntity target) {
   }

   public boolean shouldUseJumpAttack(LivingEntity attackTarget) {
      return !attackTarget.m_20069_() || this.isPreyAtSurface();
   }

   public boolean m_6673_(@NotNull DamageSource source) {
      DamageSources damageSources = this.m_9236_().m_269111_();
      return source == damageSources.m_268989_() || source == damageSources.m_269063_() || source == damageSources.m_269318_() || source.m_7639_() != null && source == damageSources.m_269564_(source.m_7639_()) || source == damageSources.m_269233_() || source.m_276093_(DamageTypes.f_268631_) || super.m_6673_(source);
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntitySeaSerpent.class, EntityDataSerializers.f_135028_);
      SCALE = SynchedEntityData.m_135353_(EntitySeaSerpent.class, EntityDataSerializers.f_135029_);
      JUMPING = SynchedEntityData.m_135353_(EntitySeaSerpent.class, EntityDataSerializers.f_135035_);
      BREATHING = SynchedEntityData.m_135353_(EntitySeaSerpent.class, EntityDataSerializers.f_135035_);
      ANCIENT = SynchedEntityData.m_135353_(EntitySeaSerpent.class, EntityDataSerializers.f_135035_);
      NOT_SEA_SERPENT = new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity instanceof LivingEntity && !(entity instanceof EntitySeaSerpent) && DragonUtils.isAlive((LivingEntity)entity);
         }
      };
      NOT_SEA_SERPENT_IN_WATER = new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity instanceof LivingEntity && !(entity instanceof EntitySeaSerpent) && DragonUtils.isAlive((LivingEntity)entity) && entity.m_20072_();
         }
      };
   }

   public class SwimmingMoveHelper extends MoveControl {
      private final EntitySeaSerpent dolphin;

      public SwimmingMoveHelper(EntitySeaSerpent dolphinIn) {
         super(dolphinIn);
         this.dolphin = dolphinIn;
      }

      public void m_8126_() {
         if (this.dolphin.m_20069_()) {
            this.dolphin.m_20256_(this.dolphin.m_20184_().m_82520_(0.0D, 0.005D, 0.0D));
         }

         if (this.f_24981_ == Operation.MOVE_TO && !this.dolphin.m_21573_().m_26571_()) {
            double d0 = this.f_24975_ - this.dolphin.m_20185_();
            double d1 = this.f_24976_ - this.dolphin.m_20186_();
            double d2 = this.f_24977_ - this.dolphin.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            if (d3 < 2.500000277905201E-7D) {
               this.f_24974_.m_21564_(0.0F);
            } else {
               float f = (float)(Mth.m_14136_(d2, d0) * 57.2957763671875D) - 90.0F;
               this.dolphin.m_146922_(this.m_24991_(this.dolphin.m_146908_(), f, 10.0F));
               this.dolphin.f_20883_ = this.dolphin.m_146908_();
               this.dolphin.f_20885_ = this.dolphin.m_146908_();
               float f1 = (float)(this.f_24978_ * 3.0D);
               if (this.dolphin.m_20069_()) {
                  this.dolphin.m_7910_(f1 * 0.02F);
                  float f2 = -((float)(Mth.m_14136_(d1, (double)Mth.m_14116_((float)(d0 * d0 + d2 * d2))) * 57.2957763671875D));
                  f2 = Mth.m_14036_(Mth.m_14177_(f2), -85.0F, 85.0F);
                  this.dolphin.m_20256_(this.dolphin.m_20184_().m_82520_(0.0D, (double)this.dolphin.m_6113_() * d1 * 0.6D, 0.0D));
                  this.dolphin.m_146926_(this.m_24991_(this.dolphin.m_146909_(), f2, 1.0F));
                  float f3 = Mth.m_14089_(this.dolphin.m_146909_() * 0.017453292F);
                  float f4 = Mth.m_14031_(this.dolphin.m_146909_() * 0.017453292F);
                  this.dolphin.f_20902_ = f3 * f1;
                  this.dolphin.f_20901_ = -f4 * f1;
               } else {
                  this.dolphin.m_7910_(f1 * 0.1F);
               }
            }
         } else {
            this.dolphin.m_7910_(0.0F);
            this.dolphin.m_21570_(0.0F);
            this.dolphin.m_21567_(0.0F);
            this.dolphin.m_21564_(0.0F);
         }

      }
   }
}
