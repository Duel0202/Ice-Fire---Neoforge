package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDreadKnight;
import com.github.alexthe666.iceandfire.util.IAFMath;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class DreadAIRideHorse extends Goal {
   private final EntityDreadKnight knight;
   private AbstractHorse horse;
   @Nonnull
   private List<AbstractHorse> list;

   public DreadAIRideHorse(EntityDreadKnight knight) {
      this.list = IAFMath.emptyAbstractHorseEntityList;
      this.knight = knight;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.knight.m_20159_()) {
         this.list = IAFMath.emptyAbstractHorseEntityList;
         return false;
      } else {
         if (this.knight.m_9236_().m_46467_() % 4L == 0L) {
            this.list = this.knight.m_9236_().m_6443_(AbstractHorse.class, this.knight.m_20191_().m_82377_(16.0D, 7.0D, 16.0D), (entity) -> {
               return !entity.m_20160_();
            });
         }

         if (this.list.isEmpty()) {
            return false;
         } else {
            this.horse = (AbstractHorse)this.list.get(0);
            return true;
         }
      }
   }

   public boolean m_8045_() {
      return !this.knight.m_20159_() && this.horse != null && !this.horse.m_20160_();
   }

   public void m_8056_() {
      this.horse.m_21573_().m_26573_();
   }

   public void m_8041_() {
      this.horse = null;
      this.knight.m_21573_().m_26573_();
   }

   public void m_8037_() {
      this.knight.m_21563_().m_24960_(this.horse, 30.0F, 30.0F);
      this.knight.m_21573_().m_5624_(this.horse, 1.2D);
      if (this.knight.m_20280_(this.horse) < 4.0D) {
         this.horse.m_30651_(true);
         this.knight.m_21573_().m_26573_();
         this.knight.m_20329_(this.horse);
      }

   }
}
