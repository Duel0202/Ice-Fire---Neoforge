package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.alexthe666.iceandfire.enums.EnumSeaSerpent;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonArmor;
import java.util.Arrays;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforge.common.Tags.Items;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class IafRecipes extends RecipeProvider {
   public IafRecipes(PackOutput output) {
      super(output);
   }

   protected void m_245200_(Consumer<FinishedRecipe> consumer) {
      this.createShaped(consumer);
      this.createShapeless(consumer);
   }

   private void createShaped(@NotNull Consumer<FinishedRecipe> consumer) {
      ShapedRecipeBuilder.m_246608_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.AMPHITHERE_ARROW.get(), 4).m_126130_("X").m_126130_("#").m_126130_("Y").m_206416_('#', Items.RODS_WOODEN).m_126127_('X', net.minecraft.world.item.Items.f_42484_).m_126127_('Y', (ItemLike)IafItemRegistry.AMPHITHERE_FEATHER.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.AMPHITHERE_FEATHER.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.AMPHITHERE_MACUAHUITL.get()).m_126130_("OXO").m_126130_("FXF").m_126130_("OSO").m_206416_('X', ItemTags.f_13168_).m_206416_('S', Items.RODS_WOODEN).m_206416_('O', Items.OBSIDIAN).m_126127_('F', (ItemLike)IafItemRegistry.AMPHITHERE_FEATHER.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.AMPHITHERE_FEATHER.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, net.minecraft.world.item.Items.f_42414_).m_126130_("BBB").m_126130_("BBB").m_126130_("BBB").m_126127_('B', (ItemLike)IafBlockRegistry.ASH.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.ASH.get())).m_126140_(consumer, location("ash_to_charcoal"));
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.BLINDFOLD.get()).m_126130_("SLS").m_206416_('L', Items.LEATHER).m_206416_('S', Items.STRING).m_126132_("has_item", m_206406_(Items.LEATHER)).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.CHAIN.get()).m_126130_("S").m_126130_("S").m_126130_("S").m_126127_('S', net.minecraft.world.item.Items.f_42026_).m_126132_("has_item", m_125977_(net.minecraft.world.item.Items.f_42026_)).m_176498_(consumer);
      this.armorSet(consumer, (ItemLike)net.minecraft.world.item.Items.f_42026_, net.minecraft.world.item.Items.f_42464_, net.minecraft.world.item.Items.f_42465_, net.minecraft.world.item.Items.f_42466_, net.minecraft.world.item.Items.f_42467_);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.ITEM_COCKATRICE_SCEPTER.get()).m_126130_("S").m_126130_("E").m_126130_("W").m_206416_('W', IafItemTags.BONES_WITHER).m_126127_('S', (ItemLike)IafItemRegistry.WITHER_SHARD.get()).m_126127_('E', (ItemLike)IafItemRegistry.COCKATRICE_EYE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.COCKATRICE_EYE.get())).m_176498_(consumer);
      this.armorSet(consumer, Items.INGOTS_COPPER, (ItemLike)IafItemRegistry.COPPER_HELMET.get(), (ItemLike)IafItemRegistry.COPPER_CHESTPLATE.get(), (ItemLike)IafItemRegistry.COPPER_LEGGINGS.get(), (ItemLike)IafItemRegistry.COPPER_BOOTS.get());
      this.toolSet(consumer, Items.INGOTS_COPPER, Items.RODS_WOODEN, (ItemLike)IafItemRegistry.COPPER_SWORD.get(), (ItemLike)IafItemRegistry.COPPER_PICKAXE.get(), (ItemLike)IafItemRegistry.COPPER_AXE.get(), (ItemLike)IafItemRegistry.COPPER_SHOVEL.get(), (ItemLike)IafItemRegistry.COPPER_HOE.get());
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DEATHWORM_GAUNTLET_RED.get()).m_126130_(" T ").m_126130_("CHC").m_126130_("CCC").m_126127_('C', (ItemLike)IafItemRegistry.DEATH_WORM_CHITIN_RED.get()).m_126127_('H', (ItemLike)IafItemRegistry.CHAIN.get()).m_126127_('T', (ItemLike)IafItemRegistry.DEATHWORM_TOUNGE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DEATHWORM_TOUNGE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DEATHWORM_GAUNTLET_WHITE.get()).m_126130_(" T ").m_126130_("CHC").m_126130_("CCC").m_126127_('C', (ItemLike)IafItemRegistry.DEATH_WORM_CHITIN_WHITE.get()).m_126127_('H', (ItemLike)IafItemRegistry.CHAIN.get()).m_126127_('T', (ItemLike)IafItemRegistry.DEATHWORM_TOUNGE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DEATHWORM_TOUNGE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DEATHWORM_GAUNTLET_YELLOW.get()).m_126130_(" T ").m_126130_("CHC").m_126130_("CCC").m_126127_('C', (ItemLike)IafItemRegistry.DEATH_WORM_CHITIN_YELLOW.get()).m_126127_('H', (ItemLike)IafItemRegistry.CHAIN.get()).m_126127_('T', (ItemLike)IafItemRegistry.DEATHWORM_TOUNGE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DEATHWORM_TOUNGE.get())).m_176498_(consumer);
      this.armorSet(consumer, (ItemLike)IafItemRegistry.DEATH_WORM_CHITIN_RED.get(), (ItemLike)IafItemRegistry.DEATHWORM_RED_HELMET.get(), (ItemLike)IafItemRegistry.DEATHWORM_RED_CHESTPLATE.get(), (ItemLike)IafItemRegistry.DEATHWORM_RED_LEGGINGS.get(), (ItemLike)IafItemRegistry.DEATHWORM_RED_BOOTS.get());
      this.armorSet(consumer, (ItemLike)IafItemRegistry.DEATH_WORM_CHITIN_WHITE.get(), (ItemLike)IafItemRegistry.DEATHWORM_WHITE_HELMET.get(), (ItemLike)IafItemRegistry.DEATHWORM_WHITE_CHESTPLATE.get(), (ItemLike)IafItemRegistry.DEATHWORM_WHITE_LEGGINGS.get(), (ItemLike)IafItemRegistry.DEATHWORM_WHITE_BOOTS.get());
      this.armorSet(consumer, (ItemLike)IafItemRegistry.DEATH_WORM_CHITIN_YELLOW.get(), (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_HELMET.get(), (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_CHESTPLATE.get(), (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_LEGGINGS.get(), (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_BOOTS.get());
      this.dragonArmorSet(consumer, Items.STORAGE_BLOCKS_COPPER, (ItemLike)IafItemRegistry.DRAGONARMOR_COPPER_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_COPPER_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_COPPER_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_COPPER_3.get());
      this.dragonArmorSet(consumer, Items.STORAGE_BLOCKS_IRON, (ItemLike)IafItemRegistry.DRAGONARMOR_IRON_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_IRON_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_IRON_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_IRON_3.get());
      this.dragonArmorSet(consumer, IafItemTags.STORAGE_BLOCKS_SILVER, (ItemLike)IafItemRegistry.DRAGONARMOR_SILVER_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_SILVER_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_SILVER_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_SILVER_3.get());
      this.dragonArmorSet(consumer, Items.STORAGE_BLOCKS_DIAMOND, (ItemLike)IafItemRegistry.DRAGONARMOR_DIAMOND_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DIAMOND_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DIAMOND_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DIAMOND_3.get());
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.IRON_HIPPOGRYPH_ARMOR.get()).m_126130_("FDF").m_206416_('F', Items.FEATHERS).m_126127_('D', net.minecraft.world.item.Items.f_42651_).m_126132_("has_item", m_125977_(net.minecraft.world.item.Items.f_42651_)).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.GOLD_HIPPOGRYPH_ARMOR.get()).m_126130_("FDF").m_206416_('F', Items.FEATHERS).m_126127_('D', net.minecraft.world.item.Items.f_42652_).m_126132_("has_item", m_125977_(net.minecraft.world.item.Items.f_42652_)).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DIAMOND_HIPPOGRYPH_ARMOR.get()).m_126130_("FDF").m_206416_('F', Items.FEATHERS).m_126127_('D', net.minecraft.world.item.Items.f_42653_).m_126132_("has_item", m_125977_(net.minecraft.world.item.Items.f_42653_)).m_176498_(consumer);
      m_247368_(consumer, RecipeCategory.MISC, (ItemLike)IafItemRegistry.DRAGON_BONE.get(), RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DRAGON_BONE_BLOCK.get(), locationString("dragon_bone_block"), (String)null, locationString("dragonbone"), (String)null);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.DRAGON_BONE_BLOCK_WALL.get()).m_126130_("BBB").m_126130_("BBB").m_126127_('B', (ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DRAGON_BONE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.DRAGON_FLUTE.get()).m_126130_("B  ").m_126130_(" B ").m_126130_("  I").m_206416_('I', Items.INGOTS_IRON).m_126127_('B', (ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DRAGON_BONE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.DRAGON_HORN.get()).m_126130_("  B").m_126130_(" BB").m_126130_("IB ").m_206416_('I', Items.RODS_WOODEN).m_126127_('B', (ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DRAGON_BONE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.DRAGON_ICE_SPIKES.get(), 4).m_126130_("I I").m_126130_("I I").m_126127_('I', (ItemLike)IafBlockRegistry.DRAGON_ICE.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DRAGON_ICE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.NEST.get(), 8).m_126130_("HHH").m_126130_("HBH").m_126130_("HHH").m_126127_('H', Blocks.f_50335_).m_126127_('B', (ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DRAGON_BONE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.DRAGON_STAFF.get()).m_126130_("S").m_126130_("T").m_126130_("T").m_206416_('T', Items.RODS_WOODEN).m_206416_('S', IafItemTags.DRAGON_SKULLS).m_126132_("has_item", m_206406_(IafItemTags.DRAGON_SKULLS)).m_176498_(consumer);
      this.toolSet(consumer, (ItemLike)IafItemRegistry.DRAGON_BONE.get(), IafItemTags.BONES_WITHER, (ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get(), (ItemLike)IafItemRegistry.DRAGONBONE_PICKAXE.get(), (ItemLike)IafItemRegistry.DRAGONBONE_AXE.get(), (ItemLike)IafItemRegistry.DRAGONBONE_SHOVEL.get(), (ItemLike)IafItemRegistry.DRAGONBONE_HOE.get());
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DRAGON_BOW.get()).m_126130_(" DS").m_126130_("W S").m_126130_(" DS").m_206416_('S', Items.STRING).m_206416_('W', IafItemTags.BONES_WITHER).m_126127_('D', (ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DRAGON_BONE.get())).m_176498_(consumer);
      this.forgeBrick(consumer, net.minecraft.world.item.Items.f_42018_, IafItemTags.STORAGE_BLOCKS_SCALES_DRAGON_FIRE, (ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get());
      this.forgeCore(consumer, (ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get(), (ItemLike)IafItemRegistry.FIRE_DRAGON_HEART.get(), (ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get());
      this.forgeInput(consumer, (ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get(), Items.INGOTS_IRON, (ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_INPUT.get());
      this.forgeBrick(consumer, net.minecraft.world.item.Items.f_42018_, IafItemTags.STORAGE_BLOCKS_SCALES_DRAGON_ICE, (ItemLike)IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get());
      this.forgeCore(consumer, (ItemLike)IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get(), (ItemLike)IafItemRegistry.ICE_DRAGON_HEART.get(), (ItemLike)IafBlockRegistry.DRAGONFORGE_ICE_CORE_DISABLED.get());
      this.forgeInput(consumer, (ItemLike)IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get(), Items.INGOTS_IRON, (ItemLike)IafBlockRegistry.DRAGONFORGE_ICE_INPUT.get());
      this.forgeBrick(consumer, net.minecraft.world.item.Items.f_42018_, IafItemTags.STORAGE_BLOCKS_SCALES_DRAGON_LIGHTNING, (ItemLike)IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get());
      this.forgeCore(consumer, (ItemLike)IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get(), (ItemLike)IafItemRegistry.LIGHTNING_DRAGON_HEART.get(), (ItemLike)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get());
      this.forgeInput(consumer, (ItemLike)IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get(), Items.INGOTS_IRON, (ItemLike)IafBlockRegistry.DRAGONFORGE_LIGHTNING_INPUT.get());
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.DRAGON_MEAL.get()).m_126130_("BMB").m_126130_("MBM").m_126130_("BMB").m_206416_('B', Items.BONES).m_206416_('M', IafItemTags.DRAGON_FOOD_MEAT).m_126132_("has_item", m_206406_(IafItemTags.DRAGON_FOOD_MEAT)).m_176498_(consumer);
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_RED.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_RED.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_GREEN.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_GREEN.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_BRONZE.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_BRONZE.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_GRAY.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_GRAY.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_BLUE.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_BLUE.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_WHITE.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_WHITE.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_SAPPHIRE.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_SAPPHIRE.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_SILVER.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_SILVER.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_ELECTRIC.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_ELECTRIC.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_AMYTHEST.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_AMYTHEST.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_COPPER.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_COPPER.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSCALES_BLACK.get(), (ItemLike)IafBlockRegistry.DRAGON_SCALE_BLACK.get());
      EnumDragonArmor[] var2 = EnumDragonArmor.values();
      int var3 = var2.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         EnumDragonArmor type = var2[var4];
         this.armorSet(consumer, type.armorMaterial.m_6230_(), (ItemLike)type.helmet.get(), (ItemLike)type.chestplate.get(), (ItemLike)type.leggings.get(), (ItemLike)type.boots.get());
      }

      EnumSeaSerpent[] var6 = EnumSeaSerpent.values();
      var3 = var6.length;

      for(var4 = 0; var4 < var3; ++var4) {
         EnumSeaSerpent type = var6[var4];
         this.armorSet(consumer, (ItemLike)type.scale.get(), (ItemLike)type.helmet.get(), (ItemLike)type.chestplate.get(), (ItemLike)type.leggings.get(), (ItemLike)type.boots.get());
         this.compact(consumer, (ItemLike)type.scale.get(), (ItemLike)type.scaleBlock.get());
      }

      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get(), (ItemLike)IafBlockRegistry.DRAGONSTEEL_FIRE_BLOCK.get());
      this.toolSet(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get(), IafItemTags.BONES_WITHER, (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_SWORD.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_PICKAXE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_AXE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_SHOVEL.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_HOE.get());
      this.armorSet(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_HELMET.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_CHESTPLATE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_LEGGINGS.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_BOOTS.get());
      this.dragonArmorSet(consumer, (ItemLike)IafBlockRegistry.DRAGONSTEEL_FIRE_BLOCK.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_FIRE_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_FIRE_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_FIRE_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_FIRE_3.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_INGOT.get(), (ItemLike)IafBlockRegistry.DRAGONSTEEL_ICE_BLOCK.get());
      this.toolSet(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_INGOT.get(), IafItemTags.BONES_WITHER, (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_SWORD.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_PICKAXE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_AXE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_SHOVEL.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_HOE.get());
      this.armorSet(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_INGOT.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_HELMET.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_CHESTPLATE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_LEGGINGS.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_BOOTS.get());
      this.dragonArmorSet(consumer, (ItemLike)IafBlockRegistry.DRAGONSTEEL_ICE_BLOCK.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_ICE_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_ICE_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_ICE_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_ICE_3.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_INGOT.get(), (ItemLike)IafBlockRegistry.DRAGONSTEEL_LIGHTNING_BLOCK.get());
      this.toolSet(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_INGOT.get(), IafItemTags.BONES_WITHER, (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_SWORD.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_PICKAXE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_AXE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_SHOVEL.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_HOE.get());
      this.armorSet(consumer, (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_INGOT.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_HELMET.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_CHESTPLATE.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_LEGGINGS.get(), (ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_BOOTS.get());
      this.dragonArmorSet(consumer, (ItemLike)IafBlockRegistry.DRAGONSTEEL_LIGHTNING_BLOCK.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_LIGHTNING_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_LIGHTNING_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_LIGHTNING_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_DRAGONSTEEL_LIGHTNING_3.get());
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE.get(), 8).m_126130_("DDD").m_126130_("DSD").m_126130_("DDD").m_206416_('S', Items.STONE).m_126127_('D', (ItemLike)IafItemRegistry.DREAD_SHARD.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DREAD_SHARD.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get(), 4).m_126130_("DD").m_126130_("DD").m_126127_('D', (ItemLike)IafBlockRegistry.DREAD_STONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS_CHISELED.get()).m_126130_("D").m_126130_("D").m_126127_('D', (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS_SLAB.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS_SLAB.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_FACE.get(), 8).m_126130_("DDD").m_126130_("DSD").m_126130_("DDD").m_126127_('S', net.minecraft.world.item.Items.f_42678_).m_126127_('D', (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS_SLAB.get(), 6).m_126130_("DDD").m_126127_('D', (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS_STAIRS.get(), 4).m_126130_("D  ").m_126130_("DD ").m_126130_("DDD").m_126127_('D', (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_TILE.get(), 8).m_126130_("DDD").m_126130_("D D").m_126130_("DDD").m_126127_('D', (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_TORCH.get(), 4).m_126130_("D").m_126130_("S").m_206416_('S', Items.RODS_WOODEN).m_126127_('D', (ItemLike)IafItemRegistry.DREAD_SHARD.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.DREAD_SHARD.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.EARPLUGS.get()).m_126130_("B B").m_206416_('B', ItemTags.f_13168_).m_126132_("has_item", m_206406_(ItemTags.f_13168_)).m_176498_(consumer);
      EnumTroll[] var7 = EnumTroll.values();
      var3 = var7.length;

      for(var4 = 0; var4 < var3; ++var4) {
         EnumTroll type = var7[var4];
         this.armorSet(consumer, (ItemLike)type.leather.get(), (ItemLike)type.chestplate.get(), (ItemLike)type.leggings.get(), (ItemLike)type.boots.get());
         ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)type.helmet.get()).m_126130_("TTT").m_126130_("U U").m_126127_('T', (ItemLike)type.leather.get()).m_126127_('U', (ItemLike)IafItemRegistry.TROLL_TUSK.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.TROLL_TUSK.get())).m_176498_(consumer);
      }

      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.GHOST_CHEST.get()).m_126130_(" E ").m_126130_("ECE").m_126130_(" E ").m_206416_('C', Items.RODS_WOODEN).m_126127_('E', (ItemLike)IafItemRegistry.ECTOPLASM.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.ECTOPLASM.get())).m_176498_(consumer);
      this.dragonArmorSet(consumer, Items.STORAGE_BLOCKS_GOLD, (ItemLike)IafItemRegistry.DRAGONARMOR_GOLD_0.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_GOLD_1.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_GOLD_2.get(), (ItemLike)IafItemRegistry.DRAGONARMOR_GOLD_3.get());
      ShapedRecipeBuilder.m_245327_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.GRAVEYARD_SOIL.get()).m_126130_(" E ").m_126130_("ECE").m_126130_(" E ").m_126127_('C', net.minecraft.world.item.Items.f_42382_).m_126127_('E', (ItemLike)IafItemRegistry.ECTOPLASM.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.ECTOPLASM.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.MYRMEX_DESERT_RESIN.get()).m_126130_("RR").m_126130_("RR").m_126127_('R', (ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_245327_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.MYRMEX_JUNGLE_RESIN.get()).m_126130_("RR").m_126130_("RR").m_126127_('R', (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get())).m_176498_(consumer);
      ShapedRecipeBuilder.m_246608_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.SEA_SERPENT_ARROW.get(), 4).m_126130_("X").m_126130_("#").m_126130_("Y").m_206416_('#', Items.RODS_WOODEN).m_126127_('X', (ItemLike)IafItemRegistry.SERPENT_FANG.get()).m_206416_('Y', IafItemTags.SCALES_SEA_SERPENT).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.SERPENT_FANG.get())).m_176498_(consumer);
      this.armorSet(consumer, (ItemLike)IafItemRegistry.MYRMEX_DESERT_CHITIN.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_HELMET.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_CHESTPLATE.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_LEGGINGS.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_BOOTS.get());
      this.toolSet(consumer, (ItemLike)IafItemRegistry.MYRMEX_DESERT_CHITIN.get(), IafItemTags.BONES_WITHER, (ItemLike)IafItemRegistry.MYRMEX_DESERT_SWORD.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_PICKAXE.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_AXE.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_SHOVEL.get(), (ItemLike)IafItemRegistry.MYRMEX_DESERT_HOE.get());
      this.armorSet(consumer, (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_CHITIN.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_HELMET.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_CHESTPLATE.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_LEGGINGS.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_BOOTS.get());
      this.toolSet(consumer, (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_CHITIN.get(), IafItemTags.BONES_WITHER, (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_SWORD.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_PICKAXE.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_AXE.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_SHOVEL.get(), (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_HOE.get());
      SimpleCookingRecipeBuilder var10000 = SimpleCookingRecipeBuilder.m_246179_(Ingredient.m_43929_(new ItemLike[]{(ItemLike)IafItemRegistry.RAW_SILVER.get()}), RecipeCategory.TOOLS, (ItemLike)IafItemRegistry.SILVER_INGOT.get(), 0.7F, 200).m_126145_("raw_silver").m_126132_(m_176602_((ItemLike)IafItemRegistry.RAW_SILVER.get()), m_125977_((ItemLike)IafItemRegistry.RAW_SILVER.get()));
      ResourceLocation var10002 = location(m_176632_((ItemLike)IafItemRegistry.SILVER_INGOT.get()));
      var10000.m_176500_(consumer, var10002 + "_from_smelting_" + m_176632_((ItemLike)IafItemRegistry.RAW_SILVER.get()));
      var10000 = SimpleCookingRecipeBuilder.m_245681_(Ingredient.m_43929_(new ItemLike[]{(ItemLike)IafItemRegistry.RAW_SILVER.get()}), RecipeCategory.TOOLS, (ItemLike)IafItemRegistry.SILVER_INGOT.get(), 0.7F, 100).m_126145_("raw_silver").m_126132_(m_176602_((ItemLike)IafItemRegistry.RAW_SILVER.get()), m_125977_((ItemLike)IafItemRegistry.RAW_SILVER.get()));
      var10002 = location(m_176632_((ItemLike)IafItemRegistry.SILVER_INGOT.get()));
      var10000.m_176500_(consumer, var10002 + "_from_blasting_" + m_176632_((ItemLike)IafItemRegistry.RAW_SILVER.get()));
      this.compact(consumer, (ItemLike)IafItemRegistry.SILVER_INGOT.get(), (ItemLike)IafBlockRegistry.SILVER_BLOCK.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.RAW_SILVER.get(), (ItemLike)IafBlockRegistry.RAW_SILVER_BLOCK.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.SILVER_NUGGET.get(), (ItemLike)IafItemRegistry.SILVER_INGOT.get());
      this.armorSet(consumer, IafItemTags.INGOTS_SILVER, (ItemLike)IafItemRegistry.SILVER_HELMET.get(), (ItemLike)IafItemRegistry.SILVER_CHESTPLATE.get(), (ItemLike)IafItemRegistry.SILVER_LEGGINGS.get(), (ItemLike)IafItemRegistry.SILVER_BOOTS.get());
      this.toolSet(consumer, IafItemTags.INGOTS_SILVER, Items.RODS_WOODEN, (ItemLike)IafItemRegistry.SILVER_SWORD.get(), (ItemLike)IafItemRegistry.SILVER_PICKAXE.get(), (ItemLike)IafItemRegistry.SILVER_AXE.get(), (ItemLike)IafItemRegistry.SILVER_SHOVEL.get(), (ItemLike)IafItemRegistry.SILVER_HOE.get());
      this.compact(consumer, (ItemLike)IafItemRegistry.SAPPHIRE_GEM.get(), (ItemLike)IafBlockRegistry.SAPPHIRE_BLOCK.get());
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.TIDE_TRIDENT.get()).m_126130_("TTT").m_126130_("SDS").m_126130_(" B ").m_206416_('D', Items.GEMS_DIAMOND).m_206416_('S', IafItemTags.SCALES_SEA_SERPENT).m_126127_('T', (ItemLike)IafItemRegistry.SERPENT_FANG.get()).m_126127_('B', (ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.SERPENT_FANG.get())).m_176498_(consumer);
   }

   private void createShapeless(@NotNull Consumer<FinishedRecipe> consumer) {
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.FOOD, (ItemLike)IafItemRegistry.AMBROSIA.get()).m_126209_((ItemLike)IafItemRegistry.PIXIE_DUST.get()).m_126209_(net.minecraft.world.item.Items.f_42399_).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.PIXIE_DUST.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.ASH.get()).m_126186_(Ingredient.m_204132_(IafItemTags.CHARRED_BLOCKS), 9).m_126132_("has_item", m_206406_(IafItemTags.CHARRED_BLOCKS)).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.BESTIARY.get()).m_126211_((ItemLike)IafItemRegistry.MANUSCRIPT.get(), 3).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.MANUSCRIPT.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.CHAIN_STICKY.get()).m_206419_(Items.SLIMEBALLS).m_126209_((ItemLike)IafItemRegistry.CHAIN.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.CHAIN.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.MISC, net.minecraft.world.item.Items.f_151052_).m_126186_(Ingredient.m_204132_(IafItemTags.NUGGETS_COPPER), 9).m_126132_("has_item", m_206406_(IafItemTags.NUGGETS_COPPER)).m_126140_(consumer, location("copper_nuggets_to_ingot"));
      ShapelessRecipeBuilder.m_246517_(RecipeCategory.MISC, (ItemLike)IafItemRegistry.COPPER_NUGGET.get(), 9).m_206419_(Items.INGOTS_COPPER).m_126132_("has_item", m_206406_(Items.INGOTS_COPPER)).m_126140_(consumer, location("copper_ingot_to_nuggets"));
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.DECORATIONS, (ItemLike)IafBlockRegistry.COPPER_PILE.get()).m_126186_(Ingredient.m_204132_(IafItemTags.NUGGETS_COPPER), 2).m_126132_("has_item", m_206406_(IafItemTags.NUGGETS_COPPER)).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.MISC, (ItemLike)IafBlockRegistry.DRAGON_ICE.get()).m_126186_(Ingredient.m_204132_(IafItemTags.FROZEN_BLOCKS), 9).m_126132_("has_item", m_206406_(IafItemTags.FROZEN_BLOCKS)).m_176498_(consumer);
      ShapelessRecipeBuilder.m_246517_(RecipeCategory.MISC, net.minecraft.world.item.Items.f_42499_, 5).m_206419_(IafItemTags.MOB_SKULLS).m_126132_("has_item", m_206406_(IafItemTags.MOB_SKULLS)).m_126140_(consumer, location("skull_to_bone_meal"));
      ShapelessRecipeBuilder.m_246517_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DRAGONBONE_ARROW.get(), 5).m_126209_((ItemLike)IafItemRegistry.DRAGON_BONE.get()).m_126209_((ItemLike)IafItemRegistry.WITHER_SHARD.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.WITHER_SHARD.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS_MOSSY.get()).m_126209_(net.minecraft.world.item.Items.f_42029_).m_126209_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREAD_STONE_BRICKS.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_246517_(RecipeCategory.BUILDING_BLOCKS, (ItemLike)IafBlockRegistry.DREADWOOD_PLANKS.get(), 4).m_126209_((ItemLike)IafBlockRegistry.DREADWOOD_LOG.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.DREADWOOD_LOG.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.FOOD, (ItemLike)IafItemRegistry.FIRE_STEW.get()).m_126209_(net.minecraft.world.item.Items.f_42399_).m_126209_(net.minecraft.world.item.Items.f_42585_).m_126209_((ItemLike)IafBlockRegistry.FIRE_LILY.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.FIRE_LILY.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.FOOD, (ItemLike)IafItemRegistry.FROST_STEW.get()).m_126209_(net.minecraft.world.item.Items.f_42399_).m_126209_(net.minecraft.world.item.Items.f_42696_).m_126209_((ItemLike)IafBlockRegistry.FROST_LILY.get()).m_126132_("has_item", m_125977_((ItemLike)IafBlockRegistry.FROST_LILY.get())).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.FOOD, (ItemLike)IafBlockRegistry.GOLD_PILE.get()).m_126186_(Ingredient.m_204132_(Items.NUGGETS_GOLD), 2).m_126132_("has_item", m_206406_(Items.NUGGETS_GOLD)).m_176498_(consumer);
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.BUILDING_BLOCKS, net.minecraft.world.item.Items.f_41832_).m_126186_(Ingredient.m_204132_(IafItemTags.CRACKLED_BLOCKS), 9).m_126132_("has_item", m_206406_(IafItemTags.CRACKLED_BLOCKS)).m_126140_(consumer, location("crackled_to_gravel"));
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DRAGONBONE_SWORD_FIRE.get()).m_126209_((ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get()).m_126209_((ItemLike)IafItemRegistry.FIRE_DRAGON_BLOOD.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.FIRE_DRAGON_BLOOD.get())).m_126140_(consumer, location("dragonbone_sword_fire"));
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DRAGONBONE_SWORD_ICE.get()).m_126209_((ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get()).m_126209_((ItemLike)IafItemRegistry.ICE_DRAGON_BLOOD.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.ICE_DRAGON_BLOOD.get())).m_126140_(consumer, location("dragonbone_sword_ice"));
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.DRAGONBONE_SWORD_LIGHTNING.get()).m_126209_((ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get()).m_126209_((ItemLike)IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get())).m_126140_(consumer, location("dragonbone_sword_lightning"));
      ShapelessRecipeBuilder.m_245498_(RecipeCategory.COMBAT, (ItemLike)IafItemRegistry.GHOST_SWORD.get()).m_126209_((ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get()).m_126209_((ItemLike)IafItemRegistry.GHOST_INGOT.get()).m_126132_("has_item", m_125977_((ItemLike)IafItemRegistry.GHOST_INGOT.get())).m_126140_(consumer, location("ghost_sword"));
   }

   private void compact(@NotNull Consumer<FinishedRecipe> consumer, ItemLike unpacked, ItemLike packed) {
      String packedPath = ForgeRegistries.ITEMS.getKey(packed.m_5456_()).m_135815_();
      String unpackedPath = ForgeRegistries.ITEMS.getKey(unpacked.m_5456_()).m_135815_();
      m_247368_(consumer, RecipeCategory.MISC, unpacked, RecipeCategory.BUILDING_BLOCKS, packed, locationString(unpackedPath + "_to_" + packedPath), (String)null, locationString(packedPath + "_to_" + unpackedPath), (String)null);
   }

   private void toolSet(@NotNull Consumer<FinishedRecipe> consumer, TagKey<Item> material, TagKey<Item> handle, ItemLike... items) {
      this.toolSet(consumer, Ingredient.m_204132_(material), Ingredient.m_204132_(handle), items);
   }

   private void toolSet(@NotNull Consumer<FinishedRecipe> consumer, ItemLike material, TagKey<Item> handle, ItemLike... items) {
      this.toolSet(consumer, Ingredient.m_43929_(new ItemLike[]{material}), Ingredient.m_204132_(handle), items);
   }

   private void toolSet(@NotNull Consumer<FinishedRecipe> consumer, TagKey<Item> material, ItemLike handle, ItemLike... items) {
      this.toolSet(consumer, Ingredient.m_204132_(material), Ingredient.m_43929_(new ItemLike[]{handle}), items);
   }

   private void toolSet(@NotNull Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike handle, ItemLike... items) {
      this.toolSet(consumer, Ingredient.m_43929_(new ItemLike[]{material}), Ingredient.m_43929_(new ItemLike[]{handle}), items);
   }

   private void toolSet(@NotNull Consumer<FinishedRecipe> consumer, Ingredient material, Ingredient handle, ItemLike... results) {
      ItemLike[] var5 = results;
      int var6 = results.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ItemLike result = var5[var7];
         Item item = result.m_5456_();
         if (item instanceof SwordItem) {
            this.sword(consumer, material, handle, result);
         } else if (item instanceof PickaxeItem) {
            this.pickaxe(consumer, material, handle, result);
         } else if (item instanceof AxeItem) {
            this.axe(consumer, material, handle, result);
         } else if (item instanceof ShovelItem) {
            this.shovel(consumer, material, handle, result);
         } else {
            if (!(item instanceof HoeItem)) {
               throw new IllegalArgumentException("Result is not a valid tool: [" + result + "]");
            }

            this.hoe(consumer, material, handle, result);
         }
      }

   }

   private void armorSet(@NotNull Consumer<FinishedRecipe> consumer, TagKey<Item> tag, ItemLike... results) {
      this.armorSet(consumer, Ingredient.m_204132_(tag), results);
   }

   private void armorSet(@NotNull Consumer<FinishedRecipe> consumer, ItemLike item, ItemLike... results) {
      this.armorSet(consumer, Ingredient.m_43929_(new ItemLike[]{item}), results);
   }

   private void armorSet(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike... results) {
      ItemLike[] var4 = results;
      int var5 = results.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemLike result = var4[var6];
         Item var9 = result.m_5456_();
         if (!(var9 instanceof ArmorItem)) {
            throw new IllegalArgumentException("Result is not an armor item: [" + result + "]");
         }

         ArmorItem armorItem = (ArmorItem)var9;
         switch(armorItem.m_266204_()) {
         case HELMET:
            this.helmet(consumer, ingredient, result);
            break;
         case CHESTPLATE:
            this.chestPlate(consumer, ingredient, result);
            break;
         case LEGGINGS:
            this.leggings(consumer, ingredient, result);
            break;
         case BOOTS:
            this.boots(consumer, ingredient, result);
            break;
         default:
            throw new IllegalArgumentException("Result is not a valid armor item: [" + result + "]");
         }
      }

   }

   private void helmet(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("###").m_126130_("# #").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void chestPlate(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("# #").m_126130_("###").m_126130_("###").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void leggings(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("###").m_126130_("# #").m_126130_("# #").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void boots(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("# #").m_126130_("# #").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void sword(@NotNull Consumer<FinishedRecipe> consumer, Ingredient material, Ingredient handle, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.TOOLS, result).m_126130_("M").m_126130_("M").m_126130_("H").m_126124_('M', material).m_126124_('H', handle).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(material.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void pickaxe(@NotNull Consumer<FinishedRecipe> consumer, Ingredient material, Ingredient handle, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.TOOLS, result).m_126130_("MMM").m_126130_(" H ").m_126130_(" H ").m_126124_('M', material).m_126124_('H', handle).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(material.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void axe(@NotNull Consumer<FinishedRecipe> consumer, Ingredient material, Ingredient handle, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.TOOLS, result).m_126130_("MM").m_126130_("MH").m_126130_(" H").m_126124_('M', material).m_126124_('H', handle).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(material.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void shovel(@NotNull Consumer<FinishedRecipe> consumer, Ingredient material, Ingredient handle, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.TOOLS, result).m_126130_("M").m_126130_("H").m_126130_("H").m_126124_('M', material).m_126124_('H', handle).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(material.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void hoe(@NotNull Consumer<FinishedRecipe> consumer, Ingredient material, Ingredient handle, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.TOOLS, result).m_126130_("MM").m_126130_(" H").m_126130_(" H").m_126124_('M', material).m_126124_('H', handle).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(material.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void dragonArmorSet(@NotNull Consumer<FinishedRecipe> consumer, ItemLike material, ItemLike... results) {
      this.dragonArmorSet(consumer, Ingredient.m_43929_(new ItemLike[]{material}), results);
   }

   private void dragonArmorSet(@NotNull Consumer<FinishedRecipe> consumer, TagKey<Item> tag, ItemLike... results) {
      this.dragonArmorSet(consumer, Ingredient.m_204132_(tag), results);
   }

   private void dragonArmorSet(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike... results) {
      ItemLike[] var4 = results;
      int var5 = results.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ItemLike result = var4[var6];
         if (!(result instanceof ItemDragonArmor)) {
            throw new IllegalArgumentException("Result is not a dragon armor [" + result + "]");
         }

         ItemDragonArmor dragonArmor = (ItemDragonArmor)result;
         switch(dragonArmor.dragonSlot) {
         case 0:
            this.dragonHead(consumer, ingredient, result);
            break;
         case 1:
            this.dragonNeck(consumer, ingredient, result);
            break;
         case 2:
            this.dragonBody(consumer, ingredient, result);
            break;
         case 3:
            this.dragonTail(consumer, ingredient, result);
            break;
         default:
            throw new IllegalArgumentException("Result is not a valid dragon armor [" + result + "]");
         }
      }

   }

   private void dragonHead(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("   ").m_126130_(" ##").m_126130_("###").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void dragonNeck(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("   ").m_126130_("###").m_126130_(" ##").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void dragonBody(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("###").m_126130_("###").m_126130_("# #").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void dragonTail(@NotNull Consumer<FinishedRecipe> consumer, Ingredient ingredient, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.COMBAT, result).m_126130_("   ").m_126130_("  #").m_126130_("## ").m_126124_('#', ingredient).m_126132_("has_item", m_125977_(((ItemStack)Arrays.stream(ingredient.m_43908_()).findFirst().get()).m_41720_())).m_176498_(consumer);
   }

   private void forgeBrick(@NotNull Consumer<FinishedRecipe> consumer, ItemLike brick, TagKey<Item> scales, ItemLike result) {
      ShapedRecipeBuilder.m_246608_(RecipeCategory.BUILDING_BLOCKS, result, 4).m_126130_("SBS").m_126130_("BSB").m_126130_("SBS").m_126124_('S', Ingredient.m_204132_(scales)).m_126127_('B', brick).m_126132_("has_item", m_125977_(brick.m_5456_())).m_176498_(consumer);
   }

   private void forgeCore(@NotNull Consumer<FinishedRecipe> consumer, ItemLike brick, ItemLike heart, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.BUILDING_BLOCKS, result).m_126130_("BBB").m_126130_("BHB").m_126130_("BBB").m_126127_('H', heart).m_126127_('B', brick).m_126132_("has_item", m_125977_(brick.m_5456_())).m_176498_(consumer);
   }

   private void forgeInput(@NotNull Consumer<FinishedRecipe> consumer, ItemLike brick, TagKey<Item> material, ItemLike result) {
      ShapedRecipeBuilder.m_245327_(RecipeCategory.BUILDING_BLOCKS, result).m_126130_("BIB").m_126130_("I I").m_126130_("BIB").m_126124_('I', Ingredient.m_204132_(material)).m_126127_('B', brick).m_126132_("has_item", m_125977_(brick.m_5456_())).m_176498_(consumer);
   }

   private static ResourceLocation location(String path) {
      return new ResourceLocation("iceandfire", path);
   }

   private static String locationString(String path) {
      return "iceandfire:" + path;
   }
}
