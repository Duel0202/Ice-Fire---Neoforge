package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerGenericGlowing;
import com.github.alexthe666.iceandfire.entity.EntityDreadHorse;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderDreadHorse extends MobRenderer<EntityDreadHorse, HorseModel<EntityDreadHorse>> {
   public static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/dread/dread_knight_horse.png");
   public static final ResourceLocation TEXTURE_EYES = new ResourceLocation("iceandfire:textures/models/dread/dread_knight_horse_eyes.png");

   public RenderDreadHorse(Context context) {
      super(context, new HorseModel(context.m_174023_(ModelLayers.f_171186_)), 0.75F);
      this.m_115326_(new LayerGenericGlowing(this, TEXTURE_EYES));
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EntityDreadHorse entity) {
      return TEXTURE;
   }
}
