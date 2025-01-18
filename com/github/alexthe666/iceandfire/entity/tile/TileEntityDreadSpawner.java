package com.github.alexthe666.iceandfire.entity.tile;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class TileEntityDreadSpawner extends SpawnerBlockEntity {
   private final BlockEntityType<?> type;
   private final DreadSpawnerBaseLogic spawner = new DreadSpawnerBaseLogic() {
      public void m_142523_(Level p_155767_, @NotNull BlockPos p_155768_, int p_155769_) {
         p_155767_.m_7696_(p_155768_, Blocks.f_50085_, p_155769_, 0);
      }

      public void m_142667_(@Nullable Level p_155771_, @NotNull BlockPos p_155772_, @NotNull SpawnData p_155773_) {
         super.m_142667_(p_155771_, p_155772_, p_155773_);
         if (p_155771_ != null) {
            BlockState blockstate = p_155771_.m_8055_(p_155772_);
            p_155771_.m_7260_(p_155772_, blockstate, blockstate, 4);
         }

      }

      @Nullable
      public BlockEntity getSpawnerBlockEntity() {
         return TileEntityDreadSpawner.this;
      }
   };

   public TileEntityDreadSpawner(BlockPos pos, BlockState state) {
      super(pos, state);
      this.type = (BlockEntityType)IafTileEntityRegistry.DREAD_SPAWNER.get();
   }

   public void m_142466_(@NotNull CompoundTag p_155760_) {
      super.m_142466_(p_155760_);
      this.spawner.m_151328_(this.f_58857_, this.f_58858_, p_155760_);
   }

   public CompoundTag save(CompoundTag p_59795_) {
      super.m_183515_(p_59795_);
      this.spawner.m_186381_(p_59795_);
      return p_59795_;
   }

   public static void clientTick(Level p_155755_, BlockPos p_155756_, BlockState p_155757_, TileEntityDreadSpawner p_155758_) {
      p_155758_.spawner.m_151319_(p_155755_, p_155756_);
   }

   public static void serverTick(Level p_155762_, BlockPos p_155763_, BlockState p_155764_, TileEntityDreadSpawner p_155765_) {
      p_155765_.spawner.m_151311_((ServerLevel)p_155762_, p_155763_);
   }

   @Nullable
   public ClientboundBlockEntityDataPacket m_58483_() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   @NotNull
   public CompoundTag m_5995_() {
      CompoundTag compoundtag = this.save(new CompoundTag());
      compoundtag.m_128473_("SpawnPotentials");
      return compoundtag;
   }

   public boolean m_7531_(int p_59797_, int p_59798_) {
      return this.spawner.m_151316_(this.f_58857_, p_59797_) || super.m_7531_(p_59797_, p_59798_);
   }

   public boolean m_6326_() {
      return true;
   }

   @NotNull
   public BaseSpawner m_59801_() {
      return this.spawner;
   }

   @NotNull
   public BlockEntityType<?> m_58903_() {
      return this.type != null ? this.type : super.m_58903_();
   }
}
