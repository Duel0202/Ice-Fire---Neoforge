package com.github.alexthe666.iceandfire.datagen.tags;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemMobSkull;
import com.github.alexthe666.iceandfire.item.ItemSeaSerpentScales;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider.TagLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.level.block.Block;
import net.neoforge.common.Tags.Items;
import net.neoforge.common.data.ExistingFileHelper;

public class IafItemTags extends ItemTagsProvider {
   private static final String STORAGE_BLOCK_PATH;
   private static final String INGOTS_PATH;
   private static final String NUGGETS_PATH;
   private static final String BONES_PATH;
   private static final String ORES_PATH;
   private static final String GEMS;
   public static TagKey<Item> CHARRED_BLOCKS;
   public static TagKey<Item> FROZEN_BLOCKS;
   public static TagKey<Item> CRACKLED_BLOCKS;
   public static TagKey<Item> DRAGON_SKULLS;
   public static TagKey<Item> MOB_SKULLS;
   public static TagKey<Item> SCALES_DRAGON_FIRE;
   public static TagKey<Item> SCALES_DRAGON_ICE;
   public static TagKey<Item> SCALES_DRAGON_LIGHTNING;
   public static TagKey<Item> SCALES_SEA_SERPENT;
   public static TagKey<Item> DRAGON_FOOD_MEAT;
   public static TagKey<Item> STORAGE_BLOCKS_SCALES_DRAGON_FIRE;
   public static TagKey<Item> STORAGE_BLOCKS_SCALES_DRAGON_ICE;
   public static TagKey<Item> STORAGE_BLOCKS_SCALES_DRAGON_LIGHTNING;
   public static TagKey<Item> STORAGE_BLOCKS_SILVER;
   public static TagKey<Item> GEMS_SAPPHIRE;
   public static TagKey<Item> INGOTS_SILVER;
   public static TagKey<Item> NUGGETS_COPPER;
   public static TagKey<Item> NUGGETS_SILVER;
   public static TagKey<Item> BONES_WITHER;
   public static TagKey<Item> MAKE_ITEM_DROPS_FIREIMMUNE;
   public static TagKey<Item> DRAGON_ARROWS;
   public static TagKey<Item> DRAGON_BLOODS;
   public static TagKey<Item> DRAGON_HEARTS;
   public static TagKey<Item> BREED_AMPITHERE;
   public static TagKey<Item> BREED_HIPPOCAMPUS;
   public static TagKey<Item> BREED_HIPPOGRYPH;
   public static TagKey<Item> HEAL_AMPITHERE;
   public static TagKey<Item> HEAL_COCKATRICE;
   public static TagKey<Item> HEAL_HIPPOCAMPUS;
   public static TagKey<Item> HEAL_PIXIE;
   public static TagKey<Item> TAME_HIPPOGRYPH;
   public static TagKey<Item> TAME_PIXIE;
   public static TagKey<Item> TEMPT_DRAGON;
   public static TagKey<Item> TEMPT_HIPPOCAMPUS;
   public static TagKey<Item> TEMPT_HIPPOGRYPH;

   public IafItemTags(PackOutput output, CompletableFuture<Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper helper) {
      super(output, lookupProvider, blockTags, "iceandfire", helper);
   }

   protected void m_6577_(Provider provider) {
      this.m_206421_(IafBlockTags.CHARRED_BLOCKS, CHARRED_BLOCKS);
      this.m_206421_(IafBlockTags.FROZEN_BLOCKS, FROZEN_BLOCKS);
      this.m_206421_(IafBlockTags.CRACKLED_BLOCKS, CRACKLED_BLOCKS);
      this.m_206424_(DRAGON_SKULLS).m_255245_((Item)IafItemRegistry.DRAGON_SKULL_FIRE.get()).m_255245_((Item)IafItemRegistry.DRAGON_SKULL_ICE.get()).m_255245_((Item)IafItemRegistry.DRAGON_SKULL_LIGHTNING.get());
      this.m_206424_(MOB_SKULLS).m_206428_(DRAGON_SKULLS);
      this.m_206424_(MAKE_ITEM_DROPS_FIREIMMUNE).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_LIGHTNING_SWORD.get()).m_255245_((Item)IafItemRegistry.DRAGONBONE_SWORD_LIGHTNING.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_LIGHTNING_PICKAXE.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_LIGHTNING_AXE.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_LIGHTNING_SHOVEL.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_LIGHTNING_HOE.get());
      this.m_206424_(DRAGON_ARROWS).m_255245_((Item)IafItemRegistry.DRAGONBONE_ARROW.get());
      this.m_206424_(DRAGON_BLOODS).m_255245_((Item)IafItemRegistry.FIRE_DRAGON_BLOOD.get()).m_255245_((Item)IafItemRegistry.ICE_DRAGON_BLOOD.get()).m_255245_((Item)IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get());
      this.m_206424_(Items.INGOTS).m_255245_((Item)IafItemRegistry.GHOST_INGOT.get()).m_255245_((Item)IafItemRegistry.SILVER_INGOT.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_ICE_INGOT.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get()).m_255245_((Item)IafItemRegistry.DRAGONSTEEL_LIGHTNING_INGOT.get());
      this.m_206424_(Items.NUGGETS).m_255245_((Item)IafItemRegistry.COPPER_NUGGET.get()).m_255245_((Item)IafItemRegistry.SILVER_NUGGET.get());
      this.m_206424_(Items.ORES).m_255245_(((Block)IafBlockRegistry.SILVER_ORE.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.SAPPHIRE_ORE.get()).m_5456_());
      this.m_206424_(Items.GEMS).m_255245_((Item)IafItemRegistry.SAPPHIRE_GEM.get());
      this.m_206424_(Items.BONES).m_255245_((Item)IafItemRegistry.DRAGON_BONE.get()).m_255245_((Item)IafItemRegistry.WITHERBONE.get());
      this.m_206424_(Items.EGGS).m_255245_((Item)IafItemRegistry.HIPPOGRYPH_EGG.get()).m_255245_((Item)IafItemRegistry.DEATHWORM_EGG.get()).m_255245_((Item)IafItemRegistry.DEATHWORM_EGG_GIGANTIC.get()).m_255245_((Item)IafItemRegistry.MYRMEX_DESERT_EGG.get()).m_255245_((Item)IafItemRegistry.MYRMEX_JUNGLE_EGG.get());
      this.m_206424_(STORAGE_BLOCKS_SCALES_DRAGON_FIRE).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_RED.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_GREEN.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_BRONZE.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_GRAY.get()).m_5456_());
      this.m_206424_(STORAGE_BLOCKS_SCALES_DRAGON_ICE).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_BLUE.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_WHITE.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_SAPPHIRE.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_SILVER.get()).m_5456_());
      this.m_206424_(STORAGE_BLOCKS_SCALES_DRAGON_LIGHTNING).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_ELECTRIC.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_AMYTHEST.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_COPPER.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_SCALE_BLACK.get()).m_5456_());
      this.m_206424_(Items.STORAGE_BLOCKS).m_206428_(STORAGE_BLOCKS_SCALES_DRAGON_FIRE).m_206428_(STORAGE_BLOCKS_SCALES_DRAGON_ICE).m_206428_(STORAGE_BLOCKS_SCALES_DRAGON_LIGHTNING).m_255245_(((Block)IafBlockRegistry.DRAGONSTEEL_FIRE_BLOCK.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGONSTEEL_ICE_BLOCK.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGONSTEEL_LIGHTNING_BLOCK.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.SAPPHIRE_BLOCK.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.SILVER_BLOCK.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.RAW_SILVER_BLOCK.get()).m_5456_()).m_255245_(((Block)IafBlockRegistry.DRAGON_BONE_BLOCK.get()).m_5456_());
      this.m_206424_(DRAGON_FOOD_MEAT).m_255179_(new Item[]{net.minecraft.world.item.Items.f_42579_, net.minecraft.world.item.Items.f_42580_}).m_255179_(new Item[]{net.minecraft.world.item.Items.f_42581_, net.minecraft.world.item.Items.f_42582_}).m_255179_(new Item[]{net.minecraft.world.item.Items.f_42658_, net.minecraft.world.item.Items.f_42659_}).m_255179_(new Item[]{net.minecraft.world.item.Items.f_42485_, net.minecraft.world.item.Items.f_42486_}).m_176841_(new ResourceLocation("forge", "raw_mutton")).m_176841_(new ResourceLocation("forge", "raw_pork")).m_176841_(new ResourceLocation("forge", "raw_chicken")).m_176841_(new ResourceLocation("forge", "raw_beef")).m_176841_(new ResourceLocation("forge", "cooked_mutton")).m_176841_(new ResourceLocation("forge", "cooked_pork")).m_176841_(new ResourceLocation("forge", "cooked_chicken")).m_176841_(new ResourceLocation("forge", "cooked_beef"));
      this.m_206424_(BREED_AMPITHERE).m_255245_(net.minecraft.world.item.Items.f_42572_);
      this.m_206424_(BREED_HIPPOCAMPUS).m_255245_(net.minecraft.world.item.Items.f_42696_);
      this.m_206424_(BREED_HIPPOGRYPH).m_255245_(net.minecraft.world.item.Items.f_42699_);
      this.m_206424_(TAME_HIPPOGRYPH).m_255245_(net.minecraft.world.item.Items.f_42648_);
      this.m_206424_(HEAL_AMPITHERE).m_255245_(net.minecraft.world.item.Items.f_42533_);
      this.m_206424_(HEAL_COCKATRICE).m_206428_(Items.SEEDS).m_255245_(net.minecraft.world.item.Items.f_42583_);
      this.m_206424_(HEAL_HIPPOCAMPUS).m_255245_(net.minecraft.world.item.Items.f_41910_);
      this.m_206424_(HEAL_PIXIE).m_255245_(net.minecraft.world.item.Items.f_42501_);
      this.m_206424_(TAME_PIXIE).m_255245_(net.minecraft.world.item.Items.f_42502_);
      this.m_206424_(TEMPT_DRAGON).m_255245_((Item)IafItemRegistry.FIRE_STEW.get());
      this.m_206424_(TEMPT_HIPPOCAMPUS).m_255245_(net.minecraft.world.item.Items.f_41910_).m_255245_(net.minecraft.world.item.Items.f_42696_);
      this.m_206424_(TEMPT_HIPPOGRYPH).m_255245_(net.minecraft.world.item.Items.f_42697_).m_255245_(net.minecraft.world.item.Items.f_42698_);
      this.m_206424_(SCALES_DRAGON_FIRE).m_255245_((Item)IafItemRegistry.DRAGONSCALES_RED.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_GREEN.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_BRONZE.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_GRAY.get());
      this.m_206424_(SCALES_DRAGON_ICE).m_255245_((Item)IafItemRegistry.DRAGONSCALES_BLUE.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_WHITE.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_SAPPHIRE.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_SILVER.get());
      this.m_206424_(SCALES_DRAGON_LIGHTNING).m_255245_((Item)IafItemRegistry.DRAGONSCALES_ELECTRIC.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_AMYTHEST.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_COPPER.get()).m_255245_((Item)IafItemRegistry.DRAGONSCALES_BLACK.get());
      this.m_206424_(createKey("scales/dragon")).m_206428_(SCALES_DRAGON_FIRE).m_206428_(SCALES_DRAGON_ICE).m_206428_(SCALES_DRAGON_LIGHTNING);
      this.m_206424_(DRAGON_HEARTS).m_255245_((Item)IafItemRegistry.FIRE_DRAGON_HEART.get()).m_255245_((Item)IafItemRegistry.ICE_DRAGON_HEART.get()).m_255245_((Item)IafItemRegistry.LIGHTNING_DRAGON_HEART.get());
      IafItemRegistry.ITEMS.getEntries().forEach((registryObject) -> {
         Item item = (Item)registryObject.get();
         if (item instanceof ItemSeaSerpentScales) {
            this.m_206424_(SCALES_SEA_SERPENT).m_255245_(item);
         } else if (item instanceof ArrowItem) {
            this.m_206424_(ItemTags.f_13161_).m_255245_(item);
         } else if (item instanceof SwordItem) {
            this.m_206424_(ItemTags.f_271388_).m_255245_(item);
         } else if (item instanceof PickaxeItem) {
            this.m_206424_(ItemTags.f_271360_).m_255245_(item);
         } else if (item instanceof AxeItem) {
            this.m_206424_(ItemTags.f_271207_).m_255245_(item);
         } else if (item instanceof ShovelItem) {
            this.m_206424_(ItemTags.f_271138_).m_255245_(item);
         } else if (item instanceof HoeItem) {
            this.m_206424_(ItemTags.f_271298_).m_255245_(item);
         } else if (item instanceof BowItem) {
            this.m_206424_(Items.TOOLS_BOWS).m_255245_(item);
         } else if (item instanceof TridentItem) {
            this.m_206424_(Items.TOOLS_TRIDENTS).m_255245_(item);
         } else if (item instanceof ArmorItem) {
            ArmorItem armorItem = (ArmorItem)item;
            this.m_206424_(Items.ARMORS).m_255245_(item);
            switch(armorItem.m_266204_()) {
            case HELMET:
               this.m_206424_(Items.ARMORS_HELMETS).m_255245_(item);
               break;
            case CHESTPLATE:
               this.m_206424_(Items.ARMORS_CHESTPLATES).m_255245_(item);
               break;
            case LEGGINGS:
               this.m_206424_(Items.ARMORS_LEGGINGS).m_255245_(item);
               break;
            case BOOTS:
               this.m_206424_(Items.ARMORS_BOOTS).m_255245_(item);
            }
         } else if (item instanceof ItemMobSkull) {
            this.m_206424_(MOB_SKULLS).m_255245_(item);
         }

         if (item instanceof TieredItem || item instanceof BowItem || item instanceof TridentItem) {
            this.m_206424_(Items.TOOLS).m_255245_(item);
            if (item instanceof TridentItem) {
               this.m_206424_(ItemTags.f_271540_).m_255245_(item);
            }
         }

      });
      this.m_206424_(createForgeKey(ORES_PATH + "/silver")).m_255245_(((Block)IafBlockRegistry.SILVER_ORE.get()).m_5456_());
      this.m_206424_(createForgeKey(ORES_PATH + "/silver")).m_255245_(((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get()).m_5456_());
      this.m_206424_(INGOTS_SILVER).m_255245_(((Item)IafItemRegistry.SILVER_INGOT.get()).m_5456_());
      this.m_206424_(NUGGETS_COPPER).m_255245_((Item)IafItemRegistry.COPPER_NUGGET.get());
      this.m_206424_(NUGGETS_SILVER).m_255245_((Item)IafItemRegistry.SILVER_NUGGET.get());
      this.m_206424_(createForgeKey("raw_materials/silver")).m_255245_((Item)IafItemRegistry.RAW_SILVER.get());
      this.m_206424_(GEMS_SAPPHIRE).m_255245_((Item)IafItemRegistry.SAPPHIRE_GEM.get());
      this.m_206424_(STORAGE_BLOCKS_SILVER).m_255245_(((Block)IafBlockRegistry.SILVER_BLOCK.get()).m_5456_());
      this.m_206424_(createForgeKey(STORAGE_BLOCK_PATH + "/raw_silver")).m_255245_(((Block)IafBlockRegistry.RAW_SILVER_BLOCK.get()).m_5456_());
      this.m_206424_(createForgeKey(STORAGE_BLOCK_PATH + "/sapphire")).m_255245_(((Block)IafBlockRegistry.SAPPHIRE_BLOCK.get()).m_5456_());
      this.m_206424_(BONES_WITHER).m_255245_((Item)IafItemRegistry.WITHERBONE.get());
   }

   private static TagKey<Item> createKey(String name) {
      return ItemTags.create(new ResourceLocation("iceandfire", name));
   }

   private static TagKey<Item> createForgeKey(String name) {
      return TagKey.m_203882_(Registries.f_256913_, new ResourceLocation("forge", name));
   }

   public String m_6055_() {
      return "Ice and Fire Item Tags";
   }

   static {
      STORAGE_BLOCK_PATH = Items.STORAGE_BLOCKS.f_203868_().m_135815_();
      INGOTS_PATH = Items.INGOTS.f_203868_().m_135815_();
      NUGGETS_PATH = Items.NUGGETS.f_203868_().m_135815_();
      BONES_PATH = Items.BONES.f_203868_().m_135815_();
      ORES_PATH = Items.ORES.f_203868_().m_135815_();
      GEMS = Items.GEMS.f_203868_().m_135815_();
      CHARRED_BLOCKS = createKey("charred_blocks");
      FROZEN_BLOCKS = createKey("frozen_blocks");
      CRACKLED_BLOCKS = createKey("crackled_blocks");
      DRAGON_SKULLS = createKey("dragon_skulls");
      MOB_SKULLS = createKey("mob_skulls");
      SCALES_DRAGON_FIRE = createKey("scales/dragon/fire");
      SCALES_DRAGON_ICE = createKey("scales/dragon/ice");
      SCALES_DRAGON_LIGHTNING = createKey("scales/dragon/lightning");
      SCALES_SEA_SERPENT = createKey("scales/sea_serpent");
      DRAGON_FOOD_MEAT = createKey("dragon_food_meat");
      STORAGE_BLOCKS_SCALES_DRAGON_FIRE = createForgeKey(STORAGE_BLOCK_PATH + "/scales/dragon/fire");
      STORAGE_BLOCKS_SCALES_DRAGON_ICE = createForgeKey(STORAGE_BLOCK_PATH + "/scales/dragon/ice");
      STORAGE_BLOCKS_SCALES_DRAGON_LIGHTNING = createForgeKey(STORAGE_BLOCK_PATH + "/scales/dragon/lightning");
      STORAGE_BLOCKS_SILVER = createForgeKey(STORAGE_BLOCK_PATH + "/silver");
      GEMS_SAPPHIRE = createForgeKey(GEMS + "/sapphire");
      INGOTS_SILVER = createForgeKey(INGOTS_PATH + "/silver");
      NUGGETS_COPPER = createForgeKey(NUGGETS_PATH + "/copper");
      NUGGETS_SILVER = createForgeKey(NUGGETS_PATH + "/silver");
      BONES_WITHER = createForgeKey(BONES_PATH + "/wither");
      MAKE_ITEM_DROPS_FIREIMMUNE = createKey("make_item_drops_fireimmune");
      DRAGON_ARROWS = createKey("dragon_arrows");
      DRAGON_BLOODS = createKey("dragon_bloods");
      DRAGON_HEARTS = createKey("dragon_hearts");
      BREED_AMPITHERE = createKey("breed_ampithere");
      BREED_HIPPOCAMPUS = createKey("breed_hippocampus");
      BREED_HIPPOGRYPH = createKey("breed_hippogryph");
      HEAL_AMPITHERE = createKey("heal_ampithere");
      HEAL_COCKATRICE = createKey("heal_cockatrice");
      HEAL_HIPPOCAMPUS = createKey("heal_hippocampus");
      HEAL_PIXIE = createKey("heal_pixie");
      TAME_HIPPOGRYPH = createKey("tame_hippogryph");
      TAME_PIXIE = createKey("tame_pixie");
      TEMPT_DRAGON = createKey("tempt_dragon");
      TEMPT_HIPPOCAMPUS = createKey("tempt_hippocampus");
      TEMPT_HIPPOGRYPH = createKey("tempt_hippogryph");
   }
}
