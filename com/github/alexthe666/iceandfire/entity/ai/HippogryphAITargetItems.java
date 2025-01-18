package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.util.IAFMath;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;

public class HippogryphAITargetItems<T extends ItemEntity> extends TargetGoal {
   protected final DragonAITargetItems.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super ItemEntity> targetEntitySelector;
   protected ItemEntity targetEntity;
   protected final int targetChance;
   @Nonnull
   private List<ItemEntity> list;

   public HippogryphAITargetItems(Mob creature, boolean checkSight) {
      this(creature, checkSight, false);
   }

   public HippogryphAITargetItems(Mob creature, boolean checkSight, boolean onlyNearby) {
      this(creature, 20, checkSight, onlyNearby, (Predicate)null);
   }

   public HippogryphAITargetItems(Mob creature, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate<? super T> targetSelector) {
      super(creature, checkSight, onlyNearby);
      this.list = IAFMath.emptyItemEntityList;
      this.theNearestAttackableTargetSorter = new DragonAITargetItems.Sorter(creature);
      this.targetChance = chance;
      this.targetEntitySelector = (item) -> {
         return item != null && !item.m_32055_().m_41619_() && item.m_32055_().m_204117_(IafItemTags.TAME_HIPPOGRYPH);
      };
   }

   public boolean m_8036_() {
      if (this.targetChance > 0 && this.f_26135_.m_217043_().m_188503_(this.targetChance) != 0) {
         return false;
      } else if (!((EntityHippogryph)this.f_26135_).canMove()) {
         this.list = IAFMath.emptyItemEntityList;
         return false;
      } else {
         return this.updateList();
      }
   }

   private boolean updateList() {
      this.list = this.f_26135_.m_9236_().m_6443_(ItemEntity.class, this.getTargetableArea(this.m_7623_()), this.targetEntitySelector);
      if (this.list.isEmpty()) {
         return false;
      } else {
         this.list.sort(this.theNearestAttackableTargetSorter);
         this.targetEntity = (ItemEntity)this.list.get(0);
         return true;
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
      if (this.targetEntity != null && this.targetEntity.m_6084_()) {
         if (this.getAttackReachSqr(this.targetEntity) >= this.f_26135_.m_20280_(this.targetEntity)) {
            EntityHippogryph hippo = (EntityHippogryph)this.f_26135_;
            this.targetEntity.m_32055_().m_41774_(1);
            this.f_26135_.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
            hippo.setAnimation(EntityHippogryph.ANIMATION_EAT);
            ++hippo.feedings;
            hippo.m_5634_(4.0F);
            if (hippo.feedings > 3 && (hippo.feedings > 7 || hippo.m_217043_().m_188503_(3) == 0) && !hippo.m_21824_() && this.targetEntity.m_19749_() instanceof Player) {
               Player owner = (Player)this.targetEntity.m_19749_();
               if (owner != null) {
                  hippo.m_21828_(owner);
                  hippo.m_6710_((LivingEntity)null);
                  hippo.setCommand(1);
                  hippo.m_21839_(true);
               }
            }

            this.m_8041_();
         } else {
            this.updateList();
         }
      } else {
         this.m_8041_();
      }

   }

   public boolean m_8045_() {
      return !this.f_26135_.m_21573_().m_26571_();
   }

   protected double getAttackReachSqr(Entity attackTarget) {
      return (double)(this.f_26135_.m_20205_() * 2.0F * this.f_26135_.m_20205_() * 2.0F + attackTarget.m_20205_());
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity theEntity;

      public Sorter(Entity theEntityIn) {
         this.theEntity = theEntityIn;
      }

      public int compare(Entity p_compare_1_, Entity p_compare_2_) {
         double d0 = this.theEntity.m_20280_(p_compare_1_);
         double d1 = this.theEntity.m_20280_(p_compare_2_);
         return Double.compare(d0, d1);
      }
   }
}
