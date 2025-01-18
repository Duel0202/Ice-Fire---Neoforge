package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.ai.AquaticAIFindWaterTarget;
import com.github.alexthe666.iceandfire.entity.ai.AquaticAIGetInWater;
import com.github.alexthe666.iceandfire.entity.ai.HippocampusAIWander;
import com.github.alexthe666.iceandfire.entity.util.ChainBuffer;
import com.github.alexthe666.iceandfire.entity.util.ICustomMoveController;
import com.github.alexthe666.iceandfire.entity.util.ISyncMount;
import com.github.alexthe666.iceandfire.inventory.HippocampusContainerMenu;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerListener;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.ForgeHooks;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.fluids.FluidType;
import net.neoforge.items.wrapper.InvWrapper;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityHippocampus extends TamableAnimal implements ISyncMount, IAnimatedEntity, ICustomMoveController, ContainerListener, Saddleable {
   public static final int INV_SLOT_SADDLE = 0;
   public static final int INV_SLOT_CHEST = 1;
   public static final int INV_SLOT_ARMOR = 2;
   public static final int INV_BASE_COUNT = 3;
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Boolean> SADDLE;
   private static final EntityDataAccessor<Integer> ARMOR;
   private static final EntityDataAccessor<Boolean> CHESTED;
   private static final EntityDataAccessor<Byte> CONTROL_STATE;
   private static final int FLAG_SITTING = 1;
   private static final int FLAG_TAME = 4;
   private static final Component CONTAINER_TITLE;
   public static Animation ANIMATION_SPEAK;
   public float onLandProgress;
   public ChainBuffer tail_buffer;
   public SimpleContainer inventory;
   public float sitProgress;
   private int animationTick;
   private Animation currentAnimation;
   private LazyOptional<?> itemHandler = null;

   public EntityHippocampus(EntityType<? extends EntityHippocampus> entityType, Level worldIn) {
      super(entityType, worldIn);
      ANIMATION_SPEAK = Animation.create(15);
      this.m_21441_(BlockPathTypes.WATER, 0.0F);
      this.f_21342_ = new EntityHippocampus.HippoMoveControl(this);
      this.m_274367_(1.0F);
      if (worldIn.f_46443_) {
         this.tail_buffer = new ChainBuffer();
      }

      this.createInventory();
   }

   public static int getIntFromArmor(ItemStack stack) {
      if (!stack.m_41619_() && stack.m_41720_() == Items.f_42651_) {
         return 1;
      } else if (!stack.m_41619_() && stack.m_41720_() == Items.f_42652_) {
         return 2;
      } else {
         return !stack.m_41619_() && stack.m_41720_() == Items.f_42653_ ? 3 : 0;
      }
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level level) {
      return new AmphibiousPathNavigation(this, level);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, 1.0D);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new AquaticAIFindWaterTarget(this, 10, true));
      this.f_21345_.m_25352_(2, new AquaticAIGetInWater(this, 1.0D));
      this.f_21345_.m_25352_(3, new HippocampusAIWander(this, 1.0D));
      this.f_21345_.m_25352_(4, new BreedGoal(this, 1.0D));
      this.addBehaviourGoals();
   }

   protected void addBehaviourGoals() {
      this.f_21345_.m_25352_(0, new TemptGoal(this, 1.0D, Ingredient.m_204132_(IafItemTags.TEMPT_HIPPOCAMPUS), false));
   }

   public int m_213860_() {
      return 2;
   }

   public float m_21692_(BlockPos pos) {
      return this.m_9236_().m_8055_(pos.m_7495_()).m_60713_(Blocks.f_49990_) ? 10.0F : (float)this.m_9236_().m_46803_(pos) - 0.5F;
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21644_;
   }

   public boolean isPushedByFluid(FluidType fluid) {
      return false;
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

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(ARMOR, 0);
      this.f_19804_.m_135372_(SADDLE, Boolean.FALSE);
      this.f_19804_.m_135372_(CHESTED, Boolean.FALSE);
      this.f_19804_.m_135372_(CONTROL_STATE, (byte)0);
   }

   @Nullable
   public LivingEntity m_6688_() {
      Entity entity = this.m_146895_();
      if (entity instanceof Mob) {
         return (Mob)entity;
      } else {
         if (this.m_6254_()) {
            entity = this.m_146895_();
            if (entity instanceof Player) {
               return (Player)entity;
            }
         }

         return null;
      }
   }

   @NotNull
   public ItemStack m_255207_(@Nullable ItemStack itemStackIn) {
      if (itemStackIn == null) {
         return ItemStack.f_41583_;
      } else {
         EquipmentSlot equipmentSlot = m_147233_(itemStackIn);
         int j = equipmentSlot.m_20749_() - 500 + 2;
         if (j >= 0 && j < this.inventory.m_6643_()) {
            this.inventory.m_6836_(j, itemStackIn);
            return itemStackIn;
         } else {
            return ItemStack.f_41583_;
         }
      }
   }

   protected void m_5907_() {
      super.m_5907_();
      if (this.inventory != null && !this.m_9236_().f_46443_) {
         for(int i = 0; i < this.inventory.m_6643_(); ++i) {
            ItemStack itemstack = this.inventory.m_8020_(i);
            if (!itemstack.m_41619_() && !EnchantmentHelper.m_44924_(itemstack)) {
               this.m_19983_(itemstack);
            }
         }
      }

      if (this.isChested()) {
         if (!this.m_9236_().f_46443_) {
            this.m_19998_(Blocks.f_50087_);
         }

         this.setChested(false);
      }

   }

   protected void dropChestItems() {
      for(int i = 3; i < 18; ++i) {
         if (!this.inventory.m_8020_(i).m_41619_()) {
            if (!this.m_9236_().f_46443_) {
               this.m_5552_(this.inventory.m_8020_(i), 1.0F);
            }

            this.inventory.m_8016_(i);
         }
      }

   }

   private void updateControlState(int i, boolean newState) {
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

   public boolean m_7341_(@NotNull Entity rider) {
      return true;
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         this.f_20883_ = this.m_146908_();
         this.m_5618_(passenger.m_146908_());
      }

      double ymod1 = (double)this.onLandProgress * -0.02D;
      passenger.m_6034_(this.m_20185_(), this.m_20186_() + 0.6000000238418579D + ymod1, this.m_20189_());
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.m_9236_().f_46443_ && this.f_19796_.m_188503_(900) == 0 && this.f_20919_ == 0) {
         this.m_5634_(1.0F);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.m_6688_() != null && this.f_19797_ % 20 == 0) {
         this.m_6688_().m_7292_(new MobEffectInstance(MobEffects.f_19608_, 30, 0, true, false));
      }

      if (this.m_9236_().f_46443_) {
         this.tail_buffer.calculateChainSwingBuffer(40.0F, 10, 1.0F, this);
      }

      boolean inWater = this.m_20069_();
      if (!inWater && this.onLandProgress < 20.0F) {
         ++this.onLandProgress;
      } else if (inWater && this.onLandProgress > 0.0F) {
         --this.onLandProgress;
      }

      boolean sitting = this.m_21827_();
      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }

   }

   protected void m_274498_(@NotNull Player player, @NotNull Vec3 travelVector) {
      super.m_274498_(player, travelVector);
      Vec2 vec2 = this.getRiddenRotation(player);
      this.m_19915_(vec2.f_82471_, vec2.f_82470_);
      this.f_19859_ = this.f_20883_ = this.f_20885_ = this.m_146908_();
      if (this.m_6109_()) {
         Vec3 vec3 = this.m_20184_();
         if (this.isGoingUp()) {
            if (!this.m_20069_() && this.m_20096_()) {
               this.m_6135_();
               ForgeHooks.onLivingJump(this);
            } else if (this.m_20069_()) {
               this.m_20256_(vec3.m_82520_(0.0D, 0.03999999910593033D, 0.0D));
            }
         }

         if (this.isGoingDown() && this.m_20069_()) {
            this.m_20256_(vec3.m_82520_(0.0D, -0.02500000037252903D, 0.0D));
         }
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

   protected float m_245547_(@NotNull Player player) {
      float speed = (float)this.m_21133_(Attributes.f_22279_) * 0.6F;
      if (this.m_20069_()) {
         speed *= (float)IafConfig.hippocampusSwimSpeedMod;
      } else {
         speed *= 0.2F;
      }

      return speed;
   }

   public boolean isGoingUp() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) & 1) == 1;
   }

   public boolean isGoingDown() {
      return ((Byte)this.f_19804_.m_135370_(CONTROL_STATE) >> 1 & 1) == 1;
   }

   public boolean isBlinking() {
      return this.f_19797_ % 50 > 43;
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128379_("Chested", this.isChested());
      compound.m_128379_("Saddled", this.m_6254_());
      compound.m_128405_("Armor", this.getArmor());
      ListTag nbttaglist = new ListTag();

      for(int i = 0; i < this.inventory.m_6643_(); ++i) {
         ItemStack itemstack = this.inventory.m_8020_(i);
         if (!itemstack.m_41619_()) {
            CompoundTag CompoundNBT = new CompoundTag();
            CompoundNBT.m_128344_("Slot", (byte)i);
            itemstack.m_41739_(CompoundNBT);
            nbttaglist.add(CompoundNBT);
         }
      }

      compound.m_128365_("Items", nbttaglist);
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.setChested(compound.m_128471_("Chested"));
      this.setSaddled(compound.m_128471_("Saddled"));
      this.setArmor(compound.m_128451_("Armor"));
      if (this.inventory != null) {
         ListTag nbttaglist = compound.m_128437_("Items", 10);
         this.createInventory();

         for(int i = 0; i < nbttaglist.size(); ++i) {
            CompoundTag CompoundNBT = nbttaglist.m_128728_(i);
            int j = CompoundNBT.m_128445_("Slot") & 255;
            this.inventory.m_6836_(j, ItemStack.m_41712_(CompoundNBT));
         }
      }

   }

   protected int getInventorySize() {
      return this.isChested() ? 18 : 3;
   }

   protected void createInventory() {
      SimpleContainer simplecontainer = this.inventory;
      this.inventory = new SimpleContainer(this.getInventorySize());
      if (simplecontainer != null) {
         simplecontainer.m_19181_(this);
         int i = Math.min(simplecontainer.m_6643_(), this.inventory.m_6643_());

         for(int j = 0; j < i; ++j) {
            ItemStack itemstack = simplecontainer.m_8020_(j);
            if (!itemstack.m_41619_()) {
               this.inventory.m_6836_(j, itemstack.m_41777_());
            }
         }
      }

      this.inventory.m_19164_(this);
      this.updateContainerEquipment();
      this.itemHandler = LazyOptional.of(() -> {
         return new InvWrapper(this.inventory);
      });
   }

   protected void updateContainerEquipment() {
      if (!this.m_9236_().f_46443_) {
         this.setSaddled(!this.inventory.m_8020_(0).m_41619_());
         this.setChested(!this.inventory.m_8020_(1).m_41619_());
         this.setArmor(getIntFromArmor(this.inventory.m_8020_(2)));
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
      return this.inventory != pInventory;
   }

   public boolean m_6741_() {
      return this.m_6084_() && !this.m_6162_() && this.m_21824_();
   }

   public void m_5853_(@Nullable SoundSource pSource) {
      this.inventory.m_6836_(0, new ItemStack(Items.f_42450_));
   }

   public boolean m_6254_() {
      return (Boolean)this.f_19804_.m_135370_(SADDLE);
   }

   public void setSaddled(boolean saddle) {
      this.f_19804_.m_135381_(SADDLE, saddle);
   }

   public boolean isChested() {
      return (Boolean)this.f_19804_.m_135370_(CHESTED);
   }

   public void setChested(boolean chested) {
      this.f_19804_.m_135381_(CHESTED, chested);
      if (!chested) {
         this.dropChestItems();
      }

   }

   public int getArmor() {
      return (Integer)this.f_19804_.m_135370_(ARMOR);
   }

   public void setArmor(int armorType) {
      this.f_19804_.m_135381_(ARMOR, armorType);
      double var10000;
      switch(armorType) {
      case 1:
         var10000 = 10.0D;
         break;
      case 2:
         var10000 = 20.0D;
         break;
      case 3:
         var10000 = 30.0D;
         break;
      default:
         var10000 = 0.0D;
      }

      double armorValue = var10000;
      this.m_21051_(Attributes.f_22284_).m_22100_(armorValue);
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setVariant(this.m_217043_().m_188503_(6));
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
      return new Animation[]{IAnimatedEntity.NO_ANIMATION, ANIMATION_SPEAK};
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      if (ageable instanceof EntityHippocampus) {
         EntityHippocampus hippo = new EntityHippocampus((EntityType)IafEntityRegistry.HIPPOCAMPUS.get(), this.m_9236_());
         hippo.setVariant(this.m_217043_().m_188499_() ? this.getVariant() : ((EntityHippocampus)ageable).getVariant());
         return hippo;
      } else {
         return null;
      }
   }

   public boolean canDrownInFluidType(FluidType type) {
      return false;
   }

   public void m_7023_(@NotNull Vec3 pTravelVector) {
      if (this.m_6109_() && this.m_20069_()) {
         this.m_19920_(0.1F, pTravelVector);
         this.m_6478_(MoverType.SELF, this.m_20184_());
         this.m_20256_(this.m_20184_().m_82490_(0.9D));
      } else {
         super.m_7023_(pTravelVector);
      }

   }

   public boolean m_6898_(ItemStack stack) {
      return stack.m_204117_(IafItemTags.BREED_HIPPOCAMPUS);
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

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      if (itemstack.m_204117_(IafItemTags.BREED_HIPPOCAMPUS) && this.m_146764_() == 0 && !this.m_27593_()) {
         this.m_21839_(false);
         this.m_27595_(player);
         this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
         if (!player.m_7500_()) {
            itemstack.m_41774_(1);
         }

         return InteractionResult.SUCCESS;
      } else if (!itemstack.m_204117_(IafItemTags.HEAL_HIPPOCAMPUS)) {
         if (this.m_21830_(player) && itemstack.m_41720_() == Items.f_42398_) {
            this.m_21839_(!this.m_21827_());
            return InteractionResult.SUCCESS;
         } else if (this.m_21830_(player) && itemstack.m_41619_() && player.m_6144_()) {
            this.openInventory(player);
            return InteractionResult.m_19078_(this.m_9236_().f_46443_);
         } else if (this.m_21830_(player) && this.m_6254_() && !this.m_6162_() && !player.m_20159_()) {
            this.doPlayerRide(player);
            return InteractionResult.SUCCESS;
         } else {
            return super.m_6071_(player, hand);
         }
      } else {
         int i;
         if (!this.m_9236_().f_46443_) {
            this.m_5634_(5.0F);
            this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);

            for(i = 0; i < 3; ++i) {
               this.m_9236_().m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, itemstack), this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
            }

            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }
         }

         if (!this.m_21824_() && this.m_217043_().m_188503_(3) == 0) {
            this.m_21828_(player);

            for(i = 0; i < 6; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123750_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   protected void doPlayerRide(Player pPlayer) {
      this.m_21839_(false);
      if (!this.m_9236_().f_46443_) {
         pPlayer.m_146922_(this.m_146908_());
         pPlayer.m_146926_(this.m_146909_());
         pPlayer.m_20329_(this);
      }

   }

   public void openInventory(Player player) {
      if (!this.m_9236_().f_46443_) {
         NetworkHooks.openScreen((ServerPlayer)player, this.getMenuProvider());
      }

      IceAndFire.PROXY.setReferencedMob(this);
   }

   public MenuProvider getMenuProvider() {
      return new SimpleMenuProvider((containerId, playerInventory, player) -> {
         return new HippocampusContainerMenu(containerId, this.inventory, playerInventory, this);
      }, CONTAINER_TITLE);
   }

   public void up(boolean up) {
      this.updateControlState(0, up);
   }

   public void down(boolean down) {
      this.updateControlState(1, down);
   }

   public void attack(boolean attack) {
   }

   public void strike(boolean strike) {
   }

   public void dismount(boolean dismount) {
      this.updateControlState(2, dismount);
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.HIPPOCAMPUS_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return IafSoundRegistry.HIPPOCAMPUS_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.HIPPOCAMPUS_DIE;
   }

   public boolean m_21532_() {
      return true;
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

   public int getInventoryColumns() {
      return 5;
   }

   public void m_5757_(@NotNull Container pInvBasic) {
      boolean flag = this.m_6254_();
      this.updateContainerEquipment();
      if (this.f_19797_ > 20 && !flag && this.m_6254_()) {
         this.m_5496_(SoundEvents.f_12034_, 0.5F, 1.0F);
      }

   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityHippocampus.class, EntityDataSerializers.f_135028_);
      SADDLE = SynchedEntityData.m_135353_(EntityHippocampus.class, EntityDataSerializers.f_135035_);
      ARMOR = SynchedEntityData.m_135353_(EntityHippocampus.class, EntityDataSerializers.f_135028_);
      CHESTED = SynchedEntityData.m_135353_(EntityHippocampus.class, EntityDataSerializers.f_135035_);
      CONTROL_STATE = SynchedEntityData.m_135353_(EntityHippocampus.class, EntityDataSerializers.f_135027_);
      CONTAINER_TITLE = Component.m_237115_("entity.iceandfire.hippocampus");
   }

   class HippoMoveControl extends MoveControl {
      private final EntityHippocampus hippo = EntityHippocampus.this;

      public HippoMoveControl(EntityHippocampus entityHippocampus) {
         super(entityHippocampus);
      }

      private void updateSpeed() {
         if (this.hippo.m_20069_()) {
            this.hippo.m_20256_(this.hippo.m_20184_().m_82520_(0.0D, 0.005D, 0.0D));
         } else if (this.hippo.m_20096_()) {
            this.hippo.m_7910_(Math.max(this.hippo.m_6113_() / 4.0F, 0.06F));
         }

      }

      public void m_8126_() {
         this.updateSpeed();
         if (this.f_24981_ == Operation.MOVE_TO && !this.hippo.m_21573_().m_26571_()) {
            double d0 = this.f_24975_ - this.hippo.m_20185_();
            double d1 = this.f_24976_ - this.hippo.m_20186_();
            double d2 = this.f_24977_ - this.hippo.m_20189_();
            double distance = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            if (distance < 9.999999747378752E-6D) {
               this.f_24974_.m_7910_(0.0F);
            } else {
               d1 /= distance;
               float minRotation = (float)(Mth.m_14136_(d2, d0) * 57.2957763671875D) - 90.0F;
               this.hippo.m_146922_(this.m_24991_(this.hippo.m_146908_(), minRotation, 90.0F));
               this.hippo.f_20883_ = this.hippo.m_146908_();
               float maxSpeed = (float)(this.f_24978_ * this.hippo.m_21133_(Attributes.f_22279_));
               maxSpeed *= 0.6F;
               if (this.hippo.m_20069_()) {
                  maxSpeed *= (float)IafConfig.hippocampusSwimSpeedMod;
               } else {
                  maxSpeed *= 0.2F;
               }

               this.hippo.m_7910_(Mth.m_14179_(0.125F, this.hippo.m_6113_(), maxSpeed));
               this.hippo.m_20256_(this.hippo.m_20184_().m_82520_(0.0D, (double)this.hippo.m_6113_() * d1 * 0.1D, 0.0D));
            }
         } else {
            this.hippo.m_7910_(0.0F);
         }

      }
   }
}
