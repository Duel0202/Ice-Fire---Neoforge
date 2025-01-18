package com.github.alexthe666.iceandfire.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemPixieDust extends ItemGenericFood {
   public ItemPixieDust() {
      super(1, 0.3F, false, false, true);
   }

   public void onFoodEaten(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19620_, 100, 1));
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 100, 1));
   }
}
