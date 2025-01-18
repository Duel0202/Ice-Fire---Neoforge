package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.google.common.primitives.Ints;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemBestiary extends Item {
   public ItemBestiary() {
      super((new Properties()).m_41487_(1));
   }

   public void m_7836_(ItemStack stack, @NotNull Level worldIn, @NotNull Player playerIn) {
      stack.m_41751_(new CompoundTag());
      stack.m_41783_().m_128385_("Pages", new int[]{0});
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
      ItemStack itemStackIn = playerIn.m_21120_(handIn);
      if (worldIn.f_46443_) {
         IceAndFire.PROXY.openBestiaryGui(itemStackIn);
      }

      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void m_6883_(ItemStack stack, @NotNull Level worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
      if (stack.m_41783_() == null) {
         stack.m_41751_(new CompoundTag());
         stack.m_41783_().m_128385_("Pages", new int[]{EnumBestiaryPages.INTRODUCTION.ordinal()});
      }

   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      if (stack.m_41783_() != null) {
         if (IceAndFire.PROXY.shouldSeeBestiaryContents()) {
            tooltip.add(Component.m_237115_("bestiary.contains").m_130940_(ChatFormatting.GRAY));
            Set<EnumBestiaryPages> pages = EnumBestiaryPages.containedPages(Ints.asList(stack.m_41783_().m_128465_("Pages")));
            Iterator var6 = pages.iterator();

            while(var6.hasNext()) {
               EnumBestiaryPages page = (EnumBestiaryPages)var6.next();
               MutableComponent var10001 = Component.m_237113_(ChatFormatting.WHITE + "-");
               String var10002 = EnumBestiaryPages.values()[page.ordinal()].toString();
               tooltip.add(var10001.m_7220_(Component.m_237115_("bestiary." + var10002.toLowerCase())).m_130940_(ChatFormatting.GRAY));
            }
         } else {
            tooltip.add(Component.m_237115_("bestiary.hold_shift").m_130940_(ChatFormatting.GRAY));
         }
      }

   }
}
