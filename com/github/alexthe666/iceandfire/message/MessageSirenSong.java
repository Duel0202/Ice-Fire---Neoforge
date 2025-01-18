package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntitySiren;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageSirenSong {
   public int sirenId;
   public boolean isSinging;

   public MessageSirenSong(int sirenId, boolean isSinging) {
      this.sirenId = sirenId;
      this.isSinging = isSinging;
   }

   public MessageSirenSong() {
   }

   public static MessageSirenSong read(FriendlyByteBuf buf) {
      return new MessageSirenSong(buf.readInt(), buf.readBoolean());
   }

   public static void write(MessageSirenSong message, FriendlyByteBuf buf) {
      buf.writeInt(message.sirenId);
      buf.writeBoolean(message.isSinging);
   }

   public static class Handler {
      public static void handle(MessageSirenSong message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               Entity entity = ((Player)player).m_9236_().m_6815_(message.sirenId);
               if (entity instanceof EntitySiren) {
                  EntitySiren siren = (EntitySiren)entity;
                  siren.setSinging(message.isSinging);
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
