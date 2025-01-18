package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforgeBrick;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class BlockDragonforgeBricks extends BaseEntityBlock implements IDragonProof {
   public static final BooleanProperty GRILL = BooleanProperty.m_61465_("grill");
   private final int isFire;

   public BlockDragonforgeBricks(int isFire) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60988_().m_60913_(40.0F, 500.0F).m_60918_(SoundType.f_56743_));
      this.isFire = isFire;
      this.m_49959_((BlockState)((BlockState)this.m_49965_().m_61090_()).m_61124_(GRILL, Boolean.FALSE));
   }

   static String name(int dragonType) {
      return "dragonforge_%s_brick".formatted(new Object[]{DragonType.getNameFromInt(dragonType)});
   }

   @NotNull
   public PushReaction getPistonPushReaction(@NotNull BlockState state) {
      return PushReaction.BLOCK;
   }

   @NotNull
   public InteractionResult m_6227_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, BlockHitResult resultIn) {
      if (this.getConnectedTileEntity(worldIn, resultIn.m_82425_()) != null) {
         TileEntityDragonforge forge = this.getConnectedTileEntity(worldIn, resultIn.m_82425_());
         if (forge != null && forge.fireType == this.isFire) {
            if (worldIn.f_46443_) {
               IceAndFire.PROXY.setRefrencedTE(worldIn.m_7702_(forge.m_58899_()));
            } else {
               MenuProvider inamedcontainerprovider = this.m_7246_(forge.m_58900_(), worldIn, forge.m_58899_());
               if (inamedcontainerprovider != null) {
                  player.m_5893_(inamedcontainerprovider);
               }
            }

            return InteractionResult.SUCCESS;
         }
      }

      return InteractionResult.FAIL;
   }

   private TileEntityDragonforge getConnectedTileEntity(Level worldIn, BlockPos pos) {
      Direction[] var3 = Direction.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Direction facing = var3[var5];
         if (worldIn.m_7702_(pos.m_121945_(facing)) != null && worldIn.m_7702_(pos.m_121945_(facing)) instanceof TileEntityDragonforge) {
            TileEntityDragonforge forge = (TileEntityDragonforge)worldIn.m_7702_(pos.m_121945_(facing));
            if (forge != null && forge.assembled()) {
               return forge;
            }
         }
      }

      return null;
   }

   protected void m_7926_(Builder<Block, BlockState> builder) {
      builder.m_61104_(new Property[]{GRILL});
   }

   @NotNull
   public RenderShape m_7514_(@NotNull BlockState state) {
      return RenderShape.MODEL;
   }

   @Nullable
   public BlockEntity m_142194_(@NotNull BlockPos pos, @NotNull BlockState state) {
      return new TileEntityDragonforgeBrick(pos, state);
   }
}
