package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelHippocampus;
import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;

public class RenderHippocampus extends MobRenderer<EntityHippocampus, ModelHippocampus> {
   private static final ResourceLocation VARIANT_0 = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_0.png");
   private static final ResourceLocation VARIANT_0_BLINK = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_0_blinking.png");
   private static final ResourceLocation VARIANT_1 = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_1.png");
   private static final ResourceLocation VARIANT_1_BLINK = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_1_blinking.png");
   private static final ResourceLocation VARIANT_2 = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_2.png");
   private static final ResourceLocation VARIANT_2_BLINK = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_2_blinking.png");
   private static final ResourceLocation VARIANT_3 = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_3.png");
   private static final ResourceLocation VARIANT_3_BLINK = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_3_blinking.png");
   private static final ResourceLocation VARIANT_4 = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_4.png");
   private static final ResourceLocation VARIANT_4_BLINK = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_4_blinking.png");
   private static final ResourceLocation VARIANT_5 = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_5.png");
   private static final ResourceLocation VARIANT_5_BLINK = new ResourceLocation("iceandfire:textures/models/hippocampus/hippocampus_5_blinking.png");

   public RenderHippocampus(Context context) {
      super(context, new ModelHippocampus(), 0.8F);
      this.f_115291_.add(new RenderHippocampus.LayerHippocampusRainbow(this));
      this.f_115291_.add(new RenderHippocampus.LayerHippocampusSaddle(this));
   }

   @Nullable
   public ResourceLocation getTextureLocation(EntityHippocampus entity) {
      switch(entity.getVariant()) {
      case 1:
         return entity.isBlinking() ? VARIANT_1_BLINK : VARIANT_1;
      case 2:
         return entity.isBlinking() ? VARIANT_2_BLINK : VARIANT_2;
      case 3:
         return entity.isBlinking() ? VARIANT_3_BLINK : VARIANT_3;
      case 4:
         return entity.isBlinking() ? VARIANT_4_BLINK : VARIANT_4;
      case 5:
         return entity.isBlinking() ? VARIANT_5_BLINK : VARIANT_5;
      default:
         return entity.isBlinking() ? VARIANT_0_BLINK : VARIANT_0;
      }
   }

   private class LayerHippocampusRainbow extends RenderLayer<EntityHippocampus, ModelHippocampus> {
      private final RenderHippocampus renderer;
      private final RenderType TEXTURE = RenderType.m_110482_(new ResourceLocation("iceandfire:textures/models/hippocampus/rainbow.png"));
      private final RenderType TEXTURE_BLINK = RenderType.m_110482_(new ResourceLocation("iceandfire:textures/models/hippocampus/rainbow_blink.png"));

      public LayerHippocampusRainbow(RenderHippocampus renderer) {
         super(renderer);
         this.renderer = renderer;
      }

      public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityHippocampus hippo, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
         if (hippo.m_8077_() && hippo.m_7770_().toString().toLowerCase().contains("rainbow")) {
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(hippo.isBlinking() ? this.TEXTURE_BLINK : this.TEXTURE);
            int i1 = true;
            int i = hippo.f_19797_ / 25 + hippo.m_19879_();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f = ((float)(hippo.f_19797_ % 25) + partialTicks) / 25.0F;
            float[] afloat1 = Sheep.m_29829_(DyeColor.m_41053_(k));
            float[] afloat2 = Sheep.m_29829_(DyeColor.m_41053_(l));
            ((ModelHippocampus)this.m_117386_()).m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, LivingEntityRenderer.m_115338_(hippo, 0.0F), afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f, 1.0F);
         }

      }
   }

   private class LayerHippocampusSaddle extends RenderLayer<EntityHippocampus, ModelHippocampus> {
      private final RenderHippocampus renderer;
      private final RenderType SADDLE_TEXTURE = RenderType.m_110482_(new ResourceLocation("iceandfire:textures/models/hippocampus/saddle.png"));
      private final RenderType BRIDLE = RenderType.m_110482_(new ResourceLocation("iceandfire:textures/models/hippocampus/bridle.png"));
      private final RenderType CHEST = RenderType.m_110473_(new ResourceLocation("iceandfire:textures/models/hippocampus/chest.png"));
      private final RenderType TEXTURE_DIAMOND = RenderType.m_110452_(new ResourceLocation("iceandfire:textures/models/hippocampus/armor_diamond.png"));
      private final RenderType TEXTURE_GOLD = RenderType.m_110452_(new ResourceLocation("iceandfire:textures/models/hippocampus/armor_gold.png"));
      private final RenderType TEXTURE_IRON = RenderType.m_110452_(new ResourceLocation("iceandfire:textures/models/hippocampus/armor_iron.png"));

      public LayerHippocampusSaddle(RenderHippocampus renderer) {
         super(renderer);
         this.renderer = renderer;
      }

      public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityHippocampus hippo, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
         VertexConsumer ivertexbuilder;
         if (hippo.m_6254_()) {
            ivertexbuilder = bufferIn.m_6299_(this.SADDLE_TEXTURE);
            ((ModelHippocampus)this.m_117386_()).m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (hippo.m_6254_() && hippo.m_6688_() != null) {
            ivertexbuilder = bufferIn.m_6299_(this.BRIDLE);
            ((ModelHippocampus)this.m_117386_()).m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (hippo.isChested()) {
            ivertexbuilder = bufferIn.m_6299_(this.CHEST);
            ((ModelHippocampus)this.m_117386_()).m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         }

         if (hippo.getArmor() != 0) {
            RenderType type = null;
            switch(hippo.getArmor()) {
            case 1:
               type = this.TEXTURE_IRON;
               break;
            case 2:
               type = this.TEXTURE_GOLD;
               break;
            case 3:
               type = this.TEXTURE_DIAMOND;
            }

            VertexConsumer ivertexbuilderx = bufferIn.m_6299_(type);
            ((ModelHippocampus)this.m_117386_()).m_7695_(matrixStackIn, ivertexbuilderx, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         }

      }
   }
}
