package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.ai.TrollAIFleeSun;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IHumanoid;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.ArrayList;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class EntityTroll extends Monster implements IAnimatedEntity, IVillagerFear, IHumanoid, IHasCustomizableAttributes {
   public static final Animation ANIMATION_STRIKE_HORIZONTAL = Animation.create(20);
   public static final Animation ANIMATION_STRIKE_VERTICAL = Animation.create(20);
   public static final Animation ANIMATION_SPEAK = Animation.create(10);
   public static final Animation ANIMATION_ROAR = Animation.create(25);
   public static final ResourceLocation FOREST_LOOT = new ResourceLocation("iceandfire", "entities/troll_forest");
   public static final ResourceLocation FROST_LOOT = new ResourceLocation("iceandfire", "entities/troll_frost");
   public static final ResourceLocation MOUNTAIN_LOOT = new ResourceLocation("iceandfire", "entities/troll_mountain");
   private static final EntityDataAccessor<Integer> VARIANT;
   private static final EntityDataAccessor<Integer> WEAPON;
   public float stoneProgress;
   private int animationTick;
   private Animation currentAnimation;
   private boolean avoidSun = true;

   public EntityTroll(EntityType<EntityTroll> t, Level worldIn) {
      super(t, worldIn);
   }

   public static boolean canTrollSpawnOn(EntityType<? extends Mob> typeIn, ServerLevelAccessor worldIn, MobSpawnType reason, BlockPos pos, RandomSource randomIn) {
      return worldIn.m_46791_() != Difficulty.PEACEFUL && m_219009_(worldIn, pos, randomIn) && m_217057_((EntityType)IafEntityRegistry.TROLL.get(), worldIn, reason, pos, randomIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.trollMaxHealth).m_22268_(Attributes.f_22279_, 0.35D).m_22268_(Attributes.f_22281_, IafConfig.trollAttackStrength).m_22268_(Attributes.f_22278_, 1.0D).m_22268_(Attributes.f_22284_, 9.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.trollMaxHealth);
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.trollAttackStrength);
   }

   private void setAvoidSun(boolean day) {
      if (day && !this.avoidSun) {
         ((GroundPathNavigation)this.m_21573_()).m_26490_(true);
         this.avoidSun = true;
      }

      if (!day && this.avoidSun) {
         ((GroundPathNavigation)this.m_21573_()).m_26490_(false);
         this.avoidSun = false;
      }

   }

   public boolean m_6914_(LevelReader worldIn) {
      return worldIn.m_45784_(this);
   }

   public boolean m_5545_(LevelAccessor worldIn, @NotNull MobSpawnType spawnReasonIn) {
      BlockPos pos = this.m_20183_();
      BlockPos heightAt = worldIn.m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, pos);
      boolean rngCheck = true;
      return rngCheck && pos.m_123342_() < heightAt.m_123342_() - 10 && super.m_5545_(worldIn, spawnReasonIn);
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new TrollAIFleeSun(this, 1.0D));
      this.f_21345_.m_25352_(3, new MeleeAttackGoal(this, 1.0D, true));
      this.f_21345_.m_25352_(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(5, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
      this.f_21345_.m_25352_(5, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, AbstractVillager.class, false));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, false));
      this.setAvoidSun(true);
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.m_217043_().m_188499_()) {
         this.setAnimation(ANIMATION_STRIKE_VERTICAL);
      } else {
         this.setAnimation(ANIMATION_STRIKE_HORIZONTAL);
      }

      return true;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(VARIANT, 0);
      this.f_19804_.m_135372_(WEAPON, 0);
   }

   private int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   private void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public EnumTroll getTrollType() {
      return EnumTroll.values()[this.getVariant()];
   }

   public void setTrollType(EnumTroll variant) {
      this.setVariant(variant.ordinal());
   }

   private int getWeapon() {
      return (Integer)this.f_19804_.m_135370_(WEAPON);
   }

   private void setWeapon(int variant) {
      this.f_19804_.m_135381_(WEAPON, variant);
   }

   public EnumTroll.Weapon getWeaponType() {
      return EnumTroll.Weapon.values()[this.getWeapon()];
   }

   public void setWeaponType(EnumTroll.Weapon variant) {
      this.setWeapon(variant.ordinal());
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128405_("Variant", this.getVariant());
      compound.m_128405_("Weapon", this.getWeapon());
      compound.m_128350_("StoneProgress", this.stoneProgress);
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setVariant(compound.m_128451_("Variant"));
      this.setWeapon(compound.m_128451_("Weapon"));
      this.stoneProgress = compound.m_128457_("StoneProgress");
      this.setConfigurableAttributes();
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setTrollType(EnumTroll.getBiomeType(this.m_9236_().m_204166_(this.m_20183_())));
      this.setWeaponType(EnumTroll.getWeaponForType(this.getTrollType()));
      return spawnDataIn;
   }

   public boolean m_6469_(DamageSource source, float damage) {
      return source.m_19385_().contains("arrow") ? false : super.m_6469_(source, damage);
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      switch(this.getTrollType()) {
      case MOUNTAIN:
         return MOUNTAIN_LOOT;
      case FROST:
         return FROST_LOOT;
      case FOREST:
         return FOREST_LOOT;
      default:
         return null;
      }
   }

   public int m_213860_() {
      return 15;
   }

   protected void m_6153_() {
      super.m_6153_();
      if (this.f_20919_ == 20 && !this.m_9236_().f_46443_ && IafConfig.trollsDropWeapon) {
         ItemStack brokenDrop;
         if (this.m_217043_().m_188503_(3) == 0) {
            brokenDrop = new ItemStack((ItemLike)this.getWeaponType().item.get(), 1);
            brokenDrop.m_220157_(this.m_217043_().m_188503_(250), this.m_217043_(), (ServerPlayer)null);
            this.dropItemAt(brokenDrop, this.m_20185_(), this.m_20186_(), this.m_20189_());
         } else {
            brokenDrop = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
            ItemStack brokenDrop2 = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
            if (this.getWeaponType() == EnumTroll.Weapon.AXE) {
               brokenDrop = new ItemStack(Items.f_42398_, this.m_217043_().m_188503_(2) + 1);
               brokenDrop2 = new ItemStack(Blocks.f_50652_, this.m_217043_().m_188503_(2) + 1);
            }

            if (this.getWeaponType() == EnumTroll.Weapon.COLUMN) {
               brokenDrop = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
               brokenDrop2 = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
            }

            if (this.getWeaponType() == EnumTroll.Weapon.COLUMN_FOREST) {
               brokenDrop = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
               brokenDrop2 = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
            }

            if (this.getWeaponType() == EnumTroll.Weapon.COLUMN_FROST) {
               brokenDrop = new ItemStack(Blocks.f_50222_, this.m_217043_().m_188503_(2) + 1);
               brokenDrop2 = new ItemStack(Items.f_42452_, this.m_217043_().m_188503_(4) + 1);
            }

            if (this.getWeaponType() == EnumTroll.Weapon.HAMMER) {
               brokenDrop = new ItemStack(Items.f_42500_, this.m_217043_().m_188503_(2) + 1);
               brokenDrop2 = new ItemStack(Blocks.f_50652_, this.m_217043_().m_188503_(2) + 1);
            }

            if (this.getWeaponType() == EnumTroll.Weapon.TRUNK) {
               brokenDrop = new ItemStack(Blocks.f_49999_, this.m_217043_().m_188503_(2) + 1);
               brokenDrop2 = new ItemStack(Blocks.f_49999_, this.m_217043_().m_188503_(2) + 1);
            }

            if (this.getWeaponType() == EnumTroll.Weapon.TRUNK_FROST) {
               brokenDrop = new ItemStack(Blocks.f_50000_, this.m_217043_().m_188503_(4) + 1);
               brokenDrop2 = new ItemStack(Items.f_42452_, this.m_217043_().m_188503_(4) + 1);
            }

            this.dropItemAt(brokenDrop, this.m_20185_(), this.m_20186_(), this.m_20189_());
            this.dropItemAt(brokenDrop2, this.m_20185_(), this.m_20186_(), this.m_20189_());
         }
      }

   }

   @Nullable
   private ItemEntity dropItemAt(ItemStack stack, double x, double y, double z) {
      if (stack.m_41613_() > 0) {
         ItemEntity entityitem = new ItemEntity(this.m_9236_(), x, y, z, stack);
         entityitem.m_32060_();
         this.m_9236_().m_7967_(entityitem);
         return entityitem;
      } else {
         return null;
      }
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      boolean stone = EntityGorgon.isStoneMob(this);
      if (stone && this.stoneProgress < 20.0F) {
         this.stoneProgress += 2.0F;
      } else if (!stone && this.stoneProgress > 0.0F) {
         this.stoneProgress -= 2.0F;
      }

      if (!stone && this.getAnimation() == NO_ANIMATION && this.m_5448_() != null && this.m_217043_().m_188503_(100) == 0) {
         this.setAnimation(ANIMATION_ROAR);
      }

      if (this.getAnimation() == ANIMATION_ROAR && this.getAnimationTick() == 5) {
         this.m_5496_(IafSoundRegistry.TROLL_ROAR, 1.0F, 1.0F);
      }

      if (!stone && this.m_21223_() < this.m_21233_() && this.f_19797_ % 30 == 0) {
         this.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 30, 1, false, false));
      }

      this.setAvoidSun(this.m_9236_().m_46461_());
      float weaponX;
      if (this.m_9236_().m_46461_() && !this.m_9236_().f_46443_) {
         weaponX = (float)this.m_9236_().m_45517_(LightLayer.SKY, this.m_20183_());
         BlockPos blockpos = this.m_20202_() instanceof Boat ? (new BlockPos(this.m_146903_(), this.m_146904_(), this.m_146907_())).m_7494_() : new BlockPos(this.m_146903_(), this.m_146904_(), this.m_146907_());
         if (weaponX > 0.5F && this.m_9236_().m_45527_(blockpos)) {
            this.m_20334_(0.0D, 0.0D, 0.0D);
            this.setAnimation(NO_ANIMATION);
            this.m_5496_(IafSoundRegistry.TURN_STONE, 1.0F, 1.0F);
            this.stoneProgress = 20.0F;
            EntityStoneStatue statue = EntityStoneStatue.buildStatueEntity(this);
            statue.getTrappedTag().m_128350_("StoneProgress", 20.0F);
            statue.m_19890_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), this.m_146909_());
            if (!this.m_9236_().f_46443_) {
               this.m_9236_().m_7967_(statue);
            }

            statue.f_19859_ = this.m_146908_();
            statue.m_146922_(this.m_146908_());
            statue.f_20885_ = this.m_146908_();
            statue.f_20883_ = this.m_146908_();
            statue.f_20884_ = this.m_146908_();
            this.m_142687_(RemovalReason.KILLED);
         }
      }

      float weaponZ;
      float weaponY;
      if (this.getAnimation() == ANIMATION_STRIKE_VERTICAL && this.getAnimationTick() == 10) {
         weaponX = (float)(this.m_20185_() + (double)(1.9F * Mth.m_14089_((float)((double)(this.f_20883_ + 90.0F) * 3.141592653589793D / 180.0D))));
         weaponZ = (float)(this.m_20189_() + (double)(1.9F * Mth.m_14031_((float)((double)(this.f_20883_ + 90.0F) * 3.141592653589793D / 180.0D))));
         weaponY = (float)(this.m_20186_() + 0.20000000298023224D);
         BlockState state = this.m_9236_().m_8055_(BlockPos.m_274561_((double)weaponX, (double)(weaponY - 1.0F), (double)weaponZ));

         for(int i = 0; i < 20; ++i) {
            double motionX = this.m_217043_().m_188583_() * 0.07D;
            double motionY = this.m_217043_().m_188583_() * 0.07D;
            double motionZ = this.m_217043_().m_188583_() * 0.07D;
            if (state.m_280296_() && this.m_9236_().f_46443_) {
               this.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, state), (double)(weaponX + (this.m_217043_().m_188501_() - 0.5F)), (double)(weaponY + (this.m_217043_().m_188501_() - 0.5F)), (double)(weaponZ + (this.m_217043_().m_188501_() - 0.5F)), motionX, motionY, motionZ);
            }
         }
      }

      if (this.getAnimation() == ANIMATION_STRIKE_VERTICAL && this.m_5448_() != null && this.m_20280_(this.m_5448_()) < 4.0D && this.getAnimationTick() == 10 && this.f_20919_ <= 0) {
         this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
      }

      if (this.getAnimation() == ANIMATION_STRIKE_HORIZONTAL && this.m_5448_() != null && this.m_20280_(this.m_5448_()) < 4.0D && this.getAnimationTick() == 10 && this.f_20919_ <= 0) {
         LivingEntity target = this.m_5448_();
         target.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
         weaponZ = 0.5F;
         weaponY = target.f_20902_;
         float f3 = 0.6F;
         float f4 = Mth.m_14116_(weaponY * weaponY + f3 * f3);
         if (f4 < 1.0F) {
            f4 = 1.0F;
         }

         f4 = weaponZ / f4;
         float var10000 = weaponY * f4;
         var10000 = f3 * f4;
         float f5 = Mth.m_14031_(this.m_146908_() * 0.017453292F);
         float f6 = Mth.m_14089_(this.m_146908_() * 0.017453292F);
         target.m_20334_((double)f5, (double)f6, 0.4000000059604645D);
      }

      if (this.m_21573_().m_26571_() && this.m_5448_() != null && this.m_20280_(this.m_5448_()) > 3.0D && this.m_20280_(this.m_5448_()) < 30.0D && this.m_9236_().m_46469_().m_46207_(GameRules.f_46132_)) {
         this.m_21391_(this.m_5448_(), 30.0F, 30.0F);
         if (this.getAnimation() == NO_ANIMATION && this.f_19796_.m_188503_(15) == 0) {
            this.setAnimation(ANIMATION_STRIKE_VERTICAL);
         }

         if (this.getAnimation() == ANIMATION_STRIKE_VERTICAL && this.getAnimationTick() == 10) {
            weaponX = (float)(this.m_20185_() + (double)(1.9F * Mth.m_14089_((float)((double)(this.f_20883_ + 90.0F) * 3.141592653589793D / 180.0D))));
            weaponZ = (float)(this.m_20189_() + (double)(1.9F * Mth.m_14031_((float)((double)(this.f_20883_ + 90.0F) * 3.141592653589793D / 180.0D))));
            weaponY = (float)(this.m_20186_() + (double)(this.m_20192_() / 2.0F));
            Explosion explosion = new Explosion(this.m_9236_(), this, (double)weaponX, (double)weaponY, (double)weaponZ, 1.0F + this.m_217043_().m_188501_(), new ArrayList());
            if (!MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this, (double)weaponX, (double)weaponY, (double)weaponZ))) {
               explosion.m_46061_();
               explosion.m_46075_(true);
            }

            this.m_5496_(SoundEvents.f_11913_, 1.0F, 1.0F);
         }
      }

      if (this.getAnimation() == ANIMATION_STRIKE_VERTICAL && this.getAnimationTick() == 10) {
         this.m_5496_(SoundEvents.f_12317_, 2.5F, 0.5F);
      }

      if (this.getAnimation() == ANIMATION_STRIKE_HORIZONTAL && this.getAnimationTick() == 10) {
         this.m_5496_(SoundEvents.f_12317_, 2.5F, 0.5F);
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
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

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.TROLL_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.TROLL_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.TROLL_DIE;
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, ANIMATION_STRIKE_HORIZONTAL, ANIMATION_STRIKE_VERTICAL, ANIMATION_SPEAK, ANIMATION_ROAR};
   }

   static {
      VARIANT = SynchedEntityData.m_135353_(EntityTroll.class, EntityDataSerializers.f_135028_);
      WEAPON = SynchedEntityData.m_135353_(EntityTroll.class, EntityDataSerializers.f_135028_);
   }
}
