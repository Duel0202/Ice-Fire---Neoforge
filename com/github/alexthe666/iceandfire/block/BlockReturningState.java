package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class BlockReturningState extends Block {
   public static final BooleanProperty REVERTS = BooleanProperty.m_61465_("revert");
   private final BlockState returnState;

   public static BlockReturningState builder(float hardness, float resistance, SoundType sound, boolean slippery, MapColor color, NoteBlockInstrument instrument, PushReaction reaction, boolean ignited, BlockState returnToState) {
      Properties props = Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance).m_60911_(0.98F).m_60977_();
      if (instrument != null) {
         props.m_280658_(instrument);
      }

      if (reaction != null) {
         props.m_278166_(reaction);
      }

      if (ignited) {
         props.m_278183_();
      }

      return new BlockReturningState(props, returnToState);
   }

   public static BlockReturningState builder(float hardness, float resistance, SoundType sound, MapColor color, NoteBlockInstrument instrument, PushReaction reaction, boolean ignited, BlockState returnToState) {
      Properties props = Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance).m_60977_();
      if (instrument != null) {
         props.m_280658_(instrument);
      }

      if (reaction != null) {
         props.m_278166_(reaction);
      }

      if (ignited) {
         props.m_278183_();
      }

      return new BlockReturningState(props, returnToState);
   }

   public BlockReturningState(Properties props, BlockState returnToState) {
      super(props);
      this.returnState = returnToState;
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(REVERTS, Boolean.FALSE));
   }

   public void m_213897_(@NotNull BlockState state, ServerLevel worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
      if (!worldIn.f_46443_) {
         if (!worldIn.isAreaLoaded(pos, 3)) {
            return;
         }

         if ((Boolean)state.m_61143_(REVERTS) && rand.m_188503_(3) == 0) {
            worldIn.m_46597_(pos, this.returnState);
         }
      }

   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{REVERTS});
   }
}
