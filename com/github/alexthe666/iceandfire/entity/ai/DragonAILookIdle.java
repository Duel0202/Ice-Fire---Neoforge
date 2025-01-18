package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.EnumSet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class DragonAILookIdle extends Goal {
   private final EntityDragonBase dragon;
   private double lookX;
   private double lookZ;
   private int idleTime;

   public DragonAILookIdle(EntityDragonBase prehistoric) {
      this.dragon = prehistoric;
      this.m_7021_(EnumSet.of(Flag.LOOK));
   }

   public boolean m_8036_() {
      if (this.dragon.canMove() && this.dragon.getAnimation() != EntityDragonBase.ANIMATION_SHAKEPREY && !this.dragon.isFuelingForge()) {
         return this.dragon.m_217043_().m_188501_() < 0.02F;
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return this.idleTime >= 0 && this.dragon.canMove();
   }

   public void m_8056_() {
      double d0 = 6.283185307179586D * this.dragon.m_217043_().m_188500_();
      this.lookX = (double)Mth.m_14089_((float)d0);
      this.lookZ = (double)Mth.m_14031_((float)d0);
      this.idleTime = 20 + this.dragon.m_217043_().m_188503_(20);
   }

   public void m_8037_() {
      if (this.idleTime > 0) {
         --this.idleTime;
      }

      this.dragon.m_21563_().m_24950_(this.dragon.m_20185_() + this.lookX, this.dragon.m_20186_() + (double)this.dragon.m_20192_(), this.dragon.m_20189_() + this.lookZ, (float)this.dragon.m_8085_(), (float)this.dragon.m_8132_());
   }
}
