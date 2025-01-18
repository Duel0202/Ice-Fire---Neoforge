package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityGhost;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.level.Level;

public class GhostPathNavigator extends FlyingPathNavigation {
   public EntityGhost ghost;

   public GhostPathNavigator(EntityGhost entityIn, Level worldIn) {
      super(entityIn, worldIn);
      this.ghost = entityIn;
   }

   public boolean m_5624_(Entity entityIn, double speedIn) {
      this.ghost.m_21566_().m_6849_(entityIn.m_20185_(), entityIn.m_20186_(), entityIn.m_20189_(), speedIn);
      return true;
   }

   public boolean m_26519_(double x, double y, double z, double speedIn) {
      this.ghost.m_21566_().m_6849_(x, y, z, speedIn);
      return true;
   }
}
