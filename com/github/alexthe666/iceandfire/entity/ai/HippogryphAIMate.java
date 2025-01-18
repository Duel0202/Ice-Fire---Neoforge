package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.item.ItemHippogryphEgg;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;

public class HippogryphAIMate extends Goal {
   private final EntityHippogryph hippo;
   Level world;
   int spawnBabyDelay;
   double moveSpeed;
   private EntityHippogryph targetMate;

   public HippogryphAIMate(EntityHippogryph animal, double speedIn) {
      this(animal, speedIn, animal.getClass());
   }

   public HippogryphAIMate(EntityHippogryph hippogryph, double speed, Class<? extends Animal> mate) {
      this.hippo = hippogryph;
      this.world = hippogryph.m_9236_();
      this.moveSpeed = speed;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.hippo.m_27593_() && !this.hippo.m_21827_()) {
         this.targetMate = this.getNearbyMate();
         return this.targetMate != null;
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return this.targetMate.m_6084_() && this.targetMate.m_27593_() && this.spawnBabyDelay < 60;
   }

   public void m_8041_() {
      this.targetMate = null;
      this.spawnBabyDelay = 0;
   }

   public void m_8037_() {
      this.hippo.m_21563_().m_24960_(this.targetMate, 10.0F, (float)this.hippo.m_8132_());
      this.hippo.m_21573_().m_5624_(this.targetMate, this.moveSpeed);
      ++this.spawnBabyDelay;
      if (this.spawnBabyDelay >= 60 && this.hippo.m_20280_(this.targetMate) < 9.0D) {
         this.spawnBaby();
      }

   }

   private EntityHippogryph getNearbyMate() {
      List<EntityHippogryph> list = this.world.m_45976_(EntityHippogryph.class, this.hippo.m_20191_().m_82400_(8.0D));
      double d0 = Double.MAX_VALUE;
      EntityHippogryph entityanimal = null;
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         EntityHippogryph entityanimal1 = (EntityHippogryph)var5.next();
         if (this.hippo.m_7848_(entityanimal1) && this.hippo.m_20280_(entityanimal1) < d0) {
            entityanimal = entityanimal1;
            d0 = this.hippo.m_20280_(entityanimal1);
         }
      }

      return entityanimal;
   }

   private void spawnBaby() {
      ItemEntity egg = new ItemEntity(this.world, this.hippo.m_20185_(), this.hippo.m_20186_(), this.hippo.m_20189_(), ItemHippogryphEgg.createEggStack(this.hippo.getEnumVariant(), this.targetMate.getEnumVariant()));
      this.hippo.m_146762_(6000);
      this.targetMate.m_146762_(6000);
      this.hippo.m_27594_();
      this.targetMate.m_27594_();
      egg.m_7678_(this.hippo.m_20185_(), this.hippo.m_20186_(), this.hippo.m_20189_(), 0.0F, 0.0F);
      if (!this.world.f_46443_) {
         this.world.m_7967_(egg);
      }

      RandomSource random = this.hippo.m_217043_();

      for(int i = 0; i < 7; ++i) {
         double d0 = random.m_188583_() * 0.02D;
         double d1 = random.m_188583_() * 0.02D;
         double d2 = random.m_188583_() * 0.02D;
         double d3 = random.m_188500_() * (double)this.hippo.m_20205_() * 2.0D - (double)this.hippo.m_20205_();
         double d4 = 0.5D + random.m_188500_() * (double)this.hippo.m_20206_();
         double d5 = random.m_188500_() * (double)this.hippo.m_20205_() * 2.0D - (double)this.hippo.m_20205_();
         this.world.m_7106_(ParticleTypes.f_123750_, this.hippo.m_20185_() + d3, this.hippo.m_20186_() + d4, this.hippo.m_20189_() + d5, d0, d1, d2);
      }

      if (this.world.m_46469_().m_46207_(GameRules.f_46135_)) {
         this.world.m_7967_(new ExperienceOrb(this.world, this.hippo.m_20185_(), this.hippo.m_20186_(), this.hippo.m_20189_(), random.m_188503_(7) + 1));
      }

   }
}
