package com.github.alexthe666.iceandfire.world.gen.processor;

import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.NotNull;

public class GraveyardProcessor extends StructureProcessor {
   private final float integrity = 1.0F;
   public static final GraveyardProcessor INSTANCE = new GraveyardProcessor();
   public static final Codec<GraveyardProcessor> CODEC = Codec.unit(() -> {
      return INSTANCE;
   });

   public static BlockState getRandomCobblestone(@Nullable BlockState prev, RandomSource random) {
      float rand = random.m_188501_();
      if ((double)rand < 0.5D) {
         return Blocks.f_50652_.m_49966_();
      } else {
         return (double)rand < 0.9D ? Blocks.f_50079_.m_49966_() : Blocks.f_50227_.m_49966_();
      }
   }

   public static BlockState getRandomCrackedBlock(@Nullable BlockState prev, RandomSource random) {
      float rand = random.m_188501_();
      if ((double)rand < 0.5D) {
         return Blocks.f_50222_.m_49966_();
      } else {
         return (double)rand < 0.9D ? Blocks.f_50224_.m_49966_() : Blocks.f_50223_.m_49966_();
      }
   }

   public StructureBlockInfo process(@NotNull LevelReader worldReader, @NotNull BlockPos pos, @NotNull BlockPos pos2, @NotNull StructureBlockInfo infoIn1, StructureBlockInfo infoIn2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      RandomSource random = settings.m_230326_(infoIn2.f_74675_());
      BlockState state;
      if (infoIn2.f_74676_().m_60734_() == Blocks.f_50222_) {
         state = getRandomCrackedBlock((BlockState)null, random);
         return new StructureBlockInfo(infoIn2.f_74675_(), state, (CompoundTag)null);
      } else if (infoIn2.f_74676_().m_60734_() == Blocks.f_50652_) {
         state = getRandomCobblestone((BlockState)null, random);
         return new StructureBlockInfo(infoIn2.f_74675_(), state, (CompoundTag)null);
      } else {
         return infoIn2;
      }
   }

   @NotNull
   protected StructureProcessorType m_6953_() {
      return (StructureProcessorType)IafProcessors.GRAVEYARDPROCESSOR.get();
   }
}
