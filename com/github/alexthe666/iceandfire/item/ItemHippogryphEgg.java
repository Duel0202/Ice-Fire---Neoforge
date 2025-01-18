package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityHippogryphEgg;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumHippogryphTypes;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemHippogryphEgg extends Item {
   public ItemHippogryphEgg() {
      super((new Properties()).m_41487_(1));
   }

   public static ItemStack createEggStack(EnumHippogryphTypes parent1, EnumHippogryphTypes parent2) {
      EnumHippogryphTypes eggType = ThreadLocalRandom.current().nextBoolean() ? parent1 : parent2;
      ItemStack stack = new ItemStack((ItemLike)IafItemRegistry.HIPPOGRYPH_EGG.get());
      CompoundTag tag = new CompoundTag();
      tag.m_128405_("EggOrdinal", eggType.ordinal());
      stack.m_41751_(tag);
      return stack;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand handIn) {
      ItemStack itemstack = playerIn.m_21120_(handIn);
      if (!playerIn.m_7500_()) {
         itemstack.m_41774_(1);
      }

      worldIn.m_6263_((Player)null, playerIn.m_20185_(), playerIn.m_20186_(), playerIn.m_20189_(), SoundEvents.f_11877_, SoundSource.PLAYERS, 0.5F, 0.4F / (worldIn.f_46441_.m_188501_() * 0.4F + 0.8F));
      if (!worldIn.f_46443_) {
         EntityHippogryphEgg entityegg = new EntityHippogryphEgg((EntityType)IafEntityRegistry.HIPPOGRYPH_EGG.get(), worldIn, playerIn, itemstack);
         entityegg.m_37251_(playerIn, playerIn.m_146909_(), playerIn.m_146908_(), 0.0F, 1.5F, 1.0F);
         worldIn.m_7967_(entityegg);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemstack);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      CompoundTag tag = stack.m_41783_();
      int eggOrdinal = 0;
      if (tag != null) {
         eggOrdinal = tag.m_128451_("EggOrdinal");
      }

      String type = EnumHippogryphTypes.values()[Mth.m_14045_(eggOrdinal, 0, EnumHippogryphTypes.values().length - 1)].name().toLowerCase();
      tooltip.add(Component.m_237115_("entity.iceandfire.hippogryph." + type).m_130940_(ChatFormatting.GRAY));
   }
}
