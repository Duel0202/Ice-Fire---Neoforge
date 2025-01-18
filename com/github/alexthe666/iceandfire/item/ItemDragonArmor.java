package com.github.alexthe666.iceandfire.item;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ItemDragonArmor extends Item {
   public ItemDragonArmor.DragonArmorType type;
   public int dragonSlot;
   public String name;
   private Pattern baseName = Pattern.compile("[a-z]+_[a-z]+");

   public ItemDragonArmor(ItemDragonArmor.DragonArmorType type, int dragonSlot) {
      super((new Properties()).m_41487_(1));
      this.type = type;
      this.dragonSlot = dragonSlot;
      if (type == ItemDragonArmor.DragonArmorType.FIRE || type == ItemDragonArmor.DragonArmorType.ICE || type == ItemDragonArmor.DragonArmorType.LIGHTNING) {
         this.baseName = Pattern.compile("[a-z]+_[a-z]+_[a-z]+");
      }

   }

   @NotNull
   public String m_5524_() {
      String fullName = ForgeRegistries.ITEMS.getKey(this).m_135815_();
      Matcher matcher = this.baseName.matcher(fullName);
      this.name = matcher.find() ? matcher.group() : fullName;
      return "item.iceandfire." + this.name;
   }

   static String getNameForSlot(int slot) {
      String var10000;
      switch(slot) {
      case 0:
         var10000 = "head";
         break;
      case 1:
         var10000 = "neck";
         break;
      case 2:
         var10000 = "body";
         break;
      case 3:
         var10000 = "tail";
         break;
      default:
         var10000 = "";
      }

      return var10000;
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      String var10000;
      switch(this.dragonSlot) {
      case 1:
         var10000 = "dragon.armor_neck";
         break;
      case 2:
         var10000 = "dragon.armor_body";
         break;
      case 3:
         var10000 = "dragon.armor_tail";
         break;
      default:
         var10000 = "dragon.armor_head";
      }

      String words = var10000;
      tooltip.add(Component.m_237115_(words).m_130940_(ChatFormatting.GRAY));
   }

   public static enum DragonArmorType {
      IRON,
      GOLD,
      DIAMOND,
      SILVER,
      FIRE,
      ICE,
      COPPER,
      LIGHTNING;

      // $FF: synthetic method
      private static ItemDragonArmor.DragonArmorType[] $values() {
         return new ItemDragonArmor.DragonArmorType[]{IRON, GOLD, DIAMOND, SILVER, FIRE, ICE, COPPER, LIGHTNING};
      }
   }
}
