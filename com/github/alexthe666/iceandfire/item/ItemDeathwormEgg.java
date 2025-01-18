package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityDeathWormEgg;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDeathwormEgg extends Item {
   private final boolean gigantic;

   public ItemDeathwormEgg(boolean gigantic) {
      super((new Properties()).m_41487_(1));
      this.gigantic = gigantic;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      if (!playerIn.m_7500_()) {
         itemstack.m_41774_(1);
      }

      worldIn.m_6263_((Player)null, playerIn.m_20185_(), playerIn.m_20186_(), playerIn.m_20189_(), SoundEvents.f_11877_, SoundSource.PLAYERS, 0.5F, 0.4F / (worldIn.f_46441_.m_188501_() * 0.4F + 0.8F));
      if (!worldIn.f_46443_) {
         EntityDeathWormEgg entityegg = new EntityDeathWormEgg((EntityType)IafEntityRegistry.DEATH_WORM_EGG.get(), playerIn, worldIn, this.gigantic);
         entityegg.m_37251_(playerIn, playerIn.m_146909_(), playerIn.m_146908_(), 0.0F, 1.5F, 1.0F);
         worldIn.m_7967_(entityegg);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }
}
