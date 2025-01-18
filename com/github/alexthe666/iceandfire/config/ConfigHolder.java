package com.github.alexthe666.iceandfire.config;

import net.neoforge.common.ForgeConfigSpec;
import net.neoforge.common.ForgeConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public final class ConfigHolder {
   public static final ForgeConfigSpec CLIENT_SPEC;
   public static final ForgeConfigSpec SERVER_SPEC;
   public static final ClientConfig CLIENT;
   public static final ServerConfig SERVER;

   static {
      Pair<ClientConfig, ForgeConfigSpec> specPair = (new Builder()).configure(ClientConfig::new);
      CLIENT = (ClientConfig)specPair.getLeft();
      CLIENT_SPEC = (ForgeConfigSpec)specPair.getRight();
      specPair = (new Builder()).configure(ServerConfig::new);
      SERVER = (ServerConfig)specPair.getLeft();
      SERVER_SPEC = (ForgeConfigSpec)specPair.getRight();
   }
}
