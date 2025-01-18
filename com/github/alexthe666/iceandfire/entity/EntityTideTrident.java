package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class EntityTideTrident extends ThrownTrident {
   private static final int ADDITIONALPIERCING = 2;
   private int entitiesHit;

   public EntityTideTrident(EntityType<? extends ThrownTrident> type, Level worldIn) {
      super(type, worldIn);
      this.entitiesHit = 0;
      this.f_37555_ = new ItemStack((ItemLike)IafItemRegistry.TIDE_TRIDENT.get());
   }

   public EntityTideTrident(Level worldIn, LivingEntity thrower, ItemStack thrownStackIn) {
      this((EntityType)IafEntityRegistry.TIDE_TRIDENT.get(), worldIn);
      this.m_6034_(thrower.m_20185_(), thrower.m_20188_() - 0.10000000149011612D, thrower.m_20189_());
      this.m_5602_(thrower);
      this.f_37555_ = thrownStackIn;
      this.f_19804_.m_135381_(f_37558_, (byte)EnchantmentHelper.m_44928_(thrownStackIn));
      this.f_19804_.m_135381_(f_37554_, thrownStackIn.m_41790_());
      int piercingLevel = EnchantmentHelper.m_44843_(Enchantments.f_44961_, thrownStackIn);
      this.m_36767_((byte)piercingLevel);
   }

   protected void m_5790_(EntityHitResult result) {
      Entity entity = result.m_82443_();
      float f = 12.0F;
      if (entity instanceof LivingEntity) {
         LivingEntity livingentity = (LivingEntity)entity;
         f += EnchantmentHelper.m_44833_(this.f_37555_, livingentity.m_6336_());
      }

      Entity entity1 = this.m_19749_();
      DamageSource damagesource = this.m_9236_().m_269111_().m_269525_(this, (Entity)(entity1 == null ? this : entity1));
      ++this.entitiesHit;
      if (this.entitiesHit >= this.getMaxPiercing()) {
         this.f_37556_ = true;
      }

      SoundEvent soundevent = SoundEvents.f_12514_;
      if (entity.m_6469_(damagesource, f)) {
         if (entity.m_6095_() == EntityType.f_20566_) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity1 = (LivingEntity)entity;
            if (entity1 instanceof LivingEntity) {
               EnchantmentHelper.m_44823_(livingentity1, entity1);
               EnchantmentHelper.m_44896_((LivingEntity)entity1, livingentity1);
            }

            this.m_7761_(livingentity1);
         }
      }

      float f1 = 1.0F;
      if (this.m_9236_() instanceof ServerLevel && this.m_9236_().m_46470_() && EnchantmentHelper.m_44936_(this.f_37555_)) {
         BlockPos blockpos = entity.m_20183_();
         if (this.m_9236_().m_45527_(blockpos)) {
            LightningBolt lightningboltentity = (LightningBolt)EntityType.f_20465_.m_20615_(this.m_9236_());
            lightningboltentity.m_20219_(Vec3.m_82512_(blockpos));
            lightningboltentity.m_20879_(entity1 instanceof ServerPlayer ? (ServerPlayer)entity1 : null);
            this.m_9236_().m_7967_(lightningboltentity);
            soundevent = SoundEvents.f_12521_;
            f1 = 5.0F;
         }
      }

      this.m_5496_(soundevent, f1, 1.0F);
   }

   private int getMaxPiercing() {
      return 2 + this.m_36796_();
   }
}
