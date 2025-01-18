package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityDragonSkull;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemDragonSkull extends Item {
   private final int dragonType;

   public ItemDragonSkull(int dragonType) {
      super((new Properties()).m_41487_(1));
      this.dragonType = dragonType;
   }

   static String getName(int type) {
      return "dragon_skull_%s".formatted(new Object[]{getType(type)});
   }

   private static String getType(int type) {
      if (type == 2) {
         return "lightning";
      } else {
         return type == 1 ? "ice" : "fire";
      }
   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
   }

   public void m_6883_(ItemStack stack, @NotNull Level worldIn, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {
      if (stack.m_41783_() == null) {
         stack.m_41751_(new CompoundTag());
         stack.m_41783_().m_128405_("Stage", 4);
         stack.m_41783_().m_128405_("DragonAge", 75);
      }

   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      String iceorfire = "dragon." + getType(this.dragonType);
      tooltip.add(Component.m_237115_(iceorfire).m_130940_(ChatFormatting.GRAY));
      if (stack.m_41783_() != null) {
         MutableComponent var10001 = Component.m_237115_("dragon.stage").m_130940_(ChatFormatting.GRAY);
         CompoundTag var10002 = stack.m_41783_();
         tooltip.add(var10001.m_7220_(Component.m_237113_(" " + var10002.m_128451_("Stage"))));
      }

   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      ItemStack stack = context.m_43723_().m_21120_(context.m_43724_());
      if (stack.m_41783_() != null) {
         EntityDragonSkull skull = new EntityDragonSkull((EntityType)IafEntityRegistry.DRAGON_SKULL.get(), context.m_43725_());
         skull.setDragonType(this.dragonType);
         skull.setStage(stack.m_41783_().m_128451_("Stage"));
         skull.setDragonAge(stack.m_41783_().m_128451_("DragonAge"));
         BlockPos offset = context.m_8083_().m_5484_(context.m_43719_(), 1);
         skull.m_7678_((double)offset.m_123341_() + 0.5D, (double)offset.m_123342_(), (double)offset.m_123343_() + 0.5D, 0.0F, 0.0F);
         float yaw = context.m_43723_().m_146908_();
         if (context.m_43719_() != Direction.UP) {
            yaw = context.m_43723_().m_6350_().m_122435_();
         }

         skull.setYaw(yaw);
         if (stack.m_41788_()) {
            skull.m_6593_(stack.m_41786_());
         }

         if (!context.m_43725_().f_46443_) {
            context.m_43725_().m_7967_(skull);
         }

         if (!context.m_43723_().m_7500_()) {
            stack.m_41774_(1);
         }
      }

      return InteractionResult.SUCCESS;
   }
}
