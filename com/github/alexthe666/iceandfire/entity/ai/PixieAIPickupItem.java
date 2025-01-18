package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.util.IAFMath;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;

public class PixieAIPickupItem<T extends ItemEntity> extends TargetGoal {
   protected final DragonAITargetItems.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super ItemEntity> targetEntitySelector;
   protected ItemEntity targetEntity;
   @Nonnull
   private List<ItemEntity> list;

   public PixieAIPickupItem(EntityPixie creature, boolean checkSight) {
      this(creature, checkSight, false);
   }

   public PixieAIPickupItem(EntityPixie creature, boolean checkSight, boolean onlyNearby) {
      this(creature, 20, checkSight, onlyNearby, (Predicate)null);
   }

   public PixieAIPickupItem(final EntityPixie creature, int chance, boolean checkSight, boolean onlyNearby, @Nullable Predicate<? super T> targetSelector) {
      super(creature, checkSight, onlyNearby);
      this.list = IAFMath.emptyItemEntityList;
      this.theNearestAttackableTargetSorter = new DragonAITargetItems.Sorter(creature);
      this.targetEntitySelector = new Predicate<ItemEntity>() {
         public boolean test(ItemEntity item) {
            return item != null && !item.m_32055_().m_41619_() && (item.m_32055_().m_41720_() == Items.f_42502_ && !creature.m_21824_() || item.m_32055_().m_41720_() == Items.f_42501_ && creature.m_21824_() && creature.m_21223_() < creature.m_21233_());
         }
      };
      this.m_7021_(EnumSet.of(Flag.TARGET));
   }

   public boolean m_8036_() {
      EntityPixie pixie = (EntityPixie)this.f_26135_;
      if (pixie.isPixieSitting()) {
         return false;
      } else {
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
      }
   }

   protected AABB getTargetableArea(double targetDistance) {
      return this.f_26135_.m_20191_().m_82377_(targetDistance, 4.0D, targetDistance);
   }

   public void m_8056_() {
      this.f_26135_.m_21566_().m_6849_(this.targetEntity.m_20185_(), this.targetEntity.m_20186_(), this.targetEntity.m_20189_(), 0.25D);
      LivingEntity attackTarget = this.f_26135_.m_5448_();
      if (attackTarget == null) {
         this.f_26135_.m_21563_().m_24950_(this.targetEntity.m_20185_(), this.targetEntity.m_20186_(), this.targetEntity.m_20189_(), 180.0F, 20.0F);
      }

      super.m_8056_();
   }

   public void m_8037_() {
      super.m_8037_();
      if (this.targetEntity != null && this.targetEntity.m_6084_()) {
         if (this.f_26135_.m_20280_(this.targetEntity) < 1.0D) {
            EntityPixie pixie = (EntityPixie)this.f_26135_;
            if (this.targetEntity.m_32055_() != null && this.targetEntity.m_32055_().m_41720_() != null) {
               if (this.targetEntity.m_32055_().m_204117_(IafItemTags.HEAL_PIXIE)) {
                  pixie.m_5634_(5.0F);
               } else if (this.targetEntity.m_32055_().m_204117_(IafItemTags.TAME_PIXIE) && !pixie.m_21824_()) {
                  Entity var3 = this.targetEntity.m_19749_();
                  if (var3 instanceof Player) {
                     Player player = (Player)var3;
                     pixie.m_21828_(player);
                     pixie.setPixieSitting(true);
                     pixie.m_6853_(true);
                  }
               }
            }

            pixie.m_21008_(InteractionHand.MAIN_HAND, this.targetEntity.m_32055_());
            this.targetEntity.m_32055_().m_41774_(1);
            pixie.m_5496_(IafSoundRegistry.PIXIE_TAUNT, 1.0F, 1.0F);
            this.m_8041_();
         }
      } else {
         this.m_8041_();
      }

   }

   public boolean m_8045_() {
      return true;
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
