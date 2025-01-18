package com.github.alexthe666.iceandfire.datagen.tags;

import com.github.alexthe666.iceandfire.datagen.IafPOITypes;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.PoiTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.neoforge.common.data.ExistingFileHelper;

public class POITagGenerator extends PoiTypeTagsProvider {
   public POITagGenerator(PackOutput pOutput, CompletableFuture<Provider> pLookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
      super(pOutput, pLookupProvider, "iceandfire", existingFileHelper);
   }

   protected void m_6577_(Provider pProvider) {
      this.m_206424_(PoiTypeTags.f_215875_).m_255204_(IafPOITypes.SCRIBE_POI);
   }

   private static TagKey<PoiType> create(String name) {
      return TagKey.m_203882_(Registries.f_256805_, new ResourceLocation("iceandfire", name));
   }

   public String m_6055_() {
      return "Ice and Fire POI Type Tags";
   }
}
