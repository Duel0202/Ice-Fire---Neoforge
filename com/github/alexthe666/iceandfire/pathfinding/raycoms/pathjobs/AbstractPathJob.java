package com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.message.MessageSyncPath;
import com.github.alexthe666.iceandfire.message.MessageSyncPathReached;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AbstractAdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.ChunkCache;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.IPassabilityNavigator;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.MNode;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathPointExtended;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.Pathfinding;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathfindingConstants;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathingOptions;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.SurfaceType;
import com.mojang.datafixers.util.Pair;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractBannerBlock;
import net.minecraft.world.level.block.BambooSaplingBlock;
import net.minecraft.world.level.block.BambooStalkBlock;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.LadderBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class AbstractPathJob implements Callable<Path> {
   public static final Map<Player, UUID> trackingMap = new HashMap();
   protected final BlockPos start;
   protected final LevelReader world;
   protected final PathResult result;
   private final Queue<MNode> nodesOpen;
   private final Map<Integer, MNode> nodesVisited;
   private final AbstractAdvancedPathNavigate.RestrictionType restrictionType;
   private final boolean hardXzRestriction;
   private final boolean xzRestricted;
   protected int maxRange;
   protected BlockPos end;
   protected boolean debugDrawEnabled;
   @Nullable
   protected Set<MNode> debugNodesVisited;
   @Nullable
   protected Set<MNode> debugNodesNotVisited;
   @Nullable
   protected Set<MNode> debugNodesPath;
   protected WeakReference<LivingEntity> entity;
   IPassabilityNavigator passabilityNavigator;
   private boolean allowJumpPointSearchTypeWalk;
   private float entitySizeXZ;
   private int entitySizeY;
   private boolean circumventSizeCheck;
   private int totalNodesAdded;
   private int totalNodesVisited;
   private PathingOptions pathingOptions;
   private int maxX;
   private int minX;
   private int maxZ;
   private int minZ;
   private int maxY;
   private int minY;
   private double maxJumpHeight;

   public AbstractPathJob(Level world, BlockPos start, BlockPos end, int range, LivingEntity entity) {
      this(world, start, end, range, new PathResult(), entity);
   }

   public AbstractPathJob(Level world, BlockPos start, BlockPos end, int range, PathResult result, LivingEntity entity) {
      this.nodesOpen = new PriorityQueue(500);
      this.nodesVisited = new HashMap();
      this.xzRestricted = false;
      this.end = null;
      this.debugDrawEnabled = false;
      this.debugNodesVisited = new HashSet();
      this.debugNodesNotVisited = new HashSet();
      this.debugNodesPath = new HashSet();
      this.entitySizeXZ = 1.0F;
      this.entitySizeY = 1;
      this.circumventSizeCheck = false;
      this.totalNodesAdded = 0;
      this.totalNodesVisited = 0;
      this.pathingOptions = new PathingOptions();
      this.maxJumpHeight = 1.3D;
      int minX = Math.min(start.m_123341_(), end.m_123341_()) - range / 2;
      int minZ = Math.min(start.m_123343_(), end.m_123343_()) - range / 2;
      int maxX = Math.max(start.m_123341_(), end.m_123341_()) + range / 2;
      int maxZ = Math.max(start.m_123343_(), end.m_123343_()) + range / 2;
      this.restrictionType = AbstractAdvancedPathNavigate.RestrictionType.NONE;
      this.hardXzRestriction = false;
      this.world = new ChunkCache(world, new BlockPos(minX, world.m_141937_(), minZ), new BlockPos(maxX, world.m_151558_(), maxZ), range, world.m_6042_());
      this.start = new BlockPos(start);
      this.end = end;
      this.maxRange = range;
      this.result = result;
      result.setJob(this);
      this.allowJumpPointSearchTypeWalk = false;
      if (entity != null && trackingMap.containsValue(entity.m_20148_())) {
         this.debugDrawEnabled = true;
         this.debugNodesVisited = new HashSet();
         this.debugNodesNotVisited = new HashSet();
         this.debugNodesPath = new HashSet();
      }

      this.setEntitySizes(entity);
      if (entity instanceof IPassabilityNavigator) {
         this.passabilityNavigator = (IPassabilityNavigator)entity;
         this.maxRange = this.passabilityNavigator.maxSearchNodes();
      }

      this.maxJumpHeight = (double)((float)Math.floor((double)(entity.m_274421_() - 0.2F)) + 1.3F);
      this.entity = new WeakReference(entity);
   }

   public AbstractPathJob(Level world, BlockPos start, BlockPos startRestriction, BlockPos endRestriction, int range, boolean hardRestriction, PathResult result, LivingEntity entity, AbstractAdvancedPathNavigate.RestrictionType restrictionType) {
      this(world, start, startRestriction, endRestriction, range, Vec3i.f_123288_, hardRestriction, result, entity, restrictionType);
      this.setEntitySizes(entity);
      if (entity instanceof IPassabilityNavigator) {
         this.passabilityNavigator = (IPassabilityNavigator)entity;
         this.maxRange = this.passabilityNavigator.maxSearchNodes();
      }

      this.maxJumpHeight = (double)((float)Math.floor((double)(entity.m_274421_() - 0.2F)) + 1.3F);
   }

   public AbstractPathJob(Level world, BlockPos start, BlockPos startRestriction, BlockPos endRestriction, int range, Vec3i grow, boolean hardRestriction, PathResult result, LivingEntity entity, AbstractAdvancedPathNavigate.RestrictionType restrictionType) {
      this.nodesOpen = new PriorityQueue(500);
      this.nodesVisited = new HashMap();
      this.xzRestricted = false;
      this.end = null;
      this.debugDrawEnabled = false;
      this.debugNodesVisited = new HashSet();
      this.debugNodesNotVisited = new HashSet();
      this.debugNodesPath = new HashSet();
      this.entitySizeXZ = 1.0F;
      this.entitySizeY = 1;
      this.circumventSizeCheck = false;
      this.totalNodesAdded = 0;
      this.totalNodesVisited = 0;
      this.pathingOptions = new PathingOptions();
      this.maxJumpHeight = 1.3D;
      this.minX = Math.min(startRestriction.m_123341_(), endRestriction.m_123341_()) - grow.m_123341_();
      this.minZ = Math.min(startRestriction.m_123343_(), endRestriction.m_123343_()) - grow.m_123343_();
      this.maxX = Math.max(startRestriction.m_123341_(), endRestriction.m_123341_()) + grow.m_123341_();
      this.maxZ = Math.max(startRestriction.m_123343_(), endRestriction.m_123343_()) + grow.m_123343_();
      this.minY = Math.min(startRestriction.m_123342_(), endRestriction.m_123342_()) - grow.m_123342_();
      this.maxY = Math.max(startRestriction.m_123342_(), endRestriction.m_123342_()) + grow.m_123342_();
      this.restrictionType = restrictionType;
      this.hardXzRestriction = hardRestriction;
      this.world = new ChunkCache(world, new BlockPos(this.minX, world.m_141937_(), this.minZ), new BlockPos(this.maxX, world.m_151558_(), this.maxZ), range, world.m_6042_());
      this.start = start;
      this.maxRange = range;
      this.result = result;
      result.setJob(this);
      this.allowJumpPointSearchTypeWalk = false;
      if (entity != null && trackingMap.containsValue(entity.m_20148_())) {
         this.debugDrawEnabled = true;
         this.debugNodesVisited = new HashSet();
         this.debugNodesNotVisited = new HashSet();
         this.debugNodesPath = new HashSet();
      }

      this.entity = new WeakReference(entity);
   }

   public static void synchToClient(HashSet<BlockPos> reached, Mob mob) {
      if (!reached.isEmpty()) {
         Iterator var2 = trackingMap.entrySet().iterator();

         while(var2.hasNext()) {
            Entry<Player, UUID> entry = (Entry)var2.next();
            if (((UUID)entry.getValue()).equals(mob.m_20148_())) {
               IceAndFire.sendMSGToPlayer(new MessageSyncPathReached(reached), (ServerPlayer)entry.getKey());
            }
         }

      }
   }

   public static BlockPos prepareStart(LivingEntity entity) {
      MutableBlockPos pos = new MutableBlockPos(entity.m_146903_(), entity.m_146904_(), entity.m_146907_());
      Level world = entity.m_9236_();
      BlockState bs = world.m_8055_(pos);
      VoxelShape collisionShape = bs.m_60816_(world, pos);
      double dX;
      if (bs.m_280555_() && collisionShape.m_83297_(Axis.X) > 0.0D) {
         double relPosX = Math.abs(entity.m_20185_() % 1.0D);
         dX = Math.abs(entity.m_20189_() % 1.0D);
         Iterator var9 = collisionShape.m_83299_().iterator();

         while(var9.hasNext()) {
            AABB box = (AABB)var9.next();
            if (relPosX >= box.f_82288_ && relPosX <= box.f_82291_ && dX >= box.f_82290_ && dX <= box.f_82293_ && box.f_82292_ > 0.0D) {
               pos.m_122178_(pos.m_123341_(), pos.m_123342_() + 1, pos.m_123343_());
               bs = world.m_8055_(pos);
               break;
            }
         }
      }

      BlockState down = world.m_8055_(pos.m_7495_());

      while(!bs.m_280555_() && !down.m_280555_() && !down.m_60734_().isLadder(down, world, pos.m_7495_(), entity) && bs.m_60819_().m_76178_()) {
         pos.m_122175_(Direction.DOWN, 1);
         bs = down;
         down = world.m_8055_(pos.m_7495_());
         if (pos.m_123342_() < 0) {
            return entity.m_20183_();
         }
      }

      Block b = bs.m_60734_();
      if (entity.m_20069_()) {
         while(!bs.m_60819_().m_76178_()) {
            pos.m_122178_(pos.m_123341_(), pos.m_123342_() + 1, pos.m_123343_());
            bs = world.m_8055_(pos);
         }
      } else if (b instanceof FenceBlock || b instanceof WallBlock || bs.m_280296_()) {
         dX = entity.m_20185_() - Math.floor(entity.m_20185_());
         double dZ = entity.m_20189_() - Math.floor(entity.m_20189_());
         if (dX < 0.25D) {
            pos.m_122178_(pos.m_123341_() - 1, pos.m_123342_(), pos.m_123343_());
         } else if (dX > 0.75D) {
            pos.m_122178_(pos.m_123341_() + 1, pos.m_123342_(), pos.m_123343_());
         }

         if (dZ < 0.25D) {
            pos.m_122178_(pos.m_123341_(), pos.m_123342_(), pos.m_123343_() - 1);
         } else if (dZ > 0.75D) {
            pos.m_122178_(pos.m_123341_(), pos.m_123342_(), pos.m_123343_() + 1);
         }
      }

      return pos.m_7949_();
   }

   private static void setLadderFacing(LevelReader world, BlockPos pos, PathPointExtended p) {
      BlockState state = world.m_8055_(pos);
      Block block = state.m_60734_();
      if (block instanceof VineBlock) {
         if ((Boolean)state.m_61143_(VineBlock.f_57836_)) {
            p.setLadderFacing(Direction.NORTH);
         } else if ((Boolean)state.m_61143_(VineBlock.f_57837_)) {
            p.setLadderFacing(Direction.EAST);
         } else if ((Boolean)state.m_61143_(VineBlock.f_57834_)) {
            p.setLadderFacing(Direction.SOUTH);
         } else if ((Boolean)state.m_61143_(VineBlock.f_57835_)) {
            p.setLadderFacing(Direction.WEST);
         }
      } else if (block instanceof LadderBlock) {
         p.setLadderFacing((Direction)state.m_61143_(LadderBlock.f_54337_));
      } else {
         p.setLadderFacing(Direction.UP);
      }

   }

   private static boolean onALadder(MNode node, @Nullable MNode nextInPath, BlockPos pos) {
      return nextInPath != null && node.isLadder() && nextInPath.pos.m_123341_() == pos.m_123341_() && nextInPath.pos.m_123343_() == pos.m_123343_();
   }

   private static int computeNodeKey(BlockPos pos) {
      return (pos.m_123341_() & 4095) << 20 | (pos.m_123342_() & 255) << 12 | pos.m_123343_() & 4095;
   }

   private static boolean nodeClosed(@Nullable MNode node) {
      return node != null && node.isClosed();
   }

   private static boolean calculateSwimming(LevelReader world, BlockPos pos, @Nullable MNode node) {
      return node == null ? SurfaceType.isWater(world, pos.m_7495_()) : node.isSwimming();
   }

   public static Direction getXZFacing(BlockPos pos, BlockPos neighbor) {
      BlockPos vector = neighbor.m_121996_(pos);
      return Direction.m_122372_((float)vector.m_123341_(), 0.0F, (float)vector.m_123343_());
   }

   public void synchToClient(LivingEntity mob) {
      Iterator iter = trackingMap.entrySet().iterator();

      while(iter.hasNext()) {
         Entry<Player, UUID> entry = (Entry)iter.next();
         if (((Player)entry.getKey()).m_213877_()) {
            iter.remove();
         } else if (((UUID)entry.getValue()).equals(mob.m_20148_())) {
            IceAndFire.sendMSGToPlayer(new MessageSyncPath(this.debugNodesVisited, this.debugNodesNotVisited, this.debugNodesPath), (ServerPlayer)entry.getKey());
         }
      }

   }

   protected boolean onLadderGoingUp(MNode currentNode, BlockPos dPos) {
      return currentNode.isLadder() && (dPos.m_123342_() >= 0 || dPos.m_123341_() != 0 || dPos.m_123343_() != 0);
   }

   public void setEntitySizes(LivingEntity entity) {
      if (entity instanceof ICustomSizeNavigator) {
         this.entitySizeXZ = ((ICustomSizeNavigator)entity).getXZNavSize();
         this.entitySizeY = ((ICustomSizeNavigator)entity).getYNavSize();
         this.circumventSizeCheck = ((ICustomSizeNavigator)entity).isSmallerThanBlock();
      } else {
         this.entitySizeXZ = entity.m_20205_() / 2.0F;
         this.entitySizeY = Mth.m_14167_(entity.m_20206_());
      }

      this.allowJumpPointSearchTypeWalk = false;
   }

   protected double computeCost(BlockPos dPos, boolean isSwimming, boolean onPath, boolean onRails, boolean railsExit, boolean swimStart, boolean corner, BlockState state, BlockPos blockPos) {
      double cost = Math.sqrt((double)(dPos.m_123341_() * dPos.m_123341_() + dPos.m_123342_() * dPos.m_123342_() + dPos.m_123343_() * dPos.m_123343_()));
      if (dPos.m_123342_() != 0 && (Math.abs(dPos.m_123342_()) > 1 || !(this.world.m_8055_(blockPos).m_60734_() instanceof StairBlock))) {
         if (dPos.m_123342_() > 0) {
            cost *= this.pathingOptions.jumpCost * (double)Math.abs(dPos.m_123342_());
         } else {
            cost *= this.pathingOptions.dropCost * (double)Math.abs(dPos.m_123342_());
         }
      }

      if (this.world.m_8055_(blockPos).m_61138_(BlockStateProperties.f_61446_)) {
         cost *= this.pathingOptions.traverseToggleAbleCost;
      }

      if (onPath) {
         cost *= this.pathingOptions.onPathCost;
      }

      if (onRails) {
         cost *= this.pathingOptions.onRailCost;
      }

      if (railsExit) {
         cost *= this.pathingOptions.railsExitCost;
      }

      if (state.m_60734_() instanceof VineBlock) {
         cost *= this.pathingOptions.vineCost;
      }

      if (isSwimming) {
         if (swimStart) {
            cost *= this.pathingOptions.swimCostEnter;
         } else {
            cost *= this.pathingOptions.swimCost;
         }
      }

      return cost;
   }

   public PathResult getResult() {
      return this.result;
   }

   public final Path call() {
      try {
         return this.search();
      } catch (Exception var2) {
         IceAndFire.LOGGER.warn("Pathfinding Exception", var2);
         return null;
      }
   }

   protected Path search() {
      MNode bestNode = this.getAndSetupStartNode();
      double bestNodeResultScore = Double.MAX_VALUE;

      while(!this.nodesOpen.isEmpty()) {
         if (Thread.currentThread().isInterrupted()) {
            return null;
         }

         MNode currentNode = (MNode)this.nodesOpen.poll();
         ++this.totalNodesVisited;
         if (this.totalNodesVisited > IafConfig.maxDragonPathingNodes || this.totalNodesVisited > this.maxRange * this.maxRange) {
            break;
         }

         currentNode.setCounterVisited(this.totalNodesVisited);
         this.handleDebugOptions(currentNode);
         currentNode.setClosed();
         boolean isViablePosition = this.isInRestrictedArea(currentNode.pos) && SurfaceType.getSurfaceType(this.world, this.world.m_8055_(currentNode.pos.m_7495_()), currentNode.pos.m_7495_()) == SurfaceType.WALKABLE;
         if (isViablePosition && this.isAtDestination(currentNode)) {
            bestNode = currentNode;
            this.result.setPathReachesDestination(true);
            break;
         }

         double nodeResultScore = this.getNodeResultScore(currentNode);
         if (isViablePosition && nodeResultScore < bestNodeResultScore && !currentNode.isCornerNode()) {
            bestNode = currentNode;
            bestNodeResultScore = nodeResultScore;
         }

         if (!this.hardXzRestriction || isViablePosition) {
            this.walkCurrentNode(currentNode);
         }
      }

      return this.finalizePath(bestNode);
   }

   private void handleDebugOptions(MNode currentNode) {
      if (this.debugDrawEnabled) {
         this.addNodeToDebug(currentNode);
      }

      if (Pathfinding.isDebug()) {
         IceAndFire.LOGGER.info(String.format("Examining MNode [%d,%d,%d] ; g=%f ; f=%f", currentNode.pos.m_123341_(), currentNode.pos.m_123342_(), currentNode.pos.m_123343_(), currentNode.getCost(), currentNode.getScore()));
      }

   }

   private void addNodeToDebug(MNode currentNode) {
      this.debugNodesNotVisited.remove(currentNode);
      this.debugNodesVisited.add(currentNode);
   }

   private void addPathNodeToDebug(MNode node) {
      this.debugNodesVisited.remove(node);
      this.debugNodesPath.add(node);
   }

   private void walkCurrentNode(MNode currentNode) {
      BlockPos dPos = PathfindingConstants.BLOCKPOS_IDENTITY;
      if (currentNode.parent != null) {
         dPos = currentNode.pos.m_121996_(currentNode.parent.pos);
      }

      if (this.onLadderGoingUp(currentNode, dPos)) {
         this.walk(currentNode, PathfindingConstants.BLOCKPOS_UP);
      }

      if (this.onLadderGoingDown(currentNode, dPos)) {
         this.walk(currentNode, PathfindingConstants.BLOCKPOS_DOWN);
      }

      if (this.pathingOptions.canClimb()) {
         if ((Integer)this.getHighest(currentNode).getFirst() > 1) {
            this.walk(currentNode, PathfindingConstants.BLOCKPOS_IDENTITY.m_6630_((Integer)this.getHighest(currentNode).getFirst()));
         }

         if (currentNode.parent != null && dPos.m_123341_() == 0 && dPos.m_123343_() == 0 && dPos.m_123342_() > 1 && this.getHighest(currentNode.parent).getSecond() != null) {
            this.walk(currentNode, (BlockPos)this.getHighest(currentNode.parent).getSecond());
         }
      }

      if ((currentNode.parent == null || !currentNode.parent.pos.equals(currentNode.pos.m_7495_())) && currentNode.isCornerNode()) {
         this.walk(currentNode, PathfindingConstants.BLOCKPOS_DOWN);
      } else {
         if (this.circumventSizeCheck && this.isPassable(currentNode.pos.m_7495_(), false, currentNode.parent) && !currentNode.isSwimming() && this.isLiquid(this.world.m_8055_(currentNode.pos.m_7495_())) || currentNode.parent != null && this.isPassableBBDown(currentNode.parent.pos, currentNode.pos.m_7495_(), currentNode.parent)) {
            this.walk(currentNode, PathfindingConstants.BLOCKPOS_DOWN);
         }

         if (dPos.m_123343_() <= 0) {
            this.walk(currentNode, PathfindingConstants.BLOCKPOS_NORTH);
         }

         if (dPos.m_123341_() >= 0) {
            this.walk(currentNode, PathfindingConstants.BLOCKPOS_EAST);
         }

         if (dPos.m_123343_() >= 0) {
            this.walk(currentNode, PathfindingConstants.BLOCKPOS_SOUTH);
         }

         if (dPos.m_123341_() <= 0) {
            this.walk(currentNode, PathfindingConstants.BLOCKPOS_WEST);
         }

      }
   }

   private boolean onLadderGoingDown(MNode currentNode, BlockPos dPos) {
      return (dPos.m_123342_() <= 0 || dPos.m_123341_() != 0 || dPos.m_123343_() != 0) && this.isLadder(currentNode.pos.m_7495_());
   }

   private MNode getAndSetupStartNode() {
      MNode startNode = new MNode(this.start, this.computeHeuristic(this.start));
      if (this.pathingOptions.isFlying() && this.start.m_123314_(this.end, (double)this.maxRange)) {
         startNode = new MNode(this.end, this.computeHeuristic(this.end));
      }

      if (this.isLadder(this.start)) {
         startNode.setLadder();
      } else if (this.isLiquid(this.world.m_8055_(this.start.m_7495_()))) {
         startNode.setSwimming();
      }

      startNode.setOnRails(this.pathingOptions.canUseRails() && this.world.m_8055_(this.start).m_60734_() instanceof BaseRailBlock);
      this.nodesOpen.offer(startNode);
      this.nodesVisited.put(computeNodeKey(this.start), startNode);
      ++this.totalNodesAdded;
      return startNode;
   }

   public boolean isLiquid(BlockState state) {
      return state.m_278721_() || !state.m_280555_() && !state.m_60819_().m_76178_();
   }

   private Path finalizePath(MNode targetNode) {
      int pathLength = 1;
      int railsLength = 0;

      MNode node;
      for(node = targetNode; node.parent != null; node = node.parent) {
         ++pathLength;
         if (node.isOnRails()) {
            ++railsLength;
         }
      }

      Node[] points = new Node[pathLength];
      points[0] = new PathPointExtended(node.pos);
      if (this.debugDrawEnabled) {
         this.addPathNodeToDebug(node);
      }

      MNode nextInPath = null;
      Node next = null;

      for(node = targetNode; node.parent != null; node = node.parent) {
         if (this.debugDrawEnabled) {
            this.addPathNodeToDebug(node);
         }

         --pathLength;
         BlockPos pos = node.pos;
         if (node.isSwimming()) {
            pos.m_121955_(PathfindingConstants.BLOCKPOS_DOWN);
         }

         PathPointExtended p = new PathPointExtended(pos);
         if (railsLength >= 8) {
            p.setOnRails(node.isOnRails());
            if (!p.isOnRails() || node.parent.isOnRails() && node.parent.parent != null) {
               if (p.isOnRails() && points.length > pathLength + 1) {
                  PathPointExtended point = (PathPointExtended)points[pathLength + 1];
                  if (!point.isOnRails()) {
                     point.setRailsExit();
                  }
               }
            } else {
               p.setRailsEntry();
            }
         }

         if (nextInPath != null && onALadder(node, nextInPath, pos)) {
            p.setOnLadder(true);
            if (nextInPath.pos.m_123342_() > pos.m_123342_()) {
               setLadderFacing(this.world, pos, p);
            }
         } else if (onALadder(node.parent, node.parent, pos)) {
            p.setOnLadder(true);
         }

         if (next != null) {
            next.f_77278_ = p;
         }

         next = p;
         points[pathLength] = p;
         nextInPath = node;
      }

      this.doDebugPrinting(points);
      return new Path(Arrays.asList(points), this.getPathTargetPos(targetNode), this.isAtDestination(targetNode));
   }

   protected BlockPos getPathTargetPos(MNode finalNode) {
      return finalNode.pos;
   }

   private void doDebugPrinting(Node[] points) {
      if (Pathfinding.isDebug()) {
         IceAndFire.LOGGER.info("Path found:");
         Node[] var2 = points;
         int var3 = points.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Node p = var2[var4];
            IceAndFire.LOGGER.info(String.format("Step: [%d,%d,%d]", p.f_77271_, p.f_77272_, p.f_77273_));
         }

         IceAndFire.LOGGER.info(String.format("Total Nodes Visited %d / %d", this.totalNodesVisited, this.totalNodesAdded));
      }

   }

   protected abstract double computeHeuristic(BlockPos var1);

   protected abstract boolean isAtDestination(MNode var1);

   protected abstract double getNodeResultScore(MNode var1);

   protected final boolean walk(MNode parent, BlockPos dPos) {
      BlockPos pos = parent.pos.m_121955_(dPos);
      int newY = this.getGroundHeight(parent, pos);
      if (newY < this.world.m_141937_()) {
         return false;
      } else {
         boolean corner = false;
         if (pos.m_123342_() != newY) {
            if (!parent.isCornerNode() && newY - pos.m_123342_() > 0 && (parent.parent == null || !parent.parent.pos.equals(parent.pos.m_121955_(new BlockPos(0, newY - pos.m_123342_(), 0))))) {
               dPos = new BlockPos(0, newY - pos.m_123342_(), 0);
               pos = parent.pos.m_121955_(dPos);
               corner = true;
            } else if (!parent.isCornerNode() && newY - pos.m_123342_() < 0 && (dPos.m_123341_() != 0 || dPos.m_123343_() != 0) && (parent.parent == null || !parent.pos.m_7495_().equals(parent.parent.pos))) {
               dPos = new BlockPos(dPos.m_123341_(), 0, dPos.m_123343_());
               pos = parent.pos.m_121955_(dPos);
               corner = true;
            } else if (!this.pathingOptions.canClimb() || dPos.m_123342_() <= 1) {
               dPos = dPos.m_7918_(0, newY - pos.m_123342_(), 0);
               pos = new BlockPos(pos.m_123341_(), newY, pos.m_123343_());
            }
         }

         int nodeKey = computeNodeKey(pos);
         MNode node = (MNode)this.nodesVisited.get(nodeKey);
         if (nodeClosed(node)) {
            return false;
         } else {
            boolean isSwimming = calculateSwimming(this.world, pos, node);
            if (isSwimming && !this.pathingOptions.canSwim()) {
               return false;
            } else {
               boolean swimStart = isSwimming && !parent.isSwimming();
               BlockState state = this.world.m_8055_(pos);
               boolean onRoad = false;
               boolean onRails = this.pathingOptions.canUseRails() && this.world.m_8055_(corner ? pos.m_7495_() : pos).m_60734_() instanceof BaseRailBlock;
               boolean railsExit = !onRails && parent != null && parent.isOnRails();
               double stepCost = this.computeCost(dPos, isSwimming, false, onRails, railsExit, swimStart, corner, state, pos);
               double heuristic = this.computeHeuristic(pos);
               double cost = parent.getCost() + stepCost;
               double score = cost + heuristic;
               if (node == null) {
                  node = this.createNode(parent, pos, nodeKey, isSwimming, heuristic, cost, score);
                  node.setOnRails(onRails);
                  node.setCornerNode(corner);
               } else if (this.updateCurrentNode(parent, node, heuristic, cost, score)) {
                  return false;
               }

               this.nodesOpen.offer(node);
               if (this.pathingOptions.canClimb() && dPos.m_123342_() > 1) {
                  return true;
               } else {
                  this.performJumpPointSearch(parent, dPos, node);
                  return true;
               }
            }
         }
      }
   }

   private void performJumpPointSearch(MNode parent, BlockPos dPos, MNode node) {
      if (this.allowJumpPointSearchTypeWalk && node.getHeuristic() <= parent.getHeuristic()) {
         this.walk(node, dPos);
      }

   }

   private MNode createNode(MNode parent, BlockPos pos, int nodeKey, boolean isSwimming, double heuristic, double cost, double score) {
      MNode node = new MNode(parent, pos, cost, heuristic, score);
      this.nodesVisited.put(nodeKey, node);
      if (this.debugDrawEnabled) {
         this.debugNodesNotVisited.add(node);
      }

      if (this.isLadder(pos)) {
         node.setLadder();
      } else if (isSwimming) {
         node.setSwimming();
      }

      ++this.totalNodesAdded;
      node.setCounterAdded(this.totalNodesAdded);
      return node;
   }

   private boolean updateCurrentNode(MNode parent, MNode node, double heuristic, double cost, double score) {
      if (score >= node.getScore()) {
         return true;
      } else if (!this.nodesOpen.remove(node)) {
         return true;
      } else {
         node.parent = parent;
         node.setSteps(parent.getSteps() + 1);
         node.setCost(cost);
         node.setHeuristic(heuristic);
         node.setScore(score);
         return false;
      }
   }

   protected int getGroundHeight(MNode parent, BlockPos pos) {
      if (this.checkHeadBlock(parent, pos)) {
         return this.handleTargetNotPassable(parent, pos.m_7494_(), this.world.m_8055_(pos.m_7494_()));
      } else {
         BlockState target = this.world.m_8055_(pos);
         if (parent != null && !this.isPassableBB(parent.pos, pos, parent)) {
            return this.handleTargetNotPassable(parent, pos, target);
         } else {
            BlockState below = this.world.m_8055_(pos.m_7495_());
            SurfaceType flyability;
            if (this.pathingOptions.isFlying()) {
               flyability = this.isFlyable(below, pos, parent);
               if (flyability == SurfaceType.FLYABLE) {
                  return pos.m_123342_();
               }

               if (flyability == SurfaceType.NOT_PASSABLE) {
                  return -1;
               }
            } else {
               flyability = this.isWalkableSurface(below, pos);
               if (flyability == SurfaceType.WALKABLE) {
                  return pos.m_123342_();
               }

               if (flyability == SurfaceType.NOT_PASSABLE) {
                  return -1;
               }
            }

            return this.handleNotStanding(parent, pos, below);
         }
      }
   }

   private int handleNotStanding(@Nullable MNode parent, BlockPos pos, BlockState below) {
      boolean isSwimming = parent != null && parent.isSwimming();
      if (this.isLiquid(below)) {
         return this.handleInLiquid(pos, below, isSwimming);
      } else {
         return this.isLadder(below.m_60734_(), pos.m_7495_()) ? pos.m_123342_() : this.checkDrop(parent, pos, isSwimming);
      }
   }

   private int checkDrop(@Nullable MNode parent, BlockPos pos, boolean isSwimming) {
      boolean canDrop = parent != null && !parent.isLadder();
      boolean isChonker = true;
      if (this.pathingOptions.canClimb() && parent != null && pos.m_123342_() > parent.pos.m_123342_() + 1) {
         return pos.m_123342_();
      } else if (isChonker || canDrop && !isSwimming && (parent.pos.m_123341_() == pos.m_123341_() && parent.pos.m_123343_() == pos.m_123343_() || !this.isPassableBBFull(parent.pos.m_7495_(), parent) || this.isWalkableSurface(this.world.m_8055_(parent.pos.m_7495_()), parent.pos.m_7495_()) != SurfaceType.DROPABLE)) {
         for(int i = 2; i <= 10; ++i) {
            BlockState below = this.world.m_8055_(pos.m_6625_(i));
            if (this.isWalkableSurface(below, pos) == SurfaceType.WALKABLE && i <= 4 || below.m_278721_()) {
               return pos.m_123342_() - i + 1;
            }

            if (below.m_60795_()) {
               return -1;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private int handleInLiquid(BlockPos pos, BlockState below, boolean isSwimming) {
      if (isSwimming) {
         return pos.m_123342_();
      } else {
         return this.pathingOptions.canSwim() && SurfaceType.isWater(this.world, pos.m_7495_()) ? pos.m_123342_() : -1;
      }
   }

   private int handleTargetNotPassable(@Nullable MNode parent, BlockPos pos, BlockState target) {
      boolean canJump = parent != null && !parent.isLadder() && !parent.isSwimming();
      if (canJump && SurfaceType.getSurfaceType(this.world, target, pos) == SurfaceType.WALKABLE) {
         VoxelShape bb1;
         VoxelShape bb2;
         if (!this.isPassable(pos.m_6630_(2), false, parent)) {
            bb1 = this.world.m_8055_(pos).m_60816_(this.world, pos);
            bb2 = this.world.m_8055_(pos.m_6630_(2)).m_60816_(this.world, pos.m_6630_(2));
            if ((double)pos.m_6630_(2).m_123342_() + this.getStartY(bb2, 1) - ((double)pos.m_123342_() + this.getEndY(bb1, 0)) < 2.0D) {
               return -1;
            }
         }

         if (!this.isPassable(parent.pos.m_6630_(2), false, parent)) {
            bb1 = this.world.m_8055_(pos).m_60816_(this.world, pos);
            bb2 = this.world.m_8055_(parent.pos.m_6630_(2)).m_60816_(this.world, parent.pos.m_6630_(2));
            if ((double)parent.pos.m_6630_(2).m_123342_() + this.getStartY(bb2, 1) - ((double)pos.m_123342_() + this.getEndY(bb1, 0)) < 2.0D) {
               return -1;
            }
         }

         BlockState parentBelow = this.world.m_8055_(parent.pos.m_7495_());
         bb2 = parentBelow.m_60816_(this.world, parent.pos.m_7495_());
         double parentY = bb2.m_83297_(Axis.Y);
         double parentMaxY = parentY + (double)parent.pos.m_7495_().m_123342_();
         double targetMaxY = target.m_60816_(this.world, pos).m_83297_(Axis.Y) + (double)pos.m_123342_();
         if (targetMaxY - parentMaxY < this.maxJumpHeight) {
            return pos.m_123342_() + 1;
         } else {
            return target.m_60734_() instanceof StairBlock && parentY - 0.5D < this.maxJumpHeight && target.m_61143_(StairBlock.f_56842_) == Half.BOTTOM && getXZFacing(parent.pos, pos) == target.m_61143_(StairBlock.f_56841_) ? pos.m_123342_() + 1 : -1;
         }
      } else {
         return -1;
      }
   }

   private Pair<Integer, BlockPos> getHighest(MNode node) {
      int max = 1;
      BlockPos pos = node.pos;
      BlockPos direction = null;
      if (this.world.m_8055_(pos.m_122012_()).m_60815_() && this.climbableTop(pos.m_122012_(), Direction.SOUTH, node) > max) {
         max = this.climbableTop(pos.m_122012_(), Direction.SOUTH, node);
         direction = PathfindingConstants.BLOCKPOS_NORTH;
      }

      if (this.world.m_8055_(pos.m_122029_()).m_60815_() && this.climbableTop(pos.m_122029_(), Direction.WEST, node) > max) {
         max = this.climbableTop(pos.m_122029_(), Direction.WEST, node);
         direction = PathfindingConstants.BLOCKPOS_EAST;
      }

      if (this.world.m_8055_(pos.m_122019_()).m_60815_() && this.climbableTop(pos.m_122019_(), Direction.NORTH, node) > max) {
         max = this.climbableTop(pos.m_122019_(), Direction.NORTH, node);
         direction = PathfindingConstants.BLOCKPOS_SOUTH;
      }

      if (this.world.m_8055_(pos.m_122024_()).m_60815_() && this.climbableTop(pos.m_122024_(), Direction.EAST, node) > max) {
         max = this.climbableTop(pos.m_122024_(), Direction.EAST, node);
         direction = PathfindingConstants.BLOCKPOS_WEST;
      }

      return new Pair(max, direction);
   }

   private int climbableTop(BlockPos pos, Direction direction, MNode node) {
      BlockState target = this.world.m_8055_(pos);

      int i;
      for(i = 0; target.m_60815_(); ++i) {
         pos = pos.m_7494_();
         target = this.world.m_8055_(pos);
         BlockState origin = this.world.m_8055_(pos.m_121945_(direction));
         if (!this.isPassable(origin, pos.m_121945_(direction), node)) {
            i = 0;
            break;
         }
      }

      return i;
   }

   private boolean checkHeadBlock(@Nullable MNode parent, BlockPos pos) {
      BlockPos localPos = pos;
      VoxelShape bb = this.world.m_8055_(pos).m_60812_(this.world, pos);
      if (bb.m_83297_(Axis.Y) < 1.0D) {
         localPos = pos.m_7494_();
      }

      VoxelShape bb2;
      VoxelShape bb3;
      if (parent == null || !this.isPassableBB(parent.pos, pos.m_7494_(), parent)) {
         VoxelShape bb1 = this.world.m_8055_(pos.m_7495_()).m_60816_(this.world, pos.m_7495_());
         bb2 = this.world.m_8055_(pos.m_7494_()).m_60816_(this.world, pos.m_7494_());
         if ((double)pos.m_7494_().m_123342_() + this.getStartY(bb2, 1) - ((double)pos.m_7495_().m_123342_() + this.getEndY(bb1, 0)) < 2.0D) {
            return true;
         }

         if (parent != null) {
            bb3 = this.world.m_8055_(parent.pos.m_7495_()).m_60816_(this.world, pos.m_7495_());
            if ((double)pos.m_7494_().m_123342_() + this.getStartY(bb2, 1) - ((double)parent.pos.m_7495_().m_123342_() + this.getEndY(bb3, 0)) < 1.75D) {
               return true;
            }
         }
      }

      if (parent != null) {
         BlockState hereState = this.world.m_8055_(localPos.m_7495_());
         bb2 = this.world.m_8055_(pos).m_60816_(this.world, pos);
         bb3 = this.world.m_8055_(localPos.m_7494_()).m_60816_(this.world, localPos.m_7494_());
         if ((double)localPos.m_7494_().m_123342_() + this.getStartY(bb3, 1) - ((double)pos.m_123342_() + this.getEndY(bb2, 0)) >= 2.0D) {
            return false;
         } else {
            return this.isLiquid(hereState) && !this.isPassable(pos, false, parent);
         }
      } else {
         return false;
      }
   }

   private double getStartY(VoxelShape bb, int def) {
      return bb.m_83281_() ? (double)def : bb.m_83288_(Axis.Y);
   }

   private double getEndY(VoxelShape bb, int def) {
      return bb.m_83281_() ? (double)def : bb.m_83297_(Axis.Y);
   }

   protected boolean isPassable(BlockState block, BlockPos pos, MNode parent) {
      BlockPos parentPos = parent == null ? this.start : parent.pos;
      BlockState parentBlock = this.world.m_8055_(parentPos);
      Direction direction;
      if (parentBlock.m_60734_() instanceof TrapDoorBlock) {
         BlockPos dir = pos.m_121996_(parentPos);
         if (dir.m_123341_() != 0 || dir.m_123343_() != 0) {
            Direction direction = getXZFacing(parentPos, pos);
            direction = (Direction)parentBlock.m_61143_(TrapDoorBlock.f_54117_);
            if (direction == direction.m_122424_()) {
               return false;
            }
         }
      }

      if (block.m_60795_()) {
         return true;
      } else {
         VoxelShape shape = block.m_60816_(this.world, pos);
         if (block.m_280555_() && !shape.m_83281_() && !(shape.m_83297_(Axis.Y) <= 0.1D)) {
            if (block.m_60734_() instanceof TrapDoorBlock) {
               BlockPos dir = pos.m_121996_(parentPos);
               if (dir.m_123342_() != 0 && dir.m_123341_() == 0 && dir.m_123343_() == 0) {
                  return true;
               } else {
                  direction = getXZFacing(parentPos, pos);
                  Direction facing = (Direction)block.m_61143_(TrapDoorBlock.f_54117_);
                  if (direction == facing.m_122424_()) {
                     return true;
                  } else {
                     return direction != facing;
                  }
               }
            } else {
               return this.pathingOptions.canEnterDoors() && (block.m_60734_() instanceof DoorBlock || block.m_60734_() instanceof FenceGateBlock) || block.m_60734_() instanceof PressurePlateBlock || block.m_60734_() instanceof SignBlock || block.m_60734_() instanceof AbstractBannerBlock;
            }
         } else if (!(block.m_60734_() instanceof FireBlock) && !(block.m_60734_() instanceof SweetBerryBushBlock)) {
            if (this.isLadder(block.m_60734_(), pos)) {
               return true;
            } else if (shape.m_83281_() || shape.m_83297_(Axis.Y) <= 0.125D && !this.isLiquid(block) && (block.m_60734_() != Blocks.f_50125_ || (Integer)block.m_61143_(SnowLayerBlock.f_56581_) == 1)) {
               BlockPathTypes pathType = block.getBlockPathType(this.world, pos, (Mob)null);
               return pathType == null || pathType.getDanger() == null;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   protected boolean isPassable(BlockPos pos, boolean head, MNode parent) {
      BlockState state = this.world.m_8055_(pos);
      VoxelShape shape = state.m_60816_(this.world, pos);
      if (this.passabilityNavigator != null && this.passabilityNavigator.isBlockExplicitlyNotPassable(state, pos, pos)) {
         return false;
      } else if (!shape.m_83281_() && !(shape.m_83297_(Axis.Y) <= 0.1D)) {
         return this.isPassable(state, pos, parent);
      } else if (this.passabilityNavigator != null && this.passabilityNavigator.isBlockExplicitlyPassable(state, pos, pos)) {
         return this.isPassable(state, pos, parent);
      } else {
         return !head || !(state.m_60734_() instanceof WoolCarpetBlock) || this.isLadder(state.m_60734_(), pos);
      }
   }

   protected boolean isPassableBBFull(BlockPos pos, MNode parent) {
      if (this.circumventSizeCheck) {
         return this.isPassable(pos, false, parent) && this.isPassable(pos.m_7494_(), true, parent);
      } else {
         for(int i = 0; (float)i <= this.entitySizeXZ; ++i) {
            for(int j = 0; j <= this.entitySizeY; ++j) {
               for(int k = 0; (float)k <= this.entitySizeXZ; ++k) {
                  if (!this.isPassable(pos.m_7918_(i, j, k), false, parent)) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   protected boolean isPassableBB(BlockPos parentPos, BlockPos pos, MNode parent) {
      if (this.circumventSizeCheck) {
         return this.isPassable(pos, false, parent) && this.isPassable(pos.m_7494_(), true, parent);
      } else {
         Direction facingDir = getXZFacing(parentPos, pos);
         if (facingDir != Direction.DOWN && facingDir != Direction.UP) {
            facingDir = facingDir.m_122427_();

            for(int i = 0; (float)i <= this.entitySizeXZ; ++i) {
               for(int j = 0; j <= this.entitySizeY; ++j) {
                  if (!this.isPassable(pos.m_5484_(facingDir, i).m_6630_(j), false, parent)) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected boolean isPassableBBDown(BlockPos parentPos, BlockPos pos, MNode parent) {
      if (this.circumventSizeCheck) {
         return this.isPassable(pos, true, parent);
      } else {
         Direction facingDir = getXZFacing(parentPos, pos);
         if (facingDir != Direction.DOWN && facingDir != Direction.UP) {
            facingDir = facingDir.m_122427_();

            for(int i = 0; (float)i <= this.entitySizeXZ; ++i) {
               for(int j = 0; j <= this.entitySizeY; ++j) {
                  if (!this.isPassable(pos.m_5484_(facingDir, i).m_6630_(j), false, parent) || pos.m_123342_() <= parentPos.m_123342_()) {
                     return false;
                  }
               }
            }

            return true;
         } else {
            return false;
         }
      }
   }

   protected SurfaceType isFlyable(BlockState blockState, BlockPos pos, MNode parent) {
      Block block = blockState.m_60734_();
      if (!(block instanceof FenceBlock) && !(block instanceof FenceGateBlock) && !(block instanceof WallBlock) && !(block instanceof FireBlock) && !(block instanceof CampfireBlock) && !(block instanceof BambooStalkBlock) && !(block instanceof BambooSaplingBlock) && !(blockState.m_60808_(this.world, pos).m_83297_(Axis.Y) > 1.0D)) {
         FluidState fluid = this.world.m_6425_(pos);
         if (fluid != null && !fluid.m_76178_() && (fluid.m_76152_() == Fluids.f_76195_ || fluid.m_76152_() == Fluids.f_76194_)) {
            return SurfaceType.NOT_PASSABLE;
         } else {
            return this.isPassable(blockState, pos, parent) ? SurfaceType.FLYABLE : SurfaceType.DROPABLE;
         }
      } else {
         return SurfaceType.NOT_PASSABLE;
      }
   }

   protected SurfaceType isWalkableSurface(BlockState blockState, BlockPos pos) {
      Block block = blockState.m_60734_();
      if (!(block instanceof FenceBlock) && !(block instanceof FenceGateBlock) && !(block instanceof WallBlock) && !(block instanceof FireBlock) && !(block instanceof CampfireBlock) && !(block instanceof BambooStalkBlock) && !(block instanceof BambooSaplingBlock) && !(blockState.m_60808_(this.world, pos).m_83297_(Axis.Y) > 1.0D)) {
         FluidState fluid = this.world.m_6425_(pos);
         if (fluid != null && !fluid.m_76178_() && (fluid.m_76152_() == Fluids.f_76195_ || fluid.m_76152_() == Fluids.f_76194_)) {
            return SurfaceType.NOT_PASSABLE;
         } else if (block instanceof SignBlock) {
            return SurfaceType.DROPABLE;
         } else {
            return !blockState.m_280296_() && (blockState.m_60734_() != Blocks.f_50125_ || (Integer)blockState.m_61143_(SnowLayerBlock.f_56581_) <= 1) && !(block instanceof WoolCarpetBlock) ? SurfaceType.DROPABLE : SurfaceType.WALKABLE;
         }
      } else {
         return SurfaceType.NOT_PASSABLE;
      }
   }

   protected boolean isLadder(Block block, BlockPos pos) {
      return block.isLadder(this.world.m_8055_(pos), this.world, pos, (LivingEntity)this.entity.get());
   }

   protected boolean isLadder(BlockPos pos) {
      return this.isLadder(this.world.m_8055_(pos).m_60734_(), pos);
   }

   public void setPathingOptions(PathingOptions pathingOptions) {
      this.pathingOptions = pathingOptions;
   }

   public boolean isInRestrictedArea(BlockPos pos) {
      if (this.restrictionType == AbstractAdvancedPathNavigate.RestrictionType.NONE) {
         return true;
      } else {
         boolean isInXZ = pos.m_123341_() <= this.maxX && pos.m_123343_() <= this.maxZ && pos.m_123343_() >= this.minZ && pos.m_123341_() >= this.minX;
         if (!isInXZ) {
            return false;
         } else if (this.restrictionType == AbstractAdvancedPathNavigate.RestrictionType.XZ) {
            return true;
         } else {
            return pos.m_123342_() <= this.maxY && pos.m_123342_() >= this.minY;
         }
      }
   }
}
