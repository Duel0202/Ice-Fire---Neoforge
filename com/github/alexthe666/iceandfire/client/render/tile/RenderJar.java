package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.client.model.ModelPixie;
import com.github.alexthe666.iceandfire.client.render.entity.RenderPixie;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import org.jetbrains.annotations.NotNull;

public class RenderJar<T extends TileEntityJar> implements BlockEntityRenderer<T> {
   public static final RenderType TEXTURE_0;
   public static final RenderType TEXTURE_1;
   public static final RenderType TEXTURE_2;
   public static final RenderType TEXTURE_3;
   public static final RenderType TEXTURE_4;
   public static final RenderType TEXTURE_5;
   public static final RenderType TEXTURE_0_GLO;
   public static final RenderType TEXTURE_1_GLO;
   public static final RenderType TEXTURE_2_GLO;
   public static final RenderType TEXTURE_3_GLO;
   public static final RenderType TEXTURE_4_GLO;
   public static final RenderType TEXTURE_5_GLO;
   private static ModelPixie MODEL_PIXIE;

   public RenderJar(Context context) {
   }

   public void render(@NotNull T entity, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      int meta = 0;
      boolean hasPixie = false;
      if (MODEL_PIXIE == null) {
         MODEL_PIXIE = new ModelPixie();
      }

      if (entity != null && entity.m_58904_() != null) {
         meta = entity.pixieType;
         hasPixie = entity.hasPixie;
      }

      if (hasPixie) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(0.5F, 1.501F, 0.5F);
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(180.0F));
         matrixStackIn.m_85836_();
         RenderType type = TEXTURE_0;
         RenderType typeGlow = TEXTURE_0_GLO;
         switch(meta) {
         case 1:
            type = TEXTURE_1;
            typeGlow = TEXTURE_1_GLO;
            break;
         case 2:
            type = TEXTURE_2;
            typeGlow = TEXTURE_2_GLO;
            break;
         case 3:
            type = TEXTURE_3;
            typeGlow = TEXTURE_3_GLO;
            break;
         case 4:
            type = TEXTURE_4;
            typeGlow = TEXTURE_4_GLO;
            break;
         default:
            type = TEXTURE_0;
            typeGlow = TEXTURE_0_GLO;
         }

         VertexConsumer ivertexbuilder = bufferIn.m_6299_(type);
         if (entity != null && entity.m_58904_() != null) {
            if (entity.hasProduced) {
               matrixStackIn.m_252880_(0.0F, 0.9F, 0.0F);
            } else {
               matrixStackIn.m_252880_(0.0F, 0.6F, 0.0F);
            }

            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(this.interpolateRotation(entity.prevRotationYaw, entity.rotationYaw, partialTicks)));
            matrixStackIn.m_85841_(0.5F, 0.5F, 0.5F);
            MODEL_PIXIE.animateInJar(entity.hasProduced, entity, 0.0F);
            MODEL_PIXIE.m_7695_(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            MODEL_PIXIE.m_7695_(matrixStackIn, bufferIn.m_6299_(typeGlow), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         }

         matrixStackIn.m_85849_();
         matrixStackIn.m_85849_();
      }

   }

   protected float interpolateRotation(float prevYawOffset, float yawOffset, float partialTicks) {
      float f;
      for(f = yawOffset - prevYawOffset; f < -180.0F; f += 360.0F) {
      }

      while(f >= 180.0F) {
         f -= 360.0F;
      }

      return prevYawOffset + partialTicks * f;
   }

   static {
      TEXTURE_0 = RenderType.m_110443_(RenderPixie.TEXTURE_0, false);
      TEXTURE_1 = RenderType.m_110443_(RenderPixie.TEXTURE_1, false);
      TEXTURE_2 = RenderType.m_110443_(RenderPixie.TEXTURE_2, false);
      TEXTURE_3 = RenderType.m_110443_(RenderPixie.TEXTURE_3, false);
      TEXTURE_4 = RenderType.m_110443_(RenderPixie.TEXTURE_4, false);
      TEXTURE_5 = RenderType.m_110443_(RenderPixie.TEXTURE_5, false);
      TEXTURE_0_GLO = RenderType.m_110488_(RenderPixie.TEXTURE_0);
      TEXTURE_1_GLO = RenderType.m_110488_(RenderPixie.TEXTURE_1);
      TEXTURE_2_GLO = RenderType.m_110488_(RenderPixie.TEXTURE_2);
      TEXTURE_3_GLO = RenderType.m_110488_(RenderPixie.TEXTURE_3);
      TEXTURE_4_GLO = RenderType.m_110488_(RenderPixie.TEXTURE_4);
      TEXTURE_5_GLO = RenderType.m_110488_(RenderPixie.TEXTURE_5);
   }
}
