package com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AbstractAdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.MNode;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.Pathfinding;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.SurfaceType;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.Path;

public class PathJobRandomPos extends AbstractPathJob {
   protected final BlockPos destination;
   protected final int minDistFromStart;
   private static final RandomSource random = RandomSource.m_216337_();
   private final int maxDistToDest;

   public PathJobRandomPos(Level world, BlockPos start, int minDistFromStart, int range, LivingEntity entity) {
      super(world, start, start, range, new PathResult(), entity);
      this.minDistFromStart = minDistFromStart;
      this.maxDistToDest = range;
      Tuple<Direction, Direction> dir = getRandomDirectionTuple(random);
      this.destination = start.m_5484_((Direction)dir.m_14418_(), minDistFromStart).m_5484_((Direction)dir.m_14419_(), minDistFromStart);
   }

   public PathJobRandomPos(Level world, BlockPos start, int minDistFromStart, int searchRange, int maxDistToDest, LivingEntity entity, BlockPos dest) {
      super(world, start, dest, searchRange, new PathResult(), entity);
      this.minDistFromStart = minDistFromStart;
      this.maxDistToDest = maxDistToDest;
      this.destination = dest;
   }

   public PathJobRandomPos(Level world, BlockPos start, int minDistFromStart, int range, LivingEntity entity, BlockPos startRestriction, BlockPos endRestriction, AbstractAdvancedPathNavigate.RestrictionType restrictionType) {
      super(world, start, startRestriction, endRestriction, range, false, new PathResult(), entity, restrictionType);
      this.minDistFromStart = minDistFromStart;
      this.maxDistToDest = range;
      Tuple<Direction, Direction> dir = getRandomDirectionTuple(random);
      this.destination = start.m_5484_((Direction)dir.m_14418_(), minDistFromStart).m_5484_((Direction)dir.m_14419_(), minDistFromStart);
   }

   public static Tuple<Direction, Direction> getRandomDirectionTuple(RandomSource random) {
      return new Tuple(Direction.m_235672_(random), Direction.m_235672_(random));
   }

   @Nullable
   protected Path search() {
      if (Pathfinding.isDebug()) {
         IceAndFire.LOGGER.info(String.format("Pathfinding from [%d,%d,%d] in the direction of [%d,%d,%d]", this.start.m_123341_(), this.start.m_123342_(), this.start.m_123343_(), this.destination.m_123341_(), this.destination.m_123342_(), this.destination.m_123343_()));
      }

      return super.search();
   }

   public PathResult getResult() {
      return super.getResult();
   }

   protected double computeHeuristic(BlockPos pos) {
      return Math.sqrt(this.destination.m_123331_(new BlockPos(pos.m_123341_(), this.destination.m_123342_(), pos.m_123343_())));
   }

   protected boolean isAtDestination(MNode n) {
      return random.m_188503_(10) == 0 && this.isInRestrictedArea(n.pos) && this.start.m_123331_(n.pos) > (double)(this.minDistFromStart * this.minDistFromStart) && SurfaceType.getSurfaceType(this.world, this.world.m_8055_(n.pos.m_7495_()), n.pos.m_7495_()) == SurfaceType.WALKABLE && this.destination.m_123331_(n.pos) < (double)(this.maxDistToDest * this.maxDistToDest);
   }

   protected double getNodeResultScore(MNode n) {
      return this.destination.m_123331_(n.pos);
   }

   public boolean posAndRangeMatch(int range, BlockPos pos) {
      return this.destination != null && pos != null && range == this.maxDistToDest && this.destination.equals(pos);
   }
}
