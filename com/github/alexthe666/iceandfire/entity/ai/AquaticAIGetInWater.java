package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntitySiren;
import java.util.EnumSet;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class AquaticAIGetInWater extends Goal {
   private final Mob creature;
   private final double movementSpeed;
   private final Level world;
   private double shelterX;
   private double shelterY;
   private double shelterZ;

   public AquaticAIGetInWater(Mob theCreatureIn, double movementSpeedIn) {
      this.creature = theCreatureIn;
      this.movementSpeed = movementSpeedIn;
      this.world = theCreatureIn.m_9236_();
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   protected boolean isAttackerInWater() {
      return this.creature.m_5448_() != null && !this.creature.m_5448_().m_20069_();
   }

   public boolean m_8036_() {
      if (this.creature.m_20160_() || this.creature instanceof TamableAnimal && ((TamableAnimal)this.creature).m_21824_() || this.creature.m_20069_() || this.isAttackerInWater() || this.creature instanceof EntitySiren && (((EntitySiren)this.creature).isSinging() || ((EntitySiren)this.creature).wantsToSing())) {
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
      return !this.creature.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.creature.m_21573_().m_26519_(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
   }

   @Nullable
   public Vec3 findPossibleShelter() {
      return this.findPossibleShelter(10, 3);
   }

   @Nullable
   protected Vec3 findPossibleShelter(int xz, int y) {
      RandomSource random = this.creature.m_217043_();
      BlockPos blockpos = BlockPos.m_274561_((double)this.creature.m_146903_(), this.creature.m_20191_().f_82289_, (double)this.creature.m_146907_());

      for(int i = 0; i < 10; ++i) {
         BlockPos blockpos1 = blockpos.m_7918_(random.m_188503_(xz * 2) - xz, random.m_188503_(y * 2) - y, random.m_188503_(xz * 2) - xz);
         if (this.world.m_8055_(blockpos1).m_60713_(Blocks.f_49990_)) {
            return new Vec3((double)blockpos1.m_123341_(), (double)blockpos1.m_123342_(), (double)blockpos1.m_123343_());
         }
      }

      return null;
   }
}
