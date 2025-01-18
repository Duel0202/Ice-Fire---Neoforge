package com.github.alexthe666.iceandfire.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ItemDragonFlesh extends ItemGenericFood {
   int dragonType;

   public ItemDragonFlesh(int dragonType) {
      super(8, 0.8F, true, false, false);
      this.dragonType = dragonType;
   }

   static String getNameForType(int dragonType) {
      String var10000;
      switch(dragonType) {
      case 0:
         var10000 = "fire_dragon_flesh";
         break;
      case 1:
         var10000 = "ice_dragon_flesh";
         break;
      case 2:
         var10000 = "lightning_dragon_flesh";
         break;
      default:
         var10000 = "fire_dragon_flesh";
      }

      return var10000;
   }

   public void onFoodEaten(ItemStack stack, Level worldIn, LivingEntity livingEntity) {
      if (!worldIn.f_46443_) {
         if (this.dragonType == 0) {
            livingEntity.m_20254_(5);
         } else if (this.dragonType == 1) {
            livingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 2));
         } else if (!livingEntity.m_9236_().f_46443_) {
            LightningBolt lightningboltentity = (LightningBolt)EntityType.f_20465_.m_20615_(livingEntity.m_9236_());
            lightningboltentity.m_20219_(livingEntity.m_20182_());
            if (!livingEntity.m_9236_().f_46443_) {
               livingEntity.m_9236_().m_7967_(lightningboltentity);
            }
         }
      }

   }
}
