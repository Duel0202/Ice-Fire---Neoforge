package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.neoforge.network.NetworkHooks;
import net.neoforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

public class EntityStymphalianArrow extends AbstractArrow {
   public EntityStymphalianArrow(EntityType<? extends AbstractArrow> t, Level worldIn) {
      super(t, worldIn);
      this.m_36781_(3.5D);
   }

   public EntityStymphalianArrow(EntityType<? extends AbstractArrow> t, Level worldIn, double x, double y, double z) {
      this(t, worldIn);
      this.m_6034_(x, y, z);
      this.m_36781_(3.5D);
   }

   public EntityStymphalianArrow(SpawnEntity spawnEntity, Level world) {
      this((EntityType)IafEntityRegistry.STYMPHALIAN_ARROW.get(), world);
   }

   @NotNull
   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public EntityStymphalianArrow(EntityType t, Level worldIn, LivingEntity shooter) {
      super(t, shooter, worldIn);
      this.m_36781_(3.5D);
   }

   public void m_8119_() {
      super.m_8119_();
      float sqrt = Mth.m_14116_((float)(this.m_20184_().f_82479_ * this.m_20184_().f_82479_ + this.m_20184_().f_82481_ * this.m_20184_().f_82481_));
      if (sqrt < 0.1F) {
         this.m_20256_(this.m_20184_().m_82520_(0.0D, -0.009999999776482582D, 0.0D));
      }

   }

   public boolean m_20068_() {
      return true;
   }

   @NotNull
   protected ItemStack m_7941_() {
      return new ItemStack((ItemLike)IafItemRegistry.STYMPHALIAN_ARROW.get());
   }
}
