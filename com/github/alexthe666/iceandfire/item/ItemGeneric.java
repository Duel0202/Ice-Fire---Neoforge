package com.github.alexthe666.iceandfire.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemGeneric extends Item {
   int description = 0;

   public ItemGeneric() {
      super(new Properties());
   }

   public ItemGeneric(int textLength) {
      super(new Properties());
      this.description = textLength;
   }

   public ItemGeneric(int textLength, boolean hide) {
      super(new Properties());
      this.description = textLength;
   }

   public ItemGeneric(int textLength, int stacksize) {
      super((new Properties()).m_41487_(stacksize));
      this.description = textLength;
   }

   public boolean m_5812_(@NotNull ItemStack stack) {
      return this == IafItemRegistry.CREATIVE_DRAGON_MEAL.get() ? true : super.m_5812_(stack);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      if (this.description > 0) {
         for(int i = 0; i < this.description; ++i) {
            String var10001 = this.m_5524_();
            tooltip.add(Component.m_237115_(var10001 + ".desc_" + i).m_130940_(ChatFormatting.GRAY));
         }
      }

   }
}
