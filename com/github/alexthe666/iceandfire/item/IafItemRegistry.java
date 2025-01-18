package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.citadel.server.item.CustomToolMaterial;
import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.datagen.tags.BannerPatternTagGenerator;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.enums.EnumSeaSerpent;
import com.github.alexthe666.iceandfire.enums.EnumSkullType;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforge.common.ForgeSpawnEggItem;
import net.neoforge.common.Tags.Items;
import net.neoforge.eventbus.api.EventPriority;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.NewRegistryEvent;
import net.neoforge.registries.RegisterEvent;
import net.neoforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "iceandfire",
   bus = Bus.MOD
)
public class IafItemRegistry {
   public static CustomArmorMaterial SILVER_ARMOR_MATERIAL;
   public static CustomArmorMaterial COPPER_ARMOR_MATERIAL;
   public static CustomArmorMaterial BLINDFOLD_ARMOR_MATERIAL;
   public static CustomArmorMaterial SHEEP_ARMOR_MATERIAL;
   public static CustomArmorMaterial MYRMEX_DESERT_ARMOR_MATERIAL;
   public static CustomArmorMaterial MYRMEX_JUNGLE_ARMOR_MATERIAL;
   public static CustomArmorMaterial EARPLUGS_ARMOR_MATERIAL;
   public static CustomArmorMaterial DEATHWORM_0_ARMOR_MATERIAL;
   public static CustomArmorMaterial DEATHWORM_1_ARMOR_MATERIAL;
   public static CustomArmorMaterial DEATHWORM_2_ARMOR_MATERIAL;
   public static CustomArmorMaterial TROLL_MOUNTAIN_ARMOR_MATERIAL;
   public static CustomArmorMaterial TROLL_FOREST_ARMOR_MATERIAL;
   public static CustomArmorMaterial TROLL_FROST_ARMOR_MATERIAL;
   public static CustomArmorMaterial DRAGONSTEEL_FIRE_ARMOR_MATERIAL;
   public static CustomArmorMaterial DRAGONSTEEL_ICE_ARMOR_MATERIAL;
   public static CustomArmorMaterial DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL;
   public static CustomToolMaterial SILVER_TOOL_MATERIAL;
   public static CustomToolMaterial COPPER_TOOL_MATERIAL;
   public static CustomToolMaterial DRAGONBONE_TOOL_MATERIAL;
   public static CustomToolMaterial FIRE_DRAGONBONE_TOOL_MATERIAL;
   public static CustomToolMaterial ICE_DRAGONBONE_TOOL_MATERIAL;
   public static CustomToolMaterial LIGHTNING_DRAGONBONE_TOOL_MATERIAL;
   public static CustomToolMaterial TROLL_WEAPON_TOOL_MATERIAL;
   public static CustomToolMaterial MYRMEX_CHITIN_TOOL_MATERIAL;
   public static CustomToolMaterial HIPPOGRYPH_SWORD_TOOL_MATERIAL;
   public static CustomToolMaterial STYMHALIAN_SWORD_TOOL_MATERIAL;
   public static CustomToolMaterial AMPHITHERE_SWORD_TOOL_MATERIAL;
   public static CustomToolMaterial HIPPOCAMPUS_SWORD_TOOL_MATERIAL;
   public static CustomToolMaterial DREAD_SWORD_TOOL_MATERIAL;
   public static CustomToolMaterial DREAD_KNIGHT_TOOL_MATERIAL;
   public static CustomToolMaterial GHOST_SWORD_TOOL_MATERIAL;
   public static DeferredRegister<Item> ITEMS;
   public static final RegistryObject<Item> BESTIARY;
   public static final RegistryObject<Item> MANUSCRIPT;
   public static final RegistryObject<Item> SAPPHIRE_GEM;
   public static final RegistryObject<Item> SILVER_INGOT;
   public static final RegistryObject<Item> SILVER_NUGGET;
   public static final RegistryObject<Item> RAW_SILVER;
   public static final RegistryObject<Item> COPPER_NUGGET;
   public static final RegistryObject<Item> SILVER_HELMET;
   public static final RegistryObject<Item> SILVER_CHESTPLATE;
   public static final RegistryObject<Item> SILVER_LEGGINGS;
   public static final RegistryObject<Item> SILVER_BOOTS;
   public static final RegistryObject<Item> SILVER_SWORD;
   public static final RegistryObject<Item> SILVER_SHOVEL;
   public static final RegistryObject<Item> SILVER_PICKAXE;
   public static final RegistryObject<Item> SILVER_AXE;
   public static final RegistryObject<Item> SILVER_HOE;
   public static final RegistryObject<Item> COPPER_HELMET;
   public static final RegistryObject<Item> COPPER_CHESTPLATE;
   public static final RegistryObject<Item> COPPER_LEGGINGS;
   public static final RegistryObject<Item> COPPER_BOOTS;
   public static final RegistryObject<Item> COPPER_SWORD;
   public static final RegistryObject<Item> COPPER_SHOVEL;
   public static final RegistryObject<Item> COPPER_PICKAXE;
   public static final RegistryObject<Item> COPPER_AXE;
   public static final RegistryObject<Item> COPPER_HOE;
   public static final RegistryObject<Item> FIRE_STEW;
   public static final RegistryObject<Item> FROST_STEW;
   public static final RegistryObject<Item> LIGHTNING_STEW;
   public static final RegistryObject<Item> DRAGONEGG_RED;
   public static final RegistryObject<Item> DRAGONEGG_GREEN;
   public static final RegistryObject<Item> DRAGONEGG_BRONZE;
   public static final RegistryObject<Item> DRAGONEGG_GRAY;
   public static final RegistryObject<Item> DRAGONEGG_BLUE;
   public static final RegistryObject<Item> DRAGONEGG_WHITE;
   public static final RegistryObject<Item> DRAGONEGG_SAPPHIRE;
   public static final RegistryObject<Item> DRAGONEGG_SILVER;
   public static final RegistryObject<Item> DRAGONEGG_ELECTRIC;
   public static final RegistryObject<Item> DRAGONEGG_AMYTHEST;
   public static final RegistryObject<Item> DRAGONEGG_COPPER;
   public static final RegistryObject<Item> DRAGONEGG_BLACK;
   public static final RegistryObject<Item> DRAGONSCALES_RED;
   public static final RegistryObject<Item> DRAGONSCALES_GREEN;
   public static final RegistryObject<Item> DRAGONSCALES_BRONZE;
   public static final RegistryObject<Item> DRAGONSCALES_GRAY;
   public static final RegistryObject<Item> DRAGONSCALES_BLUE;
   public static final RegistryObject<Item> DRAGONSCALES_WHITE;
   public static final RegistryObject<Item> DRAGONSCALES_SAPPHIRE;
   public static final RegistryObject<Item> DRAGONSCALES_SILVER;
   public static final RegistryObject<Item> DRAGONSCALES_ELECTRIC;
   public static final RegistryObject<Item> DRAGONSCALES_AMYTHEST;
   public static final RegistryObject<Item> DRAGONSCALES_COPPER;
   public static final RegistryObject<Item> DRAGONSCALES_BLACK;
   public static final RegistryObject<Item> DRAGON_BONE;
   public static final RegistryObject<Item> WITHERBONE;
   public static final RegistryObject<Item> FISHING_SPEAR;
   public static final RegistryObject<Item> WITHER_SHARD;
   public static final RegistryObject<Item> DRAGONBONE_SWORD;
   public static final RegistryObject<Item> DRAGONBONE_SHOVEL;
   public static final RegistryObject<Item> DRAGONBONE_PICKAXE;
   public static final RegistryObject<Item> DRAGONBONE_AXE;
   public static final RegistryObject<Item> DRAGONBONE_HOE;
   public static final RegistryObject<Item> DRAGONBONE_SWORD_FIRE;
   public static final RegistryObject<Item> DRAGONBONE_SWORD_ICE;
   public static final RegistryObject<Item> DRAGONBONE_SWORD_LIGHTNING;
   public static final RegistryObject<Item> DRAGONBONE_ARROW;
   public static final RegistryObject<Item> DRAGON_BOW;
   public static final RegistryObject<Item> DRAGON_SKULL_FIRE;
   public static final RegistryObject<Item> DRAGON_SKULL_ICE;
   public static final RegistryObject<Item> DRAGON_SKULL_LIGHTNING;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_IRON_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_IRON_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_IRON_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_IRON_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_COPPER_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_COPPER_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_COPPER_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_COPPER_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_GOLD_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_GOLD_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_GOLD_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_GOLD_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DIAMOND_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DIAMOND_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DIAMOND_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DIAMOND_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_SILVER_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_SILVER_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_SILVER_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_SILVER_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_FIRE_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_FIRE_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_FIRE_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_FIRE_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_ICE_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_ICE_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_ICE_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_ICE_3;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_LIGHTNING_0;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_LIGHTNING_1;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_LIGHTNING_2;
   public static final RegistryObject<ItemDragonArmor> DRAGONARMOR_DRAGONSTEEL_LIGHTNING_3;
   public static final RegistryObject<Item> DRAGON_MEAL;
   public static final RegistryObject<Item> SICKLY_DRAGON_MEAL;
   public static final RegistryObject<Item> CREATIVE_DRAGON_MEAL;
   public static final RegistryObject<Item> FIRE_DRAGON_FLESH;
   public static final RegistryObject<Item> ICE_DRAGON_FLESH;
   public static final RegistryObject<Item> LIGHTNING_DRAGON_FLESH;
   public static final RegistryObject<Item> FIRE_DRAGON_HEART;
   public static final RegistryObject<Item> ICE_DRAGON_HEART;
   public static final RegistryObject<Item> LIGHTNING_DRAGON_HEART;
   public static final RegistryObject<Item> FIRE_DRAGON_BLOOD;
   public static final RegistryObject<Item> ICE_DRAGON_BLOOD;
   public static final RegistryObject<Item> LIGHTNING_DRAGON_BLOOD;
   public static final RegistryObject<Item> DRAGON_STAFF;
   public static final RegistryObject<Item> DRAGON_HORN;
   public static final RegistryObject<Item> DRAGON_FLUTE;
   public static final RegistryObject<Item> SUMMONING_CRYSTAL_FIRE;
   public static final RegistryObject<Item> SUMMONING_CRYSTAL_ICE;
   public static final RegistryObject<Item> SUMMONING_CRYSTAL_LIGHTNING;
   public static final RegistryObject<Item> HIPPOGRYPH_EGG;
   public static final RegistryObject<Item> IRON_HIPPOGRYPH_ARMOR;
   public static final RegistryObject<Item> GOLD_HIPPOGRYPH_ARMOR;
   public static final RegistryObject<Item> DIAMOND_HIPPOGRYPH_ARMOR;
   public static final RegistryObject<Item> HIPPOGRYPH_TALON;
   public static final RegistryObject<Item> HIPPOGRYPH_SWORD;
   public static final RegistryObject<Item> GORGON_HEAD;
   public static final RegistryObject<Item> STONE_STATUE;
   public static final RegistryObject<Item> BLINDFOLD;
   public static final RegistryObject<Item> PIXIE_DUST;
   public static final RegistryObject<Item> PIXIE_WINGS;
   public static final RegistryObject<Item> PIXIE_WAND;
   public static final RegistryObject<Item> AMBROSIA;
   public static final RegistryObject<Item> CYCLOPS_EYE;
   public static final RegistryObject<Item> SHEEP_HELMET;
   public static final RegistryObject<Item> SHEEP_CHESTPLATE;
   public static final RegistryObject<Item> SHEEP_LEGGINGS;
   public static final RegistryObject<Item> SHEEP_BOOTS;
   public static final RegistryObject<Item> SHINY_SCALES;
   public static final RegistryObject<Item> SIREN_TEAR;
   public static final RegistryObject<Item> SIREN_FLUTE;
   public static final RegistryObject<Item> HIPPOCAMPUS_FIN;
   public static final RegistryObject<Item> HIPPOCAMPUS_SLAPPER;
   public static final RegistryObject<Item> EARPLUGS;
   public static final RegistryObject<Item> DEATH_WORM_CHITIN_YELLOW;
   public static final RegistryObject<Item> DEATH_WORM_CHITIN_WHITE;
   public static final RegistryObject<Item> DEATH_WORM_CHITIN_RED;
   public static final RegistryObject<Item> DEATHWORM_YELLOW_HELMET;
   public static final RegistryObject<Item> DEATHWORM_YELLOW_CHESTPLATE;
   public static final RegistryObject<Item> DEATHWORM_YELLOW_LEGGINGS;
   public static final RegistryObject<Item> DEATHWORM_YELLOW_BOOTS;
   public static final RegistryObject<Item> DEATHWORM_WHITE_HELMET;
   public static final RegistryObject<Item> DEATHWORM_WHITE_CHESTPLATE;
   public static final RegistryObject<Item> DEATHWORM_WHITE_LEGGINGS;
   public static final RegistryObject<Item> DEATHWORM_WHITE_BOOTS;
   public static final RegistryObject<Item> DEATHWORM_RED_HELMET;
   public static final RegistryObject<Item> DEATHWORM_RED_CHESTPLATE;
   public static final RegistryObject<Item> DEATHWORM_RED_LEGGINGS;
   public static final RegistryObject<Item> DEATHWORM_RED_BOOTS;
   public static final RegistryObject<Item> DEATHWORM_EGG;
   public static final RegistryObject<Item> DEATHWORM_EGG_GIGANTIC;
   public static final RegistryObject<Item> DEATHWORM_TOUNGE;
   public static final RegistryObject<Item> DEATHWORM_GAUNTLET_YELLOW;
   public static final RegistryObject<Item> DEATHWORM_GAUNTLET_WHITE;
   public static final RegistryObject<Item> DEATHWORM_GAUNTLET_RED;
   public static final RegistryObject<Item> ROTTEN_EGG;
   public static final RegistryObject<Item> COCKATRICE_EYE;
   public static final RegistryObject<Item> ITEM_COCKATRICE_SCEPTER;
   public static final RegistryObject<Item> STYMPHALIAN_BIRD_FEATHER;
   public static final RegistryObject<Item> STYMPHALIAN_ARROW;
   public static final RegistryObject<Item> STYMPHALIAN_FEATHER_BUNDLE;
   public static final RegistryObject<Item> STYMPHALIAN_DAGGER;
   public static final RegistryObject<Item> TROLL_TUSK;
   public static final RegistryObject<Item> MYRMEX_DESERT_EGG;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_EGG;
   public static final RegistryObject<Item> MYRMEX_DESERT_RESIN;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_RESIN;
   public static final RegistryObject<Item> MYRMEX_DESERT_CHITIN;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_CHITIN;
   public static final RegistryObject<Item> MYRMEX_STINGER;
   public static final RegistryObject<Item> MYRMEX_DESERT_SWORD;
   public static final RegistryObject<Item> MYRMEX_DESERT_SWORD_VENOM;
   public static final RegistryObject<Item> MYRMEX_DESERT_SHOVEL;
   public static final RegistryObject<Item> MYRMEX_DESERT_PICKAXE;
   public static final RegistryObject<Item> MYRMEX_DESERT_AXE;
   public static final RegistryObject<Item> MYRMEX_DESERT_HOE;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_SWORD;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_SWORD_VENOM;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_SHOVEL;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_PICKAXE;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_AXE;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_HOE;
   public static final RegistryObject<Item> MYRMEX_DESERT_STAFF;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_STAFF;
   public static final RegistryObject<Item> MYRMEX_DESERT_HELMET;
   public static final RegistryObject<Item> MYRMEX_DESERT_CHESTPLATE;
   public static final RegistryObject<Item> MYRMEX_DESERT_LEGGINGS;
   public static final RegistryObject<Item> MYRMEX_DESERT_BOOTS;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_HELMET;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_CHESTPLATE;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_LEGGINGS;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_BOOTS;
   public static final RegistryObject<Item> MYRMEX_DESERT_SWARM;
   public static final RegistryObject<Item> MYRMEX_JUNGLE_SWARM;
   public static final RegistryObject<Item> AMPHITHERE_FEATHER;
   public static final RegistryObject<Item> AMPHITHERE_ARROW;
   public static final RegistryObject<Item> AMPHITHERE_MACUAHUITL;
   public static final RegistryObject<Item> SERPENT_FANG;
   public static final RegistryObject<Item> SEA_SERPENT_ARROW;
   public static final RegistryObject<Item> TIDE_TRIDENT_INVENTORY;
   public static final RegistryObject<Item> TIDE_TRIDENT;
   public static final RegistryObject<Item> CHAIN;
   public static final RegistryObject<Item> CHAIN_STICKY;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_INGOT;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_SWORD;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_PICKAXE;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_AXE;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_SHOVEL;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_HOE;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_HELMET;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_CHESTPLATE;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_LEGGINGS;
   public static final RegistryObject<Item> DRAGONSTEEL_FIRE_BOOTS;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_INGOT;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_SWORD;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_PICKAXE;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_AXE;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_SHOVEL;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_HOE;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_HELMET;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_CHESTPLATE;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_LEGGINGS;
   public static final RegistryObject<Item> DRAGONSTEEL_ICE_BOOTS;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_INGOT;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_SWORD;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_PICKAXE;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_AXE;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_SHOVEL;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_HOE;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_HELMET;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_CHESTPLATE;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_LEGGINGS;
   public static final RegistryObject<Item> DRAGONSTEEL_LIGHTNING_BOOTS;
   public static final RegistryObject<Item> WEEZER_BLUE_ALBUM;
   public static final RegistryObject<Item> DRAGON_DEBUG_STICK;
   public static final RegistryObject<Item> DREAD_SWORD;
   public static final RegistryObject<Item> DREAD_KNIGHT_SWORD;
   public static final RegistryObject<Item> LICH_STAFF;
   public static final RegistryObject<Item> DREAD_QUEEN_SWORD;
   public static final RegistryObject<Item> DREAD_QUEEN_STAFF;
   public static final RegistryObject<Item> DREAD_SHARD;
   public static final RegistryObject<Item> DREAD_KEY;
   public static final RegistryObject<Item> HYDRA_FANG;
   public static final RegistryObject<Item> HYDRA_HEART;
   public static final RegistryObject<Item> HYDRA_ARROW;
   public static final RegistryObject<Item> CANNOLI;
   public static final RegistryObject<Item> ECTOPLASM;
   public static final RegistryObject<Item> GHOST_INGOT;
   public static final RegistryObject<Item> GHOST_SWORD;
   public static final RegistryObject<BannerPatternItem> PATTERN_FIRE;
   public static final RegistryObject<BannerPatternItem> PATTERN_ICE;
   public static final RegistryObject<BannerPatternItem> PATTERN_LIGHTNING;
   public static final RegistryObject<BannerPatternItem> PATTERN_FIRE_HEAD;
   public static final RegistryObject<BannerPatternItem> PATTERN_ICE_HEAD;
   public static final RegistryObject<BannerPatternItem> PATTERN_LIGHTNING_HEAD;
   public static final RegistryObject<BannerPatternItem> PATTERN_AMPHITHERE;
   public static final RegistryObject<BannerPatternItem> PATTERN_BIRD;
   public static final RegistryObject<BannerPatternItem> PATTERN_EYE;
   public static final RegistryObject<BannerPatternItem> PATTERN_FAE;
   public static final RegistryObject<BannerPatternItem> PATTERN_FEATHER;
   public static final RegistryObject<BannerPatternItem> PATTERN_GORGON;
   public static final RegistryObject<BannerPatternItem> PATTERN_HIPPOCAMPUS;
   public static final RegistryObject<BannerPatternItem> PATTERN_HIPPOGRYPH_HEAD;
   public static final RegistryObject<BannerPatternItem> PATTERN_MERMAID;
   public static final RegistryObject<BannerPatternItem> PATTERN_SEA_SERPENT;
   public static final RegistryObject<BannerPatternItem> PATTERN_TROLL;
   public static final RegistryObject<BannerPatternItem> PATTERN_WEEZER;
   public static final RegistryObject<BannerPatternItem> PATTERN_DREAD;

   public static Properties defaultBuilder() {
      return new Properties();
   }

   public static Properties unstackable() {
      return defaultBuilder().m_41487_(1);
   }

   @SubscribeEvent(
      priority = EventPriority.HIGH
   )
   public static void registerItems(NewRegistryEvent event) {
      registerItem("spawn_egg_fire_dragon", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.FIRE_DRAGON, 3407872, 10823977, new Properties());
      });
      registerItem("spawn_egg_ice_dragon", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.ICE_DRAGON, 11918843, 8305392, new Properties());
      });
      registerItem("spawn_egg_lightning_dragon", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.LIGHTNING_DRAGON, 4334439, 7493265, new Properties());
      });
      registerItem("spawn_egg_hippogryph", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.HIPPOGRYPH, 14211288, 13743453, new Properties());
      });
      registerItem("spawn_egg_gorgon", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.GORGON, 13687199, 6833456, new Properties());
      });
      registerItem("spawn_egg_pixie", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.PIXIE, 16744329, 14863586, new Properties());
      });
      registerItem("spawn_egg_cyclops", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.CYCLOPS, 11567726, 3809039, new Properties());
      });
      registerItem("spawn_egg_siren", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.SIREN, 9365194, 15917000, new Properties());
      });
      registerItem("spawn_egg_hippocampus", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.HIPPOCAMPUS, 4493767, 5227883, new Properties());
      });
      registerItem("spawn_egg_death_worm", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DEATH_WORM, 13749667, 4340282, new Properties());
      });
      registerItem("spawn_egg_cockatrice", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.COCKATRICE, 9392133, 5200419, new Properties());
      });
      registerItem("spawn_egg_stymphalian_bird", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.STYMPHALIAN_BIRD, 7622455, 10382411, new Properties());
      });
      registerItem("spawn_egg_troll", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.TROLL, 4014397, 5784378, new Properties());
      });
      registerItem("spawn_egg_myrmex_worker", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.MYRMEX_WORKER, 10575910, 5850400, new Properties());
      });
      registerItem("spawn_egg_myrmex_soldier", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.MYRMEX_SOLDIER, 10575910, 8217133, new Properties());
      });
      registerItem("spawn_egg_myrmex_sentinel", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.MYRMEX_SENTINEL, 10575910, 10649402, new Properties());
      });
      registerItem("spawn_egg_myrmex_royal", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.MYRMEX_ROYAL, 10575910, 13081416, new Properties());
      });
      registerItem("spawn_egg_myrmex_queen", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.MYRMEX_QUEEN, 10575910, 15513685, new Properties());
      });
      registerItem("spawn_egg_amphithere", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.AMPHITHERE, 5862709, 43672, new Properties());
      });
      registerItem("spawn_egg_sea_serpent", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.SEA_SERPENT, 33433, 12969703, new Properties());
      });
      registerItem("spawn_egg_dread_thrall", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_THRALL, 14739174, 65535, new Properties());
      });
      registerItem("spawn_egg_dread_ghoul", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_GHOUL, 14739174, 8094602, new Properties());
      });
      registerItem("spawn_egg_dread_beast", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_BEAST, 14739174, 3684156, new Properties());
      });
      registerItem("spawn_egg_dread_scuttler", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_SCUTTLER, 14739174, 5068391, new Properties());
      });
      registerItem("spawn_egg_lich", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_LICH, 14739174, 2574432, new Properties());
      });
      registerItem("spawn_egg_dread_knight", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_KNIGHT, 14739174, 4877422, new Properties());
      });
      registerItem("spawn_egg_dread_horse", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.DREAD_HORSE, 14739174, 11316396, new Properties());
      });
      registerItem("spawn_egg_hydra", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.HYDRA, 9145208, 3028779, new Properties());
      });
      registerItem("spawn_egg_ghost", () -> {
         return new ForgeSpawnEggItem(IafEntityRegistry.GHOST, 12185016, 7582326, new Properties());
      });
   }

   public static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<I> item) {
      return registerItem(name, item, true);
   }

   public static <I extends Item> RegistryObject<I> registerItem(String name, Supplier<I> item, boolean putInTab) {
      RegistryObject<I> itemRegistryObject = ITEMS.register(name, item);
      if (putInTab) {
         IafTabRegistry.TAB_ITEMS_LIST.add(itemRegistryObject);
      }

      return itemRegistryObject;
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public static void setRepairMaterials(RegisterEvent event) {
      if (event.getRegistryKey() == Registries.f_256913_) {
         BLINDFOLD_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_204132_(Items.STRING));
         SILVER_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_204132_(IafItemTags.INGOTS_SILVER));
         SILVER_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_204132_(IafItemTags.INGOTS_SILVER));
         DRAGONBONE_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGON_BONE.get()}));
         FIRE_DRAGONBONE_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGON_BONE.get()}));
         ICE_DRAGONBONE_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGON_BONE.get()}));
         LIGHTNING_DRAGONBONE_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGON_BONE.get()}));
         EnumDragonArmor[] var1 = EnumDragonArmor.values();
         int var2 = var1.length;

         int var3;
         for(var3 = 0; var3 < var2; ++var3) {
            EnumDragonArmor armor = var1[var3];
            armor.armorMaterial.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{EnumDragonArmor.getScaleItem(armor)}));
         }

         DRAGONSTEEL_FIRE_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGONSTEEL_FIRE_INGOT.get()}));
         DRAGONSTEEL_ICE_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGONSTEEL_ICE_INGOT.get()}));
         DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DRAGONSTEEL_LIGHTNING_INGOT.get()}));
         SHEEP_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{Blocks.f_50041_}));
         EARPLUGS_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{Blocks.f_50251_}));
         DEATHWORM_0_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DEATH_WORM_CHITIN_YELLOW.get()}));
         DEATHWORM_1_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DEATH_WORM_CHITIN_RED.get()}));
         DEATHWORM_2_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DEATH_WORM_CHITIN_WHITE.get()}));
         TROLL_WEAPON_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_204132_(Items.STONE));
         TROLL_MOUNTAIN_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)EnumTroll.MOUNTAIN.leather.get()}));
         TROLL_FOREST_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)EnumTroll.FOREST.leather.get()}));
         TROLL_FROST_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)EnumTroll.FROST.leather.get()}));
         HIPPOGRYPH_SWORD_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)HIPPOGRYPH_TALON.get()}));
         HIPPOCAMPUS_SWORD_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)SHINY_SCALES.get()}));
         AMPHITHERE_SWORD_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)AMPHITHERE_FEATHER.get()}));
         STYMHALIAN_SWORD_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)STYMPHALIAN_BIRD_FEATHER.get()}));
         MYRMEX_CHITIN_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)MYRMEX_DESERT_CHITIN.get()}));
         MYRMEX_DESERT_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)MYRMEX_DESERT_CHITIN.get()}));
         MYRMEX_JUNGLE_ARMOR_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)MYRMEX_JUNGLE_CHITIN.get()}));
         DREAD_SWORD_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DREAD_SHARD.get()}));
         DREAD_KNIGHT_TOOL_MATERIAL.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)DREAD_SHARD.get()}));
         EnumSeaSerpent[] var5 = EnumSeaSerpent.values();
         var2 = var5.length;

         for(var3 = 0; var3 < var2; ++var3) {
            EnumSeaSerpent serpent = var5[var3];
            serpent.armorMaterial.setRepairMaterial(Ingredient.m_43929_(new ItemLike[]{(ItemLike)serpent.scale.get()}));
         }

      }
   }

   static {
      SILVER_ARMOR_MATERIAL = new IafArmorMaterial("silver", 15, new int[]{1, 4, 5, 2}, 20, SoundEvents.f_11672_, 0.0F);
      COPPER_ARMOR_MATERIAL = new IafArmorMaterial("copper", 10, new int[]{1, 3, 4, 2}, 15, SoundEvents.f_11676_, 0.0F);
      BLINDFOLD_ARMOR_MATERIAL = new IafArmorMaterial("blindfold", 5, new int[]{1, 1, 1, 1}, 10, SoundEvents.f_11678_, 0.0F);
      SHEEP_ARMOR_MATERIAL = new IafArmorMaterial("sheep", 5, new int[]{1, 3, 2, 1}, 15, SoundEvents.f_11678_, 0.0F);
      MYRMEX_DESERT_ARMOR_MATERIAL = new IafArmorMaterial("myrmexdesert", 20, new int[]{3, 5, 8, 4}, 15, SoundEvents.f_11678_, 0.0F);
      MYRMEX_JUNGLE_ARMOR_MATERIAL = new IafArmorMaterial("myrmexjungle", 20, new int[]{3, 5, 8, 4}, 15, SoundEvents.f_11678_, 0.0F);
      EARPLUGS_ARMOR_MATERIAL = new IafArmorMaterial("earplugs", 5, new int[]{1, 1, 1, 1}, 10, SoundEvents.f_11678_, 0.0F);
      DEATHWORM_0_ARMOR_MATERIAL = new IafArmorMaterial("yellow seathworm", 15, new int[]{2, 5, 7, 3}, 5, SoundEvents.f_11678_, 1.5F);
      DEATHWORM_1_ARMOR_MATERIAL = new IafArmorMaterial("white seathworm", 15, new int[]{2, 5, 7, 3}, 5, SoundEvents.f_11678_, 1.5F);
      DEATHWORM_2_ARMOR_MATERIAL = new IafArmorMaterial("red deathworm", 15, new int[]{2, 5, 7, 3}, 5, SoundEvents.f_11678_, 1.5F);
      TROLL_MOUNTAIN_ARMOR_MATERIAL = new IafArmorMaterial("mountain troll", 20, new int[]{2, 5, 7, 3}, 10, SoundEvents.f_11678_, 1.0F);
      TROLL_FOREST_ARMOR_MATERIAL = new IafArmorMaterial("forest troll", 20, new int[]{2, 5, 7, 3}, 10, SoundEvents.f_11678_, 1.0F);
      TROLL_FROST_ARMOR_MATERIAL = new IafArmorMaterial("frost troll", 20, new int[]{2, 5, 7, 3}, 10, SoundEvents.f_11678_, 1.0F);
      DRAGONSTEEL_FIRE_ARMOR_MATERIAL = new DragonsteelArmorMaterial("dragonsteel_fire", (int)(0.02D * (double)IafConfig.dragonsteelBaseDurabilityEquipment), new int[]{IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor, IafConfig.dragonsteelBaseArmor - 5}, 30, SoundEvents.f_11673_, IafConfig.dragonsteelBaseArmorToughness);
      DRAGONSTEEL_ICE_ARMOR_MATERIAL = new DragonsteelArmorMaterial("dragonsteel_ice", (int)(0.02D * (double)IafConfig.dragonsteelBaseDurabilityEquipment), new int[]{IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor, IafConfig.dragonsteelBaseArmor - 5}, 30, SoundEvents.f_11673_, IafConfig.dragonsteelBaseArmorToughness);
      DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL = new DragonsteelArmorMaterial("dragonsteel_lightning", (int)(0.02D * (double)IafConfig.dragonsteelBaseDurabilityEquipment), new int[]{IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor, IafConfig.dragonsteelBaseArmor - 5}, 30, SoundEvents.f_11673_, IafConfig.dragonsteelBaseArmorToughness);
      SILVER_TOOL_MATERIAL = new CustomToolMaterial("silver", 2, 460, 1.0F, 11.0F, 18);
      COPPER_TOOL_MATERIAL = new CustomToolMaterial("copper", 2, 300, 0.0F, 0.7F, 10);
      DRAGONBONE_TOOL_MATERIAL = new CustomToolMaterial("Dragonbone", 3, 1660, 4.0F, 10.0F, 22);
      FIRE_DRAGONBONE_TOOL_MATERIAL = new CustomToolMaterial("FireDragonbone", 3, 2000, 5.5F, 10.0F, 22);
      ICE_DRAGONBONE_TOOL_MATERIAL = new CustomToolMaterial("IceDragonbone", 3, 2000, 5.5F, 10.0F, 22);
      LIGHTNING_DRAGONBONE_TOOL_MATERIAL = new CustomToolMaterial("LightningDragonbone", 3, 2000, 5.5F, 10.0F, 22);
      TROLL_WEAPON_TOOL_MATERIAL = new CustomToolMaterial("trollWeapon", 2, 300, 1.0F, 10.0F, 1);
      MYRMEX_CHITIN_TOOL_MATERIAL = new CustomToolMaterial("MyrmexChitin", 3, 600, 1.0F, 6.0F, 8);
      HIPPOGRYPH_SWORD_TOOL_MATERIAL = new CustomToolMaterial("HippogryphSword", 2, 500, 2.5F, 10.0F, 10);
      STYMHALIAN_SWORD_TOOL_MATERIAL = new CustomToolMaterial("StymphalianSword", 2, 500, 2.0F, 10.0F, 10);
      AMPHITHERE_SWORD_TOOL_MATERIAL = new CustomToolMaterial("AmphithereSword", 2, 500, 1.0F, 10.0F, 10);
      HIPPOCAMPUS_SWORD_TOOL_MATERIAL = new CustomToolMaterial("HippocampusSword", 0, 500, -2.0F, 0.0F, 50);
      DREAD_SWORD_TOOL_MATERIAL = new CustomToolMaterial("DreadSword", 0, 100, 1.0F, 10.0F, 0);
      DREAD_KNIGHT_TOOL_MATERIAL = new CustomToolMaterial("DreadKnightSword", 0, 1200, 13.0F, 0.0F, 10);
      GHOST_SWORD_TOOL_MATERIAL = new CustomToolMaterial("GhostSword", 2, 3000, 5.0F, 10.0F, 25);
      ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "iceandfire");
      BESTIARY = registerItem("bestiary", ItemBestiary::new);
      MANUSCRIPT = registerItem("manuscript", ItemGeneric::new);
      SAPPHIRE_GEM = registerItem("sapphire_gem", ItemGeneric::new);
      SILVER_INGOT = registerItem("silver_ingot", ItemGeneric::new);
      SILVER_NUGGET = registerItem("silver_nugget", ItemGeneric::new);
      RAW_SILVER = registerItem("raw_silver", ItemGeneric::new);
      COPPER_NUGGET = registerItem("copper_nugget", ItemGeneric::new);
      SILVER_HELMET = registerItem("armor_silver_metal_helmet", () -> {
         return new ItemSilverArmor(SILVER_ARMOR_MATERIAL, Type.HELMET);
      });
      SILVER_CHESTPLATE = registerItem("armor_silver_metal_chestplate", () -> {
         return new ItemSilverArmor(SILVER_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      SILVER_LEGGINGS = registerItem("armor_silver_metal_leggings", () -> {
         return new ItemSilverArmor(SILVER_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      SILVER_BOOTS = registerItem("armor_silver_metal_boots", () -> {
         return new ItemSilverArmor(SILVER_ARMOR_MATERIAL, Type.BOOTS);
      });
      SILVER_SWORD = registerItem("silver_sword", () -> {
         return new ItemModSword(SILVER_TOOL_MATERIAL);
      });
      SILVER_SHOVEL = registerItem("silver_shovel", () -> {
         return new ItemModShovel(SILVER_TOOL_MATERIAL);
      });
      SILVER_PICKAXE = registerItem("silver_pickaxe", () -> {
         return new ItemModPickaxe(SILVER_TOOL_MATERIAL);
      });
      SILVER_AXE = registerItem("silver_axe", () -> {
         return new ItemModAxe(SILVER_TOOL_MATERIAL);
      });
      SILVER_HOE = registerItem("silver_hoe", () -> {
         return new ItemModHoe(SILVER_TOOL_MATERIAL);
      });
      COPPER_HELMET = registerItem("armor_copper_metal_helmet", () -> {
         return new ItemCopperArmor(COPPER_ARMOR_MATERIAL, Type.HELMET);
      });
      COPPER_CHESTPLATE = registerItem("armor_copper_metal_chestplate", () -> {
         return new ItemCopperArmor(COPPER_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      COPPER_LEGGINGS = registerItem("armor_copper_metal_leggings", () -> {
         return new ItemCopperArmor(COPPER_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      COPPER_BOOTS = registerItem("armor_copper_metal_boots", () -> {
         return new ItemCopperArmor(COPPER_ARMOR_MATERIAL, Type.BOOTS);
      });
      COPPER_SWORD = registerItem("copper_sword", () -> {
         return new ItemModSword(COPPER_TOOL_MATERIAL);
      });
      COPPER_SHOVEL = registerItem("copper_shovel", () -> {
         return new ItemModShovel(COPPER_TOOL_MATERIAL);
      });
      COPPER_PICKAXE = registerItem("copper_pickaxe", () -> {
         return new ItemModPickaxe(COPPER_TOOL_MATERIAL);
      });
      COPPER_AXE = registerItem("copper_axe", () -> {
         return new ItemModAxe(COPPER_TOOL_MATERIAL);
      });
      COPPER_HOE = registerItem("copper_hoe", () -> {
         return new ItemModHoe(COPPER_TOOL_MATERIAL);
      });
      FIRE_STEW = registerItem("fire_stew", ItemGeneric::new);
      FROST_STEW = registerItem("frost_stew", ItemGeneric::new);
      LIGHTNING_STEW = registerItem("lightning_stew", ItemGeneric::new);
      DRAGONEGG_RED = registerItem("dragonegg_red", () -> {
         return new ItemDragonEgg(EnumDragonEgg.RED);
      });
      DRAGONEGG_GREEN = registerItem("dragonegg_green", () -> {
         return new ItemDragonEgg(EnumDragonEgg.GREEN);
      });
      DRAGONEGG_BRONZE = registerItem("dragonegg_bronze", () -> {
         return new ItemDragonEgg(EnumDragonEgg.BRONZE);
      });
      DRAGONEGG_GRAY = registerItem("dragonegg_gray", () -> {
         return new ItemDragonEgg(EnumDragonEgg.GRAY);
      });
      DRAGONEGG_BLUE = registerItem("dragonegg_blue", () -> {
         return new ItemDragonEgg(EnumDragonEgg.BLUE);
      });
      DRAGONEGG_WHITE = registerItem("dragonegg_white", () -> {
         return new ItemDragonEgg(EnumDragonEgg.WHITE);
      });
      DRAGONEGG_SAPPHIRE = registerItem("dragonegg_sapphire", () -> {
         return new ItemDragonEgg(EnumDragonEgg.SAPPHIRE);
      });
      DRAGONEGG_SILVER = registerItem("dragonegg_silver", () -> {
         return new ItemDragonEgg(EnumDragonEgg.SILVER);
      });
      DRAGONEGG_ELECTRIC = registerItem("dragonegg_electric", () -> {
         return new ItemDragonEgg(EnumDragonEgg.ELECTRIC);
      });
      DRAGONEGG_AMYTHEST = registerItem("dragonegg_amythest", () -> {
         return new ItemDragonEgg(EnumDragonEgg.AMYTHEST);
      });
      DRAGONEGG_COPPER = registerItem("dragonegg_copper", () -> {
         return new ItemDragonEgg(EnumDragonEgg.COPPER);
      });
      DRAGONEGG_BLACK = registerItem("dragonegg_black", () -> {
         return new ItemDragonEgg(EnumDragonEgg.BLACK);
      });
      DRAGONSCALES_RED = registerItem("dragonscales_red", () -> {
         return new ItemDragonScales(EnumDragonEgg.RED);
      });
      DRAGONSCALES_GREEN = registerItem("dragonscales_green", () -> {
         return new ItemDragonScales(EnumDragonEgg.GREEN);
      });
      DRAGONSCALES_BRONZE = registerItem("dragonscales_bronze", () -> {
         return new ItemDragonScales(EnumDragonEgg.BRONZE);
      });
      DRAGONSCALES_GRAY = registerItem("dragonscales_gray", () -> {
         return new ItemDragonScales(EnumDragonEgg.GRAY);
      });
      DRAGONSCALES_BLUE = registerItem("dragonscales_blue", () -> {
         return new ItemDragonScales(EnumDragonEgg.BLUE);
      });
      DRAGONSCALES_WHITE = registerItem("dragonscales_white", () -> {
         return new ItemDragonScales(EnumDragonEgg.WHITE);
      });
      DRAGONSCALES_SAPPHIRE = registerItem("dragonscales_sapphire", () -> {
         return new ItemDragonScales(EnumDragonEgg.SAPPHIRE);
      });
      DRAGONSCALES_SILVER = registerItem("dragonscales_silver", () -> {
         return new ItemDragonScales(EnumDragonEgg.SILVER);
      });
      DRAGONSCALES_ELECTRIC = registerItem("dragonscales_electric", () -> {
         return new ItemDragonScales(EnumDragonEgg.ELECTRIC);
      });
      DRAGONSCALES_AMYTHEST = registerItem("dragonscales_amythest", () -> {
         return new ItemDragonScales(EnumDragonEgg.AMYTHEST);
      });
      DRAGONSCALES_COPPER = registerItem("dragonscales_copper", () -> {
         return new ItemDragonScales(EnumDragonEgg.COPPER);
      });
      DRAGONSCALES_BLACK = registerItem("dragonscales_black", () -> {
         return new ItemDragonScales(EnumDragonEgg.BLACK);
      });
      DRAGON_BONE = registerItem("dragonbone", () -> {
         return new ItemDragonBone();
      });
      WITHERBONE = registerItem("witherbone", ItemGeneric::new);
      FISHING_SPEAR = registerItem("fishing_spear", () -> {
         return new ItemFishingSpear();
      });
      WITHER_SHARD = registerItem("wither_shard", ItemGeneric::new);
      DRAGONBONE_SWORD = registerItem("dragonbone_sword", () -> {
         return new ItemModSword(DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_SHOVEL = registerItem("dragonbone_shovel", () -> {
         return new ItemModShovel(DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_PICKAXE = registerItem("dragonbone_pickaxe", () -> {
         return new ItemModPickaxe(DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_AXE = registerItem("dragonbone_axe", () -> {
         return new ItemModAxe(DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_HOE = registerItem("dragonbone_hoe", () -> {
         return new ItemModHoe(DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_SWORD_FIRE = registerItem("dragonbone_sword_fire", () -> {
         return new ItemAlchemySword(FIRE_DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_SWORD_ICE = registerItem("dragonbone_sword_ice", () -> {
         return new ItemAlchemySword(ICE_DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_SWORD_LIGHTNING = registerItem("dragonbone_sword_lightning", () -> {
         return new ItemAlchemySword(LIGHTNING_DRAGONBONE_TOOL_MATERIAL);
      });
      DRAGONBONE_ARROW = registerItem("dragonbone_arrow", () -> {
         return new ItemDragonArrow();
      });
      DRAGON_BOW = registerItem("dragonbone_bow", () -> {
         return new ItemDragonBow();
      });
      DRAGON_SKULL_FIRE = registerItem(ItemDragonSkull.getName(0), () -> {
         return new ItemDragonSkull(0);
      });
      DRAGON_SKULL_ICE = registerItem(ItemDragonSkull.getName(1), () -> {
         return new ItemDragonSkull(1);
      });
      DRAGON_SKULL_LIGHTNING = registerItem(ItemDragonSkull.getName(2), () -> {
         return new ItemDragonSkull(2);
      });
      DRAGONARMOR_IRON_0 = registerItem("dragonarmor_iron_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.IRON, 0);
      });
      DRAGONARMOR_IRON_1 = registerItem("dragonarmor_iron_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.IRON, 1);
      });
      DRAGONARMOR_IRON_2 = registerItem("dragonarmor_iron_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.IRON, 2);
      });
      DRAGONARMOR_IRON_3 = registerItem("dragonarmor_iron_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.IRON, 3);
      });
      DRAGONARMOR_COPPER_0 = registerItem("dragonarmor_copper_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.COPPER, 0);
      });
      DRAGONARMOR_COPPER_1 = registerItem("dragonarmor_copper_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.COPPER, 1);
      });
      DRAGONARMOR_COPPER_2 = registerItem("dragonarmor_copper_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.COPPER, 2);
      });
      DRAGONARMOR_COPPER_3 = registerItem("dragonarmor_copper_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.COPPER, 3);
      });
      DRAGONARMOR_GOLD_0 = registerItem("dragonarmor_gold_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.GOLD, 0);
      });
      DRAGONARMOR_GOLD_1 = registerItem("dragonarmor_gold_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.GOLD, 1);
      });
      DRAGONARMOR_GOLD_2 = registerItem("dragonarmor_gold_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.GOLD, 2);
      });
      DRAGONARMOR_GOLD_3 = registerItem("dragonarmor_gold_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.GOLD, 3);
      });
      DRAGONARMOR_DIAMOND_0 = registerItem("dragonarmor_diamond_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.DIAMOND, 0);
      });
      DRAGONARMOR_DIAMOND_1 = registerItem("dragonarmor_diamond_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.DIAMOND, 1);
      });
      DRAGONARMOR_DIAMOND_2 = registerItem("dragonarmor_diamond_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.DIAMOND, 2);
      });
      DRAGONARMOR_DIAMOND_3 = registerItem("dragonarmor_diamond_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.DIAMOND, 3);
      });
      DRAGONARMOR_SILVER_0 = registerItem("dragonarmor_silver_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.SILVER, 0);
      });
      DRAGONARMOR_SILVER_1 = registerItem("dragonarmor_silver_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.SILVER, 1);
      });
      DRAGONARMOR_SILVER_2 = registerItem("dragonarmor_silver_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.SILVER, 2);
      });
      DRAGONARMOR_SILVER_3 = registerItem("dragonarmor_silver_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.SILVER, 3);
      });
      DRAGONARMOR_DRAGONSTEEL_FIRE_0 = registerItem("dragonarmor_dragonsteel_fire_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.FIRE, 0);
      });
      DRAGONARMOR_DRAGONSTEEL_FIRE_1 = registerItem("dragonarmor_dragonsteel_fire_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.FIRE, 1);
      });
      DRAGONARMOR_DRAGONSTEEL_FIRE_2 = registerItem("dragonarmor_dragonsteel_fire_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.FIRE, 2);
      });
      DRAGONARMOR_DRAGONSTEEL_FIRE_3 = registerItem("dragonarmor_dragonsteel_fire_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.FIRE, 3);
      });
      DRAGONARMOR_DRAGONSTEEL_ICE_0 = registerItem("dragonarmor_dragonsteel_ice_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.ICE, 0);
      });
      DRAGONARMOR_DRAGONSTEEL_ICE_1 = registerItem("dragonarmor_dragonsteel_ice_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.ICE, 1);
      });
      DRAGONARMOR_DRAGONSTEEL_ICE_2 = registerItem("dragonarmor_dragonsteel_ice_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.ICE, 2);
      });
      DRAGONARMOR_DRAGONSTEEL_ICE_3 = registerItem("dragonarmor_dragonsteel_ice_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.ICE, 3);
      });
      DRAGONARMOR_DRAGONSTEEL_LIGHTNING_0 = registerItem("dragonarmor_dragonsteel_lightning_" + ItemDragonArmor.getNameForSlot(0), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.LIGHTNING, 0);
      });
      DRAGONARMOR_DRAGONSTEEL_LIGHTNING_1 = registerItem("dragonarmor_dragonsteel_lightning_" + ItemDragonArmor.getNameForSlot(1), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.LIGHTNING, 1);
      });
      DRAGONARMOR_DRAGONSTEEL_LIGHTNING_2 = registerItem("dragonarmor_dragonsteel_lightning_" + ItemDragonArmor.getNameForSlot(2), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.LIGHTNING, 2);
      });
      DRAGONARMOR_DRAGONSTEEL_LIGHTNING_3 = registerItem("dragonarmor_dragonsteel_lightning_" + ItemDragonArmor.getNameForSlot(3), () -> {
         return new ItemDragonArmor(ItemDragonArmor.DragonArmorType.LIGHTNING, 3);
      });
      DRAGON_MEAL = registerItem("dragon_meal", ItemGeneric::new);
      SICKLY_DRAGON_MEAL = registerItem("sickly_dragon_meal", () -> {
         return new ItemGeneric(1);
      });
      CREATIVE_DRAGON_MEAL = registerItem("creative_dragon_meal", () -> {
         return new ItemGeneric(2);
      });
      FIRE_DRAGON_FLESH = registerItem(ItemDragonFlesh.getNameForType(0), () -> {
         return new ItemDragonFlesh(0);
      });
      ICE_DRAGON_FLESH = registerItem(ItemDragonFlesh.getNameForType(1), () -> {
         return new ItemDragonFlesh(1);
      });
      LIGHTNING_DRAGON_FLESH = registerItem(ItemDragonFlesh.getNameForType(2), () -> {
         return new ItemDragonFlesh(2);
      });
      FIRE_DRAGON_HEART = registerItem("fire_dragon_heart", ItemGeneric::new);
      ICE_DRAGON_HEART = registerItem("ice_dragon_heart", ItemGeneric::new);
      LIGHTNING_DRAGON_HEART = registerItem("lightning_dragon_heart", ItemGeneric::new);
      FIRE_DRAGON_BLOOD = registerItem("fire_dragon_blood", ItemGeneric::new);
      ICE_DRAGON_BLOOD = registerItem("ice_dragon_blood", ItemGeneric::new);
      LIGHTNING_DRAGON_BLOOD = registerItem("lightning_dragon_blood", ItemGeneric::new);
      DRAGON_STAFF = registerItem("dragon_stick", () -> {
         return new ItemDragonStaff();
      });
      DRAGON_HORN = registerItem("dragon_horn", () -> {
         return new ItemDragonHorn();
      });
      DRAGON_FLUTE = registerItem("dragon_flute", () -> {
         return new ItemDragonFlute();
      });
      SUMMONING_CRYSTAL_FIRE = registerItem("summoning_crystal_fire", () -> {
         return new ItemSummoningCrystal();
      });
      SUMMONING_CRYSTAL_ICE = registerItem("summoning_crystal_ice", () -> {
         return new ItemSummoningCrystal();
      });
      SUMMONING_CRYSTAL_LIGHTNING = registerItem("summoning_crystal_lightning", () -> {
         return new ItemSummoningCrystal();
      });
      HIPPOGRYPH_EGG = registerItem("hippogryph_egg", () -> {
         return new ItemHippogryphEgg();
      });
      IRON_HIPPOGRYPH_ARMOR = registerItem("iron_hippogryph_armor", () -> {
         return new ItemGeneric(0, 1);
      });
      GOLD_HIPPOGRYPH_ARMOR = registerItem("gold_hippogryph_armor", () -> {
         return new ItemGeneric(0, 1);
      });
      DIAMOND_HIPPOGRYPH_ARMOR = registerItem("diamond_hippogryph_armor", () -> {
         return new ItemGeneric(0, 1);
      });
      HIPPOGRYPH_TALON = registerItem("hippogryph_talon", () -> {
         return new ItemGeneric(1);
      });
      HIPPOGRYPH_SWORD = registerItem("hippogryph_sword", () -> {
         return new ItemHippogryphSword();
      });
      GORGON_HEAD = registerItem("gorgon_head", () -> {
         return new ItemGorgonHead();
      });
      STONE_STATUE = registerItem("stone_statue", () -> {
         return new ItemStoneStatue();
      });
      BLINDFOLD = registerItem("blindfold", () -> {
         return new ItemBlindfold();
      });
      PIXIE_DUST = registerItem("pixie_dust", () -> {
         return new ItemPixieDust();
      });
      PIXIE_WINGS = registerItem("pixie_wings", () -> {
         return new ItemGeneric(1);
      });
      PIXIE_WAND = registerItem("pixie_wand", () -> {
         return new ItemPixieWand();
      });
      AMBROSIA = registerItem("ambrosia", () -> {
         return new ItemAmbrosia();
      });
      CYCLOPS_EYE = registerItem("cyclops_eye", () -> {
         return new ItemCyclopsEye();
      });
      SHEEP_HELMET = registerItem("sheep_helmet", () -> {
         return new ItemModArmor(SHEEP_ARMOR_MATERIAL, Type.HELMET);
      });
      SHEEP_CHESTPLATE = registerItem("sheep_chestplate", () -> {
         return new ItemModArmor(SHEEP_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      SHEEP_LEGGINGS = registerItem("sheep_leggings", () -> {
         return new ItemModArmor(SHEEP_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      SHEEP_BOOTS = registerItem("sheep_boots", () -> {
         return new ItemModArmor(SHEEP_ARMOR_MATERIAL, Type.BOOTS);
      });
      SHINY_SCALES = registerItem("shiny_scales", ItemGeneric::new);
      SIREN_TEAR = registerItem("siren_tear", () -> {
         return new ItemGeneric(1);
      });
      SIREN_FLUTE = registerItem("siren_flute", () -> {
         return new ItemSirenFlute();
      });
      HIPPOCAMPUS_FIN = registerItem("hippocampus_fin", () -> {
         return new ItemGeneric(1);
      });
      HIPPOCAMPUS_SLAPPER = registerItem("hippocampus_slapper", () -> {
         return new ItemHippocampusSlapper();
      });
      EARPLUGS = registerItem("earplugs", () -> {
         return new ItemModArmor(EARPLUGS_ARMOR_MATERIAL, Type.HELMET);
      });
      DEATH_WORM_CHITIN_YELLOW = registerItem("deathworm_chitin_yellow", ItemGeneric::new);
      DEATH_WORM_CHITIN_WHITE = registerItem("deathworm_chitin_white", ItemGeneric::new);
      DEATH_WORM_CHITIN_RED = registerItem("deathworm_chitin_red", ItemGeneric::new);
      DEATHWORM_YELLOW_HELMET = registerItem("deathworm_yellow_helmet", () -> {
         return new ItemDeathwormArmor(DEATHWORM_0_ARMOR_MATERIAL, Type.HELMET);
      });
      DEATHWORM_YELLOW_CHESTPLATE = registerItem("deathworm_yellow_chestplate", () -> {
         return new ItemDeathwormArmor(DEATHWORM_0_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      DEATHWORM_YELLOW_LEGGINGS = registerItem("deathworm_yellow_leggings", () -> {
         return new ItemDeathwormArmor(DEATHWORM_0_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      DEATHWORM_YELLOW_BOOTS = registerItem("deathworm_yellow_boots", () -> {
         return new ItemDeathwormArmor(DEATHWORM_0_ARMOR_MATERIAL, Type.BOOTS);
      });
      DEATHWORM_WHITE_HELMET = registerItem("deathworm_white_helmet", () -> {
         return new ItemDeathwormArmor(DEATHWORM_1_ARMOR_MATERIAL, Type.HELMET);
      });
      DEATHWORM_WHITE_CHESTPLATE = registerItem("deathworm_white_chestplate", () -> {
         return new ItemDeathwormArmor(DEATHWORM_1_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      DEATHWORM_WHITE_LEGGINGS = registerItem("deathworm_white_leggings", () -> {
         return new ItemDeathwormArmor(DEATHWORM_1_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      DEATHWORM_WHITE_BOOTS = registerItem("deathworm_white_boots", () -> {
         return new ItemDeathwormArmor(DEATHWORM_1_ARMOR_MATERIAL, Type.BOOTS);
      });
      DEATHWORM_RED_HELMET = registerItem("deathworm_red_helmet", () -> {
         return new ItemDeathwormArmor(DEATHWORM_2_ARMOR_MATERIAL, Type.HELMET);
      });
      DEATHWORM_RED_CHESTPLATE = registerItem("deathworm_red_chestplate", () -> {
         return new ItemDeathwormArmor(DEATHWORM_2_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      DEATHWORM_RED_LEGGINGS = registerItem("deathworm_red_leggings", () -> {
         return new ItemDeathwormArmor(DEATHWORM_2_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      DEATHWORM_RED_BOOTS = registerItem("deathworm_red_boots", () -> {
         return new ItemDeathwormArmor(DEATHWORM_2_ARMOR_MATERIAL, Type.BOOTS);
      });
      DEATHWORM_EGG = registerItem("deathworm_egg", () -> {
         return new ItemDeathwormEgg(false);
      });
      DEATHWORM_EGG_GIGANTIC = registerItem("deathworm_egg_giant", () -> {
         return new ItemDeathwormEgg(true);
      });
      DEATHWORM_TOUNGE = registerItem("deathworm_tounge", () -> {
         return new ItemGeneric(1);
      });
      DEATHWORM_GAUNTLET_YELLOW = registerItem("deathworm_gauntlet_yellow", () -> {
         return new ItemDeathwormGauntlet();
      });
      DEATHWORM_GAUNTLET_WHITE = registerItem("deathworm_gauntlet_white", () -> {
         return new ItemDeathwormGauntlet();
      });
      DEATHWORM_GAUNTLET_RED = registerItem("deathworm_gauntlet_red", () -> {
         return new ItemDeathwormGauntlet();
      });
      ROTTEN_EGG = registerItem("rotten_egg", () -> {
         return new ItemRottenEgg();
      });
      COCKATRICE_EYE = registerItem("cockatrice_eye", () -> {
         return new ItemGeneric(1);
      });
      ITEM_COCKATRICE_SCEPTER = registerItem("cockatrice_scepter", () -> {
         return new ItemCockatriceScepter();
      });
      STYMPHALIAN_BIRD_FEATHER = registerItem("stymphalian_bird_feather", ItemGeneric::new);
      STYMPHALIAN_ARROW = registerItem("stymphalian_arrow", () -> {
         return new ItemStymphalianArrow();
      });
      STYMPHALIAN_FEATHER_BUNDLE = registerItem("stymphalian_feather_bundle", () -> {
         return new ItemStymphalianFeatherBundle();
      });
      STYMPHALIAN_DAGGER = registerItem("stymphalian_bird_dagger", () -> {
         return new ItemStymphalianDagger();
      });
      TROLL_TUSK = registerItem("troll_tusk", ItemGeneric::new);
      MYRMEX_DESERT_EGG = registerItem("myrmex_desert_egg", () -> {
         return new ItemMyrmexEgg(false);
      });
      MYRMEX_JUNGLE_EGG = registerItem("myrmex_jungle_egg", () -> {
         return new ItemMyrmexEgg(true);
      });
      MYRMEX_DESERT_RESIN = registerItem("myrmex_desert_resin", ItemGeneric::new);
      MYRMEX_JUNGLE_RESIN = registerItem("myrmex_jungle_resin", ItemGeneric::new);
      MYRMEX_DESERT_CHITIN = registerItem("myrmex_desert_chitin", ItemGeneric::new);
      MYRMEX_JUNGLE_CHITIN = registerItem("myrmex_jungle_chitin", ItemGeneric::new);
      MYRMEX_STINGER = registerItem("myrmex_stinger", ItemGeneric::new);
      MYRMEX_DESERT_SWORD = registerItem("myrmex_desert_sword", () -> {
         return new ItemModSword(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_DESERT_SWORD_VENOM = registerItem("myrmex_desert_sword_venom", () -> {
         return new ItemModSword(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_DESERT_SHOVEL = registerItem("myrmex_desert_shovel", () -> {
         return new ItemModShovel(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_DESERT_PICKAXE = registerItem("myrmex_desert_pickaxe", () -> {
         return new ItemModPickaxe(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_DESERT_AXE = registerItem("myrmex_desert_axe", () -> {
         return new ItemModAxe(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_DESERT_HOE = registerItem("myrmex_desert_hoe", () -> {
         return new ItemModHoe(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_JUNGLE_SWORD = registerItem("myrmex_jungle_sword", () -> {
         return new ItemModSword(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_JUNGLE_SWORD_VENOM = registerItem("myrmex_jungle_sword_venom", () -> {
         return new ItemModSword(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_JUNGLE_SHOVEL = registerItem("myrmex_jungle_shovel", () -> {
         return new ItemModShovel(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_JUNGLE_PICKAXE = registerItem("myrmex_jungle_pickaxe", () -> {
         return new ItemModPickaxe(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_JUNGLE_AXE = registerItem("myrmex_jungle_axe", () -> {
         return new ItemModAxe(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_JUNGLE_HOE = registerItem("myrmex_jungle_hoe", () -> {
         return new ItemModHoe(MYRMEX_CHITIN_TOOL_MATERIAL);
      });
      MYRMEX_DESERT_STAFF = registerItem("myrmex_desert_staff", () -> {
         return new ItemMyrmexStaff(false);
      });
      MYRMEX_JUNGLE_STAFF = registerItem("myrmex_jungle_staff", () -> {
         return new ItemMyrmexStaff(true);
      });
      MYRMEX_DESERT_HELMET = registerItem("myrmex_desert_helmet", () -> {
         return new ItemModArmor(MYRMEX_DESERT_ARMOR_MATERIAL, Type.HELMET);
      });
      MYRMEX_DESERT_CHESTPLATE = registerItem("myrmex_desert_chestplate", () -> {
         return new ItemModArmor(MYRMEX_DESERT_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      MYRMEX_DESERT_LEGGINGS = registerItem("myrmex_desert_leggings", () -> {
         return new ItemModArmor(MYRMEX_DESERT_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      MYRMEX_DESERT_BOOTS = registerItem("myrmex_desert_boots", () -> {
         return new ItemModArmor(MYRMEX_DESERT_ARMOR_MATERIAL, Type.BOOTS);
      });
      MYRMEX_JUNGLE_HELMET = registerItem("myrmex_jungle_helmet", () -> {
         return new ItemModArmor(MYRMEX_JUNGLE_ARMOR_MATERIAL, Type.HELMET);
      });
      MYRMEX_JUNGLE_CHESTPLATE = registerItem("myrmex_jungle_chestplate", () -> {
         return new ItemModArmor(MYRMEX_JUNGLE_ARMOR_MATERIAL, Type.CHESTPLATE);
      });
      MYRMEX_JUNGLE_LEGGINGS = registerItem("myrmex_jungle_leggings", () -> {
         return new ItemModArmor(MYRMEX_JUNGLE_ARMOR_MATERIAL, Type.LEGGINGS);
      });
      MYRMEX_JUNGLE_BOOTS = registerItem("myrmex_jungle_boots", () -> {
         return new ItemModArmor(MYRMEX_JUNGLE_ARMOR_MATERIAL, Type.BOOTS);
      });
      MYRMEX_DESERT_SWARM = registerItem("myrmex_desert_swarm", () -> {
         return new ItemMyrmexSwarm(false);
      });
      MYRMEX_JUNGLE_SWARM = registerItem("myrmex_jungle_swarm", () -> {
         return new ItemMyrmexSwarm(true);
      });
      AMPHITHERE_FEATHER = registerItem("amphithere_feather", ItemGeneric::new);
      AMPHITHERE_ARROW = registerItem("amphithere_arrow", () -> {
         return new ItemAmphithereArrow();
      });
      AMPHITHERE_MACUAHUITL = registerItem("amphithere_macuahuitl", () -> {
         return new ItemAmphithereMacuahuitl();
      });
      SERPENT_FANG = registerItem("sea_serpent_fang", ItemGeneric::new);
      SEA_SERPENT_ARROW = registerItem("sea_serpent_arrow", () -> {
         return new ItemSeaSerpentArrow();
      });
      TIDE_TRIDENT_INVENTORY = registerItem("tide_trident_inventory", () -> {
         return new ItemGeneric(0, true);
      });
      TIDE_TRIDENT = registerItem("tide_trident", () -> {
         return new ItemTideTrident();
      });
      CHAIN = registerItem("chain", () -> {
         return new ItemChain(false);
      });
      CHAIN_STICKY = registerItem("chain_sticky", () -> {
         return new ItemChain(true);
      });
      DRAGONSTEEL_FIRE_INGOT = registerItem("dragonsteel_fire_ingot", ItemGeneric::new);
      DRAGONSTEEL_FIRE_SWORD = registerItem("dragonsteel_fire_sword", () -> {
         return new ItemModSword(DragonSteelTier.DRAGONSTEEL_TIER_FIRE);
      });
      DRAGONSTEEL_FIRE_PICKAXE = registerItem("dragonsteel_fire_pickaxe", () -> {
         return new ItemModPickaxe(DragonSteelTier.DRAGONSTEEL_TIER_FIRE);
      });
      DRAGONSTEEL_FIRE_AXE = registerItem("dragonsteel_fire_axe", () -> {
         return new ItemModAxe(DragonSteelTier.DRAGONSTEEL_TIER_FIRE);
      });
      DRAGONSTEEL_FIRE_SHOVEL = registerItem("dragonsteel_fire_shovel", () -> {
         return new ItemModShovel(DragonSteelTier.DRAGONSTEEL_TIER_FIRE);
      });
      DRAGONSTEEL_FIRE_HOE = registerItem("dragonsteel_fire_hoe", () -> {
         return new ItemModHoe(DragonSteelTier.DRAGONSTEEL_TIER_FIRE);
      });
      DRAGONSTEEL_FIRE_HELMET = registerItem("dragonsteel_fire_helmet", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, 0, Type.HELMET);
      });
      DRAGONSTEEL_FIRE_CHESTPLATE = registerItem("dragonsteel_fire_chestplate", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, 1, Type.CHESTPLATE);
      });
      DRAGONSTEEL_FIRE_LEGGINGS = registerItem("dragonsteel_fire_leggings", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, 2, Type.LEGGINGS);
      });
      DRAGONSTEEL_FIRE_BOOTS = registerItem("dragonsteel_fire_boots", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_FIRE_ARMOR_MATERIAL, 3, Type.BOOTS);
      });
      DRAGONSTEEL_ICE_INGOT = registerItem("dragonsteel_ice_ingot", ItemGeneric::new);
      DRAGONSTEEL_ICE_SWORD = registerItem("dragonsteel_ice_sword", () -> {
         return new ItemModSword(DragonSteelTier.DRAGONSTEEL_TIER_ICE);
      });
      DRAGONSTEEL_ICE_PICKAXE = registerItem("dragonsteel_ice_pickaxe", () -> {
         return new ItemModPickaxe(DragonSteelTier.DRAGONSTEEL_TIER_ICE);
      });
      DRAGONSTEEL_ICE_AXE = registerItem("dragonsteel_ice_axe", () -> {
         return new ItemModAxe(DragonSteelTier.DRAGONSTEEL_TIER_ICE);
      });
      DRAGONSTEEL_ICE_SHOVEL = registerItem("dragonsteel_ice_shovel", () -> {
         return new ItemModShovel(DragonSteelTier.DRAGONSTEEL_TIER_ICE);
      });
      DRAGONSTEEL_ICE_HOE = registerItem("dragonsteel_ice_hoe", () -> {
         return new ItemModHoe(DragonSteelTier.DRAGONSTEEL_TIER_ICE);
      });
      DRAGONSTEEL_ICE_HELMET = registerItem("dragonsteel_ice_helmet", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_ICE_ARMOR_MATERIAL, 0, Type.HELMET);
      });
      DRAGONSTEEL_ICE_CHESTPLATE = registerItem("dragonsteel_ice_chestplate", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_ICE_ARMOR_MATERIAL, 1, Type.CHESTPLATE);
      });
      DRAGONSTEEL_ICE_LEGGINGS = registerItem("dragonsteel_ice_leggings", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_ICE_ARMOR_MATERIAL, 2, Type.LEGGINGS);
      });
      DRAGONSTEEL_ICE_BOOTS = registerItem("dragonsteel_ice_boots", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_ICE_ARMOR_MATERIAL, 3, Type.BOOTS);
      });
      DRAGONSTEEL_LIGHTNING_INGOT = registerItem("dragonsteel_lightning_ingot", ItemGeneric::new);
      DRAGONSTEEL_LIGHTNING_SWORD = registerItem("dragonsteel_lightning_sword", () -> {
         return new ItemModSword(DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING);
      });
      DRAGONSTEEL_LIGHTNING_PICKAXE = registerItem("dragonsteel_lightning_pickaxe", () -> {
         return new ItemModPickaxe(DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING);
      });
      DRAGONSTEEL_LIGHTNING_AXE = registerItem("dragonsteel_lightning_axe", () -> {
         return new ItemModAxe(DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING);
      });
      DRAGONSTEEL_LIGHTNING_SHOVEL = registerItem("dragonsteel_lightning_shovel", () -> {
         return new ItemModShovel(DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING);
      });
      DRAGONSTEEL_LIGHTNING_HOE = registerItem("dragonsteel_lightning_hoe", () -> {
         return new ItemModHoe(DragonSteelTier.DRAGONSTEEL_TIER_LIGHTNING);
      });
      DRAGONSTEEL_LIGHTNING_HELMET = registerItem("dragonsteel_lightning_helmet", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, 0, Type.HELMET);
      });
      DRAGONSTEEL_LIGHTNING_CHESTPLATE = registerItem("dragonsteel_lightning_chestplate", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, 1, Type.CHESTPLATE);
      });
      DRAGONSTEEL_LIGHTNING_LEGGINGS = registerItem("dragonsteel_lightning_leggings", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, 2, Type.LEGGINGS);
      });
      DRAGONSTEEL_LIGHTNING_BOOTS = registerItem("dragonsteel_lightning_boots", () -> {
         return new ItemDragonsteelArmor(DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL, 3, Type.BOOTS);
      });
      WEEZER_BLUE_ALBUM = registerItem("weezer_blue_album", () -> {
         return new ItemGeneric(1, true);
      });
      DRAGON_DEBUG_STICK = registerItem("dragon_debug_stick", () -> {
         return new ItemGeneric(1, true);
      }, false);
      DREAD_SWORD = registerItem("dread_sword", () -> {
         return new ItemModSword(DREAD_SWORD_TOOL_MATERIAL);
      });
      DREAD_KNIGHT_SWORD = registerItem("dread_knight_sword", () -> {
         return new ItemModSword(DREAD_KNIGHT_TOOL_MATERIAL);
      });
      LICH_STAFF = registerItem("lich_staff", () -> {
         return new ItemLichStaff();
      });
      DREAD_QUEEN_SWORD = registerItem("dread_queen_sword", () -> {
         return new ItemModSword(DragonSteelTier.DRAGONSTEEL_TIER_DREAD_QUEEN);
      });
      DREAD_QUEEN_STAFF = registerItem("dread_queen_staff", () -> {
         return new ItemDreadQueenStaff();
      });
      DREAD_SHARD = registerItem("dread_shard", () -> {
         return new ItemGeneric(0);
      });
      DREAD_KEY = registerItem("dread_key", () -> {
         return new ItemGeneric(0);
      });
      HYDRA_FANG = registerItem("hydra_fang", () -> {
         return new ItemGeneric(0);
      });
      HYDRA_HEART = registerItem("hydra_heart", () -> {
         return new ItemHydraHeart();
      });
      HYDRA_ARROW = registerItem("hydra_arrow", () -> {
         return new ItemHydraArrow();
      });
      CANNOLI = registerItem("cannoli", () -> {
         return new ItemCannoli();
      }, false);
      ECTOPLASM = registerItem("ectoplasm", ItemGeneric::new);
      GHOST_INGOT = registerItem("ghost_ingot", () -> {
         return new ItemGeneric(1);
      });
      GHOST_SWORD = registerItem("ghost_sword", () -> {
         return new ItemGhostSword();
      });
      PATTERN_FIRE = registerItem("banner_pattern_fire", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.FIRE_BANNER_PATTERN, unstackable());
      });
      PATTERN_ICE = registerItem("banner_pattern_ice", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.ICE_BANNER_PATTERN, unstackable());
      });
      PATTERN_LIGHTNING = registerItem("banner_pattern_lightning", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.LIGHTNING_BANNER_PATTERN, unstackable());
      });
      PATTERN_FIRE_HEAD = registerItem("banner_pattern_fire_head", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.FIRE_HEAD_BANNER_PATTERN, unstackable());
      });
      PATTERN_ICE_HEAD = registerItem("banner_pattern_ice_head", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.ICE_HEAD_BANNER_PATTERN, unstackable());
      });
      PATTERN_LIGHTNING_HEAD = registerItem("banner_pattern_lightning_head", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.LIGHTNING_HEAD_BANNER_PATTERN, unstackable());
      });
      PATTERN_AMPHITHERE = registerItem("banner_pattern_amphithere", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.AMPHITHERE_BANNER_PATTERN, unstackable());
      });
      PATTERN_BIRD = registerItem("banner_pattern_bird", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.BIRD_BANNER_PATTERN, unstackable());
      });
      PATTERN_EYE = registerItem("banner_pattern_eye", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.EYE_BANNER_PATTERN, unstackable());
      });
      PATTERN_FAE = registerItem("banner_pattern_fae", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.FAE_BANNER_PATTERN, unstackable());
      });
      PATTERN_FEATHER = registerItem("banner_pattern_feather", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.FEATHER_BANNER_PATTERN, unstackable());
      });
      PATTERN_GORGON = registerItem("banner_pattern_gorgon", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.GORGON_BANNER_PATTERN, unstackable());
      });
      PATTERN_HIPPOCAMPUS = registerItem("banner_pattern_hippocampus", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.HIPPOCAMPUS_BANNER_PATTERN, unstackable());
      });
      PATTERN_HIPPOGRYPH_HEAD = registerItem("banner_pattern_hippogryph_head", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.HIPPOGRYPH_HEAD_BANNER_PATTERN, unstackable());
      });
      PATTERN_MERMAID = registerItem("banner_pattern_mermaid", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.MERMAID_BANNER_PATTERN, unstackable());
      });
      PATTERN_SEA_SERPENT = registerItem("banner_pattern_sea_serpent", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.SEA_SERPENT_BANNER_PATTERN, unstackable());
      });
      PATTERN_TROLL = registerItem("banner_pattern_troll", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.TROLL_BANNER_PATTERN, unstackable());
      });
      PATTERN_WEEZER = registerItem("banner_pattern_weezer", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.WEEZER_BANNER_PATTERN, unstackable());
      });
      PATTERN_DREAD = registerItem("banner_pattern_dread", () -> {
         return new BannerPatternItem(BannerPatternTagGenerator.DREAD_BANNER_PATTERN, unstackable());
      });
      EnumDragonArmor.initArmors();
      EnumSeaSerpent.initArmors();
      EnumSkullType.initItems();
      EnumTroll.initArmors();
   }
}
