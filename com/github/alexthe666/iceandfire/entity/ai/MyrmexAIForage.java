package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.api.event.GenericGriefEvent;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.MinecraftForge;

public class MyrmexAIForage extends Goal {
   private static final int RADIUS = 16;
   private final EntityMyrmexWorker myrmex;
   private final MyrmexAIForage.BlockSorter targetSorter;
   private BlockPos targetBlock = null;
   private int wanderRadius;
   private final int chance;
   private PathResult path;
   private int failedToFindPath = 0;

   public MyrmexAIForage(EntityMyrmexWorker myrmex, int chanceIn) {
      this.myrmex = myrmex;
      this.targetSorter = new MyrmexAIForage.BlockSorter();
      this.m_7021_(EnumSet.of(Flag.MOVE));
      this.chance = chanceIn;
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && !this.myrmex.holdingSomething() && this.myrmex.m_21573_().m_26571_() && !this.myrmex.isInHive() && !this.myrmex.shouldEnterHive()) {
         if (this.myrmex.m_21573_() instanceof AdvancedPathNavigate && !this.myrmex.m_20159_()) {
            if (this.myrmex.getWaitTicks() > 0) {
               return false;
            } else {
               List<BlockPos> edibleBlocks = this.getEdibleBlocks();
               if (!edibleBlocks.isEmpty()) {
                  edibleBlocks.sort(this.targetSorter);
                  this.targetBlock = (BlockPos)edibleBlocks.get(0);
                  this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.targetBlock.m_123341_(), (double)this.targetBlock.m_123342_(), (double)this.targetBlock.m_123343_(), 1.0D);
                  return this.myrmex.m_217043_().m_188503_(this.chance) == 0;
               } else {
                  return this.myrmex.m_217043_().m_188503_(this.chance) == 0 && this.increaseRadiusAndWander();
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      if (this.targetBlock == null) {
         return false;
      } else if (this.myrmex.getWaitTicks() > 0) {
         return false;
      } else if (this.myrmex.shouldEnterHive()) {
         this.myrmex.keepSearching = false;
         return false;
      } else {
         return !this.myrmex.holdingSomething();
      }
   }

   public void m_8037_() {
      if (this.targetBlock != null && this.myrmex.keepSearching) {
         if (this.myrmex.isCloseEnoughToTarget(this.targetBlock, 12.0D) || !this.myrmex.pathReachesTarget(this.path, this.targetBlock, 12.0D)) {
            this.failedToFindPath = 0;
            List<BlockPos> edibleBlocks = this.getEdibleBlocks();
            if (!edibleBlocks.isEmpty()) {
               this.myrmex.keepSearching = false;
               edibleBlocks.sort(this.targetSorter);
               this.targetBlock = (BlockPos)edibleBlocks.get(0);
               this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.targetBlock.m_123341_(), (double)this.targetBlock.m_123342_(), (double)this.targetBlock.m_123343_(), 1.0D);
            } else {
               this.increaseRadiusAndWander();
            }
         }
      } else if (!this.myrmex.keepSearching) {
         this.failedToFindPath = 0;
         BlockState block = this.myrmex.m_9236_().m_8055_(this.targetBlock);
         if (EntityMyrmexBase.isEdibleBlock(block)) {
            double distance = this.getDistanceSq(this.targetBlock);
            List edibleBlocks;
            if (distance < 6.0D) {
               block.m_60734_();
               edibleBlocks = Block.m_49869_(block, (ServerLevel)this.myrmex.m_9236_(), this.targetBlock, this.myrmex.m_9236_().m_7702_(this.targetBlock));
               if (!edibleBlocks.isEmpty()) {
                  this.myrmex.m_9236_().m_46961_(this.targetBlock, false);
                  ItemStack heldStack = ((ItemStack)edibleBlocks.get(0)).m_41777_();
                  heldStack.m_41764_(1);
                  ((ItemStack)edibleBlocks.get(0)).m_41774_(1);
                  this.myrmex.m_21008_(InteractionHand.MAIN_HAND, heldStack);
                  Iterator var6 = edibleBlocks.iterator();

                  while(var6.hasNext()) {
                     ItemStack stack = (ItemStack)var6.next();
                     ItemEntity itemEntity = new ItemEntity(this.myrmex.m_9236_(), (double)this.targetBlock.m_123341_() + this.myrmex.m_217043_().m_188500_(), (double)this.targetBlock.m_123342_() + this.myrmex.m_217043_().m_188500_(), (double)this.targetBlock.m_123343_() + this.myrmex.m_217043_().m_188500_(), stack);
                     itemEntity.m_32060_();
                     if (!this.myrmex.m_9236_().f_46443_) {
                        this.myrmex.m_9236_().m_7967_(itemEntity);
                     }
                  }

                  this.targetBlock = null;
                  this.m_8041_();
                  this.myrmex.keepSearching = false;
                  this.wanderRadius = 16;
               }
            } else if (!this.myrmex.pathReachesTarget(this.path, this.targetBlock, 12.0D)) {
               edibleBlocks = this.getEdibleBlocks();
               if (!edibleBlocks.isEmpty()) {
                  this.myrmex.keepSearching = false;
                  this.targetBlock = (BlockPos)edibleBlocks.get(this.myrmex.m_217043_().m_188503_(edibleBlocks.size()));
                  this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.targetBlock.m_123341_(), (double)this.targetBlock.m_123342_(), (double)this.targetBlock.m_123343_(), 1.0D);
               } else {
                  this.myrmex.keepSearching = true;
               }
            }
         }
      }

   }

   public void m_8041_() {
      this.targetBlock = null;
      this.myrmex.keepSearching = true;
   }

   private double getDistanceSq(BlockPos pos) {
      double deltaX = this.myrmex.m_20185_() - ((double)pos.m_123341_() + 0.5D);
      double deltaY = this.myrmex.m_20186_() + (double)this.myrmex.m_20192_() - ((double)pos.m_123342_() + 0.5D);
      double deltaZ = this.myrmex.m_20189_() - ((double)pos.m_123343_() + 0.5D);
      return deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;
   }

   private List<BlockPos> getEdibleBlocks() {
      List<BlockPos> allBlocks = new ArrayList();
      BlockPos.m_121990_(this.myrmex.m_20183_().m_7918_(-16, -8, -16), this.myrmex.m_20183_().m_7918_(16, 8, 16)).map(BlockPos::m_7949_).forEach((pos) -> {
         if (!MinecraftForge.EVENT_BUS.post(new GenericGriefEvent(this.myrmex, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_())) && EntityMyrmexBase.isEdibleBlock(this.myrmex.m_9236_().m_8055_(pos))) {
            allBlocks.add(pos);
            this.myrmex.keepSearching = false;
         }

      });
      return allBlocks;
   }

   private boolean increaseRadiusAndWander() {
      this.myrmex.keepSearching = true;
      if (this.myrmex.getHive() != null) {
         this.wanderRadius = this.myrmex.getHive().getWanderRadius();
         this.myrmex.getHive().setWanderRadius(this.wanderRadius * 2);
      }

      this.wanderRadius *= 2;
      if (this.wanderRadius >= IafConfig.myrmexMaximumWanderRadius) {
         this.wanderRadius = IafConfig.myrmexMaximumWanderRadius;
         this.myrmex.setWaitTicks(80 + ThreadLocalRandom.current().nextInt(40));
         ++this.failedToFindPath;
         if (this.failedToFindPath >= 10) {
            this.myrmex.setWaitTicks(800 + ThreadLocalRandom.current().nextInt(40));
         }
      }

      Vec3 vec = DefaultRandomPos.m_148403_(this.myrmex, this.wanderRadius, 7);
      if (vec != null) {
         this.targetBlock = BlockPos.m_274446_(vec);
      }

      if (this.targetBlock != null) {
         this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.targetBlock.m_123341_(), (double)this.targetBlock.m_123342_(), (double)this.targetBlock.m_123343_(), 1.0D);
         return true;
      } else {
         return false;
      }
   }

   public class BlockSorter implements Comparator<BlockPos> {
      public int compare(BlockPos pos1, BlockPos pos2) {
         double distance1 = MyrmexAIForage.this.getDistanceSq(pos1);
         double distance2 = MyrmexAIForage.this.getDistanceSq(pos2);
         return Double.compare(distance1, distance2);
      }
   }
}
