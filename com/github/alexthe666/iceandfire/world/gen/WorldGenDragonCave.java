package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.BlockGoldPile;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.util.HomePosition;
import com.github.alexthe666.iceandfire.util.ShapeBuilder;
import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.tags.ITagManager;

public abstract class WorldGenDragonCave extends Feature<NoneFeatureConfiguration> implements TypedFeature {
   public ResourceLocation DRAGON_CHEST;
   public ResourceLocation DRAGON_MALE_CHEST;
   public WorldGenCaveStalactites CEILING_DECO;
   public BlockState PALETTE_BLOCK1;
   public BlockState PALETTE_BLOCK2;
   public TagKey<Block> dragonTypeOreTag;
   public BlockState TREASURE_PILE;
   private static final Direction[] HORIZONTALS;
   public boolean isMale;

   protected WorldGenDragonCave(Codec<NoneFeatureConfiguration> codec) {
      super(codec);
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      if (rand.m_188503_(IafConfig.generateDragonDenChance) == 0 && IafWorldRegistry.isFarEnoughFromSpawn(worldIn, position) && IafWorldRegistry.isFarEnoughFromDangerousGen(worldIn, position, this.getId(), this.getFeatureType())) {
         this.isMale = rand.m_188499_();
         ChunkPos chunkPos = worldIn.m_46865_(position).m_7697_();
         int j = 40;

         int dragonAge;
         int radius;
         for(dragonAge = 0; dragonAge < 20; ++dragonAge) {
            for(radius = 0; radius < 20; ++radius) {
               j = Math.min(j, worldIn.m_6924_(Types.OCEAN_FLOOR_WG, position.m_123341_() + dragonAge, position.m_123343_() + radius));
            }
         }

         j -= 20;
         j -= rand.m_188503_(30);
         if (j < worldIn.m_141937_() + 20) {
            return false;
         } else {
            position = new BlockPos((chunkPos.f_45578_ << 4) + 8, j, (chunkPos.f_45579_ << 4) + 8);
            dragonAge = 75 + rand.m_188503_(50);
            radius = (int)((float)dragonAge * 0.2F) + rand.m_188503_(4);
            this.generateCave(worldIn, radius, 3, position, rand);
            EntityDragonBase dragon = this.createDragon(worldIn, rand, position, dragonAge);
            worldIn.m_7967_(dragon);
            return true;
         }
      } else {
         return false;
      }
   }

   public void generateCave(LevelAccessor worldIn, int radius, int amount, BlockPos center, RandomSource rand) {
      List<WorldGenDragonCave.SphereInfo> sphereList = new ArrayList();
      sphereList.add(new WorldGenDragonCave.SphereInfo(radius, center.m_7949_()));
      Stream<BlockPos> sphereBlocks = ShapeBuilder.start().getAllInCutOffSphereMutable(radius, radius / 2, center).toStream(false);
      Stream<BlockPos> hollowBlocks = ShapeBuilder.start().getAllInRandomlyDistributedRangeYCutOffSphereMutable(radius - 2, (int)((double)(radius - 2) * 0.75D), (radius - 2) / 2, rand, center).toStream(false);

      for(int i = 0; i < amount + rand.m_188503_(2); ++i) {
         Direction direction = HORIZONTALS[rand.m_188503_(HORIZONTALS.length - 1)];
         int r = 2 * (int)((float)radius / 3.0F) + rand.m_188503_(8);
         BlockPos centerOffset = center.m_5484_(direction, radius - 2);
         sphereBlocks = Stream.concat(sphereBlocks, ShapeBuilder.start().getAllInCutOffSphereMutable(r, r, centerOffset).toStream(false));
         hollowBlocks = Stream.concat(hollowBlocks, ShapeBuilder.start().getAllInRandomlyDistributedRangeYCutOffSphereMutable(r - 2, (int)((double)(r - 2) * 0.75D), (r - 2) / 2, rand, centerOffset).toStream(false));
         sphereList.add(new WorldGenDragonCave.SphereInfo(r, centerOffset));
      }

      Set<BlockPos> shellBlocksSet = (Set)sphereBlocks.map(BlockPos::m_7949_).collect(Collectors.toSet());
      Set<BlockPos> hollowBlocksSet = (Set)hollowBlocks.map(BlockPos::m_7949_).collect(Collectors.toSet());
      shellBlocksSet.removeAll(hollowBlocksSet);
      this.createShell(worldIn, rand, shellBlocksSet);
      this.hollowOut(worldIn, hollowBlocksSet);
      this.decorateCave(worldIn, rand, hollowBlocksSet, sphereList, center);
      sphereList.clear();
   }

   public void createShell(LevelAccessor worldIn, RandomSource rand, Set<BlockPos> positions) {
      ITagManager<Block> tagManager = ForgeRegistries.BLOCKS.tags();
      List<Block> rareOres = this.getBlockList(tagManager, IafBlockTags.DRAGON_CAVE_RARE_ORES);
      List<Block> uncommonOres = this.getBlockList(tagManager, IafBlockTags.DRAGON_CAVE_UNCOMMON_ORES);
      List<Block> commonOres = this.getBlockList(tagManager, IafBlockTags.DRAGON_CAVE_COMMON_ORES);
      List<Block> dragonTypeOres = this.getBlockList(tagManager, this.dragonTypeOreTag);
      positions.forEach((blockPos) -> {
         if (!(worldIn.m_8055_(blockPos).m_60734_() instanceof BaseEntityBlock) && worldIn.m_8055_(blockPos).m_60800_(worldIn, blockPos) >= 0.0F) {
            boolean doOres = rand.m_188503_(IafConfig.oreToStoneRatioForDragonCaves + 1) == 0;
            if (doOres) {
               Block toPlace = null;
               if (rand.m_188499_()) {
                  toPlace = !dragonTypeOres.isEmpty() ? (Block)dragonTypeOres.get(rand.m_188503_(dragonTypeOres.size())) : null;
               } else {
                  double chance = rand.m_188500_();
                  if (!rareOres.isEmpty() && chance <= 0.15D) {
                     toPlace = (Block)rareOres.get(rand.m_188503_(rareOres.size()));
                  } else if (!uncommonOres.isEmpty() && chance <= 0.45D) {
                     toPlace = (Block)uncommonOres.get(rand.m_188503_(uncommonOres.size()));
                  } else if (!commonOres.isEmpty()) {
                     toPlace = (Block)commonOres.get(rand.m_188503_(commonOres.size()));
                  }
               }

               if (toPlace != null) {
                  worldIn.m_7731_(blockPos, toPlace.m_49966_(), 2);
               } else {
                  worldIn.m_7731_(blockPos, rand.m_188499_() ? this.PALETTE_BLOCK1 : this.PALETTE_BLOCK2, 2);
               }
            } else {
               worldIn.m_7731_(blockPos, rand.m_188499_() ? this.PALETTE_BLOCK1 : this.PALETTE_BLOCK2, 2);
            }
         }

      });
   }

   private List<Block> getBlockList(ITagManager<Block> tagManager, TagKey<Block> tagKey) {
      return tagManager == null ? List.of() : tagManager.getTag(tagKey).stream().toList();
   }

   public void hollowOut(LevelAccessor worldIn, Set<BlockPos> positions) {
      positions.forEach((blockPos) -> {
         if (!(worldIn.m_8055_(blockPos).m_60734_() instanceof BaseEntityBlock)) {
            worldIn.m_7731_(blockPos, Blocks.f_50016_.m_49966_(), 3);
         }

      });
   }

   public void decorateCave(LevelAccessor worldIn, RandomSource rand, Set<BlockPos> positions, List<WorldGenDragonCave.SphereInfo> spheres, BlockPos center) {
      Iterator var6 = spheres.iterator();

      while(var6.hasNext()) {
         WorldGenDragonCave.SphereInfo sphere = (WorldGenDragonCave.SphereInfo)var6.next();
         BlockPos pos = sphere.pos;
         int radius = sphere.radius;

         for(int i = 0; i < 15 + rand.m_188503_(10); ++i) {
            this.CEILING_DECO.generate(worldIn, rand, pos.m_6630_(radius / 2 - 1).m_7918_(rand.m_188503_(radius) - radius / 2, 0, rand.m_188503_(radius) - radius / 2));
         }
      }

      positions.forEach((blockPos) -> {
         if (blockPos.m_123342_() < center.m_123342_()) {
            BlockState stateBelow = worldIn.m_8055_(blockPos.m_7495_());
            if ((stateBelow.m_204336_(BlockTags.f_13061_) || stateBelow.m_204336_(IafBlockTags.DRAGON_ENVIRONMENT_BLOCKS)) && worldIn.m_8055_(blockPos).m_60795_()) {
               this.setGoldPile(worldIn, blockPos, rand);
            }
         }

      });
   }

   public void setGoldPile(LevelAccessor world, BlockPos pos, RandomSource rand) {
      if (!(world.m_8055_(pos).m_60734_() instanceof BaseEntityBlock)) {
         int chance = rand.m_188503_(99) + 1;
         if (chance < 60) {
            int goldRand = Math.max(1, IafConfig.dragonDenGoldAmount) * (this.isMale ? 1 : 2);
            boolean generateGold = rand.m_188503_(goldRand) == 0;
            world.m_7731_(pos, generateGold ? (BlockState)this.TREASURE_PILE.m_61124_(BlockGoldPile.LAYERS, 1 + rand.m_188503_(7)) : Blocks.f_50016_.m_49966_(), 3);
         } else if (chance == 61) {
            world.m_7731_(pos, (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, HORIZONTALS[rand.m_188503_(3)]), 2);
            if (world.m_8055_(pos).m_60734_() instanceof ChestBlock) {
               BlockEntity blockEntity = world.m_7702_(pos);
               if (blockEntity instanceof ChestBlockEntity) {
                  ChestBlockEntity chestBlockEntity = (ChestBlockEntity)blockEntity;
                  chestBlockEntity.m_59626_(this.isMale ? this.DRAGON_MALE_CHEST : this.DRAGON_CHEST, rand.m_188505_());
               }
            }
         }
      }

   }

   private EntityDragonBase createDragon(WorldGenLevel worldGen, RandomSource random, BlockPos position, int dragonAge) {
      EntityDragonBase dragon = (EntityDragonBase)this.getDragonType().m_20615_(worldGen.m_6018_());
      dragon.setGender(this.isMale);
      dragon.growDragon(dragonAge);
      dragon.setAgingDisabled(true);
      dragon.m_21153_(dragon.m_21233_());
      dragon.setVariant(random.m_188503_(4));
      dragon.m_19890_((double)position.m_123341_() + 0.5D, (double)position.m_123342_() + 0.5D, (double)position.m_123343_() + 0.5D, random.m_188501_() * 360.0F, 0.0F);
      dragon.m_21837_(true);
      dragon.homePos = new HomePosition(position, worldGen.m_6018_());
      dragon.setHunger(50);
      return dragon;
   }

   public abstract EntityType<? extends EntityDragonBase> getDragonType();

   public IafWorldData.FeatureType getFeatureType() {
      return IafWorldData.FeatureType.UNDERGROUND;
   }

   public String getId() {
      return "dragon_cave";
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }

   private static class SphereInfo {
      int radius;
      BlockPos pos;

      private SphereInfo(int radius, BlockPos pos) {
         this.radius = radius;
         this.pos = pos;
      }
   }
}
