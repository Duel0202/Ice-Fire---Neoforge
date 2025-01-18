package com.github.alexthe666.iceandfire.config;

import com.github.alexthe666.citadel.config.biome.SpawnBiomeConfig;
import com.github.alexthe666.citadel.config.biome.SpawnBiomeData;
import com.github.alexthe666.iceandfire.IceAndFire;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.apache.commons.lang3.tuple.Pair;

public class BiomeConfig {
   public static Pair<String, SpawnBiomeData> oreGenBiomes;
   public static Pair<String, SpawnBiomeData> sapphireBiomes;
   public static Pair<String, SpawnBiomeData> fireLilyBiomes;
   public static Pair<String, SpawnBiomeData> frostLilyBiomes;
   public static Pair<String, SpawnBiomeData> lightningLilyBiomes;
   public static Pair<String, SpawnBiomeData> fireDragonBiomes;
   public static Pair<String, SpawnBiomeData> fireDragonCaveBiomes;
   public static Pair<String, SpawnBiomeData> iceDragonBiomes;
   public static Pair<String, SpawnBiomeData> iceDragonCaveBiomes;
   public static Pair<String, SpawnBiomeData> lightningDragonBiomes;
   public static Pair<String, SpawnBiomeData> lightningDragonCaveBiomes;
   public static Pair<String, SpawnBiomeData> cyclopsCaveBiomes;
   public static Pair<String, SpawnBiomeData> hippogryphBiomes;
   public static Pair<String, SpawnBiomeData> pixieBiomes;
   public static Pair<String, SpawnBiomeData> hippocampusBiomes;
   public static Pair<String, SpawnBiomeData> seaSerpentBiomes;
   public static Pair<String, SpawnBiomeData> sirenBiomes;
   public static Pair<String, SpawnBiomeData> amphithereBiomes;
   public static Pair<String, SpawnBiomeData> desertMyrmexBiomes;
   public static Pair<String, SpawnBiomeData> jungleMyrmexBiomes;
   public static Pair<String, SpawnBiomeData> snowyTrollBiomes;
   public static Pair<String, SpawnBiomeData> forestTrollBiomes;
   public static Pair<String, SpawnBiomeData> mountainTrollBiomes;
   public static Pair<String, SpawnBiomeData> stymphalianBiomes;
   public static Pair<String, SpawnBiomeData> hydraBiomes;
   public static Pair<String, SpawnBiomeData> mausoleumBiomes;
   public static Pair<String, SpawnBiomeData> graveyardBiomes;
   public static Pair<String, SpawnBiomeData> gorgonTempleBiomes;
   public static Pair<String, SpawnBiomeData> cockatriceBiomes;
   public static Pair<String, SpawnBiomeData> deathwormBiomes;
   public static Pair<String, SpawnBiomeData> wanderingCyclopsBiomes;
   public static Pair<String, SpawnBiomeData> lightningDragonSkeletonBiomes;
   public static Pair<String, SpawnBiomeData> fireDragonSkeletonBiomes;
   public static Pair<String, SpawnBiomeData> iceDragonSkeletonBiomes;
   public static Pair<String, SpawnBiomeData> blackHippogryphBiomes;
   public static Pair<String, SpawnBiomeData> brownHippogryphBiomes;
   public static Pair<String, SpawnBiomeData> grayHippogryphBiomes;
   public static Pair<String, SpawnBiomeData> chestnutHippogryphBiomes;
   public static Pair<String, SpawnBiomeData> creamyHippogryphBiomes;
   public static Pair<String, SpawnBiomeData> darkBrownHippogryphBiomes;
   public static Pair<String, SpawnBiomeData> whiteHippogryphBiomes;
   private static boolean init;
   private static final Map<String, SpawnBiomeData> biomeConfigValues;

   public static void init() {
      try {
         Field[] var0 = BiomeConfig.class.getFields();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            Field f = var0[var2];
            Object obj = f.get((Object)null);
            if (obj instanceof Pair) {
               String id = (String)((Pair)obj).getLeft();
               SpawnBiomeData data = (SpawnBiomeData)((Pair)obj).getRight();
               biomeConfigValues.put(id, SpawnBiomeConfig.create(new ResourceLocation(id), data));
            }
         }
      } catch (Exception var7) {
         IceAndFire.LOGGER.warn("Encountered error building iceandfire biome config .json files");
         var7.printStackTrace();
      }

      init = true;
   }

   private static ResourceLocation getBiomeName(Holder<Biome> biome) {
      return (ResourceLocation)biome.m_203439_().map((resourceKey) -> {
         return resourceKey.m_135782_();
      }, (noKey) -> {
         return null;
      });
   }

   public static boolean test(Pair<String, SpawnBiomeData> entry, Holder<Biome> biome, ResourceLocation name) {
      if (!init) {
         init();
      }

      return ((SpawnBiomeData)biomeConfigValues.get(entry.getKey())).matches(biome, name);
   }

   public static boolean test(Pair<String, SpawnBiomeData> entry, Holder<Biome> biome) {
      return test(entry, biome, getBiomeName(biome));
   }

   public static boolean test(Pair<String, SpawnBiomeData> entry, Reference<Biome> biome) {
      return test(entry, biome, biome.m_205785_().m_135782_());
   }

   static {
      oreGenBiomes = Pair.of("iceandfire:ore_gen_biomes", DefaultBiomes.OVERWORLD);
      sapphireBiomes = Pair.of("iceandfire:sapphire_gen_biomes", DefaultBiomes.VERY_SNOWY);
      fireLilyBiomes = Pair.of("iceandfire:fire_lily_biomes", DefaultBiomes.VERY_HOT);
      frostLilyBiomes = Pair.of("iceandfire:frost_lily_biomes", DefaultBiomes.VERY_SNOWY);
      lightningLilyBiomes = Pair.of("iceandfire:lightning_lily_biomes", DefaultBiomes.SAVANNAS);
      fireDragonBiomes = Pair.of("iceandfire:fire_dragon_biomes", DefaultBiomes.FIREDRAGON_ROOST);
      fireDragonCaveBiomes = Pair.of("iceandfire:fire_dragon_cave_biomes", DefaultBiomes.FIREDRAGON_CAVE);
      iceDragonBiomes = Pair.of("iceandfire:ice_dragon_biomes", DefaultBiomes.ICEDRAGON_ROOST);
      iceDragonCaveBiomes = Pair.of("iceandfire:ice_dragon_cave_biomes", DefaultBiomes.ICEDRAGON_CAVE);
      lightningDragonBiomes = Pair.of("iceandfire:lightning_dragon_biomes", DefaultBiomes.LIGHTNING_ROOST);
      lightningDragonCaveBiomes = Pair.of("iceandfire:lightning_dragon_cave_biomes", DefaultBiomes.LIGHTNING_CAVE);
      cyclopsCaveBiomes = Pair.of("iceandfire:cyclops_cave_biomes", DefaultBiomes.BEACHES);
      hippogryphBiomes = Pair.of("iceandfire:hippogryph_biomes", DefaultBiomes.HILLS);
      pixieBiomes = Pair.of("iceandfire:pixie_village_biomes", DefaultBiomes.PIXIES);
      hippocampusBiomes = Pair.of("iceandfire:hippocampus_biomes", DefaultBiomes.OCEANS);
      seaSerpentBiomes = Pair.of("iceandfire:sea_serpent_biomes", DefaultBiomes.OCEANS);
      sirenBiomes = Pair.of("iceandfire:siren_biomes", DefaultBiomes.OCEANS);
      amphithereBiomes = Pair.of("iceandfire:amphithere_biomes", DefaultBiomes.JUNGLE);
      desertMyrmexBiomes = Pair.of("iceandfire:desert_myrmex_biomes", DefaultBiomes.DESERT);
      jungleMyrmexBiomes = Pair.of("iceandfire:jungle_myrmex_biomes", DefaultBiomes.JUNGLE);
      snowyTrollBiomes = Pair.of("iceandfire:snowy_troll_biomes", DefaultBiomes.SNOWY);
      forestTrollBiomes = Pair.of("iceandfire:forest_troll_biomes", DefaultBiomes.WOODLAND);
      mountainTrollBiomes = Pair.of("iceandfire:mountain_troll_biomes", DefaultBiomes.VERY_HILLY);
      stymphalianBiomes = Pair.of("iceandfire:stymphalian_bird_biomes", DefaultBiomes.SWAMPS);
      hydraBiomes = Pair.of("iceandfire:hydra_cave_biomes", DefaultBiomes.SWAMPS);
      mausoleumBiomes = Pair.of("iceandfire:mausoleum_biomes", DefaultBiomes.MAUSOLEUM);
      graveyardBiomes = Pair.of("iceandfire:graveyard_biomes", DefaultBiomes.GRAVEYARD);
      gorgonTempleBiomes = Pair.of("iceandfire:gorgon_temple_biomes", DefaultBiomes.BEACHES);
      cockatriceBiomes = Pair.of("iceandfire:cockatrice_biomes", DefaultBiomes.SAVANNAS);
      deathwormBiomes = Pair.of("iceandfire:deathworm_biomes", DefaultBiomes.DESERT);
      wanderingCyclopsBiomes = Pair.of("iceandfire:wandering_cyclops_biomes", DefaultBiomes.PLAINS);
      lightningDragonSkeletonBiomes = Pair.of("iceandfire:lightning_dragon_skeleton_biomes", DefaultBiomes.SAVANNAS);
      fireDragonSkeletonBiomes = Pair.of("iceandfire:fire_dragon_skeleton_biomes", DefaultBiomes.DESERT);
      iceDragonSkeletonBiomes = Pair.of("iceandfire:ice_dragon_skeleton_biomes", DefaultBiomes.VERY_SNOWY);
      blackHippogryphBiomes = Pair.of("iceandfire:hippogryph_black_biomes", DefaultBiomes.HIPPOGRYPH_BLACK);
      brownHippogryphBiomes = Pair.of("iceandfire:hippogryph_brown_biomes", DefaultBiomes.VERY_HILLY);
      grayHippogryphBiomes = Pair.of("iceandfire:hippogryph_gray_biomes", DefaultBiomes.HIPPOGRYPH_GRAY);
      chestnutHippogryphBiomes = Pair.of("iceandfire:hippogryph_chestnut_biomes", DefaultBiomes.HIPPOGRYPH_CHESTNUT);
      creamyHippogryphBiomes = Pair.of("iceandfire:hippogryph_creamy_biomes", DefaultBiomes.HIPPOGRYPH_CREAMY);
      darkBrownHippogryphBiomes = Pair.of("iceandfire:hippogryph_dark_brown_biomes", DefaultBiomes.HIPPOGRYPH_DARK_BROWN);
      whiteHippogryphBiomes = Pair.of("iceandfire:hippogryph_white_biomes", DefaultBiomes.HIPPOGRYPH_WHITE);
      init = false;
      biomeConfigValues = new HashMap();
   }
}
