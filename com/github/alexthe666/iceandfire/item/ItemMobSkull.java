package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityMobSkull;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.enums.EnumSkullType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

public class ItemMobSkull extends Item {
   private final EnumSkullType skull;

   public ItemMobSkull(EnumSkullType skull) {
      super((new Properties()).m_41487_(1));
      this.skull = skull;
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      Player player = context.m_43723_();
      EntityMobSkull skull = new EntityMobSkull((EntityType)IafEntityRegistry.MOB_SKULL.get(), context.m_43725_());
      ItemStack stack = player.m_21120_(context.m_43724_());
      BlockPos offset = context.m_8083_().m_5484_(context.m_43719_(), 1);
      skull.m_7678_((double)offset.m_123341_() + 0.5D, (double)offset.m_123342_(), (double)offset.m_123343_() + 0.5D, 0.0F, 0.0F);
      float yaw = player.m_146908_();
      if (context.m_43719_() != Direction.UP) {
         yaw = player.m_6350_().m_122435_();
      }

      skull.setYaw(yaw);
      skull.setSkullType(this.skull);
      if (!context.m_43725_().f_46443_) {
         context.m_43725_().m_7967_(skull);
      }

      if (stack.m_41788_()) {
         skull.m_6593_(stack.m_41786_());
      }

      if (!player.m_7500_()) {
         stack.m_41774_(1);
      }

      return InteractionResult.SUCCESS;
   }
}
