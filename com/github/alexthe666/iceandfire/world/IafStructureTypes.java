package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.world.structure.GorgonTempleStructure;
import com.github.alexthe666.iceandfire.world.structure.GraveyardStructure;
import com.github.alexthe666.iceandfire.world.structure.MausoleumStructure;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.RegistryObject;

public class IafStructureTypes {
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES;
   public static final RegistryObject<StructureType<GraveyardStructure>> GRAVEYARD;
   public static final RegistryObject<StructureType<MausoleumStructure>> MAUSOLEUM;
   public static final RegistryObject<StructureType<GorgonTempleStructure>> GORGON_TEMPLE;

   private static <P extends Structure> RegistryObject<StructureType<P>> registerType(String name, Supplier<StructureType<P>> factory) {
      return STRUCTURE_TYPES.register(name, factory);
   }

   static {
      STRUCTURE_TYPES = DeferredRegister.create(Registries.f_256938_, "iceandfire");
      GRAVEYARD = registerType("graveyard", () -> {
         return () -> {
            return GraveyardStructure.CODEC;
         };
      });
      MAUSOLEUM = registerType("mausoleum", () -> {
         return () -> {
            return MausoleumStructure.CODEC;
         };
      });
      GORGON_TEMPLE = registerType("gorgon_temple", () -> {
         return () -> {
            return GorgonTempleStructure.CODEC;
         };
      });
   }
}
