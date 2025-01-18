package com.github.alexthe666.iceandfire.block;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

public class BlockSeaSerpentScales extends Block {
   ChatFormatting color;
   String name;

   public BlockSeaSerpentScales(String name, ChatFormatting color) {
      super(Properties.m_284310_().m_284180_(MapColor.f_283947_).m_60913_(30.0F, 500.0F).m_60918_(SoundType.f_56742_).m_60999_());
      this.color = color;
      this.name = name;
   }

   public void m_5871_(@NotNull ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("sea_serpent." + this.name).m_130940_(this.color));
   }
}
