package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.network.NetworkEvent.Context;

public class MessageHippogryphArmor {
   public int dragonId;
   public int slot_index;
   public int armor_type;

   public MessageHippogryphArmor(int dragonId, int slot_index, int armor_type) {
      this.dragonId = dragonId;
      this.slot_index = slot_index;
      this.armor_type = armor_type;
   }

   public MessageHippogryphArmor() {
   }

   public static MessageHippogryphArmor read(FriendlyByteBuf buf) {
      return new MessageHippogryphArmor(buf.readInt(), buf.readInt(), buf.readInt());
   }

   public static void write(MessageHippogryphArmor message, FriendlyByteBuf buf) {
      buf.writeInt(message.dragonId);
      buf.writeInt(message.slot_index);
      buf.writeInt(message.armor_type);
   }

   public static class Handler {
      public static void handle(MessageHippogryphArmor message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (player != null) {
               Entity entity = player.m_9236_().m_6815_(message.dragonId);
               if (entity instanceof EntityHippogryph) {
                  EntityHippogryph hippogryph = (EntityHippogryph)entity;
                  if (message.slot_index == 0) {
                     hippogryph.setSaddled(message.armor_type == 1);
                  } else if (message.slot_index == 1) {
                     hippogryph.setChested(message.armor_type == 1);
                  } else if (message.slot_index == 2) {
                     hippogryph.setArmor(message.armor_type);
                  }
               } else if (entity instanceof EntityHippocampus) {
                  EntityHippocampus hippo = (EntityHippocampus)entity;
                  if (message.slot_index == 0) {
                     hippo.setSaddled(message.armor_type == 1);
                  } else if (message.slot_index == 1) {
                     hippo.setChested(message.armor_type == 1);
                  } else if (message.slot_index == 2) {
                     hippo.setArmor(message.armor_type);
                  }
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
