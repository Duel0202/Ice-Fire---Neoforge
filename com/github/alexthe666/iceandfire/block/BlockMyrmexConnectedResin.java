package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockMyrmexConnectedResin extends HalfTransparentBlock {
   public static final BooleanProperty UP = BooleanProperty.m_61465_("up");
   public static final BooleanProperty DOWN = BooleanProperty.m_61465_("down");
   public static final BooleanProperty NORTH = BooleanProperty.m_61465_("north");
   public static final BooleanProperty EAST = BooleanProperty.m_61465_("east");
   public static final BooleanProperty SOUTH = BooleanProperty.m_61465_("south");
   public static final BooleanProperty WEST = BooleanProperty.m_61465_("west");

   public BlockMyrmexConnectedResin(boolean jungle, boolean glass) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60978_(glass ? 1.5F : 3.5F).m_60955_().m_60988_().m_60918_(glass ? SoundType.f_56744_ : SoundType.f_56742_));
      this.m_49959_((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(UP, Boolean.FALSE)).m_61124_(DOWN, Boolean.FALSE)).m_61124_(NORTH, Boolean.FALSE)).m_61124_(EAST, Boolean.FALSE)).m_61124_(SOUTH, Boolean.FALSE)).m_61124_(WEST, Boolean.FALSE));
   }

   static String name(boolean glass, boolean jungle) {
      String biome = jungle ? "jungle" : "desert";
      String type = glass ? "glass" : "block";
      return "myrmex_%s_resin_%s".formatted(new Object[]{biome, type});
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      BlockGetter iblockreader = context.m_43725_();
      BlockPos blockpos = context.m_8083_();
      FluidState ifluidstate = context.m_43725_().m_6425_(context.m_8083_());
      BlockPos blockpos1 = blockpos.m_122012_();
      BlockPos blockpos2 = blockpos.m_122029_();
      BlockPos blockpos3 = blockpos.m_122019_();
      BlockPos blockpos4 = blockpos.m_122024_();
      BlockPos blockpos5 = blockpos.m_7494_();
      BlockPos blockpos6 = blockpos.m_7495_();
      BlockState blockstate = iblockreader.m_8055_(blockpos1);
      BlockState blockstate1 = iblockreader.m_8055_(blockpos2);
      BlockState blockstate2 = iblockreader.m_8055_(blockpos3);
      BlockState blockstate3 = iblockreader.m_8055_(blockpos4);
      BlockState blockstate4 = iblockreader.m_8055_(blockpos5);
      BlockState blockstate5 = iblockreader.m_8055_(blockpos6);
      return (BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)super.m_5573_(context).m_61124_(NORTH, this.canFenceConnectTo(blockstate, false, Direction.SOUTH))).m_61124_(EAST, this.canFenceConnectTo(blockstate1, false, Direction.WEST))).m_61124_(SOUTH, this.canFenceConnectTo(blockstate2, false, Direction.NORTH))).m_61124_(WEST, this.canFenceConnectTo(blockstate3, false, Direction.EAST))).m_61124_(UP, this.canFenceConnectTo(blockstate4, false, Direction.UP))).m_61124_(DOWN, this.canFenceConnectTo(blockstate5, false, Direction.DOWN));
   }

   @NotNull
   public BlockState m_7417_(@NotNull BlockState stateIn, Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor worldIn, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
      BooleanProperty connect = null;
      switch(facing) {
      case NORTH:
         connect = NORTH;
         break;
      case SOUTH:
         connect = SOUTH;
         break;
      case EAST:
         connect = EAST;
         break;
      case WEST:
         connect = WEST;
         break;
      case DOWN:
         connect = DOWN;
         break;
      default:
         connect = UP;
      }

      return (BlockState)stateIn.m_61124_(connect, this.canFenceConnectTo(facingState, false, facing.m_122424_()));
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{NORTH, EAST, WEST, SOUTH, DOWN, UP});
   }

   public boolean canFenceConnectTo(BlockState state, boolean isSideSolid, Direction direction) {
      return state.m_60734_() == this;
   }

   public boolean isOpaqueCube(BlockState state) {
      return false;
   }
}
