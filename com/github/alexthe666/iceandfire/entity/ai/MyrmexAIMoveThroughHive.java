package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAIMoveThroughHive extends Goal {
   private final EntityMyrmexBase myrmex;
   private final double movementSpeed;
   private BlockPos nextRoom;
   private PathResult path;

   public MyrmexAIMoveThroughHive(EntityMyrmexBase entityIn, double movementSpeedIn) {
      this.nextRoom = BlockPos.f_121853_;
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && (!(this.myrmex instanceof EntityMyrmexWorker) || !((EntityMyrmexWorker)this.myrmex).holdingSomething()) && this.myrmex.shouldMoveThroughHive() && (this.myrmex.shouldEnterHive() || this.myrmex.m_21573_().m_26571_()) && !this.myrmex.canSeeSky()) {
         MyrmexHive village = MyrmexWorldData.get(this.myrmex.m_9236_()).getNearestHive(this.myrmex.m_20183_(), 300);
         if (village == null) {
            village = this.myrmex.getHive();
         }

         if (this.myrmex.m_21573_() instanceof AdvancedPathNavigate && !this.myrmex.m_20159_()) {
            if (village == null) {
               return false;
            } else {
               this.nextRoom = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getRandomRoom(this.myrmex.m_217043_(), this.myrmex.m_20183_()));
               this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.nextRoom.m_123341_(), (double)this.nextRoom.m_123342_(), (double)this.nextRoom.m_123343_(), this.movementSpeed);
               return this.path != null;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.myrmex.shouldLeaveHive() && !this.myrmex.isCloseEnoughToTarget(this.nextRoom, 3.0D) && this.myrmex.shouldEnterHive() && (!(this.myrmex instanceof EntityMyrmexWorker) || !((EntityMyrmexWorker)this.myrmex).holdingBaby());
   }

   public void m_8056_() {
   }

   public void m_8041_() {
      this.nextRoom = BlockPos.f_121853_;
   }
}
