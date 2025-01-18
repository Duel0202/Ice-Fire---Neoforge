package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.block.BlockSeaSerpentScales;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.item.IafArmorMaterial;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemSeaSerpentArmor;
import com.github.alexthe666.iceandfire.item.ItemSeaSerpentScales;
import java.util.Locale;
import net.minecraft.ChatFormatting;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.level.block.Block;
import net.neoforge.registries.RegistryObject;

public enum EnumSeaSerpent {
   BLUE(ChatFormatting.BLUE),
   BRONZE(ChatFormatting.GOLD),
   DEEPBLUE(ChatFormatting.DARK_BLUE),
   GREEN(ChatFormatting.DARK_GREEN),
   PURPLE(ChatFormatting.DARK_PURPLE),
   RED(ChatFormatting.DARK_RED),
   TEAL(ChatFormatting.AQUA);

   public String resourceName;
   public ChatFormatting color;
   public CustomArmorMaterial armorMaterial;
   public RegistryObject<Item> scale;
   public RegistryObject<Item> helmet;
   public RegistryObject<Item> chestplate;
   public RegistryObject<Item> leggings;
   public RegistryObject<Item> boots;
   public RegistryObject<Block> scaleBlock;

   private EnumSeaSerpent(ChatFormatting color) {
      this.resourceName = this.name().toLowerCase(Locale.ROOT);
      this.color = color;
   }

   public static void initArmors() {
      EnumSeaSerpent[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumSeaSerpent color = var0[var2];
         color.armorMaterial = new IafArmorMaterial("iceandfire:sea_serpent_scales_" + color.resourceName, 30, new int[]{4, 8, 7, 4}, 25, SoundEvents.f_11676_, 2.5F);
         color.scaleBlock = IafBlockRegistry.register("sea_serpent_scale_block_" + color.resourceName, () -> {
            return new BlockSeaSerpentScales(color.resourceName, color.color);
         });
         color.scale = IafItemRegistry.registerItem("sea_serpent_scales_" + color.resourceName, () -> {
            return new ItemSeaSerpentScales(color.resourceName, color.color);
         });
         color.helmet = IafItemRegistry.registerItem("tide_" + color.resourceName + "_helmet", () -> {
            return new ItemSeaSerpentArmor(color, color.armorMaterial, Type.HELMET);
         });
         color.chestplate = IafItemRegistry.registerItem("tide_" + color.resourceName + "_chestplate", () -> {
            return new ItemSeaSerpentArmor(color, color.armorMaterial, Type.CHESTPLATE);
         });
         color.leggings = IafItemRegistry.registerItem("tide_" + color.resourceName + "_leggings", () -> {
            return new ItemSeaSerpentArmor(color, color.armorMaterial, Type.LEGGINGS);
         });
         color.boots = IafItemRegistry.registerItem("tide_" + color.resourceName + "_boots", () -> {
            return new ItemSeaSerpentArmor(color, color.armorMaterial, Type.BOOTS);
         });
      }

   }

   // $FF: synthetic method
   private static EnumSeaSerpent[] $values() {
      return new EnumSeaSerpent[]{BLUE, BRONZE, DEEPBLUE, GREEN, PURPLE, RED, TEAL};
   }
}
