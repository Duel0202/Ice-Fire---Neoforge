package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class BlockMyrmexBiolight extends BushBlock {
   public static final BooleanProperty CONNECTED_DOWN = BooleanProperty.m_61465_("down");

   public BlockMyrmexBiolight() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283915_).m_278166_(PushReaction.DESTROY).m_60955_().m_60910_().m_60988_().m_60978_(0.0F).m_60953_((state) -> {
         return 7;
      }).m_60918_(SoundType.f_56740_).m_60977_());
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(CONNECTED_DOWN, Boolean.FALSE));
   }

   public boolean m_7898_(@NotNull BlockState state, LevelReader worldIn, BlockPos pos) {
      BlockPos blockpos = pos.m_7494_();
      return worldIn.m_8055_(blockpos).m_60734_() == this || worldIn.m_8055_(blockpos).m_60815_();
   }

   @NotNull
   public BlockState m_7417_(BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, @NotNull BlockPos facingPos) {
      boolean flag3 = worldIn.m_8055_(currentPos.m_7495_()).m_60734_() == this;
      return (BlockState)stateIn.m_61124_(CONNECTED_DOWN, flag3);
   }

   public void m_213897_(@NotNull BlockState state, ServerLevel worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
      if (!worldIn.f_46443_) {
         this.updateState(state, worldIn, pos, state.m_60734_());
      }

      if (!worldIn.m_8055_(pos.m_7494_()).m_60815_() && worldIn.m_8055_(pos.m_7494_()).m_60734_() != this) {
         worldIn.m_46961_(pos, true);
      }

   }

   public void updateState(BlockState state, Level worldIn, BlockPos pos, Block blockIn) {
      boolean flag2 = (Boolean)state.m_61143_(CONNECTED_DOWN);
      boolean flag3 = worldIn.m_8055_(pos.m_7495_()).m_60734_() == this;
      if (flag2 != flag3) {
         worldIn.m_7731_(pos, (BlockState)state.m_61124_(CONNECTED_DOWN, flag3), 3);
      }

   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{CONNECTED_DOWN});
   }
}
