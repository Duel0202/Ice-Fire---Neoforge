package com.github.alexthe666.iceandfire.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.GameRenderer;
import org.joml.Matrix4f;

public class GUIColoredBlit {
   public static void blit(PoseStack p_93161_, int p_93162_, int p_93163_, int p_93164_, int p_93165_, float p_93166_, float p_93167_, int p_93168_, int p_93169_, int p_93170_, int p_93171_, float alpha) {
      innerBlit(p_93161_, p_93162_, p_93162_ + p_93164_, p_93163_, p_93163_ + p_93165_, 0, p_93168_, p_93169_, p_93166_, p_93167_, p_93170_, p_93171_, alpha);
   }

   public static void blit(PoseStack p_93134_, int p_93135_, int p_93136_, float p_93137_, float p_93138_, int p_93139_, int p_93140_, int p_93141_, int p_93142_, float alpha) {
      blit(p_93134_, p_93135_, p_93136_, p_93139_, p_93140_, p_93137_, p_93138_, p_93139_, p_93140_, p_93141_, p_93142_, alpha);
   }

   private static void innerBlit(PoseStack p_93188_, int p_93189_, int p_93190_, int p_93191_, int p_93192_, int p_93193_, int p_93194_, int p_93195_, float p_93196_, float p_93197_, int p_93198_, int p_93199_, float alpha) {
      innerBlit(p_93188_.m_85850_().m_252922_(), p_93189_, p_93190_, p_93191_, p_93192_, p_93193_, (p_93196_ + 0.0F) / (float)p_93198_, (p_93196_ + (float)p_93194_) / (float)p_93198_, (p_93197_ + 0.0F) / (float)p_93199_, (p_93197_ + (float)p_93195_) / (float)p_93199_, alpha);
   }

   private static void innerBlit(Matrix4f p_93113_, int p_93114_, int p_93115_, int p_93116_, int p_93117_, int p_93118_, float p_93119_, float p_93120_, float p_93121_, float p_93122_, float alpha) {
      RenderSystem.setShader(GameRenderer::m_172820_);
      BufferBuilder bufferbuilder = Tesselator.m_85913_().m_85915_();
      bufferbuilder.m_166779_(Mode.QUADS, DefaultVertexFormat.f_85819_);
      bufferbuilder.m_252986_(p_93113_, (float)p_93114_, (float)p_93117_, (float)p_93118_).m_7421_(p_93119_, p_93122_).m_85950_(1.0F, 1.0F, 1.0F, alpha).m_5752_();
      bufferbuilder.m_252986_(p_93113_, (float)p_93115_, (float)p_93117_, (float)p_93118_).m_7421_(p_93120_, p_93122_).m_85950_(1.0F, 1.0F, 1.0F, alpha).m_5752_();
      bufferbuilder.m_252986_(p_93113_, (float)p_93115_, (float)p_93116_, (float)p_93118_).m_7421_(p_93120_, p_93121_).m_85950_(1.0F, 1.0F, 1.0F, alpha).m_5752_();
      bufferbuilder.m_252986_(p_93113_, (float)p_93114_, (float)p_93116_, (float)p_93118_).m_7421_(p_93119_, p_93121_).m_85950_(1.0F, 1.0F, 1.0F, alpha).m_5752_();
      bufferbuilder.m_231175_();
      BufferUploader.m_166835_();
   }
}
