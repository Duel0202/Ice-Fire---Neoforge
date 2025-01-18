package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiPredicate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.phys.Vec3;

public class PathingStuckHandler implements IStuckHandler {
   private static final double MIN_TARGET_DIST = 3.0D;
   private final List<Direction> directions;
   private static final int MIN_TP_DELAY = 2400;
   private static final int MIN_DIST_FOR_TP = 10;
   private int teleportRange;
   private int timePerBlockDistance;
   private int stuckLevel;
   private int globalTimeout;
   private BlockPos prevDestination;
   private boolean canBreakBlocks;
   private boolean canPlaceLadders;
   private boolean canBuildLeafBridges;
   private boolean canTeleportGoal;
   private boolean takeDamageOnCompleteStuck;
   private float damagePct;
   private int completeStuckBlockBreakRange;
   private boolean hadPath;
   private int lastPathIndex;
   private int progressedNodes;
   private int delayBeforeActions;
   private int delayToNextUnstuckAction;
   private BlockPos moveAwayStartPos;
   private final Random rand;

   private PathingStuckHandler() {
      this.directions = Arrays.asList(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST);
      this.teleportRange = 0;
      this.timePerBlockDistance = 100;
      this.stuckLevel = 0;
      this.globalTimeout = 0;
      this.prevDestination = BlockPos.f_121853_;
      this.canBreakBlocks = false;
      this.canPlaceLadders = false;
      this.canBuildLeafBridges = false;
      this.canTeleportGoal = false;
      this.takeDamageOnCompleteStuck = false;
      this.damagePct = 0.2F;
      this.completeStuckBlockBreakRange = 0;
      this.hadPath = false;
      this.lastPathIndex = -1;
      this.progressedNodes = 0;
      this.delayBeforeActions = 1200;
      this.delayToNextUnstuckAction = this.delayBeforeActions;
      this.moveAwayStartPos = BlockPos.f_121853_;
      this.rand = new Random();
   }

   public static PathingStuckHandler createStuckHandler() {
      return new PathingStuckHandler();
   }

   public void checkStuck(AbstractAdvancedPathNavigate navigator) {
      if (navigator.getDesiredPos() != null && !navigator.getDesiredPos().equals(BlockPos.f_121853_)) {
         double distanceToGoal = navigator.getOurEntity().m_20182_().m_82554_(new Vec3((double)navigator.getDesiredPos().m_123341_(), (double)navigator.getDesiredPos().m_123342_(), (double)navigator.getDesiredPos().m_123343_()));
         if (distanceToGoal < 3.0D) {
            this.resetGlobalStuckTimers();
         } else {
            if (this.prevDestination.equals(navigator.getDesiredPos())) {
               ++this.globalTimeout;
               if ((double)this.globalTimeout > Math.max(2400.0D, (double)this.timePerBlockDistance * Math.max(10.0D, distanceToGoal))) {
                  this.completeStuckAction(navigator);
               }
            } else {
               this.resetGlobalStuckTimers();
            }

            this.prevDestination = navigator.getDesiredPos();
            if (navigator.m_26570_() != null && !navigator.m_26570_().m_77392_()) {
               if (navigator.m_26570_().m_77399_() == this.lastPathIndex) {
                  this.tryUnstuck(navigator);
               } else if (this.lastPathIndex != -1 && navigator.m_26570_().m_77406_().m_123331_(this.prevDestination) < 25.0D) {
                  this.progressedNodes = navigator.m_26570_().m_77399_() > this.lastPathIndex ? this.progressedNodes + 1 : this.progressedNodes - 1;
                  if (this.progressedNodes > 5 && (navigator.m_26570_().m_77395_() == null || !this.moveAwayStartPos.equals(navigator.m_26570_().m_77395_().m_77288_()))) {
                     this.resetStuckTimers();
                  }
               }
            } else {
               this.lastPathIndex = -1;
               this.progressedNodes = 0;
               if (!this.hadPath) {
                  this.tryUnstuck(navigator);
               }
            }

            this.lastPathIndex = navigator.m_26570_() != null ? navigator.m_26570_().m_77399_() : -1;
            this.hadPath = navigator.m_26570_() != null && !navigator.m_26570_().m_77392_();
         }
      }
   }

   private void resetGlobalStuckTimers() {
      this.globalTimeout = 0;
      this.prevDestination = BlockPos.f_121853_;
      this.resetStuckTimers();
   }

   private void completeStuckAction(AbstractAdvancedPathNavigate navigator) {
      BlockPos desired = navigator.getDesiredPos();
      Level world = navigator.getOurEntity().m_9236_();
      Mob entity = navigator.getOurEntity();
      if (this.canTeleportGoal) {
         BlockPos tpPos = findAround(world, desired, 10, 10, (posworld, pos) -> {
            return SurfaceType.getSurfaceType(posworld, posworld.m_8055_(pos.m_7495_()), pos.m_7495_()) == SurfaceType.WALKABLE && SurfaceType.getSurfaceType(posworld, posworld.m_8055_(pos), pos) == SurfaceType.DROPABLE && SurfaceType.getSurfaceType(posworld, posworld.m_8055_(pos.m_7494_()), pos.m_7494_()) == SurfaceType.DROPABLE;
         });
         if (tpPos != null) {
            entity.m_6021_((double)tpPos.m_123341_() + 0.5D, (double)tpPos.m_123342_(), (double)tpPos.m_123343_() + 0.5D);
         }
      }

      if (this.takeDamageOnCompleteStuck) {
         entity.m_6469_(new DamageSource(entity.m_9236_().m_269111_().m_269318_().m_269150_(), entity), entity.m_21233_() * this.damagePct);
      }

      if (this.completeStuckBlockBreakRange > 0) {
         Direction facing = getFacing(entity.m_20183_(), navigator.getDesiredPos());

         for(int i = 1; i <= this.completeStuckBlockBreakRange; ++i) {
            if (!world.m_46859_((new BlockPos(entity.m_20183_())).m_5484_(facing, i)) || !world.m_46859_((new BlockPos(entity.m_20183_())).m_5484_(facing, i).m_7494_())) {
               this.breakBlocksAhead(world, (new BlockPos(entity.m_20183_())).m_5484_(facing, i - 1), facing);
               break;
            }
         }
      }

      navigator.m_26573_();
      this.resetGlobalStuckTimers();
   }

   private void tryUnstuck(AbstractAdvancedPathNavigate navigator) {
      if (this.delayToNextUnstuckAction-- <= 0) {
         this.delayToNextUnstuckAction = 50;
         if (this.stuckLevel == 0) {
            ++this.stuckLevel;
            this.delayToNextUnstuckAction = 100;
            navigator.m_26573_();
         } else if (this.stuckLevel == 1) {
            ++this.stuckLevel;
            this.delayToNextUnstuckAction = 200;
            navigator.m_26573_();
            navigator.moveAwayFromXYZ(new BlockPos(navigator.getOurEntity().m_20183_()), 10.0D, 1.0D, false);
            navigator.getPathingOptions().setCanClimb(false);
            this.moveAwayStartPos = navigator.getOurEntity().m_20183_();
         } else {
            if (this.stuckLevel == 2 && this.teleportRange > 0 && this.hadPath) {
               int index = Math.min(navigator.m_26570_().m_77399_() + this.teleportRange, navigator.m_26570_().m_77398_() - 1);
               Node togo = navigator.m_26570_().m_77375_(index);
               navigator.getOurEntity().m_6021_((double)togo.f_77271_ + 0.5D, (double)togo.f_77272_, (double)togo.f_77273_ + 0.5D);
               this.delayToNextUnstuckAction = 300;
            }

            if (this.stuckLevel >= 3 && this.stuckLevel <= 5) {
               if (this.canPlaceLadders && this.rand.nextBoolean()) {
                  this.delayToNextUnstuckAction = 200;
                  this.placeLadders(navigator);
               } else if (this.canBuildLeafBridges && this.rand.nextBoolean()) {
                  this.delayToNextUnstuckAction = 100;
                  this.placeLeaves(navigator);
               }
            }

            if (this.stuckLevel >= 6 && this.stuckLevel <= 8 && this.canBreakBlocks) {
               this.delayToNextUnstuckAction = 200;
               this.breakBlocks(navigator);
            }

            this.chanceStuckLevel();
            if (this.stuckLevel == 9) {
               this.completeStuckAction(navigator);
               this.resetStuckTimers();
            }

         }
      }
   }

   private void chanceStuckLevel() {
      ++this.stuckLevel;
      if (this.stuckLevel > 1 && this.rand.nextInt(6) == 0) {
         this.stuckLevel -= 2;
      }

   }

   private void resetStuckTimers() {
      this.delayToNextUnstuckAction = this.delayBeforeActions;
      this.lastPathIndex = -1;
      this.progressedNodes = 0;
      this.stuckLevel = 0;
      this.moveAwayStartPos = BlockPos.f_121853_;
   }

   private void breakBlocksAhead(Level world, BlockPos start, Direction facing) {
      if (!world.m_46859_(start.m_6630_(3))) {
         this.setAirIfPossible(world, start.m_6630_(3));
      } else if (!world.m_46859_(start.m_7494_().m_121945_(facing))) {
         this.setAirIfPossible(world, start.m_7494_().m_121945_(facing));
      } else {
         if (!world.m_46859_(start.m_121945_(facing))) {
            this.setAirIfPossible(world, start.m_121945_(facing));
         }

      }
   }

   private void setAirIfPossible(Level world, BlockPos pos) {
      Block blockAtPos = world.m_8055_(pos).m_60734_();
      world.m_46597_(pos, Blocks.f_50016_.m_49966_());
   }

   private void placeLadders(AbstractAdvancedPathNavigate navigator) {
      Level world = navigator.getOurEntity().m_9236_();
      Mob entity = navigator.getOurEntity();

      BlockPos entityPos;
      for(entityPos = entity.m_20183_(); world.m_8055_(entityPos).m_60734_() == Blocks.f_50155_; entityPos = entityPos.m_7494_()) {
      }

      this.tryPlaceLadderAt(world, entityPos);
      this.tryPlaceLadderAt(world, entityPos.m_7494_());
      this.tryPlaceLadderAt(world, entityPos.m_6630_(2));
   }

   private void placeLeaves(AbstractAdvancedPathNavigate navigator) {
      Level world = navigator.getOurEntity().m_9236_();
      Mob entity = navigator.getOurEntity();
      Direction badFacing = getFacing(entity.m_20183_(), navigator.getDesiredPos()).m_122424_();
      Iterator var5 = this.directions.iterator();

      while(var5.hasNext()) {
         Direction dir = (Direction)var5.next();
         if (dir != badFacing && world.m_46859_(entity.m_20183_().m_7495_().m_121945_(dir))) {
            world.m_46597_(entity.m_20183_().m_7495_().m_121945_(dir), Blocks.f_50054_.m_49966_());
         }
      }

   }

   public static Direction getFacing(BlockPos pos, BlockPos neighbor) {
      BlockPos vector = neighbor.m_121996_(pos);
      return Direction.m_122372_((float)vector.m_123341_(), (float)vector.m_123342_(), (float)(-vector.m_123343_()));
   }

   private void breakBlocks(AbstractAdvancedPathNavigate navigator) {
      Level world = navigator.getOurEntity().m_9236_();
      Mob entity = navigator.getOurEntity();
      Direction facing = getFacing(entity.m_20183_(), navigator.getDesiredPos());
      this.breakBlocksAhead(world, entity.m_20183_(), facing);
   }

   private void tryPlaceLadderAt(Level world, BlockPos pos) {
      BlockState state = world.m_8055_(pos);
      if (state.m_60734_() != Blocks.f_50155_ && !state.m_60815_() && world.m_6425_(pos).m_76178_()) {
         Iterator var4 = this.directions.iterator();

         while(var4.hasNext()) {
            Direction dir = (Direction)var4.next();
            BlockState toPlace = (BlockState)Blocks.f_50155_.m_49966_().m_61124_(LadderBlock.f_54337_, dir.m_122424_());
            if (world.m_8055_(pos.m_121945_(dir)).m_280296_() && Blocks.f_50155_.m_7898_(toPlace, world, pos)) {
               world.m_46597_(pos, toPlace);
               break;
            }
         }
      }

   }

   public PathingStuckHandler withBlockBreaks() {
      this.canBreakBlocks = true;
      return this;
   }

   public PathingStuckHandler withPlaceLadders() {
      this.canPlaceLadders = true;
      return this;
   }

   public PathingStuckHandler withBuildLeafBridges() {
      this.canBuildLeafBridges = true;
      return this;
   }

   public PathingStuckHandler withTeleportSteps(int steps) {
      this.teleportRange = steps;
      return this;
   }

   public PathingStuckHandler withTeleportOnFullStuck() {
      this.canTeleportGoal = true;
      return this;
   }

   public PathingStuckHandler withTakeDamageOnStuck(float damagePct) {
      this.damagePct = damagePct;
      this.takeDamageOnCompleteStuck = true;
      return this;
   }

   public PathingStuckHandler withTimePerBlockDistance(int time) {
      this.timePerBlockDistance = time;
      return this;
   }

   public PathingStuckHandler withDelayBeforeStuckActions(int delay) {
      this.delayBeforeActions = delay;
      return this;
   }

   public PathingStuckHandler withCompleteStuckBlockBreak(int range) {
      this.completeStuckBlockBreakRange = range;
      return this;
   }

   public static BlockPos findAround(Level world, BlockPos start, int vRange, int hRange, BiPredicate<BlockGetter, BlockPos> predicate) {
      if (vRange < 1 && hRange < 1) {
         return null;
      } else if (predicate.test(world, start)) {
         return start;
      } else {
         int y = 0;
         int y_offset = 1;

         for(int i = 0; i < hRange + 2; ++i) {
            for(int steps = 1; steps <= vRange; ++steps) {
               BlockPos temp = start.m_7918_(-steps, y, -steps);

               int z;
               for(z = 0; z <= steps; ++z) {
                  temp = temp.m_7918_(1, 0, 0);
                  if (predicate.test(world, temp)) {
                     return temp;
                  }
               }

               for(z = 0; z <= steps; ++z) {
                  temp = temp.m_7918_(0, 0, 1);
                  if (predicate.test(world, temp)) {
                     return temp;
                  }
               }

               for(z = 0; z <= steps; ++z) {
                  temp = temp.m_7918_(-1, 0, 0);
                  if (predicate.test(world, temp)) {
                     return temp;
                  }
               }

               for(z = 0; z <= steps; ++z) {
                  temp = temp.m_7918_(0, 0, -1);
                  if (predicate.test(world, temp)) {
                     return temp;
                  }
               }
            }

            y += y_offset;
            y_offset = y_offset > 0 ? y_offset + 1 : y_offset - 1;
            y_offset *= -1;
            if (world.m_151558_() <= start.m_123342_() + y) {
               return null;
            }
         }

         return null;
      }
   }
}
