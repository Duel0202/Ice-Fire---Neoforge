package com.github.alexthe666.iceandfire.client.render.entity;

import com.github.alexthe666.iceandfire.client.model.ModelDreadThrall;
import com.github.alexthe666.iceandfire.client.model.util.HideableLayer;
import com.github.alexthe666.iceandfire.client.render.entity.layer.IHasArmorVariantResource;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerBipedArmorMultiple;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerGenericGlowing;
import com.github.alexthe666.iceandfire.entity.EntityDreadThrall;
import com.mojang.blaze3d.vertex.PoseStack;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.NotNull;

public class RenderDreadThrall extends MobRenderer<EntityDreadThrall, ModelDreadThrall> implements IHasArmorVariantResource {
   public static final ResourceLocation TEXTURE = new ResourceLocation("iceandfire:textures/models/dread/dread_thrall.png");
   public static final ResourceLocation TEXTURE_EYES = new ResourceLocation("iceandfire:textures/models/dread/dread_thrall_eyes.png");
   public static final ResourceLocation TEXTURE_LEG_ARMOR = new ResourceLocation("iceandfire:textures/models/dread/thrall_legs.png");
   public static final ResourceLocation TEXTURE_ARMOR_0 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_1.png");
   public static final ResourceLocation TEXTURE_ARMOR_1 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_2.png");
   public static final ResourceLocation TEXTURE_ARMOR_2 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_3.png");
   public static final ResourceLocation TEXTURE_ARMOR_3 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_4.png");
   public static final ResourceLocation TEXTURE_ARMOR_4 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_5.png");
   public static final ResourceLocation TEXTURE_ARMOR_5 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_6.png");
   public static final ResourceLocation TEXTURE_ARMOR_6 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_7.png");
   public static final ResourceLocation TEXTURE_ARMOR_7 = new ResourceLocation("iceandfire:textures/models/dread/thrall_chest_8.png");
   public final HideableLayer<EntityDreadThrall, ModelDreadThrall, ItemInHandLayer<EntityDreadThrall, ModelDreadThrall>> itemLayer;

   public RenderDreadThrall(Context context) {
      super(context, new ModelDreadThrall(0.0F, false), 0.6F);
      this.m_115326_(new LayerGenericGlowing(this, TEXTURE_EYES));
      this.itemLayer = new HideableLayer(new ItemInHandLayer(this, context.m_234598_()), this);
      this.m_115326_(this.itemLayer);
      this.m_115326_(new LayerBipedArmorMultiple(this, new ModelDreadThrall(0.5F, true), new ModelDreadThrall(1.0F, true), TEXTURE_ARMOR_0, TEXTURE_LEG_ARMOR));
   }

   public ResourceLocation getArmorResource(int variant, EquipmentSlot equipmentSlotType) {
      if (equipmentSlotType == EquipmentSlot.LEGS) {
         return TEXTURE_LEG_ARMOR;
      } else {
         switch(variant) {
         case 0:
            return TEXTURE_ARMOR_0;
         case 1:
            return TEXTURE_ARMOR_1;
         case 2:
            return TEXTURE_ARMOR_2;
         case 3:
            return TEXTURE_ARMOR_3;
         case 4:
            return TEXTURE_ARMOR_4;
         case 5:
            return TEXTURE_ARMOR_5;
         case 6:
            return TEXTURE_ARMOR_6;
         case 7:
            return TEXTURE_ARMOR_7;
         default:
            return TEXTURE_ARMOR_0;
         }
      }
   }

   public void scale(EntityDreadThrall livingEntityIn, PoseStack stack, float partialTickTime) {
      stack.m_85841_(0.95F, 0.95F, 0.95F);
      if (livingEntityIn.getAnimation() == ((ModelDreadThrall)this.m_7200_()).getSpawnAnimation()) {
         this.itemLayer.hidden = livingEntityIn.getAnimationTick() <= ((ModelDreadThrall)this.m_7200_()).getSpawnAnimation().getDuration() - 10;
      } else {
         this.itemLayer.hidden = false;
      }
   }

   @Nullable
   public ResourceLocation getTextureLocation(@NotNull EntityDreadThrall entity) {
      return TEXTURE;
   }
}
