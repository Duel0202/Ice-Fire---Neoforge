package com.github.alexthe666.iceandfire.world.gen.processor;

import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;

public class GorgonTempleProcessor extends StructureProcessor {
   public static final GorgonTempleProcessor INSTANCE = new GorgonTempleProcessor();
   public static final Codec<GorgonTempleProcessor> CODEC = Codec.unit(() -> {
      return INSTANCE;
   });

   public StructureBlockInfo process(@NotNull LevelReader worldReader, @NotNull BlockPos pos, @NotNull BlockPos pos2, @NotNull StructureBlockInfo infoIn1, StructureBlockInfo infoIn2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      if (infoIn2.f_74676_().m_60734_() instanceof SimpleWaterloggedBlock && worldReader.m_6425_(infoIn2.f_74675_()).m_205070_(FluidTags.f_13131_)) {
         ChunkPos currentChunk = new ChunkPos(infoIn2.f_74675_());
         worldReader.m_6325_(currentChunk.f_45578_, currentChunk.f_45579_).m_6978_(infoIn2.f_74675_(), Blocks.f_50016_.m_49966_(), false);
      }

      return infoIn2;
   }

   @NotNull
   protected StructureProcessorType m_6953_() {
      return (StructureProcessorType)IafProcessors.GORGONTEMPLEPROCESSOR.get();
   }
}
