package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockIceSpikes extends Block {
   protected static final VoxelShape AABB = Block.m_49796_(1.0D, 0.0D, 1.0D, 15.0D, 8.0D, 15.0D);
   public Item itemBlock;

   public BlockIceSpikes() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283828_).m_60955_().m_60988_().m_60977_().m_60918_(SoundType.f_56744_).m_60978_(2.5F).m_60999_());
   }

   @NotNull
   public BlockState m_7417_(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
      return !stateIn.m_60710_(worldIn, currentPos) ? Blocks.f_50016_.m_49966_() : super.m_7417_(stateIn, facing, facingState, worldIn, currentPos, facingPos);
   }

   public boolean m_7898_(@NotNull BlockState state, @NotNull LevelReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.m_7495_();
      return this.isValidGround(worldIn.m_8055_(blockpos), worldIn, blockpos);
   }

   public boolean m_7420_(@NotNull BlockState state, @NotNull BlockGetter reader, @NotNull BlockPos pos) {
      return true;
   }

   private boolean isValidGround(BlockState blockState, LevelReader worldIn, BlockPos blockpos) {
      return blockState.m_60815_();
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return AABB;
   }

   @NotNull
   public VoxelShape m_5939_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return AABB;
   }

   public void m_141947_(Level worldIn, BlockPos pos, BlockState pState, Entity entityIn) {
      if (!(entityIn instanceof EntityIceDragon)) {
         entityIn.m_6469_(worldIn.m_269111_().m_269325_(), 1.0F);
         if (entityIn instanceof LivingEntity && entityIn.m_20184_().f_82479_ != 0.0D && entityIn.m_20184_().f_82481_ != 0.0D) {
            ((LivingEntity)entityIn).m_147240_(0.5D, entityIn.m_20184_().f_82479_, entityIn.m_20184_().f_82481_);
         }
      }

   }

   public boolean m_7923_(@NotNull BlockState state) {
      return true;
   }
}
