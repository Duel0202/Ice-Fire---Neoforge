package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import com.github.alexthe666.iceandfire.client.render.pathfinding.PathfindingDebugRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforge.client.event.RenderLevelStageEvent;
import net.neoforge.client.event.RenderLevelStageEvent.Stage;

public class WorldEventContext {
   public static final WorldEventContext INSTANCE = new WorldEventContext();
   public BufferSource bufferSource;
   public PoseStack poseStack;
   public float partialTicks;
   public ClientLevel clientLevel;
   public LocalPlayer clientPlayer;
   public ItemStack mainHandItem;
   int clientRenderDist;

   private WorldEventContext() {
   }

   public void renderWorldLastEvent(RenderLevelStageEvent event) {
      this.bufferSource = WorldRenderMacros.getBufferSource();
      this.poseStack = event.getPoseStack();
      this.partialTicks = event.getPartialTick();
      this.clientLevel = Minecraft.m_91087_().f_91073_;
      this.clientPlayer = Minecraft.m_91087_().f_91074_;
      this.mainHandItem = this.clientPlayer.m_21205_();
      this.clientRenderDist = (Integer)Minecraft.m_91087_().f_91066_.m_231984_().m_231551_();
      Vec3 cameraPos = Minecraft.m_91087_().f_91063_.m_109153_().m_90583_();
      this.poseStack.m_85836_();
      this.poseStack.m_85837_(-cameraPos.m_7096_(), -cameraPos.m_7098_(), -cameraPos.m_7094_());
      if (event.getStage() == Stage.AFTER_CUTOUT_MIPPED_BLOCKS_BLOCKS) {
         PathfindingDebugRenderer.render(this);
         this.bufferSource.m_109911_();
      } else if (event.getStage() == Stage.AFTER_TRIPWIRE_BLOCKS) {
         this.bufferSource.m_109911_();
      }

      this.poseStack.m_85849_();
   }
}
