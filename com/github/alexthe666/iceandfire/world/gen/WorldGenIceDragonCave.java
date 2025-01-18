package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldGenIceDragonCave extends WorldGenDragonCave {
   public static ResourceLocation ICE_DRAGON_CHEST = new ResourceLocation("iceandfire", "chest/ice_dragon_female_cave");
   public static ResourceLocation ICE_DRAGON_CHEST_MALE = new ResourceLocation("iceandfire", "chest/ice_dragon_male_cave");

   public WorldGenIceDragonCave(Codec<NoneFeatureConfiguration> configuration) {
      super(configuration);
      this.DRAGON_CHEST = ICE_DRAGON_CHEST;
      this.DRAGON_MALE_CHEST = ICE_DRAGON_CHEST_MALE;
      this.CEILING_DECO = new WorldGenCaveStalactites((Block)IafBlockRegistry.FROZEN_STONE.get(), 3);
      this.PALETTE_BLOCK1 = ((Block)IafBlockRegistry.FROZEN_STONE.get()).m_49966_();
      this.PALETTE_BLOCK2 = ((Block)IafBlockRegistry.FROZEN_COBBLESTONE.get()).m_49966_();
      this.TREASURE_PILE = ((Block)IafBlockRegistry.SILVER_PILE.get()).m_49966_();
      this.dragonTypeOreTag = IafBlockTags.ICE_DRAGON_CAVE_ORES;
   }

   public EntityType<? extends EntityDragonBase> getDragonType() {
      return (EntityType)IafEntityRegistry.ICE_DRAGON.get();
   }
}
