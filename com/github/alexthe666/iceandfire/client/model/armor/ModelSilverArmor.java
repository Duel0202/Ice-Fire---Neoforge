package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelSilverArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelSilverArmor(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("head").m_171599_("faceGuard", CubeListBuilder.m_171558_().m_171514_(30, 47).m_171481_(-4.5F, -3.0F, -6.1F, 9.0F, 9.0F, 8.0F), PartPose.m_171423_(0.0F, -6.6F, 1.9F, -0.7285004F, 0.0F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("helmWingR", CubeListBuilder.m_171558_().m_171514_(2, 37).m_171481_(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 6.0F), PartPose.m_171423_(-3.0F, -6.3F, 1.3F, 0.5235988F, -0.43633232F, -0.05235988F));
      partdefinition.m_171597_("head").m_171599_("helmWingL", CubeListBuilder.m_171558_().m_171514_(2, 37).m_171480_().m_171481_(-0.5F, -1.0F, 0.0F, 1.0F, 4.0F, 6.0F), PartPose.m_171423_(3.0F, -6.3F, 1.3F, 0.5235988F, 0.43633232F, 0.05235988F));
      partdefinition.m_171597_("hat").m_171599_("crest", CubeListBuilder.m_171558_().m_171514_(18, 32).m_171481_(0.0F, -0.5F, 0.0F, 1.0F, 9.0F, 9.0F), PartPose.m_171423_(0.0F, -7.9F, -0.1F, 1.2292354F, 0.0F, 0.0F));
      partdefinition.m_171597_("body").m_171599_("robeLowerBack", CubeListBuilder.m_171558_().m_171514_(4, 55).m_171480_().m_171481_(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 1.0F), PartPose.m_171423_(0.0F, 12.0F, 0.0F, 0.0F, 3.1415927F, 0.0F));
      partdefinition.m_171597_("body").m_171599_("robeLower", CubeListBuilder.m_171558_().m_171514_(4, 55).m_171481_(-4.0F, 0.0F, -2.5F, 8.0F, 8.0F, 1.0F), PartPose.m_171419_(0.0F, 12.0F, 0.0F));
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
