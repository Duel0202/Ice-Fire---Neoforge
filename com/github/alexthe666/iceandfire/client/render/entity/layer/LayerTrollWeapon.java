package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.github.alexthe666.iceandfire.client.model.ModelTroll;
import com.github.alexthe666.iceandfire.client.render.entity.RenderTroll;
import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.github.alexthe666.iceandfire.entity.EntityTroll;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.jetbrains.annotations.NotNull;

public class LayerTrollWeapon extends RenderLayer<EntityTroll, ModelTroll> {
   private final RenderTroll renderer;

   public LayerTrollWeapon(RenderTroll renderer) {
      super(renderer);
      this.renderer = renderer;
   }

   public void render(EntityTroll entity, float f, float f1, float i, float f2, float f3, float f4, float f5) {
   }

   public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityTroll troll, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (troll.getWeaponType() != null && !EntityGorgon.isStoneMob(troll)) {
         RenderType tex = RenderType.m_110452_(troll.getWeaponType().TEXTURE);
         ((ModelTroll)this.m_117386_()).m_7695_(matrixStackIn, bufferIn.m_6299_(tex), packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }
}
