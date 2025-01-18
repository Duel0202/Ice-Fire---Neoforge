package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.block.BlockGoldPile;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class WorldGenMyrmexDecoration {
   public static final ResourceLocation MYRMEX_GOLD_CHEST = new ResourceLocation("iceandfire", "chest/myrmex_loot_chest");
   public static final ResourceLocation DESERT_MYRMEX_FOOD_CHEST = new ResourceLocation("iceandfire", "chest/myrmex_desert_food_chest");
   public static final ResourceLocation JUNGLE_MYRMEX_FOOD_CHEST = new ResourceLocation("iceandfire", "chest/myrmex_jungle_food_chest");
   public static final ResourceLocation MYRMEX_TRASH_CHEST = new ResourceLocation("iceandfire", "chest/myrmex_trash_chest");
   private static final Direction[] HORIZONTALS;

   public static void generateSkeleton(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand) {
      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         Direction direction = Direction.m_122407_(rand.m_188503_(3));
         Axis oppositeAxis = direction.m_122434_() == Axis.X ? Axis.Z : Axis.X;
         int maxRibHeight = rand.m_188503_(2);

         for(int spine = 0; spine < 5 + rand.m_188503_(2) * 2; ++spine) {
            BlockPos segment = blockpos.m_5484_(direction, spine);
            if (origin.m_123331_(segment) <= (double)(radius * radius)) {
               worldIn.m_7731_(segment, (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, direction.m_122434_()), 2);
            }

            if (spine % 2 != 0) {
               BlockPos rightRib = segment.m_121945_(direction.m_122428_());
               BlockPos leftRib = segment.m_121945_(direction.m_122427_());
               if (origin.m_123331_(rightRib) <= (double)(radius * radius)) {
                  worldIn.m_7731_(rightRib, (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
               }

               if (origin.m_123331_(leftRib) <= (double)(radius * radius)) {
                  worldIn.m_7731_(leftRib, (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
               }

               for(int ribHeight = 1; ribHeight < maxRibHeight + 2; ++ribHeight) {
                  if (origin.m_123331_(rightRib.m_6630_(ribHeight).m_121945_(direction.m_122428_())) <= (double)(radius * radius)) {
                     worldIn.m_7731_(rightRib.m_6630_(ribHeight).m_121945_(direction.m_122428_()), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, Axis.Y), 2);
                  }

                  if (origin.m_123331_(leftRib.m_6630_(ribHeight).m_121945_(direction.m_122427_())) <= (double)(radius * radius)) {
                     worldIn.m_7731_(leftRib.m_6630_(ribHeight).m_121945_(direction.m_122427_()), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, Axis.Y), 2);
                  }
               }

               if (origin.m_123331_(rightRib.m_6630_(maxRibHeight + 2)) <= (double)(radius * radius)) {
                  worldIn.m_7731_(rightRib.m_6630_(maxRibHeight + 2), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
               }

               if (origin.m_123331_(leftRib.m_6630_(maxRibHeight + 2)) <= (double)(radius * radius)) {
                  worldIn.m_7731_(leftRib.m_6630_(maxRibHeight + 2), (BlockState)Blocks.f_50453_.m_49966_().m_61124_(RotatedPillarBlock.f_55923_, oppositeAxis), 2);
               }
            }
         }
      }

   }

   public static void generateLeaves(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand, boolean jungle) {
      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         BlockState leaf = (BlockState)Blocks.f_50050_.m_49966_().m_61124_(LeavesBlock.f_54419_, Boolean.TRUE);
         if (jungle) {
            leaf = (BlockState)Blocks.f_50053_.m_49966_().m_61124_(LeavesBlock.f_54419_, Boolean.TRUE);
         }

         int i1 = 0;

         for(int i = 0; i1 >= 0 && i < 3; ++i) {
            int j = i1 + rand.m_188503_(2);
            int k = i1 + rand.m_188503_(2);
            int l = i1 + rand.m_188503_(2);
            float f = (float)(j + k + l) * 0.333F + 0.5F;
            Iterator var13 = ((Set)BlockPos.m_121990_(blockpos.m_7918_(-j, -k, -l), blockpos.m_7918_(j, k, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            while(var13.hasNext()) {
               BlockPos pos = (BlockPos)var13.next();
               if (pos.m_123331_(blockpos) <= (double)(f * f) && worldIn.m_46859_(pos)) {
                  worldIn.m_7731_(pos, leaf, 4);
               }
            }

            blockpos = blockpos.m_7918_(-(i1 + 1) + rand.m_188503_(2 + i1 * 2), -rand.m_188503_(2), -(i1 + 1) + rand.m_188503_(2 + i1 * 2));
         }
      }

   }

   public static void generatePumpkins(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand, boolean jungle) {
      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         worldIn.m_7731_(blockpos, jungle ? Blocks.f_50186_.m_49966_() : Blocks.f_50133_.m_49966_(), 2);
      }

   }

   public static void generateCocoon(LevelAccessor worldIn, BlockPos blockpos, RandomSource rand, boolean jungle, ResourceLocation lootTable) {
      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         worldIn.m_7731_(blockpos, jungle ? ((Block)IafBlockRegistry.JUNGLE_MYRMEX_COCOON.get()).m_49966_() : ((Block)IafBlockRegistry.DESERT_MYRMEX_COCOON.get()).m_49966_(), 3);
         if (worldIn.m_7702_(blockpos) != null && worldIn.m_7702_(blockpos) instanceof RandomizableContainerBlockEntity) {
            BlockEntity tileentity1 = worldIn.m_7702_(blockpos);
            ((RandomizableContainerBlockEntity)tileentity1).m_59626_(lootTable, rand.m_188505_());
         }
      }

   }

   public static void generateMushrooms(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand) {
      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         worldIn.m_7731_(blockpos, rand.m_188499_() ? Blocks.f_50072_.m_49966_() : Blocks.f_50073_.m_49966_(), 2);
      }

   }

   public static void generateGold(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand) {
      BlockState gold = ((Block)IafBlockRegistry.GOLD_PILE.get()).m_49966_();
      int choice = rand.m_188503_(2);
      if (choice == 1) {
         gold = ((Block)IafBlockRegistry.SILVER_PILE.get()).m_49966_();
      } else if (choice == 2) {
         gold = ((Block)IafBlockRegistry.COPPER_PILE.get()).m_49966_();
      }

      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         worldIn.m_7731_(blockpos, (BlockState)gold.m_61124_(BlockGoldPile.LAYERS, 8), 3);
         worldIn.m_7731_(MyrmexHive.getGroundedPos(worldIn, blockpos.m_122012_()), (BlockState)gold.m_61124_(BlockGoldPile.LAYERS, 1 + RandomSource.m_216327_().m_188503_(7)), 3);
         worldIn.m_7731_(MyrmexHive.getGroundedPos(worldIn, blockpos.m_122019_()), (BlockState)gold.m_61124_(BlockGoldPile.LAYERS, 1 + RandomSource.m_216327_().m_188503_(7)), 3);
         worldIn.m_7731_(MyrmexHive.getGroundedPos(worldIn, blockpos.m_122024_()), (BlockState)gold.m_61124_(BlockGoldPile.LAYERS, 1 + RandomSource.m_216327_().m_188503_(7)), 3);
         worldIn.m_7731_(MyrmexHive.getGroundedPos(worldIn, blockpos.m_122029_()), (BlockState)gold.m_61124_(BlockGoldPile.LAYERS, 1 + RandomSource.m_216327_().m_188503_(7)), 3);
         if (rand.m_188503_(3) == 0) {
            worldIn.m_7731_(blockpos.m_7494_(), (BlockState)Blocks.f_50087_.m_49966_().m_61124_(ChestBlock.f_51478_, HORIZONTALS[RandomSource.m_216327_().m_188503_(3)]), 2);
            if (worldIn.m_8055_(blockpos.m_7494_()).m_60734_() instanceof ChestBlock) {
               BlockEntity tileentity1 = worldIn.m_7702_(blockpos.m_7494_());
               if (tileentity1 instanceof ChestBlockEntity) {
                  ((ChestBlockEntity)tileentity1).m_59626_(MYRMEX_GOLD_CHEST, rand.m_188505_());
               }
            }
         }
      }

   }

   public static void generateTrashHeap(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand) {
      if (worldIn.m_8055_(blockpos.m_7495_()).m_60783_(worldIn, blockpos.m_7495_(), Direction.UP)) {
         Block blob = Blocks.f_50493_;
         switch(rand.m_188503_(3)) {
         case 0:
            blob = Blocks.f_50493_;
            break;
         case 1:
            blob = Blocks.f_49992_;
            break;
         case 2:
            blob = Blocks.f_50652_;
            break;
         case 3:
            blob = Blocks.f_49994_;
         }

         int i1 = 0;

         for(int i = 0; i1 >= 0 && i < 3; ++i) {
            int j = i1 + rand.m_188503_(2);
            int k = i1 + rand.m_188503_(2);
            int l = i1 + rand.m_188503_(2);
            float f = (float)(j + k + l) * 0.333F + 0.5F;
            Iterator var12 = ((Set)BlockPos.m_121990_(blockpos.m_7918_(-j, -k, -l), blockpos.m_7918_(j, k, l)).map(BlockPos::m_7949_).collect(Collectors.toSet())).iterator();

            while(var12.hasNext()) {
               BlockPos pos = (BlockPos)var12.next();
               if (pos.m_123331_(blockpos) <= (double)(f * f)) {
                  worldIn.m_7731_(pos, blob.m_49966_(), 4);
               }
            }

            blockpos = blockpos.m_7918_(-(i1 + 1) + rand.m_188503_(2 + i1 * 2), -rand.m_188503_(2), -(i1 + 1) + rand.m_188503_(2 + i1 * 2));
         }
      }

   }

   public static void generateTrashOre(LevelAccessor worldIn, BlockPos blockpos, BlockPos origin, int radius, RandomSource rand) {
      Block current = worldIn.m_8055_(blockpos).m_60734_();
      if (origin.m_123331_(blockpos) <= (double)(radius * radius) && (current == Blocks.f_50493_ || current == Blocks.f_49992_ || current == Blocks.f_50652_ || current == Blocks.f_49994_)) {
         Block ore = Blocks.f_50173_;
         if (rand.m_188503_(3) == 0) {
            ore = rand.m_188499_() ? Blocks.f_49995_ : (Block)IafBlockRegistry.SILVER_ORE.get();
            if (rand.m_188503_(2) == 0) {
               ore = Blocks.f_152505_;
            }
         } else if (rand.m_188503_(3) == 0) {
            ore = Blocks.f_50089_;
         } else if (rand.m_188503_(2) == 0) {
            ore = rand.m_188499_() ? Blocks.f_50264_ : (Block)IafBlockRegistry.SAPPHIRE_ORE.get();
            if (rand.m_188503_(2) == 0) {
               ore = Blocks.f_152492_;
            }
         }

         worldIn.m_7731_(blockpos, ore.m_49966_(), 2);
      }

   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
