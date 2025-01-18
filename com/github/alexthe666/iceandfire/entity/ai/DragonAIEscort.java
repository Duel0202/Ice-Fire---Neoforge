package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class DragonAIEscort extends Goal {
   private final EntityDragonBase dragon;
   private BlockPos previousPosition;
   private final float maxRange = 2000.0F;

   public DragonAIEscort(EntityDragonBase entityIn, double movementSpeedIn) {
      this.dragon = entityIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      return this.dragon.canMove() && this.dragon.m_5448_() == null && this.dragon.m_269323_() != null && this.dragon.getCommand() == 2;
   }

   public void m_8037_() {
      if (this.dragon.m_269323_() != null) {
         float dist = this.dragon.m_20270_(this.dragon.m_269323_());
         if (dist > 2000.0F) {
            return;
         }

         if ((double)dist > this.dragon.m_20191_().m_82309_() && (!this.dragon.isFlying() && !this.dragon.isHovering() || !this.dragon.isAllowedToTriggerFlight()) && (this.previousPosition == null || this.previousPosition.m_123331_(this.dragon.m_269323_().m_20183_()) > 9.0D)) {
            this.dragon.m_21573_().m_5624_(this.dragon.m_269323_(), 1.0D);
            this.previousPosition = this.dragon.m_269323_().m_20183_();
         }

         if ((dist > 30.0F || this.dragon.m_269323_().m_20186_() - this.dragon.m_20186_() > 8.0D) && !this.dragon.isFlying() && !this.dragon.isHovering() && this.dragon.isAllowedToTriggerFlight()) {
            this.dragon.setHovering(true);
            this.dragon.m_21837_(false);
            this.dragon.m_21839_(false);
            this.dragon.flyTicks = 0;
         }
      }

   }

   public boolean m_8045_() {
      return this.dragon.getCommand() == 2 && this.dragon.canMove() && this.dragon.m_5448_() == null && this.dragon.m_269323_() != null && this.dragon.m_269323_().m_6084_() && (this.dragon.m_20270_(this.dragon.m_269323_()) > 15.0F || !this.dragon.m_21573_().m_26571_());
   }
}
