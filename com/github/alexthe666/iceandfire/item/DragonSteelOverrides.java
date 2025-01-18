package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.google.common.collect.Multimap;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public interface DragonSteelOverrides<T extends TieredItem> {
   /** @deprecated */
   @Deprecated
   Multimap<Attribute, AttributeModifier> bakeDragonsteel();

   default float getAttackDamage(T item) {
      if (item instanceof SwordItem) {
         return ((SwordItem)item).m_43299_();
      } else {
         return item instanceof DiggerItem ? ((DiggerItem)item).m_41008_() : item.m_43314_().m_6631_();
      }
   }

   default boolean isDragonsteel(Tier tier) {
      return tier.getTag() == DragonSteelTier.DRAGONSTEEL_TIER_TAG;
   }

   default boolean isDragonsteelFire(Tier tier) {
      return tier == DragonSteelTier.DRAGONSTEEL_TIER_FIRE;
   }

   default boolean isDragonsteelIce(Tier tier) {
      return tier == DragonSteelTier.DRAGONSTEEL_TIER_ICE;
   }

   default boolean isDragonsteelLightning(Tier tier) {
      return tier == DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING;
   }

   default void hurtEnemy(T item, ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (item.m_43314_() == IafItemRegistry.SILVER_TOOL_MATERIAL && target.m_6336_() == MobType.f_21641_) {
         target.m_6469_(attacker.m_9236_().m_269111_().m_269425_(), this.getAttackDamage(item) + 3.0F);
      }

      if (item.m_43314_() == IafItemRegistry.MYRMEX_CHITIN_TOOL_MATERIAL) {
         if (target.m_6336_() != MobType.f_21642_) {
            target.m_6469_(attacker.m_9236_().m_269111_().m_269264_(), this.getAttackDamage(item) + 5.0F);
         }

         if (target instanceof EntityDeathWorm) {
            target.m_6469_(attacker.m_9236_().m_269111_().m_269264_(), this.getAttackDamage(item) + 5.0F);
         }
      }

      if (this.isDragonsteelFire(item.m_43314_()) && IafConfig.dragonWeaponFireAbility) {
         target.m_20254_(15);
         target.m_147240_(1.0D, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

      if (this.isDragonsteelIce(item.m_43314_()) && IafConfig.dragonWeaponIceAbility) {
         EntityDataProvider.getCapability(target).ifPresent((data) -> {
            data.frozenData.setFrozen(target, 300);
         });
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 300, 2));
         target.m_147240_(1.0D, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

      if (this.isDragonsteelLightning(item.m_43314_()) && IafConfig.dragonWeaponLightningAbility) {
         boolean flag = true;
         if (attacker instanceof Player && (double)attacker.f_20921_ > 0.2D) {
            flag = false;
         }

         if (!attacker.m_9236_().f_46443_ && flag) {
            LightningBolt lightningboltentity = (LightningBolt)EntityType.f_20465_.m_20615_(target.m_9236_());
            lightningboltentity.m_19880_().add(ServerEvents.BOLT_DONT_DESTROY_LOOT);
            lightningboltentity.m_19880_().add(attacker.m_20149_());
            lightningboltentity.m_20219_(target.m_20182_());
            if (!target.m_9236_().f_46443_) {
               target.m_9236_().m_7967_(lightningboltentity);
            }
         }

         target.m_147240_(1.0D, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

   }

   default void appendHoverText(Tier tier, ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      if (tier == IafItemRegistry.SILVER_TOOL_MATERIAL) {
         tooltip.add(Component.m_237115_("silvertools.hurt").m_130940_(ChatFormatting.GREEN));
      }

      if (tier == IafItemRegistry.MYRMEX_CHITIN_TOOL_MATERIAL) {
         tooltip.add(Component.m_237115_("myrmextools.hurt").m_130940_(ChatFormatting.GREEN));
      }

      if (this.isDragonsteelFire(tier) && IafConfig.dragonWeaponFireAbility) {
         tooltip.add(Component.m_237115_("dragon_sword_fire.hurt2").m_130940_(ChatFormatting.DARK_RED));
      }

      if (this.isDragonsteelIce(tier) && IafConfig.dragonWeaponIceAbility) {
         tooltip.add(Component.m_237115_("dragon_sword_ice.hurt2").m_130940_(ChatFormatting.AQUA));
      }

      if (this.isDragonsteelLightning(tier) && IafConfig.dragonWeaponLightningAbility) {
         tooltip.add(Component.m_237115_("dragon_sword_lightning.hurt2").m_130940_(ChatFormatting.DARK_PURPLE));
      }

   }
}
