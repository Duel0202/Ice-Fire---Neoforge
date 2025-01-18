package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.api.event.DragonFireEvent;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.message.MessageDragonSyncFire;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class EntityLightningDragon extends EntityDragonBase {
   public static final ResourceLocation FEMALE_LOOT = new ResourceLocation("iceandfire", "entities/dragon/lightning_dragon_female");
   public static final ResourceLocation MALE_LOOT = new ResourceLocation("iceandfire", "entities/dragon/lightning_dragon_male");
   public static final ResourceLocation SKELETON_LOOT = new ResourceLocation("iceandfire", "entities/dragon/lightning_dragon_skeleton");
   private static final EntityDataAccessor<Boolean> HAS_LIGHTNING_TARGET;
   private static final EntityDataAccessor<Float> LIGHTNING_TARGET_X;
   private static final EntityDataAccessor<Float> LIGHTNING_TARGET_Y;
   private static final EntityDataAccessor<Float> LIGHTNING_TARGET_Z;

   public EntityLightningDragon(Level worldIn) {
      this((EntityType)IafEntityRegistry.LIGHTNING_DRAGON.get(), worldIn);
   }

   public EntityLightningDragon(EntityType<?> t, Level worldIn) {
      super(t, worldIn, DragonType.LIGHTNING, 1.0D, (double)(1 + IafConfig.dragonAttackDamage), IafConfig.dragonHealth * 0.04D, IafConfig.dragonHealth, 0.15000000596046448D, 0.4000000059604645D);
      this.m_21441_(BlockPathTypes.DANGER_FIRE, 0.0F);
      this.m_21441_(BlockPathTypes.LAVA, 8.0F);
      ANIMATION_SPEAK = Animation.create(20);
      ANIMATION_BITE = Animation.create(35);
      ANIMATION_SHAKEPREY = Animation.create(65);
      ANIMATION_TAILWHACK = Animation.create(40);
      ANIMATION_FIRECHARGE = Animation.create(30);
      ANIMATION_WINGBLAST = Animation.create(50);
      ANIMATION_ROAR = Animation.create(40);
      ANIMATION_EPIC_ROAR = Animation.create(60);
   }

   public void onStruckByLightning(LightningBolt lightningBolt) {
      this.m_5634_(15.0F);
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HAS_LIGHTNING_TARGET, false);
      this.f_19804_.m_135372_(LIGHTNING_TARGET_X, 0.0F);
      this.f_19804_.m_135372_(LIGHTNING_TARGET_Y, 0.0F);
      this.f_19804_.m_135372_(LIGHTNING_TARGET_Z, 0.0F);
   }

   public int getStartMetaForType() {
      return 8;
   }

   protected boolean shouldTarget(Entity entity) {
      if (entity instanceof EntityDragonBase && !this.m_21824_()) {
         return entity.m_6095_() != this.m_6095_() && this.m_20205_() >= entity.m_20205_() && !((EntityDragonBase)entity).isMobDead();
      } else {
         return entity instanceof Player || DragonUtils.isDragonTargetable(entity, IafTagRegistry.LIGHTNING_DRAGON_TARGETS) || !this.m_21824_() && DragonUtils.isVillager(entity);
      }
   }

   public boolean isTimeToWake() {
      return !this.m_9236_().m_46461_() || this.getCommand() == 2;
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(0, new FloatGoal(this));
   }

   public String getVariantName(int variant) {
      switch(variant) {
      case 1:
         return "amythest_";
      case 2:
         return "copper_";
      case 3:
         return "black_";
      default:
         return "electric_";
      }
   }

   public boolean m_6673_(DamageSource i) {
      if (i.m_19385_().equals(this.m_9236_().m_269111_().m_269548_().m_19385_())) {
         this.m_5634_(15.0F);
         this.m_7292_(new MobEffectInstance(MobEffects.f_19607_, 20, 1));
         return true;
      } else {
         return super.m_6673_(i);
      }
   }

   public Item getVariantScale(int variant) {
      switch(variant) {
      case 1:
         return (Item)IafItemRegistry.DRAGONSCALES_AMYTHEST.get();
      case 2:
         return (Item)IafItemRegistry.DRAGONSCALES_COPPER.get();
      case 3:
         return (Item)IafItemRegistry.DRAGONSCALES_BLACK.get();
      default:
         return (Item)IafItemRegistry.DRAGONSCALES_ELECTRIC.get();
      }
   }

   public Item getVariantEgg(int variant) {
      switch(variant) {
      case 1:
         return (Item)IafItemRegistry.DRAGONEGG_AMYTHEST.get();
      case 2:
         return (Item)IafItemRegistry.DRAGONEGG_COPPER.get();
      case 3:
         return (Item)IafItemRegistry.DRAGONEGG_BLACK.get();
      default:
         return (Item)IafItemRegistry.DRAGONEGG_ELECTRIC.get();
      }
   }

   public void setHasLightningTarget(boolean lightning_target) {
      this.f_19804_.m_135381_(HAS_LIGHTNING_TARGET, lightning_target);
   }

   public boolean hasLightningTarget() {
      return (Boolean)this.f_19804_.m_135370_(HAS_LIGHTNING_TARGET);
   }

   public void setLightningTargetVec(float x, float y, float z) {
      this.f_19804_.m_135381_(LIGHTNING_TARGET_X, x);
      this.f_19804_.m_135381_(LIGHTNING_TARGET_Y, y);
      this.f_19804_.m_135381_(LIGHTNING_TARGET_Z, z);
   }

   public float getLightningTargetX() {
      return (Float)this.f_19804_.m_135370_(LIGHTNING_TARGET_X);
   }

   public float getLightningTargetY() {
      return (Float)this.f_19804_.m_135370_(LIGHTNING_TARGET_Y);
   }

   public float getLightningTargetZ() {
      return (Float)this.f_19804_.m_135370_(LIGHTNING_TARGET_Z);
   }

   public Item getSummoningCrystal() {
      return (Item)IafItemRegistry.SUMMONING_CRYSTAL_LIGHTNING.get();
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      this.m_21563_().m_24960_(entityIn, 30.0F, 30.0F);
      if (!this.isPlayingAttackAnimation()) {
         switch(this.groundAttack) {
         case BITE:
            this.setAnimation(ANIMATION_BITE);
            break;
         case TAIL_WHIP:
            this.setAnimation(ANIMATION_TAILWHACK);
            break;
         case SHAKE_PREY:
            boolean flag = false;
            if ((new Random()).nextInt(2) == 0 && this.isDirectPathBetweenPoints(this, this.m_20182_().m_82520_(0.0D, (double)(this.m_20206_() / 2.0F), 0.0D), entityIn.m_20182_().m_82520_(0.0D, (double)(entityIn.m_20206_() / 2.0F), 0.0D)) && entityIn.m_20205_() < this.m_20205_() * 0.5F && this.m_6688_() == null && this.getDragonStage() > 1 && !(entityIn instanceof EntityDragonBase) && !DragonUtils.isAnimaniaMob(entityIn)) {
               this.setAnimation(ANIMATION_SHAKEPREY);
               flag = true;
               entityIn.m_20329_(this);
            }

            if (!flag) {
               this.groundAttack = IafDragonAttacks.Ground.BITE;
               this.setAnimation(ANIMATION_BITE);
            }
            break;
         case WING_BLAST:
            this.setAnimation(ANIMATION_WINGBLAST);
         }
      }

      return false;
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity attackTarget = this.m_5448_();
      if (!this.m_9236_().f_46443_ && attackTarget != null) {
         if (this.m_20191_().m_82377_((double)(2.5F + this.getRenderSize() * 0.33F), (double)(2.5F + this.getRenderSize() * 0.33F), (double)(2.5F + this.getRenderSize() * 0.33F)).m_82381_(attackTarget.m_20191_())) {
            this.m_7327_(attackTarget);
         }

         if (this.groundAttack == IafDragonAttacks.Ground.FIRE && (this.usingGroundAttack || this.m_20096_())) {
            this.shootFireAtMob(attackTarget);
         }

         if (this.airAttack == IafDragonAttacks.Air.TACKLE && !this.usingGroundAttack && this.m_20280_(attackTarget) < 100.0D) {
            double difX = attackTarget.m_20185_() - this.m_20185_();
            double difY = attackTarget.m_20186_() + (double)attackTarget.m_20206_() - this.m_20186_();
            double difZ = attackTarget.m_20189_() - this.m_20189_();
            this.m_20256_(this.m_20184_().m_82520_(difX * 0.1D, difY * 0.1D, difZ * 0.1D));
            if (this.m_20191_().m_82377_((double)(1.0F + this.getRenderSize() * 0.5F), (double)(1.0F + this.getRenderSize() * 0.5F), (double)(1.0F + this.getRenderSize() * 0.5F)).m_82381_(attackTarget.m_20191_())) {
               this.m_7327_(attackTarget);
               this.usingGroundAttack = true;
               this.randomizeAttacks();
               this.setFlying(false);
               this.setHovering(false);
            }
         }
      }

      if (!this.isBreathingFire()) {
         this.setHasLightningTarget(false);
      }

   }

   protected void breathFireAtPos(BlockPos burningTarget) {
      if (this.isBreathingFire()) {
         if (this.isActuallyBreathingFire()) {
            this.m_146922_(this.f_20883_);
            if (this.fireTicks % 7 == 0) {
               this.m_5496_(IafSoundRegistry.LIGHTNINGDRAGON_BREATH, 4.0F, 1.0F);
            }

            this.stimulateFire((double)((float)burningTarget.m_123341_() + 0.5F), (double)((float)burningTarget.m_123342_() + 0.5F), (double)((float)burningTarget.m_123343_() + 0.5F), 1);
         }
      } else {
         this.setBreathingFire(true);
      }

   }

   public void riderShootFire(Entity controller) {
      if (this.m_217043_().m_188503_(5) == 0 && !this.m_6162_()) {
         if (this.getAnimation() != ANIMATION_FIRECHARGE) {
            this.setAnimation(ANIMATION_FIRECHARGE);
         } else if (this.getAnimationTick() == 20) {
            this.m_146922_(this.f_20883_);
            Vec3 headVec = this.getHeadPosition();
            this.m_5496_(IafSoundRegistry.LIGHTNINGDRAGON_BREATH_CRACKLE, 4.0F, 1.0F);
            double d2 = controller.m_20154_().f_82479_;
            double d3 = controller.m_20154_().f_82480_;
            double d4 = controller.m_20154_().f_82481_;
            float inaccuracy = 1.0F;
            d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            EntityDragonLightningCharge entitylargefireball = new EntityDragonLightningCharge((EntityType)IafEntityRegistry.LIGHTNING_DRAGON_CHARGE.get(), this.m_9236_(), this, d2, d3, d4);
            entitylargefireball.m_6034_(headVec.f_82479_, headVec.f_82480_, headVec.f_82481_);
            if (!this.m_9236_().f_46443_) {
               this.m_9236_().m_7967_(entitylargefireball);
            }
         }
      } else if (this.isBreathingFire()) {
         if (this.isActuallyBreathingFire()) {
            this.m_146922_(this.f_20883_);
            if (this.fireTicks % 7 == 0) {
               this.m_5496_(IafSoundRegistry.LIGHTNINGDRAGON_BREATH, 4.0F, 1.0F);
            }

            HitResult mop = this.rayTraceRider(controller, (double)(10 * this.getDragonStage()), 1.0F);
            if (mop != null) {
               this.stimulateFire(mop.m_82450_().f_82479_, mop.m_82450_().f_82480_, mop.m_82450_().f_82481_, 1);
            }
         }
      } else {
         this.setBreathingFire(true);
      }

   }

   public Item getBloodItem() {
      return (Item)IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get();
   }

   public Item getFleshItem() {
      return (Item)IafItemRegistry.LIGHTNING_DRAGON_FLESH.get();
   }

   public ItemLike getHeartItem() {
      return (ItemLike)IafItemRegistry.LIGHTNING_DRAGON_HEART.get();
   }

   public ResourceLocation getDeadLootTable() {
      if (this.getDeathStage() >= this.getAgeInDays() / 5 / 2) {
         return SKELETON_LOOT;
      } else {
         return this.isMale() ? MALE_LOOT : FEMALE_LOOT;
      }
   }

   private void shootFireAtMob(LivingEntity entity) {
      if (this.usingGroundAttack && this.groundAttack == IafDragonAttacks.Ground.FIRE || !this.usingGroundAttack && (this.airAttack == IafDragonAttacks.Air.SCORCH_STREAM || this.airAttack == IafDragonAttacks.Air.HOVER_BLAST)) {
         if ((!this.usingGroundAttack || this.m_217043_().m_188503_(5) != 0) && (this.usingGroundAttack || this.airAttack != IafDragonAttacks.Air.HOVER_BLAST)) {
            if (this.isBreathingFire()) {
               if (this.isActuallyBreathingFire()) {
                  this.m_146922_(this.f_20883_);
                  if (this.f_19797_ % 5 == 0) {
                     this.m_5496_(IafSoundRegistry.LIGHTNINGDRAGON_BREATH, 4.0F, 1.0F);
                  }

                  this.stimulateFire(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 1);
                  if (!entity.m_6084_() || entity == null) {
                     this.setBreathingFire(false);
                     this.randomizeAttacks();
                  }
               }
            } else {
               this.setBreathingFire(true);
            }
         } else if (this.getAnimation() != ANIMATION_FIRECHARGE) {
            this.setAnimation(ANIMATION_FIRECHARGE);
         } else if (this.getAnimationTick() == 20) {
            this.m_146922_(this.f_20883_);
            Vec3 headVec = this.getHeadPosition();
            double d2 = entity.m_20185_() - headVec.f_82479_;
            double d3 = entity.m_20186_() - headVec.f_82480_;
            double d4 = entity.m_20189_() - headVec.f_82481_;
            float inaccuracy = 1.0F;
            d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            this.m_5496_(IafSoundRegistry.LIGHTNINGDRAGON_BREATH, 4.0F, 1.0F);
            EntityDragonLightningCharge entitylargefireball = new EntityDragonLightningCharge((EntityType)IafEntityRegistry.LIGHTNING_DRAGON_CHARGE.get(), this.m_9236_(), this, d2, d3, d4);
            float var10000;
            if (this.m_6162_()) {
               var10000 = 0.4F;
            } else {
               var10000 = this.m_6125_() ? 1.3F : 0.8F;
            }

            entitylargefireball.m_6034_(headVec.f_82479_, headVec.f_82480_, headVec.f_82481_);
            if (!this.m_9236_().f_46443_) {
               this.m_9236_().m_7967_(entitylargefireball);
            }

            if (!entity.m_6084_() || entity == null) {
               this.setBreathingFire(false);
            }

            this.randomizeAttacks();
         }
      }

      this.m_21391_(entity, 360.0F, 360.0F);
   }

   public void stimulateFire(double burnX, double burnY, double burnZ, int syncType) {
      if (!MinecraftForge.EVENT_BUS.post(new DragonFireEvent(this, burnX, burnY, burnZ))) {
         if (syncType == 1 && !this.m_9236_().f_46443_) {
            IceAndFire.sendMSGToAll(new MessageDragonSyncFire(this.m_19879_(), burnX, burnY, burnZ, 0));
         }

         if (syncType == 2 && this.m_9236_().f_46443_) {
            IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageDragonSyncFire(this.m_19879_(), burnX, burnY, burnZ, 0));
         }

         if (syncType == 3 && !this.m_9236_().f_46443_) {
            IceAndFire.sendMSGToAll(new MessageDragonSyncFire(this.m_19879_(), burnX, burnY, burnZ, 5));
         }

         if (syncType == 4 && this.m_9236_().f_46443_) {
            IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageDragonSyncFire(this.m_19879_(), burnX, burnY, burnZ, 5));
         }

         Vec3 headVec;
         double d2;
         double d3;
         double d4;
         float inaccuracy;
         if (syncType > 2 && syncType < 6) {
            if (this.getAnimation() != ANIMATION_FIRECHARGE) {
               this.setAnimation(ANIMATION_FIRECHARGE);
            } else if (this.getAnimationTick() == 20) {
               this.m_146922_(this.f_20883_);
               headVec = this.getHeadPosition();
               d2 = burnX - headVec.f_82479_;
               d3 = burnY - headVec.f_82480_;
               d4 = burnZ - headVec.f_82481_;
               inaccuracy = 1.0F;
               d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               this.m_5496_(IafSoundRegistry.LIGHTNINGDRAGON_BREATH_CRACKLE, 4.0F, 1.0F);
               EntityDragonLightningCharge entitylargefireball = new EntityDragonLightningCharge((EntityType)IafEntityRegistry.LIGHTNING_DRAGON_CHARGE.get(), this.m_9236_(), this, d2, d3, d4);
               float var10000;
               if (this.m_6162_()) {
                  var10000 = 0.4F;
               } else {
                  var10000 = this.m_6125_() ? 1.3F : 0.8F;
               }

               entitylargefireball.m_6034_(headVec.f_82479_, headVec.f_82480_, headVec.f_82481_);
               if (!this.m_9236_().f_46443_) {
                  this.m_9236_().m_7967_(entitylargefireball);
               }
            }

         } else {
            this.burnParticleX = burnX;
            this.burnParticleY = burnY;
            this.burnParticleZ = burnZ;
            headVec = this.getHeadPosition();
            d2 = burnX - headVec.f_82479_;
            d3 = burnY - headVec.f_82480_;
            d4 = burnZ - headVec.f_82481_;
            inaccuracy = Mth.m_14036_(this.getRenderSize() * 0.08F, 0.55F, 3.0F);
            double distance = Math.max(2.5D * this.m_20275_(burnX, burnY, burnZ), 0.0D);
            double conqueredDistance = (double)this.burnProgress / 40.0D * distance;
            int increment = (int)Math.ceil(conqueredDistance / 100.0D);

            for(int i = 0; (double)i < conqueredDistance; i += increment) {
               double progressX = headVec.f_82479_ + d2 * (double)((float)i / (float)distance);
               double progressY = headVec.f_82480_ + d3 * (double)((float)i / (float)distance);
               double progressZ = headVec.f_82481_ + d4 * (double)((float)i / (float)distance);
               if (this.canPositionBeSeen(progressX, progressY, progressZ)) {
                  this.setHasLightningTarget(true);
                  this.setLightningTargetVec((float)burnX, (float)burnY, (float)burnZ);
               } else if (!this.m_9236_().f_46443_) {
                  HitResult result = this.m_9236_().m_45547_(new ClipContext(new Vec3(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_()), new Vec3(progressX, progressY, progressZ), Block.COLLIDER, Fluid.NONE, this));
                  Vec3 vec3 = result.m_82450_();
                  BlockPos pos = BlockPos.m_274446_(vec3);
                  IafDragonDestructionManager.destroyAreaBreath(this.m_9236_(), pos, this);
                  this.setHasLightningTarget(true);
                  this.setLightningTargetVec((float)result.m_82450_().f_82479_, (float)result.m_82450_().f_82480_, (float)result.m_82450_().f_82481_);
               }
            }

            if ((double)this.burnProgress >= 40.0D && this.canPositionBeSeen(burnX, burnY, burnZ)) {
               double spawnX = burnX + (double)this.f_19796_.m_188501_() * 3.0D - 1.5D;
               double spawnY = burnY + (double)this.f_19796_.m_188501_() * 3.0D - 1.5D;
               double spawnZ = burnZ + (double)this.f_19796_.m_188501_() * 3.0D - 1.5D;
               this.setHasLightningTarget(true);
               this.setLightningTargetVec((float)spawnX, (float)spawnY, (float)spawnZ);
               if (!this.m_9236_().f_46443_) {
                  IafDragonDestructionManager.destroyAreaBreath(this.m_9236_(), BlockPos.m_274561_(spawnX, spawnY, spawnZ), this);
               }
            }

         }
      }
   }

   protected SoundEvent m_7515_() {
      return this.isTeen() ? IafSoundRegistry.LIGHTNINGDRAGON_TEEN_IDLE : (this.m_6125_() ? IafSoundRegistry.LIGHTNINGDRAGON_ADULT_IDLE : IafSoundRegistry.LIGHTNINGDRAGON_CHILD_IDLE);
   }

   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return this.isTeen() ? IafSoundRegistry.LIGHTNINGDRAGON_TEEN_HURT : (this.m_6125_() ? IafSoundRegistry.LIGHTNINGDRAGON_ADULT_HURT : IafSoundRegistry.LIGHTNINGDRAGON_CHILD_HURT);
   }

   protected SoundEvent m_5592_() {
      return this.isTeen() ? IafSoundRegistry.LIGHTNINGDRAGON_TEEN_DEATH : (this.m_6125_() ? IafSoundRegistry.LIGHTNINGDRAGON_ADULT_DEATH : IafSoundRegistry.LIGHTNINGDRAGON_CHILD_DEATH);
   }

   public SoundEvent getRoarSound() {
      return this.isTeen() ? IafSoundRegistry.LIGHTNINGDRAGON_TEEN_ROAR : (this.m_6125_() ? IafSoundRegistry.LIGHTNINGDRAGON_ADULT_ROAR : IafSoundRegistry.LIGHTNINGDRAGON_CHILD_ROAR);
   }

   public Animation[] getAnimations() {
      return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityDragonBase.ANIMATION_EAT, EntityDragonBase.ANIMATION_SPEAK, EntityDragonBase.ANIMATION_BITE, EntityDragonBase.ANIMATION_SHAKEPREY, ANIMATION_TAILWHACK, ANIMATION_FIRECHARGE, ANIMATION_WINGBLAST, ANIMATION_ROAR, ANIMATION_EPIC_ROAR};
   }

   public boolean m_6898_(ItemStack stack) {
      return !stack.m_41619_() && stack.m_41720_() != null && stack.m_41720_() == IafItemRegistry.LIGHTNING_STEW.get();
   }

   protected void spawnDeathParticles() {
      for(int k = 0; k < 3; ++k) {
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         if (this.m_9236_().f_46443_) {
            this.m_9236_().m_7106_(ParticleTypes.f_123761_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d2, d0, d1);
         }
      }

   }

   protected void spawnBabyParticles() {
      for(int i = 0; i < 5; ++i) {
         float radiusAdd = (float)i * 0.15F;
         float headPosX = (float)(this.m_20185_() + (double)(1.8F * this.getRenderSize() * (0.3F + radiusAdd) * Mth.m_14089_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
         float headPosZ = (float)(this.m_20186_() + (double)(1.8F * this.getRenderSize() * (0.3F + radiusAdd) * Mth.m_14031_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
         float headPosY = (float)(this.m_20189_() + 0.5D * (double)this.getRenderSize() * 0.30000001192092896D);
         this.m_9236_().m_7106_(ParticleTypes.f_123755_, (double)headPosX, (double)headPosY, (double)headPosZ, 0.0D, 0.0D, 0.0D);
      }

   }

   public ItemStack getSkull() {
      return new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_LIGHTNING.get());
   }

   static {
      HAS_LIGHTNING_TARGET = SynchedEntityData.m_135353_(EntityLightningDragon.class, EntityDataSerializers.f_135035_);
      LIGHTNING_TARGET_X = SynchedEntityData.m_135353_(EntityLightningDragon.class, EntityDataSerializers.f_135029_);
      LIGHTNING_TARGET_Y = SynchedEntityData.m_135353_(EntityLightningDragon.class, EntityDataSerializers.f_135029_);
      LIGHTNING_TARGET_Z = SynchedEntityData.m_135353_(EntityLightningDragon.class, EntityDataSerializers.f_135029_);
   }
}
