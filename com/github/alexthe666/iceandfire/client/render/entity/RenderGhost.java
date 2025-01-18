package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelGhost;
import com.github.alexthe666.iceandfire.client.render.IafRenderType;
import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.Iterator;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.neoforge.client.event.RenderNameTagEvent;
import net.neoforge.client.event.RenderLivingEvent.Post;
import net.neoforge.client.event.RenderLivingEvent.Pre;
import net.neoforge.common.MinecraftForge;
import net.neoforge.eventbus.api.Event.Result;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class RenderGhost extends MobRenderer<EntityGhost, ModelGhost> {
   public static final ResourceLocation TEXTURE_0 = new ResourceLocation("iceandfire:textures/models/ghost/ghost_white.png");
   public static final ResourceLocation TEXTURE_1 = new ResourceLocation("iceandfire:textures/models/ghost/ghost_blue.png");
   public static final ResourceLocation TEXTURE_2 = new ResourceLocation("iceandfire:textures/models/ghost/ghost_green.png");
   public static final ResourceLocation TEXTURE_SHOPPING_LIST = new ResourceLocation("iceandfire:textures/models/ghost/haunted_shopping_list.png");

   public RenderGhost(Context renderManager) {
      super(renderManager, new ModelGhost(0.0F), 0.55F);
   }

   public static ResourceLocation getGhostOverlayForType(int ghost) {
      switch(ghost) {
      case -1:
         return TEXTURE_SHOPPING_LIST;
      case 0:
      default:
         return TEXTURE_0;
      case 1:
         return TEXTURE_1;
      case 2:
         return TEXTURE_2;
      }
   }

   public void render(@NotNull EntityGhost entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      this.f_114477_ = 0.0F;
      if (!MinecraftForge.EVENT_BUS.post(new Pre(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn))) {
         matrixStackIn.m_85836_();
         ((ModelGhost)this.f_115290_).f_102608_ = this.m_115342_(entityIn, partialTicks);
         boolean shouldSit = entityIn.m_20159_() && entityIn.m_20202_() != null && entityIn.m_20202_().shouldRiderSit();
         ((ModelGhost)this.f_115290_).f_102609_ = shouldSit;
         ((ModelGhost)this.f_115290_).f_102610_ = entityIn.m_6162_();
         float f = Mth.m_14189_(partialTicks, entityIn.f_20884_, entityIn.f_20883_);
         float f1 = Mth.m_14189_(partialTicks, entityIn.f_20886_, entityIn.f_20885_);
         float f2 = f1 - f;
         float f7;
         if (shouldSit && entityIn.m_20202_() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entityIn.m_20202_();
            f = Mth.m_14189_(partialTicks, livingentity.f_20884_, livingentity.f_20883_);
            f2 = f1 - f;
            f7 = Mth.m_14177_(f2);
            if (f7 < -85.0F) {
               f7 = -85.0F;
            }

            if (f7 >= 85.0F) {
               f7 = 85.0F;
            }

            f = f1 - f7;
            if (f7 * f7 > 2500.0F) {
               f += f7 * 0.2F;
            }

            f2 = f1 - f;
         }

         float f6 = Mth.m_14179_(partialTicks, entityIn.f_19860_, entityIn.m_146909_());
         float f8;
         if (entityIn.m_20089_() == Pose.SLEEPING) {
            Direction direction = entityIn.m_21259_();
            if (direction != null) {
               f8 = entityIn.m_20236_(Pose.STANDING) - 0.1F;
               matrixStackIn.m_85837_((double)((float)(-direction.m_122429_()) * f8), 0.0D, (double)((float)(-direction.m_122431_()) * f8));
            }
         }

         f7 = this.m_6930_(entityIn, partialTicks);
         this.m_7523_(entityIn, matrixStackIn, f7, f, partialTicks);
         matrixStackIn.m_85841_(-1.0F, -1.0F, 1.0F);
         this.scale(entityIn, matrixStackIn, partialTicks);
         matrixStackIn.m_85837_(0.0D, -1.5010000467300415D, 0.0D);
         f8 = 0.0F;
         float f5 = 0.0F;
         if (!shouldSit && entityIn.m_6084_()) {
            f8 = entityIn.f_267362_.m_267731_();
            f5 = entityIn.f_267362_.m_267756_();
            if (entityIn.m_6162_()) {
               f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
               f8 = 1.0F;
            }
         }

         ((ModelGhost)this.f_115290_).m_6839_(entityIn, f5, f8, partialTicks);
         ((ModelGhost)this.f_115290_).setupAnim(entityIn, f5, f8, f7, f2, f6);
         float alphaForRender = this.getAlphaForRender(entityIn, partialTicks);
         RenderType rendertype = entityIn.isDaytimeMode() ? IafRenderType.getGhostDaytime(this.getTextureLocation(entityIn)) : IafRenderType.getGhost(this.getTextureLocation(entityIn));
         if (rendertype != null && !entityIn.m_20145_()) {
            VertexConsumer ivertexbuilder = bufferIn.m_6299_(rendertype);
            int i = m_115338_(entityIn, this.m_6931_(entityIn, partialTicks));
            if (entityIn.isHauntedShoppingList()) {
               matrixStackIn.m_85836_();
               matrixStackIn.m_252880_(0.0F, 0.8F + Mth.m_14031_(((float)entityIn.f_19797_ + partialTicks) * 0.15F) * 0.1F, 0.0F);
               matrixStackIn.m_85841_(0.6F, 0.6F, 0.6F);
               matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F));
               matrixStackIn.m_85836_();
               com.mojang.blaze3d.vertex.PoseStack.Pose matrixstack$entry = matrixStackIn.m_85850_();
               Matrix4f matrix4f = matrixstack$entry.m_252922_();
               Matrix3f matrix3f = matrixstack$entry.m_252943_();
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, -2, 0, 1.0F, 0.0F, 0, 1, 0, 240);
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, -2, 0, 0.5F, 0.0F, 0, 1, 0, 240);
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, 2, 0, 0.5F, 1.0F, 0, 1, 0, 240);
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, 2, 0, 1.0F, 1.0F, 0, 1, 0, 240);
               matrixStackIn.m_85849_();
               matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(180.0F));
               matrixStackIn.m_85836_();
               matrixstack$entry = matrixStackIn.m_85850_();
               matrix4f = matrixstack$entry.m_252922_();
               matrix3f = matrixstack$entry.m_252943_();
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, -2, 0, 0.0F, 0.0F, 0, 1, 0, 240);
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, -2, 0, 0.5F, 0.0F, 0, 1, 0, 240);
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), 1, 2, 0, 0.5F, 1.0F, 0, 1, 0, 240);
               this.drawVertex(matrix4f, matrix3f, ivertexbuilder, i, (int)(alphaForRender * 255.0F), -1, 2, 0, 0.0F, 1.0F, 0, 1, 0, 240);
               matrixStackIn.m_85849_();
               matrixStackIn.m_85849_();
            } else {
               ((ModelGhost)this.f_115290_).m_7695_(matrixStackIn, ivertexbuilder, 240, i, 1.0F, 1.0F, 1.0F, alphaForRender);
            }
         }

         if (!entityIn.m_5833_()) {
            Iterator var24 = this.f_115291_.iterator();

            while(var24.hasNext()) {
               RenderLayer<EntityGhost, ModelGhost> layerrenderer = (RenderLayer)var24.next();
               layerrenderer.m_6494_(matrixStackIn, bufferIn, packedLightIn, entityIn, f5, f8, partialTicks, f7, f2, f6);
            }
         }

         matrixStackIn.m_85849_();
         RenderNameTagEvent renderNameplateEvent = new RenderNameTagEvent(entityIn, entityIn.m_5446_(), this, matrixStackIn, bufferIn, packedLightIn, partialTicks);
         MinecraftForge.EVENT_BUS.post(renderNameplateEvent);
         if (renderNameplateEvent.getResult() != Result.DENY && (renderNameplateEvent.getResult() == Result.ALLOW || this.m_6512_(entityIn))) {
            this.m_7649_(entityIn, renderNameplateEvent.getContent(), matrixStackIn, bufferIn, packedLightIn);
         }

         MinecraftForge.EVENT_BUS.post(new Post(entityIn, this, partialTicks, matrixStackIn, bufferIn, packedLightIn));
      }
   }

   protected float getFlipDegrees(@NotNull EntityGhost ghost) {
      return 0.0F;
   }

   public float getAlphaForRender(EntityGhost entityIn, float partialTicks) {
      return entityIn.isDaytimeMode() ? Mth.m_14036_((float)(101 - Math.min(entityIn.getDaytimeCounter(), 100)) / 100.0F, 0.0F, 1.0F) : Mth.m_14036_((Mth.m_14031_(((float)entityIn.f_19797_ + partialTicks) * 0.1F) + 1.0F) * 0.5F + 0.1F, 0.0F, 1.0F);
   }

   public void scale(@NotNull EntityGhost LivingEntityIn, @NotNull PoseStack stack, float partialTickTime) {
   }

   @NotNull
   public ResourceLocation getTextureLocation(EntityGhost ghost) {
      switch(ghost.getColor()) {
      case -1:
         return TEXTURE_SHOPPING_LIST;
      case 0:
      default:
         return TEXTURE_0;
      case 1:
         return TEXTURE_1;
      case 2:
         return TEXTURE_2;
      }
   }

   public void drawVertex(Matrix4f stack, Matrix3f normal, VertexConsumer builder, int packedRed, int alphaInt, int x, int y, int z, float u, float v, int lightmap, int lightmap3, int lightmap2, int lightmap4) {
      builder.m_252986_(stack, (float)x, (float)y, (float)z).m_6122_(255, 255, 255, alphaInt).m_7421_(u, v).m_86008_(packedRed).m_85969_(lightmap4).m_252939_(normal, (float)lightmap, (float)lightmap2, (float)lightmap3).m_5752_();
   }
}
