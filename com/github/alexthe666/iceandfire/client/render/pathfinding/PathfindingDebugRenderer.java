package com.github.alexthe666.iceandfire.client.render.pathfinding;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.MNode;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.WorldEventContext;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.WorldRenderMacros;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class PathfindingDebugRenderer {
   public static final RenderBuffers renderBuffers = new RenderBuffers();
   private static final BufferSource renderBuffer;
   public static Set<MNode> lastDebugNodesVisited;
   public static Set<MNode> lastDebugNodesNotVisited;
   public static Set<MNode> lastDebugNodesPath;

   public static void render(WorldEventContext ctx) {
      try {
         Iterator var1 = lastDebugNodesVisited.iterator();

         MNode n;
         while(var1.hasNext()) {
            n = (MNode)var1.next();
            debugDrawNode(n, -65536, ctx);
         }

         var1 = lastDebugNodesNotVisited.iterator();

         while(var1.hasNext()) {
            n = (MNode)var1.next();
            debugDrawNode(n, -16776961, ctx);
         }

         var1 = lastDebugNodesPath.iterator();

         while(var1.hasNext()) {
            n = (MNode)var1.next();
            if (n.isReachedByWorker()) {
               debugDrawNode(n, -39424, ctx);
            } else {
               debugDrawNode(n, -16711936, ctx);
            }
         }
      } catch (ConcurrentModificationException var3) {
         IceAndFire.LOGGER.catching(var3);
      }

   }

   private static void debugDrawNode(MNode n, int argbColor, WorldEventContext ctx) {
      ctx.poseStack.m_85836_();
      ctx.poseStack.m_85837_((double)n.pos.m_123341_() + 0.375D, (double)n.pos.m_123342_() + 0.375D, (double)n.pos.m_123343_() + 0.375D);
      Entity entity = Minecraft.m_91087_().m_91288_();
      if (n.pos.m_123314_(entity.m_20183_(), 5.0D)) {
         renderDebugText(n, ctx);
      }

      ctx.poseStack.m_85841_(0.25F, 0.25F, 0.25F);
      WorldRenderMacros.renderBox(ctx.bufferSource, ctx.poseStack, BlockPos.f_121853_, BlockPos.f_121853_, argbColor);
      if (n.parent != null) {
         Matrix4f lineMatrix = ctx.poseStack.m_85850_().m_252922_();
         float pdx = (float)(n.parent.pos.m_123341_() - n.pos.m_123341_()) + 0.125F;
         float pdy = (float)(n.parent.pos.m_123342_() - n.pos.m_123342_()) + 0.125F;
         float pdz = (float)(n.parent.pos.m_123343_() - n.pos.m_123343_()) + 0.125F;
         VertexConsumer buffer = ctx.bufferSource.m_6299_(WorldRenderMacros.LINES);
         buffer.m_252986_(lineMatrix, 0.5F, 0.5F, 0.5F).m_85950_(0.75F, 0.75F, 0.75F, 1.0F).m_5752_();
         buffer.m_252986_(lineMatrix, pdx / 0.25F, pdy / 0.25F, pdz / 0.25F).m_85950_(0.75F, 0.75F, 0.75F, 1.0F).m_5752_();
      }

      ctx.poseStack.m_85849_();
   }

   private static void renderDebugText(@NotNull MNode n, WorldEventContext ctx) {
      Font fontrenderer = Minecraft.m_91087_().f_91062_;
      String s1 = String.format("F: %.3f [%d]", n.getCost(), n.getCounterAdded());
      String s2 = String.format("G: %.3f [%d]", n.getScore(), n.getCounterVisited());
      int i = Math.max(fontrenderer.m_92895_(s1), fontrenderer.m_92895_(s2)) / 2;
      ctx.poseStack.m_85836_();
      ctx.poseStack.m_252880_(0.0F, 0.75F, 0.0F);
      ctx.poseStack.m_252781_(Minecraft.m_91087_().m_91290_().m_253208_());
      ctx.poseStack.m_85841_(-0.014F, -0.014F, 0.014F);
      ctx.poseStack.m_252880_(0.0F, 18.0F, 0.0F);
      Matrix4f mat = ctx.poseStack.m_85850_().m_252922_();
      WorldRenderMacros.renderFillRectangle(ctx.bufferSource, ctx.poseStack, -i - 1, -5, 0, 2 * i + 2, 17, 2130706432);
      ctx.poseStack.m_252880_(0.0F, -5.0F, -0.1F);
      fontrenderer.m_271703_(s1, (float)(-fontrenderer.m_92895_(s1)) / 2.0F, 1.0F, -1, false, mat, ctx.bufferSource, DisplayMode.NORMAL, 0, 15728880);
      ctx.poseStack.m_252880_(0.0F, 8.0F, -0.1F);
      fontrenderer.m_271703_(s2, (float)(-fontrenderer.m_92895_(s2)) / 2.0F, 1.0F, -1, false, mat, ctx.bufferSource, DisplayMode.NORMAL, 0, 15728880);
      ctx.poseStack.m_85849_();
   }

   static {
      renderBuffer = renderBuffers.m_110104_();
      lastDebugNodesVisited = new HashSet();
      lastDebugNodesNotVisited = new HashSet();
      lastDebugNodesPath = new HashSet();
   }
}
