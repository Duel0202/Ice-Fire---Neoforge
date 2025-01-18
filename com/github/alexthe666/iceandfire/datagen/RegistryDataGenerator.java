package com.github.alexthe666.iceandfire.datagen;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforge.registries.ForgeRegistries.Keys;

public class RegistryDataGenerator extends DatapackBuiltinEntriesProvider {
   public static final RegistrySetBuilder BUILDER;

   public RegistryDataGenerator(PackOutput output, CompletableFuture<Provider> provider) {
      super(output, provider, BUILDER, Set.of("minecraft", "iceandfire"));
   }

   static {
      BUILDER = (new RegistrySetBuilder()).m_254916_(Registries.f_256911_, IafConfiguredFeatures::bootstrap).m_254916_(Registries.f_256988_, IafPlacedFeatures::bootstrap).m_254916_(Registries.f_256944_, IafStructures::bootstrap).m_254916_(Registries.f_256998_, IafStructureSets::bootstrap).m_254916_(Registries.f_257011_, IafProcessorLists::bootstrap).m_254916_(Registries.f_256948_, IafStructurePieces::bootstrap).m_254916_(Keys.BIOME_MODIFIERS, IafBiomeModifierSerializers::bootstrap);
   }
}
