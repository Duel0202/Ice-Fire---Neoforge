package com.github.alexthe666.iceandfire.client.render;

import com.github.alexthe666.iceandfire.client.IafClientSetup;
import com.github.alexthe666.iceandfire.client.render.tile.RenderDreadPortal;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderStateShard.MultiTextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.TransparencyStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;

public class IafRenderType extends RenderType {
   private static final ResourceLocation STONE_TEXTURE = new ResourceLocation("textures/block/stone.png");
   protected static final ShaderStateShard RENDERTYPE_DREAD_PORTAL_SHADER = new ShaderStateShard(IafClientSetup::getRendertypeDreadPortalShader);
   private static final RenderType DREADLANDS_PORTAL;
   protected static final TransparencyStateShard GHOST_TRANSPARANCY;

   public IafRenderType(String nameIn, VertexFormat formatIn, Mode drawModeIn, int bufferSizeIn, boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
      super(nameIn, formatIn, drawModeIn, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
   }

   public static RenderType getGhost(ResourceLocation locationIn) {
      TextureStateShard lvt_1_1_ = new TextureStateShard(locationIn, false, false);
      return m_173215_("ghost_iaf", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(f_173114_).m_173290_(lvt_1_1_).m_110685_(GHOST_TRANSPARANCY).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true));
   }

   public static RenderType getGhostDaytime(ResourceLocation locationIn) {
      TextureStateShard lvt_1_1_ = new TextureStateShard(locationIn, false, false);
      return m_173215_("ghost_iaf_day", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(f_173114_).m_173290_(lvt_1_1_).m_110685_(f_110139_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true));
   }

   public static RenderType getDreadlandsPortal() {
      return DREADLANDS_PORTAL;
   }

   public static RenderType getStoneMobRenderType(float x, float y) {
      TextureStateShard textureState = new TextureStateShard(STONE_TEXTURE, false, false);
      CompositeState rendertype = CompositeState.m_110628_().m_173292_(RenderType.f_173113_).m_173290_(textureState).m_110685_(f_110134_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true);
      return m_173215_("stone_entity_type", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, false, true, rendertype);
   }

   public static RenderType getIce(ResourceLocation locationIn) {
      TextureStateShard lvt_1_1_ = new TextureStateShard(locationIn, false, false);
      return m_173215_("ice_texture", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, false, true, CompositeState.m_110628_().m_173292_(RenderType.f_173068_).m_173290_(lvt_1_1_).m_110685_(f_110139_).m_110661_(f_110158_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(true));
   }

   public static RenderType getStoneCrackRenderType(ResourceLocation crackTex) {
      TextureStateShard textureState = new TextureStateShard(crackTex, false, false);
      CompositeState rendertype$state = CompositeState.m_110628_().m_173290_(textureState).m_173292_(RenderType.f_173113_).m_110685_(f_110139_).m_110663_(f_110112_).m_110661_(f_110110_).m_110671_(f_110152_).m_110677_(f_110154_).m_110691_(false);
      return m_173215_("stone_entity_type_crack", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, false, true, rendertype$state);
   }

   static {
      DREADLANDS_PORTAL = m_173215_("dreadlands_portal", DefaultVertexFormat.f_85815_, Mode.QUADS, 256, false, false, CompositeState.m_110628_().m_173292_(RENDERTYPE_DREAD_PORTAL_SHADER).m_173290_(MultiTextureStateShard.m_173127_().m_173132_(RenderDreadPortal.DREAD_PORTAL_BACKGROUND, false, false).m_173132_(RenderDreadPortal.DREAD_PORTAL, false, false).m_173131_()).m_110691_(false));
      GHOST_TRANSPARANCY = new TransparencyStateShard("translucent_ghost_transparency", () -> {
         RenderSystem.enableBlend();
         RenderSystem.blendFuncSeparate(SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ONE_MINUS_SRC_ALPHA);
      }, () -> {
         RenderSystem.disableBlend();
         RenderSystem.defaultBlendFunc();
      });
   }
}
