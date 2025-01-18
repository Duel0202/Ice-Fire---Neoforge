package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.RenderStateShard.DepthTestStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.AABB;
import org.joml.Matrix4f;

public class WorldRenderMacros extends UiRenderMacros {
   private static final int MAX_DEBUG_TEXT_RENDER_DIST_SQUARED = 1024;
   public static final RenderType LINES;
   public static final RenderType LINES_WITH_WIDTH;
   public static final RenderType GLINT_LINES;
   public static final RenderType GLINT_LINES_WITH_WIDTH;
   public static final RenderType COLORED_TRIANGLES;
   public static final RenderType COLORED_TRIANGLES_NC_ND;
   private static final LinkedList<RenderType> buffers;
   private static BufferSource bufferSource;

   public static void putBufferHead(RenderType bufferType) {
      buffers.addFirst(bufferType);
      bufferSource = null;
   }

   public static void putBufferTail(RenderType bufferType) {
      buffers.addLast(bufferType);
      bufferSource = null;
   }

   public static void putBufferBefore(RenderType bufferType, RenderType putBefore) {
      buffers.add(Math.max(0, buffers.indexOf(putBefore)), bufferType);
      bufferSource = null;
   }

   public static void putBufferAfter(RenderType bufferType, RenderType putAfter) {
      int index = buffers.indexOf(putAfter);
      if (index == -1) {
         buffers.add(bufferType);
      } else {
         buffers.add(index + 1, bufferType);
      }

      bufferSource = null;
   }

   public static BufferSource getBufferSource() {
      if (bufferSource == null) {
         bufferSource = MultiBufferSource.m_109900_((Map)Util.m_137469_(new Object2ObjectLinkedOpenHashMap(), (map) -> {
            buffers.forEach((type) -> {
               map.put(type, new BufferBuilder(type.m_110507_()));
            });
         }), Tesselator.m_85913_().m_85915_());
      }

      return bufferSource;
   }

   public static void renderBlackLineBox(BufferSource buffer, PoseStack ps, BlockPos posA, BlockPos posB, float lineWidth) {
      renderLineBox(buffer.m_6299_(LINES_WITH_WIDTH), ps, posA, posB, 0, 0, 0, 255, lineWidth);
   }

   public static void renderRedGlintLineBox(BufferSource buffer, PoseStack ps, BlockPos posA, BlockPos posB, float lineWidth) {
      renderLineBox(buffer.m_6299_(GLINT_LINES_WITH_WIDTH), ps, posA, posB, 255, 0, 0, 255, lineWidth);
   }

   public static void renderWhiteLineBox(BufferSource buffer, PoseStack ps, BlockPos posA, BlockPos posB, float lineWidth) {
      renderLineBox(buffer.m_6299_(LINES_WITH_WIDTH), ps, posA, posB, 255, 255, 255, 255, lineWidth);
   }

   public static void renderLineAABB(VertexConsumer buffer, PoseStack ps, AABB aabb, int argbColor, float lineWidth) {
      renderLineAABB(buffer, ps, aabb, argbColor >> 16 & 255, argbColor >> 8 & 255, argbColor & 255, argbColor >> 24 & 255, lineWidth);
   }

   public static void renderLineAABB(VertexConsumer buffer, PoseStack ps, AABB aabb, int red, int green, int blue, int alpha, float lineWidth) {
      renderLineBox(buffer, ps, (float)aabb.f_82288_, (float)aabb.f_82289_, (float)aabb.f_82290_, (float)aabb.f_82291_, (float)aabb.f_82292_, (float)aabb.f_82293_, red, green, blue, alpha, lineWidth);
   }

   public static void renderLineBox(VertexConsumer buffer, PoseStack ps, BlockPos pos, int argbColor, float lineWidth) {
      renderLineBox(buffer, ps, pos, pos, argbColor >> 16 & 255, argbColor >> 8 & 255, argbColor & 255, argbColor >> 24 & 255, lineWidth);
   }

   public static void renderLineBox(VertexConsumer buffer, PoseStack ps, BlockPos posA, BlockPos posB, int argbColor, float lineWidth) {
      renderLineBox(buffer, ps, posA, posB, argbColor >> 16 & 255, argbColor >> 8 & 255, argbColor & 255, argbColor >> 24 & 255, lineWidth);
   }

   public static void renderLineBox(VertexConsumer buffer, PoseStack ps, BlockPos posA, BlockPos posB, int red, int green, int blue, int alpha, float lineWidth) {
      renderLineBox(buffer, ps, (float)Math.min(posA.m_123341_(), posB.m_123341_()), (float)Math.min(posA.m_123342_(), posB.m_123342_()), (float)Math.min(posA.m_123343_(), posB.m_123343_()), (float)(Math.max(posA.m_123341_(), posB.m_123341_()) + 1), (float)(Math.max(posA.m_123342_(), posB.m_123342_()) + 1), (float)(Math.max(posA.m_123343_(), posB.m_123343_()) + 1), red, green, blue, alpha, lineWidth);
   }

   public static void renderLineBox(VertexConsumer buffer, PoseStack ps, float minX, float minY, float minZ, float maxX, float maxY, float maxZ, int red, int green, int blue, int alpha, float lineWidth) {
      if (alpha != 0) {
         float halfLine = lineWidth / 2.0F;
         minX -= halfLine;
         minY -= halfLine;
         minZ -= halfLine;
         float minX2 = minX + lineWidth;
         float minY2 = minY + lineWidth;
         float minZ2 = minZ + lineWidth;
         maxX += halfLine;
         maxY += halfLine;
         maxZ += halfLine;
         float maxX2 = maxX - lineWidth;
         float maxY2 = maxY - lineWidth;
         float maxZ2 = maxZ - lineWidth;
         Matrix4f m = ps.m_85850_().m_252922_();
         buffer.m_7404_(red, green, blue, alpha);
         populateRenderLineBox(minX, minY, minZ, minX2, minY2, minZ2, maxX, maxY, maxZ, maxX2, maxY2, maxZ2, m, buffer);
         buffer.m_141991_();
      }
   }

   public static void populateRenderLineBox(float minX, float minY, float minZ, float minX2, float minY2, float minZ2, float maxX, float maxY, float maxZ, float maxX2, float maxY2, float maxZ2, Matrix4f m, VertexConsumer buf) {
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, minY2, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, minY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY2, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY, minZ2).m_5752_();
      buf.m_252986_(m, maxX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX2, maxY, maxZ2).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
   }

   public static void renderBox(BufferSource buffer, PoseStack ps, BlockPos posA, BlockPos posB, int argbColor) {
      renderBox(buffer.m_6299_(COLORED_TRIANGLES), ps, posA, posB, argbColor >> 16 & 255, argbColor >> 8 & 255, argbColor & 255, argbColor >> 24 & 255);
   }

   public static void renderBox(VertexConsumer buffer, PoseStack ps, BlockPos posA, BlockPos posB, int red, int green, int blue, int alpha) {
      if (alpha != 0) {
         float minX = (float)Math.min(posA.m_123341_(), posB.m_123341_());
         float minY = (float)Math.min(posA.m_123342_(), posB.m_123342_());
         float minZ = (float)Math.min(posA.m_123343_(), posB.m_123343_());
         float maxX = (float)(Math.max(posA.m_123341_(), posB.m_123341_()) + 1);
         float maxY = (float)(Math.max(posA.m_123342_(), posB.m_123342_()) + 1);
         float maxZ = (float)(Math.max(posA.m_123343_(), posB.m_123343_()) + 1);
         Matrix4f m = ps.m_85850_().m_252922_();
         buffer.m_7404_(red, green, blue, alpha);
         populateCuboid(minX, minY, minZ, maxX, maxY, maxZ, m, buffer);
         buffer.m_141991_();
      }
   }

   public static void populateCuboid(float minX, float minY, float minZ, float maxX, float maxY, float maxZ, Matrix4f m, VertexConsumer buf) {
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, minZ).m_5752_();
      buf.m_252986_(m, minX, minY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, maxZ).m_5752_();
      buf.m_252986_(m, minX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, minY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, minY, maxZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, minZ).m_5752_();
      buf.m_252986_(m, maxX, maxY, maxZ).m_5752_();
   }

   public static void renderFillRectangle(BufferSource buffer, PoseStack ps, int x, int y, int z, int w, int h, int argbColor) {
      populateRectangle(x, y, z, w, h, argbColor >> 16 & 255, argbColor >> 8 & 255, argbColor & 255, argbColor >> 24 & 255, buffer.m_6299_(COLORED_TRIANGLES_NC_ND), ps.m_85850_().m_252922_());
   }

   public static void populateRectangle(int x, int y, int z, int w, int h, int red, int green, int blue, int alpha, VertexConsumer buffer, Matrix4f m) {
      if (alpha != 0) {
         buffer.m_252986_(m, (float)x, (float)y, (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_252986_(m, (float)x, (float)(y + h), (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_252986_(m, (float)(x + w), (float)(y + h), (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_252986_(m, (float)x, (float)y, (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_252986_(m, (float)(x + w), (float)(y + h), (float)z).m_6122_(red, green, blue, alpha).m_5752_();
         buffer.m_252986_(m, (float)(x + w), (float)y, (float)z).m_6122_(red, green, blue, alpha).m_5752_();
      }
   }

   public static void renderDebugText(BlockPos pos, List<String> text, PoseStack matrixStack, boolean forceWhite, int mergeEveryXListElements, MultiBufferSource buffer) {
      if (mergeEveryXListElements < 1) {
         throw new IllegalArgumentException("mergeEveryXListElements is less than 1");
      } else {
         EntityRenderDispatcher erm = Minecraft.m_91087_().m_91290_();
         int cap = text.size();
         if (cap > 0 && erm.m_114378_((double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_()) <= 1024.0D) {
            Font fontrenderer = Minecraft.m_91087_().f_91062_;
            matrixStack.m_85836_();
            matrixStack.m_85837_((double)pos.m_123341_() + 0.5D, (double)pos.m_123342_() + 0.75D, (double)pos.m_123343_() + 0.5D);
            matrixStack.m_252781_(erm.m_253208_());
            matrixStack.m_85841_(-0.014F, -0.014F, 0.014F);
            matrixStack.m_85837_(0.0D, 18.0D, 0.0D);
            float backgroundTextOpacity = Minecraft.m_91087_().f_91066_.m_92141_(0.25F);
            int alphaMask = (int)(backgroundTextOpacity * 255.0F) << 24;
            Matrix4f rawPosMatrix = matrixStack.m_85850_().m_252922_();

            for(int i = 0; i < cap; i += mergeEveryXListElements) {
               MutableComponent renderText = Component.m_237113_(mergeEveryXListElements == 1 ? (String)text.get(i) : text.subList(i, Math.min(i + mergeEveryXListElements, cap)).toString());
               float textCenterShift = (float)(-fontrenderer.m_92852_(renderText) / 2);
               fontrenderer.m_272077_(renderText, textCenterShift, 0.0F, forceWhite ? -1 : 553648127, false, rawPosMatrix, buffer, DisplayMode.SEE_THROUGH, alphaMask, 15728880);
               if (!forceWhite) {
                  fontrenderer.m_272077_(renderText, textCenterShift, 0.0F, -1, false, rawPosMatrix, buffer, DisplayMode.NORMAL, 0, 15728880);
               }

               Objects.requireNonNull(fontrenderer);
               matrixStack.m_85837_(0.0D, (double)(9 + 1), 0.0D);
            }

            matrixStack.m_85849_();
         }

      }
   }

   static {
      LINES = WorldRenderMacros.RenderTypes.LINES;
      LINES_WITH_WIDTH = WorldRenderMacros.RenderTypes.LINES_WITH_WIDTH;
      GLINT_LINES = WorldRenderMacros.RenderTypes.GLINT_LINES;
      GLINT_LINES_WITH_WIDTH = WorldRenderMacros.RenderTypes.GLINT_LINES_WITH_WIDTH;
      COLORED_TRIANGLES = WorldRenderMacros.RenderTypes.COLORED_TRIANGLES;
      COLORED_TRIANGLES_NC_ND = WorldRenderMacros.RenderTypes.COLORED_TRIANGLES_NC_ND;
      buffers = new LinkedList();
      putBufferTail(COLORED_TRIANGLES);
      putBufferTail(LINES);
      putBufferTail(LINES_WITH_WIDTH);
      putBufferTail(GLINT_LINES);
      putBufferTail(GLINT_LINES_WITH_WIDTH);
      putBufferTail(COLORED_TRIANGLES_NC_ND);
   }

   private static final class RenderTypes extends RenderType {
      private static final RenderType GLINT_LINES;
      private static final RenderType GLINT_LINES_WITH_WIDTH;
      private static final RenderType LINES;
      private static final RenderType LINES_WITH_WIDTH;
      private static final RenderType COLORED_TRIANGLES;
      private static final RenderType COLORED_TRIANGLES_NC_ND;

      private RenderTypes(String nameIn, VertexFormat formatIn, Mode drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
         super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
         throw new IllegalStateException();
      }

      static {
         GLINT_LINES = m_173215_("structurize_glint_lines", DefaultVertexFormat.f_85815_, Mode.DEBUG_LINES, 4096, false, false, CompositeState.m_110628_().m_173290_(f_110147_).m_173292_(f_173104_).m_110685_(f_110137_).m_110663_(f_110111_).m_110661_(f_110110_).m_110671_(f_110153_).m_110677_(f_110155_).m_110669_(f_110117_).m_110675_(f_110123_).m_110683_(f_110148_).m_110687_(f_110115_).m_110691_(false));
         GLINT_LINES_WITH_WIDTH = m_173215_("structurize_glint_lines_with_width", DefaultVertexFormat.f_85815_, Mode.TRIANGLES, 8192, false, false, CompositeState.m_110628_().m_173290_(f_110147_).m_173292_(f_173104_).m_110685_(f_110137_).m_110663_(WorldRenderMacros.AlwaysDepthTestStateShard.ALWAYS_DEPTH_TEST).m_110661_(f_110158_).m_110671_(f_110153_).m_110677_(f_110155_).m_110669_(f_110117_).m_110675_(f_110123_).m_110683_(f_110148_).m_110687_(f_110114_).m_110691_(false));
         LINES = m_173215_("structurize_lines", DefaultVertexFormat.f_85815_, Mode.DEBUG_LINES, 16384, false, false, CompositeState.m_110628_().m_173290_(f_110147_).m_173292_(f_173104_).m_110685_(f_110139_).m_110663_(f_110113_).m_110661_(f_110110_).m_110671_(f_110153_).m_110677_(f_110155_).m_110669_(f_110117_).m_110675_(f_110123_).m_110683_(f_110148_).m_110687_(f_110115_).m_110691_(false));
         LINES_WITH_WIDTH = m_173215_("structurize_lines_with_width", DefaultVertexFormat.f_85815_, Mode.TRIANGLES, 8192, false, false, CompositeState.m_110628_().m_173290_(f_110147_).m_173292_(f_173104_).m_110685_(f_110139_).m_110663_(f_110113_).m_110661_(f_110158_).m_110671_(f_110153_).m_110677_(f_110155_).m_110669_(f_110117_).m_110675_(f_110123_).m_110683_(f_110148_).m_110687_(f_110114_).m_110691_(false));
         COLORED_TRIANGLES = m_173215_("structurize_colored_triangles", DefaultVertexFormat.f_85815_, Mode.TRIANGLES, 8192, false, false, CompositeState.m_110628_().m_173290_(f_110147_).m_173292_(f_173104_).m_110685_(f_110139_).m_110663_(f_110113_).m_110661_(f_110158_).m_110671_(f_110153_).m_110677_(f_110155_).m_110669_(f_110117_).m_110675_(f_110123_).m_110683_(f_110148_).m_110687_(f_110114_).m_110691_(false));
         COLORED_TRIANGLES_NC_ND = m_173215_("structurize_colored_triangles_nc_nd", DefaultVertexFormat.f_85815_, Mode.TRIANGLES, 4096, false, false, CompositeState.m_110628_().m_173290_(f_110147_).m_173292_(f_173104_).m_110685_(f_110139_).m_110663_(f_110111_).m_110661_(f_110110_).m_110671_(f_110153_).m_110677_(f_110155_).m_110669_(f_110117_).m_110675_(f_110123_).m_110683_(f_110148_).m_110687_(f_110115_).m_110691_(false));
      }
   }

   public static class AlwaysDepthTestStateShard extends DepthTestStateShard {
      public static final DepthTestStateShard ALWAYS_DEPTH_TEST = new WorldRenderMacros.AlwaysDepthTestStateShard();

      private AlwaysDepthTestStateShard() {
         super("true_always", -1);
         this.f_110131_ = () -> {
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(519);
         };
      }
   }
}
