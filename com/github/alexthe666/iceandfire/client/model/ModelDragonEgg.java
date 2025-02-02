package com.github.alexthe666.iceandfire.client.model;

import com.github.alexthe666.citadel.client.model.AdvancedEntityModel;
import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.basic.BasicModelPart;
import com.github.alexthe666.iceandfire.entity.DragonType;
import com.github.alexthe666.iceandfire.entity.EntityDragonEgg;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityEggInIce;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.LivingEntity;

public class ModelDragonEgg<T extends LivingEntity> extends AdvancedEntityModel<T> {
   public AdvancedModelBox Egg1;
   public AdvancedModelBox Egg2;
   public AdvancedModelBox Egg3;
   public AdvancedModelBox Egg4;

   public ModelDragonEgg() {
      this.texWidth = 64;
      this.texHeight = 32;
      this.Egg3 = new AdvancedModelBox(this, 0, 0);
      this.Egg3.setPos(0.0F, 0.0F, 0.0F);
      this.Egg3.addBox(-2.5F, -4.6F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F);
      this.Egg2 = new AdvancedModelBox(this, 22, 2);
      this.Egg2.setPos(0.0F, 0.0F, 0.0F);
      this.Egg2.addBox(-2.5F, -0.6F, -2.5F, 5.0F, 5.0F, 5.0F, 0.0F);
      this.Egg1 = new AdvancedModelBox(this, 0, 12);
      this.Egg1.setPos(0.0F, 19.6F, 0.0F);
      this.Egg1.addBox(-3.0F, -2.8F, -3.0F, 6.0F, 6.0F, 6.0F, 0.0F);
      this.Egg4 = new AdvancedModelBox(this, 28, 16);
      this.Egg4.setPos(0.0F, -0.9F, 0.0F);
      this.Egg4.addBox(-2.0F, -4.8F, -2.0F, 4.0F, 4.0F, 4.0F, 0.0F);
      this.Egg1.addChild(this.Egg3);
      this.Egg1.addChild(this.Egg2);
      this.Egg3.addChild(this.Egg4);
      this.updateDefaultPose();
   }

   public Iterable<BasicModelPart> parts() {
      return ImmutableList.of(this.Egg1);
   }

   public Iterable<AdvancedModelBox> getAllParts() {
      return ImmutableList.of(this.Egg1, this.Egg2, this.Egg3, this.Egg4);
   }

   public void setupAnim(LivingEntity entity, float f, float f1, float f2, float f3, float f4) {
      this.resetToDefaultPose();
      this.Egg1.setPos(0.0F, 19.6F, 0.0F);
      this.Egg4.setPos(0.0F, -0.9F, 0.0F);
      if (entity instanceof EntityDragonEgg) {
         EntityDragonEgg egg = (EntityDragonEgg)entity;
         boolean isLocationValid = false;
         if (egg.getEggType().dragonType == DragonType.FIRE) {
            isLocationValid = egg.m_9236_().m_8055_(egg.m_20183_()).isBurning(entity.m_9236_(), egg.m_20183_());
         } else if (egg.getEggType().dragonType == DragonType.LIGHTNING) {
            isLocationValid = egg.m_9236_().m_46758_(egg.m_20183_());
         }

         if (isLocationValid) {
            this.walk(this.Egg1, 0.3F, 0.3F, true, 1.0F, 0.0F, f2, 1.0F);
            this.flap(this.Egg1, 0.3F, 0.3F, false, 0.0F, 0.0F, f2, 1.0F);
         }
      }

   }

   public void renderPodium() {
      this.Egg1.rotateAngleX = (float)Math.toRadians(-180.0D);
   }

   public void renderFrozen(TileEntityEggInIce tile) {
      this.resetToDefaultPose();
      this.Egg1.rotateAngleX = (float)Math.toRadians(-180.0D);
      this.walk(this.Egg1, 0.3F, 0.1F, true, 1.0F, 0.0F, (float)tile.ticksExisted, 1.0F);
      this.flap(this.Egg1, 0.3F, 0.1F, false, 0.0F, 0.0F, (float)tile.ticksExisted, 1.0F);
   }
}
