package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.ai.CockatriceAIAggroLook;
import com.github.alexthe666.iceandfire.entity.ai.CockatriceAIFollowOwner;
import com.github.alexthe666.iceandfire.entity.ai.CockatriceAIStareAttack;
import com.github.alexthe666.iceandfire.entity.ai.CockatriceAITarget;
import com.github.alexthe666.iceandfire.entity.ai.CockatriceAITargetItems;
import com.github.alexthe666.iceandfire.entity.ai.CockatriceAIWander;
import com.github.alexthe666.iceandfire.entity.ai.EntityAIAttackMeleeNoCooldown;
import com.github.alexthe666.iceandfire.entity.ai.IAFLookHelper;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class EntityCockatrice extends TamableAnimal implements IAnimatedEntity, IBlacklistedFromStatues, IVillagerFear, IHasCustomizableAttributes {
   public static final Animation ANIMATION_JUMPAT = Animation.create(30);
   public static final Animation ANIMATION_WATTLESHAKE = Animation.create(20);
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_SPEAK = Animation.create(10);
   public static final Animation ANIMATION_EAT = Animation.create(20);
   public static final float VIEW_RADIUS = 0.6F;
   private static final EntityDataAccessor<Boolean> HEN;
   private static final EntityDataAccessor<Boolean> STARING;
   private static final EntityDataAccessor<Integer> TARGET_ENTITY;
   private static final EntityDataAccessor<Integer> TAMING_PLAYER;
   private static final EntityDataAccessor<Integer> TAMING_LEVEL;
   private static final EntityDataAccessor<Integer> COMMAND;
   private final CockatriceAIStareAttack aiStare;
   private final MeleeAttackGoal aiMelee;
   public float sitProgress;
   public float stareProgress;
   public int ticksStaring = 0;
   public HomePosition homePos;
   public boolean hasHomePosition = false;
   private int animationTick;
   private Animation currentAnimation;
   private boolean isSitting;
   private boolean isStaring;
   private boolean isMeleeMode = false;
   private LivingEntity targetedEntity;
   private int clientSideAttackTime;

   public EntityCockatrice(EntityType<EntityCockatrice> type, Level worldIn) {
      super(type, worldIn);
      this.f_21365_ = new IAFLookHelper(this);
      this.aiStare = new CockatriceAIStareAttack(this, 1.0D, 0, 15.0F);
      this.aiMelee = new EntityAIAttackMeleeNoCooldown(this, 1.5D, false);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.cockatriceMaxHealth).m_22268_(Attributes.f_22279_, 0.4D).m_22268_(Attributes.f_22281_, 5.0D).m_22268_(Attributes.f_22277_, 64.0D).m_22268_(Attributes.f_22284_, 2.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.cockatriceMaxHealth);
   }

   public int m_213860_() {
      return 10;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(3, new CockatriceAIFollowOwner(this, 1.0D, 7.0F, 2.0F));
      this.f_21345_.m_25352_(3, new SitWhenOrderedToGoal(this));
      this.f_21345_.m_25352_(3, new AvoidEntityGoal(this, LivingEntity.class, 14.0F, 1.0D, 1.0D, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            if (entity instanceof Player) {
               return !((Player)entity).m_7500_() && !entity.m_5833_();
            } else {
               return ServerEvents.doesScareCockatrice(entity) && !ServerEvents.isChicken(entity);
            }
         }
      }));
      this.f_21345_.m_25352_(4, new CockatriceAIWander(this, 1.0D));
      this.f_21345_.m_25352_(5, new CockatriceAIAggroLook(this));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, LivingEntity.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new CockatriceAITargetItems(this, false));
      this.f_21346_.m_25352_(2, new OwnerHurtByTargetGoal(this));
      this.f_21346_.m_25352_(3, new OwnerHurtTargetGoal(this));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(5, new CockatriceAITarget(this, LivingEntity.class, true, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            if (entity instanceof Player) {
               return !((Player)entity).m_7500_() && !entity.m_5833_();
            } else {
               return entity instanceof Enemy && EntityCockatrice.this.m_21824_() && !(entity instanceof Creeper) && !(entity instanceof ZombifiedPiglin) && !(entity instanceof EnderMan) || ServerEvents.isCockatriceTarget(entity) && !ServerEvents.isChicken(entity);
            }
         }
      }));
   }

   public boolean m_21536_() {
      return this.hasHomePosition && this.getCommand() == 3 && this.getHomeDimensionName().equals(DragonUtils.getDimensionName(this.m_9236_())) || super.m_21536_();
   }

   @NotNull
   public SoundSource m_5720_() {
      return SoundSource.HOSTILE;
   }

   @NotNull
   public BlockPos m_21534_() {
      return this.hasHomePosition && this.getCommand() == 3 && this.homePos != null ? this.homePos.getPosition() : super.m_21534_();
   }

   public float m_21535_() {
      return 30.0F;
   }

   public String getHomeDimensionName() {
      return this.homePos == null ? "" : this.homePos.getDimension();
   }

   public boolean m_7307_(@NotNull Entity entityIn) {
      if (ServerEvents.isChicken(entityIn)) {
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

   public boolean m_6469_(DamageSource source, float damage) {
      if (source.m_7639_() != null && ServerEvents.doesScareCockatrice(source.m_7639_())) {
         damage *= 5.0F;
      }

      return source == this.m_9236_().m_269111_().m_269318_() ? false : super.m_6469_(source, damage);
   }

   private boolean canUseStareOn(Entity entity) {
      return (!(entity instanceof IBlacklistedFromStatues) || ((IBlacklistedFromStatues)entity).canBeTurnedToStone()) && !ServerEvents.isCockatriceTarget(entity);
   }

   private void switchAI(boolean melee) {
      if (melee) {
         this.f_21345_.m_25363_(this.aiStare);
         if (this.aiMelee != null) {
            this.f_21345_.m_25352_(2, this.aiMelee);
         }

         this.isMeleeMode = true;
      } else {
         this.f_21345_.m_25363_(this.aiMelee);
         if (this.aiStare != null) {
            this.f_21345_.m_25352_(2, this.aiStare);
         }

         this.isMeleeMode = false;
      }

   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.isStaring()) {
         return false;
      } else if (this.m_217043_().m_188499_()) {
         if (this.getAnimation() != ANIMATION_JUMPAT && this.getAnimation() != ANIMATION_BITE) {
            this.setAnimation(ANIMATION_JUMPAT);
         }

         return false;
      } else {
         if (this.getAnimation() != ANIMATION_BITE && this.getAnimation() != ANIMATION_JUMPAT) {
            this.setAnimation(ANIMATION_BITE);
         }

         return false;
      }
   }

   public boolean canMove() {
      return !this.m_21827_() && (this.getAnimation() != ANIMATION_JUMPAT || this.getAnimationTick() >= 7);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HEN, Boolean.FALSE);
      this.f_19804_.m_135372_(STARING, Boolean.FALSE);
      this.f_19804_.m_135372_(TARGET_ENTITY, 0);
      this.f_19804_.m_135372_(TAMING_PLAYER, 0);
      this.f_19804_.m_135372_(TAMING_LEVEL, 0);
      this.f_19804_.m_135372_(COMMAND, 0);
   }

   public boolean hasTargetedEntity() {
      return (Integer)this.f_19804_.m_135370_(TARGET_ENTITY) != 0;
   }

   public boolean hasTamingPlayer() {
      return (Integer)this.f_19804_.m_135370_(TAMING_PLAYER) != 0;
   }

   @Nullable
   public Entity getTamingPlayer() {
      if (!this.hasTamingPlayer()) {
         return null;
      } else if (this.m_9236_().f_46443_) {
         if (this.targetedEntity != null) {
            return this.targetedEntity;
         } else {
            Entity entity = this.m_9236_().m_6815_((Integer)this.f_19804_.m_135370_(TAMING_PLAYER));
            if (entity instanceof LivingEntity) {
               this.targetedEntity = (LivingEntity)entity;
               return this.targetedEntity;
            } else {
               return null;
            }
         }
      } else {
         return this.m_9236_().m_6815_((Integer)this.f_19804_.m_135370_(TAMING_PLAYER));
      }
   }

   public void setTamingPlayer(int entityId) {
      this.f_19804_.m_135381_(TAMING_PLAYER, entityId);
   }

   @Nullable
   public LivingEntity getTargetedEntity() {
      boolean blindness = this.m_21023_(MobEffects.f_19610_) || this.m_5448_() != null && this.m_5448_().m_21023_(MobEffects.f_19610_) || EntityGorgon.isBlindfolded(this.m_5448_());
      if (blindness) {
         return null;
      } else if (!this.hasTargetedEntity()) {
         return null;
      } else if (this.m_9236_().f_46443_) {
         if (this.targetedEntity != null) {
            return this.targetedEntity;
         } else {
            Entity entity = this.m_9236_().m_6815_((Integer)this.f_19804_.m_135370_(TARGET_ENTITY));
            if (entity instanceof LivingEntity) {
               this.targetedEntity = (LivingEntity)entity;
               return this.targetedEntity;
            } else {
               return null;
            }
         }
      } else {
         return this.m_5448_();
      }
   }

   public void setTargetedEntity(int entityId) {
      this.f_19804_.m_135381_(TARGET_ENTITY, entityId);
   }

   public void m_7350_(@NotNull EntityDataAccessor<?> key) {
      super.m_7350_(key);
      if (TARGET_ENTITY.equals(key)) {
         this.clientSideAttackTime = 0;
         this.targetedEntity = null;
      }

   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128379_("Hen", this.isHen());
      tag.m_128379_("Staring", this.isStaring());
      tag.m_128405_("TamingLevel", this.getTamingLevel());
      tag.m_128405_("TamingPlayer", (Integer)this.f_19804_.m_135370_(TAMING_PLAYER));
      tag.m_128405_("Command", this.getCommand());
      tag.m_128379_("HasHomePosition", this.hasHomePosition);
      if (this.homePos != null && this.hasHomePosition) {
         this.homePos.write(tag);
      }

   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      this.setHen(tag.m_128471_("Hen"));
      this.setStaring(tag.m_128471_("Staring"));
      this.setTamingLevel(tag.m_128451_("TamingLevel"));
      this.setTamingPlayer(tag.m_128451_("TamingPlayer"));
      this.setCommand(tag.m_128451_("Command"));
      this.hasHomePosition = tag.m_128471_("HasHomePosition");
      if (this.hasHomePosition && tag.m_128451_("HomeAreaX") != 0 && tag.m_128451_("HomeAreaY") != 0 && tag.m_128451_("HomeAreaZ") != 0) {
         this.homePos = new HomePosition(tag, this.m_9236_());
      }

      this.setConfigurableAttributes();
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
      super.m_20282_(sitting);
      if (!this.m_9236_().f_46443_) {
         this.isSitting = sitting;
      }

   }

   public void fall(float distance, float damageMultiplier) {
   }

   @Nullable
   public SpawnGroupData m_6518_(@NotNull ServerLevelAccessor worldIn, @NotNull DifficultyInstance difficultyIn, @NotNull MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
      spawnDataIn = super.m_6518_(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
      this.setHen(this.m_217043_().m_188499_());
      return spawnDataIn;
   }

   public boolean isHen() {
      return (Boolean)this.f_19804_.m_135370_(HEN);
   }

   public void setHen(boolean hen) {
      this.f_19804_.m_135381_(HEN, hen);
   }

   public int getTamingLevel() {
      return (Integer)this.f_19804_.m_135370_(TAMING_LEVEL);
   }

   public void setTamingLevel(int level) {
      this.f_19804_.m_135381_(TAMING_LEVEL, level);
   }

   public int getCommand() {
      return (Integer)this.f_19804_.m_135370_(COMMAND);
   }

   public void setCommand(int command) {
      this.f_19804_.m_135381_(COMMAND, command);
      this.m_21839_(command == 1);
   }

   public boolean isStaring() {
      return this.m_9236_().f_46443_ ? (this.isStaring = (Boolean)this.f_19804_.m_135370_(STARING)) : this.isStaring;
   }

   public void setStaring(boolean staring) {
      this.f_19804_.m_135381_(STARING, staring);
      if (!this.m_9236_().f_46443_) {
         this.isStaring = staring;
      }

   }

   public void forcePreyToLook(Mob mob) {
      mob.m_21563_().m_24950_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), (float)mob.m_8085_(), (float)mob.m_8132_());
   }

   @NotNull
   public InteractionResult m_6071_(Player player, @NotNull InteractionHand hand) {
      ItemStack stackInHand = player.m_21120_(hand);
      Item itemInHand = stackInHand.m_41720_();
      if (stackInHand.m_41720_() != Items.f_42656_ && itemInHand != Items.f_42655_ && itemInHand != Items.f_42675_) {
         if (this.m_21824_() && this.m_21830_(player)) {
            if (stackInHand.m_204117_(IafItemTags.HEAL_COCKATRICE)) {
               if (this.m_21223_() < this.m_21233_()) {
                  this.m_5634_(8.0F);
                  this.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
                  stackInHand.m_41774_(1);
               }

               return InteractionResult.SUCCESS;
            }

            if (stackInHand.m_41619_()) {
               if (player.m_6144_()) {
                  if (this.hasHomePosition) {
                     this.hasHomePosition = false;
                     player.m_5661_(Component.m_237115_("cockatrice.command.remove_home"), true);
                     return InteractionResult.SUCCESS;
                  }

                  BlockPos pos = this.m_20183_();
                  this.homePos = new HomePosition(pos, this.m_9236_());
                  this.hasHomePosition = true;
                  player.m_5661_(Component.m_237110_("cockatrice.command.new_home", new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), this.homePos.getDimension()}), true);
                  return InteractionResult.SUCCESS;
               }

               this.setCommand(this.getCommand() + 1);
               if (this.getCommand() > 3) {
                  this.setCommand(0);
               }

               player.m_5661_(Component.m_237115_("cockatrice.command." + this.getCommand()), true);
               this.m_5496_(SoundEvents.f_12609_, 1.0F, 1.0F);
               return InteractionResult.SUCCESS;
            }
         }

         return InteractionResult.FAIL;
      } else {
         return super.m_6071_(player, hand);
      }
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity attackTarget = this.m_5448_();
      if (this.m_9236_().m_46791_() == Difficulty.PEACEFUL && attackTarget instanceof Player) {
         this.m_6710_((LivingEntity)null);
      }

      if (this.m_21827_() && this.getCommand() != 1) {
         this.m_21839_(false);
      }

      if (this.m_21827_() && attackTarget != null) {
         this.m_6710_((LivingEntity)null);
      }

      if (attackTarget != null && this.m_7307_(attackTarget)) {
         this.m_6710_((LivingEntity)null);
      }

      if (!this.m_9236_().f_46443_) {
         if (attackTarget != null && attackTarget.m_6084_()) {
            if (this.isStaring() || this.shouldStareAttack(attackTarget)) {
               this.setTargetedEntity(attackTarget.m_19879_());
            }
         } else {
            this.setTargetedEntity(0);
         }
      }

      double dist;
      if (this.getAnimation() == ANIMATION_BITE && attackTarget != null && this.getAnimationTick() == 7) {
         dist = this.m_20280_(attackTarget);
         if (dist < 8.0D) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      double d5;
      if (this.getAnimation() == ANIMATION_JUMPAT && attackTarget != null) {
         dist = this.m_20280_(attackTarget);
         double d0 = attackTarget.m_20185_() - this.m_20185_();
         d5 = attackTarget.m_20189_() - this.m_20189_();
         float leap = Mth.m_14116_((float)(d0 * d0 + d5 * d5));
         if (dist < 4.0D && this.getAnimationTick() > 10) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            if ((double)leap >= 1.0E-4D) {
               attackTarget.m_20256_(attackTarget.m_20184_().m_82520_(d0 / (double)leap * 0.800000011920929D + this.m_20184_().f_82479_ * 0.20000000298023224D, 0.0D, d5 / (double)leap * 0.800000011920929D + this.m_20184_().f_82481_ * 0.20000000298023224D));
            }
         }
      }

      boolean sitting = this.m_21827_();
      if (sitting && this.sitProgress < 20.0F) {
         this.sitProgress += 0.5F;
      } else if (!sitting && this.sitProgress > 0.0F) {
         this.sitProgress -= 0.5F;
      }

      boolean staring = this.isStaring();
      if (staring && this.stareProgress < 20.0F) {
         this.stareProgress += 0.5F;
      } else if (!staring && this.stareProgress > 0.0F) {
         this.stareProgress -= 0.5F;
      }

      if (!this.m_9236_().f_46443_) {
         if (staring) {
            ++this.ticksStaring;
         } else {
            this.ticksStaring = 0;
         }
      }

      if (!this.m_9236_().f_46443_ && staring && (attackTarget == null || this.shouldMelee())) {
         this.setStaring(false);
      }

      if (attackTarget != null) {
         this.m_21563_().m_24950_(attackTarget.m_20185_(), attackTarget.m_20186_() + (double)attackTarget.m_20192_(), attackTarget.m_20189_(), (float)this.m_8085_(), (float)this.m_8132_());
         if (!this.shouldMelee() && attackTarget instanceof Mob) {
            this.forcePreyToLook((Mob)attackTarget);
         }
      }

      boolean blindness = this.m_21023_(MobEffects.f_19610_) || attackTarget != null && attackTarget.m_21023_(MobEffects.f_19610_);
      if (blindness) {
         this.setStaring(false);
      }

      if (!this.m_9236_().f_46443_ && !blindness && attackTarget != null && EntityGorgon.isEntityLookingAt(this, attackTarget, 0.6000000238418579D) && EntityGorgon.isEntityLookingAt(attackTarget, this, 0.6000000238418579D) && !EntityGorgon.isBlindfolded(attackTarget) && !this.shouldMelee()) {
         if (!this.isStaring()) {
            this.setStaring(true);
         } else {
            int attackStrength = this.getFriendsCount(attackTarget);
            if (this.m_9236_().m_46791_() == Difficulty.HARD) {
               ++attackStrength;
            }

            attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 10, 2 + Math.min(1, attackStrength)));
            attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 10, Math.min(4, attackStrength)));
            attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 200, 0));
            if (attackStrength >= 2 && attackTarget.f_19797_ % 40 == 0) {
               attackTarget.m_6469_(this.m_9236_().m_269111_().m_269251_(), (float)(attackStrength - 1));
            }

            attackTarget.m_6703_(this);
            if (!this.m_21824_() && attackTarget instanceof Player) {
               this.setTamingPlayer(attackTarget.m_19879_());
               this.setTamingLevel(this.getTamingLevel() + 1);
               if (this.getTamingLevel() % 100 == 0) {
                  this.m_9236_().m_7605_(this, (byte)46);
               }

               if (this.getTamingLevel() >= 1000) {
                  this.m_9236_().m_7605_(this, (byte)45);
                  if (this.getTamingPlayer() instanceof Player) {
                     this.m_21828_((Player)this.getTamingPlayer());
                  }

                  this.m_6710_((LivingEntity)null);
                  this.setTamingPlayer(0);
                  this.setTargetedEntity(0);
               }
            }
         }
      }

      if (!this.m_9236_().f_46443_ && attackTarget == null && this.m_217043_().m_188503_(300) == 0 && this.getAnimation() == NO_ANIMATION) {
         this.setAnimation(ANIMATION_WATTLESHAKE);
      }

      if (!this.m_9236_().f_46443_) {
         if (this.shouldMelee() && !this.isMeleeMode) {
            this.switchAI(true);
         }

         if (!this.shouldMelee() && this.isMeleeMode) {
            this.switchAI(false);
         }
      }

      if (this.m_9236_().f_46443_ && this.getTargetedEntity() != null && EntityGorgon.isEntityLookingAt(this, this.getTargetedEntity(), 0.6000000238418579D) && EntityGorgon.isEntityLookingAt(this.getTargetedEntity(), this, 0.6000000238418579D) && this.isStaring() && this.hasTargetedEntity()) {
         if (this.clientSideAttackTime < this.getAttackDuration()) {
            ++this.clientSideAttackTime;
         }

         LivingEntity livingEntity = this.getTargetedEntity();
         if (livingEntity != null) {
            this.m_21563_().m_24960_(livingEntity, 90.0F, 90.0F);
            this.m_21563_().m_8128_();
            d5 = (double)this.getAttackAnimationScale(0.0F);
            double d0 = livingEntity.m_20185_() - this.m_20185_();
            double d1 = livingEntity.m_20186_() + (double)(livingEntity.m_20206_() * 0.5F) - (this.m_20186_() + (double)this.m_20192_());
            double d2 = livingEntity.m_20189_() - this.m_20189_();
            double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
            d0 /= d3;
            d1 /= d3;
            d2 /= d3;
            double d4 = this.f_19796_.m_188500_();

            while(d4 < d3) {
               d4 += 1.8D - d5 + this.f_19796_.m_188500_() * (1.7D - d5);
               this.m_9236_().m_7106_(ParticleTypes.f_123811_, this.m_20185_() + d0 * d4, this.m_20186_() + d1 * d4 + (double)this.m_20192_(), this.m_20189_() + d2 * d4, 0.0D, 0.0D, 0.0D);
            }
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   private int getFriendsCount(LivingEntity attackTarget) {
      if (this.m_5448_() == null) {
         return 0;
      } else {
         float dist = (float)IafConfig.cockatriceChickenSearchLength;
         List<EntityCockatrice> list = this.m_9236_().m_45976_(EntityCockatrice.class, this.m_20191_().m_82363_((double)dist, (double)dist, (double)dist));
         int i = 0;
         Iterator var5 = list.iterator();

         while(true) {
            EntityCockatrice cockatrice;
            do {
               do {
                  do {
                     if (!var5.hasNext()) {
                        return i;
                     }

                     cockatrice = (EntityCockatrice)var5.next();
                  } while(cockatrice.m_7306_(this));
               } while(cockatrice.m_5448_() == null);
            } while(cockatrice.m_5448_() != this.m_5448_());

            boolean bothLooking = EntityGorgon.isEntityLookingAt(cockatrice, cockatrice.m_5448_(), 0.6000000238418579D) && EntityGorgon.isEntityLookingAt(cockatrice.m_5448_(), cockatrice, 0.6000000238418579D);
            if (bothLooking) {
               ++i;
            }
         }
      }
   }

   public float getAttackAnimationScale(float f) {
      return ((float)this.clientSideAttackTime + f) / (float)this.getAttackDuration();
   }

   public boolean shouldStareAttack(Entity entity) {
      return this.m_20270_(entity) > 5.0F;
   }

   public int getAttackDuration() {
      return 80;
   }

   private boolean shouldMelee() {
      boolean blindness = this.m_21023_(MobEffects.f_19610_) || this.m_5448_() != null && this.m_5448_().m_21023_(MobEffects.f_19610_);
      if (this.m_5448_() == null) {
         return false;
      } else {
         return (double)this.m_20270_(this.m_5448_()) < 4.0D || ServerEvents.isCockatriceTarget(this.m_5448_()) || blindness || !this.canUseStareOn(this.m_5448_());
      }
   }

   public void m_7023_(@NotNull Vec3 motionVec) {
      if (!this.canMove() && !this.m_20160_()) {
         motionVec = motionVec.m_82542_(0.0D, 1.0D, 0.0D);
      }

      super.m_7023_(motionVec);
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
      return new Animation[]{NO_ANIMATION, ANIMATION_JUMPAT, ANIMATION_WATTLESHAKE, ANIMATION_BITE, ANIMATION_SPEAK, ANIMATION_EAT};
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   public boolean isTargetBlocked(Vec3 target) {
      Vec3 Vector3d = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
      return this.m_9236_().m_45547_(new ClipContext(Vector3d, target, Block.COLLIDER, Fluid.NONE, this)).m_6662_() == Type.MISS;
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.COCKATRICE_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource source) {
      return IafSoundRegistry.COCKATRICE_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.COCKATRICE_DIE;
   }

   public void m_7822_(byte id) {
      if (id == 45) {
         this.playEffect(true);
      } else if (id == 46) {
         this.playEffect(false);
      } else {
         super.m_7822_(id);
      }

   }

   protected void playEffect(boolean play) {
      ParticleOptions enumparticletypes = ParticleTypes.f_123750_;
      if (!play) {
         enumparticletypes = ParticleTypes.f_123798_;
      }

      for(int i = 0; i < 7; ++i) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         this.m_9236_().m_7106_(enumparticletypes, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + 0.5D + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d0, d1, d2);
      }

   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }

   static {
      HEN = SynchedEntityData.m_135353_(EntityCockatrice.class, EntityDataSerializers.f_135035_);
      STARING = SynchedEntityData.m_135353_(EntityCockatrice.class, EntityDataSerializers.f_135035_);
      TARGET_ENTITY = SynchedEntityData.m_135353_(EntityCockatrice.class, EntityDataSerializers.f_135028_);
      TAMING_PLAYER = SynchedEntityData.m_135353_(EntityCockatrice.class, EntityDataSerializers.f_135028_);
      TAMING_LEVEL = SynchedEntityData.m_135353_(EntityCockatrice.class, EntityDataSerializers.f_135028_);
      COMMAND = SynchedEntityData.m_135353_(EntityCockatrice.class, EntityDataSerializers.f_135028_);
   }
}
