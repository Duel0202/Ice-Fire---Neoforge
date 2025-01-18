package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockDreadBase extends BlockGeneric implements IDragonProof, IDreadBlock {
   public static final BooleanProperty PLAYER_PLACED = BooleanProperty.m_61465_("player_placed");

   public static BlockDreadBase builder(float hardness, float resistance, SoundType sound, MapColor color, NoteBlockInstrument instrument, boolean ignited) {
      Properties props = Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance);
      if (instrument != null) {
         props.m_280658_(instrument);
      }

      if (ignited) {
         props.m_278183_();
      }

      return new BlockDreadBase(props);
   }

   public BlockDreadBase(Properties props) {
      super(props);
   }

   public float m_5880_(BlockState state, @NotNull Player player, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      if ((Boolean)state.m_61143_(PLAYER_PLACED)) {
         float f = 8.0F;
         return player.getDigSpeed(state, pos) / f / 30.0F;
      } else {
         return super.m_5880_(state, player, worldIn, pos);
      }
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{PLAYER_PLACED});
   }

   public BlockState m_5573_(@NotNull BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(PLAYER_PLACED, true);
   }
}
