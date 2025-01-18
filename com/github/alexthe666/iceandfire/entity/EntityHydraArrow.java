package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforge.common.ToolActions;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityHydraArrow extends AbstractArrow {
   public EntityHydraArrow(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
      this.m_36781_(5.0D);
   }

   public EntityHydraArrow(EntityType<? extends AbstractArrow> t, Level worldIn, double x, double y, double z) {
      this(t, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_(5.0D);
   }

   public EntityHydraArrow(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.HYDRA_ARROW.get(), worldIn);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityHydraArrow(EntityType t, Level worldIn, LivingEntity shooter) {
      super(t, shooter, worldIn);
      this.m_36781_(5.0D);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_9236_().f_46443_ && !this.f_36703_) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         double d3 = 10.0D;
         double xRatio = this.m_20184_().f_82479_ * (double)this.m_20206_();
         double zRatio = this.m_20184_().f_82481_ * (double)this.m_20206_();
         IceAndFire.PROXY.spawnParticle(EnumParticles.Hydra, this.m_20185_() + xRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d0 * 10.0D, this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - d1 * 10.0D, this.m_20189_() + zRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d2 * 10.0D, 0.1D, 1.0D, 0.1D);
         IceAndFire.PROXY.spawnParticle(EnumParticles.Hydra, this.m_20185_() + xRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d0 * 10.0D, this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - d1 * 10.0D, this.m_20189_() + zRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d2 * 10.0D, 0.1D, 1.0D, 0.1D);
      }

   }

   protected void damageShield(Player player, float damage) {
      if (damage >= 3.0F && player.m_21211_().m_41720_().canPerformAction(player.m_21211_(), ToolActions.SHIELD_BLOCK)) {
         ItemStack copyBeforeUse = player.m_21211_().m_41777_();
         int i = 1 + Mth.m_14143_(damage);
         player.m_21211_().m_41622_(i, player, (p_213360_0_) -> {
            p_213360_0_.m_21166_(EquipmentSlot.CHEST);
         });
         if (player.m_21211_().m_41619_()) {
            InteractionHand Hand = player.m_7655_();
            ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, Hand);
            if (Hand == InteractionHand.MAIN_HAND) {
               this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            } else {
               this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
            }

            player.m_5810_();
            this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.m_9236_().f_46441_.m_188501_() * 0.4F);
         }
      }

   }

   protected void m_7761_(@NotNull LivingEntity living) {
      if (living instanceof Player) {
         this.damageShield((Player)living, (float)this.m_36789_());
      }

      living.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 300, 0));
      Entity shootingEntity = this.m_19749_();
      if (shootingEntity instanceof LivingEntity) {
         ((LivingEntity)shootingEntity).m_5634_((float)this.m_36789_());
      }

   }

   @NotNull
   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)IafItemRegistry.HYDRA_ARROW.get());
   }
}
