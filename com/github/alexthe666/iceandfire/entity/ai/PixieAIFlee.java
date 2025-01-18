package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityPixie;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

public class PixieAIFlee<T extends Entity> extends Goal {
   private final float avoidDistance;
   private final Class<T> classToAvoid;
   protected EntityPixie pixie;
   protected T closestLivingEntity;
   private Vec3 hidePlace;
   @Nonnull
   private List<T> list = Collections.emptyList();

   public PixieAIFlee(EntityPixie pixie, Class<T> classToAvoidIn, float avoidDistanceIn, Predicate<? super T> avoidTargetSelectorIn) {
      this.pixie = pixie;
      this.classToAvoid = classToAvoidIn;
      this.avoidDistance = avoidDistanceIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.pixie.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && !this.pixie.m_21824_()) {
         if (this.pixie.m_9236_().m_46467_() % 4L == 0L) {
            this.list = this.pixie.m_9236_().m_6443_(this.classToAvoid, this.pixie.m_20191_().m_82377_((double)this.avoidDistance, 3.0D, (double)this.avoidDistance), EntitySelector.f_20408_);
         }

         if (this.list.isEmpty()) {
            return false;
         } else {
            this.closestLivingEntity = (Entity)this.list.get(0);
            if (this.closestLivingEntity != null) {
               Vec3 Vector3d = DefaultRandomPos.m_148407_(this.pixie, 16, 4, new Vec3(this.closestLivingEntity.m_20185_(), this.closestLivingEntity.m_20186_(), this.closestLivingEntity.m_20189_()));
               if (Vector3d == null) {
                  return false;
               } else {
                  Vector3d = Vector3d.m_82520_(0.0D, 1.0D, 0.0D);
                  this.pixie.m_21566_().m_6849_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, this.calculateRunSpeed());
                  this.pixie.m_21563_().m_24950_(Vector3d.f_82479_, Vector3d.f_82480_, Vector3d.f_82481_, 180.0F, 20.0F);
                  this.hidePlace = Vector3d;
                  this.pixie.slowSpeed = true;
                  return true;
               }
            } else {
               return false;
            }
         }
      } else {
         this.list = Collections.emptyList();
         return false;
      }
   }

   private double calculateRunSpeed() {
      if (this.pixie.ticksHeldItemFor > 6000) {
         return 0.1D;
      } else if (this.pixie.ticksHeldItemFor > 1200) {
         return 0.25D;
      } else {
         return this.pixie.ticksHeldItemFor > 600 ? 0.25D : 1.0D;
      }
   }

   public boolean m_8045_() {
      return this.hidePlace != null && this.pixie.m_20238_(this.hidePlace.m_82520_(0.5D, 0.5D, 0.5D)) < 2.0D;
   }

   public void m_8056_() {
      this.pixie.m_21566_().m_6849_(this.hidePlace.f_82479_, this.hidePlace.f_82480_, this.hidePlace.f_82481_, this.calculateRunSpeed());
      this.pixie.m_21563_().m_24950_(this.hidePlace.f_82479_, this.hidePlace.f_82480_, this.hidePlace.f_82481_, 180.0F, 20.0F);
   }

   public void m_8041_() {
      this.closestLivingEntity = null;
      this.pixie.slowSpeed = false;
   }
}
