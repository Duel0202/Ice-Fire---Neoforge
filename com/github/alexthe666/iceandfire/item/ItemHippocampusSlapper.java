package com.github.alexthe666.iceandfire.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemHippocampusSlapper extends SwordItem {
   public ItemHippocampusSlapper() {
      super(IafItemRegistry.HIPPOCAMPUS_SWORD_TOOL_MATERIAL, 3, -2.4F, new Properties());
   }

   public boolean m_7579_(@NotNull ItemStack stack, LivingEntity targetEntity, @NotNull LivingEntity attacker) {
      targetEntity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 100, 2));
      targetEntity.m_7292_(new MobEffectInstance(MobEffects.f_19604_, 100, 2));
      targetEntity.m_5496_(SoundEvents.f_12004_, 3.0F, 1.0F);
      return super.m_7579_(stack, targetEntity, attacker);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.hippocampus_slapper.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.hippocampus_slapper.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
