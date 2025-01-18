package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityJar;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityPixieHouse;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkEvent.Context;

public class MessageUpdatePixieHouseModel {
   public long blockPos;
   public int houseType;

   public MessageUpdatePixieHouseModel(long blockPos, int houseType) {
      this.blockPos = blockPos;
      this.houseType = houseType;
   }

   public MessageUpdatePixieHouseModel() {
   }

   public static MessageUpdatePixieHouseModel read(FriendlyByteBuf buf) {
      return new MessageUpdatePixieHouseModel(buf.readLong(), buf.readInt());
   }

   public static void write(MessageUpdatePixieHouseModel message, FriendlyByteBuf buf) {
      buf.writeLong(message.blockPos);
      buf.writeInt(message.houseType);
   }

   public static class Handler {
      public static void handle(MessageUpdatePixieHouseModel message, Supplier<Context> contextSupplier) {
         Context context = (Context)contextSupplier.get();
         context.enqueueWork(() -> {
            Player player = context.getSender();
            if (context.getDirection().getReceptionSide() == LogicalSide.CLIENT) {
               player = IceAndFire.PROXY.getClientSidePlayer();
            }

            if (player != null) {
               BlockPos pos = BlockPos.m_122022_(message.blockPos);
               BlockEntity blockEntity = ((Player)player).m_9236_().m_7702_(pos);
               if (blockEntity instanceof TileEntityPixieHouse) {
                  TileEntityPixieHouse house = (TileEntityPixieHouse)blockEntity;
                  house.houseType = message.houseType;
               } else if (blockEntity instanceof TileEntityJar) {
                  TileEntityJar jar = (TileEntityJar)blockEntity;
                  jar.pixieType = message.houseType;
               }
            }

         });
         context.setPacketHandled(true);
      }
   }
}
