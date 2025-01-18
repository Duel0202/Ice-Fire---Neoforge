package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockDragonScales extends Block implements IDragonProof {
   EnumDragonEgg type;

   public BlockDragonScales(EnumDragonEgg type) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_280658_(NoteBlockInstrument.BASEDRUM).m_60988_().m_60913_(30.0F, 500.0F).m_60918_(SoundType.f_56742_).m_60999_());
      this.type = type;
   }

   public void m_5871_(@NotNull ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("dragon." + this.type.toString().toLowerCase()).m_130940_(this.type.color));
   }
}
