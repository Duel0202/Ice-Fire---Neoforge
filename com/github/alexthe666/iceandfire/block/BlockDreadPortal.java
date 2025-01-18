package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDreadPortal;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class BlockDreadPortal extends BaseEntityBlock implements IDreadBlock {
   public BlockDreadPortal() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283808_).m_278166_(PushReaction.BLOCK).m_60988_().m_60913_(-1.0F, 100000.0F).m_60953_((state) -> {
         return 1;
      }).m_60977_());
   }

   public void m_7892_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Entity entity) {
   }

   public void updateTick(Level worldIn, BlockPos pos, BlockState state, RandomSource rand) {
      if (!this.canSurviveAt(worldIn, pos)) {
         worldIn.m_46961_(pos, true);
      }

   }

   public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
      if (!this.canSurviveAt(worldIn, pos)) {
         worldIn.m_46961_(pos, true);
      }

   }

   public boolean canSurviveAt(Level world, BlockPos pos) {
      return DragonUtils.isDreadBlock(world.m_8055_(pos.m_7494_())) && DragonUtils.isDreadBlock(world.m_8055_(pos.m_7495_()));
   }

   public int quantityDropped(RandomSource random) {
      return 0;
   }

   public void m_214162_(@NotNull BlockState stateIn, Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
      BlockEntity tileentity = worldIn.m_7702_(pos);
      if (tileentity instanceof TileEntityDreadPortal) {
         int i = 3;

         for(int j = 0; j < i; ++j) {
            double d0 = (double)((float)pos.m_123341_() + rand.m_188501_());
            double d1 = (double)((float)pos.m_123342_() + rand.m_188501_());
            double d2 = (double)((float)pos.m_123343_() + rand.m_188501_());
            double d3 = ((double)rand.m_188501_() - 0.5D) * 0.25D;
            double d4 = (double)rand.m_188501_() * -0.25D;
            double d5 = ((double)rand.m_188501_() - 0.5D) * 0.25D;
            int k = rand.m_188503_(2) * 2 - 1;
            IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Portal, d0, d1, d2, d3, d4, d5);
         }
      }

   }

   public boolean isOpaqueCube(BlockState state) {
      return false;
   }

   public boolean isFullCube(BlockState state) {
      return false;
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
      return m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.DREAD_PORTAL.get(), TileEntityDreadPortal::tick);
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityDreadPortal(pos, state);
   }
}
