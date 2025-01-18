package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityStymphalianFeather;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
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

public class ItemStymphalianFeatherBundle extends Item {
   public ItemStymphalianFeatherBundle() {
      super(new Properties());
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player player, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = player.m_21120_(hand);
      player.m_6672_(hand);
      player.m_36335_().m_41524_(this, 15);
      player.m_5496_(SoundEvents.f_11877_, 1.0F, 1.0F);
      float rotation = player.f_20885_;

      for(int i = 0; i < 8; ++i) {
         EntityStymphalianFeather feather = new EntityStymphalianFeather((EntityType)IafEntityRegistry.STYMPHALIAN_FEATHER.get(), worldIn, player);
         rotation += 45.0F;
         feather.m_37251_(player, 0.0F, rotation, 0.0F, 1.5F, 1.0F);
         if (!worldIn.f_46443_) {
            worldIn.m_7967_(feather);
         }
      }

      if (!player.m_7500_()) {
         itemStackIn.m_41774_(1);
      }

      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.stymphalian_feather_bundle.desc_0").m_130940_(ChatFormatting.GRAY));
   }
}
