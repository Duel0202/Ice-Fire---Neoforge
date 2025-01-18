package com.github.alexthe666.iceandfire.entity.tile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.capabilities.ICapabilityProvider;
import net.neoforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class TileEntityDragonforgeBrick extends BlockEntity {
   public TileEntityDragonforgeBrick(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.DRAGONFORGE_BRICK.get(), pos, state);
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
      return this.getConnectedTileEntity() != null && capability == ForgeCapabilities.ITEM_HANDLER ? this.getConnectedTileEntity().getCapability(capability, facing) : super.getCapability(capability, facing);
   }

   private ICapabilityProvider getConnectedTileEntity() {
      Direction[] var1 = Direction.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Direction facing = var1[var3];
         if (this.f_58857_.m_7702_(this.f_58858_.m_121945_(facing)) instanceof TileEntityDragonforge) {
            return this.f_58857_.m_7702_(this.f_58858_.m_121945_(facing));
         }
      }

      return null;
   }
}
