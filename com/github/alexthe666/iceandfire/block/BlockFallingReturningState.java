package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockFallingReturningState extends FallingBlock {
   public static final BooleanProperty REVERTS = BooleanProperty.m_61465_("revert");
   public Item itemBlock;
   private final BlockState returnState;

   public BlockFallingReturningState(float hardness, float resistance, SoundType sound, MapColor color, BlockState revertState) {
      super(Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance).m_60977_());
      this.returnState = revertState;
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(REVERTS, Boolean.FALSE));
   }

   public BlockFallingReturningState(float hardness, float resistance, SoundType sound, boolean slippery, MapColor color, BlockState revertState) {
      super(Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance).m_60977_());
      this.returnState = revertState;
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(REVERTS, Boolean.FALSE));
   }

   public void m_213897_(@NotNull BlockState state, @NotNull ServerLevel worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
      super.m_213897_(state, worldIn, pos, rand);
      if (!worldIn.f_46443_) {
         if (!worldIn.isAreaLoaded(pos, 3)) {
            return;
         }

         if ((Boolean)state.m_61143_(REVERTS) && rand.m_188503_(3) == 0) {
            worldIn.m_46597_(pos, this.returnState);
         }
      }

   }

   public int getDustColor(BlockState blkst) {
      return -8356741;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{REVERTS});
   }
}
