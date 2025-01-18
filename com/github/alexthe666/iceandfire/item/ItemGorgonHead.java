package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.render.tile.RenderGorgonHead;
import com.github.alexthe666.iceandfire.datagen.tags.IafEntityTags;
import com.github.alexthe666.iceandfire.entity.EntityStoneStatue;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.base.Predicate;
import java.util.List;
import java.util.Optional;
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
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
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

public class ItemGorgonHead extends Item {
   public ItemGorgonHead() {
      super((new Properties()).m_41503_(1));
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> {
            return new RenderGorgonHead(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
         });

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return (BlockEntityWithoutLevelRenderer)renderer.get();
         }
      });
   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
   }

   public int m_8105_(@NotNull ItemStack stack) {
      return 72000;
   }

   @NotNull
   public UseAnim m_6164_(@NotNull ItemStack stack) {
      return UseAnim.BOW;
   }

   public void m_5551_(@NotNull ItemStack stack, Level worldIn, LivingEntity entity, int timeLeft) {
      double dist = 32.0D;
      Vec3 Vector3d = entity.m_20299_(1.0F);
      Vec3 Vector3d1 = entity.m_20252_(1.0F);
      Vec3 Vector3d2 = Vector3d.m_82520_(Vector3d1.f_82479_ * dist, Vector3d1.f_82480_ * dist, Vector3d1.f_82481_ * dist);
      Entity pointedEntity = null;
      List<Entity> list = worldIn.m_6249_(entity, entity.m_20191_().m_82363_(Vector3d1.f_82479_ * dist, Vector3d1.f_82480_ * dist, Vector3d1.f_82481_ * dist).m_82377_(1.0D, 1.0D, 1.0D), new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            if (!(entity instanceof LivingEntity)) {
               return false;
            } else {
               boolean var10000;
               LivingEntity livingEntity;
               label39: {
                  label38: {
                     livingEntity = (LivingEntity)entity;
                     if (livingEntity instanceof IBlacklistedFromStatues) {
                        IBlacklistedFromStatues blacklisted = (IBlacklistedFromStatues)livingEntity;
                        if (!blacklisted.canBeTurnedToStone()) {
                           break label38;
                        }
                     }

                     if (!entity.m_6095_().m_204039_(IafEntityTags.IMMUNE_TO_GORGON_STONE) && !livingEntity.m_21023_(MobEffects.f_19610_)) {
                        var10000 = false;
                        break label39;
                     }
                  }

                  var10000 = true;
               }

               boolean isImmune = var10000;
               return !isImmune && entity.m_6087_() && !livingEntity.m_21224_() && (entity instanceof Player || DragonUtils.isAlive(livingEntity));
            }
         }
      });
      double d2 = dist;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         AABB axisalignedbb = entity1.m_20191_().m_82400_((double)entity1.m_6143_());
         Optional<Vec3> optional = axisalignedbb.m_82371_(Vector3d, Vector3d2);
         if (axisalignedbb.m_82390_(Vector3d)) {
            if (d2 >= 0.0D) {
               d2 = 0.0D;
            }
         } else if (optional.isPresent()) {
            double d3 = Vector3d.m_82554_((Vec3)optional.get());
            if (d3 < d2 || d2 == 0.0D) {
               if (entity1.m_20201_() == entity.m_20201_() && !entity.canRiderInteract()) {
                  if (d2 == 0.0D) {
                     pointedEntity = entity1;
                  }
               } else {
                  pointedEntity = entity1;
                  d2 = d3;
               }
            }
         }
      }

      if (pointedEntity != null && pointedEntity instanceof LivingEntity) {
         LivingEntity livingEntity = (LivingEntity)pointedEntity;
         boolean wasSuccesful = true;
         if (pointedEntity instanceof Player) {
            wasSuccesful = pointedEntity.m_6469_(IafDamageRegistry.causeGorgonDamage(pointedEntity), 2.14748365E9F);
         } else if (!worldIn.f_46443_) {
            pointedEntity.m_142687_(RemovalReason.KILLED);
         }

         if (wasSuccesful) {
            pointedEntity.m_5496_(IafSoundRegistry.TURN_STONE, 1.0F, 1.0F);
            EntityStoneStatue statue = EntityStoneStatue.buildStatueEntity(livingEntity);
            statue.m_19890_(pointedEntity.m_20185_(), pointedEntity.m_20186_(), pointedEntity.m_20189_(), pointedEntity.m_146908_(), pointedEntity.m_146909_());
            statue.f_20883_ = pointedEntity.m_146908_();
            if (!worldIn.f_46443_) {
               worldIn.m_7967_(statue);
            }
         }

         if (entity instanceof Player) {
            Player player = (Player)entity;
            if (!player.m_7500_()) {
               stack.m_41774_(1);
            }
         }
      }

      stack.m_41783_().m_128379_("Active", false);
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = playerIn.m_21120_(hand);
      playerIn.m_6672_(hand);
      itemStackIn.m_41783_().m_128379_("Active", true);
      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
   }

   public void m_5929_(@NotNull Level level, @NotNull LivingEntity player, @NotNull ItemStack stack, int count) {
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
   }
}
