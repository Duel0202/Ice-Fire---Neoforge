package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import org.jetbrains.annotations.NotNull;

public class IAFLookHelper extends LookControl {
   public IAFLookHelper(Mob LivingEntityIn) {
      super(LivingEntityIn);
   }

   public void m_24960_(@NotNull Entity entityIn, float deltaYaw, float deltaPitch) {
      try {
         super.m_24960_(entityIn, deltaYaw, deltaPitch);
      } catch (Exception var5) {
         IceAndFire.LOGGER.warn("Stopped a crash from happening relating to faulty looking AI.");
      }

   }
}
