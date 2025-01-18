package com.github.alexthe666.iceandfire.recipe;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.RegistryObject;

public class IafBannerPatterns {
   public static final DeferredRegister<BannerPattern> BANNERS;
   public static final RegistryObject<BannerPattern> PATTERN_FIRE;
   public static final RegistryObject<BannerPattern> PATTERN_ICE;
   public static final RegistryObject<BannerPattern> PATTERN_LIGHTNING;
   public static final RegistryObject<BannerPattern> PATTERN_FIRE_HEAD;
   public static final RegistryObject<BannerPattern> PATTERN_ICE_HEAD;
   public static final RegistryObject<BannerPattern> PATTERN_LIGHTNING_HEAD;
   public static final RegistryObject<BannerPattern> PATTERN_AMPHITHERE;
   public static final RegistryObject<BannerPattern> PATTERN_BIRD;
   public static final RegistryObject<BannerPattern> PATTERN_EYE;
   public static final RegistryObject<BannerPattern> PATTERN_FAE;
   public static final RegistryObject<BannerPattern> PATTERN_FEATHER;
   public static final RegistryObject<BannerPattern> PATTERN_GORGON;
   public static final RegistryObject<BannerPattern> PATTERN_HIPPOCAMPUS;
   public static final RegistryObject<BannerPattern> PATTERN_HIPPOGRYPH_HEAD;
   public static final RegistryObject<BannerPattern> PATTERN_MERMAID;
   public static final RegistryObject<BannerPattern> PATTERN_SEA_SERPENT;
   public static final RegistryObject<BannerPattern> PATTERN_TROLL;
   public static final RegistryObject<BannerPattern> PATTERN_WEEZER;
   public static final RegistryObject<BannerPattern> PATTERN_DREAD;

   static {
      BANNERS = DeferredRegister.create(Registries.f_256969_, "iceandfire");
      PATTERN_FIRE = BANNERS.register("fire", () -> {
         return new BannerPattern("iaf_fire");
      });
      PATTERN_ICE = BANNERS.register("ice", () -> {
         return new BannerPattern("iaf_ice");
      });
      PATTERN_LIGHTNING = BANNERS.register("lightning", () -> {
         return new BannerPattern("iaf_lightning");
      });
      PATTERN_FIRE_HEAD = BANNERS.register("fire_head", () -> {
         return new BannerPattern("iaf_fire_head");
      });
      PATTERN_ICE_HEAD = BANNERS.register("ice_head", () -> {
         return new BannerPattern("iaf_ice_head");
      });
      PATTERN_LIGHTNING_HEAD = BANNERS.register("lightning_head", () -> {
         return new BannerPattern("iaf_lightning_head");
      });
      PATTERN_AMPHITHERE = BANNERS.register("amphithere", () -> {
         return new BannerPattern("iaf_amphithere");
      });
      PATTERN_BIRD = BANNERS.register("bird", () -> {
         return new BannerPattern("iaf_bird");
      });
      PATTERN_EYE = BANNERS.register("eye", () -> {
         return new BannerPattern("iaf_eye");
      });
      PATTERN_FAE = BANNERS.register("fae", () -> {
         return new BannerPattern("iaf_fae");
      });
      PATTERN_FEATHER = BANNERS.register("feather", () -> {
         return new BannerPattern("iaf_feather");
      });
      PATTERN_GORGON = BANNERS.register("gorgon", () -> {
         return new BannerPattern("iaf_gorgon");
      });
      PATTERN_HIPPOCAMPUS = BANNERS.register("hippocampus", () -> {
         return new BannerPattern("iaf_hippocampus");
      });
      PATTERN_HIPPOGRYPH_HEAD = BANNERS.register("hippogryph_head", () -> {
         return new BannerPattern("iaf_hippogryph_head");
      });
      PATTERN_MERMAID = BANNERS.register("mermaid", () -> {
         return new BannerPattern("iaf_mermaid");
      });
      PATTERN_SEA_SERPENT = BANNERS.register("sea_serpent", () -> {
         return new BannerPattern("iaf_sea_serpent");
      });
      PATTERN_TROLL = BANNERS.register("troll", () -> {
         return new BannerPattern("iaf_troll");
      });
      PATTERN_WEEZER = BANNERS.register("weezer", () -> {
         return new BannerPattern("iaf_weezer");
      });
      PATTERN_DREAD = BANNERS.register("dread", () -> {
         return new BannerPattern("iaf_dread");
      });
   }
}
