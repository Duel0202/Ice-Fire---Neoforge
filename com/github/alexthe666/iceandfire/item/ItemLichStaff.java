package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityDreadLichSkull;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import net.minecraft.sounds.SoundEvents;
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

public class ItemLichStaff extends Item {
   public ItemLichStaff() {
      super((new Properties()).m_41503_(100));
   }

   public boolean m_6832_(@NotNull ItemStack toRepair, ItemStack repair) {
      return repair.m_41720_() == IafItemRegistry.DREAD_SHARD.get() || super.m_6832_(toRepair, repair);
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = playerIn.m_21120_(hand);
      if (!worldIn.f_46443_) {
         playerIn.m_6672_(hand);
         playerIn.m_6674_(hand);
         double d2 = playerIn.m_20154_().f_82479_;
         double d3 = playerIn.m_20154_().f_82480_;
         double d4 = playerIn.m_20154_().f_82481_;
         float inaccuracy = 1.0F;
         d2 += playerIn.m_217043_().m_188583_() * 0.007499999832361937D * (double)inaccuracy;
         d3 += playerIn.m_217043_().m_188583_() * 0.007499999832361937D * (double)inaccuracy;
         d4 += playerIn.m_217043_().m_188583_() * 0.007499999832361937D * (double)inaccuracy;
         EntityDreadLichSkull charge = new EntityDreadLichSkull((EntityType)IafEntityRegistry.DREAD_LICH_SKULL.get(), worldIn, playerIn, 6.0D);
         charge.m_6686_((double)playerIn.m_146909_(), (double)playerIn.m_146908_(), 0.0D, 7.0F, 1.0F);
         charge.m_6034_(playerIn.m_20185_(), playerIn.m_20186_() + 1.0D, playerIn.m_20189_());
         worldIn.m_7967_(charge);
         charge.m_6686_(d2, d3, d4, 1.0F, 1.0F);
         playerIn.m_5496_(SoundEvents.f_12609_, 1.0F, 0.75F + 0.5F * playerIn.m_217043_().m_188501_());
         itemStackIn.m_41622_(1, playerIn, (player) -> {
            player.m_21190_(hand);
         });
         playerIn.m_36335_().m_41524_(this, 4);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
   }
}
