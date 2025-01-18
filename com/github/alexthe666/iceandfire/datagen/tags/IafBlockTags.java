package com.github.alexthe666.iceandfire.datagen.tags;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforge.common.data.BlockTagsProvider;
import net.neoforge.common.data.ExistingFileHelper;

public class IafBlockTags extends BlockTagsProvider {
   public static TagKey<Block> CHARRED_BLOCKS = createKey("charred_blocks");
   public static TagKey<Block> FROZEN_BLOCKS = createKey("frozen_blocks");
   public static TagKey<Block> CRACKLED_BLOCKS = createKey("crackled_blocks");
   public static TagKey<Block> DRAGON_ENVIRONMENT_BLOCKS = createKey("dragon_environment_blocks");
   public static TagKey<Block> DRAGON_CAVE_RARE_ORES = createKey("dragon_cave_rare_ores");
   public static TagKey<Block> DRAGON_CAVE_UNCOMMON_ORES = createKey("dragon_cave_uncommon_ores");
   public static TagKey<Block> DRAGON_CAVE_COMMON_ORES = createKey("dragon_cave_common_ores");
   public static TagKey<Block> FIRE_DRAGON_CAVE_ORES = createKey("fire_dragon_cave_ores");
   public static TagKey<Block> ICE_DRAGON_CAVE_ORES = createKey("ice_dragon_cave_ores");
   public static TagKey<Block> LIGHTNING_DRAGON_CAVE_ORES = createKey("lightning_dragon_cave_ores");
   public static TagKey<Block> DRAGON_BLOCK_BREAK_BLACKLIST = createKey("dragon_block_break_blacklist");
   public static TagKey<Block> DRAGON_BLOCK_BREAK_NO_DROPS = createKey("dragon_block_break_no_drops");

   public IafBlockTags(PackOutput output, CompletableFuture<Provider> future, ExistingFileHelper helper) {
      super(output, future, "iceandfire", helper);
   }

   protected void m_6577_(Provider pProvider) {
      this.m_206424_(CHARRED_BLOCKS).m_255245_((Block)IafBlockRegistry.CHARRED_COBBLESTONE.get()).m_255245_((Block)IafBlockRegistry.CHARRED_DIRT.get()).m_255245_((Block)IafBlockRegistry.CHARRED_DIRT_PATH.get()).m_255245_((Block)IafBlockRegistry.CHARRED_GRASS.get()).m_255245_((Block)IafBlockRegistry.CHARRED_GRAVEL.get()).m_255245_((Block)IafBlockRegistry.CHARRED_STONE.get());
      this.m_206424_(FROZEN_BLOCKS).m_255245_((Block)IafBlockRegistry.FROZEN_COBBLESTONE.get()).m_255245_((Block)IafBlockRegistry.FROZEN_DIRT.get()).m_255245_((Block)IafBlockRegistry.FROZEN_DIRT_PATH.get()).m_255245_((Block)IafBlockRegistry.FROZEN_GRASS.get()).m_255245_((Block)IafBlockRegistry.FROZEN_GRAVEL.get()).m_255245_((Block)IafBlockRegistry.FROZEN_STONE.get()).m_255245_((Block)IafBlockRegistry.FROZEN_SPLINTERS.get());
      this.m_206424_(CRACKLED_BLOCKS).m_255245_((Block)IafBlockRegistry.CRACKLED_COBBLESTONE.get()).m_255245_((Block)IafBlockRegistry.CRACKLED_DIRT.get()).m_255245_((Block)IafBlockRegistry.CRACKLED_DIRT_PATH.get()).m_255245_((Block)IafBlockRegistry.CRACKLED_GRASS.get()).m_255245_((Block)IafBlockRegistry.CRACKLED_GRASS.get()).m_255245_((Block)IafBlockRegistry.CRACKLED_STONE.get());
      this.m_206424_(DRAGON_ENVIRONMENT_BLOCKS).m_206428_(CHARRED_BLOCKS).m_206428_(FROZEN_BLOCKS).m_206428_(CRACKLED_BLOCKS);
      this.m_206424_(DRAGON_CAVE_RARE_ORES).m_255245_(Blocks.f_50089_).m_255245_(Blocks.f_50264_);
      this.m_206424_(DRAGON_CAVE_UNCOMMON_ORES).m_255245_(Blocks.f_50059_).m_255245_(Blocks.f_50173_).m_255245_(Blocks.f_49995_).m_255245_((Block)IafBlockRegistry.SILVER_ORE.get());
      this.m_206424_(DRAGON_CAVE_COMMON_ORES).m_255245_(Blocks.f_49997_).m_255245_(Blocks.f_152505_).m_255245_(Blocks.f_49996_);
      this.m_206424_(FIRE_DRAGON_CAVE_ORES).m_255245_(Blocks.f_50264_);
      this.m_206424_(ICE_DRAGON_CAVE_ORES).m_255245_((Block)IafBlockRegistry.SAPPHIRE_ORE.get());
      this.m_206424_(LIGHTNING_DRAGON_CAVE_ORES).m_255245_(Blocks.f_152491_);
      this.m_206424_(DRAGON_BLOCK_BREAK_BLACKLIST).m_206428_(net.neoforge.common.Tags.Blocks.CHESTS).m_255245_(Blocks.f_50259_).m_255245_(Blocks.f_50183_);
      this.m_206424_(DRAGON_BLOCK_BREAK_NO_DROPS).m_206428_(BlockTags.f_144274_).m_206428_(net.neoforge.common.Tags.Blocks.STONE).m_206428_(net.neoforge.common.Tags.Blocks.COBBLESTONE).m_206428_(DRAGON_ENVIRONMENT_BLOCKS).m_255245_(Blocks.f_50440_);
      this.m_206424_(BlockTags.f_144286_).m_255245_((Block)IafBlockRegistry.SILVER_ORE.get()).m_255245_((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get()).m_255245_((Block)IafBlockRegistry.SILVER_BLOCK.get()).m_255245_((Block)IafBlockRegistry.RAW_SILVER_BLOCK.get());
      this.m_206424_(BlockTags.f_144285_).m_255245_((Block)IafBlockRegistry.SAPPHIRE_ORE.get()).m_255245_((Block)IafBlockRegistry.SAPPHIRE_BLOCK.get());
      this.m_206424_(net.neoforge.common.Tags.Blocks.ORES).m_255245_((Block)IafBlockRegistry.SILVER_ORE.get()).m_255245_((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get()).m_255245_((Block)IafBlockRegistry.SAPPHIRE_ORE.get());
      this.m_206424_(net.neoforge.common.Tags.Blocks.ORES_IN_GROUND_STONE).m_255245_((Block)IafBlockRegistry.SILVER_ORE.get()).m_255245_((Block)IafBlockRegistry.SAPPHIRE_ORE.get());
      this.m_206424_(net.neoforge.common.Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).m_255245_((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get());
      this.m_206424_(TagKey.m_203882_(Registries.f_256747_, new ResourceLocation("forge", "ores/silver"))).m_255245_((Block)IafBlockRegistry.SILVER_ORE.get());
      this.m_206424_(TagKey.m_203882_(Registries.f_256747_, new ResourceLocation("forge", "ores/silver"))).m_255245_((Block)IafBlockRegistry.DEEPSLATE_SILVER_ORE.get());
   }

   private static TagKey<Block> createKey(String name) {
      return TagKey.m_203882_(Registries.f_256747_, new ResourceLocation("iceandfire", name));
   }

   public String m_6055_() {
      return "Ice and Fire Block Tags";
   }
}
