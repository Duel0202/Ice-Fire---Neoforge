package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelDreadKnight;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerGenericGlowing;
import com.github.alexthe666.iceandfire.entity.EntityDreadKnight;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderDreadKnight extends MobRenderer<EntityDreadKnight, ModelDreadKnight> {
   public static final ResourceLocation TEXTURE_EYES = new ResourceLocation("iceandfire:textures/models/dread/dread_knight_eyes.png");
   public static final ResourceLocation TEXTURE_0 = new ResourceLocation("iceandfire:textures/models/dread/dread_knight_1.png");
   public static final ResourceLocation TEXTURE_1 = new ResourceLocation("iceandfire:textures/models/dread/dread_knight_2.png");
   public static final ResourceLocation TEXTURE_2 = new ResourceLocation("iceandfire:textures/models/dread/dread_knight_3.png");

   public RenderDreadKnight(Context context) {
      super(context, new ModelDreadKnight(0.0F), 0.6F);
      this.m_115326_(new LayerGenericGlowing(this, TEXTURE_EYES));
      this.m_115326_(new ItemInHandLayer(this, context.m_234598_()));
   }

   protected void scale(@NotNull EntityDreadKnight entity, PoseStack matrixStackIn, float partialTickTime) {
      matrixStackIn.m_85841_(0.95F, 0.95F, 0.95F);
   }

   @Nullable
   public ResourceLocation getTextureLocation(EntityDreadKnight entity) {
      switch(entity.getArmorVariant()) {
      case 0:
         return TEXTURE_0;
      case 1:
         return TEXTURE_1;
      case 2:
         return TEXTURE_2;
      default:
         return TEXTURE_0;
      }
   }
}
