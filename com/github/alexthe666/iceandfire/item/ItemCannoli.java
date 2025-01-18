package com.github.alexthe666.iceandfire.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemCannoli extends ItemGenericFood {
   public ItemCannoli() {
      super(20, 2.0F, false, false, true);
   }

   public void onFoodEaten(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 3600, 2));
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.cannoli.desc").m_130940_(ChatFormatting.GRAY));
   }
}
