package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.nbt.CompoundTag;
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

public class EntityDragonArrow extends AbstractArrow {
   public EntityDragonArrow(EntityType<? extends AbstractArrow> typeIn, Level worldIn) {
      super(typeIn, worldIn);
      this.m_36781_(10.0D);
   }

   public EntityDragonArrow(EntityType<? extends AbstractArrow> typeIn, double x, double y, double z, Level world) {
      super(typeIn, x, y, z, world);
      this.m_36781_(10.0D);
   }

   public EntityDragonArrow(SpawnEntity spawnEntity, Level worldIn) {
      this((EntityType)IafEntityRegistry.DRAGON_ARROW.get(), worldIn);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityDragonArrow(EntityType<? extends AbstractArrow> typeIn, LivingEntity shooter, Level worldIn) {
      super(typeIn, shooter, worldIn);
      this.m_36781_(10.0D);
   }

   public void m_7380_(@NotNull CompoundTag tagCompound) {
      super.m_7380_(tagCompound);
      tagCompound.m_128347_("damage", 10.0D);
   }

   public void m_7378_(@NotNull CompoundTag tagCompund) {
      super.m_7378_(tagCompund);
      this.m_36781_(tagCompund.m_128459_("damage"));
   }

   @NotNull
   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_ARROW.get());
   }
}
