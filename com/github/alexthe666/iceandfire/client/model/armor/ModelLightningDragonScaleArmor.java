package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelLightningDragonScaleArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelLightningDragonScaleArmor(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("right_leg").m_171599_("RightLegSpike3", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(-0.8F, 0.0F, -0.8F, -1.2217305F, 1.2217305F, -0.17453292F));
      partdefinition.m_171597_("right_leg").m_171599_("RightLegSpike2", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(-0.7F, 3.6F, -0.4F, -1.4114478F, 0.0F, 0.0F));
      partdefinition.m_171597_("right_leg").m_171599_("RightLegSpike", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, 5.0F, 0.4F, -1.4114478F, 0.0F, 0.0F));
      partdefinition.m_171597_("left_leg").m_171599_("LeftLegSpike3", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.8F, 0.0F, -0.8F, -1.2217305F, -1.2217305F, 0.17453292F));
      partdefinition.m_171597_("left_leg").m_171599_("LeftLegSpike2", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.7F, 3.6F, -0.4F, -1.4114478F, 0.0F, 0.0F));
      partdefinition.m_171597_("left_leg").m_171599_("LeftLegSpike", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, 0.0F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, 5.0F, 0.4F, -1.4114478F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("HornR", CubeListBuilder.m_171558_().m_171514_(48, 44).m_171481_(-1.0F, -0.5F, 0.0F, 2.0F, 3.0F, 5.0F), PartPose.m_171423_(-3.6F, -8.0F, 0.0F, 0.43633232F, -0.33161256F, -0.19198622F));
      partdefinition.m_171597_("head").m_171599_("HornL", CubeListBuilder.m_171558_().m_171514_(48, 44).m_171480_().m_171481_(-1.0F, -0.5F, 0.0F, 2.0F, 3.0F, 5.0F), PartPose.m_171423_(3.6F, -8.0F, 0.0F, 0.43633232F, 0.33161256F, 0.19198622F));
      partdefinition.m_171597_("head").m_171599_("HornR3", CubeListBuilder.m_171558_().m_171514_(47, 37).m_171480_().m_171481_(-0.5F, -0.8F, 0.0F, 1.0F, 2.0F, 4.0F), PartPose.m_171423_(-4.0F, -3.0F, 0.7F, -0.06981317F, -0.4886922F, -0.08726646F));
      partdefinition.m_171597_("head").m_171599_("HornL3", CubeListBuilder.m_171558_().m_171514_(47, 37).m_171480_().m_171481_(-0.5F, -0.8F, 0.0F, 1.0F, 2.0F, 4.0F), PartPose.m_171423_(4.0F, -3.0F, 0.7F, -0.06981317F, 0.4886922F, 0.08726646F));
      partdefinition.m_171597_("head").m_171599_("HeadFront", CubeListBuilder.m_171558_().m_171514_(6, 44).m_171481_(-3.5F, -2.8F, -8.8F, 7.0F, 2.0F, 5.0F), PartPose.m_171423_(0.0F, -5.6F, 0.0F, 0.045553092F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("Jaw", CubeListBuilder.m_171558_().m_171514_(6, 51).m_171481_(-3.5F, 4.0F, -7.4F, 7.0F, 2.0F, 5.0F), PartPose.m_171423_(0.0F, -5.4F, 0.0F, -0.091106184F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("HornR4", CubeListBuilder.m_171558_().m_171514_(46, 36).m_171480_().m_171481_(-0.5F, -0.8F, 0.0F, 1.0F, 2.0F, 5.0F), PartPose.m_171423_(-4.0F, -5.1F, 0.1F, 0.12217305F, -0.31415927F, -0.034906585F));
      partdefinition.m_171597_("head").m_171599_("HornL4", CubeListBuilder.m_171558_().m_171514_(46, 36).m_171480_().m_171481_(-0.5F, -0.8F, 0.0F, 1.0F, 2.0F, 5.0F), PartPose.m_171423_(4.0F, -5.1F, -0.1F, 0.12217305F, 0.31415927F, 0.034906585F));
      partdefinition.m_171597_("head").m_171599_("Teeth1", CubeListBuilder.m_171558_().m_171514_(6, 34).m_171481_(-3.6F, 0.1F, -8.9F, 4.0F, 1.0F, 5.0F), PartPose.m_171419_(0.0F, -6.2F, 0.0F));
      partdefinition.m_171597_("right_arm").m_171599_("RightShoulderSpike1", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(-0.5F, -1.2F, 0.0F, -3.1415927F, 0.0F, -0.17453292F));
      partdefinition.m_171597_("left_arm").m_171599_("LeftShoulderSpike1", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.5F, -1.2F, 0.0F, -3.1415927F, 0.0F, 0.17453292F));
      partdefinition.m_171597_("right_arm").m_171599_("RightShoulderSpike2", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(-1.8F, -0.1F, 0.0F, -3.1415927F, 0.0F, -0.2617994F));
      partdefinition.m_171597_("left_arm").m_171599_("LeftShoulderSpike2", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(1.8F, -0.1F, 0.0F, -3.1415927F, 0.0F, 0.2617994F));
      partdefinition.m_171597_("body").m_171599_("BackSpike1", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, 0.9F, 0.2F, 1.1838568F, 0.0F, 0.0F));
      partdefinition.m_171597_("body").m_171599_("BackSpike2", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, 3.5F, 0.6F, 1.1838568F, 0.0F, 0.0F));
      partdefinition.m_171597_("body").m_171599_("BackSpike3", CubeListBuilder.m_171558_().m_171514_(0, 34).m_171481_(-0.5F, 0.0F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, 6.4F, 0.0F, 1.1838568F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornR").m_171599_("HornR2", CubeListBuilder.m_171558_().m_171514_(46, 36).m_171480_().m_171481_(-0.5F, -0.8F, 0.0F, 1.0F, 2.0F, 5.0F), PartPose.m_171423_(0.0F, 0.3F, 4.5F, -0.075049154F, 0.5009095F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornL").m_171599_("HornL2", CubeListBuilder.m_171558_().m_171514_(46, 36).m_171480_().m_171481_(-0.5F, -0.8F, 0.0F, 1.0F, 2.0F, 5.0F), PartPose.m_171423_(0.0F, 0.3F, 4.5F, -0.075049154F, -0.5009095F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HeadFront").m_171599_("Teeth2", CubeListBuilder.m_171558_().m_171514_(6, 34).m_171480_().m_171481_(-0.4F, 0.1F, -8.9F, 4.0F, 1.0F, 5.0F), PartPose.m_171419_(0.0F, -1.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("Jaw").m_171599_("Teeth3", CubeListBuilder.m_171558_().m_171514_(6, 34).m_171481_(-3.6F, 0.1F, -8.9F, 4.0F, 1.0F, 5.0F), PartPose.m_171419_(0.0F, 3.0F, 1.4F));
      partdefinition.m_171597_("head").m_171597_("Jaw").m_171599_("Teeth4", CubeListBuilder.m_171558_().m_171514_(6, 34).m_171480_().m_171481_(-0.4F, 0.1F, -8.9F, 4.0F, 1.0F, 5.0F), PartPose.m_171419_(0.0F, 3.0F, 1.4F));
      return meshdefinition;
   }

   public static ModelPart getBakedModel(boolean inner) {
      return inner ? INNER_MODEL : OUTER_MODEL;
   }

   static {
      INNER_MODEL = m_170681_(CubeDeformation.f_171458_.m_171469_(INNER_MODEL_OFFSET), 0.0F).m_171576_().m_171583_(64, 64);
      OUTER_MODEL = m_170681_(CubeDeformation.f_171458_.m_171469_(OUTER_MODEL_OFFSET), 0.0F).m_171576_().m_171583_(64, 64);
   }
}
