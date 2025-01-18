package com.github.alexthe666.iceandfire.client.model.armor;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.jetbrains.annotations.NotNull;

public class ArmorModelBase extends HumanoidModel<LivingEntity> {
   protected static float INNER_MODEL_OFFSET = 0.38F;
   protected static float OUTER_MODEL_OFFSET = 0.45F;

   public ArmorModelBase(ModelPart p_170677_) {
      super(p_170677_);
   }

   public void m_6973_(@NotNull LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
      if (entityIn instanceof ArmorStand) {
         ArmorStand armorStand = (ArmorStand)entityIn;
         this.f_102808_.f_104203_ = 0.017453292F * armorStand.m_31680_().m_123156_();
         this.f_102808_.f_104204_ = 0.017453292F * armorStand.m_31680_().m_123157_();
         this.f_102808_.f_104205_ = 0.017453292F * armorStand.m_31680_().m_123158_();
         this.f_102810_.f_104203_ = 0.017453292F * armorStand.m_31685_().m_123156_();
         this.f_102810_.f_104204_ = 0.017453292F * armorStand.m_31685_().m_123157_();
         this.f_102810_.f_104205_ = 0.017453292F * armorStand.m_31685_().m_123158_();
         this.f_102812_.f_104203_ = 0.017453292F * armorStand.m_31688_().m_123156_();
         this.f_102812_.f_104204_ = 0.017453292F * armorStand.m_31688_().m_123157_();
         this.f_102812_.f_104205_ = 0.017453292F * armorStand.m_31688_().m_123158_();
         this.f_102811_.f_104203_ = 0.017453292F * armorStand.m_31689_().m_123156_();
         this.f_102811_.f_104204_ = 0.017453292F * armorStand.m_31689_().m_123157_();
         this.f_102811_.f_104205_ = 0.017453292F * armorStand.m_31689_().m_123158_();
         this.f_102814_.f_104203_ = 0.017453292F * armorStand.m_31691_().m_123156_();
         this.f_102814_.f_104204_ = 0.017453292F * armorStand.m_31691_().m_123157_();
         this.f_102814_.f_104205_ = 0.017453292F * armorStand.m_31691_().m_123158_();
         this.f_102813_.f_104203_ = 0.017453292F * armorStand.m_31694_().m_123156_();
         this.f_102813_.f_104204_ = 0.017453292F * armorStand.m_31694_().m_123157_();
         this.f_102813_.f_104205_ = 0.017453292F * armorStand.m_31694_().m_123158_();
         this.f_102809_.m_104315_(this.f_102808_);
      } else {
         super.m_6973_(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
      }

   }
}
