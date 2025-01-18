package com.github.alexthe666.iceandfire.world;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

public class CustomBiomeFilter extends PlacementFilter {
   private static final CustomBiomeFilter INSTANCE = new CustomBiomeFilter();
   public static Codec<CustomBiomeFilter> CODEC = Codec.unit(() -> {
      return INSTANCE;
   });

   private CustomBiomeFilter() {
   }

   public static CustomBiomeFilter biome() {
      return INSTANCE;
   }

   protected boolean m_213917_(PlacementContext context, @NotNull RandomSource random, @NotNull BlockPos position) {
      PlacedFeature placedfeature = (PlacedFeature)context.m_191832_().orElseThrow(() -> {
         return new IllegalStateException("Tried to biome check an unregistered feature, or a feature that should not restrict the biome");
      });
      boolean hasFeature = context.m_191833_().m_223131_(context.m_191831_().m_204166_(position)).m_186658_(placedfeature);
      if (!hasFeature) {
         hasFeature = context.m_191833_().m_223131_(context.m_191831_().m_204166_(context.m_191831_().m_5452_(Types.WORLD_SURFACE_WG, position))).m_186658_(placedfeature);
      }

      return hasFeature;
   }

   @NotNull
   public PlacementModifierType<?> m_183327_() {
      return (PlacementModifierType)IafPlacementFilterRegistry.CUSTOM_BIOME_FILTER.get();
   }
}
