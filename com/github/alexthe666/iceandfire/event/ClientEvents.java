package com.github.alexthe666.iceandfire.event;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.client.ClientProxy;
import com.github.alexthe666.iceandfire.client.IafKeybindRegistry;
import com.github.alexthe666.iceandfire.client.gui.IceAndFireMainMenu;
import com.github.alexthe666.iceandfire.client.particle.CockatriceBeamRender;
import com.github.alexthe666.iceandfire.client.render.entity.RenderChain;
import com.github.alexthe666.iceandfire.client.render.tile.RenderFrozenState;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.ICustomMoveController;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.message.MessageDragonControl;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.WorldEventContext;
import java.util.Iterator;
import java.util.Random;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.api.distmarker.Dist;
import net.neoforge.api.distmarker.OnlyIn;
import net.neoforge.client.event.RenderLevelStageEvent;
import net.neoforge.client.event.RenderLivingEvent.Post;
import net.neoforge.client.event.RenderLivingEvent.Pre;
import net.neoforge.client.event.ScreenEvent.Opening;
import net.neoforge.client.event.ViewportEvent.ComputeCameraAngles;
import net.neoforge.event.entity.EntityMountEvent;
import net.neoforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.neoforge.eventbus.api.EventPriority;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(
   modid = "iceandfire",
   value = {Dist.CLIENT}
)
public class ClientEvents {
   private static final ResourceLocation SIREN_SHADER = new ResourceLocation("iceandfire:shaders/post/siren.json");
   private final Random rand = new Random();
   public final boolean AUTO_ADAPT_3RD_PERSON = true;

   private static boolean shouldCancelRender(LivingEntity living) {
      if (living.m_20202_() != null && living.m_20202_() instanceof EntityDragonBase) {
         return ClientProxy.currentDragonRiders.contains(living.m_20148_()) || living == Minecraft.m_91087_().f_91074_ && Minecraft.m_91087_().f_91066_.m_92176_().m_90612_();
      } else {
         return false;
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public static void renderWorldLastEvent(@NotNull RenderLevelStageEvent event) {
      WorldEventContext.INSTANCE.renderWorldLastEvent(event);
   }

   @SubscribeEvent
   public void onCameraSetup(ComputeCameraAngles event) {
      Player player = Minecraft.m_91087_().f_91074_;
      if (player.m_20202_() != null && player.m_20202_() instanceof EntityDragonBase) {
         int currentView = IceAndFire.PROXY.getDragon3rdPersonView();
         float scale = ((EntityDragonBase)player.m_20202_()).getRenderSize() / 3.0F;
         if (Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.THIRD_PERSON_BACK || Minecraft.m_91087_().f_91066_.m_92176_() == CameraType.THIRD_PERSON_FRONT) {
            if (currentView == 1) {
               event.getCamera().m_90568_(-event.getCamera().m_90566_((double)(scale * 1.2F)), 0.0D, 0.0D);
            } else if (currentView == 2) {
               event.getCamera().m_90568_(-event.getCamera().m_90566_((double)(scale * 3.0F)), 0.0D, 0.0D);
            } else if (currentView == 3) {
               event.getCamera().m_90568_(-event.getCamera().m_90566_((double)(scale * 5.0F)), 0.0D, 0.0D);
            }
         }
      }

   }

   @SubscribeEvent
   public void onLivingUpdate(LivingTickEvent event) {
      Minecraft mc = Minecraft.m_91087_();
      byte previousState;
      if (event.getEntity() instanceof ICustomMoveController) {
         Entity entity = event.getEntity();
         ICustomMoveController moveController = (ICustomMoveController)event.getEntity();
         if (entity.m_20202_() != null && entity.m_20202_() == mc.f_91074_) {
            byte previousState = moveController.getControlState();
            moveController.dismount(mc.f_91066_.f_92090_.m_90857_());
            previousState = moveController.getControlState();
            if (previousState != previousState) {
               IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageDragonControl(entity.m_19879_(), previousState, entity.m_20185_(), entity.m_20186_(), entity.m_20189_()));
            }
         }
      }

      LivingEntity var9 = event.getEntity();
      if (var9 instanceof Player) {
         Player player = (Player)var9;
         if (player.m_9236_().f_46443_ && player.m_20202_() instanceof ICustomMoveController) {
            Entity entity = player.m_20202_();
            ICustomMoveController moveController = (ICustomMoveController)player.m_20202_();
            previousState = moveController.getControlState();
            moveController.up(mc.f_91066_.f_92089_.m_90857_());
            moveController.down(IafKeybindRegistry.dragon_down.m_90857_());
            moveController.attack(IafKeybindRegistry.dragon_strike.m_90857_());
            moveController.dismount(mc.f_91066_.f_92090_.m_90857_());
            moveController.strike(IafKeybindRegistry.dragon_fireAttack.m_90857_());
            byte controlState = moveController.getControlState();
            if (controlState != previousState) {
               IceAndFire.NETWORK_WRAPPER.sendToServer(new MessageDragonControl(entity.m_19879_(), controlState, entity.m_20185_(), entity.m_20186_(), entity.m_20189_()));
            }
         }

         if (player.m_9236_().f_46443_ && IafKeybindRegistry.dragon_change_view.m_90857_()) {
            int currentView = IceAndFire.PROXY.getDragon3rdPersonView();
            if (currentView + 1 > 3) {
               currentView = 0;
            } else {
               ++currentView;
            }

            IceAndFire.PROXY.setDragon3rdPersonView(currentView);
         }

         if (player.m_9236_().f_46443_) {
            GameRenderer renderer = Minecraft.m_91087_().f_91063_;
            EntityDataProvider.getCapability(player).ifPresent((data) -> {
               if (IafConfig.sirenShader && data.sirenData.charmedBy == null && renderer.m_109149_() != null && SIREN_SHADER.toString().equals(renderer.m_109149_().m_110022_())) {
                  renderer.m_109086_();
               }

               if (data.sirenData.charmedBy != null) {
                  if (IafConfig.sirenShader && !data.sirenData.isCharmed && renderer.m_109149_() != null && SIREN_SHADER.toString().equals(renderer.m_109149_().m_110022_())) {
                     renderer.m_109086_();
                  }

                  if (data.sirenData.isCharmed) {
                     if (player.m_9236_().f_46443_ && this.rand.nextInt(40) == 0) {
                        IceAndFire.PROXY.spawnParticle(EnumParticles.Siren_Appearance, player.m_20185_(), player.m_20186_(), player.m_20189_(), (double)data.sirenData.charmedBy.getHairColor(), 0.0D, 0.0D);
                     }

                     if (IafConfig.sirenShader && renderer.m_109149_() == null) {
                        renderer.m_109128_(SIREN_SHADER);
                     }
                  }

               }
            });
         }
      }

   }

   @SubscribeEvent
   public void onPreRenderLiving(Pre event) {
      if (shouldCancelRender(event.getEntity())) {
         event.setCanceled(true);
      }

   }

   @SubscribeEvent
   public void onPostRenderLiving(Post event) {
      if (shouldCancelRender(event.getEntity())) {
         event.setCanceled(true);
      }

      LivingEntity entity = event.getEntity();
      EntityDataProvider.getCapability(entity).ifPresent((data) -> {
         Iterator var3 = data.miscData.getTargetedByScepter().iterator();

         while(var3.hasNext()) {
            LivingEntity target = (LivingEntity)var3.next();
            CockatriceBeamRender.render(entity, target, event.getPoseStack(), event.getMultiBufferSource(), event.getPartialTick());
         }

         if (data.frozenData.isFrozen) {
            RenderFrozenState.render(event.getEntity(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), data.frozenData.frozenTicks);
         }

         RenderChain.render(entity, event.getPartialTick(), event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), data.chainData.getChainedTo());
      });
   }

   @SubscribeEvent
   public void onGuiOpened(Opening event) {
      if (IafConfig.customMainMenu && event.getScreen() instanceof TitleScreen && !(event.getScreen() instanceof IceAndFireMainMenu)) {
         event.setNewScreen(new IceAndFireMainMenu());
      }

   }

   @SubscribeEvent
   public void onEntityMount(EntityMountEvent event) {
      Entity var3 = event.getEntityBeingMounted();
      if (var3 instanceof EntityDragonBase) {
         EntityDragonBase dragon = (EntityDragonBase)var3;
         if (event.getLevel().f_46443_ && event.getEntityMounting() == Minecraft.m_91087_().f_91074_ && dragon.m_21824_() && dragon.m_21830_(Minecraft.m_91087_().f_91074_)) {
            IceAndFire.PROXY.setDragon3rdPersonView(2);
            if (IafConfig.dragonAuto3rdPerson) {
               if (event.isDismounting()) {
                  Minecraft.m_91087_().f_91066_.m_92157_(CameraType.values()[IceAndFire.PROXY.getPreviousViewType()]);
               } else {
                  IceAndFire.PROXY.setPreviousViewType(Minecraft.m_91087_().f_91066_.m_92176_().ordinal());
                  Minecraft.m_91087_().f_91066_.m_92157_(CameraType.values()[1]);
               }
            }
         }
      }

   }
}
