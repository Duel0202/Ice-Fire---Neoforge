package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ModelAnimator;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.client.model.util.HideableModelRenderer;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HumanoidModel.ArmPose;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class ModelBipedBase<T extends LivingEntity> extends AdvancedEntityModel<T> implements ICustomStatueModel, BasicHeadedModel, ArmedModel {
   public HideableModelRenderer head = new HideableModelRenderer(this, 0, 0);
   public HideableModelRenderer headware = new HideableModelRenderer(this, 0, 0);
   public HideableModelRenderer body = new HideableModelRenderer(this, 0, 0);
   public HideableModelRenderer armRight = new HideableModelRenderer(this, 0, 0);
   public HideableModelRenderer armLeft = new HideableModelRenderer(this, 0, 0);
   public HideableModelRenderer legRight = new HideableModelRenderer(this, 0, 0);
   public HideableModelRenderer legLeft = new HideableModelRenderer(this, 0, 0);
   public ArmPose leftArmPose;
   public ArmPose rightArmPose;
   public boolean isSneak;
   protected ModelAnimator animator;

   protected ModelBipedBase() {
   }

   public BasicModelPart getHead() {
      return this.head;
   }

   public void m_6002_(@NotNull HumanoidArm sideIn, @NotNull PoseStack matrixStackIn) {
      this.getArmForSide(sideIn).translateAndRotate(matrixStackIn);
   }

   protected HideableModelRenderer getArmForSide(HumanoidArm side) {
      return side == HumanoidArm.LEFT ? this.armLeft : this.armRight;
   }

   protected HumanoidArm getMainHand(Entity entityIn) {
      if (entityIn instanceof LivingEntity) {
         LivingEntity LivingEntity = (LivingEntity)entityIn;
         HumanoidArm Handside = LivingEntity.m_5737_();
         return LivingEntity.f_20912_ == InteractionHand.MAIN_HAND ? Handside : Handside.m_20828_();
      } else {
         return HumanoidArm.RIGHT;
      }
   }

   public void rotate(ModelAnimator animator, AdvancedModelBox model, float x, float y, float z) {
      animator.rotate(model, (float)Math.toRadians((double)x), (float)Math.toRadians((double)y), (float)Math.toRadians((double)z));
   }

   public void rotateMinus(ModelAnimator animator, AdvancedModelBox model, float x, float y, float z) {
      animator.rotate(model, (float)Math.toRadians((double)x) - model.defaultRotationX, (float)Math.toRadians((double)y) - model.defaultRotationY, (float)Math.toRadians((double)z) - model.defaultRotationZ);
   }

   public void progressRotationInterp(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ, float max) {
      model.rotateAngleX += progress * (rotX - model.defaultRotationX) / max;
      model.rotateAngleY += progress * (rotY - model.defaultRotationY) / max;
      model.rotateAngleZ += progress * (rotZ - model.defaultRotationZ) / max;
   }

   public void progresPositionInterp(AdvancedModelBox model, float progress, float x, float y, float z, float max) {
      model.rotationPointX += progress * x / max;
      model.rotationPointY += progress * y / max;
      model.rotationPointZ += progress * z / max;
   }

   public void progressRotation(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ) {
      model.rotateAngleX += progress * (rotX - model.defaultRotationX) / 20.0F;
      model.rotateAngleY += progress * (rotY - model.defaultRotationY) / 20.0F;
      model.rotateAngleZ += progress * (rotZ - model.defaultRotationZ) / 20.0F;
   }

   public void progressRotationPrev(AdvancedModelBox model, float progress, float rotX, float rotY, float rotZ) {
      model.rotateAngleX += progress * rotX / 20.0F;
      model.rotateAngleY += progress * rotY / 20.0F;
      model.rotateAngleZ += progress * rotZ / 20.0F;
   }

   public void progressPosition(AdvancedModelBox model, float progress, float x, float y, float z) {
      model.rotationPointX += progress * (x - model.defaultPositionX) / 20.0F;
      model.rotationPointY += progress * (y - model.defaultPositionY) / 20.0F;
      model.rotationPointZ += progress * (z - model.defaultPositionZ) / 20.0F;
   }

   public void progressPositionPrev(AdvancedModelBox model, float progress, float x, float y, float z) {
      model.rotationPointX += progress * x / 20.0F;
      model.rotationPointY += progress * y / 20.0F;
      model.rotationPointZ += progress * z / 20.0F;
   }

   public void setRotateAngle(AdvancedModelBox modelRenderer, float x, float y, float z) {
      modelRenderer.rotateAngleX = x;
      modelRenderer.rotateAngleY = y;
      modelRenderer.rotateAngleZ = z;
   }

   public <T extends BasicModelPart> T copyFrom(T modelIn, T currentModel) {
      modelIn.copyModelAngles(currentModel);
      modelIn.rotationPointX = currentModel.rotationPointX;
      modelIn.rotationPointY = currentModel.rotationPointY;
      modelIn.rotationPointZ = currentModel.rotationPointZ;
      return modelIn;
   }

   public <M extends ModelPart, T extends BasicModelPart> M copyFrom(M modelIn, T currentModel) {
      modelIn.m_171327_(currentModel.rotateAngleX, currentModel.rotateAngleY, currentModel.rotateAngleZ);
      modelIn.f_104200_ = currentModel.rotationPointX;
      modelIn.f_104201_ = currentModel.rotationPointY;
      modelIn.f_104202_ = currentModel.rotationPointZ;
      return modelIn;
   }

   public void setModelAttributes(ModelBipedBase<T> modelIn) {
      super.m_102624_(modelIn);
      modelIn.animator = this.animator;
      modelIn.leftArmPose = this.leftArmPose;
      modelIn.rightArmPose = this.rightArmPose;
      modelIn.isSneak = this.isSneak;
      this.copyFrom((BasicModelPart)modelIn.head, this.head);
      this.copyFrom((BasicModelPart)modelIn.headware, this.headware);
      this.copyFrom((BasicModelPart)modelIn.body, this.body);
      this.copyFrom((BasicModelPart)modelIn.armRight, this.armRight);
      this.copyFrom((BasicModelPart)modelIn.armLeft, this.armLeft);
      this.copyFrom((BasicModelPart)modelIn.legRight, this.legRight);
      this.copyFrom((BasicModelPart)modelIn.legLeft, this.legLeft);
   }

   public void setModelAttributes(HumanoidModel<T> modelIn) {
      super.m_102624_(modelIn);
      modelIn.f_102815_ = this.leftArmPose;
      modelIn.f_102816_ = this.rightArmPose;
      modelIn.f_102817_ = this.isSneak;
      this.copyFrom((ModelPart)modelIn.f_102808_, this.head);
      this.copyFrom((ModelPart)modelIn.f_102809_, this.headware);
      this.copyFrom((ModelPart)modelIn.f_102810_, this.body);
      this.copyFrom((ModelPart)modelIn.f_102811_, this.armRight);
      this.copyFrom((ModelPart)modelIn.f_102812_, this.armLeft);
      this.copyFrom((ModelPart)modelIn.f_102813_, this.legRight);
      this.copyFrom((ModelPart)modelIn.f_102814_, this.legLeft);
   }

   public void setVisible(boolean visible) {
      this.head.invisible = !visible;
      this.headware.invisible = !visible;
      this.body.invisible = !visible;
      this.armRight.invisible = !visible;
      this.armLeft.invisible = !visible;
      this.legRight.invisible = !visible;
      this.legLeft.invisible = !visible;
   }

   public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      this.resetToDefaultPose();
      this.animate(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, 0.0F);
      this.faceTarget(netHeadYaw, headPitch, 1.0F, new AdvancedModelBox[]{this.head});
      float f = 1.0F;
      HideableModelRenderer var10000 = this.armRight;
      var10000.rotateAngleX += Mth.m_14089_(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F / f;
      var10000 = this.armLeft;
      var10000.rotateAngleX += Mth.m_14089_(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
      this.legRight.rotateAngleX = Mth.m_14089_(limbSwing * 0.6662F) * 1.4F * limbSwingAmount / f;
      this.legLeft.rotateAngleX = Mth.m_14089_(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount / f;
      this.legRight.rotateAngleY = 0.0F;
      this.legLeft.rotateAngleY = 0.0F;
      this.legRight.rotateAngleZ = 0.0F;
      this.legLeft.rotateAngleZ = 0.0F;
      if (entityIn.m_20159_()) {
         var10000 = this.armRight;
         var10000.rotateAngleX += -0.62831855F;
         var10000 = this.armLeft;
         var10000.rotateAngleX += -0.62831855F;
         this.legRight.rotateAngleX = -1.4137167F;
         this.legRight.rotateAngleY = 0.31415927F;
         this.legRight.rotateAngleZ = 0.07853982F;
         this.legLeft.rotateAngleX = -1.4137167F;
         this.legLeft.rotateAngleY = -0.31415927F;
         this.legLeft.rotateAngleZ = -0.07853982F;
      }

      if (this.f_102608_ > 0.0F) {
         HumanoidArm handSide = this.getMainHand(entityIn);
         HideableModelRenderer modelrenderer = this.getArmForSide(handSide);
         float f1 = this.f_102608_;
         this.body.rotateAngleY = Mth.m_14031_(Mth.m_14116_(f1) * 6.2831855F) * 0.2F;
         if (handSide == HumanoidArm.LEFT) {
            var10000 = this.body;
            var10000.rotateAngleY *= -1.0F;
         }

         this.armRight.rotationPointZ = Mth.m_14031_(this.body.rotateAngleY) * 5.0F;
         this.armRight.rotationPointX = -Mth.m_14089_(this.body.rotateAngleY) * 5.0F;
         this.armLeft.rotationPointZ = -Mth.m_14031_(this.body.rotateAngleY) * 5.0F;
         this.armLeft.rotationPointX = Mth.m_14089_(this.body.rotateAngleY) * 5.0F;
         var10000 = this.armRight;
         var10000.rotateAngleY += this.body.rotateAngleY;
         var10000 = this.armLeft;
         var10000.rotateAngleY += this.body.rotateAngleY;
         var10000 = this.armLeft;
         var10000.rotateAngleX += this.body.rotateAngleX;
         f1 = 1.0F - this.f_102608_;
         f1 *= f1;
         f1 *= f1;
         f1 = 1.0F - f1;
         float f2 = Mth.m_14031_(f1 * 3.1415927F);
         float f3 = Mth.m_14031_(this.f_102608_ * 3.1415927F) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
         modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
         modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
         modelrenderer.rotateAngleZ += Mth.m_14031_(this.f_102608_ * 3.1415927F) * -0.4F;
      }

      if (this.isSneak) {
         this.body.rotateAngleX = 0.5F;
         var10000 = this.armRight;
         var10000.rotateAngleX += 0.4F;
         var10000 = this.armLeft;
         var10000.rotateAngleX += 0.4F;
         this.legRight.rotationPointZ = 4.0F;
         this.legLeft.rotationPointZ = 4.0F;
         this.legRight.rotationPointY = 9.0F;
         this.legLeft.rotationPointY = 9.0F;
         this.head.rotationPointY = 1.0F;
      } else {
         this.body.rotateAngleX = 0.0F;
         this.legRight.rotationPointZ = 0.1F;
         this.legLeft.rotationPointZ = 0.1F;
         this.legRight.rotationPointY = 12.0F;
         this.legLeft.rotationPointY = 12.0F;
         this.head.rotationPointY = 0.0F;
      }

      var10000 = this.armRight;
      var10000.rotateAngleZ += Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F;
      var10000 = this.armLeft;
      var10000.rotateAngleZ -= Mth.m_14089_(ageInTicks * 0.09F) * 0.05F + 0.05F;
      var10000 = this.armRight;
      var10000.rotateAngleX += Mth.m_14031_(ageInTicks * 0.067F) * 0.05F;
      var10000 = this.armLeft;
      var10000.rotateAngleX -= Mth.m_14031_(ageInTicks * 0.067F) * 0.05F;
   }

   public void renderStatue(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, Entity living) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }

   abstract void animate(T var1, float var2, float var3, float var4, float var5, float var6, float var7);

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.head, this.headware, this.body, this.armRight, this.armLeft, this.legRight, this.legLeft);
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.body);
   }
}
