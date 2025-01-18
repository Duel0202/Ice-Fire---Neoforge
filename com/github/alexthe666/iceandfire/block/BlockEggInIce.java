package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityEggInIce;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockEggInIce extends BaseEntityBlock {
   public Item itemBlock;

   public BlockEggInIce() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283828_).m_60955_().m_60988_().m_60978_(0.5F).m_60988_().m_60918_(SoundType.f_56744_));
   }

   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityEggInIce(pos, state);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
      return m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.EGG_IN_ICE.get(), TileEntityEggInIce::tickEgg);
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   public void m_6240_(@NotNull Level worldIn, Player player, @NotNull BlockPos pos, @NotNull BlockState state, BlockEntity te, @NotNull ItemStack stack) {
      player.m_36246_(Stats.f_12949_.m_12902_(this));
      player.m_36399_(0.005F);
   }

   public void m_5707_(Level worldIn, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull Player player) {
      if (worldIn.m_7702_(pos) != null) {
         BlockEntity var6 = worldIn.m_7702_(pos);
         if (var6 instanceof TileEntityEggInIce) {
            TileEntityEggInIce tile = (TileEntityEggInIce)var6;
            tile.spawnEgg();
         }
      }

   }
}
