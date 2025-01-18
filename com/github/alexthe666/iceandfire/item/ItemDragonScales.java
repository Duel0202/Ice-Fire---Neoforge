package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDragonScales extends Item {
   EnumDragonEgg type;

   public ItemDragonScales(EnumDragonEgg type) {
      super(new Properties());
      this.type = type;
   }

   @NotNull
   public String m_5524_() {
      return "item.iceandfire.dragonscales";
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("dragon." + this.type.toString().toLowerCase()).m_130940_(this.type.color));
   }
}
