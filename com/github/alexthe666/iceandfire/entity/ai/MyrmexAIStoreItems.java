package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.block.BlockMyrmexCocoon;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityMyrmexCocoon;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.AdvancedPathNavigate;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathFindingStatus;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.PathResult;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

public class MyrmexAIStoreItems extends Goal {
   private final EntityMyrmexBase myrmex;
   private final double movementSpeed;
   private BlockPos nextRoom = null;
   private BlockPos nextCocoon = null;
   private BlockPos mainRoom = null;
   private boolean first = true;
   private PathResult path;

   public MyrmexAIStoreItems(EntityMyrmexBase entityIn, double movementSpeedIn) {
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && (!(this.myrmex instanceof EntityMyrmexWorker) || !((EntityMyrmexWorker)this.myrmex).holdingBaby()) && (this.myrmex.shouldEnterHive() || this.myrmex.m_21573_().m_26571_()) && !this.myrmex.m_21120_(InteractionHand.MAIN_HAND).m_41619_()) {
         if (this.myrmex.m_21573_() instanceof AdvancedPathNavigate && !this.myrmex.m_20159_()) {
            if (this.myrmex.getWaitTicks() > 0) {
               return false;
            } else {
               MyrmexHive village = this.myrmex.getHive();
               if (village == null) {
                  return false;
               } else if (!this.myrmex.isInHive()) {
                  return false;
               } else {
                  this.first = true;
                  this.mainRoom = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getCenter());
                  this.nextRoom = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getRandomRoom(WorldGenMyrmexHive.RoomType.FOOD, this.myrmex.m_217043_(), this.myrmex.m_20183_()));
                  this.nextCocoon = this.getNearbyCocoon(this.nextRoom);
                  if (this.nextCocoon == null) {
                     this.myrmex.setWaitTicks(20 + ThreadLocalRandom.current().nextInt(40));
                  }

                  this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.mainRoom.m_123341_() + 0.5D, (double)this.mainRoom.m_123342_() + 0.5D, (double)this.mainRoom.m_123343_() + 0.5D, this.movementSpeed);
                  return this.nextCocoon != null;
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
      return !this.myrmex.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && this.nextCocoon != null && this.isUseableCocoon(this.nextCocoon) && !this.myrmex.isCloseEnoughToTarget(this.nextCocoon, 3.0D) && this.myrmex.shouldEnterHive();
   }

   public void m_8037_() {
      if (this.first && this.mainRoom != null) {
         if (this.myrmex.isCloseEnoughToTarget(this.mainRoom, 10.0D)) {
            this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.nextCocoon.m_123341_() + 0.5D, (double)this.nextCocoon.m_123342_() + 0.5D, (double)this.nextCocoon.m_123343_() + 0.5D, this.movementSpeed);
            this.first = false;
         } else if (!this.myrmex.pathReachesTarget(this.path, this.mainRoom, 9.0D)) {
            this.nextCocoon = null;
         }
      }

      if (!this.first && this.nextCocoon != null) {
         double dist = 9.0D;
         if (this.myrmex.isCloseEnoughToTarget(this.nextCocoon, 9.0D) && !this.myrmex.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && this.isUseableCocoon(this.nextCocoon)) {
            TileEntityMyrmexCocoon cocoon = (TileEntityMyrmexCocoon)this.myrmex.m_9236_().m_7702_(this.nextCocoon);
            ItemStack itemstack = this.myrmex.m_21120_(InteractionHand.MAIN_HAND);
            if (!itemstack.m_41619_()) {
               for(int i = 0; i < cocoon.m_6643_(); ++i) {
                  if (!itemstack.m_41619_()) {
                     ItemStack cocoonStack = cocoon.m_8020_(i);
                     if (cocoonStack.m_41619_()) {
                        cocoon.m_6836_(i, itemstack.m_41777_());
                        cocoon.m_6596_();
                        this.myrmex.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
                        this.myrmex.isEnteringHive = false;
                        return;
                     }

                     if (cocoonStack.m_41720_() == itemstack.m_41720_()) {
                        int j = Math.min(cocoon.m_6893_(), cocoonStack.m_41741_());
                        int k = Math.min(itemstack.m_41613_(), j - cocoonStack.m_41613_());
                        if (k > 0) {
                           cocoonStack.m_41769_(k);
                           itemstack.m_41774_(k);
                           if (itemstack.m_41619_()) {
                              cocoon.m_6596_();
                           }

                           this.myrmex.m_21008_(InteractionHand.MAIN_HAND, ItemStack.f_41583_);
                           this.myrmex.isEnteringHive = false;
                           return;
                        }
                     }
                  }
               }
            }
         } else if (!this.myrmex.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && this.path.getStatus() == PathFindingStatus.COMPLETE && !this.myrmex.pathReachesTarget(this.path, this.nextCocoon, 9.0D)) {
            this.nextCocoon = this.getNearbyCocoon(this.nextRoom);
            if (this.nextCocoon != null) {
               this.path = ((AdvancedPathNavigate)this.myrmex.m_21573_()).moveToXYZ((double)this.nextCocoon.m_123341_() + 0.5D, (double)this.nextCocoon.m_123342_() + 0.5D, (double)this.nextCocoon.m_123343_() + 0.5D, this.movementSpeed);
            }
         } else if (this.myrmex.pathReachesTarget(this.path, this.nextCocoon, 9.0D) && this.path.isCancelled()) {
            this.m_8041_();
         }
      }

   }

   public void m_8041_() {
      this.nextRoom = null;
      this.nextCocoon = null;
      this.mainRoom = null;
      this.first = true;
   }

   public BlockPos getNearbyCocoon(BlockPos roomCenter) {
      int RADIUS_XZ = 15;
      int RADIUS_Y = 7;
      List<BlockPos> closeCocoons = new ArrayList();
      BlockPos.m_121990_(roomCenter.m_7918_(-RADIUS_XZ, -RADIUS_Y, -RADIUS_XZ), roomCenter.m_7918_(RADIUS_XZ, RADIUS_Y, RADIUS_XZ)).forEach((blockpos) -> {
         BlockEntity te = this.myrmex.m_9236_().m_7702_(blockpos);
         if (te instanceof TileEntityMyrmexCocoon && !((TileEntityMyrmexCocoon)te).isFull(this.myrmex.m_21120_(InteractionHand.MAIN_HAND))) {
            closeCocoons.add(te.m_58899_());
         }

      });
      return closeCocoons.isEmpty() ? null : (BlockPos)closeCocoons.get(this.myrmex.m_217043_().m_188503_(Math.max(closeCocoons.size() - 1, 1)));
   }

   public boolean isUseableCocoon(BlockPos blockpos) {
      if (this.myrmex.m_9236_().m_8055_(blockpos).m_60734_() instanceof BlockMyrmexCocoon && this.myrmex.m_9236_().m_7702_(blockpos) != null && this.myrmex.m_9236_().m_7702_(blockpos) instanceof TileEntityMyrmexCocoon) {
         return !((TileEntityMyrmexCocoon)this.myrmex.m_9236_().m_7702_(blockpos)).isFull(this.myrmex.m_21120_(InteractionHand.MAIN_HAND));
      } else {
         return false;
      }
   }
}
