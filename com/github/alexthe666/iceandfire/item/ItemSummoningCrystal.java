package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.world.DragonPosWorldData;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemSummoningCrystal extends Item {
   public ItemSummoningCrystal() {
      super((new Properties()).m_41487_(1));
   }

   public static boolean hasDragon(ItemStack stack) {
      if (stack.m_41720_() instanceof ItemSummoningCrystal && stack.m_41783_() != null) {
         Iterator var1 = stack.m_41783_().m_128431_().iterator();

         while(var1.hasNext()) {
            String tagInfo = (String)var1.next();
            if (tagInfo.contains("Dragon")) {
               return true;
            }
         }
      }

      return false;
   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
   }

   public ItemStack onItemUseFinish(Level worldIn, LivingEntity LivingEntity) {
      return new ItemStack(this);
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      boolean flag = false;
      String desc = "entity.iceandfire.fire_dragon";
      if (stack.m_41720_() == IafItemRegistry.SUMMONING_CRYSTAL_ICE.get()) {
         desc = "entity.iceandfire.ice_dragon";
      }

      if (stack.m_41720_() == IafItemRegistry.SUMMONING_CRYSTAL_LIGHTNING.get()) {
         desc = "entity.iceandfire.lightning_dragon";
      }

      if (stack.m_41783_() != null) {
         Iterator var7 = stack.m_41783_().m_128431_().iterator();

         while(var7.hasNext()) {
            String tagInfo = (String)var7.next();
            if (tagInfo.contains("Dragon")) {
               CompoundTag dragonTag = stack.m_41783_().m_128469_(tagInfo);
               String dragonName = I18n.m_118938_(desc, new Object[0]);
               if (!dragonTag.m_128461_("CustomName").isEmpty()) {
                  dragonName = dragonTag.m_128461_("CustomName");
               }

               tooltip.add(Component.m_237110_("item.iceandfire.summoning_crystal.bound", new Object[]{dragonName}).m_130940_(ChatFormatting.GRAY));
               flag = true;
            }
         }
      }

      if (!flag) {
         tooltip.add(Component.m_237115_("item.iceandfire.summoning_crystal.desc_0").m_130940_(ChatFormatting.GRAY));
         tooltip.add(Component.m_237115_("item.iceandfire.summoning_crystal.desc_1").m_130940_(ChatFormatting.GRAY));
      }

   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      ItemStack stack = context.m_43723_().m_21120_(context.m_43724_());
      boolean flag = false;
      BlockPos offsetPos = context.m_8083_().m_121945_(context.m_43719_());
      float yaw = context.m_43723_().m_146908_();
      boolean displayError = false;
      if (stack.m_41720_() == this && hasDragon(stack)) {
         int dragonCount = 0;
         if (stack.m_41783_() != null) {
            Iterator var8 = stack.m_41783_().m_128431_().iterator();

            while(var8.hasNext()) {
               String tagInfo = (String)var8.next();
               if (tagInfo.contains("Dragon")) {
                  ++dragonCount;
                  CompoundTag dragonTag = stack.m_41783_().m_128469_(tagInfo);
                  UUID id = dragonTag.m_128342_("DragonUUID");
                  if (id != null && !context.m_43725_().f_46443_) {
                     try {
                        Entity entity = context.m_43725_().m_7654_().m_129880_(context.m_43723_().m_9236_().m_46472_()).m_8791_(id);
                        if (entity != null) {
                           flag = true;
                           this.summonEntity(entity, context.m_43725_(), offsetPos, yaw);
                        }
                     } catch (Exception var19) {
                        var19.printStackTrace();
                        displayError = true;
                     }

                     DragonPosWorldData data = DragonPosWorldData.get(context.m_43725_());
                     BlockPos dragonChunkPos = null;
                     if (data != null) {
                        dragonChunkPos = data.getDragonPos(id);
                     }

                     if (IafConfig.chunkLoadSummonCrystal) {
                        try {
                           boolean flag2 = false;
                           if (!flag && data != null && context.m_43725_().f_46443_) {
                              ServerLevel serverWorld = (ServerLevel)context.m_43725_();
                              ChunkPos pos = new ChunkPos(dragonChunkPos);
                              serverWorld.m_8602_(pos.f_45578_, pos.f_45579_, true);
                           }

                           if (flag2) {
                              try {
                                 Entity entity = context.m_43725_().m_7654_().m_129880_(context.m_43723_().m_9236_().m_46472_()).m_8791_(id);
                                 if (entity != null) {
                                    flag = true;
                                    this.summonEntity(entity, context.m_43725_(), offsetPos, yaw);
                                 }
                              } catch (Exception var17) {
                                 var17.printStackTrace();
                              }
                           }
                        } catch (Exception var18) {
                           IceAndFire.LOGGER.warn("Could not load chunk when summoning dragon");
                           var18.printStackTrace();
                        }
                     }
                  }
               }
            }
         }

         if (flag) {
            context.m_43723_().m_5496_(SoundEvents.f_11852_, 1.0F, 1.0F);
            context.m_43723_().m_5496_(SoundEvents.f_11983_, 1.0F, 1.0F);
            context.m_43723_().m_6674_(context.m_43724_());
            context.m_43723_().m_5661_(Component.m_237115_("message.iceandfire.dragonTeleport"), true);
            stack.m_41751_(new CompoundTag());
         } else if (displayError) {
            context.m_43723_().m_5661_(Component.m_237115_("message.iceandfire.noDragonTeleport"), true);
         }
      }

      return InteractionResult.PASS;
   }

   public void summonEntity(Entity entity, Level worldIn, BlockPos offsetPos, float yaw) {
      entity.m_7678_((double)offsetPos.m_123341_() + 0.5D, (double)offsetPos.m_123342_() + 0.5D, (double)offsetPos.m_123343_() + 0.5D, yaw, 0.0F);
      if (entity instanceof EntityDragonBase) {
         ((EntityDragonBase)entity).setCrystalBound(false);
      }

      if (IafConfig.chunkLoadSummonCrystal) {
         DragonPosWorldData data = DragonPosWorldData.get(worldIn);
         if (data != null) {
            data.removeDragon(entity.m_20148_());
         }
      }

   }
}
