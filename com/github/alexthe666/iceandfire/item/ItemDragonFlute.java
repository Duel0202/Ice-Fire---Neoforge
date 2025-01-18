package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.util.IDragonFlute;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class ItemDragonFlute extends Item {
   public ItemDragonFlute() {
      super((new Properties()).m_41487_(1));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player player, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = player.m_21120_(hand);
      player.m_36335_().m_41524_(this, 60);
      float chunksize = (float)(16 * IafConfig.dragonFluteDistance);
      List<Entity> list = worldIn.m_45933_(player, (new AABB(player.m_20185_(), player.m_20186_(), player.m_20189_(), player.m_20185_() + 1.0D, player.m_20186_() + 1.0D, player.m_20189_() + 1.0D)).m_82377_((double)chunksize, 256.0D, (double)chunksize));
      Collections.sort(list, new ItemDragonFlute.Sorter(player));
      List<IDragonFlute> dragons = new ArrayList();
      Iterator itr_entities = list.iterator();

      while(itr_entities.hasNext()) {
         Entity entity = (Entity)itr_entities.next();
         if (entity instanceof IDragonFlute) {
            dragons.add((IDragonFlute)entity);
         }
      }

      Iterator itr_dragons = dragons.iterator();

      while(itr_dragons.hasNext()) {
         IDragonFlute dragon = (IDragonFlute)itr_dragons.next();
         dragon.onHearFlute(player);
      }

      worldIn.m_5594_(player, player.m_20183_(), IafSoundRegistry.DRAGONFLUTE, SoundSource.NEUTRAL, 1.0F, 1.75F);
      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
   }

   public static class Sorter implements Comparator<Entity> {
      private final Entity theEntity;

      public Sorter(Entity theEntityIn) {
         this.theEntity = theEntityIn;
      }

      public int compare(Entity p_compare_1_, Entity p_compare_2_) {
         double d0 = this.theEntity.m_20280_(p_compare_1_);
         double d1 = this.theEntity.m_20280_(p_compare_2_);
         return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
      }
   }
}
