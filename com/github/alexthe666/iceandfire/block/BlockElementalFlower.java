package com.github.alexthe666.iceandfire.block;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockElementalFlower extends BushBlock {
   public Item itemBlock;
   protected static final VoxelShape SHAPE = Block.m_49796_(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);

   public BlockElementalFlower() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283915_).m_280170_().m_278183_().m_278166_(PushReaction.DESTROY).m_60955_().m_60910_().m_60988_().m_60977_().m_60918_(SoundType.f_56740_));
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return SHAPE;
   }

   protected boolean m_6266_(BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      Block block = state.m_60734_();
      return block == Blocks.f_50440_ || block == Blocks.f_50493_ || block == Blocks.f_50546_ || block == Blocks.f_50599_ || block == Blocks.f_50093_ || state.m_204336_(BlockTags.f_13029_);
   }

   public boolean canStay(Level worldIn, BlockPos pos) {
      BlockState soil = worldIn.m_8055_(pos.m_7495_());
      if (this == IafBlockRegistry.FIRE_LILY.get()) {
         return soil.m_204336_(BlockTags.f_13029_) || soil.m_60713_(Blocks.f_50134_);
      } else if (this == IafBlockRegistry.LIGHTNING_LILY.get()) {
         return soil.m_204336_(BlockTags.f_144274_) || soil.m_60713_(Blocks.f_50034_);
      } else {
         return soil.m_204336_(BlockTags.f_13047_) || soil.m_204336_(BlockTags.f_144279_) || soil.m_204336_(BlockTags.f_215834_);
      }
   }

   public void updateTick(Level worldIn, BlockPos pos, BlockState state, Random rand) {
      this.checkFall(worldIn, pos);
   }

   private boolean checkFall(Level worldIn, BlockPos pos) {
      if (!this.canStay(worldIn, pos)) {
         worldIn.m_46961_(pos, true);
         return false;
      } else {
         return true;
      }
   }

   protected boolean canSustainBush(BlockState state) {
      return true;
   }
}
