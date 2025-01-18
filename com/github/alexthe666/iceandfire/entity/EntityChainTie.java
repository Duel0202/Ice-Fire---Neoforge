package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.phys.AABB;
import net.neoforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;

public class EntityChainTie extends HangingEntity {
   public EntityChainTie(EntityType<? extends HangingEntity> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityChainTie(EntityType<? extends HangingEntity> type, Level worldIn, BlockPos hangingPositionIn) {
      super(type, worldIn, hangingPositionIn);
      this.m_6034_((double)hangingPositionIn.m_123341_() + 0.5D, (double)hangingPositionIn.m_123342_(), (double)hangingPositionIn.m_123343_() + 0.5D);
   }

   public static EntityChainTie createTie(Level worldIn, BlockPos fence) {
      EntityChainTie entityChainTie = new EntityChainTie((EntityType)IafEntityRegistry.CHAIN_TIE.get(), worldIn, fence);
      worldIn.m_7967_(entityChainTie);
      entityChainTie.m_7084_();
      return entityChainTie;
   }

   @Nullable
   public static EntityChainTie getKnotForPosition(Level worldIn, BlockPos pos) {
      int i = pos.m_123341_();
      int j = pos.m_123342_();
      int k = pos.m_123343_();
      Iterator var5 = worldIn.m_45976_(EntityChainTie.class, new AABB((double)i - 1.0D, (double)j - 1.0D, (double)k - 1.0D, (double)i + 1.0D, (double)j + 1.0D, (double)k + 1.0D)).iterator();

      EntityChainTie entityleashknot;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         entityleashknot = (EntityChainTie)var5.next();
      } while(entityleashknot == null || entityleashknot.m_31748_() == null || !entityleashknot.m_31748_().equals(pos));

      return entityleashknot;
   }

   public void m_6034_(double x, double y, double z) {
      super.m_6034_((double)Mth.m_14107_(x) + 0.5D, (double)Mth.m_14107_(y) + 0.5D, (double)Mth.m_14107_(z) + 0.5D);
   }

   protected void m_7087_() {
      this.m_20343_((double)this.f_31698_.m_123341_() + 0.5D, (double)this.f_31698_.m_123342_() + 0.5D, (double)this.f_31698_.m_123343_() + 0.5D);
      double xSize = 0.3D;
      double ySize = 0.875D;
      this.m_20011_(new AABB(this.m_20185_() - xSize, this.m_20186_() - 0.5D, this.m_20189_() - xSize, this.m_20185_() + xSize, this.m_20186_() + ySize - 0.5D, this.m_20189_() + xSize));
   }

   public boolean m_6469_(DamageSource source, float amount) {
      return source.m_7639_() != null && source.m_7639_() instanceof Player ? super.m_6469_(source, amount) : false;
   }

   public int m_7076_() {
      return 9;
   }

   public int m_7068_() {
      return 9;
   }

   public void m_7380_(CompoundTag compound) {
      BlockPos blockpos = this.m_31748_();
      compound.m_128405_("TileX", blockpos.m_123341_());
      compound.m_128405_("TileY", blockpos.m_123342_());
      compound.m_128405_("TileZ", blockpos.m_123343_());
   }

   public void m_7378_(CompoundTag compound) {
      this.f_31698_ = new BlockPos(compound.m_128451_("TileX"), compound.m_128451_("TileY"), compound.m_128451_("TileZ"));
   }

   protected float m_6380_(@NotNull Pose poseIn, @NotNull EntityDimensions sizeIn) {
      return -0.0625F;
   }

   public boolean m_6783_(double distance) {
      return distance < 1024.0D;
   }

   public void m_5553_(@Nullable Entity brokenEntity) {
      this.m_5496_(SoundEvents.f_11672_, 1.0F, 1.0F);
   }

   public void m_142687_(@NotNull RemovalReason removalReason) {
      super.m_142687_(removalReason);
      double d0 = 30.0D;
      List<LivingEntity> list = this.m_9236_().m_45976_(LivingEntity.class, new AABB(this.m_20185_() - d0, this.m_20186_() - d0, this.m_20189_() - d0, this.m_20185_() + d0, this.m_20186_() + d0, this.m_20189_() + d0));
      Iterator var5 = list.iterator();

      while(var5.hasNext()) {
         LivingEntity livingEntity = (LivingEntity)var5.next();
         EntityDataProvider.getCapability(livingEntity).ifPresent((data) -> {
            if (data.chainData.isChainedTo(this)) {
               data.chainData.removeChain(this);
               ItemEntity entityitem = new ItemEntity(this.m_9236_(), this.m_20185_(), this.m_20186_() + 1.0D, this.m_20189_(), new ItemStack((ItemLike)IafItemRegistry.CHAIN.get()));
               entityitem.m_32060_();
               this.m_9236_().m_7967_(entityitem);
            }

         });
      }

   }

   @NotNull
   public InteractionResult m_6096_(@NotNull Player player, @NotNull InteractionHand hand) {
      if (this.m_9236_().f_46443_) {
         return InteractionResult.SUCCESS;
      } else {
         AtomicBoolean flag = new AtomicBoolean(false);
         double radius = 30.0D;
         List<LivingEntity> list = this.m_9236_().m_45976_(LivingEntity.class, new AABB(this.m_20185_() - radius, this.m_20186_() - radius, this.m_20189_() - radius, this.m_20185_() + radius, this.m_20186_() + radius, this.m_20189_() + radius));
         Iterator var7 = list.iterator();

         while(var7.hasNext()) {
            LivingEntity livingEntity = (LivingEntity)var7.next();
            EntityDataProvider.getCapability(livingEntity).ifPresent((data) -> {
               if (data.chainData.isChainedTo(player)) {
                  data.chainData.removeChain(player);
                  data.chainData.attachChain(this);
                  flag.set(true);
               }

            });
         }

         if (!flag.get()) {
            this.m_142687_(RemovalReason.DISCARDED);
            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.CONSUME;
         }
      }
   }

   public Packet<ClientGamePacketListener> m_5654_() {
      return NetworkHooks.getEntitySpawningPacket(this);
   }

   public boolean m_7088_() {
      return this.m_9236_().m_8055_(this.f_31698_).m_60734_() instanceof WallBlock;
   }

   public void m_7084_() {
      this.m_5496_(SoundEvents.f_11672_, 1.0F, 1.0F);
   }
}
