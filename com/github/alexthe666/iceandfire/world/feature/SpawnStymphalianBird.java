package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityStymphalianBird;
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

public class SpawnStymphalianBird extends Feature<NoneFeatureConfiguration> {
   public SpawnStymphalianBird(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      position = worldIn.m_5452_(Types.WORLD_SURFACE_WG, position.m_7918_(8, 0, 8));
      if (IafWorldRegistry.isFarEnoughFromSpawn(worldIn, position) && rand.m_188503_(IafConfig.stymphalianBirdSpawnChance + 1) == 0) {
         for(int i = 0; i < 4 + rand.m_188503_(4); ++i) {
            BlockPos pos = position.m_7918_(rand.m_188503_(10) - 5, 0, rand.m_188503_(10) - 5);
            pos = worldIn.m_5452_(Types.WORLD_SURFACE_WG, pos);
            if (worldIn.m_8055_(pos.m_7495_()).m_60815_()) {
               EntityStymphalianBird bird = (EntityStymphalianBird)((EntityType)IafEntityRegistry.STYMPHALIAN_BIRD.get()).m_20615_(worldIn.m_6018_());
               bird.m_7678_((double)((float)pos.m_123341_() + 0.5F), (double)((float)pos.m_123342_() + 1.5F), (double)((float)pos.m_123343_() + 0.5F), 0.0F, 0.0F);
               worldIn.m_7967_(bird);
            }
         }
      }

      return true;
   }
}
