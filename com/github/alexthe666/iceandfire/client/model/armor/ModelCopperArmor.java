package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ModelCopperArmor extends ArmorModelBase {
   private static final ModelPart INNER_MODEL;
   private static final ModelPart OUTER_MODEL;

   public ModelCopperArmor(boolean inner) {
      super(getBakedModel(inner));
   }

   public static MeshDefinition m_170681_(CubeDeformation deformation, float offset) {
      MeshDefinition meshdefinition = HumanoidModel.m_170681_(deformation, offset);
      PartDefinition partdefinition = meshdefinition.m_171576_();
      partdefinition.m_171597_("head").m_171599_("crest", CubeListBuilder.m_171558_().m_171514_(23, 31).m_171481_(0.0F, -7.5F, -9.0F, 0.0F, 16.0F, 14.0F), PartPose.m_171419_(0.0F, -7.6F, 2.6F));
      partdefinition.m_171597_("head").m_171599_("facePlate", CubeListBuilder.m_171558_().m_171514_(34, 32).m_171481_(-4.5F, -8.2F, -4.01F, 9.0F, 10.0F, 1.0F), PartPose.m_171419_(0.0F, 0.0F, 0.0F));
      partdefinition.m_171597_("right_leg").m_171599_("robeLowerRight", CubeListBuilder.m_171558_().m_171514_(0, 51).m_171481_(-2.1F, 0.0F, -2.5F, 4.0F, 8.0F, 5.0F).m_171480_(), PartPose.m_171419_(0.0F, -0.2F, 0.0F));
      partdefinition.m_171597_("left_leg").m_171599_("robeLowerLeft", CubeListBuilder.m_171558_().m_171514_(0, 51).m_171481_(-1.9F, 0.0F, -2.5F, 4.0F, 8.0F, 5.0F), PartPose.m_171419_(0.0F, -0.2F, 0.0F));
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
