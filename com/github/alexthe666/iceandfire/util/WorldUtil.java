package com.github.alexthe666.iceandfire.util;

import com.github.alexthe666.iceandfire.world.IafWorldData;
import com.github.alexthe666.iceandfire.world.IafWorldRegistry;
import com.mojang.datafixers.util.Either;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class WorldUtil {
   public static boolean isBlockLoaded(LevelAccessor world, BlockPos pos) {
      return isChunkLoaded(world, pos.m_123341_() >> 4, pos.m_123343_() >> 4);
   }

   public static boolean isChunkLoaded(LevelAccessor world, int x, int z) {
      if (world.m_7726_() instanceof ServerChunkCache) {
         ChunkHolder holder = ((ServerChunkCache)world.m_7726_()).f_8325_.m_140327_(ChunkPos.m_45589_(x, z));
         return holder != null ? ((Either)holder.m_140082_().getNow(ChunkHolder.f_139997_)).left().isPresent() : false;
      } else {
         return world.m_6522_(x, z, ChunkStatus.f_62326_, false) != null;
      }
   }

   public static void markChunkDirty(Level world, BlockPos pos) {
      if (isBlockLoaded(world, pos)) {
         world.m_6325_(pos.m_123341_() >> 4, pos.m_123343_() >> 4).m_8092_(true);
         BlockState state = world.m_8055_(pos);
         world.m_7260_(pos, state, state, 3);
      }

   }

   public static boolean isChunkLoaded(LevelAccessor world, ChunkPos pos) {
      return isChunkLoaded(world, pos.f_45578_, pos.f_45579_);
   }

   public static boolean isEntityBlockLoaded(LevelAccessor world, BlockPos pos) {
      return isEntityChunkLoaded(world, pos.m_123341_() >> 4, pos.m_123343_() >> 4);
   }

   public static boolean isEntityChunkLoaded(LevelAccessor world, int x, int z) {
      return isEntityChunkLoaded(world, new ChunkPos(x, z));
   }

   public static boolean isEntityChunkLoaded(LevelAccessor world, ChunkPos pos) {
      if (!(world instanceof ServerLevel)) {
         return isChunkLoaded(world, pos);
      } else {
         return isChunkLoaded(world, pos) && ((ServerLevel)world).m_143340_(pos.m_45615_());
      }
   }

   public static boolean isAABBLoaded(Level world, AABB box) {
      return isChunkLoaded(world, (int)box.f_82288_ >> 4, (int)box.f_82290_ >> 4) && isChunkLoaded(world, (int)box.f_82291_ >> 4, (int)box.f_82293_ >> 4);
   }

   public static boolean isPastTime(Level world, int pastTime) {
      return world.m_46468_() % 24000L <= (long)pastTime;
   }

   public static boolean isOverworldType(@NotNull Level world) {
      return isOfWorldType(world, BuiltinDimensionTypes.f_223538_);
   }

   public static boolean isNetherType(@NotNull Level world) {
      return isOfWorldType(world, BuiltinDimensionTypes.f_223539_);
   }

   public static boolean isOfWorldType(@NotNull Level world, @NotNull ResourceKey<DimensionType> type) {
      RegistryAccess dynRegistries = world.m_9598_();
      ResourceLocation loc = ((Registry)dynRegistries.m_6632_(Registries.f_256787_).get()).m_7981_(world.m_6042_());
      if (loc == null) {
         return world.f_46443_ ? world.m_6042_().f_63837_().equals(type.m_135782_()) : false;
      } else {
         ResourceKey<DimensionType> regKey = ResourceKey.m_135785_(Registries.f_256787_, loc);
         return regKey == type;
      }
   }

   public static boolean isPeaceful(@NotNull Level world) {
      return !world.m_6106_().m_5470_().m_46207_(GameRules.f_46134_) || world.m_46791_().equals(Difficulty.PEACEFUL);
   }

   public static int getDimensionMaxHeight(DimensionType dimensionType) {
      return dimensionType.f_63865_() + dimensionType.f_156647_();
   }

   public static int getDimensionMinHeight(DimensionType dimensionType) {
      return dimensionType.f_156647_();
   }

   public static boolean isInWorldHeight(int yBlock, Level world) {
      DimensionType dimensionType = world.m_6042_();
      return yBlock > getDimensionMinHeight(dimensionType) && yBlock < getDimensionMaxHeight(dimensionType);
   }

   public static boolean canGenerate(int configChance, WorldGenLevel level, RandomSource random, BlockPos origin, String id, boolean checkFluid) {
      return canGenerate(configChance, level, random, origin, id, IafWorldData.FeatureType.SURFACE, checkFluid);
   }

   public static boolean canGenerate(int configChance, WorldGenLevel level, RandomSource random, BlockPos origin, String id, IafWorldData.FeatureType type, boolean checkFluid) {
      boolean canGenerate = random.m_188503_(configChance) == 0 && IafWorldRegistry.isFarEnoughFromSpawn(level, origin) && IafWorldRegistry.isFarEnoughFromDangerousGen(level, origin, id, type);
      return canGenerate && checkFluid && !level.m_6425_(origin.m_7495_()).m_76178_() ? false : canGenerate;
   }
}
