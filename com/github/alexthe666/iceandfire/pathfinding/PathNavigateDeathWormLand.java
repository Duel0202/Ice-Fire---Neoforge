package com.github.alexthe666.iceandfire.pathfinding;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class PathNavigateDeathWormLand extends PathNavigation {
   private boolean shouldAvoidSun;
   private final EntityDeathWorm worm;

   public PathNavigateDeathWormLand(EntityDeathWorm worm, Level worldIn) {
      super(worm, worldIn);
      this.worm = worm;
   }

   @NotNull
   protected PathFinder m_5532_(int i) {
      this.f_26508_ = new WalkNodeEvaluator();
      this.f_26508_.m_77351_(true);
      this.f_26508_.m_77358_(true);
      return new PathFinder(this.f_26508_, i);
   }

   protected boolean m_7632_() {
      return this.f_26494_.m_20096_() || this.worm.isInSand() || this.f_26494_.m_20159_();
   }

   @NotNull
   protected Vec3 m_7475_() {
      return new Vec3(this.f_26494_.m_20185_(), (double)this.getPathablePosY(), this.f_26494_.m_20189_());
   }

   public Path m_7864_(@NotNull BlockPos pos, int i) {
      BlockPos blockpos1;
      if (this.f_26495_.m_8055_(pos).m_60795_()) {
         for(blockpos1 = pos.m_7495_(); blockpos1.m_123342_() > 0 && this.f_26495_.m_8055_(blockpos1).m_60795_(); blockpos1 = blockpos1.m_7495_()) {
         }

         if (blockpos1.m_123342_() > 0) {
            return super.m_7864_(blockpos1.m_7494_(), i);
         }

         while(blockpos1.m_123342_() < this.f_26495_.m_151558_() && this.f_26495_.m_8055_(blockpos1).m_60795_()) {
            blockpos1 = blockpos1.m_7494_();
         }

         pos = blockpos1;
      }

      if (!this.f_26495_.m_8055_(pos).m_280296_()) {
         return super.m_7864_(pos, i);
      } else {
         for(blockpos1 = pos.m_7494_(); blockpos1.m_123342_() < this.f_26495_.m_151558_() && this.f_26495_.m_8055_(blockpos1).m_280296_(); blockpos1 = blockpos1.m_7494_()) {
         }

         return super.m_7864_(blockpos1, i);
      }
   }

   public Path m_6570_(Entity entityIn, int i) {
      return this.m_7864_(entityIn.m_20183_(), i);
   }

   private int getPathablePosY() {
      if (this.worm.isInSand()) {
         int i = (int)this.f_26494_.m_20191_().f_82289_;
         BlockState blockstate = this.f_26495_.m_8055_(new BlockPos(this.f_26494_.m_146903_(), i, this.f_26494_.m_146907_()));
         int j = 0;

         do {
            if (!blockstate.m_204336_(BlockTags.f_13029_)) {
               return i;
            }

            ++i;
            blockstate = this.f_26495_.m_8055_(new BlockPos(this.f_26494_.m_146903_(), i, this.f_26494_.m_146907_()));
            ++j;
         } while(j <= 16);

         return (int)this.f_26494_.m_20191_().f_82289_;
      } else {
         return (int)(this.f_26494_.m_20191_().f_82289_ + 0.5D);
      }
   }

   protected void removeSunnyPath() {
      if (this.shouldAvoidSun) {
         if (this.f_26495_.m_45527_(BlockPos.m_274561_((double)this.f_26494_.m_146903_(), this.f_26494_.m_20191_().f_82289_ + 0.5D, (double)this.f_26494_.m_146907_()))) {
            return;
         }

         for(int i = 0; i < this.f_26496_.m_77398_(); ++i) {
            Node pathpoint = this.f_26496_.m_77375_(i);
            if (this.f_26495_.m_45527_(new BlockPos(pathpoint.f_77271_, pathpoint.f_77272_, pathpoint.f_77273_))) {
               this.f_26496_.m_77388_(i - 1);
               return;
            }
         }
      }

   }

   protected boolean m_183431_(Vec3 posVec31, Vec3 posVec32) {
      int i = Mth.m_14107_(posVec31.f_82479_);
      int j = Mth.m_14107_(posVec31.f_82481_);
      double d0 = posVec32.f_82479_ - posVec31.f_82479_;
      double d1 = posVec32.f_82481_ - posVec31.f_82481_;
      double d2 = d0 * d0 + d1 * d1;
      int sizeX = (int)this.worm.m_20191_().m_82362_();
      int sizeY = (int)this.worm.m_20191_().m_82376_();
      int sizeZ = (int)this.worm.m_20191_().m_82385_();
      if (d2 < 1.0E-8D) {
         return false;
      } else {
         double d3 = 1.0D / Math.sqrt(d2);
         d0 *= d3;
         d1 *= d3;
         sizeX += 2;
         sizeZ += 2;
         if (!this.isSafeToStandAt(i, (int)posVec31.f_82480_, j, sizeX, sizeY, sizeZ, posVec31, d0, d1)) {
            return false;
         } else {
            sizeX -= 2;
            sizeZ -= 2;
            double d4 = 1.0D / Math.abs(d0);
            double d5 = 1.0D / Math.abs(d1);
            double d6 = (double)i - posVec31.f_82479_;
            double d7 = (double)j - posVec31.f_82481_;
            if (d0 >= 0.0D) {
               ++d6;
            }

            if (d1 >= 0.0D) {
               ++d7;
            }

            d6 /= d0;
            d7 /= d1;
            int k = d0 < 0.0D ? -1 : 1;
            int l = d1 < 0.0D ? -1 : 1;
            int i1 = Mth.m_14107_(posVec32.f_82479_);
            int j1 = Mth.m_14107_(posVec32.f_82481_);
            int k1 = i1 - i;
            int l1 = j1 - j;

            do {
               if (k1 * k <= 0 && l1 * l <= 0) {
                  return true;
               }

               if (d6 < d7) {
                  d6 += d4;
                  i += k;
                  k1 = i1 - i;
               } else {
                  d7 += d5;
                  j += l;
                  l1 = j1 - j;
               }
            } while(this.isSafeToStandAt(i, (int)posVec31.f_82480_, j, sizeX, sizeY, sizeZ, posVec31, d0, d1));

            return false;
         }
      }
   }

   private boolean isSafeToStandAt(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 vec31, double p_179683_8_, double p_179683_10_) {
      int i = x - sizeX / 2;
      int j = z - sizeZ / 2;
      if (!this.isPositionClear(i, y, j, sizeX, sizeY, sizeZ, vec31, p_179683_8_, p_179683_10_)) {
         return false;
      } else {
         for(int k = i; k < i + sizeX; ++k) {
            for(int l = j; l < j + sizeZ; ++l) {
               double d0 = (double)k + 0.5D - vec31.f_82479_;
               double d1 = (double)l + 0.5D - vec31.f_82481_;
               if (d0 * p_179683_8_ + d1 * p_179683_10_ >= 0.0D) {
                  BlockPathTypes pathnodetype = this.f_26508_.m_7209_(this.f_26495_, k, y - 1, l, this.f_26494_);
                  if (pathnodetype == BlockPathTypes.LAVA) {
                     return false;
                  }

                  pathnodetype = this.f_26508_.m_7209_(this.f_26495_, k, y, l, this.f_26494_);
                  float f = this.f_26494_.m_21439_(pathnodetype);
                  if (f < 0.0F || f >= 8.0F) {
                     return false;
                  }

                  if (pathnodetype == BlockPathTypes.DAMAGE_FIRE || pathnodetype == BlockPathTypes.DANGER_FIRE || pathnodetype == BlockPathTypes.DAMAGE_OTHER) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean isPositionClear(int x, int y, int z, int sizeX, int sizeY, int sizeZ, Vec3 p_179692_7_, double p_179692_8_, double p_179692_10_) {
      Iterator var12 = ((List)BlockPos.m_121990_(new BlockPos(x, y, z), new BlockPos(x + sizeX - 1, y + sizeY - 1, z + sizeZ - 1)).collect(Collectors.toList())).iterator();

      BlockPos blockpos;
      do {
         double d0;
         double d1;
         do {
            if (!var12.hasNext()) {
               return true;
            }

            blockpos = (BlockPos)var12.next();
            d0 = (double)blockpos.m_123341_() + 0.5D - p_179692_7_.f_82479_;
            d1 = (double)blockpos.m_123343_() + 0.5D - p_179692_7_.f_82481_;
         } while(!(d0 * p_179692_8_ + d1 * p_179692_10_ >= 0.0D));

         Block block = this.f_26495_.m_8055_(blockpos).m_60734_();
      } while(!this.f_26495_.m_8055_(blockpos).m_280555_() && !this.f_26495_.m_8055_(blockpos).m_204336_(BlockTags.f_13029_));

      return false;
   }

   public void setBreakDoors(boolean canBreakDoors) {
      this.f_26508_.m_77355_(canBreakDoors);
   }

   public boolean getEnterDoors() {
      return this.f_26508_.m_77357_();
   }

   public void setEnterDoors(boolean enterDoors) {
      this.f_26508_.m_77351_(enterDoors);
   }

   public boolean m_26576_() {
      return this.f_26508_.m_77361_();
   }

   public void m_7008_(boolean canSwim) {
      this.f_26508_.m_77358_(canSwim);
   }

   public void setAvoidSun(boolean avoidSun) {
      this.shouldAvoidSun = avoidSun;
   }
}
