package com.github.alexthe666.iceandfire.pathfinding;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.Target;
import org.jetbrains.annotations.NotNull;

public class NodeProcessorDeathWorm extends NodeEvaluator {
   @NotNull
   public Node m_7171_() {
      return this.m_5676_(Mth.m_14107_(this.f_77313_.m_20191_().f_82288_), Mth.m_14107_(this.f_77313_.m_20191_().f_82289_ + 0.5D), Mth.m_14107_(this.f_77313_.m_20191_().f_82290_));
   }

   @NotNull
   public Target m_7568_(double x, double y, double z) {
      return new Target(this.m_5676_(Mth.m_14107_(x - 0.4D), Mth.m_14107_(y + 0.5D), Mth.m_14107_(z - 0.4D)));
   }

   @NotNull
   public BlockPathTypes m_7209_(@NotNull BlockGetter blockaccessIn, int x, int y, int z, @NotNull Mob entitylivingIn) {
      return this.m_8086_(blockaccessIn, x, y, z);
   }

   @NotNull
   public BlockPathTypes m_8086_(BlockGetter worldIn, int x, int y, int z) {
      BlockPos blockpos = new BlockPos(x, y, z);
      BlockState blockstate = worldIn.m_8055_(blockpos);
      if (this.isPassable(worldIn, blockpos.m_7495_()) || !blockstate.m_60795_() && !this.isPassable(worldIn, blockpos)) {
         return this.isPassable(worldIn, blockpos) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
      } else {
         return BlockPathTypes.BREACH;
      }
   }

   public int m_6065_(@NotNull Node[] p_222859_1_, @NotNull Node p_222859_2_) {
      int i = 0;
      Direction[] var4 = Direction.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Direction direction = var4[var6];
         Node pathpoint = this.getSandNode(p_222859_2_.f_77271_ + direction.m_122429_(), p_222859_2_.f_77272_ + direction.m_122430_(), p_222859_2_.f_77273_ + direction.m_122431_());
         if (pathpoint != null && !pathpoint.f_77279_) {
            p_222859_1_[i++] = pathpoint;
         }
      }

      return i;
   }

   @Nullable
   private Node getSandNode(int p_186328_1_, int p_186328_2_, int p_186328_3_) {
      BlockPathTypes pathnodetype = this.isFree(p_186328_1_, p_186328_2_, p_186328_3_);
      return pathnodetype != BlockPathTypes.BREACH && pathnodetype != BlockPathTypes.WATER ? null : this.m_5676_(p_186328_1_, p_186328_2_, p_186328_3_);
   }

   private BlockPathTypes isFree(int p_186327_1_, int p_186327_2_, int p_186327_3_) {
      MutableBlockPos blockpos$mutable = new MutableBlockPos();

      for(int i = p_186327_1_; i < p_186327_1_ + this.f_77315_; ++i) {
         for(int j = p_186327_2_; j < p_186327_2_ + this.f_77316_; ++j) {
            for(int k = p_186327_3_; k < p_186327_3_ + this.f_77317_; ++k) {
               BlockState blockstate = this.f_77312_.m_8055_(blockpos$mutable.m_122178_(i, j, k));
               if (!this.isPassable(this.f_77312_, blockpos$mutable.m_7495_()) && (blockstate.m_60795_() || this.isPassable(this.f_77312_, blockpos$mutable))) {
                  return BlockPathTypes.BREACH;
               }
            }
         }
      }

      BlockState blockstate1 = this.f_77312_.m_8055_(blockpos$mutable);
      return this.isPassable(blockstate1) ? BlockPathTypes.WATER : BlockPathTypes.BLOCKED;
   }

   private boolean isPassable(BlockGetter world, BlockPos pos) {
      return world.m_8055_(pos).m_204336_(BlockTags.f_13029_) || world.m_8055_(pos).m_60795_();
   }

   private boolean isPassable(BlockState state) {
      return state.m_204336_(BlockTags.f_13029_) || state.m_60795_();
   }
}
