package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.api.FoodUtils;
import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.block.IDragonProof;
import com.github.alexthe666.iceandfire.client.model.IFChainBuffer;
import com.github.alexthe666.iceandfire.client.model.util.LegSolverQuadruped;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.ai.DragonAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.DragonAIEscort;
import com.github.alexthe666.iceandfire.entity.ai.DragonAILookIdle;
import com.github.alexthe666.iceandfire.entity.ai.DragonAIMate;
import com.github.alexthe666.iceandfire.entity.ai.DragonAIReturnToRoost;
import com.github.alexthe666.iceandfire.entity.ai.DragonAITarget;
import com.github.alexthe666.iceandfire.entity.ai.DragonAITargetItems;
import com.github.alexthe666.iceandfire.entity.ai.DragonAITargetNonTamed;
import com.github.alexthe666.iceandfire.entity.ai.DragonAIWander;
import com.github.alexthe666.iceandfire.entity.ai.DragonAIWatchClosest;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforgeInput;
import com.github.alexthe666.iceandfire.entity.util.ChainBuffer;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.EntityUtil;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.ICustomMoveController;
import com.github.alexthe666.iceandfire.entity.util.IDeadMob;
import com.github.alexthe666.iceandfire.entity.util.IDragonFlute;
import com.github.alexthe666.iceandfire.entity.util.IDropArmor;
import com.github.alexthe666.iceandfire.entity.util.IFlyingMount;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IMultipartEntity;
import com.github.alexthe666.iceandfire.entity.util.ISyncMount;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.entity.util.ReversedBuffer;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.inventory.ContainerDragon;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArmor;
import com.github.alexthe666.iceandfire.item.ItemSummoningCrystal;
import com.github.alexthe666.iceandfire.message.MessageDragonSetBurnBlock;
import com.github.alexthe666.iceandfire.message.MessageStartRidingMob;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.IPassabilityNavigator;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathingStuckHandler;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.ICustomSizeNavigator;
import com.github.alexthe666.iceandfire.world.DragonPosWorldData;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.common.MinecraftForge;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.items.wrapper.InvWrapper;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public abstract class EntityDragonBase extends TamableAnimal implements IPassabilityNavigator, ISyncMount, IFlyingMount, IMultipartEntity, IAnimatedEntity, IDragonFlute, IDeadMob, IVillagerFear, IAnimalFear, IDropArmor, IHasCustomizableAttributes, ICustomSizeNavigator, ICustomMoveController, ContainerListener {
   public static final int FLIGHT_CHANCE_PER_TICK = 1500;
   protected static final EntityDataAccessor<Boolean> SWIMMING;
   private static final UUID ARMOR_MODIFIER_UUID;
   private static final EntityDataAccessor<Integer> HUNGER;
   private static final EntityDataAccessor<Integer> AGE_TICKS;
   private static final EntityDataAccessor<Boolean> GENDER;
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Boolean> SLEEPING;
   private static final EntityDataAccessor<Boolean> FIREBREATHING;
   private static final EntityDataAccessor<Boolean> HOVERING;
   private static final EntityDataAccessor<Boolean> FLYING;
   private static final EntityDataAccessor<Boolean> MODEL_DEAD;
   private static final EntityDataAccessor<Integer> DEATH_STAGE;
   private static final EntityDataAccessor<Byte> CONTROL_STATE;
   private static final EntityDataAccessor<Boolean> TACKLE;
   private static final EntityDataAccessor<Boolean> AGINGDISABLED;
   private static final EntityDataAccessor<Integer> COMMAND;
   private static final EntityDataAccessor<Float> DRAGON_PITCH;
   private static final EntityDataAccessor<Boolean> CRYSTAL_BOUND;
   private static final EntityDataAccessor<String> CUSTOM_POSE;
   public static Animation ANIMATION_FIRECHARGE;
   public static Animation ANIMATION_EAT;
   public static Animation ANIMATION_SPEAK;
   public static Animation ANIMATION_BITE;
   public static Animation ANIMATION_SHAKEPREY;
   public static Animation ANIMATION_WINGBLAST;
   public static Animation ANIMATION_ROAR;
   public static Animation ANIMATION_EPIC_ROAR;
   public static Animation ANIMATION_TAILWHACK;
   public DragonType dragonType;
   public double minimumDamage;
   public double maximumDamage;
   public double minimumHealth;
   public double maximumHealth;
   public double minimumSpeed;
   public double maximumSpeed;
   public double minimumArmor;
   public double maximumArmor;
   public float sitProgress;
   public float sleepProgress;
   public float hoverProgress;
   public float flyProgress;
   public float fireBreathProgress;
   public float diveProgress;
   public float prevDiveProgress;
   public float prevFireBreathProgress;
   public int fireStopTicks;
   public int flyTicks;
   public float modelDeadProgress;
   public float prevModelDeadProgress;
   public float ridingProgress;
   public float tackleProgress;
   public boolean isSwimming;
   public float prevSwimProgress;
   public float swimProgress;
   public int ticksSwiming;
   public int swimCycle;
   public float[] prevAnimationProgresses = new float[10];
   public boolean isDaytime;
   public int flightCycle;
   public HomePosition homePos;
   public boolean hasHomePosition = false;
   public IFChainBuffer roll_buffer;
   public IFChainBuffer pitch_buffer;
   public IFChainBuffer pitch_buffer_body;
   public ReversedBuffer turn_buffer;
   public ChainBuffer tail_buffer;
   public int spacebarTicks;
   public static final float[] growth_stage_1;
   public static final float[] growth_stage_2;
   public static final float[] growth_stage_3;
   public static final float[] growth_stage_4;
   public static final float[] growth_stage_5;
   public float[][] growth_stages;
   public LegSolverQuadruped legSolver;
   public int walkCycle;
   public BlockPos burningTarget;
   public int burnProgress;
   public double burnParticleX;
   public double burnParticleY;
   public double burnParticleZ;
   public float prevDragonPitch;
   public IafDragonAttacks.Air airAttack;
   public IafDragonAttacks.Ground groundAttack;
   public boolean usingGroundAttack;
   public IafDragonLogic logic;
   public int hoverTicks;
   public int tacklingTicks;
   public int ticksStill;
   public int navigatorType;
   public SimpleContainer dragonInventory;
   public String prevArmorResLoc;
   public String armorResLoc;
   public IafDragonFlightManager flightManager;
   public boolean lookingForRoostAIFlag;
   protected int flyHovering;
   protected boolean hasHadHornUse;
   protected int fireTicks;
   protected int blockBreakCounter;
   private int prevFlightCycle;
   private boolean isModelDead;
   private int animationTick;
   private Animation currentAnimation;
   private float lastScale;
   private EntityDragonPart headPart;
   private EntityDragonPart neckPart;
   private EntityDragonPart rightWingUpperPart;
   private EntityDragonPart rightWingLowerPart;
   private EntityDragonPart leftWingUpperPart;
   private EntityDragonPart leftWingLowerPart;
   private EntityDragonPart tail1Part;
   private EntityDragonPart tail2Part;
   private EntityDragonPart tail3Part;
   private EntityDragonPart tail4Part;
   private boolean isOverAir;
   private LazyOptional<?> itemHandler;
   public boolean allowLocalMotionControl;
   public boolean allowMousePitchControl;
   protected boolean gliding;
   protected float glidingSpeedBonus;
   protected float riderWalkingExtraY;

   public EntityDragonBase(EntityType t, Level world, DragonType type, double minimumDamage, double maximumDamage, double minimumHealth, double maximumHealth, double minimumSpeed, double maximumSpeed) {
      super(t, world);
      this.growth_stages = new float[][]{growth_stage_1, growth_stage_2, growth_stage_3, growth_stage_4, growth_stage_5};
      this.usingGroundAttack = true;
      this.prevArmorResLoc = "0|0|0|0";
      this.armorResLoc = "0|0|0|0";
      this.lookingForRoostAIFlag = false;
      this.hasHadHornUse = false;
      this.itemHandler = null;
      this.allowLocalMotionControl = true;
      this.allowMousePitchControl = true;
      this.gliding = false;
      this.glidingSpeedBonus = 0.0F;
      this.riderWalkingExtraY = 0.0F;
      this.dragonType = type;
      this.minimumDamage = minimumDamage;
      this.maximumDamage = maximumDamage;
      this.minimumHealth = minimumHealth;
      this.maximumHealth = maximumHealth;
      this.minimumSpeed = minimumSpeed;
      this.maximumSpeed = maximumSpeed;
      this.minimumArmor = 1.0D;
      this.maximumArmor = 20.0D;
      ANIMATION_EAT = Animation.create(20);
      this.createInventory();
      if (world.f_46443_) {
         this.roll_buffer = new IFChainBuffer();
         this.pitch_buffer = new IFChainBuffer();
         this.pitch_buffer_body = new IFChainBuffer();
         this.turn_buffer = new ReversedBuffer();
         this.tail_buffer = new ChainBuffer();
      }

      this.legSolver = new LegSolverQuadruped(0.3F, 0.35F, 0.2F, 1.45F, 1.0F);
      this.flightManager = new IafDragonFlightManager(this);
      this.logic = this.createDragonLogic();
      this.f_19811_ = true;
      this.switchNavigator(0);
      this.randomizeAttacks();
      this.resetParts(1.0F);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, 1.0D).m_22268_(Attributes.f_22277_, (double)Math.min(2048, IafConfig.dragonTargetSearchLength)).m_22268_(Attributes.f_22284_, 4.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22277_).m_22100_((double)Math.min(2048, IafConfig.dragonTargetSearchLength));
   }

   @NotNull
   public BlockPos m_21534_() {
      return this.homePos == null ? super.m_21534_() : this.homePos.getPosition();
   }

   public float m_21535_() {
      return (float)IafConfig.dragonWanderFromHomeDistance;
   }

   public String getHomeDimensionName() {
      return this.homePos == null ? "" : this.homePos.getDimension();
   }

   public boolean m_21536_() {
      return this.hasHomePosition && this.getHomeDimensionName().equals(DragonUtils.getDimensionName(this.m_9236_())) || super.m_21536_();
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(2, new DragonAIMate(this, 1.0D));
      this.f_21345_.m_25352_(3, new DragonAIReturnToRoost(this, 1.0D));
      this.f_21345_.m_25352_(4, new DragonAIEscort(this, 1.0D));
      this.f_21345_.m_25352_(5, new DragonAIAttackMelee(this, 1.5D, false));
      this.f_21345_.m_25352_(6, new TemptGoal(this, 1.0D, Ingredient.m_204132_(IafItemTags.TEMPT_DRAGON), false));
      this.f_21345_.m_25352_(7, new DragonAIWander(this, 1.0D));
      this.f_21345_.m_25352_(8, new DragonAIWatchClosest(this, LivingEntity.class, 6.0F));
      this.f_21345_.m_25352_(8, new DragonAILookIdle(this));
      this.f_21346_.m_25352_(1, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new DragonAITargetItems(this, 60, false, false, true));
      this.f_21346_.m_25352_(5, new DragonAITargetNonTamed(this, LivingEntity.class, false, (entity) -> {
         if (entity instanceof Player) {
            Player player = (Player)entity;
            return !player.m_7500_();
         } else if (this.m_217043_().m_188503_(100) <= this.getHunger()) {
            return false;
         } else {
            return entity.m_6095_() != this.m_6095_() && DragonUtils.canHostilesTarget(entity) && DragonUtils.isAlive(entity) && this.shouldTarget(entity);
         }
      }));
      this.f_21346_.m_25352_(6, new DragonAITarget(this, LivingEntity.class, true, (entity) -> {
         return DragonUtils.canHostilesTarget(entity) && entity.m_6095_() != this.m_6095_() && this.shouldTarget(entity) && DragonUtils.isAlive(entity);
      }));
      this.f_21346_.m_25352_(7, new DragonAITargetItems(this, false));
   }

   protected abstract boolean shouldTarget(Entity var1);

   public void resetParts(float scale) {
      this.removeParts();
      this.headPart = new EntityDragonPart(this, 1.55F * scale, 0.0F, 0.6F * scale, 0.5F * scale, 0.35F * scale, 1.5F);
      this.headPart.m_20359_(this);
      this.headPart.setParent(this);
      this.neckPart = new EntityDragonPart(this, 0.85F * scale, 0.0F, 0.7F * scale, 0.5F * scale, 0.2F * scale, 1.0F);
      this.neckPart.m_20359_(this);
      this.neckPart.setParent(this);
      this.rightWingUpperPart = new EntityDragonPart(this, scale, 90.0F, 0.5F * scale, 0.85F * scale, 0.3F * scale, 0.5F);
      this.rightWingUpperPart.m_20359_(this);
      this.rightWingUpperPart.setParent(this);
      this.rightWingLowerPart = new EntityDragonPart(this, 1.4F * scale, 100.0F, 0.3F * scale, 0.85F * scale, 0.2F * scale, 0.5F);
      this.rightWingLowerPart.m_20359_(this);
      this.rightWingLowerPart.setParent(this);
      this.leftWingUpperPart = new EntityDragonPart(this, scale, -90.0F, 0.5F * scale, 0.85F * scale, 0.3F * scale, 0.5F);
      this.leftWingUpperPart.m_20359_(this);
      this.leftWingUpperPart.setParent(this);
      this.leftWingLowerPart = new EntityDragonPart(this, 1.4F * scale, -100.0F, 0.3F * scale, 0.85F * scale, 0.2F * scale, 0.5F);
      this.leftWingLowerPart.m_20359_(this);
      this.leftWingLowerPart.setParent(this);
      this.tail1Part = new EntityDragonPart(this, -0.75F * scale, 0.0F, 0.6F * scale, 0.35F * scale, 0.35F * scale, 1.0F);
      this.tail1Part.m_20359_(this);
      this.tail1Part.setParent(this);
      this.tail2Part = new EntityDragonPart(this, -1.15F * scale, 0.0F, 0.45F * scale, 0.35F * scale, 0.35F * scale, 1.0F);
      this.tail2Part.m_20359_(this);
      this.tail2Part.setParent(this);
      this.tail3Part = new EntityDragonPart(this, -1.5F * scale, 0.0F, 0.35F * scale, 0.35F * scale, 0.35F * scale, 1.0F);
      this.tail3Part.m_20359_(this);
      this.tail3Part.setParent(this);
      this.tail4Part = new EntityDragonPart(this, -1.95F * scale, 0.0F, 0.25F * scale, 0.45F * scale, 0.3F * scale, 1.5F);
      this.tail4Part.m_20359_(this);
      this.tail4Part.setParent(this);
   }

   public void removeParts() {
      if (this.headPart != null) {
         this.headPart.m_142687_(RemovalReason.DISCARDED);
         this.headPart = null;
      }

      if (this.neckPart != null) {
         this.neckPart.m_142687_(RemovalReason.DISCARDED);
         this.neckPart = null;
      }

      if (this.rightWingUpperPart != null) {
         this.rightWingUpperPart.m_142687_(RemovalReason.DISCARDED);
         this.rightWingUpperPart = null;
      }

      if (this.rightWingLowerPart != null) {
         this.rightWingLowerPart.m_142687_(RemovalReason.DISCARDED);
         this.rightWingLowerPart = null;
      }

      if (this.leftWingUpperPart != null) {
         this.leftWingUpperPart.m_142687_(RemovalReason.DISCARDED);
         this.leftWingUpperPart = null;
      }

      if (this.leftWingLowerPart != null) {
         this.leftWingLowerPart.m_142687_(RemovalReason.DISCARDED);
         this.leftWingLowerPart = null;
      }

      if (this.tail1Part != null) {
         this.tail1Part.m_142687_(RemovalReason.DISCARDED);
         this.tail1Part = null;
      }

      if (this.tail2Part != null) {
         this.tail2Part.m_142687_(RemovalReason.DISCARDED);
         this.tail2Part = null;
      }

      if (this.tail3Part != null) {
         this.tail3Part.m_142687_(RemovalReason.DISCARDED);
         this.tail3Part = null;
      }

      if (this.tail4Part != null) {
         this.tail4Part.m_142687_(RemovalReason.DISCARDED);
         this.tail4Part = null;
      }

   }

   public void updateParts() {
      EntityUtil.updatePart(this.headPart, this);
      EntityUtil.updatePart(this.neckPart, this);
      EntityUtil.updatePart(this.rightWingUpperPart, this);
      EntityUtil.updatePart(this.rightWingLowerPart, this);
      EntityUtil.updatePart(this.leftWingUpperPart, this);
      EntityUtil.updatePart(this.leftWingLowerPart, this);
      EntityUtil.updatePart(this.tail1Part, this);
      EntityUtil.updatePart(this.tail2Part, this);
      EntityUtil.updatePart(this.tail3Part, this);
      EntityUtil.updatePart(this.tail4Part, this);
   }

   protected void updateBurnTarget() {
      if (this.burningTarget != null && !this.m_5803_() && !this.isModelDead() && !this.m_6162_()) {
         float maxDist = (float)(115 * this.getDragonStage());
         BlockEntity var3 = this.m_9236_().m_7702_(this.burningTarget);
         if (var3 instanceof TileEntityDragonforgeInput) {
            TileEntityDragonforgeInput forge = (TileEntityDragonforgeInput)var3;
            if (forge.isAssembled() && this.m_20275_((double)this.burningTarget.m_123341_() + 0.5D, (double)this.burningTarget.m_123342_() + 0.5D, (double)this.burningTarget.m_123343_() + 0.5D) < (double)maxDist && this.canPositionBeSeen((double)this.burningTarget.m_123341_() + 0.5D, (double)this.burningTarget.m_123342_() + 0.5D, (double)this.burningTarget.m_123343_() + 0.5D)) {
               this.m_21563_().m_24950_((double)this.burningTarget.m_123341_() + 0.5D, (double)this.burningTarget.m_123342_() + 0.5D, (double)this.burningTarget.m_123343_() + 0.5D, 180.0F, 180.0F);
               this.breathFireAtPos(this.burningTarget);
               this.setBreathingFire(true);
               return;
            }
         }

         if (!this.m_9236_().f_46443_) {
            IceAndFire.sendMSGToAll(new MessageDragonSetBurnBlock(this.m_19879_(), true, this.burningTarget));
         }

         this.burningTarget = null;
      }

   }

   protected abstract void breathFireAtPos(BlockPos var1);

   protected PathingStuckHandler createStuckHandler() {
      return PathingStuckHandler.createStuckHandler();
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level worldIn) {
      return this.createNavigator(worldIn, AdvancedPathNavigate.MovementType.WALKING);
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type) {
      return this.createNavigator(worldIn, type, this.createStuckHandler());
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type, PathingStuckHandler stuckHandler) {
      return this.createNavigator(worldIn, type, stuckHandler, 4.0F, 4.0F);
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type, PathingStuckHandler stuckHandler, float width, float height) {
      AdvancedPathNavigate newNavigator = new AdvancedPathNavigate(this, this.m_9236_(), type, width, height);
      this.f_21344_ = newNavigator;
      newNavigator.m_7008_(true);
      newNavigator.m_26575_().m_77355_(true);
      return newNavigator;
   }

   protected void switchNavigator(int navigatorType) {
      if (navigatorType == 0) {
         this.f_21342_ = new IafDragonFlightManager.GroundMoveHelper(this);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.WALKING, this.createStuckHandler().withTeleportSteps(5));
         this.navigatorType = 0;
         this.setFlying(false);
         this.setHovering(false);
      } else if (navigatorType == 1) {
         this.f_21342_ = new IafDragonFlightManager.FlightMoveHelper(this);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.FLYING);
         this.navigatorType = 1;
      } else {
         this.f_21342_ = new IafDragonFlightManager.PlayerFlightMoveHelper(this);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.FLYING);
         this.navigatorType = 2;
      }

   }

   public boolean m_7341_(@NotNull Entity rider) {
      return true;
   }

   protected void m_8024_() {
      super.m_8024_();
      this.breakBlocks(false);
   }

   public void m_6043_() {
      if (IafConfig.canDragonsDespawn) {
         super.m_6043_();
      }

   }

   public boolean canDestroyBlock(BlockPos pos, BlockState state) {
      return state.m_60734_().canEntityDestroy(state, this.m_9236_(), pos, this);
   }

   public boolean isMobDead() {
      return this.isModelDead();
   }

   public int m_8085_() {
      return 30 * this.getDragonStage() / 5;
   }

   public void openInventory(Player player) {
      if (!this.m_9236_().f_46443_) {
         NetworkHooks.openScreen((ServerPlayer)player, this.getMenuProvider());
      }

      IceAndFire.PROXY.setReferencedMob(this);
   }

   public MenuProvider getMenuProvider() {
      return new SimpleMenuProvider((containerId, playerInventory, player) -> {
         return new ContainerDragon(containerId, this.dragonInventory, playerInventory, this);
      }, this.m_5446_());
   }

   public int m_8100_() {
      return 90;
   }

   protected void m_6153_() {
      this.f_20919_ = 0;
      this.setModelDead(true);
      this.m_20153_();
      if (this.getDeathStage() >= this.getAgeInDays() / 5) {
         this.m_142687_(RemovalReason.KILLED);

         for(int k = 0; k < 40; ++k) {
            double d2 = this.f_19796_.m_188583_() * 0.02D;
            double d0 = this.f_19796_.m_188583_() * 0.02D;
            double d1 = this.f_19796_.m_188583_() * 0.02D;
            if (this.m_9236_().f_46443_) {
               this.m_9236_().m_7106_(ParticleTypes.f_123796_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d2, d0, d1);
            }
         }

         this.spawnDeathParticles();
      }

   }

   protected void spawnDeathParticles() {
   }

   protected void spawnBabyParticles() {
   }

   public void m_142687_(@NotNull RemovalReason reason) {
      this.removeParts();
      super.m_142687_(reason);
   }

   public int m_213860_() {
      short var10000;
      switch(this.getDragonStage()) {
      case 2:
         var10000 = 20;
         break;
      case 3:
         var10000 = 150;
         break;
      case 4:
         var10000 = 300;
         break;
      case 5:
         var10000 = 650;
         break;
      default:
         var10000 = 5;
      }

      return var10000;
   }

   public int getArmorOrdinal(ItemStack stack) {
      if (!stack.m_41619_()) {
         Item var3 = stack.m_41720_();
         if (var3 instanceof ItemDragonArmor) {
            ItemDragonArmor armorItem = (ItemDragonArmor)var3;
            return armorItem.type.ordinal() + 1;
         }
      }

      return 0;
   }

   public boolean m_21525_() {
      return this.isModelDead() || super.m_21525_();
   }

   public boolean isAiDisabled() {
      return super.m_21525_();
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HUNGER, 0);
      this.f_19804_.m_135372_(AGE_TICKS, 0);
      this.f_19804_.m_135372_(GENDER, false);
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(SLEEPING, false);
      this.f_19804_.m_135372_(FIREBREATHING, false);
      this.f_19804_.m_135372_(HOVERING, false);
      this.f_19804_.m_135372_(FLYING, false);
      this.f_19804_.m_135372_(DEATH_STAGE, 0);
      this.f_19804_.m_135372_(MODEL_DEAD, false);
      this.f_19804_.m_135372_(CONTROL_STATE, (byte)0);
      this.f_19804_.m_135372_(TACKLE, false);
      this.f_19804_.m_135372_(AGINGDISABLED, false);
      this.f_19804_.m_135372_(COMMAND, 0);
      this.f_19804_.m_135372_(DRAGON_PITCH, 0.0F);
      this.f_19804_.m_135372_(CRYSTAL_BOUND, false);
      this.f_19804_.m_135372_(CUSTOM_POSE, "");
   }

   public boolean isGoingUp() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) & 1) == 1;
   }

   public boolean isGoingDown() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 1 & 1) == 1;
   }

   public boolean isAttacking() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 2 & 1) == 1;
   }

   public boolean isStriking() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 3 & 1) == 1;
   }

   public boolean isDismounting() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 4 & 1) == 1;
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
      this.setStateField(3, strike);
   }

   public void dismount(boolean dismount) {
      this.setStateField(4, dismount);
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

   public int getCommand() {
      return (Integer)this.f_19804_.m_135370_(COMMAND);
   }

   public void setCommand(int command) {
      this.f_19804_.m_135381_(COMMAND, command);
      this.m_21839_(command == 1);
   }

   public float getDragonPitch() {
      return (Float)this.f_19804_.m_135370_(DRAGON_PITCH);
   }

   public void setDragonPitch(float pitch) {
      this.f_19804_.m_135381_(DRAGON_PITCH, pitch);
   }

   public void incrementDragonPitch(float pitch) {
      this.f_19804_.m_135381_(DRAGON_PITCH, this.getDragonPitch() + pitch);
   }

   public void decrementDragonPitch(float pitch) {
      this.f_19804_.m_135381_(DRAGON_PITCH, this.getDragonPitch() - pitch);
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Hunger", this.getHunger());
      compound.m_128405_("AgeTicks", this.getAgeInTicks());
      compound.m_128379_("Gender", this.isMale());
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128379_("Sleeping", this.m_5803_());
      compound.m_128379_("TamedDragon", this.m_21824_());
      compound.m_128379_("FireBreathing", this.isBreathingFire());
      compound.m_128379_("AttackDecision", this.usingGroundAttack);
      compound.m_128379_("Hovering", this.isHovering());
      compound.m_128379_("Flying", this.isFlying());
      compound.m_128405_("DeathStage", this.getDeathStage());
      compound.m_128379_("ModelDead", this.isModelDead());
      compound.m_128350_("DeadProg", this.modelDeadProgress);
      compound.m_128379_("Tackle", this.isTackling());
      compound.m_128379_("HasHomePosition", this.hasHomePosition);
      compound.m_128359_("CustomPose", this.getCustomPose());
      if (this.homePos != null && this.hasHomePosition) {
         this.homePos.write(compound);
      }

      compound.m_128379_("AgingDisabled", this.isAgingDisabled());
      compound.m_128405_("Command", this.getCommand());
      if (this.dragonInventory != null) {
         ListTag nbttaglist = new ListTag();

         for(int i = 0; i < this.dragonInventory.m_6643_(); ++i) {
            ItemStack itemstack = this.dragonInventory.m_8020_(i);
            if (!itemstack.m_41619_()) {
               CompoundTag CompoundNBT = new CompoundTag();
               CompoundNBT.m_128344_("Slot", (byte)i);
               itemstack.m_41739_(CompoundNBT);
               nbttaglist.add(CompoundNBT);
            }
         }

         compound.m_128365_("Items", nbttaglist);
      }

      compound.m_128379_("CrystalBound", this.isBoundToCrystal());
      if (this.m_8077_()) {
         compound.m_128359_("CustomName", Serializer.m_130703_(this.m_7770_()));
      }

   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setHunger(compound.m_128451_("Hunger"));
      this.setAgeInTicks(compound.m_128451_("AgeTicks"));
      this.setGender(compound.m_128471_("Gender"));
      this.setVariant(compound.m_128451_("Variant"));
      this.m_21837_(compound.m_128471_("Sleeping"));
      this.m_7105_(compound.m_128471_("TamedDragon"));
      this.setBreathingFire(compound.m_128471_("FireBreathing"));
      this.usingGroundAttack = compound.m_128471_("AttackDecision");
      this.setHovering(compound.m_128471_("Hovering"));
      this.setFlying(compound.m_128471_("Flying"));
      this.setDeathStage(compound.m_128451_("DeathStage"));
      this.setModelDead(compound.m_128471_("ModelDead"));
      this.modelDeadProgress = compound.m_128457_("DeadProg");
      this.setCustomPose(compound.m_128461_("CustomPose"));
      this.hasHomePosition = compound.m_128471_("HasHomePosition");
      if (this.hasHomePosition && compound.m_128451_("HomeAreaX") != 0 && compound.m_128451_("HomeAreaY") != 0 && compound.m_128451_("HomeAreaZ") != 0) {
         this.homePos = new HomePosition(compound, this.m_9236_());
      }

      this.setTackling(compound.m_128471_("Tackle"));
      this.setAgingDisabled(compound.m_128471_("AgingDisabled"));
      this.setCommand(compound.m_128451_("Command"));
      ListTag nbttaglist;
      Iterator var3;
      Tag inbt;
      CompoundTag CompoundNBT;
      int j;
      if (this.dragonInventory != null) {
         nbttaglist = compound.m_128437_("Items", 10);
         this.createInventory();
         var3 = nbttaglist.iterator();

         while(var3.hasNext()) {
            inbt = (Tag)var3.next();
            CompoundNBT = (CompoundTag)inbt;
            j = CompoundNBT.m_128445_("Slot") & 255;
            if (j <= 4) {
               this.dragonInventory.m_6836_(j, ItemStack.m_41712_(CompoundNBT));
            }
         }
      } else {
         nbttaglist = compound.m_128437_("Items", 10);
         this.createInventory();
         var3 = nbttaglist.iterator();

         while(var3.hasNext()) {
            inbt = (Tag)var3.next();
            CompoundNBT = (CompoundTag)inbt;
            j = CompoundNBT.m_128445_("Slot") & 255;
            this.dragonInventory.m_6836_(j, ItemStack.m_41712_(CompoundNBT));
         }
      }

      this.setCrystalBound(compound.m_128471_("CrystalBound"));
      if (compound.m_128425_("CustomName", 8) && !compound.m_128461_("CustomName").startsWith("TextComponent")) {
         this.m_6593_(Serializer.m_130701_(compound.m_128461_("CustomName")));
      }

      this.setConfigurableAttributes();
      this.updateAttributes();
   }

   public int getContainerSize() {
      return 5;
   }

   protected void createInventory() {
      SimpleContainer tempInventory = this.dragonInventory;
      this.dragonInventory = new SimpleContainer(this.getContainerSize());
      if (tempInventory != null) {
         tempInventory.m_19181_(this);
         int i = Math.min(tempInventory.m_6643_(), this.dragonInventory.m_6643_());

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = tempInventory.m_8020_(j);
            if (!itemstack.m_41619_()) {
               this.dragonInventory.m_6836_(j, itemstack.m_41777_());
            }
         }
      }

      this.dragonInventory.m_19164_(this);
      this.updateContainerEquipment();
      this.itemHandler = LazyOptional.of(() -> {
         return new InvWrapper(this.dragonInventory);
      });
   }

   protected void updateContainerEquipment() {
      if (!this.m_9236_().f_46443_) {
         this.updateAttributes();
      }

   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
      return this.m_6084_() && capability == ForgeCapabilities.ITEM_HANDLER && this.itemHandler != null ? this.itemHandler.cast() : super.getCapability(capability, facing);
   }

   public void invalidateCaps() {
      super.invalidateCaps();
      if (this.itemHandler != null) {
         LazyOptional<?> oldHandler = this.itemHandler;
         this.itemHandler = null;
         oldHandler.invalidate();
      }

   }

   public boolean hasInventoryChanged(Container pInventory) {
      return this.dragonInventory != pInventory;
   }

   @Nullable
   public LivingEntity m_6688_() {
      Iterator var1 = this.m_20197_().iterator();

      while(var1.hasNext()) {
         Entity passenger = (Entity)var1.next();
         if (passenger instanceof Player) {
            Player player = (Player)passenger;
            if (this.m_5448_() != passenger && this.m_21824_() && this.m_21805_() != null && this.m_21805_().equals(player.m_20148_())) {
               return player;
            }
         }
      }

      return null;
   }

   public boolean isRidingPlayer(Player player) {
      return this.getRidingPlayer() != null && player != null && this.getRidingPlayer().m_20148_().equals(player.m_20148_());
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

   protected void updateAttributes() {
      this.prevArmorResLoc = this.armorResLoc;
      int armorHead = this.getArmorOrdinal(this.m_6844_(EquipmentSlot.HEAD));
      int armorNeck = this.getArmorOrdinal(this.m_6844_(EquipmentSlot.CHEST));
      int armorLegs = this.getArmorOrdinal(this.m_6844_(EquipmentSlot.LEGS));
      int armorFeet = this.getArmorOrdinal(this.m_6844_(EquipmentSlot.FEET));
      this.armorResLoc = this.dragonType.getName() + "|" + armorHead + "|" + armorNeck + "|" + armorLegs + "|" + armorFeet;
      IceAndFire.PROXY.updateDragonArmorRender(this.armorResLoc);
      double age = 125.0D;
      if (this.getAgeInDays() <= 125) {
         age = (double)this.getAgeInDays();
      }

      double healthStep = (this.maximumHealth - this.minimumHealth) / 125.0D;
      double attackStep = (this.maximumDamage - this.minimumDamage) / 125.0D;
      double speedStep = (this.maximumSpeed - this.minimumSpeed) / 125.0D;
      double armorStep = (this.maximumArmor - this.minimumArmor) / 125.0D;
      this.m_21051_(Attributes.f_22276_).m_22100_((double)Math.round(this.minimumHealth + healthStep * age));
      this.m_21051_(Attributes.f_22281_).m_22100_((double)Math.round(this.minimumDamage + attackStep * age));
      this.m_21051_(Attributes.f_22279_).m_22100_(this.minimumSpeed + speedStep * age);
      double baseValue = this.minimumArmor + armorStep * (double)this.getAgeInDays();
      this.m_21051_(Attributes.f_22284_).m_22100_(baseValue);
      if (!this.m_9236_().f_46443_) {
         this.m_21051_(Attributes.f_22284_).m_22120_(ARMOR_MODIFIER_UUID);
         this.m_21051_(Attributes.f_22284_).m_22125_(new AttributeModifier(ARMOR_MODIFIER_UUID, "Dragon armor bonus", this.calculateArmorModifier(), Operation.ADDITION));
      }

      this.m_21051_(Attributes.f_22277_).m_22100_((double)Math.min(2048, IafConfig.dragonTargetSearchLength));
   }

   public int getHunger() {
      return (Integer)this.f_19804_.m_135370_(HUNGER);
   }

   public void setHunger(int hunger) {
      this.f_19804_.m_135381_(HUNGER, Mth.m_14045_(hunger, 0, 100));
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public int getAgeInDays() {
      return (Integer)this.f_19804_.m_135370_(AGE_TICKS) / 24000;
   }

   public void setAgeInDays(int age) {
      this.f_19804_.m_135381_(AGE_TICKS, age * 24000);
   }

   public int getAgeInTicks() {
      return (Integer)this.f_19804_.m_135370_(AGE_TICKS);
   }

   public void setAgeInTicks(int age) {
      this.f_19804_.m_135381_(AGE_TICKS, age);
   }

   public int getDeathStage() {
      return (Integer)this.f_19804_.m_135370_(DEATH_STAGE);
   }

   public void setDeathStage(int stage) {
      this.f_19804_.m_135381_(DEATH_STAGE, stage);
   }

   public boolean isMale() {
      return (Boolean)this.f_19804_.m_135370_(GENDER);
   }

   public boolean isModelDead() {
      return this.m_9236_().f_46443_ ? (this.isModelDead = (Boolean)this.f_19804_.m_135370_(MODEL_DEAD)) : this.isModelDead;
   }

   public void setModelDead(boolean modeldead) {
      this.f_19804_.m_135381_(MODEL_DEAD, modeldead);
      if (!this.m_9236_().f_46443_) {
         this.isModelDead = modeldead;
      }

   }

   public boolean isHovering() {
      return (Boolean)this.f_19804_.m_135370_(HOVERING);
   }

   public void setHovering(boolean hovering) {
      this.f_19804_.m_135381_(HOVERING, hovering);
   }

   public boolean isFlying() {
      return (Boolean)this.f_19804_.m_135370_(FLYING);
   }

   public void setFlying(boolean flying) {
      this.f_19804_.m_135381_(FLYING, flying);
   }

   public boolean useFlyingPathFinder() {
      return this.isFlying() && this.m_6688_() == null;
   }

   public void setGender(boolean male) {
      this.f_19804_.m_135381_(GENDER, male);
   }

   public boolean m_5803_() {
      return (Boolean)this.f_19804_.m_135370_(SLEEPING);
   }

   public boolean isBlinking() {
      return this.f_19797_ % 50 > 43;
   }

   public boolean isBreathingFire() {
      return (Boolean)this.f_19804_.m_135370_(FIREBREATHING);
   }

   public void setBreathingFire(boolean breathing) {
      this.f_19804_.m_135381_(FIREBREATHING, breathing);
   }

   protected boolean m_7310_(@NotNull Entity passenger) {
      return this.m_20197_().size() < 2;
   }

   public boolean m_21827_() {
      return ((Byte)this.f_19804_.m_135370_(f_21798_) & 1) != 0;
   }

   public void m_21837_(boolean sleeping) {
      this.f_19804_.m_135381_(SLEEPING, sleeping);
      if (sleeping) {
         this.m_21573_().m_26573_();
      }

   }

   public void m_21839_(boolean sitting) {
      byte b0 = (Byte)this.f_19804_.m_135370_(f_21798_);
      if (sitting) {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 | 1));
         this.m_21573_().m_26573_();
      } else {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 & -2));
      }

   }

   public String getCustomPose() {
      return (String)this.f_19804_.m_135370_(CUSTOM_POSE);
   }

   public void setCustomPose(String customPose) {
      this.f_19804_.m_135381_(CUSTOM_POSE, customPose);
      this.modelDeadProgress = 20.0F;
   }

   public void riderShootFire(Entity controller) {
   }

   private double calculateArmorModifier() {
      double val = 1.0D;
      EquipmentSlot[] slots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
      EquipmentSlot[] var4 = slots;
      int var5 = slots.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EquipmentSlot slot = var4[var6];
         switch(this.getArmorOrdinal(this.m_6844_(slot))) {
         case 1:
            val += 2.0D;
            break;
         case 2:
         case 4:
            val += 3.0D;
            break;
         case 3:
            val += 5.0D;
            break;
         case 5:
         case 6:
         case 8:
            val += 10.0D;
            break;
         case 7:
            ++val;
         }
      }

      return val;
   }

   public boolean canMove() {
      return !this.m_21827_() && !this.m_5803_() && this.m_6688_() == null && !this.m_20159_() && !this.isModelDead() && this.sleepProgress == 0.0F && this.getAnimation() != ANIMATION_SHAKEPREY;
   }

   public boolean isFuelingForge() {
      return this.burningTarget != null && this.m_9236_().m_7702_(this.burningTarget) instanceof TileEntityDragonforgeInput;
   }

   public boolean m_6084_() {
      if (this.isModelDead()) {
         return !this.m_213877_();
      } else {
         return super.m_6084_();
      }
   }

   @NotNull
   public InteractionResult m_7111_(Player player, @NotNull Vec3 vec, @NotNull InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      int lastDeathStage = Math.min(this.getAgeInDays() / 5, 25);
      if (stack.m_41720_() == IafItemRegistry.DRAGON_DEBUG_STICK.get()) {
         this.logic.debug();
         return InteractionResult.SUCCESS;
      } else if (this.isModelDead() && this.getDeathStage() < lastDeathStage && player.m_36326_()) {
         if (!this.m_9236_().f_46443_ && !stack.m_41619_() && stack.m_41720_() != null && stack.m_41720_() == Items.f_42590_ && this.getDeathStage() < lastDeathStage / 2 && IafConfig.dragonDropBlood) {
            if (!player.m_7500_()) {
               stack.m_41774_(1);
            }

            this.setDeathStage(this.getDeathStage() + 1);
            player.m_150109_().m_36054_(new ItemStack(this.getBloodItem(), 1));
            return InteractionResult.SUCCESS;
         } else {
            if (!this.m_9236_().f_46443_ && stack.m_41619_() && IafConfig.dragonDropSkull) {
               ItemStack drop;
               if (this.getDeathStage() >= lastDeathStage - 1) {
                  drop = this.getSkull().m_41777_();
                  drop.m_41751_(new CompoundTag());
                  drop.m_41783_().m_128405_("Stage", this.getDragonStage());
                  drop.m_41783_().m_128405_("DragonType", 0);
                  drop.m_41783_().m_128405_("DragonAge", this.getAgeInDays());
                  this.setDeathStage(this.getDeathStage() + 1);
                  if (!this.m_9236_().f_46443_) {
                     this.m_5552_(drop, 1.0F);
                  }

                  this.m_142687_(RemovalReason.DISCARDED);
               } else if (this.getDeathStage() == lastDeathStage / 2 - 1 && IafConfig.dragonDropHeart) {
                  drop = new ItemStack(this.getHeartItem(), 1);
                  ItemStack egg = new ItemStack(this.getVariantEgg(this.f_19796_.m_188503_(4)), 1);
                  if (!this.m_9236_().f_46443_) {
                     this.m_5552_(drop, 1.0F);
                     if (!this.isMale() && this.getDragonStage() > 3) {
                        this.m_5552_(egg, 1.0F);
                     }
                  }

                  this.setDeathStage(this.getDeathStage() + 1);
               } else {
                  this.setDeathStage(this.getDeathStage() + 1);
                  drop = this.getRandomDrop();
                  if (!drop.m_41619_() && !this.m_9236_().f_46443_) {
                     this.m_5552_(drop, 1.0F);
                  }
               }
            }

            return InteractionResult.SUCCESS;
         }
      } else {
         return super.m_7111_(player, vec, hand);
      }
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack stack = player.m_21205_();
      if (stack == ItemStack.f_41583_) {
         stack = player.m_21120_(hand);
      }

      if (stack.m_41720_() == IafItemRegistry.DRAGON_DEBUG_STICK.get()) {
         this.logic.debug();
         return InteractionResult.SUCCESS;
      } else {
         if (!this.isModelDead()) {
            if (stack.m_41720_() == IafItemRegistry.CREATIVE_DRAGON_MEAL.get()) {
               this.m_7105_(true);
               this.m_21828_(player);
               this.setHunger(this.getHunger() + 20);
               this.m_5634_(Math.min(this.m_21223_(), (float)((int)(this.m_21233_() / 2.0F))));
               this.m_5496_(SoundEvents.f_11912_, this.m_6121_(), this.m_6100_());
               this.spawnItemCrackParticles(stack.m_41720_());
               this.spawnItemCrackParticles(Items.f_42500_);
               this.spawnItemCrackParticles(Items.f_42499_);
               this.eatFoodBonus(stack);
               if (!player.m_7500_()) {
                  stack.m_41774_(1);
               }

               return InteractionResult.SUCCESS;
            }

            if (this.m_6898_(stack) && this.m_6125_()) {
               this.m_146762_(0);
               this.m_142075_(player, InteractionHand.MAIN_HAND, stack);
               this.m_27595_(player);
               return InteractionResult.SUCCESS;
            }

            if (this.m_21830_(player)) {
               if (stack.m_41720_() == this.getSummoningCrystal() && !ItemSummoningCrystal.hasDragon(stack)) {
                  this.setCrystalBound(true);
                  CompoundTag compound = stack.m_41784_();
                  CompoundTag dragonTag = new CompoundTag();
                  dragonTag.m_128362_("DragonUUID", this.m_20148_());
                  if (this.m_7770_() != null) {
                     dragonTag.m_128359_("CustomName", this.m_7770_().getString());
                  }

                  compound.m_128365_("Dragon", dragonTag);
                  this.m_5496_(SoundEvents.f_11771_, 1.0F, 1.0F);
                  player.m_6674_(hand);
                  return InteractionResult.SUCCESS;
               }

               this.m_21828_(player);
               if (stack.m_41720_() == IafItemRegistry.DRAGON_HORN.get()) {
                  return super.m_6071_(player, hand);
               }

               int dragonStage;
               if (stack.m_41619_() && !player.m_6144_()) {
                  if (!this.m_9236_().f_46443_) {
                     dragonStage = this.getDragonStage();
                     if (dragonStage < 2) {
                        if (player.m_20197_().size() >= 3) {
                           return InteractionResult.FAIL;
                        }

                        this.m_7998_(player, true);
                        IceAndFire.sendMSGToAll(new MessageStartRidingMob(this.m_19879_(), true, true));
                     } else if (dragonStage > 2 && !player.m_20159_()) {
                        player.m_20260_(false);
                        player.m_7998_(this, true);
                        IceAndFire.sendMSGToAll(new MessageStartRidingMob(this.m_19879_(), true, false));
                        this.m_21837_(false);
                     }

                     this.m_21573_().m_26573_();
                  }

                  return InteractionResult.SUCCESS;
               }

               if (stack.m_41619_() && player.m_6144_()) {
                  this.openInventory(player);
                  return InteractionResult.SUCCESS;
               }

               dragonStage = FoodUtils.getFoodPoints(stack, true, this.dragonType.isPiscivore());
               if (dragonStage > 0 && (this.getHunger() < 100 || this.m_21223_() < this.m_21233_())) {
                  this.setHunger(this.getHunger() + dragonStage);
                  this.m_21153_(Math.min(this.m_21233_(), (float)((int)(this.m_21223_() + (float)(dragonStage / 10)))));
                  this.m_5496_(SoundEvents.f_11912_, this.m_6121_(), this.m_6100_());
                  this.spawnItemCrackParticles(stack.m_41720_());
                  this.eatFoodBonus(stack);
                  if (!player.m_7500_()) {
                     stack.m_41774_(1);
                  }

                  return InteractionResult.SUCCESS;
               }

               Item stackItem = stack.m_41720_();
               if (stackItem == IafItemRegistry.DRAGON_MEAL.get()) {
                  this.growDragon(1);
                  this.setHunger(this.getHunger() + 20);
                  this.m_5634_(Math.min(this.m_21223_(), (float)((int)(this.m_21233_() / 2.0F))));
                  this.m_5496_(SoundEvents.f_11912_, this.m_6121_(), this.m_6100_());
                  this.spawnItemCrackParticles(stackItem);
                  this.spawnItemCrackParticles(Items.f_42500_);
                  this.spawnItemCrackParticles(Items.f_42499_);
                  this.eatFoodBonus(stack);
                  if (!player.m_7500_()) {
                     stack.m_41774_(1);
                  }

                  return InteractionResult.SUCCESS;
               }

               if (stackItem == IafItemRegistry.SICKLY_DRAGON_MEAL.get() && !this.isAgingDisabled()) {
                  this.setHunger(this.getHunger() + 20);
                  this.m_5634_(this.m_21233_());
                  this.m_5496_(SoundEvents.f_12644_, this.m_6121_(), this.m_6100_());
                  this.spawnItemCrackParticles(stackItem);
                  this.spawnItemCrackParticles(Items.f_42500_);
                  this.spawnItemCrackParticles(Items.f_42499_);
                  this.spawnItemCrackParticles(Items.f_42675_);
                  this.spawnItemCrackParticles(Items.f_42675_);
                  this.setAgingDisabled(true);
                  this.eatFoodBonus(stack);
                  if (!player.m_7500_()) {
                     stack.m_41774_(1);
                  }

                  return InteractionResult.SUCCESS;
               }

               if (stackItem == IafItemRegistry.DRAGON_STAFF.get()) {
                  if (player.m_6144_()) {
                     if (this.hasHomePosition) {
                        this.hasHomePosition = false;
                        player.m_5661_(Component.m_237115_("dragon.command.remove_home"), true);
                        return InteractionResult.SUCCESS;
                     }

                     BlockPos pos = this.m_20183_();
                     this.homePos = new HomePosition(pos, this.m_9236_());
                     this.hasHomePosition = true;
                     player.m_5661_(Component.m_237110_("dragon.command.new_home", new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), this.homePos.getDimension()}), true);
                     return InteractionResult.SUCCESS;
                  }

                  this.m_5496_(SoundEvents.f_12609_, this.m_6121_(), this.m_6100_());
                  if (!this.m_9236_().f_46443_) {
                     this.setCommand(this.getCommand() + 1);
                     if (this.getCommand() > 2) {
                        this.setCommand(0);
                     }
                  }

                  String commandText = "stand";
                  if (this.getCommand() == 1) {
                     commandText = "sit";
                  } else if (this.getCommand() == 2) {
                     commandText = "escort";
                  }

                  player.m_5661_(Component.m_237115_("dragon.command." + commandText), true);
                  return InteractionResult.SUCCESS;
               }
            }
         }

         return super.m_6071_(player, hand);
      }
   }

   public abstract ItemLike getHeartItem();

   public abstract Item getBloodItem();

   public abstract Item getFleshItem();

   public ItemStack getSkull() {
      return ItemStack.f_41583_;
   }

   private ItemStack getRandomDrop() {
      ItemStack stack = this.getItemFromLootTable();
      if (stack.m_41720_() == IafItemRegistry.DRAGON_BONE.get()) {
         this.m_5496_(SoundEvents.f_12423_, 1.0F, 1.0F);
      } else {
         this.m_5496_(SoundEvents.f_11678_, 1.0F, 1.0F);
      }

      return stack;
   }

   public boolean canPositionBeSeen(double x, double y, double z) {
      HitResult result = this.m_9236_().m_45547_(new ClipContext(new Vec3(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_()), new Vec3(x, y, z), Block.COLLIDER, Fluid.NONE, this));
      double dist = result.m_82450_().m_82531_(x, y, z);
      return dist <= 1.0D || result.m_6662_() == Type.MISS;
   }

   public abstract ResourceLocation getDeadLootTable();

   public ItemStack getItemFromLootTable() {
      LootTable loottable = this.m_9236_().m_7654_().getServerResources().f_206585_().m_278801_().m_278676_(this.getDeadLootTable());
      net.minecraft.world.level.storage.loot.LootParams.Builder lootparams$builder = (new net.minecraft.world.level.storage.loot.LootParams.Builder((ServerLevel)this.m_9236_())).m_287286_(LootContextParams.f_81455_, this).m_287286_(LootContextParams.f_81460_, this.m_20182_()).m_287286_(LootContextParams.f_81457_, this.m_9236_().m_269111_().m_269264_());
      ObjectListIterator var3 = loottable.m_287195_(lootparams$builder.m_287235_(LootContextParamSets.f_81415_)).iterator();
      if (var3.hasNext()) {
         ItemStack itemstack = (ItemStack)var3.next();
         return itemstack;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public void eatFoodBonus(ItemStack stack) {
   }

   public boolean m_8023_() {
      return true;
   }

   public boolean m_21532_() {
      return true;
   }

   public void growDragon(int ageInDays) {
      if (!this.isAgingDisabled()) {
         this.setAgeInDays(this.getAgeInDays() + ageInDays);
         this.m_20011_(this.m_20191_());
         if (this.m_9236_().f_46443_ && this.getAgeInDays() % 25 == 0) {
            for(int i = 0; (float)i < this.getRenderSize() * 4.0F; ++i) {
               float f = (float)((double)this.m_217043_().m_188501_() * (this.m_20191_().f_82291_ - this.m_20191_().f_82288_) + this.m_20191_().f_82288_);
               float f1 = (float)((double)this.m_217043_().m_188501_() * (this.m_20191_().f_82292_ - this.m_20191_().f_82289_) + this.m_20191_().f_82289_);
               float f2 = (float)((double)this.m_217043_().m_188501_() * (this.m_20191_().f_82293_ - this.m_20191_().f_82290_) + this.m_20191_().f_82290_);
               double motionX = this.m_217043_().m_188583_() * 0.07D;
               double motionY = this.m_217043_().m_188583_() * 0.07D;
               double motionZ = this.m_217043_().m_188583_() * 0.07D;
               this.m_9236_().m_7106_(ParticleTypes.f_123748_, (double)f, (double)f1, (double)f2, motionX, motionY, motionZ);
            }
         }

         if (this.getDragonStage() >= 2) {
            this.m_6038_();
         }

         this.updateAttributes();
      }
   }

   public void spawnItemCrackParticles(Item item) {
      for(int i = 0; i < 15; ++i) {
         double motionX = this.m_217043_().m_188583_() * 0.07D;
         double motionY = this.m_217043_().m_188583_() * 0.07D;
         double motionZ = this.m_217043_().m_188583_() * 0.07D;
         Vec3 headVec = this.getHeadPosition();
         if (!this.m_9236_().f_46443_) {
            ((ServerLevel)this.m_9236_()).m_8767_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(item)), headVec.f_82479_, headVec.f_82480_, headVec.f_82481_, 1, motionX, motionY, motionZ, 0.1D);
         } else {
            this.m_9236_().m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, new ItemStack(item)), headVec.f_82479_, headVec.f_82480_, headVec.f_82481_, motionX, motionY, motionZ);
         }
      }

   }

   public boolean isTimeToWake() {
      return this.m_9236_().m_46461_() || this.getCommand() == 2;
   }

   private boolean isStuck() {
      boolean skip = this.isChained() || this.m_21824_();
      if (skip) {
         return false;
      } else {
         boolean checkNavigation = this.ticksStill > 80 && this.canMove() && !this.isHovering();
         if (checkNavigation) {
            PathNavigation navigation = this.m_21573_();
            Path path = navigation.m_26570_();
            if (!navigation.m_26571_() && (path == null || path.m_77395_() != null || this.m_20183_().m_123331_(path.m_77395_().m_77288_()) > 15.0D)) {
               return true;
            }
         }

         return false;
      }
   }

   protected boolean isOverAir() {
      return this.isOverAir;
   }

   private boolean isOverAirLogic() {
      return this.m_9236_().m_46859_(BlockPos.m_274561_((double)this.m_146903_(), this.m_20191_().f_82289_ - 1.0D, (double)this.m_146907_()));
   }

   public boolean isDiving() {
      return false;
   }

   public boolean isBeyondHeight() {
      if (this.m_20186_() > (double)this.m_9236_().m_151558_()) {
         return true;
      } else {
         return this.m_20186_() > (double)IafConfig.maxDragonFlight;
      }
   }

   private int calculateDownY() {
      if (this.m_21573_().m_26570_() != null) {
         Path path = this.m_21573_().m_26570_();
         Vec3 p = path.m_77382_(this, Math.min(path.m_77398_() - 1, path.m_77399_() + 1));
         if (p.f_82480_ < this.m_20186_() - 1.0D) {
            return -1;
         }
      }

      return 1;
   }

   public void breakBlock(BlockPos position) {
      if (!MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this, (double)position.m_123341_(), (double)position.m_123342_(), (double)position.m_123343_()))) {
         BlockState state = this.m_9236_().m_8055_(position);
         float hardness = IafConfig.dragonGriefing != 1 && this.getDragonStage() > 3 ? 5.0F : 2.0F;
         if (this.isBreakable(position, state, hardness, this)) {
            this.m_20256_(this.m_20184_().m_82542_(0.6000000238418579D, 1.0D, 0.6000000238418579D));
            if (!this.m_9236_().m_5776_()) {
               this.m_9236_().m_46961_(position, !state.m_204336_(IafBlockTags.DRAGON_BLOCK_BREAK_NO_DROPS) && (double)this.f_19796_.m_188501_() <= IafConfig.dragonBlockBreakingDropChance);
            }
         }

      }
   }

   public void breakBlocks(boolean force) {
      boolean doBreak = force;
      if (this.blockBreakCounter > 0 || IafConfig.dragonBreakBlockCooldown == 0) {
         --this.blockBreakCounter;
         if (this.blockBreakCounter == 0 || IafConfig.dragonBreakBlockCooldown == 0) {
            doBreak = true;
         }
      }

      if (doBreak && ForgeEventFactory.getMobGriefingEvent(this.m_9236_(), this) && DragonUtils.canGrief(this) && !this.isModelDead() && this.getDragonStage() >= 3 && (this.canMove() || this.m_6688_() != null)) {
         int bounds = true;
         int flightModifier = this.isFlying() && this.m_5448_() != null ? -1 : 1;
         int yMinus = this.calculateDownY();
         BlockPos.m_121886_((int)Math.floor(this.m_20191_().f_82288_) - 1, (int)Math.floor(this.m_20191_().f_82289_) + yMinus, (int)Math.floor(this.m_20191_().f_82290_) - 1, (int)Math.floor(this.m_20191_().f_82291_) + 1, (int)Math.floor(this.m_20191_().f_82292_) + 1 + flightModifier, (int)Math.floor(this.m_20191_().f_82293_) + 1).forEach(this::breakBlock);
      }

   }

   protected boolean isBreakable(BlockPos pos, BlockState state, float hardness, EntityDragonBase entity) {
      return state.m_280555_() && !state.m_60795_() && state.m_60819_().m_76178_() && !state.m_60808_(this.m_9236_(), pos).m_83281_() && state.m_60800_(this.m_9236_(), pos) >= 0.0F && state.m_60800_(this.m_9236_(), pos) <= hardness && DragonUtils.canDragonBreak(state, entity) && this.canDestroyBlock(pos, state);
   }

   public boolean isBlockExplicitlyPassable(BlockState state, BlockPos pos, BlockPos entityPos) {
      return !this.isModelDead() && this.getDragonStage() >= 3 && DragonUtils.canGrief(this) && (double)pos.m_123342_() >= this.m_20186_() ? this.isBreakable(pos, state, IafConfig.dragonGriefing != 1 && this.getDragonStage() > 3 ? 5.0F : 2.0F, this) : false;
   }

   public boolean isBlockExplicitlyNotPassable(BlockState state, BlockPos pos, BlockPos entityPos) {
      return false;
   }

   public void spawnGroundEffects() {
      if (this.m_9236_().f_46443_) {
         for(int i = 0; (float)i < this.getRenderSize(); ++i) {
            for(int i1 = 0; i1 < 20; ++i1) {
               float radius = 0.75F * (0.7F * this.getRenderSize() / 3.0F) * -3.0F;
               float angle = 0.017453292F * this.f_20883_ + (float)i1 * 1.0F;
               double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
               double extraY = 0.800000011920929D;
               double extraZ = (double)(radius * Mth.m_14089_(angle));
               BlockPos ground = this.getGround(BlockPos.m_274561_(this.m_20185_() + extraX, this.m_20186_() + 0.800000011920929D - 1.0D, this.m_20189_() + extraZ));
               BlockState BlockState = this.m_9236_().m_8055_(ground);
               if (BlockState.m_60795_()) {
                  double motionX = this.m_217043_().m_188583_() * 0.07D;
                  double motionY = this.m_217043_().m_188583_() * 0.07D;
                  double motionZ = this.m_217043_().m_188583_() * 0.07D;
                  this.m_9236_().m_6493_(new BlockParticleOption(ParticleTypes.f_123794_, BlockState), true, this.m_20185_() + extraX, (double)ground.m_123342_() + 0.800000011920929D, this.m_20189_() + extraZ, motionX, motionY, motionZ);
               }
            }
         }
      }

   }

   private BlockPos getGround(BlockPos blockPos) {
      while(this.m_9236_().m_46859_(blockPos) && blockPos.m_123342_() > 1) {
         blockPos = blockPos.m_7495_();
      }

      return blockPos;
   }

   public boolean isActuallyBreathingFire() {
      return this.fireTicks > 20 && this.isBreathingFire();
   }

   public boolean doesWantToLand() {
      return this.flyTicks > 6000 || this.isGoingDown() || this.flyTicks > 40 && this.flyProgress == 0.0F || this.isChained() && this.flyTicks > 100;
   }

   public abstract String getVariantName(int var1);

   public boolean shouldRiderSit() {
      return this.m_6688_() != null;
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         if (this.m_6688_() != null && this.m_6688_().m_20148_().equals(passenger.m_20148_())) {
            if (this.isModelDead()) {
               passenger.m_8127_();
            }

            this.m_146922_(passenger.m_146908_());
            this.m_5616_(passenger.m_6080_());
            this.m_146926_(passenger.m_146909_());
            Vec3 riderPos = this.getRiderPosition();
            passenger.m_6034_(riderPos.f_82479_, riderPos.f_82480_ + (double)passenger.m_20206_(), riderPos.f_82481_);
         } else {
            this.updatePreyInMouth(passenger);
         }
      }

   }

   private float bob(float speed, float degree, boolean bounce, float f, float f1) {
      double a = (double)(Mth.m_14031_(f * speed) * f1 * degree);
      float bob = (float)(a - (double)(f1 * degree));
      if (bounce) {
         bob = (float)(-Math.abs(a));
      }

      return bob * this.getRenderSize() / 3.0F;
   }

   protected void updatePreyInMouth(Entity prey) {
      if (this.getAnimation() != ANIMATION_SHAKEPREY) {
         this.setAnimation(ANIMATION_SHAKEPREY);
      }

      float modTick_0;
      float damage;
      if (this.getAnimation() == ANIMATION_SHAKEPREY && this.getAnimationTick() > 55 && prey != null) {
         modTick_0 = (float)this.m_21051_(Attributes.f_22281_).m_22135_();
         damage = modTick_0 * 2.0F;
         boolean didDamage = prey.m_6469_(this.m_9236_().m_269111_().m_269333_(this), damage);
         if (didDamage && IafConfig.canDragonsHealFromBiting) {
            this.m_5634_(damage * 0.5F);
         }

         if (!(prey instanceof Player)) {
            this.setHunger(this.getHunger() + 1);
         }

         prey.m_8127_();
      } else {
         this.f_20883_ = this.m_146908_();
         modTick_0 = (float)(this.getAnimationTick() - 25);
         damage = this.getAnimationTick() > 25 && this.getAnimationTick() < 55 ? 8.0F * Mth.m_14036_(Mth.m_14031_((float)(3.141592653589793D + (double)modTick_0 * 0.25D)), -0.8F, 0.8F) : 0.0F;
         float modTick_2 = this.getAnimationTick() > 30 ? 10.0F : (float)Math.max(0, this.getAnimationTick() - 20);
         float radius = 0.75F * (0.6F * this.getRenderSize() / 3.0F) * -3.0F;
         float angle = 0.017453292F * this.f_20883_ + 3.15F + damage * 2.0F * 0.015F;
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         double extraY = modTick_2 == 0.0F ? 0.0D : 0.03500000014901161D * ((double)(this.getRenderSize() / 3.0F) + (double)modTick_2 * 0.5D * (double)(this.getRenderSize() / 3.0F));
         prey.m_6034_(this.m_20185_() + extraX, this.m_20186_() + extraY, this.m_20189_() + extraZ);
      }

   }

   public int getDragonStage() {
      int age = this.getAgeInDays();
      if (age >= 100) {
         return 5;
      } else if (age >= 75) {
         return 4;
      } else if (age >= 50) {
         return 3;
      } else {
         return age >= 25 ? 2 : 1;
      }
   }

   public boolean isTeen() {
      return this.getDragonStage() < 4 && this.getDragonStage() > 2;
   }

   public boolean m_6125_() {
      return this.getDragonStage() >= 4;
   }

   public boolean m_6162_() {
      return this.getDragonStage() < 2;
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setGender(this.m_217043_().m_188499_());
      int age = this.m_217043_().m_188503_(80) + 1;
      this.growDragon(age);
      this.setVariant((new Random()).nextInt(4));
      this.m_21837_(false);
      double healthStep = (this.maximumHealth - this.minimumHealth) / 125.0D;
      this.m_5634_((float)Math.round(this.minimumHealth + healthStep * (double)age));
      this.usingGroundAttack = true;
      this.setHunger(50);
      return spawnDataIn;
   }

   public boolean m_6469_(@NotNull DamageSource dmg, float i) {
      if (this.isModelDead() && dmg != this.m_9236_().m_269111_().m_269341_()) {
         return false;
      } else if (this.m_20160_() && dmg.m_7639_() != null && this.m_6688_() != null && dmg.m_7639_() == this.m_6688_()) {
         return false;
      } else if ((dmg.m_269415_().f_268677_().contains("arrow") || this.m_20202_() != null && dmg.m_7639_() != null && dmg.m_7639_().m_7306_(this.m_20202_())) && this.m_20159_()) {
         return false;
      } else if (!dmg.m_276093_(DamageTypes.f_268612_) && !dmg.m_276093_(DamageTypes.f_268659_) && !dmg.m_276093_(DamageTypes.f_268613_)) {
         if (!this.m_9236_().f_46443_ && dmg.m_7639_() != null && this.m_217043_().m_188503_(4) == 0) {
            this.roar();
         }

         if (i > 0.0F && this.m_5803_()) {
            this.m_21837_(false);
            if (!this.m_21824_() && dmg.m_7639_() instanceof Player) {
               this.m_6710_((Player)dmg.m_7639_());
            }
         }

         return super.m_6469_(dmg, i);
      } else {
         return false;
      }
   }

   public void m_6210_() {
      super.m_6210_();
      float scale = Math.min(this.getRenderSize() * 0.35F, 7.0F);
      if (scale != this.lastScale) {
         this.resetParts(this.getRenderSize() / 3.0F);
      }

      this.lastScale = scale;
   }

   public float getStepHeight() {
      return Math.max(1.2F, 1.2F + (float)(Math.min(this.getAgeInDays(), 125) - 25) * 1.8F / 100.0F);
   }

   public void m_8119_() {
      super.m_8119_();
      this.m_6210_();
      this.updateParts();
      this.prevDragonPitch = this.getDragonPitch();
      this.m_9236_().m_46473_().m_6180_("dragonLogic");
      this.m_274367_(this.getStepHeight());
      this.isOverAir = this.isOverAirLogic();
      this.logic.updateDragonCommon();
      if (this.isModelDead()) {
         if (!this.m_9236_().f_46443_ && this.m_9236_().m_46859_(BlockPos.m_274561_((double)this.m_146903_(), this.m_20191_().f_82289_, (double)this.m_146907_())) && this.m_20186_() > -1.0D) {
            this.m_6478_(MoverType.SELF, new Vec3(0.0D, -0.20000000298023224D, 0.0D));
         }

         this.setBreathingFire(false);
         float dragonPitch = this.getDragonPitch();
         if (dragonPitch > 0.0F) {
            dragonPitch = Math.min(0.0F, dragonPitch - 5.0F);
            this.setDragonPitch(dragonPitch);
         }

         if (dragonPitch < 0.0F) {
            this.setDragonPitch(Math.max(0.0F, dragonPitch + 5.0F));
         }
      } else if (this.m_9236_().f_46443_) {
         this.logic.updateDragonClient();
      } else {
         this.logic.updateDragonServer();
         this.logic.updateDragonAttack();
      }

      this.m_9236_().m_46473_().m_7238_();
      this.m_9236_().m_46473_().m_6180_("dragonFlight");
      if (this.useFlyingPathFinder() && !this.m_9236_().f_46443_) {
         this.flightManager.update();
      }

      this.m_9236_().m_46473_().m_7238_();
      this.m_9236_().m_46473_().m_7238_();
      if (!this.m_9236_().m_5776_() && IafConfig.dragonDigWhenStuck && this.isStuck()) {
         this.breakBlocks(true);
         this.resetStuck();
      }

   }

   private void resetStuck() {
      this.ticksStill = 0;
   }

   public void m_8107_() {
      super.m_8107_();
      this.prevModelDeadProgress = this.modelDeadProgress;
      this.prevDiveProgress = this.diveProgress;
      this.prevAnimationProgresses[0] = this.sitProgress;
      this.prevAnimationProgresses[1] = this.sleepProgress;
      this.prevAnimationProgresses[2] = this.hoverProgress;
      this.prevAnimationProgresses[3] = this.flyProgress;
      this.prevAnimationProgresses[4] = this.fireBreathProgress;
      this.prevAnimationProgresses[5] = this.ridingProgress;
      this.prevAnimationProgresses[6] = this.tackleProgress;
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.isModelDead()) {
         if (this.m_20160_()) {
            this.m_20153_();
         }

         this.setHovering(false);
         this.setFlying(false);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.animationTick > this.getAnimation().getDuration() && !this.m_9236_().f_46443_) {
         this.animationTick = 0;
      }

   }

   @NotNull
   public EntityDimensions m_6972_(@NotNull Pose poseIn) {
      return this.m_6095_().m_20680_().m_20388_(this.m_6134_());
   }

   public float m_6134_() {
      return Math.min(this.getRenderSize() * 0.35F, 7.0F);
   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public float getRenderSize() {
      int stage = this.getDragonStage() - 1;
      float step = (this.growth_stages[stage][1] - this.growth_stages[stage][0]) / 25.0F;
      return this.getAgeInDays() > 125 ? this.growth_stages[stage][0] + step * 25.0F : this.growth_stages[stage][0] + step * (float)this.getAgeFactor();
   }

   private int getAgeFactor() {
      return this.getDragonStage() > 1 ? this.getAgeInDays() - 25 * (this.getDragonStage() - 1) : this.getAgeInDays();
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      this.m_21563_().m_24960_(entityIn, 30.0F, 30.0F);
      if (!this.isTackling() && !this.isModelDead()) {
         boolean flag = entityIn.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         if (flag) {
            this.m_19970_(this, entityIn);
         }

         return flag;
      } else {
         return false;
      }
   }

   public void m_6083_() {
      Entity entity = this.m_20202_();
      if (this.m_20159_() && !entity.m_6084_()) {
         this.m_8127_();
      } else {
         this.m_20334_(0.0D, 0.0D, 0.0D);
         this.m_8119_();
         if (this.m_20159_()) {
            this.updateRiding(entity);
         }
      }

   }

   public void updateRiding(Entity riding) {
      if (riding != null && riding.m_20363_(this) && riding instanceof Player) {
         int i = riding.m_20197_().indexOf(this);
         float radius = (i == 2 ? -0.2F : 0.5F) + (float)(((Player)riding).m_21255_() ? 2 : 0);
         float angle = 0.017453292F * ((Player)riding).f_20883_ + (float)(i == 1 ? 90 : (i == 0 ? -90 : 0));
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         double extraY = (riding.m_6144_() ? 1.2D : 1.4D) + (i == 2 ? 0.4D : 0.0D);
         this.f_20885_ = ((Player)riding).f_20885_;
         this.m_146922_(((Player)riding).f_20885_);
         this.m_6034_(riding.m_20185_() + extraX, riding.m_20186_() + extraY, riding.m_20189_() + extraZ);
         if ((this.getControlState() == 16 || ((Player)riding).m_21255_()) && !riding.m_20159_()) {
            this.m_8127_();
            if (this.m_9236_().f_46443_) {
               IceAndFire.sendMSGToServer(new MessageStartRidingMob(this.m_19879_(), false, true));
            }
         }
      }

   }

   public int getAnimationTick() {
      return this.animationTick;
   }

   public void setAnimationTick(int tick) {
      this.animationTick = tick;
   }

   public Animation getAnimation() {
      return this.isModelDead() ? NO_ANIMATION : this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      if (!this.isModelDead()) {
         this.currentAnimation = animation;
      }
   }

   public void m_8032_() {
      if (!this.m_5803_() && !this.isModelDead() && !this.m_9236_().f_46443_) {
         if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ANIMATION_SPEAK);
         }

         super.m_8032_();
      }

   }

   protected void m_6677_(@NotNull DamageSource source) {
      if (!this.isModelDead()) {
         if (this.getAnimation() == NO_ANIMATION && !this.m_9236_().f_46443_) {
            this.setAnimation(ANIMATION_SPEAK);
         }

         super.m_6677_(source);
      }

   }

   public Animation[] getAnimations() {
      return new Animation[]{IAnimatedEntity.NO_ANIMATION, ANIMATION_EAT};
   }

   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      return null;
   }

   public boolean m_7848_(@NotNull Animal otherAnimal) {
      if (otherAnimal instanceof EntityDragonBase) {
         EntityDragonBase dragon = (EntityDragonBase)otherAnimal;
         if (otherAnimal != this && otherAnimal.getClass() == this.getClass()) {
            return this.isMale() && !dragon.isMale() || !this.isMale() && dragon.isMale();
         }
      }

      return false;
   }

   public EntityDragonEgg createEgg(EntityDragonBase ageable) {
      EntityDragonEgg dragon = new EntityDragonEgg((EntityType)IafEntityRegistry.DRAGON_EGG.get(), this.m_9236_());
      dragon.setEggType(EnumDragonEgg.byMetadata((new Random()).nextInt(4) + this.getStartMetaForType()));
      dragon.m_6034_((double)Mth.m_14107_(this.m_20185_()) + 0.5D, (double)(Mth.m_14107_(this.m_20186_()) + 1), (double)Mth.m_14107_(this.m_20189_()) + 0.5D);
      return dragon;
   }

   public int getStartMetaForType() {
      return 0;
   }

   public boolean isTargetBlocked(Vec3 target) {
      if (target != null) {
         BlockHitResult rayTrace = this.m_9236_().m_45547_(new ClipContext(this.m_20182_().m_82520_(0.0D, (double)this.m_20192_(), 0.0D), target, Block.COLLIDER, Fluid.NONE, this));
         BlockPos sidePos = rayTrace.m_82425_();
         if (!this.m_9236_().m_46859_(sidePos)) {
            return true;
         } else {
            return rayTrace.m_6662_() == Type.BLOCK;
         }
      } else {
         return false;
      }
   }

   private double getFlySpeed() {
      return (double)((2 + this.getAgeInDays() / 125 * 2) * (this.isTackling() ? 2 : 1));
   }

   public boolean isTackling() {
      return (Boolean)this.f_19804_.m_135370_(TACKLE);
   }

   public void setTackling(boolean tackling) {
      this.f_19804_.m_135381_(TACKLE, tackling);
   }

   public boolean isAgingDisabled() {
      return (Boolean)this.f_19804_.m_135370_(AGINGDISABLED);
   }

   public void setAgingDisabled(boolean isAgingDisabled) {
      this.f_19804_.m_135381_(AGINGDISABLED, isAgingDisabled);
   }

   public boolean isBoundToCrystal() {
      return (Boolean)this.f_19804_.m_135370_(CRYSTAL_BOUND);
   }

   public void setCrystalBound(boolean crystalBound) {
      this.f_19804_.m_135381_(CRYSTAL_BOUND, crystalBound);
   }

   public float getDistanceSquared(Vec3 Vector3d) {
      float f = (float)(this.m_20185_() - Vector3d.f_82479_);
      float f1 = (float)(this.m_20186_() - Vector3d.f_82480_);
      float f2 = (float)(this.m_20189_() - Vector3d.f_82481_);
      return f * f + f1 * f1 + f2 * f2;
   }

   public abstract Item getVariantScale(int var1);

   public abstract Item getVariantEgg(int var1);

   public abstract Item getSummoningCrystal();

   public boolean m_6107_() {
      return this.m_21223_() <= 0.0F || this.m_21827_() && !this.m_20160_() || this.isModelDead() || this.m_20159_();
   }

   public boolean m_20069_() {
      return super.m_20069_() && this.m_204036_(FluidTags.f_13131_) > (double)Mth.m_14143_((float)this.getDragonStage() / 2.0F);
   }

   public void m_7023_(@NotNull Vec3 pTravelVector) {
      if (this.getAnimation() == ANIMATION_SHAKEPREY || !this.canMove() && !this.m_20160_() || this.m_21827_()) {
         if (this.m_21573_().m_26570_() != null) {
            this.m_21573_().m_26573_();
         }

         pTravelVector = new Vec3(0.0D, 0.0D, 0.0D);
      }

      if (this.allowLocalMotionControl && this.m_6688_() != null) {
         LivingEntity rider = this.m_6688_();
         if (rider == null) {
            super.m_7023_(pTravelVector);
            return;
         }

         double forward;
         double strafing;
         double vertical;
         float speed;
         float groundSpeedModifier;
         if (this.isHovering() || this.isFlying()) {
            forward = (double)rider.f_20902_;
            strafing = (double)rider.f_20900_;
            vertical = 0.0D;
            speed = (float)this.m_21133_(Attributes.f_22279_);
            groundSpeedModifier = (float)(5.199999809265137D + 1.0D * Mth.m_144914_((double)speed, this.minimumSpeed, this.maximumSpeed, 0.0D, 1.5D));
            speed *= groundSpeedModifier;
            if (forward > 0.0D) {
               this.setFlying(true);
               this.setHovering(false);
            }

            if (this.isAttacking() && this.m_146909_() > -5.0F && this.m_20184_().m_82553_() > 1.0D) {
               this.setTackling(true);
            } else {
               this.setTackling(false);
            }

            this.gliding = this.allowMousePitchControl && rider.m_20142_();
            if (!this.gliding) {
               speed += this.glidingSpeedBonus;
               forward *= rider.f_20902_ > 0.0F ? 1.0D : 0.5D;
               strafing *= 0.4000000059604645D;
               if (this.isGoingUp() && !this.isGoingDown()) {
                  vertical = 1.0D;
               } else if (this.isGoingDown() && !this.isGoingUp()) {
                  vertical = -1.0D;
               } else if (this.m_6109_()) {
               }
            } else {
               speed *= 1.5F;
               strafing *= 0.10000000149011612D;
               this.glidingSpeedBonus = (float)Mth.m_14008_((double)this.glidingSpeedBonus + this.m_20184_().f_82480_ * -0.05D, -0.8D, 1.5D);
               speed += this.glidingSpeedBonus;
               forward = (double)Mth.m_14154_(Mth.m_14089_(this.m_146909_() * 0.017453292F));
               vertical = (double)Mth.m_14154_(Mth.m_14031_(this.m_146909_() * 0.017453292F));
               if (this.isGoingUp() && !this.isGoingDown()) {
                  vertical = Math.max(vertical, 0.5D);
               } else if (this.isGoingDown() && !this.isGoingUp()) {
                  vertical = Math.min(vertical, -0.5D);
               } else if (this.isGoingUp() && this.isGoingDown()) {
                  vertical = 0.0D;
               } else if (this.m_146909_() < 0.0F) {
                  vertical *= 1.0D;
               } else if (this.m_146909_() > 0.0F) {
                  vertical *= -1.0D;
               } else if (this.m_6109_()) {
               }
            }

            this.glidingSpeedBonus -= (float)((double)this.glidingSpeedBonus * 0.01D);
            if (this.m_6109_()) {
               float flyingSpeed = speed * 0.1F;
               this.m_7910_(flyingSpeed);
               this.m_19920_(flyingSpeed, new Vec3(strafing, vertical, forward));
               this.m_6478_(MoverType.SELF, this.m_20184_());
               this.m_20256_(this.m_20184_().m_82559_(new Vec3(0.9D, 0.9D, 0.9D)));
               Vec3 currentMotion = this.m_20184_();
               if (this.f_19862_) {
                  currentMotion = new Vec3(currentMotion.f_82479_, 0.1D, currentMotion.f_82481_);
               }

               this.m_20256_(currentMotion);
               this.m_267651_(false);
            } else {
               this.m_20256_(Vec3.f_82478_);
            }

            this.m_146872_();
            this.updatePitch(this.f_19791_ - this.m_20186_());
            return;
         }

         if (this.m_20069_() || this.m_20077_()) {
            forward = (double)rider.f_20902_;
            strafing = (double)rider.f_20900_;
            vertical = 0.0D;
            speed = (float)this.m_21133_(Attributes.f_22279_);
            if (this.isGoingUp() && !this.isGoingDown()) {
               vertical = 0.5D;
            } else if (this.isGoingDown() && !this.isGoingUp()) {
               vertical = -0.5D;
            }

            this.m_7910_(speed);
            this.m_21564_((float)forward);
            super.m_7023_(pTravelVector.m_82520_(strafing, vertical, forward));
            return;
         }

         forward = (double)rider.f_20902_;
         strafing = (double)(rider.f_20900_ * 0.5F);
         vertical = pTravelVector.f_82480_;
         speed = (float)this.m_21133_(Attributes.f_22279_);
         groundSpeedModifier = (float)(1.7999999523162842D * this.getFlightSpeedModifier());
         speed *= groundSpeedModifier;
         forward *= (double)speed;
         forward *= rider.m_20142_() ? 1.2000000476837158D : 1.0D;
         forward *= rider.f_20902_ > 0.0F ? 1.0D : 0.20000000298023224D;
         if (this.m_6109_()) {
            this.m_7910_(speed);
            super.m_7023_(new Vec3(strafing, vertical, forward));
         } else {
            this.m_20256_(Vec3.f_82478_);
         }

         this.m_146872_();
         this.updatePitch(this.f_19791_ - this.m_20186_());
      } else {
         super.m_7023_(pTravelVector);
      }

   }

   protected void updatePitch(double verticalDelta) {
      if (this.isOverAir() && !this.m_20159_()) {
         if (!this.isHovering()) {
            this.incrementDragonPitch((float)verticalDelta * 10.0F);
         }

         this.setDragonPitch(Mth.m_14036_(this.getDragonPitch(), -60.0F, 40.0F));
         float plateau = 2.0F;
         float planeDist = (float)((Math.abs(this.m_20184_().f_82479_) + Math.abs(this.m_20184_().f_82481_)) * 6.0D);
         if (this.getDragonPitch() > 2.0F) {
            this.decrementDragonPitch(planeDist * Math.abs(this.getDragonPitch()) / 90.0F);
         }

         if (this.getDragonPitch() < -2.0F) {
            this.incrementDragonPitch(planeDist * Math.abs(this.getDragonPitch()) / 90.0F);
         }

         if (this.getDragonPitch() > 2.0F) {
            this.decrementDragonPitch(1.0F);
         } else if (this.getDragonPitch() < -2.0F) {
            this.incrementDragonPitch(1.0F);
         }

         if (this.m_6688_() == null && this.getDragonPitch() < -45.0F && planeDist < 3.0F && this.isFlying() && !this.isHovering()) {
            this.setHovering(true);
         }
      } else if (Mth.m_14154_(this.getDragonPitch()) < 1.0F) {
         this.setDragonPitch(0.0F);
      } else {
         this.setDragonPitch(this.getDragonPitch() / 1.5F);
      }

   }

   public void updateRider() {
      Entity controllingPassenger = this.m_6688_();
      if (controllingPassenger instanceof Player) {
         Player rider = (Player)controllingPassenger;
         this.ticksStill = 0;
         this.hoverTicks = 0;
         this.flyTicks = 0;
         if (this.isGoingUp()) {
            if (!this.isFlying() && !this.isHovering()) {
               this.spacebarTicks += 2;
            }
         } else if (this.isDismounting() && (this.isFlying() || this.isHovering())) {
            this.setCommand(2);
         }

         if (this.spacebarTicks > 0) {
            --this.spacebarTicks;
         }

         if (this.spacebarTicks > 20 && this.m_269323_() != null && this.m_20197_().contains(this.m_269323_()) && !this.isFlying() && !this.isHovering() && !this.m_20069_()) {
            this.setHovering(true);
            this.spacebarTicks = 0;
            this.glidingSpeedBonus = 0.0F;
         }

         if (this.isFlying() || this.isHovering()) {
            if (rider.f_20902_ > 0.0F) {
               this.setFlying(true);
               this.setHovering(false);
            } else {
               this.setFlying(false);
               this.setHovering(true);
            }

            if (!this.isOverAir() && this.isFlying() && rider.m_146909_() > 10.0F && !this.m_20069_()) {
               this.setHovering(false);
               this.setFlying(false);
            }

            if (!this.isOverAir() && this.isGoingDown() && !this.m_20069_()) {
               this.setFlying(false);
               this.setHovering(false);
            }
         }

         if (this.isTackling()) {
            ++this.tacklingTicks;
            if (this.tacklingTicks == 40) {
               this.tacklingTicks = 0;
            }

            if (!this.isFlying() && this.m_20096_()) {
               this.tacklingTicks = 0;
               this.setTackling(false);
            }

            List<Entity> victims = this.m_9236_().m_6249_(this, this.m_20191_().m_82363_(2.0D, 2.0D, 2.0D), (potentialVictim) -> {
               return potentialVictim != rider && potentialVictim instanceof LivingEntity;
            });
            victims.forEach((victim) -> {
               this.logic.attackTarget(victim, rider, (float)(this.getDragonStage() * 3));
            });
         }

         if (this.isStriking() && this.m_6688_() != null && this.getDragonStage() > 1) {
            this.setBreathingFire(true);
            this.riderShootFire(this.m_6688_());
            this.fireStopTicks = 10;
         }

         if (this.isAttacking() && this.m_6688_() != null && this.m_6688_() instanceof Player) {
            LivingEntity target = DragonUtils.riderLookingAtEntity(this, this.m_6688_(), (double)this.getDragonStage() + (this.m_20191_().f_82291_ - this.m_20191_().f_82288_));
            if (this.getAnimation() != ANIMATION_BITE) {
               this.setAnimation(ANIMATION_BITE);
            }

            if (target != null && !DragonUtils.hasSameOwner(this, target)) {
               int damage = (int)this.m_21051_(Attributes.f_22281_).m_22135_();
               boolean didDamage = this.logic.attackTarget(target, rider, (float)damage);
               if (didDamage && IafConfig.canDragonsHealFromBiting) {
                  this.m_5634_((float)damage * 0.1F);
               }
            }
         }

         if (this.m_6688_() != null && this.m_6688_().m_6144_()) {
            EntityDataProvider.getCapability(this.m_6688_()).ifPresent((data) -> {
               data.miscData.setDismounted(true);
            });
            this.m_6688_().m_8127_();
         }

         if (this.m_5448_() != null && !this.m_20197_().isEmpty() && this.m_269323_() != null && this.m_20197_().contains(this.m_269323_())) {
            this.m_6710_((LivingEntity)null);
         }

         if (this.m_146900_().m_60819_().m_76170_() && this.m_20069_() && !this.isGoingUp()) {
            this.setFlying(false);
            this.setHovering(false);
         }
      } else if (controllingPassenger instanceof EntityDreadQueen) {
         Player ridingPlayer = this.getRidingPlayer();
         if (ridingPlayer != null) {
            if (this.isGoingUp()) {
               if (!this.isFlying() && !this.isHovering()) {
                  this.spacebarTicks += 2;
               }
            } else if (this.isDismounting() && (this.isFlying() || this.isHovering())) {
               this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.04D, 0.0D));
               this.setFlying(false);
               this.setHovering(false);
            }
         }

         if (!this.isDismounting() && (this.isFlying() || this.isHovering())) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.01D, 0.0D));
         }

         if (this.isStriking() && this.m_6688_() != null && this.getDragonStage() > 1) {
            this.setBreathingFire(true);
            this.riderShootFire(this.m_6688_());
            this.fireStopTicks = 10;
         }

         if (this.isAttacking() && this.m_6688_() != null && this.m_6688_() instanceof Player) {
            LivingEntity target = DragonUtils.riderLookingAtEntity(this, this.m_6688_(), (double)this.getDragonStage() + (this.m_20191_().f_82291_ - this.m_20191_().f_82288_));
            if (this.getAnimation() != ANIMATION_BITE) {
               this.setAnimation(ANIMATION_BITE);
            }

            if (target != null && !DragonUtils.hasSameOwner(this, target)) {
               this.logic.attackTarget(target, ridingPlayer, (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            }
         }

         if (this.m_6688_() != null && this.m_6688_().m_6144_()) {
            EntityDataProvider.getCapability(this.m_6688_()).ifPresent((data) -> {
               data.miscData.setDismounted(true);
            });
            this.m_6688_().m_8127_();
         }

         if (this.isFlying()) {
            if (!this.isHovering() && this.m_6688_() != null && !this.m_20096_() && Math.max(Math.abs(this.m_20184_().m_7096_()), Math.abs(this.m_20184_().m_7094_())) < 0.10000000149011612D) {
               this.setHovering(true);
               this.setFlying(false);
            }
         } else if (this.isHovering() && this.m_6688_() != null && !this.m_20096_() && Math.max(Math.abs(this.m_20184_().m_7096_()), Math.abs(this.m_20184_().m_7094_())) > 0.10000000149011612D) {
            this.setFlying(true);
            this.usingGroundAttack = false;
            this.setHovering(false);
         }

         if (this.spacebarTicks > 0) {
            --this.spacebarTicks;
         }

         if (this.spacebarTicks > 20 && this.m_269323_() != null && this.m_20197_().contains(this.m_269323_()) && !this.isFlying() && !this.isHovering()) {
            this.setHovering(true);
         }

         if (this.m_20160_() && !this.isOverAir() && this.isFlying() && !this.isHovering() && this.flyTicks > 40) {
            this.setFlying(false);
         }
      }

   }

   public void m_6478_(@NotNull MoverType pType, @NotNull Vec3 pPos) {
      if (this.m_21827_() && !this.m_20160_()) {
         pPos = new Vec3(0.0D, pPos.m_7098_(), 0.0D);
      }

      if (this.m_20160_()) {
         if (this.m_6109_()) {
            if (this.f_19862_) {
               this.m_20256_(this.m_20184_().m_82542_(0.6000000238418579D, 1.0D, 0.6000000238418579D));
            }

            super.m_6478_(pType, pPos);
         } else {
            super.m_6478_(pType, pPos);
         }

         this.m_20242_(this.isHovering() || this.isFlying());
      } else {
         this.m_20242_(false);
         super.m_6478_(pType, pPos);
      }

   }

   public void updateCheckPlayer() {
      double checkLength = this.m_20191_().m_82309_() * 3.0D;
      Player player = this.m_9236_().m_45930_(this, checkLength);
      if (this.m_5803_() && player != null && !this.m_21830_(player) && !player.m_7500_()) {
         this.m_21837_(false);
         this.m_21839_(false);
         this.m_6710_(player);
      }

   }

   public boolean isDirectPathBetweenPoints(Vec3 vec1, Vec3 vec2) {
      BlockHitResult rayTrace = this.m_9236_().m_45547_(new ClipContext(vec1, new Vec3(vec2.f_82479_, vec2.f_82480_ + (double)this.m_20206_() * 0.5D, vec2.f_82481_), Block.COLLIDER, Fluid.NONE, this));
      return rayTrace.m_6662_() != Type.BLOCK;
   }

   public void m_6667_(@NotNull DamageSource cause) {
      super.m_6667_(cause);
      this.setHunger(this.getHunger() + FoodUtils.getFoodPoints(this));
   }

   public void onHearFlute(Player player) {
      if (this.m_21824_() && this.m_21830_(player) && (this.isFlying() || this.isHovering())) {
         this.setFlying(false);
         this.setHovering(false);
      }

   }

   public abstract SoundEvent getRoarSound();

   public void roar() {
      if (!EntityGorgon.isStoneMob(this) && !this.isModelDead()) {
         int size;
         List entities;
         Iterator var3;
         Entity entity;
         boolean isStrongerDragon;
         LivingEntity living;
         if (this.f_19796_.m_188499_()) {
            if (this.getAnimation() != ANIMATION_EPIC_ROAR) {
               this.setAnimation(ANIMATION_EPIC_ROAR);
               this.m_5496_(this.getRoarSound(), this.m_6121_() + 3.0F + (float)Math.max(0, this.getDragonStage() - 2), this.m_6100_() * 0.7F);
            }

            if (this.getDragonStage() > 3) {
               size = (this.getDragonStage() - 3) * 30;
               entities = this.m_9236_().m_45933_(this, this.m_20191_().m_82363_((double)size, (double)size, (double)size));
               var3 = entities.iterator();

               while(true) {
                  while(true) {
                     do {
                        do {
                           if (!var3.hasNext()) {
                              return;
                           }

                           entity = (Entity)var3.next();
                           isStrongerDragon = entity instanceof EntityDragonBase && ((EntityDragonBase)entity).getDragonStage() >= this.getDragonStage();
                        } while(!(entity instanceof LivingEntity));

                        living = (LivingEntity)entity;
                     } while(isStrongerDragon);

                     if (!this.m_21830_(living) && !this.isOwnersPet(living)) {
                        if (living.m_6844_(EquipmentSlot.HEAD).m_41720_() != IafItemRegistry.EARPLUGS.get()) {
                           living.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 50 * size));
                        }
                     } else {
                        living.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 50 * size));
                     }
                  }
               }
            }
         } else {
            if (this.getAnimation() != ANIMATION_ROAR) {
               this.setAnimation(ANIMATION_ROAR);
               this.m_5496_(this.getRoarSound(), this.m_6121_() + 2.0F + (float)Math.max(0, this.getDragonStage() - 3), this.m_6100_());
            }

            if (this.getDragonStage() > 3) {
               size = (this.getDragonStage() - 3) * 30;
               entities = this.m_9236_().m_45933_(this, this.m_20191_().m_82363_((double)size, (double)size, (double)size));
               var3 = entities.iterator();

               while(true) {
                  while(true) {
                     do {
                        do {
                           if (!var3.hasNext()) {
                              return;
                           }

                           entity = (Entity)var3.next();
                           isStrongerDragon = entity instanceof EntityDragonBase && ((EntityDragonBase)entity).getDragonStage() >= this.getDragonStage();
                        } while(!(entity instanceof LivingEntity));

                        living = (LivingEntity)entity;
                     } while(isStrongerDragon);

                     if (!this.m_21830_(living) && !this.isOwnersPet(living)) {
                        living.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 30 * size));
                     } else {
                        living.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 30 * size));
                     }
                  }
               }
            }
         }

      }
   }

   private boolean isOwnersPet(LivingEntity living) {
      return this.m_21824_() && this.m_269323_() != null && living instanceof TamableAnimal && ((TamableAnimal)living).m_269323_() != null && this.m_269323_().m_7306_(((TamableAnimal)living).m_269323_());
   }

   public boolean isDirectPathBetweenPoints(Entity entity, Vec3 vec1, Vec3 vec2) {
      HitResult movingobjectposition = this.m_9236_().m_45547_(new ClipContext(vec1, vec2, Block.COLLIDER, Fluid.NONE, this));
      return movingobjectposition.m_6662_() != Type.BLOCK;
   }

   public boolean shouldRenderEyes() {
      return !this.m_5803_() && !this.isModelDead() && !this.isBlinking() && !EntityGorgon.isStoneMob(this);
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return DragonUtils.canTameDragonAttack(this, entity);
   }

   public void dropArmor() {
   }

   public boolean isChained() {
      AtomicBoolean isChained = new AtomicBoolean(false);
      EntityDataProvider.getCapability(this).ifPresent((data) -> {
         isChained.set(data.chainData.getChainedTo().isEmpty());
      });
      return isChained.get();
   }

   protected void m_7625_(@NotNull DamageSource damageSourceIn, boolean attackedRecently) {
   }

   public HitResult rayTraceRider(Entity rider, double blockReachDistance, float partialTicks) {
      Vec3 Vector3d = rider.m_20299_(partialTicks);
      Vec3 Vector3d1 = rider.m_20252_(partialTicks);
      Vec3 Vector3d2 = Vector3d.m_82520_(Vector3d1.f_82479_ * blockReachDistance, Vector3d1.f_82480_ * blockReachDistance, Vector3d1.f_82481_ * blockReachDistance);
      return this.m_9236_().m_45547_(new ClipContext(Vector3d, Vector3d2, Block.COLLIDER, Fluid.NONE, this));
   }

   protected float getRideHeightBase() {
      return 0.00223789F * Mth.m_14207_(this.getRenderSize()) + 0.23313718F * this.getRenderSize() - 1.7179043F;
   }

   protected float getRideHorizontalBase() {
      return 0.00336283F * Mth.m_14207_(this.getRenderSize()) + 0.19342425F * this.getRenderSize() - 0.026221339F;
   }

   public Vec3 getRiderPosition() {
      float extraXZ = 0.0F;
      float extraY = 0.0F;
      float pitchXZ = 0.0F;
      float pitchY = 0.0F;
      float dragonPitch = this.getDragonPitch();
      if (dragonPitch > 0.0F) {
         pitchXZ = Math.min(dragonPitch / 90.0F, 0.2F);
         pitchY = -(dragonPitch / 90.0F) * 0.6F;
      } else if (dragonPitch < 0.0F) {
         pitchXZ = Math.max(dragonPitch / 90.0F, -0.5F);
         pitchY = dragonPitch / 90.0F * 0.03F;
      }

      extraXZ += pitchXZ * this.getRenderSize();
      extraY += pitchY * this.getRenderSize();
      float linearFactor = Mth.m_184637_((float)Math.max(this.getAgeInDays() - 50, 0), 0.0F, 75.0F, 0.0F, 1.0F);
      LivingEntity rider = this.m_6688_();
      if (rider != null && rider.m_146909_() < 0.0F) {
         extraY += (float)Mth.m_144914_((double)rider.m_146909_(), 60.0D, -40.0D, -0.1D, 0.1D);
      }

      float MAX_RAISE_HEIGHT;
      if (!this.isHovering() && !this.isFlying()) {
         if (rider != null && rider.f_20902_ > 0.0F) {
            MAX_RAISE_HEIGHT = 1.1F * linearFactor + this.getRideHeightBase() * 0.1F;
            this.riderWalkingExtraY = Math.min(MAX_RAISE_HEIGHT, this.riderWalkingExtraY + 0.1F);
         } else {
            this.riderWalkingExtraY = Math.max(0.0F, this.riderWalkingExtraY - 0.15F);
         }

         extraY += this.riderWalkingExtraY;
      } else {
         extraY += 1.1F * linearFactor;
         extraY += this.getRideHeightBase() * 0.6F;
      }

      MAX_RAISE_HEIGHT = this.getRideHorizontalBase() + extraXZ;
      float yMod = this.getRideHeightBase() + extraY;
      float headPosX = (float)(this.m_20185_() + (double)(MAX_RAISE_HEIGHT * Mth.m_14089_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
      float headPosY = (float)(this.m_20186_() + (double)yMod);
      float headPosZ = (float)(this.m_20189_() + (double)(MAX_RAISE_HEIGHT * Mth.m_14031_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
      return new Vec3((double)headPosX, (double)headPosY, (double)headPosZ);
   }

   @NotNull
   public Vec3 m_7688_(LivingEntity passenger) {
      return passenger.m_5830_() ? this.m_20182_().m_82520_(0.0D, 1.0D, 0.0D) : this.getRiderPosition().m_82520_(0.0D, (double)passenger.m_20206_(), 0.0D);
   }

   public void m_6074_() {
      this.m_142687_(RemovalReason.KILLED);
      this.setDeathStage(this.getAgeInDays() / 5);
      this.setModelDead(false);
   }

   public boolean m_7307_(@NotNull Entity entityIn) {
      if (this.isModelDead()) {
         return true;
      } else {
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
   }

   public Vec3 getHeadPosition() {
      float sitProg = this.sitProgress * 0.015F;
      float deadProg = this.modelDeadProgress * -0.02F;
      float hoverProg = this.hoverProgress * 0.03F;
      float flyProg = this.flyProgress * 0.01F;
      int tick;
      if (this.getAnimationTick() < 10) {
         tick = this.getAnimationTick();
      } else if (this.getAnimationTick() > 50) {
         tick = 60 - this.getAnimationTick();
      } else {
         tick = 10;
      }

      float epicRoarProg = this.getAnimation() == ANIMATION_EPIC_ROAR ? (float)tick * 0.1F : 0.0F;
      float sleepProg = this.sleepProgress * -0.025F;
      float pitchMulti = 0.0F;
      float pitchAdjustment = 0.0F;
      float pitchMinus = 0.0F;
      float dragonPitch = -this.getDragonPitch();
      if (this.isFlying() || this.isHovering()) {
         pitchMulti = Mth.m_14031_((float)Math.toRadians((double)dragonPitch));
         pitchAdjustment = 1.2F;
         pitchMulti *= 2.1F * Math.abs(dragonPitch) / 90.0F;
         if (pitchMulti > 0.0F) {
            pitchMulti *= 1.5F - pitchMulti * 0.5F;
         }

         if (pitchMulti < 0.0F) {
            pitchMulti *= 1.3F - pitchMulti * 0.1F;
         }

         pitchMinus = 0.3F * Math.abs(dragonPitch / 90.0F);
         if (dragonPitch >= 0.0F) {
            pitchAdjustment = 0.6F * Math.abs(dragonPitch / 90.0F);
            pitchMinus = 0.95F * Math.abs(dragonPitch / 90.0F);
         }
      }

      float flightXz = 1.0F + flyProg + hoverProg;
      float xzMod = 1.7F * this.getRenderSize() * 0.3F * flightXz + this.getRenderSize() * (0.3F * Mth.m_14031_((float)((double)(dragonPitch + 90.0F) * 3.141592653589793D / 180.0D)) * pitchAdjustment - pitchMinus - hoverProg * 0.45F);
      float headPosX = (float)(this.m_20185_() + (double)(xzMod * Mth.m_14089_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
      float headPosY = (float)(this.m_20186_() + (double)((0.7F + sitProg + hoverProg + deadProg + epicRoarProg + sleepProg + flyProg + pitchMulti) * this.getRenderSize() * 0.3F));
      float headPosZ = (float)(this.m_20189_() + (double)(xzMod * Mth.m_14031_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
      return new Vec3((double)headPosX, (double)headPosY, (double)headPosZ);
   }

   public abstract void stimulateFire(double var1, double var3, double var5, int var7);

   public void randomizeAttacks() {
      this.airAttack = IafDragonAttacks.Air.values()[this.m_217043_().m_188503_(IafDragonAttacks.Air.values().length)];
      this.groundAttack = IafDragonAttacks.Ground.values()[this.m_217043_().m_188503_(IafDragonAttacks.Ground.values().length)];
   }

   public boolean m_7349_(@NotNull Explosion explosionIn, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, BlockState blockStateIn, float explosionPower) {
      return !(blockStateIn.m_60734_() instanceof IDragonProof) && DragonUtils.canDragonBreak(blockStateIn, this);
   }

   public void tryScorchTarget() {
      LivingEntity entity = this.m_5448_();
      if (entity != null) {
         float distX = (float)(entity.m_20185_() - this.m_20185_());
         float distZ = (float)(entity.m_20189_() - this.m_20189_());
         if (this.isBreathingFire()) {
            if (this.isActuallyBreathingFire()) {
               this.m_146922_(this.f_20883_);
               if (this.f_19797_ % 5 == 0) {
                  this.m_5496_(IafSoundRegistry.FIREDRAGON_BREATH, 4.0F, 1.0F);
               }

               this.stimulateFire(this.m_20185_() + (double)(distX * (float)this.fireTicks / 40.0F), entity.m_20186_(), this.m_20189_() + (double)(distZ * (float)this.fireTicks / 40.0F), 1);
            }
         } else {
            this.setBreathingFire(true);
         }
      }

   }

   public void m_6710_(@Nullable LivingEntity LivingEntityIn) {
      super.m_6710_(LivingEntityIn);
      this.flightManager.onSetAttackTarget(LivingEntityIn);
   }

   public boolean m_7757_(@NotNull LivingEntity target, @NotNull LivingEntity owner) {
      if (this.m_21824_() && target instanceof TamableAnimal) {
         TamableAnimal tamableTarget = (TamableAnimal)target;
         UUID targetOwner = tamableTarget.m_21805_();
         if (targetOwner != null && targetOwner.equals(this.m_21805_())) {
            return false;
         }
      }

      return super.m_7757_(target, owner);
   }

   public boolean m_6779_(@NotNull LivingEntity target) {
      return super.m_6779_(target) && DragonUtils.isAlive(target);
   }

   public boolean isPart(Entity entityHit) {
      return this.headPart != null && this.headPart.m_7306_(entityHit) || this.neckPart != null && this.neckPart.m_7306_(entityHit) || this.leftWingLowerPart != null && this.leftWingLowerPart.m_7306_(entityHit) || this.rightWingLowerPart != null && this.rightWingLowerPart.m_7306_(entityHit) || this.leftWingUpperPart != null && this.leftWingUpperPart.m_7306_(entityHit) || this.rightWingUpperPart != null && this.rightWingUpperPart.m_7306_(entityHit) || this.tail1Part != null && this.tail1Part.m_7306_(entityHit) || this.tail2Part != null && this.tail2Part.m_7306_(entityHit) || this.tail3Part != null && this.tail3Part.m_7306_(entityHit) || this.tail4Part != null && this.tail4Part.m_7306_(entityHit);
   }

   public double getFlightSpeedModifier() {
      return IafConfig.dragonFlightSpeedMod;
   }

   public boolean isAllowedToTriggerFlight() {
      return (this.hasFlightClearance() && this.m_20096_() || this.m_20069_()) && !this.m_21827_() && this.m_20197_().isEmpty() && !this.m_6162_() && !this.m_5803_() && this.canMove();
   }

   public BlockPos getEscortPosition() {
      return this.m_269323_() != null ? new BlockPos(this.m_269323_().m_20183_()) : this.m_20183_();
   }

   public boolean shouldTPtoOwner() {
      return this.m_269323_() != null && this.m_20270_(this.m_269323_()) > 10.0F;
   }

   public boolean isSkeletal() {
      return this.getDeathStage() >= this.getAgeInDays() / 5 / 2;
   }

   public boolean m_20223_(@NotNull CompoundTag compound) {
      return this.m_20086_(compound);
   }

   public void m_5496_(@NotNull SoundEvent soundIn, float volume, float pitch) {
      if (soundIn != SoundEvents.f_11912_ && soundIn != this.m_7515_() && soundIn != this.m_7975_(this.m_9236_().m_269111_().m_269264_()) && soundIn != this.m_5592_() && soundIn != this.getRoarSound()) {
         super.m_5496_(soundIn, volume, pitch);
      } else if (!this.m_20067_() && this.headPart != null) {
         this.m_9236_().m_6263_((Player)null, this.headPart.m_20185_(), this.headPart.m_20186_(), this.headPart.m_20189_(), soundIn, this.m_5720_(), volume, pitch);
      }

   }

   @NotNull
   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public boolean hasFlightClearance() {
      BlockPos topOfBB = BlockPos.m_274561_((double)this.m_146903_(), this.m_20191_().f_82292_, (double)this.m_146907_());

      for(int i = 1; i < 4; ++i) {
         if (!this.m_9236_().m_46859_(topOfBB.m_6630_(i))) {
            return false;
         }
      }

      return true;
   }

   @NotNull
   public ItemStack m_6844_(EquipmentSlot slotIn) {
      ItemStack var10000;
      switch(slotIn) {
      case OFFHAND:
         var10000 = this.dragonInventory.m_8020_(0);
         break;
      case HEAD:
         var10000 = this.dragonInventory.m_8020_(1);
         break;
      case CHEST:
         var10000 = this.dragonInventory.m_8020_(2);
         break;
      case LEGS:
         var10000 = this.dragonInventory.m_8020_(3);
         break;
      case FEET:
         var10000 = this.dragonInventory.m_8020_(4);
         break;
      default:
         var10000 = super.m_6844_(slotIn);
      }

      return var10000;
   }

   public void m_8061_(EquipmentSlot slotIn, @NotNull ItemStack stack) {
      switch(slotIn) {
      case OFFHAND:
         this.dragonInventory.m_6836_(0, stack);
         break;
      case HEAD:
         this.dragonInventory.m_6836_(1, stack);
         break;
      case CHEST:
         this.dragonInventory.m_6836_(2, stack);
         break;
      case LEGS:
         this.dragonInventory.m_6836_(3, stack);
         break;
      case FEET:
         this.dragonInventory.m_6836_(4, stack);
         break;
      default:
         super.m_6844_(slotIn);
      }

   }

   public SoundEvent getBabyFireSound() {
      return SoundEvents.f_11937_;
   }

   protected boolean isPlayingAttackAnimation() {
      return this.getAnimation() == ANIMATION_BITE || this.getAnimation() == ANIMATION_SHAKEPREY || this.getAnimation() == ANIMATION_WINGBLAST || this.getAnimation() == ANIMATION_TAILWHACK;
   }

   protected IafDragonLogic createDragonLogic() {
      return new IafDragonLogic(this);
   }

   protected int getFlightChancePerTick() {
      return 1500;
   }

   public void onRemovedFromWorld() {
      if (IafConfig.chunkLoadSummonCrystal && this.isBoundToCrystal()) {
         DragonPosWorldData data = DragonPosWorldData.get(this.m_9236_());
         if (data != null) {
            data.addDragon(this.m_20148_(), this.m_20183_());
         }
      }

      super.onRemovedFromWorld();
   }

   public int maxSearchNodes() {
      return (int)this.m_21051_(Attributes.f_22277_).m_22135_();
   }

   public boolean isSmallerThanBlock() {
      return false;
   }

   public float getXZNavSize() {
      return Math.max(1.4F, this.m_20205_() / 2.0F);
   }

   public int getYNavSize() {
      return Mth.m_14167_(this.m_20206_());
   }

   public void m_5757_(@NotNull Container invBasic) {
      if (!this.m_9236_().f_46443_) {
         this.updateAttributes();
      }

   }

   @NotNull
   public Vec3 m_21074_(@NotNull Vec3 pDeltaMovement, float pFriction) {
      return this.f_21342_ instanceof IafDragonFlightManager.PlayerFlightMoveHelper ? pDeltaMovement : super.m_21074_(pDeltaMovement, pFriction);
   }

   static {
      SWIMMING = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
      HUNGER = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135028_);
      AGE_TICKS = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135028_);
      GENDER = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      VARIANT = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135028_);
      SLEEPING = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      FIREBREATHING = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      HOVERING = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      FLYING = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      MODEL_DEAD = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      DEATH_STAGE = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135028_);
      CONTROL_STATE = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135027_);
      TACKLE = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      AGINGDISABLED = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      COMMAND = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135028_);
      DRAGON_PITCH = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135029_);
      CRYSTAL_BOUND = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135035_);
      CUSTOM_POSE = SynchedEntityData.m_135353_(EntityDragonBase.class, EntityDataSerializers.f_135030_);
      growth_stage_1 = new float[]{1.0F, 3.0F};
      growth_stage_2 = new float[]{3.0F, 7.0F};
      growth_stage_3 = new float[]{7.0F, 12.5F};
      growth_stage_4 = new float[]{12.5F, 20.0F};
      growth_stage_5 = new float[]{20.0F, 30.0F};
   }
}
