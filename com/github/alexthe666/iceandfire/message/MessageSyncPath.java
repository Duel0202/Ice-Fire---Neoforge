package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.iceandfire.client.render.pathfinding.PathfindingDebugRenderer;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.MNode;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.network.FriendlyByteBuf;
import net.neoforge.fml.LogicalSide;
import net.neoforge.network.NetworkDirection;
import net.neoforge.network.NetworkEvent.Context;

public class MessageSyncPath {
   public Set<MNode> lastDebugNodesVisited = new HashSet();
   public Set<MNode> lastDebugNodesNotVisited = new HashSet();
   public Set<MNode> lastDebugNodesPath = new HashSet();

   public MessageSyncPath(Set<MNode> lastDebugNodesVisited, Set<MNode> lastDebugNodesNotVisited, Set<MNode> lastDebugNodesPath) {
      this.lastDebugNodesVisited = lastDebugNodesVisited;
      this.lastDebugNodesNotVisited = lastDebugNodesNotVisited;
      this.lastDebugNodesPath = lastDebugNodesPath;
   }

   public void write(FriendlyByteBuf buf) {
      buf.writeInt(this.lastDebugNodesVisited.size());
      Iterator var2 = this.lastDebugNodesVisited.iterator();

      MNode MNode;
      while(var2.hasNext()) {
         MNode = (MNode)var2.next();
         MNode.serializeToBuf(buf);
      }

      buf.writeInt(this.lastDebugNodesNotVisited.size());
      var2 = this.lastDebugNodesNotVisited.iterator();

      while(var2.hasNext()) {
         MNode = (MNode)var2.next();
         MNode.serializeToBuf(buf);
      }

      buf.writeInt(this.lastDebugNodesPath.size());
      var2 = this.lastDebugNodesPath.iterator();

      while(var2.hasNext()) {
         MNode = (MNode)var2.next();
         MNode.serializeToBuf(buf);
      }

   }

   public static MessageSyncPath read(FriendlyByteBuf buf) {
      int size = buf.readInt();
      Set<MNode> lastDebugNodesVisited = new HashSet();

      for(int i = 0; i < size; ++i) {
         lastDebugNodesVisited.add(new MNode(buf));
      }

      size = buf.readInt();
      Set<MNode> lastDebugNodesNotVisited = new HashSet();

      for(int i = 0; i < size; ++i) {
         lastDebugNodesNotVisited.add(new MNode(buf));
      }

      size = buf.readInt();
      Set<MNode> lastDebugNodesPath = new HashSet();

      for(int i = 0; i < size; ++i) {
         lastDebugNodesPath.add(new MNode(buf));
      }

      return new MessageSyncPath(lastDebugNodesVisited, lastDebugNodesNotVisited, lastDebugNodesPath);
   }

   public boolean handle(Supplier<Context> contextSupplier) {
      ((Context)contextSupplier.get()).enqueueWork(() -> {
         ((Context)contextSupplier.get()).setPacketHandled(true);
         if (((Context)contextSupplier.get()).getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            PathfindingDebugRenderer.lastDebugNodesVisited = this.lastDebugNodesVisited;
            PathfindingDebugRenderer.lastDebugNodesNotVisited = this.lastDebugNodesNotVisited;
            PathfindingDebugRenderer.lastDebugNodesPath = this.lastDebugNodesPath;
         }

      });
      return true;
   }

   public LogicalSide getExecutionSide() {
      return LogicalSide.CLIENT;
   }
}
