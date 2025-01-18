package com.github.alexthe666.iceandfire.client.particle;

import com.github.alexthe666.iceandfire.client.model.ModelGhost;
import com.github.alexthe666.iceandfire.client.render.IafRenderType;
import com.github.alexthe666.iceandfire.client.render.entity.RenderGhost;
import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Camera;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class ParticleGhostAppearance extends Particle {
   private final ModelGhost model = new ModelGhost(0.0F);
   private final int ghost;
   private boolean fromLeft = false;

   public ParticleGhostAppearance(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, int ghost) {
      super(worldIn, xCoordIn, yCoordIn, zCoordIn);
      this.f_107226_ = 0.0F;
      this.f_107225_ = 15;
      this.ghost = ghost;
      this.fromLeft = worldIn.f_46441_.m_188499_();
   }

   @NotNull
   public ParticleRenderType m_7556_() {
      return ParticleRenderType.f_107433_;
   }

   public void m_5744_(@NotNull VertexConsumer buffer, @NotNull Camera renderInfo, float partialTicks) {
      float f = ((float)this.f_107224_ + partialTicks) / (float)this.f_107225_;
      float f1 = 0.05F + 0.5F * Mth.m_14031_(f * 3.1415927F);
      Entity entity = this.f_107208_.m_6815_(this.ghost);
      if (entity instanceof EntityGhost && Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.FIRST_PERSON) {
         EntityGhost ghostEntity = (EntityGhost)entity;
         PoseStack matrixstack = new PoseStack();
         matrixstack.m_252781_(renderInfo.m_253121_());
         if (this.fromLeft) {
            matrixstack.m_252781_(Axis.f_252392_.m_252977_(150.0F * f - 60.0F));
            matrixstack.m_252781_(Axis.f_252393_.m_252977_(150.0F * f - 60.0F));
         } else {
            matrixstack.m_252781_(Axis.f_252436_.m_252977_(150.0F * f - 60.0F));
            matrixstack.m_252781_(Axis.f_252403_.m_252977_(150.0F * f - 60.0F));
         }

         matrixstack.m_85841_(-1.0F, -1.0F, 1.0F);
         matrixstack.m_85837_(0.0D, 0.30000001192092896D, 1.25D);
         BufferSource irendertypebuffer$impl = Minecraft.m_91087_().m_91269_().m_110104_();
         VertexConsumer ivertexbuilder = irendertypebuffer$impl.m_6299_(IafRenderType.getGhost(RenderGhost.getGhostOverlayForType(ghostEntity.getColor())));
         this.model.setupAnim(ghostEntity, 0.0F, 0.0F, (float)entity.f_19797_ + partialTicks, 0.0F, 0.0F);
         this.model.m_7695_(matrixstack, ivertexbuilder, 240, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, f1);
         irendertypebuffer$impl.m_109911_();
      }

   }
}
