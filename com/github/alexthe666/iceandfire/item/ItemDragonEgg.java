package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityDragonEgg;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDragonEgg extends Item {
   public EnumDragonEgg type;

   public ItemDragonEgg(EnumDragonEgg type) {
      super((new Properties()).m_41487_(1));
      this.type = type;
   }

   @NotNull
   public String m_5524_() {
      return "item.iceandfire.dragonegg";
   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("dragon." + this.type.toString().toLowerCase()).m_130940_(this.type.color));
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      ItemStack itemstack = context.m_43723_().m_21120_(context.m_43724_());
      BlockPos offset = context.m_8083_().m_121945_(context.m_43719_());
      EntityDragonEgg egg = new EntityDragonEgg((EntityType)IafEntityRegistry.DRAGON_EGG.get(), context.m_43725_());
      egg.setEggType(this.type);
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
}
