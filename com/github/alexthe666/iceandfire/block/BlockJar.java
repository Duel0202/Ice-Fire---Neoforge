package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class BlockJar extends BaseEntityBlock {
   protected static final VoxelShape AABB = Block.m_49796_(3.0D, 0.0D, 3.0D, 13.0D, 16.0D, 13.0D);
   public Item itemBlock;
   private final boolean empty;
   private final int pixieType;

   public BlockJar(int pixieType) {
      super(pixieType != -1 ? Properties.m_284310_().m_284180_(MapColor.f_283808_).m_280658_(NoteBlockInstrument.HAT).m_60955_().m_60988_().m_60913_(1.0F, 2.0F).m_60918_(SoundType.f_56744_).m_60953_((state) -> {
         return pixieType == -1 ? 0 : 10;
      }).m_60916_((Block)IafBlockRegistry.JAR_EMPTY.get()) : Properties.m_284310_().m_284180_(MapColor.f_283808_).m_280658_(NoteBlockInstrument.HAT).m_60955_().m_60988_().m_60913_(1.0F, 2.0F).m_60918_(SoundType.f_56744_));
      this.empty = pixieType == -1;
      this.pixieType = pixieType;
   }

   static String name(int pixieType) {
      return pixieType == -1 ? "pixie_jar_empty" : "pixie_jar_%d".formatted(new Object[]{pixieType});
   }

   @NotNull
   public VoxelShape m_5940_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return AABB;
   }

   @NotNull
   public VoxelShape m_5939_(@NotNull BlockState state, @NotNull BlockGetter worldIn, @NotNull BlockPos pos, @NotNull CollisionContext context) {
      return AABB;
   }

   public void m_6810_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
      this.dropPixie(worldIn, pos);
      super.m_6810_(state, worldIn, pos, newState, isMoving);
   }

   public void dropPixie(Level world, BlockPos pos) {
      if (world.m_7702_(pos) != null && world.m_7702_(pos) instanceof TileEntityJar && ((TileEntityJar)world.m_7702_(pos)).hasPixie) {
         ((TileEntityJar)world.m_7702_(pos)).releasePixie();
      }

   }

   @NotNull
   public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult resultIn) {
      if (!this.empty && world.m_7702_(pos) != null && world.m_7702_(pos) instanceof TileEntityJar && ((TileEntityJar)world.m_7702_(pos)).hasPixie && ((TileEntityJar)world.m_7702_(pos)).hasProduced) {
         ((TileEntityJar)world.m_7702_(pos)).hasProduced = false;
         ItemEntity item = new ItemEntity(world, (double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.5D, (double)pos.m_123343_() + 0.5D, new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get()));
         if (!world.f_46443_) {
            world.m_7967_(item);
         }

         world.m_7785_((double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.5D, (double)pos.m_123343_() + 0.5D, IafSoundRegistry.PIXIE_HURT, SoundSource.NEUTRAL, 1.0F, 1.0F, false);
         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   public void m_6402_(Level world, @NotNull BlockPos pos, @NotNull BlockState state, LivingEntity placer, @NotNull ItemStack stack) {
      if (world.m_7702_(pos) instanceof TileEntityJar) {
         TileEntityJar jar = (TileEntityJar)world.m_7702_(pos);
         if (!this.empty) {
            jar.hasPixie = true;
            jar.pixieType = this.pixieType;
         } else {
            jar.hasPixie = false;
         }

         jar.m_6596_();
      }

   }

   @Nullable
   public <T extends BlockEntity> BlockEntityTicker<T> m_142354_(Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> entityType) {
      return m_152132_(entityType, (BlockEntityType)IafTileEntityRegistry.PIXIE_JAR.get(), TileEntityJar::tick);
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityJar(pos, state, this.empty);
   }
}
