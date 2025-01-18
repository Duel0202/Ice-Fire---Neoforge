package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ArmorItem.Type;

public class IafArmorMaterial extends CustomArmorMaterial {
   protected static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};
   private final int maxDamageFactor;

   public IafArmorMaterial(String name, int durability, int[] damageReduction, int encantability, SoundEvent sound, float toughness) {
      super(name, durability, damageReduction, encantability, sound, toughness, 0.0F);
      this.maxDamageFactor = durability;
   }

   public int m_266425_(Type slotIn) {
      return MAX_DAMAGE_ARRAY[slotIn.m_266308_().m_20749_()] * this.maxDamageFactor;
   }

   public float m_6649_() {
      return 0.0F;
   }
}
