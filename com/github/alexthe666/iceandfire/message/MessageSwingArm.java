package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.event.ServerEvents;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.neoforge.network.NetworkEvent.Context;

public class MessageSwingArm {
   public static MessageSwingArm read(FriendlyByteBuf buf) {
      return new MessageSwingArm();
   }

   public static void write(MessageSwingArm message, FriendlyByteBuf buf) {
   }

   public static class Handler {
      public static void handle(MessageSwingArm message, Supplier<Context> context) {
         ((Context)context.get()).setPacketHandled(true);
         Player player = ((Context)context.get()).getSender();
         if (player != null) {
            ServerEvents.onLeftClick(player, player.m_21120_(InteractionHand.MAIN_HAND));
         }

      }
   }
}
