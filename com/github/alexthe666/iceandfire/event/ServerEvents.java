package com.github.alexthe666.iceandfire.event;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.entity.EntityAmphithere;
import com.github.alexthe666.iceandfire.entity.EntityCockatrice;
import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.github.alexthe666.iceandfire.entity.EntityHydra;
import com.github.alexthe666.iceandfire.entity.EntityHydraHead;
import com.github.alexthe666.iceandfire.entity.EntityMutlipartPart;
import com.github.alexthe666.iceandfire.entity.EntityStoneStatue;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.IafVillagerRegistry;
import com.github.alexthe666.iceandfire.entity.ai.AiDebug;
import com.github.alexthe666.iceandfire.entity.ai.EntitySheepAIFollowCyclops;
import com.github.alexthe666.iceandfire.entity.ai.VillagerAIFearUntamed;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.entity.util.IAnimalFear;
import com.github.alexthe666.iceandfire.entity.util.IVillagerFear;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemChain;
import com.github.alexthe666.iceandfire.item.ItemCockatriceScepter;
import com.github.alexthe666.iceandfire.item.ItemDeathwormGauntlet;
import com.github.alexthe666.iceandfire.item.ItemDragonsteelArmor;
import com.github.alexthe666.iceandfire.item.ItemGhostSword;
import com.github.alexthe666.iceandfire.item.ItemScaleArmor;
import com.github.alexthe666.iceandfire.item.ItemTrollArmor;
import com.github.alexthe666.iceandfire.message.MessagePlayerHitMultipart;
import com.github.alexthe666.iceandfire.message.MessageSwingArm;
import com.github.alexthe666.iceandfire.message.MessageSyncPath;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.Pathfinding;
import com.github.alexthe666.iceandfire.pathfinding.raycoms.pathjobs.AbstractPathJob;
import com.github.alexthe666.iceandfire.world.gen.WorldGenFireDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenIceDragonCave;
import com.github.alexthe666.iceandfire.world.gen.WorldGenLightningDragonCave;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.CombatEntry;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer.Builder;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforge.event.LootTableLoadEvent;
import net.neoforge.event.entity.EntityStruckByLightningEvent;
import net.neoforge.event.entity.ProjectileImpactEvent;
import net.neoforge.event.entity.living.LivingAttackEvent;
import net.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforge.event.entity.living.LivingDropsEvent;
import net.neoforge.event.entity.living.LivingFallEvent;
import net.neoforge.event.entity.living.LivingHurtEvent;
import net.neoforge.event.entity.living.LivingEntityUseItemEvent.Tick;
import net.neoforge.event.entity.living.LivingEvent.LivingTickEvent;
import net.neoforge.event.entity.living.MobSpawnEvent.FinalizeSpawn;
import net.neoforge.event.entity.player.AttackEntityEvent;
import net.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.neoforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.neoforge.event.entity.player.PlayerInteractEvent.LeftClickEmpty;
import net.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;
import net.neoforge.event.level.BlockEvent.BreakEvent;
import net.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforge.event.village.VillagerTradesEvent;
import net.neoforge.eventbus.api.EventPriority;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.tags.ITagManager;

@EventBusSubscriber(
   modid = "iceandfire"
)
public class ServerEvents {
   public static final UUID ALEX_UUID = UUID.fromString("71363abe-fd03-49c9-940d-aae8b8209b7c");
   private static final Predicate<LivingEntity> VILLAGER_FEAR = (entity) -> {
      return entity instanceof IVillagerFear;
   };
   private final Random rand = new Random();
   private static final String[] VILLAGE_TYPES = new String[]{"plains", "desert", "snowy", "savanna", "taiga"};
   public static String BOLT_DONT_DESTROY_LOOT = "iceandfire.bolt_skip_loot";

   private static void signalChickenAlarm(LivingEntity chicken, LivingEntity attacker) {
      float d0 = (float)IafConfig.cockatriceChickenSearchLength;
      List<EntityCockatrice> list = chicken.m_9236_().m_45976_(EntityCockatrice.class, (new AABB(chicken.m_20185_(), chicken.m_20186_(), chicken.m_20189_(), chicken.m_20185_() + 1.0D, chicken.m_20186_() + 1.0D, chicken.m_20189_() + 1.0D)).m_82377_((double)d0, 10.0D, (double)d0));
      if (!list.isEmpty()) {
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            EntityCockatrice cockatrice = (EntityCockatrice)var4.next();
            if (!(attacker instanceof EntityCockatrice) && !DragonUtils.hasSameOwner(cockatrice, attacker)) {
               if (attacker instanceof Player) {
                  Player player = (Player)attacker;
                  if (!player.m_7500_() && !cockatrice.m_21830_(player)) {
                     cockatrice.m_6710_(player);
                  }
               } else {
                  cockatrice.m_6710_(attacker);
               }
            }
         }

      }
   }

   private static void signalAmphithereAlarm(LivingEntity villager, LivingEntity attacker) {
      float d0 = IafConfig.amphithereVillagerSearchLength;
      List<EntityAmphithere> list = villager.m_9236_().m_45976_(EntityAmphithere.class, (new AABB(villager.m_20185_() - 1.0D, villager.m_20186_() - 1.0D, villager.m_20189_() - 1.0D, villager.m_20185_() + 1.0D, villager.m_20186_() + 1.0D, villager.m_20189_() + 1.0D)).m_82377_((double)d0, (double)d0, (double)d0));
      if (!list.isEmpty()) {
         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            Entity entity = (Entity)var4.next();
            if (entity instanceof EntityAmphithere) {
               EntityAmphithere amphithere = (EntityAmphithere)entity;
               if (!(attacker instanceof EntityAmphithere) && !DragonUtils.hasSameOwner(amphithere, attacker)) {
                  if (attacker instanceof Player) {
                     Player player = (Player)attacker;
                     if (!player.m_7500_() && !amphithere.m_21830_(player)) {
                        amphithere.m_6710_(player);
                     }
                  } else {
                     amphithere.m_6710_(attacker);
                  }
               }
            }
         }

      }
   }

   private static boolean isInEntityTag(ResourceLocation loc, EntityType<?> type) {
      return type.m_204039_(((ITagManager)Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags())).createTagKey(loc));
   }

   public static boolean isLivestock(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.FEAR_DRAGONS, entity.m_6095_());
   }

   public static boolean isVillager(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.VILLAGERS, entity.m_6095_());
   }

   public static boolean isSheep(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.SHEEP, entity.m_6095_());
   }

   public static boolean isChicken(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.CHICKENS, entity.m_6095_());
   }

   public static boolean isCockatriceTarget(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.COCKATRICE_TARGETS, entity.m_6095_());
   }

   public static boolean doesScareCockatrice(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.SCARES_COCKATRICES, entity.m_6095_());
   }

   public static boolean isBlindMob(Entity entity) {
      return entity != null && isInEntityTag(IafTagRegistry.BLINDED, entity.m_6095_());
   }

   public static boolean isRidingOrBeingRiddenBy(Entity first, Entity entityIn) {
      if (first != null && entityIn != null) {
         Iterator var2 = first.m_20197_().iterator();

         Entity entity;
         do {
            if (!var2.hasNext()) {
               return false;
            }

            entity = (Entity)var2.next();
         } while(!entity.equals(entityIn) && !isRidingOrBeingRiddenBy(entity, entityIn));

         return true;
      } else {
         return false;
      }
   }

   @SubscribeEvent
   public void onArrowCollide(ProjectileImpactEvent event) {
      HitResult var3 = event.getRayTraceResult();
      if (var3 instanceof EntityHitResult) {
         EntityHitResult result = (EntityHitResult)var3;
         Entity shotEntity = result.m_82443_();
         if (shotEntity instanceof EntityGhost) {
            event.setCanceled(true);
         } else {
            Entity shootingEntity = event.getEntity();
            if (shootingEntity instanceof AbstractArrow) {
               AbstractArrow arrow = (AbstractArrow)shootingEntity;
               if (arrow.m_19749_() != null) {
                  shootingEntity = arrow.m_19749_();
                  if (shootingEntity instanceof LivingEntity && isRidingOrBeingRiddenBy(shootingEntity, shotEntity) && shotEntity instanceof TamableAnimal) {
                     TamableAnimal tamable = (TamableAnimal)shotEntity;
                     if (tamable.m_21824_() && shotEntity.m_7307_(shootingEntity)) {
                        event.setCanceled(true);
                     }
                  }
               }
            }
         }
      }

   }

   @SubscribeEvent
   public static void addNewVillageBuilding(ServerAboutToStartEvent event) {
      if (IafConfig.villagerHouseWeight > 0) {
         Registry<StructureTemplatePool> templatePoolRegistry = (Registry)event.getServer().m_206579_().m_6632_(Registries.f_256948_).orElseThrow();
         Registry<StructureProcessorList> processorListRegistry = (Registry)event.getServer().m_206579_().m_6632_(Registries.f_257011_).orElseThrow();
         String[] var3 = VILLAGE_TYPES;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String type = var3[var5];
            IafVillagerRegistry.addBuildingToPool(templatePoolRegistry, processorListRegistry, new ResourceLocation("village/" + type + "/houses"), "iceandfire:village/" + type + "_scriber_1", IafConfig.villagerHouseWeight);
         }
      }

   }

   @SubscribeEvent
   public void onPlayerAttackMob(AttackEntityEvent event) {
      if (event.getTarget() instanceof EntityMutlipartPart && event.getEntity() instanceof Player) {
         event.setCanceled(true);
         Entity parent = ((EntityMutlipartPart)event.getTarget()).getParent();

         try {
            if (parent != null) {
               event.getEntity().m_5706_(parent);
            }
         } catch (Exception var4) {
            IceAndFire.LOGGER.warn("Exception thrown while interacting with entity.", var4);
         }

         int extraData = 0;
         if (event.getTarget() instanceof EntityHydraHead && parent instanceof EntityHydra) {
            extraData = ((EntityHydraHead)event.getTarget()).headIndex;
            ((EntityHydra)parent).triggerHeadFlags(extraData);
         }

         if (event.getTarget().m_9236_().f_46443_ && parent != null) {
            IceAndFire.NETWORK_WRAPPER.sendToServer(new MessagePlayerHitMultipart(parent.m_19879_(), extraData));
         }
      }

   }

   @SubscribeEvent
   public void onEntityFall(LivingFallEvent event) {
      if (event.getEntity() instanceof Player) {
         EntityDataProvider.getCapability(event.getEntity()).ifPresent((data) -> {
            if (data.miscData.hasDismounted) {
               event.setDamageMultiplier(0.0F);
               data.miscData.setDismounted(false);
            }

         });
      }

   }

   @SubscribeEvent
   public void onEntityDamage(LivingHurtEvent event) {
      float multi;
      if (event.getSource().m_269533_(DamageTypeTags.f_268524_)) {
         multi = 1.0F;
         if (event.getEntity().m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof ItemTrollArmor) {
            multi -= 0.1F;
         }

         if (event.getEntity().m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof ItemTrollArmor) {
            multi -= 0.3F;
         }

         if (event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof ItemTrollArmor) {
            multi -= 0.2F;
         }

         if (event.getEntity().m_6844_(EquipmentSlot.FEET).m_41720_() instanceof ItemTrollArmor) {
            multi -= 0.1F;
         }

         event.setAmount(event.getAmount() * multi);
      }

      if (event.getSource().m_276093_(IafDamageRegistry.DRAGON_FIRE_TYPE) || event.getSource().m_276093_(IafDamageRegistry.DRAGON_ICE_TYPE) || event.getSource().m_276093_(IafDamageRegistry.DRAGON_LIGHTNING_TYPE)) {
         multi = 1.0F;
         if (event.getEntity().m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof ItemScaleArmor || event.getEntity().m_6844_(EquipmentSlot.HEAD).m_41720_() instanceof ItemDragonsteelArmor) {
            multi -= 0.1F;
         }

         if (event.getEntity().m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof ItemScaleArmor || event.getEntity().m_6844_(EquipmentSlot.CHEST).m_41720_() instanceof ItemDragonsteelArmor) {
            multi -= 0.3F;
         }

         if (event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof ItemScaleArmor || event.getEntity().m_6844_(EquipmentSlot.LEGS).m_41720_() instanceof ItemDragonsteelArmor) {
            multi -= 0.2F;
         }

         if (event.getEntity().m_6844_(EquipmentSlot.FEET).m_41720_() instanceof ItemScaleArmor || event.getEntity().m_6844_(EquipmentSlot.FEET).m_41720_() instanceof ItemDragonsteelArmor) {
            multi -= 0.1F;
         }

         event.setAmount(event.getAmount() * multi);
      }

   }

   @SubscribeEvent
   public void onEntityDrop(LivingDropsEvent event) {
      if (event.getEntity() instanceof WitherSkeleton) {
         event.getDrops().add(new ItemEntity(event.getEntity().m_9236_(), event.getEntity().m_20185_(), event.getEntity().m_20186_(), event.getEntity().m_20189_(), new ItemStack((ItemLike)IafItemRegistry.WITHERBONE.get(), event.getEntity().m_217043_().m_188503_(2))));
      }

   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void makeItemDropsFireImmune(LivingDropsEvent event) {
      boolean makeFireImmune;
      label20: {
         makeFireImmune = false;
         Entity var5 = event.getSource().m_7640_();
         if (var5 instanceof LightningBolt) {
            LightningBolt bolt = (LightningBolt)var5;
            if (bolt.m_19880_().contains(BOLT_DONT_DESTROY_LOOT)) {
               makeFireImmune = true;
               break label20;
            }
         }

         var5 = event.getSource().m_7639_();
         if (var5 instanceof Player) {
            Player player = (Player)var5;
            if (player.m_21120_(player.m_7655_()).m_204117_(IafItemTags.MAKE_ITEM_DROPS_FIREIMMUNE)) {
               makeFireImmune = true;
            }
         }
      }

      if (makeFireImmune) {
         Set<ItemEntity> fireImmuneDrops = (Set)event.getDrops().stream().map((itemEntity) -> {
            return new ItemEntity(itemEntity.m_9236_(), itemEntity.m_20185_(), itemEntity.m_20186_(), itemEntity.m_20189_(), itemEntity.m_32055_()) {
               public boolean m_5825_() {
                  return true;
               }
            };
         }).collect(Collectors.toSet());
         event.getDrops().clear();
         event.getDrops().addAll(fireImmuneDrops);
      }

   }

   @SubscribeEvent
   public void onLivingAttacked(LivingAttackEvent event) {
      if (event.getSource() != null && event.getSource().m_7639_() != null) {
         Entity attacker = event.getSource().m_7639_();
         if (attacker instanceof LivingEntity) {
            EntityDataProvider.getCapability(attacker).ifPresent((data) -> {
               if (data.miscData.loveTicks > 0) {
                  event.setCanceled(true);
               }

            });
            if (isChicken(event.getEntity())) {
               signalChickenAlarm(event.getEntity(), (LivingEntity)attacker);
            } else if (DragonUtils.isVillager(event.getEntity())) {
               signalAmphithereAlarm(event.getEntity(), (LivingEntity)attacker);
            }
         }
      }

   }

   @SubscribeEvent
   public void onLivingSetTarget(LivingChangeTargetEvent event) {
      LivingEntity target = event.getOriginalTarget();
      if (target != null) {
         LivingEntity attacker = event.getEntity();
         if (isChicken(target)) {
            signalChickenAlarm(target, attacker);
         } else if (DragonUtils.isVillager(target)) {
            signalAmphithereAlarm(target, attacker);
         }
      }

   }

   @SubscribeEvent
   public void onPlayerAttack(AttackEntityEvent event) {
      if (event.getTarget() != null && isSheep(event.getTarget())) {
         float dist = (float)IafConfig.cyclopesSheepSearchLength;
         List<Entity> list = event.getTarget().m_9236_().m_45933_(event.getEntity(), event.getEntity().m_20191_().m_82363_((double)dist, (double)dist, (double)dist));
         if (!list.isEmpty()) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
               Entity entity = (Entity)var4.next();
               if (entity instanceof EntityCyclops) {
                  EntityCyclops cyclops = (EntityCyclops)entity;
                  if (!cyclops.isBlinded() && !event.getEntity().m_7500_()) {
                     cyclops.m_6710_(event.getEntity());
                  }
               }
            }
         }
      }

      Entity var8 = event.getTarget();
      if (var8 instanceof EntityStoneStatue) {
         EntityStoneStatue statue = (EntityStoneStatue)var8;
         statue.m_21153_(statue.m_21233_());
         if (event.getEntity() != null) {
            ItemStack stack = event.getEntity().m_21205_();
            event.getTarget().m_5496_(SoundEvents.f_12442_, 2.0F, 0.5F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F);
            if (stack.m_41720_().m_8096_(Blocks.f_50069_.m_49966_()) || stack.m_41720_().m_5524_().contains("pickaxe")) {
               event.setCanceled(true);
               statue.setCrackAmount(statue.getCrackAmount() + 1);
               if (statue.getCrackAmount() > 9) {
                  CompoundTag writtenTag = new CompoundTag();
                  event.getTarget().m_20240_(writtenTag);
                  event.getTarget().m_5496_(SoundEvents.f_12442_, 2.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 0.5F);
                  event.getTarget().m_142687_(RemovalReason.KILLED);
                  if (stack.getEnchantmentLevel(Enchantments.f_44985_) > 0) {
                     ItemStack statuette = new ItemStack((ItemLike)IafItemRegistry.STONE_STATUE.get());
                     CompoundTag tag = statuette.m_41784_();
                     tag.m_128379_("IAFStoneStatuePlayerEntity", statue.getTrappedEntityTypeString().equalsIgnoreCase("minecraft:player"));
                     tag.m_128359_("IAFStoneStatueEntityID", statue.getTrappedEntityTypeString());
                     tag.m_128365_("IAFStoneStatueNBT", writtenTag);
                     statue.m_7380_(tag);
                     if (!statue.m_9236_().m_5776_()) {
                        statue.m_5552_(statuette, 1.0F);
                     }
                  } else if (!statue.m_9236_().f_46443_) {
                     statue.m_20000_(Blocks.f_50652_.m_5456_(), 2 + event.getEntity().m_217043_().m_188503_(4));
                  }

                  statue.m_142687_(RemovalReason.KILLED);
               }
            }
         }
      }

   }

   @SubscribeEvent
   public void onEntityDie(LivingDeathEvent event) {
      EntityDataProvider.getCapability(event.getEntity()).ifPresent((data) -> {
         if (!event.getEntity().m_9236_().m_5776_()) {
            if (!data.chainData.getChainedTo().isEmpty()) {
               ItemEntity entityitem = new ItemEntity(event.getEntity().m_9236_(), event.getEntity().m_20185_(), event.getEntity().m_20186_() + 1.0D, event.getEntity().m_20189_(), new ItemStack((ItemLike)IafItemRegistry.CHAIN.get(), data.chainData.getChainedTo().size()));
               entityitem.m_32060_();
               event.getEntity().m_9236_().m_7967_(entityitem);
               data.chainData.clearChains();
            }

         }
      });
      if (event.getEntity().m_20148_().equals(ALEX_UUID)) {
         event.getEntity().m_5552_(new ItemStack((ItemLike)IafItemRegistry.WEEZER_BLUE_ALBUM.get()), 1.0F);
      }

      if (event.getEntity() instanceof Player && IafConfig.ghostsFromPlayerDeaths) {
         Entity attacker = event.getEntity().m_21188_();
         if (attacker instanceof Player && event.getEntity().m_217043_().m_188503_(3) == 0) {
            CombatTracker combat = event.getEntity().m_21231_();
            CombatEntry entry = combat.m_19298_();
            boolean flag = entry != null && (entry.f_19250_().m_276093_(DamageTypes.f_268671_) || entry.f_19250_().m_276093_(DamageTypes.f_268722_) || entry.f_19250_().m_276093_(DamageTypes.f_268546_));
            if (event.getEntity().m_21023_(MobEffects.f_19614_)) {
               flag = true;
            }

            if (flag) {
               Level world = event.getEntity().m_9236_();
               EntityGhost ghost = (EntityGhost)((EntityType)IafEntityRegistry.GHOST.get()).m_20615_(world);
               ghost.m_20359_(event.getEntity());
               if (!world.f_46443_) {
                  ghost.m_6518_((ServerLevelAccessor)world, world.m_6436_(event.getEntity().m_20183_()), MobSpawnType.SPAWNER, (SpawnGroupData)null, (CompoundTag)null);
                  world.m_7967_(ghost);
               }

               ghost.setDaytimeMode(true);
            }
         }
      }

   }

   @SubscribeEvent
   public void onEntityStopUsingItem(Tick event) {
      if (event.getItem().m_41720_() instanceof ItemDeathwormGauntlet || event.getItem().m_41720_() instanceof ItemCockatriceScepter) {
         event.setDuration(20);
      }

   }

   @SubscribeEvent
   public void onEntityUseItem(RightClickItem event) {
      if (event.getEntity() != null && event.getEntity().m_146909_() > 87.0F && event.getEntity().m_20202_() != null && event.getEntity().m_20202_() instanceof EntityDragonBase) {
         ((EntityDragonBase)event.getEntity().m_20202_()).m_6071_(event.getEntity(), event.getHand());
      }

   }

   @SubscribeEvent
   public void onEntityUpdate(LivingTickEvent event) {
      if (AiDebug.isEnabled() && event.getEntity() instanceof Mob && AiDebug.contains((Mob)event.getEntity())) {
         AiDebug.logData();
      }

   }

   @SubscribeEvent
   public void onEntityInteract(EntityInteract event) {
      Entity var3 = event.getTarget();
      if (var3 instanceof LivingEntity) {
         LivingEntity target = (LivingEntity)var3;
         EntityDataProvider.getCapability(target).ifPresent((data) -> {
            if (data.chainData.isChainedTo(event.getEntity())) {
               data.chainData.removeChain(event.getEntity());
               if (!event.getLevel().m_5776_()) {
                  event.getTarget().m_20000_((ItemLike)IafItemRegistry.CHAIN.get(), 1);
               }

               event.setCanceled(true);
               event.setCancellationResult(InteractionResult.SUCCESS);
            }

         });
      }

      if (!event.getLevel().m_5776_() && event.getTarget() instanceof Mob && event.getItemStack().m_41720_() == Items.f_42398_) {
         if (AiDebug.isEnabled()) {
            AiDebug.addEntity((Mob)event.getTarget());
         }

         if (Pathfinding.isDebug()) {
            if (((UUID)AbstractPathJob.trackingMap.getOrDefault(event.getEntity(), UUID.randomUUID())).equals(event.getTarget().m_20148_())) {
               AbstractPathJob.trackingMap.remove(event.getEntity());
               IceAndFire.sendMSGToPlayer(new MessageSyncPath(new HashSet(), new HashSet(), new HashSet()), (ServerPlayer)event.getEntity());
            } else {
               AbstractPathJob.trackingMap.put(event.getEntity(), event.getTarget().m_20148_());
            }
         }
      }

   }

   @SubscribeEvent
   public static void onPlayerLeftClick(LeftClickEmpty event) {
      onLeftClick(event.getEntity(), event.getItemStack());
      if (event.getLevel().f_46443_) {
         IceAndFire.sendMSGToServer(new MessageSwingArm());
      }

   }

   public static void onLeftClick(Player playerEntity, ItemStack stack) {
      if (stack.m_41720_() == IafItemRegistry.GHOST_SWORD.get()) {
         ItemGhostSword.spawnGhostSwordEntity(stack, playerEntity);
      }

   }

   @SubscribeEvent
   public void onPlayerRightClick(RightClickBlock event) {
      if (event.getEntity() != null && event.getLevel().m_8055_(event.getPos()).m_60734_() instanceof AbstractChestBlock && !event.getEntity().m_7500_()) {
         float dist = (float)IafConfig.dragonGoldSearchLength;
         List<Entity> list = event.getLevel().m_45933_(event.getEntity(), event.getEntity().m_20191_().m_82377_((double)dist, (double)dist, (double)dist));
         if (!list.isEmpty()) {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
               Entity entity = (Entity)var4.next();
               if (entity instanceof EntityDragonBase) {
                  EntityDragonBase dragon = (EntityDragonBase)entity;
                  if (!dragon.m_21824_() && !dragon.isModelDead() && !dragon.m_21830_(event.getEntity())) {
                     dragon.m_21837_(false);
                     dragon.m_21839_(false);
                     dragon.m_6710_(event.getEntity());
                  }
               }
            }
         }
      }

      if (event.getLevel().m_8055_(event.getPos()).m_60734_() instanceof WallBlock) {
         ItemChain.attachToFence(event.getEntity(), event.getLevel(), event.getPos());
      }

   }

   @SubscribeEvent
   public void onBreakBlock(BreakEvent event) {
      if (event.getPlayer() != null && (event.getState().m_60734_() instanceof AbstractChestBlock || event.getState().m_60734_() == IafBlockRegistry.GOLD_PILE.get() || event.getState().m_60734_() == IafBlockRegistry.SILVER_PILE.get() || event.getState().m_60734_() == IafBlockRegistry.COPPER_PILE.get())) {
         float dist = (float)IafConfig.dragonGoldSearchLength;
         List<Entity> list = event.getLevel().m_45933_(event.getPlayer(), event.getPlayer().m_20191_().m_82377_((double)dist, (double)dist, (double)dist));
         if (list.isEmpty()) {
            return;
         }

         Iterator var4 = list.iterator();

         while(var4.hasNext()) {
            Entity entity = (Entity)var4.next();
            if (entity instanceof EntityDragonBase) {
               EntityDragonBase dragon = (EntityDragonBase)entity;
               if (!dragon.m_21824_() && !dragon.isModelDead() && !dragon.m_21830_(event.getPlayer()) && !event.getPlayer().m_7500_()) {
                  dragon.m_21837_(false);
                  dragon.m_21839_(false);
                  dragon.m_6710_(event.getPlayer());
               }
            }
         }
      }

   }

   public static void onChestGenerated(LootTableLoadEvent event) {
      ResourceLocation eventName = event.getName();
      boolean condition1 = eventName.equals(BuiltInLootTables.f_78742_) || eventName.equals(BuiltInLootTables.f_78759_) || eventName.equals(BuiltInLootTables.f_78764_) || eventName.equals(BuiltInLootTables.f_78686_) || eventName.equals(BuiltInLootTables.f_78763_) || eventName.equals(BuiltInLootTables.f_78762_);
      Builder item;
      net.minecraft.world.level.storage.loot.LootPool.Builder builder;
      if (condition1 || eventName.equals(BuiltInLootTables.f_78746_)) {
         item = LootItem.m_79579_((ItemLike)IafItemRegistry.MANUSCRIPT.get()).m_79711_(20).m_79707_(5);
         builder = (new net.minecraft.world.level.storage.loot.LootPool.Builder()).name("iaf_manuscript").m_79076_(item).m_79080_(LootItemRandomChanceCondition.m_81927_(0.35F)).m_165133_(UniformGenerator.m_165780_(1.0F, 4.0F)).m_165135_(UniformGenerator.m_165780_(0.0F, 3.0F));
         event.getTable().addPool(builder.m_79082_());
      }

      if (!condition1 && !eventName.equals(BuiltInLootTables.f_78688_) && !eventName.equals(BuiltInLootTables.f_78689_) && !eventName.equals(BuiltInLootTables.f_78744_) && !eventName.equals(BuiltInLootTables.f_78745_)) {
         if (event.getName().equals(WorldGenFireDragonCave.FIRE_DRAGON_CHEST) || event.getName().equals(WorldGenFireDragonCave.FIRE_DRAGON_CHEST_MALE) || event.getName().equals(WorldGenIceDragonCave.ICE_DRAGON_CHEST) || event.getName().equals(WorldGenIceDragonCave.ICE_DRAGON_CHEST_MALE) || event.getName().equals(WorldGenLightningDragonCave.LIGHTNING_DRAGON_CHEST) || event.getName().equals(WorldGenLightningDragonCave.LIGHTNING_DRAGON_CHEST_MALE)) {
            item = LootItem.m_79579_((ItemLike)IafItemRegistry.WEEZER_BLUE_ALBUM.get()).m_79711_(100).m_79707_(1);
            builder = (new net.minecraft.world.level.storage.loot.LootPool.Builder()).name("iaf_weezer").m_79076_(item).m_79080_(LootItemRandomChanceCondition.m_81927_(0.01F)).m_165133_(UniformGenerator.m_165780_(1.0F, 1.0F));
            event.getTable().addPool(builder.m_79082_());
         }
      } else {
         item = LootItem.m_79579_((ItemLike)IafItemRegistry.SILVER_INGOT.get()).m_79711_(15).m_79707_(12);
         builder = (new net.minecraft.world.level.storage.loot.LootPool.Builder()).name("iaf_silver_ingot").m_79076_(item).m_79080_(LootItemRandomChanceCondition.m_81927_(0.5F)).m_165133_(UniformGenerator.m_165780_(1.0F, 3.0F)).m_165135_(UniformGenerator.m_165780_(0.0F, 3.0F));
         event.getTable().addPool(builder.m_79082_());
      }

   }

   @SubscribeEvent
   public void onPlayerLeaveEvent(PlayerLoggedOutEvent event) {
      if (event.getEntity() != null && !event.getEntity().m_20197_().isEmpty()) {
         Iterator var2 = event.getEntity().m_20197_().iterator();

         while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            entity.m_8127_();
         }
      }

   }

   @SubscribeEvent
   public void onEntityJoinWorld(FinalizeSpawn event) {
      Mob mob = event.getEntity();

      try {
         if (isSheep(mob) && mob instanceof Animal) {
            Animal animal = (Animal)mob;
            animal.f_21345_.m_25352_(8, new EntitySheepAIFollowCyclops(animal, 1.2D));
         }

         if (isVillager(mob) && IafConfig.villagersFearDragons) {
            mob.f_21345_.m_25352_(1, new VillagerAIFearUntamed((PathfinderMob)mob, LivingEntity.class, 8.0F, 0.8D, 0.8D, VILLAGER_FEAR));
         }

         if (isLivestock(mob) && IafConfig.animalsFearDragons) {
            mob.f_21345_.m_25352_(1, new VillagerAIFearUntamed((PathfinderMob)mob, LivingEntity.class, 30.0F, 1.0D, 0.5D, (entity) -> {
               return entity instanceof IAnimalFear && ((IAnimalFear)entity).shouldAnimalsFear(mob);
            }));
         }
      } catch (Exception var4) {
         IceAndFire.LOGGER.warn("Tried to add unique behaviors to vanilla mobs and encountered an error");
      }

   }

   @SubscribeEvent
   public void onVillagerTrades(VillagerTradesEvent event) {
      if (event.getType() == IafVillagerRegistry.SCRIBE.get()) {
         IafVillagerRegistry.addScribeTrades(event.getTrades());
      }

   }

   @SubscribeEvent
   public void onLightningHit(EntityStruckByLightningEvent event) {
      if ((event.getEntity() instanceof ItemEntity || event.getEntity() instanceof ExperienceOrb) && event.getLightning().m_19880_().contains(BOLT_DONT_DESTROY_LOOT)) {
         event.setCanceled(true);
      } else if (event.getLightning().m_19880_().contains(event.getEntity().m_20149_())) {
         event.setCanceled(true);
      }

   }
}
