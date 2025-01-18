package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
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
import net.neoforge.entity.IEntityAdditionalSpawnData;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityDeathWormEgg extends ThrowableItemProjectile implements IEntityAdditionalSpawnData {
   private boolean giant;

   public EntityDeathWormEgg(EntityType<? extends ThrowableItemProjectile> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityDeathWormEgg(EntityType<? extends ThrowableItemProjectile> type, LivingEntity throwerIn, Level worldIn, boolean giant) {
      super(type, throwerIn, worldIn);
      this.giant = giant;
   }

   public EntityDeathWormEgg(EntityType<? extends ThrowableItemProjectile> type, double x, double y, double z, Level worldIn, boolean giant) {
      super(type, x, y, z, worldIn);
      this.giant = giant;
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void writeSpawnData(FriendlyByteBuf buffer) {
      buffer.writeBoolean(this.giant);
   }

   public void readSpawnData(FriendlyByteBuf additionalData) {
      this.giant = additionalData.readBoolean();
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
         float wormSize = 0.25F + (float)(Math.random() * 0.3499999940395355D);
         EntityDeathWorm deathworm = new EntityDeathWorm((EntityType)IafEntityRegistry.DEATH_WORM.get(), this.m_9236_());
         deathworm.setVariant(this.f_19796_.m_188503_(3));
         deathworm.m_7105_(true);
         deathworm.setWormHome(this.m_20183_());
         deathworm.setWormAge(1);
         deathworm.setDeathWormScale(this.giant ? wormSize * 4.0F : wormSize);
         deathworm.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
         if (thrower instanceof Player) {
            deathworm.m_21816_(thrower.m_20148_());
         }

         this.m_9236_().m_7967_(deathworm);
         this.m_9236_().m_7605_(this, (byte)3);
         this.m_142687_(RemovalReason.DISCARDED);
      }

   }

   @NotNull
   protected Item m_7881_() {
      return this.giant ? (Item)IafItemRegistry.DEATHWORM_EGG_GIGANTIC.get() : (Item)IafItemRegistry.DEATHWORM_EGG.get();
   }
}
