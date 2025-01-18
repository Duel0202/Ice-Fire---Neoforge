package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.EntityHydra;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SkullBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldGenHydraCave extends Feature<NoneFeatureConfiguration> implements TypedFeature {
   public static final ResourceLocation HYDRA_CHEST = new ResourceLocation("iceandfire", "chest/hydra_cave");
   private static final Direction[] HORIZONTALS;

   public WorldGenHydraCave(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      ChunkGenerator generator = context.m_159775_();
      if (rand.m_188503_(IafConfig.generateHydraChance) == 0 && IafWorldRegistry.isFarEnoughFromSpawn(worldIn, position) && IafWorldRegistry.isFarEnoughFromDangerousGen(worldIn, position, this.getId())) {
         int i1 = 8;
         int i2 = i1 - 2;
         int dist = 6;
         if (!worldIn.m_46859_(position.m_7918_(i1 - dist, -3, -i1 + dist)) && !worldIn.m_46859_(position.m_7918_(i1 - dist, -3, i1 - dist)) && !worldIn.m_46859_(position.m_7918_(-i1 + dist, -3, -i1 + dist)) && !worldIn.m_46859_(position.m_7918_(-i1 + dist, -3, i1 - dist))) {
            int ySize = rand.m_188503_(2);
            int j = i1 + rand.m_188503_(2);
            int k = 5 + ySize;
            int l = i1 + rand.m_188503_(2);
            float f = (float)(j + k + l) * 0.333F + 0.5F;
            Iterator var14 = ((Set)BlockPos.m_121990_(position.m_7918_(-j, -k, -l), position.m_7918_(j, k, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            BlockPos blockpos;
            while(var14.hasNext()) {
               blockpos = (BlockPos)var14.next();
               boolean doorwayX = blockpos.m_123341_() >= position.m_123341_() - 2 + rand.m_188503_(2) && blockpos.m_123341_() <= position.m_123341_() + 2 + rand.m_188503_(2);
               boolean doorwayZ = blockpos.m_123343_() >= position.m_123343_() - 2 + rand.m_188503_(2) && blockpos.m_123343_() <= position.m_123343_() + 2 + rand.m_188503_(2);
               boolean isNotInDoorway = !doorwayX && !doorwayZ && blockpos.m_123342_() > position.m_123342_() || blockpos.m_123342_() > position.m_123342_() + k - (1 + rand.m_188503_(2));
               if (blockpos.m_123331_(position) <= (double)(f * f)) {
                  if (!(worldIn.m_8055_(position).m_60734_() instanceof ChestBlock) && isNotInDoorway) {
                     worldIn.m_7731_(blockpos, Blocks.f_50440_.m_49966_(), 3);
                     if (worldIn.m_8055_(position.m_7495_()).m_60734_() == Blocks.f_50440_) {
                        worldIn.m_7731_(blockpos.m_7495_(), Blocks.f_50493_.m_49966_(), 3);
                     }

                     if (rand.m_188503_(4) == 0) {
                        worldIn.m_7731_(blockpos.m_7494_(), Blocks.f_50034_.m_49966_(), 2);
                     }

                     if (rand.m_188503_(9) == 0) {
                        Holder<ConfiguredFeature<?, ?>> holder = (Holder)context.m_159774_().m_9598_().m_175515_(Registries.f_256911_).m_203636_(TreeFeatures.f_195137_).orElse((Reference)null);
                        if (holder != null) {
                           ((ConfiguredFeature)holder.get()).m_224953_(worldIn, generator, rand, blockpos.m_7494_());
                        }
                     }
                  }

                  if (blockpos.m_123342_() == position.m_123342_()) {
                     worldIn.m_7731_(blockpos, Blocks.f_50440_.m_49966_(), 3);
                  }

                  if (blockpos.m_123342_() <= position.m_123342_() - 1 && !worldIn.m_8055_(blockpos).m_60815_()) {
                     worldIn.m_7731_(blockpos, Blocks.f_50069_.m_49966_(), 3);
                  }
               }
            }

            ySize = rand.m_188503_(2);
            j = i2 + rand.m_188503_(2);
            k = 4 + ySize;
            l = i2 + rand.m_188503_(2);
            f = (float)(j + k + l) * 0.333F + 0.5F;
            var14 = ((Set)BlockPos.m_121990_(position.m_7918_(-j, -k, -l), position.m_7918_(j, k, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            while(var14.hasNext()) {
               blockpos = (BlockPos)var14.next();
               if (blockpos.m_123331_(position) <= (double)(f * f) && blockpos.m_123342_() > position.m_123342_() && !(worldIn.m_8055_(position).m_60734_() instanceof ChestBlock)) {
                  worldIn.m_7731_(blockpos, Blocks.f_50016_.m_49966_(), 3);
               }
            }

            var14 = ((Set)BlockPos.m_121990_(position.m_7918_(-j, -k, -l), position.m_7918_(j, k + 8, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            while(true) {
               while(true) {
                  do {
                     do {
                        if (!var14.hasNext()) {
                           EntityHydra hydra = new EntityHydra((EntityType)IafEntityRegistry.HYDRA.get(), worldIn.m_6018_());
                           hydra.setVariant(rand.m_188503_(3));
                           hydra.m_21446_(position, 15);
                           hydra.m_19890_((double)position.m_123341_() + 0.5D, (double)position.m_123342_() + 1.5D, (double)position.m_123343_() + 0.5D, rand.m_188501_() * 360.0F, 0.0F);
                           worldIn.m_7967_(hydra);
                           return true;
                        }

                        blockpos = (BlockPos)var14.next();
                     } while(!(blockpos.m_123331_(position) <= (double)(f * f)));
                  } while(blockpos.m_123342_() != position.m_123342_());

                  if (rand.m_188503_(30) == 0 && this.isTouchingAir(worldIn, blockpos.m_7494_())) {
                     worldIn.m_7731_(blockpos.m_6630_(1), (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, HORIZONTALS[(new Random()).nextInt(3)]), 2);
                     if (worldIn.m_8055_(blockpos.m_6630_(1)).m_60734_() instanceof ChestBlock) {
                        BlockEntity tileentity1 = worldIn.m_7702_(blockpos.m_6630_(1));
                        if (tileentity1 instanceof ChestBlockEntity) {
                           ((ChestBlockEntity)tileentity1).m_59626_(HYDRA_CHEST, rand.m_188505_());
                        }
                     }
                  } else if (rand.m_188503_(45) == 0 && this.isTouchingAir(worldIn, blockpos.m_7494_())) {
                     worldIn.m_7731_(blockpos.m_7494_(), (BlockState)Blocks.f_50310_.m_49966_().m_61124_(SkullBlock.f_56314_, rand.m_188503_(15)), 2);
                  } else if (rand.m_188503_(35) == 0 && this.isTouchingAir(worldIn, blockpos.m_7494_())) {
                     worldIn.m_7731_(blockpos.m_7494_(), (BlockState)Blocks.f_50050_.m_49966_().m_61124_(LeavesBlock.f_54419_, true), 2);
                     Direction[] var21 = Direction.values();
                     int var23 = var21.length;

                     for(int var24 = 0; var24 < var23; ++var24) {
                        Direction facing = var21[var24];
                        if (rand.m_188501_() < 0.3F && facing != Direction.DOWN) {
                           worldIn.m_7731_(blockpos.m_7494_().m_121945_(facing), Blocks.f_50050_.m_49966_(), 2);
                        }
                     }
                  } else if (rand.m_188503_(15) == 0 && this.isTouchingAir(worldIn, blockpos.m_7494_())) {
                     worldIn.m_7731_(blockpos.m_7494_(), Blocks.f_50359_.m_49966_(), 2);
                  } else if (rand.m_188503_(15) == 0 && this.isTouchingAir(worldIn, blockpos.m_7494_())) {
                     worldIn.m_7731_(blockpos.m_7494_(), rand.m_188499_() ? Blocks.f_50072_.m_49966_() : Blocks.f_50073_.m_49966_(), 2);
                  }
               }
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private boolean isTouchingAir(LevelAccessor worldIn, BlockPos pos) {
      boolean isTouchingAir = true;
      Direction[] var4 = HORIZONTALS;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Direction direction = var4[var6];
         if (!worldIn.m_46859_(pos.m_121945_(direction))) {
            isTouchingAir = false;
         }
      }

      return isTouchingAir;
   }

   public IafWorldData.FeatureType getFeatureType() {
      return IafWorldData.FeatureType.SURFACE;
   }

   public String getId() {
      return "hydra_cave";
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
