package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouse;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouseModel;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityPixieHouse extends BlockEntity {
   private static final float PARTICLE_WIDTH = 0.3F;
   private static final float PARTICLE_HEIGHT = 0.6F;
   private final Random rand;
   public int houseType;
   public boolean hasPixie;
   public boolean tamedPixie;
   public UUID pixieOwnerUUID;
   public int pixieType;
   public NonNullList<ItemStack> pixieItems;

   public TileEntityPixieHouse(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.PIXIE_HOUSE.get(), pos, state);
      this.pixieItems = NonNullList.m_122780_(1, ItemStack.f_41583_);
      this.rand = new Random();
   }

   public static int getHouseTypeFromBlock(Block block) {
      if (block == IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED.get()) {
         return 1;
      } else if (block == IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN.get()) {
         return 0;
      } else if (block == IafBlockRegistry.PIXIE_HOUSE_OAK.get()) {
         return 3;
      } else if (block == IafBlockRegistry.PIXIE_HOUSE_BIRCH.get()) {
         return 2;
      } else if (block == IafBlockRegistry.PIXIE_HOUSE_SPRUCE.get()) {
         return 5;
      } else {
         return block == IafBlockRegistry.PIXIE_HOUSE_DARK_OAK.get() ? 4 : 0;
      }
   }

   public static void tickClient(Level level, BlockPos pos, BlockState state, TileEntityPixieHouse entityPixieHouse) {
      if (entityPixieHouse.hasPixie) {
         IceAndFire.PROXY.spawnParticle(EnumParticles.If_Pixie, (double)((float)pos.m_123341_() + 0.5F) + (double)(entityPixieHouse.rand.nextFloat() * 0.3F * 2.0F) - 0.30000001192092896D, (double)pos.m_123342_() + (double)(entityPixieHouse.rand.nextFloat() * 0.6F), (double)((float)pos.m_123343_() + 0.5F) + (double)(entityPixieHouse.rand.nextFloat() * 0.3F * 2.0F) - 0.30000001192092896D, (double)EntityPixie.PARTICLE_RGB[entityPixieHouse.pixieType][0], (double)EntityPixie.PARTICLE_RGB[entityPixieHouse.pixieType][1], (double)EntityPixie.PARTICLE_RGB[entityPixieHouse.pixieType][2]);
      }

   }

   public static void tickServer(Level level, BlockPos pos, BlockState state, TileEntityPixieHouse entityPixieHouse) {
      if (entityPixieHouse.hasPixie && ThreadLocalRandom.current().nextInt(100) == 0) {
         entityPixieHouse.releasePixie();
      }

   }

   public void m_183515_(CompoundTag compound) {
      compound.m_128405_("HouseType", this.houseType);
      compound.m_128379_("HasPixie", this.hasPixie);
      compound.m_128405_("PixieType", this.pixieType);
      compound.m_128379_("TamedPixie", this.tamedPixie);
      if (this.pixieOwnerUUID != null) {
         compound.m_128362_("PixieOwnerUUID", this.pixieOwnerUUID);
      }

      ContainerHelper.m_18973_(compound, this.pixieItems);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
      this.m_142466_(packet.m_131708_());
      if (!this.f_58857_.f_46443_) {
         IceAndFire.sendMSGToAll(new MessageUpdatePixieHouseModel(this.f_58858_.m_121878_(), packet.m_131708_().m_128451_("HouseType")));
      }

   }

   @NotNull
   public CompoundTag m_5995_() {
      return this.m_187480_();
   }

   public void m_142466_(CompoundTag compound) {
      this.houseType = compound.m_128451_("HouseType");
      this.hasPixie = compound.m_128471_("HasPixie");
      this.pixieType = compound.m_128451_("PixieType");
      this.tamedPixie = compound.m_128471_("TamedPixie");
      if (compound.m_128403_("PixieOwnerUUID")) {
         this.pixieOwnerUUID = compound.m_128342_("PixieOwnerUUID");
      }

      this.pixieItems = NonNullList.m_122780_(1, ItemStack.f_41583_);
      ContainerHelper.m_18980_(compound, this.pixieItems);
      super.m_142466_(compound);
   }

   public void releasePixie() {
      EntityPixie pixie = new EntityPixie((EntityType)IafEntityRegistry.PIXIE.get(), this.f_58857_);
      pixie.m_19890_((double)((float)this.f_58858_.m_123341_() + 0.5F), (double)((float)this.f_58858_.m_123342_() + 1.0F), (double)((float)this.f_58858_.m_123343_() + 0.5F), (float)ThreadLocalRandom.current().nextInt(360), 0.0F);
      pixie.m_21008_(InteractionHand.MAIN_HAND, (ItemStack)this.pixieItems.get(0));
      pixie.setColor(this.pixieType);
      if (!this.f_58857_.f_46443_) {
         this.f_58857_.m_7967_(pixie);
      }

      this.hasPixie = false;
      this.pixieType = 0;
      pixie.ticksUntilHouseAI = 500;
      pixie.m_7105_(this.tamedPixie);
      pixie.m_21816_(this.pixieOwnerUUID);
      if (!this.f_58857_.f_46443_) {
         IceAndFire.sendMSGToAll(new MessageUpdatePixieHouse(this.f_58858_.m_121878_(), false, 0));
      }

   }
}
