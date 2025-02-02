package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.HideableModelRenderer;
import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.google.common.collect.ImmutableList;

public class ModelGhost extends ModelBipedBase<EntityGhost> {
   public AdvancedModelBox robe;
   public AdvancedModelBox mask;
   public AdvancedModelBox hood;
   public AdvancedModelBox jaw;
   public AdvancedModelBox sleeveRight;
   public AdvancedModelBox robeLowerRight;
   public AdvancedModelBox robeLowerLeft;
   public AdvancedModelBox sleeveLeft;

   public ModelGhost(float modelScale) {
      this.texWidth = 128;
      this.texHeight = 64;
      this.sleeveRight = new AdvancedModelBox(this, 33, 35);
      this.sleeveRight.setPos(0.0F, -0.1F, 0.0F);
      this.sleeveRight.addBox(-2.2F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, 0.0F, modelScale);
      this.body = new HideableModelRenderer(this, 16, 16);
      this.body.setPos(0.0F, 0.0F, 0.0F);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, 0.0F, modelScale);
      this.robeLowerRight = new AdvancedModelBox(this, 48, 35);
      this.robeLowerRight.mirror = true;
      this.robeLowerRight.setPos(0.0F, 0.0F, 0.0F);
      this.robeLowerRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, modelScale);
      this.armLeft = new HideableModelRenderer(this, 40, 16);
      this.armLeft.mirror = true;
      this.armLeft.setPos(5.0F, 2.0F, -0.0F);
      this.armLeft.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, 0.0F, modelScale);
      this.setRotateAngle(this.armLeft, -1.457001F, 0.10000737F, -0.10000737F);
      this.jaw = new AdvancedModelBox(this, 98, 8);
      this.jaw.setPos(0.0F, -1.4F, 0.2F);
      this.jaw.addBox(-2.0F, 0.0F, -4.5F, 4.0F, 2.0F, 8.0F, 0.0F, 0.0F, modelScale);
      this.setRotateAngle(this.jaw, 0.13665928F, 0.0F, 0.0F);
      this.hood = new AdvancedModelBox(this, 60, 0);
      this.hood.setPos(0.0F, 0.0F, 0.0F);
      this.hood.addBox(-4.5F, -8.6F, -4.5F, 9.0F, 9.0F, 9.0F, 0.0F, 0.0F, modelScale);
      this.legLeft = new HideableModelRenderer(this, 0, 16);
      this.legLeft.mirror = true;
      this.legLeft.setPos(2.2F, 12.0F, 0.1F);
      this.legLeft.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, 0.0F, modelScale);
      this.head = new HideableModelRenderer(this, 0, 0);
      this.head.setPos(0.0F, 0.0F, 0.0F);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, 0.0F, modelScale);
      this.mask = new AdvancedModelBox(this, 40, 8);
      this.mask.setPos(0.0F, 0.0F, 0.0F);
      this.mask.addBox(-4.0F, -8.6F, -4.4F, 8.0F, 8.0F, 0.0F, 0.0F, 0.0F, modelScale);
      this.armRight = new HideableModelRenderer(this, 40, 16);
      this.armRight.setPos(-5.0F, 2.0F, 0.0F);
      this.armRight.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, 0.0F, modelScale);
      this.setRotateAngle(this.armRight, -1.457001F, -0.10000737F, 0.10000737F);
      this.robe = new AdvancedModelBox(this, 4, 34);
      this.robe.setPos(0.0F, 0.1F, 0.0F);
      this.robe.addBox(-4.5F, 0.0F, -2.5F, 9.0F, 12.0F, 5.0F, 0.0F, 0.0F, modelScale);
      this.legRight = new HideableModelRenderer(this, 0, 16);
      this.legRight.setPos(-2.3F, 12.0F, 0.1F);
      this.legRight.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, 0.0F, modelScale);
      this.robeLowerLeft = new AdvancedModelBox(this, 48, 35);
      this.robeLowerLeft.setPos(0.0F, 0.0F, 0.0F);
      this.robeLowerLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, 0.0F, modelScale);
      this.sleeveLeft = new AdvancedModelBox(this, 33, 35);
      this.sleeveLeft.mirror = true;
      this.sleeveLeft.setPos(0.0F, -0.1F, 0.0F);
      this.sleeveLeft.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, 0.0F, 0.0F, modelScale);
      this.armRight.addChild(this.sleeveRight);
      this.legRight.addChild(this.robeLowerRight);
      this.body.addChild(this.armLeft);
      this.mask.addChild(this.jaw);
      this.head.addChild(this.hood);
      this.body.addChild(this.legLeft);
      this.body.addChild(this.head);
      this.head.addChild(this.mask);
      this.body.addChild(this.armRight);
      this.body.addChild(this.robe);
      this.body.addChild(this.legRight);
      this.legLeft.addChild(this.robeLowerLeft);
      this.armLeft.addChild(this.sleeveLeft);
      this.animator = ModelAnimator.create();
      this.updateDefaultPose();
   }

   public void setupAnim(EntityGhost entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.animate(entity, f, f1, f2, f3, f4, 1.0F);
      this.faceTarget(f3, f4, 1.0F, new AdvancedModelBox[]{this.head});
      float speed_walk = 0.6F;
      float speed_idle = 0.05F;
      float degree_walk = 1.0F;
      float degree_idle = 0.25F;
      float f12 = (float)Math.toRadians(-1.2899999618530273D) + f1;
      if (f12 < 0.0F) {
         f12 = 0.0F;
      }

      if ((double)f12 > Math.toRadians(20.0D)) {
         f12 = (float)Math.toRadians(20.0D);
      }

      this.body.rotateAngleX = f12;
      this.head.rotateAngleX -= f12;
      this.armRight.rotateAngleX -= f12;
      this.armLeft.rotateAngleX -= f12;
      this.walk(this.jaw, speed_idle * 2.0F, degree_idle * 0.5F, false, 0.0F, 0.1F, f2, 1.0F);
      this.walk(this.armRight, speed_idle * 1.5F, degree_idle * 0.4F, false, 2.0F, 0.0F, f2, 1.0F);
      this.walk(this.armLeft, speed_idle * 1.5F, degree_idle * 0.4F, true, 2.0F, 0.0F, f2, 1.0F);
      this.flap(this.armRight, speed_idle * 1.5F, degree_idle * 0.2F, false, 2.0F, 0.2F, f2, 1.0F);
      this.flap(this.armRight, speed_idle * 1.5F, degree_idle * 0.2F, true, 2.0F, 0.2F, f2, 1.0F);
      this.walk(this.legLeft, speed_idle * 1.5F, degree_idle * 0.4F, false, 2.0F, 0.2F, f2, 1.0F);
      this.walk(this.legRight, speed_idle * 1.5F, degree_idle * 0.4F, false, 2.0F, 0.2F, f2, 1.0F);
      this.flap(this.body, speed_idle, degree_idle * 0.1F, true, 3.0F, 0.0F, f2, 1.0F);
      this.bob(this.body, speed_idle * 0.5F, degree_idle * 4.1F, false, f2, 1.0F);
      this.bob(this.body, speed_walk * 0.75F, degree_walk * 2.1F, false, f, f1);
   }

   public void animate(EntityGhost entity, float f, float f1, float f2, float f3, float f4, float f5) {
      this.animator.update(entity);
      if (this.animator.setAnimation(EntityGhost.ANIMATION_SCARE)) {
         this.animator.startKeyframe(5);
         this.animator.move(this.head, 0.0F, -2.0F, 0.0F);
         this.animator.move(this.armLeft, 0.0F, 1.0F, 0.0F);
         this.animator.move(this.armRight, 0.0F, 1.0F, 0.0F);
         this.rotateMinus(this.animator, this.head, 0.0F, 20.0F, 0.0F);
         this.rotateMinus(this.animator, this.jaw, 20.0F, 10.0F, 0.0F);
         this.rotateMinus(this.animator, this.armLeft, 30.0F, -12.5F, -70.0F);
         this.rotateMinus(this.animator, this.armRight, -30.0F, 12.5F, 70.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(5);
         this.animator.move(this.head, 0.0F, -1.0F, 0.0F);
         this.animator.move(this.armLeft, 0.0F, -1.0F, 0.0F);
         this.animator.move(this.armRight, 0.0F, -1.0F, 0.0F);
         this.rotateMinus(this.animator, this.head, 0.0F, -20.0F, 0.0F);
         this.rotateMinus(this.animator, this.jaw, 20.0F, -10.0F, 0.0F);
         this.rotateMinus(this.animator, this.armLeft, -20.0F, -25.0F, -140.0F);
         this.rotateMinus(this.animator, this.armRight, 20.0F, 25.0F, 140.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(5);
         this.animator.move(this.head, 0.0F, -2.0F, 0.0F);
         this.animator.move(this.armLeft, 0.0F, 1.0F, 0.0F);
         this.animator.move(this.armRight, 0.0F, 1.0F, 0.0F);
         this.rotateMinus(this.animator, this.head, 0.0F, 20.0F, 0.0F);
         this.rotateMinus(this.animator, this.jaw, 20.0F, 10.0F, 0.0F);
         this.rotateMinus(this.animator, this.armLeft, 30.0F, -25.0F, -140.0F);
         this.rotateMinus(this.animator, this.armRight, -30.0F, 25.0F, 140.0F);
         this.animator.endKeyframe();
         this.animator.startKeyframe(5);
         this.animator.move(this.head, 0.0F, -1.0F, 0.0F);
         this.animator.move(this.armLeft, 0.0F, -1.0F, 0.0F);
         this.animator.move(this.armRight, 0.0F, -1.0F, 0.0F);
         this.rotateMinus(this.animator, this.head, 0.0F, -20.0F, 0.0F);
         this.rotateMinus(this.animator, this.jaw, 20.0F, -10.0F, 0.0F);
         this.rotateMinus(this.animator, this.armLeft, -20.0F, -25.0F, -140.0F);
         this.rotateMinus(this.animator, this.armRight, 20.0F, 25.0F, 140.0F);
         this.animator.endKeyframe();
         this.animator.resetKeyframe(10);
      }

      if (this.animator.setAnimation(EntityGhost.ANIMATION_HIT)) {
         this.animator.startKeyframe(5);
         this.animator.move(this.head, 0.0F, -1.0F, 0.0F);
         this.rotateMinus(this.animator, this.body, 0.0F, 0.0F, 0.0F);
         this.rotateMinus(this.animator, this.head, -20.0F, 0.0F, 0.0F);
         this.rotateMinus(this.animator, this.jaw, 70.0F, 0.0F, 0.0F);
         this.rotateMinus(this.animator, this.armRight, -180.0F, 0.0F, -30.0F);
         this.rotateMinus(this.animator, this.armLeft, -180.0F, 0.0F, 30.0F);
         this.animator.endKeyframe();
         this.animator.resetKeyframe(5);
      }

   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.head, this.headware, this.body, this.armRight, this.armLeft, this.legRight, this.legLeft, this.robe, this.mask, this.hood, this.jaw, this.sleeveRight, new AdvancedModelBox[]{this.robeLowerRight, this.robeLowerLeft, this.sleeveLeft});
   }
}
