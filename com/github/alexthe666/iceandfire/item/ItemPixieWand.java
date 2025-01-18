package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityPixieCharge;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemPixieWand extends Item {
   public ItemPixieWand() {
      super((new Properties()).m_41487_(1).m_41503_(500));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = playerIn.m_21120_(hand);
      boolean flag = playerIn.m_7500_() || EnchantmentHelper.m_44843_(Enchantments.f_44952_, itemStackIn) > 0;
      ItemStack itemstack = this.findAmmo(playerIn);
      playerIn.m_6672_(hand);
      playerIn.m_6674_(hand);
      if (!itemstack.m_41619_() || flag) {
         boolean flag1 = playerIn.m_7500_() || this.isInfinite(itemstack, itemStackIn, playerIn);
         if (!flag1) {
            itemstack.m_41774_(1);
            if (itemstack.m_41619_()) {
               playerIn.m_150109_().m_36057_(itemstack);
            }
         }

         double d2 = playerIn.m_20154_().f_82479_;
         double d3 = playerIn.m_20154_().f_82480_;
         double d4 = playerIn.m_20154_().f_82481_;
         float inaccuracy = 1.0F;
         d2 += playerIn.m_217043_().m_188583_() * 0.007499999832361937D * (double)inaccuracy;
         d3 += playerIn.m_217043_().m_188583_() * 0.007499999832361937D * (double)inaccuracy;
         d4 += playerIn.m_217043_().m_188583_() * 0.007499999832361937D * (double)inaccuracy;
         EntityPixieCharge charge = new EntityPixieCharge((EntityType)IafEntityRegistry.PIXIE_CHARGE.get(), worldIn, playerIn, d2, d3, d4);
         charge.m_6034_(playerIn.m_20185_(), playerIn.m_20186_() + 1.0D, playerIn.m_20189_());
         if (!worldIn.f_46443_) {
            worldIn.m_7967_(charge);
         }

         playerIn.m_5496_(IafSoundRegistry.PIXIE_WAND, 1.0F, 0.75F + 0.5F * playerIn.m_217043_().m_188501_());
         itemstack.m_41622_(1, playerIn, (player) -> {
            player.m_21190_(playerIn.m_7655_());
         });
         playerIn.m_36335_().m_41524_(this, 5);
      }

      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public boolean isInfinite(ItemStack stack, ItemStack bow, Player player) {
      int enchant = EnchantmentHelper.m_44843_(Enchantments.f_44952_, bow);
      return enchant > 0 && stack.m_41720_() == IafItemRegistry.PIXIE_DUST.get();
   }

   private ItemStack findAmmo(Player player) {
      if (this.isAmmo(player.m_21120_(InteractionHand.OFF_HAND))) {
         return player.m_21120_(InteractionHand.OFF_HAND);
      } else if (this.isAmmo(player.m_21120_(InteractionHand.MAIN_HAND))) {
         return player.m_21120_(InteractionHand.MAIN_HAND);
      } else {
         for(int i = 0; i < player.m_150109_().m_6643_(); ++i) {
            ItemStack itemstack = player.m_150109_().m_8020_(i);
            if (this.isAmmo(itemstack)) {
               return itemstack;
            }
         }

         return ItemStack.f_41583_;
      }
   }

   protected boolean isAmmo(ItemStack stack) {
      return !stack.m_41619_() && stack.m_41720_() == IafItemRegistry.PIXIE_DUST.get();
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.pixie_wand.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.pixie_wand.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
