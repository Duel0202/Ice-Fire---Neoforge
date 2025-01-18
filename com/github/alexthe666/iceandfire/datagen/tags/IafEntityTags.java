package com.github.alexthe666.iceandfire.datagen.tags;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.neoforge.common.Tags.EntityTypes;
import net.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class IafEntityTags extends EntityTypeTagsProvider {
   public static TagKey<EntityType<?>> IMMUNE_TO_GORGON_STONE = createKey("immune_to_gorgon_stone");

   public IafEntityTags(PackOutput output, CompletableFuture<Provider> provider, @Nullable ExistingFileHelper existingFileHelper) {
      super(output, provider, "iceandfire", existingFileHelper);
   }

   protected void m_6577_(Provider provider) {
      this.m_206424_(IMMUNE_TO_GORGON_STONE).m_206428_(EntityTypes.BOSSES).m_255245_(EntityType.f_217015_);
   }

   private static TagKey<EntityType<?>> createKey(String name) {
      return TagKey.m_203882_(Registries.f_256939_, new ResourceLocation("iceandfire", name));
   }
}
