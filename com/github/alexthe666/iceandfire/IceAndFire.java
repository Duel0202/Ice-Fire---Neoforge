package com.github.alexthe666.iceandfire;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.ClientProxy;
import com.github.alexthe666.iceandfire.config.ConfigHolder;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.IafVillagerRegistry;
import com.github.alexthe666.iceandfire.entity.props.SyncEntityData;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.inventory.IafContainerRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.IafTabRegistry;
import com.github.alexthe666.iceandfire.loot.IafLootRegistry;
import com.github.alexthe666.iceandfire.message.MessageDaytime;
import com.github.alexthe666.iceandfire.message.MessageDeathWormHitbox;
import com.github.alexthe666.iceandfire.message.MessageDragonControl;
import com.github.alexthe666.iceandfire.message.MessageDragonSetBurnBlock;
import com.github.alexthe666.iceandfire.message.MessageDragonSyncFire;
import com.github.alexthe666.iceandfire.message.MessageGetMyrmexHive;
import com.github.alexthe666.iceandfire.message.MessageHandler;
import com.github.alexthe666.iceandfire.message.MessageHippogryphArmor;
import com.github.alexthe666.iceandfire.message.MessageMultipartInteract;
import com.github.alexthe666.iceandfire.message.MessageMyrmexSettings;
import com.github.alexthe666.iceandfire.message.MessagePlayerHitMultipart;
import com.github.alexthe666.iceandfire.message.MessageSetMyrmexHiveNull;
import com.github.alexthe666.iceandfire.message.MessageSirenSong;
import com.github.alexthe666.iceandfire.message.MessageSpawnParticleAt;
import com.github.alexthe666.iceandfire.message.MessageStartRidingMob;
import com.github.alexthe666.iceandfire.message.MessageSwingArm;
import com.github.alexthe666.iceandfire.message.MessageSyncPath;
import com.github.alexthe666.iceandfire.message.MessageSyncPathReached;
import com.github.alexthe666.iceandfire.message.MessageUpdateDragonforge;
import com.github.alexthe666.iceandfire.message.MessageUpdateLectern;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouse;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieHouseModel;
import com.github.alexthe666.iceandfire.message.MessageUpdatePixieJar;
import com.github.alexthe666.iceandfire.message.MessageUpdatePodium;
import com.github.alexthe666.iceandfire.recipe.IafBannerPatterns;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import com.github.alexthe666.iceandfire.recipe.IafRecipeSerializers;
import com.github.alexthe666.iceandfire.world.IafFeatureBiomeModifier;
import com.github.alexthe666.iceandfire.world.IafMobSpawnBiomeModifier;
import com.github.alexthe666.iceandfire.world.IafPlacementFilterRegistry;
import com.github.alexthe666.iceandfire.world.IafProcessors;
import com.github.alexthe666.iceandfire.world.IafStructureTypes;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import java.lang.invoke.SerializedLambda;
import java.util.Iterator;
import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforge.common.MinecraftForge;
import net.neoforge.common.world.BiomeModifier;
import net.neoforge.event.server.ServerStartedEvent;
import net.neoforge.eventbus.api.IEventBus;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.DistExecutor;
import net.neoforge.fml.ModContainer;
import net.neoforge.fml.ModList;
import net.neoforge.fml.ModLoadingContext;
import net.neoforge.fml.common.Mod;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.config.ModConfig.Type;
import net.neoforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.neoforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforge.network.NetworkDirection;
import net.neoforge.network.NetworkRegistry.ChannelBuilder;
import net.neoforge.network.simple.SimpleChannel;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries.Keys;
import net.neoforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@NeoMod("iceandfire")
@EventBusSubscriber(
   modid = "iceandfire"
)
public class IceAndFire {
   public static final Logger LOGGER = LogManager.getLogger();
   public static final String MODID = "iceandfire";
   public static final SimpleChannel NETWORK_WRAPPER;
   private static final String PROTOCOL_VERSION = Integer.toString(1);
   public static boolean DEBUG = true;
   public static String VERSION = "UNKNOWN";
   public static CommonProxy PROXY = (CommonProxy)DistExecutor.safeRunForDist(() -> {
      return ClientProxy::new;
   }, () -> {
      return CommonProxy::new;
   });
   private static int packetsRegistered = 0;

   public IceAndFire() {
      try {
         ModContainer mod = (ModContainer)ModList.get().getModContainerById("iceandfire").orElseThrow(NullPointerException::new);
         VERSION = mod.getModInfo().getVersion().toString();
      } catch (Exception var4) {
      }

      IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
      ModLoadingContext modLoadingContext = ModLoadingContext.get();
      modLoadingContext.registerConfig(Type.CLIENT, ConfigHolder.CLIENT_SPEC);
      modLoadingContext.registerConfig(Type.COMMON, ConfigHolder.SERVER_SPEC);
      PROXY.init();
      MinecraftForge.EVENT_BUS.addListener(this::onServerStarted);
      DeferredRegister<Codec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, "iceandfire");
      biomeModifiers.register(modBus);
      biomeModifiers.register("iaf_mob_spawns", IafMobSpawnBiomeModifier::makeCodec);
      biomeModifiers.register("iaf_features", IafFeatureBiomeModifier::makeCodec);
      IafItemRegistry.ITEMS.register(modBus);
      IafBlockRegistry.BLOCKS.register(modBus);
      IafTabRegistry.TAB_REGISTER.register(modBus);
      IafEntityRegistry.ENTITIES.register(modBus);
      IafTileEntityRegistry.TYPES.register(modBus);
      IafPlacementFilterRegistry.PLACEMENT_MODIFIER_TYPES.register(modBus);
      IafWorldRegistry.FEATURES.register(modBus);
      IafRecipeRegistry.RECIPE_TYPE.register(modBus);
      IafBannerPatterns.BANNERS.register(modBus);
      IafStructureTypes.STRUCTURE_TYPES.register(modBus);
      IafContainerRegistry.CONTAINERS.register(modBus);
      IafRecipeSerializers.SERIALIZERS.register(modBus);
      IafProcessors.PROCESSORS.register(modBus);
      IafVillagerRegistry.POI_TYPES.register(modBus);
      IafVillagerRegistry.PROFESSIONS.register(modBus);
      MinecraftForge.EVENT_BUS.register(IafBlockRegistry.class);
      MinecraftForge.EVENT_BUS.register(IafRecipeRegistry.class);
      modBus.addListener(this::setup);
      modBus.addListener(this::setupComplete);
      modBus.addListener(this::setupClient);
   }

   @SubscribeEvent
   public void onServerStarted(ServerStartedEvent event) {
      LOGGER.info(IafWorldRegistry.LOADED_FEATURES);
      LOGGER.info(IafEntityRegistry.LOADED_ENTITIES);
   }

   public static <MSG> void sendMSGToServer(MSG message) {
      NETWORK_WRAPPER.sendToServer(message);
   }

   public static <MSG> void sendMSGToAll(MSG message) {
      Iterator var1 = ServerLifecycleHooks.getCurrentServer().m_6846_().m_11314_().iterator();

      while(var1.hasNext()) {
         ServerPlayer player = (ServerPlayer)var1.next();
         NETWORK_WRAPPER.sendTo(message, player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
      }

   }

   public static <MSG> void sendMSGToPlayer(MSG message, ServerPlayer player) {
      NETWORK_WRAPPER.sendTo(message, player.f_8906_.f_9742_, NetworkDirection.PLAY_TO_CLIENT);
   }

   private void setup(FMLCommonSetupEvent event) {
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageDaytime.class, MessageDaytime::write, MessageDaytime::read, MessageHandler.handle(MessageDaytime.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageDeathWormHitbox.class, MessageDeathWormHitbox::write, MessageDeathWormHitbox::read, MessageHandler.handle(MessageDeathWormHitbox.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageDragonControl.class, MessageDragonControl::write, MessageDragonControl::read, MessageHandler.handle(MessageDragonControl.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageDragonSetBurnBlock.class, MessageDragonSetBurnBlock::write, MessageDragonSetBurnBlock::read, MessageHandler.handle(MessageDragonSetBurnBlock.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageDragonSyncFire.class, MessageDragonSyncFire::write, MessageDragonSyncFire::read, MessageHandler.handle(MessageDragonSyncFire.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageGetMyrmexHive.class, MessageGetMyrmexHive::write, MessageGetMyrmexHive::read, MessageHandler.handle(MessageGetMyrmexHive.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageMyrmexSettings.class, MessageMyrmexSettings::write, MessageMyrmexSettings::read, MessageHandler.handle(MessageMyrmexSettings.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageHippogryphArmor.class, MessageHippogryphArmor::write, MessageHippogryphArmor::read, MessageHandler.handle(MessageHippogryphArmor.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageMultipartInteract.class, MessageMultipartInteract::write, MessageMultipartInteract::read, MessageHandler.handle(MessageMultipartInteract.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessagePlayerHitMultipart.class, MessagePlayerHitMultipart::write, MessagePlayerHitMultipart::read, MessageHandler.handle(MessagePlayerHitMultipart.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageSetMyrmexHiveNull.class, MessageSetMyrmexHiveNull::write, MessageSetMyrmexHiveNull::read, MessageHandler.handle(MessageSetMyrmexHiveNull.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageSirenSong.class, MessageSirenSong::write, MessageSirenSong::read, MessageHandler.handle(MessageSirenSong.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageSpawnParticleAt.class, MessageSpawnParticleAt::write, MessageSpawnParticleAt::read, MessageHandler.handle(MessageSpawnParticleAt.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageStartRidingMob.class, MessageStartRidingMob::write, MessageStartRidingMob::read, MessageHandler.handle(MessageStartRidingMob.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageUpdatePixieHouse.class, MessageUpdatePixieHouse::write, MessageUpdatePixieHouse::read, MessageHandler.handle(MessageUpdatePixieHouse.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageUpdatePixieHouseModel.class, MessageUpdatePixieHouseModel::write, MessageUpdatePixieHouseModel::read, MessageHandler.handle(MessageUpdatePixieHouseModel.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageUpdatePixieJar.class, MessageUpdatePixieJar::write, MessageUpdatePixieJar::read, MessageHandler.handle(MessageUpdatePixieJar.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageUpdatePodium.class, MessageUpdatePodium::write, MessageUpdatePodium::read, MessageHandler.handle(MessageUpdatePodium.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageUpdateDragonforge.class, MessageUpdateDragonforge::write, MessageUpdateDragonforge::read, MessageHandler.handle(MessageUpdateDragonforge.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageUpdateLectern.class, MessageUpdateLectern::write, MessageUpdateLectern::read, MessageHandler.handle(MessageUpdateLectern.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageSyncPath.class, MessageSyncPath::write, MessageSyncPath::read, MessageHandler.handle(MessageSyncPath::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageSyncPathReached.class, MessageSyncPathReached::write, MessageSyncPathReached::read, MessageHandler.handle(MessageSyncPathReached::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, MessageSwingArm.class, MessageSwingArm::write, MessageSwingArm::read, MessageHandler.handle(MessageSwingArm.Handler::handle));
      NETWORK_WRAPPER.registerMessage(packetsRegistered++, SyncEntityData.class, SyncEntityData::encode, SyncEntityData::decode, SyncEntityData::handle);
      event.enqueueWork(() -> {
         PROXY.setup();
         IafLootRegistry.init();
      });
   }

   private void setupClient(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         PROXY.clientInit();
      });
   }

   private void setupComplete(FMLLoadCompleteEvent event) {
      PROXY.postInit();
   }

   // $FF: synthetic method
   private static Object $deserializeLambda$(SerializedLambda lambda) {
      String var1 = lambda.getImplMethodName();
      byte var2 = -1;
      switch(var1.hashCode()) {
      case 1818100338:
         if (var1.equals("<init>")) {
            var2 = 0;
         }
      default:
         switch(var2) {
         case 0:
            if (lambda.getImplMethodKind() == 8 && lambda.getFunctionalInterfaceClass().equals("net/minecraftforge/fml/DistExecutor$SafeSupplier") && lambda.getFunctionalInterfaceMethodName().equals("get") && lambda.getFunctionalInterfaceMethodSignature().equals("()Ljava/lang/Object;") && lambda.getImplClass().equals("com/github/alexthe666/iceandfire/client/ClientProxy") && lambda.getImplMethodSignature().equals("()V")) {
               return ClientProxy::new;
            } else if (lambda.getImplMethodKind() == 8 && lambda.getFunctionalInterfaceClass().equals("net/minecraftforge/fml/DistExecutor$SafeSupplier") && lambda.getFunctionalInterfaceMethodName().equals("get") && lambda.getFunctionalInterfaceMethodSignature().equals("()Ljava/lang/Object;") && lambda.getImplClass().equals("com/github/alexthe666/iceandfire/CommonProxy") && lambda.getImplMethodSignature().equals("()V")) {
               return CommonProxy::new;
            }
         default:
            throw new IllegalArgumentException("Invalid lambda deserialization");
         }
      }
   }

   static {
      ChannelBuilder channel = ChannelBuilder.named(new ResourceLocation("iceandfire", "main_channel"));
      String version = PROTOCOL_VERSION;
      version.getClass();
      Objects.requireNonNull(version);
      channel = channel.clientAcceptedVersions(version::equals);
      version = PROTOCOL_VERSION;
      version.getClass();
      Objects.requireNonNull(version);
      NETWORK_WRAPPER = channel.serverAcceptedVersions(version::equals).networkProtocolVersion(() -> {
         return PROTOCOL_VERSION;
      }).simpleChannel();
   }
}
