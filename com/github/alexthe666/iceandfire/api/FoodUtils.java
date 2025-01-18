package com.github.alexthe666.iceandfire.api;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FoodUtils {
   public static int getFoodPoints(Entity entity) {
      int foodPoints = Math.round(entity.m_20205_() * entity.m_20206_() * 10.0F);
      if (entity instanceof AgeableMob) {
         return foodPoints;
      } else {
         return entity instanceof Player ? 15 : 0;
      }
   }

   public static int getFoodPoints(ItemStack item, boolean meatOnly, boolean includeFish) {
      if (item != null && item != ItemStack.f_41583_ && item.m_41720_() != null && item.m_41720_().m_41473_() != null) {
         int food = item.m_41720_().m_41473_().m_38744_() * 10;
         if (!meatOnly) {
            return food;
         }

         if (item.m_41720_().m_41473_().m_38746_()) {
            return food;
         }

         if (includeFish && item.m_204117_(ItemTags.f_13156_)) {
            return food;
         }
      }

      return 0;
   }
}
