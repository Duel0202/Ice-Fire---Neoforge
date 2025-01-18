package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.util.IGroundMount;
import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class EntityGroundAIRide<T extends Mob & IGroundMount> extends Goal {
   private final T dragon;
   private Player player;

   public EntityGroundAIRide(T dragon) {
      this.m_7021_(EnumSet.of(Flag.MOVE, Flag.LOOK));
      this.dragon = dragon;
   }

   public boolean m_8036_() {
      this.player = ((IGroundMount)this.dragon).getRidingPlayer();
      return this.player != null;
   }

   public void m_8056_() {
      this.dragon.m_21573_().m_26573_();
   }

   public void m_8037_() {
      this.dragon.m_21573_().m_26573_();
      this.dragon.m_6710_((LivingEntity)null);
      double x = this.dragon.m_20185_();
      double y = this.dragon.m_20186_();
      if (this.dragon instanceof EntityDeathWorm) {
         y = ((EntityDeathWorm)this.dragon).processRiderY(y);
      }

      double z = this.dragon.m_20189_();
      double speed = 1.7999999523162842D * ((IGroundMount)this.dragon).getRideSpeedModifier();
      if (this.player.f_20900_ != 0.0F || this.player.f_20902_ != 0.0F) {
         Vec3 lookVec = this.player.m_20154_();
         if (this.player.f_20902_ < 0.0F) {
            lookVec = lookVec.m_82524_(3.1415927F);
         } else if (this.player.f_20900_ > 0.0F) {
            lookVec = lookVec.m_82524_(1.5707964F);
         } else if (this.player.f_20900_ < 0.0F) {
            lookVec = lookVec.m_82524_(-1.5707964F);
         }

         if ((double)Math.abs(this.player.f_20900_) > 0.0D) {
            speed *= 0.25D;
         }

         if ((double)this.player.f_20902_ < 0.0D) {
            speed *= 0.15D;
         }

         x += lookVec.f_82479_ * 10.0D;
         z += lookVec.f_82481_ * 10.0D;
      }

      this.dragon.m_21566_().m_6849_(x, y, z, speed);
   }
}
