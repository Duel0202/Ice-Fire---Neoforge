package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityCockatriceEgg extends ThrowableItemProjectile {
   public EntityCockatriceEgg(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityCockatriceEgg(EntityType<? extends ThrowableItemProjectile> type, Level worldIn, LivingEntity throwerIn) {
      super(type, throwerIn, worldIn);
   }

   public EntityCockatriceEgg(EntityType<? extends ThrowableItemProjectile> type, double x, double y, double z, Level worldIn) {
      super(type, x, y, z, worldIn);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_7822_(byte id) {
      if (id == 3) {
         for(int i = 0; i < 8; ++i) {
            this.m_9236_().m_7106_(new ItemParticleOption(ParticleTypes.f_123752_, this.m_7846_()), this.m_20185_(), this.m_20186_(), this.m_20189_(), ((double)this.f_19796_.m_188501_() - 0.5D) * 0.08D, ((double)this.f_19796_.m_188501_() - 0.5D) * 0.08D, ((double)this.f_19796_.m_188501_() - 0.5D) * 0.08D);
         }
      }

   }

   protected void m_6532_(HitResult result) {
      Entity thrower = this.m_19749_();
      if (result.m_6662_() == Type.ENTITY) {
         ((EntityHitResult)result).m_82443_().m_6469_(this.m_9236_().m_269111_().m_269390_(this, thrower), 0.0F);
      }

      if (!this.m_9236_().f_46443_) {
         if (this.f_19796_.m_188503_(4) == 0) {
            int i = 1;
            if (this.f_19796_.m_188503_(32) == 0) {
               i = 4;
            }

            for(int j = 0; j < i; ++j) {
               EntityCockatrice cockatrice = new EntityCockatrice((EntityType)IafEntityRegistry.COCKATRICE.get(), this.m_9236_());
               cockatrice.m_146762_(-24000);
               cockatrice.setHen(this.f_19796_.m_188499_());
               cockatrice.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
               if (thrower instanceof Player) {
                  cockatrice.m_21828_((Player)thrower);
               }

               this.m_9236_().m_7967_(cockatrice);
            }
         }

         this.m_9236_().m_7605_(this, (byte)3);
         this.m_142687_(RemovalReason.DISCARDED);
      }

   }

   @NotNull
   protected Item m_7881_() {
      return (Item)IafItemRegistry.ROTTEN_EGG.get();
   }
}
