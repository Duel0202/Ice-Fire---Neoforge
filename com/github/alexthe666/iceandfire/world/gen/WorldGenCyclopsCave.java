package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockGoldPile;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityCyclops;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.util.WorldUtil;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.AbstractChestBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldGenCyclopsCave extends Feature<NoneFeatureConfiguration> implements TypedFeature {
   public static final ResourceLocation CYCLOPS_CHEST = new ResourceLocation("iceandfire", "chest/cyclops_cave");
   private static final Direction[] HORIZONTALS;

   public WorldGenCyclopsCave(Codec<NoneFeatureConfiguration> configuration) {
      super(configuration);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      if (!WorldUtil.canGenerate(IafConfig.spawnCyclopsCaveChance, context.m_159774_(), context.m_225041_(), context.m_159777_(), this.getId(), true)) {
         return false;
      } else {
         int size = 16;
         int distance = 6;
         if (!context.m_159774_().m_46859_(context.m_159777_().m_7918_(size - distance, -3, -size + distance)) && !context.m_159774_().m_46859_(context.m_159777_().m_7918_(size - distance, -3, size - distance)) && !context.m_159774_().m_46859_(context.m_159777_().m_7918_(-size + distance, -3, -size + distance)) && !context.m_159774_().m_46859_(context.m_159777_().m_7918_(-size + distance, -3, size - distance))) {
            generateShell(context, size);
            int innerSize = size - 2;
            int x = innerSize + context.m_225041_().m_188503_(2);
            int y = 10 + context.m_225041_().m_188503_(2);
            int z = innerSize + context.m_225041_().m_188503_(2);
            float radius = (float)(x + y + z) * 0.333F + 0.5F;
            int sheepPenCount = 0;
            Iterator var10 = ((Set)BlockPos.m_121990_(context.m_159777_().m_7918_(-x, -y, -z), context.m_159777_().m_7918_(x, y, z)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            BlockPos position;
            while(var10.hasNext()) {
               position = (BlockPos)var10.next();
               if (position.m_123331_(context.m_159777_()) <= (double)(radius * radius) && position.m_123342_() > context.m_159777_().m_123342_() && !(context.m_159774_().m_8055_(context.m_159777_()).m_60734_() instanceof AbstractChestBlock)) {
                  context.m_159774_().m_7731_(position, Blocks.f_50016_.m_49966_(), 3);
               }
            }

            var10 = ((Set)BlockPos.m_121990_(context.m_159777_().m_7918_(-x, -y, -z), context.m_159777_().m_7918_(x, y, z)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            while(true) {
               do {
                  do {
                     do {
                        do {
                           if (!var10.hasNext()) {
                              EntityCyclops cyclops = (EntityCyclops)((EntityType)IafEntityRegistry.CYCLOPS.get()).m_20615_(context.m_159774_().m_6018_());
                              cyclops.m_19890_((double)context.m_159777_().m_123341_() + 0.5D, (double)context.m_159777_().m_123342_() + 1.5D, (double)context.m_159777_().m_123343_() + 0.5D, context.m_225041_().m_188501_() * 360.0F, 0.0F);
                              context.m_159774_().m_7967_(cyclops);
                              return true;
                           }

                           position = (BlockPos)var10.next();
                        } while(!(position.m_123331_(context.m_159777_()) <= (double)(radius * radius)));
                     } while(position.m_123342_() != context.m_159777_().m_123342_());

                     if (context.m_225041_().m_188503_(130) == 0 && this.isTouchingAir(context.m_159774_(), position.m_7494_())) {
                        this.generateSkeleton(context.m_159774_(), position.m_7494_(), context.m_225041_(), context.m_159777_(), radius);
                     }

                     if (context.m_225041_().m_188503_(130) == 0 && position.m_123331_(context.m_159777_()) <= (double)(radius * radius) * 0.800000011920929D && sheepPenCount < 2) {
                        this.generateSheepPen(context.m_159774_(), position.m_7494_(), context.m_225041_(), context.m_159777_(), radius);
                        ++sheepPenCount;
                     }

                     if (context.m_225041_().m_188503_(80) == 0 && this.isTouchingAir(context.m_159774_(), position.m_7494_())) {
                        context.m_159774_().m_7731_(position.m_7494_(), (BlockState)((Block)IafBlockRegistry.GOLD_PILE.get()).m_49966_().m_61124_(BlockGoldPile.LAYERS, 8), 3);
                        context.m_159774_().m_7731_(position.m_7494_().m_122012_(), (BlockState)((Block)IafBlockRegistry.GOLD_PILE.get()).m_49966_().m_61124_(BlockGoldPile.LAYERS, 1 + (new Random()).nextInt(7)), 3);
                        context.m_159774_().m_7731_(position.m_7494_().m_122019_(), (BlockState)((Block)IafBlockRegistry.GOLD_PILE.get()).m_49966_().m_61124_(BlockGoldPile.LAYERS, 1 + (new Random()).nextInt(7)), 3);
                        context.m_159774_().m_7731_(position.m_7494_().m_122024_(), (BlockState)((Block)IafBlockRegistry.GOLD_PILE.get()).m_49966_().m_61124_(BlockGoldPile.LAYERS, 1 + (new Random()).nextInt(7)), 3);
                        context.m_159774_().m_7731_(position.m_7494_().m_122029_(), (BlockState)((Block)IafBlockRegistry.GOLD_PILE.get()).m_49966_().m_61124_(BlockGoldPile.LAYERS, 1 + (new Random()).nextInt(7)), 3);
                        context.m_159774_().m_7731_(position.m_6630_(2), (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, HORIZONTALS[(new Random()).nextInt(3)]), 2);
                        if (context.m_159774_().m_8055_(position.m_6630_(2)).m_60734_() instanceof AbstractChestBlock) {
                           BlockEntity blockEntity = context.m_159774_().m_7702_(position.m_6630_(2));
                           if (blockEntity instanceof ChestBlockEntity) {
                              ChestBlockEntity chestBlockEntity = (ChestBlockEntity)blockEntity;
                              chestBlockEntity.m_59626_(CYCLOPS_CHEST, context.m_225041_().m_188505_());
                           }
                        }
                     }
                  } while(context.m_225041_().m_188503_(50) != 0);
               } while(!this.isTouchingAir(context.m_159774_(), position.m_7494_()));

               int torchHeight = context.m_225041_().m_188503_(2) + 1;

               for(int fence = 0; fence < torchHeight; ++fence) {
                  context.m_159774_().m_7731_(position.m_6630_(1 + fence), this.getFenceState(context.m_159774_(), position.m_6630_(1 + fence)), 3);
               }

               context.m_159774_().m_7731_(position.m_6630_(1 + torchHeight), Blocks.f_50081_.m_49966_(), 2);
            }
         } else {
            return false;
         }
      }
   }

   private static void generateShell(FeaturePlaceContext<NoneFeatureConfiguration> context, int size) {
      int x = size + context.m_225041_().m_188503_(2);
      int y = 12 + context.m_225041_().m_188503_(2);
      int z = size + context.m_225041_().m_188503_(2);
      float radius = (float)(x + y + z) * 0.333F + 0.5F;
      Iterator var6 = ((Set)BlockPos.m_121990_(context.m_159777_().m_7918_(-x, -y, -z), context.m_159777_().m_7918_(x, y, z)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

      while(var6.hasNext()) {
         BlockPos position = (BlockPos)var6.next();
         boolean doorwayX = position.m_123341_() >= context.m_159777_().m_123341_() - 2 + context.m_225041_().m_188503_(2) && position.m_123341_() <= context.m_159777_().m_123341_() + 2 + context.m_225041_().m_188503_(2);
         boolean doorwayZ = position.m_123343_() >= context.m_159777_().m_123343_() - 2 + context.m_225041_().m_188503_(2) && position.m_123343_() <= context.m_159777_().m_123343_() + 2 + context.m_225041_().m_188503_(2);
         boolean isNotInDoorway = !doorwayX && !doorwayZ && position.m_123342_() > context.m_159777_().m_123342_() || position.m_123342_() > context.m_159777_().m_123342_() + y - (3 + context.m_225041_().m_188503_(2));
         if (position.m_123331_(context.m_159777_()) <= (double)(radius * radius)) {
            BlockState state = context.m_159774_().m_8055_(position);
            if (!(state.m_60734_() instanceof AbstractChestBlock) && state.m_60800_(context.m_159774_(), position) >= 0.0F && isNotInDoorway) {
               context.m_159774_().m_7731_(position, Blocks.f_50069_.m_49966_(), 3);
            }

            if (position.m_123342_() == context.m_159777_().m_123342_()) {
               context.m_159774_().m_7731_(position, Blocks.f_50079_.m_49966_(), 3);
            }

            if (position.m_123342_() <= context.m_159777_().m_123342_() - 1 && !state.m_60815_()) {
               context.m_159774_().m_7731_(position, Blocks.f_50652_.m_49966_(), 3);
            }
         }
      }

   }

   private void generateSheepPen(ServerLevelAccessor level, BlockPos position, RandomSource random, BlockPos origin, float radius) {
      int width = 5 + random.m_188503_(3);
      int sheepAmount = 2 + random.m_188503_(3);
      Direction direction = Direction.NORTH;
      BlockPos end = position;

      int sideCount;
      int side;
      BlockPos relativePosition;
      for(sideCount = 0; sideCount < 4; ++sideCount) {
         for(side = 0; side < width; ++side) {
            relativePosition = end.m_5484_(direction, side);
            if (origin.m_123331_(relativePosition) <= (double)(radius * radius)) {
               level.m_7731_(relativePosition, this.getFenceState(level, relativePosition), 3);
               if (level.m_46859_(relativePosition.m_121945_(direction.m_122427_())) && sheepAmount > 0) {
                  BlockPos sheepPos = relativePosition.m_121945_(direction.m_122427_());
                  Sheep sheep = new Sheep(EntityType.f_20520_, level.m_6018_());
                  sheep.m_6034_((double)((float)sheepPos.m_123341_() + 0.5F), (double)((float)sheepPos.m_123342_() + 0.5F), (double)((float)sheepPos.m_123343_() + 0.5F));
                  sheep.m_29855_(random.m_188503_(4) == 0 ? DyeColor.YELLOW : DyeColor.WHITE);
                  level.m_7967_(sheep);
                  --sheepAmount;
               }
            }
         }

         end = end.m_5484_(direction, width);
         direction = direction.m_122427_();
      }

      for(sideCount = 0; sideCount < 4; ++sideCount) {
         for(side = 0; side < width; ++side) {
            relativePosition = end.m_5484_(direction, side);
            if (origin.m_123331_(relativePosition) <= (double)(radius * radius)) {
               level.m_7731_(relativePosition, this.getFenceState(level, relativePosition), 3);
            }
         }

         end = end.m_5484_(direction, width);
         direction = direction.m_122427_();
      }

   }

   private void generateSkeleton(LevelAccessor level, BlockPos position, RandomSource random, BlockPos origin, float radius) {
      Direction direction = HORIZONTALS[(new Random()).nextInt(3)];
      Axis oppositeAxis = direction.m_122434_() == Axis.X ? Axis.Z : Axis.X;
      int maxRibHeight = random.m_188503_(2);

      for(int spine = 0; spine < 5 + random.m_188503_(2) * 2; ++spine) {
         BlockPos segment = position.m_5484_(direction, spine);
         if (origin.m_123331_(segment) <= (double)(radius * radius)) {
            level.m_7731_(segment, (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, direction.m_122434_()), 2);
         }

         if (spine % 2 != 0) {
            BlockPos rightRib = segment.m_121945_(direction.m_122428_());
            BlockPos leftRib = segment.m_121945_(direction.m_122427_());
            if (origin.m_123331_(rightRib) <= (double)(radius * radius)) {
               level.m_7731_(rightRib, (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
            }

            if (origin.m_123331_(leftRib) <= (double)(radius * radius)) {
               level.m_7731_(leftRib, (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
            }

            for(int ribHeight = 1; ribHeight < maxRibHeight + 2; ++ribHeight) {
               if (origin.m_123331_(rightRib.m_6630_(ribHeight).m_121945_(direction.m_122428_())) <= (double)(radius * radius)) {
                  level.m_7731_(rightRib.m_6630_(ribHeight).m_121945_(direction.m_122428_()), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, Axis.Y), 2);
               }

               if (origin.m_123331_(leftRib.m_6630_(ribHeight).m_121945_(direction.m_122427_())) <= (double)(radius * radius)) {
                  level.m_7731_(leftRib.m_6630_(ribHeight).m_121945_(direction.m_122427_()), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, Axis.Y), 2);
               }
            }

            if (origin.m_123331_(rightRib.m_6630_(maxRibHeight + 2)) <= (double)(radius * radius)) {
               level.m_7731_(rightRib.m_6630_(maxRibHeight + 2), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
            }

            if (origin.m_123331_(leftRib.m_6630_(maxRibHeight + 2)) <= (double)(radius * radius)) {
               level.m_7731_(leftRib.m_6630_(maxRibHeight + 2), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
            }
         }
      }

   }

   private boolean isTouchingAir(LevelAccessor level, BlockPos position) {
      boolean isTouchingAir = true;
      Direction[] var4 = HORIZONTALS;
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Direction direction = var4[var6];
         if (!level.m_46859_(position.m_121945_(direction))) {
            isTouchingAir = false;
         }
      }

      return isTouchingAir;
   }

   private BlockState getFenceState(LevelAccessor level, BlockPos position) {
      boolean east = level.m_8055_(position.m_122029_()).m_60734_() == Blocks.f_50132_;
      boolean west = level.m_8055_(position.m_122024_()).m_60734_() == Blocks.f_50132_;
      boolean north = level.m_8055_(position.m_122012_()).m_60734_() == Blocks.f_50132_;
      boolean south = level.m_8055_(position.m_122019_()).m_60734_() == Blocks.f_50132_;
      return (BlockState)((BlockState)((BlockState)((BlockState)Blocks.f_50132_.m_49966_().m_61124_(FenceBlock.f_52310_, east)).m_61124_(FenceBlock.f_52312_, west)).m_61124_(FenceBlock.f_52309_, north)).m_61124_(FenceBlock.f_52311_, south);
   }

   public IafWorldData.FeatureType getFeatureType() {
      return IafWorldData.FeatureType.SURFACE;
   }

   public String getId() {
      return "cyclops_cave";
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
