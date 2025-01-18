package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import java.util.EnumSet;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;

public class MyrmexAIStoreBabies extends Goal {
   private final EntityMyrmexWorker myrmex;
   private BlockPos nextRoom;

   public MyrmexAIStoreBabies(EntityMyrmexWorker entityIn, double movementSpeedIn) {
      this.nextRoom = BlockPos.f_121853_;
      this.myrmex = entityIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && this.myrmex.holdingBaby() && (this.myrmex.shouldEnterHive() || this.myrmex.m_21573_().m_26571_()) && !this.myrmex.canSeeSky()) {
         MyrmexHive village = this.myrmex.getHive();
         if (village == null) {
            return false;
         } else {
            this.nextRoom = MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getRandomRoom(WorldGenMyrmexHive.RoomType.NURSERY, this.myrmex.m_217043_(), this.myrmex.m_20183_())).m_7494_();
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return this.myrmex.holdingBaby() && !this.myrmex.m_21573_().m_26571_() && this.myrmex.m_20275_((double)this.nextRoom.m_123341_() + 0.5D, (double)this.nextRoom.m_123342_() + 0.5D, (double)this.nextRoom.m_123343_() + 0.5D) > 3.0D && this.myrmex.shouldEnterHive();
   }

   public void m_8056_() {
      this.myrmex.m_21573_().m_26519_((double)this.nextRoom.m_123341_(), (double)this.nextRoom.m_123342_(), (double)this.nextRoom.m_123343_(), 1.5D);
   }

   public void m_8037_() {
      if (this.nextRoom != null && this.myrmex.m_20275_((double)this.nextRoom.m_123341_() + 0.5D, (double)this.nextRoom.m_123342_() + 0.5D, (double)this.nextRoom.m_123343_() + 0.5D) < 4.0D && this.myrmex.holdingBaby() && !this.myrmex.m_20197_().isEmpty()) {
         Iterator var1 = this.myrmex.m_20197_().iterator();

         while(var1.hasNext()) {
            Entity entity = (Entity)var1.next();
            entity.m_8127_();
            this.m_8041_();
            entity.m_20359_(this.myrmex);
         }
      }

   }

   public void m_8041_() {
      this.nextRoom = BlockPos.f_121853_;
      this.myrmex.m_21573_().m_26573_();
   }
}
