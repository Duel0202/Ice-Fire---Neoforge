package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.ai.GorgonAIStareAttack;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IHasCustomizableAttributes;
import com.github.alexthe666.iceandfire.entity.util.IHumanoid;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import javax.annotation.Nullable;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.event.ForgeEventFactory;
import org.jetbrains.annotations.NotNull;

public class EntityGorgon extends Monster implements IAnimatedEntity, IVillagerFear, IAnimalFear, IHumanoid, IHasCustomizableAttributes {
   public static Animation ANIMATION_SCARE;
   public static Animation ANIMATION_HIT;
   private int animationTick;
   private Animation currentAnimation;
   private GorgonAIStareAttack aiStare;
   private MeleeAttackGoal aiMelee;
   private int playerStatueCooldown;

   public EntityGorgon(EntityType<EntityGorgon> type, Level worldIn) {
      super(type, worldIn);
      ANIMATION_SCARE = Animation.create(30);
      ANIMATION_HIT = Animation.create(10);
   }

   public static boolean isEntityLookingAt(LivingEntity looker, LivingEntity seen, double degree) {
      degree *= 1.0D + (double)looker.m_20270_(seen) * 0.1D;
      Vec3 Vector3d = looker.m_20252_(1.0F).m_82541_();
      Vec3 Vector3d1 = new Vec3(seen.m_20185_() - looker.m_20185_(), seen.m_20191_().f_82289_ + (double)seen.m_20192_() - (looker.m_20186_() + (double)looker.m_20192_()), seen.m_20189_() - looker.m_20189_());
      double d0 = Vector3d1.m_82553_();
      Vector3d1 = Vector3d1.m_82541_();
      double d1 = Vector3d.m_82526_(Vector3d1);
      return d1 > 1.0D - degree / d0 && looker.m_142582_(seen) && !isStoneMob(seen);
   }

   public static boolean isStoneMob(LivingEntity mob) {
      return mob instanceof EntityStoneStatue;
   }

   public static boolean isBlindfolded(LivingEntity attackTarget) {
      return attackTarget != null && (attackTarget.m_6844_(EquipmentSlot.HEAD).m_41720_() == IafItemRegistry.BLINDFOLD.get() || attackTarget.m_21023_(MobEffects.f_19610_) || ServerEvents.isBlindMob(attackTarget));
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, IafConfig.gorgonMaxHealth).m_22268_(Attributes.f_22279_, 0.25D).m_22268_(Attributes.f_22281_, 3.0D).m_22268_(Attributes.f_22284_, 1.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22276_).m_22100_(IafConfig.gorgonMaxHealth);
   }

   public boolean isTargetBlocked(Vec3 target) {
      Vec3 Vector3d = new Vec3(this.m_20185_(), this.m_20188_(), this.m_20189_());
      HitResult result = this.m_9236_().m_45547_(new ClipContext(Vector3d, target, Block.COLLIDER, Fluid.NONE, this));
      return result.m_6662_() != Type.MISS;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(1, new FloatGoal(this));
      this.f_21345_.m_25352_(2, new RestrictSunGoal(this));
      this.f_21345_.m_25352_(3, new FleeSunGoal(this, 1.0D));
      this.f_21345_.m_25352_(3, this.aiStare = new GorgonAIStareAttack(this, 1.0D, 0, 15.0F));
      this.f_21345_.m_25352_(3, this.aiMelee = new MeleeAttackGoal(this, 1.0D, false));
      this.f_21345_.m_25352_(5, new WaterAvoidingRandomStrollGoal(this, 1.0D) {
         public boolean m_8036_() {
            this.f_25730_ = 20;
            return super.m_8036_();
         }
      });
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F) {
         public boolean m_8045_() {
            return this.f_25513_ != null && this.f_25513_ instanceof Player && ((Player)this.f_25513_).m_7500_() ? false : super.m_8045_();
         }
      });
      this.f_21345_.m_25352_(6, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, Player.class, 10, false, false, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity.m_6084_();
         }
      }));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, false, new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            return entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity) || entity instanceof IBlacklistedFromStatues && ((IBlacklistedFromStatues)entity).canBeTurnedToStone();
         }
      }));
      this.f_21345_.m_25363_(this.aiMelee);
   }

   public void attackEntityWithRangedAttack(LivingEntity entity) {
      if (!(entity instanceof Mob) && entity instanceof LivingEntity) {
         this.forcePreyToLook(entity);
      }

   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      boolean blindness = this.m_21023_(MobEffects.f_19610_) || this.m_5448_() != null && this.m_5448_().m_21023_(MobEffects.f_19610_) || this.m_5448_() != null && this.m_5448_() instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)this.m_5448_()).canBeTurnedToStone();
      if (blindness && this.f_20919_ == 0) {
         if (this.getAnimation() != ANIMATION_HIT) {
            this.setAnimation(ANIMATION_HIT);
         }

         if (entityIn instanceof LivingEntity) {
            ((LivingEntity)entityIn).m_7292_(new MobEffectInstance(MobEffects.f_19614_, 100, 2, false, true));
         }
      }

      return super.m_7327_(entityIn);
   }

   public void m_6710_(@Nullable LivingEntity LivingEntityIn) {
      super.m_6710_(LivingEntityIn);
      if (LivingEntityIn != null && !this.m_9236_().f_46443_) {
         boolean blindness = this.m_21023_(MobEffects.f_19610_) || LivingEntityIn.m_21023_(MobEffects.f_19610_) || LivingEntityIn instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)LivingEntityIn).canBeTurnedToStone() || isBlindfolded(LivingEntityIn);
         if (blindness && this.f_20919_ == 0) {
            this.f_21345_.m_25352_(3, this.aiMelee);
            this.f_21345_.m_25363_(this.aiStare);
         } else {
            this.f_21345_.m_25352_(3, this.aiStare);
            this.f_21345_.m_25363_(this.aiMelee);
         }
      }

   }

   public int m_213860_() {
      return 30;
   }

   protected void m_6153_() {
      ++this.f_20919_;
      this.f_21363_ = 20;
      int k;
      double d2;
      double d0;
      double d1;
      if (this.m_9236_().f_46443_) {
         for(k = 0; k < 5; ++k) {
            d2 = 0.4D;
            d0 = 0.1D;
            d1 = 0.1D;
            IceAndFire.PROXY.spawnParticle(EnumParticles.Blood, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_(), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d2, d0, d1);
         }
      }

      if (this.f_20919_ >= 200) {
         if (!this.m_9236_().f_46443_ && (this.m_6124_() || this.f_20889_ > 0 && this.m_6149_() && this.m_9236_().m_46469_().m_46207_(GameRules.f_46137_))) {
            k = this.m_213860_();
            k = ForgeEventFactory.getExperienceDrop(this, this.f_20888_, k);

            while(k > 0) {
               int j = ExperienceOrb.m_20782_(k);
               k -= j;
               this.m_9236_().m_7967_(new ExperienceOrb(this.m_9236_(), this.m_20185_(), this.m_20186_(), this.m_20189_(), j));
            }
         }

         this.m_142687_(RemovalReason.KILLED);

         for(k = 0; k < 20; ++k) {
            d2 = this.f_19796_.m_188583_() * 0.02D;
            d0 = this.f_19796_.m_188583_() * 0.02D;
            d1 = this.f_19796_.m_188583_() * 0.02D;
            this.m_9236_().m_7106_(ParticleTypes.f_123796_, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()), this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_(), d2, d0, d1);
         }
      }

   }

   public void m_8107_() {
      super.m_8107_();
      if (this.playerStatueCooldown > 0) {
         --this.playerStatueCooldown;
      }

      LivingEntity attackTarget = this.m_5448_();
      boolean blindness;
      if (attackTarget != null) {
         blindness = this.m_21023_(MobEffects.f_19610_) || attackTarget.m_21023_(MobEffects.f_19610_);
         if (!blindness && this.f_20919_ == 0 && attackTarget instanceof Mob && !(attackTarget instanceof Player)) {
            this.forcePreyToLook(attackTarget);
         }

         if (isEntityLookingAt(attackTarget, this, 0.4D)) {
            this.m_21563_().m_24950_(attackTarget.m_20185_(), attackTarget.m_20186_() + (double)attackTarget.m_20192_(), attackTarget.m_20189_(), (float)this.m_8085_(), (float)this.m_8132_());
         }
      }

      if (attackTarget != null && isEntityLookingAt(this, attackTarget, 0.4D) && isEntityLookingAt(attackTarget, this, 0.4D) && !isBlindfolded(attackTarget)) {
         blindness = this.m_21023_(MobEffects.f_19610_) || attackTarget.m_21023_(MobEffects.f_19610_) || attackTarget instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)attackTarget).canBeTurnedToStone();
         if (!blindness && this.f_20919_ == 0) {
            if (this.getAnimation() != ANIMATION_SCARE) {
               this.m_5496_(IafSoundRegistry.GORGON_ATTACK, 1.0F, 1.0F);
               this.setAnimation(ANIMATION_SCARE);
            }

            if (this.getAnimation() == ANIMATION_SCARE && this.getAnimationTick() > 10 && !this.m_9236_().f_46443_ && this.playerStatueCooldown == 0) {
               EntityStoneStatue statue = EntityStoneStatue.buildStatueEntity(attackTarget);
               statue.m_19890_(attackTarget.m_20185_(), attackTarget.m_20186_(), attackTarget.m_20189_(), attackTarget.m_146908_(), attackTarget.m_146909_());
               if (!this.m_9236_().f_46443_) {
                  this.m_9236_().m_7967_(statue);
               }

               statue.m_146922_(attackTarget.m_146908_());
               statue.m_146922_(attackTarget.m_146908_());
               statue.f_20885_ = attackTarget.m_146908_();
               statue.f_20883_ = attackTarget.m_146908_();
               statue.f_20884_ = attackTarget.m_146908_();
               this.playerStatueCooldown = 40;
               if (attackTarget instanceof Player) {
                  attackTarget.m_6469_(IafDamageRegistry.causeGorgonDamage(this), 2.14748365E9F);
               } else {
                  attackTarget.m_142687_(RemovalReason.KILLED);
               }

               this.m_6710_((LivingEntity)null);
            }
         }
      }

      AnimationHandler.INSTANCE.updateAnimations(this);
   }

   public int m_8132_() {
      return 10;
   }

   public int m_8085_() {
      return 30;
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21641_;
   }

   public void forcePreyToLook(LivingEntity mob) {
      if (mob instanceof Mob) {
         Mob mobEntity = (Mob)mob;
         mobEntity.m_21563_().m_24950_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), (float)mobEntity.m_8085_(), (float)mobEntity.m_8132_());
      }

   }

   public void m_7378_(CompoundTag pCompound) {
      super.m_7378_(pCompound);
      this.setConfigurableAttributes();
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
      return new Animation[]{ANIMATION_SCARE, ANIMATION_HIT};
   }

   @Nullable
   protected SoundEvent m_7515_() {
      return IafSoundRegistry.GORGON_IDLE;
   }

   @Nullable
   protected SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return IafSoundRegistry.GORGON_HURT;
   }

   @Nullable
   protected SoundEvent m_5592_() {
      return IafSoundRegistry.GORGON_DIE;
   }

   public boolean shouldAnimalsFear(Entity entity) {
      return true;
   }

   public boolean m_21532_() {
      return true;
   }

   public boolean m_6785_(double distanceToClosestPlayer) {
      return false;
   }
}
