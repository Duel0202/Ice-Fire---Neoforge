package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.config.BiomeConfig;
import java.util.HashMap;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType.Builder;
import net.minecraft.world.entity.SpawnPlacements.Type;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "iceandfire",
   bus = Bus.MOD
)
public class IafEntityRegistry {
   public static final DeferredRegister<EntityType<?>> ENTITIES;
   public static final RegistryObject<EntityType<EntityDragonPart>> DRAGON_MULTIPART;
   public static final RegistryObject<EntityType<EntitySlowPart>> SLOW_MULTIPART;
   public static final RegistryObject<EntityType<EntityHydraHead>> HYDRA_MULTIPART;
   public static final RegistryObject<EntityType<EntityCyclopsEye>> CYCLOPS_MULTIPART;
   public static final RegistryObject<EntityType<EntityDragonEgg>> DRAGON_EGG;
   public static final RegistryObject<EntityType<EntityDragonArrow>> DRAGON_ARROW;
   public static final RegistryObject<EntityType<EntityDragonSkull>> DRAGON_SKULL;
   public static final RegistryObject<EntityType<EntityFireDragon>> FIRE_DRAGON;
   public static final RegistryObject<EntityType<EntityIceDragon>> ICE_DRAGON;
   public static final RegistryObject<EntityType<EntityLightningDragon>> LIGHTNING_DRAGON;
   public static final RegistryObject<EntityType<EntityDragonFireCharge>> FIRE_DRAGON_CHARGE;
   public static final RegistryObject<EntityType<EntityDragonIceCharge>> ICE_DRAGON_CHARGE;
   public static final RegistryObject<EntityType<EntityDragonLightningCharge>> LIGHTNING_DRAGON_CHARGE;
   public static final RegistryObject<EntityType<EntityHippogryphEgg>> HIPPOGRYPH_EGG;
   public static final RegistryObject<EntityType<EntityHippogryph>> HIPPOGRYPH;
   public static final RegistryObject<EntityType<EntityStoneStatue>> STONE_STATUE;
   public static final RegistryObject<EntityType<EntityGorgon>> GORGON;
   public static final RegistryObject<EntityType<EntityPixie>> PIXIE;
   public static final RegistryObject<EntityType<EntityCyclops>> CYCLOPS;
   public static final RegistryObject<EntityType<EntitySiren>> SIREN;
   public static final RegistryObject<EntityType<EntityHippocampus>> HIPPOCAMPUS;
   public static final RegistryObject<EntityType<EntityDeathWorm>> DEATH_WORM;
   public static final RegistryObject<EntityType<EntityDeathWormEgg>> DEATH_WORM_EGG;
   public static final RegistryObject<EntityType<EntityCockatrice>> COCKATRICE;
   public static final RegistryObject<EntityType<EntityCockatriceEgg>> COCKATRICE_EGG;
   public static final RegistryObject<EntityType<EntityStymphalianBird>> STYMPHALIAN_BIRD;
   public static final RegistryObject<EntityType<EntityStymphalianFeather>> STYMPHALIAN_FEATHER;
   public static final RegistryObject<EntityType<EntityStymphalianArrow>> STYMPHALIAN_ARROW;
   public static final RegistryObject<EntityType<EntityTroll>> TROLL;
   public static final RegistryObject<EntityType<EntityMyrmexWorker>> MYRMEX_WORKER;
   public static final RegistryObject<EntityType<EntityMyrmexSoldier>> MYRMEX_SOLDIER;
   public static final RegistryObject<EntityType<EntityMyrmexSentinel>> MYRMEX_SENTINEL;
   public static final RegistryObject<EntityType<EntityMyrmexRoyal>> MYRMEX_ROYAL;
   public static final RegistryObject<EntityType<EntityMyrmexQueen>> MYRMEX_QUEEN;
   public static final RegistryObject<EntityType<EntityMyrmexEgg>> MYRMEX_EGG;
   public static final RegistryObject<EntityType<EntityAmphithere>> AMPHITHERE;
   public static final RegistryObject<EntityType<EntityAmphithereArrow>> AMPHITHERE_ARROW;
   public static final RegistryObject<EntityType<EntitySeaSerpent>> SEA_SERPENT;
   public static final RegistryObject<EntityType<EntitySeaSerpentBubbles>> SEA_SERPENT_BUBBLES;
   public static final RegistryObject<EntityType<EntitySeaSerpentArrow>> SEA_SERPENT_ARROW;
   public static final RegistryObject<EntityType<EntityChainTie>> CHAIN_TIE;
   public static final RegistryObject<EntityType<EntityPixieCharge>> PIXIE_CHARGE;
   public static final RegistryObject<EntityType<EntityMyrmexSwarmer>> MYRMEX_SWARMER;
   public static final RegistryObject<EntityType<EntityTideTrident>> TIDE_TRIDENT;
   public static final RegistryObject<EntityType<EntityMobSkull>> MOB_SKULL;
   public static final RegistryObject<EntityType<EntityDreadThrall>> DREAD_THRALL;
   public static final RegistryObject<EntityType<EntityDreadGhoul>> DREAD_GHOUL;
   public static final RegistryObject<EntityType<EntityDreadBeast>> DREAD_BEAST;
   public static final RegistryObject<EntityType<EntityDreadScuttler>> DREAD_SCUTTLER;
   public static final RegistryObject<EntityType<EntityDreadLich>> DREAD_LICH;
   public static final RegistryObject<EntityType<EntityDreadLichSkull>> DREAD_LICH_SKULL;
   public static final RegistryObject<EntityType<EntityDreadKnight>> DREAD_KNIGHT;
   public static final RegistryObject<EntityType<EntityDreadHorse>> DREAD_HORSE;
   public static final RegistryObject<EntityType<EntityHydra>> HYDRA;
   public static final RegistryObject<EntityType<EntityHydraBreath>> HYDRA_BREATH;
   public static final RegistryObject<EntityType<EntityHydraArrow>> HYDRA_ARROW;
   public static final RegistryObject<EntityType<EntityGhost>> GHOST;
   public static final RegistryObject<EntityType<EntityGhostSword>> GHOST_SWORD;
   public static HashMap<String, Boolean> LOADED_ENTITIES;

   private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(Builder<T> builder, String entityName) {
      return ENTITIES.register(entityName, () -> {
         return builder.m_20712_(entityName);
      });
   }

   @SubscribeEvent
   public static void bakeAttributes(EntityAttributeCreationEvent creationEvent) {
      creationEvent.put((EntityType)DRAGON_EGG.get(), EntityDragonEgg.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DRAGON_SKULL.get(), EntityDragonSkull.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)FIRE_DRAGON.get(), EntityFireDragon.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)ICE_DRAGON.get(), EntityIceDragon.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)LIGHTNING_DRAGON.get(), EntityLightningDragon.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)HIPPOGRYPH.get(), EntityHippogryph.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)GORGON.get(), EntityGorgon.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)STONE_STATUE.get(), EntityStoneStatue.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)PIXIE.get(), EntityPixie.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)CYCLOPS.get(), EntityCyclops.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)SIREN.get(), EntitySiren.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)HIPPOCAMPUS.get(), EntityHippocampus.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DEATH_WORM.get(), EntityDeathWorm.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)COCKATRICE.get(), EntityCockatrice.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)STYMPHALIAN_BIRD.get(), EntityStymphalianBird.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)TROLL.get(), EntityTroll.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_WORKER.get(), EntityMyrmexWorker.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_SOLDIER.get(), EntityMyrmexSoldier.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_SENTINEL.get(), EntityMyrmexSentinel.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_ROYAL.get(), EntityMyrmexRoyal.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_QUEEN.get(), EntityMyrmexQueen.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_EGG.get(), EntityMyrmexEgg.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MYRMEX_SWARMER.get(), EntityMyrmexSwarmer.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)AMPHITHERE.get(), EntityAmphithere.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)SEA_SERPENT.get(), EntitySeaSerpent.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)MOB_SKULL.get(), EntityMobSkull.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_THRALL.get(), EntityDreadThrall.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_LICH.get(), EntityDreadLich.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_BEAST.get(), EntityDreadBeast.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_HORSE.get(), EntityDreadHorse.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_GHOUL.get(), EntityDreadGhoul.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_KNIGHT.get(), EntityDreadKnight.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)DREAD_SCUTTLER.get(), EntityDreadScuttler.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)HYDRA.get(), EntityHydra.bakeAttributes().m_22265_());
      creationEvent.put((EntityType)GHOST.get(), EntityGhost.bakeAttributes().m_22265_());
   }

   @SubscribeEvent
   public static void commonSetup(FMLCommonSetupEvent event) {
      SpawnPlacements.m_21754_((EntityType)HIPPOGRYPH.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
      SpawnPlacements.m_21754_((EntityType)TROLL.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, EntityTroll::canTrollSpawnOn);
      SpawnPlacements.m_21754_((EntityType)DREAD_LICH.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, EntityDreadLich::canLichSpawnOn);
      SpawnPlacements.m_21754_((EntityType)COCKATRICE.get(), Type.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::m_217057_);
      SpawnPlacements.m_21754_((EntityType)AMPHITHERE.get(), Type.NO_RESTRICTIONS, Types.MOTION_BLOCKING, EntityAmphithere::canAmphithereSpawnOn);
   }

   public static void addSpawners(Holder<Biome> biome, net.neoforge.common.world.ModifiableBiomeInfo.BiomeInfo.Builder builder) {
      if (IafConfig.spawnHippogryphs && BiomeConfig.test(BiomeConfig.hippogryphBiomes, biome)) {
         builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new SpawnerData((EntityType)HIPPOGRYPH.get(), IafConfig.hippogryphSpawnRate, 1, 1));
         LOADED_ENTITIES.put("HIPPOGRYPH", true);
      }

      if (IafConfig.spawnLiches && BiomeConfig.test(BiomeConfig.mausoleumBiomes, biome)) {
         builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new SpawnerData((EntityType)DREAD_LICH.get(), IafConfig.lichSpawnRate, 1, 1));
         LOADED_ENTITIES.put("DREAD_LICH", true);
      }

      if (IafConfig.spawnCockatrices && BiomeConfig.test(BiomeConfig.cockatriceBiomes, biome)) {
         builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new SpawnerData((EntityType)COCKATRICE.get(), IafConfig.cockatriceSpawnRate, 1, 2));
         LOADED_ENTITIES.put("COCKATRICE", true);
      }

      if (IafConfig.spawnAmphitheres && BiomeConfig.test(BiomeConfig.amphithereBiomes, biome)) {
         builder.getMobSpawnSettings().getSpawner(MobCategory.CREATURE).add(new SpawnerData((EntityType)AMPHITHERE.get(), IafConfig.amphithereSpawnRate, 1, 3));
         LOADED_ENTITIES.put("AMPHITHERE", true);
      }

      if (IafConfig.spawnTrolls && (BiomeConfig.test(BiomeConfig.forestTrollBiomes, biome) || BiomeConfig.test(BiomeConfig.snowyTrollBiomes, biome) || BiomeConfig.test(BiomeConfig.mountainTrollBiomes, biome))) {
         builder.getMobSpawnSettings().getSpawner(MobCategory.MONSTER).add(new SpawnerData((EntityType)TROLL.get(), IafConfig.trollSpawnRate, 1, 3));
         if (BiomeConfig.test(BiomeConfig.forestTrollBiomes, biome)) {
            LOADED_ENTITIES.put("TROLL_F", true);
         }

         if (BiomeConfig.test(BiomeConfig.snowyTrollBiomes, biome)) {
            LOADED_ENTITIES.put("TROLL_S", true);
         }

         if (BiomeConfig.test(BiomeConfig.mountainTrollBiomes, biome)) {
            LOADED_ENTITIES.put("TROLL_M", true);
         }
      }

   }

   static {
      ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, "iceandfire");
      DRAGON_MULTIPART = registerEntity(Builder.m_20704_(EntityDragonPart::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20719_().setCustomClientFactory(EntityDragonPart::new), "dragon_multipart");
      SLOW_MULTIPART = registerEntity(Builder.m_20704_(EntitySlowPart::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20719_().setCustomClientFactory(EntitySlowPart::new), "multipart");
      HYDRA_MULTIPART = registerEntity(Builder.m_20704_(EntityHydraHead::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20719_().setCustomClientFactory(EntityHydraHead::new), "hydra_multipart");
      CYCLOPS_MULTIPART = registerEntity(Builder.m_20704_(EntityCyclopsEye::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).m_20719_().setCustomClientFactory(EntityCyclopsEye::new), "cylcops_multipart");
      DRAGON_EGG = registerEntity(Builder.m_20704_(EntityDragonEgg::new, MobCategory.MISC).m_20699_(0.45F, 0.55F).m_20719_(), "dragon_egg");
      DRAGON_ARROW = registerEntity(Builder.m_20704_(EntityDragonArrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityDragonArrow::new), "dragon_arrow");
      DRAGON_SKULL = registerEntity(Builder.m_20704_(EntityDragonSkull::new, MobCategory.MISC).m_20699_(0.9F, 0.65F), "dragon_skull");
      FIRE_DRAGON = registerEntity(Builder.m_20704_(EntityFireDragon::new, MobCategory.CREATURE).m_20699_(0.78F, 1.2F).m_20719_().setTrackingRange(256).m_20702_(10), "fire_dragon");
      ICE_DRAGON = registerEntity(Builder.m_20704_(EntityIceDragon::new, MobCategory.CREATURE).m_20699_(0.78F, 1.2F).setTrackingRange(256).m_20702_(10), "ice_dragon");
      LIGHTNING_DRAGON = registerEntity(Builder.m_20704_(EntityLightningDragon::new, MobCategory.CREATURE).m_20699_(0.78F, 1.2F).setTrackingRange(256).m_20702_(10), "lightning_dragon");
      FIRE_DRAGON_CHARGE = registerEntity(Builder.m_20704_(EntityDragonFireCharge::new, MobCategory.MISC).m_20699_(0.9F, 0.9F).setCustomClientFactory(EntityDragonFireCharge::new), "fire_dragon_charge");
      ICE_DRAGON_CHARGE = registerEntity(Builder.m_20704_(EntityDragonIceCharge::new, MobCategory.MISC).m_20699_(0.9F, 0.9F).setCustomClientFactory(EntityDragonIceCharge::new), "ice_dragon_charge");
      LIGHTNING_DRAGON_CHARGE = registerEntity(Builder.m_20704_(EntityDragonLightningCharge::new, MobCategory.MISC).m_20699_(0.9F, 0.9F).setCustomClientFactory(EntityDragonLightningCharge::new), "lightning_dragon_charge");
      HIPPOGRYPH_EGG = registerEntity(Builder.m_20704_(EntityHippogryphEgg::new, MobCategory.MISC).m_20699_(0.5F, 0.5F), "hippogryph_egg");
      HIPPOGRYPH = registerEntity(Builder.m_20704_(EntityHippogryph::new, MobCategory.CREATURE).m_20699_(1.7F, 1.6F).setTrackingRange(128), "hippogryph");
      STONE_STATUE = registerEntity(Builder.m_20704_(EntityStoneStatue::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F), "stone_statue");
      GORGON = registerEntity(Builder.m_20704_(EntityGorgon::new, MobCategory.CREATURE).m_20699_(0.8F, 1.99F), "gorgon");
      PIXIE = registerEntity(Builder.m_20704_(EntityPixie::new, MobCategory.CREATURE).m_20699_(0.4F, 0.8F), "pixie");
      CYCLOPS = registerEntity(Builder.m_20704_(EntityCyclops::new, MobCategory.CREATURE).m_20699_(1.95F, 7.4F).m_20702_(8), "cyclops");
      SIREN = registerEntity(Builder.m_20704_(EntitySiren::new, MobCategory.CREATURE).m_20699_(1.6F, 0.9F), "siren");
      HIPPOCAMPUS = registerEntity(Builder.m_20704_(EntityHippocampus::new, MobCategory.CREATURE).m_20699_(1.95F, 0.95F), "hippocampus");
      DEATH_WORM = registerEntity(Builder.m_20704_(EntityDeathWorm::new, MobCategory.CREATURE).m_20699_(0.8F, 0.8F).setTrackingRange(128), "deathworm");
      DEATH_WORM_EGG = registerEntity(Builder.m_20704_(EntityDeathWormEgg::new, MobCategory.MISC).m_20699_(0.5F, 0.5F), "deathworm_egg");
      COCKATRICE = registerEntity(Builder.m_20704_(EntityCockatrice::new, MobCategory.CREATURE).m_20699_(1.1F, 1.0F), "cockatrice");
      COCKATRICE_EGG = registerEntity(Builder.m_20704_(EntityCockatriceEgg::new, MobCategory.MISC).m_20699_(0.5F, 0.5F), "cockatrice_egg");
      STYMPHALIAN_BIRD = registerEntity(Builder.m_20704_(EntityStymphalianBird::new, MobCategory.CREATURE).m_20699_(1.3F, 1.2F).setTrackingRange(128), "stymphalian_bird");
      STYMPHALIAN_FEATHER = registerEntity(Builder.m_20704_(EntityStymphalianFeather::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityStymphalianFeather::new), "stymphalian_feather");
      STYMPHALIAN_ARROW = registerEntity(Builder.m_20704_(EntityStymphalianArrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityStymphalianArrow::new), "stymphalian_arrow");
      TROLL = registerEntity(Builder.m_20704_(EntityTroll::new, MobCategory.MONSTER).m_20699_(1.2F, 3.5F), "troll");
      MYRMEX_WORKER = registerEntity(Builder.m_20704_(EntityMyrmexWorker::new, MobCategory.CREATURE).m_20699_(0.9F, 0.9F), "myrmex_worker");
      MYRMEX_SOLDIER = registerEntity(Builder.m_20704_(EntityMyrmexSoldier::new, MobCategory.CREATURE).m_20699_(1.2F, 0.95F), "myrmex_soldier");
      MYRMEX_SENTINEL = registerEntity(Builder.m_20704_(EntityMyrmexSentinel::new, MobCategory.CREATURE).m_20699_(1.3F, 1.95F), "myrmex_sentinel");
      MYRMEX_ROYAL = registerEntity(Builder.m_20704_(EntityMyrmexRoyal::new, MobCategory.CREATURE).m_20699_(1.9F, 1.86F), "myrmex_royal");
      MYRMEX_QUEEN = registerEntity(Builder.m_20704_(EntityMyrmexQueen::new, MobCategory.CREATURE).m_20699_(2.9F, 1.86F), "myrmex_queen");
      MYRMEX_EGG = registerEntity(Builder.m_20704_(EntityMyrmexEgg::new, MobCategory.MISC).m_20699_(0.45F, 0.55F), "myrmex_egg");
      AMPHITHERE = registerEntity(Builder.m_20704_(EntityAmphithere::new, MobCategory.CREATURE).m_20699_(2.5F, 1.25F).setTrackingRange(128).m_20702_(8), "amphithere");
      AMPHITHERE_ARROW = registerEntity(Builder.m_20704_(EntityAmphithereArrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityAmphithereArrow::new), "amphithere_arrow");
      SEA_SERPENT = registerEntity(Builder.m_20704_(EntitySeaSerpent::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F).setTrackingRange(256).m_20702_(8), "sea_serpent");
      SEA_SERPENT_BUBBLES = registerEntity(Builder.m_20704_(EntitySeaSerpentBubbles::new, MobCategory.MISC).m_20699_(0.9F, 0.9F).setCustomClientFactory(EntitySeaSerpentBubbles::new), "sea_serpent_bubbles");
      SEA_SERPENT_ARROW = registerEntity(Builder.m_20704_(EntitySeaSerpentArrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntitySeaSerpentArrow::new), "sea_serpent_arrow");
      CHAIN_TIE = registerEntity(Builder.m_20704_(EntityChainTie::new, MobCategory.MISC).m_20699_(0.8F, 0.9F), "chain_tie");
      PIXIE_CHARGE = registerEntity(Builder.m_20704_(EntityPixieCharge::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityPixieCharge::new), "pixie_charge");
      MYRMEX_SWARMER = registerEntity(Builder.m_20704_(EntityMyrmexSwarmer::new, MobCategory.CREATURE).m_20699_(0.5F, 0.5F), "myrmex_swarmer");
      TIDE_TRIDENT = registerEntity(Builder.m_20704_(EntityTideTrident::new, MobCategory.MISC).m_20699_(0.85F, 0.5F), "tide_trident");
      MOB_SKULL = registerEntity(Builder.m_20704_(EntityMobSkull::new, MobCategory.MISC).m_20699_(0.85F, 0.85F), "mob_skull");
      DREAD_THRALL = registerEntity(Builder.m_20704_(EntityDreadThrall::new, MobCategory.MONSTER).m_20699_(0.6F, 1.8F), "dread_thrall");
      DREAD_GHOUL = registerEntity(Builder.m_20704_(EntityDreadGhoul::new, MobCategory.MONSTER).m_20699_(0.6F, 1.8F), "dread_ghoul");
      DREAD_BEAST = registerEntity(Builder.m_20704_(EntityDreadBeast::new, MobCategory.MONSTER).m_20699_(1.2F, 0.9F), "dread_beast");
      DREAD_SCUTTLER = registerEntity(Builder.m_20704_(EntityDreadScuttler::new, MobCategory.MONSTER).m_20699_(1.5F, 1.3F), "dread_scuttler");
      DREAD_LICH = registerEntity(Builder.m_20704_(EntityDreadLich::new, MobCategory.MONSTER).m_20699_(0.6F, 1.8F), "dread_lich");
      DREAD_LICH_SKULL = registerEntity(Builder.m_20704_(EntityDreadLichSkull::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityDreadLichSkull::new), "dread_lich_skull");
      DREAD_KNIGHT = registerEntity(Builder.m_20704_(EntityDreadKnight::new, MobCategory.MONSTER).m_20699_(0.6F, 1.8F), "dread_knight");
      DREAD_HORSE = registerEntity(Builder.m_20704_(EntityDreadHorse::new, MobCategory.MONSTER).m_20699_(1.3964844F, 1.6F), "dread_horse");
      HYDRA = registerEntity(Builder.m_20704_(EntityHydra::new, MobCategory.CREATURE).m_20699_(2.8F, 1.39F), "hydra");
      HYDRA_BREATH = registerEntity(Builder.m_20704_(EntityHydraBreath::new, MobCategory.MISC).m_20699_(0.9F, 0.9F).setCustomClientFactory(EntityHydraBreath::new), "hydra_breath");
      HYDRA_ARROW = registerEntity(Builder.m_20704_(EntityHydraArrow::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityHydraArrow::new), "hydra_arrow");
      GHOST = registerEntity(Builder.m_20704_(EntityGhost::new, MobCategory.MONSTER).m_20699_(0.8F, 1.9F).m_20719_(), "ghost");
      GHOST_SWORD = registerEntity(Builder.m_20704_(EntityGhostSword::new, MobCategory.MISC).m_20699_(0.5F, 0.5F).setCustomClientFactory(EntityGhostSword::new), "ghost_sword");
      LOADED_ENTITIES = new HashMap();
      LOADED_ENTITIES.put("HIPPOGRYPH", false);
      LOADED_ENTITIES.put("DREAD_LICH", false);
      LOADED_ENTITIES.put("COCKATRICE", false);
      LOADED_ENTITIES.put("AMPHITHERE", false);
      LOADED_ENTITIES.put("TROLL_F", false);
      LOADED_ENTITIES.put("TROLL_S", false);
      LOADED_ENTITIES.put("TROLL_M", false);
   }
}
