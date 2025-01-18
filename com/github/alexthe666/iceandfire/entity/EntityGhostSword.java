package com.github.alexthe666.iceandfire.entity;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityGhostSword extends AbstractArrow {
   private IntOpenHashSet piercedEntities;
   private List<Entity> hitEntities;
   private int knockbackStrength;

   public EntityGhostSword(EntityType<? extends AbstractArrow> type, Level worldIn) {
      super(type, worldIn);
      this.m_36781_(9.0D);
   }

   public EntityGhostSword(EntityType<? extends AbstractArrow> type, Level worldIn, double x, double y, double z, float r, float g, float b) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_(9.0D);
   }

   public EntityGhostSword(EntityType<? extends AbstractArrow> type, Level worldIn, LivingEntity shooter, double dmg) {
      super(type, shooter, worldIn);
      this.m_36781_(dmg);
   }

   public EntityGhostSword(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.GHOST_SWORD.get(), worldIn);
   }

   public boolean m_20069_() {
      return false;
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_8119_() {
      super.m_8119_();
      this.f_19794_ = true;
      float sqrt = Mth.m_14116_((float)(this.m_20184_().f_82479_ * this.m_20184_().f_82479_ + this.m_20184_().f_82481_ * this.m_20184_().f_82481_));
      if (sqrt < 0.1F && this.f_19797_ > 200) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      double d0 = 0.0D;
      double d1 = 0.0D;
      double d2 = 0.01D;
      double x = this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_();
      double y = this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - (double)this.m_20206_();
      double z = this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_();
      float f = (this.m_20205_() + this.m_20206_() + this.m_20205_()) * 0.333F + 0.5F;
      if (this.particleDistSq(x, y, z) < (double)(f * f)) {
         this.m_9236_().m_7106_(ParticleTypes.f_123763_, x, y + 0.5D, z, d0, d1, d2);
      }

      Vec3 vector3d = this.m_20184_();
      double f3 = vector3d.m_165924_();
      this.m_146922_((float)(Mth.m_14136_(vector3d.f_82479_, vector3d.f_82481_) * 57.2957763671875D));
      this.m_146926_((float)(Mth.m_14136_(vector3d.f_82480_, f3) * 57.2957763671875D));
      this.f_19859_ = this.m_146908_();
      this.f_19860_ = this.m_146909_();
      Vec3 vector3d2 = this.m_20182_();
      Vec3 vector3d3 = vector3d2.m_82549_(vector3d);
      HitResult raytraceresult = this.m_9236_().m_45547_(new ClipContext(vector3d2, vector3d3, Block.COLLIDER, Fluid.NONE, this));
      if (((HitResult)raytraceresult).m_6662_() != Type.MISS) {
         vector3d3 = ((HitResult)raytraceresult).m_82450_();
      }

      while(!this.m_213877_()) {
         EntityHitResult entityraytraceresult = this.m_6351_(vector3d2, vector3d3);
         if (entityraytraceresult != null) {
            raytraceresult = entityraytraceresult;
         }

         if (raytraceresult != null && ((HitResult)raytraceresult).m_6662_() == Type.ENTITY) {
            Entity entity = ((EntityHitResult)raytraceresult).m_82443_();
            Entity entity1 = this.m_19749_();
            if (entity instanceof Player && entity1 instanceof Player && !((Player)entity1).m_7099_((Player)entity)) {
               raytraceresult = null;
               entityraytraceresult = null;
            }
         }

         if (raytraceresult != null && ((HitResult)raytraceresult).m_6662_() != Type.MISS && !ForgeEventFactory.onProjectileImpact(this, (HitResult)raytraceresult)) {
            if (((HitResult)raytraceresult).m_6662_() != Type.BLOCK) {
               this.m_6532_((HitResult)raytraceresult);
            }

            this.f_19812_ = true;
         }

         if (entityraytraceresult == null || this.m_36796_() <= 0) {
            break;
         }

         raytraceresult = null;
      }

   }

   public double particleDistSq(double toX, double toY, double toZ) {
      double d0 = this.m_20185_() - toX;
      double d1 = this.m_20186_() - toY;
      double d2 = this.m_20189_() - toZ;
      return d0 * d0 + d1 * d1 + d2 * d2;
   }

   public void m_5496_(@NotNull SoundEvent soundIn, float volume, float pitch) {
      if (!this.m_20067_() && soundIn != SoundEvents.f_11685_ && soundIn != SoundEvents.f_11686_) {
         this.m_9236_().m_6263_((Player)null, this.m_20185_(), this.m_20186_(), this.m_20189_(), soundIn, this.m_5720_(), volume, pitch);
      }

   }

   public boolean m_20068_() {
      return true;
   }

   @NotNull
   protected ItemStack m_7941_() {
      return ItemStack.f_41583_;
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public void m_36735_(int knockbackStrengthIn) {
      this.knockbackStrength = knockbackStrengthIn;
   }

   protected void m_5790_(EntityHitResult result) {
      Entity entity = result.m_82443_();
      float f = (float)this.m_20184_().m_82553_();
      int i = Mth.m_14165_(Math.max((double)f * this.m_36789_(), 0.0D));
      if (this.m_36796_() > 0) {
         if (this.piercedEntities == null) {
            this.piercedEntities = new IntOpenHashSet(5);
         }

         if (this.hitEntities == null) {
            this.hitEntities = Lists.newArrayListWithCapacity(5);
         }

         if (this.piercedEntities.size() >= this.m_36796_() + 1) {
            this.m_142687_(RemovalReason.DISCARDED);
            return;
         }

         this.piercedEntities.add(entity.m_19879_());
      }

      if (this.m_36792_()) {
         i += this.f_19796_.m_188503_(i / 2 + 2);
      }

      Entity entity1 = this.m_19749_();
      DamageSource damagesource = this.m_9236_().m_269111_().m_269425_();
      if (entity1 != null && entity1 instanceof LivingEntity) {
         damagesource = this.m_9236_().m_269111_().m_269104_(this, entity1);
         ((LivingEntity)entity1).m_21335_(entity);
      }

      boolean flag = entity.m_6095_() == EntityType.f_20566_;
      int j = entity.m_20094_();
      if (this.m_6060_() && !flag) {
         entity.m_20254_(5);
      }

      if (entity.m_6469_(damagesource, (float)i)) {
         if (flag) {
            return;
         }

         if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;
            if (this.knockbackStrength > 0) {
               Vec3 vec3d = this.m_20184_().m_82542_(1.0D, 0.0D, 1.0D).m_82541_().m_82490_((double)this.knockbackStrength * 0.6D);
               if (vec3d.m_82556_() > 0.0D) {
                  livingentity.m_5997_(vec3d.f_82479_, 0.1D, vec3d.f_82481_);
               }
            }

            this.m_7761_(livingentity);
            if (entity1 != null && livingentity != entity1 && livingentity instanceof Player && entity1 instanceof ServerPlayer) {
               ((ServerPlayer)entity1).f_8906_.m_9829_(new ClientboundGameEventPacket(ClientboundGameEventPacket.f_132159_, 0.0F));
            }

            if (!entity.m_6084_() && this.hitEntities != null) {
               this.hitEntities.add(livingentity);
            }
         }

         this.m_5496_(this.m_36784_(), 1.0F, 1.2F / (this.f_19796_.m_188501_() * 0.2F + 0.9F));
         if (this.m_36796_() <= 0) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      } else {
         this.m_20256_(this.m_20184_().m_82490_(-0.1D));
         if (!this.m_9236_().f_46443_ && this.m_20184_().m_82556_() < 1.0E-7D) {
            this.m_142687_(RemovalReason.DISCARDED);
         }
      }

   }
}
