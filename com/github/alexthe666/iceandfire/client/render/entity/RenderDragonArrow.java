package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.entity.EntityDragonArrow;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderDragonArrow extends ArrowRenderer<EntityDragonArrow> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/misc/dragonbone_arrow.png");

   public RenderDragonArrow(Context context) {
      super(context);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EntityDragonArrow entity) {
      return TEXTURE;
   }
}
