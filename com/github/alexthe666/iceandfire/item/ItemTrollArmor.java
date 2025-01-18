package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.iceandfire.client.model.armor.ModelTrollArmor;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

public class ItemTrollArmor extends ArmorItem {
   public EnumTroll troll;

   public ItemTrollArmor(EnumTroll troll, CustomArmorMaterial material, Type slot) {
      super(material, slot, new Properties());
      this.troll = troll;
   }

   public static String getName(EnumTroll troll, EquipmentSlot slot) {
      return "%s_troll_leather_%s".formatted(new Object[]{troll.name().toLowerCase(Locale.ROOT), getArmorPart(slot)});
   }

   @NotNull
   public ArmorMaterial m_40401_() {
      return this.troll.material;
   }

   private static String getArmorPart(EquipmentSlot slot) {
      String var10000;
      switch(slot) {
      case HEAD:
         var10000 = "helmet";
         break;
      case CHEST:
         var10000 = "chestplate";
         break;
      case LEGS:
         var10000 = "leggings";
         break;
      case FEET:
         var10000 = "boots";
         break;
      default:
         var10000 = "";
      }

      return var10000;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity LivingEntity, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
            return new ModelTrollArmor(armorSlot == EquipmentSlot.LEGS || armorSlot == EquipmentSlot.HEAD);
         }
      });
   }

   public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
      String var10000 = this.troll.name().toLowerCase(Locale.ROOT);
      return "iceandfire:textures/models/armor/armor_troll_" + var10000 + (slot == EquipmentSlot.LEGS ? "_legs.png" : ".png");
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.troll_leather_armor_" + getArmorPart(this.f_265916_.m_266308_()) + ".desc").m_130940_(ChatFormatting.GREEN));
   }
}
