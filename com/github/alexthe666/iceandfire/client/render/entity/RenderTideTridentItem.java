package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelTideTrident;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

public class RenderTideTridentItem extends BlockEntityWithoutLevelRenderer {
   private static final ModelTideTrident MODEL = new ModelTideTrident();

   public RenderTideTridentItem(BlockEntityRenderDispatcher p_172550_, EntityModelSet p_172551_) {
      super(p_172550_, p_172551_);
   }

   public void m_108829_(@NotNull ItemStack stack, @NotNull ItemDisplayContext type, PoseStack stackIn, @NotNull MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
      stackIn.m_252880_(0.5F, 0.5F, 0.5F);
      if (type != ItemDisplayContext.GUI && type != ItemDisplayContext.FIXED && type != ItemDisplayContext.NONE && type != ItemDisplayContext.GROUND) {
         stackIn.m_85836_();
         stackIn.m_252880_(0.0F, 0.2F, -0.15F);
         if (type.m_269069_()) {
            stackIn.m_252880_(type == ItemDisplayContext.FIRST_PERSON_LEFT_HAND ? -0.3F : 0.3F, 0.2F, -0.2F);
         } else {
            stackIn.m_252880_(0.0F, 0.6F, 0.0F);
         }

         stackIn.m_252781_(Axis.f_252529_.m_252977_(160.0F));
         VertexConsumer glintVertexBuilder = ItemRenderer.m_115222_(bufferIn, RenderType.m_110458_(RenderTideTrident.TRIDENT), false, stack.m_41790_());
         MODEL.m_7695_(stackIn, glintVertexBuilder, combinedLightIn, combinedOverlayIn, 1.0F, 1.0F, 1.0F, 1.0F);
         stackIn.m_85849_();
      } else {
         ItemStack tridentInventory = new ItemStack((ItemLike)IafItemRegistry.TIDE_TRIDENT_INVENTORY.get());
         if (stack.m_41793_()) {
            ListTag enchantments = stack.m_41783_().m_128437_("Enchantments", 10);
            tridentInventory.m_41700_("Enchantments", enchantments);
         }

         Minecraft.m_91087_().m_91291_().m_269128_(tridentInventory, type, type == ItemDisplayContext.GROUND ? combinedLightIn : 240, combinedOverlayIn, stackIn, bufferIn, Minecraft.m_91087_().f_91073_, 0);
      }

   }
}
