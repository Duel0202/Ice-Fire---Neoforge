package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexBase;
import com.github.alexthe666.iceandfire.client.render.entity.RenderMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LayerMyrmexItem extends RenderLayer<EntityMyrmexBase, AdvancedEntityModel<EntityMyrmexBase>> {
   protected final RenderMyrmexBase livingEntityRenderer;

   public LayerMyrmexItem(RenderMyrmexBase livingEntityRendererIn) {
      super(livingEntityRendererIn);
      this.livingEntityRenderer = livingEntityRendererIn;
   }

   private void renderHeldItem(EntityMyrmexBase myrmex, ItemStack stack, ItemDisplayContext transform, HumanoidArm handSide) {
   }

   protected void translateToHand(HumanoidArm side, PoseStack stack) {
      ((ModelMyrmexBase)this.livingEntityRenderer.m_7200_()).postRenderArm(0.0F, stack);
   }

   public boolean shouldCombineTextures() {
      return false;
   }

   public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, @NotNull EntityMyrmexBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entitylivingbaseIn instanceof EntityMyrmexWorker) {
         ItemStack itemstack = entitylivingbaseIn.m_21120_(InteractionHand.MAIN_HAND);
         if (!itemstack.m_41619_()) {
            matrixStackIn.m_85836_();
            if (!itemstack.m_41619_()) {
               matrixStackIn.m_85836_();
               if (entitylivingbaseIn.m_6144_()) {
                  matrixStackIn.m_252880_(0.0F, 0.2F, 0.0F);
               }

               this.translateToHand(HumanoidArm.RIGHT, matrixStackIn);
               matrixStackIn.m_252880_(0.0F, 0.3F, -1.6F);
               if (itemstack.m_41720_() instanceof BlockItem) {
                  matrixStackIn.m_252880_(0.0F, 0.0F, 0.2F);
               } else {
                  matrixStackIn.m_252880_(0.0F, 0.2F, 0.3F);
               }

               matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(160.0F));
               matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F));
               Minecraft.m_91087_().m_91291_().m_269128_(itemstack, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.f_118083_, matrixStackIn, bufferIn, Minecraft.m_91087_().f_91073_, 0);
               matrixStackIn.m_85849_();
            }

            matrixStackIn.m_85849_();
         }
      }

   }
}
