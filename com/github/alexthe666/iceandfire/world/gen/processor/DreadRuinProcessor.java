package com.github.alexthe666.iceandfire.world.gen.processor;

import com.github.alexthe666.iceandfire.block.BlockDreadBase;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.mojang.serialization.Codec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class DreadRuinProcessor extends StructureProcessor {
   public static final DreadRuinProcessor INSTANCE = new DreadRuinProcessor();
   public static final Codec<DreadRuinProcessor> CODEC = Codec.unit(() -> {
      return INSTANCE;
   });

   public static BlockState getRandomCrackedBlock(@Nullable BlockState prev, RandomSource random) {
      float rand = random.m_188501_();
      if ((double)rand < 0.5D) {
         return ((BlockDreadBase)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_49966_();
      } else {
         return (double)rand < 0.9D ? ((BlockDreadBase)IafBlockRegistry.DREAD_STONE_BRICKS_CRACKED.get()).m_49966_() : ((BlockDreadBase)IafBlockRegistry.DREAD_STONE_BRICKS_MOSSY.get()).m_49966_();
      }
   }

   public StructureBlockInfo process(@NotNull LevelReader worldReader, @NotNull BlockPos pos, @NotNull BlockPos pos2, @NotNull StructureBlockInfo infoIn1, StructureBlockInfo infoIn2, StructurePlaceSettings settings, @Nullable StructureTemplate template) {
      RandomSource random = settings.m_230326_(infoIn2.f_74675_());
      if (infoIn2.f_74676_().m_60734_() == IafBlockRegistry.DREAD_STONE_BRICKS.get()) {
         BlockState state = getRandomCrackedBlock((BlockState)null, random);
         return new StructureBlockInfo(infoIn2.f_74675_(), state, (CompoundTag)null);
      } else if (infoIn2.f_74676_().m_60734_() == IafBlockRegistry.DREAD_SPAWNER.get()) {
         CompoundTag tag = new CompoundTag();
         CompoundTag spawnData = new CompoundTag();
         ResourceLocation spawnerMobId = ForgeRegistries.ENTITY_TYPES.getKey(this.getRandomMobForMobSpawner(random));
         if (spawnerMobId != null) {
            CompoundTag entity = new CompoundTag();
            entity.m_128359_("id", spawnerMobId.toString());
            spawnData.m_128365_("entity", entity);
            tag.m_128473_("SpawnPotentials");
            tag.m_128365_("SpawnData", spawnData.m_6426_());
         }

         StructureBlockInfo newInfo = new StructureBlockInfo(infoIn2.f_74675_(), ((Block)IafBlockRegistry.DREAD_SPAWNER.get()).m_49966_(), tag);
         return newInfo;
      } else {
         return infoIn2;
      }
   }

   @NotNull
   protected StructureProcessorType m_6953_() {
      return (StructureProcessorType)IafProcessors.DREADRUINPROCESSOR.get();
   }

   private EntityType getRandomMobForMobSpawner(RandomSource random) {
      float rand = random.m_188501_();
      if ((double)rand < 0.3D) {
         return (EntityType)IafEntityRegistry.DREAD_THRALL.get();
      } else if ((double)rand < 0.5D) {
         return (EntityType)IafEntityRegistry.DREAD_GHOUL.get();
      } else if ((double)rand < 0.7D) {
         return (EntityType)IafEntityRegistry.DREAD_BEAST.get();
      } else {
         return (double)rand < 0.85D ? (EntityType)IafEntityRegistry.DREAD_SCUTTLER.get() : (EntityType)IafEntityRegistry.DREAD_KNIGHT.get();
      }
   }
}
