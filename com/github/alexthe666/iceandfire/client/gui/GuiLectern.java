package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityLectern;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.inventory.ContainerLectern;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexSorting;
import com.mojang.math.Axis;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class GuiLectern extends AbstractContainerScreen<ContainerLectern> {
   private static final ResourceLocation ENCHANTMENT_TABLE_GUI_TEXTURE = new ResourceLocation("iceandfire:textures/gui/lectern.png");
   private static final ResourceLocation ENCHANTMENT_TABLE_BOOK_TEXTURE = new ResourceLocation("iceandfire:textures/models/lectern_book.png");
   private static BookModel bookModel;
   private final Random random = new Random();
   private final Component nameable;
   public int ticks;
   public float flip;
   public float oFlip;
   public float flipT;
   public float flipA;
   public float open;
   public float oOpen;
   private ItemStack last;
   private int flapTimer;

   public GuiLectern(ContainerLectern container, Inventory inv, Component name) {
      super(container, inv, name);
      this.last = ItemStack.f_41583_;
      this.flapTimer = 0;
      this.nameable = name;
   }

   protected void m_7856_() {
      super.m_7856_();
      bookModel = new BookModel(this.f_96541_.m_167973_().m_171103_(ModelLayers.f_171271_));
   }

   protected void m_280003_(@NotNull GuiGraphics ms, int mouseX, int mouseY) {
      Font font = this.getMinecraft().f_91062_;
      font.m_271703_(this.nameable.getString(), 12.0F, 4.0F, 4210752, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      font.m_272077_(this.f_169604_, 8.0F, (float)(this.f_97727_ - 96 + 2), 4210752, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
   }

   public void m_181908_() {
      super.m_181908_();
      ((ContainerLectern)this.f_97732_).onUpdate();
      this.tickBook();
   }

   public boolean m_6375_(double mouseX, double mouseY, int mouseButton) {
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;

      for(int k = 0; k < 3; ++k) {
         double l = mouseX - (double)(i + 60);
         double i1 = mouseY - (double)(j + 14 + 19 * k);
         if (l >= 0.0D && i1 >= 0.0D && l < 108.0D && i1 < 19.0D && ((ContainerLectern)this.f_97732_).m_6366_(this.getMinecraft().f_91074_, k)) {
            this.flapTimer = 5;
            this.getMinecraft().f_91072_.m_105208_(((ContainerLectern)this.f_97732_).f_38840_, k);
            return true;
         }
      }

      return super.m_6375_(mouseX, mouseY, mouseButton);
   }

   protected void m_7286_(@NotNull GuiGraphics ms, float partialTicks, int mouseX, int mouseY) {
      Lighting.m_84930_();
      RenderSystem.setShader(GameRenderer::m_172817_);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int i = (this.f_96543_ - this.f_97726_) / 2;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, i, j, 0, 0, this.f_97726_, this.f_97727_);
      int k = (int)this.f_96541_.m_91268_().m_85449_();
      RenderSystem.viewport((this.f_96543_ - 320) / 2 * k, (this.f_96544_ - 240) / 2 * k, 320 * k, 240 * k);
      Matrix4f matrix4f = (new Matrix4f()).m03(-0.34F).m13(0.23F);
      matrix4f.mul((new Matrix4f()).perspective(90.0F, 1.3333334F, 9.0F, 80.0F));
      RenderSystem.backupProjectionMatrix();
      RenderSystem.setProjectionMatrix(matrix4f, (VertexSorting)null);
      ms.m_280168_().m_85836_();
      ms.m_280168_().m_166856_();
      ms.m_280168_().m_85837_(0.0D, 3.299999952316284D, 1984.0D);
      float f = 5.0F;
      ms.m_280168_().m_85841_(5.0F, 5.0F, 5.0F);
      ms.m_280168_().m_252781_(Axis.f_252403_.m_252977_(180.0F));
      ms.m_280168_().m_252781_(Axis.f_252529_.m_252977_(20.0F));
      float f1 = Mth.m_14179_(partialTicks, this.oOpen, this.open);
      ms.m_280168_().m_252880_((1.0F - f1) * 0.2F, (1.0F - f1) * 0.1F, (1.0F - f1) * 0.25F);
      float f2 = -(1.0F - f1) * 90.0F - 90.0F;
      ms.m_280168_().m_252781_(Axis.f_252436_.m_252977_(f2));
      ms.m_280168_().m_252781_(Axis.f_252529_.m_252977_(180.0F));
      float f3 = Mth.m_14179_(partialTicks, this.oFlip, this.flip) + 0.25F;
      float f4 = Mth.m_14179_(partialTicks, this.oFlip, this.flip) + 0.75F;
      f3 = (f3 - (float)Mth.m_14143_(f3)) * 1.6F - 0.3F;
      f4 = (f4 - (float)Mth.m_14143_(f4)) * 1.6F - 0.3F;
      if (f3 < 0.0F) {
         f3 = 0.0F;
      }

      if (f4 < 0.0F) {
         f4 = 0.0F;
      }

      if (f3 > 1.0F) {
         f3 = 1.0F;
      }

      if (f4 > 1.0F) {
         f4 = 1.0F;
      }

      bookModel.m_102292_(0.0F, f3, f4, f1);
      BufferSource multibuffersource$buffersource = MultiBufferSource.m_109898_(Tesselator.m_85913_().m_85915_());
      VertexConsumer vertexconsumer = multibuffersource$buffersource.m_6299_(bookModel.m_103119_(ENCHANTMENT_TABLE_BOOK_TEXTURE));
      bookModel.m_7695_(ms.m_280168_(), vertexconsumer, 15728880, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      multibuffersource$buffersource.m_109911_();
      ms.m_280168_().m_85849_();
      RenderSystem.viewport(0, 0, this.f_96541_.m_91268_().m_85441_(), this.f_96541_.m_91268_().m_85442_());
      RenderSystem.restoreProjectionMatrix();
      Lighting.m_84931_();
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      ((ContainerLectern)this.f_97732_).getManuscriptAmount();

      for(int i1 = 0; i1 < 3; ++i1) {
         int j1 = i + 60;
         int k1 = j1 + 20;
         int l1 = ((ContainerLectern)this.f_97732_).getPossiblePages()[i1] == null ? -1 : ((ContainerLectern)this.f_97732_).getPossiblePages()[i1].ordinal();
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         if (l1 == -1) {
            ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
         } else {
            String s = "3";
            Font fontrenderer = this.getMinecraft().f_91062_;
            String s1 = "";
            float textScale = 1.0F;
            EnumBestiaryPages enchantment = ((ContainerLectern)this.f_97732_).getPossiblePages()[i1];
            if (enchantment != null) {
               s1 = I18n.m_118938_("bestiary." + enchantment.toString().toLowerCase(), new Object[0]);
               if (fontrenderer.m_92895_(s1) > 80) {
                  textScale = 1.0F - (float)(fontrenderer.m_92895_(s1) - 80) * 0.01F;
               }
            }

            int j2 = 6839882;
            if (IceAndFire.PROXY.getRefrencedTE() instanceof TileEntityLectern) {
               IceAndFire.PROXY.getRefrencedTE();
               if (((ContainerLectern)this.f_97732_).m_38853_(0).m_7993_().m_41720_() != IafItemRegistry.BESTIARY.get()) {
                  ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, j1, j + 14 + 19 * i1, 0, 185, 108, 19);
                  ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, j1 + 1, j + 15 + 19 * i1, 16 * i1, 239, 16, 16);
               } else {
                  int k2 = mouseX - (i + 60);
                  int l2 = mouseY - (j + 14 + 19 * i1);
                  int j3 = 10459276;
                  if (k2 >= 0 && l2 >= 0 && k2 < 108 && l2 < 19) {
                     ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, j1, j + 14 + 19 * i1, 0, 204, 108, 19);
                     j2 = 16777088;
                     j3 = 16777088;
                  } else {
                     ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, j1, j + 14 + 19 * i1, 0, 166, 108, 19);
                  }

                  ms.m_280218_(ENCHANTMENT_TABLE_GUI_TEXTURE, j1 + 1, j + 15 + 19 * i1, 16 * i1, 223, 16, 16);
                  ms.m_280168_().m_85836_();
                  ms.m_280168_().m_252880_((float)this.f_96543_ / 2.0F - 10.0F, (float)this.f_96544_ / 2.0F - 83.0F + (1.0F - textScale) * 55.0F, 2.0F);
                  ms.m_280168_().m_85841_(textScale, textScale, 1.0F);
                  fontrenderer.m_271703_(s1, 0.0F, (float)(20 + 19 * i1), j2, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
                  ms.m_280168_().m_85849_();
                  fontrenderer = this.getMinecraft().f_91062_;
                  fontrenderer.m_271703_(s, (float)(k1 + 84 - fontrenderer.m_92895_(s)), (float)(j + 13 + 19 * i1 + 7), j3, true, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
               }
            }
         }
      }

   }

   public void m_88315_(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(ms);
      super.m_88315_(ms, mouseX, mouseY, partialTicks);
      this.m_280072_(ms, mouseX, mouseY);
      boolean flag = this.getMinecraft().f_91074_.m_7500_();
      int i = ((ContainerLectern)this.f_97732_).getManuscriptAmount();

      for(int j = 0; j < 3; ++j) {
         int k = 1;
         EnumBestiaryPages enchantment = ((ContainerLectern)this.f_97732_).getPossiblePages()[j];
         int i1 = 3;
         if (this.m_6774_(60, 14 + 19 * j, 108, 17, (double)mouseX, (double)mouseY) && k > 0) {
            List<FormattedCharSequence> list = Lists.newArrayList();
            ChatFormatting var10001;
            if (enchantment == null) {
               var10001 = ChatFormatting.RED;
               list.add(Component.m_237113_(var10001 + I18n.m_118938_("container.lectern.no_bestiary", new Object[0])).m_7532_());
            } else if (!flag) {
               var10001 = ChatFormatting.WHITE;
               list.add(Component.m_237113_(var10001 + ChatFormatting.ITALIC + I18n.m_118938_(enchantment == null ? "" : "bestiary." + enchantment.name().toLowerCase(), new Object[0])).m_7532_());
               ChatFormatting textformatting = i >= i1 ? ChatFormatting.GRAY : ChatFormatting.RED;
               list.add(Component.m_237113_(textformatting + I18n.m_118938_("container.lectern.costs", new Object[0])).m_7532_());
               String s = I18n.m_118938_("container.lectern.manuscript.many", new Object[]{Integer.valueOf(i1)});
               list.add(Component.m_237113_(textformatting + s).m_7532_());
            }

            this.m_280072_(ms, mouseX, mouseY);
            break;
         }
      }

   }

   public void tickBook() {
      ItemStack itemstack = ((ContainerLectern)this.f_97732_).m_38853_(0).m_7993_();
      if (!ItemStack.m_41728_(itemstack, this.last)) {
         this.last = itemstack;

         do {
            this.flipT += (float)(this.random.nextInt(4) - this.random.nextInt(4));
         } while(!(this.flip > this.flipT + 1.0F) && !(this.flip < this.flipT - 1.0F));
      }

      ++this.ticks;
      this.oFlip = this.flip;
      this.oOpen = this.open;
      boolean flag = false;

      for(int i = 0; i < 3; ++i) {
         if (((ContainerLectern)this.f_97732_).getPossiblePages()[i] != null) {
            flag = true;
         }
      }

      if (flag) {
         this.open += 0.2F;
      } else {
         this.open -= 0.2F;
      }

      this.open = Mth.m_14036_(this.open, 0.0F, 1.0F);
      float f1 = (this.flipT - this.flip) * 0.4F;
      if (this.flapTimer > 0) {
         f1 = ((float)this.ticks + this.getMinecraft().m_91296_()) * 0.5F;
         --this.flapTimer;
      }

      f1 = Mth.m_14036_(f1, -0.2F, 0.2F);
      this.flipA += (f1 - this.flipA) * 0.9F;
      this.flip += this.flipA;
   }
}
