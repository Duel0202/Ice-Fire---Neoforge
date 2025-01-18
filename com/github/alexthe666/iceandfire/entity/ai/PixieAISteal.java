package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class PixieAISteal extends Goal {
   private final EntityPixie temptedEntity;
   private Player temptingPlayer;
   private int delayTemptCounter = 0;
   private boolean isRunning;

   public PixieAISteal(EntityPixie temptedEntityIn, double speedIn) {
      this.temptedEntity = temptedEntityIn;
   }

   public boolean m_8036_() {
      if (IafConfig.pixiesStealItems && this.temptedEntity.m_21205_().m_41619_() && this.temptedEntity.stealCooldown <= 0) {
         if (this.temptedEntity.m_217043_().m_188503_(200) == 0) {
            return false;
         } else if (this.temptedEntity.m_21824_()) {
            return false;
         } else if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return false;
         } else {
            this.temptingPlayer = this.temptedEntity.m_9236_().m_45930_(this.temptedEntity, 10.0D);
            return this.temptingPlayer != null && this.temptedEntity.m_21120_(InteractionHand.MAIN_HAND).m_41619_() && !this.temptingPlayer.m_150109_().m_7983_() && !this.temptingPlayer.m_7500_();
         }
      } else {
         return false;
      }
   }

   public boolean m_8045_() {
      return !this.temptedEntity.m_21824_() && this.temptedEntity.m_21205_().m_41619_() && this.delayTemptCounter == 0 && this.temptedEntity.stealCooldown == 0;
   }

   public void m_8056_() {
      this.isRunning = true;
   }

   public void m_8041_() {
      this.temptingPlayer = null;
      if (this.delayTemptCounter < 10) {
         this.delayTemptCounter += 10;
      }

      this.isRunning = false;
   }

   public void m_8037_() {
      this.temptedEntity.m_21563_().m_24960_(this.temptingPlayer, (float)(this.temptedEntity.m_8085_() + 20), (float)this.temptedEntity.m_8132_());
      ArrayList<Integer> slotlist = new ArrayList();
      if (this.temptedEntity.m_20280_(this.temptingPlayer) < 3.0D && !this.temptingPlayer.m_150109_().m_7983_()) {
         int slot;
         ItemStack randomItem;
         for(slot = 0; slot < this.temptingPlayer.m_150109_().m_6643_(); ++slot) {
            randomItem = this.temptingPlayer.m_150109_().m_8020_(slot);
            if (!Inventory.m_36045_(slot) && !randomItem.m_41619_() && randomItem.m_41753_()) {
               slotlist.add(slot);
            }
         }

         if (!slotlist.isEmpty()) {
            if (slotlist.size() == 1) {
               slot = (Integer)slotlist.get(0);
            } else {
               slot = (Integer)slotlist.get(ThreadLocalRandom.current().nextInt(slotlist.size()));
            }

            randomItem = this.temptingPlayer.m_150109_().m_8020_(slot);
            this.temptedEntity.m_21008_(InteractionHand.MAIN_HAND, randomItem);
            this.temptingPlayer.m_150109_().m_8016_(slot);
            this.temptedEntity.flipAI(true);
            this.temptedEntity.m_5496_(IafSoundRegistry.PIXIE_TAUNT, 1.0F, 1.0F);

            EntityPixie pixie;
            for(Iterator var4 = this.temptingPlayer.m_9236_().m_45976_(EntityPixie.class, this.temptedEntity.m_20191_().m_82400_(40.0D)).iterator(); var4.hasNext(); pixie.stealCooldown = 1000 + pixie.m_217043_().m_188503_(3000)) {
               pixie = (EntityPixie)var4.next();
            }

            if (this.temptingPlayer != null) {
               this.temptingPlayer.m_7292_(new MobEffectInstance(this.temptedEntity.negativePotions[this.temptedEntity.getColor()], 100));
            }
         } else {
            this.temptedEntity.flipAI(true);
            this.delayTemptCounter = 200;
         }
      } else {
         this.temptedEntity.m_21566_().m_6849_(this.temptingPlayer.m_20185_(), this.temptingPlayer.m_20186_() + 1.5D, this.temptingPlayer.m_20189_(), 1.0D);
      }

   }

   public boolean isRunning() {
      return this.isRunning;
   }
}
