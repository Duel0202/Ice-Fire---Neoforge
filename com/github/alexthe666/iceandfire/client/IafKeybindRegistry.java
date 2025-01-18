package com.github.alexthe666.iceandfire.client;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.ArrayUtils;

public class IafKeybindRegistry {
   public static KeyMapping dragon_fireAttack;
   public static KeyMapping dragon_strike;
   public static KeyMapping dragon_down;
   public static KeyMapping dragon_change_view;

   public static void init() {
      if (Minecraft.m_91087_() != null) {
         dragon_fireAttack = new KeyMapping("key.dragon_fireAttack", 82, "key.categories.gameplay");
         dragon_strike = new KeyMapping("key.dragon_strike", 71, "key.categories.gameplay");
         dragon_down = new KeyMapping("key.dragon_down", 88, "key.categories.gameplay");
         dragon_change_view = new KeyMapping("key.dragon_change_view", 296, "key.categories.misc");
         Minecraft.m_91087_().f_91066_.f_92059_ = (KeyMapping[])ArrayUtils.add(Minecraft.m_91087_().f_91066_.f_92059_, dragon_fireAttack);
         Minecraft.m_91087_().f_91066_.f_92059_ = (KeyMapping[])ArrayUtils.add(Minecraft.m_91087_().f_91066_.f_92059_, dragon_strike);
         Minecraft.m_91087_().f_91066_.f_92059_ = (KeyMapping[])ArrayUtils.add(Minecraft.m_91087_().f_91066_.f_92059_, dragon_down);
         Minecraft.m_91087_().f_91066_.f_92059_ = (KeyMapping[])ArrayUtils.add(Minecraft.m_91087_().f_91066_.f_92059_, dragon_change_view);
      }
   }
}
