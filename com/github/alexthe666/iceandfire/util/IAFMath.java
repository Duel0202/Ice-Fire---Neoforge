package com.github.alexthe666.iceandfire.util;

import java.util.Collections;
import java.util.List;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;

public class IAFMath {
   public static final List<Player> emptyPlayerEntityList = Collections.emptyList();
   public static final List<ItemEntity> emptyItemEntityList = Collections.emptyList();
   public static final List<AbstractHorse> emptyAbstractHorseEntityList = Collections.emptyList();
   public static final List<Entity> emptyEntityList = Collections.emptyList();
   public static final List<LivingEntity> emptyLivingEntityList = Collections.emptyList();
   private static final double coeff_1 = 0.7853981633974483D;
   private static final double coeff_2 = 2.356194490192345D;

   public static double atan2_accurate(double y, double x) {
      double r;
      if (y < 0.0D) {
         y = -y;
         if (x > 0.0D) {
            r = (x - y) / (x + y);
            return -(0.1963D * r * r * r - 0.9817D * r + 0.7853981633974483D);
         } else {
            r = (x + y) / (y - x);
            return -(0.1963D * r * r * r - 0.9817D * r + 2.356194490192345D);
         }
      } else {
         if (y == 0.0D) {
            y = 1.0E-25D;
         }

         if (x > 0.0D) {
            r = (x - y) / (x + y);
            return 0.1963D * r * r * r - 0.9817D * r + 0.7853981633974483D;
         } else {
            r = (x + y) / (y - x);
            return 0.1963D * r * r * r - 0.9817D * r + 2.356194490192345D;
         }
      }
   }
}
