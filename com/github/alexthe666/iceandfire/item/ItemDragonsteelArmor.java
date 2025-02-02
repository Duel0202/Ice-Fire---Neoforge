package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.model.armor.ModelDragonsteelFireArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelDragonsteelIceArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelDragonsteelLightningArmor;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ItemDragonsteelArmor extends ArmorItem implements IProtectAgainstDragonItem {
   private static final UUID[] ARMOR_MODIFIERS = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};
   private final ArmorMaterial material;
   private Multimap<Attribute, AttributeModifier> attributeModifierMultimap;

   public ItemDragonsteelArmor(ArmorMaterial material, int renderIndex, Type slot) {
      super(material, slot, new Properties());
      this.material = material;
      this.attributeModifierMultimap = this.createAttributeMap();
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity LivingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            if (itemStack.m_41720_() instanceof ArmorItem) {
               ArmorMaterial armorMaterial = ((ArmorItem)itemStack.m_41720_()).m_40401_();
               if (IafItemRegistry.DRAGONSTEEL_FIRE_ARMOR_MATERIAL.equals(armorMaterial)) {
                  return new ModelDragonsteelFireArmor(inner);
               }

               if (IafItemRegistry.DRAGONSTEEL_ICE_ARMOR_MATERIAL.equals(armorMaterial)) {
                  return new ModelDragonsteelIceArmor(inner);
               }

               if (IafItemRegistry.DRAGONSTEEL_LIGHTNING_ARMOR_MATERIAL.equals(armorMaterial)) {
                  return new ModelDragonsteelLightningArmor(inner);
               }
            }

            return _default;
         }
      });
   }

   private Multimap<Attribute, AttributeModifier> createAttributeMap() {
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      UUID uuid = ARMOR_MODIFIERS[this.f_265916_.m_266308_().m_20749_()];
      builder.put(Attributes.f_22284_, new AttributeModifier(uuid, "Armor modifier", (double)this.material.m_7366_(this.f_265916_), Operation.ADDITION));
      builder.put(Attributes.f_22285_, new AttributeModifier(uuid, "Armor toughness", (double)this.material.m_6651_(), Operation.ADDITION));
      if (this.f_40378_ > 0.0F) {
         builder.put(Attributes.f_22278_, new AttributeModifier(uuid, "Armor knockback resistance", (double)this.f_40378_, Operation.ADDITION));
      }

      return builder.build();
   }

   private Multimap<Attribute, AttributeModifier> getOrUpdateAttributeMap() {
      if (this.attributeModifierMultimap.containsKey(Attributes.f_22284_) && !this.attributeModifierMultimap.get(Attributes.f_22284_).isEmpty() && this.attributeModifierMultimap.get(Attributes.f_22284_).toArray()[0] instanceof AttributeModifier && ((AttributeModifier)this.attributeModifierMultimap.get(Attributes.f_22284_).toArray()[0]).m_22218_() != (double)this.m_40404_()) {
         this.attributeModifierMultimap = this.createAttributeMap();
      }

      return this.attributeModifierMultimap;
   }

   public int getMaxDamage(ItemStack stack) {
      return this.f_265916_ != null ? this.m_40401_().m_266425_(this.f_265916_) : super.getMaxDamage(stack);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.dragonscales_armor.desc").m_130940_(ChatFormatting.GRAY));
   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot equipmentSlot) {
      return equipmentSlot == this.f_265916_.m_266308_() ? this.getOrUpdateAttributeMap() : super.m_7167_(equipmentSlot);
   }

   public int m_40404_() {
      return this.material != null ? this.material.m_7366_(this.m_266204_()) : super.m_40404_();
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      if (this.material == IafItemRegistry.DRAGONSTEEL_FIRE_ARMOR_MATERIAL) {
         return "iceandfire:textures/models/armor/armor_dragonsteel_fire" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
      } else {
         return this.material == IafItemRegistry.DRAGONSTEEL_ICE_ARMOR_MATERIAL ? "iceandfire:textures/models/armor/armor_dragonsteel_ice" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png") : "iceandfire:textures/models/armor/armor_dragonsteel_lightning" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
      }
   }
}
