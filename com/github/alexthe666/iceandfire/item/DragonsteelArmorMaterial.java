package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IafConfig;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;

public class DragonsteelArmorMaterial extends IafArmorMaterial {
   public DragonsteelArmorMaterial(String name, int durability, int[] damageReduction, int encantability, SoundEvent sound, float toughness) {
      super(name, durability, damageReduction, encantability, sound, toughness);
   }

   public int m_7366_(Type slotIn) {
      int[] damageReduction = new int[]{IafConfig.dragonsteelBaseArmor - 6, IafConfig.dragonsteelBaseArmor - 3, IafConfig.dragonsteelBaseArmor, IafConfig.dragonsteelBaseArmor - 5};
      return damageReduction[slotIn.m_266308_().m_20749_()];
   }

   public float m_6649_() {
      return 0.0F;
   }

   public float m_6651_() {
      return IafConfig.dragonsteelBaseArmorToughness;
   }

   public int m_266425_(Type slotIn) {
      return (int)((double)MAX_DAMAGE_ARRAY[slotIn.m_266308_().m_20749_()] * 0.02D * (double)IafConfig.dragonsteelBaseDurabilityEquipment);
   }
}
