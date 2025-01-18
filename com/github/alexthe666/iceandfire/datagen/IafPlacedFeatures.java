package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.world.CustomBiomeFilter;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RarityFilter;

public final class IafPlacedFeatures {
   public static final ResourceKey<PlacedFeature> PLACED_FIRE_DRAGON_ROOST = registerKey("fire_dragon_roost");
   public static final ResourceKey<PlacedFeature> PLACED_ICE_DRAGON_ROOST = registerKey("ice_dragon_roost");
   public static final ResourceKey<PlacedFeature> PLACED_LIGHTNING_DRAGON_ROOST = registerKey("lightning_dragon_roost");
   public static final ResourceKey<PlacedFeature> PLACED_FIRE_DRAGON_CAVE = registerKey("fire_dragon_cave");
   public static final ResourceKey<PlacedFeature> PLACED_ICE_DRAGON_CAVE = registerKey("ice_dragon_cave");
   public static final ResourceKey<PlacedFeature> PLACED_LIGHTNING_DRAGON_CAVE = registerKey("lightning_dragon_cave");
   public static final ResourceKey<PlacedFeature> PLACED_CYCLOPS_CAVE = registerKey("cyclops_cave");
   public static final ResourceKey<PlacedFeature> PLACED_PIXIE_VILLAGE = registerKey("pixie_village");
   public static final ResourceKey<PlacedFeature> PLACED_SIREN_ISLAND = registerKey("siren_island");
   public static final ResourceKey<PlacedFeature> PLACED_HYDRA_CAVE = registerKey("hydra_cave");
   public static final ResourceKey<PlacedFeature> PLACED_MYRMEX_HIVE_DESERT = registerKey("myrmex_hive_desert");
   public static final ResourceKey<PlacedFeature> PLACED_MYRMEX_HIVE_JUNGLE = registerKey("myrmex_hive_jungle");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_DEATH_WORM = registerKey("spawn_death_worm");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_DRAGON_SKELETON_L = registerKey("spawn_dragon_skeleton_lightning");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_DRAGON_SKELETON_F = registerKey("spawn_dragon_skeleton_fire");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_DRAGON_SKELETON_I = registerKey("spawn_dragon_skeleton_ice");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_HIPPOCAMPUS = registerKey("spawn_hippocampus");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_SEA_SERPENT = registerKey("spawn_sea_serpent");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_STYMPHALIAN_BIRD = registerKey("spawn_stymphalian_bird");
   public static final ResourceKey<PlacedFeature> PLACED_SPAWN_WANDERING_CYCLOPS = registerKey("spawn_wandering_cyclops");
   public static final ResourceKey<PlacedFeature> PLACED_SILVER_ORE = registerKey("silver_ore");
   public static final ResourceKey<PlacedFeature> PLACED_SAPPHIRE_ORE = registerKey("sapphire_ore");
   public static final ResourceKey<PlacedFeature> PLACED_FIRE_LILY = registerKey("fire_lily");
   public static final ResourceKey<PlacedFeature> PLACED_LIGHTNING_LILY = registerKey("lightning_lily");
   public static final ResourceKey<PlacedFeature> PLACED_FROST_LILY = registerKey("frost_lily");

   private static List<PlacementModifier> orePlacement(PlacementModifier pCountPlacement, PlacementModifier pHeightRange) {
      return List.of(pCountPlacement, InSquarePlacement.m_191715_(), pHeightRange, BiomeFilter.m_191561_());
   }

   private static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
      return orePlacement(CountPlacement.m_191628_(pCount), pHeightRange);
   }

   public static void bootstrap(BootstapContext<PlacedFeature> context) {
      HolderGetter<ConfiguredFeature<?, ?>> features = context.m_255420_(Registries.f_256911_);
      context.m_255272_(PLACED_FIRE_DRAGON_ROOST, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.FIRE_DRAGON_ROOST), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_ICE_DRAGON_ROOST, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.ICE_DRAGON_ROOST), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_LIGHTNING_DRAGON_ROOST, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.LIGHTNING_DRAGON_ROOST), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_CYCLOPS_CAVE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.CYCLOPS_CAVE), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_PIXIE_VILLAGE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.PIXIE_VILLAGE), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SIREN_ISLAND, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SIREN_ISLAND), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_HYDRA_CAVE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.HYDRA_CAVE), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_MYRMEX_HIVE_DESERT, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.MYRMEX_HIVE_DESERT), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_MYRMEX_HIVE_JUNGLE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.MYRMEX_HIVE_JUNGLE), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_DEATH_WORM, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_DEATH_WORM), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_DRAGON_SKELETON_L, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_DRAGON_SKELETON_L), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_DRAGON_SKELETON_F, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_DRAGON_SKELETON_F), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_DRAGON_SKELETON_I, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_DRAGON_SKELETON_I), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_HIPPOCAMPUS, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_HIPPOCAMPUS), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_SEA_SERPENT, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_SEA_SERPENT), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_STYMPHALIAN_BIRD, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_STYMPHALIAN_BIRD), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SPAWN_WANDERING_CYCLOPS, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SPAWN_WANDERING_CYCLOPS), List.of(PlacementUtils.f_195354_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_FIRE_LILY, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.FIRE_LILY), List.of(RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_FROST_LILY, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.FROST_LILY), List.of(RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_LIGHTNING_LILY, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.LIGHTNING_LILY), List.of(RarityFilter.m_191900_(32), InSquarePlacement.m_191715_(), PlacementUtils.f_195352_, BiomeFilter.m_191561_())));
      context.m_255272_(PLACED_SILVER_ORE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SILVER_ORE), commonOrePlacement(16, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-16), VerticalAnchor.m_158922_(112)))));
      context.m_255272_(PLACED_SAPPHIRE_ORE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.SAPPHIRE_ORE), commonOrePlacement(4, HeightRangePlacement.m_191692_(VerticalAnchor.m_158922_(-16), VerticalAnchor.m_158922_(112)))));
      context.m_255272_(PLACED_FIRE_DRAGON_CAVE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.FIRE_DRAGON_CAVE), List.of(CustomBiomeFilter.biome())));
      context.m_255272_(PLACED_ICE_DRAGON_CAVE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.ICE_DRAGON_CAVE), List.of(CustomBiomeFilter.biome())));
      context.m_255272_(PLACED_LIGHTNING_DRAGON_CAVE, new PlacedFeature(features.m_255043_(IafConfiguredFeatures.LIGHTNING_DRAGON_CAVE), List.of(CustomBiomeFilter.biome())));
   }

   public static ResourceKey<PlacedFeature> registerKey(String name) {
      return ResourceKey.m_135785_(Registries.f_256988_, new ResourceLocation("iceandfire", name));
   }
}
