package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockDreadStoneFace extends HorizontalDirectionalBlock implements IDreadBlock, IDragonProof {
   public static final BooleanProperty PLAYER_PLACED = BooleanProperty.m_61465_("player_placed");

   public BlockDreadStoneFace() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60918_(SoundType.f_56742_).m_60913_(-1.0F, 10000.0F));
      this.m_49959_((BlockState)((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(f_54117_, Direction.NORTH)).m_61124_(PLAYER_PLACED, Boolean.FALSE));
   }

   public float m_5880_(BlockState state, @NotNull Player player, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      if ((Boolean)state.m_61143_(PLAYER_PLACED)) {
         float f = 8.0F;
         return player.getDigSpeed(state, pos) / f / 30.0F;
      } else {
         return super.m_5880_(state, player, worldIn, pos);
      }
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)((BlockState)this.m_49966_().m_61124_(f_54117_, context.m_8125_().m_122424_())).m_61124_(PLAYER_PLACED, true);
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{f_54117_});
      builder.m_61104_(new Property[]{PLAYER_PLACED});
   }
}
