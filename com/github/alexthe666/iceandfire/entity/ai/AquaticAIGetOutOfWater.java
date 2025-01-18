package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class AquaticAIGetOutOfWater extends Goal {
   private final Mob creature;
   private final double movementSpeed;
   private final Level world;
   private double shelterX;
   private double shelterY;
   private double shelterZ;

   public AquaticAIGetOutOfWater(Mob theCreatureIn, double movementSpeedIn) {
      this.creature = theCreatureIn;
      this.movementSpeed = movementSpeedIn;
      this.world = theCreatureIn.m_9236_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.creature.m_20069_() && ((EntitySiren)this.creature).wantsToSing()) {
         Vec3 Vector3d = this.findPossibleShelter();
         if (Vector3d == null) {
            return false;
         } else {
            this.shelterX = Vector3d.f_82479_;
            this.shelterY = Vector3d.f_82480_;
            this.shelterZ = Vector3d.f_82481_;
            return true;
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.creature.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.creature.m_21573_().m_26519_(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
   }

   @Nullable
   private Vec3 findPossibleShelter() {
      RandomSource random = this.creature.m_217043_();
      BlockPos blockpos = BlockPos.m_274561_((double)this.creature.m_146903_(), this.creature.m_20191_().f_82289_, (double)this.creature.m_146907_());

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = blockpos.m_7918_(random.m_188503_(20) - 10, random.m_188503_(6) - 3, random.m_188503_(20) - 10);
         if (this.world.m_8055_(blockpos1).m_60804_(this.world, blockpos1)) {
            return new Vec3((double)blockpos1.m_123341_(), (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_());
         }
      }

      return null;
   }
}
