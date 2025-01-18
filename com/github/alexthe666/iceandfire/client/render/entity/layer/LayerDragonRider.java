package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.client.ClientProxy;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityDreadQueen;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import java.util.Iterator;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class LayerDragonRider extends RenderLayer<EntityDragonBase, AdvancedEntityModel<EntityDragonBase>> {
   private final MobRenderer render;
   private final boolean excludeDreadQueenMob;

   public LayerDragonRider(MobRenderer renderIn, boolean excludeDreadQueenMob) {
      super(renderIn);
      this.render = renderIn;
      this.excludeDreadQueenMob = excludeDreadQueenMob;
   }

   public void render(PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityDragonBase dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      matrixStackIn.m_85836_();
      if (!dragon.m_20197_().isEmpty()) {
         float dragonScale = dragon.getRenderSize() / 3.0F;
         Iterator var12 = dragon.m_20197_().iterator();

         while(var12.hasNext()) {
            Entity passenger = (Entity)var12.next();
            boolean prey = dragon.m_6688_() == null || dragon.m_6688_().m_19879_() != passenger.m_19879_();
            if (this.excludeDreadQueenMob && passenger instanceof EntityDreadQueen) {
               prey = false;
            }

            ClientProxy.currentDragonRiders.remove(passenger.m_20148_());
            float riderRot = passenger.f_19859_ + (passenger.m_146908_() - passenger.f_19859_) * partialTicks;
            int animationTicks = 0;
            if (dragon.getAnimation() == EntityDragonBase.ANIMATION_SHAKEPREY) {
               animationTicks = dragon.getAnimationTick();
            }

            if (animationTicks == 0 || animationTicks >= 15) {
               this.translateToBody(matrixStackIn);
            }

            if (!prey) {
               matrixStackIn.m_252880_(0.0F, -0.01F * dragonScale, -0.035F * dragonScale);
            } else if (animationTicks != 0 && animationTicks < 15 && !dragon.isFlying()) {
               matrixStackIn.m_252880_(0.0F, 0.555F * dragonScale, -0.5F * dragonScale);
            } else {
               this.translateToHead(matrixStackIn);
               this.offsetPerDragonType(dragon.dragonType, matrixStackIn);
               EntityRenderer render = Minecraft.m_91087_().m_91290_().m_114382_(passenger);
               EntityModel modelBase = null;
               if (render instanceof MobRenderer) {
                  modelBase = ((MobRenderer)render).m_7200_();
               }

               if ((passenger.m_20206_() > passenger.m_20205_() || modelBase instanceof HumanoidModel) && !(modelBase instanceof QuadrupedModel) && !(modelBase instanceof HorseModel)) {
                  matrixStackIn.m_252880_(-0.15F * passenger.m_20206_(), 0.1F * dragonScale - 0.1F * passenger.m_20206_(), -0.1F * dragonScale - 0.1F * passenger.m_20205_());
                  matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(90.0F));
                  matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(45.0F));
               } else {
                  boolean horse = modelBase instanceof HorseModel;
                  matrixStackIn.m_252880_((horse ? -0.08F : -0.15F) * passenger.m_20205_(), 0.1F * dragonScale - 0.15F * passenger.m_20205_(), -0.1F * dragonScale - 0.1F * passenger.m_20205_());
                  matrixStackIn.m_252781_(Axis.f_252495_.m_252977_(90.0F));
               }
            }

            matrixStackIn.m_85836_();
            matrixStackIn.m_252781_(Axis.f_252403_.m_252977_(180.0F));
            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(riderRot + 180.0F));
            matrixStackIn.m_85841_(1.0F / dragonScale, 1.0F / dragonScale, 1.0F / dragonScale);
            matrixStackIn.m_252880_(0.0F, -0.25F, 0.0F);
            this.renderEntity(passenger, 0, 0, 0, 0.0F, partialTicks, matrixStackIn, bufferIn, packedLightIn);
            matrixStackIn.m_85849_();
            ClientProxy.currentDragonRiders.add(passenger.m_20148_());
         }
      }

      matrixStackIn.m_85849_();
   }

   protected void translateToBody(PoseStack stack) {
      this.postRender(((TabulaModel)this.render.m_7200_()).getCube("BodyUpper"), stack, 0.0625F);
      this.postRender(((TabulaModel)this.render.m_7200_()).getCube("Neck1"), stack, 0.0625F);
   }

   protected void translateToHead(PoseStack stack) {
      this.postRender(((TabulaModel)this.render.m_7200_()).getCube("Neck2"), stack, 0.0625F);
      this.postRender(((TabulaModel)this.render.m_7200_()).getCube("Neck3"), stack, 0.0625F);
      this.postRender(((TabulaModel)this.render.m_7200_()).getCube("Head"), stack, 0.0625F);
   }

   protected void postRender(AdvancedModelBox renderer, PoseStack matrixStackIn, float scale) {
      if (renderer.rotateAngleX == 0.0F && renderer.rotateAngleY == 0.0F && renderer.rotateAngleZ == 0.0F) {
         if (renderer.rotationPointX != 0.0F || renderer.rotationPointY != 0.0F || renderer.rotationPointZ != 0.0F) {
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

   private void offsetPerDragonType(DragonType dragonType, PoseStack stackIn) {
      if (dragonType == DragonType.LIGHTNING) {
         stackIn.m_252880_(0.1F, -0.2F, -0.1F);
      }

   }

   public <E extends Entity> void renderEntity(E entityIn, int x, int y, int z, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
      EntityRenderer<? super E> render = null;
      EntityRenderDispatcher manager = Minecraft.m_91087_().m_91290_();

      try {
         render = manager.m_114382_(entityIn);
         if (render != null) {
            try {
               render.m_7392_(entityIn, 0.0F, partialTicks, matrixStack, bufferIn, packedLight);
            } catch (Throwable var16) {
               throw new ReportedException(CrashReport.m_127521_(var16, "Rendering entity in world"));
            }
         }

      } catch (Throwable var17) {
         CrashReport crashreport = CrashReport.m_127521_(var17, "Rendering entity in world");
         CrashReportCategory crashreportcategory = crashreport.m_127514_("Entity being rendered");
         entityIn.m_7976_(crashreportcategory);
         CrashReportCategory crashreportcategory1 = crashreport.m_127514_("Renderer details");
         crashreportcategory1.m_128159_("Assigned renderer", render);
         crashreportcategory1.m_128159_("Location", new BlockPos(x, y, z));
         crashreportcategory1.m_128159_("Rotation", yaw);
         crashreportcategory1.m_128159_("Delta", partialTicks);
         throw new ReportedException(crashreport);
      }
   }

   public boolean shouldCombineTextures() {
      return false;
   }
}
