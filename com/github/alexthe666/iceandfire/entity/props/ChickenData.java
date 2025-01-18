package com.github.alexthe666.iceandfire.entity.props;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.event.ServerEvents;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class ChickenData {
   public int timeUntilNextEgg = -1;

   public void tickChicken(LivingEntity entity) {
      if (IafConfig.chickensLayRottenEggs && !entity.m_9236_().m_5776_() && ServerEvents.isChicken(entity) && !entity.m_6162_()) {
         if (this.timeUntilNextEgg == -1) {
            this.timeUntilNextEgg = this.createDefaultTime(entity.m_217043_());
         }

         if (this.timeUntilNextEgg == 0) {
            if (entity.f_19797_ > 30 && entity.m_217043_().m_188503_(IafConfig.cockatriceEggChance + 1) == 0) {
               entity.m_5496_(SoundEvents.f_11753_, 2.0F, (entity.m_217043_().m_188501_() - entity.m_217043_().m_188501_()) * 0.2F + 1.0F);
               entity.m_5496_(SoundEvents.f_11752_, 1.0F, (entity.m_217043_().m_188501_() - entity.m_217043_().m_188501_()) * 0.2F + 1.0F);
               entity.m_20000_((ItemLike)IafItemRegistry.ROTTEN_EGG.get(), 1);
            }

            this.timeUntilNextEgg = -1;
         } else {
            --this.timeUntilNextEgg;
         }

      }
   }

   public void setTime(int timeUntilNextEgg) {
      this.timeUntilNextEgg = timeUntilNextEgg;
   }

   public void serialize(CompoundTag tag) {
      CompoundTag chickenData = new CompoundTag();
      chickenData.m_128405_("timeUntilNextEgg", this.timeUntilNextEgg);
      tag.m_128365_("chickenData", chickenData);
   }

   public void deserialize(CompoundTag tag) {
      CompoundTag chickenData = tag.m_128469_("chickenData");
      this.timeUntilNextEgg = chickenData.m_128451_("timeUntilNextEgg");
   }

   private int createDefaultTime(@NotNull RandomSource random) {
      return random.m_188503_(6000) + 6000;
   }
}
