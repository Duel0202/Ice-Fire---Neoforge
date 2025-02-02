package com.github.alexthe666.iceandfire.event;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.mojang.math.Axis;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforge.client.event.RenderPlayerEvent.Pre;
import net.neoforge.eventbus.api.SubscribeEvent;

public class PlayerRenderEvents {
   public ResourceLocation redTex = new ResourceLocation("iceandfire", "textures/models/misc/cape_fire.png");
   public ResourceLocation redElytraTex = new ResourceLocation("iceandfire", "textures/models/misc/elytra_fire.png");
   public ResourceLocation blueTex = new ResourceLocation("iceandfire", "textures/models/misc/cape_ice.png");
   public ResourceLocation blueElytraTex = new ResourceLocation("iceandfire", "textures/models/misc/elytra_ice.png");
   public ResourceLocation betaTex = new ResourceLocation("iceandfire", "textures/models/misc/cape_beta.png");
   public ResourceLocation betaElytraTex = new ResourceLocation("iceandfire", "textures/models/misc/elytra_beta.png");
   public UUID[] redcapes = new UUID[]{UUID.fromString("59efccaf-902d-45da-928a-5a549b9fd5e0"), UUID.fromString("71363abe-fd03-49c9-940d-aae8b8209b7c")};
   public UUID[] bluecapes = new UUID[]{UUID.fromString("0ed918c8-d612-4360-b711-cd415671356f"), UUID.fromString("5d43896a-06a0-49fb-95c5-38485c63667f")};
   public UUID[] betatesters = new UUID[0];

   @SubscribeEvent
   public void playerRender(Pre event) {
      if (event.getEntity().m_20148_().equals(ServerEvents.ALEX_UUID)) {
         event.getPoseStack().m_85836_();
         float f2 = (float)event.getEntity().f_19797_ - 1.0F + event.getPartialTick();
         float f3 = Mth.m_14031_(f2 / 10.0F) * 0.1F + 0.1F;
         event.getPoseStack().m_252880_(0.0F, event.getEntity().m_20206_() * 1.25F, 0.0F);
         float f4 = f2 / 20.0F * 57.295776F;
         event.getPoseStack().m_252781_(Axis.f_252436_.m_252977_(f4));
         event.getPoseStack().m_85836_();
         Minecraft.m_91087_().m_91291_().m_269491_(Minecraft.m_91087_().f_91074_, new ItemStack((ItemLike)IafItemRegistry.WEEZER_BLUE_ALBUM.get()), ItemDisplayContext.GROUND, false, event.getPoseStack(), event.getMultiBufferSource(), event.getEntity().m_9236_(), event.getPackedLight(), OverlayTexture.f_118083_, 0);
         event.getPoseStack().m_85849_();
         event.getPoseStack().m_85849_();
      }

   }

   private boolean hasRedCape(UUID uniqueID) {
      UUID[] var2 = this.redcapes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         UUID uuid1 = var2[var4];
         if (uniqueID.equals(uuid1)) {
            return true;
         }
      }

      return false;
   }

   private boolean hasBlueCape(UUID uniqueID) {
      UUID[] var2 = this.bluecapes;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         UUID uuid1 = var2[var4];
         if (uniqueID.equals(uuid1)) {
            return true;
         }
      }

      return false;
   }

   private boolean hasBetaCape(UUID uniqueID) {
      UUID[] var2 = this.betatesters;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         UUID uuid1 = var2[var4];
         if (uniqueID.equals(uuid1)) {
            return true;
         }
      }

      return false;
   }
}
