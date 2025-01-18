package com.github.alexthe666.iceandfire.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.FoodProperties.Builder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemGenericFood extends Item {
   private final int healAmount;
   private final float saturation;

   public ItemGenericFood(int amount, float saturation, boolean isWolfFood, boolean eatFast, boolean alwaysEdible) {
      super((new Properties()).m_41489_(createFood(amount, saturation, isWolfFood, eatFast, alwaysEdible, (MobEffectInstance)null)));
      this.healAmount = amount;
      this.saturation = saturation;
   }

   public ItemGenericFood(int amount, float saturation, boolean isWolfFood, boolean eatFast, boolean alwaysEdible, int stackSize) {
      super((new Properties()).m_41489_(createFood(amount, saturation, isWolfFood, eatFast, alwaysEdible, (MobEffectInstance)null)).m_41487_(stackSize));
      this.healAmount = amount;
      this.saturation = saturation;
   }

   public static final FoodProperties createFood(int amount, float saturation, boolean isWolfFood, boolean eatFast, boolean alwaysEdible, MobEffectInstance potion) {
      Builder builder = new Builder();
      builder.m_38760_(amount);
      builder.m_38758_(saturation);
      if (isWolfFood) {
         builder.m_38757_();
      }

      if (eatFast) {
         builder.m_38766_();
      }

      if (alwaysEdible) {
         builder.m_38765_();
      }

      if (potion != null) {
         builder.m_38762_(potion, 1.0F);
      }

      return builder.m_38767_();
   }

   @NotNull
   public ItemStack m_5922_(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity LivingEntity) {
      this.onFoodEaten(stack, worldIn, LivingEntity);
      return super.m_5922_(stack, worldIn, LivingEntity);
   }

   public void onFoodEaten(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
   }
}
