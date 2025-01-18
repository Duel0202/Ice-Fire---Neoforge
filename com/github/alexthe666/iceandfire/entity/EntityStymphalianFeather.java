package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforge.common.ToolActions;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityStymphalianFeather extends AbstractArrow {
   public EntityStymphalianFeather(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
   }

   public EntityStymphalianFeather(EntityType<? extends AbstractArrow> t, Level worldIn, LivingEntity shooter) {
      super(t, shooter, worldIn);
      this.m_36781_(IafConfig.stymphalianBirdFeatherAttackStength);
   }

   public EntityStymphalianFeather(SpawnEntity spawnEntity, Level world) {
      this((EntityType)IafEntityRegistry.STYMPHALIAN_FEATHER.get(), world);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_142687_(@NotNull RemovalReason reason) {
      super.m_142687_(reason);
      if (IafConfig.stymphalianBirdFeatherDropChance > 0 && this.m_9236_().f_46443_ && this.f_19796_.m_188503_(IafConfig.stymphalianBirdFeatherDropChance) == 0) {
         this.m_5552_(this.m_7941_(), 0.1F);
      }

   }

   public void m_8119_() {
      super.m_8119_();
      if (this.f_19797_ > 100) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

   }

   protected void m_5790_(@NotNull EntityHitResult entityHit) {
      Entity shootingEntity = this.m_19749_();
      if (!(shootingEntity instanceof EntityStymphalianBird) || entityHit.m_82443_() == null || !(entityHit.m_82443_() instanceof EntityStymphalianBird)) {
         super.m_5790_(entityHit);
         if (entityHit.m_82443_() != null && entityHit.m_82443_() instanceof EntityStymphalianBird) {
            LivingEntity LivingEntity = (LivingEntity)entityHit.m_82443_();
            LivingEntity.m_21317_(LivingEntity.m_21234_() - 1);
            ItemStack itemstack1 = LivingEntity.m_6117_() ? LivingEntity.m_21211_() : ItemStack.f_41583_;
            if (itemstack1.m_41720_().canPerformAction(itemstack1, ToolActions.SHIELD_BLOCK)) {
               this.damageShield(LivingEntity, 1.0F);
            }
         }

      }
   }

   protected void damageShield(LivingEntity entity, float damage) {
      if (damage >= 3.0F && entity.m_21211_().m_41720_().canPerformAction(entity.m_21211_(), ToolActions.SHIELD_BLOCK)) {
         ItemStack copyBeforeUse = entity.m_21211_().m_41777_();
         int i = 1 + Mth.m_14143_(damage);
         InteractionHand Hand = entity.m_7655_();
         copyBeforeUse.m_41622_(i, entity, (player1) -> {
            player1.m_21190_(Hand);
         });
         if (entity.m_21211_().m_41619_()) {
            if (entity instanceof Player) {
               ForgeEventFactory.onPlayerDestroyItem((Player)entity, copyBeforeUse, Hand);
            }

            if (Hand == InteractionHand.MAIN_HAND) {
               this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            } else {
               this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
            }

            this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.m_9236_().f_46441_.m_188501_() * 0.4F);
         }
      }

   }

   @NotNull
   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)IafItemRegistry.STYMPHALIAN_BIRD_FEATHER.get());
   }
}
