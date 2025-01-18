package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityStoneStatue;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.Mth;
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

public class ItemStoneStatue extends Item {
   public ItemStoneStatue() {
      super((new Properties()).m_41487_(1));
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      if (stack.m_41783_() != null) {
         boolean isPlayer = stack.m_41783_().m_128471_("IAFStoneStatuePlayerEntity");
         String id = stack.m_41783_().m_128461_("IAFStoneStatueEntityID");
         if (EntityType.m_20632_(id).orElse((Object)null) != null) {
            EntityType type = (EntityType)EntityType.m_20632_(id).orElse((Object)null);
            MutableComponent untranslated = isPlayer ? Component.m_237115_("entity.minecraft.player") : Component.m_237115_(type.m_20675_());
            tooltip.add(untranslated.m_130940_(ChatFormatting.GRAY));
         }
      }

   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
      itemStack.m_41783_().m_128379_("IAFStoneStatuePlayerEntity", true);
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      if (context.m_43719_() != Direction.UP) {
         return InteractionResult.FAIL;
      } else {
         ItemStack stack = context.m_43723_().m_21120_(context.m_43724_());
         if (stack.m_41783_() != null) {
            String id = stack.m_41783_().m_128461_("IAFStoneStatueEntityID");
            CompoundTag statueNBT = stack.m_41783_().m_128469_("IAFStoneStatueNBT");
            EntityStoneStatue statue = new EntityStoneStatue((EntityType)IafEntityRegistry.STONE_STATUE.get(), context.m_43725_());
            statue.m_7378_(statueNBT);
            statue.setTrappedEntityTypeString(id);
            double d1 = context.m_43723_().m_20185_() - ((double)context.m_8083_().m_123341_() + 0.5D);
            double d2 = context.m_43723_().m_20189_() - ((double)context.m_8083_().m_123343_() + 0.5D);
            float yaw = (float)(Mth.m_14136_(d2, d1) * 57.2957763671875D) - 90.0F;
            statue.f_19859_ = yaw;
            statue.m_146922_(yaw);
            statue.f_20885_ = yaw;
            statue.f_20883_ = yaw;
            statue.f_20884_ = yaw;
            statue.m_19890_((double)context.m_8083_().m_123341_() + 0.5D, (double)(context.m_8083_().m_123342_() + 1), (double)context.m_8083_().m_123343_() + 0.5D, yaw, 0.0F);
            if (!context.m_43725_().f_46443_) {
               context.m_43725_().m_7967_(statue);
               statue.m_7378_(stack.m_41783_());
            }

            statue.setCrackAmount(0);
            if (!context.m_43723_().m_7500_()) {
               stack.m_41774_(1);
            }

            return InteractionResult.SUCCESS;
         } else {
            return InteractionResult.SUCCESS;
         }
      }
   }
}
