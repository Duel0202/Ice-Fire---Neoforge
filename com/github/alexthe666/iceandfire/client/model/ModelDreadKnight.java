package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.HideableModelRenderer;
import com.github.alexthe666.iceandfire.entity.EntityDreadKnight;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

public class ModelDreadKnight extends ModelDreadBase<EntityDreadKnight> {
   public HideableModelRenderer chestplate;
   public HideableModelRenderer cloak;
   public HideableModelRenderer crown;
   public HideableModelRenderer sleeveRight;
   public HideableModelRenderer robeLowerRight;
   public HideableModelRenderer sleeveLeft;
   public HideableModelRenderer robeLowerLeft;

   public ModelDreadKnight(float modelScale) {
      this.texWidth = 128;
      this.texHeight = 64;
      this.leftArmPose = ArmPose.EMPTY;
      this.rightArmPose = ArmPose.EMPTY;
      this.sleeveRight = new HideableModelRenderer(this, 35, 33);
      this.sleeveRight.setPos(0.0F, -0.1F, 0.0F);
      this.sleeveRight.addBox(-4.0F, -2.1F, -2.5F, 5.0F, 6.0F, 5.0F, modelScale);
      this.chestplate = new HideableModelRenderer(this, 1, 32);
      this.chestplate.setPos(0.0F, 0.1F, 0.0F);
      this.chestplate.addBox(-4.5F, 0.0F, -2.5F, 9.0F, 11.0F, 5.0F, modelScale);
      this.crown = new HideableModelRenderer(this, 58, -1);
      this.crown.setPos(0.0F, 0.0F, 0.0F);
      this.crown.addBox(-4.5F, -10.2F, -4.5F, 9.0F, 11.0F, 9.0F, modelScale);
      this.body = new HideableModelRenderer(this, 16, 16);
      this.body.setPos(0.0F, 0.0F, 0.0F);
      this.body.addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, modelScale);
      this.legRight = new HideableModelRenderer(this, 0, 16);
      this.legRight.setPos(-1.9F, 12.0F, 0.1F);
      this.legRight.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.armLeft = new HideableModelRenderer(this, 40, 16);
      this.armLeft.mirror = true;
      this.armLeft.setPos(5.0F, 2.0F, -0.0F);
      this.armLeft.addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.setRotateAngle(this.armLeft, -0.0F, 0.10000736F, -0.10000736F);
      this.legLeft = new HideableModelRenderer(this, 0, 16);
      this.legLeft.mirror = true;
      this.legLeft.setPos(1.9F, 12.0F, 0.1F);
      this.legLeft.addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.head = new HideableModelRenderer(this, 0, 0);
      this.head.setPos(0.0F, 0.0F, 0.0F);
      this.head.addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, modelScale);
      this.sleeveLeft = new HideableModelRenderer(this, 35, 33);
      this.sleeveLeft.mirror = true;
      this.sleeveLeft.setPos(0.0F, -0.1F, 0.0F);
      this.sleeveLeft.addBox(-1.0F, -2.1F, -2.5F, 5.0F, 6.0F, 5.0F, modelScale);
      this.robeLowerRight = new HideableModelRenderer(this, 58, 33);
      this.robeLowerRight.mirror = true;
      this.robeLowerRight.setPos(0.0F, -0.2F, 0.0F);
      this.robeLowerRight.addBox(-2.1F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, modelScale);
      this.cloak = new HideableModelRenderer(this, 81, 37);
      this.cloak.setPos(0.0F, 0.1F, 0.0F);
      this.cloak.addBox(-4.5F, 0.0F, 2.3F, 9.0F, 21.0F, 1.0F, modelScale);
      this.setRotateAngle(this.cloak, 0.045553092F, 0.0F, 0.0F);
      this.armRight = new HideableModelRenderer(this, 40, 16);
      this.armRight.setPos(-5.0F, 2.0F, 0.0F);
      this.armRight.addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, modelScale);
      this.setRotateAngle(this.armRight, -0.0F, -0.10000736F, 0.10000736F);
      this.robeLowerLeft = new HideableModelRenderer(this, 58, 33);
      this.robeLowerLeft.setPos(0.0F, -0.2F, 0.0F);
      this.robeLowerLeft.addBox(-1.9F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F, modelScale);
      this.armRight.addChild(this.sleeveRight);
      this.body.addChild(this.chestplate);
      this.head.addChild(this.crown);
      this.body.addChild(this.legRight);
      this.body.addChild(this.armLeft);
      this.body.addChild(this.legLeft);
      this.body.addChild(this.head);
      this.armLeft.addChild(this.sleeveLeft);
      this.legRight.addChild(this.robeLowerRight);
      this.body.addChild(this.cloak);
      this.body.addChild(this.armRight);
      this.legLeft.addChild(this.robeLowerLeft);
      this.updateDefaultPose();
      this.animator = ModelAnimator.create();
   }

   public void prepareMobModel(EntityDreadKnight livingEntityIn, float limbSwing, float limbSwingAmount, float partialTickTime) {
      this.rightArmPose = ArmPose.EMPTY;
      this.leftArmPose = ArmPose.EMPTY;
      ItemStack itemstack = livingEntityIn.m_21120_(InteractionHand.MAIN_HAND);
      if (itemstack.m_41720_() == Items.f_42411_ && livingEntityIn.f_20911_) {
         if (livingEntityIn.m_5737_() == HumanoidArm.RIGHT) {
            this.rightArmPose = ArmPose.BOW_AND_ARROW;
         } else {
            this.leftArmPose = ArmPose.BOW_AND_ARROW;
         }
      }

      super.m_6839_(livingEntityIn, limbSwing, limbSwingAmount, partialTickTime);
   }

   public void setRotationAnglesSpawn(EntityDreadKnight entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public void animate(EntityDreadKnight entity, float f, float f1, float f2, float f3, float f4, float f5) {
   }

   public Animation getSpawnAnimation() {
      return EntityDreadKnight.ANIMATION_SPAWN;
   }

   public void m_102624_(@NotNull EntityModel<EntityDreadKnight> p_217111_1_) {
      super.m_102624_(p_217111_1_);
      if (p_217111_1_ instanceof HumanoidModel) {
         HumanoidModel modelbiped = (HumanoidModel)p_217111_1_;
         modelbiped.f_102815_ = this.leftArmPose;
         modelbiped.f_102816_ = this.rightArmPose;
         modelbiped.f_102817_ = this.isSneak;
      }

   }
}
