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

public class WorldGenLightningDragonCave extends WorldGenDragonCave {
   public static ResourceLocation LIGHTNING_DRAGON_CHEST = new ResourceLocation("iceandfire", "chest/lightning_dragon_female_cave");
   public static ResourceLocation LIGHTNING_DRAGON_CHEST_MALE = new ResourceLocation("iceandfire", "chest/lightning_dragon_male_cave");

   public WorldGenLightningDragonCave(Codec<NoneFeatureConfiguration> configuration) {
      super(configuration);
      this.DRAGON_CHEST = LIGHTNING_DRAGON_CHEST;
      this.DRAGON_MALE_CHEST = LIGHTNING_DRAGON_CHEST_MALE;
      this.CEILING_DECO = new WorldGenCaveStalactites((Block)IafBlockRegistry.CRACKLED_STONE.get(), 6);
      this.PALETTE_BLOCK1 = ((Block)IafBlockRegistry.CRACKLED_STONE.get()).m_49966_();
      this.PALETTE_BLOCK2 = ((Block)IafBlockRegistry.CRACKLED_COBBLESTONE.get()).m_49966_();
      this.TREASURE_PILE = ((Block)IafBlockRegistry.COPPER_PILE.get()).m_49966_();
      this.dragonTypeOreTag = IafBlockTags.LIGHTNING_DRAGON_CAVE_ORES;
   }

   public EntityType<? extends EntityDragonBase> getDragonType() {
      return (EntityType)IafEntityRegistry.LIGHTNING_DRAGON.get();
   }
}
