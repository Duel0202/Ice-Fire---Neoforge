package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforge;
import com.github.alexthe666.iceandfire.inventory.ContainerDragonForge;
import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class GuiDragonForge extends AbstractContainerScreen<ContainerDragonForge> {
   private static final ResourceLocation TEXTURE_FIRE = new ResourceLocation("iceandfire:textures/gui/dragonforge_fire.png");
   private static final ResourceLocation TEXTURE_ICE = new ResourceLocation("iceandfire:textures/gui/dragonforge_ice.png");
   private static final ResourceLocation TEXTURE_LIGHTNING = new ResourceLocation("iceandfire:textures/gui/dragonforge_lightning.png");
   private final ContainerDragonForge tileFurnace;
   private final int dragonType;

   public GuiDragonForge(ContainerDragonForge container, Inventory inv, Component name) {
      super(container, inv, name);
      this.tileFurnace = container;
      this.dragonType = this.tileFurnace.fireType;
   }

   protected void m_280003_(GuiGraphics pGuiGraphics, int mouseX, int mouseY) {
      Font font = this.getMinecraft().f_91062_;
      if (this.tileFurnace != null) {
         String s = I18n.m_118938_("block.iceandfire.dragonforge_" + DragonType.getNameFromInt(this.dragonType) + "_core", new Object[0]);
         pGuiGraphics.m_280056_(this.f_96547_, s, this.f_97726_ / 2 - font.m_92895_(s) / 2, 6, 4210752, false);
      }

      pGuiGraphics.m_280614_(this.f_96547_, this.f_169604_, 8, this.f_97727_ - 96 + 2, 4210752, false);
   }

   protected void m_7286_(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      ResourceLocation texture = TEXTURE_FIRE;
      if (this.dragonType == 0) {
         texture = TEXTURE_FIRE;
      } else if (this.dragonType == 1) {
         texture = TEXTURE_ICE;
      } else {
         texture = TEXTURE_LIGHTNING;
      }

      int k = (this.f_96543_ - this.f_97726_) / 2;
      int l = (this.f_96544_ - this.f_97727_) / 2;
      pGuiGraphics.m_280218_(texture, k, l, 0, 0, this.f_97726_, this.f_97727_);
      int i1 = this.getCookTime(126);
      pGuiGraphics.m_280218_(texture, k + 12, l + 23, 0, 166, i1, 38);
   }

   private int getCookTime(int p_175381_1_) {
      BlockEntity te = IceAndFire.PROXY.getRefrencedTE();
      int j = 0;
      List<DragonForgeRecipe> recipes = (List)this.getMinecraft().f_91073_.m_7465_().m_44013_((RecipeType)IafRecipeRegistry.DRAGON_FORGE_TYPE.get()).stream().filter((item) -> {
         return item.isValidInput(this.tileFurnace.m_38853_(0).m_7993_()) && item.isValidBlood(this.tileFurnace.m_38853_(1).m_7993_());
      }).collect(Collectors.toList());
      int maxCookTime = recipes.isEmpty() ? 100 : ((DragonForgeRecipe)recipes.get(0)).getCookTime();
      if (te instanceof TileEntityDragonforge) {
         j = Math.min(((TileEntityDragonforge)te).cookTime, maxCookTime);
      }

      return j != 0 ? j * p_175381_1_ / maxCookTime : 0;
   }

   public void m_88315_(GuiGraphics pGuiGraphics, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(pGuiGraphics);
      super.m_88315_(pGuiGraphics, mouseX, mouseY, partialTicks);
      this.m_280072_(pGuiGraphics, mouseX, mouseY);
   }
}
