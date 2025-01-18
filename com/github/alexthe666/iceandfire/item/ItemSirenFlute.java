package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class ItemSirenFlute extends Item {
   public ItemSirenFlute() {
      super((new Properties()).m_41503_(200));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player player, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = player.m_21120_(hand);
      player.m_6672_(hand);
      player.m_36335_().m_41524_(this, 900);
      double dist = 32.0D;
      Vec3 Vector3d = player.m_20299_(1.0F);
      Vec3 Vector3d1 = player.m_20252_(1.0F);
      Vec3 Vector3d2 = Vector3d.m_82520_(Vector3d1.f_82479_ * dist, Vector3d1.f_82480_ * dist, Vector3d1.f_82481_ * dist);
      Entity pointedEntity = null;
      List<Entity> list = player.m_9236_().m_6249_(player, player.m_20191_().m_82363_(Vector3d1.f_82479_ * dist, Vector3d1.f_82480_ * dist, Vector3d1.f_82481_ * dist).m_82377_(1.0D, 1.0D, 1.0D), new Predicate<Entity>() {
         public boolean test(Entity entity) {
            boolean blindness = entity instanceof LivingEntity && ((LivingEntity)entity).m_21023_(MobEffects.f_19610_) || entity instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)entity).canBeTurnedToStone();
            return entity != null && entity.m_6087_() && !blindness && (entity instanceof Player || entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity));
         }
      });
      double d2 = dist;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         AABB axisalignedbb = entity1.m_20191_().m_82400_((double)entity1.m_6143_());
         Optional<Vec3> raytraceresult = axisalignedbb.m_82371_(Vector3d, Vector3d2);
         if (axisalignedbb.m_82390_(Vector3d)) {
            if (d2 >= 0.0D) {
               pointedEntity = entity1;
               d2 = 0.0D;
            }
         } else if (raytraceresult.isPresent()) {
            double d3 = Vector3d.m_82554_((Vec3)raytraceresult.get());
            if (d3 < d2 || d2 == 0.0D) {
               if (entity1.m_20201_() == player.m_20201_() && !player.canRiderInteract()) {
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
         EntityDataProvider.getCapability(pointedEntity).ifPresent((data) -> {
            data.miscData.setLoveTicks(600);
         });
         itemStackIn.m_41622_(2, player, (entity) -> {
            entity.m_21166_(EquipmentSlot.MAINHAND);
         });
      }

      player.m_5496_(IafSoundRegistry.SIREN_SONG, 1.0F, 1.0F);
      return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.siren_flute.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.siren_flute.desc_1").m_130940_(ChatFormatting.GRAY));
   }
}
