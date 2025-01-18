package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityDragonArrow;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDragonArrow extends ArrowItem {
   public ItemDragonArrow() {
      super(new Properties());
   }

   @NotNull
   public AbstractArrow m_6394_(@NotNull Level level, @NotNull ItemStack arrow, @NotNull LivingEntity shooter) {
      return new EntityDragonArrow((EntityType)IafEntityRegistry.DRAGON_ARROW.get(), shooter, level);
   }

   public boolean isInfinite(@NotNull ItemStack arrow, @NotNull ItemStack bow, @NotNull Player player) {
      boolean isInfinite = super.isInfinite(arrow, bow, player);
      if (!isInfinite) {
         isInfinite = bow.getEnchantmentLevel(Enchantments.f_44952_) > 0 && this.getClass() == ItemDragonArrow.class;
      }

      return isInfinite;
   }
}
