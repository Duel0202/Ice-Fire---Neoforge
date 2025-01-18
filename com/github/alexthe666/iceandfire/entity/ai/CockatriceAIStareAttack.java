package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityCockatrice;
import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import java.util.EnumSet;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.phys.Vec3;

public class CockatriceAIStareAttack extends Goal {
   private final EntityCockatrice entity;
   private final double moveSpeedAmp;
   private int seeTime;
   private BlockPos target = null;

   public CockatriceAIStareAttack(EntityCockatrice cockatrice, double speedAmplifier, int delay, float maxDistance) {
      this.entity = cockatrice;
      this.moveSpeedAmp = speedAmplifier;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public static boolean isEntityLookingAt(LivingEntity looker, LivingEntity seen, double degree) {
      Vec3 Vector3d = looker.m_20252_(1.0F).m_82541_();
      Vec3 Vector3d1 = new Vec3(seen.m_20185_() - looker.m_20185_(), seen.m_20191_().f_82289_ + (double)seen.m_20192_() - (looker.m_20186_() + (double)looker.m_20192_()), seen.m_20189_() - looker.m_20189_());
      Vector3d1 = Vector3d1.m_82541_();
      double d0 = Vector3d1.m_82553_();
      double d1 = Vector3d.m_82526_(Vector3d1);
      return d1 > 1.0D - degree / d0 && !looker.m_5833_();
   }

   public void setAttackCooldown(int cooldown) {
   }

   public boolean m_8036_() {
      return this.entity.m_5448_() != null;
   }

   public boolean m_8045_() {
      return this.m_8036_();
   }

   public void m_8041_() {
      super.m_8041_();
      this.seeTime = 0;
      this.entity.m_5810_();
      this.entity.m_21573_().m_26573_();
      this.target = null;
   }

   public void m_8037_() {
      LivingEntity LivingEntity = this.entity.m_5448_();
      if (LivingEntity != null) {
         if (EntityGorgon.isStoneMob(LivingEntity) || !LivingEntity.m_6084_()) {
            this.entity.m_6710_((LivingEntity)null);
            this.entity.setTargetedEntity(0);
            this.m_8041_();
            return;
         }

         if (!isEntityLookingAt(LivingEntity, this.entity, 0.6000000238418579D) || LivingEntity.f_19854_ != this.entity.m_20185_() || LivingEntity.f_19855_ != this.entity.m_20186_() || LivingEntity.f_19856_ != this.entity.m_20189_()) {
            this.entity.m_21573_().m_26573_();
            BlockPos pos = DragonUtils.getBlockInTargetsViewCockatrice(this.entity, LivingEntity);
            if (this.target == null || pos.m_123331_(this.target) > 4.0D) {
               this.target = pos;
            }
         }

         this.entity.setTargetedEntity(LivingEntity.m_19879_());
         this.entity.m_20275_(LivingEntity.m_20185_(), LivingEntity.m_20191_().f_82289_, LivingEntity.m_20189_());
         boolean flag = this.entity.m_21574_().m_148306_(LivingEntity);
         boolean flag1 = this.seeTime > 0;
         if (flag != flag1) {
            this.seeTime = 0;
         }

         if (flag) {
            ++this.seeTime;
         } else {
            --this.seeTime;
         }

         if (this.target != null && this.entity.m_20275_((double)this.target.m_123341_(), (double)this.target.m_123342_(), (double)this.target.m_123343_()) > 16.0D && !isEntityLookingAt(LivingEntity, this.entity, 0.6000000238418579D)) {
            this.entity.m_21573_().m_26519_((double)this.target.m_123341_(), (double)this.target.m_123342_(), (double)this.target.m_123343_(), this.moveSpeedAmp);
         }

         this.entity.m_21563_().m_24950_(LivingEntity.m_20185_(), LivingEntity.m_20186_() + (double)LivingEntity.m_20192_(), LivingEntity.m_20189_(), (float)this.entity.m_8085_(), (float)this.entity.m_8132_());
      }

   }
}
