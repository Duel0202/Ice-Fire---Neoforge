package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityDragonEgg;
import com.github.alexthe666.iceandfire.entity.EntityIceDragon;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityEggInIce extends BlockEntity {
   public EnumDragonEgg type;
   public int age;
   public int ticksExisted;
   @Nullable
   public UUID ownerUUID;
   private boolean spawned;

   public TileEntityEggInIce(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.EGG_IN_ICE.get(), pos, state);
   }

   public static void tickEgg(Level level, BlockPos pos, BlockState state, TileEntityEggInIce entityEggInIce) {
      ++entityEggInIce.age;
      if (entityEggInIce.age >= IafConfig.dragonEggTime && entityEggInIce.type != null && !entityEggInIce.spawned && !level.f_46443_) {
         EntityIceDragon dragon = new EntityIceDragon(level);
         dragon.m_6034_((double)pos.m_123341_() + 0.5D, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5D);
         dragon.setVariant(entityEggInIce.type.ordinal() - 4);
         dragon.setGender(ThreadLocalRandom.current().nextBoolean());
         dragon.m_7105_(true);
         dragon.setHunger(50);
         dragon.m_21816_(entityEggInIce.ownerUUID);
         level.m_7967_(dragon);
         entityEggInIce.spawned = true;
         level.m_46961_(pos, false);
         level.m_46597_(pos, Blocks.f_49990_.m_49966_());
      }

      ++entityEggInIce.ticksExisted;
   }

   public void m_183515_(@NotNull CompoundTag tag) {
      if (this.type != null) {
         tag.m_128344_("Color", (byte)this.type.ordinal());
      } else {
         tag.m_128344_("Color", (byte)0);
      }

      tag.m_128405_("Age", this.age);
      if (this.ownerUUID == null) {
         tag.m_128359_("OwnerUUID", "");
      } else {
         tag.m_128362_("OwnerUUID", this.ownerUUID);
      }

   }

   public void m_142466_(@NotNull CompoundTag tag) {
      super.m_142466_(tag);
      this.type = EnumDragonEgg.values()[tag.m_128445_("Color")];
      this.age = tag.m_128451_("Age");
      UUID s = null;
      if (tag.m_128403_("OwnerUUID")) {
         s = tag.m_128342_("OwnerUUID");
      } else {
         try {
            String s1 = tag.m_128461_("OwnerUUID");
            s = OldUsersConverter.m_11083_(this.f_58857_.m_7654_(), s1);
         } catch (Exception var4) {
         }
      }

      if (s != null) {
         this.ownerUUID = s;
      }

   }

   public void handleUpdateTag(CompoundTag parentNBTTagCompound) {
      this.m_142466_(parentNBTTagCompound);
   }

   @NotNull
   public CompoundTag m_5995_() {
      CompoundTag nbtTagCompound = new CompoundTag();
      this.m_183515_(nbtTagCompound);
      return nbtTagCompound;
   }

   @Nullable
   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      CompoundTag nbtTagCompound = new CompoundTag();
      this.m_183515_(nbtTagCompound);
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      this.m_142466_(pkt.m_131708_());
   }

   public void spawnEgg() {
      if (this.type != null) {
         EntityDragonEgg egg = new EntityDragonEgg((EntityType)IafEntityRegistry.DRAGON_EGG.get(), this.f_58857_);
         egg.setEggType(this.type);
         egg.m_6034_((double)this.f_58858_.m_123341_() + 0.5D, (double)(this.f_58858_.m_123342_() + 1), (double)this.f_58858_.m_123343_() + 0.5D);
         egg.setOwnerId(this.ownerUUID);
         if (!this.f_58857_.f_46443_) {
            this.f_58857_.m_7967_(egg);
         }
      }

   }
}
