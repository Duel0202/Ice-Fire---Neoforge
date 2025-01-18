package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexSwarmer;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemMyrmexSwarm extends Item {
   private final boolean jungle;

   public ItemMyrmexSwarm(boolean jungle) {
      super((new Properties()).m_41487_(1));
      this.jungle = jungle;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = playerIn.m_21120_(hand);
      playerIn.m_6672_(hand);
      playerIn.m_6674_(hand);
      if (!playerIn.m_7500_()) {
         itemStackIn.m_41774_(1);
         playerIn.m_36335_().m_41524_(this, 20);
      }

      for(int i = 0; i < 5; ++i) {
         EntityMyrmexSwarmer myrmex = new EntityMyrmexSwarmer((EntityType)IafEntityRegistry.MYRMEX_SWARMER.get(), worldIn);
         myrmex.m_6034_(playerIn.m_20185_(), playerIn.m_20186_(), playerIn.m_20189_());
         myrmex.setJungleVariant(this.jungle);
         myrmex.setSummonedBy(playerIn);
         myrmex.setFlying(true);
         if (!worldIn.f_46443_) {
            worldIn.m_7967_(myrmex);
         }
      }

      playerIn.m_36335_().m_41524_(this, 1800);
      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.myrmex_swarm.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.myrmex_swarm.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
