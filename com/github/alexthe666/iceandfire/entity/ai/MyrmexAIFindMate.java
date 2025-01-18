package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexRoyal;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.util.IAFMath;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.phys.AABB;

public class MyrmexAIFindMate<T extends EntityMyrmexBase> extends TargetGoal {
   protected final DragonAITargetItems.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super Entity> targetEntitySelector;
   public EntityMyrmexRoyal myrmex;
   protected EntityMyrmexBase targetEntity;
   @Nonnull
   private List<Entity> list;

   public MyrmexAIFindMate(EntityMyrmexRoyal myrmex) {
      super(myrmex, false, false);
      this.list = IAFMath.emptyEntityList;
      this.theNearestAttackableTargetSorter = new DragonAITargetItems.Sorter(myrmex);
      this.targetEntitySelector = new Predicate<Entity>() {
         public boolean test(Entity myrmex) {
            return myrmex instanceof EntityMyrmexRoyal && ((EntityMyrmexRoyal)myrmex).getGrowthStage() >= 2;
         }
      };
      this.myrmex = myrmex;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.myrmex.shouldHaveNormalAI()) {
         this.list = IAFMath.emptyEntityList;
         return false;
      } else if (this.myrmex.canMove() && this.myrmex.m_5448_() == null && this.myrmex.releaseTicks >= 400 && this.myrmex.mate == null) {
         MyrmexHive village = this.myrmex.getHive();
         if (village == null) {
            village = MyrmexWorldData.get(this.myrmex.m_9236_()).getNearestHive(this.myrmex.m_20183_(), 100);
         }

         if (village != null && village.getCenter().m_203198_(this.myrmex.m_20185_(), (double)village.getCenter().m_123342_(), this.myrmex.m_20189_()) < 2000.0D) {
            this.list = IAFMath.emptyEntityList;
            return false;
         } else {
            if (this.myrmex.m_9236_().m_46467_() % 4L == 0L) {
               this.list = this.f_26135_.m_9236_().m_6249_(this.myrmex, this.getTargetableArea(100.0D), this.targetEntitySelector);
            }

            if (this.list.isEmpty()) {
               return false;
            } else {
               this.list.sort(this.theNearestAttackableTargetSorter);
               Iterator var2 = this.list.iterator();

               Entity royal;
               do {
                  if (!var2.hasNext()) {
                     return false;
                  }

                  royal = (Entity)var2.next();
               } while(!this.myrmex.m_7848_((EntityMyrmexRoyal)royal));

               this.myrmex.mate = (EntityMyrmexRoyal)royal;
               this.myrmex.m_9236_().m_7605_(this.myrmex, (byte)76);
               return true;
            }
         }
      } else {
         this.list = IAFMath.emptyEntityList;
         return false;
      }
   }

   protected AABB getTargetableArea(double targetDistance) {
      return this.f_26135_.m_20191_().m_82377_(targetDistance, targetDistance / 2.0D, targetDistance);
   }

   public boolean m_8045_() {
      return false;
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity theEntity;

      public Sorter(EntityMyrmexBase theEntityIn) {
         this.theEntity = theEntityIn;
      }

      public int compare(Entity p_compare_1_, Entity p_compare_2_) {
         double d0 = this.theEntity.m_20280_(p_compare_1_);
         double d1 = this.theEntity.m_20280_(p_compare_2_);
         return Double.compare(d0, d1);
      }
   }
}
