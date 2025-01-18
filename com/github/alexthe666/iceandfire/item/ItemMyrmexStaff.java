package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.message.MessageGetMyrmexHive;
import com.github.alexthe666.iceandfire.message.MessageSetMyrmexHiveNull;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class ItemMyrmexStaff extends Item {
   public ItemMyrmexStaff(boolean jungle) {
      super((new Properties()).m_41487_(1));
   }

   public void m_7836_(ItemStack itemStack, @NotNull Level world, @NotNull Player player) {
      itemStack.m_41751_(new CompoundTag());
   }

   public void m_6883_(ItemStack stack, @NotNull Level world, @NotNull Entity entity, int itemSlot, boolean isSelected) {
      if (stack.m_41783_() == null) {
         stack.m_41751_(new CompoundTag());
         stack.m_41783_().m_128362_("HiveUUID", new UUID(0L, 0L));
      }

   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level worldIn, Player playerIn, @NotNull InteractionHand hand) {
      ItemStack itemStackIn = playerIn.m_21120_(hand);
      if (playerIn.m_6144_()) {
         return super.m_7203_(worldIn, playerIn, hand);
      } else {
         if (itemStackIn.m_41783_() != null && itemStackIn.m_41783_().m_128403_("HiveUUID")) {
            UUID id = itemStackIn.m_41783_().m_128342_("HiveUUID");
            if (!worldIn.f_46443_) {
               MyrmexHive hive = MyrmexWorldData.get(worldIn).getHiveFromUUID(id);
               MyrmexWorldData.addHive(worldIn, new MyrmexHive());
               if (hive != null) {
                  IceAndFire.sendMSGToAll(new MessageGetMyrmexHive(hive.toNBT()));
               } else {
                  IceAndFire.sendMSGToAll(new MessageSetMyrmexHiveNull());
               }
            } else if (id != null && !id.equals(new UUID(0L, 0L))) {
               IceAndFire.PROXY.openMyrmexStaffGui(itemStackIn);
            }
         }

         playerIn.m_6674_(hand);
         return new InteractionResultHolder(InteractionResult.PASS, itemStackIn);
      }
   }

   @NotNull
   public InteractionResult m_6225_(UseOnContext context) {
      if (!context.m_43723_().m_6144_()) {
         return super.m_6225_(context);
      } else {
         CompoundTag tag = context.m_43723_().m_21120_(context.m_43724_()).m_41783_();
         if (tag != null && tag.m_128403_("HiveUUID")) {
            UUID id = tag.m_128342_("HiveUUID");
            if (!context.m_43725_().f_46443_) {
               MyrmexHive hive = MyrmexWorldData.get(context.m_43725_()).getHiveFromUUID(id);
               if (hive != null) {
                  IceAndFire.sendMSGToAll(new MessageGetMyrmexHive(hive.toNBT()));
               } else {
                  IceAndFire.sendMSGToAll(new MessageSetMyrmexHiveNull());
               }
            } else if (id != null && !id.equals(new UUID(0L, 0L))) {
               IceAndFire.PROXY.openMyrmexAddRoomGui(context.m_43723_().m_21120_(context.m_43724_()), context.m_8083_(), context.m_43723_().m_6350_());
            }
         }

         context.m_43723_().m_6674_(context.m_43724_());
         return InteractionResult.SUCCESS;
      }
   }
}
