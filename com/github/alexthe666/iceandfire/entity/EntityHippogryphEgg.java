package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownEgg;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityHippogryphEgg extends ThrownEgg {
   private ItemStack itemstack;

   public EntityHippogryphEgg(EntityType<? extends ThrownEgg> type, Level world) {
      super(type, world);
   }

   public EntityHippogryphEgg(EntityType<? extends ThrownEgg> type, Level worldIn, double x, double y, double z, ItemStack stack) {
      this(type, worldIn);
      this.m_6034_(x, y, z);
      this.itemstack = stack;
   }

   public EntityHippogryphEgg(EntityType<? extends ThrownEgg> type, Level worldIn, LivingEntity throwerIn, ItemStack stack) {
      this(type, worldIn);
      this.m_6034_(throwerIn.m_20185_(), throwerIn.m_20188_() - 0.10000000149011612D, throwerIn.m_20189_());
      this.itemstack = stack;
      this.m_5602_(throwerIn);
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
         EntityHippogryph hippogryph = new EntityHippogryph((EntityType)IafEntityRegistry.HIPPOGRYPH.get(), this.m_9236_());
         hippogryph.m_146762_(-24000);
         hippogryph.m_7678_(this.m_20185_(), this.m_20186_(), this.m_20189_(), this.m_146908_(), 0.0F);
         if (this.itemstack != null) {
            int variant = 0;
            CompoundTag tag = this.itemstack.m_41783_();
            if (tag != null) {
               variant = tag.m_128451_("EggOrdinal");
            }

            hippogryph.setVariant(variant);
         }

         if (thrower instanceof Player) {
            hippogryph.m_21828_((Player)thrower);
         }

         this.m_9236_().m_7967_(hippogryph);
      }

      this.m_9236_().m_7605_(this, (byte)3);
      this.m_142687_(RemovalReason.DISCARDED);
   }

   @NotNull
   protected Item m_7881_() {
      return (Item)IafItemRegistry.HIPPOGRYPH_EGG.get();
   }
}
