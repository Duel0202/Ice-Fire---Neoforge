package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntitySeaSerpentArrow extends AbstractArrow {
   public EntitySeaSerpentArrow(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
      this.m_36781_(3.0D);
   }

   public EntitySeaSerpentArrow(EntityType<? extends AbstractArrow> t, Level worldIn, double x, double y, double z) {
      this(t, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_(3.0D);
   }

   public EntitySeaSerpentArrow(SpawnEntity spawnEntity, Level world) {
      this((EntityType)IafEntityRegistry.SEA_SERPENT_ARROW.get(), world);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntitySeaSerpentArrow(EntityType t, Level worldIn, LivingEntity shooter) {
      super(t, shooter, worldIn);
      this.m_36781_(3.0D);
   }

   public void m_8119_() {
      super.m_8119_();
      if (this.m_9236_().f_46443_ && !this.f_36703_) {
         double d0 = this.f_19796_.m_188583_() * 0.02D;
         double d1 = this.f_19796_.m_188583_() * 0.02D;
         double d2 = this.f_19796_.m_188583_() * 0.02D;
         double d3 = 10.0D;
         double xRatio = this.m_20184_().f_82479_ * (double)this.m_20206_();
         double zRatio = this.m_20184_().f_82481_ * (double)this.m_20206_();
         this.m_9236_().m_7106_(ParticleTypes.f_123795_, this.m_20185_() + xRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d0 * 10.0D, this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - d1 * 10.0D, this.m_20189_() + zRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d2 * 10.0D, d0, d1, d2);
         this.m_9236_().m_7106_(ParticleTypes.f_123769_, this.m_20185_() + xRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d0 * 10.0D, this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - d1 * 10.0D, this.m_20189_() + zRatio + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 1.0F) - (double)this.m_20205_() - d2 * 10.0D, d0, d1, d2);
      }

   }

   public boolean m_20069_() {
      return false;
   }

   @NotNull
   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)IafItemRegistry.SEA_SERPENT_ARROW.get());
   }
}
