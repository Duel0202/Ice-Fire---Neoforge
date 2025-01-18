package com.github.alexthe666.iceandfire.item;

import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class ItemCyclopsEye extends Item {
   public ItemCyclopsEye() {
      super((new Properties()).m_41503_(500));
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !ItemStack.m_41656_(oldStack, newStack);
   }

   public void m_6883_(ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
      if (stack.m_41783_() == null) {
         stack.m_41751_(new CompoundTag());
      } else if (entity instanceof LivingEntity) {
         LivingEntity living = (LivingEntity)entity;
         if (living.m_21205_() == stack || living.m_21206_() == stack) {
            double range = 15.0D;
            boolean inflictedDamage = false;
            Iterator var10 = world.m_45976_(Mob.class, new AABB(living.m_20185_() - range, living.m_20186_() - range, living.m_20189_() - range, living.m_20185_() + range, living.m_20186_() + range, living.m_20189_() + range)).iterator();

            label47:
            while(true) {
               Mob LivingEntity;
               do {
                  do {
                     do {
                        if (!var10.hasNext()) {
                           if (inflictedDamage) {
                              stack.m_41783_().m_128405_("HurtingTicks", stack.m_41783_().m_128451_("HurtingTicks") + 1);
                           }
                           break label47;
                        }

                        LivingEntity = (Mob)var10.next();
                     } while(LivingEntity.m_7306_(living));
                  } while(LivingEntity.m_7307_(living));
               } while(LivingEntity.m_5448_() != living && LivingEntity.m_21188_() != living && !(LivingEntity instanceof Enemy));

               LivingEntity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 10, 1));
               inflictedDamage = true;
            }
         }

         if (stack.m_41783_().m_128451_("HurtingTicks") > 120) {
            stack.m_41622_(1, (LivingEntity)entity, (p_220017_1_) -> {
            });
            stack.m_41783_().m_128405_("HurtingTicks", 0);
         }
      }

   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.cyclops_eye.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.cyclops_eye.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
