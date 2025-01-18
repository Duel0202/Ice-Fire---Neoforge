package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlockDreadWoodLock extends Block implements IDragonProof, IDreadBlock {
   public static final BooleanProperty PLAYER_PLACED = BooleanProperty.m_61465_("player_placed");

   public BlockDreadWoodLock() {
      super(Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_278183_().m_60913_(-1.0F, 1000000.0F).m_60918_(SoundType.f_56736_));
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(PLAYER_PLACED, Boolean.FALSE));
   }

   public float m_5880_(BlockState state, @NotNull Player player, @NotNull BlockGetter worldIn, @NotNull BlockPos pos) {
      if ((Boolean)state.m_61143_(PLAYER_PLACED)) {
         float f = 8.0F;
         return player.getDigSpeed(state, pos) / f / 30.0F;
      } else {
         return super.m_5880_(state, player, worldIn, pos);
      }
   }

   @NotNull
   public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult resultIn) {
      ItemStack stack = player.m_21120_(handIn);
      if (stack.m_41720_() == IafItemRegistry.DREAD_KEY.get()) {
         if (!player.m_7500_()) {
            stack.m_41774_(1);
         }

         this.deleteNearbyWood(worldIn, pos, pos);
         worldIn.m_7785_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), SoundEvents.f_12600_, SoundSource.BLOCKS, 1.0F, 1.0F, false);
         worldIn.m_7785_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), SoundEvents.f_11749_, SoundSource.BLOCKS, 1.0F, 2.0F, false);
      }

      return InteractionResult.SUCCESS;
   }

   private void deleteNearbyWood(Level worldIn, BlockPos pos, BlockPos startPos) {
      if (pos.m_123331_(startPos) < 32.0D && (worldIn.m_8055_(pos).m_60734_() == IafBlockRegistry.DREADWOOD_PLANKS.get() || worldIn.m_8055_(pos).m_60734_() == IafBlockRegistry.DREADWOOD_PLANKS_LOCK.get())) {
         worldIn.m_46961_(pos, false);
         Direction[] var4 = Direction.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Direction facing = var4[var6];
            this.deleteNearbyWood(worldIn, pos.m_121945_(facing), startPos);
         }
      }

   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{PLAYER_PLACED});
   }

   public BlockState getStateForPlacement(Level worldIn, BlockPos pos, Direction facing, float hitX, float hitY, float hitZ, int meta, LivingEntity placer) {
      return (BlockState)this.m_49966_().m_61124_(PLAYER_PLACED, true);
   }
}
