package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.github.alexthe666.iceandfire.client.model.ModelPixie;
import com.github.alexthe666.iceandfire.client.render.entity.RenderPixie;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LayerPixieItem extends RenderLayer<EntityPixie, ModelPixie> {
   RenderPixie renderer;

   public LayerPixieItem(RenderPixie renderer) {
      super(renderer);
      this.renderer = renderer;
   }

   public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityPixie entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      ItemStack itemstack = entity.m_21120_(InteractionHand.MAIN_HAND);
      if (!itemstack.m_41619_()) {
         matrixStackIn.m_85836_();
         matrixStackIn.m_252880_(-0.0625F, 0.53125F, 0.21875F);
         Item item = itemstack.m_41720_();
         Minecraft minecraft = Minecraft.m_91087_();
         if (!(item instanceof BlockItem)) {
            matrixStackIn.m_252880_(-0.075F, 0.0F, -0.05F);
         } else {
            matrixStackIn.m_252880_(-0.075F, 0.0F, -0.05F);
         }

         matrixStackIn.m_252880_(0.05F, 0.55F, -0.4F);
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(200.0F));
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F));
         Minecraft.m_91087_().m_91291_().m_269128_(itemstack, ItemDisplayContext.FIXED, packedLightIn, OverlayTexture.f_118083_, matrixStackIn, bufferIn, Minecraft.m_91087_().f_91073_, 0);
         matrixStackIn.m_85849_();
      }

   }
}
