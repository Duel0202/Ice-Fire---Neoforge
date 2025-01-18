package com.github.alexthe666.iceandfire.item;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforge.common.ForgeTier;
import net.neoforge.common.TierSortingRegistry;

public class DragonSteelTier {
   public static final TagKey<Block> DRAGONSTEEL_TIER_TAG = BlockTags.create(new ResourceLocation("iceandfire:needs_dragonsteel"));
   public static final Tier DRAGONSTEEL_TIER_FIRE = createTierWithRepairItem(() -> {
      return Ingredient.m_43929_(new ItemLike[]{(ItemLike)IafItemRegistry.DRAGONSTEEL_FIRE_INGOT.get()});
   }, "dragonsteel_tier_fire");
   public static final Tier DRAGONSTEEL_TIER_ICE = createTierWithRepairItem(() -> {
      return Ingredient.m_43929_(new ItemLike[]{(ItemLike)IafItemRegistry.DRAGONSTEEL_ICE_INGOT.get()});
   }, "dragonsteel_tier_ice");
   public static final Tier DRAGONSTEEL_TIER_LIGHTNING = createTierWithRepairItem(() -> {
      return Ingredient.m_43929_(new ItemLike[]{(ItemLike)IafItemRegistry.DRAGONSTEEL_LIGHTNING_INGOT.get()});
   }, "dragonsteel_tier_lightning");
   public static final Tier DRAGONSTEEL_TIER_DREAD_QUEEN = createTierWithRepairItem(() -> {
      return Ingredient.m_151265_();
   }, "dragonsteel_tier_dread_queen");

   private static Tier createTierWithRepairItem(Supplier<Ingredient> ingredient, String name) {
      return TierSortingRegistry.registerTier(new ForgeTier(4, 8000, 10.0F, 21.0F, 10, DRAGONSTEEL_TIER_TAG, ingredient), new ResourceLocation("iceandfire", name), List.of(Tiers.NETHERITE), List.of());
   }
}
