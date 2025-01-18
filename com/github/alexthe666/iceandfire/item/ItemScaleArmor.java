package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.client.model.armor.ModelFireDragonScaleArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelIceDragonScaleArmor;
import com.github.alexthe666.iceandfire.client.model.armor.ModelLightningDragonScaleArmor;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ItemScaleArmor extends ArmorItem implements IProtectAgainstDragonItem {
   public EnumDragonArmor armor_type;
   public EnumDragonEgg eggType;

   public ItemScaleArmor(EnumDragonEgg eggType, EnumDragonArmor armorType, CustomArmorMaterial material, Type slot) {
      super(material, slot, new Properties());
      this.armor_type = armorType;
      this.eggType = eggType;
   }

   @NotNull
   public String m_5524_() {
      switch(this.f_265916_) {
      case HELMET:
         return "item.iceandfire.dragon_helmet";
      case CHESTPLATE:
         return "item.iceandfire.dragon_chestplate";
      case LEGGINGS:
         return "item.iceandfire.dragon_leggings";
      case BOOTS:
         return "item.iceandfire.dragon_boots";
      default:
         return "item.iceandfire.dragon_helmet";
      }
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity LivingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            boolean inner = armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD;
            if (itemStack.m_41720_() instanceof ItemScaleArmor) {
               DragonType dragonType = ((ItemScaleArmor)itemStack.m_41720_()).armor_type.eggType.dragonType;
               if (DragonType.FIRE == dragonType) {
                  return new ModelFireDragonScaleArmor(inner);
               }

               if (DragonType.ICE == dragonType) {
                  return new ModelIceDragonScaleArmor(inner);
               }

               if (DragonType.LIGHTNING == dragonType) {
                  return new ModelLightningDragonScaleArmor(inner);
               }
            }

            return _default;
         }
      });
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      String var10000 = this.armor_type.name();
      return "iceandfire:textures/models/armor/" + var10000 + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("dragon." + this.eggType.toString().toLowerCase()).m_130940_(this.eggType.color));
      tooltip.add(Component.m_237115_("item.dragonscales_armor.desc").m_130940_(ChatFormatting.GRAY));
   }
}
