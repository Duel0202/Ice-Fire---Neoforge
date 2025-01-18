package com.github.alexthe666.iceandfire.recipe;

import com.github.alexthe666.citadel.client.model.container.JsonUtils;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforge.registries.NewRegistryEvent;
import org.jetbrains.annotations.NotNull;

public class DragonForgeRecipe implements Recipe<TileEntityDragonforge> {
   private final Ingredient input;
   private final Ingredient blood;
   private final ItemStack result;
   private final String dragonType;
   private final int cookTime;
   private final ResourceLocation recipeId;

   public DragonForgeRecipe(ResourceLocation recipeId, Ingredient input, Ingredient blood, ItemStack result, String dragonType, int cookTime) {
      this.recipeId = recipeId;
      this.input = input;
      this.blood = blood;
      this.result = result;
      this.dragonType = dragonType;
      this.cookTime = cookTime;
   }

   public Ingredient getInput() {
      return this.input;
   }

   public Ingredient getBlood() {
      return this.blood;
   }

   public int getCookTime() {
      return this.cookTime;
   }

   public String getDragonType() {
      return this.dragonType;
   }

   public boolean m_5598_() {
      return true;
   }

   public boolean matches(TileEntityDragonforge inv, @NotNull Level worldIn) {
      return this.input.test(inv.m_8020_(0)) && this.blood.test(inv.m_8020_(1)) && this.dragonType.equals(inv.getTypeID());
   }

   public boolean isValidInput(ItemStack stack) {
      return this.input.test(stack);
   }

   public boolean isValidBlood(ItemStack blood) {
      return this.blood.test(blood);
   }

   @NotNull
   public ItemStack m_8043_(RegistryAccess registryAccess) {
      return this.result;
   }

   @NotNull
   public ItemStack getResultItem() {
      return this.result;
   }

   @NotNull
   public ItemStack assemble(@NotNull TileEntityDragonforge dragonforge, RegistryAccess registryAccess) {
      return this.result;
   }

   public boolean m_8004_(int width, int height) {
      return false;
   }

   @NotNull
   public ResourceLocation m_6423_() {
      return this.recipeId;
   }

   @NotNull
   public ItemStack m_8042_() {
      return new ItemStack((ItemLike)IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get());
   }

   @NotNull
   public RecipeSerializer<?> m_7707_() {
      return (RecipeSerializer)IafRecipeSerializers.DRAGONFORGE_SERIALIZER.get();
   }

   @NotNull
   public RecipeType<?> m_6671_() {
      return (RecipeType)IafRecipeRegistry.DRAGON_FORGE_TYPE.get();
   }

   public static class Serializer extends NewRegistryEvent implements RecipeSerializer<DragonForgeRecipe> {
      @NotNull
      public DragonForgeRecipe fromJson(@NotNull ResourceLocation recipeId, @NotNull JsonObject json) {
         String dragonType = JsonUtils.getString(json, "dragon_type");
         Ingredient input = Ingredient.m_43917_(JsonUtils.getJsonObject(json, "input"));
         Ingredient blood = Ingredient.m_43917_(JsonUtils.getJsonObject(json, "blood"));
         int cookTime = JsonUtils.getInt(json, "cook_time");
         ItemStack result = ShapedRecipe.m_151274_(JsonUtils.getJsonObject(json, "result"));
         return new DragonForgeRecipe(recipeId, input, blood, result, dragonType, cookTime);
      }

      public DragonForgeRecipe fromNetwork(@NotNull ResourceLocation recipeId, FriendlyByteBuf buffer) {
         int cookTime = buffer.readInt();
         String dragonType = buffer.m_130277_();
         Ingredient input = Ingredient.m_43940_(buffer);
         Ingredient blood = Ingredient.m_43940_(buffer);
         ItemStack result = buffer.m_130267_();
         return new DragonForgeRecipe(recipeId, input, blood, result, dragonType, cookTime);
      }

      public void toNetwork(FriendlyByteBuf buffer, DragonForgeRecipe recipe) {
         buffer.writeInt(recipe.cookTime);
         buffer.m_130070_(recipe.dragonType);
         recipe.input.m_43923_(buffer);
         recipe.blood.m_43923_(buffer);
         buffer.writeItemStack(recipe.result, true);
      }
   }
}
