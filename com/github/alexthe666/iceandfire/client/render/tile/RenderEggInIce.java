package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.client.model.ModelDragonEgg;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityEggInIce;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import org.jetbrains.annotations.NotNull;

public class RenderEggInIce<T extends TileEntityEggInIce> implements BlockEntityRenderer<T> {
   public RenderEggInIce(Context context) {
   }

   public void render(T egg, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      ModelDragonEgg model = new ModelDragonEgg();
      if (egg.type != null) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_85837_(0.5D, -0.800000011920929D, 0.5D);
         matrixStackIn.m_85836_();
         model.renderFrozen(egg);
         model.m_7695_(matrixStackIn, bufferIn.m_6299_(RenderPodium.getEggTexture(egg.type)), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         matrixStackIn.m_85849_();
         matrixStackIn.m_85849_();
      }

   }
}
