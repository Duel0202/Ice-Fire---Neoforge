package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.HideableModelRenderer;
import com.github.alexthe666.iceandfire.entity.EntityDreadLich;
import com.github.alexthe666.iceandfire.entity.EntityDreadThrall;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ModelDreadLich extends ModelDreadBase<EntityDreadLich> implements ArmedModel {
   public HideableModelRenderer robe;
   public HideableModelRenderer mask;
   public HideableModelRenderer hood;
   public HideableModelRenderer sleeveRight;
   public HideableModelRenderer robeLowerRight;
   public HideableModelRenderer robeLowerLeft;
   public HideableModelRenderer sleeveLeft;

   public ModelDreadLich(float modelScale) {
      this.texWidth = 128;
      this.texHeight = 64;
      this.leftArmPose = ArmPose.EMPTY;
      this.rightArmPose = ArmPose.EMPTY;
      this.sleeveLeft = new HideableModelRenderer(this, 33, 35);
      this.sleeveLeft.mirror = true;
      this.sleeveLeft.setPos(0.0F, -0.1F, 0.0F);
      this.sleeveLeft.addBox(-1.0F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelScale);
      this.robeLowerRight = new HideableModelRenderer(this, 48, 35);
      this.robeLowerRight.mirror = true;
      this.robeLowerRight.setPos(0.0F, 0.0F, 0.0F);
      this.robeLowerRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.legLeft = new HideableModelRenderer(this, 0, 16);
      this.legLeft.mirror = true;
      this.legLeft.setPos(2.0F, 12.0F, 0.1F);
      this.legLeft.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelScale);
      this.robe = new HideableModelRenderer(this, 4, 34);
      this.robe.setPos(0.0F, 0.1F, 0.0F);
      this.robe.addBox(-4.5F, 0.0F, -2.5F, 9.0F, 12.0F, 5.0F, modelScale);
      this.legRight = new HideableModelRenderer(this, 0, 16);
      this.legRight.setPos(-2.0F, 12.0F, 0.1F);
      this.legRight.addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelScale);
      this.sleeveRight = new HideableModelRenderer(this, 33, 35);
      this.sleeveRight.setPos(0.0F, -0.1F, 0.0F);
      this.sleeveRight.addBox(-2.2F, -2.0F, -2.0F, 3.0F, 12.0F, 4.0F, modelScale);
      this.mask = new HideableModelRenderer(this, 40, 6);
      this.mask.setPos(0.0F, 0.0F, 0.0F);
      this.mask.addBox(-3.5F, -10.0F, -4.1F, 7.0F, 8.0F, 0.0F, modelScale);
      this.armRight = new HideableModelRenderer(this, 40, 16);
      this.armRight.setPos(-5.0F, 2.0F, 0.0F);
      this.armRight.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelScale);
      this.setRotateAngle(this.armRight, 0.0F, -0.10000736F, 0.10000736F);
      this.hood = new HideableModelRenderer(this, 60, 0);
      this.hood.setPos(0.0F, 0.0F, 0.0F);
      this.hood.addBox(-4.5F, -8.6F, -4.5F, 9.0F, 9.0F, 9.0F, modelScale);
      this.head = new HideableModelRenderer(this, 0, 0);
      this.head.setPos(0.0F, 0.0F, 0.0F);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelScale);
      this.body = new HideableModelRenderer(this, 16, 16);
      this.body.setPos(0.0F, 0.0F, 0.0F);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelScale);
      this.armLeft = new HideableModelRenderer(this, 40, 16);
      this.armLeft.mirror = true;
      this.armLeft.setPos(5.0F, 2.0F, -0.0F);
      this.armLeft.addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, modelScale);
      this.setRotateAngle(this.armLeft, 0.0F, 0.10000736F, -0.10000736F);
      this.robeLowerLeft = new HideableModelRenderer(this, 48, 35);
      this.robeLowerLeft.setPos(0.0F, 0.0F, 0.0F);
      this.robeLowerLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.armLeft.addChild(this.sleeveLeft);
      this.legRight.addChild(this.robeLowerRight);
      this.body.addChild(this.legLeft);
      this.body.addChild(this.robe);
      this.body.addChild(this.legRight);
      this.armRight.addChild(this.sleeveRight);
      this.head.addChild(this.mask);
      this.body.addChild(this.armRight);
      this.head.addChild(this.hood);
      this.body.addChild(this.head);
      this.body.addChild(this.armLeft);
      this.legLeft.addChild(this.robeLowerLeft);
      this.updateDefaultPose();
      this.animator = ModelAnimator.create();
   }

   public void setLivingAnimations(EntityDreadThrall LivingEntityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
      this.rightArmPose = ArmPose.EMPTY;
      this.leftArmPose = ArmPose.EMPTY;
      ItemStack itemstack = LivingEntityIn.m_21120_(InteractionHand.MAIN_HAND);
      if (itemstack.m_41720_() == Items.f_42411_) {
         if (LivingEntityIn.m_5737_() == HumanoidArm.RIGHT) {
            this.rightArmPose = ArmPose.BOW_AND_ARROW;
         } else {
            this.leftArmPose = ArmPose.BOW_AND_ARROW;
         }
      }

   }

   public Animation getSpawnAnimation() {
      return EntityDreadLich.ANIMATION_SPAWN;
   }

   public void setupAnim(EntityDreadLich entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      super.setupAnim(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      if (entityIn.getAnimation() == EntityDreadLich.ANIMATION_SUMMON) {
         this.armRight.rotationPointZ = 0.0F;
         this.armRight.rotationPointX = -5.0F;
         this.armLeft.rotationPointZ = 0.0F;
         this.armLeft.rotationPointX = 5.0F;
         this.armRight.rotateAngleX = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
         this.armLeft.rotateAngleX = Mth.m_14089_(ageInTicks * 0.6662F) * 0.25F;
         this.armRight.rotateAngleZ = 2.3561945F;
         this.armLeft.rotateAngleZ = -2.3561945F;
         this.armRight.rotateAngleY = 0.0F;
         this.armLeft.rotateAngleY = 0.0F;
      }

   }
}
