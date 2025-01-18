package com.github.alexthe666.iceandfire.world.gen;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.jetbrains.annotations.NotNull;

public class WorldGenFireDragonRoosts extends WorldGenDragonRoosts {
   private static final ResourceLocation DRAGON_CHEST = new ResourceLocation("iceandfire", "chest/fire_dragon_roost");

   public WorldGenFireDragonRoosts(Codec<NoneFeatureConfiguration> configuration) {
      super(configuration, (Block)IafBlockRegistry.GOLD_PILE.get());
   }

   protected EntityType<? extends EntityDragonBase> getDragonType() {
      return (EntityType)IafEntityRegistry.FIRE_DRAGON.get();
   }

   protected ResourceLocation getRoostLootTable() {
      return DRAGON_CHEST;
   }

   protected BlockState transform(BlockState state) {
      Block block = null;
      if (state.m_60713_(Blocks.f_50440_)) {
         block = (Block)IafBlockRegistry.CHARRED_GRASS.get();
      } else if (state.m_60713_(Blocks.f_152481_)) {
         block = (Block)IafBlockRegistry.CHARRED_DIRT_PATH.get();
      } else if (state.m_204336_(net.neoforge.common.Tags.Blocks.GRAVEL)) {
         block = (Block)IafBlockRegistry.CHARRED_GRAVEL.get();
      } else if (state.m_204336_(BlockTags.f_144274_)) {
         block = (Block)IafBlockRegistry.CHARRED_DIRT.get();
      } else if (state.m_204336_(net.neoforge.common.Tags.Blocks.STONE)) {
         block = (Block)IafBlockRegistry.CHARRED_STONE.get();
      } else if (state.m_204336_(net.neoforge.common.Tags.Blocks.COBBLESTONE)) {
         block = (Block)IafBlockRegistry.CHARRED_COBBLESTONE.get();
      } else if (!state.m_204336_(BlockTags.f_13106_) && !state.m_204336_(BlockTags.f_13090_)) {
         if (state.m_60713_(Blocks.f_50034_) || state.m_204336_(BlockTags.f_13035_) || state.m_204336_(BlockTags.f_13041_) || state.m_204336_(BlockTags.f_13073_)) {
            block = Blocks.f_50016_;
         }
      } else {
         block = (Block)IafBlockRegistry.ASH.get();
      }

      return block != null ? block.m_49966_() : state;
   }

   protected void handleCustomGeneration(@NotNull FeaturePlaceContext<NoneFeatureConfiguration> context, BlockPos position, double distance) {
      if (context.m_225041_().m_188503_(1000) == 0) {
         this.generateRoostPile(context.m_159774_(), context.m_225041_(), this.getSurfacePosition(context.m_159774_(), position), (Block)IafBlockRegistry.ASH.get());
      }

   }
}
