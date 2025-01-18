package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class ModelPixie extends ModelDragonBase<EntityPixie> {
   public AdvancedModelBox Body;
   public AdvancedModelBox Left_Arm;
   public AdvancedModelBox Head;
   public AdvancedModelBox Right_Arm;
   public AdvancedModelBox Neck;
   public AdvancedModelBox Left_Leg;
   public AdvancedModelBox Right_Leg;
   public AdvancedModelBox Left_Wing;
   public AdvancedModelBox Left_Wing2;
   public AdvancedModelBox Right_Wing;
   public AdvancedModelBox Right_Wing2;
   public AdvancedModelBox Dress;

   public ModelPixie() {
      this.texWidth = 32;
      this.texHeight = 32;
      this.Neck = new AdvancedModelBox(this, 40, 25);
      this.Neck.setPos(0.0F, -8.2F, 0.0F);
      this.Neck.addBox(-1.5F, -1.1F, -1.0F, 3.0F, 1.0F, 1.0F, 0.0F);
      this.Right_Arm = new AdvancedModelBox(this, 0, 17);
      this.Right_Arm.setPos(-1.8F, -7.0F, 0.0F);
      this.Right_Arm.addBox(-0.6F, -0.5F, -1.0F, 1.0F, 6.0F, 1.0F, 0.0F);
      this.setRotateAngle(this.Right_Arm, 0.0F, 0.0F, 0.17453292F);
      this.Right_Wing2 = new AdvancedModelBox(this, 24, 10);
      this.Right_Wing2.setPos(-1.4F, -5.0F, -0.1F);
      this.Right_Wing2.addBox(-1.2F, -0.5F, 0.5F, 3.0F, 10.0F, 1.0F, 0.0F);
      this.setRotateAngle(this.Right_Wing2, 0.5235988F, -0.018325957F, 1.0471976F);
      this.Right_Wing = new AdvancedModelBox(this, 14, 10);
      this.Right_Wing.setPos(-1.2F, -6.3F, 0.4F);
      this.Right_Wing.addBox(-1.2F, -0.5F, 0.5F, 3.0F, 12.0F, 1.0F, 0.0F);
      this.setRotateAngle(this.Right_Wing, 0.5235988F, -0.2617994F, 1.7453293F);
      this.Body = new AdvancedModelBox(this, 0, 8);
      this.Body.setPos(0.0F, 16.9F, 0.5F);
      this.Body.addBox(-1.5F, -7.9F, -1.4F, 3.0F, 5.0F, 2.0F, 0.0F);
      this.Right_Leg = new AdvancedModelBox(this, 5, 17);
      this.Right_Leg.mirror = true;
      this.Right_Leg.setPos(-0.8F, -1.5F, 0.0F);
      this.Right_Leg.addBox(-0.6F, -0.5F, -0.9F, 1.0F, 6.0F, 1.0F, 0.0F);
      this.Dress = new AdvancedModelBox(this, 0, 24);
      this.Dress.setPos(0.0F, -2.5F, 0.1F);
      this.Dress.addBox(-2.0F, -0.4F, -1.5F, 4.0F, 3.0F, 2.0F, 0.0F);
      this.Head = new AdvancedModelBox(this, 0, 0);
      this.Head.setPos(0.0F, -8.0F, -0.8F);
      this.Head.addBox(-2.0F, -3.8F, -1.6F, 4.0F, 4.0F, 4.0F, 0.0F);
      this.Left_Wing = new AdvancedModelBox(this, 14, 10);
      this.Left_Wing.mirror = true;
      this.Left_Wing.setPos(1.2F, -6.3F, 0.4F);
      this.Left_Wing.addBox(-1.8F, -0.5F, 0.5F, 3.0F, 12.0F, 1.0F, 0.0F);
      this.setRotateAngle(this.Left_Wing, 0.5235988F, 0.2617994F, -1.7453293F);
      this.Left_Leg = new AdvancedModelBox(this, 5, 17);
      this.Left_Leg.setPos(0.8F, -1.5F, 0.0F);
      this.Left_Leg.addBox(-0.6F, -0.5F, -0.9F, 1.0F, 6.0F, 1.0F, 0.0F);
      this.Left_Wing2 = new AdvancedModelBox(this, 24, 10);
      this.Left_Wing2.mirror = true;
      this.Left_Wing2.setPos(1.4F, -5.0F, -0.1F);
      this.Left_Wing2.addBox(-1.8F, -0.5F, 0.5F, 3.0F, 10.0F, 1.0F, 0.0F);
      this.setRotateAngle(this.Left_Wing2, 0.5235988F, 0.018325957F, -1.0471976F);
      this.Left_Arm = new AdvancedModelBox(this, 0, 17);
      this.Left_Arm.setPos(1.8F, -7.0F, 0.0F);
      this.Left_Arm.addBox(-0.6F, -0.5F, -0.9F, 1.0F, 6.0F, 1.0F, 0.0F);
      this.setRotateAngle(this.Left_Arm, 0.0F, 0.0F, -0.17453292F);
      this.Body.addChild(this.Neck);
      this.Body.addChild(this.Right_Arm);
      this.Body.addChild(this.Right_Wing2);
      this.Body.addChild(this.Right_Wing);
      this.Body.addChild(this.Right_Leg);
      this.Body.addChild(this.Dress);
      this.Body.addChild(this.Head);
      this.Body.addChild(this.Left_Wing);
      this.Body.addChild(this.Left_Leg);
      this.Body.addChild(this.Left_Wing2);
      this.Body.addChild(this.Left_Arm);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.Body);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.Body, this.Left_Arm, this.Head, this.Right_Arm, this.Neck, this.Left_Leg, this.Right_Leg, this.Left_Wing, this.Left_Wing2, this.Right_Wing, this.Right_Wing2, this.Dress, new AdvancedModelBox[0]);
   }

   public void setupAnim(EntityPixie entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      float speed_fly = 1.1F;
      float speed_idle = 0.05F;
      float degree_fly = 1.0F;
      float degree_idle = 0.5F;
      AdvancedModelBox[] LEFT_WINGS = new AdvancedModelBox[]{this.Left_Wing, this.Left_Wing2};
      AdvancedModelBox[] RIGHT_WINGS = new AdvancedModelBox[]{this.Right_Wing, this.Right_Wing2};
      this.Left_Leg.rotateAngleX = Mth.m_14089_(f * 0.6662F + 3.1415927F) * 1.0F * f1 * 0.5F;
      this.Right_Leg.rotateAngleX = Mth.m_14089_(f * 0.6662F) * 1.0F * f1 * 0.5F;
      float f12 = f1;
      if (f1 < 0.0F) {
         f12 = 0.0F;
      }

      if ((double)f12 > Math.toRadians(20.0D)) {
         f12 = (float)Math.toRadians(20.0D);
      }

      this.Body.rotateAngleX = f12;
      AdvancedModelBox var10000 = this.Head;
      var10000.rotateAngleX -= f12;
      ItemStack itemstack = entity.m_21120_(InteractionHand.MAIN_HAND);
      if (!itemstack.m_41619_()) {
         this.faceTarget(f3, f4, 1.0F, new AdvancedModelBox[]{this.Head});
         var10000 = this.Left_Arm;
         var10000.rotateAngleX += (float)Math.toRadians(-35.0D);
         var10000 = this.Right_Arm;
         var10000.rotateAngleX += (float)Math.toRadians(-35.0D);
         var10000 = this.Body;
         var10000.rotateAngleX += (float)Math.toRadians(10.0D);
         var10000 = this.Left_Leg;
         var10000.rotateAngleX += (float)Math.toRadians(-10.0D);
         var10000 = this.Right_Leg;
         var10000.rotateAngleX += (float)Math.toRadians(-10.0D);
         var10000 = this.Head;
         var10000.rotateAngleX += (float)Math.toRadians(-10.0D);
      } else {
         this.Right_Arm.rotateAngleX = Mth.m_14089_(f * 0.6662F + 3.1415927F) * 1.0F * f1 * 0.5F;
         this.Left_Arm.rotateAngleX = Mth.m_14089_(f * 0.6662F) * 1.0F * f1 * 0.5F;
      }

      if (entity.isPixieSitting()) {
         var10000 = this.Right_Arm;
         var10000.rotateAngleX += -0.62831855F;
         var10000 = this.Left_Arm;
         var10000.rotateAngleX += -0.62831855F;
         this.Right_Leg.rotateAngleX = -1.4137167F;
         this.Right_Leg.rotateAngleY = 0.31415927F;
         this.Right_Leg.rotateAngleZ = 0.07853982F;
         this.Left_Leg.rotateAngleX = -1.4137167F;
         this.Left_Leg.rotateAngleY = -0.31415927F;
         this.Left_Leg.rotateAngleZ = -0.07853982F;
         var10000 = this.Dress;
         var10000.rotateAngleX += (float)Math.toRadians(-50.0D);
         var10000 = this.Dress;
         var10000.rotationPointZ += 0.25F;
         var10000 = this.Dress;
         var10000.rotationPointY += 0.35F;
         this.Left_Wing.rotateAngleZ = (float)Math.toRadians(-28.0D);
         this.Right_Wing.rotateAngleZ = (float)Math.toRadians(28.0D);
         this.Left_Wing2.rotateAngleZ = (float)Math.toRadians(-8.0D);
         this.Right_Wing2.rotateAngleZ = (float)Math.toRadians(8.0D);
      } else {
         this.chainWave(LEFT_WINGS, speed_fly, degree_fly * 0.75F, 1.0D, f2, 1.0F);
         this.chainWave(RIGHT_WINGS, speed_fly, degree_fly * 0.75F, 1.0D, f2, 1.0F);
      }

   }

   public void animateInHouse(TileEntityPixieHouse house) {
      this.resetToDefaultPose();
      float speed_fly = 1.1F;
      float speed_idle = 0.05F;
      float degree_fly = 1.0F;
      float degree_idle = 0.5F;
      AdvancedModelBox[] var10000 = new AdvancedModelBox[]{this.Left_Wing, this.Left_Wing2};
      var10000 = new AdvancedModelBox[]{this.Right_Wing, this.Right_Wing2};
      float f12 = 0.0F;
      if (f12 < 0.0F) {
         f12 = 0.0F;
      }

      if ((double)f12 > Math.toRadians(20.0D)) {
         f12 = (float)Math.toRadians(20.0D);
      }

      AdvancedModelBox var9 = this.Right_Arm;
      var9.rotateAngleX += -0.62831855F;
      var9 = this.Left_Arm;
      var9.rotateAngleX += -0.62831855F;
      this.Right_Leg.rotateAngleX = -1.4137167F;
      this.Right_Leg.rotateAngleY = 0.31415927F;
      this.Right_Leg.rotateAngleZ = 0.07853982F;
      this.Left_Leg.rotateAngleX = -1.4137167F;
      this.Left_Leg.rotateAngleY = -0.31415927F;
      this.Left_Leg.rotateAngleZ = -0.07853982F;
      var9 = this.Dress;
      var9.rotateAngleX += (float)Math.toRadians(-50.0D);
      var9 = this.Dress;
      var9.rotationPointZ += 0.25F;
      var9 = this.Dress;
      var9.rotationPointY += 0.35F;
      this.Left_Wing.rotateAngleZ = (float)Math.toRadians(-28.0D);
      this.Right_Wing.rotateAngleZ = (float)Math.toRadians(28.0D);
      this.Left_Wing2.rotateAngleZ = (float)Math.toRadians(-8.0D);
      this.Right_Wing2.rotateAngleZ = (float)Math.toRadians(8.0D);
   }

   public void animateInJar(boolean sitting, TileEntityJar jar, float headRot) {
      this.resetToDefaultPose();
      float speed_fly = 1.1F;
      float speed_idle = 0.05F;
      float degree_fly = 1.0F;
      float degree_idle = 0.5F;
      AdvancedModelBox[] LEFT_WINGS = new AdvancedModelBox[]{this.Left_Wing, this.Left_Wing2};
      AdvancedModelBox[] RIGHT_WINGS = new AdvancedModelBox[]{this.Right_Wing, this.Right_Wing2};
      float f12 = 0.0F;
      if (f12 < 0.0F) {
         f12 = 0.0F;
      }

      if ((double)f12 > Math.toRadians(20.0D)) {
         f12 = (float)Math.toRadians(20.0D);
      }

      if (sitting) {
         AdvancedModelBox var10000 = this.Right_Arm;
         var10000.rotateAngleX += -0.62831855F;
         var10000 = this.Left_Arm;
         var10000.rotateAngleX += -0.62831855F;
         this.Right_Leg.rotateAngleX = -1.4137167F;
         this.Right_Leg.rotateAngleY = 0.31415927F;
         this.Right_Leg.rotateAngleZ = 0.07853982F;
         this.Left_Leg.rotateAngleX = -1.4137167F;
         this.Left_Leg.rotateAngleY = -0.31415927F;
         this.Left_Leg.rotateAngleZ = -0.07853982F;
         var10000 = this.Dress;
         var10000.rotateAngleX += (float)Math.toRadians(-50.0D);
         var10000 = this.Dress;
         var10000.rotationPointZ += 0.25F;
         var10000 = this.Dress;
         var10000.rotationPointY += 0.35F;
         this.Left_Wing.rotateAngleZ = (float)Math.toRadians(-28.0D);
         this.Right_Wing.rotateAngleZ = (float)Math.toRadians(28.0D);
         this.Left_Wing2.rotateAngleZ = (float)Math.toRadians(-8.0D);
         this.Right_Wing2.rotateAngleZ = (float)Math.toRadians(8.0D);
      } else if (jar != null) {
         float partialTicks = Minecraft.m_91087_().m_91296_();
         this.chainWave(LEFT_WINGS, speed_fly, degree_fly * 0.75F, 1.0D, (float)jar.ticksExisted + partialTicks, 1.0F);
         this.chainWave(RIGHT_WINGS, speed_fly, degree_fly * 0.75F, 1.0D, (float)jar.ticksExisted + partialTicks, 1.0F);
      }

   }

   public void renderStatue(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, Entity living) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
