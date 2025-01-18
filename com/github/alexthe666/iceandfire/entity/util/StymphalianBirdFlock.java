package com.github.alexthe666.iceandfire.entity.util;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityStymphalianBird;
import com.github.alexthe666.iceandfire.entity.ai.StymphalianBirdAIAirTarget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

public class StymphalianBirdFlock {
   private EntityStymphalianBird leader;
   private ArrayList<EntityStymphalianBird> members = new ArrayList();
   private BlockPos leaderTarget;
   private BlockPos prevLeaderTarget;
   private RandomSource random;
   private final int distance = 15;

   private StymphalianBirdFlock() {
   }

   public static StymphalianBirdFlock createFlock(EntityStymphalianBird bird) {
      StymphalianBirdFlock flock = new StymphalianBirdFlock();
      flock.leader = bird;
      flock.members = new ArrayList();
      flock.members.add(bird);
      flock.leaderTarget = bird.airTarget;
      flock.random = bird.m_217043_();
      return flock;
   }

   @Nullable
   public static StymphalianBirdFlock getNearbyFlock(EntityStymphalianBird bird) {
      float d0 = (float)IafConfig.stymphalianBirdFlockLength;
      List<Entity> list = bird.m_9236_().m_6249_(bird, (new AABB(bird.m_20185_(), bird.m_20186_(), bird.m_20189_(), bird.m_20185_() + 1.0D, bird.m_20186_() + 1.0D, bird.m_20189_() + 1.0D)).m_82377_((double)d0, 10.0D, (double)d0), EntityStymphalianBird.STYMPHALIAN_PREDICATE);
      if (!list.isEmpty()) {
         Iterator itr = list.iterator();

         while(itr.hasNext()) {
            Entity entity = (Entity)itr.next();
            if (entity instanceof EntityStymphalianBird) {
               EntityStymphalianBird other = (EntityStymphalianBird)entity;
               if (other.flock != null) {
                  return other.flock;
               }
            }
         }
      }

      return null;
   }

   public boolean isLeader(EntityStymphalianBird bird) {
      return this.leader != null && this.leader == bird;
   }

   public void addToFlock(EntityStymphalianBird bird) {
      this.members.add(bird);
   }

   public void update() {
      if (!this.members.isEmpty() && (this.leader == null || !this.leader.m_6084_())) {
         this.leader = (EntityStymphalianBird)this.members.get(this.random.m_188503_(this.members.size()));
      }

      if (this.leader != null && this.leader.m_6084_()) {
         this.prevLeaderTarget = this.leaderTarget;
         this.leaderTarget = this.leader.airTarget;
      }

   }

   public void onLeaderAttack(LivingEntity attackTarget) {
      Iterator var2 = this.members.iterator();

      while(var2.hasNext()) {
         EntityStymphalianBird bird = (EntityStymphalianBird)var2.next();
         if (bird.m_5448_() == null && !this.isLeader(bird)) {
            bird.m_6710_(attackTarget);
         }
      }

   }

   public EntityStymphalianBird getLeader() {
      return this.leader;
   }

   public void setTarget(BlockPos target) {
      this.leaderTarget = target;
      Iterator var2 = this.members.iterator();

      while(var2.hasNext()) {
         EntityStymphalianBird bird = (EntityStymphalianBird)var2.next();
         if (!this.isLeader(bird)) {
            bird.airTarget = StymphalianBirdAIAirTarget.getNearbyAirTarget(bird);
         }
      }

   }

   public void setFlying(boolean flying) {
      Iterator var2 = this.members.iterator();

      while(var2.hasNext()) {
         EntityStymphalianBird bird = (EntityStymphalianBird)var2.next();
         if (!this.isLeader(bird)) {
            bird.setFlying(flying);
         }
      }

   }

   public void setFearTarget(LivingEntity living) {
      Iterator var2 = this.members.iterator();

      while(var2.hasNext()) {
         EntityStymphalianBird bird = (EntityStymphalianBird)var2.next();
         bird.setVictor(living);
      }

   }
}
