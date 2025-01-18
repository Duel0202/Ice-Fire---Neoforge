package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.EntityDragonSkull;
import com.github.alexthe666.iceandfire.enums.EnumDragonTextures;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class RenderDragonSkull extends EntityRenderer<EntityDragonSkull> {
   public static final float[] growth_stage_1 = new float[]{1.0F, 3.0F};
   public static final float[] growth_stage_2 = new float[]{3.0F, 7.0F};
   public static final float[] growth_stage_3 = new float[]{7.0F, 12.5F};
   public static final float[] growth_stage_4 = new float[]{12.5F, 20.0F};
   public static final float[] growth_stage_5 = new float[]{20.0F, 30.0F};
   private final TabulaModel fireDragonModel;
   private final TabulaModel lightningDragonModel;
   private final TabulaModel iceDragonModel;
   public float[][] growth_stages;

   public RenderDragonSkull(Context context, TabulaModel fireDragonModel, TabulaModel iceDragonModel, TabulaModel lightningDragonModel) {
      super(context);
      this.growth_stages = new float[][]{growth_stage_1, growth_stage_2, growth_stage_3, growth_stage_4, growth_stage_5};
      this.fireDragonModel = fireDragonModel;
      this.iceDragonModel = iceDragonModel;
      this.lightningDragonModel = lightningDragonModel;
   }

   private static void setRotationAngles(BasicModelPart cube, float rotX, float rotY, float rotZ) {
      cube.rotateAngleX = rotX;
      cube.rotateAngleY = rotY;
      cube.rotateAngleZ = rotZ;
   }

   public void render(EntityDragonSkull entity, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      TabulaModel model;
      if (entity.getDragonType() == 2) {
         model = this.lightningDragonModel;
      } else if (entity.getDragonType() == 1) {
         model = this.iceDragonModel;
      } else {
         model = this.fireDragonModel;
      }

      VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110473_(this.getTextureLocation(entity)));
      matrixStackIn.m_85836_();
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(-180.0F));
      matrixStackIn.m_252781_(Axis.f_252392_.m_252977_(-180.0F - entity.getYaw()));
      float f = 0.0625F;
      matrixStackIn.m_85841_(1.0F, 1.0F, 1.0F);
      float size = this.getRenderSize(entity) / 3.0F;
      matrixStackIn.m_85841_(size, size, size);
      matrixStackIn.m_252880_(0.0F, entity.isOnWall() ? -0.24F : -0.12F, entity.isOnWall() ? 0.4F : 0.5F);
      model.resetToDefaultPose();
      setRotationAngles(model.getCube("Head"), entity.isOnWall() ? (float)Math.toRadians(50.0D) : 0.0F, 0.0F, 0.0F);
      model.getCube("Head").render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
   }

   @NotNull
   public ResourceLocation getTextureLocation(EntityDragonSkull entity) {
      if (entity.getDragonType() == 2) {
         return EnumDragonTextures.getLightningDragonSkullTextures(entity);
      } else {
         return entity.getDragonType() == 1 ? EnumDragonTextures.getIceDragonSkullTextures(entity) : EnumDragonTextures.getFireDragonSkullTextures(entity);
      }
   }

   public float getRenderSize(EntityDragonSkull skull) {
      float step = (this.growth_stages[skull.getDragonStage() - 1][1] - this.growth_stages[skull.getDragonStage() - 1][0]) / 25.0F;
      return skull.getDragonAge() > 125 ? this.growth_stages[skull.getDragonStage() - 1][0] + step * 25.0F : this.growth_stages[skull.getDragonStage() - 1][0] + step * (float)this.getAgeFactor(skull);
   }

   private int getAgeFactor(EntityDragonSkull skull) {
      return skull.getDragonStage() > 1 ? skull.getDragonAge() - 25 * (skull.getDragonStage() - 1) : skull.getDragonAge();
   }
}
