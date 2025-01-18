package com.github.alexthe666.iceandfire.client;

import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.client.gui.IafGuiRegistry;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexQueen;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexRoyal;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexSentinel;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexSoldier;
import com.github.alexthe666.iceandfire.client.model.ModelMyrmexWorker;
import com.github.alexthe666.iceandfire.client.model.animator.FireDragonTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.IceDragonTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.LightningTabulaDragonAnimator;
import com.github.alexthe666.iceandfire.client.model.animator.SeaSerpentTabulaModelAnimator;
import com.github.alexthe666.iceandfire.client.model.util.DragonAnimationsLibrary;
import com.github.alexthe666.iceandfire.client.model.util.EnumDragonModelTypes;
import com.github.alexthe666.iceandfire.client.model.util.EnumDragonPoses;
import com.github.alexthe666.iceandfire.client.model.util.EnumSeaSerpentAnimations;
import com.github.alexthe666.iceandfire.client.model.util.TabulaModelHandlerHelper;
import com.github.alexthe666.iceandfire.client.render.entity.RenderAmphithere;
import com.github.alexthe666.iceandfire.client.render.entity.RenderAmphithereArrow;
import com.github.alexthe666.iceandfire.client.render.entity.RenderChainTie;
import com.github.alexthe666.iceandfire.client.render.entity.RenderCockatrice;
import com.github.alexthe666.iceandfire.client.render.entity.RenderCyclops;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDeathWorm;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonArrow;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonBase;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonEgg;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonFireCharge;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonLightningCharge;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDragonSkull;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadBeast;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadGhoul;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadHorse;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadKnight;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadLich;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadLichSkull;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadScuttler;
import com.github.alexthe666.iceandfire.client.render.entity.RenderDreadThrall;
import com.github.alexthe666.iceandfire.client.render.entity.RenderGhost;
import com.github.alexthe666.iceandfire.client.render.entity.RenderGhostSword;
import com.github.alexthe666.iceandfire.client.render.entity.RenderGorgon;
import com.github.alexthe666.iceandfire.client.render.entity.RenderHippocampus;
import com.github.alexthe666.iceandfire.client.render.entity.RenderHippogryph;
import com.github.alexthe666.iceandfire.client.render.entity.RenderHydra;
import com.github.alexthe666.iceandfire.client.render.entity.RenderHydraArrow;
import com.github.alexthe666.iceandfire.client.render.entity.RenderLightningDragon;
import com.github.alexthe666.iceandfire.client.render.entity.RenderMobSkull;
import com.github.alexthe666.iceandfire.client.render.entity.RenderMyrmexBase;
import com.github.alexthe666.iceandfire.client.render.entity.RenderMyrmexEgg;
import com.github.alexthe666.iceandfire.client.render.entity.RenderNothing;
import com.github.alexthe666.iceandfire.client.render.entity.RenderPixie;
import com.github.alexthe666.iceandfire.client.render.entity.RenderSeaSerpent;
import com.github.alexthe666.iceandfire.client.render.entity.RenderSeaSerpentArrow;
import com.github.alexthe666.iceandfire.client.render.entity.RenderSiren;
import com.github.alexthe666.iceandfire.client.render.entity.RenderStoneStatue;
import com.github.alexthe666.iceandfire.client.render.entity.RenderStymphalianArrow;
import com.github.alexthe666.iceandfire.client.render.entity.RenderStymphalianBird;
import com.github.alexthe666.iceandfire.client.render.entity.RenderStymphalianFeather;
import com.github.alexthe666.iceandfire.client.render.entity.RenderTideTrident;
import com.github.alexthe666.iceandfire.client.render.entity.RenderTroll;
import com.github.alexthe666.iceandfire.client.render.tile.RenderDreadPortal;
import com.github.alexthe666.iceandfire.client.render.tile.RenderDreadSpawner;
import com.github.alexthe666.iceandfire.client.render.tile.RenderEggInIce;
import com.github.alexthe666.iceandfire.client.render.tile.RenderGhostChest;
import com.github.alexthe666.iceandfire.client.render.tile.RenderJar;
import com.github.alexthe666.iceandfire.client.render.tile.RenderLectern;
import com.github.alexthe666.iceandfire.client.render.tile.RenderPixieHouse;
import com.github.alexthe666.iceandfire.client.render.tile.RenderPodium;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.entity.tile.IafTileEntityRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.ItemDragonBow;
import com.github.alexthe666.iceandfire.item.ItemDragonHorn;
import com.github.alexthe666.iceandfire.item.ItemSummoningCrystal;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import java.io.IOException;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforge.api.distmarker.Dist;
import net.neoforge.client.event.RegisterShadersEvent;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   bus = Bus.MOD,
   modid = "iceandfire"
)
public class IafClientSetup {
   public static TabulaModel FIRE_DRAGON_BASE_MODEL;
   public static TabulaModel ICE_DRAGON_BASE_MODEL;
   public static TabulaModel SEA_SERPENT_BASE_MODEL;
   public static TabulaModel LIGHTNING_DRAGON_BASE_MODEL;
   private static ShaderInstance rendertypeDreadPortalShader;
   public static final ResourceLocation GHOST_CHEST_LOCATION = new ResourceLocation("iceandfire", "models/ghost/ghost_chest");
   public static final ResourceLocation GHOST_CHEST_LEFT_LOCATION = new ResourceLocation("iceandfire", "models/ghost/ghost_chest_left");
   public static final ResourceLocation GHOST_CHEST_RIGHT_LOCATION = new ResourceLocation("iceandfire", "models/ghost/ghost_chest_right");

   public static void clientInit() {
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.FIRE_DRAGON.get(), (x) -> {
         return new RenderDragonBase(x, FIRE_DRAGON_BASE_MODEL, 0);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.ICE_DRAGON.get(), (manager) -> {
         return new RenderDragonBase(manager, ICE_DRAGON_BASE_MODEL, 1);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.LIGHTNING_DRAGON.get(), (manager) -> {
         return new RenderLightningDragon(manager, LIGHTNING_DRAGON_BASE_MODEL, 2);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DRAGON_EGG.get(), RenderDragonEgg::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DRAGON_ARROW.get(), RenderDragonArrow::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DRAGON_SKULL.get(), (manager) -> {
         return new RenderDragonSkull(manager, FIRE_DRAGON_BASE_MODEL, ICE_DRAGON_BASE_MODEL, LIGHTNING_DRAGON_BASE_MODEL);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.FIRE_DRAGON_CHARGE.get(), (manager) -> {
         return new RenderDragonFireCharge(manager, true);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.ICE_DRAGON_CHARGE.get(), (manager) -> {
         return new RenderDragonFireCharge(manager, false);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.LIGHTNING_DRAGON_CHARGE.get(), RenderDragonLightningCharge::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HIPPOGRYPH_EGG.get(), ThrownItemRenderer::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HIPPOGRYPH.get(), RenderHippogryph::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.STONE_STATUE.get(), RenderStoneStatue::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.GORGON.get(), RenderGorgon::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.PIXIE.get(), RenderPixie::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.CYCLOPS.get(), RenderCyclops::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.SIREN.get(), RenderSiren::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HIPPOCAMPUS.get(), RenderHippocampus::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DEATH_WORM.get(), RenderDeathWorm::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DEATH_WORM_EGG.get(), ThrownItemRenderer::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.COCKATRICE.get(), RenderCockatrice::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.COCKATRICE_EGG.get(), ThrownItemRenderer::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.STYMPHALIAN_BIRD.get(), RenderStymphalianBird::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.STYMPHALIAN_FEATHER.get(), RenderStymphalianFeather::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.STYMPHALIAN_ARROW.get(), RenderStymphalianArrow::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.TROLL.get(), RenderTroll::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_WORKER.get(), (manager) -> {
         return new RenderMyrmexBase(manager, new ModelMyrmexWorker(), 0.5F);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_SOLDIER.get(), (manager) -> {
         return new RenderMyrmexBase(manager, new ModelMyrmexSoldier(), 0.75F);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_QUEEN.get(), (manager) -> {
         return new RenderMyrmexBase(manager, new ModelMyrmexQueen(), 1.25F);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_EGG.get(), RenderMyrmexEgg::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_SENTINEL.get(), (manager) -> {
         return new RenderMyrmexBase(manager, new ModelMyrmexSentinel(), 0.85F);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_ROYAL.get(), (manager) -> {
         return new RenderMyrmexBase(manager, new ModelMyrmexRoyal(), 0.75F);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MYRMEX_SWARMER.get(), (manager) -> {
         return new RenderMyrmexBase(manager, new ModelMyrmexRoyal(), 0.25F);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.AMPHITHERE.get(), RenderAmphithere::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.AMPHITHERE_ARROW.get(), RenderAmphithereArrow::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.SEA_SERPENT.get(), (manager) -> {
         return new RenderSeaSerpent(manager, SEA_SERPENT_BASE_MODEL);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.SEA_SERPENT_BUBBLES.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.SEA_SERPENT_ARROW.get(), RenderSeaSerpentArrow::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.CHAIN_TIE.get(), RenderChainTie::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.PIXIE_CHARGE.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.TIDE_TRIDENT.get(), RenderTideTrident::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.MOB_SKULL.get(), (manager) -> {
         return new RenderMobSkull(manager, SEA_SERPENT_BASE_MODEL);
      });
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_SCUTTLER.get(), RenderDreadScuttler::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_GHOUL.get(), RenderDreadGhoul::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_BEAST.get(), RenderDreadBeast::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_SCUTTLER.get(), RenderDreadScuttler::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_THRALL.get(), RenderDreadThrall::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_LICH.get(), RenderDreadLich::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_LICH_SKULL.get(), RenderDreadLichSkull::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_KNIGHT.get(), RenderDreadKnight::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DREAD_HORSE.get(), RenderDreadHorse::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HYDRA.get(), RenderHydra::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HYDRA_BREATH.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HYDRA_ARROW.get(), RenderHydraArrow::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.SLOW_MULTIPART.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.DRAGON_MULTIPART.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.CYCLOPS_MULTIPART.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.HYDRA_MULTIPART.get(), RenderNothing::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.GHOST.get(), RenderGhost::new);
      EntityRenderers.m_174036_((EntityType)IafEntityRegistry.GHOST_SWORD.get(), RenderGhostSword::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.PODIUM.get(), RenderPodium::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.IAF_LECTERN.get(), RenderLectern::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.EGG_IN_ICE.get(), RenderEggInIce::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.PIXIE_HOUSE.get(), RenderPixieHouse::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.PIXIE_JAR.get(), RenderJar::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.DREAD_PORTAL.get(), RenderDreadPortal::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.DREAD_SPAWNER.get(), RenderDreadSpawner::new);
      BlockEntityRenderers.m_173590_((BlockEntityType)IafTileEntityRegistry.GHOST_CHEST.get(), RenderGhostChest::new);
   }

   @SubscribeEvent
   public static void setupShaders(RegisterShadersEvent event) throws IOException {
      ResourceProvider provider = event.getResourceProvider();
      event.registerShader(new ShaderInstance(provider, new ResourceLocation("iceandfire", "rendertype_dread_portal"), DefaultVertexFormat.f_85815_), (p_172782_) -> {
         rendertypeDreadPortalShader = p_172782_;
      });
   }

   public static ShaderInstance getRendertypeDreadPortalShader() {
      return rendertypeDreadPortalShader;
   }

   @SubscribeEvent
   public static void setupClient(FMLClientSetupEvent event) {
      event.enqueueWork(() -> {
         IafGuiRegistry.register();
         EnumSeaSerpentAnimations.initializeSerpentModels();
         DragonAnimationsLibrary.register(EnumDragonPoses.values(), EnumDragonModelTypes.values());

         try {
            SEA_SERPENT_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/seaserpent/seaserpent_base"), new SeaSerpentTabulaModelAnimator());
            FIRE_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/firedragon/firedragon_ground"), new FireDragonTabulaModelAnimator());
            ICE_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/icedragon/icedragon_ground"), new IceDragonTabulaModelAnimator());
            LIGHTNING_DRAGON_BASE_MODEL = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel("/assets/iceandfire/models/tabula/lightningdragon/lightningdragon_ground"), new LightningTabulaDragonAnimator());
         } catch (IOException var2) {
            var2.printStackTrace();
         }

         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.GOLD_PILE.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.SILVER_PILE.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.LECTERN.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PODIUM_OAK.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PODIUM_BIRCH.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PODIUM_SPRUCE.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PODIUM_JUNGLE.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PODIUM_ACACIA.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PODIUM_DARK_OAK.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.FIRE_LILY.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.FROST_LILY.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.LIGHTNING_LILY.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.DRAGON_ICE_SPIKES.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.MYRMEX_DESERT_RESIN_BLOCK.get(), RenderType.m_110466_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.MYRMEX_DESERT_RESIN_GLASS.get(), RenderType.m_110466_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.MYRMEX_JUNGLE_RESIN_BLOCK.get(), RenderType.m_110466_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.MYRMEX_JUNGLE_RESIN_GLASS.get(), RenderType.m_110466_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.MYRMEX_DESERT_BIOLIGHT.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.MYRMEX_JUNGLE_BIOLIGHT.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.DREAD_STONE_FACE.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.DREAD_TORCH.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.BURNT_TORCH.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.EGG_IN_ICE.get(), RenderType.m_110466_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.JAR_EMPTY.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.JAR_PIXIE_0.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.JAR_PIXIE_1.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.JAR_PIXIE_2.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.JAR_PIXIE_3.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.JAR_PIXIE_4.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PIXIE_HOUSE_OAK.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PIXIE_HOUSE_BIRCH.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PIXIE_HOUSE_SPRUCE.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.PIXIE_HOUSE_DARK_OAK.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.DREAD_SPAWNER.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.DREAD_TORCH_WALL.get(), RenderType.m_110463_());
         ItemBlockRenderTypes.setRenderLayer((Block)IafBlockRegistry.BURNT_TORCH_WALL.get(), RenderType.m_110463_());
         ItemPropertyFunction pulling = ItemProperties.m_117829_(Items.f_42411_, new ResourceLocation("pulling"));
         ItemPropertyFunction pull = (stack, worldIn, entity, p) -> {
            if (entity == null) {
               return 0.0F;
            } else {
               ItemDragonBow item = (ItemDragonBow)stack.m_41720_();
               return entity.m_21211_() != stack ? 0.0F : (float)(stack.m_41779_() - entity.m_21212_()) / 20.0F;
            }
         };
         ItemProperties.register(((Item)IafItemRegistry.DRAGON_BOW.get()).m_5456_(), new ResourceLocation("pulling"), pulling);
         ItemProperties.register(((Item)IafItemRegistry.DRAGON_BOW.get()).m_5456_(), new ResourceLocation("pull"), pull);
         ItemProperties.register((Item)IafItemRegistry.DRAGON_HORN.get(), new ResourceLocation("iceorfire"), (stack, level, entity, p) -> {
            return (float)ItemDragonHorn.getDragonType(stack) * 0.25F;
         });
         ItemProperties.register((Item)IafItemRegistry.SUMMONING_CRYSTAL_FIRE.get(), new ResourceLocation("has_dragon"), (stack, level, entity, p) -> {
            return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
         });
         ItemProperties.register((Item)IafItemRegistry.SUMMONING_CRYSTAL_ICE.get(), new ResourceLocation("has_dragon"), (stack, level, entity, p) -> {
            return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
         });
         ItemProperties.register((Item)IafItemRegistry.SUMMONING_CRYSTAL_LIGHTNING.get(), new ResourceLocation("has_dragon"), (stack, level, entity, p) -> {
            return ItemSummoningCrystal.hasDragon(stack) ? 1.0F : 0.0F;
         });
         ItemProperties.register((Item)IafItemRegistry.TIDE_TRIDENT.get(), new ResourceLocation("throwing"), (stack, level, entity, p) -> {
            return entity != null && entity.m_6117_() && entity.m_21211_() == stack ? 1.0F : 0.0F;
         });
      });
   }
}
