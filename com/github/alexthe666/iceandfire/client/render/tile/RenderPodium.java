package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.client.model.ModelDragonEgg;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonEgg;
import com.github.alexthe666.iceandfire.client.render.entity.RenderMyrmexEgg;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPodium;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonEgg;
import com.github.alexthe666.iceandfire.item.ItemMyrmexEgg;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import org.jetbrains.annotations.NotNull;

public class RenderPodium<T extends TileEntityPodium> implements BlockEntityRenderer<T> {
   public RenderPodium(Context context) {
   }

   protected static RenderType getEggTexture(EnumDragonEgg type) {
      RenderType var10000;
      switch(type) {
      case GREEN:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_GREEN);
         break;
      case BRONZE:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_BRONZE);
         break;
      case GRAY:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_GREY);
         break;
      case BLUE:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_BLUE);
         break;
      case WHITE:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_WHITE);
         break;
      case SAPPHIRE:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_SAPPHIRE);
         break;
      case SILVER:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_SILVER);
         break;
      case ELECTRIC:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_ELECTRIC);
         break;
      case AMYTHEST:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_AMYTHEST);
         break;
      case COPPER:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_COPPER);
         break;
      case BLACK:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_BLACK);
         break;
      default:
         var10000 = RenderType.m_110452_(RenderDragonEgg.EGG_RED);
      }

      return var10000;
   }

   public void render(@NotNull T entity, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      ModelDragonEgg model = new ModelDragonEgg();
      if (!entity.m_8020_(0).m_41619_()) {
         if (entity.m_8020_(0).m_41720_() instanceof ItemDragonEgg) {
            ItemDragonEgg item = (ItemDragonEgg)entity.m_8020_(0).m_41720_();
            matrixStackIn.m_85836_();
            matrixStackIn.m_252880_(0.5F, 0.475F, 0.5F);
            matrixStackIn.m_85836_();
            matrixStackIn.m_85836_();
            model.renderPodium();
            model.m_7695_(matrixStackIn, bufferIn.m_6299_(getEggTexture(item.type)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.m_85849_();
            matrixStackIn.m_85849_();
            matrixStackIn.m_85849_();
         } else if (entity.m_8020_(0).m_41720_() instanceof ItemMyrmexEgg) {
            boolean jungle = entity.m_8020_(0).m_41720_() == IafItemRegistry.MYRMEX_JUNGLE_EGG.get();
            matrixStackIn.m_85836_();
            matrixStackIn.m_252880_(0.5F, 0.475F, 0.5F);
            matrixStackIn.m_85836_();
            matrixStackIn.m_85836_();
            model.renderPodium();
            model.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderType.m_110452_(jungle ? RenderMyrmexEgg.EGG_JUNGLE : RenderMyrmexEgg.EGG_DESERT)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.m_85849_();
            matrixStackIn.m_85849_();
            matrixStackIn.m_85849_();
         } else if (!entity.m_8020_(0).m_41619_()) {
            matrixStackIn.m_85836_();
            float f2 = (float)entity.prevTicksExisted + (float)(entity.ticksExisted - entity.prevTicksExisted) * partialTicks;
            float f3 = Mth.m_14031_(f2 / 10.0F) * 0.1F + 0.1F;
            matrixStackIn.m_252880_(0.5F, 1.55F + f3, 0.5F);
            float f4 = f2 / 20.0F;
            matrixStackIn.m_252781_(Axis.f_252436_.m_252961_(f4));
            matrixStackIn.m_85836_();
            matrixStackIn.m_252880_(0.0F, 0.2F, 0.0F);
            matrixStackIn.m_85841_(0.65F, 0.65F, 0.65F);
            Minecraft.m_91087_().m_91291_().m_269128_(entity.m_8020_(0), ItemDisplayContext.FIXED, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn, entity.m_58904_(), 0);
            matrixStackIn.m_85849_();
            matrixStackIn.m_85849_();
         }
      }

   }
}
