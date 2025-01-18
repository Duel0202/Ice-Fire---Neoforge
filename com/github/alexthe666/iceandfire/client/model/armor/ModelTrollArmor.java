package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelTrollArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelTrollArmor(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("head").m_171599_("hornL", CubeListBuilder.m_171558_().m_171514_(3, 41).m_171480_().m_171481_(-1.0F, -0.5F, 0.0F, 1.0F, 2.0F, 5.0F), PartPose.m_171423_(3.0F, -2.2F, -3.0F, -0.7740535F, 2.959555F, -0.27314404F));
      partdefinition.m_171597_("head").m_171597_("hornL").m_171599_("hornL2", CubeListBuilder.m_171558_().m_171514_(15, 50).m_171480_().m_171481_(-0.51F, -0.8F, 0.0F, 1.0F, 2.0F, 7.0F), PartPose.m_171423_(-0.4F, 1.3F, 4.5F, 1.2747885F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("hornR", CubeListBuilder.m_171558_().m_171514_(4, 41).m_171480_().m_171481_(-0.5F, -0.5F, 0.0F, 1.0F, 2.0F, 5.0F), PartPose.m_171423_(-3.3F, -2.2F, -3.0F, -0.7740535F, -2.959555F, 0.27314404F));
      partdefinition.m_171597_("head").m_171597_("hornR").m_171599_("hornR2", CubeListBuilder.m_171558_().m_171514_(15, 50).m_171480_().m_171481_(-0.01F, -0.8F, 0.0F, 1.0F, 2.0F, 7.0F), PartPose.m_171423_(-0.6F, 1.3F, 4.5F, 1.2747885F, 0.0F, 0.0F));
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
