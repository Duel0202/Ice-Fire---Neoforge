package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityMyrmexCocoon;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlockMyrmexCocoon extends BaseEntityBlock {
   public BlockMyrmexCocoon() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283762_).m_60978_(2.5F).m_60955_().m_60988_().m_60918_(SoundType.f_56750_));
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   public void m_6810_(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
      BlockEntity tileentity = worldIn.m_7702_(pos);
      if (tileentity instanceof Container) {
         Containers.m_19002_(worldIn, pos, (Container)tileentity);
         worldIn.m_46717_(pos, this);
      }

      super.m_6810_(state, worldIn, pos, newState, isMoving);
   }

   @NotNull
   public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
      if (!player.m_6144_()) {
         if (worldIn.f_46443_) {
            IceAndFire.PROXY.setRefrencedTE(worldIn.m_7702_(pos));
         } else {
            MenuProvider inamedcontainerprovider = this.m_7246_(state, worldIn, pos);
            if (inamedcontainerprovider != null) {
               player.m_5893_(inamedcontainerprovider);
            }
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.FAIL;
      }
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityMyrmexCocoon(pos, state);
   }
}
