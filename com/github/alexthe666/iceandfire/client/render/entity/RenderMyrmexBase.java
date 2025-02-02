package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexPupa;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerMyrmexItem;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderMyrmexBase extends MobRenderer<EntityMyrmexBase, AdvancedEntityModel<EntityMyrmexBase>> {
   private static final AdvancedEntityModel<EntityMyrmexBase> LARVA_MODEL = new ModelMyrmexPupa();
   private static final AdvancedEntityModel<EntityMyrmexBase> PUPA_MODEL = new ModelMyrmexPupa();
   private final AdvancedEntityModel<EntityMyrmexBase> adultModel;

   public RenderMyrmexBase(Context context, AdvancedEntityModel<EntityMyrmexBase> model, float shadowSize) {
      super(context, model, shadowSize);
      this.m_115326_(new LayerMyrmexItem(this));
      this.adultModel = model;
   }

   public void render(EntityMyrmexBase entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      if (entityIn.getGrowthStage() == 0) {
         this.f_115290_ = LARVA_MODEL;
      } else if (entityIn.getGrowthStage() == 1) {
         this.f_115290_ = PUPA_MODEL;
      } else {
         this.f_115290_ = this.adultModel;
      }

      super.m_7392_(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
   }

   protected void scale(EntityMyrmexBase myrmex, @NotNull PoseStack matrixStackIn, float partialTickTime) {
      float scale = myrmex.getModelScale();
      if (myrmex.getGrowthStage() == 0) {
         scale /= 2.0F;
      }

      if (myrmex.getGrowthStage() == 1) {
         scale /= 1.5F;
      }

      matrixStackIn.m_85841_(scale, scale, scale);
      if (myrmex.m_20159_() && myrmex.getGrowthStage() < 2) {
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(90.0F));
      }

   }

   @NotNull
   public ResourceLocation getTextureLocation(EntityMyrmexBase myrmex) {
      return myrmex.getTexture();
   }
}
