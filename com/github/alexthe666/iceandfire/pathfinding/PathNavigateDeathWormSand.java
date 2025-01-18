package com.github.alexthe666.iceandfire.pathfinding;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class PathNavigateDeathWormSand extends WaterBoundPathNavigation {
   public PathNavigateDeathWormSand(EntityDeathWorm deathworm, Level worldIn) {
      super(deathworm, worldIn);
   }

   public boolean m_26576_() {
      return this.f_26508_.m_77361_();
   }

   @NotNull
   protected PathFinder m_5532_(int i) {
      this.f_26508_ = new NodeProcessorDeathWorm();
      this.f_26508_.m_77351_(true);
      this.f_26508_.m_77358_(true);
      return new PathFinder(this.f_26508_, i);
   }

   protected boolean m_7632_() {
      return true;
   }

   @NotNull
   protected Vec3 m_7475_() {
      return new Vec3(this.f_26494_.m_20185_(), this.f_26494_.m_20186_() + 0.5D, this.f_26494_.m_20189_());
   }

   protected boolean m_183431_(@NotNull Vec3 start, @NotNull Vec3 end) {
      HitResult raytraceresult = this.f_26495_.m_45547_(new PathNavigateDeathWormSand.CustomRayTraceContext(start, end, Block.COLLIDER, Fluid.NONE, this.f_26494_));
      if (raytraceresult.m_6662_() == Type.BLOCK) {
         Vec3 vec3i = raytraceresult.m_82450_();
         return this.f_26494_.m_9236_().m_8055_(BlockPos.m_274446_(vec3i)).m_204336_(BlockTags.f_13029_);
      } else {
         return raytraceresult.m_6662_() == Type.MISS;
      }
   }

   public boolean m_6342_(@NotNull BlockPos pos) {
      return this.f_26495_.m_8055_(pos).m_60815_();
   }

   public static class CustomRayTraceContext extends ClipContext {
      private final Block blockMode;
      private final CollisionContext context;

      public CustomRayTraceContext(Vec3 startVecIn, Vec3 endVecIn, Block blockModeIn, Fluid fluidModeIn, @Nullable Entity entityIn) {
         super(startVecIn, endVecIn, blockModeIn, fluidModeIn, entityIn);
         this.blockMode = blockModeIn;
         this.context = entityIn == null ? CollisionContext.m_82749_() : CollisionContext.m_82750_(entityIn);
      }

      @NotNull
      public VoxelShape m_45694_(BlockState blockState, @NotNull BlockGetter world, @NotNull BlockPos pos) {
         return blockState.m_204336_(BlockTags.f_13029_) ? Shapes.m_83040_() : this.blockMode.m_7544_(blockState, world, pos, this.context);
      }
   }
}
