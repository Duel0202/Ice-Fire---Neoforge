package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackPlayers;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIDefendHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIFindMate;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILeaveHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILookAtTradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIMoveThroughHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIMoveToMate;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIReEnterHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAITradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWander;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWanderHiveCenter;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.MyrmexTrades;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.google.common.base.Predicate;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexRoyal extends EntityMyrmexBase {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_STING = Animation.create(15);
   public static final ResourceLocation DESERT_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_royal_desert");
   public static final ResourceLocation JUNGLE_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_royal_jungle");
   private static final ResourceLocation TEXTURE_DESERT = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_royal.png");
   private static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_royal.png");
   private static final EntityDataAccessor<Boolean> FLYING;
   public int releaseTicks = 0;
   public int daylightTicks = 0;
   public float flyProgress;
   public EntityMyrmexRoyal mate;
   private int hiveTicks = 0;
   private int breedingTicks = 0;
   private boolean isFlying;
   private boolean isLandNavigator;
   private boolean isMating = false;

   public EntityMyrmexRoyal(EntityType<EntityMyrmexRoyal> t, Level worldIn) {
      super(t, worldIn);
      this.switchNavigator(true);
   }

   protected ItemListing[] getLevel1Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_ROYAL.get(1) : (ItemListing[])MyrmexTrades.DESERT_ROYAL.get(1);
   }

   protected ItemListing[] getLevel2Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_ROYAL.get(2) : (ItemListing[])MyrmexTrades.DESERT_ROYAL.get(2);
   }

   public static BlockPos getPositionRelativetoGround(Entity entity, Level world, double x, double z, RandomSource rand) {
      BlockPos pos = BlockPos.m_274561_(x, (double)entity.m_146904_(), z);

      for(int yDown = 0; yDown < 10; ++yDown) {
         if (!world.m_46859_(pos.m_6625_(yDown))) {
            return pos.m_6630_(yDown);
         }
      }

      return pos;
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.isJungle() ? JUNGLE_LOOT : DESERT_LOOT;
   }

   public int m_213860_() {
      return 10;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(FLYING, Boolean.FALSE);
   }

   protected void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.CLIMBING);
         this.isLandNavigator = true;
      } else {
         this.f_21342_ = new EntityMyrmexRoyal.FlyMoveHelper(this);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.FLYING);
         this.isLandNavigator = false;
      }

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

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("HiveTicks", this.hiveTicks);
      tag.m_128405_("ReleaseTicks", this.releaseTicks);
      tag.m_128379_("Flying", this.isFlying());
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.hiveTicks = tag.m_128451_("HiveTicks");
      this.releaseTicks = tag.m_128451_("ReleaseTicks");
      this.setFlying(tag.m_128471_("Flying"));
   }

   public void m_8107_() {
      super.m_8107_();
      boolean flying = this.isFlying() && !this.m_20096_();
      LivingEntity attackTarget = this.m_5448_();
      if (flying && this.flyProgress < 20.0F) {
         ++this.flyProgress;
      } else if (!flying && this.flyProgress > 0.0F) {
         --this.flyProgress;
      }

      if (flying) {
         double up = this.m_20069_() ? 0.16D : 0.08D;
         this.m_20256_(this.m_20184_().m_82520_(0.0D, up, 0.0D));
      }

      if (flying && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!flying && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if (this.canSeeSky()) {
         ++this.daylightTicks;
      } else {
         this.daylightTicks = 0;
      }

      if (flying && this.canSeeSky() && this.isBreedingSeason()) {
         ++this.releaseTicks;
      }

      if (!flying && this.canSeeSky() && this.daylightTicks > 300 && this.isBreedingSeason() && attackTarget == null && this.canMove() && this.m_20096_() && !this.isMating) {
         this.setFlying(true);
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.42D, 0.0D));
      }

      if (this.getGrowthStage() >= 2) {
         ++this.hiveTicks;
      }

      if (this.getAnimation() == ANIMATION_BITE && attackTarget != null && this.getAnimationTick() == 6) {
         this.playBiteSound();
         if (this.getAttackBounds().m_82381_(attackTarget.m_20191_())) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (this.getAnimation() == ANIMATION_STING && attackTarget != null && this.getAnimationTick() == 6) {
         this.playStingSound();
         if (this.getAttackBounds().m_82381_(attackTarget.m_20191_())) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() * 2));
            attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 70, 1));
         }
      }

      if (this.mate != null) {
         this.m_9236_().m_7605_(this, (byte)77);
         if (this.m_20270_(this.mate) < 10.0F) {
            this.setFlying(false);
            this.mate.setFlying(false);
            this.isMating = true;
            if (this.m_20096_() && this.mate.m_20096_()) {
               ++this.breedingTicks;
               if (this.breedingTicks > 100) {
                  if (this.m_6084_()) {
                     this.mate.m_142687_(RemovalReason.KILLED);
                     this.m_142687_(RemovalReason.KILLED);
                     EntityMyrmexQueen queen = new EntityMyrmexQueen((EntityType)IafEntityRegistry.MYRMEX_QUEEN.get(), this.m_9236_());
                     queen.m_20359_(this);
                     queen.setJungleVariant(this.isJungle());
                     queen.setMadeHome(false);
                     if (!this.m_9236_().f_46443_) {
                        this.m_9236_().m_7967_(queen);
                     }
                  }

                  this.isMating = false;
               }
            }
         }

         this.mate.mate = this;
         if (!this.mate.m_6084_()) {
            this.mate.mate = null;
            this.mate = null;
         }
      }

   }

   protected double attackDistance() {
      return 8.0D;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new MyrmexAITradePlayer(this));
      this.f_21345_.m_25352_(0, new MyrmexAILookAtTradePlayer(this));
      this.f_21345_.m_25352_(0, new MyrmexAIMoveToMate(this, 1.0D));
      this.f_21345_.m_25352_(1, new EntityMyrmexRoyal.AIFlyAtTarget());
      this.f_21345_.m_25352_(2, new EntityMyrmexRoyal.AIFlyRandom());
      this.f_21345_.m_25352_(3, new MyrmexAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(4, new MyrmexAILeaveHive(this, 1.0D));
      this.f_21345_.m_25352_(4, new MyrmexAIReEnterHive(this, 1.0D));
      this.f_21345_.m_25352_(5, new MyrmexAIMoveThroughHive(this, 1.0D));
      this.f_21345_.m_25352_(5, new MyrmexAIWanderHiveCenter(this, 1.0D));
      this.f_21345_.m_25352_(6, new MyrmexAIWander(this, 1.0D));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new MyrmexAIDefendHive(this));
      this.f_21346_.m_25352_(2, new MyrmexAIFindMate(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new MyrmexAIAttackPlayers(this));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, true, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            if ((!(entity instanceof EntityMyrmexBase) || !EntityMyrmexRoyal.this.isBreedingSeason()) && !(entity instanceof EntityMyrmexRoyal)) {
               return entity != null && !EntityMyrmexBase.haveSameHive(EntityMyrmexRoyal.this, entity) && DragonUtils.isAlive(entity) && !(entity instanceof Enemy);
            } else {
               return false;
            }
         }
      }));
   }

   public boolean m_7848_(@NotNull Animal otherAnimal) {
      if (otherAnimal != this && otherAnimal != null) {
         if (otherAnimal.getClass() != this.getClass()) {
            return false;
         } else if (otherAnimal instanceof EntityMyrmexBase) {
            if (((EntityMyrmexBase)otherAnimal).getHive() != null && this.getHive() != null) {
               return !this.getHive().equals(((EntityMyrmexBase)otherAnimal).getHive());
            } else {
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean shouldMoveThroughHive() {
      return false;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 50.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, IafConfig.myrmexBaseAttackStrength * 2.0D).m_22268_(Attributes.f_22277_, 64.0D).m_22268_(Attributes.f_22284_, 9.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.myrmexBaseAttackStrength * 2.0D);
   }

   public ResourceLocation getAdultTexture() {
      return this.isJungle() ? TEXTURE_JUNGLE : TEXTURE_DESERT;
   }

   public float getModelScale() {
      return 1.25F;
   }

   public int getCasteImportance() {
      return 2;
   }

   public boolean shouldLeaveHive() {
      return this.isBreedingSeason();
   }

   public boolean shouldEnterHive() {
      return !this.isBreedingSeason();
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getGrowthStage() < 2) {
         return false;
      } else if (this.getAnimation() != ANIMATION_STING && this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(this.m_217043_().m_188499_() ? ANIMATION_STING : ANIMATION_BITE);
         return true;
      } else {
         return false;
      }
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_PUPA_WIGGLE, ANIMATION_BITE, ANIMATION_STING};
   }

   public boolean isBreedingSeason() {
      return this.getGrowthStage() >= 2 && (this.getHive() == null || this.getHive().reproduces);
   }

   public void m_7822_(byte id) {
      if (id == 76) {
         this.playEffect(20);
      } else if (id == 77) {
         this.playEffect(7);
      } else {
         super.m_7822_(id);
      }

   }

   protected void playEffect(int hearts) {
      for(int i = 0; i < hearts; ++i) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         this.m_9236_().m_7106_(ParticleTypes.f_123750_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + 0.5D + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d0, d1, d2);
      }

   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public int m_7809_() {
      return 0;
   }

   public boolean m_7826_() {
      return false;
   }

   protected boolean isDirectPathBetweenPoints(BlockPos posVec31, BlockPos posVec32) {
      Vec3 vector3d = Vec3.m_82512_(posVec31);
      Vec3 vector3d1 = Vec3.m_82512_(posVec32);
      return this.m_9236_().m_45547_(new ClipContext(vector3d, vector3d1, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   public boolean m_183595_() {
      return this.m_9236_().f_46443_;
   }

   static {
      FLYING = SynchedEntityData.m_135353_(EntityMyrmexRoyal.class, EntityDataSerializers.f_135035_);
   }

   class FlyMoveHelper extends MoveControl {
      public FlyMoveHelper(EntityMyrmexRoyal pixie) {
         super(pixie);
         this.f_24978_ = 1.75D;
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.MOVE_TO) {
            if (EntityMyrmexRoyal.this.f_19862_) {
               EntityMyrmexRoyal.this.m_146922_(EntityMyrmexRoyal.this.m_146908_() + 180.0F);
               this.f_24978_ = 0.10000000149011612D;
               BlockPos target = EntityMyrmexRoyal.getPositionRelativetoGround(EntityMyrmexRoyal.this, EntityMyrmexRoyal.this.m_9236_(), EntityMyrmexRoyal.this.m_20185_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(15) - 7.0D, EntityMyrmexRoyal.this.m_20189_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(15) - 7.0D, EntityMyrmexRoyal.this.f_19796_);
               this.f_24975_ = (double)target.m_123341_();
               this.f_24976_ = (double)target.m_123342_();
               this.f_24977_ = (double)target.m_123343_();
            }

            double d0 = this.f_24975_ - EntityMyrmexRoyal.this.m_20185_();
            double d1 = this.f_24976_ - EntityMyrmexRoyal.this.m_20186_();
            double d2 = this.f_24977_ - EntityMyrmexRoyal.this.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = Math.sqrt(d3);
            if (d3 < EntityMyrmexRoyal.this.m_20191_().m_82309_()) {
               this.f_24981_ = Operation.WAIT;
               EntityMyrmexRoyal.this.m_20256_(EntityMyrmexRoyal.this.m_20184_().m_82542_(0.5D, 0.5D, 0.5D));
            } else {
               EntityMyrmexRoyal.this.m_20256_(EntityMyrmexRoyal.this.m_20184_().m_82520_(d0 / d3 * 0.1D * this.f_24978_, d1 / d3 * 0.1D * this.f_24978_, d2 / d3 * 0.1D * this.f_24978_));
               if (EntityMyrmexRoyal.this.m_5448_() == null) {
                  EntityMyrmexRoyal.this.m_146922_(-((float)Mth.m_14136_(EntityMyrmexRoyal.this.m_20184_().f_82479_, EntityMyrmexRoyal.this.m_20184_().f_82481_)) * 57.295776F);
                  EntityMyrmexRoyal.this.f_20883_ = EntityMyrmexRoyal.this.m_146908_();
               } else {
                  double d4 = EntityMyrmexRoyal.this.m_5448_().m_20185_() - EntityMyrmexRoyal.this.m_20185_();
                  double d5 = EntityMyrmexRoyal.this.m_5448_().m_20189_() - EntityMyrmexRoyal.this.m_20189_();
                  EntityMyrmexRoyal.this.m_146922_(-((float)Mth.m_14136_(d4, d5)) * 57.295776F);
                  EntityMyrmexRoyal.this.f_20883_ = EntityMyrmexRoyal.this.m_146908_();
               }
            }
         }

      }
   }

   class AIFlyAtTarget extends Goal {
      public AIFlyAtTarget() {
      }

      public boolean m_8036_() {
         if (EntityMyrmexRoyal.this.m_5448_() != null && !EntityMyrmexRoyal.this.m_21566_().m_24995_() && EntityMyrmexRoyal.this.f_19796_.m_188503_(7) == 0) {
            return EntityMyrmexRoyal.this.m_20280_(EntityMyrmexRoyal.this.m_5448_()) > 4.0D;
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         return EntityMyrmexRoyal.this.m_21566_().m_24995_() && EntityMyrmexRoyal.this.m_5448_() != null && EntityMyrmexRoyal.this.m_5448_().m_6084_();
      }

      public void m_8056_() {
         LivingEntity LivingEntity = EntityMyrmexRoyal.this.m_5448_();
         Vec3 Vector3d = LivingEntity.m_20299_(1.0F);
         EntityMyrmexRoyal.this.f_21342_.m_6849_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, 1.0D);
      }

      public void m_8041_() {
      }

      public void m_8037_() {
         LivingEntity LivingEntity = EntityMyrmexRoyal.this.m_5448_();
         if (LivingEntity != null) {
            if (EntityMyrmexRoyal.this.m_20191_().m_82381_(LivingEntity.m_20191_())) {
               EntityMyrmexRoyal.this.m_7327_(LivingEntity);
            } else {
               double d0 = EntityMyrmexRoyal.this.m_20280_(LivingEntity);
               if (d0 < 9.0D) {
                  Vec3 Vector3d = LivingEntity.m_20299_(1.0F);
                  EntityMyrmexRoyal.this.f_21342_.m_6849_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, 1.0D);
               }
            }
         }

      }
   }

   class AIFlyRandom extends Goal {
      BlockPos target;

      public AIFlyRandom() {
         this.m_7021_(EnumSet.of(Flag.MOVE));
      }

      public boolean m_8036_() {
         if (EntityMyrmexRoyal.this.isFlying() && EntityMyrmexRoyal.this.m_5448_() == null) {
            if (EntityMyrmexRoyal.this instanceof EntityMyrmexSwarmer && ((EntityMyrmexSwarmer)EntityMyrmexRoyal.this).getSummoner() != null) {
               Entity summon = ((EntityMyrmexSwarmer)EntityMyrmexRoyal.this).getSummoner();
               this.target = EntityMyrmexRoyal.getPositionRelativetoGround(EntityMyrmexRoyal.this, EntityMyrmexRoyal.this.m_9236_(), summon.m_20185_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(10) - 5.0D, summon.m_20189_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(10) - 5.0D, EntityMyrmexRoyal.this.f_19796_);
            } else {
               this.target = EntityMyrmexRoyal.getPositionRelativetoGround(EntityMyrmexRoyal.this, EntityMyrmexRoyal.this.m_9236_(), EntityMyrmexRoyal.this.m_20185_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(30) - 15.0D, EntityMyrmexRoyal.this.m_20189_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(30) - 15.0D, EntityMyrmexRoyal.this.f_19796_);
            }

            return EntityMyrmexRoyal.this.isDirectPathBetweenPoints(EntityMyrmexRoyal.this.m_20183_(), this.target) && !EntityMyrmexRoyal.this.m_21566_().m_24995_() && EntityMyrmexRoyal.this.f_19796_.m_188503_(2) == 0;
         } else {
            return false;
         }
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8037_() {
         if (!EntityMyrmexRoyal.this.isDirectPathBetweenPoints(EntityMyrmexRoyal.this.m_20183_(), this.target)) {
            if (EntityMyrmexRoyal.this instanceof EntityMyrmexSwarmer && ((EntityMyrmexSwarmer)EntityMyrmexRoyal.this).getSummoner() != null) {
               Entity summon = ((EntityMyrmexSwarmer)EntityMyrmexRoyal.this).getSummoner();
               this.target = EntityMyrmexRoyal.getPositionRelativetoGround(EntityMyrmexRoyal.this, EntityMyrmexRoyal.this.m_9236_(), summon.m_20185_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(10) - 5.0D, summon.m_20189_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(10) - 5.0D, EntityMyrmexRoyal.this.f_19796_);
            } else {
               this.target = EntityMyrmexRoyal.getPositionRelativetoGround(EntityMyrmexRoyal.this, EntityMyrmexRoyal.this.m_9236_(), EntityMyrmexRoyal.this.m_20185_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(30) - 15.0D, EntityMyrmexRoyal.this.m_20189_() + (double)EntityMyrmexRoyal.this.f_19796_.m_188503_(30) - 15.0D, EntityMyrmexRoyal.this.f_19796_);
            }
         }

         if (EntityMyrmexRoyal.this.m_9236_().m_46859_(this.target)) {
            EntityMyrmexRoyal.this.f_21342_.m_6849_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 0.25D);
            if (EntityMyrmexRoyal.this.m_5448_() == null) {
               EntityMyrmexRoyal.this.m_21563_().m_24950_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 180.0F, 20.0F);
            }
         }

      }
   }
}
