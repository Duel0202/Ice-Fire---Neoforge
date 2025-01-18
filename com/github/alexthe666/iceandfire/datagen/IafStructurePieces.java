package com.github.alexthe666.iceandfire.datagen;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

public class IafStructurePieces {
   public static final ResourceKey<StructureTemplatePool> GRAVEYARD_START = createKey("graveyard/start_pool");
   public static final ResourceKey<StructureTemplatePool> MAUSOLEUM_START = createKey("mausoleum/start_pool");
   public static final ResourceKey<StructureTemplatePool> GORGON_TEMPLE_START = createKey("gorgon_temple/start_pool");

   private static ResourceKey<StructureTemplatePool> createKey(String name) {
      return ResourceKey.m_135785_(Registries.f_256948_, new ResourceLocation("iceandfire", name));
   }

   public static void registerGraveyard(BootstapContext<StructureTemplatePool> pContext) {
      HolderGetter<StructureProcessorList> processorListHolderGetter = pContext.m_255420_(Registries.f_257011_);
      Holder<StructureProcessorList> graveyardProcessor = processorListHolderGetter.m_255043_(IafProcessorLists.GRAVEYARD_PROCESSORS);
      HolderGetter<StructureTemplatePool> templatePoolHolderGetter = pContext.m_255420_(Registries.f_256948_);
      Holder<StructureTemplatePool> fallback = templatePoolHolderGetter.m_255043_(Pools.f_127186_);
      pContext.m_255272_(GRAVEYARD_START, new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.m_210531_("iceandfire:graveyard/graveyard_top", graveyardProcessor), 1)), Projection.RIGID));
      pContext.m_255272_(createKey("graveyard/bottom_pool"), new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.m_210531_("iceandfire:graveyard/graveyard_bottom", graveyardProcessor), 1)), Projection.RIGID));
   }

   public static void registerMausoleum(BootstapContext<StructureTemplatePool> pContext) {
      HolderGetter<StructureProcessorList> processorListHolderGetter = pContext.m_255420_(Registries.f_257011_);
      Holder<StructureProcessorList> graveyardProcessor = processorListHolderGetter.m_255043_(IafProcessorLists.MAUSOLEUM_PROCESSORS);
      HolderGetter<StructureTemplatePool> templatePoolHolderGetter = pContext.m_255420_(Registries.f_256948_);
      Holder<StructureTemplatePool> fallback = templatePoolHolderGetter.m_255043_(Pools.f_127186_);
      pContext.m_255272_(MAUSOLEUM_START, new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.m_210531_("iceandfire:mausoleum/building", graveyardProcessor), 1)), Projection.RIGID));
   }

   public static void registerGorgonTemple(BootstapContext<StructureTemplatePool> pContext) {
      HolderGetter<StructureProcessorList> processorListHolderGetter = pContext.m_255420_(Registries.f_257011_);
      Holder<StructureProcessorList> graveyardProcessor = processorListHolderGetter.m_255043_(IafProcessorLists.GORGON_TEMPLE_PROCESSORS);
      HolderGetter<StructureTemplatePool> templatePoolHolderGetter = pContext.m_255420_(Registries.f_256948_);
      Holder<StructureTemplatePool> fallback = templatePoolHolderGetter.m_255043_(Pools.f_127186_);
      pContext.m_255272_(GORGON_TEMPLE_START, new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.m_210531_("iceandfire:gorgon_temple/building", graveyardProcessor), 1)), Projection.RIGID));
      pContext.m_255272_(createKey("gorgon_temple/bottom_pool"), new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.m_210531_("iceandfire:gorgon_temple/basement", graveyardProcessor), 1)), Projection.RIGID));
      pContext.m_255272_(createKey("gorgon_temple/gorgon_pool"), new StructureTemplatePool(fallback, ImmutableList.of(Pair.of(StructurePoolElement.m_210531_("iceandfire:gorgon_temple/gorgon", graveyardProcessor), 1)), Projection.RIGID));
   }

   public static void bootstrap(BootstapContext<StructureTemplatePool> pContext) {
      registerGraveyard(pContext);
      registerMausoleum(pContext);
      registerGorgonTemple(pContext);
   }
}
