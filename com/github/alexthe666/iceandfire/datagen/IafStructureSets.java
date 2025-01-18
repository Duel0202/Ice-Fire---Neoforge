package com.github.alexthe666.iceandfire.datagen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;

public class IafStructureSets {
   public static final ResourceKey<StructureSet> GRAVEYARD = registerKey("graveyard");
   public static final ResourceKey<StructureSet> MAUSOLEUM = registerKey("mausoleum");
   public static final ResourceKey<StructureSet> GORGON_TEMPLE = registerKey("gorgon_temple");

   private static ResourceKey<StructureSet> registerKey(String name) {
      return ResourceKey.m_135785_(Registries.f_256998_, new ResourceLocation("iceandfire", name));
   }

   public static void bootstrap(BootstapContext<StructureSet> context) {
      HolderGetter<Structure> structures = context.m_255420_(Registries.f_256944_);
      context.m_255272_(GRAVEYARD, new StructureSet(structures.m_255043_(IafStructures.GRAVEYARD), new RandomSpreadStructurePlacement(28, 8, RandomSpreadType.LINEAR, 44712661)));
      context.m_255272_(MAUSOLEUM, new StructureSet(structures.m_255043_(IafStructures.MAUSOLEUM), new RandomSpreadStructurePlacement(32, 12, RandomSpreadType.LINEAR, 14200531)));
      context.m_255272_(GORGON_TEMPLE, new StructureSet(structures.m_255043_(IafStructures.GORGON_TEMPLE), new RandomSpreadStructurePlacement(32, 12, RandomSpreadType.LINEAR, 76489509)));
   }
}
