package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.ai.CyclopsAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.CyclopsAITargetSheepPlayers;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.EntityUtil;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IHumanoid;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import com.github.alexthe666.iceandfire.pathfinding.PathNavigateCyclops;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.MinecraftForge;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class EntityCyclops extends Monster implements IAnimatedEntity, IBlacklistedFromStatues, IVillagerFear, IHumanoid, IHasCustomizableAttributes {
   private static final EntityDataAccessor<Boolean> BLINDED;
   private static final EntityDataAccessor<Integer> VARIANT;
   public static Animation ANIMATION_STOMP;
   public static Animation ANIMATION_EATPLAYER;
   public static Animation ANIMATION_KICK;
   public static Animation ANIMATION_ROAR;
   public EntityCyclopsEye eyeEntity;
   private int animationTick;
   private Animation currentAnimation;

   public EntityCyclops(EntityType<EntityCyclops> type, Level worldIn) {
      super(type, worldIn);
      this.m_274367_(2.5F);
      this.m_21441_(BlockPathTypes.WATER, -1.0F);
      this.m_21441_(BlockPathTypes.FENCE, 0.0F);
      ANIMATION_STOMP = Animation.create(27);
      ANIMATION_EATPLAYER = Animation.create(40);
      ANIMATION_KICK = Animation.create(20);
      ANIMATION_ROAR = Animation.create(30);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.cyclopsMaxHealth).m_22268_(Attributes.f_22279_, 0.35D).m_22268_(Attributes.f_22281_, IafConfig.cyclopsAttackStrength).m_22268_(Attributes.f_22277_, 32.0D).m_22268_(Attributes.f_22284_, 20.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.cyclopsMaxHealth);
      this.m_21051_(Attributes.f_22279_).m_22100_(0.35D);
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level worldIn) {
      return new PathNavigateCyclops(this, this.m_9236_());
   }

   public int m_213860_() {
      return 40;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new RestrictSunGoal(this));
      this.f_21345_.m_25352_(3, new FleeSunGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, new CyclopsAIAttackMelee(this, 1.0D, false));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, true, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            if (EntityGorgon.isStoneMob(entity)) {
               return false;
            } else if (!DragonUtils.isAlive(entity)) {
               return false;
            } else if (entity instanceof WaterAnimal) {
               return false;
            } else {
               if (entity instanceof Player) {
                  Player playerEntity = (Player)entity;
                  if (playerEntity.m_7500_() || playerEntity.m_5833_()) {
                     return false;
                  }
               }

               if (entity instanceof EntityCyclops) {
                  return false;
               } else if (entity instanceof Animal && !(entity instanceof Wolf) && !(entity instanceof PolarBear) && !(entity instanceof EntityDragonBase)) {
                  return false;
               } else {
                  return !ServerEvents.isSheep(entity);
               }
            }
         }
      }));
      this.f_21346_.m_25352_(2, new NearestAttackableTargetGoal(this, Player.class, 10, true, true, new Predicate<Player>() {
         public boolean apply(@Nullable Player entity) {
            return entity != null && !entity.m_7500_() && !entity.m_5833_();
         }
      }));
      this.f_21346_.m_25352_(3, new CyclopsAITargetSheepPlayers(this, Player.class, true));
   }

   protected void m_7324_(@NotNull Entity entityIn) {
      if (!ServerEvents.isSheep(entityIn)) {
         entityIn.m_7334_(this);
      }

   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      int attackDescision = this.m_217043_().m_188503_(3);
      if (attackDescision == 0) {
         this.setAnimation(ANIMATION_STOMP);
         return true;
      } else if (attackDescision != 1) {
         this.setAnimation(ANIMATION_KICK);
         return true;
      } else {
         if (!entityIn.m_20363_(this) && entityIn.m_20205_() < 1.95F && !(entityIn instanceof EntityDragonBase) && !entityIn.m_6095_().m_204039_(ForgeRegistries.ENTITY_TYPES.tags().createTagKey(IafTagRegistry.CYCLOPS_UNLIFTABLES))) {
            this.setAnimation(ANIMATION_EATPLAYER);
            entityIn.m_8127_();
            entityIn.m_7998_(this, true);
         } else {
            this.setAnimation(ANIMATION_STOMP);
         }

         return true;
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(BLINDED, Boolean.FALSE);
      this.f_19804_.m_135372_(VARIANT, 0);
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Blind", this.isBlinded());
      compound.m_128405_("Variant", this.getVariant());
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.setBlinded(compound.m_128471_("Blind"));
      this.setVariant(compound.m_128451_("Variant"));
      this.setConfigurableAttributes();
   }

   public int getVariant() {
      return (Integer)this.f_19804_.m_135370_(VARIANT);
   }

   public void setVariant(int variant) {
      this.f_19804_.m_135381_(VARIANT, variant);
   }

   public boolean isBlinded() {
      return (Boolean)this.f_19804_.m_135370_(BLINDED);
   }

   public void setBlinded(boolean blind) {
      this.f_19804_.m_135381_(BLINDED, blind);
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         passenger.m_20334_(0.0D, passenger.m_20184_().f_82480_, 0.0D);
         this.setAnimation(ANIMATION_EATPLAYER);
         double raiseUp = this.getAnimationTick() < 10 ? 0.0D : Math.min((double)(this.getAnimationTick() * 3 - 30) * 0.2D, 5.199999809265137D);
         float pullIn = this.getAnimationTick() < 15 ? 0.0F : Math.min((float)(this.getAnimationTick() - 15) * 0.15F, 0.75F);
         this.f_20883_ = this.m_146908_();
         this.m_146922_(0.0F);
         float radius = -2.75F + pullIn;
         float angle = 0.017453292F * this.f_20883_ + 3.15F;
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         passenger.m_6034_(this.m_20185_() + extraX, this.m_20186_() + raiseUp, this.m_20189_() + extraZ);
         if (this.getAnimationTick() == 32) {
            passenger.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)IafConfig.cyclopsBiteStrength);
            passenger.m_8127_();
         }
      }

   }

   public void m_7023_(@NotNull Vec3 vec) {
      if (this.getAnimation() == ANIMATION_EATPLAYER) {
         super.m_7023_(vec.m_82542_(0.0D, 0.0D, 0.0D));
      } else {
         super.m_7023_(vec);
      }
   }

   public boolean m_6094_() {
      return false;
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.eyeEntity == null) {
         this.eyeEntity = new EntityCyclopsEye(this, 0.2F, 0.0F, 7.4F, 1.2F, 0.6F, 1.0F);
         this.eyeEntity.m_20359_(this);
      }

      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && this.m_5448_() instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.isBlinded() && this.m_5448_() != null && this.m_20280_(this.m_5448_()) > 6.0D) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.getAnimation() == ANIMATION_ROAR && this.getAnimationTick() == 5) {
         this.m_5496_(IafSoundRegistry.CYCLOPS_BLINDED, 1.0F, 1.0F);
      }

      if (this.getAnimation() == ANIMATION_EATPLAYER && this.getAnimationTick() == 25) {
         this.m_5496_(IafSoundRegistry.CYCLOPS_BITE, 1.0F, 1.0F);
      }

      if (this.getAnimation() == ANIMATION_STOMP && this.m_5448_() != null && this.m_20280_(this.m_5448_()) < 12.0D && this.getAnimationTick() == 14) {
         this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
      }

      if (this.getAnimation() == ANIMATION_KICK && this.m_5448_() != null && this.m_20280_(this.m_5448_()) < 14.0D && this.getAnimationTick() == 12) {
         this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)this.m_21051_(Attributes.f_22281_).m_22135_());
         if (this.m_5448_() != null) {
            this.m_5448_().m_147240_(2.0D, this.m_20185_() - this.m_5448_().m_20185_(), this.m_20189_() - this.m_5448_().m_20189_());
         }
      }

      if (this.getAnimation() != ANIMATION_EATPLAYER && this.m_5448_() != null && !this.m_20197_().isEmpty() && this.m_20197_().contains(this.m_5448_())) {
         this.setAnimation(ANIMATION_EATPLAYER);
      }

      if (this.getAnimation() == NO_ANIMATION && this.m_5448_() != null && this.m_217043_().m_188503_(100) == 0) {
         this.setAnimation(ANIMATION_ROAR);
      }

      if (this.getAnimation() == ANIMATION_STOMP && this.getAnimationTick() == 14) {
         for(int i1 = 0; i1 < 20; ++i1) {
            double motionX = this.m_217043_().m_188583_() * 0.07D;
            double motionY = this.m_217043_().m_188583_() * 0.07D;
            double motionZ = this.m_217043_().m_188583_() * 0.07D;
            float radius = -1.5F;
            float angle = 0.017453292F * this.f_20883_ + (float)i1 * 1.0F;
            double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
            double extraY = 0.800000011920929D;
            double extraZ = (double)(radius * Mth.m_14089_(angle));
            BlockState BlockState = this.m_9236_().m_8055_(BlockPos.m_274561_(this.m_20185_() + extraX, this.m_20186_() + extraY - 1.0D, this.m_20189_() + extraZ));
            if (BlockState.m_60795_() && this.m_9236_().f_46443_) {
               this.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, BlockState), this.m_20185_() + extraX, this.m_20186_() + extraY, this.m_20189_() + extraZ, motionX, motionY, motionZ);
            }
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
      if (this.eyeEntity == null) {
         this.eyeEntity = new EntityCyclopsEye(this, 0.2F, 0.0F, 7.4F, 1.2F, 0.5F, 1.0F);
         this.eyeEntity.m_20359_(this);
      }

      EntityUtil.updatePart(this.eyeEntity, this);
      this.breakBlock();
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setVariant(this.m_217043_().m_188503_(4));
      return spawnDataIn;
   }

   public void breakBlock() {
      if (IafConfig.cyclopsGriefing) {
         for(int a = (int)Math.round(this.m_20191_().f_82288_) - 1; a <= (int)Math.round(this.m_20191_().f_82291_) + 1; ++a) {
            for(int b = (int)Math.round(this.m_20191_().f_82289_) + 1; b <= (int)Math.round(this.m_20191_().f_82292_) + 2 && b <= 127; ++b) {
               for(int c = (int)Math.round(this.m_20191_().f_82290_) - 1; c <= (int)Math.round(this.m_20191_().f_82293_) + 1; ++c) {
                  BlockPos pos = new BlockPos(a, b, c);
                  BlockState state = this.m_9236_().m_8055_(pos);
                  Block block = state.m_60734_();
                  if (!state.m_60795_() && !state.m_60808_(this.m_9236_(), pos).m_83281_() && !(block instanceof BushBlock) && block != Blocks.f_50752_ && (state.m_60734_() instanceof LeavesBlock || state.m_204336_(BlockTags.f_13106_))) {
                     this.m_20184_().m_82490_(0.6D);
                     if (!MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this, (double)a, (double)b, (double)c)) && block != Blocks.f_50016_ && !this.m_9236_().f_46443_) {
                        this.m_9236_().m_46961_(pos, true);
                     }
                  }
               }
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

   public void m_142687_(@NotNull RemovalReason reason) {
      if (this.eyeEntity != null) {
         this.eyeEntity.m_142687_(reason);
      }

      super.m_142687_(reason);
   }

   public Animation getAnimation() {
      return this.currentAnimation;
   }

   public void setAnimation(Animation animation) {
      this.currentAnimation = animation;
   }

   public Animation[] getAnimations() {
      return new Animation[]{NO_ANIMATION, ANIMATION_STOMP, ANIMATION_EATPLAYER, ANIMATION_KICK, ANIMATION_ROAR};
   }

   public boolean isBlinking() {
      return this.f_19797_ % 50 > 40 && !this.isBlinded();
   }

   public void onHitEye(DamageSource source, float damage) {
      if (!this.isBlinded()) {
         this.setBlinded(true);
         this.m_21051_(Attributes.f_22277_).m_22100_(6.0D);
         this.m_21051_(Attributes.f_22279_).m_22100_(0.35D);
         this.setAnimation(ANIMATION_ROAR);
         this.m_6469_(source, damage * 3.0F);
      }

   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.CYCLOPS_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return IafSoundRegistry.CYCLOPS_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.CYCLOPS_DIE;
   }

   public boolean canBeTurnedToStone() {
      return !this.isBlinded();
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   static {
      BLINDED = SynchedEntityData.m_135353_(EntityCyclops.class, EntityDataSerializers.f_135035_);
      VARIANT = SynchedEntityData.m_135353_(EntityCyclops.class, EntityDataSerializers.f_135028_);
   }
}
