package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.render.entity.RenderTideTridentItem;
import com.github.alexthe666.iceandfire.entity.EntityTideTrident;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.enchantment.ArrowPiercingEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforge.common.util.NonNullLazy;
import org.jetbrains.annotations.NotNull;

public class ItemTideTrident extends TridentItem {
   public ItemTideTrident() {
      super((new Properties()).m_41503_(400));
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> {
            return new RenderTideTridentItem(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
         });

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return (BlockEntityWithoutLevelRenderer)renderer.get();
         }
      });
   }

   public void m_5551_(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity entityLiving, int timeLeft) {
      if (entityLiving instanceof Player) {
         Player lvt_5_1_ = (Player)entityLiving;
         int lvt_6_1_ = this.m_8105_(stack) - timeLeft;
         if (lvt_6_1_ >= 10) {
            int lvt_7_1_ = EnchantmentHelper.m_44932_(stack);
            if (lvt_7_1_ <= 0 || lvt_5_1_.m_20070_()) {
               if (!worldIn.f_46443_) {
                  stack.m_41622_(1, lvt_5_1_, (player) -> {
                     player.m_21190_(entityLiving.m_7655_());
                  });
                  if (lvt_7_1_ == 0) {
                     EntityTideTrident lvt_8_1_ = new EntityTideTrident(worldIn, lvt_5_1_, stack);
                     lvt_8_1_.m_37251_(lvt_5_1_, lvt_5_1_.m_146909_(), lvt_5_1_.m_146908_(), 0.0F, 2.5F + (float)lvt_7_1_ * 0.5F, 1.0F);
                     if (lvt_5_1_.m_150110_().f_35937_) {
                        lvt_8_1_.f_36705_ = Pickup.CREATIVE_ONLY;
                     }

                     worldIn.m_7967_(lvt_8_1_);
                     worldIn.m_6269_((Player)null, lvt_8_1_, SoundEvents.f_12520_, SoundSource.PLAYERS, 1.0F, 1.0F);
                     if (!lvt_5_1_.m_150110_().f_35937_) {
                        lvt_5_1_.m_150109_().m_36057_(stack);
                     }
                  }
               }

               lvt_5_1_.m_36246_(Stats.f_12982_.m_12902_(this));
               if (lvt_7_1_ > 0) {
                  float lvt_8_2_ = lvt_5_1_.m_146908_();
                  float lvt_9_1_ = lvt_5_1_.m_146909_();
                  float lvt_10_1_ = -Mth.m_14031_(lvt_8_2_ * 0.017453292F) * Mth.m_14089_(lvt_9_1_ * 0.017453292F);
                  float lvt_11_1_ = -Mth.m_14031_(lvt_9_1_ * 0.017453292F);
                  float lvt_12_1_ = Mth.m_14089_(lvt_8_2_ * 0.017453292F) * Mth.m_14089_(lvt_9_1_ * 0.017453292F);
                  float lvt_13_1_ = Mth.m_14116_(lvt_10_1_ * lvt_10_1_ + lvt_11_1_ * lvt_11_1_ + lvt_12_1_ * lvt_12_1_);
                  float lvt_14_1_ = 3.0F * ((1.0F + (float)lvt_7_1_) / 4.0F);
                  lvt_10_1_ *= lvt_14_1_ / lvt_13_1_;
                  lvt_11_1_ *= lvt_14_1_ / lvt_13_1_;
                  lvt_12_1_ *= lvt_14_1_ / lvt_13_1_;
                  lvt_5_1_.m_5997_((double)lvt_10_1_, (double)lvt_11_1_, (double)lvt_12_1_);
                  lvt_5_1_.m_204079_(20);
                  if (lvt_5_1_.m_20096_()) {
                     float lvt_15_1_ = 1.1999999F;
                     lvt_5_1_.m_6478_(MoverType.SELF, new Vec3(0.0D, 1.1999999284744263D, 0.0D));
                  }

                  SoundEvent lvt_15_4_;
                  if (lvt_7_1_ >= 3) {
                     lvt_15_4_ = SoundEvents.f_12519_;
                  } else if (lvt_7_1_ == 2) {
                     lvt_15_4_ = SoundEvents.f_12518_;
                  } else {
                     lvt_15_4_ = SoundEvents.f_12517_;
                  }

                  worldIn.m_6269_((Player)null, lvt_5_1_, lvt_15_4_, SoundSource.PLAYERS, 1.0F, 1.0F);
               }
            }
         }
      }

   }

   @NotNull
   public Multimap<Attribute, AttributeModifier> m_7167_(@NotNull EquipmentSlot equipmentSlot) {
      Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
      if (equipmentSlot == EquipmentSlot.MAINHAND) {
         builder.put(Attributes.f_22281_, new AttributeModifier(f_41374_, "Weapon modifier", 12.0D, Operation.ADDITION));
         builder.put(Attributes.f_22283_, new AttributeModifier(f_41375_, "Weapon modifier", -2.9000000953674316D, Operation.ADDITION));
      }

      return builder.build();
   }

   public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
      return enchantment instanceof ArrowPiercingEnchantment ? true : enchantment.f_44672_.m_7454_(stack.m_41720_());
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.tide_trident.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.tide_trident.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
