package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class DreadSpawnerBaseLogic extends BaseSpawner {
   private short spawnDelay = 20;
   private double spin;
   private double oSpin;

   public void m_151319_(@NotNull Level p_151320_, @NotNull BlockPos p_151321_) {
      if (!this.m_151343_(p_151320_, p_151321_)) {
         this.oSpin = this.spin;
      } else {
         double d0 = (double)p_151321_.m_123341_() + p_151320_.f_46441_.m_188500_();
         double d1 = (double)p_151321_.m_123342_() + p_151320_.f_46441_.m_188500_();
         double d2 = (double)p_151321_.m_123343_() + p_151320_.f_46441_.m_188500_();
         p_151320_.m_7106_(ParticleTypes.f_123762_, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, d0, d1, d2, 0.0D, 0.0D, 0.0D);
         if (this.spawnDelay > 0) {
            --this.spawnDelay;
         }

         this.oSpin = this.spin;
         this.spin = (this.spin + (double)(1000.0F / ((float)this.spawnDelay + 200.0F))) % 360.0D;
      }

   }

   private boolean m_151343_(Level p_151344_, BlockPos p_151345_) {
      return p_151344_.m_45914_((double)p_151345_.m_123341_() + 0.5D, (double)p_151345_.m_123342_() + 0.5D, (double)p_151345_.m_123343_() + 0.5D, 20.0D);
   }

   public double m_45473_() {
      return this.spin;
   }

   public double m_45474_() {
      return this.oSpin;
   }
}
