package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackPlayers;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIDefendHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIForage;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIForageForItems;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILeaveHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILookAtTradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIMoveThroughHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIPickupBabies;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIReEnterHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIStoreBabies;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIStoreItems;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAITradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWander;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.MyrmexTrades;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemMyrmexEgg;
import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
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
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexWorker extends EntityMyrmexBase {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_STING = Animation.create(15);
   public static final ResourceLocation DESERT_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_worker_desert");
   public static final ResourceLocation JUNGLE_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_worker_jungle");
   private static final ResourceLocation TEXTURE_DESERT = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_worker.png");
   private static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_worker.png");
   public boolean keepSearching = true;

   public EntityMyrmexWorker(EntityType<EntityMyrmexWorker> t, Level worldIn) {
      super(t, worldIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0D).m_22268_(Attributes.f_22279_, 0.3D).m_22268_(Attributes.f_22281_, IafConfig.myrmexBaseAttackStrength).m_22268_(Attributes.f_22277_, 32.0D).m_22268_(Attributes.f_22284_, 4.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.myrmexBaseAttackStrength);
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.isJungle() ? JUNGLE_LOOT : DESERT_LOOT;
   }

   public void m_6667_(DamageSource cause) {
      if (!this.m_9236_().f_46443_ && !this.m_21120_(InteractionHand.MAIN_HAND).m_41619_()) {
         this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
         this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
      }

      super.m_6667_(cause);
   }

   public int m_213860_() {
      return 3;
   }

   public boolean isSmallerThanBlock() {
      return true;
   }

   public void m_8107_() {
      super.m_8107_();
      if (!this.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && this.m_21120_(InteractionHand.MAIN_HAND).m_41720_() instanceof ItemMyrmexEgg) {
         boolean isJungle = this.m_21120_(InteractionHand.MAIN_HAND).m_41720_() == IafItemRegistry.MYRMEX_JUNGLE_EGG.get();
         CompoundTag tag = this.m_21120_(InteractionHand.MAIN_HAND).m_41783_();
         int metadata = 0;
         if (tag != null) {
            metadata = tag.m_128451_("EggOrdinal");
         }

         EntityMyrmexEgg egg = new EntityMyrmexEgg((EntityType)IafEntityRegistry.MYRMEX_EGG.get(), this.m_9236_());
         egg.m_20359_(this);
         egg.setJungle(isJungle);
         egg.setMyrmexCaste(metadata);
         if (!this.m_9236_().f_46443_) {
            this.m_9236_().m_7967_(egg);
         }

         egg.m_20329_(this);
         this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
      }

      if (!this.m_20197_().isEmpty()) {
         Iterator var5 = this.m_20197_().iterator();

         while(var5.hasNext()) {
            Entity entity = (Entity)var5.next();
            if (entity instanceof EntityMyrmexBase && ((EntityMyrmexBase)entity).getGrowthStage() >= 2) {
               entity.m_8127_();
            }
         }
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new MyrmexAITradePlayer(this));
      this.f_21345_.m_25352_(0, new MyrmexAILookAtTradePlayer(this));
      this.f_21345_.m_25352_(1, new MyrmexAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(2, new MyrmexAIStoreBabies(this, 1.0D));
      this.f_21345_.m_25352_(3, new MyrmexAIStoreItems(this, 1.0D));
      this.f_21345_.m_25352_(4, new MyrmexAIReEnterHive(this, 1.0D));
      this.f_21345_.m_25352_(4, new MyrmexAILeaveHive(this, 1.0D));
      this.f_21345_.m_25352_(6, new MyrmexAIForage(this, 2));
      this.f_21345_.m_25352_(7, new MyrmexAIMoveThroughHive(this, 1.0D));
      this.f_21345_.m_25352_(8, new MyrmexAIWander(this, 1.0D));
      this.f_21345_.m_25352_(9, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(10, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new MyrmexAIDefendHive(this));
      this.f_21346_.m_25352_(2, new MyrmexAIForageForItems(this));
      this.f_21346_.m_25352_(3, new MyrmexAIPickupBabies(this));
      this.f_21346_.m_25352_(4, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new MyrmexAIAttackPlayers(this));
      this.f_21346_.m_25352_(5, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, true, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return EntityMyrmexWorker.this.m_21205_().m_41619_() && entity != null && !EntityMyrmexBase.haveSameHive(EntityMyrmexWorker.this, entity) && DragonUtils.isAlive(entity) && !(entity instanceof Enemy);
         }
      }));
   }

   public boolean shouldWander() {
      return super.shouldWander() && this.canSeeSky();
   }

   protected ItemListing[] getLevel1Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_WORKER.get(1) : (ItemListing[])MyrmexTrades.DESERT_WORKER.get(1);
   }

   protected ItemListing[] getLevel2Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_WORKER.get(2) : (ItemListing[])MyrmexTrades.DESERT_WORKER.get(2);
   }

   public ResourceLocation getAdultTexture() {
      return this.isJungle() ? TEXTURE_JUNGLE : TEXTURE_DESERT;
   }

   public float getModelScale() {
      return 0.6F;
   }

   public boolean shouldLeaveHive() {
      return !this.holdingSomething();
   }

   public boolean shouldEnterHive() {
      return this.holdingSomething() || !this.m_9236_().m_46461_() && !IafConfig.myrmexHiveIgnoreDaytime;
   }

   public boolean shouldMoveThroughHive() {
      return !this.shouldLeaveHive() && !this.holdingSomething();
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getGrowthStage() < 2) {
         return false;
      } else if (this.getAnimation() != ANIMATION_STING && this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(this.m_217043_().m_188499_() ? ANIMATION_STING : ANIMATION_BITE);
         float f = (float)this.m_21133_(Attributes.f_22281_);
         this.m_21335_(entityIn);
         boolean flag = entityIn.m_6469_(this.m_9236_().m_269111_().m_269333_(this), f);
         if (this.getAnimation() == ANIMATION_STING && flag) {
            this.playStingSound();
            if (entityIn instanceof LivingEntity) {
               ((LivingEntity)entityIn).m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 2));
               this.m_6710_((LivingEntity)entityIn);
            }
         } else {
            this.playBiteSound();
         }

         if (!this.m_9236_().f_46443_ && this.m_217043_().m_188503_(3) == 0 && this.m_21120_(InteractionHand.MAIN_HAND) != ItemStack.f_41583_) {
            this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
            this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
         }

         if (!this.m_20197_().isEmpty()) {
            Iterator var4 = this.m_20197_().iterator();

            while(var4.hasNext()) {
               Entity entity = (Entity)var4.next();
               entity.m_8127_();
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean holdingSomething() {
      return this.getHeldEntity() != null || !this.m_21120_(InteractionHand.MAIN_HAND).m_41619_() || this.m_5448_() != null;
   }

   public boolean holdingBaby() {
      return this.getHeldEntity() != null && (this.getHeldEntity() instanceof EntityMyrmexBase || this.getHeldEntity() instanceof EntityMyrmexEgg);
   }

   public int getCasteImportance() {
      return 0;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_PUPA_WIGGLE, ANIMATION_BITE, ANIMATION_STING};
   }

   public void m_19956_(@NotNull Entity passenger, @NotNull MoveFunction callback) {
      super.m_19956_(passenger, callback);
      if (this.m_20363_(passenger)) {
         this.f_20883_ = this.m_146908_();
         float radius = 1.05F;
         float angle = 0.017453292F * this.f_20883_;
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
         double extraZ = (double)(radius * Mth.m_14089_(angle));
         passenger.m_6034_(this.m_20185_() + extraX, this.m_20186_() + 0.25D, this.m_20189_() + extraZ);
      }

   }

   public boolean m_6469_(DamageSource source, float amount) {
      if ((double)amount >= 1.0D && !this.m_9236_().f_46443_ && this.m_217043_().m_188503_(3) == 0 && this.m_21120_(InteractionHand.MAIN_HAND) != ItemStack.f_41583_) {
         this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
         this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
      }

      if ((double)amount >= 1.0D && !this.m_20197_().isEmpty()) {
         Iterator var3 = this.m_20197_().iterator();

         while(var3.hasNext()) {
            Entity entity = (Entity)var3.next();
            entity.m_8127_();
         }
      }

      return super.m_6469_(source, amount);
   }

   public Entity getHeldEntity() {
      return this.m_20197_().isEmpty() ? null : (Entity)this.m_20197_().get(0);
   }

   public void onPickupItem(ItemEntity itemEntity) {
      Item item = itemEntity.m_32055_().m_41720_();
      if (item == IafItemRegistry.MYRMEX_JUNGLE_RESIN.get() && this.isJungle() || item == IafItemRegistry.MYRMEX_DESERT_RESIN.get() && !this.isJungle()) {
         Player owner = null;

         try {
            if (itemEntity.m_19749_() != null) {
               owner = (Player)itemEntity.m_19749_();
            }
         } catch (Exception var5) {
            IceAndFire.LOGGER.warn("Myrmex picked up resin that wasn't thrown!");
         }

         if (owner != null && this.getHive() != null) {
            this.getHive().modifyPlayerReputation(owner.m_20148_(), 5);
            this.m_5496_(SoundEvents.f_12388_, 1.0F, 1.0F);
            if (!this.m_9236_().f_46443_) {
               this.m_9236_().m_7967_(new ExperienceOrb(this.m_9236_(), owner.m_20185_(), owner.m_20186_(), owner.m_20189_(), 1 + this.f_19796_.m_188503_(3)));
            }
         }
      }

   }

   public int m_7809_() {
      return 0;
   }

   public boolean m_7826_() {
      return false;
   }

   public boolean m_183595_() {
      return this.m_9236_().f_46443_;
   }
}
