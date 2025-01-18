package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.client.render.pathfinding.PathfindingDebugRenderer;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.MNode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkDirection;
import net.neoforge.network.NetworkEvent.Context;

public class MessageSyncPathReached {
   public Set<BlockPos> reached = new HashSet();

   public MessageSyncPathReached(Set<BlockPos> reached) {
      this.reached = reached;
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.reached.size());
      Iterator var2 = this.reached.iterator();

      while(var2.hasNext()) {
         BlockPos node = (BlockPos)var2.next();
         buf.m_130064_(node);
      }

   }

   public static MessageSyncPathReached read(FriendlyByteBuf buf) {
      int size = buf.readInt();
      Set<BlockPos> reached = new HashSet();

      for(int i = 0; i < size; ++i) {
         reached.add(buf.m_130135_());
      }

      return new MessageSyncPathReached(reached);
   }

   public LogicalSide getExecutionSide() {
      return LogicalSide.CLIENT;
   }

   public boolean handle(Supplier<Context> contextSupplier) {
      ((Context)contextSupplier.get()).enqueueWork(() -> {
         ((Context)contextSupplier.get()).setPacketHandled(true);
         if (((Context)contextSupplier.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            Iterator var2 = PathfindingDebugRenderer.lastDebugNodesPath.iterator();

            while(var2.hasNext()) {
               MNode node = (MNode)var2.next();
               if (this.reached.contains(node.pos)) {
                  node.setReachedByWorker(true);
               }
            }
         }

      });
      return true;
   }
}
