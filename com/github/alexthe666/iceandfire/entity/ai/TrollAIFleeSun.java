package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityTroll;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class TrollAIFleeSun extends Goal {
   private final EntityTroll troll;
   private final double movementSpeed;
   private final Level world;
   private double shelterX;
   private double shelterY;
   private double shelterZ;

   public TrollAIFleeSun(EntityTroll theCreatureIn, double movementSpeedIn) {
      this.troll = theCreatureIn;
      this.movementSpeed = movementSpeedIn;
      this.world = theCreatureIn.m_9236_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.world.m_46461_()) {
         return false;
      } else if (!this.world.m_45527_(BlockPos.m_274561_((double)this.troll.m_146903_(), this.troll.m_20191_().f_82289_, (double)this.troll.m_146907_()))) {
         return false;
      } else {
         Vec3 Vector3d = this.findPossibleShelter();
         if (Vector3d == null) {
            return false;
         } else {
            this.shelterX = Vector3d.f_82479_;
            this.shelterY = Vector3d.f_82480_;
            this.shelterZ = Vector3d.f_82481_;
            return true;
         }
      }
   }

   public boolean m_8045_() {
      return !this.troll.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.troll.m_21573_().m_26519_(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
   }

   @Nullable
   private Vec3 findPossibleShelter() {
      RandomSource random = this.troll.m_217043_();
      BlockPos blockpos = BlockPos.m_274561_((double)this.troll.m_146903_(), this.troll.m_20191_().f_82289_, (double)this.troll.m_146907_());

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = blockpos.m_7918_(random.m_188503_(20) - 10, random.m_188503_(6) - 3, random.m_188503_(20) - 10);
         if (!this.world.m_45527_(blockpos1) && this.troll.m_21692_(blockpos1) < 0.0F) {
            return new Vec3((double)blockpos1.m_123341_(), (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_());
         }
      }

      return null;
   }
}
