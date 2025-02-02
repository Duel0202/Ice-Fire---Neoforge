package com.github.alexthe666.iceandfire.client.model.animator;

import com.github.alexthe666.citadel.client.model.AdvancedModelBox;
import com.github.alexthe666.citadel.client.model.ITabulaModelAnimator;
import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.client.model.util.EnumDragonPoses;
import com.github.alexthe666.iceandfire.client.model.util.LegArticulator;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public abstract class DragonTabulaModelAnimator<T extends EntityDragonBase> extends IceAndFireTabulaModelAnimator implements ITabulaModelAnimator<T> {
   protected TabulaModel[] walkPoses;
   protected TabulaModel[] flyPoses;
   protected TabulaModel[] swimPoses;
   protected AdvancedModelBox[] neckParts;
   protected AdvancedModelBox[] tailParts;
   protected AdvancedModelBox[] tailPartsWBody;
   protected AdvancedModelBox[] toesPartsL;
   protected AdvancedModelBox[] toesPartsR;
   protected AdvancedModelBox[] clawL;
   protected AdvancedModelBox[] clawR;

   public DragonTabulaModelAnimator(TabulaModel baseModel) {
      super(baseModel);
   }

   public void init(TabulaModel model) {
      this.neckParts = new AdvancedModelBox[]{model.getCube("Neck1"), model.getCube("Neck2"), model.getCube("Neck3"), model.getCube("Head")};
      this.tailParts = new AdvancedModelBox[]{model.getCube("Tail1"), model.getCube("Tail2"), model.getCube("Tail3"), model.getCube("Tail4")};
      this.tailPartsWBody = new AdvancedModelBox[]{model.getCube("BodyLower"), model.getCube("Tail1"), model.getCube("Tail2"), model.getCube("Tail3"), model.getCube("Tail4")};
      this.toesPartsL = new AdvancedModelBox[]{model.getCube("ToeL1"), model.getCube("ToeL2"), model.getCube("ToeL3")};
      this.toesPartsR = new AdvancedModelBox[]{model.getCube("ToeR1"), model.getCube("ToeR2"), model.getCube("ToeR3")};
      this.clawL = new AdvancedModelBox[]{model.getCube("ClawL")};
      this.clawR = new AdvancedModelBox[]{model.getCube("ClawR")};
   }

   public void setRotationAngles(TabulaModel model, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
      model.resetToDefaultPose();
      if (this.neckParts == null) {
         this.init(model);
      }

      this.animate(model, entity, limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale);
      boolean walking = !entity.isHovering() && !entity.isFlying() && entity.hoverProgress <= 0.0F && entity.flyProgress <= 0.0F;
      boolean swimming = entity.m_20069_() && entity.swimProgress > 0.0F;
      int currentIndex = walking ? entity.walkCycle / 10 : entity.flightCycle / 10;
      if (swimming) {
         currentIndex = entity.swimCycle / 10;
      }

      int prevIndex = currentIndex - 1;
      if (prevIndex < 0) {
         prevIndex = swimming ? 4 : (walking ? 3 : 5);
      }

      TabulaModel currentPosition = swimming ? this.swimPoses[currentIndex] : (walking ? this.walkPoses[currentIndex] : this.flyPoses[currentIndex]);
      TabulaModel prevPosition = swimming ? this.swimPoses[prevIndex] : (walking ? this.walkPoses[prevIndex] : this.flyPoses[prevIndex]);
      float delta = (float)(walking ? entity.walkCycle : entity.flightCycle) / 10.0F % 1.0F;
      if (swimming) {
         delta = (float)entity.swimCycle / 10.0F % 1.0F;
      }

      float partialTick = Minecraft.m_91087_().m_91296_();
      float deltaTicks = delta + partialTick / 10.0F;
      if (delta == 0.0F) {
         deltaTicks = 0.0F;
      }

      float speed_walk = 0.2F;
      float speed_idle = entity.m_5803_() ? 0.025F : 0.05F;
      float speed_fly = 0.2F;
      float degree_walk = 0.5F;
      float degree_idle = entity.m_5803_() ? 0.25F : 0.5F;
      float degree_fly = 0.5F;
      Iterator var24;
      AdvancedModelBox cube;
      if (entity.isModelDead()) {
         var24 = model.getCubes().values().iterator();

         while(var24.hasNext()) {
            cube = (AdvancedModelBox)var24.next();
            this.setRotationsLoopDeath(model, entity, limbSwingAmount, walking, currentPosition, prevPosition, partialTick, deltaTicks, cube);
         }

      } else if (!entity.isAiDisabled()) {
         var24 = model.getCubes().values().iterator();

         while(var24.hasNext()) {
            cube = (AdvancedModelBox)var24.next();
            this.setRotationsLoop(model, entity, limbSwingAmount, walking, currentPosition, prevPosition, partialTick, deltaTicks, cube);
         }

         if (entity.getAnimation() != EntityDragonBase.ANIMATION_SHAKEPREY || entity.getAnimation() != EntityDragonBase.ANIMATION_ROAR) {
            model.faceTarget(rotationYaw, rotationPitch, 2.0F, this.neckParts);
         }

         if (!walking) {
            model.bob(model.getCube("BodyUpper"), -speed_fly, degree_fly * 5.0F, false, ageInTicks, 1.0F);
            model.walk(model.getCube("BodyUpper"), -speed_fly, degree_fly * 0.1F, false, 0.0F, 0.0F, ageInTicks, 1.0F);
            model.chainWave(this.tailPartsWBody, speed_fly, degree_fly * -0.1F, 0.0D, ageInTicks, 1.0F);
            model.chainWave(this.neckParts, speed_fly, degree_fly * 0.2F, -4.0D, ageInTicks, 1.0F);
            model.chainWave(this.toesPartsL, speed_fly, degree_fly * 0.2F, -2.0D, ageInTicks, 1.0F);
            model.chainWave(this.toesPartsR, speed_fly, degree_fly * 0.2F, -2.0D, ageInTicks, 1.0F);
            model.walk(model.getCube("ThighR"), -speed_fly, degree_fly * 0.1F, false, 0.0F, 0.0F, ageInTicks, 1.0F);
            model.walk(model.getCube("ThighL"), -speed_fly, degree_fly * 0.1F, true, 0.0F, 0.0F, ageInTicks, 1.0F);
         } else {
            model.bob(model.getCube("BodyUpper"), speed_walk * 2.0F, degree_walk * 1.7F, false, limbSwing, limbSwingAmount);
            model.bob(model.getCube("ThighR"), speed_walk, degree_walk * 1.7F, false, limbSwing, limbSwingAmount);
            model.bob(model.getCube("ThighL"), speed_walk, degree_walk * 1.7F, false, limbSwing, limbSwingAmount);
            model.chainSwing(this.tailParts, speed_walk, degree_walk * 0.25F, -2.0D, limbSwing, limbSwingAmount);
            model.chainWave(this.tailParts, speed_walk, degree_walk * 0.15F, 2.0D, limbSwing, limbSwingAmount);
            model.chainSwing(this.neckParts, speed_walk, degree_walk * 0.15F, 2.0D, limbSwing, limbSwingAmount);
            model.chainWave(this.neckParts, speed_walk, degree_walk * 0.05F, -2.0D, limbSwing, limbSwingAmount);
            model.chainSwing(this.tailParts, speed_idle, degree_idle * 0.25F, -2.0D, ageInTicks, 1.0F);
            model.chainWave(this.tailParts, speed_idle, degree_idle * 0.15F, -2.0D, ageInTicks, 1.0F);
            model.chainWave(this.neckParts, speed_idle, degree_idle * -0.15F, -3.0D, ageInTicks, 1.0F);
            model.walk(model.getCube("Neck1"), speed_idle, degree_idle * 0.05F, false, 0.0F, 0.0F, ageInTicks, 1.0F);
         }

         model.bob(model.getCube("BodyUpper"), speed_idle, degree_idle * 1.3F, false, ageInTicks, 1.0F);
         model.bob(model.getCube("ThighR"), speed_idle, -degree_idle * 1.3F, false, ageInTicks, 1.0F);
         model.bob(model.getCube("ThighL"), speed_idle, -degree_idle * 1.3F, false, ageInTicks, 1.0F);
         model.bob(model.getCube("armR1"), speed_idle, -degree_idle * 1.3F, false, ageInTicks, 1.0F);
         model.bob(model.getCube("armL1"), speed_idle, -degree_idle * 1.3F, false, ageInTicks, 1.0F);
         if (entity.isActuallyBreathingFire()) {
            float speed_shake = 0.7F;
            float degree_shake = 0.1F;
            model.chainFlap(this.neckParts, speed_shake, degree_shake, 2.0D, ageInTicks, 1.0F);
            model.chainSwing(this.neckParts, speed_shake * 0.65F, degree_shake * 0.1F, 1.0D, ageInTicks, 1.0F);
         }

         if (entity.turn_buffer != null && !entity.m_20160_() && !entity.m_20159_() && entity.isBreathingFire()) {
            entity.turn_buffer.applyChainSwingBuffer(this.neckParts);
         }

         if (entity.tail_buffer != null && !entity.m_20159_()) {
            entity.tail_buffer.applyChainSwingBuffer(this.tailPartsWBody);
         }

         if (entity.roll_buffer != null && entity.pitch_buffer_body != null && entity.pitch_buffer != null && (entity.flyProgress > 0.0F || entity.hoverProgress > 0.0F)) {
            entity.roll_buffer.applyChainFlapBuffer(model.getCube("BodyUpper"));
            entity.pitch_buffer_body.applyChainWaveBuffer(model.getCube("BodyUpper"));
            entity.pitch_buffer.applyChainWaveBufferReverse(this.tailPartsWBody);
         }

         if (entity.m_20205_() >= 2.0F && entity.flyProgress == 0.0F && entity.hoverProgress == 0.0F) {
            LegArticulator.articulateQuadruped(entity, entity.legSolver, model.getCube("BodyUpper"), model.getCube("BodyLower"), model.getCube("Neck1"), model.getCube("ThighL"), model.getCube("LegL"), this.toesPartsL, model.getCube("ThighR"), model.getCube("LegR"), this.toesPartsR, model.getCube("armL1"), model.getCube("armL2"), this.clawL, model.getCube("armR1"), model.getCube("armR2"), this.clawR, 1.0F, 0.5F, 0.5F, -0.15F, -0.15F, 0.0F, Minecraft.m_91087_().m_91296_());
         }

      }
   }

   private void setRotationsLoop(TabulaModel model, T entity, float limbSwingAmount, boolean walking, TabulaModel currentPosition, TabulaModel prevPosition, float partialTick, float deltaTicks, AdvancedModelBox cube) {
      this.genderMob(entity, cube);
      AdvancedModelBox flightPart;
      AdvancedModelBox prevPositionCube;
      AdvancedModelBox currPositionCube;
      float prevX;
      float prevY;
      float prevZ;
      float x;
      float y;
      float z;
      if (walking && entity.flyProgress <= 0.0F && entity.hoverProgress <= 0.0F && entity.modelDeadProgress <= 0.0F) {
         flightPart = this.getModel(EnumDragonPoses.GROUND_POSE).getCube(cube.boxName);
         prevPositionCube = prevPosition.getCube(cube.boxName);
         currPositionCube = currentPosition.getCube(cube.boxName);
         if (prevPositionCube == null || currPositionCube == null) {
            return;
         }

         prevX = prevPositionCube.rotateAngleX;
         prevY = prevPositionCube.rotateAngleY;
         prevZ = prevPositionCube.rotateAngleZ;
         x = currPositionCube.rotateAngleX;
         y = currPositionCube.rotateAngleY;
         z = currPositionCube.rotateAngleZ;
         if (!this.isHorn(cube) && (!this.isWing(model, cube) || entity.getAnimation() != EntityDragonBase.ANIMATION_WINGBLAST && entity.getAnimation() != EntityDragonBase.ANIMATION_EPIC_ROAR)) {
            this.addToRotateAngle(cube, limbSwingAmount, prevX + deltaTicks * this.distance(prevX, x), prevY + deltaTicks * this.distance(prevY, y), prevZ + deltaTicks * this.distance(prevZ, z));
         } else {
            this.addToRotateAngle(cube, limbSwingAmount, flightPart.rotateAngleX, flightPart.rotateAngleY, flightPart.rotateAngleZ);
         }
      }

      if (entity.sleepProgress > 0.0F && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.SLEEPING_POSE).getCube(cube.boxName))) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.SLEEPING_POSE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevAnimationProgresses[1], entity.sleepProgress), 20.0F, false);
      }

      if (entity.hoverProgress > 0.0F && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.HOVERING_POSE).getCube(cube.boxName)) && !this.isWing(model, cube) && !cube.boxName.contains("Tail")) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.HOVERING_POSE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevAnimationProgresses[2], entity.hoverProgress), 20.0F, false);
      }

      if (entity.flyProgress > 0.0F && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.FLYING_POSE).getCube(cube.boxName))) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.FLYING_POSE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevAnimationProgresses[3], entity.flyProgress) - Mth.m_14179_(partialTick, entity.prevDiveProgress, entity.diveProgress) * 2.0F, 20.0F, false);
      }

      if (entity.sitProgress > 0.0F && !entity.m_20159_() && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.SITTING_POSE).getCube(cube.boxName))) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.SITTING_POSE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevAnimationProgresses[0], entity.sitProgress), 20.0F, false);
      }

      if (entity.ridingProgress > 0.0F && !this.isHorn(cube) && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.SIT_ON_PLAYER_POSE).getCube(cube.boxName))) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.SIT_ON_PLAYER_POSE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevAnimationProgresses[5], entity.ridingProgress), 20.0F, false);
         if (cube.boxName.equals("BodyUpper")) {
            cube.offsetZ += (-12.0F - cube.offsetZ) / 20.0F * Mth.m_14179_(partialTick, entity.prevAnimationProgresses[5], entity.ridingProgress);
         }
      }

      if (entity.tackleProgress > 0.0F && !this.isRotationEqual(this.getModel(EnumDragonPoses.TACKLE).getCube(cube.boxName), this.getModel(EnumDragonPoses.FLYING_POSE).getCube(cube.boxName)) && !this.isWing(model, cube)) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.TACKLE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevAnimationProgresses[6], entity.tackleProgress), 5.0F, false);
      }

      if (entity.diveProgress > 0.0F && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.DIVING_POSE).getCube(cube.boxName))) {
         this.transitionTo(cube, this.getModel(EnumDragonPoses.DIVING_POSE).getCube(cube.boxName), Mth.m_14179_(partialTick, entity.prevDiveProgress, entity.diveProgress), 10.0F, false);
      }

      if (entity.fireBreathProgress > 0.0F && !this.isRotationEqual(cube, this.getModel(EnumDragonPoses.STREAM_BREATH).getCube(cube.boxName)) && !this.isWing(model, cube) && !cube.boxName.contains("Finger")) {
         if (entity.prevFireBreathProgress <= entity.fireBreathProgress) {
            this.transitionTo(cube, this.getModel(EnumDragonPoses.BLAST_CHARGE3).getCube(cube.boxName), Mth.m_14036_(Mth.m_14179_(partialTick, entity.prevFireBreathProgress, entity.fireBreathProgress), 0.0F, 5.0F), 5.0F, false);
         }

         this.transitionTo(cube, this.getModel(EnumDragonPoses.STREAM_BREATH).getCube(cube.boxName), Mth.m_14036_(Mth.m_14179_(partialTick, entity.prevFireBreathProgress, entity.fireBreathProgress) - 5.0F, 0.0F, 5.0F), 5.0F, false);
      }

      if (!walking) {
         flightPart = this.getModel(EnumDragonPoses.FLYING_POSE).getCube(cube.boxName);
         prevPositionCube = prevPosition.getCube(cube.boxName);
         currPositionCube = currentPosition.getCube(cube.boxName);
         prevX = prevPositionCube.rotateAngleX;
         prevY = prevPositionCube.rotateAngleY;
         prevZ = prevPositionCube.rotateAngleZ;
         x = currPositionCube.rotateAngleX;
         y = currPositionCube.rotateAngleY;
         z = currPositionCube.rotateAngleZ;
         if (x != flightPart.rotateAngleX || y != flightPart.rotateAngleY || z != flightPart.rotateAngleZ) {
            this.setRotateAngle(cube, 1.0F, prevX + deltaTicks * this.distance(prevX, x), prevY + deltaTicks * this.distance(prevY, y), prevZ + deltaTicks * this.distance(prevZ, z));
         }
      }

   }

   public void setRotationsLoopDeath(TabulaModel model, T entity, float limbSwingAmount, boolean walking, TabulaModel currentPosition, TabulaModel prevPosition, float partialTick, float deltaTicks, AdvancedModelBox cube) {
      if (entity.modelDeadProgress > 0.0F) {
         TabulaModel pose = this.getModel(EnumDragonPoses.DEAD);
         if (!this.isRotationEqual(cube, pose.getCube(cube.boxName))) {
            this.transitionTo(cube, pose.getCube(cube.boxName), entity.prevModelDeadProgress + (entity.modelDeadProgress - entity.prevModelDeadProgress) * Minecraft.m_91087_().m_91296_(), 20.0F, cube.boxName.equals("ThighR") || cube.boxName.equals("ThighL"));
         }

         if (this instanceof IceDragonTabulaModelAnimator && cube.boxName.equals("BodyUpper")) {
            cube.rotationPointY += 0.35F * Mth.m_14179_(partialTick, entity.prevModelDeadProgress, entity.modelDeadProgress);
         }
      }

   }

   protected boolean isWing(TabulaModel model, AdvancedModelBox modelRenderer) {
      return model.getCube("armL1") == modelRenderer || model.getCube("armR1") == modelRenderer || model.getCube("armL1").childModels.contains(modelRenderer) || model.getCube("armR1").childModels.contains(modelRenderer);
   }

   protected TabulaModel customPose(T entity) {
      try {
         return this.getModel(EnumDragonPoses.valueOf(entity.getCustomPose()));
      } catch (IllegalArgumentException var3) {
         return null;
      }
   }

   protected boolean isHorn(AdvancedModelBox modelRenderer) {
      return modelRenderer.boxName.contains("Horn");
   }

   protected void genderMob(T entity, AdvancedModelBox cube) {
      if (!entity.isMale()) {
         TabulaModel maleModel = this.getModel(EnumDragonPoses.MALE);
         TabulaModel femaleModel = this.getModel(EnumDragonPoses.FEMALE);
         AdvancedModelBox femaleModelCube = femaleModel.getCube(cube.boxName);
         AdvancedModelBox maleModelCube = maleModel.getCube(cube.boxName);
         if (maleModelCube == null || femaleModelCube == null) {
            return;
         }

         float x = femaleModelCube.rotateAngleX;
         float y = femaleModelCube.rotateAngleY;
         float z = femaleModelCube.rotateAngleZ;
         if (x != maleModelCube.rotateAngleX || y != maleModelCube.rotateAngleY || z != maleModelCube.rotateAngleZ) {
            this.setRotateAngle(cube, 1.0F, x, y, z);
         }
      }

   }

   protected abstract TabulaModel getModel(EnumDragonPoses var1);

   protected void animate(TabulaModel model, T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
      AdvancedModelBox modelCubeJaw = model.getCube("Jaw");
      AdvancedModelBox modelCubeBodyUpper = model.getCube("BodyUpper");
      model.llibAnimator.update(entity);
      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_FIRECHARGE)) {
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.BLAST_CHARGE1));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.BLAST_CHARGE2));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.BLAST_CHARGE3));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(5);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_SPEAK)) {
         model.llibAnimator.startKeyframe(5);
         this.rotate(model.llibAnimator, modelCubeJaw, 18.0F, 0.0F, 0.0F);
         model.llibAnimator.move(modelCubeJaw, 0.0F, 0.0F, 0.2F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.setStaticKeyframe(5);
         model.llibAnimator.startKeyframe(5);
         this.rotate(model.llibAnimator, modelCubeJaw, 18.0F, 0.0F, 0.0F);
         model.llibAnimator.move(modelCubeJaw, 0.0F, 0.0F, 0.2F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(5);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_BITE)) {
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.BITE1));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.BITE2));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.BITE3));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(10);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_SHAKEPREY)) {
         model.llibAnimator.startKeyframe(15);
         this.moveToPose(model, this.getModel(EnumDragonPoses.GRAB1));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.GRAB2));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.GRAB_SHAKE1));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.GRAB_SHAKE2));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.GRAB_SHAKE3));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(10);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_TAILWHACK)) {
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.TAIL_WHIP1));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.TAIL_WHIP2));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.TAIL_WHIP3));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(10);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_WINGBLAST)) {
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST1));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST2));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, -2.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST3));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, -4.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST4));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, -4.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST5));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, -4.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST6));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, -4.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.moveToPose(model, this.getModel(EnumDragonPoses.WING_BLAST7));
         model.llibAnimator.move(modelCubeBodyUpper, 0.0F, -2.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(10);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_ROAR)) {
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.ROAR1));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.ROAR2));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.ROAR3));
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(10);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_EPIC_ROAR)) {
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.EPIC_ROAR1));
         model.llibAnimator.rotate(modelCubeBodyUpper, -0.1F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.EPIC_ROAR2));
         model.llibAnimator.rotate(modelCubeBodyUpper, -0.2F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.EPIC_ROAR3));
         model.llibAnimator.rotate(modelCubeBodyUpper, -0.2F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.EPIC_ROAR2));
         model.llibAnimator.rotate(modelCubeBodyUpper, -0.2F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(10);
         this.moveToPose(model, this.getModel(EnumDragonPoses.EPIC_ROAR3));
         model.llibAnimator.rotate(modelCubeBodyUpper, -0.1F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.resetKeyframe(10);
      }

      if (model.llibAnimator.setAnimation(EntityDragonBase.ANIMATION_EAT)) {
         model.llibAnimator.startKeyframe(5);
         this.rotate(model.llibAnimator, model.getCube("Neck1"), 18.0F, 0.0F, 0.0F);
         this.rotate(model.llibAnimator, model.getCube("Neck2"), 18.0F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.rotate(model.llibAnimator, modelCubeJaw, 18.0F, 0.0F, 0.0F);
         model.llibAnimator.move(modelCubeJaw, 0.0F, 0.0F, 0.2F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.setStaticKeyframe(5);
         model.llibAnimator.startKeyframe(5);
         this.rotate(model.llibAnimator, modelCubeJaw, 18.0F, 0.0F, 0.0F);
         model.llibAnimator.move(modelCubeJaw, 0.0F, 0.0F, 0.2F);
         model.llibAnimator.endKeyframe();
         model.llibAnimator.startKeyframe(5);
         this.rotate(model.llibAnimator, model.getCube("Neck1"), -18.0F, 0.0F, 0.0F);
         this.rotate(model.llibAnimator, model.getCube("Neck2"), -18.0F, 0.0F, 0.0F);
         model.llibAnimator.endKeyframe();
      }

   }
}
