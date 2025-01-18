package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockGoldPile;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.util.WorldUtil;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public abstract class WorldGenDragonRoosts extends Feature<NoneFeatureConfiguration> implements TypedFeature {
   protected static final Direction[] HORIZONTALS;
   protected final Block treasureBlock;

   public WorldGenDragonRoosts(Codec<NoneFeatureConfiguration> configuration, Block treasureBlock) {
      super(configuration);
      this.treasureBlock = treasureBlock;
   }

   public String getId() {
      return "dragon_roost";
   }

   public IafWorldData.FeatureType getFeatureType() {
      return IafWorldData.FeatureType.SURFACE;
   }

   public boolean m_142674_(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context) {
      if (!WorldUtil.canGenerate(IafConfig.generateDragonRoostChance, context.m_159774_(), context.m_225041_(), context.m_159777_(), this.getId(), true)) {
         return false;
      } else {
         boolean isMale = (new Random()).nextBoolean();
         int radius = 12 + context.m_225041_().m_188503_(8);
         this.spawnDragon(context, radius, isMale);
         this.generateSurface(context, radius);
         this.generateShell(context, radius);
         radius -= 2;
         this.hollowOut(context, radius);
         radius += 15;
         this.generateDecoration(context, radius, isMale);
         return true;
      }
   }

   protected void generateRoostPile(WorldGenLevel level, RandomSource random, BlockPos position, Block block) {
      int radius = random.m_188503_(4);

      for(int i = 0; i < radius; ++i) {
         int layeredRadius = radius - i;
         double circularArea = this.getCircularArea(radius);
         BlockPos up = position.m_6630_(i);
         Iterator var11 = ((Set)BlockPos.m_121990_(up.m_7918_(-layeredRadius, 0, -layeredRadius), up.m_7918_(layeredRadius, 0, layeredRadius)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

         while(var11.hasNext()) {
            BlockPos blockpos = (BlockPos)var11.next();
            if (blockpos.m_123331_(position) <= circularArea) {
               level.m_7731_(blockpos, block.m_49966_(), 2);
            }
         }
      }

   }

   protected double getCircularArea(int radius, int height) {
      double area = (double)((float)(radius + height + radius) * 0.333F + 0.5F);
      return (double)Mth.m_14107_(area * area);
   }

   protected double getCircularArea(int radius) {
      double area = (double)((float)(radius + radius) * 0.333F + 0.5F);
      return (double)Mth.m_14107_(area * area);
   }

   protected BlockPos getSurfacePosition(WorldGenLevel level, BlockPos position) {
      return level.m_5452_(Types.WORLD_SURFACE_WG, position);
   }

   protected BlockState transform(Block block) {
      return this.transform(block.m_49966_());
   }

   private void generateDecoration(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context, int radius, boolean isMale) {
      int height = radius / 5;
      double circularArea = this.getCircularArea(radius, height);
      BlockPos.m_121990_(context.m_159777_().m_7918_(-radius, -height, -radius), context.m_159777_().m_7918_(radius, height, radius)).map(BlockPos::m_7949_).forEach((position) -> {
         if (position.m_123331_(context.m_159777_()) <= circularArea) {
            double distance = position.m_123331_(context.m_159777_()) / circularArea;
            if (!context.m_159774_().m_46859_(context.m_159777_()) && context.m_225041_().m_188500_() > distance * 0.5D) {
               BlockState state = context.m_159774_().m_8055_(position);
               if (!(state.m_60734_() instanceof BaseEntityBlock) && state.m_60800_(context.m_159774_(), position) >= 0.0F) {
                  BlockState transformed = this.transform(state);
                  if (transformed != state) {
                     context.m_159774_().m_7731_(position, transformed, 2);
                  }
               }
            }

            this.handleCustomGeneration(context, position, distance);
            if (distance > 0.5D && context.m_225041_().m_188503_(1000) == 0) {
               (new WorldGenRoostBoulder(this.transform(Blocks.f_50652_).m_60734_(), context.m_225041_().m_188503_(3), true)).generate(context.m_159774_(), context.m_225041_(), this.getSurfacePosition(context.m_159774_(), position));
            }

            if (distance < 0.3D && context.m_225041_().m_188503_(isMale ? 200 : 300) == 0) {
               this.generateTreasurePile(context.m_159774_(), context.m_225041_(), position);
            }

            if (distance < 0.3D && context.m_225041_().m_188503_(isMale ? 500 : 700) == 0) {
               BlockPos surfacePosition = context.m_159774_().m_5452_(Types.WORLD_SURFACE, position);
               boolean wasPlaced = context.m_159774_().m_7731_(surfacePosition, (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, HORIZONTALS[(new Random()).nextInt(3)]), 2);
               if (wasPlaced) {
                  BlockEntity blockEntity = context.m_159774_().m_7702_(surfacePosition);
                  if (blockEntity instanceof ChestBlockEntity) {
                     ChestBlockEntity chest = (ChestBlockEntity)blockEntity;
                     chest.m_59626_(this.getRoostLootTable(), context.m_225041_().m_188505_());
                  }
               }
            }

            if (context.m_225041_().m_188503_(5000) == 0) {
               (new WorldGenRoostArch(this.transform(Blocks.f_50652_).m_60734_())).generate(context.m_159774_(), context.m_225041_(), this.getSurfacePosition(context.m_159774_(), position));
            }
         }

      });
   }

   private void hollowOut(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context, int radius) {
      int height = 2;
      double circularArea = this.getCircularArea(radius, height);
      BlockPos up = context.m_159777_().m_6630_(height - 1);
      BlockPos.m_121990_(up.m_7918_(-radius, 0, -radius), up.m_7918_(radius, height, radius)).map(BlockPos::m_7949_).forEach((position) -> {
         if (position.m_123331_(context.m_159777_()) <= circularArea) {
            context.m_159774_().m_7731_(position, Blocks.f_50016_.m_49966_(), 2);
         }

      });
   }

   private void generateShell(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context, int radius) {
      int height = radius / 5;
      double circularArea = this.getCircularArea(radius, height);
      BlockPos.m_121990_(context.m_159777_().m_7918_(-radius, -height, -radius), context.m_159777_().m_7918_(radius, 1, radius)).map(BlockPos::m_7949_).forEach((position) -> {
         if (position.m_123331_(context.m_159777_()) < circularArea) {
            context.m_159774_().m_7731_(position, context.m_225041_().m_188499_() ? this.transform(Blocks.f_49994_) : this.transform(Blocks.f_50493_), 2);
         } else if (position.m_123331_(context.m_159777_()) == circularArea) {
            context.m_159774_().m_7731_(position, this.transform(Blocks.f_50652_), 2);
         }

      });
   }

   private void generateSurface(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context, int radius) {
      int height = 2;
      double circularArea = this.getCircularArea(radius, height);
      BlockPos.m_121990_(context.m_159777_().m_7918_(-radius, height, -radius), context.m_159777_().m_7918_(radius, 0, radius)).map(BlockPos::m_7949_).forEach((position) -> {
         int heightDifference = position.m_123342_() - context.m_159777_().m_123342_();
         if (position.m_123331_(context.m_159777_()) <= circularArea && heightDifference < 2 + context.m_225041_().m_188503_(height) && !context.m_159774_().m_46859_(position.m_7495_())) {
            if (context.m_159774_().m_46859_(position.m_7494_())) {
               context.m_159774_().m_7731_(position, this.transform(Blocks.f_50034_), 2);
            } else {
               context.m_159774_().m_7731_(position, this.transform(Blocks.f_50493_), 2);
            }
         }

      });
   }

   private void generateTreasurePile(WorldGenLevel level, RandomSource random, BlockPos origin) {
      int layers = random.m_188503_(3);

      for(int i = 0; i < layers; ++i) {
         int radius = layers - i;
         double circularArea = this.getCircularArea(radius);
         Iterator var9 = ((Set)BlockPos.m_121990_(origin.m_7918_(-radius, i, -radius), origin.m_7918_(radius, i, radius)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

         while(var9.hasNext()) {
            BlockPos position = (BlockPos)var9.next();
            if (position.m_123331_(origin) <= circularArea) {
               position = level.m_5452_(Types.WORLD_SURFACE, position);
               if (this.treasureBlock instanceof BlockGoldPile) {
                  BlockState state = level.m_8055_(position);
                  boolean placed = false;
                  if (state.m_60795_()) {
                     level.m_7731_(position, (BlockState)this.treasureBlock.m_49966_().m_61124_(BlockGoldPile.LAYERS, 1 + random.m_188503_(7)), 2);
                     placed = true;
                  } else if (state.m_60734_() instanceof SnowLayerBlock) {
                     level.m_7731_(position.m_7495_(), (BlockState)this.treasureBlock.m_49966_().m_61124_(BlockGoldPile.LAYERS, (Integer)state.m_61143_(SnowLayerBlock.f_56581_)), 2);
                     placed = true;
                  }

                  if (placed && level.m_8055_(position.m_7495_()).m_60734_() instanceof BlockGoldPile) {
                     level.m_7731_(position.m_7495_(), (BlockState)this.treasureBlock.m_49966_().m_61124_(BlockGoldPile.LAYERS, 8), 2);
                  }
               }
            }
         }
      }

   }

   private void spawnDragon(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context, int ageOffset, boolean isMale) {
      EntityDragonBase dragon = (EntityDragonBase)this.getDragonType().m_20615_(context.m_159774_().m_6018_());
      dragon.setGender(isMale);
      dragon.growDragon(40 + ageOffset);
      dragon.setAgingDisabled(true);
      dragon.m_21153_(dragon.m_21233_());
      dragon.setVariant((new Random()).nextInt(4));
      dragon.m_19890_((double)context.m_159777_().m_123341_() + 0.5D, (double)context.m_159774_().m_5452_(Types.WORLD_SURFACE_WG, context.m_159777_()).m_123342_() + 1.5D, (double)context.m_159777_().m_123343_() + 0.5D, context.m_225041_().m_188501_() * 360.0F, 0.0F);
      dragon.homePos = new HomePosition(context.m_159777_(), context.m_159774_().m_6018_());
      dragon.hasHomePosition = true;
      dragon.setHunger(50);
      context.m_159774_().m_7967_(dragon);
   }

   protected abstract EntityType<? extends EntityDragonBase> getDragonType();

   protected abstract ResourceLocation getRoostLootTable();

   protected abstract BlockState transform(BlockState var1);

   protected abstract void handleCustomGeneration(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> var1, BlockPos var2, double var3);

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
