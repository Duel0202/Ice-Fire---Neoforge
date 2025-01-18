package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import java.util.function.Predicate;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import org.jetbrains.annotations.NotNull;

public class ItemDragonBow extends BowItem {
   private static final Predicate<ItemStack> DRAGON_ARROWS = (stack) -> {
      return stack.m_204117_(IafItemTags.DRAGON_ARROWS);
   };

   public ItemDragonBow() {
      super((new Properties()).m_41503_(584));
   }

   @NotNull
   public Predicate<ItemStack> m_6437_() {
      return DRAGON_ARROWS;
   }
}
