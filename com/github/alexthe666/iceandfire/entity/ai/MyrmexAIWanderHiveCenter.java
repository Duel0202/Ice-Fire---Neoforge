package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.pathfinder.Path;

public class MyrmexAIWanderHiveCenter extends Goal {
   private final EntityMyrmexBase myrmex;
   private final double movementSpeed;
   private Path path;
   private BlockPos target;

   public MyrmexAIWanderHiveCenter(EntityMyrmexBase entityIn, double movementSpeedIn) {
      this.target = BlockPos.f_121853_;
      this.myrmex = entityIn;
      this.movementSpeed = movementSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && (this.myrmex.shouldEnterHive() || this.myrmex.m_21573_().m_26571_()) && !this.myrmex.canSeeSky()) {
         MyrmexHive village = MyrmexWorldData.get(this.myrmex.m_9236_()).getNearestHive(this.myrmex.m_20183_(), 300);
         if (village == null) {
            village = this.myrmex.getHive();
         }

         if (village == null) {
            return false;
         } else {
            this.target = this.getNearPos(MyrmexHive.getGroundedPos(this.myrmex.m_9236_(), village.getCenter()));
            this.path = this.myrmex.m_21573_().m_7864_(this.target, 0);
            return this.path != null;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.myrmex.m_21573_().m_26571_() && this.myrmex.m_20275_((double)this.target.m_123341_() + 0.5D, (double)this.target.m_123342_() + 0.5D, (double)this.target.m_123343_() + 0.5D) > 3.0D && this.myrmex.shouldEnterHive();
   }

   public void m_8056_() {
      this.myrmex.m_21573_().m_26536_(this.path, this.movementSpeed);
   }

   public void m_8041_() {
      this.target = BlockPos.f_121853_;
      this.myrmex.m_21573_().m_26536_((Path)null, this.movementSpeed);
   }

   public BlockPos getNearPos(BlockPos pos) {
      return pos.m_7918_(this.myrmex.m_217043_().m_188503_(8) - 4, 0, this.myrmex.m_217043_().m_188503_(8) - 4);
   }
}
