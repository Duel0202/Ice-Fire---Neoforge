package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class MNode implements Comparable<MNode> {
   private static final int HASH_A = 12;
   private static final int HASH_B = 20;
   private static final int HASH_C = 24;
   public final BlockPos pos;
   private final int hash;
   @Nullable
   public MNode parent;
   private int counterAdded;
   private int counterVisited;
   private int steps;
   private double cost;
   private double heuristic;
   private double score;
   private boolean closed;
   private boolean ladder;
   private boolean swimming;
   private boolean isOnRails;
   private boolean isCornerNode;
   private boolean isReachedByWorker;

   public MNode(BlockPos pos, double heuristic) {
      this((MNode)null, pos, 0.0D, heuristic, heuristic);
   }

   public MNode(@Nullable MNode parent, BlockPos pos, double cost, double heuristic, double score) {
      this.closed = false;
      this.ladder = false;
      this.swimming = false;
      this.isOnRails = false;
      this.isCornerNode = false;
      this.isReachedByWorker = false;
      this.parent = parent;
      this.pos = pos;
      this.steps = parent == null ? 0 : parent.steps + 1;
      this.cost = cost;
      this.heuristic = heuristic;
      this.score = score;
      this.hash = pos.m_123341_() ^ (pos.m_123343_() << 12 | pos.m_123343_() >> 20) ^ pos.m_123342_() << 24;
   }

   public MNode(FriendlyByteBuf byteBuf) {
      this.closed = false;
      this.ladder = false;
      this.swimming = false;
      this.isOnRails = false;
      this.isCornerNode = false;
      this.isReachedByWorker = false;
      if (byteBuf.readBoolean()) {
         this.parent = new MNode(byteBuf.m_130135_(), 0.0D);
      }

      this.pos = byteBuf.m_130135_();
      this.cost = byteBuf.readDouble();
      this.heuristic = byteBuf.readDouble();
      this.score = byteBuf.readDouble();
      this.hash = this.pos.m_123341_() ^ (this.pos.m_123343_() << 12 | this.pos.m_123343_() >> 20) ^ this.pos.m_123342_() << 24;
      this.isReachedByWorker = byteBuf.readBoolean();
   }

   public void serializeToBuf(FriendlyByteBuf byteBuf) {
      byteBuf.writeBoolean(this.parent != null);
      if (this.parent != null) {
         byteBuf.m_130064_(this.parent.pos);
      }

      byteBuf.m_130064_(this.pos);
      byteBuf.writeDouble(this.cost);
      byteBuf.writeDouble(this.heuristic);
      byteBuf.writeDouble(this.score);
      byteBuf.writeBoolean(this.isReachedByWorker);
   }

   public int compareTo(MNode o) {
      if (this.score < o.score) {
         return -1;
      } else if (this.score > o.score) {
         return 1;
      } else if (this.heuristic < o.heuristic) {
         return -1;
      } else {
         return this.heuristic > o.heuristic ? 1 : this.counterAdded - o.counterAdded;
      }
   }

   public int hashCode() {
      return this.hash;
   }

   public boolean equals(@Nullable Object o) {
      if (o != null && o.getClass() == this.getClass()) {
         MNode other = (MNode)o;
         return this.pos.m_123341_() == other.pos.m_123341_() && this.pos.m_123342_() == other.pos.m_123342_() && this.pos.m_123343_() == other.pos.m_123343_();
      } else {
         return false;
      }
   }

   public boolean isClosed() {
      return this.closed;
   }

   public boolean isLadder() {
      return this.ladder;
   }

   public boolean isSwimming() {
      return this.swimming;
   }

   public void setClosed() {
      this.closed = true;
   }

   public int getCounterVisited() {
      return this.counterVisited;
   }

   public void setCounterVisited(int counterVisited) {
      this.counterVisited = counterVisited;
   }

   public double getScore() {
      return this.score;
   }

   public void setScore(double score) {
      this.score = score;
   }

   public double getCost() {
      return this.cost;
   }

   public void setCost(double cost) {
      this.cost = cost;
   }

   public int getSteps() {
      return this.steps;
   }

   public void setSteps(int steps) {
      this.steps = steps;
   }

   public void setLadder() {
      this.ladder = true;
   }

   public void setSwimming() {
      this.swimming = true;
   }

   public double getHeuristic() {
      return this.heuristic;
   }

   public void setHeuristic(double heuristic) {
      this.heuristic = heuristic;
   }

   public int getCounterAdded() {
      return this.counterAdded;
   }

   public void setCounterAdded(int counterAdded) {
      this.counterAdded = counterAdded;
   }

   public void setOnRails(boolean isOnRails) {
      this.isOnRails = isOnRails;
   }

   public boolean isOnRails() {
      return this.isOnRails;
   }

   public void setReachedByWorker(boolean bool) {
      this.isReachedByWorker = bool;
   }

   public boolean isReachedByWorker() {
      return this.isReachedByWorker;
   }

   public void setCornerNode(boolean isCornerNode) {
      this.isCornerNode = isCornerNode;
   }

   public boolean isCornerNode() {
      return this.isCornerNode;
   }
}
