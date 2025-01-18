package com.github.alexthe666.iceandfire.loot;

import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemSeaSerpentScales;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class CustomizeToSeaSerpent extends LootItemConditionalFunction {
   public CustomizeToSeaSerpent(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   @NotNull
   public ItemStack m_7372_(ItemStack stack, @NotNull LootContext context) {
      if (!stack.m_41619_()) {
         Object var4 = context.m_78953_(LootContextParams.f_81455_);
         if (var4 instanceof EntitySeaSerpent) {
            EntitySeaSerpent seaSerpent = (EntitySeaSerpent)var4;
            int ancientModifier = seaSerpent.isAncient() ? 2 : 1;
            if (stack.m_41720_() instanceof ItemSeaSerpentScales) {
               stack.m_41764_(1 + seaSerpent.m_217043_().m_188503_(1 + (int)Math.ceil((double)(seaSerpent.getSeaSerpentScale() * 3.0F * (float)ancientModifier))));
               return new ItemStack((ItemLike)seaSerpent.getEnum().scale.get(), stack.m_41613_());
            }

            if (stack.m_41720_() == IafItemRegistry.SERPENT_FANG.get()) {
               stack.m_41764_(1 + seaSerpent.m_217043_().m_188503_(1 + (int)Math.ceil((double)(seaSerpent.getSeaSerpentScale() * 2.0F * (float)ancientModifier))));
               return stack;
            }
         }
      }

      return stack;
   }

   @NotNull
   public LootItemFunctionType m_7162_() {
      return IafLootRegistry.CUSTOMIZE_TO_SERPENT;
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer<CustomizeToSeaSerpent> {
      public void serialize(@NotNull JsonObject object, @NotNull CustomizeToSeaSerpent functionClazz, @NotNull JsonSerializationContext serializationContext) {
      }

      @NotNull
      public CustomizeToSeaSerpent deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext deserializationContext, @NotNull LootItemCondition[] conditionsIn) {
         return new CustomizeToSeaSerpent(conditionsIn);
      }
   }
}
