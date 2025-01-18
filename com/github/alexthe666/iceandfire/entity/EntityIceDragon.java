package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.api.event.DragonFireEvent;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.message.MessageDragonSyncFire;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class EntityIceDragon extends EntityDragonBase {
   public static final ResourceLocation FEMALE_LOOT = new ResourceLocation("iceandfire", "entities/dragon/ice_dragon_female");
   public static final ResourceLocation MALE_LOOT = new ResourceLocation("iceandfire", "entities/dragon/ice_dragon_male");
   public static final ResourceLocation SKELETON_LOOT = new ResourceLocation("iceandfire", "entities/dragon/ice_dragon_skeleton");

   public EntityIceDragon(Level worldIn) {
      this((EntityType)IafEntityRegistry.ICE_DRAGON.get(), worldIn);
   }

   public EntityIceDragon(EntityType<?> t, Level worldIn) {
      super(t, worldIn, DragonType.ICE, 1.0D, (double)(1 + IafConfig.dragonAttackDamage), IafConfig.dragonHealth * 0.04D, IafConfig.dragonHealth, 0.15000000596046448D, 0.4000000059604645D);
      ANIMATION_SPEAK = Animation.create(20);
      ANIMATION_BITE = Animation.create(35);
      ANIMATION_SHAKEPREY = Animation.create(65);
      ANIMATION_TAILWHACK = Animation.create(40);
      ANIMATION_FIRECHARGE = Animation.create(25);
      ANIMATION_WINGBLAST = Animation.create(50);
      ANIMATION_ROAR = Animation.create(40);
      ANIMATION_EPIC_ROAR = Animation.create(60);
   }

   protected boolean shouldTarget(Entity entity) {
      if (entity instanceof EntityDragonBase && !this.m_21824_()) {
         return entity.m_6095_() != this.m_6095_() && this.m_20205_() >= entity.m_20205_() && !((EntityDragonBase)entity).isMobDead();
      } else {
         return entity instanceof Player || DragonUtils.isDragonTargetable(entity, IafTagRegistry.ICE_DRAGON_TARGETS) || entity instanceof WaterAnimal || !this.m_21824_() && DragonUtils.isVillager(entity);
      }
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SWIMMING, Boolean.FALSE);
   }

   public String getVariantName(int variant) {
      switch(variant) {
      case 1:
         return "white_";
      case 2:
         return "sapphire_";
      case 3:
         return "silver_";
      default:
         return "blue_";
      }
   }

   public boolean m_6040_() {
      return true;
   }

   public Item getVariantScale(int variant) {
      switch(variant) {
      case 1:
         return (Item)IafItemRegistry.DRAGONSCALES_WHITE.get();
      case 2:
         return (Item)IafItemRegistry.DRAGONSCALES_SAPPHIRE.get();
      case 3:
         return (Item)IafItemRegistry.DRAGONSCALES_SILVER.get();
      default:
         return (Item)IafItemRegistry.DRAGONSCALES_BLUE.get();
      }
   }

   public Item getVariantEgg(int variant) {
      switch(variant) {
      case 1:
         return (Item)IafItemRegistry.DRAGONEGG_WHITE.get();
      case 2:
         return (Item)IafItemRegistry.DRAGONEGG_SAPPHIRE.get();
      case 3:
         return (Item)IafItemRegistry.DRAGONEGG_SILVER.get();
      default:
         return (Item)IafItemRegistry.DRAGONEGG_BLUE.get();
      }
   }

   public boolean m_6063_() {
      return false;
   }

   public void m_7380_(@NotNull CompoundTag compound) {
      super.m_7380_(compound);
      compound.m_128379_("Swimming", this.m_6069_());
      compound.m_128405_("SwimmingTicks", this.ticksSwiming);
   }

   public void m_7378_(@NotNull CompoundTag compound) {
      super.m_7378_(compound);
      this.m_20282_(compound.m_128471_("Swimming"));
      this.ticksSwiming = compound.m_128451_("SwimmingTicks");
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
      if (!this.m_9236_().f_46443_ && this.m_20077_() && this.isAllowedToTriggerFlight() && !this.isModelDead()) {
         this.setHovering(true);
         this.m_21837_(false);
         this.m_21839_(false);
         this.flyHovering = 0;
         this.flyTicks = 0;
      }

      if (!this.m_9236_().f_46443_ && attackTarget != null) {
         if (this.m_20191_().m_82377_((double)(0.0F + this.getRenderSize() * 0.33F), (double)(0.0F + this.getRenderSize() * 0.33F), (double)(0.0F + this.getRenderSize() * 0.33F)).m_82381_(attackTarget.m_20191_())) {
            this.m_7327_(attackTarget);
         }

         if (this.groundAttack == IafDragonAttacks.Ground.FIRE && (this.usingGroundAttack || this.m_20096_())) {
            this.shootIceAtMob(attackTarget);
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

      boolean swimming = this.m_20069_();
      this.prevSwimProgress = this.swimProgress;
      if (swimming && this.swimProgress < 20.0F) {
         this.swimProgress += 0.5F;
      } else if (!swimming && this.swimProgress > 0.0F) {
         this.swimProgress -= 0.5F;
      }

      if (this.m_20069_() && !this.m_6069_() && (!this.isFlying() && !this.isHovering() || this.flyTicks > 100)) {
         this.m_20282_(true);
         this.setHovering(false);
         this.setFlying(false);
         this.flyTicks = 0;
         this.ticksSwiming = 0;
      }

      if ((!this.m_20069_() || this.isHovering() || this.isFlying()) && this.m_6069_()) {
         this.m_20282_(false);
         this.ticksSwiming = 0;
      }

      if (this.m_6069_() && !this.isModelDead()) {
         ++this.ticksSwiming;
         if (this.m_20069_() && (this.ticksSwiming > 4000 || this.m_5448_() != null && this.m_20069_() != this.m_5448_().m_20069_()) && !this.m_6162_() && !this.isHovering() && !this.isFlying()) {
            this.setHovering(true);
            this.m_6135_();
            this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.8D, 0.0D));
            this.m_20282_(false);
         }
      }

      if (!this.m_9236_().f_46443_ && this.m_6688_() == null && this.isHovering() && !this.isFlying() && this.m_20069_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.2D, 0.0D));
      }

      if (this.swimCycle < 48) {
         this.swimCycle += 2;
      } else {
         this.swimCycle = 0;
      }

      if (this.isModelDead() && this.swimCycle != 0) {
         this.swimCycle = 0;
      }

   }

   public void riderShootFire(Entity controller) {
      if (this.m_217043_().m_188503_(5) == 0 && !this.m_6162_()) {
         if (this.getAnimation() != ANIMATION_FIRECHARGE) {
            this.setAnimation(ANIMATION_FIRECHARGE);
         } else if (this.getAnimationTick() == 15) {
            this.m_146922_(this.f_20883_);
            Vec3 headVec = this.getHeadPosition();
            this.m_5496_(IafSoundRegistry.ICEDRAGON_BREATH, 4.0F, 1.0F);
            double d2 = controller.m_20154_().f_82479_;
            double d3 = controller.m_20154_().f_82480_;
            double d4 = controller.m_20154_().f_82481_;
            float inaccuracy = 1.0F;
            d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            EntityDragonIceCharge entitylargefireball = new EntityDragonIceCharge((EntityType)IafEntityRegistry.ICE_DRAGON_CHARGE.get(), this.m_9236_(), this, d2, d3, d4);
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
      } else if (this.isBreathingFire()) {
         if (this.isActuallyBreathingFire()) {
            this.m_146922_(this.f_20883_);
            if (this.f_19797_ % 5 == 0) {
               this.m_5496_(IafSoundRegistry.ICEDRAGON_BREATH, 4.0F, 1.0F);
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

   public boolean m_6914_(LevelReader worldIn) {
      return worldIn.m_45784_(this);
   }

   public void m_20321_(boolean pDownwards) {
      if (this.getDragonStage() < 2) {
         super.m_20321_(pDownwards);
      }

   }

   public void m_6845_(boolean pDownwards) {
      if (this.getDragonStage() < 2) {
         super.m_6845_(pDownwards);
      }

   }

   public void m_7023_(@NotNull Vec3 pTravelVector) {
      LivingEntity rider;
      if (this.m_20069_()) {
         if (this.m_21515_() && this.m_6688_() == null) {
            this.m_19920_(this.m_6113_(), pTravelVector);
            this.m_6478_(MoverType.SELF, this.m_20184_());
            this.m_20256_(this.m_20184_().m_82490_(0.9D));
            if (this.m_5448_() == null) {
            }
         } else if (this.allowLocalMotionControl && this.m_6688_() != null && !this.isHovering() && !this.isFlying()) {
            rider = this.m_6688_();
            float speed = (float)this.m_21133_(Attributes.f_22279_);
            float waterSpeedMod = (float)(0.41999998688697815D + 0.1D * Mth.m_144914_((double)speed, this.minimumSpeed, this.maximumSpeed, 0.0D, 1.5D));
            speed *= waterSpeedMod;
            speed *= rider.m_20142_() ? 1.5F : 1.0F;
            float vertical = 0.0F;
            if (this.isGoingUp() && !this.isGoingDown()) {
               vertical = 1.0F;
            } else if (this.isGoingDown() && !this.isGoingUp()) {
               vertical = -1.0F;
            } else if (this.isGoingUp() && this.isGoingDown() && this.m_6109_()) {
               this.m_20256_(this.m_20184_().m_82542_(1.0D, 0.5D, 1.0D));
            }

            Vec3 travelVector = new Vec3((double)rider.f_20900_, (double)vertical, (double)rider.f_20902_);
            if (this.m_6109_()) {
               this.m_7910_(speed);
               this.m_19920_(this.m_6113_(), travelVector);
               this.m_6478_(MoverType.SELF, this.m_20184_());
               Vec3 currentMotion = this.m_20184_();
               if (this.f_19862_) {
                  currentMotion = new Vec3(currentMotion.f_82479_, 0.2D, currentMotion.f_82481_);
               }

               this.m_20256_(currentMotion.m_82490_(0.9D));
               this.m_267651_(false);
            } else {
               this.m_20256_(Vec3.f_82478_);
            }

            this.m_146872_();
         } else {
            super.m_7023_(pTravelVector);
         }
      } else {
         if (this.allowLocalMotionControl && this.m_6688_() != null && !this.isHovering() && !this.isFlying() && this.m_9236_().m_8055_(this.m_20099_()).m_60819_().m_205070_(FluidTags.f_13131_)) {
            rider = this.m_6688_();
            double forward = (double)rider.f_20902_;
            double strafing = (double)rider.f_20900_;
            double vertical = pTravelVector.f_82480_;
            float speed = (float)this.m_21133_(Attributes.f_22279_);
            float groundSpeedModifier = (float)(1.7999999523162842D * this.getFlightSpeedModifier());
            speed *= groundSpeedModifier;
            forward *= rider.m_20142_() ? 1.2000000476837158D : 1.0D;
            forward *= rider.f_20902_ > 0.0F ? 1.0D : 0.20000000298023224D;
            strafing *= 0.05000000074505806D;
            if (this.m_6109_()) {
               float flyingSpeed = speed * 0.1F;
               this.m_7910_(speed);
               super.m_7023_(new Vec3(strafing, vertical, forward));
               Vec3 currentMotion = this.m_20184_();
               if (this.f_19862_) {
                  currentMotion = new Vec3(currentMotion.f_82479_, 0.2D, currentMotion.f_82481_);
               }

               this.m_20256_(currentMotion.m_82490_(1.0D));
            } else {
               this.m_20256_(Vec3.f_82478_);
            }

            this.m_146872_();
            return;
         }

         super.m_7023_(pTravelVector);
      }

   }

   public ResourceLocation getDeadLootTable() {
      if (this.getDeathStage() >= this.getAgeInDays() / 5 / 2) {
         return SKELETON_LOOT;
      } else {
         return this.isMale() ? MALE_LOOT : FEMALE_LOOT;
      }
   }

   private void shootIceAtMob(LivingEntity entity) {
      if (this.usingGroundAttack && this.groundAttack == IafDragonAttacks.Ground.FIRE || !this.usingGroundAttack && (this.airAttack == IafDragonAttacks.Air.SCORCH_STREAM || this.airAttack == IafDragonAttacks.Air.HOVER_BLAST)) {
         if ((!this.usingGroundAttack || this.m_217043_().m_188503_(5) != 0) && (this.usingGroundAttack || this.airAttack != IafDragonAttacks.Air.HOVER_BLAST)) {
            if (this.isBreathingFire()) {
               if (this.isActuallyBreathingFire()) {
                  this.m_146922_(this.f_20883_);
                  if (this.f_19797_ % 5 == 0) {
                     this.m_5496_(IafSoundRegistry.ICEDRAGON_BREATH, 4.0F, 1.0F);
                  }

                  this.stimulateFire(entity.m_20185_(), entity.m_20186_(), entity.m_20189_(), 1);
                  if (!entity.m_6084_() || entity == null) {
                     this.setBreathingFire(false);
                     this.usingGroundAttack = true;
                  }
               }
            } else {
               this.setBreathingFire(true);
            }
         } else if (this.getAnimation() != ANIMATION_FIRECHARGE) {
            this.setAnimation(ANIMATION_FIRECHARGE);
         } else if (this.getAnimationTick() == 15) {
            this.m_146922_(this.f_20883_);
            Vec3 headVec = this.getHeadPosition();
            double d2 = entity.m_20185_() - headVec.f_82479_;
            double d3 = entity.m_20186_() - headVec.f_82480_;
            double d4 = entity.m_20189_() - headVec.f_82481_;
            float inaccuracy = 1.0F;
            d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
            this.m_5496_(IafSoundRegistry.ICEDRAGON_BREATH, 4.0F, 1.0F);
            EntityDragonIceCharge entitylargefireball = new EntityDragonIceCharge((EntityType)IafEntityRegistry.ICE_DRAGON_CHARGE.get(), this.m_9236_(), this, d2, d3, d4);
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
               this.usingGroundAttack = true;
            }
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

         Vec3 headPos;
         double d2;
         double d3;
         double d4;
         if (syncType > 2 && syncType < 6) {
            if (this.getAnimation() != ANIMATION_FIRECHARGE) {
               this.setAnimation(ANIMATION_FIRECHARGE);
            } else if (this.getAnimationTick() == 20) {
               this.m_146922_(this.f_20883_);
               headPos = this.getHeadPosition();
               d2 = burnX - headPos.f_82479_;
               d3 = burnY - headPos.f_82480_;
               d4 = burnZ - headPos.f_82481_;
               float inaccuracy = 1.0F;
               d2 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               d3 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               d4 += this.f_19796_.m_188583_() * 0.007499999832361937D * (double)inaccuracy;
               this.m_5496_(IafSoundRegistry.FIREDRAGON_BREATH, 4.0F, 1.0F);
               EntityDragonIceCharge entitylargefireball = new EntityDragonIceCharge((EntityType)IafEntityRegistry.ICE_DRAGON_CHARGE.get(), this.m_9236_(), this, d2, d3, d4);
               entitylargefireball.m_6034_(headPos.f_82479_, headPos.f_82480_, headPos.f_82481_);
               if (!this.m_9236_().f_46443_) {
                  this.m_9236_().m_7967_(entitylargefireball);
               }
            }

         } else {
            this.m_21573_().m_26573_();
            this.burnParticleX = burnX;
            this.burnParticleY = burnY;
            this.burnParticleZ = burnZ;
            headPos = this.getHeadPosition();
            d2 = burnX - headPos.f_82479_;
            d3 = burnY - headPos.f_82480_;
            d4 = burnZ - headPos.f_82481_;
            double distance = Math.max(2.5D * this.m_20275_(burnX, burnY, burnZ), 0.0D);
            double conqueredDistance = (double)this.burnProgress / 40.0D * distance;
            int increment = (int)Math.ceil(conqueredDistance / 100.0D);
            int particleCount = this.getDragonStage() <= 3 ? 6 : 3;

            for(int i = 0; (double)i < conqueredDistance; i += increment) {
               double progressX = headPos.f_82479_ + d2 * (double)((float)i / (float)distance);
               double progressY = headPos.f_82480_ + d3 * (double)((float)i / (float)distance);
               double progressZ = headPos.f_82481_ + d4 * (double)((float)i / (float)distance);
               if (this.canPositionBeSeen(progressX, progressY, progressZ)) {
                  if (this.m_9236_().f_46443_ && this.f_19796_.m_188503_(particleCount) == 0) {
                     IceAndFire.PROXY.spawnDragonParticle(EnumParticles.DragonIce, headPos.f_82479_, headPos.f_82480_, headPos.f_82481_, 0.0D, 0.0D, 0.0D, this);
                  }
               } else if (!this.m_9236_().f_46443_) {
                  HitResult result = this.m_9236_().m_45547_(new ClipContext(new Vec3(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_()), new Vec3(progressX, progressY, progressZ), Block.COLLIDER, Fluid.NONE, this));
                  Vec3 vec3 = result.m_82450_();
                  BlockPos pos = BlockPos.m_274446_(vec3);
                  IafDragonDestructionManager.destroyAreaBreath(this.m_9236_(), pos, this);
               }
            }

            if ((double)this.burnProgress >= 40.0D && this.canPositionBeSeen(burnX, burnY, burnZ)) {
               double spawnX = burnX + (double)this.f_19796_.m_188501_() * 3.0D - 1.5D;
               double spawnY = burnY + (double)this.f_19796_.m_188501_() * 3.0D - 1.5D;
               double spawnZ = burnZ + (double)this.f_19796_.m_188501_() * 3.0D - 1.5D;
               if (!this.m_9236_().f_46443_) {
                  IafDragonDestructionManager.destroyAreaBreath(this.m_9236_(), BlockPos.m_274561_(spawnX, spawnY, spawnZ), this);
               }
            }

         }
      }
   }

   public boolean m_6069_() {
      if (this.m_9236_().f_46443_) {
         boolean swimming = (Boolean)this.f_19804_.m_135370_(SWIMMING);
         this.isSwimming = swimming;
         return swimming;
      } else {
         return this.isSwimming;
      }
   }

   public void m_20282_(boolean swimming) {
      this.f_19804_.m_135381_(SWIMMING, swimming);
      if (!this.m_9236_().f_46443_) {
         this.isSwimming = swimming;
      }

   }

   protected SoundEvent m_7515_() {
      return this.isTeen() ? IafSoundRegistry.ICEDRAGON_TEEN_IDLE : (this.m_6125_() ? IafSoundRegistry.ICEDRAGON_ADULT_IDLE : IafSoundRegistry.ICEDRAGON_CHILD_IDLE);
   }

   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return this.isTeen() ? IafSoundRegistry.ICEDRAGON_TEEN_HURT : (this.m_6125_() ? IafSoundRegistry.ICEDRAGON_ADULT_HURT : IafSoundRegistry.ICEDRAGON_CHILD_HURT);
   }

   protected SoundEvent m_5592_() {
      return this.isTeen() ? IafSoundRegistry.ICEDRAGON_TEEN_DEATH : (this.m_6125_() ? IafSoundRegistry.ICEDRAGON_ADULT_DEATH : IafSoundRegistry.ICEDRAGON_CHILD_DEATH);
   }

   public SoundEvent getRoarSound() {
      return this.isTeen() ? IafSoundRegistry.ICEDRAGON_TEEN_ROAR : (this.m_6125_() ? IafSoundRegistry.ICEDRAGON_ADULT_ROAR : IafSoundRegistry.ICEDRAGON_CHILD_ROAR);
   }

   public Animation[] getAnimations() {
      return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityDragonBase.ANIMATION_EAT, EntityDragonBase.ANIMATION_SPEAK, EntityDragonBase.ANIMATION_BITE, EntityDragonBase.ANIMATION_SHAKEPREY, ANIMATION_TAILWHACK, ANIMATION_FIRECHARGE, ANIMATION_WINGBLAST, ANIMATION_ROAR};
   }

   public boolean m_6898_(@Nullable ItemStack stack) {
      return !stack.m_41619_() && stack.m_41720_() != null && stack.m_41720_() == IafItemRegistry.FROST_STEW.get();
   }

   protected void breathFireAtPos(BlockPos burningTarget) {
      if (this.isBreathingFire()) {
         if (this.isActuallyBreathingFire()) {
            this.m_146922_(this.f_20883_);
            if (this.f_19797_ % 5 == 0) {
               this.m_5496_(IafSoundRegistry.ICEDRAGON_BREATH, 4.0F, 1.0F);
            }

            this.stimulateFire((double)((float)burningTarget.m_123341_() + 0.5F), (double)((float)burningTarget.m_123342_() + 0.5F), (double)((float)burningTarget.m_123343_() + 0.5F), 1);
         }
      } else {
         this.setBreathingFire(true);
      }

   }

   public double getFlightSpeedModifier() {
      return super.getFlightSpeedModifier() * (double)(this.m_20069_() ? 0.3F : 1.0F);
   }

   public boolean isAllowedToTriggerFlight() {
      return super.isAllowedToTriggerFlight() && !this.m_20069_();
   }

   protected void spawnDeathParticles() {
      if (this.m_9236_().f_46443_) {
         for(int k = 0; k < 10; ++k) {
            double d2 = this.f_19796_.m_188583_() * 0.02D;
            double d0 = this.f_19796_.m_188583_() * 0.02D;
            double d1 = this.f_19796_.m_188583_() * 0.02D;
            IceAndFire.PROXY.spawnParticle(EnumParticles.Snowflake, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d2, d0, d1);
         }
      }

   }

   protected void spawnBabyParticles() {
      if (this.m_9236_().f_46443_) {
         for(int i = 0; i < 5; ++i) {
            float radiusAdd = (float)i * 0.15F;
            float headPosX = (float)(this.m_20185_() + (double)(1.8F * this.getRenderSize() * (0.3F + radiusAdd) * Mth.m_14089_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
            float headPosZ = (float)(this.m_20189_() + (double)(1.8F * this.getRenderSize() * (0.3F + radiusAdd) * Mth.m_14031_((float)((double)(this.m_146908_() + 90.0F) * 3.141592653589793D / 180.0D))));
            float headPosY = (float)(this.m_20186_() + 0.5D * (double)this.getRenderSize() * 0.30000001192092896D);
            IceAndFire.PROXY.spawnParticle(EnumParticles.DragonIce, (double)headPosX, (double)headPosY, (double)headPosZ, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public int getStartMetaForType() {
      return 4;
   }

   public SoundEvent getBabyFireSound() {
      return SoundEvents.f_11771_;
   }

   public ItemStack getSkull() {
      return new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_ICE.get());
   }

   public boolean useFlyingPathFinder() {
      return (this.isFlying() || this.m_20069_()) && this.m_6688_() == null;
   }

   public Item getSummoningCrystal() {
      return (Item)IafItemRegistry.SUMMONING_CRYSTAL_ICE.get();
   }

   public Item getBloodItem() {
      return (Item)IafItemRegistry.ICE_DRAGON_BLOOD.get();
   }

   public Item getFleshItem() {
      return (Item)IafItemRegistry.ICE_DRAGON_FLESH.get();
   }

   public ItemLike getHeartItem() {
      return (ItemLike)IafItemRegistry.ICE_DRAGON_HEART.get();
   }
}
