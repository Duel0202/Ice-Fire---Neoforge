package com.github.alexthe666.iceandfire.world.feature;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class SpawnDragonSkeleton extends Feature<NoneFeatureConfiguration> {
   protected EntityType<? extends EntityDragonBase> dragonType;

   public SpawnDragonSkeleton(EntityType<? extends EntityDragonBase> dt, Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
      this.dragonType = dt;
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      position = worldIn.m_5452_(Types.WORLD_SURFACE_WG, position.m_7918_(8, 0, 8));
      if (IafConfig.generateDragonSkeletons && rand.m_188503_(IafConfig.generateDragonSkeletonChance + 1) == 0) {
         EntityDragonBase dragon = (EntityDragonBase)this.dragonType.m_20615_(worldIn.m_6018_());
         dragon.m_6034_((double)((float)position.m_123341_() + 0.5F), (double)(position.m_123342_() + 1), (double)((float)position.m_123343_() + 0.5F));
         int dragonage = 10 + rand.m_188503_(100);
         dragon.growDragon(dragonage);
         dragon.modelDeadProgress = 20.0F;
         dragon.setModelDead(true);
         dragon.setDeathStage(dragonage / 5 / 2);
         dragon.m_146922_((float)rand.m_188503_(360));
         worldIn.m_7967_(dragon);
      }

      return true;
   }
}
