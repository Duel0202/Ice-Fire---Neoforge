package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.ai.PixieAIEnterHouse;
import com.github.alexthe666.iceandfire.entity.ai.PixieAIFlee;
import com.github.alexthe666.iceandfire.entity.ai.PixieAIFollowOwner;
import com.github.alexthe666.iceandfire.entity.ai.PixieAIMoveRandom;
import com.github.alexthe666.iceandfire.entity.ai.PixieAIPickupItem;
import com.github.alexthe666.iceandfire.entity.ai.PixieAISteal;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouse;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.control.MoveControl.Operation;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EntityPixie extends TamableAnimal {
   public static final float[][] PARTICLE_RGB = new float[][]{{1.0F, 0.752F, 0.792F}, {0.831F, 0.662F, 1.0F}, {0.513F, 0.843F, 1.0F}, {0.654F, 0.909F, 0.615F}, {0.996F, 0.788F, 0.407F}};
   private static final EntityDataAccessor<Integer> COLOR;
   private static final EntityDataAccessor<Integer> COMMAND;
   public static final int STEAL_COOLDOWN = 3000;
   public MobEffect[] positivePotions;
   public MobEffect[] negativePotions;
   public boolean slowSpeed;
   public int ticksUntilHouseAI;
   public int ticksHeldItemFor;
   private BlockPos housePos;
   public int stealCooldown;
   private boolean isSitting;

   public EntityPixie(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.positivePotions = new MobEffect[]{MobEffects.f_19600_, MobEffects.f_19603_, MobEffects.f_19596_, MobEffects.f_19621_, MobEffects.f_19598_};
      this.negativePotions = new MobEffect[]{MobEffects.f_19613_, MobEffects.f_19604_, MobEffects.f_19597_, MobEffects.f_19590_, MobEffects.f_19599_};
      this.slowSpeed = false;
      this.stealCooldown = 0;
      this.f_21342_ = new EntityPixie.AIMoveControl(this);
      this.f_21364_ = 3;
      this.m_21409_(EquipmentSlot.MAINHAND, 0.0F);
   }

   public static BlockPos getPositionRelativetoGround(Entity entity, Level world, double x, double z, RandomSource rand) {
      BlockPos pos = BlockPos.m_274561_(x, (double)entity.m_146904_(), z);

      for(int yDown = 0; yDown < 3; ++yDown) {
         if (!world.m_46859_(pos.m_6625_(yDown))) {
            return pos.m_6630_(yDown);
         }
      }

      return pos;
   }

   public static BlockPos findAHouse(Entity entity, Level world) {
      for(int xSearch = -10; xSearch < 10; ++xSearch) {
         for(int ySearch = -10; ySearch < 10; ++ySearch) {
            for(int zSearch = -10; zSearch < 10; ++zSearch) {
               if (world.m_7702_(entity.m_20183_().m_7918_(xSearch, ySearch, zSearch)) != null) {
                  BlockEntity var6 = world.m_7702_(entity.m_20183_().m_7918_(xSearch, ySearch, zSearch));
                  if (var6 instanceof TileEntityPixieHouse) {
                     TileEntityPixieHouse house = (TileEntityPixieHouse)var6;
                     if (!house.hasPixie) {
                        return entity.m_20183_().m_7918_(xSearch, ySearch, zSearch);
                     }
                  }
               }
            }
         }
      }

      return entity.m_20183_();
   }

   public boolean isPixieSitting() {
      if (this.m_9236_().f_46443_) {
         boolean isSitting = ((Byte)this.f_19804_.m_135370_(f_21798_) & 1) != 0;
         this.isSitting = isSitting;
         this.m_21839_(isSitting);
         return isSitting;
      } else {
         return this.isSitting;
      }
   }

   public void setPixieSitting(boolean sitting) {
      if (!this.m_9236_().f_46443_) {
         this.isSitting = sitting;
         this.m_21837_(sitting);
      }

      byte b0 = (Byte)this.f_19804_.m_135370_(f_21798_);
      if (sitting) {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 | 1));
      } else {
         this.f_19804_.m_135381_(f_21798_, (byte)(b0 & -2));
      }

   }

   public boolean m_21827_() {
      return this.isPixieSitting();
   }

   public int m_213860_() {
      return 3;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, 0.25D);
   }

   public boolean m_6469_(@NotNull DamageSource source, float amount) {
      if (!this.m_9236_().f_46443_ && this.m_217043_().m_188503_(3) == 0 && !this.m_21120_(InteractionHand.MAIN_HAND).m_41619_()) {
         this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
         this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
         this.stealCooldown = 3000;
         return true;
      } else {
         return !this.isOwnerClose() || (source.m_7639_() == null || source != this.m_9236_().m_269111_().m_269564_(source.m_7639_())) && source != this.m_9236_().m_269111_().m_269318_() && (this.m_269323_() == null || source.m_7639_() != this.m_269323_()) ? super.m_6469_(source, amount) : false;
      }
   }

   public boolean m_6673_(@NotNull DamageSource source) {
      boolean invulnerable = super.m_6673_(source);
      if (!invulnerable) {
         Entity owner = this.m_269323_();
         if (owner != null && source.m_7639_() == owner) {
            return true;
         }
      }

      return invulnerable;
   }

   public void m_6667_(@NotNull DamageSource cause) {
      if (!this.m_9236_().f_46443_ && !this.m_21120_(InteractionHand.MAIN_HAND).m_41619_()) {
         this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
         this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
      }

      super.m_6667_(cause);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(COLOR, 0);
      this.f_19804_.m_135372_(COMMAND, 0);
   }

   protected void m_7324_(@NotNull Entity entityIn) {
      if (this.m_269323_() != entityIn) {
         entityIn.m_7334_(this);
      }

   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   @NotNull
   public InteractionResult m_6071_(@NotNull Player player, @NotNull InteractionHand hand) {
      if (this.m_21830_(player)) {
         if (player.m_21120_(hand).m_204117_(IafItemTags.HEAL_PIXIE) && this.m_21223_() < this.m_21233_()) {
            this.m_5634_(5.0F);
            player.m_21120_(hand).m_41774_(1);
            this.m_5496_(IafSoundRegistry.PIXIE_TAUNT, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
         } else {
            this.setCommand(this.getCommand() + 1);
            if (this.getCommand() > 1) {
               this.setCommand(0);
            }

            return InteractionResult.SUCCESS;
         }
      } else {
         if (player.m_21120_(hand).m_41720_() == ((Block)IafBlockRegistry.JAR_EMPTY.get()).m_5456_() && !this.m_21824_()) {
            if (!player.m_7500_()) {
               player.m_21120_(hand).m_41774_(1);
            }

            Block jar = (Block)IafBlockRegistry.JAR_PIXIE_0.get();
            switch(this.getColor()) {
            case 0:
               jar = (Block)IafBlockRegistry.JAR_PIXIE_0.get();
               break;
            case 1:
               jar = (Block)IafBlockRegistry.JAR_PIXIE_1.get();
               break;
            case 2:
               jar = (Block)IafBlockRegistry.JAR_PIXIE_2.get();
               break;
            case 3:
               jar = (Block)IafBlockRegistry.JAR_PIXIE_3.get();
               break;
            case 4:
               jar = (Block)IafBlockRegistry.JAR_PIXIE_4.get();
            }

            ItemStack stack = new ItemStack(jar, 1);
            if (!this.m_9236_().f_46443_) {
               if (!this.m_21120_(InteractionHand.MAIN_HAND).m_41619_()) {
                  this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
                  this.stealCooldown = 3000;
               }

               this.m_5552_(stack, 0.0F);
            }

            this.m_142687_(RemovalReason.DISCARDED);
         }

         return super.m_6071_(player, hand);
      }
   }

   public void flipAI(boolean flee) {
   }

   public void fall(float distance, float damageMultiplier) {
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new PixieAIFollowOwner(this, 1.0D, 2.0F, 4.0F));
      this.f_21345_.m_25352_(2, new PixieAIPickupItem(this, false));
      this.f_21345_.m_25352_(2, new PixieAIFlee(this, Player.class, 10.0F, (entity) -> {
         return true;
      }));
      this.f_21345_.m_25352_(2, new PixieAISteal(this, 1.0D));
      this.f_21345_.m_25352_(3, new PixieAIMoveRandom(this));
      this.f_21345_.m_25352_(4, new PixieAIEnterHouse(this));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setColor(this.f_19796_.m_188503_(5));
      this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
      if (dataTag != null) {
         System.out.println("EntityPixie spawned with dataTag: " + dataTag);
      }

      return spawnDataIn;
   }

   private boolean isBeyondHeight() {
      if (this.m_20186_() > (double)this.m_9236_().m_151558_()) {
         return true;
      } else {
         BlockPos height = this.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, this.m_20183_());
         int maxY = 20 + height.m_123342_();
         return this.m_20186_() > (double)maxY;
      }
   }

   public int getCommand() {
      return (Integer)this.f_19804_.m_135370_(COMMAND);
   }

   public void setCommand(int command) {
      this.f_19804_.m_135381_(COMMAND, command);
      this.setPixieSitting(command == 1);
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.m_9236_().f_46443_) {
         if (this.isPixieSitting() && this.getCommand() != 1) {
            this.setPixieSitting(false);
         }

         if (!this.isPixieSitting() && this.getCommand() == 1) {
            this.setPixieSitting(true);
         }

         if (this.isPixieSitting()) {
            this.m_21573_().m_26573_();
         }
      }

      if (this.stealCooldown > 0) {
         --this.stealCooldown;
      }

      if (!this.m_21205_().m_41619_() && !this.m_21824_()) {
         ++this.ticksHeldItemFor;
      } else {
         this.ticksHeldItemFor = 0;
      }

      if (!this.isPixieSitting() && !this.isBeyondHeight()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.08D, 0.0D));
      }

      if (this.m_9236_().f_46443_) {
         IceAndFire.PROXY.spawnParticle(EnumParticles.If_Pixie, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), (double)PARTICLE_RGB[this.getColor()][0], (double)PARTICLE_RGB[this.getColor()][1], (double)PARTICLE_RGB[this.getColor()][2]);
      }

      if (this.ticksUntilHouseAI > 0) {
         --this.ticksUntilHouseAI;
      }

      if (!this.m_9236_().f_46443_ && this.housePos != null && this.m_20238_(Vec3.m_82512_(this.housePos)) < 1.5D && this.m_9236_().m_7702_(this.housePos) != null) {
         BlockEntity var2 = this.m_9236_().m_7702_(this.housePos);
         if (var2 instanceof TileEntityPixieHouse) {
            TileEntityPixieHouse house = (TileEntityPixieHouse)var2;
            if (house.hasPixie) {
               this.housePos = null;
            } else {
               house.hasPixie = true;
               house.pixieType = this.getColor();
               house.pixieItems.set(0, this.m_21120_(InteractionHand.MAIN_HAND));
               house.tamedPixie = this.m_21824_();
               house.pixieOwnerUUID = this.m_21805_();
               IceAndFire.sendMSGToAll(new MessageUpdatePixieHouse(this.housePos.m_121878_(), true, this.getColor()));
               this.m_142687_(RemovalReason.DISCARDED);
            }
         }
      }

      if (this.m_269323_() != null && this.isOwnerClose() && this.f_19797_ % 80 == 0) {
         this.m_269323_().m_7292_(new MobEffectInstance(this.positivePotions[this.getColor()], 100, 0, false, false));
      }

   }

   public int getColor() {
      return Mth.m_14045_((Integer)this.m_20088_().m_135370_(COLOR), 0, 4);
   }

   public void setColor(int color) {
      this.m_20088_().m_135381_(COLOR, color);
   }

   public void m_7378_(CompoundTag compound) {
      this.setColor(compound.m_128451_("Color"));
      this.stealCooldown = compound.m_128451_("StealCooldown");
      this.ticksHeldItemFor = compound.m_128451_("HoldingTicks");
      this.setPixieSitting(compound.m_128471_("PixieSitting"));
      this.setCommand(compound.m_128451_("Command"));
      super.m_7378_(compound);
   }

   public void m_7380_(CompoundTag compound) {
      compound.m_128405_("Color", this.getColor());
      compound.m_128405_("Command", this.getCommand());
      compound.m_128405_("StealCooldown", this.stealCooldown);
      compound.m_128405_("HoldingTicks", this.ticksHeldItemFor);
      compound.m_128379_("PixieSitting", this.isPixieSitting());
      super.m_7380_(compound);
   }

   @Nullable
   public AgeableMob m_142606_(@NotNull ServerLevel serverWorld, @NotNull AgeableMob ageable) {
      return null;
   }

   public void setHousePosition(BlockPos blockPos) {
      this.housePos = blockPos;
   }

   public BlockPos getHousePos() {
      return this.housePos;
   }

   public boolean isOwnerClose() {
      return this.m_21824_() && this.m_269323_() != null && this.m_20280_(this.m_269323_()) < 100.0D;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.PIXIE_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return IafSoundRegistry.PIXIE_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.PIXIE_DIE;
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

   static {
      COLOR = SynchedEntityData.m_135353_(EntityPixie.class, EntityDataSerializers.f_135028_);
      COMMAND = SynchedEntityData.m_135353_(EntityPixie.class, EntityDataSerializers.f_135028_);
   }

   class AIMoveControl extends MoveControl {
      public AIMoveControl(EntityPixie pixie) {
         super(pixie);
      }

      public void m_8126_() {
         float speedMod = 1.0F;
         if (EntityPixie.this.slowSpeed) {
            speedMod = 2.0F;
         }

         if (this.f_24981_ == Operation.MOVE_TO) {
            if (EntityPixie.this.f_19862_) {
               EntityPixie.this.m_146922_(this.f_24974_.m_146908_() + 180.0F);
               speedMod = 0.1F;
               BlockPos target = EntityPixie.getPositionRelativetoGround(EntityPixie.this, EntityPixie.this.m_9236_(), EntityPixie.this.m_20185_() + (double)EntityPixie.this.f_19796_.m_188503_(15) - 7.0D, EntityPixie.this.m_20189_() + (double)EntityPixie.this.f_19796_.m_188503_(15) - 7.0D, EntityPixie.this.f_19796_);
               this.f_24975_ = (double)target.m_123341_();
               this.f_24976_ = (double)target.m_123342_();
               this.f_24977_ = (double)target.m_123343_();
            }

            double d0 = this.f_24975_ - EntityPixie.this.m_20185_();
            double d1 = this.f_24976_ - EntityPixie.this.m_20186_();
            double d2 = this.f_24977_ - EntityPixie.this.m_20189_();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = Math.sqrt(d3);
            if (d3 < EntityPixie.this.m_20191_().m_82309_()) {
               this.f_24981_ = Operation.WAIT;
               EntityPixie.this.m_20256_(EntityPixie.this.m_20184_().m_82542_(0.5D, 0.5D, 0.5D));
            } else {
               EntityPixie.this.m_20256_(EntityPixie.this.m_20184_().m_82520_(d0 / d3 * 0.05D * this.f_24978_ * (double)speedMod, d1 / d3 * 0.05D * this.f_24978_ * (double)speedMod, d2 / d3 * 0.05D * this.f_24978_ * (double)speedMod));
               if (EntityPixie.this.m_5448_() == null) {
                  EntityPixie.this.m_146922_(-((float)Mth.m_14136_(EntityPixie.this.m_20184_().f_82479_, EntityPixie.this.m_20184_().f_82481_)) * 57.295776F);
                  EntityPixie.this.f_20883_ = EntityPixie.this.m_146908_();
               } else {
                  double d4 = EntityPixie.this.m_5448_().m_20185_() - EntityPixie.this.m_20185_();
                  double d5 = EntityPixie.this.m_5448_().m_20189_() - EntityPixie.this.m_20189_();
                  EntityPixie.this.m_146922_(-((float)Mth.m_14136_(d4, d5)) * 57.295776F);
                  EntityPixie.this.f_20883_ = EntityPixie.this.m_146908_();
               }
            }
         }

      }
   }
}
