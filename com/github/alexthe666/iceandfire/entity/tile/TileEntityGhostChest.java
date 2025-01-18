package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityGhostChest extends ChestBlockEntity {
   public TileEntityGhostChest(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.GHOST_CHEST.get(), pos, state);
   }

   public void m_142466_(@NotNull CompoundTag nbt) {
      super.m_142466_(nbt);
   }

   public void m_5856_(@NotNull Player player) {
      super.m_5856_(player);
      if (this.f_58857_.m_46791_() != Difficulty.PEACEFUL) {
         EntityGhost ghost = (EntityGhost)((EntityType)IafEntityRegistry.GHOST.get()).m_20615_(this.f_58857_);
         ghost.m_19890_((double)((float)this.f_58858_.m_123341_() + 0.5F), (double)((float)this.f_58858_.m_123342_() + 0.5F), (double)((float)this.f_58858_.m_123343_() + 0.5F), ThreadLocalRandom.current().nextFloat() * 360.0F, 0.0F);
         if (!this.f_58857_.f_46443_) {
            ghost.m_6518_((ServerLevel)this.f_58857_, this.f_58857_.m_6436_(this.f_58858_), MobSpawnType.SPAWNER, (SpawnGroupData)null, (CompoundTag)null);
            if (!player.m_7500_()) {
               ghost.m_6710_(player);
            }

            ghost.m_21530_();
            this.f_58857_.m_7967_(ghost);
         }

         ghost.setAnimation(EntityGhost.ANIMATION_SCARE);
         ghost.m_21446_(this.f_58858_, 4);
         ghost.setFromChest(true);
      }

   }

   protected void m_142151_(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, int p_155336_, int p_155337_) {
      super.m_142151_(level, pos, state, p_155336_, p_155337_);
      level.m_46672_(pos.m_7495_(), state.m_60734_());
   }
}
