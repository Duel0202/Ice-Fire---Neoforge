package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.HideableModelRenderer;
import com.github.alexthe666.iceandfire.entity.EntityDreadQueen;
import net.minecraft.client.model.HumanoidModel.ArmPose;

public class ModelDreadQueen extends ModelDreadBase<EntityDreadQueen> {
   public HideableModelRenderer chestplate;
   public HideableModelRenderer cloak;
   public HideableModelRenderer necklace;
   public HideableModelRenderer sleeveRight;
   public HideableModelRenderer robeLowerRight;
   public HideableModelRenderer sleeveLeft;
   public HideableModelRenderer robeLowerLeft;

   public ModelDreadQueen(float modelScale) {
      this.texWidth = 128;
      this.texHeight = 64;
      this.leftArmPose = ArmPose.EMPTY;
      this.rightArmPose = ArmPose.EMPTY;
      this.body = new HideableModelRenderer(this, 16, 16);
      this.body.setPos(0.0F, 0.0F, 0.0F);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelScale);
      this.headware = new HideableModelRenderer(this, 58, 0);
      this.headware.setPos(0.0F, 1.0F, 0.0F);
      this.headware.addBox(-4.5F, -12.5F, -4.5F, 9.0F, 13.0F, 9.0F, modelScale);
      this.legRight = new HideableModelRenderer(this, 0, 16);
      this.legRight.setPos(-1.9F, 12.0F, 0.1F);
      this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.sleeveLeft = new HideableModelRenderer(this, 36, 33);
      this.sleeveLeft.mirror = true;
      this.sleeveLeft.setPos(-0.5F, -0.1F, 0.0F);
      this.sleeveLeft.addBox(-0.5F, -2.1F, -2.4F, 5.0F, 6.0F, 5.0F, modelScale);
      this.robeLowerLeft = new HideableModelRenderer(this, 58, 33);
      this.robeLowerLeft.setPos(0.0F, -0.2F, 0.0F);
      this.robeLowerLeft.addBox(-1.9F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, modelScale);
      this.chestplate = new HideableModelRenderer(this, 1, 32);
      this.chestplate.setPos(0.0F, 0.1F, 0.0F);
      this.chestplate.addBox(-4.5F, -0.3F, -2.5F, 9.0F, 12.0F, 5.0F, modelScale);
      this.robeLowerRight = new HideableModelRenderer(this, 58, 33);
      this.robeLowerRight.mirror = true;
      this.robeLowerRight.setPos(0.0F, -0.2F, 0.0F);
      this.robeLowerRight.addBox(-2.1F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, modelScale);
      this.sleeveRight = new HideableModelRenderer(this, 36, 33);
      this.sleeveRight.setPos(0.5F, -0.1F, 0.0F);
      this.sleeveRight.addBox(-4.5F, -2.1F, -2.4F, 5.0F, 6.0F, 5.0F, modelScale);
      this.cloak = new HideableModelRenderer(this, 81, 37);
      this.cloak.setPos(0.0F, 0.1F, 0.0F);
      this.cloak.addBox(-4.5F, 0.0F, 2.3F, 9.0F, 21.0F, 1.0F, modelScale);
      this.setRotateAngle(this.cloak, 0.045553092F, 0.0F, 0.0F);
      this.armRight = new HideableModelRenderer(this, 40, 16);
      this.armRight.setPos(-5.0F, 2.0F, 0.0F);
      this.armRight.addBox(-2.5F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelScale);
      this.setRotateAngle(this.armRight, 0.0F, 0.0F, 0.10000736F);
      this.necklace = new HideableModelRenderer(this, 1, 52);
      this.necklace.setPos(0.0F, 0.1F, 0.0F);
      this.necklace.addBox(-4.5F, -0.3F, -2.8F, 9.0F, 7.0F, 5.0F, modelScale);
      this.setRotateAngle(this.necklace, 0.03211406F, 0.0F, 0.0F);
      this.armLeft = new HideableModelRenderer(this, 40, 16);
      this.armLeft.mirror = true;
      this.armLeft.setPos(5.0F, 2.0F, -0.0F);
      this.armLeft.addBox(-0.5F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelScale);
      this.setRotateAngle(this.armLeft, -0.0F, 0.0F, -0.10000736F);
      this.head = new HideableModelRenderer(this, 0, 0);
      this.head.setPos(0.0F, 0.0F, 0.0F);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelScale);
      this.legLeft = new HideableModelRenderer(this, 0, 16);
      this.legLeft.mirror = true;
      this.legLeft.setPos(1.9F, 12.0F, 0.1F);
      this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.head.addChild(this.headware);
      this.body.addChild(this.legRight);
      this.armLeft.addChild(this.sleeveLeft);
      this.legLeft.addChild(this.robeLowerLeft);
      this.body.addChild(this.chestplate);
      this.legRight.addChild(this.robeLowerRight);
      this.armRight.addChild(this.sleeveRight);
      this.body.addChild(this.cloak);
      this.body.addChild(this.armRight);
      this.chestplate.addChild(this.necklace);
      this.body.addChild(this.armLeft);
      this.body.addChild(this.head);
      this.body.addChild(this.legLeft);
      this.updateDefaultPose();
      this.animator = ModelAnimator.create();
   }

   public void prepareMobModel(EntityDreadQueen LivingEntityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
      this.rightArmPose = ArmPose.EMPTY;
      this.leftArmPose = ArmPose.EMPTY;
      super.m_6839_(LivingEntityIn, limbSwing, limbSwingAmount, partialTickTime);
   }

   public Animation getSpawnAnimation() {
      return EntityDreadQueen.ANIMATION_SPAWN;
   }
}
