package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.iceandfire.config.BiomeConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public enum EnumHippogryphTypes {
   BLACK(false),
   BROWN(false),
   GRAY(false),
   CHESTNUT(false),
   CREAMY(false),
   DARK_BROWN(false),
   WHITE(false),
   RAPTOR(true),
   ALEX(true),
   DODO(true);

   public boolean developer;
   public ResourceLocation TEXTURE;
   public ResourceLocation TEXTURE_BLINK;

   private EnumHippogryphTypes(boolean developer) {
      this.developer = developer;
      String var10003 = this.name();
      this.TEXTURE = new ResourceLocation("iceandfire:textures/models/hippogryph/" + var10003.toLowerCase(Locale.ROOT) + ".png");
      var10003 = this.name();
      this.TEXTURE_BLINK = new ResourceLocation("iceandfire:textures/models/hippogryph/" + var10003.toLowerCase(Locale.ROOT) + "_blink.png");
   }

   public static EnumHippogryphTypes[] getWildTypes() {
      return new EnumHippogryphTypes[]{BLACK, BROWN, GRAY, CHESTNUT, CREAMY, DARK_BROWN, WHITE};
   }

   public static EnumHippogryphTypes getRandomType() {
      return getWildTypes()[ThreadLocalRandom.current().nextInt(getWildTypes().length - 1)];
   }

   public static EnumHippogryphTypes getBiomeType(Holder<Biome> biome) {
      List<EnumHippogryphTypes> types = new ArrayList();
      if (BiomeConfig.test(BiomeConfig.blackHippogryphBiomes, biome)) {
         types.add(BLACK);
      }

      if (BiomeConfig.test(BiomeConfig.brownHippogryphBiomes, biome)) {
         types.add(BROWN);
      }

      if (BiomeConfig.test(BiomeConfig.grayHippogryphBiomes, biome)) {
         types.add(BROWN);
      }

      if (BiomeConfig.test(BiomeConfig.chestnutHippogryphBiomes, biome)) {
         types.add(CHESTNUT);
      }

      if (BiomeConfig.test(BiomeConfig.creamyHippogryphBiomes, biome)) {
         types.add(CREAMY);
      }

      if (BiomeConfig.test(BiomeConfig.darkBrownHippogryphBiomes, biome)) {
         types.add(DARK_BROWN);
      }

      if (BiomeConfig.test(BiomeConfig.whiteHippogryphBiomes, biome)) {
         types.add(WHITE);
      }

      if (types.isEmpty()) {
         return getRandomType();
      } else {
         return types.contains(GRAY) && types.contains(CHESTNUT) ? GRAY : (EnumHippogryphTypes)types.get(ThreadLocalRandom.current().nextInt(types.size()));
      }
   }

   // $FF: synthetic method
   private static EnumHippogryphTypes[] $values() {
      return new EnumHippogryphTypes[]{BLACK, BROWN, GRAY, CHESTNUT, CREAMY, DARK_BROWN, WHITE, RAPTOR, ALEX, DODO};
   }
}
