package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Predicate;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import org.jetbrains.annotations.NotNull;

public class ItemCockatriceScepter extends Item {
   private final Random rand = new Random();
   private int specialWeaponDmg;

   public ItemCockatriceScepter() {
      super((new Properties()).m_41503_(700));
   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.cockatrice_scepter.desc_0").m_130940_(ChatFormatting.GRAY));
      tooltip.add(Component.m_237115_("item.iceandfire.cockatrice_scepter.desc_1").m_130940_(ChatFormatting.GRAY));
   }

   public void m_5551_(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull LivingEntity livingEntity, int timeLeft) {
      if (this.specialWeaponDmg > 0) {
         stack.m_41622_(this.specialWeaponDmg, livingEntity, (player) -> {
            player.m_21190_(livingEntity.m_7655_());
         });
         this.specialWeaponDmg = 0;
      }

      EntityDataProvider.getCapability(livingEntity).ifPresent((data) -> {
         data.miscData.getTargetedByScepter().clear();
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

   public void m_5929_(Level level, LivingEntity player, ItemStack stack, int count) {
      if (player instanceof Player) {
         double dist = 32.0D;
         Vec3 playerEyePosition = player.m_20299_(1.0F);
         Vec3 playerLook = player.m_20252_(1.0F);
         Vec3 Vector3d2 = playerEyePosition.m_82520_(playerLook.f_82479_ * dist, playerLook.f_82480_ * dist, playerLook.f_82481_ * dist);
         Entity pointedEntity = null;
         List<Entity> nearbyEntities = level.m_6249_(player, player.m_20191_().m_82363_(playerLook.f_82479_ * dist, playerLook.f_82480_ * dist, playerLook.f_82481_ * dist).m_82377_(1.0D, 1.0D, 1.0D), new Predicate<Entity>() {
            public boolean test(Entity entity) {
               boolean blindness = entity instanceof LivingEntity && ((LivingEntity)entity).m_21023_(MobEffects.f_19610_) || entity instanceof IBlacklistedFromStatues && !((IBlacklistedFromStatues)entity).canBeTurnedToStone();
               return entity != null && entity.m_6087_() && !blindness && (entity instanceof Player || entity instanceof LivingEntity && DragonUtils.isAlive((LivingEntity)entity));
            }
         });
         double d2 = dist;
         Iterator var14 = nearbyEntities.iterator();

         while(true) {
            while(var14.hasNext()) {
               Entity nearbyEntity = (Entity)var14.next();
               AABB axisalignedbb = nearbyEntity.m_20191_().m_82400_((double)nearbyEntity.m_6143_());
               Optional<Vec3> optional = axisalignedbb.m_82371_(playerEyePosition, Vector3d2);
               if (axisalignedbb.m_82390_(playerEyePosition)) {
                  if (d2 >= 0.0D) {
                     pointedEntity = nearbyEntity;
                     d2 = 0.0D;
                  }
               } else if (optional.isPresent()) {
                  double d3 = playerEyePosition.m_82554_((Vec3)optional.get());
                  if (d3 < d2 || d2 == 0.0D) {
                     if (nearbyEntity.m_20201_() == player.m_20201_() && !player.canRiderInteract()) {
                        if (d2 == 0.0D) {
                           pointedEntity = nearbyEntity;
                        }
                     } else {
                        pointedEntity = nearbyEntity;
                        d2 = d3;
                     }
                  }
               }
            }

            if (pointedEntity instanceof LivingEntity) {
               LivingEntity target = (LivingEntity)pointedEntity;
               if (!target.m_6084_()) {
                  return;
               }

               EntityDataProvider.getCapability(player).ifPresent((data) -> {
                  data.miscData.addScepterTarget(target);
               });
            }

            this.attackTargets(player);
            break;
         }
      }

   }

   private void attackTargets(LivingEntity caster) {
      EntityDataProvider.getCapability(caster).ifPresent((data) -> {
         List<LivingEntity> targets = new ArrayList(data.miscData.getTargetedByScepter());
         Iterator var4 = targets.iterator();

         while(true) {
            while(var4.hasNext()) {
               LivingEntity target = (LivingEntity)var4.next();
               if (EntityGorgon.isEntityLookingAt(caster, target, 0.20000000298023224D) && caster.m_6084_() && target.m_6084_()) {
                  target.m_7292_(new MobEffectInstance(MobEffects.f_19615_, 40, 2));
                  if (caster.f_19797_ % 20 == 0) {
                     ++this.specialWeaponDmg;
                     target.m_6469_(caster.m_9236_().m_269111_().m_269251_(), 2.0F);
                  }

                  this.drawParticleBeam(caster, target);
               } else {
                  data.miscData.removeScepterTarget(target);
               }
            }

            return;
         }
      });
   }

   private void drawParticleBeam(LivingEntity origin, LivingEntity target) {
      double d5 = 80.0D;
      double d0 = target.m_20185_() - origin.m_20185_();
      double d1 = target.m_20186_() + (double)(target.m_20206_() * 0.5F) - (origin.m_20186_() + (double)origin.m_20192_() * 0.5D);
      double d2 = target.m_20189_() - origin.m_20189_();
      double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
      d0 /= d3;
      d1 /= d3;
      d2 /= d3;
      double d4 = this.rand.nextDouble();

      while(d4 < d3) {
         ++d4;
         origin.m_9236_().m_7106_(ParticleTypes.f_123811_, origin.m_20185_() + d0 * d4, origin.m_20186_() + d1 * d4 + (double)origin.m_20192_() * 0.5D, origin.m_20189_() + d2 * d4, 0.0D, 0.0D, 0.0D);
      }

   }
}
