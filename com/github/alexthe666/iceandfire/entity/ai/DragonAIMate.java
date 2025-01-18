package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityDragonEgg;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DragonAIMate extends Goal {
   private static final BlockState NEST;
   private final EntityDragonBase dragon;
   Level theWorld;
   int spawnBabyDelay;
   double moveSpeed;
   private EntityDragonBase targetMate;

   public DragonAIMate(EntityDragonBase dragon, double speedIn) {
      this.dragon = dragon;
      this.theWorld = dragon.m_9236_();
      this.moveSpeed = speedIn;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (this.dragon.m_27593_() && this.dragon.canMove()) {
         this.targetMate = this.getNearbyMate();
         return this.targetMate != null;
      } else {
         return false;
      }
   }

   public boolean continueExecuting() {
      return this.targetMate.m_6084_() && this.targetMate.m_27593_() && this.spawnBabyDelay < 60;
   }

   public void m_8041_() {
      this.targetMate = null;
      this.spawnBabyDelay = 0;
   }

   public void m_8037_() {
      this.dragon.m_21563_().m_24960_(this.targetMate, 10.0F, (float)this.dragon.m_8132_());
      this.dragon.m_21573_().m_26519_(this.targetMate.m_20185_(), this.targetMate.m_20186_(), this.targetMate.m_20189_(), this.moveSpeed);
      this.dragon.setFlying(false);
      this.dragon.setHovering(false);
      ++this.spawnBabyDelay;
      if (this.spawnBabyDelay >= 60 && this.dragon.m_20270_(this.targetMate) < 35.0F) {
         this.spawnBaby();
      }

   }

   private EntityDragonBase getNearbyMate() {
      List<? extends EntityDragonBase> list = this.theWorld.m_45976_(this.dragon.getClass(), this.dragon.m_20191_().m_82377_(180.0D, 180.0D, 180.0D));
      double d0 = Double.MAX_VALUE;
      EntityDragonBase mate = null;
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         EntityDragonBase partner = (EntityDragonBase)var5.next();
         if (this.dragon.m_7848_(partner)) {
            double d1 = this.dragon.m_20280_(partner);
            if (d1 < d0) {
               mate = partner;
               d0 = d1;
            }
         }
      }

      return mate;
   }

   private void spawnBaby() {
      EntityDragonEgg egg = this.dragon.createEgg(this.targetMate);
      if (egg != null) {
         this.dragon.m_146762_(6000);
         this.targetMate.m_146762_(6000);
         this.dragon.m_27594_();
         this.targetMate.m_27594_();
         int nestX = (int)(this.dragon.isMale() ? this.targetMate.m_20185_() : this.dragon.m_20185_());
         int nestY = (int)(this.dragon.isMale() ? this.targetMate.m_20186_() : this.dragon.m_20186_()) - 1;
         int nestZ = (int)(this.dragon.isMale() ? this.targetMate.m_20189_() : this.dragon.m_20189_());
         egg.m_7678_((double)((float)nestX - 0.5F), (double)((float)nestY + 1.0F), (double)((float)nestZ - 0.5F), 0.0F, 0.0F);
         this.theWorld.m_7967_(egg);
         RandomSource random = this.dragon.m_217043_();

         for(int i = 0; i < 17; ++i) {
            double d0 = random.m_188583_() * 0.02D;
            double d1 = random.m_188583_() * 0.02D;
            double d2 = random.m_188583_() * 0.02D;
            double d3 = random.m_188500_() * (double)this.dragon.m_20205_() * 2.0D - (double)this.dragon.m_20205_();
            double d4 = 0.5D + random.m_188500_() * (double)this.dragon.m_20206_();
            double d5 = random.m_188500_() * (double)this.dragon.m_20205_() * 2.0D - (double)this.dragon.m_20205_();
            this.theWorld.m_7106_(ParticleTypes.f_123750_, this.dragon.m_20185_() + d3, this.dragon.m_20186_() + d4, this.dragon.m_20189_() + d5, d0, d1, d2);
         }

         BlockPos eggPos = new BlockPos(nestX - 2, nestY, nestZ - 2);
         BlockPos dirtPos = eggPos.m_7918_(1, 0, 1);

         for(int x = 0; x < 3; ++x) {
            for(int z = 0; z < 3; ++z) {
               BlockPos add = eggPos.m_7918_(x, 0, z);
               BlockState prevState = this.theWorld.m_8055_(add);
               if (prevState.m_247087_() || this.theWorld.m_8055_(add).m_204336_(BlockTags.f_144274_) || this.theWorld.m_8055_(add).m_60800_(this.theWorld, add) < 5.0F || this.theWorld.m_8055_(add).m_60800_(this.theWorld, add) >= 0.0F) {
                  this.theWorld.m_46597_(add, NEST);
               }
            }
         }

         if (this.theWorld.m_8055_(dirtPos).m_247087_() || this.theWorld.m_8055_(dirtPos) == NEST) {
            this.theWorld.m_46597_(dirtPos, Blocks.f_152481_.m_49966_());
         }

         if (this.theWorld.m_46469_().m_46207_(GameRules.f_46135_)) {
            this.theWorld.m_7967_(new ExperienceOrb(this.theWorld, this.dragon.m_20185_(), this.dragon.m_20186_(), this.dragon.m_20189_(), random.m_188503_(15) + 10));
         }
      }

   }

   static {
      NEST = ((Block)IafBlockRegistry.NEST.get()).m_49966_();
   }
}
