package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.StatCollector;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.inventory.ContainerDragon;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;

public class GuiDragon extends AbstractContainerScreen<ContainerDragon> {
   private static final ResourceLocation texture = new ResourceLocation("iceandfire:textures/gui/dragon.png");

   public GuiDragon(ContainerDragon dragonInv, Inventory playerInv, Component name) {
      super(dragonInv, playerInv, name);
      this.f_97727_ = 214;
   }

   protected void m_280003_(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY) {
   }

   public void m_88315_(@NotNull GuiGraphics matrixStack, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(matrixStack);
      super.m_88315_(matrixStack, mouseX, mouseY, partialTicks);
      this.m_280072_(matrixStack, mouseX, mouseY);
   }

   protected void m_7286_(@NotNull GuiGraphics matrixStack, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.setShader(GameRenderer::m_172817_);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int k = (this.f_96543_ - this.f_97726_) / 2;
      int l = (this.f_96544_ - this.f_97727_) / 2;
      matrixStack.m_280218_(texture, k, l, 0, 0, this.f_97726_, this.f_97727_);
      Entity entity = IceAndFire.PROXY.getReferencedMob();
      EntityDragonBase dragon;
      if (entity instanceof EntityDragonBase) {
         dragon = (EntityDragonBase)entity;
         float dragonScale = 1.0F / Math.max(1.0E-4F, dragon.m_6134_());
         Quaternionf quaternionf = (new Quaternionf()).rotateY((float)Mth.m_14139_((double)((float)mouseX / (float)this.f_96543_), 0.0D, 3.141592653589793D)).rotateZ((float)Mth.m_14139_((double)((float)mouseY / (float)this.f_96543_), 3.141592653589793D, 3.3415926535897933D));
         InventoryScreen.m_280432_(matrixStack, k + 88, l + (int)(0.5F * dragon.flyProgress) + 55, (int)(dragonScale * 23.0F), quaternionf, (Quaternionf)null, dragon);
      }

      if (entity instanceof EntityDragonBase) {
         dragon = (EntityDragonBase)entity;
         Font font = this.getMinecraft().f_91062_;
         String s3 = dragon.m_7770_() == null ? StatCollector.translateToLocal("dragon.unnamed") : StatCollector.translateToLocal("dragon.name") + " " + dragon.m_7770_().getString();
         font.m_271703_(s3, (float)(k + this.f_97726_ / 2 - font.m_92895_(s3) / 2), (float)(l + 75), 16777215, false, matrixStack.m_280168_().m_85850_().m_252922_(), matrixStack.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         String var10000 = StatCollector.translateToLocal("dragon.health");
         String s2 = var10000 + " " + Math.floor((double)Math.min(dragon.m_21223_(), dragon.m_21233_())) + " / " + dragon.m_21233_();
         font.m_271703_(s2, (float)(k + this.f_97726_ / 2 - font.m_92895_(s2) / 2), (float)(l + 84), 16777215, false, matrixStack.m_280168_().m_85850_().m_252922_(), matrixStack.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         var10000 = StatCollector.translateToLocal("dragon.gender");
         String s5 = var10000 + StatCollector.translateToLocal(dragon.isMale() ? "dragon.gender.male" : "dragon.gender.female");
         font.m_271703_(s5, (float)(k + this.f_97726_ / 2 - font.m_92895_(s5) / 2), (float)(l + 93), 16777215, false, matrixStack.m_280168_().m_85850_().m_252922_(), matrixStack.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         var10000 = StatCollector.translateToLocal("dragon.hunger");
         String s6 = var10000 + dragon.getHunger() + "/100";
         font.m_271703_(s6, (float)(k + this.f_97726_ / 2 - font.m_92895_(s6) / 2), (float)(l + 102), 16777215, false, matrixStack.m_280168_().m_85850_().m_252922_(), matrixStack.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         var10000 = StatCollector.translateToLocal("dragon.stage");
         String s4 = var10000 + " " + dragon.getDragonStage() + " " + StatCollector.translateToLocal("dragon.days.front") + dragon.getAgeInDays() + " " + StatCollector.translateToLocal("dragon.days.back");
         font.m_271703_(s4, (float)(k + this.f_97726_ / 2 - font.m_92895_(s4) / 2), (float)(l + 111), 16777215, false, matrixStack.m_280168_().m_85850_().m_252922_(), matrixStack.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         String s7 = dragon.m_269323_() != null ? StatCollector.translateToLocal("dragon.owner") + dragon.m_269323_().m_7755_().getString() : StatCollector.translateToLocal("dragon.untamed");
         font.m_271703_(s7, (float)(k + this.f_97726_ / 2 - font.m_92895_(s7) / 2), (float)(l + 120), 16777215, false, matrixStack.m_280168_().m_85850_().m_252922_(), matrixStack.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      }

   }
}
