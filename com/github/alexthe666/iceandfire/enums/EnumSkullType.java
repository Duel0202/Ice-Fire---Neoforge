package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemMobSkull;
import java.util.Locale;
import net.minecraft.world.item.Item;
import net.neoforge.registries.RegistryObject;

public enum EnumSkullType {
   HIPPOGRYPH,
   CYCLOPS,
   COCKATRICE,
   STYMPHALIAN,
   TROLL,
   AMPHITHERE,
   SEASERPENT,
   HYDRA;

   public String itemResourceName;
   public RegistryObject<Item> skull_item;

   private EnumSkullType() {
      String var10001 = this.name();
      this.itemResourceName = var10001.toLowerCase(Locale.ROOT) + "_skull";
   }

   public static void initItems() {
      EnumSkullType[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         EnumSkullType skull = var0[var2];
         skull.skull_item = IafItemRegistry.registerItem(skull.itemResourceName, () -> {
            return new ItemMobSkull(skull);
         });
      }

   }

   // $FF: synthetic method
   private static EnumSkullType[] $values() {
      return new EnumSkullType[]{HIPPOGRYPH, CYCLOPS, COCKATRICE, STYMPHALIAN, TROLL, AMPHITHERE, SEASERPENT, HYDRA};
   }
}
