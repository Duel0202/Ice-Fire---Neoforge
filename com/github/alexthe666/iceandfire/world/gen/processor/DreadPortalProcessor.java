package com.github.alexthe666.iceandfire.world.gen.processor;

import com.github.alexthe666.iceandfire.block.BlockDreadBase;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;

public class DreadPortalProcessor extends StructureProcessor {
   private final float integrity = 1.0F;

   public DreadPortalProcessor(BlockPos position, StructurePlaceSettings settings, Biome biome) {
   }

   public static BlockState getRandomCrackedBlock(@Nullable BlockState prev, RandomSource random) {
      float rand = random.m_188501_();
      if ((double)rand < 0.3D) {
         return ((BlockDreadBase)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_49966_();
      } else {
         return (double)rand < 0.6D ? ((BlockDreadBase)IafBlockRegistry.DREAD_STONE_BRICKS_CRACKED.get()).m_49966_() : ((BlockDreadBase)IafBlockRegistry.DREAD_STONE_BRICKS_MOSSY.get()).m_49966_();
      }
   }

   @Nullable
   public StructureBlockInfo process(@NotNull LevelReader world, @NotNull BlockPos pos, @NotNull BlockPos p_230386_3_, @NotNull StructureBlockInfo blockInfoIn, @NotNull StructureBlockInfo p_230386_5_, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      RandomSource random = settings.m_230326_(pos);
      if (random.m_188501_() <= 1.0F) {
         if (blockInfoIn.f_74676_().m_60734_() == Blocks.f_50090_) {
            return new StructureBlockInfo(pos, ((Block)IafBlockRegistry.DREAD_PORTAL.get()).m_49966_(), (CompoundTag)null);
         } else if (blockInfoIn.f_74676_().m_60734_() == IafBlockRegistry.DREAD_STONE_BRICKS.get()) {
            BlockState state = getRandomCrackedBlock((BlockState)null, random);
            return new StructureBlockInfo(pos, state, (CompoundTag)null);
         } else {
            return blockInfoIn;
         }
      } else {
         return blockInfoIn;
      }
   }

   @NotNull
   protected StructureProcessorType m_6953_() {
      return StructureProcessorType.f_74457_;
   }
}
