package com.github.alexthe666.iceandfire.block;

import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.item.BlockItemWithRender;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.item.IafTabRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import java.util.function.Supplier;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "iceandfire",
   bus = Bus.MOD
)
public class IafBlockRegistry {
   public static DeferredRegister<Block> BLOCKS;
   public static final SoundType SOUND_TYPE_GOLD;
   public static final RegistryObject<Block> LECTERN;
   public static final RegistryObject<Block> PODIUM_OAK;
   public static final RegistryObject<Block> PODIUM_BIRCH;
   public static final RegistryObject<Block> PODIUM_SPRUCE;
   public static final RegistryObject<Block> PODIUM_JUNGLE;
   public static final RegistryObject<Block> PODIUM_DARK_OAK;
   public static final RegistryObject<Block> PODIUM_ACACIA;
   public static final RegistryObject<Block> FIRE_LILY;
   public static final RegistryObject<Block> FROST_LILY;
   public static final RegistryObject<Block> LIGHTNING_LILY;
   public static final RegistryObject<Block> GOLD_PILE;
   public static final RegistryObject<Block> SILVER_PILE;
   public static final RegistryObject<Block> COPPER_PILE;
   public static final RegistryObject<Block> SILVER_ORE;
   public static final RegistryObject<Block> DEEPSLATE_SILVER_ORE;
   public static final RegistryObject<Block> SILVER_BLOCK;
   public static final RegistryObject<Block> SAPPHIRE_ORE;
   public static final RegistryObject<Block> SAPPHIRE_BLOCK;
   public static final RegistryObject<Block> RAW_SILVER_BLOCK;
   public static final RegistryObject<Block> CHARRED_DIRT;
   public static final RegistryObject<Block> CHARRED_GRASS;
   public static final RegistryObject<Block> CHARRED_STONE;
   public static final RegistryObject<Block> CHARRED_COBBLESTONE;
   public static final RegistryObject<Block> CHARRED_GRAVEL;
   public static final RegistryObject<Block> CHARRED_DIRT_PATH;
   public static final RegistryObject<Block> ASH;
   public static final RegistryObject<Block> FROZEN_DIRT;
   public static final RegistryObject<Block> FROZEN_GRASS;
   public static final RegistryObject<Block> FROZEN_STONE;
   public static final RegistryObject<Block> FROZEN_COBBLESTONE;
   public static final RegistryObject<Block> FROZEN_GRAVEL;
   public static final RegistryObject<Block> FROZEN_DIRT_PATH;
   public static final RegistryObject<Block> FROZEN_SPLINTERS;
   public static final RegistryObject<Block> DRAGON_ICE;
   public static final RegistryObject<Block> DRAGON_ICE_SPIKES;
   public static final RegistryObject<Block> CRACKLED_DIRT;
   public static final RegistryObject<Block> CRACKLED_GRASS;
   public static final RegistryObject<Block> CRACKLED_STONE;
   public static final RegistryObject<Block> CRACKLED_COBBLESTONE;
   public static final RegistryObject<Block> CRACKLED_GRAVEL;
   public static final RegistryObject<Block> CRACKLED_DIRT_PATH;
   public static final RegistryObject<Block> NEST;
   public static final RegistryObject<Block> DRAGON_SCALE_RED;
   public static final RegistryObject<Block> DRAGON_SCALE_GREEN;
   public static final RegistryObject<Block> DRAGON_SCALE_BRONZE;
   public static final RegistryObject<Block> DRAGON_SCALE_GRAY;
   public static final RegistryObject<Block> DRAGON_SCALE_BLUE;
   public static final RegistryObject<Block> DRAGON_SCALE_WHITE;
   public static final RegistryObject<Block> DRAGON_SCALE_SAPPHIRE;
   public static final RegistryObject<Block> DRAGON_SCALE_SILVER;
   public static final RegistryObject<Block> DRAGON_SCALE_ELECTRIC;
   public static final RegistryObject<Block> DRAGON_SCALE_AMYTHEST;
   public static final RegistryObject<Block> DRAGON_SCALE_COPPER;
   public static final RegistryObject<Block> DRAGON_SCALE_BLACK;
   public static final RegistryObject<Block> DRAGON_BONE_BLOCK;
   public static final RegistryObject<Block> DRAGON_BONE_BLOCK_WALL;
   public static final RegistryObject<Block> DRAGONFORGE_FIRE_BRICK;
   public static final RegistryObject<Block> DRAGONFORGE_ICE_BRICK;
   public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_BRICK;
   public static final RegistryObject<Block> DRAGONFORGE_FIRE_INPUT;
   public static final RegistryObject<Block> DRAGONFORGE_ICE_INPUT;
   public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_INPUT;
   public static final RegistryObject<Block> DRAGONFORGE_FIRE_CORE;
   public static final RegistryObject<Block> DRAGONFORGE_ICE_CORE;
   public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_CORE;
   public static final RegistryObject<Block> DRAGONFORGE_FIRE_CORE_DISABLED;
   public static final RegistryObject<Block> DRAGONFORGE_ICE_CORE_DISABLED;
   public static final RegistryObject<Block> DRAGONFORGE_LIGHTNING_CORE_DISABLED;
   public static final RegistryObject<Block> EGG_IN_ICE;
   public static final RegistryObject<Block> PIXIE_HOUSE_MUSHROOM_RED;
   public static final RegistryObject<Block> PIXIE_HOUSE_MUSHROOM_BROWN;
   public static final RegistryObject<Block> PIXIE_HOUSE_OAK;
   public static final RegistryObject<Block> PIXIE_HOUSE_BIRCH;
   public static final RegistryObject<Block> PIXIE_HOUSE_SPRUCE;
   public static final RegistryObject<Block> PIXIE_HOUSE_DARK_OAK;
   public static final RegistryObject<Block> JAR_EMPTY;
   public static final RegistryObject<Block> JAR_PIXIE_0;
   public static final RegistryObject<Block> JAR_PIXIE_1;
   public static final RegistryObject<Block> JAR_PIXIE_2;
   public static final RegistryObject<Block> JAR_PIXIE_3;
   public static final RegistryObject<Block> JAR_PIXIE_4;
   public static final RegistryObject<Block> MYRMEX_DESERT_RESIN;
   public static final RegistryObject<Block> MYRMEX_DESERT_RESIN_STICKY;
   public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN;
   public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN_STICKY;
   public static final RegistryObject<Block> DESERT_MYRMEX_COCOON;
   public static final RegistryObject<Block> JUNGLE_MYRMEX_COCOON;
   public static final RegistryObject<Block> MYRMEX_DESERT_BIOLIGHT;
   public static final RegistryObject<Block> MYRMEX_JUNGLE_BIOLIGHT;
   public static final RegistryObject<Block> MYRMEX_DESERT_RESIN_BLOCK;
   public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN_BLOCK;
   public static final RegistryObject<Block> MYRMEX_DESERT_RESIN_GLASS;
   public static final RegistryObject<Block> MYRMEX_JUNGLE_RESIN_GLASS;
   public static final RegistryObject<Block> DRAGONSTEEL_FIRE_BLOCK;
   public static final RegistryObject<Block> DRAGONSTEEL_ICE_BLOCK;
   public static final RegistryObject<Block> DRAGONSTEEL_LIGHTNING_BLOCK;
   public static final RegistryObject<BlockDreadBase> DREAD_STONE;
   public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS;
   public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS_CHISELED;
   public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS_CRACKED;
   public static final RegistryObject<BlockDreadBase> DREAD_STONE_BRICKS_MOSSY;
   public static final RegistryObject<BlockDreadBase> DREAD_STONE_TILE;
   public static final RegistryObject<Block> DREAD_STONE_FACE;
   public static final RegistryObject<TorchBlock> DREAD_TORCH;
   public static final RegistryObject<BlockDreadTorchWall> DREAD_TORCH_WALL;
   public static final RegistryObject<Block> DREAD_STONE_BRICKS_STAIRS;
   public static final RegistryObject<Block> DREAD_STONE_BRICKS_SLAB;
   public static final RegistryObject<Block> DREADWOOD_LOG;
   public static final RegistryObject<BlockDreadBase> DREADWOOD_PLANKS;
   public static final RegistryObject<Block> DREADWOOD_PLANKS_LOCK;
   public static final RegistryObject<Block> DREAD_PORTAL;
   public static final RegistryObject<Block> DREAD_SPAWNER;
   public static final RegistryObject<TorchBlock> BURNT_TORCH;
   public static final RegistryObject<BlockBurntTorchWall> BURNT_TORCH_WALL;
   public static final RegistryObject<Block> GHOST_CHEST;
   public static final RegistryObject<Block> GRAVEYARD_SOIL;

   public static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
      RegistryObject<T> ret = BLOCKS.register(name, block);
      IafItemRegistry.registerItem(name, () -> {
         return new BlockItem((Block)ret.get(), new Properties());
      }, false);
      IafTabRegistry.TAB_BLOCKS_LIST.add(ret);
      return ret;
   }

   public static <T extends TorchBlock> RegistryObject<T> registerWallBlock(String name, Supplier<T> block) {
      RegistryObject<T> ret = BLOCKS.register(name, block);
      IafItemRegistry.registerItem(name, () -> {
         return new StandingAndWallBlockItem((Block)ret.get(), ((IWallBlock)ret.get()).wallBlock(), new Properties(), Direction.DOWN);
      }, false);
      IafTabRegistry.TAB_BLOCKS_LIST.add(ret);
      return ret;
   }

   public static <T extends Block> RegistryObject<T> registerWithRender(String name, Supplier<T> block) {
      RegistryObject<T> ret = BLOCKS.register(name, block);
      IafItemRegistry.registerItem(name, () -> {
         return new BlockItemWithRender((Block)ret.get(), new Properties());
      }, false);
      IafTabRegistry.TAB_BLOCKS_LIST.add(ret);
      return ret;
   }

   public static <T extends WallTorchBlock> RegistryObject<T> registerWallTorch(String name, Supplier<T> block) {
      return BLOCKS.register(name, block);
   }

   static {
      BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "iceandfire");
      SOUND_TYPE_GOLD = new SoundType(1.0F, 1.0F, IafSoundRegistry.GOLD_PILE_BREAK, IafSoundRegistry.GOLD_PILE_STEP, IafSoundRegistry.GOLD_PILE_BREAK, IafSoundRegistry.GOLD_PILE_STEP, IafSoundRegistry.GOLD_PILE_STEP);
      LECTERN = register("lectern", BlockLectern::new);
      PODIUM_OAK = register("podium_oak", BlockPodium::new);
      PODIUM_BIRCH = register("podium_birch", BlockPodium::new);
      PODIUM_SPRUCE = register("podium_spruce", BlockPodium::new);
      PODIUM_JUNGLE = register("podium_jungle", BlockPodium::new);
      PODIUM_DARK_OAK = register("podium_dark_oak", BlockPodium::new);
      PODIUM_ACACIA = register("podium_acacia", BlockPodium::new);
      FIRE_LILY = register("fire_lily", BlockElementalFlower::new);
      FROST_LILY = register("frost_lily", BlockElementalFlower::new);
      LIGHTNING_LILY = register("lightning_lily", BlockElementalFlower::new);
      GOLD_PILE = register("gold_pile", BlockGoldPile::new);
      SILVER_PILE = register("silver_pile", BlockGoldPile::new);
      COPPER_PILE = register("copper_pile", BlockGoldPile::new);
      SILVER_ORE = register("silver_ore", () -> {
         return new DropExperienceBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283947_).m_60913_(3.0F, 3.0F).m_60999_());
      });
      DEEPSLATE_SILVER_ORE = register("deepslate_silver_ore", () -> {
         return new DropExperienceBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283875_).m_60913_(3.0F, 3.0F).m_60999_());
      });
      SILVER_BLOCK = register("silver_block", () -> {
         return BlockGeneric.builder(3.0F, 5.0F, SoundType.f_56743_, MapColor.f_283906_, (NoteBlockInstrument)null, (PushReaction)null, false);
      });
      SAPPHIRE_ORE = register("sapphire_ore", () -> {
         return new DropExperienceBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283947_).m_60913_(4.0F, 3.0F).m_60999_(), UniformInt.m_146622_(3, 7));
      });
      SAPPHIRE_BLOCK = register("sapphire_block", () -> {
         return BlockGeneric.builder(3.0F, 6.0F, SoundType.f_56743_, MapColor.f_283906_, (NoteBlockInstrument)null, (PushReaction)null, false);
      });
      RAW_SILVER_BLOCK = register("raw_silver_block", () -> {
         return BlockGeneric.builder(3.0F, 5.0F, SoundType.f_56742_, MapColor.f_283906_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false);
      });
      CHARRED_DIRT = register("chared_dirt", () -> {
         return BlockReturningState.builder(0.5F, 0.0F, SoundType.f_56739_, MapColor.f_283762_, (NoteBlockInstrument)null, (PushReaction)null, false, Blocks.f_50493_.m_49966_());
      });
      CHARRED_GRASS = register("chared_grass", () -> {
         return BlockReturningState.builder(0.6F, 0.0F, SoundType.f_56739_, MapColor.f_283824_, (NoteBlockInstrument)null, (PushReaction)null, false, Blocks.f_50440_.m_49966_());
      });
      CHARRED_STONE = register("chared_stone", () -> {
         return BlockReturningState.builder(1.5F, 10.0F, SoundType.f_56742_, MapColor.f_283947_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false, Blocks.f_50069_.m_49966_());
      });
      CHARRED_COBBLESTONE = register("chared_cobblestone", () -> {
         return BlockReturningState.builder(2.0F, 10.0F, SoundType.f_56742_, MapColor.f_283947_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false, Blocks.f_50652_.m_49966_());
      });
      CHARRED_GRAVEL = register("chared_gravel", () -> {
         return new BlockFallingReturningState(0.6F, 0.0F, SoundType.f_56739_, MapColor.f_283762_, Blocks.f_49994_.m_49966_());
      });
      CHARRED_DIRT_PATH = register(BlockCharedPath.getNameFromType(0), () -> {
         return new BlockCharedPath(0);
      });
      ASH = register("ash", () -> {
         return BlockFallingGeneric.builder(0.5F, 0.0F, SoundType.f_56746_, MapColor.f_283761_, NoteBlockInstrument.SNARE);
      });
      FROZEN_DIRT = register("frozen_dirt", () -> {
         return BlockReturningState.builder(0.5F, 0.0F, SoundType.f_56744_, true, MapColor.f_283762_, (NoteBlockInstrument)null, (PushReaction)null, false, Blocks.f_50493_.m_49966_());
      });
      FROZEN_GRASS = register("frozen_grass", () -> {
         return BlockReturningState.builder(0.6F, 0.0F, SoundType.f_56744_, true, MapColor.f_283824_, (NoteBlockInstrument)null, (PushReaction)null, false, Blocks.f_50440_.m_49966_());
      });
      FROZEN_STONE = register("frozen_stone", () -> {
         return BlockReturningState.builder(1.5F, 1.0F, SoundType.f_56744_, true, MapColor.f_283947_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false, Blocks.f_50069_.m_49966_());
      });
      FROZEN_COBBLESTONE = register("frozen_cobblestone", () -> {
         return BlockReturningState.builder(2.0F, 2.0F, SoundType.f_56744_, true, MapColor.f_283947_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false, Blocks.f_50652_.m_49966_());
      });
      FROZEN_GRAVEL = register("frozen_gravel", () -> {
         return new BlockFallingReturningState(0.6F, 0.0F, SoundType.f_56744_, true, MapColor.f_283762_, Blocks.f_49994_.m_49966_());
      });
      FROZEN_DIRT_PATH = register(BlockCharedPath.getNameFromType(1), () -> {
         return new BlockCharedPath(1);
      });
      FROZEN_SPLINTERS = register("frozen_splinters", () -> {
         return BlockGeneric.builder(2.0F, 1.0F, SoundType.f_56744_, true, MapColor.f_283825_, NoteBlockInstrument.BASS, (PushReaction)null, true);
      });
      DRAGON_ICE = register("dragon_ice", () -> {
         return BlockGeneric.builder(0.5F, 0.0F, SoundType.f_56744_, true, MapColor.f_283828_, (NoteBlockInstrument)null, (PushReaction)null, false);
      });
      DRAGON_ICE_SPIKES = register("dragon_ice_spikes", BlockIceSpikes::new);
      CRACKLED_DIRT = register("crackled_dirt", () -> {
         return BlockReturningState.builder(0.5F, 0.0F, SoundType.f_56739_, MapColor.f_283762_, (NoteBlockInstrument)null, (PushReaction)null, false, Blocks.f_50493_.m_49966_());
      });
      CRACKLED_GRASS = register("crackled_grass", () -> {
         return BlockReturningState.builder(0.6F, 0.0F, SoundType.f_56739_, MapColor.f_283824_, (NoteBlockInstrument)null, (PushReaction)null, false, Blocks.f_50440_.m_49966_());
      });
      CRACKLED_STONE = register("crackled_stone", () -> {
         return BlockReturningState.builder(1.5F, 1.0F, SoundType.f_56742_, MapColor.f_283947_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false, Blocks.f_50069_.m_49966_());
      });
      CRACKLED_COBBLESTONE = register("crackled_cobblestone", () -> {
         return BlockReturningState.builder(2.0F, 2.0F, SoundType.f_56742_, MapColor.f_283947_, NoteBlockInstrument.BASEDRUM, (PushReaction)null, false, Blocks.f_50652_.m_49966_());
      });
      CRACKLED_GRAVEL = register("crackled_gravel", () -> {
         return new BlockFallingReturningState(0.6F, 0.0F, SoundType.f_56739_, MapColor.f_283762_, Blocks.f_49994_.m_49966_());
      });
      CRACKLED_DIRT_PATH = register(BlockCharedPath.getNameFromType(2), () -> {
         return new BlockCharedPath(2);
      });
      NEST = register("nest", () -> {
         return BlockGeneric.builder(0.5F, 0.0F, SoundType.f_56739_, false, MapColor.f_283915_, (NoteBlockInstrument)null, PushReaction.DESTROY, false);
      });
      DRAGON_SCALE_RED = register("dragonscale_red", () -> {
         return new BlockDragonScales(EnumDragonEgg.RED);
      });
      DRAGON_SCALE_GREEN = register("dragonscale_green", () -> {
         return new BlockDragonScales(EnumDragonEgg.GREEN);
      });
      DRAGON_SCALE_BRONZE = register("dragonscale_bronze", () -> {
         return new BlockDragonScales(EnumDragonEgg.BRONZE);
      });
      DRAGON_SCALE_GRAY = register("dragonscale_gray", () -> {
         return new BlockDragonScales(EnumDragonEgg.GRAY);
      });
      DRAGON_SCALE_BLUE = register("dragonscale_blue", () -> {
         return new BlockDragonScales(EnumDragonEgg.BLUE);
      });
      DRAGON_SCALE_WHITE = register("dragonscale_white", () -> {
         return new BlockDragonScales(EnumDragonEgg.WHITE);
      });
      DRAGON_SCALE_SAPPHIRE = register("dragonscale_sapphire", () -> {
         return new BlockDragonScales(EnumDragonEgg.SAPPHIRE);
      });
      DRAGON_SCALE_SILVER = register("dragonscale_silver", () -> {
         return new BlockDragonScales(EnumDragonEgg.SILVER);
      });
      DRAGON_SCALE_ELECTRIC = register("dragonscale_electric", () -> {
         return new BlockDragonScales(EnumDragonEgg.ELECTRIC);
      });
      DRAGON_SCALE_AMYTHEST = register("dragonscale_amythest", () -> {
         return new BlockDragonScales(EnumDragonEgg.AMYTHEST);
      });
      DRAGON_SCALE_COPPER = register("dragonscale_copper", () -> {
         return new BlockDragonScales(EnumDragonEgg.COPPER);
      });
      DRAGON_SCALE_BLACK = register("dragonscale_black", () -> {
         return new BlockDragonScales(EnumDragonEgg.BLACK);
      });
      DRAGON_BONE_BLOCK = register("dragon_bone_block", BlockDragonBone::new);
      DRAGON_BONE_BLOCK_WALL = register("dragon_bone_wall", () -> {
         return new BlockDragonBoneWall(net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_60926_((BlockBehaviour)DRAGON_BONE_BLOCK.get()));
      });
      DRAGONFORGE_FIRE_BRICK = register(BlockDragonforgeBricks.name(0), () -> {
         return new BlockDragonforgeBricks(0);
      });
      DRAGONFORGE_ICE_BRICK = register(BlockDragonforgeBricks.name(1), () -> {
         return new BlockDragonforgeBricks(1);
      });
      DRAGONFORGE_LIGHTNING_BRICK = register(BlockDragonforgeBricks.name(2), () -> {
         return new BlockDragonforgeBricks(2);
      });
      DRAGONFORGE_FIRE_INPUT = register(BlockDragonforgeInput.name(0), () -> {
         return new BlockDragonforgeInput(0);
      });
      DRAGONFORGE_ICE_INPUT = register(BlockDragonforgeInput.name(1), () -> {
         return new BlockDragonforgeInput(1);
      });
      DRAGONFORGE_LIGHTNING_INPUT = register(BlockDragonforgeInput.name(2), () -> {
         return new BlockDragonforgeInput(2);
      });
      DRAGONFORGE_FIRE_CORE = register(BlockDragonforgeCore.name(0, true), () -> {
         return new BlockDragonforgeCore(0, true);
      });
      DRAGONFORGE_ICE_CORE = register(BlockDragonforgeCore.name(1, true), () -> {
         return new BlockDragonforgeCore(1, true);
      });
      DRAGONFORGE_LIGHTNING_CORE = register(BlockDragonforgeCore.name(2, true), () -> {
         return new BlockDragonforgeCore(2, true);
      });
      DRAGONFORGE_FIRE_CORE_DISABLED = register(BlockDragonforgeCore.name(0, false), () -> {
         return new BlockDragonforgeCore(0, false);
      });
      DRAGONFORGE_ICE_CORE_DISABLED = register(BlockDragonforgeCore.name(1, false), () -> {
         return new BlockDragonforgeCore(1, false);
      });
      DRAGONFORGE_LIGHTNING_CORE_DISABLED = register(BlockDragonforgeCore.name(2, false), () -> {
         return new BlockDragonforgeCore(2, false);
      });
      EGG_IN_ICE = register("egginice", BlockEggInIce::new);
      PIXIE_HOUSE_MUSHROOM_RED = registerWithRender(BlockPixieHouse.name("mushroom_red"), BlockPixieHouse::new);
      PIXIE_HOUSE_MUSHROOM_BROWN = registerWithRender(BlockPixieHouse.name("mushroom_brown"), BlockPixieHouse::new);
      PIXIE_HOUSE_OAK = registerWithRender(BlockPixieHouse.name("oak"), BlockPixieHouse::new);
      PIXIE_HOUSE_BIRCH = registerWithRender(BlockPixieHouse.name("birch"), BlockPixieHouse::new);
      PIXIE_HOUSE_SPRUCE = registerWithRender(BlockPixieHouse.name("spruce"), BlockPixieHouse::new);
      PIXIE_HOUSE_DARK_OAK = registerWithRender(BlockPixieHouse.name("dark_oak"), BlockPixieHouse::new);
      JAR_EMPTY = register(BlockJar.name(-1), () -> {
         return new BlockJar(-1);
      });
      JAR_PIXIE_0 = register(BlockJar.name(0), () -> {
         return new BlockJar(0);
      });
      JAR_PIXIE_1 = register(BlockJar.name(1), () -> {
         return new BlockJar(1);
      });
      JAR_PIXIE_2 = register(BlockJar.name(2), () -> {
         return new BlockJar(2);
      });
      JAR_PIXIE_3 = register(BlockJar.name(3), () -> {
         return new BlockJar(3);
      });
      JAR_PIXIE_4 = register(BlockJar.name(4), () -> {
         return new BlockJar(4);
      });
      MYRMEX_DESERT_RESIN = register(BlockMyrmexResin.name(false, "desert"), () -> {
         return new BlockMyrmexResin(false);
      });
      MYRMEX_DESERT_RESIN_STICKY = register(BlockMyrmexResin.name(true, "desert"), () -> {
         return new BlockMyrmexResin(true);
      });
      MYRMEX_JUNGLE_RESIN = register(BlockMyrmexResin.name(false, "jungle"), () -> {
         return new BlockMyrmexResin(false);
      });
      MYRMEX_JUNGLE_RESIN_STICKY = register(BlockMyrmexResin.name(true, "jungle"), () -> {
         return new BlockMyrmexResin(true);
      });
      DESERT_MYRMEX_COCOON = register("desert_myrmex_cocoon", BlockMyrmexCocoon::new);
      JUNGLE_MYRMEX_COCOON = register("jungle_myrmex_cocoon", BlockMyrmexCocoon::new);
      MYRMEX_DESERT_BIOLIGHT = register("myrmex_desert_biolight", BlockMyrmexBiolight::new);
      MYRMEX_JUNGLE_BIOLIGHT = register("myrmex_jungle_biolight", BlockMyrmexBiolight::new);
      MYRMEX_DESERT_RESIN_BLOCK = register(BlockMyrmexConnectedResin.name(false, false), () -> {
         return new BlockMyrmexConnectedResin(false, false);
      });
      MYRMEX_JUNGLE_RESIN_BLOCK = register(BlockMyrmexConnectedResin.name(true, false), () -> {
         return new BlockMyrmexConnectedResin(true, false);
      });
      MYRMEX_DESERT_RESIN_GLASS = register(BlockMyrmexConnectedResin.name(false, true), () -> {
         return new BlockMyrmexConnectedResin(false, true);
      });
      MYRMEX_JUNGLE_RESIN_GLASS = register(BlockMyrmexConnectedResin.name(true, true), () -> {
         return new BlockMyrmexConnectedResin(true, true);
      });
      DRAGONSTEEL_FIRE_BLOCK = register("dragonsteel_fire_block", () -> {
         return BlockGeneric.builder(10.0F, 1000.0F, SoundType.f_56743_, MapColor.f_283906_, (NoteBlockInstrument)null, (PushReaction)null, false);
      });
      DRAGONSTEEL_ICE_BLOCK = register("dragonsteel_ice_block", () -> {
         return BlockGeneric.builder(10.0F, 1000.0F, SoundType.f_56743_, MapColor.f_283906_, (NoteBlockInstrument)null, (PushReaction)null, false);
      });
      DRAGONSTEEL_LIGHTNING_BLOCK = register("dragonsteel_lightning_block", () -> {
         return BlockGeneric.builder(10.0F, 1000.0F, SoundType.f_56743_, MapColor.f_283906_, (NoteBlockInstrument)null, (PushReaction)null, false);
      });
      DREAD_STONE = register("dread_stone", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56742_, MapColor.f_283947_, (NoteBlockInstrument)null, false);
      });
      DREAD_STONE_BRICKS = register("dread_stone_bricks", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56742_, MapColor.f_283947_, (NoteBlockInstrument)null, false);
      });
      DREAD_STONE_BRICKS_CHISELED = register("dread_stone_bricks_chiseled", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56742_, MapColor.f_283947_, (NoteBlockInstrument)null, false);
      });
      DREAD_STONE_BRICKS_CRACKED = register("dread_stone_bricks_cracked", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56742_, MapColor.f_283947_, (NoteBlockInstrument)null, false);
      });
      DREAD_STONE_BRICKS_MOSSY = register("dread_stone_bricks_mossy", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56742_, MapColor.f_283947_, (NoteBlockInstrument)null, false);
      });
      DREAD_STONE_TILE = register("dread_stone_tile", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56742_, MapColor.f_283947_, (NoteBlockInstrument)null, false);
      });
      DREAD_STONE_FACE = register("dread_stone_face", BlockDreadStoneFace::new);
      DREAD_TORCH = registerWallBlock("dread_torch", BlockDreadTorch::new);
      DREAD_TORCH_WALL = registerWallTorch("dread_torch_wall", BlockDreadTorchWall::new);
      DREAD_STONE_BRICKS_STAIRS = register("dread_stone_stairs", () -> {
         return new BlockGenericStairs(((BlockDreadBase)DREAD_STONE_BRICKS.get()).m_49966_());
      });
      DREAD_STONE_BRICKS_SLAB = register("dread_stone_slab", () -> {
         return new SlabBlock(net.minecraft.world.level.block.state.BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283947_).m_60913_(10.0F, 10000.0F));
      });
      DREADWOOD_LOG = register("dreadwood_log", BlockDreadWoodLog::new);
      DREADWOOD_PLANKS = register("dreadwood_planks", () -> {
         return BlockDreadBase.builder(-1.0F, 100000.0F, SoundType.f_56736_, MapColor.f_283825_, NoteBlockInstrument.BASS, true);
      });
      DREADWOOD_PLANKS_LOCK = register("dreadwood_planks_lock", BlockDreadWoodLock::new);
      DREAD_PORTAL = registerWithRender("dread_portal", BlockDreadPortal::new);
      DREAD_SPAWNER = register("dread_spawner", BlockDreadSpawner::new);
      BURNT_TORCH = registerWallBlock("burnt_torch", BlockBurntTorch::new);
      BURNT_TORCH_WALL = registerWallTorch("burnt_torch_wall", BlockBurntTorchWall::new);
      GHOST_CHEST = registerWithRender("ghost_chest", BlockGhostChest::new);
      GRAVEYARD_SOIL = register("graveyard_soil", BlockGraveyardSoil::new);
   }
}
