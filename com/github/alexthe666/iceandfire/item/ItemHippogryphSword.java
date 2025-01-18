package com.github.alexthe666.iceandfire.item;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemHippogryphSword extends SwordItem {
   public ItemHippogryphSword() {
      super(IafItemRegistry.HIPPOGRYPH_SWORD_TOOL_MATERIAL, 3, -2.4F, new Properties());
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity targetEntity, LivingEntity attacker) {
      float f = (float)attacker.m_21051_(Attributes.f_22281_).m_22135_();
      float f3 = 1.0F + EnchantmentHelper.m_44821_(attacker) * f;
      if (attacker instanceof Player) {
         Player player = (Player)attacker;
         Iterator var7 = attacker.m_9236_().m_45976_(LivingEntity.class, targetEntity.m_20191_().m_82377_(1.0D, 0.25D, 1.0D)).iterator();

         while(var7.hasNext()) {
            LivingEntity LivingEntity = (LivingEntity)var7.next();
            if (LivingEntity != player && LivingEntity != targetEntity && !attacker.m_7307_(LivingEntity) && attacker.m_20280_(LivingEntity) < 9.0D) {
               LivingEntity.m_147240_(0.4000000059604645D, (double)Mth.m_14031_(attacker.m_146908_() * 0.017453292F), (double)(-Mth.m_14089_(attacker.m_146908_() * 0.017453292F)));
               LivingEntity.m_6469_(attacker.m_9236_().m_269111_().m_269075_(player), f3);
            }
         }

         player.m_9236_().m_6263_((Player)null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12317_, player.m_5720_(), 1.0F, 1.0F);
         player.m_36346_();
      }

      return super.m_7579_(stack, targetEntity, attacker);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.hippogryph_sword.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.hippogryph_sword.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
