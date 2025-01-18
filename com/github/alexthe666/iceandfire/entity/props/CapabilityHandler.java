package com.github.alexthe666.iceandfire.entity.props;

import com.github.alexthe666.iceandfire.IceAndFire;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.CapabilityManager;
import net.neoforge.common.capabilities.CapabilityToken;
import net.neoforge.event.AttachCapabilitiesEvent;
import net.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforge.event.entity.EntityLeaveLevelEvent;
import net.neoforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.neoforge.event.entity.player.PlayerEvent.StartTracking;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber
public class CapabilityHandler {
   public static final Capability<EntityData> ENTITY_DATA_CAPABILITY = CapabilityManager.get(new CapabilityToken<EntityData>() {
   });
   public static final ResourceLocation ENTITY_DATA = new ResourceLocation("iceandfire", "entity_data");

   @SubscribeEvent
   public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
      if (event.getObject() instanceof LivingEntity) {
         event.addCapability(ENTITY_DATA, new EntityDataProvider());
      }

   }

   @SubscribeEvent
   public static void handleInitialSync(EntityJoinLevelEvent event) {
      syncEntityData(event.getEntity());
   }

   @SubscribeEvent
   public static void removeCachedEntry(EntityLeaveLevelEvent event) {
      EntityDataProvider.removeCachedEntry(event.getEntity());
   }

   @SubscribeEvent
   public static void onPlayerStartTracking(StartTracking event) {
      Entity var3 = event.getTarget();
      if (var3 instanceof LivingEntity) {
         LivingEntity target = (LivingEntity)var3;
         Player var4 = event.getEntity();
         if (var4 instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)var4;
            EntityDataProvider.getCapability(target).ifPresent((data) -> {
               IceAndFire.sendMSGToPlayer(new SyncEntityData(target.m_19879_(), data.serialize()), serverPlayer);
            });
         }
      }

   }

   @SubscribeEvent
   public static void tickData(LivingTickEvent event) {
      EntityDataProvider.getCapability(event.getEntity()).ifPresent((data) -> {
         data.tick(event.getEntity());
      });
   }

   public static void syncEntityData(Entity entity) {
      if (!entity.m_9236_().m_5776_() && entity instanceof LivingEntity) {
         if (entity instanceof ServerPlayer) {
            ServerPlayer serverPlayer = (ServerPlayer)entity;
            EntityDataProvider.getCapability(entity).ifPresent((data) -> {
               IceAndFire.NETWORK_WRAPPER.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> {
                  return serverPlayer;
               }), new SyncEntityData(entity.m_19879_(), data.serialize()));
            });
         } else {
            EntityDataProvider.getCapability(entity).ifPresent((data) -> {
               IceAndFire.NETWORK_WRAPPER.send(PacketDistributor.TRACKING_ENTITY.with(() -> {
                  return entity;
               }), new SyncEntityData(entity.m_19879_(), data.serialize()));
            });
         }

      }
   }

   @Nullable
   public static Player getLocalPlayer() {
      return IceAndFire.PROXY.getClientSidePlayer();
   }
}
