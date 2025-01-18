package com.github.alexthe666.iceandfire.client.model.util;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LegSolver {
   public final LegSolver.Leg[] legs;

   public LegSolver(LegSolver.Leg... legs) {
      this.legs = legs;
   }

   public final void update(EntityDragonBase entity, float scale) {
      this.update(entity, entity.f_20883_, scale);
   }

   public final void update(EntityDragonBase entity, float yaw, float scale) {
      double sideTheta = (double)yaw / 57.29577951308232D;
      double sideX = (double)(Mth.m_14089_((float)sideTheta) * scale);
      double sideZ = (double)(Mth.m_14031_((float)sideTheta) * scale);
      double forwardTheta = sideTheta + 1.5707963267948966D;
      double forwardX = (double)(Mth.m_14089_((float)forwardTheta) * scale);
      double forwardZ = (double)(Mth.m_14031_((float)forwardTheta) * scale);
      LegSolver.Leg[] var16 = this.legs;
      int var17 = var16.length;

      for(int var18 = 0; var18 < var17; ++var18) {
         LegSolver.Leg leg = var16[var18];
         leg.update(entity, sideX, sideZ, forwardX, forwardZ, scale);
      }

   }

   public static final class Leg {
      public final float forward;
      public final float side;
      private final float range;
      private float height;
      private float prevHeight;
      private final boolean isWing;

      public Leg(float forward, float side, float range, boolean isWing) {
         this.forward = forward;
         this.side = side;
         this.range = range;
         this.isWing = isWing;
      }

      public final float getHeight(float delta) {
         return this.prevHeight + (this.height - this.prevHeight) * delta;
      }

      public void update(EntityDragonBase entity, double sideX, double sideZ, double forwardX, double forwardZ, float scale) {
         this.prevHeight = this.height;
         double posY = entity.m_20186_();
         float settledHeight = this.settle(entity, entity.m_20185_() + sideX * (double)this.side + forwardX * (double)this.forward, posY, entity.m_20189_() + sideZ * (double)this.side + forwardZ * (double)this.forward, this.height);
         this.height = Mth.m_14036_(settledHeight, -this.range * scale, this.range * scale);
      }

      private float settle(EntityDragonBase entity, double x, double y, double z, float height) {
         BlockPos pos = BlockPos.m_274561_(x, y + 0.001D, z);
         float dist = this.getDistance(entity.m_9236_(), pos);
         if ((double)(1.0F - dist) < 0.001D) {
            dist = this.getDistance(entity.m_9236_(), pos.m_7495_()) + (float)y % 1.0F;
         } else {
            dist = (float)((double)dist - (1.0D - y % 1.0D));
         }

         if (entity.m_20096_() && height <= dist) {
            return height == dist ? height : Math.min(height + this.getFallSpeed(), dist);
         } else {
            return height > 0.0F ? Math.max(height - this.getRiseSpeed(), dist) : height;
         }
      }

      private float getDistance(Level world, BlockPos pos) {
         BlockState state = world.m_8055_(pos);
         VoxelShape aabb = state.m_60812_(world, pos);
         return aabb.m_83281_() ? 1.0F : 1.0F - Math.min((float)aabb.m_83290_(Axis.Y, 0.5D, 0.5D), 1.0F);
      }

      protected float getFallSpeed() {
         return 0.25F;
      }

      protected float getRiseSpeed() {
         return 0.25F;
      }
   }
}
