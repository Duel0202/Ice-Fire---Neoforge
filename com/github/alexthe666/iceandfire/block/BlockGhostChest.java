package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityGhostChest;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

public class BlockGhostChest extends ChestBlock {
   public BlockGhostChest() {
      Properties var10001 = Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_278183_().m_60978_(2.5F).m_60918_(SoundType.f_56736_);
      RegistryObject var10002 = IafTileEntityRegistry.GHOST_CHEST;
      Objects.requireNonNull(var10002);
      super(var10001, var10002::get);
   }

   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityGhostChest(pos, state);
   }

   @NotNull
   protected Stat<ResourceLocation> m_7699_() {
      return Stats.f_12988_.m_12902_(Stats.f_12962_);
   }

   public boolean m_7899_(@NotNull BlockState state) {
      return true;
   }

   public int m_6378_(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
      return Mth.m_14045_(ChestBlockEntity.m_59086_(blockAccess, pos), 0, 15);
   }

   public int m_6376_(@NotNull BlockState blockState, @NotNull BlockGetter blockAccess, @NotNull BlockPos pos, @NotNull Direction side) {
      return side == Direction.UP ? blockState.m_60746_(blockAccess, pos, side) : 0;
   }
}
