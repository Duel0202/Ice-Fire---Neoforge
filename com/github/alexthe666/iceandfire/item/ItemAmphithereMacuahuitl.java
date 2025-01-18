package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemAmphithereMacuahuitl extends SwordItem {
   public ItemAmphithereMacuahuitl() {
      super(IafItemRegistry.AMPHITHERE_SWORD_TOOL_MATERIAL, 3, -2.4F, new Properties());
   }

   public boolean m_7579_(@NotNull ItemStack stack, LivingEntity targetEntity, LivingEntity attacker) {
      targetEntity.m_5496_(IafSoundRegistry.AMPHITHERE_GUST, 1.0F, 1.0F);
      targetEntity.m_5496_(SoundEvents.f_12346_, 1.0F, 1.0F);
      targetEntity.f_19812_ = true;
      double xRatio = (double)(-Mth.m_14031_(attacker.m_146908_() * 0.017453292F));
      double zRatio = (double)Mth.m_14089_(attacker.m_146908_() * 0.017453292F);
      float strength = -0.6F;
      float f = Mth.m_14116_((float)(xRatio * xRatio + zRatio * zRatio));
      targetEntity.m_20334_(targetEntity.m_20184_().f_82479_ / 2.0D - xRatio / (double)f * (double)strength, 0.8D, targetEntity.m_20184_().f_82481_ / 2.0D - zRatio / (double)f * (double)strength);
      Random rand = new Random();

      for(int i = 0; i < 20; ++i) {
         double d0 = rand.nextGaussian() * 0.02D;
         double d1 = rand.nextGaussian() * 0.02D;
         double d2 = rand.nextGaussian() * 0.02D;
         double d3 = 10.0D;
         targetEntity.m_9236_().m_7106_(ParticleTypes.f_123796_, targetEntity.m_20185_() + (double)(rand.nextFloat() * targetEntity.m_20205_() * 5.0F) - (double)targetEntity.m_20205_() - d0 * 10.0D, targetEntity.m_20186_() + (double)(rand.nextFloat() * targetEntity.m_20206_()) - d1 * 10.0D, targetEntity.m_20189_() + (double)(rand.nextFloat() * targetEntity.m_20205_() * 5.0F) - (double)targetEntity.m_20205_() - d2 * 10.0D, d0, d1, d2);
      }

      return super.m_7579_(stack, targetEntity, attacker);
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.amphithere_macuahuitl.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.amphithere_macuahuitl.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
