package com.github.alexthe666.iceandfire.entity.tile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityDreadPortal extends BlockEntity {
   private long age;
   private BlockPos exitPortal;
   private boolean exactTeleport;

   public TileEntityDreadPortal(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.DREAD_PORTAL.get(), pos, state);
   }

   public void m_183515_(@NotNull CompoundTag compound) {
      super.m_183515_(compound);
      compound.m_128356_("Age", this.age);
      if (this.exitPortal != null) {
      }

      if (this.exactTeleport) {
         compound.m_128379_("ExactTeleport", this.exactTeleport);
      }

   }

   public void m_142466_(@NotNull CompoundTag compound) {
      super.m_142466_(compound);
      this.age = compound.m_128454_("Age");
      if (compound.m_128425_("ExitPortal", 10)) {
         this.exitPortal = BlockPos.f_121853_;
      }

      this.exactTeleport = compound.m_128471_("ExactTeleport");
   }

   public static void tick(Level level, BlockPos pos, BlockState state, TileEntityDreadPortal dreadPortal) {
      ++dreadPortal.age;
   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public boolean shouldRenderFace(Direction face) {
      return true;
   }
}
