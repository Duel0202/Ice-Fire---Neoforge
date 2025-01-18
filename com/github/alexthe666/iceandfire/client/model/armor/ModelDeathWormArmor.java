package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelDeathWormArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelDeathWormArmor(ModelPart modelPart) {
      super(modelPart);
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("right_arm").m_171599_("spineR1", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(-1.0F, -2.7F, 0.0F, -0.49148473F, 0.0F, 0.0F));
      partdefinition.m_171597_("left_arm").m_171599_("spineL1", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(1.0F, -2.7F, 0.0F, -0.49148473F, 0.0F, 0.0F));
      partdefinition.m_171597_("right_arm").m_171599_("spineR2", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.6F, -1.7F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.m_171423_(-2.5F, -1.6F, 0.0F, -0.49148473F, 0.0F, 0.0F));
      partdefinition.m_171597_("left_arm").m_171599_("spineL2", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.4F, -1.7F, -0.5F, 1.0F, 2.0F, 1.0F), PartPose.m_171423_(2.5F, -1.6F, 0.0F, -0.49148473F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH1", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, -9.0F, -3.0F, -0.49148473F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH2", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -2.7F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.m_171423_(0.0F, -9.0F, 0.0F, -0.49148473F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH3", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, -9.0F, 3.0F, -0.8651597F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH4", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -2.7F, -0.5F, 1.0F, 4.0F, 1.0F), PartPose.m_171423_(0.0F, -8.0F, 5.0F, -1.548107F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH5", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, -6.0F, 5.0F, -1.821251F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH6", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -2.7F, -0.5F, 1.0F, 5.0F, 1.0F), PartPose.m_171423_(0.0F, -3.5F, 5.0F, -2.003289F, 0.0F, 0.0F));
      partdefinition.m_171597_("hat").m_171599_("spineH7", CubeListBuilder.m_171558_().m_171514_(32, 40).m_171481_(-0.5F, -1.7F, -0.5F, 1.0F, 3.0F, 1.0F), PartPose.m_171423_(0.0F, -1.3F, 4.5F, -2.003289F, 0.0F, 0.0F));
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
