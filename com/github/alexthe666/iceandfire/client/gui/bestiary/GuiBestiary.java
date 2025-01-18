package com.github.alexthe666.iceandfire.client.gui.bestiary;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.StatCollector;
import com.github.alexthe666.iceandfire.enums.EnumBestiaryPages;
import com.github.alexthe666.iceandfire.enums.EnumDragonArmor;
import com.github.alexthe666.iceandfire.enums.EnumSeaSerpent;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.mojang.blaze3d.systems.RenderSystem;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforge.registries.ForgeRegistries;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

public class GuiBestiary extends Screen {
   protected static final int X = 390;
   protected static final int Y = 245;
   private static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/gui/bestiary/bestiary.png");
   private static final ResourceLocation DRAWINGS_0 = new ResourceLocation("iceandfire:textures/gui/bestiary/drawings_0.png");
   private static final ResourceLocation DRAWINGS_1 = new ResourceLocation("iceandfire:textures/gui/bestiary/drawings_1.png");
   private static final Map<String, ResourceLocation> PICTURE_LOCATION_CACHE = Maps.newHashMap();
   public List<EnumBestiaryPages> allPageTypes = new ArrayList();
   public EnumBestiaryPages pageType;
   public List<IndexPageButton> indexButtons = new ArrayList();
   public ChangePageButton previousPage;
   public ChangePageButton nextPage;
   public int bookPages;
   public int bookPagesTotal = 1;
   public int indexPages;
   public int indexPagesTotal = 1;
   protected ItemStack book;
   protected boolean index;
   protected Font font = getFont();

   public GuiBestiary(ItemStack book) {
      super(Component.m_237115_("bestiary_gui"));
      this.book = book;
      if (!book.m_41619_() && book.m_41720_() != null && book.m_41720_() == IafItemRegistry.BESTIARY.get() && book.m_41783_() != null) {
         Set<EnumBestiaryPages> pages = EnumBestiaryPages.containedPages(Ints.asList(book.m_41783_().m_128465_("Pages")));
         this.allPageTypes.addAll(pages);
         this.allPageTypes.sort(Comparator.comparingInt(Enum::ordinal));
         this.indexPagesTotal = (int)Math.ceil((double)pages.size() / 10.0D);
      }

      this.index = true;
   }

   private static Font getFont() {
      return !IafConfig.useVanillaFont && Minecraft.m_91087_().f_91066_.f_92075_.equalsIgnoreCase("en_us") ? (Font)IceAndFire.PROXY.getFontRenderer() : Minecraft.m_91087_().f_91062_;
   }

   private static Item getItemByRegistryName(String registryName) {
      return (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(registryName));
   }

   protected void m_7856_() {
      super.m_7856_();
      this.m_169413_();
      this.indexButtons.clear();
      int centerX = (this.f_96543_ - 390) / 2;
      int centerY = (this.f_96544_ - 245) / 2;
      this.previousPage = new ChangePageButton(centerX + 15, centerY + 215, false, 0, (p_214132_1_) -> {
         if (this.index) {
            if (this.indexPages <= 0) {
               return;
            }
         } else if (this.pageType == null) {
            return;
         }

         if (this.index) {
            --this.indexPages;
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(IafSoundRegistry.BESTIARY_PAGE, 1.0F));
         } else if (this.bookPages > 0) {
            --this.bookPages;
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(IafSoundRegistry.BESTIARY_PAGE, 1.0F));
         } else {
            this.index = true;
         }

      });
      this.m_142416_(this.previousPage);
      this.nextPage = new ChangePageButton(centerX + 357, centerY + 215, true, 0, (p_214132_1_) -> {
         if (this.index) {
            if (this.indexPages >= this.indexPagesTotal - 1) {
               return;
            }
         } else if (this.pageType == null || this.bookPages >= this.pageType.pages) {
            return;
         }

         if (this.index) {
            ++this.indexPages;
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(IafSoundRegistry.BESTIARY_PAGE, 1.0F));
         } else {
            ++this.bookPages;
            Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(IafSoundRegistry.BESTIARY_PAGE, 1.0F));
         }

      });
      this.m_142416_(this.nextPage);
      if (!this.allPageTypes.isEmpty()) {
         for(int i = 0; i < this.allPageTypes.size(); ++i) {
            int xIndex = i % -2;
            int yIndex = i % 10;
            int id = 2 + i;
            int var10002 = centerX + 15 + xIndex * 200;
            int var10003 = centerY + 10 + yIndex * 20 - (xIndex == 1 ? 20 : 0);
            EnumBestiaryPages[] var10004 = EnumBestiaryPages.values();
            Object var10005 = this.allPageTypes.get(i);
            IndexPageButton button = new IndexPageButton(var10002, var10003, Component.m_237115_("bestiary." + var10004[((EnumBestiaryPages)var10005).ordinal()].toString().toLowerCase()), (p_214132_1_) -> {
               if (this.indexButtons.get(id - 2) != null && this.allPageTypes.get(id - 2) != null) {
                  Minecraft.m_91087_().m_91106_().m_120367_(SimpleSoundInstance.m_119752_(IafSoundRegistry.BESTIARY_PAGE, 1.0F));
                  this.index = false;
                  this.bookPages = 0;
                  this.pageType = (EnumBestiaryPages)this.allPageTypes.get(id - 2);
               }

            });
            this.indexButtons.add(button);
            this.m_142416_(button);
         }
      }

   }

   public void m_88315_(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(ms);
      Iterator var5 = this.f_169369_.iterator();

      while(var5.hasNext()) {
         Renderable widget = (Renderable)var5.next();
         if (widget instanceof IndexPageButton) {
            IndexPageButton button = (IndexPageButton)widget;
            button.f_93623_ = this.index;
            button.f_93624_ = this.index;
         }
      }

      int cornerX;
      for(cornerX = 0; cornerX < this.indexButtons.size(); ++cornerX) {
         ((IndexPageButton)this.indexButtons.get(cornerX)).f_93623_ = cornerX < 10 * (this.indexPages + 1) && cornerX >= 10 * this.indexPages && this.index;
      }

      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      cornerX = (this.f_96543_ - 390) / 2;
      int cornerY = (this.f_96544_ - 245) / 2;
      ms.m_280163_(TEXTURE, cornerX, cornerY, 0.0F, 0.0F, 390, 245, 390, 390);
      RenderSystem.disableDepthTest();
      super.m_88315_(ms, mouseX, mouseY, partialTicks);
      ms.m_280168_().m_85836_();
      ms.m_280168_().m_252880_((float)cornerX, (float)cornerY, 0.0F);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      int centerX = (this.f_96543_ - 390) / 2;
      int centerY = (this.f_96544_ - 245) / 2;
      if (!this.index) {
         this.drawPerPage(ms, this.bookPages);
         int pageLeft = this.bookPages * 2 + 1;
         int pageRight = pageLeft + 1;
         this.font.m_271703_(pageLeft.makeConcatWithConstants<invokedynamic>(pageLeft), (float)centerX, (float)((double)centerY - 31.85D), 3158064, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         this.font.m_271703_(pageRight.makeConcatWithConstants<invokedynamic>(pageRight), (float)centerX, (float)((double)centerY - 31.85D), 3158064, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      }

      ms.m_280168_().m_85849_();
      this.f_169369_.forEach((widgetx) -> {
         widgetx.m_88315_(ms, mouseX, mouseY, partialTicks);
      });
      RenderSystem.enableDepthTest();
   }

   public void drawPerPage(GuiGraphics ms, int bookPages) {
      this.imageFromTxt(ms);
      int drawType;
      boolean drawFire;
      byte j;
      ItemStack var10002;
      switch(this.pageType) {
      case INTRODUCTION:
         if (bookPages == 1) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafBlockRegistry.SAPPHIRE_ORE.get()), 30, 20, 2.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SAPPHIRE_GEM.get()), 40, 60, 2.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 0, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            drawFire = Minecraft.m_91087_().f_91074_.f_19797_ % 20 < 10;
            this.drawItemStack(ms, new ItemStack((ItemLike)(drawFire ? Items.f_42587_ : (ItemLike)IafItemRegistry.SILVER_NUGGET.get())), 144, 34, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)(drawFire ? Items.f_42587_ : (ItemLike)IafItemRegistry.SILVER_NUGGET.get())), 161, 34, 1.5F);
            this.drawItemStack(ms, new ItemStack(drawFire ? (ItemLike)IafBlockRegistry.GOLD_PILE.get() : (ItemLike)IafBlockRegistry.SILVER_PILE.get()), 151, 7, 2.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 90, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Blocks.f_50705_), 161, 124, 1.5F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50705_), 161, 107, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MANUSCRIPT.get()), 161, 91, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafBlockRegistry.LECTERN.get()), 151, 78, 2.0F);
         }
      case FIREDRAGON:
      case FIREDRAGONEGG:
      case LIGHTNINGDRAGON:
      case LIGHTNINGDRAGONEGG:
      case ICEDRAGON:
      case ICEDRAGONEGG:
      default:
         break;
      case TAMEDDRAGONS:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 90, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42500_), 145, 124, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42485_), 145, 107, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42500_), 145, 91, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42485_), 161, 124, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42500_), 161, 107, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42485_), 161, 91, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42500_), 177, 124, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42485_), 177, 107, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42500_), 177, 91, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_MEAL.get()), 151, 78, 2.0F);
         }

         if (bookPages == 1) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 0, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_FIRE.get()), 161, 17, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 161, 32, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_STAFF.get()), 151, 10, 2.0F);
         }

         if (bookPages == 2) {
            ms.m_280168_().m_85836_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafBlockRegistry.FIRE_LILY.get()), 5, 14, 3.75F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafBlockRegistry.FROST_LILY.get()), 30, 14, 3.75F);
            ms.m_280168_().m_85849_();
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 0, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            drawFire = Minecraft.m_91087_().f_91074_.f_19797_ % 40 < 20;
            this.drawItemStack(ms, new ItemStack(drawFire ? (ItemLike)IafBlockRegistry.FIRE_LILY.get() : (ItemLike)IafBlockRegistry.FROST_LILY.get()), 161, 17, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42399_), 161, 32, 1.5F);
            this.drawItemStack(ms, new ItemStack(drawFire ? Items.f_42585_ : Items.f_42696_), 177, 17, 1.5F);
            this.drawItemStack(ms, new ItemStack(drawFire ? (ItemLike)IafItemRegistry.FIRE_STEW.get() : (ItemLike)IafItemRegistry.FROST_STEW.get()), 151, 10, 2.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 65, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 144, 97, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 180, 110, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 180, 92, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 198, 92, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 198, 74, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_HORN.get()), 151, 60, 2.0F);
         }

         if (bookPages == 3) {
            j = 18;
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONARMOR_IRON_0.get(), 1);
            drawType = j + 16;
            this.drawItemStack(ms, var10002, drawType, 60, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONARMOR_IRON_1.get(), 1);
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 60, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONARMOR_IRON_2.get(), 1);
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 60, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONARMOR_IRON_3.get(), 1);
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 60, 1.5F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 10, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 160, 12, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 180, 31, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42416_), 199, 50, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_FLUTE.get()), 151, 18, 2.0F);
         }
         break;
      case MATERIALS:
         if (bookPages == 0) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGONSCALES_RED.get()), 18, 16, 3.75F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get()), 70, 10, 3.75F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.WITHERBONE.get()), 112, 70, 2.5F);
            j = 18;
            var10002 = new ItemStack((ItemLike)EnumDragonArmor.armor_red.helmet.get());
            drawType = j + 16;
            this.drawItemStack(ms, var10002, drawType, 115, 1.5F);
            var10002 = new ItemStack((ItemLike)EnumDragonArmor.armor_red.chestplate.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 115, 1.5F);
            var10002 = new ItemStack((ItemLike)EnumDragonArmor.armor_red.leggings.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 115, 1.5F);
            var10002 = new ItemStack((ItemLike)EnumDragonArmor.armor_red.boots.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 115, 1.5F);
         }

         if (bookPages == 1) {
            j = 1;
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get());
            drawType = j + 16;
            this.drawItemStack(ms, var10002, drawType, 14, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_PICKAXE.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 14, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_AXE.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 14, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_SHOVEL.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 14, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_HOE.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 14, 1.5F);
            var10002 = new ItemStack((ItemLike)IafItemRegistry.DRAGON_BOW.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 14, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.FIRE_DRAGON_FLESH.get()), 18, 24, 3.75F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.FIRE_DRAGON_HEART.get()), 70, 14, 3.75F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_FIRE.get()), 70, 39, 3.75F);
         }

         if (bookPages == 2) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.FIRE_DRAGON_BLOOD.get()), 18, 24, 3.75F);
         }
         break;
      case ALCHEMY:
         if (bookPages == 0) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.FIRE_DRAGON_BLOOD.get()), 10, 24, 3.75F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.ICE_DRAGON_BLOOD.get()), 26, 24, 3.75F);
            drawFire = Minecraft.m_91087_().f_91074_.f_19797_ % 40 < 20;
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DRAGONBONE_SWORD.get()), 161, 17, 1.5F);
            this.drawItemStack(ms, new ItemStack(drawFire ? (ItemLike)IafItemRegistry.FIRE_DRAGON_BLOOD.get() : (ItemLike)IafItemRegistry.ICE_DRAGON_BLOOD.get()), 161, 32, 1.5F);
            this.drawItemStack(ms, new ItemStack(drawFire ? (ItemLike)IafItemRegistry.DRAGONBONE_SWORD_FIRE.get() : (ItemLike)IafItemRegistry.DRAGONBONE_SWORD_ICE.get()), 151, 10, 2.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 0, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
         }
         break;
      case HIPPOGRYPH:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(0.8F, 0.8F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 29, 150, 303, 151, 61, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 91, 150, 364, 151, 61, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 151, 150, 425, 151, 61, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 29, 190, 303, 187, 61, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 91, 190, 364, 187, 61, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 151, 190, 425, 187, 61, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 90, 230, 425, 223, 61, 35, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42648_), 70, 20, 3.75F);
         }

         if (bookPages == 1) {
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 16, 24, 3.75F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 10, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42402_), 160, 31, 1.35F);
            drawType = Minecraft.m_91087_().f_91074_.f_19797_ % 60 > 40 ? 2 : (Minecraft.m_91087_().f_91074_.f_19797_ % 60 > 20 ? 1 : 0);
            this.drawItemStack(ms, new ItemStack(drawType == 0 ? Items.f_42651_ : (drawType == 1 ? Items.f_42652_ : Items.f_42653_)), 180, 31, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42402_), 199, 31, 1.35F);
            this.drawItemStack(ms, new ItemStack(drawType == 0 ? (ItemLike)IafItemRegistry.IRON_HIPPOGRYPH_ARMOR.get() : (drawType == 1 ? (ItemLike)IafItemRegistry.GOLD_HIPPOGRYPH_ARMOR.get() : (ItemLike)IafItemRegistry.DIAMOND_HIPPOGRYPH_ARMOR.get())), 151, 18, 2.0F);
            this.drawItemStack(ms, new ItemStack(Items.f_42699_), 70, 23, 3.75F);
         }
         break;
      case GORGON:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 10, 89, 473, 117, 19, 34, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 50, 78, 399, 106, 28, 45, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 100, 89, 455, 117, 18, 34, 512.0F);
            ms.m_280168_().m_85849_();
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 70, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42401_), 160, 97, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42454_), 180, 97, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42401_), 199, 97, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.BLINDFOLD.get()), 171, 65, 2.0F);
         }

         if (bookPages == 1) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.GORGON_HEAD.get()), 16, 12, 3.75F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.7F, 1.7F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 37, 95, 473, 117, 19, 34, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 60, 95, 455, 117, 18, 34, 512.0F);
            ms.m_280168_().m_85849_();
         }
         break;
      case PIXIE:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            this.drawImage(ms, DRAWINGS_0, 20, 60, 371, 258, 47, 35, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 42, 95, 416, 258, 45, 35, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 67, 60, 462, 258, 47, 35, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 88, 95, 370, 293, 47, 35, 512.0F);
            this.drawImage(ms, DRAWINGS_0, 110, 60, 416, 293, 47, 35, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get()), 70, 10, 3.75F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(0.9F, 0.9F, 1.0F);
            ms.m_280168_().m_252880_(20.0F, 24.0F, 0.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 150, 100, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 160, 113, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 199, 113, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50705_), 180, 113, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 160, 131, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 199, 131, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 180, 150, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 160, 150, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50058_), 199, 150, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafBlockRegistry.JAR_EMPTY.get()), 171, 85, 2.0F);
            ms.m_280168_().m_85849_();
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.AMBROSIA.get()), 14, 22, 3.75F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 100, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get()), 180, 131, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42399_), 180, 150, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.AMBROSIA.get()), 171, 85, 2.0F);
         }
         break;
      case CYCLOPS:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.5F);
            this.drawImage(ms, DRAWINGS_0, 185, 8, 399, 328, 24, 63, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.5F);
            this.drawImage(ms, DRAWINGS_0, 50, 35, 423, 328, 24, 63, 512.0F);
            ms.m_280168_().m_85849_();
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 50, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42407_), 180, 76, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 160, 76, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 199, 76, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 160, 57, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 180, 57, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 199, 57, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SHEEP_HELMET.get()), 165, 45, 2.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 144, 95, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42408_), 180, 126, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 160, 126, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 199, 126, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 160, 107, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 199, 107, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 160, 145, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 180, 145, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 199, 145, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SHEEP_CHESTPLATE.get()), 165, 95, 2.0F);
         }

         if (bookPages == 2) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.5F);
            this.drawImage(ms, DRAWINGS_0, 185, 30, 447, 328, 24, 63, 512.0F);
            ms.m_280168_().m_85849_();
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 13, 24, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42462_), 34, 46, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 14, 46, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 53, 46, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 14, 27, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 34, 27, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 53, 27, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 14, 65, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 53, 65, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SHEEP_LEGGINGS.get()), 64, 27, 2.0F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 13, 84, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42463_), 34, 94, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 14, 113, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 53, 113, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 14, 94, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50041_), 53, 94, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SHEEP_BOOTS.get()), 64, 73, 2.0F);
         }
         break;
      case SIREN:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.25F, 1.25F, 1.25F);
            this.drawImage(ms, DRAWINGS_1, 190, 25, 0, 0, 25, 42, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 220, 15, 25, 0, 25, 42, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 255, 25, 50, 0, 25, 42, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 190, 135, 0, 42, 26, 28, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 220, 125, 26, 42, 26, 28, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 255, 135, 52, 42, 26, 28, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.EARPLUGS.get()), 18, 40, 3.75F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 160, 0, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Blocks.f_50251_), 180, 20, 1.35F);
            this.drawItemStack(ms, new ItemStack(Blocks.f_50251_), 215, 20, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.EARPLUGS.get()), 170, 10, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SHINY_SCALES.get()), 123, 75, 2.25F);
         }
         break;
      case HIPPOCAMPUS:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            this.drawImage(ms, DRAWINGS_1, 210, 25, 0, 70, 57, 49, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 265, 25, 57, 70, 57, 49, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 320, 25, 0, 119, 57, 49, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 210, 80, 57, 119, 57, 49, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 265, 80, 0, 168, 57, 49, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 320, 80, 57, 168, 57, 49, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            this.drawItemStack(ms, new ItemStack(Items.f_41910_), 37, 33, 2.25F);
            this.drawItemStack(ms, new ItemStack(Items.f_42696_), 37, 73, 2.25F);
         }

         if (bookPages == 2) {
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 35, 25, 2.25F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SHINY_SCALES.get()), 35, 75, 2.25F);
         }
         break;
      case DEATHWORM:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            this.drawImage(ms, DRAWINGS_1, 230, 25, 0, 217, 133, 16, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 230, 50, 0, 233, 133, 16, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 230, 75, 0, 249, 133, 16, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            ms.m_280168_().m_85836_();
            this.drawImage(ms, DRAWINGS_1, 25, 95, 0, 265, 148, 44, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 250, 5, 0, 309, 81, 162, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 2) {
            drawType = Minecraft.m_91087_().f_91074_.f_19797_ % 60 > 40 ? 2 : (Minecraft.m_91087_().f_91074_.f_19797_ % 60 > 20 ? 1 : 0);
            Item chitin = (Item)IafItemRegistry.DEATH_WORM_CHITIN_YELLOW.get();
            if (drawType == 2) {
               chitin = (Item)IafItemRegistry.DEATH_WORM_CHITIN_RED.get();
            }

            if (drawType == 1) {
               chitin = (Item)IafItemRegistry.DEATH_WORM_CHITIN_WHITE.get();
            }

            this.drawItemStack(ms, new ItemStack(chitin, 1), 17, 30, 3.75F);
            this.drawItemStack(ms, new ItemStack(drawType == 2 ? (ItemLike)IafItemRegistry.DEATHWORM_RED_HELMET.get() : (drawType == 1 ? (ItemLike)IafItemRegistry.DEATHWORM_WHITE_HELMET.get() : (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_HELMET.get())), 92, 8, 2.25F);
            this.drawItemStack(ms, new ItemStack(drawType == 2 ? (ItemLike)IafItemRegistry.DEATHWORM_RED_CHESTPLATE.get() : (drawType == 1 ? (ItemLike)IafItemRegistry.DEATHWORM_WHITE_CHESTPLATE.get() : (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_CHESTPLATE.get())), 112, 8, 2.25F);
            this.drawItemStack(ms, new ItemStack(drawType == 2 ? (ItemLike)IafItemRegistry.DEATHWORM_RED_LEGGINGS.get() : (drawType == 1 ? (ItemLike)IafItemRegistry.DEATHWORM_WHITE_LEGGINGS.get() : (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_LEGGINGS.get())), 132, 8, 2.25F);
            this.drawItemStack(ms, new ItemStack(drawType == 2 ? (ItemLike)IafItemRegistry.DEATHWORM_RED_BOOTS.get() : (drawType == 1 ? (ItemLike)IafItemRegistry.DEATHWORM_WHITE_BOOTS.get() : (ItemLike)IafItemRegistry.DEATHWORM_YELLOW_BOOTS.get())), 152, 8, 2.25F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DEATHWORM_EGG.get()), 125, 42, 2.25F);
         }

         if (bookPages == 3) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.DEATHWORM_EGG_GIGANTIC.get(), 1), 125, 4, 2.25F);
            this.drawItemStack(ms, new ItemStack(Items.f_42523_), 115, 55, 2.25F);
            this.drawItemStack(ms, new ItemStack(Items.f_42523_), 135, 55, 2.25F);
         }
         break;
      case COCKATRICE:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_1, 155, 10, 114, 0, 88, 36, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 155, 45, 114, 36, 88, 36, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 18, 10, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42401_), 20, 30, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42454_), 40, 30, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42401_), 59, 30, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.BLINDFOLD.get()), 60, 18, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.WITHERBONE.get()), 30, 58, 2.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.ROTTEN_EGG.get()), 109, 18, 2.5F);
         }
         break;
      case STYMPHALIANBIRD:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_1, 34, 46, 114, 72, 59, 37, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 155, 35, 114, 109, 67, 35, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.STYMPHALIAN_BIRD_FEATHER.get()), 109, 60, 2.5F);
         }

         if (bookPages == 1) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 18, 10, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42484_), 40, 13, 1.35F);
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 40, 30, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.STYMPHALIAN_BIRD_FEATHER.get()), 40, 49, 1.35F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.STYMPHALIAN_ARROW.get()), 60, 18, 2.0F);
         }
         break;
      case TROLL:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_1, 15, 60, 156, 211, 25, 58, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 50, 55, 181, 211, 25, 58, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 85, 60, 206, 211, 25, 58, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 155, 22, 114, 145, 24, 66, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 190, 19, 188, 142, 47, 69, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            drawType = Minecraft.m_91087_().f_91074_.f_19797_ % (EnumTroll.Weapon.values().length * 20) / 20;
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumTroll.Weapon.values()[drawType].item.get()), 30, 7, 2.5F);
            int j = Minecraft.m_91087_().f_91074_.f_19797_ % (EnumTroll.values().length * 20) / 20;
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumTroll.values()[j].leather.get()), 100, 30, 2.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.TROLL_TUSK.get()), 120, 30, 2.5F);
         }

         if (bookPages == 2) {
            drawType = Minecraft.m_91087_().f_91074_.f_19797_ % (EnumTroll.values().length * 20) / 20;
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumTroll.values()[drawType].helmet.get()), 27, 15, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumTroll.values()[drawType].chestplate.get()), 47, 15, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumTroll.values()[drawType].leggings.get()), 67, 15, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumTroll.values()[drawType].boots.get()), 87, 15, 1.5F);
         }
         break;
      case MYRMEX:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.51F, 1.51F, 1.0F);
            this.drawImage(ms, DRAWINGS_1, 137, 10, 202, 16, 57, 21, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 195, 10, 278, 16, 57, 21, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.51F, 1.51F, 1.0F);
            this.drawImage(ms, DRAWINGS_1, 7, 17, 202, 37, 59, 21, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 65, 17, 278, 37, 59, 21, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 7, 77, 202, 58, 59, 21, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 65, 77, 278, 58, 59, 21, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 145, 20, 278, 103, 43, 45, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 195, 20, 321, 103, 43, 45, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 2) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.51F, 1.51F, 1.0F);
            this.drawImage(ms, DRAWINGS_1, 25, 13, 202, 79, 76, 24, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 25, 40, 278, 79, 76, 24, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_CHITIN.get()), 125, 43, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_CHITIN.get()), 155, 43, 2.0F);
            int i = 133;
            boolean jungle = Minecraft.m_91087_().f_91074_.f_19797_ % 60 > 30;
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_SHOVEL.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_SHOVEL.get());
            drawType = i + 16;
            this.drawItemStack(ms, var10002, drawType, 100, 1.51F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_PICKAXE.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_PICKAXE.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 100, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_AXE.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_AXE.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 100, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_SWORD.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_SWORD.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 100, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_SWORD_VENOM.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_SWORD_VENOM.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 100, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_HOE.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_HOE.get());
            drawType += 16;
            this.drawItemStack(ms, var10002, drawType, 100, 1.5F);
            int j = 148;
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_HELMET.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_HELMET.get());
            int j = j + 16;
            this.drawItemStack(ms, var10002, j, 115, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_CHESTPLATE.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_CHESTPLATE.get());
            j += 16;
            this.drawItemStack(ms, var10002, j, 115, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_LEGGINGS.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_LEGGINGS.get());
            j += 16;
            this.drawItemStack(ms, var10002, j, 115, 1.5F);
            var10002 = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_BOOTS.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_BOOTS.get());
            j += 16;
            this.drawItemStack(ms, var10002, j, 115, 1.5F);
         }

         if (bookPages == 3) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_STINGER.get()), 35, 22, 2.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get()), 25, 64, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get()), 55, 64, 2.0F);
         }

         if (bookPages == 4) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_STAFF.get()), 25, 73, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_STAFF.get()), 55, 73, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_EGG.get()), 125, 90, 2.0F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_EGG.get()), 155, 90, 2.0F);
         }
         break;
      case AMPHITHERE:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(0.75F, 0.75F, 0.75F);
            this.drawImage(ms, DRAWINGS_1, 70, 97, 257, 163, 136, 93, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 270, 50, 148, 267, 120, 51, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 380, 50, 148, 318, 120, 51, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 270, 100, 148, 369, 120, 51, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 380, 100, 148, 420, 120, 51, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 330, 150, 268, 267, 120, 51, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 2) {
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.AMPHITHERE_FEATHER.get()), 30, 20, 2.5F);
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 19, 71, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            this.drawItemStack(ms, new ItemStack(Items.f_42484_), 36, 73, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 36, 89, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.AMPHITHERE_FEATHER.get()), 36, 106, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.AMPHITHERE_ARROW.get()), 60, 65, 2.0F);
         }
         break;
      case SEASERPENT:
         if (bookPages == 0) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(0.75F, 0.75F, 0.75F);
            this.drawImage(ms, DRAWINGS_1, 290, 5, 422, 0, 90, 64, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 380, 5, 422, 64, 90, 64, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 290, 70, 422, 128, 90, 64, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 380, 70, 422, 192, 90, 64, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 290, 140, 422, 256, 90, 64, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 380, 140, 422, 320, 90, 64, 512.0F);
            this.drawImage(ms, DRAWINGS_1, 345, 210, 422, 384, 90, 64, 512.0F);
            ms.m_280168_().m_85849_();
         }

         if (bookPages == 1) {
            this.drawImage(ms, DRAWINGS_1, 60, 90, 337, 0, 70, 83, 512.0F);
            drawType = Minecraft.m_91087_().f_91074_.f_19797_ % (EnumSeaSerpent.values().length * 20) / 20;
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumSeaSerpent.values()[drawType].scale.get()), 130, 40, 2.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SERPENT_FANG.get()), 90, 40, 2.5F);
         }

         if (bookPages == 2) {
            ms.m_280168_().m_85836_();
            ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
            this.drawImage(ms, DRAWINGS_0, 19, 31, 389, 1, 50, 50, 512.0F);
            ms.m_280168_().m_85849_();
            drawType = Minecraft.m_91087_().f_91074_.f_19797_ % (EnumSeaSerpent.values().length * 20) / 20;
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SERPENT_FANG.get()), 36, 32, 1.5F);
            this.drawItemStack(ms, new ItemStack(Items.f_42398_), 36, 48, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumSeaSerpent.values()[drawType].scale.get()), 36, 66, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumSeaSerpent.values()[drawType].helmet.get()), 34, 125, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumSeaSerpent.values()[drawType].chestplate.get()), 50, 125, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumSeaSerpent.values()[drawType].leggings.get()), 66, 125, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)EnumSeaSerpent.values()[drawType].boots.get()), 82, 125, 1.5F);
            this.drawItemStack(ms, new ItemStack((ItemLike)IafItemRegistry.SEA_SERPENT_ARROW.get()), 60, 33, 2.0F);
         }
      }

      this.writeFromTxt(ms);
   }

   public void imageFromTxt(GuiGraphics ms) {
      String var10000 = this.pageType.toString().toLowerCase(Locale.ROOT);
      String fileName = var10000 + "_" + this.bookPages + ".txt";
      String languageName = Minecraft.m_91087_().f_91066_.f_92075_.toLowerCase(Locale.ROOT);
      ResourceLocation fileLoc = new ResourceLocation("iceandfire:lang/bestiary/" + languageName + "_0/" + fileName);
      ResourceLocation backupLoc = new ResourceLocation("iceandfire:lang/bestiary/en_us_0/" + fileName);
      Optional<Resource> resource = Minecraft.m_91087_().m_91098_().m_213713_(fileLoc);
      if (resource.isEmpty()) {
         resource = Minecraft.m_91087_().m_91098_().m_213713_(backupLoc);
      }

      try {
         if (resource.isPresent()) {
            List<String> lines = IOUtils.readLines(((Resource)resource.get()).m_215507_(), StandardCharsets.UTF_8);
            int zLevelAdd = 0;
            Iterator var9 = lines.iterator();

            while(true) {
               String line;
               String[] split;
               do {
                  if (!var9.hasNext()) {
                     return;
                  }

                  line = (String)var9.next();
                  line = line.trim();
                  if ((line.contains("<") || line.contains(">")) && line.contains("<image>")) {
                     line = line.substring(8, line.length() - 1);
                     split = line.split(" ");
                     String texture = "iceandfire:textures/gui/bestiary/" + split[0];
                     ResourceLocation resourcelocation = (ResourceLocation)PICTURE_LOCATION_CACHE.get(texture);
                     if (resourcelocation == null) {
                        resourcelocation = new ResourceLocation(texture);
                        PICTURE_LOCATION_CACHE.put(texture, resourcelocation);
                     }

                     ms.m_280168_().m_85836_();
                     this.drawImage(ms, resourcelocation, Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Integer.parseInt(split[4]), Integer.parseInt(split[5]), Integer.parseInt(split[6]), Float.parseFloat(split[7]) * 512.0F);
                     ms.m_280168_().m_85849_();
                  }

                  if (line.contains("<item>")) {
                     line = line.substring(7, line.length() - 1);
                     split = line.split(" ");
                     RenderSystem.enableDepthTest();
                     this.drawItemStack(ms, new ItemStack(getItemByRegistryName(split[0]), 1), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Float.parseFloat(split[4]) * 2.0F);
                  }

                  if (line.contains("<block>")) {
                     ++zLevelAdd;
                     line = line.substring(8, line.length() - 1);
                     split = line.split(" ");
                     RenderSystem.enableDepthTest();
                     this.drawBlockStack(ms, new ItemStack(getItemByRegistryName(split[0]), 1), Integer.parseInt(split[2]), Integer.parseInt(split[3]), Float.parseFloat(split[4]) * 2.0F, zLevelAdd);
                  }
               } while(!line.contains("<recipe>"));

               line = line.substring(9, line.length() - 1);
               split = line.split(" ");
               RenderSystem.enableDepthTest();
               float scale = Float.parseFloat(split[split.length - 1]);
               int x = Integer.parseInt(split[split.length - 3]);
               int y = Integer.parseInt(split[split.length - 2]);
               ItemStack result = new ItemStack(getItemByRegistryName(split[0]), 1);
               ItemStack[] ingredients = new ItemStack[]{ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_};
               int j = 8;

               for(int i = split.length - 5; i >= 2; i -= 2) {
                  ingredients[j] = new ItemStack(getItemByRegistryName(split[i]), 1);
                  --j;
               }

               RenderSystem.enableDepthTest();
               ms.m_280168_().m_85836_();
               this.drawRecipe(ms, result, ingredients, x, y, scale);
               ms.m_280168_().m_85849_();
            }
         }
      } catch (Exception var19) {
         var19.printStackTrace();
      }

   }

   private void drawRecipe(GuiGraphics ms, ItemStack result, ItemStack[] ingredients, int x, int y, float scale) {
      int k = (this.f_96543_ - 390 + 84) / 2;
      int l = (this.f_96544_ - 245 + 40) / 2;
      ms.m_280168_().m_85836_();
      ms.m_280168_().m_85837_((double)x, (double)y, 0.0D);
      ms.m_280168_().m_85841_(scale, scale, scale);
      ms.m_280168_().m_85849_();

      for(int i = 0; i < 9; ++i) {
         ms.m_280168_().m_85836_();
         ms.m_280168_().m_85837_(44.0D, 20.0D, 32.0D);
         ms.m_280168_().m_85837_((double)((float)x + (float)(i % 3 * 22) * scale), (double)((float)y + (float)(i / 3 * 22) * scale), 0.0D);
         ms.m_280168_().m_85841_(scale, scale, scale);
         ms.m_280480_(ingredients[i], 0, 0);
         ms.m_280168_().m_85849_();
      }

      ms.m_280168_().m_85836_();
      ms.m_280168_().m_85837_(40.0D, 20.0D, 32.0D);
      float finScale = scale * 1.5F;
      ms.m_280168_().m_85837_((double)((float)x + 70.0F * finScale), (double)((float)y + 10.0F * finScale), 0.0D);
      ms.m_280168_().m_85841_(finScale, finScale, finScale);
      ms.m_280480_(result, 0, 0);
      ms.m_280168_().m_85849_();
      ms.m_280168_().m_85836_();
      ms.m_280168_().m_252880_((float)x, (float)y, 0.0F);
      ms.m_280168_().m_85841_(scale, scale, 0.0F);
      ms.m_280168_().m_252880_(37.0F, 13.0F, 1.0F);
      ms.m_280168_().m_85841_(1.5F, 1.5F, 1.0F);
      this.drawImage(ms, DRAWINGS_0, 0, 0, 389, 1, 50, 50, 512.0F);
      ms.m_280168_().m_85849_();
   }

   public void writeFromTxt(GuiGraphics ms) {
      String var10000 = this.pageType.toString().toLowerCase(Locale.ROOT);
      String fileName = var10000 + "_" + this.bookPages + ".txt";
      String languageName = Minecraft.m_91087_().f_91066_.f_92075_.toLowerCase(Locale.ROOT);
      ResourceLocation fileLoc = new ResourceLocation("iceandfire:lang/bestiary/" + languageName + "_0/" + fileName);
      ResourceLocation backupLoc = new ResourceLocation("iceandfire:lang/bestiary/en_us_0/" + fileName);
      Optional<Resource> resource = Minecraft.m_91087_().m_91098_().m_213713_(fileLoc);
      if (resource.isEmpty()) {
         resource = Minecraft.m_91087_().m_91098_().m_213713_(backupLoc);
      }

      try {
         List<String> lines = IOUtils.readLines(((Resource)resource.get()).m_215507_(), "UTF-8");
         int linenumber = 0;
         Iterator var9 = lines.iterator();

         while(var9.hasNext()) {
            String line = (String)var9.next();
            line = line.trim();
            if (!line.contains("<") && !line.contains(">")) {
               ms.m_280168_().m_85836_();
               if (this.usingVanillaFont()) {
                  ms.m_280168_().m_85841_(0.945F, 0.945F, 0.945F);
                  ms.m_280168_().m_252880_(0.0F, 5.5F, 0.0F);
               }

               if (linenumber <= 19) {
                  this.font.m_271703_(line, 15.0F, (float)(20 + linenumber * 10), 3158064, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
               } else {
                  this.font.m_271703_(line, 220.0F, (float)((linenumber - 19) * 10), 3158064, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
               }

               ++linenumber;
               ms.m_280168_().m_85849_();
            }
         }
      } catch (Exception var11) {
         var11.printStackTrace();
      }

      ms.m_280168_().m_85836_();
      var10000 = this.pageType.toString();
      String s = StatCollector.translateToLocal("bestiary." + var10000.toLowerCase(Locale.ROOT));
      float scale = this.font.m_92895_(s) <= 100 ? 2.0F : (float)this.font.m_92895_(s) * 0.0125F;
      ms.m_280168_().m_85841_(scale, scale, scale);
      this.font.m_271703_(s, 10.0F, 2.0F, 8025450, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      ms.m_280168_().m_85849_();
   }

   private boolean usingVanillaFont() {
      return this.font == Minecraft.m_91087_().f_91062_;
   }

   public void drawImage(GuiGraphics ms, ResourceLocation texture, int x, int y, int u, int v, int width, int height, float scale) {
      ms.m_280168_().m_85836_();
      RenderSystem.setShaderTexture(0, texture);
      ms.m_280168_().m_85841_(scale / 512.0F, scale / 512.0F, scale / 512.0F);
      ms.m_280163_(texture, x, y, (float)u, (float)v, width, height, 512, 512);
      ms.m_280168_().m_85849_();
   }

   private void drawItemStack(GuiGraphics ms, ItemStack stack, int x, int y, float scale) {
      ms.m_280168_().m_85836_();
      ms.m_280168_().m_85841_(scale, scale, scale);
      ms.m_280480_(stack, x, y);
      ms.m_280168_().m_85849_();
   }

   private void drawBlockStack(GuiGraphics ms, ItemStack stack, int x, int y, float scale, int zScale) {
      ms.m_280168_().m_85836_();
      ms.m_280168_().m_85841_(scale, scale, scale);
      ms.m_280168_().m_252880_(0.0F, 0.0F, (float)(zScale * 10));
      ms.m_280480_(stack, x, y);
      ms.m_280168_().m_85849_();
   }
}
