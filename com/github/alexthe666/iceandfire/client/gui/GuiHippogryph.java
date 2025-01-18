package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.inventory.ContainerHippogryph;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;

public class GuiHippogryph extends AbstractContainerScreen<ContainerHippogryph> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/gui/hippogryph.png");
   private float mousePosx;
   private float mousePosY;

   public GuiHippogryph(ContainerHippogryph dragonInv, Inventory playerInv, Component name) {
      super(dragonInv, playerInv, name);
   }

   protected void m_280003_(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
      Entity entity = IceAndFire.PROXY.getReferencedMob();
      Font font = this.getMinecraft().f_91062_;
      if (entity instanceof EntityHippogryph) {
         EntityHippogryph hippo = (EntityHippogryph)entity;
         pGuiGraphics.m_280056_(font, hippo.m_5446_().getString(), 8, 6, 4210752, false);
      }

      pGuiGraphics.m_280614_(font, this.f_169604_, 8, this.f_97727_ - 96 + 2, 4210752, false);
   }

   public void m_88315_(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(pGuiGraphics);
      this.mousePosx = (float)mouseX;
      this.mousePosY = (float)mouseY;
      super.m_88315_(pGuiGraphics, mouseX, mouseY, partialTicks);
      this.m_280072_(pGuiGraphics, mouseX, mouseY);
   }

   protected void m_7286_(GuiGraphics pGuiGraphics, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::m_172817_);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      pGuiGraphics.m_280218_(TEXTURE, i, j, 0, 0, this.f_97726_, this.f_97727_);
      Entity entity = IceAndFire.PROXY.getReferencedMob();
      if (entity instanceof EntityHippogryph) {
         EntityHippogryph hippo = (EntityHippogryph)entity;
         if (hippo.isChested()) {
            pGuiGraphics.m_280218_(TEXTURE, i + 79, j + 17, 0, this.f_97727_, 90, 54);
         }

         InventoryScreen.m_274545_(pGuiGraphics, i + 51, j + 60, 17, (float)(i + 51) - this.mousePosx, (float)(j + 75 - 50) - this.mousePosY, hippo);
      }

   }
}
