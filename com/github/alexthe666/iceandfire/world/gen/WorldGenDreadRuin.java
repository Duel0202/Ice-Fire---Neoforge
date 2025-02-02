package com.github.alexthe666.iceandfire.world.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class WorldGenDreadRuin extends Feature<NoneFeatureConfiguration> {
   private static final ResourceLocation STRUCTURE_0 = new ResourceLocation("iceandfire", "dread_ruin_0");
   private static final ResourceLocation STRUCTURE_1 = new ResourceLocation("iceandfire", "dread_ruin_1");
   private static final ResourceLocation STRUCTURE_2 = new ResourceLocation("iceandfire", "dread_ruin_2");
   private static final ResourceLocation STRUCTURE_3 = new ResourceLocation("iceandfire", "dread_ruin_3");
   private static final ResourceLocation STRUCTURE_4 = new ResourceLocation("iceandfire", "dread_ruin_4");
   private static final ResourceLocation STRUCTURE_5 = new ResourceLocation("iceandfire", "dread_ruin_5");
   private static final ResourceLocation STRUCTURE_6 = new ResourceLocation("iceandfire", "dread_ruin_6");
   private static final ResourceLocation STRUCTURE_7 = new ResourceLocation("iceandfire", "dread_ruin_7");
   private static final ResourceLocation STRUCTURE_8 = new ResourceLocation("iceandfire", "dread_ruin_8");
   private static final ResourceLocation STRUCTURE_9 = new ResourceLocation("iceandfire", "dread_ruin_9");
   private static final ResourceLocation STRUCTURE_10 = new ResourceLocation("iceandfire", "dread_ruin_10");
   private static final ResourceLocation STRUCTURE_11 = new ResourceLocation("iceandfire", "dread_ruin_11");
   private static final ResourceLocation STRUCTURE_12 = new ResourceLocation("iceandfire", "dread_ruin_12");
   private static final Direction[] HORIZONTALS;

   public WorldGenDreadRuin(Codec<NoneFeatureConfiguration> configFactoryIn) {
      super(configFactoryIn);
   }

   public static Rotation getRotationFromFacing(Direction facing) {
      switch(facing) {
      case EAST:
         return Rotation.CLOCKWISE_90;
      case SOUTH:
         return Rotation.CLOCKWISE_180;
      case WEST:
         return Rotation.COUNTERCLOCKWISE_90;
      default:
         return Rotation.NONE;
      }
   }

   private ResourceLocation getRandomStructure(RandomSource rand) {
      switch(rand.m_188503_(11)) {
      case 0:
         return STRUCTURE_0;
      case 1:
         return STRUCTURE_1;
      case 2:
         return STRUCTURE_2;
      case 3:
         return STRUCTURE_3;
      case 4:
         return STRUCTURE_4;
      case 5:
         return STRUCTURE_5;
      case 6:
         return STRUCTURE_6;
      case 7:
         return STRUCTURE_7;
      case 8:
         return STRUCTURE_8;
      case 9:
         return STRUCTURE_9;
      case 10:
         return STRUCTURE_10;
      case 11:
         return STRUCTURE_11;
      case 12:
         return STRUCTURE_12;
      default:
         return STRUCTURE_0;
      }
   }

   public boolean m_142674_(FeaturePlaceContext<NoneFeatureConfiguration> context) {
      WorldGenLevel worldIn = context.m_159774_();
      RandomSource rand = context.m_225041_();
      BlockPos position = context.m_159777_();
      this.getRandomStructure(rand);
      Direction facing = HORIZONTALS[rand.m_188503_(3)];
      MinecraftServer server = worldIn.m_6018_().m_7654_();
      Biome biome = (Biome)worldIn.m_204166_(position).m_203334_();
      return false;
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
