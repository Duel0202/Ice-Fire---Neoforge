package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexQueen;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAILeaveHive extends Goal {
   private final EntityMyrmexBase myrmex;
   private final double movementSpeed;
   private PathResult path;
   private BlockPos nextEntrance;

   public MyrmexAILeaveHive(EntityMyrmexBase entityIn, double movementSpeedIn) {
      this.nextEntrance = BlockPos.f_121853_;
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.myrmex instanceof EntityMyrmexQueen) {
         return false;
      } else if (this.myrmex.m_21573_() instanceof AdvancedPathNavigate && !this.myrmex.m_20159_()) {
         if (this.myrmex.m_6162_()) {
            return false;
         } else if (this.myrmex.canMove() && this.myrmex.shouldLeaveHive() && !this.myrmex.shouldEnterHive() && this.myrmex.isInHive() && (!(this.myrmex instanceof EntityMyrmexWorker) || !((EntityMyrmexWorker)this.myrmex).holdingSomething() && this.myrmex.m_21120_(InteractionHand.MAIN_HAND).m_41619_()) && !this.myrmex.isEnteringHive) {
            MyrmexHive village = MyrmexWorldData.get(this.myrmex.m_9236_()).getNearestHive(this.myrmex.m_20183_(), 1000);
            if (village == null) {
               return false;
            } else {
               this.nextEntrance = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getClosestEntranceToEntity(this.myrmex, this.myrmex.m_217043_(), true));
               this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.nextEntrance.m_123341_(), (double)this.nextEntrance.m_123342_(), (double)this.nextEntrance.m_123343_(), this.movementSpeed);
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.myrmex.isCloseEnoughToTarget(this.nextEntrance, 12.0D) && !this.myrmex.shouldEnterHive() ? this.myrmex.shouldLeaveHive() : false;
   }

   public void m_8037_() {
      if (!this.myrmex.pathReachesTarget(this.path, this.nextEntrance, 12.0D)) {
         MyrmexHive village = MyrmexWorldData.get(this.myrmex.m_9236_()).getNearestHive(this.myrmex.m_20183_(), 1000);
         this.nextEntrance = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getClosestEntranceToEntity(this.myrmex, this.myrmex.m_217043_(), true));
         this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.nextEntrance.m_123341_(), (double)(this.nextEntrance.m_123342_() + 1), (double)this.nextEntrance.m_123343_(), this.movementSpeed);
      }

   }

   public void m_8056_() {
   }

   public void m_8041_() {
      this.nextEntrance = BlockPos.f_121853_;
      this.myrmex.m_21573_().m_26573_();
   }
}
