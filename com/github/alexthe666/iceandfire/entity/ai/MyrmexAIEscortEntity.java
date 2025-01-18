package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexSoldier;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAIEscortEntity extends Goal {
   private final EntityMyrmexSoldier myrmex;
   private final double movementSpeed;

   public MyrmexAIEscortEntity(EntityMyrmexSoldier entityIn, double movementSpeedIn) {
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      return this.myrmex.canMove() && this.myrmex.m_5448_() == null && this.myrmex.guardingEntity != null && (this.myrmex.guardingEntity.canSeeSky() || !this.myrmex.canSeeSky()) && !this.myrmex.isEnteringHive;
   }

   public void m_8037_() {
      if (this.myrmex.guardingEntity != null && (this.myrmex.m_20270_(this.myrmex.guardingEntity) > 30.0F || this.myrmex.m_21573_().m_26571_())) {
         this.myrmex.m_21573_().m_5624_(this.myrmex.guardingEntity, this.movementSpeed);
      }

   }

   public boolean m_8045_() {
      return this.myrmex.canMove() && this.myrmex.m_5448_() == null && this.myrmex.guardingEntity != null && this.myrmex.guardingEntity.m_6084_() && (this.myrmex.m_20270_(this.myrmex.guardingEntity) < 15.0F || !this.myrmex.m_21573_().m_26571_()) && this.myrmex.canSeeSky() == this.myrmex.guardingEntity.canSeeSky() && !this.myrmex.guardingEntity.canSeeSky();
   }
}
