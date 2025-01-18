package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.mojang.datafixers.types.Type;
import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;

public class IafTileEntityRegistry {
   public static final DeferredRegister<BlockEntityType<?>> TYPES;
   public static final RegistryObject<BlockEntityType<TileEntityLectern>> IAF_LECTERN;
   public static final RegistryObject<BlockEntityType<TileEntityPodium>> PODIUM;
   public static final RegistryObject<BlockEntityType<TileEntityEggInIce>> EGG_IN_ICE;
   public static final RegistryObject<BlockEntityType<TileEntityPixieHouse>> PIXIE_HOUSE;
   public static final RegistryObject<BlockEntityType<TileEntityJar>> PIXIE_JAR;
   public static final RegistryObject<BlockEntityType<TileEntityMyrmexCocoon>> MYRMEX_COCOON;
   public static final RegistryObject<BlockEntityType<TileEntityDragonforge>> DRAGONFORGE_CORE;
   public static final RegistryObject<BlockEntityType<TileEntityDragonforgeBrick>> DRAGONFORGE_BRICK;
   public static final RegistryObject<BlockEntityType<TileEntityDragonforgeInput>> DRAGONFORGE_INPUT;
   public static final RegistryObject<BlockEntityType<TileEntityDreadPortal>> DREAD_PORTAL;
   public static final RegistryObject<BlockEntityType<TileEntityDreadSpawner>> DREAD_SPAWNER;
   public static final RegistryObject<BlockEntityType<TileEntityGhostChest>> GHOST_CHEST;

   public static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> registerTileEntity(Supplier<Builder<T>> supplier, String entityName) {
      return TYPES.register(entityName, () -> {
         return ((Builder)supplier.get()).m_58966_((Type)null);
      });
   }

   static {
      TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, "iceandfire");
      IAF_LECTERN = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityLectern::new, new Block[]{(Block)IafBlockRegistry.LECTERN.get()});
      }, "lectern");
      PODIUM = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityPodium::new, new Block[]{(Block)IafBlockRegistry.PODIUM_OAK.get(), (Block)IafBlockRegistry.PODIUM_BIRCH.get(), (Block)IafBlockRegistry.PODIUM_SPRUCE.get(), (Block)IafBlockRegistry.PODIUM_JUNGLE.get(), (Block)IafBlockRegistry.PODIUM_DARK_OAK.get(), (Block)IafBlockRegistry.PODIUM_ACACIA.get()});
      }, "podium");
      EGG_IN_ICE = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityEggInIce::new, new Block[]{(Block)IafBlockRegistry.EGG_IN_ICE.get()});
      }, "egginice");
      PIXIE_HOUSE = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityPixieHouse::new, new Block[]{(Block)IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED.get(), (Block)IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN.get(), (Block)IafBlockRegistry.PIXIE_HOUSE_OAK.get(), (Block)IafBlockRegistry.PIXIE_HOUSE_BIRCH.get(), (Block)IafBlockRegistry.PIXIE_HOUSE_BIRCH.get(), (Block)IafBlockRegistry.PIXIE_HOUSE_SPRUCE.get(), (Block)IafBlockRegistry.PIXIE_HOUSE_DARK_OAK.get()});
      }, "pixie_house");
      PIXIE_JAR = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityJar::new, new Block[]{(Block)IafBlockRegistry.JAR_EMPTY.get(), (Block)IafBlockRegistry.JAR_PIXIE_0.get(), (Block)IafBlockRegistry.JAR_PIXIE_1.get(), (Block)IafBlockRegistry.JAR_PIXIE_2.get(), (Block)IafBlockRegistry.JAR_PIXIE_3.get(), (Block)IafBlockRegistry.JAR_PIXIE_4.get()});
      }, "pixie_jar");
      MYRMEX_COCOON = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityMyrmexCocoon::new, new Block[]{(Block)IafBlockRegistry.DESERT_MYRMEX_COCOON.get(), (Block)IafBlockRegistry.JUNGLE_MYRMEX_COCOON.get()});
      }, "myrmex_cocoon");
      DRAGONFORGE_CORE = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityDragonforge::new, new Block[]{(Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get(), (Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE.get(), (Block)IafBlockRegistry.DRAGONFORGE_FIRE_CORE_DISABLED.get(), (Block)IafBlockRegistry.DRAGONFORGE_ICE_CORE_DISABLED.get(), (Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE.get(), (Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE_DISABLED.get()});
      }, "dragonforge_core");
      DRAGONFORGE_BRICK = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityDragonforgeBrick::new, new Block[]{(Block)IafBlockRegistry.DRAGONFORGE_FIRE_BRICK.get(), (Block)IafBlockRegistry.DRAGONFORGE_ICE_BRICK.get(), (Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_BRICK.get()});
      }, "dragonforge_brick");
      DRAGONFORGE_INPUT = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityDragonforgeInput::new, new Block[]{(Block)IafBlockRegistry.DRAGONFORGE_FIRE_INPUT.get(), (Block)IafBlockRegistry.DRAGONFORGE_ICE_INPUT.get(), (Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_INPUT.get()});
      }, "dragonforge_input");
      DREAD_PORTAL = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityDreadPortal::new, new Block[]{(Block)IafBlockRegistry.DREAD_PORTAL.get()});
      }, "dread_portal");
      DREAD_SPAWNER = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityDreadSpawner::new, new Block[]{(Block)IafBlockRegistry.DREAD_SPAWNER.get()});
      }, "dread_spawner");
      GHOST_CHEST = registerTileEntity(() -> {
         return Builder.m_155273_(TileEntityGhostChest::new, new Block[]{(Block)IafBlockRegistry.GHOST_CHEST.get()});
      }, "ghost_chest");
   }
}
