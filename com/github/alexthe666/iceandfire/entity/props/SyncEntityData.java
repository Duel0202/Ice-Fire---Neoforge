package com.github.alexthe666.iceandfire.entity.props;

import java.util.function.Supplier;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.neoforge.network.NetworkDirection;
import net.neoforge.network.NetworkEvent.Context;

public record SyncEntityData(int entityId, CompoundTag tag) {
   public SyncEntityData(int entityId, CompoundTag tag) {
      this.entityId = entityId;
      this.tag = tag;
   }

   public void encode(FriendlyByteBuf buffer) {
      buffer.writeInt(this.entityId);
      buffer.m_130079_(this.tag);
   }

   public static SyncEntityData decode(FriendlyByteBuf buffer) {
      return new SyncEntityData(buffer.readInt(), buffer.m_130260_());
   }

   public static void handle(SyncEntityData message, Supplier<Context> contextSupplier) {
      Context context = (Context)contextSupplier.get();
      if (context.getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
         context.enqueueWork(() -> {
            Player localPlayer = CapabilityHandler.getLocalPlayer();
            if (localPlayer != null) {
               EntityDataProvider.getCapability(localPlayer.m_9236_().m_6815_(message.entityId)).ifPresent((data) -> {
                  data.deserialize(message.tag);
               });
            }

         });
      }

      context.setPacketHandled(true);
   }

   public int entityId() {
      return this.entityId;
   }

   public CompoundTag tag() {
      return this.tag;
   }
}
