package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.Goal.Flag;
import net.minecraft.world.entity.player.Player;

public class MyrmexAITradePlayer extends Goal {
   private final EntityMyrmexBase myrmex;

   public MyrmexAITradePlayer(EntityMyrmexBase myrmex) {
      this.myrmex = myrmex;
      this.m_7021_(EnumSet.of(Flag.MOVE));
   }

   public boolean m_8036_() {
      if (!this.myrmex.m_6084_()) {
         return false;
      } else if (this.myrmex.m_20069_()) {
         return false;
      } else if (!this.myrmex.m_20096_()) {
         return false;
      } else if (this.myrmex.f_19864_) {
         return false;
      } else {
         Player PlayerEntity = this.myrmex.m_7962_();
         if (PlayerEntity == null) {
            return false;
         } else if (this.myrmex.m_20280_(PlayerEntity) > 16.0D) {
            return false;
         } else if (this.myrmex.getHive() != null && !this.myrmex.getHive().isPlayerReputationTooLowToTrade(PlayerEntity.m_20148_())) {
            return false;
         } else {
            return PlayerEntity.f_36096_ != null;
         }
      }
   }

   public void m_8037_() {
      this.myrmex.m_21573_().m_26573_();
   }

   public void m_8041_() {
      this.myrmex.m_7189_((Player)null);
   }
}
