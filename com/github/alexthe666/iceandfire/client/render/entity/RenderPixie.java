package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelPixie;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerPixieGlow;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerPixieItem;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderPixie extends MobRenderer<EntityPixie, ModelPixie> {
   public static final ResourceLocation TEXTURE_0 = new ResourceLocation("iceandfire:textures/models/pixie/pixie_0.png");
   public static final ResourceLocation TEXTURE_1 = new ResourceLocation("iceandfire:textures/models/pixie/pixie_1.png");
   public static final ResourceLocation TEXTURE_2 = new ResourceLocation("iceandfire:textures/models/pixie/pixie_2.png");
   public static final ResourceLocation TEXTURE_3 = new ResourceLocation("iceandfire:textures/models/pixie/pixie_3.png");
   public static final ResourceLocation TEXTURE_4 = new ResourceLocation("iceandfire:textures/models/pixie/pixie_4.png");
   public static final ResourceLocation TEXTURE_5 = new ResourceLocation("iceandfire:textures/models/pixie/pixie_5.png");

   public RenderPixie(Context context) {
      super(context, new ModelPixie(), 0.2F);
      this.f_115291_.add(new LayerPixieItem(this));
      this.f_115291_.add(new LayerPixieGlow(this));
   }

   public void scale(EntityPixie LivingEntityIn, PoseStack stack, float partialTickTime) {
      stack.m_85841_(0.55F, 0.55F, 0.55F);
      if (LivingEntityIn.m_21827_()) {
         stack.m_252880_(0.0F, 0.5F, 0.0F);
      }

   }

   @NotNull
   public ResourceLocation getTextureLocation(EntityPixie pixie) {
      switch(pixie.getColor()) {
      case 1:
         return TEXTURE_1;
      case 2:
         return TEXTURE_2;
      case 3:
         return TEXTURE_3;
      case 4:
         return TEXTURE_4;
      case 5:
         return TEXTURE_5;
      default:
         return TEXTURE_0;
      }
   }
}
