package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.ai.EntityAIAttackMeleeNoCooldown;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIFollowSummoner;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAISummonerHurtByTarget;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAISummonerHurtTarget;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWander;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexSwarmer extends EntityMyrmexRoyal {
   private static final EntityDataAccessor<Optional<UUID>> SUMMONER_ID;
   private static final EntityDataAccessor<Integer> TICKS_ALIVE;

   public EntityMyrmexSwarmer(EntityType type, Level worldIn) {
      super(type, worldIn);
      this.f_21342_ = new EntityMyrmexRoyal.FlyMoveHelper(this);
      this.f_21344_ = this.createNavigator(this.m_9236_(), AdvancedPathNavigate.MovementType.FLYING);
      this.switchNavigator(false);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 5.0D).m_22268_(Attributes.f_22279_, 0.35D).m_22268_(Attributes.f_22281_, 2.0D).m_22268_(Attributes.f_22277_, 64.0D).m_22268_(Attributes.f_22284_, 0.0D);
   }

   public int m_213860_() {
      return 0;
   }

   protected void switchNavigator(boolean onLand) {
   }

   protected double attackDistance() {
      return 25.0D;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(1, new MyrmexAIFollowSummoner(this, 1.0D, 10.0F, 5.0F));
      this.f_21345_.m_25352_(2, new EntityMyrmexRoyal.AIFlyAtTarget());
      this.f_21345_.m_25352_(3, new EntityMyrmexRoyal.AIFlyRandom());
      this.f_21345_.m_25352_(4, new EntityAIAttackMeleeNoCooldown(this, 1.0D, true));
      this.f_21345_.m_25352_(5, new MyrmexAIWander(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(2, new MyrmexAISummonerHurtByTarget(this));
      this.f_21346_.m_25352_(3, new MyrmexAISummonerHurtTarget(this));
   }

   protected void m_7324_(Entity entityIn) {
      if (entityIn instanceof EntityMyrmexSwarmer) {
         super.m_7324_(entityIn);
      }

   }

   protected void m_7840_(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(SUMMONER_ID, Optional.empty());
      this.f_19804_.m_135372_(TICKS_ALIVE, 0);
   }

   @Nullable
   public LivingEntity getSummoner() {
      try {
         UUID uuid = this.getSummonerUUID();
         return uuid == null ? null : this.m_9236_().m_46003_(uuid);
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public boolean m_7307_(@NotNull Entity entityIn) {
      if (this.getSummonerUUID() != null && (!(entityIn instanceof EntityMyrmexSwarmer) || ((EntityMyrmexSwarmer)entityIn).getSummonerUUID() != null)) {
         if (entityIn instanceof TamableAnimal) {
            UUID ownerID = ((TamableAnimal)entityIn).m_21805_();
            return ownerID != null && ownerID.equals(this.getSummonerUUID());
         } else {
            return entityIn.m_20148_().equals(this.getSummonerUUID()) || entityIn instanceof EntityMyrmexSwarmer && ((EntityMyrmexSwarmer)entityIn).getSummonerUUID() != null && ((EntityMyrmexSwarmer)entityIn).getSummonerUUID().equals(this.getSummonerUUID());
         }
      } else {
         return false;
      }
   }

   public void setSummonerID(@Nullable UUID uuid) {
      this.f_19804_.m_135381_(SUMMONER_ID, Optional.ofNullable(uuid));
   }

   public void m_7380_(CompoundTag compound) {
      super.m_7380_(compound);
      if (this.getSummonerUUID() == null) {
         compound.m_128359_("SummonerUUID", "");
      } else {
         compound.m_128359_("SummonerUUID", this.getSummonerUUID().toString());
      }

      compound.m_128405_("SummonTicks", this.getTicksAlive());
   }

   public void m_7378_(CompoundTag compound) {
      super.m_7378_(compound);
      String s = "";
      if (compound.m_128403_("SummonerUUID")) {
         s = compound.m_128461_("SummonerUUID");
      }

      if (!s.isEmpty()) {
         try {
            this.setSummonerID(UUID.fromString(s));
         } catch (Throwable var4) {
         }
      }

      this.setTicksAlive(compound.m_128451_("SummonTicks"));
   }

   public void setSummonedBy(Player player) {
      this.setSummonerID(player.m_20148_());
   }

   @Nullable
   public UUID getSummonerUUID() {
      return (UUID)((Optional)this.f_19804_.m_135370_(SUMMONER_ID)).orElse((Object)null);
   }

   public int getTicksAlive() {
      return (Integer)this.f_19804_.m_135370_(TICKS_ALIVE);
   }

   public void setTicksAlive(int ticks) {
      this.f_19804_.m_135381_(TICKS_ALIVE, ticks);
   }

   public void m_8107_() {
      super.m_8107_();
      this.setFlying(true);
      boolean flying = this.isFlying() && !this.m_20096_();
      this.setTicksAlive(this.getTicksAlive() + 1);
      if (flying) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.08D, 0.0D));
         if (this.f_21342_.m_25001_() > this.m_20186_()) {
            this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.08D, 0.0D));
         }
      }

      if (this.m_20096_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, 0.2D, 0.0D));
      }

      if (this.m_5448_() != null) {
         this.f_21342_.m_6849_(this.m_5448_().m_20185_(), this.m_5448_().m_20191_().f_82289_, this.m_5448_().m_20189_(), 1.0D);
         if (this.getAttackBounds().m_82381_(this.m_5448_().m_20191_())) {
            this.setAnimation(this.f_19796_.m_188499_() ? ANIMATION_BITE : ANIMATION_STING);
         }
      }

      if (this.getTicksAlive() > 1800) {
         this.m_6074_();
      }

      double dist;
      if (this.getAnimation() == ANIMATION_BITE && this.m_5448_() != null && this.getAnimationTick() == 6) {
         this.playBiteSound();
         dist = this.m_20280_(this.m_5448_());
         if (dist < this.attackDistance()) {
            this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (this.getAnimation() == ANIMATION_STING && this.m_5448_() != null && this.getAnimationTick() == 6) {
         this.playStingSound();
         dist = this.m_20280_(this.m_5448_());
         if (dist < this.attackDistance()) {
            this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() * 2));
            if (this.m_5448_() != null) {
               this.m_5448_().m_7292_(new MobEffectInstance(MobEffects.f_19614_, 70, 1));
            }
         }
      }

   }

   public int getGrowthStage() {
      return 2;
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return null;
   }

   public float getModelScale() {
      return 0.25F;
   }

   public boolean shouldHaveNormalAI() {
      return false;
   }

   public int getCasteImportance() {
      return 0;
   }

   public boolean isBreedingSeason() {
      return false;
   }

   public boolean shouldAttackEntity(LivingEntity attacker, LivingEntity LivingEntity) {
      return !this.m_7307_(attacker);
   }

   static {
      SUMMONER_ID = SynchedEntityData.m_135353_(EntityMyrmexSwarmer.class, EntityDataSerializers.f_135041_);
      TICKS_ALIVE = SynchedEntityData.m_135353_(EntityMyrmexSwarmer.class, EntityDataSerializers.f_135028_);
   }
}
