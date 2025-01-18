package com.github.alexthe666.iceandfire.block;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class BlockFallingGeneric extends FallingBlock {
   public Item itemBlock;

   public static BlockFallingGeneric builder(float hardness, float resistance, SoundType sound, MapColor color, NoteBlockInstrument instrument) {
      Properties props = Properties.m_284310_().m_284180_(color).m_280658_(instrument).m_60918_(sound).m_60913_(hardness, resistance);
      return new BlockFallingGeneric(props);
   }

   public BlockFallingGeneric(Properties props) {
      super(props);
   }

   public int getDustColor(BlockState blkst) {
      return -8356741;
   }
}
