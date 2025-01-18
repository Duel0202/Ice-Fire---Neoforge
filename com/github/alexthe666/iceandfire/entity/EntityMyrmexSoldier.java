package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackPlayers;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIDefendHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIEscortEntity;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIFindGaurdingEntity;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILeaveHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILookAtTradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIMoveThroughHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIReEnterHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAITradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWander;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.MyrmexTrades;
import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexSoldier extends EntityMyrmexBase {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_STING = Animation.create(15);
   public static final ResourceLocation DESERT_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_soldier_desert");
   public static final ResourceLocation JUNGLE_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_soldier_jungle");
   private static final ResourceLocation TEXTURE_DESERT = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_soldier.png");
   private static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_soldier.png");
   public EntityMyrmexBase guardingEntity = null;

   public EntityMyrmexSoldier(EntityType<EntityMyrmexSoldier> t, Level worldIn) {
      super(t, worldIn);
   }

   protected ItemListing[] getLevel1Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_SOLDIER.get(1) : (ItemListing[])MyrmexTrades.DESERT_SOLDIER.get(1);
   }

   protected ItemListing[] getLevel2Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_SOLDIER.get(2) : (ItemListing[])MyrmexTrades.DESERT_SOLDIER.get(2);
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.isJungle() ? JUNGLE_LOOT : DESERT_LOOT;
   }

   public int m_213860_() {
      return 5;
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.guardingEntity != null) {
         this.guardingEntity.isBeingGuarded = true;
         this.isEnteringHive = this.guardingEntity.isEnteringHive;
         if (!this.guardingEntity.m_6084_()) {
            this.guardingEntity.isBeingGuarded = false;
            this.guardingEntity = null;
         }
      }

   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new MyrmexAITradePlayer(this));
      this.f_21345_.m_25352_(0, new MyrmexAILookAtTradePlayer(this));
      this.f_21345_.m_25352_(1, new MyrmexAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(2, new MyrmexAIEscortEntity(this, 1.0D));
      this.f_21345_.m_25352_(2, new MyrmexAIReEnterHive(this, 1.0D));
      this.f_21345_.m_25352_(4, new MyrmexAILeaveHive(this, 1.0D));
      this.f_21345_.m_25352_(5, new MyrmexAIMoveThroughHive(this, 1.0D));
      this.f_21345_.m_25352_(6, new MyrmexAIWander(this, 1.0D));
      this.f_21345_.m_25352_(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(8, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new MyrmexAIDefendHive(this));
      this.f_21346_.m_25352_(2, new MyrmexAIFindGaurdingEntity(this));
      this.f_21346_.m_25352_(3, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(4, new MyrmexAIAttackPlayers(this));
      this.f_21346_.m_25352_(4, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, true, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return entity != null && !EntityMyrmexBase.haveSameHive(EntityMyrmexSoldier.this, entity) && DragonUtils.isAlive(entity) && !(entity instanceof Enemy);
         }
      }));
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 40.0D).m_22268_(Attributes.f_22279_, 0.35D).m_22268_(Attributes.f_22281_, IafConfig.myrmexBaseAttackStrength * 2.0D).m_22268_(Attributes.f_22277_, 64.0D).m_22268_(Attributes.f_22284_, 6.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.myrmexBaseAttackStrength * 2.0D);
   }

   public ResourceLocation getAdultTexture() {
      return this.isJungle() ? TEXTURE_JUNGLE : TEXTURE_DESERT;
   }

   public float getModelScale() {
      return 0.8F;
   }

   public int getCasteImportance() {
      return 1;
   }

   public boolean shouldLeaveHive() {
      return false;
   }

   public boolean shouldEnterHive() {
      return this.guardingEntity == null || !this.guardingEntity.canSeeSky() || this.guardingEntity.shouldEnterHive();
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

   public boolean needsGaurding() {
      return false;
   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_PUPA_WIGGLE, ANIMATION_BITE, ANIMATION_STING};
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
