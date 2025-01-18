package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouse;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouseModel;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieJar;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import net.neoforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;

public class TileEntityJar extends BlockEntity {
   private static final float PARTICLE_WIDTH = 0.3F;
   private static final float PARTICLE_HEIGHT = 0.6F;
   public boolean hasPixie;
   public boolean prevHasProduced;
   public boolean hasProduced;
   public boolean tamedPixie;
   public UUID pixieOwnerUUID;
   public int pixieType;
   public int ticksExisted;
   public NonNullList<ItemStack> pixieItems;
   public float rotationYaw;
   public float prevRotationYaw;
   LazyOptional<? extends IItemHandler> downHandler;
   private final Random rand;

   public TileEntityJar(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.PIXIE_JAR.get(), pos, state);
      this.pixieItems = NonNullList.m_122780_(1, ItemStack.f_41583_);
      this.downHandler = PixieJarInvWrapper.create(this);
      this.rand = new Random();
      this.hasPixie = true;
   }

   public TileEntityJar(BlockPos pos, BlockState state, boolean empty) {
      super((BlockEntityType)IafTileEntityRegistry.PIXIE_JAR.get(), pos, state);
      this.pixieItems = NonNullList.m_122780_(1, ItemStack.f_41583_);
      this.downHandler = PixieJarInvWrapper.create(this);
      this.rand = new Random();
      this.hasPixie = !empty;
   }

   public void m_183515_(CompoundTag compound) {
      compound.m_128379_("HasPixie", this.hasPixie);
      compound.m_128405_("PixieType", this.pixieType);
      compound.m_128379_("HasProduced", this.hasProduced);
      compound.m_128379_("TamedPixie", this.tamedPixie);
      if (this.pixieOwnerUUID != null) {
         compound.m_128362_("PixieOwnerUUID", this.pixieOwnerUUID);
      }

      compound.m_128405_("TicksExisted", this.ticksExisted);
      ContainerHelper.m_18973_(compound, this.pixieItems);
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
      this.m_142466_(packet.m_131708_());
      if (!this.f_58857_.f_46443_) {
         IceAndFire.sendMSGToAll(new MessageUpdatePixieHouseModel(this.f_58858_.m_121878_(), packet.m_131708_().m_128451_("PixieType")));
      }

   }

   public void m_142466_(CompoundTag compound) {
      this.hasPixie = compound.m_128471_("HasPixie");
      this.pixieType = compound.m_128451_("PixieType");
      this.hasProduced = compound.m_128471_("HasProduced");
      this.ticksExisted = compound.m_128451_("TicksExisted");
      this.tamedPixie = compound.m_128471_("TamedPixie");
      if (compound.m_128403_("PixieOwnerUUID")) {
         this.pixieOwnerUUID = compound.m_128342_("PixieOwnerUUID");
      }

      this.pixieItems = NonNullList.m_122780_(1, ItemStack.f_41583_);
      ContainerHelper.m_18980_(compound, this.pixieItems);
      super.m_142466_(compound);
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TileEntityJar entityJar) {
      ++entityJar.ticksExisted;
      if (level.f_46443_ && entityJar.hasPixie) {
         IceAndFire.PROXY.spawnParticle(EnumParticles.If_Pixie, (double)((float)pos.m_123341_() + 0.5F) + (double)(entityJar.rand.nextFloat() * 0.3F * 2.0F) - 0.30000001192092896D, (double)pos.m_123342_() + (double)(entityJar.rand.nextFloat() * 0.6F), (double)((float)pos.m_123343_() + 0.5F) + (double)(entityJar.rand.nextFloat() * 0.3F * 2.0F) - 0.30000001192092896D, (double)EntityPixie.PARTICLE_RGB[entityJar.pixieType][0], (double)EntityPixie.PARTICLE_RGB[entityJar.pixieType][1], (double)EntityPixie.PARTICLE_RGB[entityJar.pixieType][2]);
      }

      if (entityJar.ticksExisted % 24000 == 0 && !entityJar.hasProduced && entityJar.hasPixie) {
         entityJar.hasProduced = true;
         if (!level.f_46443_) {
            IceAndFire.sendMSGToAll(new MessageUpdatePixieJar(pos.m_121878_(), entityJar.hasProduced));
         }
      }

      if (entityJar.hasPixie && entityJar.hasProduced != entityJar.prevHasProduced && entityJar.ticksExisted > 5) {
         if (!level.f_46443_) {
            IceAndFire.sendMSGToAll(new MessageUpdatePixieJar(pos.m_121878_(), entityJar.hasProduced));
         } else {
            level.m_7785_((double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.5D, (double)pos.m_123343_() + 0.5D, IafSoundRegistry.PIXIE_HURT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         }
      }

      entityJar.prevRotationYaw = entityJar.rotationYaw;
      if (entityJar.rand.nextInt(30) == 0) {
         entityJar.rotationYaw = entityJar.rand.nextFloat() * 360.0F - 180.0F;
      }

      if (entityJar.hasPixie && entityJar.ticksExisted % 40 == 0 && entityJar.rand.nextInt(6) == 0 && level.f_46443_) {
         level.m_7785_((double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.5D, (double)pos.m_123343_() + 0.5D, IafSoundRegistry.PIXIE_IDLE, SoundSource.BLOCKS, 1.0F, 1.0F, false);
      }

      entityJar.prevHasProduced = entityJar.hasProduced;
   }

   public void releasePixie() {
      EntityPixie pixie = new EntityPixie((EntityType)IafEntityRegistry.PIXIE.get(), this.f_58857_);
      pixie.m_19890_((double)((float)this.f_58858_.m_123341_() + 0.5F), (double)((float)this.f_58858_.m_123342_() + 1.0F), (double)((float)this.f_58858_.m_123343_() + 0.5F), (float)(new Random()).nextInt(360), 0.0F);
      pixie.m_21008_(InteractionHand.MAIN_HAND, (ItemStack)this.pixieItems.get(0));
      pixie.setColor(this.pixieType);
      this.f_58857_.m_7967_(pixie);
      this.hasPixie = false;
      this.pixieType = 0;
      pixie.ticksUntilHouseAI = 500;
      pixie.m_7105_(this.tamedPixie);
      pixie.m_21816_(this.pixieOwnerUUID);
      if (!this.f_58857_.f_46443_) {
         IceAndFire.sendMSGToAll(new MessageUpdatePixieHouse(this.f_58858_.m_121878_(), false, 0));
      }

   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
      return facing == Direction.DOWN && capability == ForgeCapabilities.ITEM_HANDLER ? this.downHandler.cast() : super.getCapability(capability, facing);
   }
}
