package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpentArrow;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemSeaSerpentArrow extends ArrowItem {
   public ItemSeaSerpentArrow() {
      super(new Properties());
   }

   @NotNull
   public AbstractArrow m_6394_(@NotNull Level worldIn, @NotNull ItemStack stack, @NotNull LivingEntity shooter) {
      return new EntitySeaSerpentArrow((EntityType)IafEntityRegistry.SEA_SERPENT_ARROW.get(), worldIn, shooter);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.sea_serpent_arrow.desc").m_130940_(ChatFormatting.GRAY));
   }
}
