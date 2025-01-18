package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.iceandfire.client.model.ModelGorgonHead;
import com.github.alexthe666.iceandfire.client.model.ModelGorgonHeadActive;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class RenderGorgonHead extends BlockEntityWithoutLevelRenderer {
   private static final RenderType ACTIVE_TEXTURE = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/gorgon/head_active.png"), false);
   private static final RenderType INACTIVE_TEXTURE = RenderType.m_110443_(new ResourceLocation("iceandfire:textures/models/gorgon/head_inactive.png"), false);
   private static final AdvancedEntityModel ACTIVE_MODEL = new ModelGorgonHeadActive();
   private static final AdvancedEntityModel INACTIVE_MODEL = new ModelGorgonHead();

   public RenderGorgonHead(BlockEntityRenderDispatcher dispatcher, EntityModelSet set) {
      super(dispatcher, set);
   }

   public void m_108829_(ItemStack stack, @NotNull ItemDisplayContext type, @NotNull PoseStack stackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      boolean active = false;
      if (stack.m_41720_() == IafItemRegistry.GORGON_HEAD.get() && stack.m_41783_() != null && stack.m_41783_().m_128471_("Active")) {
         active = true;
      }

      AdvancedEntityModel model = active ? ACTIVE_MODEL : INACTIVE_MODEL;
      stackIn.m_85836_();
      stackIn.m_252880_(0.5F, active ? 1.5F : 1.25F, 0.5F);
      VertexConsumer ivertexbuilder = bufferIn.m_6299_(active ? ACTIVE_TEXTURE : INACTIVE_TEXTURE);
      model.m_7695_(stackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
      stackIn.m_85849_();
   }
}
