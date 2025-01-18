package com.github.alexthe666.iceandfire.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class ItemAmbrosia extends ItemGenericFood {
   public ItemAmbrosia() {
      super(5, 0.6F, false, false, true, 1);
   }

   public void onFoodEaten(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 3600, 2));
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19617_, 3600, 2));
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19603_, 3600, 2));
      livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19621_, 3600, 2));
   }

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
      super.m_5922_(stack, worldIn, livingEntity);
      return livingEntity instanceof Player && ((Player)livingEntity).m_150110_().f_35937_ ? stack : new ItemStack(Items.f_42399_);
   }
}
