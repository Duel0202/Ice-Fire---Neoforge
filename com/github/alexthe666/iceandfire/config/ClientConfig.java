package com.github.alexthe666.iceandfire.config;

import net.neoforge.common.ForgeConfigSpec.BooleanValue;
import net.neoforge.common.ForgeConfigSpec.Builder;
import net.neoforge.common.ForgeConfigSpec.DoubleValue;
import net.neoforge.common.ForgeConfigSpec.IntValue;

public class ClientConfig {
   public final BooleanValue customMainMenu;
   public final BooleanValue useVanillaFont;
   public final BooleanValue dragonAuto3rdPerson;

   public ClientConfig(Builder builder) {
      builder.push("general");
      this.customMainMenu = buildBoolean(builder, "Custom main menu", "all", true, "Whether to display the dragon on the main menu or not");
      this.dragonAuto3rdPerson = buildBoolean(builder, "Auto 3rd person when riding dragon", "all", true, "True if riding dragons should make the player take a 3rd person view automatically.");
      this.useVanillaFont = buildBoolean(builder, "Use Vanilla Font", "all", false, "Whether to use the vanilla font in the bestiary or not");
   }

   private static BooleanValue buildBoolean(Builder builder, String name, String catagory, boolean defaultValue, String comment) {
      return builder.comment(comment).translation(name).define(name, defaultValue);
   }

   private static IntValue buildInt(Builder builder, String name, String catagory, int defaultValue, int min, int max, String comment) {
      return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
   }

   private static DoubleValue buildDouble(Builder builder, String name, String catagory, double defaultValue, double min, double max, String comment) {
      return builder.comment(comment).translation(name).defineInRange(name, defaultValue, min, max);
   }
}
