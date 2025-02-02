package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class ModelDeathWormGauntlet extends ModelDragonBase {
   public AdvancedModelBox Head;
   public AdvancedModelBox JawExtender;
   public AdvancedModelBox HeadInner;
   public AdvancedModelBox ToothB;
   public AdvancedModelBox ToothT;
   public AdvancedModelBox ToothL;
   public AdvancedModelBox ToothL_1;
   public AdvancedModelBox JawExtender2;
   public AdvancedModelBox JawExtender3;
   public AdvancedModelBox JawExtender4;
   public AdvancedModelBox TopJaw;
   public AdvancedModelBox BottomJaw;
   public AdvancedModelBox JawHook;

   public ModelDeathWormGauntlet() {
      this.texWidth = 128;
      this.texHeight = 64;
      this.Head = new AdvancedModelBox(this, 0, 29);
      this.Head.setPos(0.0F, 0.0F, 1.5F);
      this.Head.addBox(-5.0F, -5.0F, -8.0F, 10.0F, 10.0F, 8.0F, 0.0F);
      this.TopJaw = new AdvancedModelBox(this, 19, 7);
      this.TopJaw.setPos(0.0F, -0.2F, -11.4F);
      this.TopJaw.addBox(-2.0F, -1.5F, -6.4F, 4.0F, 2.0F, 6.0F, 0.0F);
      this.setRotateAngle(this.TopJaw, 0.091106184F, 0.0F, 0.0F);
      this.JawHook = new AdvancedModelBox(this, 0, 7);
      this.JawHook.setPos(0.0F, -0.3F, -6.0F);
      this.JawHook.addBox(-0.5F, -0.7F, -2.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.JawHook, 1.7301449F, 0.0F, 0.0F);
      this.ToothL = new AdvancedModelBox(this, 52, 34);
      this.ToothL.setPos(4.5F, 0.0F, -7.5F);
      this.ToothL.addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.ToothL, -3.1415927F, 0.34906584F, 0.0F);
      this.ToothB = new AdvancedModelBox(this, 52, 34);
      this.ToothB.setPos(0.0F, 4.5F, -7.5F);
      this.ToothB.addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.ToothB, 2.7930503F, -0.0F, 0.0F);
      this.JawExtender = new AdvancedModelBox(this, 0, 7);
      this.JawExtender.setPos(0.0F, 0.0F, 10.0F);
      this.JawExtender.addBox(-1.5F, -1.5F, -13.0F, 3.0F, 3.0F, 13.0F, 0.0F);
      this.BottomJaw = new AdvancedModelBox(this, 40, 7);
      this.BottomJaw.setPos(0.0F, 0.8F, -12.3F);
      this.BottomJaw.addBox(-2.0F, 0.2F, -4.9F, 4.0F, 1.0F, 5.0F, 0.0F);
      this.setRotateAngle(this.BottomJaw, -0.045553092F, 0.0F, 0.0F);
      this.ToothT = new AdvancedModelBox(this, 52, 34);
      this.ToothT.setPos(0.0F, -4.5F, -7.5F);
      this.ToothT.addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.ToothT, -2.7930503F, -0.0F, 0.0F);
      this.HeadInner = new AdvancedModelBox(this, 0, 48);
      this.HeadInner.setPos(0.0F, 0.0F, -0.3F);
      this.HeadInner.addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, 0.0F);
      this.ToothL_1 = new AdvancedModelBox(this, 52, 34);
      this.ToothL_1.setPos(-4.5F, 0.0F, -7.5F);
      this.ToothL_1.addBox(-0.5F, -0.4F, 0.0F, 1.0F, 1.0F, 3.0F, 0.0F);
      this.setRotateAngle(this.ToothL_1, -3.1415927F, -0.34906584F, 0.0F);
      this.JawExtender2 = new AdvancedModelBox(this, 0, 7);
      this.JawExtender2.setPos(0.0F, 0.0F, 0.0F);
      this.JawExtender2.addBox(-1.5F, -1.5F, -13.0F, 3.0F, 3.0F, 13.0F, 0.0F);
      this.JawExtender3 = new AdvancedModelBox(this, 0, 7);
      this.JawExtender3.setPos(0.0F, 0.0F, 0.0F);
      this.JawExtender3.addBox(-1.5F, -1.5F, -13.0F, 3.0F, 3.0F, 13.0F, 0.0F);
      this.JawExtender4 = new AdvancedModelBox(this, 0, 7);
      this.JawExtender4.setPos(0.0F, 0.0F, 0.0F);
      this.JawExtender4.addBox(-1.5F, -1.5F, -13.0F, 3.0F, 3.0F, 13.0F, 0.0F);
      this.TopJaw.addChild(this.JawHook);
      this.Head.addChild(this.ToothL);
      this.Head.addChild(this.ToothB);
      this.Head.addChild(this.ToothT);
      this.Head.addChild(this.HeadInner);
      this.Head.addChild(this.ToothL_1);
      this.JawExtender.addChild(this.JawExtender2);
      this.JawExtender2.addChild(this.JawExtender3);
      this.JawExtender3.addChild(this.JawExtender4);
      this.JawExtender4.addChild(this.TopJaw);
      this.JawExtender4.addChild(this.BottomJaw);
      this.updateDefaultPose();
   }

   public void m_6973_(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.Head, this.JawExtender);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.Head, this.JawExtender, this.HeadInner, this.ToothB, this.ToothT, this.ToothL, this.ToothL_1, this.JawExtender2, this.JawExtender3, this.JawExtender4, this.TopJaw, this.BottomJaw, new AdvancedModelBox[]{this.JawHook});
   }

   public void animate(ItemStack stack, float partialTick) {
      this.resetToDefaultPose();
      CompoundTag tag = stack.m_41784_();
      Entity holder = Minecraft.m_91087_().f_91073_.m_6815_(tag.m_128451_("HolderID"));
      if (holder instanceof LivingEntity) {
         EntityDataProvider.getCapability(holder).ifPresent((data) -> {
            float lungeTicks = (float)data.miscData.lungeTicks + partialTick;
            this.progressRotation(this.TopJaw, lungeTicks, (float)Math.toRadians(-30.0D), 0.0F, 0.0F);
            this.progressRotation(this.BottomJaw, lungeTicks, (float)Math.toRadians(30.0D), 0.0F, 0.0F);
            this.progressPosition(this.JawExtender, lungeTicks, 0.0F, 0.0F, -4.0F);
            this.progressPosition(this.JawExtender2, lungeTicks, 0.0F, 0.0F, -10.0F);
            this.progressPosition(this.JawExtender3, lungeTicks, 0.0F, 0.0F, -10.0F);
            this.progressPosition(this.JawExtender4, lungeTicks, 0.0F, 0.0F, -10.0F);
         });
      }
   }

   public void renderStatue(PoseStack matrixStackIn, VertexConsumer bufferIn, int packedLightIn, Entity living) {
      this.m_7695_(matrixStackIn, bufferIn, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
   }
}
