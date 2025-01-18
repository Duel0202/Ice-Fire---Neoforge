package com.github.alexthe666.iceandfire.datagen;

import com.github.alexthe666.iceandfire.datagen.tags.BannerPatternTagGenerator;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.datagen.tags.IafEntityTags;
import com.github.alexthe666.iceandfire.datagen.tags.IafItemTags;
import com.github.alexthe666.iceandfire.datagen.tags.POITagGenerator;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.DetectedVersion;
import net.minecraft.WorldVersion;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.MetadataSectionType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforge.common.data.BlockTagsProvider;
import net.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforge.common.data.ExistingFileHelper;
import net.neoforge.data.event.GatherDataEvent;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   modid = "iceandfire",
   bus = Bus.MOD
)
public class DataGenerators {
   @SubscribeEvent
   public static void gatherData(GatherDataEvent event) {
      DataGenerator generator = event.getGenerator();
      PackOutput output = event.getGenerator().getPackOutput();
      CompletableFuture<Provider> provider = event.getLookupProvider();
      ExistingFileHelper helper = event.getExistingFileHelper();
      DatapackBuiltinEntriesProvider datapackProvider = new RegistryDataGenerator(output, provider);
      CompletableFuture<Provider> lookupProvider = datapackProvider.getRegistryProvider();
      generator.addProvider(event.includeServer(), datapackProvider);
      generator.addProvider(event.includeServer(), new BannerPatternTagGenerator(output, provider, helper));
      generator.addProvider(event.includeServer(), new POITagGenerator(output, provider, helper));
      PackMetadataGenerator var10002 = new PackMetadataGenerator(output);
      MetadataSectionType var10003 = PackMetadataSection.f_243696_;
      MutableComponent var10006 = Component.m_237113_("Resources for Ice and Fire");
      int var10007 = DetectedVersion.f_132476_.m_264084_(PackType.CLIENT_RESOURCES);
      Stream var10008 = Arrays.stream(PackType.values());
      Function var10009 = Function.identity();
      WorldVersion var10010 = DetectedVersion.f_132476_;
      Objects.requireNonNull(var10010);
      generator.addProvider(true, var10002.m_247300_(var10003, new PackMetadataSection(var10006, var10007, (Map)var10008.collect(Collectors.toMap(var10009, var10010::m_264084_)))));
      generator.addProvider(event.includeServer(), new IafBiomeTagGenerator(output, lookupProvider, helper));
      generator.addProvider(event.includeClient(), new AtlasGenerator(output, helper));
      BlockTagsProvider blocktags = new IafBlockTags(output, provider, helper);
      generator.addProvider(event.includeServer(), blocktags);
      generator.addProvider(event.includeServer(), new IafItemTags(output, provider, blocktags.m_274426_(), helper));
      generator.addProvider(event.includeServer(), new IafEntityTags(output, provider, helper));
      generator.addProvider(event.includeServer(), new IafRecipes(output));
   }
}
