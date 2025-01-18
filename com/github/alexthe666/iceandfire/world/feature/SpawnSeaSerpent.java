package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;

public class SpawnSeaSerpent extends Feature<NoneFeatureConfiguration> {
   public SpawnSeaSerpent(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      position = worldIn.m_5452_(Types.WORLD_SURFACE_WG, position.m_7918_(8, 0, 8));
      BlockPos oceanPos = worldIn.m_5452_(Types.OCEAN_FLOOR_WG, position.m_7918_(8, 0, 8));
      if (IafWorldRegistry.isFarEnoughFromSpawn(worldIn, position) && rand.m_188503_(IafConfig.seaSerpentSpawnChance + 1) == 0) {
         BlockPos pos = oceanPos.m_7918_(rand.m_188503_(10) - 5, rand.m_188503_(30), rand.m_188503_(10) - 5);
         if (worldIn.m_6425_(pos).m_76152_() == Fluids.f_76193_) {
            EntitySeaSerpent serpent = (EntitySeaSerpent)((EntityType)IafEntityRegistry.SEA_SERPENT.get()).m_20615_(worldIn.m_6018_());
            serpent.onWorldSpawn(rand);
            serpent.m_7678_((double)((float)pos.m_123341_() + 0.5F), (double)((float)pos.m_123342_() + 0.5F), (double)((float)pos.m_123343_() + 0.5F), 0.0F, 0.0F);
            worldIn.m_7967_(serpent);
         }
      }

      return true;
   }
}
