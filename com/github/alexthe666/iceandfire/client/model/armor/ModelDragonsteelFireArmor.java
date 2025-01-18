package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelDragonsteelFireArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelDragonsteelFireArmor(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("head").m_171599_("HornR", CubeListBuilder.m_171558_().m_171514_(9, 39).m_171481_(-1.0F, -0.5F, 0.0F, 2.0F, 2.0F, 4.0F), PartPose.m_171423_(-2.5F, -7.9F, -4.2F, 0.43022367F, -0.15707964F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("HornL", CubeListBuilder.m_171558_().m_171514_(9, 39).m_171481_(-1.0F, -0.5F, 0.0F, 2.0F, 2.0F, 4.0F), PartPose.m_171423_(2.5F, -7.9F, -4.2F, 0.43022367F, 0.15707964F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("HornL4", CubeListBuilder.m_171558_().m_171514_(9, 38).m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 5.0F), PartPose.m_171423_(3.2F, -7.4F, -3.0F, -0.14713125F, 0.29670596F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("HornR4", CubeListBuilder.m_171558_().m_171514_(9, 38).m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 5.0F), PartPose.m_171423_(-3.2F, -7.4F, -3.0F, -0.14713125F, -0.29670596F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("visor1", CubeListBuilder.m_171558_().m_171514_(27, 50).m_171481_(-4.7F, -13.3F, -4.9F, 4.0F, 5.0F, 8.0F), PartPose.m_171419_(0.0F, 9.0F, 0.2F));
      partdefinition.m_171597_("head").m_171599_("visor2", CubeListBuilder.m_171558_().m_171514_(27, 50).m_171480_().m_171481_(0.8F, -13.3F, -4.9F, 4.0F, 5.0F, 8.0F), PartPose.m_171419_(-0.1F, 9.0F, 0.2F));
      partdefinition.m_171597_("right_arm").m_171599_("sleeveRight", CubeListBuilder.m_171558_().m_171514_(36, 33).m_171481_(-4.5F, -2.1F, -2.4F, 5.0F, 6.0F, 5.0F), PartPose.m_171423_(0.3F, -0.3F, 0.0F, 0.0F, 0.0F, -0.12217305F));
      partdefinition.m_171597_("left_arm").m_171599_("sleeveLeft", CubeListBuilder.m_171558_().m_171514_(36, 33).m_171480_().m_171481_(-0.5F, -2.1F, -2.4F, 5.0F, 6.0F, 5.0F), PartPose.m_171423_(-0.7F, -0.3F, 0.0F, 0.0F, 0.0F, 0.12217305F));
      partdefinition.m_171597_("right_leg").m_171599_("robeLowerRight", CubeListBuilder.m_171558_().m_171514_(4, 51).m_171480_().m_171481_(-2.1F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F), PartPose.m_171419_(0.0F, -0.2F, 0.0F));
      partdefinition.m_171597_("left_leg").m_171599_("robeLowerLeft", CubeListBuilder.m_171558_().m_171514_(4, 51).m_171481_(-1.9F, 0.0F, -2.5F, 4.0F, 7.0F, 5.0F), PartPose.m_171419_(0.0F, -0.2F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornR").m_171599_("HornR2", CubeListBuilder.m_171558_().m_171514_(9, 38).m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 5.0F), PartPose.m_171423_(0.0F, 0.3F, 3.6F, -0.33911747F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornL").m_171599_("HornL2", CubeListBuilder.m_171558_().m_171514_(9, 38).m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 5.0F), PartPose.m_171423_(0.0F, 0.3F, 3.6F, -0.33911747F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornR").m_171597_("HornR2").m_171599_("HornR3", CubeListBuilder.m_171558_().m_171514_(24, 44).m_171480_().m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 4.0F), PartPose.m_171423_(0.0F, -0.1F, 4.3F, 0.59184116F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornL").m_171597_("HornL2").m_171599_("HornL3", CubeListBuilder.m_171558_().m_171514_(24, 44).m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 4.0F), PartPose.m_171423_(0.0F, -0.1F, 4.3F, 0.59184116F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornR4").m_171599_("HornR5", CubeListBuilder.m_171558_().m_171514_(25, 45).m_171480_().m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 3.0F), PartPose.m_171423_(0.0F, -0.1F, 4.3F, 0.36494833F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171597_("HornL4").m_171599_("HornL5", CubeListBuilder.m_171558_().m_171514_(25, 45).m_171481_(-1.0F, -0.8F, 0.0F, 2.0F, 2.0F, 3.0F), PartPose.m_171423_(0.0F, -0.1F, 4.3F, 0.36494833F, 0.0F, 0.0F));
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
