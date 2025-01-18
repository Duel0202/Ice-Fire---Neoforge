package com.github.alexthe666.iceandfire.datagen.util;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet.ListBacked;
import net.minecraft.tags.TagKey;

public class ListHolderSet<T> extends ListBacked<T> {
   List<Holder<T>> contents;

   public ListHolderSet(List<Holder<T>> contents) {
      this.contents = contents;
   }

   protected List<Holder<T>> m_203661_() {
      return this.contents;
   }

   public Either<TagKey<T>, List<Holder<T>>> m_203440_() {
      return Either.right(this.contents);
   }

   public boolean m_203333_(Holder<T> pHolder) {
      return this.contents.contains(pHolder);
   }

   public Optional<TagKey<T>> m_245234_() {
      return Optional.empty();
   }
}
