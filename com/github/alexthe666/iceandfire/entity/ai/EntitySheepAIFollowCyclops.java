package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class EntitySheepAIFollowCyclops extends Goal {
   Animal childAnimal;
   EntityCyclops cyclops;
   double moveSpeed;
   private int delayCounter;

   public EntitySheepAIFollowCyclops(Animal animal, double speed) {
      this.childAnimal = animal;
      this.moveSpeed = speed;
   }

   public boolean m_8036_() {
      List<EntityCyclops> list = this.childAnimal.m_9236_().m_45976_(EntityCyclops.class, this.childAnimal.m_20191_().m_82377_(16.0D, 8.0D, 16.0D));
      EntityCyclops cyclops = null;
      double d0 = Double.MAX_VALUE;
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         EntityCyclops cyclops1 = (EntityCyclops)var5.next();
         double d1 = this.childAnimal.m_20280_(cyclops1);
         if (d1 <= d0) {
            d0 = d1;
            cyclops = cyclops1;
         }
      }

      if (cyclops == null) {
         return false;
      } else if (d0 < 10.0D) {
         return false;
      } else {
         this.cyclops = cyclops;
         return true;
      }
   }

   public boolean m_8045_() {
      if (this.cyclops.m_6084_()) {
         return false;
      } else {
         double d0 = this.childAnimal.m_20280_(this.cyclops);
         return d0 >= 9.0D && d0 <= 256.0D;
      }
   }

   public void m_8056_() {
      this.delayCounter = 0;
   }

   public void m_8041_() {
      this.cyclops = null;
   }

   public void m_8037_() {
      if (--this.delayCounter <= 0) {
         this.delayCounter = this.m_183277_(10);
         if (this.childAnimal.m_20280_(this.cyclops) > 10.0D) {
            Path path = this.getPathToLivingEntity(this.childAnimal, this.cyclops);
            if (path != null) {
               this.childAnimal.m_21573_().m_26536_(path, this.moveSpeed);
            }
         }
      }

   }

   public Path getPathToLivingEntity(Animal entityIn, EntityCyclops cyclops) {
      PathNavigation navi = entityIn.m_21573_();
      Vec3 Vector3d = DefaultRandomPos.m_148412_(entityIn, 2, 7, cyclops.m_20182_(), 1.5707963705062866D);
      if (Vector3d != null) {
         BlockPos blockpos = BlockPos.m_274446_(Vector3d);
         return navi.m_7864_(blockpos, 0);
      } else {
         return null;
      }
   }
}
