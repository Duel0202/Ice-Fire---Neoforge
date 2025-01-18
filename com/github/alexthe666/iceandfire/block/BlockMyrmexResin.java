package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockMyrmexResin extends Block {
   private final boolean sticky;

   public BlockMyrmexResin(boolean sticky) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283744_).m_60978_(2.5F).m_60918_(sticky ? SoundType.f_56750_ : SoundType.f_56739_));
      this.sticky = sticky;
   }

   static String name(boolean sticky, String suffix) {
      return sticky ? "myrmex_resin_sticky_%s".formatted(new Object[]{suffix}) : "myrmex_resin_%s".formatted(new Object[]{suffix});
   }

   /** @deprecated */
   @Deprecated
   public boolean canEntitySpawn(BlockState state, BlockGetter worldIn, BlockPos pos, EntityType<?> type) {
      return false;
   }

   public void m_7892_(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Entity entity) {
      if (this.sticky && !(entity instanceof EntityMyrmexBase)) {
         entity.m_20256_(entity.m_20184_().m_82542_(0.4D, 0.4D, 0.4D));
      }

   }
}
