package com.github.alexthe666.iceandfire.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class RenderSeaSerpentArrow extends ArrowRenderer {
   private static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/misc/sea_serpent_arrow.png");

   public RenderSeaSerpentArrow(Context context) {
      super(context);
   }

   @NotNull
   public ResourceLocation m_5478_(@NotNull Entity entity) {
      return TEXTURE;
   }
}
