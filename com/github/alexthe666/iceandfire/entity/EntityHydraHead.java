package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.neoforge.network.PlayMessages.SpawnEntity;

public class EntityHydraHead extends EntityMutlipartPart {
   public int headIndex;
   public EntityHydra hydra;
   private boolean neck;

   public EntityHydraHead(EntityType<?> t, Level world) {
      super(t, world);
   }

   public EntityHydraHead(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.HYDRA_MULTIPART.get(), worldIn);
   }

   public EntityHydraHead(EntityHydra entity, float radius, float angle, float y, float width, float height, float damageMulti, int headIndex, boolean neck) {
      super((EntityType)IafEntityRegistry.HYDRA_MULTIPART.get(), entity, radius, angle, y, width, height, damageMulti);
      this.headIndex = headIndex;
      this.neck = neck;
      this.hydra = entity;
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.hydra != null && this.hydra.getSeveredHead() != -1 && this.neck && !EntityGorgon.isStoneMob(this.hydra) && this.hydra.getSeveredHead() == this.headIndex && this.m_9236_().f_46443_) {
         for(int k = 0; k < 5; ++k) {
            double d2 = 0.4D;
            double d0 = 0.1D;
            double d1 = 0.1D;
            IceAndFire.PROXY.spawnParticle(EnumParticles.Blood, this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_()) - (double)this.m_20205_() * 0.5D, this.m_20186_() - 0.5D, this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_()) - (double)this.m_20205_() * 0.5D, d2, d0, d1);
         }
      }

   }

   public boolean m_6469_(DamageSource source, float damage) {
      Entity parent = this.getParent();
      if (parent instanceof EntityHydra) {
         ((EntityHydra)parent).onHitHead(damage, this.headIndex);
         return parent.m_6469_(source, damage);
      } else {
         return parent != null && parent.m_6469_(source, damage);
      }
   }
}
