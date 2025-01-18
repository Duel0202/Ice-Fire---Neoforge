package com.github.alexthe666.iceandfire.client.render.entity.layer;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.iceandfire.client.texture.ArrayLayeredTexture;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.enums.EnumDragonTextures;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class LayerDragonArmor extends RenderLayer<EntityDragonBase, AdvancedEntityModel<EntityDragonBase>> {
   private static final Map<String, ResourceLocation> LAYERED_ARMOR_CACHE = Maps.newHashMap();
   private static final EquipmentSlot[] ARMOR_SLOTS;
   private final MobRenderer render;

   public LayerDragonArmor(MobRenderer renderIn, int type) {
      super(renderIn);
      this.render = renderIn;
   }

   public static void clearCache(String str) {
      LAYERED_ARMOR_CACHE.remove(str);
   }

   public void render(@NotNull PoseStack matrixStackIn, @NotNull MultiBufferSource bufferIn, int packedLightIn, EntityDragonBase dragon, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      int armorHead = dragon.getArmorOrdinal(dragon.m_6844_(EquipmentSlot.HEAD));
      int armorNeck = dragon.getArmorOrdinal(dragon.m_6844_(EquipmentSlot.CHEST));
      int armorLegs = dragon.getArmorOrdinal(dragon.m_6844_(EquipmentSlot.LEGS));
      int armorFeet = dragon.getArmorOrdinal(dragon.m_6844_(EquipmentSlot.FEET));
      String armorTexture = dragon.dragonType.getName() + "_" + armorHead + "_" + armorNeck + "_" + armorLegs + "_" + armorFeet;
      if (!armorTexture.equals(dragon.dragonType.getName() + "_0_0_0_0")) {
         ResourceLocation resourcelocation = (ResourceLocation)LAYERED_ARMOR_CACHE.get(armorTexture);
         if (resourcelocation == null) {
            resourcelocation = new ResourceLocation("iceandfiredragon_armor_" + armorTexture);
            List<String> tex = new ArrayList();
            EquipmentSlot[] var18 = ARMOR_SLOTS;
            int var19 = var18.length;

            for(int var20 = 0; var20 < var19; ++var20) {
               EquipmentSlot slot = var18[var20];
               if (dragon.dragonType == DragonType.FIRE) {
                  tex.add(EnumDragonTextures.Armor.getArmorForDragon(dragon, slot).FIRETEXTURE.toString());
               } else if (dragon.dragonType == DragonType.ICE) {
                  tex.add(EnumDragonTextures.Armor.getArmorForDragon(dragon, slot).ICETEXTURE.toString());
               } else {
                  tex.add(EnumDragonTextures.Armor.getArmorForDragon(dragon, slot).LIGHTNINGTEXTURE.toString());
               }
            }

            ArrayLayeredTexture layeredBase = new ArrayLayeredTexture(tex);
            Minecraft.m_91087_().m_91097_().m_118495_(resourcelocation, layeredBase);
            LAYERED_ARMOR_CACHE.put(armorTexture, resourcelocation);
         }

         VertexConsumer ivertexbuilder = bufferIn.m_6299_(RenderType.m_110458_(resourcelocation));
         ((AdvancedEntityModel)this.m_117386_()).m_7695_(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.f_118083_, 1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   static {
      ARMOR_SLOTS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};
   }
}
