package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Fireball;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class RenderDragonFireCharge extends EntityRenderer<Fireball> {
   public boolean isFire;

   public RenderDragonFireCharge(Context context, boolean isFire) {
      super(context);
      this.isFire = isFire;
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull Fireball entity) {
      return TextureAtlas.f_118259_;
   }

   public void render(@NotNull Fireball entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      BlockRenderDispatcher blockrendererdispatcher = Minecraft.m_91087_().m_91289_();
      matrixStackIn.m_85836_();
      matrixStackIn.m_85837_(0.0D, 0.5D, 0.0D);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(-90.0F));
      matrixStackIn.m_85837_(-0.5D, -0.5D, 0.5D);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(90.0F));
      Minecraft.m_91087_().m_91289_().m_110912_(this.isFire ? Blocks.f_50450_.m_49966_() : ((Block)IafBlockRegistry.DRAGON_ICE.get()).m_49966_(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_);
      matrixStackIn.m_85849_();
   }
}
