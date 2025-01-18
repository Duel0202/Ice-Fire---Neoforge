package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforge.network.NetworkEvent.Context;

public class MessageSetMyrmexHiveNull {
   public static MessageSetMyrmexHiveNull read(FriendlyByteBuf buf) {
      return new MessageSetMyrmexHiveNull();
   }

   public static void write(MessageSetMyrmexHiveNull message, FriendlyByteBuf buf) {
   }

   public static class Handler {
      public static void handle(MessageSetMyrmexHiveNull message, Supplier<Context> context) {
         ((Context)context.get()).setPacketHandled(true);
         Player player = ((Context)context.get()).getSender();
         if (player != null) {
            IceAndFire.PROXY.setReferencedHive((MyrmexHive)null);
         }

      }
   }
}
