package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.citadel.server.entity.collision.ICustomCollisions;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.ai.DeathWormAIAttack;
import com.github.alexthe666.iceandfire.entity.ai.DeathWormAIFindSandTarget;
import com.github.alexthe666.iceandfire.entity.ai.DeathWormAIGetInSand;
import com.github.alexthe666.iceandfire.entity.ai.DeathWormAIJump;
import com.github.alexthe666.iceandfire.entity.ai.DeathWormAITarget;
import com.github.alexthe666.iceandfire.entity.ai.DeathWormAIWander;
import com.github.alexthe666.iceandfire.entity.ai.DeathwormAITargetItems;
import com.github.alexthe666.iceandfire.entity.ai.EntityGroundAIRide;
import com.github.alexthe666.iceandfire.entity.ai.IAFLookHelper;
import com.github.alexthe666.iceandfire.entity.util.BlockLaunchExplosion;
import com.github.alexthe666.iceandfire.entity.util.ChainBuffer;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.EntityUtil;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.ICustomMoveController;
import com.github.alexthe666.iceandfire.entity.util.IGroundMount;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.ISyncMount;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.message.MessageDeathWormHitbox;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.pathfinding.PathNavigateDeathWormLand;
import com.github.alexthe666.iceandfire.pathfinding.PathNavigateDeathWormSand;
import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.PositionImpl;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class EntityDeathWorm extends TamableAnimal implements ISyncMount, ICustomCollisions, IBlacklistedFromStatues, IAnimatedEntity, IVillagerFear, IAnimalFear, IGroundMount, IHasCustomizableAttributes, ICustomMoveController {
   public static final ResourceLocation TAN_LOOT = new ResourceLocation("iceandfire", "entities/deathworm_tan");
   public static final ResourceLocation WHITE_LOOT = new ResourceLocation("iceandfire", "entities/deathworm_white");
   public static final ResourceLocation RED_LOOT = new ResourceLocation("iceandfire", "entities/deathworm_red");
   public static final ResourceLocation TAN_GIANT_LOOT = new ResourceLocation("iceandfire", "entities/deathworm_tan_giant");
   public static final ResourceLocation WHITE_GIANT_LOOT = new ResourceLocation("iceandfire", "entities/deathworm_white_giant");
   public static final ResourceLocation RED_GIANT_LOOT = new ResourceLocation("iceandfire", "entities/deathworm_red_giant");
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Float> SCALE;
   private static final EntityDataAccessor<Integer> JUMP_TICKS;
   private static final EntityDataAccessor<Byte> CONTROL_STATE;
   private static final EntityDataAccessor<Integer> WORM_AGE;
   private static final EntityDataAccessor<BlockPos> HOME;
   public static Animation ANIMATION_BITE;
   public ChainBuffer tail_buffer;
   public float jumpProgress;
   public float prevJumpProgress;
   private int animationTick;
   private boolean willExplode = false;
   private int ticksTillExplosion = 60;
   private Animation currentAnimation;
   private EntityMutlipartPart[] segments = new EntityMutlipartPart[6];
   private boolean isSandNavigator;
   private final float prevScale = 0.0F;
   private final LookControl lookHelper;
   private int growthCounter = 0;
   private Player thrower;
   public DeathwormAITargetItems targetItemsGoal;

   public EntityDeathWorm(EntityType<EntityDeathWorm> type, Level worldIn) {
      super(type, worldIn);
      this.m_21441_(BlockPathTypes.OPEN, 2.0F);
      this.m_21441_(BlockPathTypes.WATER, 4.0F);
      this.m_21441_(BlockPathTypes.WATER_BORDER, 4.0F);
      this.lookHelper = new IAFLookHelper(this);
      this.f_19811_ = true;
      if (worldIn.f_46443_) {
         this.tail_buffer = new ChainBuffer();
      }

      this.m_274367_(1.0F);
      this.switchNavigator(false);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new EntityGroundAIRide(this));
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new DeathWormAIAttack(this));
      this.f_21345_.m_25352_(3, new DeathWormAIJump(this, 12));
      this.f_21345_.m_25352_(4, new DeathWormAIFindSandTarget(this, 10));
      this.f_21345_.m_25352_(5, new DeathWormAIGetInSand(this, 1.0D));
      this.f_21345_.m_25352_(6, new DeathWormAIWander(this, 1.0D));
      this.f_21346_.m_25352_(2, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(3, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, this.targetItemsGoal = new DeathwormAITargetItems(this, false, false));
      this.f_21346_.m_25352_(5, new DeathWormAITarget(this, LivingEntity.class, false, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity input) {
            if (EntityDeathWorm.this.m_21824_()) {
               return input instanceof Monster;
            } else if (input != null) {
               if (!input.m_20069_() && DragonUtils.isAlive(input) && !EntityDeathWorm.this.m_21830_(input)) {
                  return !(input instanceof Player) && !(input instanceof Animal) ? IafConfig.deathWormAttackMonsters : true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }));
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.deathWormMaxHealth).m_22268_(Attributes.f_22279_, 0.15D).m_22268_(Attributes.f_22281_, IafConfig.deathWormAttackStrength).m_22268_(Attributes.f_22277_, (double)IafConfig.deathWormTargetSearchLength).m_22268_(Attributes.f_22284_, 3.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(Math.max(6.0D, IafConfig.deathWormMaxHealth));
      this.m_21051_(Attributes.f_22281_).m_22100_(Math.max(1.0D, IafConfig.deathWormAttackStrength));
      this.m_21051_(Attributes.f_22277_).m_22100_((double)IafConfig.deathWormTargetSearchLength);
   }

   @NotNull
   public LookControl m_21563_() {
      return this.lookHelper;
   }

   @NotNull
   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public boolean getCanSpawnHere() {
      int i = Mth.m_14107_(this.m_20185_());
      int j = Mth.m_14107_(this.m_20191_().f_82289_);
      int k = Mth.m_14107_(this.m_20189_());
      BlockPos blockpos = new BlockPos(i, j, k);
      this.m_9236_().m_8055_(blockpos.m_7495_()).m_204336_(BlockTags.f_13029_);
      return this.m_9236_().m_8055_(blockpos.m_7495_()).m_204336_(BlockTags.f_13029_) && this.m_9236_().m_46803_(blockpos) > 8;
   }

   public void onUpdateParts() {
      this.addSegmentsToWorld();
   }

   public int m_213860_() {
      return this.m_6134_() > 3.0F ? 20 : 10;
   }

   public void initSegments(float scale) {
      this.segments = new EntityMutlipartPart[7];

      for(int i = 0; i < this.segments.length; ++i) {
         this.segments[i] = new EntitySlowPart(this, (-0.8F - (float)i * 0.8F) * scale, 0.0F, 0.0F, 0.7F * scale, 0.7F * scale, 1.0F);
         this.segments[i].m_20359_(this);
         this.segments[i].setParent(this);
      }

   }

   private void addSegmentsToWorld() {
      EntityMutlipartPart[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         EntityMutlipartPart entity = var1[var3];
         EntityUtil.updatePart(entity, this);
      }

   }

   private void clearSegments() {
      EntityMutlipartPart[] var1 = this.segments;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Entity entity = var1[var3];
         if (entity != null) {
            entity.m_6074_();
            entity.m_142687_(RemovalReason.KILLED);
         }
      }

   }

   public void setExplosive(boolean explosive, Player thrower) {
      this.willExplode = true;
      this.ticksTillExplosion = 60;
      this.thrower = thrower;
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(ANIMATION_BITE);
         this.m_5496_(this.m_6134_() > 3.0F ? IafSoundRegistry.DEATHWORM_GIANT_ATTACK : IafSoundRegistry.DEATHWORM_ATTACK, 1.0F, 1.0F);
      }

      if (this.m_217043_().m_188503_(3) == 0 && this.m_6134_() > 1.0F && this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_) && !MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this, entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_()))) {
         BlockLaunchExplosion explosion = new BlockLaunchExplosion(this.m_9236_(), this, entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_(), this.m_6134_());
         explosion.m_46061_();
         explosion.m_46075_(true);
      }

      return false;
   }

   public void m_6667_(@NotNull DamageSource cause) {
      this.clearSegments();
      super.m_6667_(cause);
   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      switch(this.getVariant()) {
      case 0:
         return this.m_6134_() > 3.0F ? TAN_GIANT_LOOT : TAN_LOOT;
      case 1:
         return this.m_6134_() > 3.0F ? RED_GIANT_LOOT : RED_LOOT;
      case 2:
         return this.m_6134_() > 3.0F ? WHITE_GIANT_LOOT : WHITE_LOOT;
      default:
         return null;
      }
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      return null;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(SCALE, 1.0F);
      this.f_19804_.m_135372_(CONTROL_STATE, (byte)0);
      this.f_19804_.m_135372_(WORM_AGE, 10);
      this.f_19804_.m_135372_(HOME, BlockPos.f_121853_);
      this.f_19804_.m_135372_(JUMP_TICKS, 0);
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128405_("GrowthCounter", this.growthCounter);
      compound.m_128350_("Scale", this.getDeathwormScale());
      compound.m_128405_("WormAge", this.getWormAge());
      compound.m_128356_("WormHome", this.getWormHome().m_121878_());
      compound.m_128379_("WillExplode", this.willExplode);
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.growthCounter = compound.m_128451_("GrowthCounter");
      this.setDeathWormScale(compound.m_128457_("Scale"));
      this.setWormAge(compound.m_128451_("WormAge"));
      this.setWormHome(BlockPos.m_122022_(compound.m_128454_("WormHome")));
      this.willExplode = compound.m_128471_("WillExplode");
      this.setConfigurableAttributes();
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

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public int getWormJumping() {
      return (Integer)this.f_19804_.m_135370_(JUMP_TICKS);
   }

   public void setWormJumping(int jump) {
      this.f_19804_.m_135381_(JUMP_TICKS, jump);
   }

   public BlockPos getWormHome() {
      return (BlockPos)this.f_19804_.m_135370_(HOME);
   }

   public void setWormHome(BlockPos home) {
      if (home instanceof BlockPos) {
         this.f_19804_.m_135381_(HOME, home);
      }

   }

   public int getWormAge() {
      return Math.max(1, (Integer)this.f_19804_.m_135370_(WORM_AGE));
   }

   public void setWormAge(int age) {
      this.f_19804_.m_135381_(WORM_AGE, age);
   }

   public float m_6134_() {
      return Math.min(this.getDeathwormScale() * ((float)this.getWormAge() / 5.0F), 7.0F);
   }

   public float getDeathwormScale() {
      return (Float)this.f_19804_.m_135370_(SCALE);
   }

   public void setDeathWormScale(float scale) {
      this.f_19804_.m_135381_(SCALE, scale);
      this.updateAttributes();
      this.clearSegments();
      if (!this.m_9236_().f_46443_) {
         this.initSegments(scale * ((float)this.getWormAge() / 5.0F));
         IceAndFire.sendMSGToAll(new MessageDeathWormHitbox(this.m_19879_(), scale * ((float)this.getWormAge() / 5.0F)));
      }

   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setVariant(this.m_217043_().m_188503_(3));
      float size = 0.25F + (float)(Math.random() * 0.3499999940395355D);
      this.setDeathWormScale(this.m_217043_().m_188503_(20) == 0 ? size * 4.0F : size);
      return spawnDataIn;
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         this.m_5618_(passenger.m_146908_());
         float radius = -0.5F * this.m_6134_();
         float angle = 0.017453292F * this.f_20883_;
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         passenger.m_6034_(this.m_20185_() + extraX, this.m_20186_() + (double)this.m_20192_() - 0.550000011920929D, this.m_20189_() + extraZ);
      }

   }

   @Nullable
   public LivingEntity m_6688_() {
      Iterator var1 = this.m_20197_().iterator();

      Entity passenger;
      do {
         if (!var1.hasNext()) {
            return null;
         }

         passenger = (Entity)var1.next();
      } while(!(passenger instanceof Player));

      Player player = (Player)passenger;
      return player;
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      player.m_21120_(hand);
      if (this.getWormAge() > 4 && player.m_20202_() == null && player.m_21205_().m_41720_() == Items.f_42523_ && player.m_21206_().m_41720_() == Items.f_42523_ && !this.m_9236_().f_46443_) {
         player.m_20329_(this);
         return InteractionResult.SUCCESS;
      } else {
         return super.m_6071_(player, hand);
      }
   }

   private void switchNavigator(boolean inSand) {
      if (inSand) {
         this.f_21342_ = new EntityDeathWorm.SandMoveHelper();
         this.f_21344_ = new PathNavigateDeathWormSand(this, this.m_9236_());
         this.isSandNavigator = true;
      } else {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = new PathNavigateDeathWormLand(this, this.m_9236_());
         this.isSandNavigator = false;
      }

   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      if (!source.m_276093_(DamageTypes.f_268612_) && !source.m_276093_(DamageTypes.f_268659_)) {
         return this.m_20160_() && source.m_7639_() != null && this.m_6688_() != null && source.m_7639_() == this.m_6688_() ? false : super.m_6469_(source, amount);
      } else {
         return false;
      }
   }

   public void m_6478_(@NotNull MoverType typeIn, @NotNull Vec3 pos) {
      super.m_6478_(typeIn, pos);
   }

   @NotNull
   public Vec3 m_20272_(@NotNull Vec3 vec) {
      return ICustomCollisions.getAllowedMovementForEntity(this, vec);
   }

   public boolean m_5830_() {
      return this.isInSand() ? false : super.m_5830_();
   }

   protected void m_20314_(double x, double y, double z) {
      PositionImpl blockpos = new PositionImpl(x, y, z);
      Vec3i vec3i = new Vec3i((int)Math.round(blockpos.m_7096_()), (int)Math.round(blockpos.m_7098_()), (int)Math.round(blockpos.m_7094_()));
      Vec3 vector3d = new Vec3(x - blockpos.m_7096_(), y - blockpos.m_7098_(), z - blockpos.m_7094_());
      MutableBlockPos blockpos$mutable = new MutableBlockPos();
      Direction direction = Direction.UP;
      double d0 = Double.MAX_VALUE;
      Direction[] var14 = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST, Direction.UP};
      int var15 = var14.length;

      for(int var16 = 0; var16 < var15; ++var16) {
         Direction direction1 = var14[var16];
         blockpos$mutable.m_122159_(vec3i, direction1);
         if (!this.m_9236_().m_8055_(blockpos$mutable).m_60838_(this.m_9236_(), blockpos$mutable) || this.m_9236_().m_8055_(blockpos$mutable).m_204336_(BlockTags.f_13029_)) {
            double d1 = vector3d.m_82507_(direction1.m_122434_());
            double d2 = direction1.m_122421_() == AxisDirection.POSITIVE ? 1.0D - d1 : d1;
            if (d2 < d0) {
               d0 = d2;
               direction = direction1;
            }
         }
      }

      float f = this.f_19796_.m_188501_() * 0.2F + 0.1F;
      float f1 = (float)direction.m_122421_().m_122540_();
      Vec3 vector3d1 = this.m_20184_().m_82490_(0.75D);
      if (direction.m_122434_() == Axis.X) {
         this.m_20334_((double)(f1 * f), vector3d1.f_82480_, vector3d1.f_82481_);
      } else if (direction.m_122434_() == Axis.Y) {
         this.m_20334_(vector3d1.f_82479_, (double)(f1 * f), vector3d1.f_82481_);
      } else if (direction.m_122434_() == Axis.Z) {
         this.m_20334_(vector3d1.f_82479_, vector3d1.f_82480_, (double)(f1 * f));
      }

   }

   private void updateAttributes() {
      this.m_21051_(Attributes.f_22279_).m_22100_(Math.min(0.2D, 0.15D * (double)this.m_6134_()));
      this.m_21051_(Attributes.f_22281_).m_22100_(Math.max(1.0D, IafConfig.deathWormAttackStrength * (double)this.m_6134_()));
      this.m_21051_(Attributes.f_22276_).m_22100_(Math.max(6.0D, IafConfig.deathWormMaxHealth * (double)this.m_6134_()));
      this.m_21051_(Attributes.f_22277_).m_22100_((double)IafConfig.deathWormTargetSearchLength);
      this.m_21153_((float)this.m_21051_(Attributes.f_22276_).m_22115_());
   }

   public boolean m_214076_(@NotNull ServerLevel world, @NotNull LivingEntity entity) {
      if (this.m_21824_()) {
         this.m_5634_(14.0F);
         return false;
      } else {
         return true;
      }
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

   public void m_8107_() {
      super.m_8107_();
      this.prevJumpProgress = this.jumpProgress;
      if (this.getWormJumping() > 0 && this.jumpProgress < 5.0F) {
         ++this.jumpProgress;
      }

      if (this.getWormJumping() == 0 && this.jumpProgress > 0.0F) {
         --this.jumpProgress;
      }

      if (this.isInSand() && this.f_19862_) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.05D, 0.0D));
      }

      float f;
      if (this.getWormJumping() > 0) {
         f = (float)(-((double)((float)this.m_20184_().f_82480_) * 57.2957763671875D));
         this.m_146926_(f);
         if (this.isInSand() || this.m_20096_()) {
            this.setWormJumping(this.getWormJumping() - 1);
         }
      }

      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_5448_() != null && (!this.m_5448_().m_6084_() || !DragonUtils.isAlive(this.m_5448_()))) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.willExplode) {
         if (this.ticksTillExplosion == 0) {
            boolean b = !MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this, this.m_20185_(), this.m_20186_(), this.m_20189_()));
            if (b) {
               this.m_9236_().m_255391_(this.thrower, this.m_20185_(), this.m_20186_(), this.m_20189_(), 2.5F * this.m_6134_(), false, ExplosionInteraction.MOB);
            }

            this.thrower = null;
         } else {
            --this.ticksTillExplosion;
         }
      }

      if (this.f_19797_ == 1) {
         this.initSegments(this.m_6134_());
      }

      if (this.isInSandStrict()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.08D, 0.0D));
      }

      if (this.growthCounter > 1000 && this.getWormAge() < 5) {
         this.growthCounter = 0;
         this.setWormAge(Math.min(5, this.getWormAge() + 1));
         this.clearSegments();
         this.m_5634_(15.0F);
         this.setDeathWormScale(this.getDeathwormScale());
         if (this.m_9236_().f_46443_) {
            for(int i = 0; (float)i < 10.0F * this.m_6134_(); ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123748_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), (double)((float)this.getSurface((int)Math.floor(this.m_20185_()), (int)Math.floor(this.m_20186_()), (int)Math.floor(this.m_20189_())) + 0.5F), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D);
            }
         }
      }

      if (this.getWormAge() < 5) {
         ++this.growthCounter;
      }

      if (this.m_6688_() != null && this.m_5448_() != null) {
         this.m_21573_().m_26573_();
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_5448_() != null && (double)this.m_20270_(this.m_5448_()) < Math.min(4.0D, 4.0D * (double)this.m_6134_()) && this.getAnimation() == ANIMATION_BITE && this.getAnimationTick() == 5) {
         f = (float)this.m_21051_(Attributes.f_22281_).m_22135_();
         this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), f);
         this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.4000000059604645D, 0.0D));
      }

   }

   public int getWormBrightness(boolean sky) {
      Vec3 vec3 = this.m_20299_(1.0F);

      BlockPos eyePos;
      for(eyePos = BlockPos.m_274446_(vec3); eyePos.m_123342_() < 256 && !this.m_9236_().m_46859_(eyePos); eyePos = eyePos.m_7494_()) {
      }

      int light = this.m_9236_().m_45517_(sky ? LightLayer.SKY : LightLayer.BLOCK, eyePos.m_7494_());
      return light;
   }

   public int getSurface(int x, int y, int z) {
      BlockPos pos;
      for(pos = new BlockPos(x, y, z); !this.m_9236_().m_46859_(pos); pos = pos.m_7494_()) {
      }

      return pos.m_123342_();
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return this.m_6134_() > 3.0F ? IafSoundRegistry.DEATHWORM_GIANT_IDLE : IafSoundRegistry.DEATHWORM_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return this.m_6134_() > 3.0F ? IafSoundRegistry.DEATHWORM_GIANT_HURT : IafSoundRegistry.DEATHWORM_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return this.m_6134_() > 3.0F ? IafSoundRegistry.DEATHWORM_GIANT_DIE : IafSoundRegistry.DEATHWORM_DIE;
   }

   public void m_8119_() {
      super.m_8119_();
      this.m_6210_();
      this.onUpdateParts();
      if (this.attack() && this.m_6688_() != null && this.m_6688_() instanceof Player) {
         LivingEntity target = DragonUtils.riderLookingAtEntity(this, this.m_6688_(), 3.0D);
         if (this.getAnimation() != ANIMATION_BITE) {
            this.setAnimation(ANIMATION_BITE);
            this.m_5496_(this.m_6134_() > 3.0F ? IafSoundRegistry.DEATHWORM_GIANT_ATTACK : IafSoundRegistry.DEATHWORM_ATTACK, 1.0F, 1.0F);
            if (this.m_217043_().m_188503_(3) == 0 && this.m_6134_() > 1.0F) {
               float radius = 1.5F * this.m_6134_();
               float angle = 0.017453292F * this.f_20883_;
               double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
               double extraZ = (double)(radius * Mth.m_14089_(angle));
               BlockLaunchExplosion explosion = new BlockLaunchExplosion(this.m_9236_(), this, this.m_20185_() + extraX, this.m_20186_() - (double)this.m_20192_(), this.m_20189_() + extraZ, this.m_6134_() * 0.75F);
               explosion.m_46061_();
               explosion.m_46075_(true);
            }
         }

         if (target != null) {
            target.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (this.isInSand()) {
         BlockPos pos = (new BlockPos(this.m_146903_(), this.getSurface(this.m_146903_(), this.m_146904_(), this.m_146907_()), this.m_146907_())).m_7495_();
         BlockState state = this.m_9236_().m_8055_(pos);
         if (state.m_60804_(this.m_9236_(), pos) && this.m_9236_().f_46443_) {
            this.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, state), this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), (double)((float)this.getSurface((int)Math.floor(this.m_20185_()), (int)Math.floor(this.m_20186_()), (int)Math.floor(this.m_20189_())) + 0.5F), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D, this.f_19796_.m_188583_() * 0.02D);
         }

         if (this.f_19797_ % 10 == 0) {
            this.m_5496_(SoundEvents.f_12331_, 1.0F, 0.5F);
         }
      }

      if (this.up() && this.m_20096_()) {
         this.m_6135_();
      }

      boolean inSand = this.isInSand() || this.m_6688_() == null;
      if (inSand && !this.isSandNavigator) {
         this.switchNavigator(true);
      }

      if (!inSand && this.isSandNavigator) {
         this.switchNavigator(false);
      }

      if (this.m_9236_().f_46443_) {
         this.tail_buffer.calculateChainSwingBuffer(90.0F, 20, 5.0F, this);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public boolean up() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) & 1) == 1;
   }

   public boolean dismountIAF() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 1 & 1) == 1;
   }

   public boolean attack() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 2 & 1) == 1;
   }

   public void up(boolean up) {
      this.setStateField(0, up);
   }

   public void down(boolean down) {
   }

   public void dismount(boolean dismount) {
      this.setStateField(1, dismount);
   }

   public void attack(boolean attack) {
      this.setStateField(2, attack);
   }

   public void strike(boolean strike) {
   }

   public boolean isSandBelow() {
      int i = Mth.m_14107_(this.m_20185_());
      int j = Mth.m_14107_(this.m_20186_() + 1.0D);
      int k = Mth.m_14107_(this.m_20189_());
      BlockPos blockpos = new BlockPos(i, j, k);
      BlockState BlockState = this.m_9236_().m_8055_(blockpos);
      return BlockState.m_204336_(BlockTags.f_13029_);
   }

   public boolean isInSand() {
      return this.m_6688_() == null && this.isInSandStrict();
   }

   public boolean isInSandStrict() {
      return this.m_9236_().m_8055_(this.m_20183_()).m_204336_(BlockTags.f_13029_);
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
      return new Animation[]{ANIMATION_BITE};
   }

   public Entity[] getWormParts() {
      return this.segments;
   }

   public int m_8085_() {
      return 10;
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   public boolean canPassThrough(BlockPos pos, BlockState state, VoxelShape shape) {
      return this.m_9236_().m_8055_(pos).m_204336_(BlockTags.f_13029_);
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public boolean isRidingPlayer(Player player) {
      return this.getRidingPlayer() != null && player != null && this.getRidingPlayer().m_20148_().equals(player.m_20148_());
   }

   @Nullable
   public Player getRidingPlayer() {
      return this.m_6688_() instanceof Player ? (Player)this.m_6688_() : null;
   }

   public double getRideSpeedModifier() {
      return this.isInSand() ? 1.5D : 1.0D;
   }

   public double processRiderY(double y) {
      return this.isInSand() ? y + 0.20000000298023224D : y;
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityDeathWorm.class, EntityDataSerializers.f_135028_);
      SCALE = SynchedEntityData.m_135353_(EntityDeathWorm.class, EntityDataSerializers.f_135029_);
      JUMP_TICKS = SynchedEntityData.m_135353_(EntityDeathWorm.class, EntityDataSerializers.f_135028_);
      CONTROL_STATE = SynchedEntityData.m_135353_(EntityDeathWorm.class, EntityDataSerializers.f_135027_);
      WORM_AGE = SynchedEntityData.m_135353_(EntityDeathWorm.class, EntityDataSerializers.f_135028_);
      HOME = SynchedEntityData.m_135353_(EntityDeathWorm.class, EntityDataSerializers.f_135038_);
      ANIMATION_BITE = Animation.create(10);
   }

   public class SandMoveHelper extends MoveControl {
      private final EntityDeathWorm worm = EntityDeathWorm.this;

      public SandMoveHelper() {
         super(EntityDeathWorm.this);
      }

      public void m_8126_() {
         if (this.f_24981_ == Operation.MOVE_TO) {
            double d1 = this.f_24976_ - this.worm.m_20186_();
            double d2 = this.f_24977_ - this.worm.m_20189_();
            Vec3 Vector3d = new Vec3(this.f_24975_ - this.worm.m_20185_(), this.f_24976_ - this.worm.m_20186_(), this.f_24977_ - this.worm.m_20189_());
            double d0 = Vector3d.m_82553_();
            if (d0 < 2.500000277905201E-7D) {
               this.f_24974_.m_21564_(0.0F);
            } else {
               this.f_24978_ = 1.0D;
               this.worm.m_20256_(this.worm.m_20184_().m_82549_(Vector3d.m_82490_(this.f_24978_ * 0.05D / d0)));
               Vec3 Vector3d1 = this.worm.m_20184_();
               this.worm.m_146922_(-((float)Mth.m_14136_(Vector3d1.f_82479_, Vector3d1.f_82481_)) * 57.295776F);
            }
         }

      }
   }
}
