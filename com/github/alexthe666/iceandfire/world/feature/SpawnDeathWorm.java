package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityDeathWorm;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SpawnDeathWorm extends Feature<NoneFeatureConfiguration> {
   public SpawnDeathWorm(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      position = worldIn.m_5452_(Types.WORLD_SURFACE_WG, position.m_7918_(8, 0, 8));
      if (IafWorldRegistry.isFarEnoughFromSpawn(worldIn, position) && rand.m_188503_(IafConfig.deathWormSpawnRate + 1) == 0) {
         EntityDeathWorm deathWorm = (EntityDeathWorm)((EntityType)IafEntityRegistry.DEATH_WORM.get()).m_20615_(worldIn.m_6018_());
         deathWorm.m_6034_((double)((float)position.m_123341_() + 0.5F), (double)(position.m_123342_() + 1), (double)((float)position.m_123343_() + 0.5F));
         deathWorm.m_6518_(worldIn, worldIn.m_6436_(position), MobSpawnType.CHUNK_GENERATION, (SpawnGroupData)null, (CompoundTag)null);
         worldIn.m_7967_(deathWorm);
      }

      return true;
   }
}
