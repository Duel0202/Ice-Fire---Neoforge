package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import java.util.Random;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
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
import net.neoforge.client.extensions.common.IClientBlockExtensions;
import org.jetbrains.annotations.NotNull;

public class BlockPixieHouse extends BaseEntityBlock {
   public static final DirectionProperty FACING;

   public BlockPixieHouse() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_278183_().m_60955_().m_60988_().m_60913_(2.0F, 5.0F).m_60977_());
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(FACING, Direction.NORTH));
   }

   static String name(String type) {
      return "pixie_house_%s".formatted(new Object[]{type});
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{FACING});
   }

   public BlockState m_5573_(BlockPlaceContext context) {
      return (BlockState)this.m_49966_().m_61124_(FACING, context.m_8125_().m_122424_());
   }

   public void m_6810_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
      this.dropPixie(worldIn, pos);
      m_49840_(worldIn, pos, new ItemStack(this, 0));
      super.m_6810_(state, worldIn, pos, newState, isMoving);
   }

   public void updateTick(Level worldIn, BlockPos pos, BlockState state, Random rand) {
      this.checkFall(worldIn, pos);
   }

   private boolean checkFall(Level worldIn, BlockPos pos) {
      if (!this.canPlaceBlockAt(worldIn, pos)) {
         worldIn.m_46961_(pos, true);
         this.dropPixie(worldIn, pos);
         return false;
      } else {
         return true;
      }
   }

   public void initializeClient(@NotNull Consumer<IClientBlockExtensions> consumer) {
      super.initializeClient(consumer);
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   private boolean canPlaceBlockAt(Level worldIn, BlockPos pos) {
      return true;
   }

   public void dropPixie(Level world, BlockPos pos) {
      if (world.m_7702_(pos) != null && world.m_7702_(pos) instanceof TileEntityPixieHouse && ((TileEntityPixieHouse)world.m_7702_(pos)).hasPixie) {
         ((TileEntityPixieHouse)world.m_7702_(pos)).releasePixie();
      }

   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
      return level.f_46443_ ? m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.PIXIE_HOUSE.get(), TileEntityPixieHouse::tickClient) : m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.PIXIE_HOUSE.get(), TileEntityPixieHouse::tickServer);
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityPixieHouse(pos, state);
   }

   static {
      FACING = DirectionProperty.m_61546_("facing", Plane.HORIZONTAL);
   }
}
