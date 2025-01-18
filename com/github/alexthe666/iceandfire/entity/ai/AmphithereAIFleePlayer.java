package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityAmphithere;
import com.github.alexthe666.iceandfire.util.IAFMath;
import java.util.EnumSet;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class AmphithereAIFleePlayer extends Goal {
   private final double farSpeed;
   private final double nearSpeed;
   private final float avoidDistance;
   protected EntityAmphithere entity;
   protected Player closestLivingEntity;
   private Path path;
   @Nonnull
   private List<Player> list;

   public AmphithereAIFleePlayer(EntityAmphithere entityIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
      this.list = IAFMath.emptyPlayerEntityList;
      this.entity = entityIn;
      this.avoidDistance = avoidDistanceIn;
      this.farSpeed = farSpeedIn;
      this.nearSpeed = nearSpeedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.entity.isFlying() && !this.entity.m_21824_()) {
         if (this.entity.m_9236_().m_46467_() % 4L == 0L) {
            this.list = this.entity.m_9236_().m_6443_(Player.class, this.entity.m_20191_().m_82377_((double)this.avoidDistance, 6.0D, (double)this.avoidDistance), EntitySelector.f_20406_);
         }

         if (this.list.isEmpty()) {
            return false;
         } else {
            this.closestLivingEntity = (Player)this.list.get(0);
            Vec3 Vector3d = DefaultRandomPos.m_148407_(this.entity, 20, 7, new Vec3(this.closestLivingEntity.m_20185_(), this.closestLivingEntity.m_20186_(), this.closestLivingEntity.m_20189_()));
            if (Vector3d == null) {
               return false;
            } else if (this.closestLivingEntity.m_20238_(Vector3d) < this.closestLivingEntity.m_20280_(this.entity)) {
               return false;
            } else {
               this.path = this.entity.m_21573_().m_26524_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, 0);
               return this.path != null;
            }
         }
      } else {
         this.list = IAFMath.emptyPlayerEntityList;
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.entity.m_21573_().m_26571_();
   }

   public void m_8056_() {
      this.entity.m_21573_().m_26536_(this.path, this.farSpeed);
   }

   public void m_8041_() {
      this.closestLivingEntity = null;
   }

   public void m_8037_() {
      if (this.entity.m_20280_(this.closestLivingEntity) < 49.0D) {
         this.entity.m_21573_().m_26517_(this.nearSpeed);
      } else {
         this.entity.m_21573_().m_26517_(this.farSpeed);
      }

   }
}
