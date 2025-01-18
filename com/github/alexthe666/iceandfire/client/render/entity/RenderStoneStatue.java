package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.model.ICustomStatueModel;
import com.github.alexthe666.iceandfire.client.model.ModelHydraBody;
import com.github.alexthe666.iceandfire.client.model.ModelStonePlayer;
import com.github.alexthe666.iceandfire.client.render.IafRenderType;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerHydraHead;
import com.github.alexthe666.iceandfire.entity.EntityHydra;
import com.github.alexthe666.iceandfire.entity.EntityStoneStatue;
import com.github.alexthe666.iceandfire.entity.EntityTroll;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;

public class RenderStoneStatue extends EntityRenderer<EntityStoneStatue> {
   protected static final ResourceLocation[] DESTROY_STAGES = new ResourceLocation[]{new ResourceLocation("textures/block/destroy_stage_0.png"), new ResourceLocation("textures/block/destroy_stage_1.png"), new ResourceLocation("textures/block/destroy_stage_2.png"), new ResourceLocation("textures/block/destroy_stage_3.png"), new ResourceLocation("textures/block/destroy_stage_4.png"), new ResourceLocation("textures/block/destroy_stage_5.png"), new ResourceLocation("textures/block/destroy_stage_6.png"), new ResourceLocation("textures/block/destroy_stage_7.png"), new ResourceLocation("textures/block/destroy_stage_8.png"), new ResourceLocation("textures/block/destroy_stage_9.png")};
   private final Map<String, EntityModel> modelMap = new HashMap();
   private final Map<String, Entity> hollowEntityMap = new HashMap();
   private final Context context;

   public RenderStoneStatue(Context context) {
      super(context);
      this.context = context;
   }

   @NotNull
   public ResourceLocation getTextureLocation(@NotNull EntityStoneStatue entity) {
      return TextureAtlas.f_118259_;
   }

   protected void preRenderCallback(EntityStoneStatue entity, PoseStack matrixStackIn, float partialTickTime) {
      float scale = entity.m_6134_() < 0.01F ? 1.0F : entity.m_6134_();
      matrixStackIn.m_85841_(scale, scale, scale);
   }

   public void render(EntityStoneStatue entityIn, float entityYaw, float partialTicks, @NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn) {
      EntityModel model = new PigModel(this.context.m_174023_(ModelLayers.f_171205_));
      if (this.modelMap.get(entityIn.getTrappedEntityTypeString()) != null) {
         model = (EntityModel)this.modelMap.get(entityIn.getTrappedEntityTypeString());
      } else {
         EntityRenderer renderer = (EntityRenderer)Minecraft.m_91087_().m_91290_().f_114362_.get(entityIn.getTrappedEntityType());
         if (renderer instanceof RenderLayerParent) {
            model = ((RenderLayerParent)renderer).m_7200_();
         } else if (entityIn.getTrappedEntityType() == EntityType.f_20532_) {
            model = new ModelStonePlayer(this.context.m_174023_(ModelLayers.f_171162_));
         }

         this.modelMap.put(entityIn.getTrappedEntityTypeString(), model);
      }

      if (model != null) {
         Entity fakeEntity = null;
         if (this.hollowEntityMap.get(entityIn.getTrappedEntityTypeString()) == null) {
            Entity build = entityIn.getTrappedEntityType().m_20615_(Minecraft.m_91087_().f_91073_);
            if (build != null) {
               try {
                  build.m_20258_(entityIn.getTrappedTag());
               } catch (Exception var16) {
                  IceAndFire.LOGGER.warn("Mob " + entityIn.getTrappedEntityTypeString() + " could not build statue NBT");
               }

               fakeEntity = (Entity)this.hollowEntityMap.putIfAbsent(entityIn.getTrappedEntityTypeString(), build);
            }
         } else {
            fakeEntity = (Entity)this.hollowEntityMap.get(entityIn.getTrappedEntityTypeString());
         }

         RenderType tex = IafRenderType.getStoneMobRenderType(200.0F, 200.0F);
         if (fakeEntity instanceof EntityTroll) {
            tex = RenderType.m_110452_(((EntityTroll)fakeEntity).getTrollType().TEXTURE_STONE);
         }

         VertexConsumer ivertexbuilder = bufferIn.m_6299_(tex);
         matrixStackIn.m_85836_();
         float yaw = entityIn.f_19859_ + (entityIn.m_146908_() - entityIn.f_19859_) * partialTicks;
         boolean shouldSit = entityIn.m_20159_() && entityIn.m_20202_() != null && entityIn.m_20202_().shouldRiderSit();
         ((EntityModel)model).f_102610_ = entityIn.m_6162_();
         ((EntityModel)model).f_102609_ = shouldSit;
         ((EntityModel)model).f_102608_ = entityIn.m_21324_(partialTicks);
         if (model instanceof AdvancedEntityModel) {
            ((AdvancedEntityModel)model).resetToDefaultPose();
         } else if (fakeEntity != null) {
            ((EntityModel)model).m_6973_(fakeEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
         }

         this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
         matrixStackIn.m_252880_(0.0F, 1.5F, 0.0F);
         matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(180.0F));
         matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(yaw));
         if (model instanceof ICustomStatueModel && fakeEntity != null) {
            ((ICustomStatueModel)model).renderStatue(matrixStackIn, ivertexbuilder, packedLightIn, fakeEntity);
            if (model instanceof ModelHydraBody && fakeEntity instanceof EntityHydra) {
               LayerHydraHead.renderHydraHeads((ModelHydraBody)model, true, matrixStackIn, bufferIn, packedLightIn, (EntityHydra)fakeEntity, 0.0F, 0.0F, partialTicks, 0.0F, 0.0F, 0.0F);
            }
         } else {
            ((EntityModel)model).m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
         }

         matrixStackIn.m_85849_();
         if (entityIn.getCrackAmount() >= 1) {
            int i = Mth.m_14045_(entityIn.getCrackAmount() - 1, 0, DESTROY_STAGES.length - 1);
            RenderType crackTex = IafRenderType.getStoneCrackRenderType(DESTROY_STAGES[i]);
            VertexConsumer ivertexbuilder2 = bufferIn.m_6299_(crackTex);
            matrixStackIn.m_85836_();
            matrixStackIn.m_85836_();
            this.preRenderCallback(entityIn, matrixStackIn, partialTicks);
            matrixStackIn.m_252880_(0.0F, 1.5F, 0.0F);
            matrixStackIn.m_252781_(Axis.f_252529_.m_252977_(180.0F));
            matrixStackIn.m_252781_(Axis.f_252436_.m_252977_(yaw));
            if (model instanceof ICustomStatueModel) {
               ((ICustomStatueModel)model).renderStatue(matrixStackIn, ivertexbuilder2, packedLightIn, fakeEntity);
            } else {
               ((EntityModel)model).m_7695_(matrixStackIn, ivertexbuilder2, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
            }

            matrixStackIn.m_85849_();
            matrixStackIn.m_85849_();
         }

      }
   }
}
