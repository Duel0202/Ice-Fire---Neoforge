package com.github.alexthe666.iceandfire.entity.props;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;

public class FrozenData {
   public int frozenTicks;
   public boolean isFrozen;
   private boolean triggerClientUpdate;

   public void tickFrozen(LivingEntity entity) {
      if (this.isFrozen) {
         if (entity instanceof EntityIceDragon) {
            this.clearFrozen(entity);
         } else if (entity.m_6060_()) {
            this.clearFrozen(entity);
            entity.m_20095_();
         } else if (entity.m_21224_()) {
            this.clearFrozen(entity);
         } else {
            if (this.frozenTicks > 0) {
               --this.frozenTicks;
            } else {
               this.clearFrozen(entity);
            }

            if (this.isFrozen) {
               if (entity instanceof Player) {
                  Player player = (Player)entity;
                  if (player.m_7500_()) {
                     return;
                  }
               }

               entity.m_20256_(entity.m_20184_().m_82542_(0.25D, 1.0D, 0.25D));
               if (!(entity instanceof EnderDragon) && !entity.m_20096_()) {
                  entity.m_20256_(entity.m_20184_().m_82520_(0.0D, -0.2D, 0.0D));
               }
            }

         }
      }
   }

   public void setFrozen(LivingEntity target, int duration) {
      if (!this.isFrozen) {
         target.m_5496_(SoundEvents.f_11986_, 1.0F, 1.0F);
      }

      this.frozenTicks = duration;
      this.isFrozen = true;
      this.triggerClientUpdate = true;
   }

   private void clearFrozen(LivingEntity entity) {
      for(int i = 0; i < 15; ++i) {
         entity.m_9236_().m_7106_(new BlockParticleOption(ParticleTypes.f_123794_, ((Block)IafBlockRegistry.DRAGON_ICE.get()).m_49966_()), entity.m_20185_() + (entity.m_217043_().m_188500_() - 0.5D) * (double)entity.m_20205_(), entity.m_20186_() + entity.m_217043_().m_188500_() * (double)entity.m_20206_(), entity.m_20189_() + (entity.m_217043_().m_188500_() - 0.5D) * (double)entity.m_20205_(), 0.0D, 0.0D, 0.0D);
      }

      entity.m_5496_(SoundEvents.f_11983_, 3.0F, 1.0F);
      this.isFrozen = false;
      this.frozenTicks = 0;
      this.triggerClientUpdate = true;
   }

   public void serialize(CompoundTag tag) {
      CompoundTag frozenData = new CompoundTag();
      frozenData.m_128405_("frozenTicks", this.frozenTicks);
      frozenData.m_128379_("isFrozen", this.isFrozen);
      tag.m_128365_("frozenData", frozenData);
   }

   public void deserialize(CompoundTag tag) {
      CompoundTag frozenData = tag.m_128469_("frozenData");
      this.frozenTicks = frozenData.m_128451_("frozenTicks");
      this.isFrozen = frozenData.m_128471_("isFrozen");
   }

   public boolean doesClientNeedUpdate() {
      if (this.triggerClientUpdate) {
         this.triggerClientUpdate = false;
         return true;
      } else {
         return false;
      }
   }
}
