package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.model.armor.ModelDeathWormArmor;
import java.util.function.Consumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ItemDeathwormArmor extends ArmorItem {
   public ItemDeathwormArmor(ArmorMaterial material, Type slot) {
      super(material, slot, new Properties());
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity LivingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return new ModelDeathWormArmor(ModelDeathWormArmor.getBakedModel(armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD));
         }
      });
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      if (this.m_40401_() == IafItemRegistry.DEATHWORM_2_ARMOR_MATERIAL) {
         return "iceandfire:textures/models/armor/armor_deathworm_red" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
      } else {
         return this.m_40401_() == IafItemRegistry.DEATHWORM_1_ARMOR_MATERIAL ? "iceandfire:textures/models/armor/armor_deathworm_white" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png") : "iceandfire:textures/models/armor/armor_deathworm_yellow" + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
      }
   }
}
