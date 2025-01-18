package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.block.BlockPixieHouse;
import com.github.alexthe666.iceandfire.client.model.ModelPixie;
import com.github.alexthe666.iceandfire.client.model.ModelPixieHouse;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import org.jetbrains.annotations.NotNull;

public class RenderPixieHouse<T extends TileEntityPixieHouse> implements BlockEntityRenderer<T> {
   private static final ModelPixieHouse MODEL = new ModelPixieHouse();
   private static ModelPixie MODEL_PIXIE;
   private static final RenderType TEXTURE_0 = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/pixie/house/pixie_house_0.png"), false);
   private static final RenderType TEXTURE_1 = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/pixie/house/pixie_house_1.png"), false);
   private static final RenderType TEXTURE_2 = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/pixie/house/pixie_house_2.png"), false);
   private static final RenderType TEXTURE_3 = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/pixie/house/pixie_house_3.png"), false);
   private static final RenderType TEXTURE_4 = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/pixie/house/pixie_house_4.png"), false);
   private static final RenderType TEXTURE_5 = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/pixie/house/pixie_house_5.png"), false);
   public BlockItem metaOverride;

   public RenderPixieHouse(Context context) {
   }

   public void render(@NotNull T entity, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      int rotation = 0;
      int meta = 0;
      if (MODEL_PIXIE == null) {
         MODEL_PIXIE = new ModelPixie();
      }

      if (entity != null && entity.m_58904_() != null && entity.m_58904_().m_8055_(entity.m_58899_()).m_60734_() instanceof BlockPixieHouse) {
         meta = TileEntityPixieHouse.getHouseTypeFromBlock(entity.m_58904_().m_8055_(entity.m_58899_()).m_60734_());
         if (!entity.m_58904_().m_8055_(entity.m_58899_()).m_61138_(BlockPixieHouse.FACING)) {
            return;
         }

         Direction facing = (Direction)entity.m_58904_().m_8055_(entity.m_58899_()).m_61143_(BlockPixieHouse.FACING);
         if (facing == Direction.NORTH) {
            rotation = 180;
         } else if (facing == Direction.EAST) {
            rotation = -90;
         } else if (facing == Direction.WEST) {
            rotation = 90;
         }
      }

      if (entity == null) {
         meta = TileEntityPixieHouse.getHouseTypeFromBlock(this.metaOverride.m_40614_());
      }

      matrixStackIn.m_85836_();
      matrixStackIn.m_252880_(0.5F, 1.501F, 0.5F);
      matrixStackIn.m_85836_();
      matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(180.0F));
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_((float)rotation));
      RenderType pixieType;
      if (entity != null && entity.m_58904_() != null && entity.hasPixie) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(0.0F, 0.95F, 0.0F);
         matrixStackIn.m_85841_(0.55F, 0.55F, 0.55F);
         matrixStackIn.m_85836_();
         pixieType = RenderJar.TEXTURE_0;
         RenderType type2 = RenderJar.TEXTURE_0_GLO;
         switch(entity.pixieType) {
         case 1:
            pixieType = RenderJar.TEXTURE_1;
            type2 = RenderJar.TEXTURE_1_GLO;
            break;
         case 2:
            pixieType = RenderJar.TEXTURE_2;
            type2 = RenderJar.TEXTURE_2_GLO;
            break;
         case 3:
            pixieType = RenderJar.TEXTURE_3;
            type2 = RenderJar.TEXTURE_3_GLO;
            break;
         case 4:
            pixieType = RenderJar.TEXTURE_4;
            type2 = RenderJar.TEXTURE_4_GLO;
            break;
         case 5:
            pixieType = RenderJar.TEXTURE_5;
            type2 = RenderJar.TEXTURE_5_GLO;
            break;
         default:
            pixieType = RenderJar.TEXTURE_0;
            type2 = RenderJar.TEXTURE_0_GLO;
         }

         matrixStackIn.m_85836_();
         MODEL_PIXIE.animateInHouse(entity);
         MODEL_PIXIE.m_7695_(matrixStackIn, bufferIn.m_6299_(pixieType), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         MODEL_PIXIE.m_7695_(matrixStackIn, bufferIn.m_6299_(type2), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
         matrixStackIn.m_85849_();
         matrixStackIn.m_85849_();
      }

      pixieType = TEXTURE_0;
      switch(meta) {
      case 0:
         pixieType = TEXTURE_0;
         break;
      case 1:
         pixieType = TEXTURE_1;
         break;
      case 2:
         pixieType = TEXTURE_2;
         break;
      case 3:
         pixieType = TEXTURE_3;
         break;
      case 4:
         pixieType = TEXTURE_4;
         break;
      case 5:
         pixieType = TEXTURE_5;
      }

      matrixStackIn.m_85836_();
      MODEL.m_7695_(matrixStackIn, bufferIn.m_6299_(pixieType), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
      matrixStackIn.m_85849_();
   }
}
