package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexSentinel;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MyrmexAIFindHidingSpot extends Goal {
   private static final int RADIUS = 32;
   protected final DragonAITargetItems.Sorter theNearestAttackableTargetSorter;
   protected final Predicate<? super Entity> targetEntitySelector;
   private final EntityMyrmexSentinel myrmex;
   private BlockPos targetBlock = null;
   private int wanderRadius = 32;

   public MyrmexAIFindHidingSpot(EntityMyrmexSentinel myrmex) {
      this.theNearestAttackableTargetSorter = new DragonAITargetItems.Sorter(myrmex);
      this.targetEntitySelector = new Predicate<Entity>() {
         public boolean test(Entity myrmex) {
            return myrmex instanceof EntityMyrmexSentinel;
         }
      };
      this.myrmex = myrmex;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      this.targetBlock = this.getTargetPosition(this.wanderRadius);
      return this.myrmex.canMove() && this.myrmex.m_5448_() == null && this.myrmex.canSeeSky() && !this.myrmex.isHiding() && this.myrmex.visibleTicks <= 0;
   }

   public boolean m_8045_() {
      return !this.myrmex.shouldEnterHive() && this.myrmex.m_5448_() == null && !this.myrmex.isHiding() && this.myrmex.visibleTicks <= 0;
   }

   public void m_8037_() {
      if (this.targetBlock != null) {
         this.myrmex.m_21573_().m_26519_((double)this.targetBlock.m_123341_() + 0.5D, (double)this.targetBlock.m_123342_(), (double)this.targetBlock.m_123343_() + 0.5D, 1.0D);
         if (!this.areMyrmexNear(5.0D) && !this.myrmex.isOnResin()) {
            if (this.myrmex.m_5448_() == null && this.myrmex.m_7962_() == null && this.myrmex.visibleTicks == 0 && this.myrmex.m_20238_(Vec3.m_82512_(this.targetBlock)) < 9.0D) {
               this.myrmex.setHiding(true);
               this.myrmex.m_21573_().m_26573_();
            }
         } else if (this.myrmex.m_20238_(Vec3.m_82512_(this.targetBlock)) < 9.0D) {
            this.wanderRadius += 32;
            this.targetBlock = this.getTargetPosition(this.wanderRadius);
         }
      }

   }

   public void m_8041_() {
      this.targetBlock = null;
      this.wanderRadius = 32;
   }

   protected AABB getTargetableArea(double targetDistance) {
      return this.myrmex.m_20191_().m_82377_(targetDistance, 14.0D, targetDistance);
   }

   public BlockPos getTargetPosition(int radius) {
      int x = (int)this.myrmex.m_20185_() + this.myrmex.m_217043_().m_188503_(radius * 2) - radius;
      int z = (int)this.myrmex.m_20189_() + this.myrmex.m_217043_().m_188503_(radius * 2) - radius;
      return this.myrmex.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, new BlockPos(x, 0, z));
   }

   private boolean areMyrmexNear(double distance) {
      List<Entity> sentinels = this.myrmex.m_9236_().m_6249_(this.myrmex, this.getTargetableArea(distance), this.targetEntitySelector);
      List<Entity> hiddenSentinels = new ArrayList();
      Iterator var5 = sentinels.iterator();

      while(var5.hasNext()) {
         Entity sentinel = (Entity)var5.next();
         if (sentinel instanceof EntityMyrmexSentinel && ((EntityMyrmexSentinel)sentinel).isHiding()) {
            hiddenSentinels.add(sentinel);
         }
      }

      return !hiddenSentinels.isEmpty();
   }
}
