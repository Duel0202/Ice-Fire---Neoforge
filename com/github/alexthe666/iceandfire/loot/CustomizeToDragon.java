package com.github.alexthe666.iceandfire.loot;

import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonEgg;
import com.github.alexthe666.iceandfire.item.ItemDragonFlesh;
import com.github.alexthe666.iceandfire.item.ItemDragonScales;
import com.github.alexthe666.iceandfire.item.ItemDragonSkull;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import org.jetbrains.annotations.NotNull;

public class CustomizeToDragon extends LootItemConditionalFunction {
   public CustomizeToDragon(LootItemCondition[] conditionsIn) {
      super(conditionsIn);
   }

   @NotNull
   protected ItemStack m_7372_(ItemStack stack, @NotNull LootContext context) {
      if (!stack.m_41619_()) {
         Object var4 = context.m_78953_(LootContextParams.f_81455_);
         if (var4 instanceof EntityDragonBase) {
            EntityDragonBase dragon = (EntityDragonBase)var4;
            if (stack.m_41720_() == IafItemRegistry.DRAGON_BONE.get()) {
               stack.m_41764_(1 + dragon.m_217043_().m_188503_(1 + dragon.getAgeInDays() / 25));
               return stack;
            }

            if (stack.m_41720_() instanceof ItemDragonScales) {
               stack.m_41764_(dragon.getAgeInDays() / 25 + dragon.m_217043_().m_188503_(1 + dragon.getAgeInDays() / 5));
               return new ItemStack(dragon.getVariantScale(dragon.getVariant()), stack.m_41613_());
            }

            if (stack.m_41720_() instanceof ItemDragonEgg) {
               if (dragon.m_6125_()) {
                  return new ItemStack(dragon.getVariantEgg(dragon.getVariant()), stack.m_41613_());
               }

               stack.m_41764_(1 + dragon.m_217043_().m_188503_(1 + dragon.getAgeInDays() / 5));
               return new ItemStack(dragon.getVariantScale(dragon.getVariant()), stack.m_41613_());
            }

            if (stack.m_41720_() instanceof ItemDragonFlesh) {
               return new ItemStack(dragon.getFleshItem(), 1 + dragon.m_217043_().m_188503_(1 + dragon.getAgeInDays() / 25));
            }

            if (stack.m_41720_() instanceof ItemDragonSkull) {
               ItemStack skull = dragon.getSkull();
               skull.m_41764_(stack.m_41613_());
               skull.m_41751_(stack.m_41783_());
               return skull;
            }

            if (stack.m_204117_(IafItemTags.DRAGON_BLOODS)) {
               return new ItemStack(dragon.getBloodItem(), stack.m_41613_());
            }

            if (stack.m_204117_(IafItemTags.DRAGON_HEARTS)) {
               return new ItemStack(dragon.getHeartItem(), stack.m_41613_());
            }
         }
      }

      return stack;
   }

   @NotNull
   public LootItemFunctionType m_7162_() {
      return IafLootRegistry.CUSTOMIZE_TO_DRAGON;
   }

   public static class Serializer extends net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction.Serializer<CustomizeToDragon> {
      public void serialize(@NotNull JsonObject object, @NotNull CustomizeToDragon functionClazz, @NotNull JsonSerializationContext serializationContext) {
      }

      @NotNull
      public CustomizeToDragon deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext deserializationContext, @NotNull LootItemCondition[] conditionsIn) {
         return new CustomizeToDragon(conditionsIn);
      }
   }
}
