package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.ai.AquaticAIGetInWater;
import com.github.alexthe666.iceandfire.entity.ai.AquaticAIGetOutOfWater;
import com.github.alexthe666.iceandfire.entity.ai.SirenAIFindWaterTarget;
import com.github.alexthe666.iceandfire.entity.ai.SirenAIWander;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.ChainBuffer;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IHearsSiren;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.message.MessageSirenSong;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class EntitySiren extends Monster implements IAnimatedEntity, IVillagerFear, IHasCustomizableAttributes {
   public static final int SEARCH_RANGE = 32;
   public static final Predicate<Entity> SIREN_PREY = new Predicate<Entity>() {
      public boolean apply(@Nullable Entity p_apply_1_) {
         return p_apply_1_ instanceof Player && !((Player)p_apply_1_).m_7500_() && !p_apply_1_.m_5833_() || p_apply_1_ instanceof AbstractVillager || p_apply_1_ instanceof IHearsSiren;
      }
   };
   private static final EntityDataAccessor<Integer> HAIR_COLOR;
   private static final EntityDataAccessor<Boolean> AGGRESSIVE;
   private static final EntityDataAccessor<Integer> SING_POSE;
   private static final EntityDataAccessor<Boolean> SINGING;
   private static final EntityDataAccessor<Boolean> SWIMMING;
   private static final EntityDataAccessor<Boolean> CHARMED;
   private static final EntityDataAccessor<Byte> CLIMBING;
   public static Animation ANIMATION_BITE;
   public static Animation ANIMATION_PULL;
   public ChainBuffer tail_buffer;
   public float singProgress;
   public float swimProgress;
   public int singCooldown;
   private int animationTick;
   private Animation currentAnimation;
   private boolean isSinging;
   private boolean isSwimming;
   private boolean isLandNavigator;
   private int ticksAgressive;

   public EntitySiren(EntityType<EntitySiren> t, Level worldIn) {
      super(t, worldIn);
      this.switchNavigator(true);
      if (worldIn.f_46443_) {
         this.tail_buffer = new ChainBuffer();
      }

      this.m_274367_(1.0F);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new SirenAIFindWaterTarget(this));
      this.f_21345_.m_25352_(1, new AquaticAIGetInWater(this, 1.0D));
      this.f_21345_.m_25352_(1, new AquaticAIGetOutOfWater(this, 1.0D));
      this.f_21345_.m_25352_(2, new SirenAIWander(this, 1.0D));
      this.f_21345_.m_25352_(3, new RandomLookAroundGoal(this));
      this.f_21345_.m_25352_(3, new MeleeAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, Player.class, 10, true, false, new Predicate<Player>() {
         public boolean apply(@Nullable Player entity) {
            return EntitySiren.this.isAgressive() && !entity.m_7500_() && !entity.m_5833_();
         }
      }));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, AbstractVillager.class, 10, true, false, new Predicate<AbstractVillager>() {
         public boolean apply(@Nullable AbstractVillager entity) {
            return EntitySiren.this.isAgressive();
         }
      }));
   }

   public static boolean isWearingEarplugs(LivingEntity entity) {
      ItemStack helmet = entity.m_6844_(EquipmentSlot.HEAD);
      return helmet.m_41720_() == IafItemRegistry.EARPLUGS.get() || helmet != ItemStack.f_41583_ && helmet.m_41720_().m_5524_().contains("earmuff");
   }

   public int m_213860_() {
      return 8;
   }

   public float m_21692_(@NotNull BlockPos pos) {
      return this.m_9236_().m_8055_(pos).m_60713_(Blocks.f_49990_) ? 10.0F : super.m_21692_(pos);
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.m_217043_().m_188503_(2) == 0) {
         if (this.getAnimation() != ANIMATION_PULL) {
            this.setAnimation(ANIMATION_PULL);
            this.m_5496_(IafSoundRegistry.NAGA_ATTACK, 1.0F, 1.0F);
         }
      } else if (this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(ANIMATION_BITE);
         this.m_5496_(IafSoundRegistry.NAGA_ATTACK, 1.0F, 1.0F);
      }

      return true;
   }

   public boolean isDirectPathBetweenPoints(Vec3 vec1, Vec3 pos) {
      Vec3 Vector3d1 = new Vec3(pos.m_7096_() + 0.5D, pos.m_7098_() + 0.5D, pos.m_7094_() + 0.5D);
      return this.m_9236_().m_45547_(new ClipContext(vec1, Vector3d1, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   public float m_21439_(@NotNull BlockPathTypes nodeType) {
      return nodeType == BlockPathTypes.WATER ? 0.0F : super.m_21439_(nodeType);
   }

   private void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new AmphibiousPathNavigation(this, this.m_9236_());
         this.isLandNavigator = true;
      } else {
         this.f_21342_ = new EntitySiren.SwimmingMoveHelper();
         this.f_21344_ = new WaterBoundPathNavigation(this, this.m_9236_());
         this.isLandNavigator = false;
      }

   }

   private boolean isPathOnHighGround() {
      if (this.f_21344_ != null && this.f_21344_.m_26570_() != null && this.f_21344_.m_26570_().m_77395_() != null) {
         BlockPos target = new BlockPos(this.f_21344_.m_26570_().m_77395_().f_77271_, this.f_21344_.m_26570_().m_77395_().f_77272_, this.f_21344_.m_26570_().m_77395_().f_77273_);
         BlockPos siren = this.m_20183_();
         return this.m_9236_().m_46859_(siren.m_7494_()) && this.m_9236_().m_46859_(target.m_7494_()) && target.m_123342_() >= siren.m_123342_();
      } else {
         return false;
      }
   }

   public boolean m_6040_() {
      return true;
   }

   public void m_8107_() {
      super.m_8107_();
      this.f_20883_ = this.m_146908_();
      LivingEntity attackTarget = this.m_5448_();
      if (this.singCooldown > 0) {
         --this.singCooldown;
         this.setSinging(false);
      }

      if (!this.m_9236_().f_46443_ && attackTarget == null && !this.isAgressive()) {
         this.setSinging(true);
      }

      if (this.getAnimation() == ANIMATION_BITE && attackTarget != null && this.m_20280_(attackTarget) < 7.0D && this.getAnimationTick() == 5) {
         attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
      }

      double extraX;
      double extraY;
      double extraZ;
      if (this.getAnimation() == ANIMATION_PULL && attackTarget != null && this.m_20280_(attackTarget) < 16.0D && this.getAnimationTick() == 5) {
         attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
         double attackmotionX = (Math.signum(this.m_20185_() - attackTarget.m_20185_()) * 0.5D - attackTarget.m_20184_().f_82481_) * 0.100000000372529D * 5.0D;
         double attackmotionY = (Math.signum(this.m_20186_() - attackTarget.m_20186_() + 1.0D) * 0.5D - attackTarget.m_20184_().f_82480_) * 0.100000000372529D * 5.0D;
         double attackmotionZ = (Math.signum(this.m_20189_() - attackTarget.m_20189_()) * 0.5D - attackTarget.m_20184_().f_82481_) * 0.100000000372529D * 5.0D;
         attackTarget.m_20256_(attackTarget.m_20184_().m_82520_(attackmotionX, attackmotionY, attackmotionZ));
         extraX = this.m_20185_() - attackTarget.m_20185_();
         extraY = this.m_20189_() - attackTarget.m_20189_();
         extraZ = this.m_20186_() - 1.0D - attackTarget.m_20186_();
         double d3 = Math.sqrt((double)((float)(extraX * extraX + extraY * extraY)));
         float f = (float)(Mth.m_14136_(extraY, extraX) * 57.29577951308232D) - 90.0F;
         float f1 = (float)(-(Mth.m_14136_(extraZ, d3) * 57.29577951308232D));
         attackTarget.m_146926_(updateRotation(attackTarget.m_146909_(), f1, 30.0F));
         attackTarget.m_146922_(updateRotation(attackTarget.m_146908_(), f, 30.0F));
      }

      if (this.m_9236_().f_46443_) {
         this.tail_buffer.calculateChainSwingBuffer(40.0F, 10, 2.5F, this);
      }

      if (this.isAgressive()) {
         ++this.ticksAgressive;
      } else {
         this.ticksAgressive = 0;
      }

      if (this.ticksAgressive > 300 && this.isAgressive() && attackTarget == null && !this.m_9236_().f_46443_) {
         this.m_21561_(false);
         this.ticksAgressive = 0;
         this.setSinging(false);
      }

      if (this.m_20069_() && !this.m_6069_()) {
         this.m_20282_(true);
      }

      if (!this.m_20069_() && this.m_6069_()) {
         this.m_20282_(false);
      }

      LivingEntity target = this.m_5448_();
      boolean pathOnHighGround = this.isPathOnHighGround() || !this.m_9236_().f_46443_ && target != null && !target.m_20069_();
      if ((target == null || !target.m_20069_() && !target.m_20069_()) && pathOnHighGround && this.m_20069_()) {
         this.m_6135_();
         this.m_5841_();
      }

      if (this.m_20069_() && !pathOnHighGround && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if ((!this.m_20069_() || pathOnHighGround) && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (target instanceof Player && ((Player)target).m_7500_()) {
         this.m_6710_((LivingEntity)null);
         this.m_21561_(false);
      }

      if (target != null && !this.isAgressive()) {
         this.m_21561_(true);
      }

      boolean singing = this.isActuallySinging() && !this.isAgressive() && !this.m_20069_() && this.m_20096_();
      if (singing && this.singProgress < 20.0F) {
         ++this.singProgress;
      } else if (!singing && this.singProgress > 0.0F) {
         --this.singProgress;
      }

      boolean swimming = this.m_6069_();
      if (swimming && this.swimProgress < 20.0F) {
         ++this.swimProgress;
      } else if (!swimming && this.swimProgress > 0.0F) {
         this.swimProgress -= 0.5F;
      }

      if (!this.m_9236_().f_46443_ && !EntityGorgon.isStoneMob(this) && this.isActuallySinging()) {
         this.updateLure();
         this.checkForPrey();
      }

      if (!this.m_9236_().f_46443_ && EntityGorgon.isStoneMob(this) && this.isSinging()) {
         this.setSinging(false);
      }

      if (this.isActuallySinging() && !this.m_20069_() && this.m_217043_().m_188503_(3) == 0) {
         this.f_20883_ = this.m_146908_();
         if (this.m_9236_().f_46443_) {
            float radius = -0.9F;
            float angle = 0.017453292F * this.f_20883_ - 3.0F;
            extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
            extraY = 1.2000000476837158D;
            extraZ = (double)(radius * Mth.m_14089_(angle));
            IceAndFire.PROXY.spawnParticle(EnumParticles.Siren_Music, this.m_20185_() + extraX + (double)this.f_19796_.m_188501_() - 0.5D, this.m_20186_() + extraY + (double)this.f_19796_.m_188501_() - 0.5D, this.m_20189_() + extraZ + (double)this.f_19796_.m_188501_() - 0.5D, 0.0D, 0.0D, 0.0D);
         }
      }

      if (this.isActuallySinging() && !this.m_20069_() && this.f_19797_ % 200 == 0) {
         this.m_5496_(IafSoundRegistry.SIREN_SONG, 2.0F, 1.0F);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   private void checkForPrey() {
      this.setSinging(true);
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source.m_7639_() != null && source.m_7639_() instanceof LivingEntity) {
         this.triggerOtherSirens((LivingEntity)source.m_7639_());
      }

      return super.m_6469_(source, amount);
   }

   public void triggerOtherSirens(LivingEntity aggressor) {
      List<Entity> entities = this.m_9236_().m_45933_(this, this.m_20191_().m_82377_(12.0D, 12.0D, 12.0D));
      Iterator var3 = entities.iterator();

      while(var3.hasNext()) {
         Entity entity = (Entity)var3.next();
         if (entity instanceof EntitySiren) {
            ((EntitySiren)entity).m_6710_(aggressor);
            ((EntitySiren)entity).m_21561_(true);
            ((EntitySiren)entity).setSinging(false);
         }
      }

   }

   public void updateLure() {
      if (this.f_19797_ % 20 == 0) {
         List<LivingEntity> entities = this.m_9236_().m_6443_(LivingEntity.class, this.m_20191_().m_82377_(50.0D, 12.0D, 50.0D), SIREN_PREY);
         Iterator var2 = entities.iterator();

         while(var2.hasNext()) {
            LivingEntity entity = (LivingEntity)var2.next();
            if (!isWearingEarplugs(entity)) {
               EntityDataProvider.getCapability(entity).ifPresent((data) -> {
                  if (data.sirenData.isCharmed || data.sirenData.charmedBy == null) {
                     data.sirenData.setCharmed(this);
                  }

               });
            }
         }
      }

   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("HairColor", this.getHairColor());
      tag.m_128379_("Aggressive", this.isAgressive());
      tag.m_128405_("SingingPose", this.getSingingPose());
      tag.m_128379_("Singing", this.isSinging());
      tag.m_128379_("Swimming", this.m_6069_());
      tag.m_128379_("Passive", this.isCharmed());
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      this.setHairColor(tag.m_128451_("HairColor"));
      this.m_21561_(tag.m_128471_("Aggressive"));
      this.setSingingPose(tag.m_128451_("SingingPose"));
      this.setSinging(tag.m_128471_("Singing"));
      this.m_20282_(tag.m_128471_("Swimming"));
      this.setCharmed(tag.m_128471_("Passive"));
      this.setConfigurableAttributes();
   }

   public boolean isSinging() {
      return this.m_9236_().f_46443_ ? (this.isSinging = (Boolean)this.f_19804_.m_135370_(SINGING)) : this.isSinging;
   }

   public void setSinging(boolean singing) {
      if (this.singCooldown > 0) {
         singing = false;
      }

      this.f_19804_.m_135381_(SINGING, singing);
      if (!this.m_9236_().f_46443_) {
         this.isSinging = singing;
         IceAndFire.sendMSGToAll(new MessageSirenSong(this.m_19879_(), singing));
      }

   }

   public boolean wantsToSing() {
      return this.isSinging() && this.m_20069_() && !this.isAgressive();
   }

   public boolean isActuallySinging() {
      return this.isSinging() && !this.wantsToSing();
   }

   public boolean m_6069_() {
      return this.m_9236_().f_46443_ ? (this.isSwimming = (Boolean)this.f_19804_.m_135370_(SWIMMING)) : this.isSwimming;
   }

   public void m_20282_(boolean swimming) {
      this.f_19804_.m_135381_(SWIMMING, swimming);
      if (!this.m_9236_().f_46443_) {
         this.isSwimming = swimming;
      }

   }

   public void m_21561_(boolean aggressive) {
      this.f_19804_.m_135381_(AGGRESSIVE, aggressive);
   }

   public boolean isAgressive() {
      return (Boolean)this.f_19804_.m_135370_(AGGRESSIVE);
   }

   public boolean isCharmed() {
      return (Boolean)this.f_19804_.m_135370_(CHARMED);
   }

   public void setCharmed(boolean aggressive) {
      this.f_19804_.m_135381_(CHARMED, aggressive);
   }

   public int getHairColor() {
      return (Integer)this.f_19804_.m_135370_(HAIR_COLOR);
   }

   public void setHairColor(int hairColor) {
      this.f_19804_.m_135381_(HAIR_COLOR, hairColor);
   }

   public int getSingingPose() {
      return (Integer)this.f_19804_.m_135370_(SING_POSE);
   }

   public void setSingingPose(int pose) {
      this.f_19804_.m_135381_(SING_POSE, Mth.m_14045_(pose, 0, 2));
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.sirenMaxHealth).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22281_, 6.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.sirenMaxHealth);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HAIR_COLOR, 0);
      this.f_19804_.m_135372_(SING_POSE, 0);
      this.f_19804_.m_135372_(AGGRESSIVE, Boolean.FALSE);
      this.f_19804_.m_135372_(SINGING, Boolean.FALSE);
      this.f_19804_.m_135372_(SWIMMING, Boolean.FALSE);
      this.f_19804_.m_135372_(CHARMED, Boolean.FALSE);
      this.f_19804_.m_135372_(CLIMBING, (byte)0);
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setHairColor(this.m_217043_().m_188503_(3));
      this.setSingingPose(this.m_217043_().m_188503_(3));
      return spawnDataIn;
   }

   public static float updateRotation(float angle, float targetAngle, float maxIncrease) {
      float f = Mth.m_14177_(targetAngle - angle);
      if (f > maxIncrease) {
         f = maxIncrease;
      }

      if (f < -maxIncrease) {
         f = -maxIncrease;
      }

      return angle + f;
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
      return new Animation[]{NO_ANIMATION, ANIMATION_BITE, ANIMATION_PULL};
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return this.isAgressive() ? IafSoundRegistry.NAGA_IDLE : IafSoundRegistry.MERMAID_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return this.isAgressive() ? IafSoundRegistry.NAGA_HURT : IafSoundRegistry.MERMAID_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return this.isAgressive() ? IafSoundRegistry.NAGA_DIE : IafSoundRegistry.MERMAID_DIE;
   }

   public void m_7023_(@NotNull Vec3 motion) {
      super.m_7023_(motion);
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public boolean shouldFear() {
      return this.isAgressive();
   }

   static {
      HAIR_COLOR = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135028_);
      AGGRESSIVE = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135035_);
      SING_POSE = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135028_);
      SINGING = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135035_);
      SWIMMING = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135035_);
      CHARMED = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135035_);
      CLIMBING = SynchedEntityData.m_135353_(EntitySiren.class, EntityDataSerializers.f_135027_);
      ANIMATION_BITE = Animation.create(20);
      ANIMATION_PULL = Animation.create(20);
   }

   class SwimmingMoveHelper extends MoveControl {
      private final EntitySiren siren = EntitySiren.this;

      public SwimmingMoveHelper() {
         super(EntitySiren.this);
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.MOVE_TO) {
            double distanceX = this.f_24975_ - this.siren.m_20185_();
            double distanceY = this.f_24976_ - this.siren.m_20186_();
            double distanceZ = this.f_24977_ - this.siren.m_20189_();
            double distance = Math.abs(distanceX * distanceX + distanceZ * distanceZ);
            double distanceWithY = Math.sqrt(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
            distanceY /= distanceWithY;
            float angle = (float)(Math.atan2(distanceZ, distanceX) * 180.0D / 3.141592653589793D) - 90.0F;
            this.siren.m_146922_(this.m_24991_(this.siren.m_146908_(), angle, 30.0F));
            this.siren.m_7910_(1.0F);
            float f1 = 0.0F;
            float f2 = 0.0F;
            if (distance < (double)Math.max(1.0F, this.siren.m_20205_())) {
               float f = this.siren.m_146908_() * 0.017453292F;
               f1 = (float)((double)f1 - (double)(Mth.m_14031_(f) * 0.35F));
               f2 = (float)((double)f2 + (double)(Mth.m_14089_(f) * 0.35F));
            }

            this.siren.m_20256_(this.siren.m_20184_().m_82520_((double)f1, (double)this.siren.m_6113_() * distanceY * 0.1D, (double)f2));
         } else if (this.f_24981_ == Operation.JUMPING) {
            this.siren.m_7910_((float)(this.f_24978_ * this.siren.m_21051_(Attributes.f_22279_).m_22135_()));
            if (this.siren.m_20096_()) {
               this.f_24981_ = Operation.WAIT;
            }
         } else {
            this.siren.m_7910_(0.0F);
         }

      }
   }
}
