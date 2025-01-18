package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockDreadTorchWall extends WallTorchBlock implements IDreadBlock {
   public BlockDreadTorchWall() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_278183_().m_60953_((state) -> {
         return 5;
      }).m_60918_(SoundType.f_56742_).m_60955_().m_60988_().m_60910_().m_60916_((Block)IafBlockRegistry.DREAD_TORCH.get()), DustParticleOptions.f_123656_);
   }

   public void m_214162_(BlockState stateIn, @NotNull Level worldIn, BlockPos pos, @NotNull RandomSource rand) {
      Direction direction = (Direction)stateIn.m_61143_(f_58119_);
      double d0 = (double)pos.m_123341_() + 0.5D;
      double d1 = (double)pos.m_123342_() + 0.7D;
      double d2 = (double)pos.m_123343_() + 0.5D;
      double d3 = 0.22D;
      double d4 = 0.27D;
      Direction direction1 = direction.m_122424_();
      IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, d0 + 0.27D * (double)direction1.m_122429_(), d1 + 0.22D, d2 + 0.27D * (double)direction1.m_122431_(), 0.0D, 0.0D, 0.0D);
   }
}
