package com.github.alexthe666.iceandfire.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemHydraHeart extends Item {
   public ItemHydraHeart() {
      super((new Properties()).m_41487_(1));
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !ItemStack.m_41656_(oldStack, newStack);
   }

   public void m_6883_(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof Player && itemSlot >= 0 && itemSlot <= 8) {
         double healthPercentage = (double)(((Player)entity).m_21223_() / Math.max(1.0F, ((Player)entity).m_21233_()));
         if (healthPercentage < 1.0D) {
            int level = 0;
            if (healthPercentage < 0.25D) {
               level = 3;
            } else if (healthPercentage < 0.5D) {
               level = 2;
            } else if (healthPercentage < 0.75D) {
               level = 1;
            }

            if (!((Player)entity).m_21023_(MobEffects.f_19605_) || ((Player)entity).m_21124_(MobEffects.f_19605_).m_19564_() < level) {
               ((Player)entity).m_7292_(new MobEffectInstance(MobEffects.f_19605_, 900, level, true, false));
            }
         }
      }

   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.hydra_heart.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.hydra_heart.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
