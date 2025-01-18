package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.client.model.armor.ModelSeaSerpentArmor;
import com.github.alexthe666.iceandfire.enums.EnumSeaSerpent;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ItemSeaSerpentArmor extends ArmorItem {
   public EnumSeaSerpent armor_type;

   public ItemSeaSerpentArmor(EnumSeaSerpent armorType, CustomArmorMaterial material, Type slot) {
      super(material, slot, new Properties());
      this.armor_type = armorType;
   }

   @NotNull
   public String m_5524_() {
      switch(this.f_265916_) {
      case HELMET:
         return "item.iceandfire.sea_serpent_helmet";
      case CHESTPLATE:
         return "item.iceandfire.sea_serpent_chestplate";
      case LEGGINGS:
         return "item.iceandfire.sea_serpent_leggings";
      case BOOTS:
         return "item.iceandfire.sea_serpent_boots";
      default:
         return "item.iceandfire.sea_serpent_helmet";
      }
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity LivingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return new ModelSeaSerpentArmor(armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD);
         }
      });
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      return "iceandfire:textures/models/armor/armor_tide_" + this.armor_type.resourceName + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
   }

   public void onArmorTick(ItemStack stack, Level world, Player player) {
      super.onArmorTick(stack, world, player);
      player.m_7292_(new MobEffectInstance(MobEffects.f_19608_, 50, 0, false, false));
      if (player.m_20070_()) {
         int headMod = player.m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof ItemSeaSerpentArmor ? 1 : 0;
         int chestMod = player.m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof ItemSeaSerpentArmor ? 1 : 0;
         int legMod = player.m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof ItemSeaSerpentArmor ? 1 : 0;
         int footMod = player.m_6844_(EquipmentSlot.FEET).m_41720_() instanceof ItemSeaSerpentArmor ? 1 : 0;
         player.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 50, headMod + chestMod + legMod + footMod - 1, false, false));
      }

   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("sea_serpent." + this.armor_type.resourceName).m_130940_(this.armor_type.color));
      tooltip.add(Component.m_237115_("item.iceandfire.sea_serpent_armor.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.sea_serpent_armor.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
