package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelSeaSerpentArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelSeaSerpentArmor(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("head").m_171599_("headFin", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171481_(-0.5F, -8.4F, -7.9F, 1.0F, 16.0F, 14.0F), PartPose.m_171423_(-3.5F, -8.8F, 3.5F, 3.1415927F, -0.5235988F, 0.0F));
      partdefinition.m_171597_("head").m_171599_("headFin2", CubeListBuilder.m_171558_().m_171514_(0, 32).m_171480_().m_171481_(-0.5F, -8.4F, -7.9F, 1.0F, 16.0F, 14.0F), PartPose.m_171423_(3.5F, -8.8F, 3.5F, 3.1415927F, 0.5235988F, 0.0F));
      partdefinition.m_171597_("right_arm").m_171599_("armFinR", CubeListBuilder.m_171558_().m_171514_(30, 32).m_171481_(-0.5F, -5.4F, -6.0F, 1.0F, 7.0F, 5.0F), PartPose.m_171423_(-1.5F, 4.0F, -0.4F, 3.1415927F, -1.3089969F, -0.0034906585F));
      partdefinition.m_171597_("left_arm").m_171599_("armFinL", CubeListBuilder.m_171558_().m_171514_(30, 32).m_171480_().m_171481_(-0.5F, -5.4F, -6.0F, 1.0F, 7.0F, 5.0F), PartPose.m_171423_(1.5F, 4.0F, -0.4F, 3.1415927F, 1.3089969F, 0.0F));
      partdefinition.m_171597_("right_leg").m_171599_("legFinR", CubeListBuilder.m_171558_().m_171514_(45, 31).m_171481_(-0.5F, -5.4F, -6.0F, 1.0F, 7.0F, 6.0F), PartPose.m_171423_(-1.5F, 5.2F, 1.6F, 3.1415927F, -1.3089969F, 0.0F));
      partdefinition.m_171597_("left_leg").m_171599_("legFinL", CubeListBuilder.m_171558_().m_171514_(45, 31).m_171480_().m_171481_(-0.5F, -5.4F, -6.0F, 1.0F, 7.0F, 6.0F), PartPose.m_171423_(1.5F, 5.2F, 1.6F, 3.1415927F, 1.3089969F, 0.0F));
      partdefinition.m_171597_("right_arm").m_171599_("shoulderR", CubeListBuilder.m_171558_().m_171514_(38, 46).m_171481_(-3.5F, -2.0F, -2.5F, 5.0F, 12.0F, 5.0F), PartPose.m_171419_(0.0F, -0.5F, 0.0F));
      partdefinition.m_171597_("left_arm").m_171599_("shoulderL", CubeListBuilder.m_171558_().m_171514_(38, 46).m_171480_().m_171481_(-1.5F, -2.0F, -2.5F, 5.0F, 12.0F, 5.0F), PartPose.m_171419_(0.0F, -0.5F, 0.0F));
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
