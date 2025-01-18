package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAIReEnterHive extends Goal {
   private final EntityMyrmexBase myrmex;
   private final double movementSpeed;
   private PathResult path;
   private BlockPos currentTarget;
   private MyrmexAIReEnterHive.Phases currentPhase;
   private MyrmexHive hive;

   public MyrmexAIReEnterHive(EntityMyrmexBase entityIn, double movementSpeedIn) {
      this.currentTarget = BlockPos.f_121853_;
      this.currentPhase = MyrmexAIReEnterHive.Phases.GOTOENTRANCE;
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && !this.myrmex.shouldLeaveHive() && this.myrmex.shouldEnterHive() && this.currentPhase == MyrmexAIReEnterHive.Phases.GOTOENTRANCE) {
         MyrmexHive village = this.myrmex.getHive();
         if (village == null) {
            village = MyrmexWorldData.get(this.myrmex.m_9236_()).getNearestHive(this.myrmex.m_20183_(), 500);
         }

         if (this.myrmex.m_21573_() instanceof AdvancedPathNavigate && !this.myrmex.m_20159_()) {
            if (village != null && !this.myrmex.isInHive()) {
               this.hive = village;
               this.currentTarget = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), this.hive.getClosestEntranceToEntity(this.myrmex, this.myrmex.m_217043_(), false));
               this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.currentTarget.m_123341_(), (double)this.currentTarget.m_123342_(), (double)this.currentTarget.m_123343_(), 1.0D);
               this.currentPhase = MyrmexAIReEnterHive.Phases.GOTOENTRANCE;
               return this.path != null;
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void m_8037_() {
      if (this.currentPhase == MyrmexAIReEnterHive.Phases.GOTOENTRANCE && !this.myrmex.pathReachesTarget(this.path, this.currentTarget, 12.0D)) {
         this.currentTarget = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), this.hive.getClosestEntranceToEntity(this.myrmex, this.myrmex.m_217043_(), true));
         this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.currentTarget.m_123341_(), (double)this.currentTarget.m_123342_(), (double)this.currentTarget.m_123343_(), this.movementSpeed);
      }

      if (this.currentPhase == MyrmexAIReEnterHive.Phases.GOTOENTRANCE && this.myrmex.isCloseEnoughToTarget(this.currentTarget, 12.0D) && this.hive != null) {
         this.currentTarget = this.hive.getClosestEntranceBottomToEntity(this.myrmex, this.myrmex.m_217043_());
         this.currentPhase = MyrmexAIReEnterHive.Phases.GOTOEXIT;
         this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.currentTarget.m_123341_(), (double)this.currentTarget.m_123342_(), (double)this.currentTarget.m_123343_(), 1.0D);
      }

      if (this.currentPhase == MyrmexAIReEnterHive.Phases.GOTOEXIT && this.myrmex.isCloseEnoughToTarget(this.currentTarget, 12.0D) && this.hive != null) {
         this.currentTarget = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), this.hive.getCenter());
         this.currentPhase = MyrmexAIReEnterHive.Phases.GOTOCENTER;
         this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.currentTarget.m_123341_(), (double)this.currentTarget.m_123342_(), (double)this.currentTarget.m_123343_(), 1.0D);
      }

      this.myrmex.isEnteringHive = !this.myrmex.isCloseEnoughToTarget(this.currentTarget, 14.0D) && this.currentPhase != MyrmexAIReEnterHive.Phases.GOTOCENTER;
   }

   public boolean m_8045_() {
      return !this.myrmex.isCloseEnoughToTarget(this.currentTarget, 9.0D) || this.currentPhase == MyrmexAIReEnterHive.Phases.GOTOCENTER;
   }

   public void m_8041_() {
      this.currentTarget = BlockPos.f_121853_;
      this.currentPhase = MyrmexAIReEnterHive.Phases.GOTOENTRANCE;
   }

   private static enum Phases {
      GOTOENTRANCE,
      GOTOEXIT,
      GOTOCENTER;

      // $FF: synthetic method
      private static MyrmexAIReEnterHive.Phases[] $values() {
         return new MyrmexAIReEnterHive.Phases[]{GOTOENTRANCE, GOTOEXIT, GOTOCENTER};
      }
   }
}
