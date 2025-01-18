package com.github.alexthe666.iceandfire.compat.jei;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.compat.jei.firedragonforge.FireDragonForgeCategory;
import com.github.alexthe666.iceandfire.compat.jei.icedragonforge.IceDragonForgeCategory;
import com.github.alexthe666.iceandfire.compat.jei.lightningdragonforge.LightningDragonForgeCategory;
import com.github.alexthe666.iceandfire.enums.EnumSkullType;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import java.util.List;
import java.util.stream.Collectors;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class IceAndFireJEIPlugin implements IModPlugin {
   public static final ResourceLocation MOD = new ResourceLocation("iceandfire:iceandfire");
   public static final RecipeType<DragonForgeRecipe> FIRE_DRAGON_FORGE_RECIPE_TYPE = RecipeType.create("minecraft", "firedragonforge", DragonForgeRecipe.class);
   public static final RecipeType<DragonForgeRecipe> ICE_DRAGON_FORGE_RECIPE_TYPE = RecipeType.create("minecraft", "icedragonforge", DragonForgeRecipe.class);
   public static final RecipeType<DragonForgeRecipe> LIGHTNING_DRAGON_FORGE_RECIPE_TYPE = RecipeType.create("minecraft", "lightningdragonforge", DragonForgeRecipe.class);
   public static final ResourceLocation FIRE_DRAGON_FORGE_ID = new ResourceLocation("iceandfire:fire_dragon_forge");
   public static final ResourceLocation ICE_DRAGON_FORGE_ID = new ResourceLocation("iceandfire:ice_dragon_forge");
   public static final ResourceLocation LIGHTNING_DRAGON_FORGE_ID = new ResourceLocation("iceandfire:lightning_dragon_forge");

   private void addDescription(IRecipeRegistration registry, ItemStack itemStack) {
      registry.addIngredientInfo(itemStack, VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_(itemStack.m_41778_() + ".jei_desc")});
   }

   public void registerRecipes(IRecipeRegistration registry) {
      List<DragonForgeRecipe> forgeRecipeList = Minecraft.m_91087_().f_91073_.m_7465_().m_44013_((net.minecraft.world.item.crafting.RecipeType)IafRecipeRegistry.DRAGON_FORGE_TYPE.get());
      List<DragonForgeRecipe> fire = (List)forgeRecipeList.stream().filter((item) -> {
         return item.getDragonType().equals("fire");
      }).collect(Collectors.toList());
      List<DragonForgeRecipe> ice = (List)forgeRecipeList.stream().filter((item) -> {
         return item.getDragonType().equals("ice");
      }).collect(Collectors.toList());
      List<DragonForgeRecipe> lightning = (List)forgeRecipeList.stream().filter((item) -> {
         return item.getDragonType().equals("lightning");
      }).collect(Collectors.toList());
      registry.addRecipes(FIRE_DRAGON_FORGE_RECIPE_TYPE, fire);
      registry.addRecipes(ICE_DRAGON_FORGE_RECIPE_TYPE, ice);
      registry.addRecipes(LIGHTNING_DRAGON_FORGE_RECIPE_TYPE, lightning);
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.FIRE_DRAGON_BLOOD.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.ICE_DRAGON_BLOOD.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_RED.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_BRONZE.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_GRAY.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_GREEN.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_BLUE.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_WHITE.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_SAPPHIRE.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_SILVER.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_ELECTRIC.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_AMYTHEST.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_COPPER.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_BLACK.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_FIRE.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_ICE.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_LIGHTNING.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.FIRE_STEW.get()));
      this.addDescription(registry, new ItemStack((ItemLike)IafItemRegistry.FROST_STEW.get()));
      EnumSkullType[] var6 = EnumSkullType.values();
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         EnumSkullType skull = var6[var8];
         this.addDescription(registry, new ItemStack((ItemLike)skull.skull_item.get()));
      }

      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_FIRE.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_ICE.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_LIGHTNING.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_FIRE_HEAD.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_ICE_HEAD.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_LIGHTNING_HEAD.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_AMPHITHERE.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_BIRD.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_EYE.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_FAE.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_FEATHER.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_GORGON.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_HIPPOCAMPUS.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_HIPPOGRYPH_HEAD.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_MERMAID.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_SEA_SERPENT.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_TROLL.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_WEEZER.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
      registry.addIngredientInfo(((BannerPatternItem)IafItemRegistry.PATTERN_DREAD.get()).m_7968_(), VanillaTypes.ITEM_STACK, new Component[]{Component.m_237115_("item.iceandfire.custom_banner.jei_desc")});
   }

   public void registerCategories(IRecipeCategoryRegistration registry) {
      registry.addRecipeCategories(new IRecipeCategory[]{new FireDragonForgeCategory()});
      registry.addRecipeCategories(new IRecipeCategory[]{new IceDragonForgeCategory()});
      registry.addRecipeCategories(new IRecipeCategory[]{new LightningDragonForgeCategory()});
   }

   public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
      registry.addRecipeCatalyst(new ItemStack((ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get()), new RecipeType[]{FIRE_DRAGON_FORGE_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack((ItemLike)IafBlockRegistry.DRAGONFORGE_ICE_CORE.get()), new RecipeType[]{ICE_DRAGON_FORGE_RECIPE_TYPE});
      registry.addRecipeCatalyst(new ItemStack((ItemLike)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE.get()), new RecipeType[]{LIGHTNING_DRAGON_FORGE_RECIPE_TYPE});
   }

   @NotNull
   public ResourceLocation getPluginUid() {
      return MOD;
   }
}
