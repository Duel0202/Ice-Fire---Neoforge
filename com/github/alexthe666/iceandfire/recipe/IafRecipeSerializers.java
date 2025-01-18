package com.github.alexthe666.iceandfire.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;

public class IafRecipeSerializers {
   public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS;
   public static final RegistryObject<RecipeSerializer<?>> DRAGONFORGE_SERIALIZER;

   static {
      SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "iceandfire");
      DRAGONFORGE_SERIALIZER = SERIALIZERS.register("dragonforge", DragonForgeRecipe.Serializer::new);
   }
}
