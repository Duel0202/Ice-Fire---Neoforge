package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.render.tile.RenderDeathWormGauntlet;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforge.common.util.NonNullLazy;
import org.jetbrains.annotations.NotNull;

public class ItemDeathwormGauntlet extends Item {
   private boolean deathwormReceded = true;
   private boolean deathwormLaunched = false;
   private int specialDamage = 0;

   public ItemDeathwormGauntlet() {
      super((new Properties()).m_41503_(500));
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> {
            return new RenderDeathWormGauntlet(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
         });

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return (BlockEntityWithoutLevelRenderer)renderer.get();
         }
      });
   }

   public int m_8105_(@NotNull ItemStack stack) {
      return 1;
   }

   @NotNull
   public UseAnim m_6164_(@NotNull ItemStack stack) {
      return UseAnim.BOW;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = playerIn.m_21120_(hand);
      playerIn.m_6672_(hand);
      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void m_5929_(@NotNull Level level, @NotNull LivingEntity entity, @NotNull ItemStack stack, int count) {
      if (!this.deathwormReceded && !this.deathwormLaunched && entity instanceof Player) {
         Player player = (Player)entity;
         CompoundTag tag = stack.m_41784_();
         if (tag.m_128451_("HolderID") != player.m_19879_()) {
            tag.m_128405_("HolderID", player.m_19879_());
         }

         if (player.m_36335_().m_41521_(this, 0.0F) == 0.0F) {
            player.m_36335_().m_41524_(this, 10);
            player.m_5496_(IafSoundRegistry.DEATHWORM_ATTACK, 1.0F, 1.0F);
            this.deathwormReceded = false;
            this.deathwormLaunched = true;
         }
      }

   }

   public void m_5551_(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity LivingEntity, int timeLeft) {
      if (this.specialDamage > 0) {
         stack.m_41622_(this.specialDamage, LivingEntity, (player) -> {
            player.m_21190_(LivingEntity.m_7655_());
         });
         this.specialDamage = 0;
      }

      CompoundTag tag = stack.m_41784_();
      if (tag.m_128451_("HolderID") != -1) {
         tag.m_128405_("HolderID", -1);
      }

   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return !ItemStack.m_41656_(oldStack, newStack);
   }

   public void m_6883_(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
      if (entity instanceof LivingEntity) {
         EntityDataProvider.getCapability(entity).ifPresent((data) -> {
            int tempLungeTicks = data.miscData.lungeTicks;
            if (this.deathwormReceded) {
               if (tempLungeTicks > 0) {
                  tempLungeTicks -= 4;
               }

               if (tempLungeTicks <= 0) {
                  tempLungeTicks = 0;
                  this.deathwormReceded = false;
                  this.deathwormLaunched = false;
               }
            } else if (this.deathwormLaunched) {
               tempLungeTicks += 4;
               if (tempLungeTicks > 20) {
                  this.deathwormReceded = true;
               }
            }

            if (data.miscData.lungeTicks == 20 && entity instanceof Player) {
               Player player = (Player)entity;
               Vec3 Vector3d = player.m_20252_(1.0F).m_82541_();
               double range = 5.0D;
               Iterator var9 = world.m_45976_(LivingEntity.class, new AABB(player.m_20185_() - range, player.m_20186_() - range, player.m_20189_() - range, player.m_20185_() + range, player.m_20186_() + range, player.m_20189_() + range)).iterator();

               label45:
               while(true) {
                  LivingEntity livingEntity;
                  do {
                     if (!var9.hasNext()) {
                        break label45;
                     }

                     livingEntity = (LivingEntity)var9.next();
                  } while(livingEntity == entity);

                  Vec3 Vector3d1 = new Vec3(livingEntity.m_20185_() - player.m_20185_(), livingEntity.m_20186_() - player.m_20186_(), livingEntity.m_20189_() - player.m_20189_());
                  double d0 = Vector3d1.m_82553_();
                  Vector3d1 = Vector3d1.m_82541_();
                  double d1 = Vector3d.m_82526_(Vector3d1);
                  boolean canSee = d1 > 1.0D - 0.5D / d0 && player.m_142582_(livingEntity);
                  if (canSee) {
                     ++this.specialDamage;
                     livingEntity.m_6469_(entity.m_9236_().m_269111_().m_269075_((Player)entity), 3.0F);
                     livingEntity.m_147240_(0.5D, livingEntity.m_20185_() - player.m_20185_(), livingEntity.m_20189_() - player.m_20189_());
                  }
               }
            }

            data.miscData.setLungeTicks(tempLungeTicks);
         });
      }
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.deathworm_gauntlet.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.deathworm_gauntlet.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
