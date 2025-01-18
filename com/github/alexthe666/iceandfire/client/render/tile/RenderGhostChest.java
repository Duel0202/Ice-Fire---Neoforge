package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.client.IafClientSetup;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityGhostChest;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.state.properties.ChestType;
import org.jetbrains.annotations.NotNull;

public class RenderGhostChest extends ChestRenderer<TileEntityGhostChest> {
   private static final Material GHOST_CHEST;
   private static final Material GHOST_CHEST_LEFT;
   private static final Material GHOST_CHEST_RIGHT;

   public RenderGhostChest(Context context) {
      super(context);
   }

   private static Material getChestMaterial(ChestType chestType, Material doubleMaterial, Material leftMaterial, Material rightMaterial) {
      switch(chestType) {
      case LEFT:
         return leftMaterial;
      case RIGHT:
         return rightMaterial;
      case SINGLE:
      default:
         return doubleMaterial;
      }
   }

   @NotNull
   protected Material getMaterial(@NotNull TileEntityGhostChest tileEntity, @NotNull ChestType chestType) {
      return getChestMaterial(chestType, GHOST_CHEST, GHOST_CHEST_LEFT, GHOST_CHEST_RIGHT);
   }

   static {
      GHOST_CHEST = new Material(Sheets.f_110740_, IafClientSetup.GHOST_CHEST_LOCATION);
      GHOST_CHEST_LEFT = new Material(Sheets.f_110740_, IafClientSetup.GHOST_CHEST_LEFT_LOCATION);
      GHOST_CHEST_RIGHT = new Material(Sheets.f_110740_, IafClientSetup.GHOST_CHEST_RIGHT_LOCATION);
   }
}
