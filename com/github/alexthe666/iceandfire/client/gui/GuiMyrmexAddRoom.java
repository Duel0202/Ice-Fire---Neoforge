package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.ClientProxy;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.message.MessageGetMyrmexHive;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuiMyrmexAddRoom extends Screen {
   private static final ResourceLocation JUNGLE_TEXTURE = new ResourceLocation("iceandfire:textures/gui/myrmex_staff_jungle.png");
   private static final ResourceLocation DESERT_TEXTURE = new ResourceLocation("iceandfire:textures/gui/myrmex_staff_desert.png");
   private final boolean jungle;
   private final BlockPos interactPos;
   private final Direction facing;

   public GuiMyrmexAddRoom(ItemStack staff, BlockPos interactPos, Direction facing) {
      super(Component.m_237115_("myrmex_add_room"));
      this.jungle = staff.m_41720_() == IafItemRegistry.MYRMEX_JUNGLE_STAFF.get();
      this.interactPos = interactPos;
      this.facing = facing;
      this.m_7856_();
   }

   public static void onGuiClosed() {
      IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageGetMyrmexHive(ClientProxy.getReferedClientHive().toNBT()));
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_169369_.clear();
      int i = (this.f_96543_ - 248) / 2;
      int j = (this.f_96544_ - 166) / 2;
      if (ClientProxy.getReferedClientHive() != null) {
         Player player = Minecraft.m_91087_().f_91074_;
         this.m_7787_(Button.m_253074_(Component.m_237115_("myrmex.message.establishroom_food"), (p_214132_1_) -> {
            ClientProxy.getReferedClientHive().addRoomWithMessage(player, this.interactPos, WorldGenMyrmexHive.RoomType.FOOD);
            onGuiClosed();
            Minecraft.m_91087_().m_91152_((Screen)null);
         }).m_252794_(i + 50, j + 35).m_253046_(150, 20).m_253136_());
         this.m_7787_(Button.m_253074_(Component.m_237115_("myrmex.message.establishroom_nursery"), (p_214132_1_) -> {
            ClientProxy.getReferedClientHive().addRoomWithMessage(player, this.interactPos, WorldGenMyrmexHive.RoomType.NURSERY);
            onGuiClosed();
            Minecraft.m_91087_().m_91152_((Screen)null);
         }).m_252794_(i + 50, j + 60).m_253046_(150, 20).m_253136_());
         this.m_7787_(Button.m_253074_(Component.m_237115_("myrmex.message.establishroom_enterance_surface"), (p_214132_1_) -> {
            ClientProxy.getReferedClientHive().addEnteranceWithMessage(player, false, this.interactPos, this.facing);
            onGuiClosed();
            Minecraft.m_91087_().m_91152_((Screen)null);
         }).m_252794_(i + 50, j + 85).m_253046_(150, 20).m_253136_());
         this.m_7787_(Button.m_253074_(Component.m_237115_("myrmex.message.establishroom_enterance_bottom"), (p_214132_1_) -> {
            ClientProxy.getReferedClientHive().addEnteranceWithMessage(player, true, this.interactPos, this.facing);
            onGuiClosed();
            Minecraft.m_91087_().m_91152_((Screen)null);
         }).m_252794_(i + 50, j + 110).m_253046_(150, 20).m_253136_());
         this.m_7787_(Button.m_253074_(Component.m_237115_("myrmex.message.establishroom_misc"), (p_214132_1_) -> {
            ClientProxy.getReferedClientHive().addRoomWithMessage(player, this.interactPos, WorldGenMyrmexHive.RoomType.EMPTY);
            onGuiClosed();
            Minecraft.m_91087_().m_91152_((Screen)null);
         }).m_252794_(i + 50, j + 135).m_253046_(150, 20).m_253136_());
      }

   }

   public void m_280273_(@NotNull GuiGraphics ms) {
      super.m_280273_(ms);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      this.getMinecraft().m_91097_().m_174784_(this.jungle ? JUNGLE_TEXTURE : DESERT_TEXTURE);
      int i = (this.f_96543_ - 248) / 2;
      int j = (this.f_96544_ - 166) / 2;
      ms.m_280218_(this.jungle ? JUNGLE_TEXTURE : DESERT_TEXTURE, i, j, 0, 0, 248, 166);
   }

   public void m_88315_(@NotNull GuiGraphics ms, int mouseX, int mouseY, float partialTicks) {
      this.m_280273_(ms);
      this.m_7856_();
      int i = (this.f_96543_ - 248) / 2 + 10;
      int j = (this.f_96544_ - 166) / 2 + 8;
      super.m_88315_(ms, mouseX, mouseY, partialTicks);
      int color = this.jungle ? 3533333 : 16760576;
      if (ClientProxy.getReferedClientHive() != null) {
         if (!ClientProxy.getReferedClientHive().colonyName.isEmpty()) {
            String title = I18n.m_118938_("myrmex.message.colony_named", new Object[]{ClientProxy.getReferedClientHive().colonyName});
            this.getMinecraft().f_91062_.m_271703_(title, (float)(i + 40 - title.length() / 2), (float)(j - 3), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         } else {
            this.getMinecraft().f_91062_.m_271703_(I18n.m_118938_("myrmex.message.colony", new Object[0]), (float)(i + 80), (float)(j - 3), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         }

         this.getMinecraft().f_91062_.m_271703_(I18n.m_118938_("myrmex.message.create_new_room", new Object[]{this.interactPos.m_123341_(), this.interactPos.m_123342_(), this.interactPos.m_123343_()}), (float)(i + 30), (float)(j + 6), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      }

   }

   public boolean m_7043_() {
      return false;
   }
}
