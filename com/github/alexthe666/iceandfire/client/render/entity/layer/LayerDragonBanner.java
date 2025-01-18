package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.stream.StreamSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LayerDragonBanner extends RenderLayer<EntityDragonBase, AdvancedEntityModel<EntityDragonBase>> {
   private final RenderLayerParent<EntityDragonBase, AdvancedEntityModel<EntityDragonBase>> renderer;

   public LayerDragonBanner(RenderLayerParent<EntityDragonBase, AdvancedEntityModel<EntityDragonBase>> renderIn) {
      super(renderIn);
      this.renderer = renderIn;
   }

   public void render(PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityDragonBase entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entity.m_21120_(InteractionHand.OFF_HAND);
      matrixStackIn.m_85836_();
      if (!itemstack.m_41619_() && itemstack.m_41720_() instanceof BannerItem) {
         float f = entity.getRenderSize() / 3.0F;
         float f2 = 1.0F / f;
         matrixStackIn.m_85836_();
         this.postRender((AdvancedModelBox)StreamSupport.stream(((AdvancedEntityModel)this.renderer.m_7200_()).getAllParts().spliterator(), false).filter((cube) -> {
            return cube.boxName.equals("BodyUpper");
         }).findFirst().get(), matrixStackIn, 0.0625F);
         matrixStackIn.m_252880_(0.0F, -0.2F, 0.4F);
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(180.0F));
         matrixStackIn.m_85836_();
         matrixStackIn.m_85841_(f2, f2, f2);
         Minecraft.m_91087_().m_91291_().m_269128_(itemstack, ItemDisplayContext.NONE, packedLightIn, OverlayTexture.f_118083_, matrixStackIn, bufferIn, Minecraft.m_91087_().f_91073_, 0);
         matrixStackIn.m_85849_();
         matrixStackIn.m_85849_();
      }

      matrixStackIn.m_85849_();
   }

   protected void postRender(AdvancedModelBox renderer, PoseStack matrixStackIn, float scale) {
      if (renderer.rotateAngleX == 0.0F && renderer.rotateAngleY == 0.0F && renderer.rotateAngleZ == 0.0F) {
         if (renderer.rotationPointX != 0.0F || renderer.rotationPointY != 0.0F || renderer.offsetZ != 0.0F) {
            matrixStackIn.m_252880_(renderer.rotationPointX * scale, renderer.rotationPointY * scale, renderer.rotationPointZ * scale);
         }
      } else {
         matrixStackIn.m_252880_(renderer.rotationPointX * scale, renderer.rotationPointY * scale, renderer.rotationPointZ * scale);
         if (renderer.rotateAngleZ != 0.0F) {
            matrixStackIn.m_252781_(Axis.f_252403_.m_252961_(renderer.rotateAngleZ));
         }

         if (renderer.rotateAngleY != 0.0F) {
            matrixStackIn.m_252781_(Axis.f_252436_.m_252961_(renderer.rotateAngleY));
         }

         if (renderer.rotateAngleX != 0.0F) {
            matrixStackIn.m_252781_(Axis.f_252529_.m_252961_(renderer.rotateAngleX));
         }
      }

   }
}
