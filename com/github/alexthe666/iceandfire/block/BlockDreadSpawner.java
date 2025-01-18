package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDreadSpawner;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockDreadSpawner extends SpawnerBlock implements IDreadBlock {
   public BlockDreadSpawner() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60913_(10.0F, 10000.0F).m_60918_(SoundType.f_56743_).m_60955_().m_60988_());
   }

   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityDreadSpawner(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_154683_, @NotNull BlockState p_154684_, @NotNull BlockEntityType<T> p_154685_) {
      return m_152132_(p_154685_, (BlockEntityType)IafTileEntityRegistry.DREAD_SPAWNER.get(), p_154683_.f_46443_ ? TileEntityDreadSpawner::clientTick : TileEntityDreadSpawner::serverTick);
   }
}
