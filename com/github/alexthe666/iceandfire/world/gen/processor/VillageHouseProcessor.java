package com.github.alexthe666.iceandfire.world.gen.processor;

import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;

public class VillageHouseProcessor extends StructureProcessor {
   public static final ResourceLocation LOOT = new ResourceLocation("iceandfire", "chest/village_scribe");
   public static final VillageHouseProcessor INSTANCE = new VillageHouseProcessor();
   public static final Codec<VillageHouseProcessor> CODEC = Codec.unit(() -> {
      return INSTANCE;
   });

   public StructureBlockInfo process(@NotNull LevelReader worldReader, @NotNull BlockPos pos, @NotNull BlockPos pos2, @NotNull StructureBlockInfo infoIn1, StructureBlockInfo infoIn2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      RandomSource random = settings.m_230326_(infoIn2.f_74675_());
      if (infoIn2.f_74676_().m_60734_() == Blocks.f_50087_) {
         CompoundTag tag = new CompoundTag();
         tag.m_128359_("LootTable", LOOT.toString());
         tag.m_128356_("LootTableSeed", random.m_188505_());
         return new StructureBlockInfo(infoIn2.f_74675_(), infoIn2.f_74676_(), tag);
      } else {
         return infoIn2;
      }
   }

   @NotNull
   protected StructureProcessorType m_6953_() {
      return (StructureProcessorType)IafProcessors.VILLAGEHOUSEPROCESSOR.get();
   }
}
