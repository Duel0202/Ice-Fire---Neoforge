package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.entity.tile.TileEntityDreadSpawner;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BaseSpawner;
import org.jetbrains.annotations.NotNull;

public class RenderDreadSpawner<T extends TileEntityDreadSpawner> implements BlockEntityRenderer<T> {
   public RenderDreadSpawner(Context context) {
   }

   public void render(TileEntityDreadSpawner tileEntityIn, float partialTicks, PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.5D, 0.0D, 0.5D);
      BaseSpawner abstractspawner = tileEntityIn.m_59801_();
      Entity entity = abstractspawner.m_253067_(tileEntityIn.m_58904_(), RandomSource.m_216327_(), tileEntityIn.m_58899_());
      if (entity != null) {
         float f = 0.53125F;
         float f1 = Math.max(entity.m_20205_(), entity.m_20206_());
         if ((double)f1 > 1.0D) {
            f /= f1;
         }

         matrixStackIn.m_85837_(0.0D, 0.4000000059604645D, 0.0D);
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_((float)Mth.m_14139_((double)partialTicks, abstractspawner.m_45474_(), abstractspawner.m_45473_()) * 10.0F));
         matrixStackIn.m_85837_(0.0D, -0.20000000298023224D, 0.0D);
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(-30.0F));
         matrixStackIn.m_85841_(f, f, f);
         Minecraft.m_91087_().m_91290_().m_114384_(entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, matrixStackIn, bufferIn, combinedLightIn);
      }

      matrixStackIn.m_85849_();
   }
}
