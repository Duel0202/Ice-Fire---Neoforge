package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityGhostSword;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.google.common.collect.Multimap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemGhostSword extends SwordItem {
   public ItemGhostSword() {
      super(IafItemRegistry.GHOST_SWORD_TOOL_MATERIAL, 5, -1.0F, new Properties());
   }

   public static void spawnGhostSwordEntity(ItemStack stack, Player playerEntity) {
      if (!playerEntity.m_36335_().m_41519_(stack.m_41720_())) {
         if (playerEntity.m_21120_(InteractionHand.MAIN_HAND) == stack) {
            Multimap<Attribute, AttributeModifier> dmg = stack.m_41638_(EquipmentSlot.MAINHAND);
            double totalDmg = 0.0D;

            AttributeModifier modifier;
            for(Iterator var5 = dmg.get(Attributes.f_22281_).iterator(); var5.hasNext(); totalDmg += modifier.m_22218_()) {
               modifier = (AttributeModifier)var5.next();
            }

            playerEntity.m_5496_(SoundEvents.f_12609_, 1.0F, 1.0F);
            EntityGhostSword shot = new EntityGhostSword((EntityType)IafEntityRegistry.GHOST_SWORD.get(), playerEntity.m_9236_(), playerEntity, totalDmg * 0.5D);
            shot.m_37251_(playerEntity, playerEntity.m_146909_(), playerEntity.m_146908_(), 0.0F, 1.0F, 0.5F);
            playerEntity.m_9236_().m_7967_(shot);
            stack.m_41622_(1, playerEntity, (entity) -> {
               entity.m_21166_(EquipmentSlot.MAINHAND);
            });
            playerEntity.m_36335_().m_41524_(stack.m_41720_(), 10);
         }
      }
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity targetEntity, @NotNull LivingEntity attacker) {
      return super.m_7579_(stack, targetEntity, attacker);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.ghost_sword.desc_0").m_130940_(ChatFormatting.GRAY));
   }
}
