package com.github.alexthe666.iceandfire.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.RegistryObject;

public class IafPlacementFilterRegistry {
   public static final DeferredRegister<PlacementModifierType<?>> PLACEMENT_MODIFIER_TYPES;
   public static RegistryObject<PlacementModifierType<CustomBiomeFilter>> CUSTOM_BIOME_FILTER;

   static {
      PLACEMENT_MODIFIER_TYPES = DeferredRegister.create(Registries.f_256843_, "iceandfire");
      CUSTOM_BIOME_FILTER = PLACEMENT_MODIFIER_TYPES.register("biome_extended", () -> {
         return () -> {
            return CustomBiomeFilter.CODEC;
         };
      });
   }
}
