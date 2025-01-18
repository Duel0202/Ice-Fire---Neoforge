package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.item.IafArmorMaterial;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemScaleArmor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.neoforge.registries.RegistryObject;

public enum EnumDragonArmor {
   armor_red(12, EnumDragonEgg.RED),
   armor_bronze(13, EnumDragonEgg.BRONZE),
   armor_green(14, EnumDragonEgg.GREEN),
   armor_gray(15, EnumDragonEgg.GRAY),
   armor_blue(12, EnumDragonEgg.BLUE),
   armor_white(13, EnumDragonEgg.WHITE),
   armor_sapphire(14, EnumDragonEgg.SAPPHIRE),
   armor_silver(15, EnumDragonEgg.SILVER),
   armor_electric(12, EnumDragonEgg.ELECTRIC),
   armor_amythest(13, EnumDragonEgg.AMYTHEST),
   armor_copper(14, EnumDragonEgg.COPPER),
   armor_black(15, EnumDragonEgg.BLACK);

   public CustomArmorMaterial material;
   public int armorId;
   public EnumDragonEgg eggType;
   public RegistryObject<Item> helmet;
   public RegistryObject<Item> chestplate;
   public RegistryObject<Item> leggings;
   public RegistryObject<Item> boots;
   public CustomArmorMaterial armorMaterial;

   private EnumDragonArmor(int armorId, EnumDragonEgg eggType) {
      this.armorId = armorId;
      this.eggType = eggType;
   }

   public static void initArmors() {
      for(int i = 0; i < values().length; ++i) {
         values()[i].armorMaterial = new IafArmorMaterial("iceandfire:armor_dragon_scales" + (i + 1), 36, new int[]{5, 7, 9, 5}, 15, SoundEvents.f_11672_, 2.0F);
         String sub = values()[i].name();
         values()[i].helmet = IafItemRegistry.registerItem(sub + "_helmet", () -> {
            return new ItemScaleArmor(values()[i].eggType, values()[i], values()[i].armorMaterial, Type.HELMET);
         });
         values()[i].chestplate = IafItemRegistry.registerItem(sub + "_chestplate", () -> {
            return new ItemScaleArmor(values()[i].eggType, values()[i], values()[i].armorMaterial, Type.CHESTPLATE);
         });
         values()[i].leggings = IafItemRegistry.registerItem(sub + "_leggings", () -> {
            return new ItemScaleArmor(values()[i].eggType, values()[i], values()[i].armorMaterial, Type.LEGGINGS);
         });
         values()[i].boots = IafItemRegistry.registerItem(sub + "_boots", () -> {
            return new ItemScaleArmor(values()[i].eggType, values()[i], values()[i].armorMaterial, Type.BOOTS);
         });
      }

   }

   public static Item getScaleItem(EnumDragonArmor armor) {
      Item var10000;
      switch(armor) {
      case armor_red:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_RED.get();
         break;
      case armor_bronze:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_BRONZE.get();
         break;
      case armor_green:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_GREEN.get();
         break;
      case armor_gray:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_GRAY.get();
         break;
      case armor_blue:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_BLUE.get();
         break;
      case armor_white:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_WHITE.get();
         break;
      case armor_sapphire:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_SAPPHIRE.get();
         break;
      case armor_silver:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_SILVER.get();
         break;
      case armor_electric:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_ELECTRIC.get();
         break;
      case armor_amythest:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_AMYTHEST.get();
         break;
      case armor_copper:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_COPPER.get();
         break;
      case armor_black:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_BLACK.get();
         break;
      default:
         var10000 = (Item)IafItemRegistry.DRAGONSCALES_RED.get();
      }

      return var10000;
   }

   // $FF: synthetic method
   private static EnumDragonArmor[] $values() {
      return new EnumDragonArmor[]{armor_red, armor_bronze, armor_green, armor_gray, armor_blue, armor_white, armor_sapphire, armor_silver, armor_electric, armor_amythest, armor_copper, armor_black};
   }
}
