package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackPlayers;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIDefendHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIFindHidingSpot;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILeaveHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILookAtTradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAITradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWander;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.MyrmexTrades;
import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.MoveFunction;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexSentinel extends EntityMyrmexBase {
   public static final Animation ANIMATION_GRAB = Animation.create(15);
   public static final Animation ANIMATION_NIBBLE = Animation.create(10);
   public static final Animation ANIMATION_STING = Animation.create(25);
   public static final Animation ANIMATION_SLASH = Animation.create(25);
   public static final ResourceLocation DESERT_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_sentinel_desert");
   public static final ResourceLocation JUNGLE_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_sentinel_jungle");
   private static final ResourceLocation TEXTURE_DESERT = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_sentinel.png");
   private static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_sentinel.png");
   private static final ResourceLocation TEXTURE_DESERT_HIDDEN = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_sentinel_hidden.png");
   private static final ResourceLocation TEXTURE_JUNGLE_HIDDEN = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_sentinel_hidden.png");
   private static final EntityDataAccessor<Boolean> HIDING;
   public float holdingProgress;
   public float hidingProgress;
   public int visibleTicks = 0;
   public int daylightTicks = 0;

   public EntityMyrmexSentinel(EntityType t, Level worldIn) {
      super(t, worldIn);
   }

   protected ItemListing[] getLevel1Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_SENTINEL.get(1) : (ItemListing[])MyrmexTrades.DESERT_SENTINEL.get(1);
   }

   protected ItemListing[] getLevel2Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_SENTINEL.get(2) : (ItemListing[])MyrmexTrades.DESERT_SENTINEL.get(2);
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.isJungle() ? JUNGLE_LOOT : DESERT_LOOT;
   }

   public int m_213860_() {
      return 8;
   }

   public Entity getHeldEntity() {
      return this.m_20197_().isEmpty() ? null : (Entity)this.m_20197_().get(0);
   }

   public void m_8107_() {
      super.m_8107_();
      LivingEntity attackTarget = this.m_5448_();
      if (this.visibleTicks > 0) {
         --this.visibleTicks;
      } else {
         this.visibleTicks = 0;
      }

      if (attackTarget != null) {
         this.visibleTicks = 100;
      }

      if (this.canSeeSky()) {
         ++this.daylightTicks;
      } else {
         this.daylightTicks = 0;
      }

      boolean holding = this.getHeldEntity() != null;
      boolean hiding = this.isHiding() && !this.hasCustomer();
      if (holding || this.isOnResin() || attackTarget != null || this.visibleTicks > 0) {
         this.setHiding(false);
      }

      if (holding && this.holdingProgress < 20.0F) {
         ++this.holdingProgress;
      } else if (!holding && this.holdingProgress > 0.0F) {
         --this.holdingProgress;
      }

      if (hiding) {
         this.m_146922_(this.f_19859_);
      }

      if (hiding && this.hidingProgress < 20.0F) {
         ++this.hidingProgress;
      } else if (!hiding && this.hidingProgress > 0.0F) {
         --this.hidingProgress;
      }

      if (this.getHeldEntity() != null) {
         this.setAnimation(ANIMATION_NIBBLE);
         if (this.getAnimationTick() == 5) {
            this.playBiteSound();
            this.getHeldEntity().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() / 6));
         }
      }

      if (this.getAnimation() == ANIMATION_GRAB && attackTarget != null && this.getAnimationTick() == 7) {
         this.playStingSound();
         if (this.getAttackBounds().m_82381_(attackTarget.m_20191_())) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() / 2));
            if (attackTarget instanceof EntityDragonBase) {
               if (!((EntityDragonBase)attackTarget).isMobDead()) {
                  attackTarget.m_20329_(this);
               }
            } else {
               attackTarget.m_20329_(this);
            }
         }
      }

      if (this.getAnimation() == ANIMATION_SLASH && attackTarget != null && this.getAnimationTick() % 5 == 0 && this.getAnimationTick() <= 20) {
         this.playBiteSound();
         if (this.getAttackBounds().m_82381_(attackTarget.m_20191_())) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() / 4));
         }
      }

      if (this.getAnimation() == ANIMATION_STING && (this.getAnimationTick() == 0 || this.getAnimationTick() == 10)) {
         this.playStingSound();
      }

      if (this.getAnimation() == ANIMATION_STING && attackTarget != null && (this.getAnimationTick() == 6 || this.getAnimationTick() == 16)) {
         double dist = this.m_20280_(attackTarget);
         if (dist < 18.0D) {
            attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
            attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 100, 3));
         }
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new MyrmexAIFindHidingSpot(this));
      this.f_21345_.m_25352_(0, new MyrmexAITradePlayer(this));
      this.f_21345_.m_25352_(0, new MyrmexAILookAtTradePlayer(this));
      this.f_21345_.m_25352_(1, new MyrmexAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(3, new MyrmexAILeaveHive(this, 1.0D));
      this.f_21345_.m_25352_(5, new MyrmexAIWander(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new MyrmexAIDefendHive(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new MyrmexAIAttackPlayers(this));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, LivingEntity.class, 4, true, true, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return entity != null && !EntityMyrmexBase.haveSameHive(EntityMyrmexSentinel.this, entity) && DragonUtils.isAlive(entity) && !(entity instanceof Enemy);
         }
      }));
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HIDING, Boolean.FALSE);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 60.0D).m_22268_(Attributes.f_22279_, 0.35D).m_22268_(Attributes.f_22281_, IafConfig.myrmexBaseAttackStrength * 3.0D).m_22268_(Attributes.f_22277_, 64.0D).m_22268_(Attributes.f_22284_, 12.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.myrmexBaseAttackStrength * 3.0D);
   }

   public ResourceLocation getAdultTexture() {
      if (this.isHiding()) {
         return this.isJungle() ? TEXTURE_JUNGLE_HIDDEN : TEXTURE_DESERT_HIDDEN;
      } else {
         return this.isJungle() ? TEXTURE_JUNGLE : TEXTURE_DESERT;
      }
   }

   public float getModelScale() {
      return 0.8F;
   }

   public int getCasteImportance() {
      return 2;
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128379_("Hiding", this.isHiding());
      tag.m_128405_("DaylightTicks", this.daylightTicks);
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.setHiding(tag.m_128471_("Hiding"));
      this.daylightTicks = tag.m_128451_("DaylightTicks");
   }

   public boolean shouldLeaveHive() {
      return true;
   }

   public boolean shouldEnterHive() {
      return false;
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         this.f_20883_ = this.m_146908_();
         float radius = 1.25F;
         float extraY = 0.35F;
         if (this.getAnimation() == ANIMATION_GRAB) {
            int modTick = Mth.m_14045_(this.getAnimationTick(), 0, 10);
            radius = 3.25F - (float)modTick * 0.2F;
            extraY = (float)modTick * 0.035F;
         }

         float angle = 0.017453292F * this.f_20883_;
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         if (passenger.m_20206_() >= 1.75F) {
            extraY = passenger.m_20206_() - 2.0F;
         }

         passenger.m_6034_(this.m_20185_() + extraX, this.m_20186_() + (double)extraY, this.m_20189_() + extraZ);
      }

   }

   public boolean m_6469_(DamageSource source, float amount) {
      if ((double)amount >= 1.0D && !this.m_20197_().isEmpty() && this.f_19796_.m_188503_(2) == 0) {
         Iterator var3 = this.m_20197_().iterator();

         while(var3.hasNext()) {
            Entity entity = (Entity)var3.next();
            entity.m_8127_();
         }
      }

      this.visibleTicks = 300;
      this.setHiding(false);
      return super.m_6469_(source, amount);
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getGrowthStage() < 2) {
         return false;
      } else if (this.getAnimation() != ANIMATION_STING && this.getAnimation() != ANIMATION_SLASH && this.getAnimation() != ANIMATION_GRAB && this.getHeldEntity() == null) {
         if (this.m_217043_().m_188503_(2) == 0 && entityIn.m_20205_() < 2.0F) {
            this.setAnimation(ANIMATION_GRAB);
         } else {
            this.setAnimation(this.m_217043_().m_188499_() ? ANIMATION_STING : ANIMATION_SLASH);
         }

         this.visibleTicks = 300;
         return true;
      } else {
         return false;
      }
   }

   public boolean needsGaurding() {
      return false;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_PUPA_WIGGLE, ANIMATION_SLASH, ANIMATION_STING, ANIMATION_GRAB, ANIMATION_NIBBLE};
   }

   public boolean canMove() {
      return super.canMove() && this.getHeldEntity() == null && !this.isHiding();
   }

   public boolean shouldRiderSit() {
      return false;
   }

   public boolean isHiding() {
      return (Boolean)this.f_19804_.m_135370_(HIDING);
   }

   public void setHiding(boolean hiding) {
      this.f_19804_.m_135381_(HIDING, hiding);
   }

   public int m_7809_() {
      return 4;
   }

   public boolean m_7826_() {
      return false;
   }

   public boolean m_183595_() {
      return this.m_9236_().f_46443_;
   }

   static {
      HIDING = SynchedEntityData.m_135353_(EntityMyrmexSentinel.class, EntityDataSerializers.f_135035_);
   }
}
