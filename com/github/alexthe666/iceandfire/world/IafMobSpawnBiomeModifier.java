package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.neoforge.common.world.BiomeModifier;
import net.neoforge.common.world.BiomeModifier.Phase;
import net.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforge.registries.RegistryObject;
import net.neoforge.registries.ForgeRegistries.Keys;

public class IafMobSpawnBiomeModifier implements BiomeModifier {
   private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER;

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         IafEntityRegistry.addSpawners(biome, builder);
      }

   }

   public Codec<? extends BiomeModifier> codec() {
      return (Codec)SERIALIZER.get();
   }

   public static Codec<IafMobSpawnBiomeModifier> makeCodec() {
      return Codec.unit(IafMobSpawnBiomeModifier::new);
   }

   static {
      SERIALIZER = RegistryObject.create(new ResourceLocation("iceandfire", "iaf_mob_spawns"), Keys.BIOME_MODIFIER_SERIALIZERS, "iceandfire");
   }
}
