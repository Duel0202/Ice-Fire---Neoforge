package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Guardian;

public class ModelGuardianStatue extends AdvancedEntityModel<Entity> {
   private final AdvancedModelBox guardianBody;
   private final AdvancedModelBox guardianEye;
   private final AdvancedModelBox[] guardianSpines;
   private final AdvancedModelBox[] guardianTail;

   public ModelGuardianStatue() {
      this.texWidth = 64;
      this.texHeight = 64;
      this.guardianSpines = new AdvancedModelBox[12];
      this.guardianBody = new AdvancedModelBox(this);
      this.guardianBody.setTextureOffset(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F);
      this.guardianBody.setTextureOffset(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F);
      this.guardianBody.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true);
      this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F);
      this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F);

      for(int i = 0; i < this.guardianSpines.length; ++i) {
         this.guardianSpines[i] = new AdvancedModelBox(this, 0, 0);
         this.guardianSpines[i].addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);
         this.guardianBody.addChild(this.guardianSpines[i]);
      }

      this.guardianEye = new AdvancedModelBox(this, 8, 0);
      this.guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F);
      this.guardianBody.addChild(this.guardianEye);
      this.guardianTail = new AdvancedModelBox[3];
      this.guardianTail[0] = new AdvancedModelBox(this, 40, 0);
      this.guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F);
      this.guardianTail[1] = new AdvancedModelBox(this, 0, 54);
      this.guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F);
      this.guardianTail[2] = new AdvancedModelBox(this);
      this.guardianTail[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F);
      this.guardianTail[2].setTextureOffset(25, 19).addBox(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F);
      this.guardianBody.addChild(this.guardianTail[0]);
      this.guardianTail[0].addChild(this.guardianTail[1]);
      this.guardianTail[1].addChild(this.guardianTail[2]);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return null;
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.guardianBody);
   }

   public void m_6973_(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      Guardian entityguardian = (Guardian)entityIn;
      float f = ageInTicks - (float)entityguardian.f_19797_;
      this.guardianBody.rotateAngleY = netHeadYaw * 0.017453292F;
      this.guardianBody.rotateAngleX = headPitch * 0.017453292F;
      float[] afloat = new float[]{1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F};
      float[] afloat1 = new float[]{0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F};
      float[] afloat2 = new float[]{0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F};
      float[] afloat3 = new float[]{0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F};
      float[] afloat4 = new float[]{-8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F};
      float[] afloat5 = new float[]{8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F};
      float f1 = 0.55F;

      for(int i = 0; i < 12; ++i) {
         this.guardianSpines[i].rotateAngleX = 3.1415927F * afloat[i];
         this.guardianSpines[i].rotateAngleY = 3.1415927F * afloat1[i];
         this.guardianSpines[i].rotateAngleZ = 3.1415927F * afloat2[i];
         this.guardianSpines[i].rotationPointX = afloat3[i] * (1.0F + Mth.m_14089_(ageInTicks * 1.5F + (float)i) * 0.01F - f1);
         this.guardianSpines[i].rotationPointY = 16.0F + afloat4[i] * (1.0F + Mth.m_14089_(ageInTicks * 1.5F + (float)i) * 0.01F - f1);
         this.guardianSpines[i].rotationPointZ = afloat5[i] * (1.0F + Mth.m_14089_(ageInTicks * 1.5F + (float)i) * 0.01F - f1);
      }

      this.guardianEye.rotationPointZ = -8.25F;
      Entity entity = Minecraft.m_91087_().m_91288_();
      if (entityguardian.m_32855_()) {
         LivingEntity var19 = entityguardian.m_32856_();
      }

      this.guardianEye.showModel = true;
      float f2 = entityguardian.m_32863_(f);
      this.guardianTail[0].rotateAngleY = Mth.m_14031_(f2) * 3.1415927F * 0.05F;
      this.guardianTail[1].rotateAngleY = Mth.m_14031_(f2) * 3.1415927F * 0.1F;
      this.guardianTail[1].rotationPointX = -1.5F;
      this.guardianTail[1].rotationPointY = 0.5F;
      this.guardianTail[1].rotationPointZ = 14.0F;
      this.guardianTail[2].rotateAngleY = Mth.m_14031_(f2) * 3.1415927F * 0.15F;
      this.guardianTail[2].rotationPointX = 0.5F;
      this.guardianTail[2].rotationPointY = 0.5F;
      this.guardianTail[2].rotationPointZ = 6.0F;
   }
}
