package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;

public class MyrmexQueenAIWander extends MyrmexAIWander {
   public MyrmexQueenAIWander(EntityMyrmexBase myrmex, double speed) {
      super(myrmex, speed);
   }

   public boolean m_8036_() {
      return (this.myrmex.canSeeSky() || this.myrmex.getHive() == null) && super.m_8036_();
   }
}
