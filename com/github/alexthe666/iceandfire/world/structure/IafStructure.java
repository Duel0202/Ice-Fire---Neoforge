package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.citadel.config.biome.SpawnBiomeData;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.apache.commons.lang3.tuple.Pair;

public class IafStructure extends Structure {
   protected final Holder<StructureTemplatePool> startPool;
   protected final Optional<ResourceLocation> startJigsawName;
   protected final int size;
   protected final HeightProvider startHeight;
   protected final Optional<Types> projectStartToHeightmap;
   protected final int maxDistanceFromCenter;

   public IafStructure(StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Types> projectStartToHeightmap, int maxDistanceFromCenter) {
      super(config);
      this.startPool = startPool;
      this.startJigsawName = startJigsawName;
      this.size = size;
      this.startHeight = startHeight;
      this.projectStartToHeightmap = projectStartToHeightmap;
      this.maxDistanceFromCenter = maxDistanceFromCenter;
   }

   protected boolean isBiomeValid(GenerationContext pContext, Pair<String, SpawnBiomeData> validBiomes, BlockPos blockPos) {
      boolean validBiome = false;
      Set<Holder<Biome>> biomes = pContext.f_226622_().m_62218_().m_183399_(blockPos.m_123341_(), blockPos.m_123342_(), blockPos.m_123343_(), this.maxDistanceFromCenter, pContext.f_226624_().m_224579_());
      Iterator var6 = biomes.iterator();

      while(var6.hasNext()) {
         Holder<Biome> biome = (Holder)var6.next();
         if (BiomeConfig.test(validBiomes, biome)) {
            validBiome = true;
            break;
         }
      }

      return validBiome;
   }

   protected Optional<GenerationStub> m_214086_(GenerationContext pContext) {
      return Optional.empty();
   }

   public StructureType<?> m_213658_() {
      return null;
   }
}
