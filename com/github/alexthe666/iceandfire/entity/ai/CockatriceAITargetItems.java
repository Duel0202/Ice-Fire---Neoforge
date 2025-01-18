package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.EntityCockatrice;
import com.github.alexthe666.iceandfire.util.IAFMath;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.AABB;

public class CockatriceAITargetItems<T extends ItemEntity> extends TargetGoal {
   protected final DragonAITargetItems.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super ItemEntity> targetEntitySelector;
   protected ItemEntity targetEntity;
   protected final int targetChance;
   @Nonnull
   private List<ItemEntity> list;

   public CockatriceAITargetItems(EntityCockatrice creature, boolean checkSight) {
      this(creature, checkSight, false);
   }

   public CockatriceAITargetItems(EntityCockatrice creature, boolean checkSight, boolean onlyNearby) {
      this(creature, 10, checkSight, onlyNearby, (Predicate)null);
   }

   public CockatriceAITargetItems(EntityCockatrice creature, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate<? super T> targetSelector) {
      super(creature, checkSight, onlyNearby);
      this.list = IAFMath.emptyItemEntityList;
      this.theNearestAttackableTargetSorter = new DragonAITargetItems.Sorter(creature);
      this.targetChance = chance;
      this.targetEntitySelector = (item) -> {
         return item != null && !item.m_32055_().m_41619_() && item.m_32055_().m_204117_(IafItemTags.HEAL_COCKATRICE);
      };
   }

   public boolean m_8036_() {
      if (this.targetChance > 0 && this.f_26135_.m_217043_().m_188503_(this.targetChance) != 0) {
         return false;
      } else if (((EntityCockatrice)this.f_26135_).canMove() && !(this.f_26135_.m_21223_() >= this.f_26135_.m_21233_())) {
         if (this.f_26135_.m_9236_().m_46467_() % 4L == 0L) {
            this.list = this.f_26135_.m_9236_().m_6443_(ItemEntity.class, this.getTargetableArea(this.m_7623_()), this.targetEntitySelector);
         }

         if (this.list.isEmpty()) {
            return false;
         } else {
            this.list.sort(this.theNearestAttackableTargetSorter);
            this.targetEntity = (ItemEntity)this.list.get(0);
            return true;
         }
      } else {
         this.list = IAFMath.emptyItemEntityList;
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
      if (this.targetEntity != null && this.targetEntity.m_6084_()) {
         if (this.f_26135_.m_20280_(this.targetEntity) < 1.0D) {
            EntityCockatrice cockatrice = (EntityCockatrice)this.f_26135_;
            this.targetEntity.m_32055_().m_41774_(1);
            this.f_26135_.m_5496_(SoundEvents.f_11912_, 1.0F, 1.0F);
            cockatrice.m_5634_(8.0F);
            cockatrice.setAnimation(EntityCockatrice.ANIMATION_EAT);
            this.m_8041_();
         }
      } else {
         this.m_8041_();
      }

   }

   public boolean m_8045_() {
      return !this.f_26135_.m_21573_().m_26571_();
   }
}
