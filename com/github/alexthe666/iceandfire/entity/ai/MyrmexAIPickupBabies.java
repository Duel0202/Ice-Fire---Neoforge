package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexEgg;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexWorker;
import com.github.alexthe666.iceandfire.util.IAFMath;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class MyrmexAIPickupBabies<T extends ItemEntity> extends TargetGoal {
   protected final DragonAITargetItems.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super LivingEntity> targetEntitySelector;
   public EntityMyrmexWorker myrmex;
   protected LivingEntity targetEntity;
   private List<LivingEntity> listBabies;

   public MyrmexAIPickupBabies(EntityMyrmexWorker myrmex) {
      super(myrmex, false, false);
      this.listBabies = IAFMath.emptyLivingEntityList;
      this.theNearestAttackableTargetSorter = new DragonAITargetItems.Sorter(myrmex);
      this.targetEntitySelector = new Predicate<LivingEntity>() {
         public boolean test(LivingEntity myrmex) {
            return myrmex instanceof EntityMyrmexBase && ((EntityMyrmexBase)myrmex).getGrowthStage() < 2 && !((EntityMyrmexBase)myrmex).isInNursery() || myrmex instanceof EntityMyrmexEgg && !((EntityMyrmexEgg)myrmex).isInNursery();
         }
      };
      this.myrmex = myrmex;
      this.m_7021_(EnumSet.of(Flag.TARGET));
   }

   public boolean m_8036_() {
      if (this.myrmex.canMove() && !this.myrmex.holdingSomething() && this.myrmex.m_21573_().m_26571_() && !this.myrmex.shouldEnterHive() && this.myrmex.keepSearching && !this.myrmex.holdingBaby()) {
         if (this.myrmex.m_9236_().m_46467_() % 4L == 0L) {
            this.listBabies = this.f_26135_.m_9236_().m_6443_(LivingEntity.class, this.getTargetableArea(20.0D), this.targetEntitySelector);
         }

         if (this.listBabies.isEmpty()) {
            return false;
         } else {
            this.listBabies.sort(this.theNearestAttackableTargetSorter);
            this.targetEntity = (LivingEntity)this.listBabies.get(0);
            return true;
         }
      } else {
         this.listBabies = IAFMath.emptyLivingEntityList;
         return false;
      }
   }

   protected AABB getTargetableArea(double targetDistance) {
      return this.f_26135_.m_20191_().m_82377_(targetDistance, 4.0D, targetDistance);
   }

   public void m_8056_() {
      this.f_26135_.m_21573_().m_26519_(this.targetEntity.m_20185_(), this.targetEntity.m_20186_(), this.targetEntity.m_20189_(), 1.0D);
      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.targetEntity != null && this.targetEntity.m_6084_() && this.f_26135_.m_20280_(this.targetEntity) < 2.0D) {
         this.targetEntity.m_20329_(this.myrmex);
      }

      this.m_8041_();
   }

   public boolean m_8045_() {
      return !this.f_26135_.m_21573_().m_26571_();
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
