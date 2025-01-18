package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.EntityDreadMob;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class BlockGeneric extends Block {
   public static BlockGeneric builder(float hardness, float resistance, SoundType sound, MapColor color, NoteBlockInstrument instrument, PushReaction reaction, boolean ignited) {
      Properties props = Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance).m_60999_();
      if (instrument != null) {
         props.m_280658_(instrument);
      }

      if (reaction != null) {
         props.m_278166_(reaction);
      }

      if (ignited) {
         props.m_278183_();
      }

      return new BlockGeneric(props);
   }

   public static BlockGeneric builder(float hardness, float resistance, SoundType sound, boolean slippery, MapColor color, NoteBlockInstrument instrument, PushReaction reaction, boolean ignited) {
      Properties props = Properties.m_284310_().m_284180_(color).m_60918_(sound).m_60913_(hardness, resistance).m_60911_(0.98F);
      if (instrument != null) {
         props.m_280658_(instrument);
      }

      if (reaction != null) {
         props.m_278166_(reaction);
      }

      if (ignited) {
         props.m_278183_();
      }

      return new BlockGeneric(props);
   }

   public BlockGeneric(Properties props) {
      super(props);
   }

   public boolean isOpaqueCube(BlockState state) {
      return this != IafBlockRegistry.DRAGON_ICE.get();
   }

   public boolean isFullCube(BlockState state) {
      return this != IafBlockRegistry.DRAGON_ICE.get();
   }

   /** @deprecated */
   @Deprecated
   public boolean canEntitySpawn(BlockState state, Entity entityIn) {
      return entityIn instanceof EntityDreadMob || !DragonUtils.isDreadBlock(state);
   }
}
