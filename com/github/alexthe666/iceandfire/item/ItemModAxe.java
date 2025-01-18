package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IafConfig;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemModAxe extends AxeItem implements DragonSteelOverrides<ItemModAxe> {
   private final Tier tier;
   private Multimap<Attribute, AttributeModifier> dragonsteelModifiers;

   public ItemModAxe(Tier toolmaterial) {
      super(toolmaterial, 5.0F, -3.0F, new Properties());
      this.tier = toolmaterial;
   }

   /** @deprecated */
   @Deprecated
   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot equipmentSlot) {
      return equipmentSlot == EquipmentSlot.MAINHAND && this.isDragonsteel(this.m_43314_()) ? this.bakeDragonsteel() : super.m_7167_(equipmentSlot);
   }

   /** @deprecated */
   @Deprecated
   public Multimap<Attribute, AttributeModifier> bakeDragonsteel() {
      if ((double)this.tier.m_6631_() == IafConfig.dragonsteelBaseDamage && this.dragonsteelModifiers != null) {
         return this.dragonsteelModifiers;
      } else {
         Builder<Attribute, AttributeModifier> lvt_5_1_ = ImmutableMultimap.builder();
         lvt_5_1_.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Weapon modifier", IafConfig.dragonsteelBaseDamage - 1.0D + 5.0D, Operation.ADDITION));
         lvt_5_1_.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Weapon modifier", -3.0D, Operation.ADDITION));
         this.dragonsteelModifiers = lvt_5_1_.build();
         return this.dragonsteelModifiers;
      }
   }

   public int getMaxDamage(ItemStack stack) {
      return this.isDragonsteel(this.m_43314_()) ? IafConfig.dragonsteelBaseDurability : this.m_43314_().m_6609_();
   }

   public boolean m_7579_(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
      this.hurtEnemy(this, stack, target, attacker);
      return super.m_7579_(stack, target, attacker);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      this.appendHoverText(this.tier, stack, worldIn, tooltip, flagIn);
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }
}
