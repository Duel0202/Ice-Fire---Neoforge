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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemModPickaxe extends PickaxeItem implements DragonSteelOverrides<ItemModPickaxe> {
   private Multimap<Attribute, AttributeModifier> dragonsteelModifiers;

   public ItemModPickaxe(Tier toolmaterial) {
      super(toolmaterial, 1, -2.8F, new Properties());
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
      if ((double)this.m_43314_().m_6631_() == IafConfig.dragonsteelBaseDamage && this.dragonsteelModifiers != null) {
         return this.dragonsteelModifiers;
      } else {
         Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
         builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Weapon modifier", IafConfig.dragonsteelBaseDamage - 1.0D + 1.0D, Operation.ADDITION));
         builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Weapon modifier", -2.799999952316284D, Operation.ADDITION));
         this.dragonsteelModifiers = builder.build();
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
      this.appendHoverText(this.m_43314_(), stack, worldIn, tooltip, flagIn);
   }
}
