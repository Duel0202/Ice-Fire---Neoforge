package com.github.alexthe666.iceandfire.pathfinding.raycoms;

public enum PathFindingStatus {
   IN_PROGRESS_COMPUTING,
   IN_PROGRESS_FOLLOWING,
   CALCULATION_COMPLETE,
   COMPLETE,
   CANCELLED;

   // $FF: synthetic method
   private static PathFindingStatus[] $values() {
      return new PathFindingStatus[]{IN_PROGRESS_COMPUTING, IN_PROGRESS_FOLLOWING, CALCULATION_COMPLETE, COMPLETE, CANCELLED};
   }
}
