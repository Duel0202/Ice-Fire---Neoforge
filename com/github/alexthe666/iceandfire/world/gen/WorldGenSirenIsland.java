package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntitySiren;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.util.WorldUtil;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldGenSirenIsland extends Feature<NoneFeatureConfiguration> implements TypedFeature {
   private final int MAX_ISLAND_RADIUS = 10;

   public WorldGenSirenIsland(Codec<NoneFeatureConfiguration> configuration) {
      super(configuration);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      if (!WorldUtil.canGenerate(IafConfig.generateSirenChance, context.m_159774_(), context.m_225041_(), context.m_159777_(), this.getId(), false)) {
         return false;
      } else {
         int up = context.m_225041_().m_188503_(4) + 1;
         BlockPos center = context.m_159777_().m_6630_(up);
         int layer = 0;

         float i;
         float j;
         BlockPos stonePos;
         for(int sirens = 1 + context.m_225041_().m_188503_(3); !context.m_159774_().m_8055_(center).m_60815_() && center.m_123342_() >= context.m_159774_().m_141937_(); center = center.m_7495_()) {
            ++layer;

            for(i = 0.0F; i < (float)this.getRadius(layer, up); i += 0.5F) {
               for(j = 0.0F; (double)j < 6.283185307179586D * (double)i + (double)context.m_225041_().m_188503_(2); j += 0.5F) {
                  stonePos = BlockPos.m_274561_(Math.floor((double)((float)center.m_123341_() + Mth.m_14031_(j) * i + (float)context.m_225041_().m_188503_(2))), (double)center.m_123342_(), Math.floor((double)((float)center.m_123343_() + Mth.m_14089_(j) * i + (float)context.m_225041_().m_188503_(2))));
                  context.m_159774_().m_7731_(stonePos, this.getStone(context.m_225041_()), 3);
                  BlockPos upPos = stonePos.m_7494_();
                  if (context.m_159774_().m_46859_(upPos) && context.m_159774_().m_46859_(upPos.m_122029_()) && context.m_159774_().m_46859_(upPos.m_122012_()) && context.m_159774_().m_46859_(upPos.m_122012_().m_122029_()) && context.m_225041_().m_188503_(3) == 0 && sirens > 0) {
                     this.spawnSiren(context.m_159774_(), context.m_225041_(), upPos.m_122012_().m_122029_());
                     --sirens;
                  }
               }
            }
         }

         ++layer;

         for(i = 0.0F; i < (float)this.getRadius(layer, up); i += 0.5F) {
            for(j = 0.0F; (double)j < 6.283185307179586D * (double)i + (double)context.m_225041_().m_188503_(2); j += 0.5F) {
               for(stonePos = BlockPos.m_274561_(Math.floor((double)((float)center.m_123341_() + Mth.m_14031_(j) * i + (float)context.m_225041_().m_188503_(2))), (double)center.m_123342_(), Math.floor((double)((float)center.m_123343_() + Mth.m_14089_(j) * i + (float)context.m_225041_().m_188503_(2)))); !context.m_159774_().m_8055_(stonePos).m_60815_() && stonePos.m_123342_() >= 0; stonePos = stonePos.m_7495_()) {
                  context.m_159774_().m_7731_(stonePos, this.getStone(context.m_225041_()), 3);
               }
            }
         }

         return true;
      }
   }

   private int getRadius(int layer, int up) {
      return layer > up ? (int)((double)layer * 0.25D) + up : Math.min(layer, 10);
   }

   private BlockState getStone(RandomSource random) {
      int chance = random.m_188503_(100);
      if (chance > 90) {
         return Blocks.f_50079_.m_49966_();
      } else if (chance > 70) {
         return Blocks.f_49994_.m_49966_();
      } else {
         return chance > 45 ? Blocks.f_50652_.m_49966_() : Blocks.f_50069_.m_49966_();
      }
   }

   private void spawnSiren(ServerLevelAccessor worldIn, RandomSource rand, BlockPos position) {
      EntitySiren siren = new EntitySiren((EntityType)IafEntityRegistry.SIREN.get(), worldIn.m_6018_());
      siren.setSinging(true);
      siren.setHairColor(rand.m_188503_(2));
      siren.setSingingPose(rand.m_188503_(2));
      siren.m_19890_((double)position.m_123341_() + 0.5D, (double)(position.m_123342_() + 1), (double)position.m_123343_() + 0.5D, rand.m_188501_() * 360.0F, 0.0F);
      worldIn.m_7967_(siren);
   }

   public IafWorldData.FeatureType getFeatureType() {
      return IafWorldData.FeatureType.OCEAN;
   }

   public String getId() {
      return "siren_island";
   }
}
