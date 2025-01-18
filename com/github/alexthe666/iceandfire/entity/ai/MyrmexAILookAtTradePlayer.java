package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.player.Player;

public class MyrmexAILookAtTradePlayer extends LookAtPlayerGoal {
   private final EntityMyrmexBase myrmex;

   public MyrmexAILookAtTradePlayer(EntityMyrmexBase myrmex) {
      super(myrmex, Player.class, 8.0F);
      this.myrmex = myrmex;
   }

   public boolean m_8036_() {
      if (this.myrmex.hasCustomer() && this.myrmex.getHive() != null && !this.myrmex.getHive().isPlayerReputationTooLowToTrade(this.myrmex.m_7962_().m_20148_())) {
         this.f_25513_ = this.myrmex.m_7962_();
         return true;
      } else {
         return false;
      }
   }
}
