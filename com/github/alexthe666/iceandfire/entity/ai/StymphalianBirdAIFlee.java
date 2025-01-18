package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityStymphalianBird;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class StymphalianBirdAIFlee extends Goal {
   private final Predicate<Entity> canBeSeenSelector;
   private final float avoidDistance;
   protected EntityStymphalianBird stymphalianBird;
   protected LivingEntity closestLivingEntity;
   private Vec3 hidePlace;

   public StymphalianBirdAIFlee(EntityStymphalianBird stymphalianBird, float avoidDistanceIn) {
      this.stymphalianBird = stymphalianBird;
      this.canBeSeenSelector = new Predicate<Entity>() {
         public boolean test(Entity entity) {
            return entity instanceof Player && entity.m_6084_() && StymphalianBirdAIFlee.this.stymphalianBird.m_21574_().m_148306_(entity) && !StymphalianBirdAIFlee.this.stymphalianBird.m_7307_(entity);
         }
      };
      this.avoidDistance = avoidDistanceIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.stymphalianBird.getVictor() == null) {
         return false;
      } else {
         List<LivingEntity> list = this.stymphalianBird.m_9236_().m_6443_(LivingEntity.class, this.stymphalianBird.m_20191_().m_82377_((double)this.avoidDistance, 3.0D, (double)this.avoidDistance), this.canBeSeenSelector);
         if (list.isEmpty()) {
            return false;
         } else {
            this.closestLivingEntity = (LivingEntity)list.get(0);
            if (this.closestLivingEntity != null && this.stymphalianBird.getVictor() != null && this.closestLivingEntity.equals(this.stymphalianBird.getVictor())) {
               Vec3 Vector3d = DefaultRandomPos.m_148407_(this.stymphalianBird, 32, 7, new Vec3(this.closestLivingEntity.m_20185_(), this.closestLivingEntity.m_20186_(), this.closestLivingEntity.m_20189_()));
               if (Vector3d == null) {
                  return false;
               } else {
                  Vector3d = Vector3d.m_82520_(0.0D, 3.0D, 0.0D);
                  this.stymphalianBird.m_21566_().m_6849_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, 3.0D);
                  this.stymphalianBird.m_21563_().m_24950_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, 180.0F, 20.0F);
                  this.hidePlace = Vector3d;
                  return true;
               }
            } else {
               return false;
            }
         }
      }
   }

   public boolean m_8045_() {
      return this.hidePlace != null && this.stymphalianBird.m_20238_(this.hidePlace.m_82520_(0.5D, 0.5D, 0.5D)) < 2.0D;
   }

   public void m_8056_() {
      this.stymphalianBird.m_21566_().m_6849_(this.hidePlace.f_82479_, this.hidePlace.f_82480_, this.hidePlace.f_82481_, 3.0D);
      this.stymphalianBird.m_21563_().m_24950_(this.hidePlace.f_82479_, this.hidePlace.f_82480_, this.hidePlace.f_82481_, 180.0F, 20.0F);
   }

   public void m_8041_() {
      this.closestLivingEntity = null;
   }
}
