package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockBurntTorchWall extends WallTorchBlock implements IDreadBlock {
   public BlockBurntTorchWall() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283825_).m_278183_().m_60953_((state) -> {
         return 0;
      }).m_60918_(SoundType.f_56736_).m_60955_().m_60988_().lootFrom(IafBlockRegistry.BURNT_TORCH).m_60910_(), DustParticleOptions.f_123656_);
   }

   public void m_214162_(@NotNull BlockState stateIn, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
   }
}
