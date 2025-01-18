package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.citadel.config.biome.SpawnBiomeData;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import com.github.alexthe666.iceandfire.datagen.IafPlacedFeatures;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.feature.SpawnDeathWorm;
import com.github.alexthe666.iceandfire.world.feature.SpawnDragonSkeleton;
import com.github.alexthe666.iceandfire.world.feature.SpawnHippocampus;
import com.github.alexthe666.iceandfire.world.feature.SpawnSeaSerpent;
import com.github.alexthe666.iceandfire.world.feature.SpawnStymphalianBird;
import com.github.alexthe666.iceandfire.world.feature.SpawnWanderingCyclops;
import com.github.alexthe666.iceandfire.world.gen.WorldGenCyclopsCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenFireDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenFireDragonRoosts;
import com.github.alexthe666.iceandfire.world.gen.WorldGenHydraCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenIceDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenIceDragonRoosts;
import com.github.alexthe666.iceandfire.world.gen.WorldGenLightningDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenLightningDragonRoosts;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.github.alexthe666.iceandfire.world.gen.WorldGenPixieVillage;
import com.github.alexthe666.iceandfire.world.gen.WorldGenSirenIsland;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.storage.LevelData;
import net.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;
import org.apache.commons.lang3.tuple.Pair;

public class IafWorldRegistry {
   public static final DeferredRegister<Feature<?>> FEATURES;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_ROOST;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_ROOST;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_ROOST;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> FIRE_DRAGON_CAVE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> ICE_DRAGON_CAVE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> LIGHTNING_DRAGON_CAVE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> CYCLOPS_CAVE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> PIXIE_VILLAGE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SIREN_ISLAND;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> HYDRA_CAVE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_DESERT;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> MYRMEX_HIVE_JUNGLE;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DEATH_WORM;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_L;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_F;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_DRAGON_SKELETON_I;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_HIPPOCAMPUS;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_SEA_SERPENT;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_STYMPHALIAN_BIRD;
   public static final RegistryObject<Feature<NoneFeatureConfiguration>> SPAWN_WANDERING_CYCLOPS;
   public static HashMap<String, Boolean> LOADED_FEATURES;
   private static List<String> ADDED_FEATURES;

   private static <C extends FeatureConfiguration, F extends Feature<C>> RegistryObject<F> register(String name, Supplier<? extends F> supplier) {
      return FEATURES.register(name, supplier);
   }

   public static boolean isFarEnoughFromSpawn(LevelAccessor level, BlockPos position) {
      LevelData spawnPoint = level.m_6106_();
      BlockPos spawnRelative = new BlockPos(spawnPoint.m_6789_(), position.m_123342_(), spawnPoint.m_6527_());
      return !spawnRelative.m_123314_(position, IafConfig.dangerousWorldGenDistanceLimit);
   }

   public static boolean isFarEnoughFromDangerousGen(ServerLevelAccessor level, BlockPos position, String id) {
      return isFarEnoughFromDangerousGen(level, position, id, IafWorldData.FeatureType.SURFACE);
   }

   public static boolean isFarEnoughFromDangerousGen(ServerLevelAccessor level, BlockPos position, String id, IafWorldData.FeatureType type) {
      IafWorldData data = IafWorldData.get(level.m_6018_());
      return data.check(type, position, id);
   }

   public static void addFeatures(Holder<Biome> biome, HashMap<String, Holder<PlacedFeature>> features, Builder builder) {
      ADDED_FEATURES = new ArrayList();
      if (safelyTestBiome(BiomeConfig.fireLilyBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_FIRE_LILY, features, builder, Decoration.VEGETAL_DECORATION);
      }

      if (safelyTestBiome(BiomeConfig.lightningLilyBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_LIGHTNING_LILY, features, builder, Decoration.VEGETAL_DECORATION);
      }

      if (safelyTestBiome(BiomeConfig.frostLilyBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_FROST_LILY, features, builder, Decoration.VEGETAL_DECORATION);
      }

      if (safelyTestBiome(BiomeConfig.oreGenBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SILVER_ORE, features, builder, Decoration.UNDERGROUND_ORES);
      }

      if (safelyTestBiome(BiomeConfig.sapphireBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SAPPHIRE_ORE, features, builder, Decoration.UNDERGROUND_ORES);
      }

      if (safelyTestBiome(BiomeConfig.fireDragonBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_FIRE_DRAGON_ROOST, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.lightningDragonBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_LIGHTNING_DRAGON_ROOST, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.iceDragonBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_ICE_DRAGON_ROOST, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.fireDragonCaveBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_FIRE_DRAGON_CAVE, features, builder, Decoration.UNDERGROUND_STRUCTURES);
      }

      if (safelyTestBiome(BiomeConfig.lightningDragonCaveBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_LIGHTNING_DRAGON_CAVE, features, builder, Decoration.UNDERGROUND_STRUCTURES);
      }

      if (safelyTestBiome(BiomeConfig.iceDragonCaveBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_ICE_DRAGON_CAVE, features, builder, Decoration.UNDERGROUND_STRUCTURES);
      }

      if (safelyTestBiome(BiomeConfig.cyclopsCaveBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_CYCLOPS_CAVE, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.pixieBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_PIXIE_VILLAGE, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.hydraBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_HYDRA_CAVE, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.desertMyrmexBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_MYRMEX_HIVE_DESERT, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.jungleMyrmexBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_MYRMEX_HIVE_JUNGLE, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.sirenBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SIREN_ISLAND, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.deathwormBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_DEATH_WORM, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.wanderingCyclopsBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_WANDERING_CYCLOPS, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.lightningDragonSkeletonBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_DRAGON_SKELETON_L, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.fireDragonSkeletonBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_DRAGON_SKELETON_F, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.iceDragonSkeletonBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_DRAGON_SKELETON_I, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.hippocampusBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_HIPPOCAMPUS, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.seaSerpentBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_SEA_SERPENT, features, builder);
      }

      if (safelyTestBiome(BiomeConfig.stymphalianBiomes, biome)) {
         addFeatureToBiome(IafPlacedFeatures.PLACED_SPAWN_STYMPHALIAN_BIRD, features, builder);
      }

      if (!ADDED_FEATURES.isEmpty()) {
         StringBuilder featureList = new StringBuilder();
         Iterator var4 = ADDED_FEATURES.iterator();

         while(var4.hasNext()) {
            String feature = (String)var4.next();
            featureList.append("\n").append("\t- ").append(feature);
         }

         IceAndFire.LOGGER.debug("Added the following features to the biome [{}]: {}", ((ResourceKey)biome.m_203543_().get()).m_135782_(), featureList);
      }

      ADDED_FEATURES = null;
   }

   private static void addFeatureToBiome(ResourceKey<PlacedFeature> feature, HashMap<String, Holder<PlacedFeature>> features, Builder builder) {
      addFeatureToBiome(feature, features, builder, Decoration.SURFACE_STRUCTURES);
   }

   private static void addFeatureToBiome(ResourceKey<PlacedFeature> featureResource, HashMap<String, Holder<PlacedFeature>> features, Builder builder, Decoration step) {
      String identifier = featureResource.m_135782_().toString();
      Holder<PlacedFeature> feature = (Holder)features.get(identifier);
      if (feature != null) {
         builder.getGenerationSettings().getFeatures(step).add(feature);
         LOADED_FEATURES.put(identifier, true);
         ADDED_FEATURES.add(identifier);
      } else {
         IceAndFire.LOGGER.warn("Feature [{}] could not be found", identifier);
      }

   }

   private static boolean safelyTestBiome(Pair<String, SpawnBiomeData> entry, Holder<Biome> biomeHolder) {
      try {
         return BiomeConfig.test(entry, biomeHolder);
      } catch (Exception var3) {
         return false;
      }
   }

   static {
      FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, "iceandfire");
      FIRE_DRAGON_ROOST = register("fire_dragon_roost", () -> {
         return new WorldGenFireDragonRoosts(NoneFeatureConfiguration.f_67815_);
      });
      ICE_DRAGON_ROOST = register("ice_dragon_roost", () -> {
         return new WorldGenIceDragonRoosts(NoneFeatureConfiguration.f_67815_);
      });
      LIGHTNING_DRAGON_ROOST = register("lightning_dragon_roost", () -> {
         return new WorldGenLightningDragonRoosts(NoneFeatureConfiguration.f_67815_);
      });
      FIRE_DRAGON_CAVE = register("fire_dragon_cave", () -> {
         return new WorldGenFireDragonCave(NoneFeatureConfiguration.f_67815_);
      });
      ICE_DRAGON_CAVE = register("ice_dragon_cave", () -> {
         return new WorldGenIceDragonCave(NoneFeatureConfiguration.f_67815_);
      });
      LIGHTNING_DRAGON_CAVE = register("lightning_dragon_cave", () -> {
         return new WorldGenLightningDragonCave(NoneFeatureConfiguration.f_67815_);
      });
      CYCLOPS_CAVE = register("cyclops_cave", () -> {
         return new WorldGenCyclopsCave(NoneFeatureConfiguration.f_67815_);
      });
      PIXIE_VILLAGE = register("pixie_village", () -> {
         return new WorldGenPixieVillage(NoneFeatureConfiguration.f_67815_);
      });
      SIREN_ISLAND = register("siren_island", () -> {
         return new WorldGenSirenIsland(NoneFeatureConfiguration.f_67815_);
      });
      HYDRA_CAVE = register("hydra_cave", () -> {
         return new WorldGenHydraCave(NoneFeatureConfiguration.f_67815_);
      });
      MYRMEX_HIVE_DESERT = register("myrmex_hive_desert", () -> {
         return new WorldGenMyrmexHive(false, false, NoneFeatureConfiguration.f_67815_);
      });
      MYRMEX_HIVE_JUNGLE = register("myrmex_hive_jungle", () -> {
         return new WorldGenMyrmexHive(false, true, NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_DEATH_WORM = register("spawn_death_worm", () -> {
         return new SpawnDeathWorm(NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_DRAGON_SKELETON_L = register("spawn_dragon_skeleton_lightning", () -> {
         return new SpawnDragonSkeleton((EntityType)IafEntityRegistry.LIGHTNING_DRAGON.get(), NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_DRAGON_SKELETON_F = register("spawn_dragon_skeleton_fire", () -> {
         return new SpawnDragonSkeleton((EntityType)IafEntityRegistry.FIRE_DRAGON.get(), NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_DRAGON_SKELETON_I = register("spawn_dragon_skeleton_ice", () -> {
         return new SpawnDragonSkeleton((EntityType)IafEntityRegistry.ICE_DRAGON.get(), NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_HIPPOCAMPUS = register("spawn_hippocampus", () -> {
         return new SpawnHippocampus(NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_SEA_SERPENT = register("spawn_sea_serpent", () -> {
         return new SpawnSeaSerpent(NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_STYMPHALIAN_BIRD = register("spawn_stymphalian_bird", () -> {
         return new SpawnStymphalianBird(NoneFeatureConfiguration.f_67815_);
      });
      SPAWN_WANDERING_CYCLOPS = register("spawn_wandering_cyclops", () -> {
         return new SpawnWanderingCyclops(NoneFeatureConfiguration.f_67815_);
      });
      LOADED_FEATURES = new HashMap();
      LOADED_FEATURES.put("iceandfire:fire_lily", false);
      LOADED_FEATURES.put("iceandfire:frost_lily", false);
      LOADED_FEATURES.put("iceandfire:lightning_lily", false);
      LOADED_FEATURES.put("iceandfire:silver_ore", false);
      LOADED_FEATURES.put("iceandfire:sapphire_ore", false);
      LOADED_FEATURES.put("iceandfire:fire_dragon_roost", false);
      LOADED_FEATURES.put("iceandfire:ice_dragon_roost", false);
      LOADED_FEATURES.put("iceandfire:lightning_dragon_roost", false);
      LOADED_FEATURES.put("iceandfire:fire_dragon_cave", false);
      LOADED_FEATURES.put("iceandfire:ice_dragon_cave", false);
      LOADED_FEATURES.put("iceandfire:lightning_dragon_cave", false);
      LOADED_FEATURES.put("iceandfire:cyclops_cave", false);
      LOADED_FEATURES.put("iceandfire:pixie_village", false);
      LOADED_FEATURES.put("iceandfire:siren_island", false);
      LOADED_FEATURES.put("iceandfire:hydra_cave", false);
      LOADED_FEATURES.put("iceandfire:myrmex_hive_desert", false);
      LOADED_FEATURES.put("iceandfire:myrmex_hive_jungle", false);
      LOADED_FEATURES.put("iceandfire:spawn_death_worm", false);
      LOADED_FEATURES.put("iceandfire:spawn_dragon_skeleton_lightning", false);
      LOADED_FEATURES.put("iceandfire:spawn_dragon_skeleton_fire", false);
      LOADED_FEATURES.put("iceandfire:spawn_dragon_skeleton_ice", false);
      LOADED_FEATURES.put("iceandfire:spawn_hippocampus", false);
      LOADED_FEATURES.put("iceandfire:spawn_sea_serpent", false);
      LOADED_FEATURES.put("iceandfire:spawn_stymphalian_bird", false);
      LOADED_FEATURES.put("iceandfire:spawn_wandering_cyclops", false);
   }
}
