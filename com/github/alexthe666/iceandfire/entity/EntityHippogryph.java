package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.ai.HippogryphAIMate;
import com.github.alexthe666.iceandfire.entity.ai.HippogryphAITarget;
import com.github.alexthe666.iceandfire.entity.ai.HippogryphAITargetItems;
import com.github.alexthe666.iceandfire.entity.ai.HippogryphAIWander;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.ICustomMoveController;
import com.github.alexthe666.iceandfire.entity.util.IDragonFlute;
import com.github.alexthe666.iceandfire.entity.util.IDropArmor;
import com.github.alexthe666.iceandfire.entity.util.IFlyingMount;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.ISyncMount;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumHippogryphTypes;
import com.github.alexthe666.iceandfire.inventory.ContainerHippogryph;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.message.MessageHippogryphArmor;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
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
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityHippogryph extends TamableAnimal implements ISyncMount, IAnimatedEntity, IDragonFlute, IVillagerFear, IAnimalFear, IDropArmor, IFlyingMount, ICustomMoveController, IHasCustomizableAttributes {
   private static final int FLIGHT_CHANCE_PER_TICK = 1200;
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Boolean> SADDLE;
   private static final EntityDataAccessor<Integer> ARMOR;
   private static final EntityDataAccessor<Boolean> CHESTED;
   private static final EntityDataAccessor<Boolean> HOVERING;
   private static final EntityDataAccessor<Boolean> FLYING;
   private static final EntityDataAccessor<Byte> CONTROL_STATE;
   private static final EntityDataAccessor<Integer> COMMAND;
   public static Animation ANIMATION_EAT;
   public static Animation ANIMATION_SPEAK;
   public static Animation ANIMATION_SCRATCH;
   public static Animation ANIMATION_BITE;
   public SimpleContainer hippogryphInventory;
   public float sitProgress;
   public float hoverProgress;
   public float flyProgress;
   public int spacebarTicks;
   public int airBorneCounter;
   public BlockPos homePos;
   public boolean hasHomePosition = false;
   public int feedings = 0;
   private boolean isLandNavigator;
   private boolean isSitting;
   private boolean isHovering;
   private boolean isFlying;
   private int animationTick;
   private Animation currentAnimation;
   private int flyTicks;
   private int hoverTicks;
   private boolean hasChestVarChanged = false;
   private boolean isOverAir;

   public EntityHippogryph(EntityType<? extends TamableAnimal> type, Level worldIn) {
      super(type, worldIn);
      this.switchNavigator(true);
      ANIMATION_EAT = Animation.create(25);
      ANIMATION_SPEAK = Animation.create(15);
      ANIMATION_SCRATCH = Animation.create(25);
      ANIMATION_BITE = Animation.create(20);
      this.initHippogryphInv();
      this.m_274367_(1.0F);
   }

   public static int getIntFromArmor(ItemStack stack) {
      if (!stack.m_41619_() && stack.m_41720_() == IafItemRegistry.IRON_HIPPOGRYPH_ARMOR.get()) {
         return 1;
      } else if (!stack.m_41619_() && stack.m_41720_() == IafItemRegistry.GOLD_HIPPOGRYPH_ARMOR.get()) {
         return 2;
      } else {
         return !stack.m_41619_() && stack.m_41720_() == IafItemRegistry.DIAMOND_HIPPOGRYPH_ARMOR.get() ? 3 : 0;
      }
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22280_, IafConfig.hippogryphFlightSpeedMod).m_22268_(Attributes.f_22281_, 5.0D).m_22268_(Attributes.f_22277_, 32.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22280_).m_22100_(IafConfig.hippogryphFlightSpeedMod);
   }

   protected boolean isOverAir() {
      return this.isOverAir;
   }

   private boolean isOverAirLogic() {
      return this.m_9236_().m_46859_(BlockPos.m_274561_((double)this.m_146903_(), this.m_20191_().f_82289_ - 1.0D, (double)this.m_146907_()));
   }

   public int m_213860_() {
      return 10;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new MeleeAttackGoal(this, 1.2D, true));
      this.f_21345_.m_25352_(4, new HippogryphAIMate(this, 1.0D));
      this.f_21345_.m_25352_(5, new TemptGoal(this, 1.0D, Ingredient.m_204132_(IafItemTags.TEMPT_HIPPOGRYPH), false));
      this.f_21345_.m_25352_(6, new WaterAvoidingRandomFlyingGoal(this, 1.0D));
      this.f_21345_.m_25352_(7, new HippogryphAIWander(this, 1.0D));
      this.f_21345_.m_25352_(8, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(2, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new HippogryphAITargetItems(this, false));
      this.f_21346_.m_25352_(5, new HippogryphAITarget(this, LivingEntity.class, false, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity instanceof LivingEntity && !(entity instanceof AbstractHorse) && DragonUtils.isAlive((LivingEntity)entity);
         }
      }));
      this.f_21346_.m_25352_(5, new HippogryphAITarget(this, Player.class, 350, false, new Predicate<Player>() {
         public boolean apply(@Nullable Player entity) {
            return !entity.m_7500_();
         }
      }));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(ARMOR, 0);
      this.f_19804_.m_135372_(SADDLE, Boolean.FALSE);
      this.f_19804_.m_135372_(CHESTED, Boolean.FALSE);
      this.f_19804_.m_135372_(HOVERING, Boolean.FALSE);
      this.f_19804_.m_135372_(FLYING, Boolean.FALSE);
      this.f_19804_.m_135372_(CONTROL_STATE, (byte)0);
      this.f_19804_.m_135372_(COMMAND, 0);
   }

   public double getYSpeedMod() {
      return 4.0D;
   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         this.f_20883_ = this.m_146908_();
         this.m_5616_(passenger.m_6080_());
         this.m_5618_(passenger.m_146908_());
      }

      passenger.m_6034_(this.m_20185_(), this.m_20186_() + 1.0499999523162842D, this.m_20189_());
   }

   private void initHippogryphInv() {
      SimpleContainer animalchest = this.hippogryphInventory;
      this.hippogryphInventory = new SimpleContainer(18);
      if (animalchest != null) {
         int i = Math.min(animalchest.m_6643_(), this.hippogryphInventory.m_6643_());

         ItemStack chest;
         for(int j = 0; j < i; ++j) {
            chest = animalchest.m_8020_(j);
            if (!chest.m_41619_()) {
               this.hippogryphInventory.m_6836_(j, chest.m_41777_());
            }
         }

         if (this.m_9236_().f_46443_) {
            ItemStack saddle = animalchest.m_8020_(0);
            chest = animalchest.m_8020_(1);
            IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageHippogryphArmor(this.m_19879_(), 0, saddle != null && saddle.m_41720_() == Items.f_42450_ && !saddle.m_41619_() ? 1 : 0));
            IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageHippogryphArmor(this.m_19879_(), 1, chest != null && chest.m_41720_() == Blocks.f_50087_.m_5456_() && !chest.m_41619_() ? 1 : 0));
            IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageHippogryphArmor(this.m_19879_(), 2, getIntFromArmor(animalchest.m_8020_(2))));
         }
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

   public boolean isBlinking() {
      return this.f_19797_ % 50 > 43;
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack itemstack = player.m_21120_(hand);
      String s = ChatFormatting.m_126649_(player.m_7755_().getString());
      boolean isDev = s.equals("Alexthe666") || s.equals("Raptorfarian") || s.equals("tweakbsd");
      if (this.m_21824_() && this.m_21830_(player)) {
         int i;
         if (itemstack.m_41720_() == Items.f_42497_ && this.getEnumVariant() != EnumHippogryphTypes.ALEX && isDev) {
            this.setEnumVariant(EnumHippogryphTypes.ALEX);
            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }

            this.m_5496_(SoundEvents.f_12609_, 1.0F, 1.0F);

            for(i = 0; i < 20; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123796_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
            }

            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_41720_() == Items.f_42491_ && this.getEnumVariant() != EnumHippogryphTypes.RAPTOR && isDev) {
            this.setEnumVariant(EnumHippogryphTypes.RAPTOR);
            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }

            this.m_5496_(SoundEvents.f_12609_, 1.0F, 1.0F);

            for(i = 0; i < 20; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123796_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
            }

            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_204117_(IafItemTags.BREED_HIPPOGRYPH) && this.m_146764_() == 0 && !this.m_27593_()) {
            this.m_27595_(player);
            this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }

            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_41720_() == Items.f_42398_) {
            if (player.m_6144_()) {
               if (this.hasHomePosition) {
                  this.hasHomePosition = false;
                  player.m_5661_(Component.m_237115_("hippogryph.command.remove_home"), true);
                  return InteractionResult.SUCCESS;
               }

               this.homePos = this.m_20183_();
               this.hasHomePosition = true;
               player.m_5661_(Component.m_237110_("hippogryph.command.new_home", new Object[]{this.homePos.m_123341_(), this.homePos.m_123342_(), this.homePos.m_123343_()}), true);
               return InteractionResult.SUCCESS;
            }

            this.setCommand(this.getCommand() + 1);
            if (this.getCommand() > 1) {
               this.setCommand(0);
            }

            int var10001 = this.getCommand();
            player.m_5661_(Component.m_237115_("hippogryph.command." + (var10001 == 1 ? "sit" : "stand")), true);
            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_41720_() == Items.f_42546_ && this.getEnumVariant() != EnumHippogryphTypes.DODO) {
            this.setEnumVariant(EnumHippogryphTypes.DODO);
            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }

            this.m_5496_(SoundEvents.f_12609_, 1.0F, 1.0F);

            for(i = 0; i < 20; ++i) {
               this.m_9236_().m_7106_(ParticleTypes.f_123809_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
            }

            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_41720_().m_41472_() && itemstack.m_41720_().m_41473_() != null && itemstack.m_41720_().m_41473_().m_38746_() && this.m_21223_() < this.m_21233_()) {
            this.m_5634_(5.0F);
            this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);

            for(i = 0; i < 3; ++i) {
               this.m_9236_().m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, itemstack), this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), 0.0D, 0.0D, 0.0D);
            }

            if (!player.m_7500_()) {
               itemstack.m_41774_(1);
            }

            return InteractionResult.SUCCESS;
         }

         if (itemstack.m_41619_()) {
            if (player.m_6144_()) {
               this.openGUI(player);
               return InteractionResult.SUCCESS;
            }

            if (this.isSaddled() && !this.m_6162_() && !player.m_20159_()) {
               player.m_7998_(this, true);
               return InteractionResult.SUCCESS;
            }
         }
      }

      return super.m_6071_(player, hand);
   }

   public void openGUI(Player playerEntity) {
      if (!this.m_9236_().f_46443_ && (!this.m_20160_() || this.m_20363_(playerEntity))) {
         NetworkHooks.openScreen((ServerPlayer)playerEntity, new MenuProvider() {
            public AbstractContainerMenu m_7208_(int p_createMenu_1_, @NotNull Inventory p_createMenu_2_, @NotNull Player p_createMenu_3_) {
               return new ContainerHippogryph(p_createMenu_1_, EntityHippogryph.this.hippogryphInventory, p_createMenu_2_, EntityHippogryph.this);
            }

            @NotNull
            public Component m_5446_() {
               return Component.m_237115_("entity.iceandfire.hippogryph");
            }
         });
      }

      IceAndFire.PROXY.setReferencedMob(this);
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

   public int getCommand() {
      return (Integer)this.f_19804_.m_135370_(COMMAND);
   }

   public void setCommand(int command) {
      this.f_19804_.m_135381_(COMMAND, command);
      this.m_21839_(command == 1);
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128379_("Chested", this.isChested());
      compound.m_128379_("Saddled", this.isSaddled());
      compound.m_128379_("Hovering", this.isHovering());
      compound.m_128379_("Flying", this.isFlying());
      compound.m_128405_("Armor", this.getArmor());
      compound.m_128405_("Feedings", this.feedings);
      if (this.hippogryphInventory != null) {
         ListTag nbttaglist = new ListTag();

         for(int i = 0; i < this.hippogryphInventory.m_6643_(); ++i) {
            ItemStack itemstack = this.hippogryphInventory.m_8020_(i);
            if (!itemstack.m_41619_()) {
               CompoundTag CompoundNBT = new CompoundTag();
               CompoundNBT.m_128344_("Slot", (byte)i);
               itemstack.m_41739_(CompoundNBT);
               nbttaglist.add(CompoundNBT);
            }
         }

         compound.m_128365_("Items", nbttaglist);
      }

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
      this.setChested(compound.m_128471_("Chested"));
      this.setSaddled(compound.m_128471_("Saddled"));
      this.setHovering(compound.m_128471_("Hovering"));
      this.setFlying(compound.m_128471_("Flying"));
      this.setArmor(compound.m_128451_("Armor"));
      this.feedings = compound.m_128451_("Feedings");
      ListTag nbttaglist;
      int i;
      CompoundTag CompoundNBT;
      int j;
      if (this.hippogryphInventory != null) {
         nbttaglist = compound.m_128437_("Items", 10);
         this.initHippogryphInv();

         for(i = 0; i < nbttaglist.size(); ++i) {
            CompoundNBT = nbttaglist.m_128728_(i);
            j = CompoundNBT.m_128445_("Slot") & 255;
            this.hippogryphInventory.m_6836_(j, ItemStack.m_41712_(CompoundNBT));
         }
      } else {
         nbttaglist = compound.m_128437_("Items", 10);
         this.initHippogryphInv();

         for(i = 0; i < nbttaglist.size(); ++i) {
            CompoundNBT = nbttaglist.m_128728_(i);
            j = CompoundNBT.m_128445_("Slot") & 255;
            this.initHippogryphInv();
            this.hippogryphInventory.m_6836_(j, ItemStack.m_41712_(CompoundNBT));
            ItemStack saddle = this.hippogryphInventory.m_8020_(0);
            ItemStack chest = this.hippogryphInventory.m_8020_(1);
            if (this.m_9236_().f_46443_) {
               IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageHippogryphArmor(this.m_19879_(), 0, saddle != null && saddle.m_41720_() == Items.f_42450_ && !saddle.m_41619_() ? 1 : 0));
               IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageHippogryphArmor(this.m_19879_(), 1, chest != null && chest.m_41720_() == Blocks.f_50087_.m_5456_() && !chest.m_41619_() ? 1 : 0));
               IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageHippogryphArmor(this.m_19879_(), 2, getIntFromArmor(this.hippogryphInventory.m_8020_(2))));
            }
         }
      }

      this.hasHomePosition = compound.m_128471_("HasHomePosition");
      if (this.hasHomePosition && compound.m_128451_("HomeAreaX") != 0 && compound.m_128451_("HomeAreaY") != 0 && compound.m_128451_("HomeAreaZ") != 0) {
         this.homePos = new BlockPos(compound.m_128451_("HomeAreaX"), compound.m_128451_("HomeAreaY"), compound.m_128451_("HomeAreaZ"));
      }

      this.setCommand(compound.m_128451_("Command"));
      if (this.m_21827_()) {
         this.sitProgress = 20.0F;
      }

      this.setConfigurableAttributes();
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public EnumHippogryphTypes getEnumVariant() {
      return EnumHippogryphTypes.values()[this.getVariant()];
   }

   public void setEnumVariant(EnumHippogryphTypes variant) {
      this.setVariant(variant.ordinal());
   }

   public boolean isSaddled() {
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
      this.hasChestVarChanged = true;
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

   public boolean isHovering() {
      return this.m_9236_().f_46443_ ? (this.isHovering = (Boolean)this.f_19804_.m_135370_(HOVERING)) : this.isHovering;
   }

   public void setHovering(boolean hovering) {
      this.f_19804_.m_135381_(HOVERING, hovering);
      if (!this.m_9236_().f_46443_) {
         this.isHovering = hovering;
      }

   }

   @Nullable
   public Player getRidingPlayer() {
      return this.m_6688_() instanceof Player ? (Player)this.m_6688_() : null;
   }

   public double getFlightSpeedModifier() {
      return IafConfig.hippogryphFlightSpeedMod * 0.8999999761581421D;
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

   public boolean canMove() {
      return !this.m_21827_() && this.m_6688_() == null && this.sitProgress == 0.0F;
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      SpawnGroupData data = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setEnumVariant(EnumHippogryphTypes.getBiomeType(worldIn.m_204166_(this.m_20183_())));
      return data;
   }

   public boolean m_6469_(@NotNull DamageSource dmg, float i) {
      return this.m_20160_() && dmg.m_7639_() != null && this.m_6688_() != null && dmg.m_7639_() == this.m_6688_() ? false : super.m_6469_(dmg, i);
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

   protected float m_245547_(@NotNull Player pPlayer) {
      return !this.isFlying() && !this.isHovering() ? (float)this.m_21133_(Attributes.f_22279_) * 0.75F : (float)this.m_21133_(Attributes.f_22280_);
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.HIPPOGRYPH_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return IafSoundRegistry.HIPPOGRYPH_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.HIPPOGRYPH_DIE;
   }

   public Animation[] getAnimations() {
      return new Animation[]{IAnimatedEntity.NO_ANIMATION, ANIMATION_EAT, ANIMATION_BITE, ANIMATION_SPEAK, ANIMATION_SCRATCH};
   }

   public void m_7023_(@NotNull Vec3 pTravelVector) {
      if (this.m_6109_()) {
         if (this.m_20069_()) {
            this.m_19920_(0.02F, pTravelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.800000011920929D));
         } else if (this.m_20077_()) {
            this.m_19920_(0.02F, pTravelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.5D));
         } else if (!this.isFlying() && !this.isHovering()) {
            super.m_7023_(pTravelVector);
         } else {
            this.m_19920_(0.1F, pTravelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9D));
         }
      } else {
         super.m_7023_(pTravelVector);
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

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getAnimation() != ANIMATION_SCRATCH && this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(this.m_217043_().m_188499_() ? ANIMATION_SCRATCH : ANIMATION_BITE);
         return false;
      } else {
         return true;
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

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

         if (this.f_19796_.m_188503_(900) == 0 && this.f_20919_ == 0) {
            this.m_5634_(1.0F);
         }
      }

      if (this.getAnimation() == ANIMATION_BITE && this.m_5448_() != null && this.getAnimationTick() == 6) {
         double dist = this.m_20280_(this.m_5448_());
         if (dist < 8.0D) {
            this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      LivingEntity attackTarget = this.m_5448_();
      if (this.getAnimation() == ANIMATION_SCRATCH && attackTarget != null && this.getAnimationTick() == 6) {
         double dist = this.m_20280_(attackTarget);
         if (dist < 8.0D) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            attackTarget.f_19812_ = true;
            float f = Mth.m_14116_(0.5F);
            attackTarget.m_20256_(attackTarget.m_20184_().m_82520_(-0.5D / (double)f, 1.0D, -0.5D / (double)f));
            attackTarget.m_20256_(attackTarget.m_20184_().m_82542_(0.5D, 1.0D, 0.5D));
            if (attackTarget.m_20096_()) {
               attackTarget.m_20256_(attackTarget.m_20184_().m_82520_(0.0D, 0.3D, 0.0D));
            }
         }
      }

      if (!this.m_9236_().f_46443_ && !this.isOverAir() && this.m_21573_().m_26571_() && attackTarget != null && attackTarget.m_20186_() - 3.0D > this.m_20186_() && this.m_217043_().m_188503_(15) == 0 && this.canMove() && !this.isHovering() && !this.isFlying()) {
         this.setHovering(true);
         this.hoverTicks = 0;
         this.flyTicks = 0;
      }

      if (this.isOverAir()) {
         ++this.airBorneCounter;
      } else {
         this.airBorneCounter = 0;
      }

      if (this.hasChestVarChanged && this.hippogryphInventory != null && !this.isChested()) {
         for(int i = 3; i < 18; ++i) {
            if (!this.hippogryphInventory.m_8020_(i).m_41619_()) {
               if (!this.m_9236_().f_46443_) {
                  this.m_5552_(this.hippogryphInventory.m_8020_(i), 1.0F);
               }

               this.hippogryphInventory.m_8016_(i);
            }
         }

         this.hasChestVarChanged = false;
      }

      if (this.isFlying() && this.f_19797_ % 40 == 0 || this.isFlying() && this.m_21827_()) {
         this.setFlying(true);
      }

      if (!this.canMove() && attackTarget != null) {
         this.m_6710_((LivingEntity)null);
      }

      if (!this.canMove()) {
         this.m_21573_().m_26573_();
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      boolean sitting = this.m_21827_() && !this.isHovering() && !this.isFlying();
      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }

      boolean hovering = this.isHovering();
      if (hovering && this.hoverProgress < 20.0F) {
         this.hoverProgress += 0.5F;
      } else if (!hovering && this.hoverProgress > 0.0F) {
         this.hoverProgress -= 0.5F;
      }

      boolean flying = this.isFlying() || this.isHovering() && this.airBorneCounter > 10;
      if (flying && this.flyProgress < 20.0F) {
         this.flyProgress += 0.5F;
      } else if (!flying && this.flyProgress > 0.0F) {
         this.flyProgress -= 0.5F;
      }

      if (flying && this.isLandNavigator) {
         this.switchNavigator(false);
      }

      if (!flying && !this.isLandNavigator) {
         this.switchNavigator(true);
      }

      if ((flying || hovering) && !this.doesWantToLand() && this.m_6688_() == null) {
         double up = this.m_20069_() ? 0.16D : 0.08D;
         this.m_20256_(this.m_20184_().m_82520_(0.0D, up, 0.0D));
      }

      if ((flying || hovering) && this.f_19797_ % 20 == 0 && this.isOverAir()) {
         this.m_5496_(SoundEvents.f_11893_, this.m_6121_() * (float)(IafConfig.dragonFlapNoiseDistance / 2), 0.6F + this.f_19796_.m_188501_() * 0.6F * this.m_6100_());
      }

      if (this.m_20096_() && this.doesWantToLand() && (this.isFlying() || this.isHovering())) {
         this.setFlying(false);
         this.setHovering(false);
      }

      if (this.isHovering()) {
         if (this.m_21827_()) {
            this.setHovering(false);
         }

         ++this.hoverTicks;
         if (this.doesWantToLand()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.05D, 0.0D));
         } else {
            if (this.m_6688_() == null) {
               this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.08D, 0.0D));
            }

            if (this.hoverTicks > 40) {
               if (!this.m_6162_()) {
                  this.setFlying(true);
               }

               this.setHovering(false);
               this.hoverTicks = 0;
               this.flyTicks = 0;
            }
         }
      }

      if (this.m_21827_()) {
         this.m_21573_().m_26573_();
      }

      if (this.m_20096_() && this.flyTicks != 0) {
         this.flyTicks = 0;
      }

      if (this.isFlying() && this.doesWantToLand() && this.m_6688_() == null) {
         this.setHovering(false);
         if (this.m_20096_()) {
            this.flyTicks = 0;
         }

         this.setFlying(false);
      }

      if (this.isFlying()) {
         ++this.flyTicks;
      }

      if ((this.isHovering() || this.isFlying()) && this.m_21827_()) {
         this.setFlying(false);
         this.setHovering(false);
      }

      if (this.m_20160_() && this.isGoingDown() && this.m_20096_()) {
         this.setHovering(false);
         this.setFlying(false);
      }

      if (!this.m_9236_().f_46443_ && this.m_217043_().m_188503_(1200) == 0 && !this.m_21827_() && !this.isFlying() && this.m_20197_().isEmpty() && !this.m_6162_() && !this.isHovering() && !this.m_21827_() && this.canMove() && !this.isOverAir() || this.m_20186_() < -1.0D) {
         this.setHovering(true);
         this.hoverTicks = 0;
         this.flyTicks = 0;
      }

      if (this.m_5448_() != null && !this.m_20197_().isEmpty() && this.m_269323_() != null && this.m_20197_().contains(this.m_269323_())) {
         this.m_6710_((LivingEntity)null);
      }

   }

   public boolean doesWantToLand() {
      return (this.flyTicks > 200 || this.flyTicks > 40 && this.flyProgress == 0.0F) && !this.m_20160_();
   }

   public void m_8119_() {
      super.m_8119_();
      this.isOverAir = this.isOverAirLogic();
      if (this.isGoingUp()) {
         if (this.airBorneCounter == 0) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.019999999552965164D, 0.0D));
         }

         if (!this.isFlying() && !this.isHovering()) {
            this.spacebarTicks += 2;
         }
      } else if (this.dismountIAF() && (this.isFlying() || this.isHovering())) {
         this.setFlying(false);
         this.setHovering(false);
      }

      if (this.attack() && this.m_6688_() != null && this.m_6688_() instanceof Player) {
         LivingEntity target = DragonUtils.riderLookingAtEntity(this, (Player)this.m_6688_(), 3.0D);
         if (this.getAnimation() != ANIMATION_BITE && this.getAnimation() != ANIMATION_SCRATCH) {
            this.setAnimation(this.m_217043_().m_188499_() ? ANIMATION_SCRATCH : ANIMATION_BITE);
         }

         if (target != null && this.getAnimationTick() >= 10 && this.getAnimationTick() < 13) {
            target.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (this.m_6688_() != null && this.m_6688_().m_6144_()) {
         this.m_6688_().m_8127_();
      }

      double motion = this.m_20184_().f_82479_ * this.m_20184_().f_82479_ + this.m_20184_().f_82481_ * this.m_20184_().f_82481_;
      if (this.isFlying() && !this.isHovering() && this.m_6688_() != null && this.isOverAir() && motion < 0.009999999776482582D) {
         this.setHovering(true);
         this.setFlying(false);
      }

      if (this.isHovering() && !this.isFlying() && this.m_6688_() != null && this.isOverAir() && motion > 0.009999999776482582D) {
         this.setFlying(true);
         this.setHovering(false);
      }

      if (this.spacebarTicks > 0) {
         --this.spacebarTicks;
      }

      if (this.spacebarTicks > 10 && this.m_269323_() != null && this.m_20197_().contains(this.m_269323_()) && !this.isFlying() && !this.isHovering()) {
         this.setHovering(true);
      }

      if (this.m_5448_() != null && this.m_20202_() == null && !this.m_5448_().m_6084_() || this.m_5448_() != null && this.m_5448_() instanceof EntityDragonBase && !this.m_5448_().m_6084_()) {
         this.m_6710_((LivingEntity)null);
      }

   }

   public boolean isTargetBlocked(Vec3 target) {
      if (target != null) {
         BlockHitResult rayTrace = this.m_9236_().m_45547_(new ClipContext(this.m_20299_(1.0F), target, Block.COLLIDER, Fluid.NONE, this));
         BlockPos pos = rayTrace.m_82425_();
         return !this.m_9236_().m_46859_(pos);
      } else {
         return false;
      }
   }

   public float getDistanceSquared(Vec3 Vector3d) {
      float f = (float)(this.m_20185_() - Vector3d.f_82479_);
      float f1 = (float)(this.m_20186_() - Vector3d.f_82480_);
      float f2 = (float)(this.m_20189_() - Vector3d.f_82481_);
      return f * f + f1 * f1 + f2 * f2;
   }

   public void m_6667_(@NotNull DamageSource cause) {
      super.m_6667_(cause);
      if (this.hippogryphInventory != null && !this.m_9236_().f_46443_) {
         for(int i = 0; i < this.hippogryphInventory.m_6643_(); ++i) {
            ItemStack itemstack = this.hippogryphInventory.m_8020_(i);
            if (!itemstack.m_41619_()) {
               this.m_5552_(itemstack, 0.0F);
            }
         }
      }

   }

   public void refreshInventory() {
      if (!this.m_9236_().f_46443_) {
         ItemStack saddle = this.hippogryphInventory.m_8020_(0);
         ItemStack chest = this.hippogryphInventory.m_8020_(1);
         this.setSaddled(saddle.m_41720_() == Items.f_42450_ && !saddle.m_41619_());
         this.setChested(chest.m_41720_() == Blocks.f_50087_.m_5456_() && !chest.m_41619_());
         this.setArmor(getIntFromArmor(this.hippogryphInventory.m_8020_(2)));
      }

   }

   protected void switchNavigator(boolean onLand) {
      if (onLand) {
         this.f_21342_ = new MoveControl(this);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.CLIMBING);
         this.isLandNavigator = true;
      } else {
         this.f_21342_ = new FlyingMoveControl(this, 10, true);
         this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.FLYING);
         this.isLandNavigator = false;
      }

   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level worldIn) {
      return this.createNavigator(worldIn, AdvancedPathNavigate.MovementType.CLIMBING);
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type) {
      return this.createNavigator(worldIn, type, 2.0F, 2.0F);
   }

   protected PathNavigation createNavigator(Level worldIn, AdvancedPathNavigate.MovementType type, float width, float height) {
      AdvancedPathNavigate newNavigator = new AdvancedPathNavigate(this, this.m_9236_(), type, width, height);
      this.f_21344_ = newNavigator;
      newNavigator.m_7008_(true);
      newNavigator.m_26575_().m_77355_(true);
      return newNavigator;
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

   public void onHearFlute(Player player) {
      if (this.m_21824_() && this.m_21830_(player) && (this.isFlying() || this.isHovering())) {
         this.setFlying(false);
         this.setHovering(false);
      }

   }

   public boolean shouldAnimalsFear(Entity entity) {
      return DragonUtils.canTameDragonAttack(this, entity);
   }

   public void dropArmor() {
      if (this.hippogryphInventory != null && !this.m_9236_().f_46443_) {
         for(int i = 0; i < this.hippogryphInventory.m_6643_(); ++i) {
            ItemStack itemstack = this.hippogryphInventory.m_8020_(i);
            if (!itemstack.m_41619_()) {
               this.m_5552_(itemstack, 0.0F);
            }
         }
      }

   }

   public boolean m_21532_() {
      return true;
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135028_);
      SADDLE = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135035_);
      ARMOR = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135028_);
      CHESTED = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135035_);
      HOVERING = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135035_);
      FLYING = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135035_);
      CONTROL_STATE = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135027_);
      COMMAND = SynchedEntityData.m_135353_(EntityHippogryph.class, EntityDataSerializers.f_135028_);
   }
}
