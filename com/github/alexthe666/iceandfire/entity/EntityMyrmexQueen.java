package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackMelee;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIAttackPlayers;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIDefendHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAILookAtTradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIReEnterHive;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAITradePlayer;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexAIWanderHiveCenter;
import com.github.alexthe666.iceandfire.entity.ai.MyrmexQueenAIWander;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.entity.util.MyrmexTrades;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.google.common.base.Predicate;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexQueen extends EntityMyrmexBase {
   public static final Animation ANIMATION_BITE = Animation.create(15);
   public static final Animation ANIMATION_STING = Animation.create(15);
   public static final Animation ANIMATION_EGG = Animation.create(20);
   public static final Animation ANIMATION_DIGNEST = Animation.create(45);
   public static final ResourceLocation DESERT_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_queen_desert");
   public static final ResourceLocation JUNGLE_LOOT = new ResourceLocation("iceandfire", "entities/myrmex_queen_jungle");
   private static final ResourceLocation TEXTURE_DESERT = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_desert_queen.png");
   private static final ResourceLocation TEXTURE_JUNGLE = new ResourceLocation("iceandfire:textures/models/myrmex/myrmex_jungle_queen.png");
   private static final EntityDataAccessor<Boolean> HASMADEHOME;
   private int eggTicks = 0;

   public EntityMyrmexQueen(EntityType<EntityMyrmexQueen> t, Level worldIn) {
      super(t, worldIn);
   }

   @Nullable
   protected ResourceLocation m_7582_() {
      return this.isJungle() ? JUNGLE_LOOT : DESERT_LOOT;
   }

   public int m_213860_() {
      return 20;
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(HASMADEHOME, Boolean.TRUE);
   }

   protected ItemListing[] getLevel1Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_QUEEN.get(1) : (ItemListing[])MyrmexTrades.DESERT_QUEEN.get(1);
   }

   protected ItemListing[] getLevel2Trades() {
      return this.isJungle() ? (ItemListing[])MyrmexTrades.JUNGLE_QUEEN.get(2) : (ItemListing[])MyrmexTrades.DESERT_QUEEN.get(2);
   }

   public void m_6593_(Component name) {
      if (this.getHive() != null && !this.getHive().colonyName.equals(name.m_214077_())) {
         this.getHive().colonyName = name.getString();
      }

      super.m_6593_(name);
   }

   public void m_7380_(CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("EggTicks", this.eggTicks);
      tag.m_128379_("MadeHome", this.hasMadeHome());
   }

   public void m_7378_(CompoundTag tag) {
      super.m_7378_(tag);
      this.eggTicks = tag.m_128451_("EggTicks");
      this.setMadeHome(tag.m_128471_("MadeHome"));
   }

   public boolean hasMadeHome() {
      return (Boolean)this.f_19804_.m_135370_(HASMADEHOME);
   }

   public void setMadeHome(boolean madeHome) {
      this.f_19804_.m_135381_(HASMADEHOME, madeHome);
   }

   public void m_8107_() {
      super.m_8107_();
      if (this.getAnimation() == ANIMATION_DIGNEST) {
         this.spawnGroundEffects(3.0F);
      }

      if (this.getHive() != null) {
         this.getHive().tick(0, this.m_9236_());
      }

      if (this.hasMadeHome() && this.getGrowthStage() >= 2 && !this.canSeeSky()) {
         ++this.eggTicks;
      } else if (this.canSeeSky()) {
         this.setAnimation(ANIMATION_DIGNEST);
         if (this.getAnimationTick() == 42) {
            int down = Math.max(15, this.m_20183_().m_123342_() - 20 + this.m_217043_().m_188503_(10));
            BlockPos genPos = new BlockPos(this.m_146903_(), down, this.m_146907_());
            if (!MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this, (double)genPos.m_123341_(), (double)genPos.m_123342_(), (double)genPos.m_123343_()))) {
               WorldGenMyrmexHive hiveGen = new WorldGenMyrmexHive(true, this.isJungle(), NoneFeatureConfiguration.f_67815_);
               if (!this.m_9236_().f_46443_ && this.m_9236_() instanceof ServerLevel) {
                  hiveGen.placeSmallGen((ServerLevel)this.m_9236_(), this.m_217043_(), genPos);
               }

               this.setMadeHome(true);
               this.m_7678_((double)genPos.m_123341_(), (double)down, (double)genPos.m_123343_(), 0.0F, 0.0F);
               this.m_7292_(new MobEffectInstance(MobEffects.f_19609_, 30));
               this.setHive(hiveGen.hive);

               for(int i = 0; i < 3; ++i) {
                  EntityMyrmexWorker worker = new EntityMyrmexWorker((EntityType)IafEntityRegistry.MYRMEX_WORKER.get(), this.m_9236_());
                  worker.m_20359_(this);
                  worker.setHive(this.getHive());
                  worker.setJungleVariant(this.isJungle());
                  if (!this.m_9236_().f_46443_) {
                     this.m_9236_().m_7967_(worker);
                  }
               }

               return;
            }
         }
      }

      float f;
      if (!this.m_9236_().f_46443_ && this.eggTicks > IafConfig.myrmexPregnantTicks && this.getHive() == null || !this.m_9236_().f_46443_ && this.getHive() != null && this.getHive().repopulate() && this.eggTicks > IafConfig.myrmexPregnantTicks) {
         float radius = -5.25F;
         f = 0.017453292F * this.f_20883_;
         double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)f)));
         double extraZ = (double)(radius * Mth.m_14089_(f));
         BlockPos eggPos = BlockPos.m_274561_(this.m_20185_() + extraX, this.m_20186_() + 0.75D, this.m_20189_() + extraZ);
         if (this.m_9236_().m_46859_(eggPos)) {
            this.setAnimation(ANIMATION_EGG);
            if (this.getAnimationTick() == 10) {
               EntityMyrmexEgg egg = new EntityMyrmexEgg((EntityType)IafEntityRegistry.MYRMEX_EGG.get(), this.m_9236_());
               egg.setJungle(this.isJungle());
               int caste = getRandomCaste(this.m_9236_(), this.m_217043_(), this.getHive() == null || this.getHive().reproduces);
               egg.setMyrmexCaste(caste);
               egg.m_7678_(this.m_20185_() + extraX, this.m_20186_() + 0.75D, this.m_20189_() + extraZ, 0.0F, 0.0F);
               if (this.getHive() != null) {
                  egg.hiveUUID = this.getHive().hiveUUID;
               }

               if (!this.m_9236_().f_46443_) {
                  this.m_9236_().m_7967_(egg);
               }

               this.eggTicks = 0;
            }
         }
      }

      if (this.getAnimation() == ANIMATION_BITE && this.m_5448_() != null && this.getAnimationTick() == 6) {
         this.playBiteSound();
         if (this.getAttackBounds().m_82381_(this.m_5448_().m_20191_())) {
            this.m_5448_().m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_()));
         }
      }

      if (this.getAnimation() == ANIMATION_STING && this.getAnimationTick() == 0) {
         this.playStingSound();
      }

      if (this.getAnimation() == ANIMATION_STING && this.m_5448_() != null && this.getAnimationTick() == 6 && this.getAttackBounds().m_82381_(this.m_5448_().m_20191_())) {
         LivingEntity attackTarget = this.m_5448_();
         attackTarget.m_6469_(this.m_9236_().m_269111_().m_269333_(this), (float)((int)this.m_21051_(Attributes.f_22281_).m_22135_() * 2));
         attackTarget.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 200, 2));
         attackTarget.f_19812_ = true;
         f = Mth.m_14116_(0.5F);
         attackTarget.m_20256_(attackTarget.m_20184_().m_82542_(0.5D, 1.0D, 0.5D));
         attackTarget.m_20256_(attackTarget.m_20184_().m_82520_(-0.5D / (double)f * 4.0D, 1.0D, -0.5D / (double)f * 4.0D));
         if (attackTarget.m_20096_()) {
            attackTarget.m_20256_(attackTarget.m_20184_().m_82520_(0.0D, 0.4D, 0.0D));
         }
      }

   }

   public boolean m_6673_(@NotNull DamageSource source) {
      return super.m_6673_(source) || this.getAnimation() == ANIMATION_DIGNEST;
   }

   protected void m_8099_() {
      this.f_21345_.m_25352_(0, new FloatGoal(this));
      this.f_21345_.m_25352_(0, new MyrmexAITradePlayer(this));
      this.f_21345_.m_25352_(0, new MyrmexAILookAtTradePlayer(this));
      this.f_21345_.m_25352_(1, new MyrmexAIAttackMelee(this, 1.0D, true));
      this.f_21345_.m_25352_(3, new MyrmexAIReEnterHive(this, 1.0D));
      this.f_21345_.m_25352_(4, new MyrmexAIWanderHiveCenter(this, 1.0D));
      this.f_21345_.m_25352_(5, new MyrmexQueenAIWander(this, 1.0D));
      this.f_21345_.m_25352_(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.f_21345_.m_25352_(7, new RandomLookAroundGoal(this));
      this.f_21346_.m_25352_(1, new MyrmexAIDefendHive(this));
      this.f_21346_.m_25352_(2, new HurtByTargetGoal(this, new Class[0]));
      this.f_21346_.m_25352_(3, new MyrmexAIAttackPlayers(this));
      this.f_21346_.m_25352_(3, new NearestAttackableTargetGoal(this, LivingEntity.class, 10, true, true, new Predicate<LivingEntity>() {
         public boolean apply(@Nullable LivingEntity entity) {
            return entity != null && !EntityMyrmexBase.haveSameHive(EntityMyrmexQueen.this, entity) && DragonUtils.isAlive(entity) && !(entity instanceof Enemy);
         }
      }));
   }

   public void fall(float distance, float damageMultiplier) {
   }

   public boolean isInHive() {
      if (this.getHive() != null) {
         Iterator var1 = this.getHive().getAllRooms().iterator();

         while(var1.hasNext()) {
            BlockPos pos = (BlockPos)var1.next();
            if (this.isCloseEnoughToTarget(MyrmexHive.getGroundedPos(this.m_9236_(), pos), 300.0D)) {
               return true;
            }
         }
      }

      return false;
   }

   public boolean shouldMoveThroughHive() {
      return false;
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 120.0D).m_22268_(Attributes.f_22279_, 0.2D).m_22268_(Attributes.f_22281_, IafConfig.myrmexBaseAttackStrength * 3.5D).m_22268_(Attributes.f_22277_, 128.0D).m_22268_(Attributes.f_22284_, 15.0D);
   }

   public void setConfigurableAttributes() {
      this.m_21051_(Attributes.f_22281_).m_22100_(IafConfig.myrmexBaseAttackStrength * 3.5D);
   }

   public ResourceLocation getAdultTexture() {
      return this.isJungle() ? TEXTURE_JUNGLE : TEXTURE_DESERT;
   }

   public float getModelScale() {
      return 1.75F;
   }

   public int getCasteImportance() {
      return 3;
   }

   public boolean shouldLeaveHive() {
      return false;
   }

   public boolean shouldEnterHive() {
      return true;
   }

   public boolean m_7327_(@NotNull Entity entityIn) {
      if (this.getGrowthStage() < 2) {
         return false;
      } else if (this.getAnimation() != ANIMATION_STING && this.getAnimation() != ANIMATION_BITE) {
         this.setAnimation(this.m_217043_().m_188499_() ? ANIMATION_STING : ANIMATION_BITE);
         if (!this.m_9236_().f_46443_ && this.m_217043_().m_188503_(3) == 0 && this.m_21120_(InteractionHand.MAIN_HAND) != ItemStack.f_41583_) {
            this.m_5552_(this.m_21120_(InteractionHand.MAIN_HAND), 0.0F);
            this.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
         }

         if (!this.m_20197_().isEmpty()) {
            Iterator var2 = this.m_20197_().iterator();

            while(var2.hasNext()) {
               Entity entity = (Entity)var2.next();
               entity.m_8127_();
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean canMove() {
      return super.canMove() && this.hasMadeHome();
   }

   public void spawnGroundEffects(float size) {
      for(int i = 0; (float)i < size * 3.0F; ++i) {
         for(int i1 = 0; i1 < 10; ++i1) {
            double motionX = this.m_217043_().m_188583_() * 0.07D;
            double motionY = this.m_217043_().m_188583_() * 0.07D;
            double motionZ = this.m_217043_().m_188583_() * 0.07D;
            float radius = size * this.f_19796_.m_188501_();
            float angle = 0.017453292F * this.f_20883_ * 3.14F * this.f_19796_.m_188501_();
            double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
            double extraY = 0.800000011920929D;
            double extraZ = (double)(radius * Mth.m_14089_(angle));
            BlockState BlockState = this.m_9236_().m_8055_(BlockPos.m_274561_((double)this.m_146903_() + extraX, (double)this.m_146904_() + extraY - 1.0D, (double)this.m_146907_() + extraZ));
            if (BlockState.m_60795_() && this.m_9236_().f_46443_) {
               this.m_9236_().m_6493_(new BlockParticleOption(ParticleTypes.f_123794_, BlockState), true, this.m_20185_() + extraX, this.m_20186_() + extraY, this.m_20189_() + extraZ, motionX, motionY, motionZ);
            }
         }
      }

   }

   public Animation[] getAnimations() {
      return new Animation[]{ANIMATION_PUPA_WIGGLE, ANIMATION_BITE, ANIMATION_STING, ANIMATION_EGG, ANIMATION_DIGNEST};
   }

   public int m_7809_() {
      return 0;
   }

   public boolean m_7826_() {
      return false;
   }

   public boolean m_183595_() {
      return false;
   }

   static {
      HASMADEHOME = SynchedEntityData.m_135353_(EntityMyrmexQueen.class, EntityDataSerializers.f_135035_);
   }
}
