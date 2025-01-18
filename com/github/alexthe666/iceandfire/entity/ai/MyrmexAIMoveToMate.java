package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexRoyal;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAIMoveToMate extends Goal {
   private final EntityMyrmexRoyal myrmex;
   private final double movementSpeed;

   public MyrmexAIMoveToMate(EntityMyrmexRoyal entityIn, double movementSpeedIn) {
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      return this.myrmex.canMove() && this.myrmex.m_5448_() == null && this.myrmex.mate != null && this.myrmex.canSeeSky();
   }

   public void m_8037_() {
      if (this.myrmex.mate != null && (this.myrmex.m_20270_(this.myrmex.mate) > 30.0F || this.myrmex.m_21573_().m_26571_())) {
         this.myrmex.m_21566_().m_6849_(this.myrmex.mate.m_20185_(), this.myrmex.m_20186_(), this.myrmex.mate.m_20189_(), this.movementSpeed);
      }

   }

   public boolean m_8045_() {
      return this.myrmex.canMove() && this.myrmex.m_5448_() == null && this.myrmex.mate != null && this.myrmex.mate.m_6084_() && (this.myrmex.m_20270_(this.myrmex.mate) < 15.0F || !this.myrmex.m_21573_().m_26571_());
   }
}
