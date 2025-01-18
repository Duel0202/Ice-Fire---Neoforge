package com.github.alexthe666.iceandfire.client.render.tile;

import com.github.alexthe666.iceandfire.block.BlockPixieHouse;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDreadPortal;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityGhostChest;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class IceAndFireTEISR extends BlockEntityWithoutLevelRenderer {
   private final RenderPixieHouse PIXIE_HOUSE_RENDERER;
   private final BlockEntityRenderDispatcher blockEntityRenderDispatcher;
   private final EntityModelSet entityModelSet;
   private final TileEntityGhostChest chest;
   private final TileEntityDreadPortal portal;

   public IceAndFireTEISR() {
      this(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
   }

   public IceAndFireTEISR(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
      super(dispatcher, modelSet);
      this.chest = new TileEntityGhostChest(BlockPos.f_121853_, ((Block)IafBlockRegistry.GHOST_CHEST.get()).m_49966_());
      this.portal = new TileEntityDreadPortal(BlockPos.f_121853_, ((Block)IafBlockRegistry.DREAD_PORTAL.get()).m_49966_());
      this.blockEntityRenderDispatcher = dispatcher;
      this.entityModelSet = modelSet;
      this.PIXIE_HOUSE_RENDERER = new RenderPixieHouse((Context)null);
   }

   public void m_108829_(ItemStack stack, @NotNull ItemDisplayContext type, @NotNull PoseStack stackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      if (stack.m_41720_() == ((Block)IafBlockRegistry.GHOST_CHEST.get()).m_5456_()) {
         this.blockEntityRenderDispatcher.m_112272_(this.chest, stackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      }

      if (stack.m_41720_() instanceof BlockItem && ((BlockItem)stack.m_41720_()).m_40614_() == IafBlockRegistry.DREAD_PORTAL.get()) {
         this.blockEntityRenderDispatcher.m_112272_(this.portal, stackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      }

      if (stack.m_41720_() instanceof BlockItem && ((BlockItem)stack.m_41720_()).m_40614_() instanceof BlockPixieHouse) {
         this.PIXIE_HOUSE_RENDERER.metaOverride = (BlockItem)stack.m_41720_();
         this.PIXIE_HOUSE_RENDERER.render((TileEntityPixieHouse)null, 0.0F, stackIn, bufferIn, combinedLightIn, combinedOverlayIn);
      }

   }
}
