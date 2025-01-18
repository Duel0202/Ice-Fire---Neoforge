package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import java.util.Iterator;
import java.util.List;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.common.ToolActions;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityDreadLichSkull extends AbstractArrow {
   public EntityDreadLichSkull(EntityType<? extends AbstractArrow> type, Level worldIn) {
      super(type, worldIn);
      this.m_36781_(6.0D);
   }

   public EntityDreadLichSkull(EntityType<? extends AbstractArrow> type, Level worldIn, double x, double y, double z) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_(6.0D);
   }

   public EntityDreadLichSkull(EntityType<? extends AbstractArrow> type, Level worldIn, LivingEntity shooter, double x, double y, double z) {
      super(type, shooter, worldIn);
      this.m_36781_(6.0D);
   }

   public EntityDreadLichSkull(EntityType<? extends AbstractArrow> type, Level worldIn, LivingEntity shooter, double dmg) {
      super(type, shooter, worldIn);
      this.m_36781_(dmg);
   }

   public EntityDreadLichSkull(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.DREAD_LICH_SKULL.get(), worldIn);
   }

   public boolean m_20069_() {
      return false;
   }

   protected void m_8097_() {
      super.m_8097_();
   }

   public void m_8119_() {
      float sqrt = Mth.m_14116_((float)(this.m_20184_().f_82479_ * this.m_20184_().f_82479_ + this.m_20184_().f_82481_ * this.m_20184_().f_82481_));
      boolean flag = true;
      Entity shootingEntity = this.m_19749_();
      LivingEntity target;
      double minusX;
      double minusY;
      double minusZ;
      double speed;
      if (shootingEntity != null && shootingEntity instanceof Mob && ((Mob)shootingEntity).m_5448_() != null) {
         target = ((Mob)shootingEntity).m_5448_();
         minusX = target.m_20185_() - this.m_20185_();
         minusY = target.m_20186_() - this.m_20186_();
         minusZ = target.m_20189_() - this.m_20189_();
         speed = 0.15D;
         this.m_20256_(this.m_20184_().m_82520_(minusX * speed * 0.1D, minusY * speed * 0.1D, minusZ * speed * 0.1D));
      }

      if (shootingEntity instanceof Player) {
         target = ((Player)shootingEntity).m_21232_();
         if (target == null || !target.m_6084_()) {
            minusX = 10.0D;
            List<Entity> list = this.m_9236_().m_6249_(shootingEntity, (new AABB(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_20185_() + 1.0D, this.m_20186_() + 1.0D, this.m_20189_() + 1.0D)).m_82377_(minusX, 10.0D, minusX), EntitySelector.f_20402_);
            LivingEntity closest = null;
            if (!list.isEmpty()) {
               Iterator var20 = list.iterator();

               label75:
               while(true) {
                  Entity e;
                  do {
                     do {
                        do {
                           do {
                              if (!var20.hasNext()) {
                                 break label75;
                              }

                              e = (Entity)var20.next();
                           } while(!(e instanceof LivingEntity));
                        } while(e.m_20148_().equals(shootingEntity.m_20148_()));
                     } while(!(e instanceof Enemy));
                  } while(closest != null && !(closest.m_20270_(shootingEntity) > e.m_20270_(shootingEntity)));

                  closest = (LivingEntity)e;
               }
            }

            target = closest;
         }

         if (target != null && target.m_6084_()) {
            minusX = target.m_20185_() - this.m_20185_();
            minusY = target.m_20186_() + (double)target.m_20192_() - this.m_20186_();
            minusZ = target.m_20189_() - this.m_20189_();
            speed = 0.25D * Math.min((double)this.m_20270_(target), 10.0D) / 10.0D;
            this.m_20256_(this.m_20184_().m_82520_((Math.signum(minusX) * 0.5D - this.m_20184_().f_82479_) * 0.10000000149011612D, (Math.signum(minusY) * 0.5D - this.m_20184_().f_82480_) * 0.10000000149011612D, (Math.signum(minusZ) * 0.5D - this.m_20184_().f_82481_) * 0.10000000149011612D));
            this.m_146922_((float)(Mth.m_14136_(this.m_20184_().f_82479_, this.m_20184_().f_82481_) * 57.29577951308232D));
            this.m_146926_((float)(Mth.m_14136_(this.m_20184_().f_82480_, (double)sqrt) * 57.29577951308232D));
            flag = false;
         }
      }

      if ((sqrt < 0.1F || this.f_19862_ || this.f_19863_ || this.f_36703_) && this.f_19797_ > 5 && flag) {
         this.m_142687_(RemovalReason.DISCARDED);
      }

      double d0 = 0.0D;
      double d1 = 0.01D;
      double d2 = 0.0D;
      double x = this.m_20185_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_();
      double y = this.m_20186_() + (double)(this.f_19796_.m_188501_() * this.m_20206_()) - (double)this.m_20206_();
      double z = this.m_20189_() + (double)(this.f_19796_.m_188501_() * this.m_20205_() * 2.0F) - (double)this.m_20205_();
      float f = (this.m_20205_() + this.m_20206_() + this.m_20205_()) * 0.333F + 0.5F;
      if (this.particleDistSq(x, y, z) < (double)(f * f)) {
         IceAndFire.PROXY.spawnParticle(EnumParticles.Dread_Torch, x, y + 0.5D, z, d0, d1, d2);
      }

      super.m_8119_();
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

   protected void m_5790_(EntityHitResult raytraceResultIn) {
      if (raytraceResultIn.m_6662_() == Type.ENTITY) {
         Entity entity = raytraceResultIn.m_82443_();
         Entity shootingEntity = this.m_19749_();
         if (entity != null && shootingEntity != null && entity.m_7307_(shootingEntity)) {
            return;
         }
      }

      super.m_5790_(raytraceResultIn);
   }

   protected void m_7761_(@NotNull LivingEntity living) {
      super.m_7761_(living);
      Entity shootingEntity = this.m_19749_();
      if (living != null && (shootingEntity == null || !living.m_7306_(shootingEntity)) && living instanceof Player) {
         this.damageShield((Player)living, (float)this.m_36789_());
      }

   }

   protected void damageShield(Player player, float damage) {
      if (damage >= 3.0F && player.m_21211_().m_41720_().canPerformAction(player.m_21211_(), ToolActions.SHIELD_BLOCK)) {
         ItemStack copyBeforeUse = player.m_21211_().m_41777_();
         int i = 1 + Mth.m_14143_(damage);
         player.m_21211_().m_41622_(i, player, (playerSheild) -> {
            playerSheild.m_21190_(playerSheild.m_7655_());
         });
         if (player.m_21211_().m_41619_()) {
            InteractionHand Hand = player.m_7655_();
            ForgeEventFactory.onPlayerDestroyItem(player, copyBeforeUse, Hand);
            if (Hand == InteractionHand.MAIN_HAND) {
               this.m_8061_(EquipmentSlot.MAINHAND, ItemStack.f_41583_);
            } else {
               this.m_8061_(EquipmentSlot.OFFHAND, ItemStack.f_41583_);
            }

            player.m_5810_();
            this.m_5496_(SoundEvents.f_12347_, 0.8F, 0.8F + this.m_9236_().f_46441_.m_188501_() * 0.4F);
         }
      }

   }

   public int getBrightnessForRender() {
      return 15728880;
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
}
