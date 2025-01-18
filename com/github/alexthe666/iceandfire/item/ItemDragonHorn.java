package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ItemDragonHorn extends Item {
   public ItemDragonHorn() {
      super((new Properties()).m_41487_(1));
   }

   public static int getDragonType(ItemStack stack) {
      if (stack.m_41783_() != null) {
         String id = stack.m_41783_().m_128461_("DragonHornEntityID");
         if (EntityType.m_20632_(id).isPresent()) {
            EntityType entityType = (EntityType)EntityType.m_20632_(id).get();
            if (entityType == IafEntityRegistry.FIRE_DRAGON.get()) {
               return 1;
            }

            if (entityType == IafEntityRegistry.ICE_DRAGON.get()) {
               return 2;
            }

            if (entityType == IafEntityRegistry.LIGHTNING_DRAGON.get()) {
               return 3;
            }
         }
      }

      return 0;
   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
   }

   @NotNull
   public InteractionResult m_6880_(@NotNull ItemStack stack, Player playerIn, @NotNull LivingEntity target, @NotNull InteractionHand hand) {
      ItemStack trueStack = playerIn.m_21120_(hand);
      if (playerIn.m_9236_().f_46443_ || hand != InteractionHand.MAIN_HAND || !(target instanceof EntityDragonBase) || !((EntityDragonBase)target).m_21830_(playerIn) || trueStack.m_41783_() != null && (trueStack.m_41783_() == null || !trueStack.m_41783_().m_128469_("EntityTag").m_128456_())) {
         return InteractionResult.FAIL;
      } else {
         CompoundTag newTag = new CompoundTag();
         CompoundTag entityTag = new CompoundTag();
         target.m_20223_(entityTag);
         newTag.m_128365_("EntityTag", entityTag);
         newTag.m_128359_("DragonHornEntityID", ForgeRegistries.ENTITY_TYPES.getKey(target.m_6095_()).toString());
         trueStack.m_41751_(newTag);
         playerIn.m_6674_(hand);
         playerIn.m_9236_().m_5594_(playerIn, playerIn.m_20183_(), SoundEvents.f_12616_, SoundSource.NEUTRAL, 3.0F, 0.75F);
         target.m_142687_(RemovalReason.DISCARDED);
         return InteractionResult.SUCCESS;
      }
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      if (context.m_43719_() != Direction.UP) {
         return InteractionResult.FAIL;
      } else {
         ItemStack stack = context.m_43722_();
         if (stack.m_41783_() != null && !stack.m_41783_().m_128461_("DragonHornEntityID").isEmpty()) {
            Level world = context.m_43725_();
            String id = stack.m_41783_().m_128461_("DragonHornEntityID");
            EntityType type = (EntityType)EntityType.m_20632_(id).orElse((Object)null);
            if (type != null) {
               Entity entity = type.m_20615_(world);
               if (entity instanceof EntityDragonBase) {
                  EntityDragonBase dragon = (EntityDragonBase)entity;
                  dragon.m_20258_(stack.m_41783_().m_128469_("EntityTag"));
               }

               if (stack.m_41783_().m_128441_("EntityUUID")) {
                  entity.m_20084_(stack.m_41783_().m_128342_("EntityUUID"));
               }

               entity.m_19890_((double)context.m_8083_().m_123341_() + 0.5D, (double)(context.m_8083_().m_123342_() + 1), (double)context.m_8083_().m_123343_() + 0.5D, 180.0F + context.m_8125_().m_122435_(), 0.0F);
               if (world.m_7967_(entity)) {
                  CompoundTag tag = stack.m_41783_();
                  tag.m_128473_("DragonHornEntityID");
                  tag.m_128473_("EntityTag");
                  tag.m_128473_("EntityUUID");
                  stack.m_41751_(tag);
               }
            }
         }

         return InteractionResult.SUCCESS;
      }
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      if (stack.m_41783_() != null) {
         CompoundTag entityTag = stack.m_41783_().m_128469_("EntityTag");
         if (!entityTag.m_128456_()) {
            String id = stack.m_41783_().m_128461_("DragonHornEntityID");
            if (EntityType.m_20632_(id).isPresent()) {
               EntityType type = (EntityType)EntityType.m_20632_(id).get();
               tooltip.add(Component.m_237115_(type.m_20675_()).m_130940_(this.getTextColorForEntityType(type)));
               String name = Component.m_237115_("dragon.unnamed").getString();
               if (!entityTag.m_128461_("CustomName").isEmpty()) {
                  MutableComponent component = Serializer.m_130701_(entityTag.m_128461_("CustomName"));
                  if (component != null) {
                     name = component.getString();
                  }
               }

               tooltip.add(Component.m_237113_(name).m_130940_(ChatFormatting.GRAY));
               String var10000 = Component.m_237115_("dragon.gender").getString();
               String gender = var10000 + " " + Component.m_237115_(entityTag.m_128471_("Gender") ? "dragon.gender.male" : "dragon.gender.female").getString();
               tooltip.add(Component.m_237113_(gender).m_130940_(ChatFormatting.GRAY));
               int stagenumber = entityTag.m_128451_("AgeTicks") / 24000;
               int stage1 = false;
               byte stage1;
               if (stagenumber >= 100) {
                  stage1 = 5;
               } else if (stagenumber >= 75) {
                  stage1 = 4;
               } else if (stagenumber >= 50) {
                  stage1 = 3;
               } else if (stagenumber >= 25) {
                  stage1 = 2;
               } else {
                  stage1 = 1;
               }

               String stage = Component.m_237115_("dragon.stage").getString() + " " + stage1 + " " + Component.m_237115_("dragon.days.front").getString() + stagenumber + " " + Component.m_237115_("dragon.days.back").getString();
               tooltip.add(Component.m_237113_(stage).m_130940_(ChatFormatting.GRAY));
            }
         }
      }

   }

   private ChatFormatting getTextColorForEntityType(EntityType type) {
      if (type == IafEntityRegistry.FIRE_DRAGON.get()) {
         return ChatFormatting.DARK_RED;
      } else if (type == IafEntityRegistry.ICE_DRAGON.get()) {
         return ChatFormatting.BLUE;
      } else {
         return type == IafEntityRegistry.LIGHTNING_DRAGON.get() ? ChatFormatting.DARK_PURPLE : ChatFormatting.GRAY;
      }
   }
}
