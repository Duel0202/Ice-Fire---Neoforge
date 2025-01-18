package com.github.alexthe666.iceandfire.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class RenderHydraArrow extends ArrowRenderer {
   private static final ResourceLocation TEXTURES = new ResourceLocation("iceandfire:textures/models/misc/hydra_arrow.png");

   public RenderHydraArrow(Context context) {
      super(context);
   }

   @NotNull
   public ResourceLocation m_5478_(@NotNull Entity entity) {
      return TEXTURES;
   }
}
