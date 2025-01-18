package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityFireDragon;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemAlchemySword extends SwordItem {
   public ItemAlchemySword(Tier toolmaterial) {
      super(toolmaterial, 3, -2.4F, new Properties());
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
      if (this == IafItemRegistry.DRAGONBONE_SWORD_FIRE.get() && IafConfig.dragonWeaponFireAbility) {
         if (target instanceof EntityIceDragon) {
            target.m_6469_(attacker.m_9236_().m_269111_().m_269387_(), 13.5F);
         }

         target.m_20254_(5);
         target.m_147240_(1.0D, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

      if (this == IafItemRegistry.DRAGONBONE_SWORD_ICE.get() && IafConfig.dragonWeaponIceAbility) {
         if (target instanceof EntityFireDragon) {
            target.m_6469_(attacker.m_9236_().m_269111_().m_269063_(), 13.5F);
         }

         EntityDataProvider.getCapability(target).ifPresent((data) -> {
            data.frozenData.setFrozen(target, 200);
         });
         target.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 2));
         target.m_7292_(new MobEffectInstance(MobEffects.f_19599_, 100, 2));
         target.m_147240_(1.0D, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

      if (this == IafItemRegistry.DRAGONBONE_SWORD_LIGHTNING.get() && IafConfig.dragonWeaponLightningAbility) {
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

         if (target instanceof EntityFireDragon || target instanceof EntityIceDragon) {
            target.m_6469_(attacker.m_9236_().m_269111_().m_269548_(), 9.5F);
         }

         target.m_147240_(1.0D, attacker.m_20185_() - target.m_20185_(), attacker.m_20189_() - target.m_20189_());
      }

      return super.m_7579_(stack, target, attacker);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      if (this == IafItemRegistry.DRAGONBONE_SWORD_FIRE.get()) {
         tooltip.add(Component.m_237115_("dragon_sword_fire.hurt1").m_130940_(ChatFormatting.GREEN));
         if (IafConfig.dragonWeaponFireAbility) {
            tooltip.add(Component.m_237115_("dragon_sword_fire.hurt2").m_130940_(ChatFormatting.DARK_RED));
         }
      }

      if (this == IafItemRegistry.DRAGONBONE_SWORD_ICE.get()) {
         tooltip.add(Component.m_237115_("dragon_sword_ice.hurt1").m_130940_(ChatFormatting.GREEN));
         if (IafConfig.dragonWeaponIceAbility) {
            tooltip.add(Component.m_237115_("dragon_sword_ice.hurt2").m_130940_(ChatFormatting.AQUA));
         }
      }

      if (this == IafItemRegistry.DRAGONBONE_SWORD_LIGHTNING.get()) {
         tooltip.add(Component.m_237115_("dragon_sword_lightning.hurt1").m_130940_(ChatFormatting.GREEN));
         if (IafConfig.dragonWeaponLightningAbility) {
            tooltip.add(Component.m_237115_("dragon_sword_lightning.hurt2").m_130940_(ChatFormatting.DARK_PURPLE));
         }
      }

   }

   public boolean m_5812_(@NotNull ItemStack stack) {
      return true;
   }
}
