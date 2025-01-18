package com.github.alexthe666.iceandfire.client.gui;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.ClientProxy;
import com.github.alexthe666.iceandfire.client.gui.bestiary.ChangePageButton;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.message.MessageGetMyrmexHive;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuiMyrmexStaff extends Screen {
   private static final ResourceLocation JUNGLE_TEXTURE = new ResourceLocation("iceandfire:textures/gui/myrmex_staff_jungle.png");
   private static final ResourceLocation DESERT_TEXTURE = new ResourceLocation("iceandfire:textures/gui/myrmex_staff_desert.png");
   private static final WorldGenMyrmexHive.RoomType[] ROOMS;
   private static final int ROOMS_PER_PAGE = 5;
   private final List<GuiMyrmexStaff.Room> allRoomPos = Lists.newArrayList();
   private final List<MyrmexDeleteButton> allRoomButtonPos = Lists.newArrayList();
   public ChangePageButton previousPage;
   public ChangePageButton nextPage;
   int ticksSinceDeleted = 0;
   int currentPage = 0;
   private final boolean jungle;
   private int hiveCount;

   public GuiMyrmexStaff(ItemStack staff) {
      super(Component.m_237115_("myrmex_staff_screen"));
      this.jungle = staff.m_41720_() == IafItemRegistry.MYRMEX_JUNGLE_STAFF.get();
   }

   protected void m_7856_() {
      super.m_7856_();
      this.f_169369_.clear();
      this.allRoomButtonPos.clear();
      int i = (this.f_96543_ - 248) / 2;
      int j = (this.f_96544_ - 166) / 2;
      int x_translate = 193;
      int y_translate = 37;
      if (ClientProxy.getReferedClientHive() != null) {
         this.populateRoomMap();
         this.m_7787_(Button.m_253074_(ClientProxy.getReferedClientHive().reproduces ? Component.m_237115_("myrmex.message.disablebreeding") : Component.m_237115_("myrmex.message.enablebreeding"), (p_214132_1_) -> {
            boolean opposite = !ClientProxy.getReferedClientHive().reproduces;
            ClientProxy.getReferedClientHive().reproduces = opposite;
         }).m_252794_(i + 124, j + 15).m_253046_(120, 20).m_253136_());
         this.m_7787_(this.previousPage = new ChangePageButton(i + 5, j + 150, false, this.jungle ? 2 : 1, (p_214132_1_) -> {
            if (this.currentPage > 0) {
               --this.currentPage;
            }

         }));
         this.m_7787_(this.nextPage = new ChangePageButton(i + 225, j + 150, true, this.jungle ? 2 : 1, (p_214132_1_) -> {
            if (this.currentPage < this.allRoomButtonPos.size() / 5) {
               ++this.currentPage;
            }

         }));
         int totalRooms = this.allRoomPos.size();

         for(int rooms = 0; rooms < this.allRoomPos.size(); ++rooms) {
            int yIndex = rooms % 5;
            BlockPos pos = ((GuiMyrmexStaff.Room)this.allRoomPos.get(rooms)).pos;
            MyrmexDeleteButton button = new MyrmexDeleteButton(i + x_translate, j + y_translate + yIndex * 22, pos, Component.m_237115_("myrmex.message.delete"), (p_214132_1_) -> {
               if (this.ticksSinceDeleted <= 0) {
                  ClientProxy.getReferedClientHive().removeRoom(pos);
                  this.ticksSinceDeleted = 5;
               }

            });
            button.f_93624_ = rooms < 5 * (this.currentPage + 1) && rooms >= 5 * this.currentPage;
            this.m_7787_(button);
            this.allRoomButtonPos.add(button);
         }

         if (totalRooms <= 5 * this.currentPage && this.currentPage > 0) {
            --this.currentPage;
         }

      }
   }

   private void populateRoomMap() {
      this.allRoomPos.clear();
      WorldGenMyrmexHive.RoomType[] var1 = ROOMS;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         WorldGenMyrmexHive.RoomType type = var1[var3];
         List<BlockPos> roomPos = ClientProxy.getReferedClientHive().getRooms(type);
         Iterator var6 = roomPos.iterator();

         while(var6.hasNext()) {
            BlockPos pos = (BlockPos)var6.next();
            String name = type == WorldGenMyrmexHive.RoomType.FOOD ? "food" : (type == WorldGenMyrmexHive.RoomType.NURSERY ? "nursery" : "misc");
            this.allRoomPos.add(new GuiMyrmexStaff.Room(pos, name));
         }
      }

      Iterator var9 = ClientProxy.getReferedClientHive().getEntrances().keySet().iterator();

      BlockPos pos;
      while(var9.hasNext()) {
         pos = (BlockPos)var9.next();
         this.allRoomPos.add(new GuiMyrmexStaff.Room(pos, "enterance_surface"));
      }

      var9 = ClientProxy.getReferedClientHive().getEntranceBottoms().keySet().iterator();

      while(var9.hasNext()) {
         pos = (BlockPos)var9.next();
         this.allRoomPos.add(new GuiMyrmexStaff.Room(pos, "enterance_bottom"));
      }

   }

   public void m_280273_(@NotNull GuiGraphics ms) {
      super.m_280273_(ms);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
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
      if (this.ticksSinceDeleted > 0) {
         --this.ticksSinceDeleted;
      }

      this.hiveCount = 0;

      int opinion;
      for(opinion = 0; opinion < this.allRoomButtonPos.size(); ++opinion) {
         if (opinion < 5 * (this.currentPage + 1) && opinion >= 5 * this.currentPage) {
            this.drawRoomInfo(ms, ((GuiMyrmexStaff.Room)this.allRoomPos.get(opinion)).string, ((GuiMyrmexStaff.Room)this.allRoomPos.get(opinion)).pos, i, j, color);
         }
      }

      if (ClientProxy.getReferedClientHive() != null) {
         if (!ClientProxy.getReferedClientHive().colonyName.isEmpty()) {
            String title = I18n.m_118938_("myrmex.message.colony_named", new Object[]{ClientProxy.getReferedClientHive().colonyName});
            this.getMinecraft().f_91062_.m_271703_(title, (float)(i + 40 - title.length() / 2), (float)(j - 3), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         } else {
            this.getMinecraft().f_91062_.m_271703_(I18n.m_118938_("myrmex.message.colony", new Object[0]), (float)(i + 80), (float)(j - 3), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         }

         opinion = ClientProxy.getReferedClientHive().getPlayerReputation(Minecraft.m_91087_().f_91074_.m_20148_());
         this.getMinecraft().f_91062_.m_271703_(I18n.m_118938_("myrmex.message.hive_opinion", new Object[]{opinion}), (float)i, (float)(j + 12), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
         this.getMinecraft().f_91062_.m_271703_(I18n.m_118938_("myrmex.message.rooms", new Object[0]), (float)i, (float)(j + 25), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      }

   }

   public void m_7861_() {
      if (ClientProxy.getReferedClientHive() != null) {
         IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageGetMyrmexHive(ClientProxy.getReferedClientHive().toNBT()));
      }

   }

   private void drawRoomInfo(GuiGraphics ms, String type, BlockPos pos, int i, int j, int color) {
      String translate = "myrmex.message.room." + type;
      this.getMinecraft().f_91062_.m_271703_(I18n.m_118938_(translate, new Object[]{pos.m_123341_(), pos.m_123342_(), pos.m_123343_()}), (float)i, (float)(j + 36 + this.hiveCount * 22), color, false, ms.m_280168_().m_85850_().m_252922_(), ms.m_280091_(), DisplayMode.NORMAL, 0, 15728880);
      ++this.hiveCount;
   }

   static {
      ROOMS = new WorldGenMyrmexHive.RoomType[]{WorldGenMyrmexHive.RoomType.FOOD, WorldGenMyrmexHive.RoomType.NURSERY, WorldGenMyrmexHive.RoomType.EMPTY};
   }

   private class Room {
      public BlockPos pos;
      public String string;

      public Room(BlockPos pos, String string) {
         this.pos = pos;
         this.string = string;
      }
   }
}
