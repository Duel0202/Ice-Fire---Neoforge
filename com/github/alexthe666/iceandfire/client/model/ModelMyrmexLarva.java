package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;

public class ModelMyrmexLarva extends ModelDragonBase {
   public AdvancedModelBox Body2;
   public AdvancedModelBox Body3;
   public AdvancedModelBox Body1;
   public AdvancedModelBox Body4;
   public AdvancedModelBox Body5;
   public AdvancedModelBox Body4_1;
   public AdvancedModelBox Body5_1;
   public AdvancedModelBox Body4_2;
   public AdvancedModelBox Neck1;
   public AdvancedModelBox HeadBase;
   private final ModelAnimator animator;

   public ModelMyrmexLarva() {
      this.texWidth = 128;
      this.texHeight = 128;
      this.Body5 = new AdvancedModelBox(this, 82, 35);
      this.Body5.setPos(0.0F, -0.4F, 4.2F);
      this.Body5.addBox(-3.5F, -2.5F, -2.1F, 7.0F, 8.0F, 6.0F, 0.0F);
      this.setRotateAngle(this.Body5, -0.045553092F, 0.0F, 0.0F);
      this.Body3 = new AdvancedModelBox(this, 36, 73);
      this.Body3.setPos(0.0F, 0.2F, 4.1F);
      this.Body3.addBox(-4.5F, -3.4F, -1.4F, 9.0F, 9.0F, 9.0F, 0.0F);
      this.Body4_1 = new AdvancedModelBox(this, 58, 35);
      this.Body4_1.setPos(0.0F, 0.6F, 3.3F);
      this.Body4_1.addBox(-3.0F, -2.7F, -1.5F, 6.0F, 7.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.Body4_1, 0.045553092F, 0.0F, 0.0F);
      this.Neck1 = new AdvancedModelBox(this, 32, 22);
      this.Neck1.setPos(0.0F, 2.0F, -5.0F);
      this.Neck1.addBox(-2.5F, -2.0F, -3.5F, 5.0F, 5.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.Neck1, -1.6845918F, 0.0F, 0.0F);
      this.HeadBase = new AdvancedModelBox(this, 2, 2);
      this.HeadBase.setPos(0.0F, 1.2F, -3.3F);
      this.HeadBase.addBox(-4.0F, -2.51F, -8.1F, 8.0F, 6.0F, 8.0F, 0.0F);
      this.setRotateAngle(this.HeadBase, 2.5953045F, 0.0F, 0.0F);
      this.Body4_2 = new AdvancedModelBox(this, 58, 35);
      this.Body4_2.setPos(0.0F, 0.8F, 4.3F);
      this.Body4_2.addBox(-3.0F, -2.7F, -1.5F, 6.0F, 7.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.Body4_2, -0.4553564F, 0.0F, 0.0F);
      this.Body1 = new AdvancedModelBox(this, 34, 47);
      this.Body1.setPos(0.0F, -0.7F, -1.0F);
      this.Body1.addBox(-3.5F, -2.1F, -6.3F, 7.0F, 8.0F, 9.0F, 0.0F);
      this.setRotateAngle(this.Body1, 0.045553092F, 0.0F, 0.0F);
      this.Body4 = new AdvancedModelBox(this, 58, 35);
      this.Body4.setPos(0.0F, -0.4F, 7.3F);
      this.Body4.addBox(-3.0F, -2.7F, -1.5F, 6.0F, 7.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.Body4, 0.045553092F, 0.0F, 0.0F);
      this.Body5_1 = new AdvancedModelBox(this, 82, 35);
      this.Body5_1.setPos(0.0F, -0.4F, 4.2F);
      this.Body5_1.addBox(-3.5F, -2.5F, -2.1F, 7.0F, 8.0F, 6.0F, 0.0F);
      this.setRotateAngle(this.Body5_1, -0.045553092F, 0.0F, 0.0F);
      this.Body2 = new AdvancedModelBox(this, 70, 53);
      this.Body2.setPos(0.0F, 19.0F, -6.0F);
      this.Body2.addBox(-3.0F, -2.7F, -0.1F, 6.0F, 7.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.Body2, -0.045553092F, 0.0F, 0.0F);
      this.Body4.addChild(this.Body5);
      this.Body2.addChild(this.Body3);
      this.Body5.addChild(this.Body4_1);
      this.Body1.addChild(this.Neck1);
      this.Neck1.addChild(this.HeadBase);
      this.Body5_1.addChild(this.Body4_2);
      this.Body2.addChild(this.Body1);
      this.Body3.addChild(this.Body4);
      this.Body4_1.addChild(this.Body5_1);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.Body2);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.Body2, this.Body3, this.Body1, this.Body4, this.Body5, this.Body4_1, this.Body5_1, this.Body4_2, this.Neck1, this.HeadBase);
   }

   public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.resetToDefaultPose();
      this.animator.update(entity);
      if (this.animator.setAnimation(EntityMyrmexBase.ANIMATION_PUPA_WIGGLE)) {
         this.animator.startKeyframe(5);
         ModelUtils.rotate(this.animator, this.Body1, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body2, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body3, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body4, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body5, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body5_1, 0.0F, 15.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(5);
         ModelUtils.rotate(this.animator, this.Body1, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body2, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body3, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body4, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body5, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body5_1, 0.0F, -15.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(5);
         ModelUtils.rotate(this.animator, this.Body1, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body2, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body3, 0.0F, -15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body4, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body5, 0.0F, 15.0F, 0.0F);
         ModelUtils.rotate(this.animator, this.Body5_1, 0.0F, 15.0F, 0.0F);
         this.animator.endKeyframe();
         this.animator.resetKeyframe(5);
      }

   }

   public void m_6973_(Entity entity, float f, float f1, float f2, float f3, float f4) {
      this.animate((IAnimatedEntity)entity, f, f1, f2, f3, f4, 1.0F);
      this.resetToDefaultPose();
      float speed_idle = 0.025F;
      float degree_idle = 0.25F;
      AdvancedModelBox[] PARTS = new AdvancedModelBox[]{this.Body1, this.Body2, this.Body3, this.Body4, this.Body4_1, this.Body4_2, this.Body5, this.Body5_1};
      this.bob(this.Body2, speed_idle, degree_idle * 2.5F, true, f2, 1.0F);
      this.chainSwing(PARTS, speed_idle, degree_idle * 0.15F, 1.0D, f2, 1.0F);
      this.chainFlap(PARTS, speed_idle, degree_idle * 0.15F, 1.0D, f2, 1.0F);
   }

   public void renderStatue(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, Entity living) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
