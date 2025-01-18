package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.entity.EntityGhostSword;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class RenderGhostSword extends EntityRenderer<EntityGhostSword> {
   public RenderGhostSword(Context context) {
      super(context);
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EntityGhostSword entity) {
      return TextureAtlas.f_118259_;
   }

   public void render(EntityGhostSword entityIn, float entityYaw, float partialTicks, PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      matrixStackIn.m_85836_();
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(Mth.m_14179_(partialTicks, entityIn.f_19859_, entityIn.m_146908_()) - 90.0F));
      matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_())));
      matrixStackIn.m_252880_(0.0F, 0.5F, 0.0F);
      matrixStackIn.m_85841_(2.0F, 2.0F, 2.0F);
      matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(0.0F));
      matrixStackIn.m_252781_(Axis.f_252393_.m_252977_(((float)entityIn.f_19797_ + partialTicks) * 30.0F));
      matrixStackIn.m_252880_(0.0F, -0.15F, 0.0F);
      Minecraft.m_91087_().m_91291_().m_269128_(new ItemStack((ItemLike)IafItemRegistry.GHOST_SWORD.get()), ItemDisplayContext.GROUND, 240, OverlayTexture.f_118083_, matrixStackIn, bufferIn, Minecraft.m_91087_().f_91073_, 0);
      matrixStackIn.m_85849_();
   }
}
