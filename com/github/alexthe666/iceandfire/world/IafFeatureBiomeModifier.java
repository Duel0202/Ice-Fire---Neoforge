package com.github.alexthe666.iceandfire.world;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforge.common.world.BiomeModifier;
import net.neoforge.common.world.BiomeModifier.Phase;
import net.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder;
import net.neoforge.registries.RegistryObject;
import net.neoforge.registries.ForgeRegistries.Keys;

public class IafFeatureBiomeModifier implements BiomeModifier {
   private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER;
   private final HolderSet<PlacedFeature> features;
   public final HashMap<String, Holder<PlacedFeature>> featureMap = new HashMap();

   public IafFeatureBiomeModifier(HolderSet<PlacedFeature> features) {
      this.features = features;
      this.features.forEach((feature) -> {
         this.featureMap.put(((ResourceKey)feature.m_203543_().get()).m_135782_().toString(), feature);
      });
   }

   public void modify(Holder<Biome> biome, Phase phase, Builder builder) {
      if (phase == Phase.ADD) {
         IafWorldRegistry.addFeatures(biome, this.featureMap, builder);
      }

   }

   public Codec<? extends BiomeModifier> codec() {
      return (Codec)SERIALIZER.get();
   }

   public static Codec<IafFeatureBiomeModifier> makeCodec() {
      return RecordCodecBuilder.create((config) -> {
         return config.group(PlacedFeature.f_191774_.fieldOf("features").forGetter((otherConfig) -> {
            return otherConfig.features;
         })).apply(config, IafFeatureBiomeModifier::new);
      });
   }

   static {
      SERIALIZER = RegistryObject.create(new ResourceLocation("iceandfire", "iaf_features"), Keys.BIOME_MODIFIER_SERIALIZERS, "iceandfire");
   }
}
