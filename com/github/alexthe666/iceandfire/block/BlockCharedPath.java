package com.github.alexthe666.iceandfire.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirtPathBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

public class BlockCharedPath extends DirtPathBlock {
   public static final BooleanProperty REVERTS = BooleanProperty.m_61465_("revert");
   public Item itemBlock;
   public int dragonType;

   public BlockCharedPath(int dragonType) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283915_).m_278166_(PushReaction.DESTROY).m_60918_(dragonType != 1 ? SoundType.f_56739_ : SoundType.f_56744_).m_60978_(0.6F).m_60911_(dragonType != 1 ? 0.6F : 0.98F).m_60977_().m_60999_());
      this.dragonType = dragonType;
      this.m_49959_((BlockState)((BlockState)this.f_49792_.m_61090_()).m_61124_(REVERTS, Boolean.FALSE));
   }

   public static String getNameFromType(int dragonType) {
      String var10000;
      switch(dragonType) {
      case 0:
         var10000 = "chared_dirt_path";
         break;
      case 1:
         var10000 = "frozen_dirt_path";
         break;
      case 2:
         var10000 = "crackled_dirt_path";
         break;
      default:
         var10000 = "";
      }

      return var10000;
   }

   public BlockState getSmushedState(int dragonType) {
      BlockState var10000;
      switch(dragonType) {
      case 0:
         var10000 = ((Block)IafBlockRegistry.CHARRED_DIRT.get()).m_49966_();
         break;
      case 1:
         var10000 = ((Block)IafBlockRegistry.FROZEN_DIRT.get()).m_49966_();
         break;
      case 2:
         var10000 = ((Block)IafBlockRegistry.CRACKLED_DIRT.get()).m_49966_();
         break;
      default:
         var10000 = null;
      }

      return var10000;
   }

   public void m_213897_(@NotNull BlockState state, @NotNull ServerLevel worldIn, @NotNull BlockPos pos, @NotNull RandomSource rand) {
      super.m_213897_(state, worldIn, pos, rand);
      if (!worldIn.f_46443_) {
         if (!worldIn.isAreaLoaded(pos, 3)) {
            return;
         }

         if ((Boolean)state.m_61143_(REVERTS) && rand.m_188503_(3) == 0) {
            worldIn.m_46597_(pos, Blocks.f_152481_.m_49966_());
         }
      }

      if (worldIn.m_8055_(pos.m_7494_()).m_280296_()) {
         worldIn.m_46597_(pos, this.getSmushedState(this.dragonType));
      }

      this.updateBlockState(worldIn, pos);
   }

   private void updateBlockState(Level worldIn, BlockPos pos) {
      if (worldIn.m_8055_(pos.m_7494_()).m_280296_()) {
         worldIn.m_46597_(pos, this.getSmushedState(this.dragonType));
      }

   }

   public BlockState getStateFromMeta(int meta) {
      return (BlockState)this.m_49966_().m_61124_(REVERTS, meta == 1);
   }

   public int getMetaFromState(BlockState state) {
      return (Boolean)state.m_61143_(REVERTS) ? 1 : 0;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{REVERTS});
   }
}
