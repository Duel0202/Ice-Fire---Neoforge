package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockPixieHouse;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityPixie;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldGenPixieVillage extends Feature<NoneFeatureConfiguration> implements TypedFeature {
   public WorldGenPixieVillage(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      if (rand.m_188503_(IafConfig.spawnPixiesChance) == 0 && IafWorldRegistry.isFarEnoughFromSpawn(worldIn, position)) {
         int maxRoads = IafConfig.pixieVillageSize + rand.m_188503_(5);
         BlockPos buildPosition = position;

         for(int placedRoads = 0; placedRoads < maxRoads; ++placedRoads) {
            int roadLength = 10 + rand.m_188503_(15);
            Direction buildingDirection = Direction.m_122407_(rand.m_188503_(3));

            for(int i = 0; i < roadLength; ++i) {
               BlockPos buildPosition2 = buildPosition.m_5484_(buildingDirection, i);
               buildPosition2 = worldIn.m_5452_(Types.WORLD_SURFACE_WG, buildPosition2).m_7495_();
               if (worldIn.m_8055_(buildPosition2).m_60819_().m_76178_()) {
                  worldIn.m_7731_(buildPosition2, Blocks.f_152481_.m_49966_(), 2);
               } else {
                  worldIn.m_7731_(buildPosition2, Blocks.f_50741_.m_49966_(), 2);
               }

               if (rand.m_188503_(8) == 0) {
                  Direction houseDir = rand.m_188499_() ? buildingDirection.m_122427_() : buildingDirection.m_122428_();
                  BlockState houseState = ((Block)IafBlockRegistry.PIXIE_HOUSE_OAK.get()).m_49966_();
                  int houseColor = rand.m_188503_(5);
                  BlockState var10000;
                  switch(houseColor) {
                  case 0:
                     var10000 = (BlockState)((Block)IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_RED.get()).m_49966_().m_61124_(BlockPixieHouse.FACING, houseDir.m_122424_());
                     break;
                  case 1:
                     var10000 = (BlockState)((Block)IafBlockRegistry.PIXIE_HOUSE_MUSHROOM_BROWN.get()).m_49966_().m_61124_(BlockPixieHouse.FACING, houseDir.m_122424_());
                     break;
                  case 2:
                     var10000 = (BlockState)((Block)IafBlockRegistry.PIXIE_HOUSE_OAK.get()).m_49966_().m_61124_(BlockPixieHouse.FACING, houseDir.m_122424_());
                     break;
                  case 3:
                     var10000 = (BlockState)((Block)IafBlockRegistry.PIXIE_HOUSE_BIRCH.get()).m_49966_().m_61124_(BlockPixieHouse.FACING, houseDir.m_122424_());
                     break;
                  case 4:
                     var10000 = (BlockState)((Block)IafBlockRegistry.PIXIE_HOUSE_SPRUCE.get()).m_49966_().m_61124_(BlockPixieHouse.FACING, houseDir.m_122424_());
                     break;
                  case 5:
                     var10000 = (BlockState)((Block)IafBlockRegistry.PIXIE_HOUSE_DARK_OAK.get()).m_49966_().m_61124_(BlockPixieHouse.FACING, houseDir.m_122424_());
                     break;
                  default:
                     var10000 = houseState;
                  }

                  houseState = var10000;
                  EntityPixie pixie = (EntityPixie)((EntityType)IafEntityRegistry.PIXIE.get()).m_20615_(worldIn.m_6018_());
                  pixie.m_6518_(worldIn, worldIn.m_6436_(buildPosition2.m_7494_()), MobSpawnType.SPAWNER, (SpawnGroupData)null, (CompoundTag)null);
                  pixie.m_6034_((double)buildPosition2.m_123341_(), (double)(buildPosition2.m_123342_() + 2), (double)buildPosition2.m_123343_());
                  pixie.m_21530_();
                  worldIn.m_7967_(pixie);
                  worldIn.m_7731_(buildPosition2.m_121945_(houseDir).m_7494_(), houseState, 2);
                  if (!worldIn.m_8055_(buildPosition2.m_121945_(houseDir)).m_60815_()) {
                     worldIn.m_7731_(buildPosition2.m_121945_(houseDir), Blocks.f_50546_.m_49966_(), 2);
                     worldIn.m_7731_(buildPosition2.m_121945_(houseDir).m_7495_(), Blocks.f_50546_.m_49966_(), 2);
                  }
               }
            }

            buildPosition = buildPosition.m_5484_(buildingDirection, rand.m_188503_(roadLength));
         }

         return true;
      } else {
         return false;
      }
   }

   public IafWorldData.FeatureType getFeatureType() {
      return IafWorldData.FeatureType.SURFACE;
   }

   public String getId() {
      return "pixie_village";
   }
}
