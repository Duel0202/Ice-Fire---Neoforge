package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.pathfinder.Node;

public class PathPointExtended extends Node {
   private boolean onLadder = false;
   private Direction ladderFacing;
   private boolean onRails;
   private boolean railsEntry;
   private boolean railsExit;

   public PathPointExtended(BlockPos pos) {
      super(pos.m_123341_(), pos.m_123342_(), pos.m_123343_());
      this.ladderFacing = Direction.DOWN;
   }

   public boolean isOnLadder() {
      return this.onLadder;
   }

   public void setOnLadder(boolean onLadder) {
      this.onLadder = onLadder;
   }

   public Direction getLadderFacing() {
      return this.ladderFacing;
   }

   public void setLadderFacing(Direction ladderFacing) {
      this.ladderFacing = ladderFacing;
   }

   public void setOnRails(boolean isOnRails) {
      this.onRails = isOnRails;
   }

   public void setRailsEntry() {
      this.railsEntry = true;
   }

   public void setRailsExit() {
      this.railsExit = true;
   }

   public boolean isOnRails() {
      return this.onRails;
   }

   public boolean isRailsEntry() {
      return this.railsEntry;
   }

   public boolean isRailsExit() {
      return this.railsExit;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         if (!super.equals(o)) {
            return false;
         } else {
            PathPointExtended that = (PathPointExtended)o;
            if (this.onLadder != that.onLadder) {
               return false;
            } else {
               return this.ladderFacing == that.ladderFacing;
            }
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (this.onLadder ? 1 : 0);
      result = 31 * result + this.ladderFacing.hashCode();
      return result;
   }
}
