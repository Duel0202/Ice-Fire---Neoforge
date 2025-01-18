package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.client.model.ModelDeathWormGauntlet;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDeathWorm;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderDeathWormGauntlet extends BlockEntityWithoutLevelRenderer {
   private static final ModelDeathWormGauntlet MODEL = new ModelDeathWormGauntlet();

   public RenderDeathWormGauntlet(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
      super(p_172550_, p_172551_);
   }

   public void m_108829_(ItemStack stack, @NotNull ItemDisplayContext type, @NotNull PoseStack stackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      RenderType texture;
      if (stack.m_41720_() == IafItemRegistry.DEATHWORM_GAUNTLET_RED.get()) {
         texture = RenderType.m_110452_(RenderDeathWorm.TEXTURE_RED);
      } else if (stack.m_41720_() == IafItemRegistry.DEATHWORM_GAUNTLET_WHITE.get()) {
         texture = RenderType.m_110452_(RenderDeathWorm.TEXTURE_WHITE);
      } else {
         texture = RenderType.m_110452_(RenderDeathWorm.TEXTURE_YELLOW);
      }

      stackIn.m_85836_();
      stackIn.m_252880_(0.5F, 0.5F, 0.5F);
      stackIn.m_85836_();
      stackIn.m_85836_();
      MODEL.animate(stack, Minecraft.m_91087_().m_91296_());
      MODEL.m_7695_(stackIn, bufferIn.m_6299_(texture), combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      stackIn.m_85849_();
      stackIn.m_85849_();
      stackIn.m_85849_();
   }
}
