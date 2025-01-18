package com.github.alexthe666.iceandfire.client.particle;

import com.github.alexthe666.iceandfire.client.model.ModelSiren;
import com.github.alexthe666.iceandfire.client.render.entity.RenderSiren;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ParticleSirenAppearance extends Particle {
   private final Model model = new ModelSiren();
   private final int sirenType;

   public ParticleSirenAppearance(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, int sirenType) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.f_107226_ = 0.0F;
      this.f_107225_ = 30;
      this.sirenType = sirenType;
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public void m_5744_(@NotNull VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      float f = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_;
      float f1 = 0.05F + 0.5F * Mth.m_14031_(f * 3.1415927F);
      PoseStack matrixstack = new PoseStack();
      matrixstack.m_252781_(renderInfo.m_253121_());
      matrixstack.m_252781_(Axis.f_252529_.m_252977_(150.0F * f - 60.0F));
      matrixstack.m_85841_(-1.0F, -1.0F, 1.0F);
      matrixstack.m_85837_(0.0D, -1.1009999513626099D, 1.5D);
      BufferSource irendertypebuffer$impl = Minecraft.m_91087_().m_91269_().m_110104_();
      VertexConsumer ivertexbuilder = irendertypebuffer$impl.m_6299_(RenderType.m_110473_(RenderSiren.getSirenOverlayTexture(this.sirenType)));
      this.model.m_7695_(matrixstack, ivertexbuilder, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, f1);
      irendertypebuffer$impl.m_109911_();
   }
}
