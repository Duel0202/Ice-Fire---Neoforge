package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.pathfinding.NodeProcessorFly;
import com.github.alexthe666.iceandfire.pathfinding.NodeProcessorWalk;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.AbstractPathJob;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.PathJobMoveAwayFromLocation;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.PathJobMoveToLocation;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.PathJobRandomPos;
import com.github.alexthe666.iceandfire.util.WorldUtil;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ExecutionException;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class AdvancedPathNavigate extends AbstractAdvancedPathNavigate {
   public static final double MIN_Y_DISTANCE = 0.001D;
   public static final int MAX_SPEED_ALLOWED = 2;
   public static final double MIN_SPEED_ALLOWED = 0.1D;
   @Nullable
   private PathResult<AbstractPathJob> pathResult;
   private long pathStartTime;
   private final BlockPos spawnedPos;
   private BlockPos desiredPos;
   private int desiredPosTimeout;
   private IStuckHandler stuckHandler;
   private boolean isSneaking;
   private double swimSpeedFactor;
   private float width;
   private float height;

   public AdvancedPathNavigate(Mob entity, Level world) {
      this(entity, world, AdvancedPathNavigate.MovementType.WALKING);
   }

   public AdvancedPathNavigate(Mob entity, Level world, AdvancedPathNavigate.MovementType type) {
      this(entity, world, type, 1.0F, 1.0F);
   }

   public AdvancedPathNavigate(Mob entity, Level world, AdvancedPathNavigate.MovementType type, float width, float height) {
      this(entity, world, type, width, height, PathingStuckHandler.createStuckHandler().withTeleportSteps(6).withTeleportOnFullStuck());
   }

   public AdvancedPathNavigate(Mob entity, Level world, AdvancedPathNavigate.MovementType type, float width, float height, PathingStuckHandler stuckHandler) {
      super(entity, world);
      this.pathStartTime = 0L;
      this.spawnedPos = BlockPos.f_121853_;
      this.desiredPosTimeout = 0;
      this.isSneaking = true;
      this.swimSpeedFactor = 1.0D;
      this.width = 1.0F;
      this.height = 1.0F;
      switch(type) {
      case FLYING:
         this.f_26508_ = new NodeProcessorFly();
         this.getPathingOptions().setIsFlying(true);
         break;
      case WALKING:
         this.f_26508_ = new NodeProcessorWalk();
         break;
      case CLIMBING:
         this.f_26508_ = new NodeProcessorWalk();
         this.getPathingOptions().setCanClimb(true);
      }

      this.f_26508_.m_77351_(true);
      this.getPathingOptions().setEnterDoors(true);
      this.f_26508_.m_77355_(true);
      this.getPathingOptions().setCanOpenDoors(true);
      this.f_26508_.m_77358_(true);
      this.getPathingOptions().setCanSwim(true);
      this.width = width;
      this.height = height;
      this.stuckHandler = stuckHandler;
   }

   public BlockPos getDestination() {
      return this.destination;
   }

   @Nullable
   public PathResult moveAwayFromXYZ(BlockPos avoid, double range, double speedFactor, boolean safeDestination) {
      BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
      return this.setPathJob(new PathJobMoveAwayFromLocation(this.ourEntity.m_9236_(), start, avoid, (int)range, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity), (BlockPos)null, speedFactor, safeDestination);
   }

   @Nullable
   public PathResult moveToRandomPos(double range, double speedFactor) {
      if (this.pathResult != null && this.pathResult.getJob() instanceof PathJobRandomPos) {
         return this.pathResult;
      } else {
         this.desiredPos = BlockPos.f_121853_;
         int theRange = (int)((double)this.f_26494_.m_217043_().m_188503_((int)range) + range / 2.0D);
         BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
         return this.setPathJob(new PathJobRandomPos(this.ourEntity.m_9236_(), start, theRange, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity), (BlockPos)null, speedFactor, true);
      }
   }

   @Nullable
   public PathResult moveToRandomPosAroundX(int range, double speedFactor, BlockPos pos) {
      if (this.pathResult != null && this.pathResult.getJob() instanceof PathJobRandomPos && ((PathJobRandomPos)this.pathResult.getJob()).posAndRangeMatch(range, pos)) {
         return this.pathResult;
      } else {
         this.desiredPos = BlockPos.f_121853_;
         return this.setPathJob(new PathJobRandomPos(this.ourEntity.m_9236_(), AbstractPathJob.prepareStart(this.ourEntity), 3, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), range, this.ourEntity, pos), pos, speedFactor, true);
      }
   }

   public PathResult moveToRandomPos(int range, double speedFactor, Tuple<BlockPos, BlockPos> corners, AbstractAdvancedPathNavigate.RestrictionType restrictionType) {
      if (this.pathResult != null && this.pathResult.getJob() instanceof PathJobRandomPos) {
         return this.pathResult;
      } else {
         this.desiredPos = BlockPos.f_121853_;
         int theRange = this.f_26494_.m_217043_().m_188503_(range) + range / 2;
         BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
         return this.setPathJob(new PathJobRandomPos(this.ourEntity.m_9236_(), start, theRange, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity, (BlockPos)corners.m_14418_(), (BlockPos)corners.m_14419_(), restrictionType), (BlockPos)null, speedFactor, true);
      }
   }

   @Nullable
   public PathResult setPathJob(AbstractPathJob job, BlockPos dest, double speedFactor, boolean safeDestination) {
      this.m_26573_();
      this.destination = dest;
      this.originalDestination = dest;
      if (safeDestination) {
         this.desiredPos = dest;
         if (dest != null) {
            this.desiredPosTimeout = 1000;
         }
      }

      this.walkSpeedFactor = speedFactor;
      if (!(speedFactor > 2.0D) && !(speedFactor < 0.1D)) {
         job.setPathingOptions(this.getPathingOptions());
         this.pathResult = job.getResult();
         this.pathResult.startJob(Pathfinding.getExecutor());
         return this.pathResult;
      } else {
         IceAndFire.LOGGER.error("Tried to set a bad speed:" + speedFactor + " for entity:" + this.ourEntity, new Exception());
         return null;
      }
   }

   public boolean m_26571_() {
      return (this.pathResult == null || this.pathResult.isFinished() && this.pathResult.getStatus() != PathFindingStatus.CALCULATION_COMPLETE) && super.m_26571_();
   }

   public void m_7638_() {
      if (this.f_26508_ instanceof NodeProcessorWalk) {
         ((NodeProcessorWalk)this.f_26508_).setEntitySize(this.width, this.height);
      } else {
         ((NodeProcessorFly)this.f_26508_).setEntitySize(this.width, this.height);
      }

      if (this.desiredPosTimeout > 0 && this.desiredPosTimeout-- <= 0) {
         this.desiredPos = null;
      }

      if (this.pathResult != null) {
         if (!this.pathResult.isFinished()) {
            return;
         }

         if (this.pathResult.getStatus() == PathFindingStatus.CALCULATION_COMPLETE) {
            try {
               this.processCompletedCalculationResult();
            } catch (ExecutionException | InterruptedException var4) {
               IceAndFire.LOGGER.catching(var4);
            }
         }
      }

      int oldIndex = this.m_26571_() ? 0 : this.m_26570_().m_77399_();
      if (this.isSneaking) {
         this.isSneaking = false;
         this.f_26494_.m_20260_(false);
      }

      this.ourEntity.m_21567_(0.0F);
      if (this.handleLadders(oldIndex)) {
         this.m_7636_();
         this.stuckHandler.checkStuck(this);
      } else if (this.handleRails()) {
         this.stuckHandler.checkStuck(this);
      } else {
         ++this.f_26498_;
         if (!this.m_26571_()) {
            Vec3 vector3d2;
            if (this.m_7632_()) {
               this.m_7636_();
            } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
               vector3d2 = this.m_7475_();
               Vec3 vector3d1 = this.f_26496_.m_77380_(this.f_26494_);
               if (vector3d2.f_82480_ > vector3d1.f_82480_ && !this.f_26494_.m_20096_() && Mth.m_14107_(vector3d2.f_82479_) == Mth.m_14107_(vector3d1.f_82479_) && Mth.m_14107_(vector3d2.f_82481_) == Mth.m_14107_(vector3d1.f_82481_)) {
                  this.f_26496_.m_77374_();
               }
            }

            DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
            if (!this.m_26571_()) {
               vector3d2 = this.f_26496_.m_77380_(this.f_26494_);
               BlockPos blockpos = BlockPos.m_274446_(vector3d2);
               if (isEntityBlockLoaded(this.f_26495_, blockpos)) {
                  this.f_26494_.m_21566_().m_6849_(vector3d2.f_82479_, this.f_26495_.m_8055_(blockpos.m_7495_()).m_60795_() ? vector3d2.f_82480_ : getSmartGroundY(this.f_26495_, blockpos), vector3d2.f_82481_, this.f_26497_);
               }
            }
         }

         if (this.f_26506_) {
            this.m_26569_();
         }

         if (this.pathResult != null && this.m_26571_()) {
            this.pathResult.setStatus(PathFindingStatus.COMPLETE);
            this.pathResult = null;
         }

         if (this.f_26494_ instanceof TamableAnimal) {
            if (((TamableAnimal)this.f_26494_).m_21824_()) {
               return;
            }

            if (this.f_26494_ instanceof EntityDragonBase) {
               if (((EntityDragonBase)this.f_26494_).isChained()) {
                  return;
               }

               if (((EntityDragonBase)this.f_26494_).m_21825_()) {
                  return;
               }
            }
         }

         this.stuckHandler.checkStuck(this);
      }
   }

   public static double getSmartGroundY(BlockGetter world, BlockPos pos) {
      BlockPos blockpos = pos.m_7495_();
      VoxelShape voxelshape = world.m_8055_(blockpos).m_60816_(world, blockpos);
      return !voxelshape.m_83281_() && !(voxelshape.m_83297_(Axis.Y) < 1.0D) ? (double)blockpos.m_123342_() + voxelshape.m_83297_(Axis.Y) : (double)pos.m_123342_();
   }

   @Nullable
   public PathResult moveToXYZ(double x, double y, double z, double speedFactor) {
      int newX = Mth.m_14107_(x);
      int newY = (int)y;
      int newZ = Mth.m_14107_(z);
      if (this.pathResult == null || !(this.pathResult.getJob() instanceof PathJobMoveToLocation) || !this.pathResult.isComputing() && (this.destination == null || !isEqual(this.destination, newX, newY, newZ)) && (this.originalDestination == null || !isEqual(this.originalDestination, newX, newY, newZ))) {
         BlockPos start = AbstractPathJob.prepareStart(this.ourEntity);
         this.desiredPos = new BlockPos(newX, newY, newZ);
         return this.setPathJob(new PathJobMoveToLocation(this.ourEntity.m_9236_(), start, this.desiredPos, (int)this.ourEntity.m_21051_(Attributes.f_22277_).m_22135_(), this.ourEntity), this.desiredPos, speedFactor, true);
      } else {
         return this.pathResult;
      }
   }

   public boolean tryMoveToBlockPos(BlockPos pos, double speedFactor) {
      this.moveToXYZ((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), speedFactor);
      return true;
   }

   @NotNull
   protected PathFinder m_5532_(int p_179679_1_) {
      return new PathFinder(new WalkNodeEvaluator(), p_179679_1_);
   }

   protected boolean m_7632_() {
      if (this.ourEntity.m_20202_() != null) {
         PathPointExtended pEx = (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_());
         Entity entity;
         if (pEx.isRailsExit()) {
            entity = this.ourEntity.m_20202_();
            this.ourEntity.m_8127_();
            entity.m_142687_(RemovalReason.DISCARDED);
         } else if (!pEx.isOnRails()) {
            if (this.destination == null || this.f_26494_.m_20275_((double)this.destination.m_123341_(), (double)this.destination.m_123342_(), (double)this.destination.m_123343_()) > 2.0D) {
               this.ourEntity.m_8127_();
            }
         } else if ((Math.abs((double)pEx.f_77271_ - this.f_26494_.m_20185_()) > 7.0D || Math.abs((double)pEx.f_77273_ - this.f_26494_.m_20189_()) > 7.0D) && this.ourEntity.m_20202_() != null) {
            entity = this.ourEntity.m_20202_();
            this.ourEntity.m_8127_();
            entity.m_142687_(RemovalReason.DISCARDED);
         }
      }

      return true;
   }

   @NotNull
   protected Vec3 m_7475_() {
      return this.ourEntity.m_20182_();
   }

   public Path m_7864_(@NotNull BlockPos pos, int accuracy) {
      return null;
   }

   protected boolean m_183431_(@NotNull Vec3 start, @NotNull Vec3 end) {
      return super.m_183431_(start, end);
   }

   public double getSpeedFactor() {
      if (this.ourEntity.m_20069_()) {
         this.f_26497_ = this.walkSpeedFactor * this.swimSpeedFactor;
         return this.f_26497_;
      } else {
         this.f_26497_ = this.walkSpeedFactor;
         return this.walkSpeedFactor;
      }
   }

   public void m_26517_(double speedFactor) {
      if (!(speedFactor > 2.0D) && !(speedFactor < 0.1D)) {
         this.walkSpeedFactor = speedFactor;
      } else {
         IceAndFire.LOGGER.debug("Tried to set a bad speed:" + speedFactor + " for entity:" + this.ourEntity);
      }
   }

   public boolean m_26519_(double x, double y, double z, double speedFactor) {
      if (x == 0.0D && y == 0.0D && z == 0.0D) {
         return false;
      } else {
         this.moveToXYZ(x, y, z, speedFactor);
         return true;
      }
   }

   public boolean m_5624_(Entity entityIn, double speedFactor) {
      return this.tryMoveToBlockPos(entityIn.m_20183_(), speedFactor);
   }

   protected void m_6804_() {
   }

   public boolean m_26536_(@Nullable Path path, double speedFactor) {
      if (path == null) {
         this.m_26573_();
         return false;
      } else {
         this.pathStartTime = this.f_26495_.m_46467_();
         return super.m_26536_(this.convertPath(path), speedFactor);
      }
   }

   private Path convertPath(Path path) {
      int pathLength = path.m_77398_();
      Path tempPath = null;
      if (pathLength > 0 && !(path.m_77375_(0) instanceof PathPointExtended)) {
         PathPointExtended[] newPoints = new PathPointExtended[pathLength];

         for(int i = 0; i < pathLength; ++i) {
            Node point = path.m_77375_(i);
            if (!(point instanceof PathPointExtended)) {
               newPoints[i] = new PathPointExtended(new BlockPos(point.f_77271_, point.f_77272_, point.f_77273_));
            } else {
               newPoints[i] = (PathPointExtended)point;
            }
         }

         tempPath = new Path(Arrays.asList(newPoints), path.m_77406_(), path.m_77403_());
         PathPointExtended finalPoint = newPoints[pathLength - 1];
         this.destination = new BlockPos(finalPoint.f_77271_, finalPoint.f_77272_, finalPoint.f_77273_);
      }

      return tempPath == null ? path : tempPath;
   }

   private boolean processCompletedCalculationResult() throws InterruptedException, ExecutionException {
      ((AbstractPathJob)this.pathResult.getJob()).synchToClient(this.f_26494_);
      this.m_26536_(this.pathResult.getPath(), this.getSpeedFactor());
      if (this.pathResult != null) {
         this.pathResult.setStatus(PathFindingStatus.IN_PROGRESS_FOLLOWING);
      }

      return false;
   }

   private boolean handleLadders(int oldIndex) {
      if (!this.m_26571_()) {
         PathPointExtended pEx = (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_());
         PathPointExtended pExNext = this.m_26570_().m_77398_() > this.m_26570_().m_77399_() + 1 ? (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_() + 1) : null;
         BlockPos pos = new BlockPos(pEx.f_77271_, pEx.f_77272_, pEx.f_77273_);
         if (pEx.isOnLadder() && pExNext != null && (pEx.f_77272_ != pExNext.f_77272_ || this.f_26494_.m_20186_() > (double)pEx.f_77272_) && this.f_26495_.m_8055_(pos).isLadder(this.f_26495_, pos, this.ourEntity)) {
            return this.handlePathPointOnLadder(pEx);
         }

         if (this.ourEntity.m_20069_()) {
            return this.handleEntityInWater(oldIndex, pEx);
         }

         if (this.f_26495_.f_46441_.m_188503_(10) == 0) {
            if (!pEx.isOnLadder() && pExNext != null && pExNext.isOnLadder()) {
               this.f_26497_ = this.getSpeedFactor() / 4.0D;
            } else {
               this.f_26497_ = this.getSpeedFactor();
            }
         }
      }

      return false;
   }

   private BlockPos findBlockUnderEntity(Entity parEntity) {
      int blockX = (int)Math.round(parEntity.m_20185_());
      int blockY = Mth.m_14107_(parEntity.m_20186_() - 0.2D);
      int blockZ = (int)Math.round(parEntity.m_20189_());
      return new BlockPos(blockX, blockY, blockZ);
   }

   private boolean handleRails() {
      if (!this.m_26571_()) {
         PathPointExtended pEx = (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_());
         PathPointExtended pExNext = this.m_26570_().m_77398_() > this.m_26570_().m_77399_() + 1 ? (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_() + 1) : null;
         if (pExNext != null && pEx.f_77271_ == pExNext.f_77271_ && pEx.f_77273_ == pExNext.f_77273_) {
            pExNext = this.m_26570_().m_77398_() > this.m_26570_().m_77399_() + 2 ? (PathPointExtended)this.m_26570_().m_77375_(this.m_26570_().m_77399_() + 2) : null;
         }

         if (pEx.isOnRails() || pEx.isRailsExit()) {
            return this.handlePathOnRails(pEx, pExNext);
         }
      }

      return false;
   }

   private boolean handlePathOnRails(PathPointExtended pEx, PathPointExtended pExNext) {
      return false;
   }

   private boolean handlePathPointOnLadder(PathPointExtended pEx) {
      Vec3 vec3 = this.m_26570_().m_77380_(this.ourEntity);
      BlockPos entityPos = new BlockPos(this.ourEntity.m_20183_());
      if (vec3.m_82531_(this.ourEntity.m_20185_(), vec3.f_82480_, this.ourEntity.m_20189_()) < 0.6D && Math.abs(vec3.f_82480_ - (double)entityPos.m_123342_()) <= 2.0D) {
         double newSpeed = 0.3D;
         switch(pEx.getLadderFacing()) {
         case NORTH:
            vec3 = vec3.m_82520_(0.0D, 0.0D, 0.4D);
            break;
         case SOUTH:
            vec3 = vec3.m_82520_(0.0D, 0.0D, -0.4D);
            break;
         case WEST:
            vec3 = vec3.m_82520_(0.4D, 0.0D, 0.0D);
            break;
         case EAST:
            vec3 = vec3.m_82520_(-0.4D, 0.0D, 0.0D);
            break;
         case UP:
            vec3 = vec3.m_82520_(0.0D, 1.0D, 0.0D);
            break;
         default:
            newSpeed = 0.0D;
            this.f_26494_.m_20260_(true);
            this.isSneaking = true;
            this.ourEntity.m_21566_().m_6849_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, 0.2D);
         }

         if (!(newSpeed > 0.0D)) {
            if (this.f_26495_.m_8055_(entityPos.m_7495_()).isLadder(this.f_26495_, entityPos.m_7495_(), this.ourEntity)) {
               this.ourEntity.m_21567_(-0.5F);
               return true;
            }

            return false;
         }

         if (!(this.f_26495_.m_8055_(this.ourEntity.m_20183_()).m_60734_() instanceof LadderBlock)) {
            this.ourEntity.m_20256_(this.ourEntity.m_20184_().m_82520_(0.0D, 0.1D, 0.0D));
         }

         this.ourEntity.m_21566_().m_6849_(vec3.f_82479_, vec3.f_82480_, vec3.f_82481_, newSpeed);
      }

      return false;
   }

   private boolean handleEntityInWater(int oldIndex, PathPointExtended pEx) {
      int curIndex = this.m_26570_().m_77399_();
      if (curIndex > 0 && curIndex + 1 < this.m_26570_().m_77398_() && this.m_26570_().m_77375_(curIndex - 1).f_77272_ != pEx.f_77272_) {
         oldIndex = curIndex + 1;
      }

      this.m_26570_().m_77393_(oldIndex);
      Vec3 vec3d = this.m_26570_().m_77380_(this.ourEntity);
      if (vec3d.m_82557_(new Vec3(this.ourEntity.m_20185_(), vec3d.f_82480_, this.ourEntity.m_20189_())) < 0.1D && Math.abs(this.ourEntity.m_20186_() - vec3d.f_82480_) < 0.5D) {
         this.m_26570_().m_77374_();
         if (this.m_26571_()) {
            return true;
         }

         vec3d = this.m_26570_().m_77380_(this.ourEntity);
      }

      this.ourEntity.m_21566_().m_6849_(vec3d.f_82479_, vec3d.f_82480_, vec3d.f_82481_, this.getSpeedFactor());
      return false;
   }

   protected void m_7636_() {
      this.getSpeedFactor();
      int curNode = this.f_26496_.m_77399_();
      int curNodeNext = curNode + 1;
      if (curNodeNext < this.f_26496_.m_77398_()) {
         if (!(this.f_26496_.m_77375_(curNode) instanceof PathPointExtended)) {
            this.f_26496_ = this.convertPath(this.f_26496_);
         }

         PathPointExtended pEx = (PathPointExtended)this.f_26496_.m_77375_(curNode);
         PathPointExtended pExNext = (PathPointExtended)this.f_26496_.m_77375_(curNodeNext);
         if (pEx.isOnLadder() && pEx.getLadderFacing() == Direction.DOWN && !pExNext.isOnLadder()) {
            Vec3 vec3 = this.m_7475_();
            if (vec3.f_82480_ - (double)pEx.f_77272_ < 0.001D) {
               this.f_26496_.m_77393_(curNodeNext);
            }

            return;
         }
      }

      this.f_26505_ = Math.max(1.2F, this.f_26494_.m_20205_());
      boolean wentAhead = false;
      boolean isTracking = AbstractPathJob.trackingMap.containsValue(this.ourEntity.m_20148_());
      int maxDropHeight = 3;
      HashSet<BlockPos> reached = new HashSet();

      Vec3 next;
      for(int i = this.f_26496_.m_77399_(); i < Math.min(this.f_26496_.m_77398_(), this.f_26496_.m_77399_() + 4); ++i) {
         next = this.f_26496_.m_77382_(this.f_26494_, i);
         if (Math.abs(this.f_26494_.m_20185_() - next.f_82479_) < (double)this.f_26505_ - Math.abs(this.f_26494_.m_20186_() - next.f_82480_) * 0.1D && Math.abs(this.f_26494_.m_20189_() - next.f_82481_) < (double)this.f_26505_ - Math.abs(this.f_26494_.m_20186_() - next.f_82480_) * 0.1D && (Math.abs(this.f_26494_.m_20186_() - next.f_82480_) <= Math.min(1.0D, Math.ceil((double)(this.f_26494_.m_20206_() / 2.0F))) || Math.abs(this.f_26494_.m_20186_() - next.f_82480_) <= Math.ceil((double)(this.f_26494_.m_20205_() / 2.0F)) * (double)maxDropHeight)) {
            this.f_26496_.m_77374_();
            wentAhead = true;
            if (isTracking) {
               Node point = this.f_26496_.m_77375_(i);
               reached.add(new BlockPos(point.f_77271_, point.f_77272_, point.f_77273_));
            }
         }
      }

      if (isTracking) {
         AbstractPathJob.synchToClient(reached, this.ourEntity);
         reached.clear();
      }

      if (this.f_26496_.m_77392_()) {
         this.onPathFinish();
      } else if (!wentAhead) {
         if (curNode < this.f_26496_.m_77398_() && curNode > 1) {
            Vec3 curr = this.f_26496_.m_77382_(this.f_26494_, curNode - 1);
            next = this.f_26496_.m_77382_(this.f_26494_, curNode);
            Vec3i currI = new Vec3i((int)Math.round(curr.f_82479_), (int)Math.round(curr.f_82480_), (int)Math.round(curr.f_82481_));
            Vec3i nextI = new Vec3i((int)Math.round(next.f_82479_), (int)Math.round(next.f_82480_), (int)Math.round(next.f_82481_));
            if (this.f_26494_.m_20183_().m_123314_(currI, 2.0D) && this.f_26494_.m_20183_().m_123314_(nextI, 2.0D)) {
               for(int currentIndex = curNode - 1; currentIndex > 0; --currentIndex) {
                  Vec3 tempoPos = this.f_26496_.m_77382_(this.f_26494_, currentIndex);
                  Vec3i tempoPosI = new Vec3i((int)Math.round(tempoPos.f_82479_), (int)Math.round(tempoPos.f_82480_), (int)Math.round(tempoPos.f_82481_));
                  if (this.f_26494_.m_20183_().m_123314_(tempoPosI, 1.0D)) {
                     this.f_26496_.m_77393_(currentIndex);
                  } else if (isTracking) {
                     reached.add(new BlockPos(tempoPosI));
                  }
               }
            }

            if (isTracking) {
               AbstractPathJob.synchToClient(reached, this.ourEntity);
               reached.clear();
            }

         }
      }
   }

   private void onPathFinish() {
      this.m_26573_();
   }

   public void m_26569_() {
   }

   protected void m_6481_(@NotNull Vec3 positionVec3) {
   }

   public boolean entityOnAndBelowPath(Entity entity, Vec3 slack) {
      Path path = this.m_26570_();
      if (path == null) {
         return false;
      } else {
         int closest = path.m_77399_();

         for(int i = 0; i < path.m_77398_() - 1; ++i) {
            Node currentPoint;
            if (closest + i < path.m_77398_()) {
               currentPoint = path.m_77375_(closest + i);
               if (this.entityNearAndBelowPoint(currentPoint, entity, slack)) {
                  return true;
               }
            }

            if (closest - i >= 0) {
               currentPoint = path.m_77375_(closest - i);
               if (this.entityNearAndBelowPoint(currentPoint, entity, slack)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private boolean entityNearAndBelowPoint(Node currentPoint, Entity entity, Vec3 slack) {
      return Math.abs((double)currentPoint.f_77271_ - entity.m_20185_()) < slack.m_7096_() && (double)currentPoint.f_77272_ - entity.m_20186_() + slack.m_7098_() > 0.0D && Math.abs((double)currentPoint.f_77273_ - entity.m_20189_()) < slack.m_7094_();
   }

   public void m_26573_() {
      if (this.pathResult != null) {
         this.pathResult.cancel();
         this.pathResult.setStatus(PathFindingStatus.CANCELLED);
         this.pathResult = null;
      }

      this.destination = null;
      super.m_26573_();
   }

   @Nullable
   public PathResult moveToLivingEntity(Entity e, double speed) {
      return this.moveToXYZ(e.m_20185_(), e.m_20186_(), e.m_20189_(), speed);
   }

   @Nullable
   public PathResult moveAwayFromLivingEntity(Entity e, double distance, double speed) {
      return this.moveAwayFromXYZ(new BlockPos(e.m_20183_()), distance, speed, true);
   }

   public void m_7008_(boolean canSwim) {
      super.m_7008_(canSwim);
      this.getPathingOptions().setCanSwim(canSwim);
   }

   public BlockPos getDesiredPos() {
      return this.desiredPos;
   }

   public void setStuckHandler(IStuckHandler stuckHandler) {
      this.stuckHandler = stuckHandler;
   }

   public void setSwimSpeedFactor(double factor) {
      this.swimSpeedFactor = factor;
   }

   public static boolean isEqual(BlockPos coords, int x, int y, int z) {
      return coords.m_123341_() == x && coords.m_123342_() == y && coords.m_123343_() == z;
   }

   public static boolean isEntityBlockLoaded(LevelAccessor world, BlockPos pos) {
      return WorldUtil.isEntityBlockLoaded(world, pos);
   }

   public static enum MovementType {
      WALKING,
      FLYING,
      CLIMBING;

      // $FF: synthetic method
      private static AdvancedPathNavigate.MovementType[] $values() {
         return new AdvancedPathNavigate.MovementType[]{WALKING, FLYING, CLIMBING};
      }
   }
}
