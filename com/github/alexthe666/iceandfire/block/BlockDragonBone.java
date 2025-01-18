package com.github.alexthe666.iceandfire.block;

import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class BlockDragonBone extends RotatedPillarBlock implements IDragonProof {
   public BlockDragonBone() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60918_(SoundType.f_56736_).m_60913_(30.0F, 500.0F).m_60999_());
   }

   @NotNull
   public PushReaction getPistonPushReaction(@NotNull BlockState state) {
      return PushReaction.BLOCK;
   }
}
