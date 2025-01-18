package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.world.gen.processor.DreadRuinProcessor;
import com.github.alexthe666.iceandfire.world.gen.processor.GorgonTempleProcessor;
import com.github.alexthe666.iceandfire.world.gen.processor.GraveyardProcessor;
import com.github.alexthe666.iceandfire.world.gen.processor.VillageHouseProcessor;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.RegistryObject;

public class IafProcessors {
   public static final DeferredRegister<StructureProcessorType<?>> PROCESSORS;
   public static final RegistryObject<StructureProcessorType<DreadRuinProcessor>> DREADRUINPROCESSOR;
   public static final RegistryObject<StructureProcessorType<GorgonTempleProcessor>> GORGONTEMPLEPROCESSOR;
   public static final RegistryObject<StructureProcessorType<GraveyardProcessor>> GRAVEYARDPROCESSOR;
   public static final RegistryObject<StructureProcessorType<VillageHouseProcessor>> VILLAGEHOUSEPROCESSOR;

   private static <P extends StructureProcessor> RegistryObject<StructureProcessorType<P>> registerProcessor(String name, Supplier<StructureProcessorType<P>> processor) {
      return PROCESSORS.register(name, processor);
   }

   static {
      PROCESSORS = DeferredRegister.create(Registries.f_256983_, "iceandfire");
      DREADRUINPROCESSOR = registerProcessor("dread_mausoleum_processor", () -> {
         return () -> {
            return DreadRuinProcessor.CODEC;
         };
      });
      GORGONTEMPLEPROCESSOR = registerProcessor("gorgon_temple_processor", () -> {
         return () -> {
            return GorgonTempleProcessor.CODEC;
         };
      });
      GRAVEYARDPROCESSOR = registerProcessor("graveyard_processor", () -> {
         return () -> {
            return GraveyardProcessor.CODEC;
         };
      });
      VILLAGEHOUSEPROCESSOR = registerProcessor("village_house_processor", () -> {
         return () -> {
            return VillageHouseProcessor.CODEC;
         };
      });
   }
}
