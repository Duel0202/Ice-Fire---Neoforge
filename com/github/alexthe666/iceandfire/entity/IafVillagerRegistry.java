package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.IafProcessorLists;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool.Projection;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.RegistryObject;

@EventBusSubscriber(
   modid = "iceandfire",
   bus = Bus.MOD
)
public class IafVillagerRegistry {
   public static final DeferredRegister<PoiType> POI_TYPES;
   public static final RegistryObject<PoiType> SCRIBE_POI;
   public static final DeferredRegister<VillagerProfession> PROFESSIONS;
   public static final RegistryObject<VillagerProfession> SCRIBE;

   public static void addScribeTrades(Int2ObjectMap<List<ItemListing>> trades) {
      float emeraldForItemsMultiplier = 0.05F;
      float itemForEmeraldMultiplier = 0.05F;
      float rareItemForEmeraldMultiplier = 0.2F;
      ((List)trades.get(1)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 1), new ItemStack((ItemLike)IafItemRegistry.MANUSCRIPT.get(), 4), 25, 2, 0.05F);
      });
      ((List)trades.get(1)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_41997_, 3), new ItemStack(Items.f_42616_, 1), 8, 3, 0.05F);
      });
      ((List)trades.get(1)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42516_, 15), new ItemStack(Items.f_42616_, 2), 4, 4, 0.05F);
      });
      ((List)trades.get(1)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafBlockRegistry.ASH.get(), 10), new ItemStack(Items.f_42616_, 1), 8, 4, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.SILVER_INGOT.get(), 5), new ItemStack(Items.f_42616_, 1), 3, 5, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafBlockRegistry.FIRE_LILY.get(), 8), new ItemStack(Items.f_42616_, 1), 3, 5, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafBlockRegistry.LIGHTNING_LILY.get(), 7), new ItemStack(Items.f_42616_, 3), 2, 5, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 3), new ItemStack((ItemLike)IafBlockRegistry.FROST_LILY.get(), 4), 3, 3, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 2), new ItemStack((ItemLike)IafBlockRegistry.DRAGON_ICE_SPIKES.get(), 7), 2, 3, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.SAPPHIRE_GEM.get()), new ItemStack(Items.f_42616_, 2), 30, 3, 0.2F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 2), new ItemStack((ItemLike)IafBlockRegistry.JAR_EMPTY.get(), 1), 3, 4, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 2), new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get(), 1), 40, 2, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 2), new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get(), 1), 40, 2, 0.05F);
      });
      ((List)trades.get(2)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_151049_), new ItemStack(Items.f_42616_, 3), 20, 3, 0.2F);
      });
      ((List)trades.get(3)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get(), 6), new ItemStack(Items.f_42616_, 1), 7, 4, 0.05F);
      });
      ((List)trades.get(3)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.CHAIN.get(), 2), new ItemStack(Items.f_42616_, 3), 4, 2, 0.05F);
      });
      ((List)trades.get(3)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 6), new ItemStack((ItemLike)IafItemRegistry.PIXIE_DUST.get(), 2), 8, 3, 0.05F);
      });
      ((List)trades.get(3)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 6), new ItemStack((ItemLike)IafItemRegistry.FIRE_DRAGON_FLESH.get(), 2), 8, 3, 0.05F);
      });
      ((List)trades.get(3)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 7), new ItemStack((ItemLike)IafItemRegistry.ICE_DRAGON_FLESH.get(), 1), 8, 3, 0.05F);
      });
      ((List)trades.get(3)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 8), new ItemStack((ItemLike)IafItemRegistry.LIGHTNING_DRAGON_FLESH.get(), 1), 8, 3, 0.05F);
      });
      ((List)trades.get(4)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 10), new ItemStack((ItemLike)IafItemRegistry.DRAGON_BONE.get(), 2), 20, 5, 0.05F);
      });
      ((List)trades.get(4)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 4), new ItemStack((ItemLike)IafItemRegistry.SHINY_SCALES.get(), 1), 5, 2, 0.05F);
      });
      ((List)trades.get(4)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.DREAD_SHARD.get(), 5), new ItemStack(Items.f_42616_, 1), 10, 4, 0.05F);
      });
      ((List)trades.get(4)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 8), new ItemStack((ItemLike)IafItemRegistry.STYMPHALIAN_BIRD_FEATHER.get(), 12), 3, 6, 0.05F);
      });
      ((List)trades.get(4)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 4), new ItemStack((ItemLike)IafItemRegistry.TROLL_TUSK.get(), 12), 7, 3, 0.05F);
      });
      ((List)trades.get(5)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 15), new ItemStack((ItemLike)IafItemRegistry.SERPENT_FANG.get(), 3), 20, 3, 0.05F);
      });
      ((List)trades.get(5)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack(Items.f_42616_, 12), new ItemStack((ItemLike)IafItemRegistry.HYDRA_FANG.get(), 1), 20, 3, 0.05F);
      });
      ((List)trades.get(5)).add((entity, random) -> {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.ECTOPLASM.get(), 6), new ItemStack(Items.f_42616_, 1), 7, 3, 0.05F);
      });
   }

   public static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry, Registry<StructureProcessorList> processorListRegistry, ResourceLocation poolRL, String nbtPieceRL, int weight) {
      Holder<StructureProcessorList> villageHouseProcessorList = processorListRegistry.m_246971_(IafProcessorLists.HOUSE_PROCESSOR);
      StructureTemplatePool pool = (StructureTemplatePool)templatePoolRegistry.m_7745_(poolRL);
      if (pool != null) {
         SinglePoolElement piece = (SinglePoolElement)SinglePoolElement.m_210512_(nbtPieceRL, villageHouseProcessorList).apply(Projection.RIGID);

         for(int i = 0; i < weight; ++i) {
            pool.f_210560_.add(piece);
         }

         List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList(pool.f_210559_);
         listOfPieceEntries.add(new Pair(piece, weight));
         pool.f_210559_ = listOfPieceEntries;
      }
   }

   static {
      POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, "iceandfire");
      SCRIBE_POI = POI_TYPES.register("scribe", () -> {
         return new PoiType(ImmutableSet.copyOf(((Block)IafBlockRegistry.LECTERN.get()).m_49965_().m_61056_()), 1, 1);
      });
      PROFESSIONS = DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, "iceandfire");
      SCRIBE = PROFESSIONS.register("scribe", () -> {
         return new VillagerProfession("scribe", (entry) -> {
            return ((PoiType)entry.m_203334_()).equals(SCRIBE_POI.get());
         }, (entry) -> {
            return ((PoiType)entry.m_203334_()).equals(SCRIBE_POI.get());
         }, ImmutableSet.of(), ImmutableSet.of(), SoundEvents.f_12571_);
      });
   }
}
