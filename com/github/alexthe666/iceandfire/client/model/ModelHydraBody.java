package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.EntityHydra;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;

public class ModelHydraBody extends ModelDragonBase<EntityHydra> {
   public AdvancedModelBox BodyUpper;
   public AdvancedModelBox BodyLower;
   public AdvancedModelBox BodySpike1;
   public AdvancedModelBox BodySpike2;
   public AdvancedModelBox Tail1;
   public AdvancedModelBox BodySpike3;
   public AdvancedModelBox Tail2;
   public AdvancedModelBox Tail3;
   public AdvancedModelBox Tail4;
   public AdvancedModelBox Tail5;
   public AdvancedModelBox TailSpike1;
   public AdvancedModelBox TailSpike2;
   public AdvancedModelBox TailSpike3;
   private final ModelAnimator animator;

   public ModelHydraBody() {
      this.texWidth = 256;
      this.texHeight = 128;
      this.BodySpike1 = new AdvancedModelBox(this, 0, 0);
      this.BodySpike1.setPos(0.0F, -1.2F, 3.0F);
      this.BodySpike1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.BodySpike1, 0.64332837F, -0.0F, 0.0F);
      this.Tail3 = new AdvancedModelBox(this, 70, 15);
      this.Tail3.setPos(0.0F, -0.1F, 7.7F);
      this.Tail3.addBox(-1.5F, -1.3F, 0.7F, 3.0F, 4.0F, 9.0F, 0.0F);
      this.setRotateAngle(this.Tail3, 0.091106184F, 0.0F, 0.0F);
      this.Tail1 = new AdvancedModelBox(this, 69, 34);
      this.Tail1.setPos(0.0F, -1.2F, 7.3F);
      this.Tail1.addBox(-2.5F, -2.0F, 1.0F, 5.0F, 5.0F, 8.0F, 0.0F);
      this.setRotateAngle(this.Tail1, 0.045553092F, 0.0F, 0.0F);
      this.TailSpike2 = new AdvancedModelBox(this, 0, 0);
      this.TailSpike2.setPos(0.0F, 0.0F, 3.0F);
      this.TailSpike2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.TailSpike2, 0.64332837F, -0.0F, 0.0F);
      this.Tail4 = new AdvancedModelBox(this, 97, 16);
      this.Tail4.setPos(0.0F, 0.3F, 8.0F);
      this.Tail4.addBox(-1.52F, -1.3F, 0.8F, 3.0F, 3.0F, 9.0F, 0.0F);
      this.Tail5 = new AdvancedModelBox(this, 42, 17);
      this.Tail5.setPos(0.0F, -0.4F, 7.5F);
      this.Tail5.addBox(-1.0F, -0.4F, 1.0F, 2.0F, 2.0F, 8.0F, 0.0F);
      this.setRotateAngle(this.Tail5, 0.091106184F, 0.0F, 0.0F);
      this.TailSpike3 = new AdvancedModelBox(this, 40, 0);
      this.TailSpike3.setPos(0.0F, 0.0F, 7.0F);
      this.TailSpike3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.TailSpike3, 0.64332837F, -0.0F, 0.0F);
      this.BodySpike3 = new AdvancedModelBox(this, 0, 0);
      this.BodySpike3.setPos(0.0F, -3.1F, 2.5F);
      this.BodySpike3.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F);
      this.setRotateAngle(this.BodySpike3, 0.64332837F, -0.0F, 0.0F);
      this.BodySpike2 = new AdvancedModelBox(this, 40, 0);
      this.BodySpike2.setPos(0.0F, -1.2F, 7.0F);
      this.BodySpike2.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.BodySpike2, 0.64332837F, -0.0F, 0.0F);
      this.BodyLower = new AdvancedModelBox(this, 103, 47);
      this.BodyLower.setPos(0.0F, 2.2F, 8.1F);
      this.BodyLower.addBox(-3.5F, -3.5F, 0.0F, 7.0F, 6.0F, 9.0F, 0.0F);
      this.setRotateAngle(this.BodyLower, -0.091106184F, -0.0F, 0.0F);
      this.TailSpike1 = new AdvancedModelBox(this, 40, 0);
      this.TailSpike1.setPos(0.0F, -0.6F, 4.2F);
      this.TailSpike1.addBox(-0.5F, -0.5F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.TailSpike1, 0.64332837F, -0.0F, 0.0F);
      this.Tail2 = new AdvancedModelBox(this, 95, 34);
      this.Tail2.setPos(0.0F, 0.5F, 7.4F);
      this.Tail2.addBox(-2.01F, -1.6F, 0.9F, 4.0F, 4.0F, 8.0F, 0.0F);
      this.BodyUpper = new AdvancedModelBox(this, 67, 47);
      this.BodyUpper.setPos(0.0F, 19.1F, -9.7F);
      this.BodyUpper.addBox(-4.5F, -1.8F, 0.0F, 9.0F, 7.0F, 9.0F, 0.0F);
      this.BodyUpper.addChild(this.BodySpike1);
      this.Tail2.addChild(this.Tail3);
      this.BodyLower.addChild(this.Tail1);
      this.Tail5.addChild(this.TailSpike2);
      this.Tail3.addChild(this.Tail4);
      this.Tail4.addChild(this.Tail5);
      this.Tail5.addChild(this.TailSpike3);
      this.BodyLower.addChild(this.BodySpike3);
      this.BodyUpper.addChild(this.BodySpike2);
      this.BodyUpper.addChild(this.BodyLower);
      this.Tail4.addChild(this.TailSpike1);
      this.Tail1.addChild(this.Tail2);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void animate(IAnimatedEntity entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.resetToDefaultPose();
      this.animator.update(entity);
   }

   public void setupAnim(EntityHydra entity, float f, float f1, float f2, float f3, float f4) {
      this.animate(entity, f, f1, f2, f3, f4, 1.0F);
      float speed_walk = 0.6F;
      float speed_idle = 0.05F;
      float degree_walk = 1.0F;
      float degree_idle = 0.25F;
      AdvancedModelBox[] TAIL = new AdvancedModelBox[]{this.BodyLower, this.Tail1, this.Tail2, this.Tail3, this.Tail4, this.Tail5};
      this.chainSwing(TAIL, speed_walk, degree_walk * 0.75F, -3.0D, f, f1);
      this.swing(this.BodyUpper, speed_walk * 1.5F, degree_walk * 0.12F, true, 3.0F, 0.0F, f, f1);
      this.swing(this.Tail5, speed_idle * 1.5F, degree_idle * 0.2F, false, 3.0F, 0.0F, f2, 1.0F);
      this.swing(this.Tail4, speed_idle * 1.5F, degree_idle * 0.2F, false, 2.0F, 0.0F, f2, 1.0F);
      this.walk(this.BodySpike1, speed_idle * 1.5F, degree_idle * 0.4F, false, 2.0F, -0.2F, f2, 1.0F);
      this.walk(this.BodySpike2, speed_idle * 1.5F, degree_idle * 0.4F, false, 3.0F, -0.2F, f2, 1.0F);
      this.walk(this.BodySpike3, speed_idle * 1.5F, degree_idle * 0.4F, false, 4.0F, -0.2F, f2, 1.0F);
      this.walk(this.TailSpike1, speed_idle * 1.5F, degree_idle * 0.4F, false, 2.0F, -0.2F, f2, 1.0F);
      this.walk(this.TailSpike2, speed_idle * 1.5F, degree_idle * 0.4F, false, 3.0F, -0.2F, f2, 1.0F);
      this.walk(this.TailSpike3, speed_idle * 1.5F, degree_idle * 0.4F, false, 4.0F, -0.2F, f2, 1.0F);
   }

   public void renderStatue(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, Entity living) {
      this.resetToDefaultPose();
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      this.resetToDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.BodyUpper);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.BodyUpper, this.BodyLower, this.BodySpike1, this.BodySpike2, this.Tail1, this.BodySpike3, this.Tail2, this.Tail3, this.Tail4, this.Tail5, this.TailSpike1, this.TailSpike2, new AdvancedModelBox[]{this.TailSpike3});
   }
}
