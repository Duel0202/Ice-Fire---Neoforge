package com.github.alexthe666.iceandfire.world.structure;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.datagen.IafBiomeTagGenerator;
import com.github.alexthe666.iceandfire.datagen.IafStructurePieces;
import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class GraveyardStructure extends IafStructure {
   public static final Codec<GraveyardStructure> CODEC = RecordCodecBuilder.mapCodec((instance) -> {
      return instance.group(m_226567_(instance), StructureTemplatePool.f_210555_.fieldOf("start_pool").forGetter((structure) -> {
         return structure.startPool;
      }), ResourceLocation.f_135803_.optionalFieldOf("start_jigsaw_name").forGetter((structure) -> {
         return structure.startJigsawName;
      }), Codec.intRange(0, 30).fieldOf("size").forGetter((structure) -> {
         return structure.size;
      }), HeightProvider.f_161970_.fieldOf("start_height").forGetter((structure) -> {
         return structure.startHeight;
      }), Types.f_64274_.optionalFieldOf("project_start_to_heightmap").forGetter((structure) -> {
         return structure.projectStartToHeightmap;
      }), Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter((structure) -> {
         return structure.maxDistanceFromCenter;
      })).apply(instance, GraveyardStructure::new);
   }).codec();

   public GraveyardStructure(StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Types> projectStartToHeightmap, int maxDistanceFromCenter) {
      super(config, startPool, startJigsawName, size, startHeight, projectStartToHeightmap, maxDistanceFromCenter);
   }

   protected Optional<GenerationStub> m_214086_(GenerationContext pContext) {
      if (!IafConfig.generateGraveyards) {
         return Optional.empty();
      } else {
         BlockPos blockpos = pContext.f_226628_().m_151394_(1);
         if (!this.isBiomeValid(pContext, BiomeConfig.graveyardBiomes, blockpos)) {
            return Optional.empty();
         } else {
            Optional<GenerationStub> structurePiecesGenerator = JigsawPlacement.m_227238_(pContext, this.startPool, this.startJigsawName, this.size, blockpos, false, this.projectStartToHeightmap, this.maxDistanceFromCenter);
            return structurePiecesGenerator;
         }
      }
   }

   public StructureType<?> m_213658_() {
      return (StructureType)IafStructureTypes.GRAVEYARD.get();
   }

   public static GraveyardStructure buildStructureConfig(BootstapContext<Structure> context) {
      HolderGetter<StructureTemplatePool> templatePoolHolderGetter = context.m_255420_(Registries.f_256948_);
      Holder<StructureTemplatePool> graveyardHolder = templatePoolHolderGetter.m_255043_(IafStructurePieces.GRAVEYARD_START);
      return new GraveyardStructure(new StructureSettings(context.m_255420_(Registries.f_256952_).m_254956_(IafBiomeTagGenerator.HAS_GRAVEYARD), new HashMap(), Decoration.SURFACE_STRUCTURES, TerrainAdjustment.BEARD_THIN), graveyardHolder, Optional.empty(), 1, ConstantHeight.f_161945_, Optional.of(Types.WORLD_SURFACE_WG), 16);
   }
}
