package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.inventory.IafContainerRegistry;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.inventory.MenuType;

public class IafGuiRegistry {
   public static void register() {
      MenuScreens.m_96206_((MenuType)IafContainerRegistry.IAF_LECTERN_CONTAINER.get(), GuiLectern::new);
      MenuScreens.m_96206_((MenuType)IafContainerRegistry.PODIUM_CONTAINER.get(), GuiPodium::new);
      MenuScreens.m_96206_((MenuType)IafContainerRegistry.DRAGON_CONTAINER.get(), GuiDragon::new);
      MenuScreens.m_96206_((MenuType)IafContainerRegistry.HIPPOGRYPH_CONTAINER.get(), GuiHippogryph::new);
      MenuScreens.m_96206_((MenuType)IafContainerRegistry.HIPPOCAMPUS_CONTAINER.get(), GuiHippocampus::new);
      MenuScreens.m_96206_((MenuType)IafContainerRegistry.DRAGON_FORGE_CONTAINER.get(), GuiDragonForge::new);
   }
}
