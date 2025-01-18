package com.github.alexthe666.iceandfire.inventory;

import java.util.function.Supplier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;

public class IafContainerRegistry {
   public static final DeferredRegister<MenuType<?>> CONTAINERS;
   public static final RegistryObject<MenuType<ContainerLectern>> IAF_LECTERN_CONTAINER;
   public static final RegistryObject<MenuType<ContainerPodium>> PODIUM_CONTAINER;
   public static final RegistryObject<MenuType<ContainerDragon>> DRAGON_CONTAINER;
   public static final RegistryObject<MenuType<ContainerHippogryph>> HIPPOGRYPH_CONTAINER;
   public static final RegistryObject<MenuType<HippocampusContainerMenu>> HIPPOCAMPUS_CONTAINER;
   public static final RegistryObject<MenuType<ContainerDragonForge>> DRAGON_FORGE_CONTAINER;

   public static <C extends AbstractContainerMenu> RegistryObject<MenuType<C>> register(Supplier<MenuType<C>> type, String name) {
      return CONTAINERS.register(name, type);
   }

   static {
      CONTAINERS = DeferredRegister.create(ForgeRegistries.MENU_TYPES, "iceandfire");
      IAF_LECTERN_CONTAINER = register(() -> {
         return new MenuType(ContainerLectern::new, FeatureFlags.f_244377_);
      }, "iaf_lectern");
      PODIUM_CONTAINER = register(() -> {
         return new MenuType(ContainerPodium::new, FeatureFlags.f_244377_);
      }, "podium");
      DRAGON_CONTAINER = register(() -> {
         return new MenuType(ContainerDragon::new, FeatureFlags.f_244377_);
      }, "dragon");
      HIPPOGRYPH_CONTAINER = register(() -> {
         return new MenuType(ContainerHippogryph::new, FeatureFlags.f_244377_);
      }, "hippogryph");
      HIPPOCAMPUS_CONTAINER = register(() -> {
         return new MenuType(HippocampusContainerMenu::new, FeatureFlags.f_244377_);
      }, "hippocampus");
      DRAGON_FORGE_CONTAINER = register(() -> {
         return new MenuType(ContainerDragonForge::new, FeatureFlags.f_244377_);
      }, "dragon_forge");
   }
}
