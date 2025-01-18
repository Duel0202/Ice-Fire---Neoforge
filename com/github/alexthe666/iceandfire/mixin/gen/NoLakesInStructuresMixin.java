package com.github.alexthe666.iceandfire.mixin.gen;

import com.github.alexthe666.iceandfire.datagen.IafStructures;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.structure.Structure;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({LakeFeature.class})
public class NoLakesInStructuresMixin {
   @Inject(
      method = {"place(Lnet/minecraft/world/level/levelgen/feature/FeaturePlaceContext;)Z"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void iaf_noLakesInMausoleum(FeaturePlaceContext<BlockStateConfiguration> context, CallbackInfoReturnable<Boolean> cir) {
      if (context.m_159774_() instanceof WorldGenRegion) {
         Registry<Structure> configuredStructureFeatureRegistry = context.m_159774_().m_9598_().m_175515_(Registries.f_256944_);
         StructureManager structureManager = context.m_159774_().m_6018_().m_215010_();
         List<Optional<Structure>> availableStructures = List.of(configuredStructureFeatureRegistry.m_123009_(IafStructures.MAUSOLEUM), configuredStructureFeatureRegistry.m_123009_(IafStructures.GRAVEYARD), configuredStructureFeatureRegistry.m_123009_(IafStructures.GORGON_TEMPLE));
         Iterator var6 = availableStructures.iterator();

         Optional structure;
         do {
            if (!var6.hasNext()) {
               return;
            }

            structure = (Optional)var6.next();
         } while(!structure.isPresent() || !structureManager.m_220494_(context.m_159777_(), (Structure)structure.get()).m_73603_());

         cir.setReturnValue(false);
      }
   }
}
