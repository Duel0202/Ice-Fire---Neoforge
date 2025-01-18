package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import java.util.List;
import java.util.function.Function;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration.TargetBlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

public final class IafConfiguredFeatures {
   public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_DRAGON_ROOST = registerKey("fire_dragon_roost");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_DRAGON_ROOST = registerKey("ice_dragon_roost");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTNING_DRAGON_ROOST = registerKey("lightning_dragon_roost");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_DRAGON_CAVE = registerKey("fire_dragon_cave");
   public static final ResourceKey<ConfiguredFeature<?, ?>> ICE_DRAGON_CAVE = registerKey("ice_dragon_cave");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTNING_DRAGON_CAVE = registerKey("lightning_dragon_cave");
   public static final ResourceKey<ConfiguredFeature<?, ?>> CYCLOPS_CAVE = registerKey("cyclops_cave");
   public static final ResourceKey<ConfiguredFeature<?, ?>> PIXIE_VILLAGE = registerKey("pixie_village");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SIREN_ISLAND = registerKey("siren_island");
   public static final ResourceKey<ConfiguredFeature<?, ?>> HYDRA_CAVE = registerKey("hydra_cave");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MYRMEX_HIVE_DESERT = registerKey("myrmex_hive_desert");
   public static final ResourceKey<ConfiguredFeature<?, ?>> MYRMEX_HIVE_JUNGLE = registerKey("myrmex_hive_jungle");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_DEATH_WORM = registerKey("spawn_death_worm");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_DRAGON_SKELETON_L = registerKey("spawn_dragon_skeleton_lightning");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_DRAGON_SKELETON_F = registerKey("spawn_dragon_skeleton_fire");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_DRAGON_SKELETON_I = registerKey("spawn_dragon_skeleton_ice");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_HIPPOCAMPUS = registerKey("spawn_hippocampus");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_SEA_SERPENT = registerKey("spawn_sea_serpent");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_STYMPHALIAN_BIRD = registerKey("spawn_stymphalian_bird");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SPAWN_WANDERING_CYCLOPS = registerKey("spawn_wandering_cyclops");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORE = registerKey("silver_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> SAPPHIRE_ORE = registerKey("sapphire_ore");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FIRE_LILY = registerKey("fire_lily");
   public static final ResourceKey<ConfiguredFeature<?, ?>> FROST_LILY = registerKey("frost_lily");
   public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTNING_LILY = registerKey("lightning_lily");
   private static Function<Block, RandomPatchConfiguration> flowerConf = (block) -> {
      return FeatureUtils.m_206470_(8, PlacementUtils.m_206495_(Feature.f_65741_, new SimpleBlockConfiguration(BlockStateProvider.m_191382_(block.m_49966_().m_60734_()))));
   };

   public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
      return ResourceKey.m_135785_(Registries.f_256911_, new ResourceLocation("iceandfire", name));
   }

   public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
      context.m_255272_(FIRE_DRAGON_ROOST, new ConfiguredFeature((Feature)IafWorldRegistry.FIRE_DRAGON_ROOST.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(ICE_DRAGON_ROOST, new ConfiguredFeature((Feature)IafWorldRegistry.ICE_DRAGON_ROOST.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(LIGHTNING_DRAGON_ROOST, new ConfiguredFeature((Feature)IafWorldRegistry.LIGHTNING_DRAGON_ROOST.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(FIRE_DRAGON_CAVE, new ConfiguredFeature((Feature)IafWorldRegistry.FIRE_DRAGON_CAVE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(ICE_DRAGON_CAVE, new ConfiguredFeature((Feature)IafWorldRegistry.ICE_DRAGON_CAVE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(LIGHTNING_DRAGON_CAVE, new ConfiguredFeature((Feature)IafWorldRegistry.LIGHTNING_DRAGON_CAVE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(CYCLOPS_CAVE, new ConfiguredFeature((Feature)IafWorldRegistry.CYCLOPS_CAVE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(PIXIE_VILLAGE, new ConfiguredFeature((Feature)IafWorldRegistry.PIXIE_VILLAGE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SIREN_ISLAND, new ConfiguredFeature((Feature)IafWorldRegistry.SIREN_ISLAND.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(HYDRA_CAVE, new ConfiguredFeature((Feature)IafWorldRegistry.HYDRA_CAVE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(MYRMEX_HIVE_DESERT, new ConfiguredFeature((Feature)IafWorldRegistry.MYRMEX_HIVE_DESERT.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(MYRMEX_HIVE_JUNGLE, new ConfiguredFeature((Feature)IafWorldRegistry.MYRMEX_HIVE_JUNGLE.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_DEATH_WORM, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_DEATH_WORM.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_DRAGON_SKELETON_L, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_DRAGON_SKELETON_L.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_DRAGON_SKELETON_F, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_DRAGON_SKELETON_F.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_DRAGON_SKELETON_I, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_DRAGON_SKELETON_I.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_HIPPOCAMPUS, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_HIPPOCAMPUS.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_SEA_SERPENT, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_SEA_SERPENT.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_STYMPHALIAN_BIRD, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_STYMPHALIAN_BIRD.get(), FeatureConfiguration.f_67737_));
      context.m_255272_(SPAWN_WANDERING_CYCLOPS, new ConfiguredFeature((Feature)IafWorldRegistry.SPAWN_WANDERING_CYCLOPS.get(), FeatureConfiguration.f_67737_));
      RuleTest stoneOreRule = new TagMatchTest(BlockTags.f_144266_);
      RuleTest deepslateOreRule = new TagMatchTest(BlockTags.f_144267_);
      List<TargetBlockState> silverOreConfiguration = List.of(OreConfiguration.m_161021_(stoneOreRule, ((Block)IafBlockRegistry.SILVER_ORE.get()).m_49966_()), OreConfiguration.m_161021_(deepslateOreRule, ((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get()).m_49966_()));
      context.m_255272_(SILVER_ORE, new ConfiguredFeature(Feature.f_65731_, new OreConfiguration(silverOreConfiguration, 4)));
      context.m_255272_(SAPPHIRE_ORE, new ConfiguredFeature(Feature.f_65731_, new OreConfiguration(new TagMatchTest(BlockTags.f_144266_), ((Block)IafBlockRegistry.SAPPHIRE_ORE.get()).m_49966_(), 4, 0.5F)));
      context.m_255272_(FIRE_LILY, new ConfiguredFeature(Feature.f_65761_, (FeatureConfiguration)flowerConf.apply((Block)IafBlockRegistry.FIRE_LILY.get())));
      context.m_255272_(FROST_LILY, new ConfiguredFeature(Feature.f_65761_, (FeatureConfiguration)flowerConf.apply((Block)IafBlockRegistry.FROST_LILY.get())));
      context.m_255272_(LIGHTNING_LILY, new ConfiguredFeature(Feature.f_65761_, (FeatureConfiguration)flowerConf.apply((Block)IafBlockRegistry.LIGHTNING_LILY.get())));
   }
}
