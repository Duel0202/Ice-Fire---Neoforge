package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityMyrmexEgg;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemMyrmexEgg extends Item {
   boolean isJungle;

   public ItemMyrmexEgg(boolean isJungle) {
      super((new Properties()).m_41487_(1));
      this.isJungle = isJungle;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      CompoundTag tag = stack.m_41783_();
      int eggOrdinal = 0;
      if (tag != null) {
         eggOrdinal = tag.m_128451_("EggOrdinal");
      }

      String caste;
      switch(eggOrdinal) {
      case 1:
         caste = "soldier";
         break;
      case 2:
         caste = "royal";
         break;
      case 3:
         caste = "sentinel";
         break;
      case 4:
         caste = "queen";
         break;
      default:
         caste = "worker";
      }

      if (eggOrdinal == 4) {
         tooltip.add(Component.m_237115_("myrmex.caste_" + caste + ".name").m_130940_(ChatFormatting.LIGHT_PURPLE));
      } else {
         tooltip.add(Component.m_237115_("myrmex.caste_" + caste + ".name").m_130940_(ChatFormatting.GRAY));
      }

   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      ItemStack itemstack = context.m_43723_().m_21120_(context.m_43724_());
      BlockPos offset = context.m_8083_().m_121945_(context.m_43719_());
      EntityMyrmexEgg egg = new EntityMyrmexEgg((EntityType)IafEntityRegistry.MYRMEX_EGG.get(), context.m_43725_());
      CompoundTag tag = itemstack.m_41783_();
      int eggOrdinal = 0;
      if (tag != null) {
         eggOrdinal = tag.m_128451_("EggOrdinal");
      }

      egg.setJungle(this.isJungle);
      egg.setMyrmexCaste(eggOrdinal);
      egg.m_7678_((double)offset.m_123341_() + 0.5D, (double)offset.m_123342_(), (double)offset.m_123343_() + 0.5D, 0.0F, 0.0F);
      egg.onPlayerPlace(context.m_43723_());
      if (itemstack.m_41788_()) {
         egg.m_6593_(itemstack.m_41786_());
      }

      if (!context.m_43725_().f_46443_) {
         context.m_43725_().m_7967_(egg);
      }

      itemstack.m_41774_(1);
      return InteractionResult.SUCCESS;
   }

   public boolean m_5812_(ItemStack stack) {
      CompoundTag tag = stack.m_41783_();
      int eggOrdinal = 0;
      if (tag != null) {
         eggOrdinal = tag.m_128451_("EggOrdinal");
      }

      return super.m_5812_(stack) || eggOrdinal == 4;
   }
}
