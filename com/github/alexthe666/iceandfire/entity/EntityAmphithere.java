package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.client.model.IFChainBuffer;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.ai.AmphithereAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.AmphithereAIFleePlayer;
import com.github.alexthe666.iceandfire.entity.ai.AmphithereAIFollowOwner;
import com.github.alexthe666.iceandfire.entity.ai.AmphithereAIHurtByTarget;
import com.github.alexthe666.iceandfire.entity.ai.AmphithereAITargetItems;
import com.github.alexthe666.iceandfire.entity.ai.EntityAIWatchClosestIgnoreRider;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.ICustomMoveController;
import com.github.alexthe666.iceandfire.entity.util.IDragonFlute;
import com.github.alexthe666.iceandfire.entity.util.IFlapable;
import com.github.alexthe666.iceandfire.entity.util.IFlyingMount;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IPhasesThroughBlock;
import com.github.alexthe666.iceandfire.entity.util.ISyncMount;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.pathfinding.PathNavigateFlyingCreature;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class EntityAmphithere extends TamableAnimal implements ISyncMount, IAnimatedEntity, IPhasesThroughBlock, IFlapable, IDragonFlute, IFlyingMount, IHasCustomizableAttributes, ICustomMoveController {
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Boolean> FLYING;
   private static final EntityDataAccessor<Integer> FLAP_TICKS;
   private static final EntityDataAccessor<Byte> CONTROL_STATE;
   private static final EntityDataAccessor<Integer> COMMAND;
   public static Animation ANIMATION_BITE;
   public static Animation ANIMATION_BITE_RIDER;
   public static Animation ANIMATION_WING_BLAST;
   public static Animation ANIMATION_TAIL_WHIP;
   public static Animation ANIMATION_SPEAK;
   public float flapProgress;
   public float groundProgress = 0.0F;
   public float sitProgress = 0.0F;
   public float diveProgress = 0.0F;
   public IFChainBuffer roll_buffer;
   public IFChainBuffer tail_buffer;
   public IFChainBuffer pitch_buffer;
   @Nullable
   public BlockPos orbitPos = null;
   public float orbitRadius = 0.0F;
   public boolean isFallen;
   public BlockPos homePos;
   public boolean hasHomePosition = false;
   protected EntityAmphithere.FlightBehavior flightBehavior;
   protected int ticksCircling;
   private int animationTick;
   private Animation currentAnimation;
   private int flapTicks;
   private int flightCooldown;
   private int ticksFlying;
   private boolean isFlying;
   private boolean changedFlightBehavior;
   private int ticksStill;
   private int ridingTime;
   private boolean isSitting;
   private int navigatorType;

   public EntityAmphithere(EntityType<EntityAmphithere> type, Level worldIn) {
      super(type, worldIn);
      this.flightBehavior = EntityAmphithere.FlightBehavior.WANDER;
      this.ticksCircling = 0;
      this.flapTicks = 0;
      this.flightCooldown = 0;
      this.ticksFlying = 0;
      this.changedFlightBehavior = false;
      this.ticksStill = 0;
      this.ridingTime = 0;
      this.navigatorType = 0;
      if (worldIn.f_46443_) {
         this.roll_buffer = new IFChainBuffer();
         this.pitch_buffer = new IFChainBuffer();
         this.tail_buffer = new IFChainBuffer();
      }

      this.m_274367_(1.0F);
      this.switchNavigator(0);
   }

   public static BlockPos getPositionRelativetoGround(Entity entity, Level world, int x, int z, RandomSource rand) {
      BlockPos pos = new BlockPos(x, entity.m_146904_(), z);

      for(int yDown = 0; yDown < 6 + rand.m_188503_(6); ++yDown) {
         if (!world.m_46859_(pos.m_6625_(yDown))) {
            return pos.m_6630_(yDown);
         }
      }

      return pos;
   }

   public static boolean canAmphithereSpawnOn(EntityType<EntityAmphithere> parrotIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos p_223317_3_, RandomSource random) {
      BlockState blockState = worldIn.m_8055_(p_223317_3_.m_7495_());
      Block block = blockState.m_60734_();
      return blockState.m_204336_(BlockTags.f_13035_) || block == Blocks.f_50440_ || blockState.m_204336_(BlockTags.f_13106_) || block == Blocks.f_50016_;
   }

   public boolean m_6914_(LevelReader worldIn) {
      if (worldIn.m_45784_(this) && !worldIn.m_46855_(this.m_20191_())) {
         BlockPos blockpos = this.m_20183_();
         if (blockpos.m_123342_() < worldIn.m_5736_()) {
            return false;
         } else {
            BlockState blockstate = worldIn.m_8055_(blockpos.m_7495_());
            return blockstate.m_60713_(Blocks.f_50440_) || blockstate.m_204336_(BlockTags.f_13035_);
         }
      } else {
         return false;
      }
   }

   public static BlockPos getPositionInOrbit(EntityAmphithere entity, Level world, BlockPos orbit, RandomSource rand) {
      float possibleOrbitRadius = entity.orbitRadius + 10.0F;
      float radius = 10.0F;
      if (entity.getCommand() == 2) {
         if (entity.m_269323_() != null) {
            orbit = entity.m_269323_().m_20183_().m_6630_(7);
            radius = 5.0F;
         }
      } else if (entity.hasHomePosition) {
         orbit = entity.homePos.m_6630_(30);
         radius = 30.0F;
      }

      float angle = 0.017453292F * possibleOrbitRadius;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = BlockPos.m_274561_((double)orbit.m_123341_() + extraX, (double)orbit.m_123342_(), (double)orbit.m_123343_() + extraZ);
      entity.orbitRadius = possibleOrbitRadius;
      return radialPos;
   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public float m_21692_(@NotNull BlockPos pos) {
      if (this.isFlying()) {
         return this.m_9236_().m_46859_(pos) ? 10.0F : 0.0F;
      } else {
         return super.m_21692_(pos);
      }
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (itemstack != null && itemstack.m_204117_(IafItemTags.BREED_AMPITHERE)) {
         if (this.m_146764_() == 0 && !this.m_27593_()) {
            this.m_21839_(false);
            this.m_27595_(player);
            this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }
         }

         return InteractionResult.SUCCESS;
      } else if (itemstack != null && itemstack.m_204117_(IafItemTags.HEAL_AMPITHERE) && this.m_21223_() < this.m_21233_()) {
         this.m_5634_(5.0F);
         this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         return InteractionResult.SUCCESS;
      } else {
         if (super.m_6071_(player, hand) == InteractionResult.PASS) {
            if (itemstack != null && itemstack.m_41720_() == IafItemRegistry.DRAGON_STAFF.get() && this.m_21830_(player)) {
               if (player.m_6144_()) {
                  BlockPos pos = this.m_20183_();
                  this.homePos = pos;
                  this.hasHomePosition = true;
                  player.m_5661_(Component.m_237110_("amphithere.command.new_home", new Object[]{this.homePos.m_123341_(), this.homePos.m_123342_(), this.homePos.m_123343_()}), true);
                  return InteractionResult.SUCCESS;
               }

               return InteractionResult.SUCCESS;
            }

            if (player.m_6144_() && this.m_21830_(player)) {
               if (player.m_21120_(hand).m_41619_()) {
                  this.setCommand(this.getCommand() + 1);
                  if (this.getCommand() > 2) {
                     this.setCommand(0);
                  }

                  player.m_5661_(Component.m_237115_("amphithere.command." + this.getCommand()), true);
                  this.m_5496_(SoundEvents.f_12609_, 1.0F, 1.0F);
                  return InteractionResult.SUCCESS;
               }

               return InteractionResult.SUCCESS;
            }

            if ((!this.m_21824_() || this.m_21830_(player)) && !this.m_6162_() && itemstack.m_41619_()) {
               player.m_20329_(this);
               return InteractionResult.SUCCESS;
            }
         }

         return super.m_6071_(player, hand);
      }
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new AmphithereAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(2, new AmphithereAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
      this.f_21345_.m_25352_(3, new AmphithereAIFleePlayer(this, 32.0F, 0.8D, 1.8D));
      this.f_21345_.m_25352_(3, new EntityAmphithere.AIFlyWander());
      this.f_21345_.m_25352_(3, new EntityAmphithere.AIFlyCircle());
      this.f_21345_.m_25352_(3, new EntityAmphithere.AILandWander(this, 1.0D));
      this.f_21345_.m_25352_(4, new EntityAIWatchClosestIgnoreRider(this, LivingEntity.class, 6.0F));
      this.f_21345_.m_25352_(4, new BreedGoal(this, 1.0D));
      this.f_21346_.m_25352_(1, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(3, new AmphithereAIHurtByTarget(this, false, new Class[0]));
      this.f_21346_.m_25352_(3, new AmphithereAITargetItems(this, false));
   }

   public boolean isStill() {
      return Math.abs(this.m_20184_().f_82479_) < 0.05D && Math.abs(this.m_20184_().f_82481_) < 0.05D;
   }

   protected void switchNavigator(int navigatorType) {
      if (navigatorType == 0) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new WallClimberNavigation(this, this.m_9236_());
         this.navigatorType = 0;
      } else if (navigatorType == 1) {
         this.f_21342_ = new EntityAmphithere.FlyMoveHelper(this);
         this.f_21344_ = new PathNavigateFlyingCreature(this, this.m_9236_());
         this.navigatorType = 1;
      } else {
         this.f_21342_ = new FlyingMoveControl(this, 20, false);
         this.f_21344_ = new PathNavigateFlyingCreature(this, this.m_9236_());
         this.navigatorType = 2;
      }

   }

   public boolean onLeaves() {
      BlockState state = this.m_9236_().m_8055_(this.m_20183_().m_7495_());
      return state.m_60734_() instanceof LeavesBlock;
   }

   public boolean m_6469_(@NotNull DamageSource source, float damage) {
      if (!this.m_21824_() && this.isFlying() && !this.m_20096_() && source.m_269533_(DamageTypeTags.f_268524_) && !this.m_9236_().f_46443_) {
         this.isFallen = true;
      }

      return source.m_7639_() instanceof LivingEntity && source.m_7639_().m_20365_(this) && this.m_21824_() && this.m_21830_((LivingEntity)source.m_7639_()) ? false : super.m_6469_(source, damage);
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger) && this.m_21824_()) {
         this.m_5618_(passenger.m_146908_());
         this.m_5616_(passenger.m_6080_());
      }

      if (!this.m_9236_().f_46443_ && !this.m_21824_() && passenger instanceof Player && this.getAnimation() == NO_ANIMATION && this.f_19796_.m_188503_(15) == 0) {
         this.setAnimation(ANIMATION_BITE_RIDER);
      }

      if (!this.m_9236_().f_46443_ && this.getAnimation() == ANIMATION_BITE_RIDER && this.getAnimationTick() == 6 && !this.m_21824_()) {
         passenger.m_6469_(this.m_9236_().m_269111_().m_269333_(this), 1.0F);
      }

      float pitch_forward = 0.0F;
      if (this.m_146909_() > 0.0F && this.isFlying()) {
         pitch_forward = this.m_146909_() / 45.0F * 0.45F;
      } else {
         pitch_forward = 0.0F;
      }

      float scaled_ground = this.groundProgress * 0.1F;
      float radius = (this.m_21824_() ? 0.5F : 0.3F) - scaled_ground * 0.5F + pitch_forward;
      float angle = 0.017453292F * this.f_20883_;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      passenger.m_6034_(this.m_20185_() + extraX, this.m_20186_() + 0.699999988079071D - (double)(scaled_ground * 0.14F) + (double)pitch_forward, this.m_20189_() + extraZ);
   }

   public boolean m_6898_(ItemStack stack) {
      return stack.m_204117_(IafItemTags.BREED_AMPITHERE);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_20069_() && this.f_20899_) {
         this.m_20334_(this.m_20184_().f_82479_, this.m_20184_().f_82480_ + 0.1D, this.m_20184_().f_82481_);
      }

      if (this.m_6162_() && this.m_5448_() != null) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_27593_()) {
         this.setFlying(false);
      }

      if (this.m_21827_() && this.m_5448_() != null) {
         this.m_6710_((LivingEntity)null);
      }

      boolean flapping = this.m_142039_();
      boolean flying = this.isFlying() && this.isOverAir() || this.isOverAir() && !this.onLeaves();
      boolean diving = flying && this.m_20184_().f_82480_ <= -0.10000000149011612D || this.isFallen;
      boolean sitting = this.m_21827_() && !this.isFlying();
      boolean notGrounded = flying || this.getAnimation() == ANIMATION_WING_BLAST;
      if (!this.m_9236_().f_46443_) {
         if (this.m_21827_() && (this.getCommand() != 1 || this.m_6688_() != null)) {
            this.m_21839_(false);
         }

         if (!this.m_21827_() && this.getCommand() == 1 && this.m_6688_() == null) {
            this.m_21839_(true);
         }

         if (this.m_21827_()) {
            this.m_21573_().m_26573_();
         }

         if (flying) {
            ++this.ticksFlying;
         } else {
            this.ticksFlying = 0;
         }
      }

      if (this.isFlying() && this.m_20096_()) {
         this.setFlying(false);
      }

      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }

      if (this.flightCooldown > 0) {
         --this.flightCooldown;
      }

      if (!this.m_9236_().f_46443_) {
         if (this.flightBehavior == EntityAmphithere.FlightBehavior.CIRCLE) {
            ++this.ticksCircling;
         } else {
            this.ticksCircling = 0;
         }
      }

      if (this.getUntamedRider() != null && !this.m_21824_()) {
         ++this.ridingTime;
      }

      if (this.getUntamedRider() == null) {
         this.ridingTime = 0;
      }

      if (!this.m_21824_() && this.ridingTime > IafConfig.amphithereTameTime && this.getUntamedRider() != null && this.getUntamedRider() instanceof Player) {
         this.m_9236_().m_7605_(this, (byte)45);
         this.m_21828_((Player)this.getUntamedRider());
         if (this.m_5448_() == this.getUntamedRider()) {
            this.m_6710_((LivingEntity)null);
         }
      }

      if (this.isStill()) {
         ++this.ticksStill;
      } else {
         this.ticksStill = 0;
      }

      if (!this.isFlying() && !this.m_6162_() && (this.m_20096_() && this.f_19796_.m_188503_(200) == 0 && this.flightCooldown == 0 && this.m_20197_().isEmpty() && !this.m_21525_() && this.canMove() || this.m_20186_() < -1.0D)) {
         this.m_20334_(this.m_20184_().f_82479_, this.m_20184_().f_82480_ + 0.5D, this.m_20184_().f_82481_);
         this.setFlying(true);
      }

      if (this.m_6688_() != null && this.isFlying() && !this.m_20096_()) {
         if (this.m_6688_().m_146909_() > 25.0F && this.m_20184_().f_82480_ > -1.0D) {
            this.m_20334_(this.m_20184_().f_82479_, this.m_20184_().f_82480_ - 0.1D, this.m_20184_().f_82481_);
         }

         if (this.m_6688_().m_146909_() < -25.0F && this.m_20184_().f_82480_ < 1.0D) {
            this.m_20334_(this.m_20184_().f_82479_, this.m_20184_().f_82480_ + 0.1D, this.m_20184_().f_82481_);
         }
      }

      if (notGrounded && this.groundProgress > 0.0F) {
         this.groundProgress -= 2.0F;
      } else if (!notGrounded && this.groundProgress < 20.0F) {
         this.groundProgress += 2.0F;
      }

      if (diving && this.diveProgress < 20.0F) {
         ++this.diveProgress;
      } else if (!diving && this.diveProgress > 0.0F) {
         --this.diveProgress;
      }

      if (this.isFallen && this.flightBehavior != EntityAmphithere.FlightBehavior.NONE) {
         this.flightBehavior = EntityAmphithere.FlightBehavior.NONE;
      }

      if (this.flightBehavior == EntityAmphithere.FlightBehavior.NONE && this.m_6688_() == null && this.isFlying()) {
         this.m_20334_(this.m_20184_().f_82479_, this.m_20184_().f_82480_ - 0.3D, this.m_20184_().f_82481_);
      }

      if (this.isFlying() && !this.m_20096_() && this.isFallen && this.m_6688_() == null) {
         this.m_20334_(this.m_20184_().f_82479_, this.m_20184_().f_82480_ - 0.2D, this.m_20184_().f_82481_);
         this.m_146926_(Math.max(this.m_146909_() + 5.0F, 75.0F));
      }

      if (this.isFallen && this.m_20096_()) {
         this.setFlying(false);
         if (this.m_21824_()) {
            this.flightCooldown = 50;
         } else {
            this.flightCooldown = 12000;
         }

         this.isFallen = false;
      }

      if (flying && this.isOverAir()) {
         if (this.getRidingPlayer() == null && this.navigatorType != 1) {
            this.switchNavigator(1);
         }

         if (this.getRidingPlayer() != null && this.navigatorType != 2) {
            this.switchNavigator(2);
         }
      }

      if (!flying && this.navigatorType != 0) {
         this.switchNavigator(0);
      }

      if ((this.hasHomePosition || this.getCommand() == 2) && this.flightBehavior == EntityAmphithere.FlightBehavior.WANDER) {
         this.flightBehavior = EntityAmphithere.FlightBehavior.CIRCLE;
      }

      if (flapping && this.flapProgress < 10.0F) {
         ++this.flapProgress;
      } else if (!flapping && this.flapProgress > 0.0F) {
         --this.flapProgress;
      }

      if (this.flapTicks > 0) {
         --this.flapTicks;
      }

      if (this.m_9236_().f_46443_) {
         if (!this.m_20096_()) {
            if (this.m_20160_()) {
               this.roll_buffer.calculateChainFlapBufferHead(40.0F, 1, 2.0F, 0.5F, this);
            } else {
               this.f_20883_ = this.m_146908_();
               this.roll_buffer.calculateChainFlapBuffer(70.0F, 1, 2.0F, 0.5F, this);
            }

            this.pitch_buffer.calculateChainPitchBuffer(90.0F, 10, 10.0F, 0.5F, this);
         }

         this.tail_buffer.calculateChainSwingBuffer(70.0F, 20, 5.0F, this);
      }

      if (this.changedFlightBehavior) {
         this.changedFlightBehavior = false;
      }

      if (!flapping && (this.m_20184_().f_82480_ > 0.15000000596046448D || this.m_20184_().f_82480_ > 0.0D && this.f_19797_ % 200 == 0) && this.isOverAir()) {
         this.flapWings();
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean m_142039_() {
      return this.flapTicks > 0;
   }

   public int getCommand() {
      return (Integer)this.f_19804_.m_135370_(COMMAND);
   }

   public void setCommand(int command) {
      this.f_19804_.m_135381_(COMMAND, command);
      this.m_21839_(command == 1);
   }

   public void flapWings() {
      this.flapTicks = 20;
   }

   public boolean m_21827_() {
      if (this.m_9236_().f_46443_) {
         boolean isSitting = ((Byte)this.f_19804_.m_135370_(f_21798_) & 1) != 0;
         this.isSitting = isSitting;
         return isSitting;
      } else {
         return this.isSitting;
      }
   }

   public void m_21839_(boolean sitting) {
      if (!this.m_9236_().f_46443_) {
         this.isSitting = sitting;
      }

      byte b0 = (Byte)this.f_19804_.m_135370_(f_21798_);
      if (sitting) {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 | 1));
      } else {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 & -2));
      }

   }

   @Nullable
   public LivingEntity m_6688_() {
      Iterator var1 = this.m_20197_().iterator();

      while(var1.hasNext()) {
         Entity passenger = (Entity)var1.next();
         if (passenger instanceof Player && this.m_5448_() != passenger) {
            Player player = (Player)passenger;
            if (this.m_21824_() && this.m_21805_() != null && this.m_21805_().equals(player.m_20148_())) {
               return player;
            }
         }
      }

      return null;
   }

   @Nullable
   public Entity getUntamedRider() {
      Iterator var1 = this.m_20197_().iterator();

      Entity passenger;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         passenger = (Entity)var1.next();
      } while(!(passenger instanceof Player));

      return passenger;
   }

   public boolean m_7307_(@NotNull Entity entityIn) {
      if (this.m_21824_()) {
         LivingEntity livingentity = this.m_269323_();
         if (entityIn == livingentity) {
            return true;
         }

         if (entityIn instanceof TamableAnimal) {
            return ((TamableAnimal)entityIn).m_21830_(livingentity);
         }

         if (livingentity != null) {
            return livingentity.m_7307_(entityIn);
         }
      }

      return super.m_7307_(entityIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.amphithereMaxHealth).m_22268_(Attributes.f_22279_, 0.4D).m_22268_(Attributes.f_22281_, IafConfig.amphithereAttackStrength).m_22268_(Attributes.f_22280_, IafConfig.amphithereFlightSpeed).m_22268_(Attributes.f_22277_, 32.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.amphithereMaxHealth);
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.amphithereAttackStrength);
      this.m_21051_(Attributes.f_22280_).m_22100_(IafConfig.amphithereFlightSpeed);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(FLYING, false);
      this.f_19804_.m_135372_(FLAP_TICKS, 0);
      this.f_19804_.m_135372_(CONTROL_STATE, (byte)0);
      this.f_19804_.m_135372_(COMMAND, 0);
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128379_("Flying", this.isFlying());
      compound.m_128405_("FlightCooldown", this.flightCooldown);
      compound.m_128405_("RidingTime", this.ridingTime);
      compound.m_128379_("HasHomePosition", this.hasHomePosition);
      if (this.homePos != null && this.hasHomePosition) {
         compound.m_128405_("HomeAreaX", this.homePos.m_123341_());
         compound.m_128405_("HomeAreaY", this.homePos.m_123342_());
         compound.m_128405_("HomeAreaZ", this.homePos.m_123343_());
      }

      compound.m_128405_("Command", this.getCommand());
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.setFlying(compound.m_128471_("Flying"));
      this.flightCooldown = compound.m_128451_("FlightCooldown");
      this.ridingTime = compound.m_128451_("RidingTime");
      this.hasHomePosition = compound.m_128471_("HasHomePosition");
      if (this.hasHomePosition && compound.m_128451_("HomeAreaX") != 0 && compound.m_128451_("HomeAreaY") != 0 && compound.m_128451_("HomeAreaZ") != 0) {
         this.homePos = new BlockPos(compound.m_128451_("HomeAreaX"), compound.m_128451_("HomeAreaY"), compound.m_128451_("HomeAreaZ"));
      }

      this.setCommand(compound.m_128451_("Command"));
      this.setConfigurableAttributes();
   }

   public boolean getCanSpawnHere() {
      int i = Mth.m_14107_(this.m_20185_());
      int j = Mth.m_14107_(this.m_20191_().f_82289_);
      int k = Mth.m_14107_(this.m_20189_());
      BlockPos blockpos = new BlockPos(i, j, k);
      this.m_9236_().m_8055_(blockpos.m_7495_()).m_60734_();
      return this.m_9236_().m_46861_(blockpos.m_7494_());
   }

   public void m_8119_() {
      super.m_8119_();
      LivingEntity target = this.m_5448_();
      double dist;
      if (target != null && this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 7) {
         dist = this.m_20280_(target);
         if (dist < 10.0D) {
            target.m_147240_(0.6000000238418579D, (double)Mth.m_14031_(this.m_146908_() * 0.017453292F), (double)(-Mth.m_14089_(this.m_146908_() * 0.017453292F)));
            target.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (this.getAnimation() == ANIMATION_WING_BLAST && this.getAnimationTick() == 5) {
         this.m_5496_(IafSoundRegistry.AMPHITHERE_GUST, 1.0F, 1.0F);
      }

      if ((this.getAnimation() == ANIMATION_BITE || this.getAnimation() == ANIMATION_BITE_RIDER) && this.getAnimationTick() == 1) {
         this.m_5496_(IafSoundRegistry.AMPHITHERE_BITE, 1.0F, 1.0F);
      }

      Vec3 Vector3d;
      if (target != null && this.getAnimation() == ANIMATION_WING_BLAST && this.getAnimationTick() > 5 && this.getAnimationTick() < 22) {
         dist = this.m_20280_(target);
         if (dist < 25.0D) {
            target.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() / 2));
            target.f_19812_ = true;
            if (!(this.f_19796_.m_188500_() < this.m_21051_(Attributes.f_22278_).m_22135_())) {
               this.f_19812_ = true;
               double d1 = target.m_20185_() - this.m_20185_();

               double d0;
               for(d0 = target.m_20189_() - this.m_20189_(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                  d1 = (Math.random() - Math.random()) * 0.01D;
               }

               Vec3 Vector3d = this.m_20184_();
               Vector3d = (new Vec3(d0, 0.0D, d1)).m_82541_().m_82490_(0.5D);
               this.m_20334_(Vector3d.f_82479_ / 2.0D - Vector3d.f_82479_, this.m_20096_() ? Math.min(0.4D, Vector3d.f_82480_ / 2.0D + 0.5D) : Vector3d.f_82480_, Vector3d.f_82481_ / 2.0D - Vector3d.f_82481_);
            }
         }
      }

      if (this.getAnimation() == ANIMATION_TAIL_WHIP && target != null && this.getAnimationTick() == 7) {
         dist = this.m_20280_(target);
         if (dist < 10.0D) {
            target.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            target.f_19812_ = true;
            float f = Mth.m_14116_(0.5F);
            double d1 = target.m_20185_() - this.m_20185_();

            double d0;
            for(d0 = target.m_20189_() - this.m_20189_(); d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
               d1 = (Math.random() - Math.random()) * 0.01D;
            }

            Vector3d = this.m_20184_();
            Vec3 Vector3d1 = (new Vec3(d0, 0.0D, d1)).m_82541_().m_82490_(0.5D);
            this.m_20334_(Vector3d.f_82479_ / 2.0D - Vector3d1.f_82479_, this.m_20096_() ? Math.min(0.4D, Vector3d.f_82480_ / 2.0D + 0.5D) : Vector3d.f_82480_, Vector3d.f_82481_ / 2.0D - Vector3d1.f_82481_);
         }
      }

      if (this.isGoingUp() && !this.m_9236_().f_46443_ && !this.isFlying()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 1.0D, 0.0D));
         this.setFlying(true);
      }

      if (!this.isOverAir() && this.isFlying() && this.ticksFlying > 25) {
         this.setFlying(false);
      }

      if (this.dismountIAF() && this.isFlying() && this.m_20096_()) {
         this.setFlying(false);
      }

      LivingEntity riderTarget;
      if (this.getUntamedRider() != null && this.getUntamedRider().m_6144_()) {
         Entity var3 = this.getUntamedRider();
         if (var3 instanceof LivingEntity) {
            riderTarget = (LivingEntity)var3;
            EntityDataProvider.getCapability(riderTarget).ifPresent((data) -> {
               data.miscData.setDismounted(true);
            });
         }

         this.getUntamedRider().m_8127_();
      }

      if (this.attack() && this.m_6688_() != null && this.m_6688_() instanceof Player) {
         riderTarget = DragonUtils.riderLookingAtEntity(this, (Player)this.m_6688_(), 2.5D);
         if (this.getAnimation() != ANIMATION_BITE) {
            this.setAnimation(ANIMATION_BITE);
         }

         if (riderTarget != null) {
            riderTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (target != null && this.m_21830_(target)) {
         this.m_6710_((LivingEntity)null);
      }

      if (target != null && this.m_20096_() && this.isFlying() && this.ticksFlying > 40) {
         this.setFlying(false);
      }

   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() != ANIMATION_BITE && this.getAnimation() != ANIMATION_TAIL_WHIP && this.getAnimation() != ANIMATION_WING_BLAST && this.m_6688_() == null) {
         if (this.f_19796_.m_188499_()) {
            this.setAnimation(ANIMATION_BITE);
         } else {
            this.setAnimation(!this.m_217043_().m_188499_() && !this.isFlying() ? ANIMATION_TAIL_WHIP : ANIMATION_WING_BLAST);
         }

         return true;
      } else {
         return false;
      }
   }

   @Nullable
   public Player getRidingPlayer() {
      LivingEntity var2 = this.m_6688_();
      if (var2 instanceof Player) {
         Player player = (Player)var2;
         return player;
      } else {
         return null;
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

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public boolean isGoingUp() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) & 1) == 1;
   }

   public boolean isGoingDown() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 1 & 1) == 1;
   }

   public boolean attack() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 2 & 1) == 1;
   }

   public boolean dismountIAF() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 3 & 1) == 1;
   }

   public void up(boolean up) {
      this.setStateField(0, up);
   }

   public void down(boolean down) {
      this.setStateField(1, down);
   }

   public void attack(boolean attack) {
      this.setStateField(2, attack);
   }

   public void strike(boolean strike) {
   }

   public void dismount(boolean dismount) {
      this.setStateField(3, dismount);
   }

   private void setStateField(int i, boolean newState) {
      byte prevState = (Byte)this.f_19804_.m_135370_(CONTROL_STATE);
      if (newState) {
         this.f_19804_.m_135381_(CONTROL_STATE, (byte)(prevState | 1 << i));
      } else {
         this.f_19804_.m_135381_(CONTROL_STATE, (byte)(prevState & ~(1 << i)));
      }

   }

   public byte getControlState() {
      return (Byte)this.f_19804_.m_135370_(CONTROL_STATE);
   }

   public void setControlState(byte state) {
      this.f_19804_.m_135381_(CONTROL_STATE, state);
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.AMPHITHERE_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.AMPHITHERE_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.AMPHITHERE_DIE;
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
      return new Animation[]{ANIMATION_BITE, ANIMATION_BITE_RIDER, ANIMATION_WING_BLAST, ANIMATION_TAIL_WHIP, ANIMATION_SPEAK};
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

   public boolean isBlinking() {
      return this.f_19797_ % 50 > 40;
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageableEntity) {
      EntityAmphithere amphithere = new EntityAmphithere((EntityType)IafEntityRegistry.AMPHITHERE.get(), this.m_9236_());
      amphithere.setVariant(this.getVariant());
      return amphithere;
   }

   public int m_213860_() {
      return 10;
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setVariant(this.m_217043_().m_188503_(5));
      return spawnDataIn;
   }

   public boolean canPhaseThroughBlock(LevelAccessor world, BlockPos pos) {
      return world.m_8055_(pos).m_60734_() instanceof LeavesBlock;
   }

   protected float m_245547_(@NotNull Player pPlayer) {
      return !this.isFlying() && !this.isHovering() ? (float)this.m_21133_(Attributes.f_22279_) * 0.5F : (float)this.m_21133_(Attributes.f_22280_) * 2.0F;
   }

   public void m_7023_(@NotNull Vec3 travelVector) {
      if (this.m_6109_()) {
         if (this.m_20069_()) {
            this.m_19920_(0.02F, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.800000011920929D));
         } else if (this.m_20077_()) {
            this.m_19920_(0.02F, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.5D));
         } else if (!this.isFlying() && !this.isHovering()) {
            super.m_7023_(travelVector);
         } else {
            this.m_19920_(0.1F, travelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9D));
         }
      } else {
         super.m_7023_(travelVector);
      }

   }

   protected void m_274498_(@NotNull Player player, @NotNull Vec3 travelVector) {
      super.m_274498_(player, travelVector);
      Vec2 vec2 = this.getRiddenRotation(player);
      this.m_19915_(vec2.f_82471_, vec2.f_82470_);
      this.f_19859_ = this.f_20883_ = this.f_20885_ = this.m_146908_();
      if (this.m_6109_()) {
         Vec3 vec3 = this.m_20184_();
         float vertical = this.isGoingUp() ? 0.2F : (this.isGoingDown() ? -0.2F : 0.0F);
         if (!this.isFlying() && !this.isHovering()) {
            vertical = (float)travelVector.f_82480_;
         }

         this.m_20256_(vec3.m_82520_(0.0D, (double)vertical, 0.0D));
      }

   }

   @NotNull
   protected Vec3 m_274312_(Player player, @NotNull Vec3 travelVector) {
      float f = player.f_20900_ * 0.5F;
      float f1 = player.f_20902_;
      if (f1 <= 0.0F) {
         f1 *= 0.25F;
      }

      return new Vec3((double)f, 0.0D, (double)f1);
   }

   protected Vec2 getRiddenRotation(LivingEntity entity) {
      return new Vec2(entity.m_146909_() * 0.5F, entity.m_146908_());
   }

   public boolean canMove() {
      return this.m_6688_() == null && this.sitProgress == 0.0F && !this.m_21827_();
   }

   public void m_7822_(byte id) {
      if (id == 45) {
         this.playEffect();
      } else {
         super.m_7822_(id);
      }

   }

   protected void playEffect() {
      for(int i = 0; i < 7; ++i) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         this.m_9236_().m_7106_(ParticleTypes.f_123750_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + 0.5D + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d0, d1, d2);
      }

   }

   public void onHearFlute(Player player) {
      if (!this.m_20096_() && this.m_21824_()) {
         this.isFallen = true;
      }

   }

   public boolean m_21532_() {
      return true;
   }

   public double getFlightSpeedModifier() {
      return 0.555D;
   }

   public boolean fliesLikeElytra() {
      return !this.m_20096_();
   }

   private boolean isOverAir() {
      return this.m_9236_().m_46859_(this.m_20183_().m_7495_());
   }

   public boolean canBlockPosBeSeen(BlockPos pos) {
      Vec3 Vector3d = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
      Vec3 Vector3d1 = new Vec3((double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.5D, (double)pos.m_123343_() + 0.5D);
      return this.m_9236_().m_45547_(new ClipContext(Vector3d, Vector3d1, net.minecraft.world.level.ClipContext.Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityAmphithere.class, EntityDataSerializers.f_135028_);
      FLYING = SynchedEntityData.m_135353_(EntityAmphithere.class, EntityDataSerializers.f_135035_);
      FLAP_TICKS = SynchedEntityData.m_135353_(EntityAmphithere.class, EntityDataSerializers.f_135028_);
      CONTROL_STATE = SynchedEntityData.m_135353_(EntityAmphithere.class, EntityDataSerializers.f_135027_);
      COMMAND = SynchedEntityData.m_135353_(EntityAmphithere.class, EntityDataSerializers.f_135028_);
      ANIMATION_BITE = Animation.create(15);
      ANIMATION_BITE_RIDER = Animation.create(15);
      ANIMATION_WING_BLAST = Animation.create(30);
      ANIMATION_TAIL_WHIP = Animation.create(30);
      ANIMATION_SPEAK = Animation.create(10);
   }

   public static enum FlightBehavior {
      CIRCLE,
      WANDER,
      NONE;

      // $FF: synthetic method
      private static EntityAmphithere.FlightBehavior[] $values() {
         return new EntityAmphithere.FlightBehavior[]{CIRCLE, WANDER, NONE};
      }
   }

   class AIFlyWander extends Goal {
      BlockPos target;

      public AIFlyWander() {
      }

      public boolean m_8036_() {
         if (EntityAmphithere.this.flightBehavior == EntityAmphithere.FlightBehavior.WANDER && EntityAmphithere.this.canMove()) {
            if (!EntityAmphithere.this.isFlying()) {
               return false;
            } else {
               this.target = EntityAmphithere.getPositionRelativetoGround(EntityAmphithere.this, EntityAmphithere.this.m_9236_(), EntityAmphithere.this.m_146903_() + EntityAmphithere.this.f_19796_.m_188503_(30) - 15, EntityAmphithere.this.m_146907_() + EntityAmphithere.this.f_19796_.m_188503_(30) - 15, EntityAmphithere.this.f_19796_);
               EntityAmphithere.this.orbitPos = null;
               return !EntityAmphithere.this.m_21566_().m_24995_() || EntityAmphithere.this.ticksStill >= 50;
            }
         } else {
            return false;
         }
      }

      protected boolean isDirectPathBetweenPoints(Entity e) {
         return EntityAmphithere.this.canBlockPosBeSeen(this.target);
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8037_() {
         if (!this.isDirectPathBetweenPoints(EntityAmphithere.this)) {
            this.target = EntityAmphithere.getPositionRelativetoGround(EntityAmphithere.this, EntityAmphithere.this.m_9236_(), EntityAmphithere.this.m_146903_() + EntityAmphithere.this.f_19796_.m_188503_(30) - 15, EntityAmphithere.this.m_146907_() + EntityAmphithere.this.f_19796_.m_188503_(30) - 15, EntityAmphithere.this.f_19796_);
         }

         if (EntityAmphithere.this.m_9236_().m_46859_(this.target)) {
            EntityAmphithere.this.f_21342_.m_6849_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 0.25D);
            if (EntityAmphithere.this.m_5448_() == null) {
               EntityAmphithere.this.m_21563_().m_24950_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 180.0F, 20.0F);
            }
         }

      }

      public boolean m_183429_() {
         return true;
      }
   }

   class AIFlyCircle extends Goal {
      BlockPos target;

      public AIFlyCircle() {
      }

      public boolean m_8036_() {
         if (EntityAmphithere.this.flightBehavior == EntityAmphithere.FlightBehavior.CIRCLE && EntityAmphithere.this.canMove()) {
            if (EntityAmphithere.this.isFlying()) {
               EntityAmphithere.this.orbitPos = EntityAmphithere.getPositionRelativetoGround(EntityAmphithere.this, EntityAmphithere.this.m_9236_(), EntityAmphithere.this.m_146903_() + EntityAmphithere.this.f_19796_.m_188503_(30) - 15, EntityAmphithere.this.m_146907_() + EntityAmphithere.this.f_19796_.m_188503_(30) - 15, EntityAmphithere.this.f_19796_);
               this.target = EntityAmphithere.getPositionInOrbit(EntityAmphithere.this, EntityAmphithere.this.m_9236_(), EntityAmphithere.this.orbitPos, EntityAmphithere.this.f_19796_);
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      protected boolean isDirectPathBetweenPoints() {
         return EntityAmphithere.this.canBlockPosBeSeen(this.target);
      }

      public boolean m_8045_() {
         return false;
      }

      public void m_8037_() {
         if (!this.isDirectPathBetweenPoints()) {
            this.target = EntityAmphithere.getPositionInOrbit(EntityAmphithere.this, EntityAmphithere.this.m_9236_(), EntityAmphithere.this.orbitPos, EntityAmphithere.this.f_19796_);
         }

         if (EntityAmphithere.this.m_9236_().m_46859_(this.target)) {
            EntityAmphithere.this.f_21342_.m_6849_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 0.25D);
            if (EntityAmphithere.this.m_5448_() == null) {
               EntityAmphithere.this.m_21563_().m_24950_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D, 180.0F, 20.0F);
            }
         }

      }

      public boolean m_183429_() {
         return true;
      }
   }

   class AILandWander extends WaterAvoidingRandomStrollGoal {
      public AILandWander(PathfinderMob creature, double speed) {
         super(creature, speed, 10.0F);
      }

      public boolean m_8036_() {
         return this.f_25725_.m_20096_() && super.m_8036_() && ((EntityAmphithere)this.f_25725_).canMove();
      }

      public boolean m_183429_() {
         return true;
      }
   }

   class FlyMoveHelper extends MoveControl {
      public FlyMoveHelper(EntityAmphithere entity) {
         super(entity);
         this.f_24978_ = 1.75D;
      }

      public void m_8126_() {
         if (EntityAmphithere.this.canMove()) {
            if (EntityAmphithere.this.f_19862_) {
               EntityAmphithere.this.m_146922_(EntityAmphithere.this.m_146908_() + 180.0F);
               this.f_24978_ = 0.10000000149011612D;
               BlockPos target = EntityAmphithere.getPositionRelativetoGround(EntityAmphithere.this, EntityAmphithere.this.m_9236_(), EntityAmphithere.this.m_146903_() + EntityAmphithere.this.f_19796_.m_188503_(15) - 7, EntityAmphithere.this.m_146907_() + EntityAmphithere.this.f_19796_.m_188503_(15) - 7, EntityAmphithere.this.f_19796_);
               this.f_24975_ = (double)target.m_123341_();
               this.f_24976_ = (double)target.m_123342_();
               this.f_24977_ = (double)target.m_123343_();
            }

            if (this.f_24981_ == Operation.MOVE_TO) {
               double d0 = this.f_24975_ - EntityAmphithere.this.m_20185_();
               double d1 = this.f_24976_ - EntityAmphithere.this.m_20186_();
               double d2 = this.f_24977_ - EntityAmphithere.this.m_20189_();
               double d3 = d0 * d0 + d1 * d1 + d2 * d2;
               d3 = (double)Mth.m_14116_((float)d3);
               if (d3 < 6.0D && EntityAmphithere.this.m_5448_() == null) {
                  if (!EntityAmphithere.this.changedFlightBehavior && EntityAmphithere.this.flightBehavior == EntityAmphithere.FlightBehavior.WANDER && EntityAmphithere.this.f_19796_.m_188503_(30) == 0) {
                     EntityAmphithere.this.flightBehavior = EntityAmphithere.FlightBehavior.CIRCLE;
                     EntityAmphithere.this.changedFlightBehavior = true;
                  }

                  if (!EntityAmphithere.this.changedFlightBehavior && EntityAmphithere.this.flightBehavior == EntityAmphithere.FlightBehavior.CIRCLE && EntityAmphithere.this.f_19796_.m_188503_(5) == 0 && EntityAmphithere.this.ticksCircling > 150) {
                     EntityAmphithere.this.flightBehavior = EntityAmphithere.FlightBehavior.WANDER;
                     EntityAmphithere.this.changedFlightBehavior = true;
                  }

                  if (EntityAmphithere.this.hasHomePosition && EntityAmphithere.this.flightBehavior != EntityAmphithere.FlightBehavior.NONE || EntityAmphithere.this.getCommand() == 2) {
                     EntityAmphithere.this.flightBehavior = EntityAmphithere.FlightBehavior.CIRCLE;
                  }
               }

               if (d3 < 1.0D && EntityAmphithere.this.m_5448_() == null) {
                  this.f_24981_ = Operation.WAIT;
                  EntityAmphithere.this.m_20256_(EntityAmphithere.this.m_20184_().m_82542_(0.5D, 0.5D, 0.5D));
               } else {
                  EntityAmphithere.this.m_20256_(EntityAmphithere.this.m_20184_().m_82520_(d0 / d3 * 0.5D * this.f_24978_, d1 / d3 * 0.5D * this.f_24978_, d2 / d3 * 0.5D * this.f_24978_));
                  float f1 = (float)(-(Mth.m_14136_(d1, d3) * 57.29577951308232D));
                  EntityAmphithere.this.m_146926_(f1);
                  if (EntityAmphithere.this.m_5448_() == null) {
                     EntityAmphithere.this.m_146922_(-((float)Mth.m_14136_(EntityAmphithere.this.m_20184_().f_82479_, EntityAmphithere.this.m_20184_().f_82481_)) * 57.295776F);
                     EntityAmphithere.this.f_20883_ = EntityAmphithere.this.m_146908_();
                  } else {
                     double d4 = EntityAmphithere.this.m_5448_().m_20185_() - EntityAmphithere.this.m_20185_();
                     double d5 = EntityAmphithere.this.m_5448_().m_20189_() - EntityAmphithere.this.m_20189_();
                     EntityAmphithere.this.m_146922_(-((float)Mth.m_14136_(d4, d5)) * 57.295776F);
                     EntityAmphithere.this.f_20883_ = EntityAmphithere.this.m_146908_();
                  }
               }
            }

         }
      }
   }
}
