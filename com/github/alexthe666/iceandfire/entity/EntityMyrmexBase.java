package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockMyrmexConnectedResin;
import com.github.alexthe666.iceandfire.block.BlockMyrmexResin;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.IPassabilityNavigator;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.ICustomSizeNavigator;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.util.ITeleporter;
import org.jetbrains.annotations.NotNull;

public abstract class EntityMyrmexBase extends Animal implements IAnimatedEntity, Merchant, ICustomSizeNavigator, IPassabilityNavigator, IHasCustomizableAttributes {
   public static final Animation ANIMATION_PUPA_WIGGLE = Animation.create(20);
   private static final EntityDataAccessor<Byte> CLIMBING;
   private static final EntityDataAccessor<Integer> GROWTH_STAGE;
   private static final EntityDataAccessor<Boolean> VARIANT;
   private static final ResourceLocation TEXTURE_DESERT_LARVA;
   private static final ResourceLocation TEXTURE_DESERT_PUPA;
   private static final ResourceLocation TEXTURE_JUNGLE_LARVA;
   private static final ResourceLocation TEXTURE_JUNGLE_PUPA;
   private final SimpleContainer villagerInventory = new SimpleContainer(8);
   public boolean isEnteringHive = false;
   public boolean isBeingGuarded = false;
   protected int growthTicks = 1;
   @Nullable
   protected MerchantOffers offers;
   private int waitTicks = 0;
   private int animationTick;
   private Animation currentAnimation;
   private MyrmexHive hive;
   private int timeUntilReset;
   private boolean leveledUp;
   @Nullable
   private Player customer;

   public EntityMyrmexBase(EntityType<? extends EntityMyrmexBase> t, Level worldIn) {
      super(t, worldIn);
      this.f_21344_ = this.createNavigator(worldIn, AdvancedPathNavigate.MovementType.CLIMBING);
   }

   private static boolean isJungleBiome(Level world, BlockPos position) {
      return BiomeConfig.test(BiomeConfig.jungleMyrmexBiomes, world.m_204166_(position));
   }

   public static boolean haveSameHive(EntityMyrmexBase myrmex, Entity entity) {
      if (entity instanceof EntityMyrmexBase && myrmex.getHive() != null && ((EntityMyrmexBase)entity).getHive() != null && myrmex.isJungle() == ((EntityMyrmexBase)entity).isJungle()) {
         return myrmex.getHive().getCenter() == ((EntityMyrmexBase)entity).getHive().getCenter();
      } else if (entity instanceof EntityMyrmexEgg) {
         return myrmex.isJungle() == ((EntityMyrmexEgg)entity).isJungle();
      } else {
         return false;
      }
   }

   public static boolean isEdibleBlock(BlockState blockState) {
      return blockState.m_204336_(BlockTags.create(IafTagRegistry.MYRMEX_HARVESTABLES));
   }

   public static int getRandomCaste(Level world, RandomSource random, boolean royal) {
      float rand = random.m_188501_();
      if (royal) {
         if ((double)rand > 0.9D) {
            return 2;
         } else if ((double)rand > 0.75D) {
            return 3;
         } else {
            return (double)rand > 0.5D ? 1 : 0;
         }
      } else if ((double)rand > 0.8D) {
         return 3;
      } else {
         return (double)rand > 0.6D ? 1 : 0;
      }
   }

   @NotNull
   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   public boolean canMove() {
      return this.getGrowthStage() > 1;
   }

   public boolean m_6162_() {
      return this.getGrowthStage() < 2;
   }

   protected void m_8024_() {
      if (!this.hasCustomer() && this.timeUntilReset > 0) {
         --this.timeUntilReset;
         if (this.timeUntilReset <= 0) {
            if (this.leveledUp) {
               this.levelUp();
               this.leveledUp = false;
            }

            this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 200, 0));
         }
      }

      if (this.getHive() != null && this.m_7962_() != null) {
         this.m_9236_().m_7605_(this, (byte)14);
         this.getHive().setWorld(this.m_9236_());
      }

      super.m_8024_();
   }

   public int m_213860_() {
      return this.getCasteImportance() * 7 + this.m_9236_().f_46441_.m_188503_(3);
   }

   public boolean m_6469_(@NotNull DamageSource dmg, float i) {
      if (dmg == this.m_9236_().m_269111_().m_269318_() && this.getGrowthStage() < 2) {
         return false;
      } else {
         if (this.getGrowthStage() < 2) {
            this.setAnimation(ANIMATION_PUPA_WIGGLE);
         }

         return super.m_6469_(dmg, i);
      }
   }

   protected float m_6118_() {
      return 0.52F;
   }

   public float m_21692_(BlockPos pos) {
      return this.m_9236_().m_8055_(pos.m_7495_()).m_60734_() instanceof BlockMyrmexResin ? 10.0F : super.m_21692_(pos);
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level worldIn) {
      return this.createNavigator(worldIn, AdvancedPathNavigate.MovementType.CLIMBING);
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type) {
      return this.createNavigator(worldIn, type, this.m_20205_(), this.m_20206_());
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type, float width, float height) {
      AdvancedPathNavigate newNavigator = new AdvancedPathNavigate(this, this.m_9236_(), type, width, height);
      this.f_21344_ = newNavigator;
      newNavigator.m_7008_(true);
      newNavigator.m_26575_().m_77355_(true);
      return newNavigator;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(CLIMBING, (byte)0);
      this.f_19804_.m_135372_(GROWTH_STAGE, 2);
      this.f_19804_.m_135372_(VARIANT, Boolean.FALSE);
   }

   public void m_8119_() {
      super.m_8119_();
      this.m_274367_(1.0F);
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.getGrowthStage() < 2 && this.m_20202_() != null && this.m_20202_() instanceof EntityMyrmexBase) {
         float yaw = this.m_20202_().m_146908_();
         this.m_146922_(yaw);
         this.f_20885_ = yaw;
         this.f_20883_ = 0.0F;
         this.f_20884_ = 0.0F;
      }

      if (!this.m_9236_().f_46443_) {
         this.setBesideClimbableBlock(this.f_19862_ && (this.m_20096_() || !this.f_19863_));
      }

      if (this.getGrowthStage() < 2) {
         ++this.growthTicks;
         if (this.growthTicks == IafConfig.myrmexLarvaTicks) {
            this.setGrowthStage(this.getGrowthStage() + 1);
            this.growthTicks = 0;
         }
      }

      if (!this.m_9236_().f_46443_ && this.getGrowthStage() < 2 && this.m_217043_().m_188503_(150) == 0 && this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_PUPA_WIGGLE);
      }

      if (this.m_5448_() != null && !(this.m_5448_() instanceof Player) && this.m_21573_().m_26571_()) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_5448_() != null && (haveSameHive(this, this.m_5448_()) || this.m_5448_() instanceof TamableAnimal && !this.canAttackTamable((TamableAnimal)this.m_5448_()) || this.m_5448_() instanceof Player && this.getHive() != null && !this.getHive().isPlayerReputationLowEnoughToFight(this.m_5448_().m_20148_()))) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.getWaitTicks() > 0) {
         this.setWaitTicks(this.getWaitTicks() - 1);
      }

      if (this.m_21223_() < this.m_21233_() && this.f_19797_ % 500 == 0 && this.isOnResin()) {
         this.m_5634_(1.0F);
         this.m_9236_().m_7605_(this, (byte)76);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("GrowthStage", this.getGrowthStage());
      tag.m_128405_("GrowthTicks", this.growthTicks);
      tag.m_128379_("Variant", this.isJungle());
      if (this.getHive() != null) {
         tag.m_128362_("HiveUUID", this.getHive().hiveUUID);
      }

      MerchantOffers merchantoffers = this.m_6616_();
      if (!merchantoffers.isEmpty()) {
         tag.m_128365_("Offers", merchantoffers.m_45388_());
      }

      ListTag listnbt = new ListTag();

      for(int i = 0; i < this.villagerInventory.m_6643_(); ++i) {
         ItemStack itemstack = this.villagerInventory.m_8020_(i);
         if (!itemstack.m_41619_()) {
            listnbt.add(itemstack.m_41739_(new CompoundTag()));
         }
      }

      tag.m_128365_("Inventory", listnbt);
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      this.setGrowthStage(tag.m_128451_("GrowthStage"));
      this.growthTicks = tag.m_128451_("GrowthTicks");
      this.setJungleVariant(tag.m_128471_("Variant"));
      if (tag.m_128403_("HiveUUID")) {
         this.setHive(MyrmexWorldData.get(this.m_9236_()).getHiveFromUUID(tag.m_128342_("HiveUUID")));
      }

      if (tag.m_128425_("Offers", 10)) {
         this.offers = new MerchantOffers(tag.m_128469_("Offers"));
      }

      ListTag listnbt = tag.m_128437_("Inventory", 10);

      for(int i = 0; i < listnbt.size(); ++i) {
         ItemStack itemstack = ItemStack.m_41712_(listnbt.m_128728_(i));
         if (!itemstack.m_41619_()) {
            this.villagerInventory.m_19173_(itemstack);
         }
      }

      this.setConfigurableAttributes();
   }

   public boolean canAttackTamable(TamableAnimal tameable) {
      return tameable.m_269323_() != null && this.getHive() != null ? this.getHive().isPlayerReputationLowEnoughToFight(tameable.m_21805_()) : true;
   }

   public BlockPos getPos() {
      return this.m_20183_();
   }

   public int getGrowthStage() {
      return (Integer)this.f_19804_.m_135370_(GROWTH_STAGE);
   }

   public void setGrowthStage(int stage) {
      this.f_19804_.m_135381_(GROWTH_STAGE, stage);
   }

   public int getWaitTicks() {
      return this.waitTicks;
   }

   public void setWaitTicks(int waitTicks) {
      this.waitTicks = waitTicks;
   }

   public boolean isJungle() {
      return (Boolean)this.f_19804_.m_135370_(VARIANT);
   }

   public void setJungleVariant(boolean isJungle) {
      this.f_19804_.m_135381_(VARIANT, isJungle);
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21642_;
   }

   public boolean isBesideClimbableBlock() {
      return ((Byte)this.f_19804_.m_135370_(CLIMBING) & 1) != 0;
   }

   public void setBesideClimbableBlock(boolean climbing) {
      byte b0 = (Byte)this.f_19804_.m_135370_(CLIMBING);
      if (climbing) {
         b0 = (byte)(b0 | 1);
      } else {
         b0 &= -2;
      }

      this.f_19804_.m_135381_(CLIMBING, b0);
   }

   public boolean m_6147_() {
      return this.m_21573_() instanceof AdvancedPathNavigate && ((AdvancedPathNavigate)this.m_21573_()).entityOnAndBelowPath(this, new Vec3(1.1D, 0.0D, 1.1D)) ? true : super.m_6147_();
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
      return new Animation[]{ANIMATION_PUPA_WIGGLE};
   }

   public void m_6703_(@Nullable LivingEntity livingBase) {
      if (this.getHive() == null || livingBase == null || livingBase instanceof Player && this.getHive().isPlayerReputationLowEnoughToFight(livingBase.m_20148_())) {
         super.m_6703_(livingBase);
      }

      if (this.getHive() != null && livingBase != null) {
         this.getHive().addOrRenewAgressor(livingBase, this.getImportance());
      }

      if (this.getHive() != null && livingBase != null && livingBase instanceof Player) {
         int i = -5 * this.getCasteImportance();
         this.getHive().setWorld(this.m_9236_());
         this.getHive().modifyPlayerReputation(livingBase.m_20148_(), i);
         if (this.m_6084_()) {
            this.m_9236_().m_7605_(this, (byte)13);
         }
      }

   }

   public void m_6667_(@NotNull DamageSource cause) {
      if (this.getHive() != null) {
         Entity entity = cause.m_7639_();
         if (entity != null) {
            this.getHive().setWorld(this.m_9236_());
            this.getHive().modifyPlayerReputation(entity.m_20148_(), -15);
         }
      }

      this.resetCustomer();
      super.m_6667_(cause);
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (!this.shouldHaveNormalAI()) {
         return InteractionResult.PASS;
      } else {
         boolean flag2 = itemstack.m_41720_() == IafItemRegistry.MYRMEX_JUNGLE_STAFF.get() || itemstack.m_41720_() == IafItemRegistry.MYRMEX_DESERT_STAFF.get();
         if (flag2) {
            this.onStaffInteract(player, itemstack);
            player.m_6674_(hand);
            return InteractionResult.SUCCESS;
         } else {
            boolean flag = itemstack.m_41720_() == Items.f_42656_ || itemstack.m_41720_() == Items.f_42655_;
            if (flag) {
               return super.m_6071_(player, hand);
            } else if (this.getGrowthStage() >= 2 && this.m_6084_() && !this.m_6162_() && !player.m_6144_()) {
               if (this.m_6616_().isEmpty()) {
                  return super.m_6071_(player, hand);
               } else if (!this.m_9236_().f_46443_ && (this.m_5448_() == null || !this.m_5448_().equals(player)) && hand == InteractionHand.MAIN_HAND && this.getHive() != null && !this.getHive().isPlayerReputationTooLowToTrade(player.m_20148_())) {
                  this.m_7189_(player);
                  this.m_45301_(player, this.m_5446_(), 1);
                  return InteractionResult.SUCCESS;
               } else {
                  return InteractionResult.PASS;
               }
            } else {
               return super.m_6071_(player, hand);
            }
         }
      }
   }

   public void onStaffInteract(Player player, ItemStack itemstack) {
      if (itemstack.m_41783_() != null) {
         UUID staffUUID = itemstack.m_41783_().m_128403_("HiveUUID") ? itemstack.m_41783_().m_128342_("HiveUUID") : null;
         if (!this.m_9236_().f_46443_) {
            if (player.m_7500_() || this.getHive() == null || this.getHive().canPlayerCommandHive(player.m_20148_())) {
               if (this.getHive() == null) {
                  player.m_5661_(Component.m_237115_("myrmex.message.null_hive"), true);
               } else if (staffUUID != null && staffUUID.equals(this.getHive().hiveUUID)) {
                  player.m_5661_(Component.m_237115_("myrmex.message.staff_already_set"), true);
               } else {
                  this.getHive().setWorld(this.m_9236_());
                  EntityMyrmexQueen queen = this.getHive().getQueen();
                  BlockPos center = this.getHive().getCenterGround();
                  if (queen != null && queen.m_8077_()) {
                     player.m_5661_(Component.m_237110_("myrmex.message.staff_set_named", new Object[]{queen.m_7755_(), center.m_123341_(), center.m_123342_(), center.m_123343_()}), true);
                  } else {
                     player.m_5661_(Component.m_237110_("myrmex.message.staff_set_unnamed", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), true);
                  }

                  itemstack.m_41783_().m_128362_("HiveUUID", this.getHive().hiveUUID);
               }

            }
         }
      }
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setHive(MyrmexWorldData.get(this.m_9236_()).getNearestHive(this.m_20183_(), 400));
      if (this.getHive() != null) {
         this.setJungleVariant(isJungleBiome(this.m_9236_(), this.getHive().getCenter()));
      } else {
         this.setJungleVariant(this.f_19796_.m_188499_());
      }

      return spawnDataIn;
   }

   public abstract boolean shouldLeaveHive();

   public abstract boolean shouldEnterHive();

   public float m_6134_() {
      return this.getGrowthStage() == 0 ? 0.5F : (this.getGrowthStage() == 1 ? 0.75F : 1.0F);
   }

   public abstract ResourceLocation getAdultTexture();

   public abstract float getModelScale();

   public ResourceLocation getTexture() {
      if (this.getGrowthStage() == 0) {
         return this.isJungle() ? TEXTURE_JUNGLE_LARVA : TEXTURE_DESERT_LARVA;
      } else if (this.getGrowthStage() == 1) {
         return this.isJungle() ? TEXTURE_JUNGLE_PUPA : TEXTURE_DESERT_PUPA;
      } else {
         return this.getAdultTexture();
      }
   }

   public MyrmexHive getHive() {
      return this.hive;
   }

   public void setHive(MyrmexHive newHive) {
      this.hive = newHive;
      if (this.hive != null) {
         this.hive.addMyrmex(this);
      }

   }

   protected void m_7324_(@NotNull Entity entityIn) {
      if (!haveSameHive(this, entityIn)) {
         entityIn.m_7334_(this);
      }

   }

   public boolean canSeeSky() {
      return this.m_9236_().m_46861_(this.m_20183_());
   }

   public boolean isOnResin() {
      int d0 = this.m_146904_() - 1;

      BlockPos blockpos;
      for(blockpos = new BlockPos(this.m_146903_(), d0, this.m_146907_()); this.m_9236_().m_46859_(blockpos) && blockpos.m_123342_() > 1; blockpos = blockpos.m_7495_()) {
      }

      BlockState BlockState = this.m_9236_().m_8055_(blockpos);
      return BlockState.m_60734_() instanceof BlockMyrmexResin || BlockState.m_60734_() instanceof BlockMyrmexConnectedResin;
   }

   public boolean isInNursery() {
      if (this.getHive() != null && this.getHive().getRooms(WorldGenMyrmexHive.RoomType.NURSERY).isEmpty() && this.getHive().getRandomRoom(WorldGenMyrmexHive.RoomType.NURSERY, this.m_217043_(), this.m_20183_()) != null) {
         return false;
      } else if (this.getHive() != null) {
         BlockPos nursery = this.getHive().getRandomRoom(WorldGenMyrmexHive.RoomType.NURSERY, this.m_217043_(), this.m_20183_());
         return Math.sqrt(this.m_20275_((double)nursery.m_123341_(), (double)nursery.m_123342_(), (double)nursery.m_123343_())) < 45.0D;
      } else {
         return false;
      }
   }

   public boolean isInHive() {
      if (this.getHive() != null) {
         Iterator var1 = this.getHive().getAllRooms().iterator();

         while(var1.hasNext()) {
            BlockPos pos = (BlockPos)var1.next();
            if (this.isCloseEnoughToTarget(MyrmexHive.getGroundedPos(this.m_9236_(), pos), 50.0D)) {
               return true;
            }
         }
      }

      return false;
   }

   public void m_7023_(@NotNull Vec3 motion) {
      if (!this.canMove()) {
         super.m_7023_(Vec3.f_82478_);
      } else {
         super.m_7023_(motion);
      }
   }

   public int getImportance() {
      return this.getGrowthStage() < 2 ? 1 : this.getCasteImportance();
   }

   public abstract int getCasteImportance();

   public boolean needsGaurding() {
      return true;
   }

   public boolean shouldMoveThroughHive() {
      return true;
   }

   public boolean shouldWander() {
      return this.getHive() == null;
   }

   public void m_7822_(byte id) {
      if (id == 76) {
         this.playVillagerEffect();
      } else {
         super.m_7822_(id);
      }

   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.MYRMEX_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.MYRMEX_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.MYRMEX_DIE;
   }

   protected void playStepSound(BlockPos pos, Block blockIn) {
      this.m_5496_(IafSoundRegistry.MYRMEX_WALK, 0.16F * this.getMyrmexPitch() * (this.m_217043_().m_188501_() * 0.6F + 0.4F), 1.0F);
   }

   protected void playBiteSound() {
      this.m_5496_(IafSoundRegistry.MYRMEX_BITE, this.getMyrmexPitch(), 1.0F);
   }

   protected void playStingSound() {
      this.m_5496_(IafSoundRegistry.MYRMEX_STING, this.getMyrmexPitch(), 0.6F);
   }

   protected void playVillagerEffect() {
      for(int i = 0; i < 7; ++i) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         this.m_9236_().m_7106_(ParticleTypes.f_123748_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + 0.5D + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d0, d1, d2);
      }

   }

   public float getMyrmexPitch() {
      return this.m_20205_();
   }

   public boolean shouldHaveNormalAI() {
      return true;
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   public AABB getAttackBounds() {
      float size = this.m_6134_() * 0.65F;
      return this.m_20191_().m_82377_((double)(1.0F + size), (double)(1.0F + size), (double)(1.0F + size));
   }

   @Nullable
   public Player m_7962_() {
      return this.customer;
   }

   public void m_7189_(@Nullable Player player) {
      this.customer = player;
   }

   public boolean hasCustomer() {
      return this.customer != null;
   }

   @NotNull
   public MerchantOffers m_6616_() {
      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.populateTradeData();
      }

      return this.offers;
   }

   public void m_6255_(@Nullable MerchantOffers offers) {
   }

   public void m_6621_(int xpIn) {
   }

   public void m_6996_(MerchantOffer offer) {
      offer.m_45374_();
      this.f_21363_ = -this.m_8100_();
      this.onVillagerTrade(offer);
   }

   protected void onVillagerTrade(MerchantOffer offer) {
      if (offer.m_45383_()) {
         int i = 3 + this.f_19796_.m_188503_(4);
         this.m_9236_().m_7967_(new ExperienceOrb(this.m_9236_(), this.m_20185_(), this.m_20186_() + 0.5D, this.m_20189_(), i));
      }

      if (this.getHive() != null && this.m_7962_() != null) {
         this.getHive().setWorld(this.m_9236_());
         this.getHive().modifyPlayerReputation(this.m_7962_().m_20148_(), 1);
      }

   }

   public void m_7713_(@NotNull ItemStack stack) {
      if (!this.m_9236_().f_46443_ && this.f_21363_ > -this.m_8100_() + 20) {
         this.f_21363_ = -this.m_8100_();
         this.m_5496_(this.getVillagerYesNoSound(!stack.m_41619_()), this.m_6121_(), this.m_6100_());
      }

   }

   @NotNull
   public SoundEvent m_7596_() {
      return IafSoundRegistry.MYRMEX_IDLE;
   }

   protected SoundEvent getVillagerYesNoSound(boolean getYesSound) {
      return IafSoundRegistry.MYRMEX_IDLE;
   }

   public void playCelebrateSound() {
   }

   protected void resetCustomer() {
      this.m_7189_((Player)null);
   }

   @Nullable
   public Entity changeDimension(@NotNull ServerLevel server, @NotNull ITeleporter teleporter) {
      this.resetCustomer();
      return super.changeDimension(server, teleporter);
   }

   public SimpleContainer getVillagerInventory() {
      return this.villagerInventory;
   }

   @NotNull
   public ItemStack m_255207_(@NotNull ItemStack stack) {
      ItemStack superStack = super.m_255207_(stack);
      if (ItemStack.m_41656_(superStack, stack) && ItemStack.m_41728_(superStack, stack)) {
         return stack;
      } else {
         EquipmentSlot inventorySlot = stack.getEquipmentSlot();
         int i = inventorySlot.m_20749_() - 300;
         if (i >= 0 && i < this.villagerInventory.m_6643_()) {
            this.villagerInventory.m_6836_(i, stack);
            return stack;
         } else {
            return ItemStack.f_41583_;
         }
      }
   }

   protected void addTrades(MerchantOffers givenMerchantOffers, ItemListing[] newTrades, int maxNumbers) {
      Set<Integer> set = Sets.newHashSet();
      if (newTrades.length > maxNumbers) {
         while(set.size() < maxNumbers) {
            set.add(this.f_19796_.m_188503_(newTrades.length));
         }
      } else {
         for(int i = 0; i < newTrades.length; ++i) {
            set.add(i);
         }
      }

      Iterator var9 = set.iterator();

      while(var9.hasNext()) {
         Integer integer = (Integer)var9.next();
         ItemListing villagertrades$itrade = newTrades[integer];
         MerchantOffer merchantoffer = villagertrades$itrade.m_213663_(this, this.f_19796_);
         if (merchantoffer != null) {
            givenMerchantOffers.add(merchantoffer);
         }
      }

   }

   private void levelUp() {
      this.populateTradeData();
   }

   protected abstract ItemListing[] getLevel1Trades();

   protected abstract ItemListing[] getLevel2Trades();

   protected void populateTradeData() {
      ItemListing[] level1 = this.getLevel1Trades();
      ItemListing[] level2 = this.getLevel2Trades();
      if (level1 != null && level2 != null) {
         MerchantOffers merchantoffers = this.m_6616_();
         this.addTrades(merchantoffers, level1, 5);
         int i = this.f_19796_.m_188503_(level2.length);
         int j = this.f_19796_.m_188503_(level2.length);
         int k = this.f_19796_.m_188503_(level2.length);

         int rolls;
         for(rolls = 0; j == i && rolls < 100; ++rolls) {
            j = this.f_19796_.m_188503_(level2.length);
         }

         for(rolls = 0; (k == i || k == j) && rolls < 100; ++rolls) {
            k = this.f_19796_.m_188503_(level2.length);
         }

         ItemListing rareTrade1 = level2[i];
         ItemListing rareTrade2 = level2[j];
         ItemListing rareTrade3 = level2[k];
         MerchantOffer merchantoffer1 = rareTrade1.m_213663_(this, this.f_19796_);
         if (merchantoffer1 != null) {
            merchantoffers.add(merchantoffer1);
         }

         MerchantOffer merchantoffer2 = rareTrade2.m_213663_(this, this.f_19796_);
         if (merchantoffer2 != null) {
            merchantoffers.add(merchantoffer2);
         }

         MerchantOffer merchantoffer3 = rareTrade3.m_213663_(this, this.f_19796_);
         if (merchantoffer3 != null) {
            merchantoffers.add(merchantoffer3);
         }
      }

   }

   public boolean isCloseEnoughToTarget(BlockPos target, double distanceSquared) {
      if (target != null) {
         return this.m_20275_((double)target.m_123341_() + 0.5D, (double)target.m_123342_() + 0.5D, (double)target.m_123343_() + 0.5D) <= distanceSquared;
      } else {
         return false;
      }
   }

   public boolean pathReachesTarget(PathResult path, BlockPos target, double distanceSquared) {
      return !path.failedToReachDestination() && (this.isCloseEnoughToTarget(target, distanceSquared) || this.m_21573_().m_26570_() == null || !this.m_21573_().m_26570_().m_77392_());
   }

   public boolean isSmallerThanBlock() {
      return false;
   }

   public float getXZNavSize() {
      return this.m_20205_() / 2.0F;
   }

   public int getYNavSize() {
      return (int)this.m_20206_() / 2;
   }

   public int maxSearchNodes() {
      return IafConfig.maxDragonPathingNodes;
   }

   public boolean isBlockExplicitlyPassable(BlockState state, BlockPos pos, BlockPos entityPos) {
      return false;
   }

   public boolean isBlockExplicitlyNotPassable(BlockState state, BlockPos pos, BlockPos entityPos) {
      return state.m_60734_() instanceof LeavesBlock;
   }

   static {
      CLIMBING = SynchedEntityData.m_135353_(EntityMyrmexBase.class, EntityDataSerializers.f_135027_);
      GROWTH_STAGE = SynchedEntityData.m_135353_(EntityMyrmexBase.class, EntityDataSerializers.f_135028_);
      VARIANT = SynchedEntityData.m_135353_(EntityMyrmexBase.class, EntityDataSerializers.f_135035_);
      TEXTURE_DESERT_LARVA = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_larva.png");
      TEXTURE_DESERT_PUPA = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_pupa.png");
      TEXTURE_JUNGLE_LARVA = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_larva.png");
      TEXTURE_JUNGLE_PUPA = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_pupa.png");
   }
}
