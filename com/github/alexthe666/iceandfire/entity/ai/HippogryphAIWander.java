package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class HippogryphAIWander extends Goal {
   private final EntityHippogryph hippo;
   private double xPosition;
   private double yPosition;
   private double zPosition;
   private final double speed;
   private int executionChance;
   private boolean mustUpdate;

   public HippogryphAIWander(EntityHippogryph creatureIn, double speedIn) {
      this(creatureIn, speedIn, 20);
   }

   public HippogryphAIWander(EntityHippogryph creatureIn, double speedIn, int chance) {
      this.hippo = creatureIn;
      this.speed = speedIn;
      this.executionChance = chance;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.hippo.canMove()) {
         return false;
      } else if (!this.hippo.isFlying() && !this.hippo.isHovering()) {
         if (!this.mustUpdate && this.hippo.m_217043_().m_188503_(this.executionChance) != 0) {
            return false;
         } else {
            Vec3 Vector3d = DefaultRandomPos.m_148403_(this.hippo, 10, 7);
            if (Vector3d == null) {
               return false;
            } else {
               this.xPosition = Vector3d.f_82479_;
               this.yPosition = Vector3d.f_82480_;
               this.zPosition = Vector3d.f_82481_;
               this.mustUpdate = false;
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.hippo.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.hippo.m_21573_().m_26519_(this.xPosition, this.yPosition, this.zPosition, this.speed);
   }

   public void makeUpdate() {
      this.mustUpdate = true;
   }

   public void setExecutionChance(int newchance) {
      this.executionChance = newchance;
   }
}
