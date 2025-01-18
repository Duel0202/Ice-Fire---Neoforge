package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityLectern;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockLectern extends BaseEntityBlock {
   public static final DirectionProperty FACING;
   protected static final VoxelShape AABB;

   public BlockLectern() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_278183_().m_60955_().m_60988_().m_60913_(2.0F, 5.0F).m_60918_(SoundType.f_56736_));
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(FACING, Direction.NORTH));
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return AABB;
   }

   @NotNull
   public BlockState m_6843_(BlockState p_185499_1_, Rotation p_185499_2_) {
      return (BlockState)p_185499_1_.m_61124_(FACING, p_185499_2_.m_55954_((Direction)p_185499_1_.m_61143_(FACING)));
   }

   @NotNull
   public BlockState m_6943_(BlockState p_185471_1_, Mirror p_185471_2_) {
      return p_185471_1_.m_60717_(p_185471_2_.m_54846_((Direction)p_185471_1_.m_61143_(FACING)));
   }

   @NotNull
   public VoxelShape m_5939_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return AABB;
   }

   public void m_6810_(@NotNull BlockState state, Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
      BlockEntity tileentity = worldIn.m_7702_(pos);
      if (tileentity instanceof TileEntityLectern) {
         Containers.m_19002_(worldIn, pos, (TileEntityLectern)tileentity);
         worldIn.m_46717_(pos, this);
      }

      super.m_6810_(state, worldIn, pos, newState, isMoving);
   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level p_153182_, @NotNull BlockState p_153183_, @NotNull BlockEntityType<T> entityType) {
      return p_153182_.f_46443_ ? m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.IAF_LECTERN.get(), TileEntityLectern::bookAnimationTick) : null;
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(FACING, context.m_8125_().m_122424_());
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING});
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
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
      return new TileEntityLectern(pos, state);
   }

   static {
      FACING = DirectionProperty.m_61546_("facing", Plane.HORIZONTAL);
      AABB = Block.m_49796_(4.0D, 0.0D, 4.0D, 12.0D, 19.0D, 12.0D);
   }
}
