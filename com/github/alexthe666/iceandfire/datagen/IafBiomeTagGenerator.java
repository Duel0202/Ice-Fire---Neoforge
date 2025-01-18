package com.github.alexthe666.iceandfire.datagen;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforge.common.data.ExistingFileHelper;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

public class IafBiomeTagGenerator extends BiomeTagsProvider {
   public static final TagKey<Biome> HAS_GORGON_TEMPLE;
   public static final TagKey<Biome> HAS_MAUSOLEUM;
   public static final TagKey<Biome> HAS_GRAVEYARD;

   public IafBiomeTagGenerator(PackOutput pOutput, CompletableFuture<Provider> pProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(pOutput, pProvider, "iceandfire", existingFileHelper);
   }

   protected void m_6577_(Provider pProvider) {
      this.m_206424_(HAS_GRAVEYARD).m_206428_(BiomeTags.f_215817_);
      this.m_206424_(HAS_MAUSOLEUM).m_206428_(BiomeTags.f_215817_);
      this.m_206424_(HAS_GORGON_TEMPLE).m_206428_(BiomeTags.f_215817_);
   }

   public String m_6055_() {
      return "Ice and Fire Biome Tags";
   }

   static {
      HAS_GORGON_TEMPLE = TagKey.m_203882_(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation("iceandfire", "has_structure/gorgon_temple"));
      HAS_MAUSOLEUM = TagKey.m_203882_(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation("iceandfire", "has_structure/mausoleum"));
      HAS_GRAVEYARD = TagKey.m_203882_(ForgeRegistries.BIOMES.getRegistryKey(), new ResourceLocation("iceandfire", "has_structure/graveyard"));
   }
}
