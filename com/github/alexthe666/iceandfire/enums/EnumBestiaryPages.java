package com.github.alexthe666.iceandfire.enums;

import com.github.alexthe666.iceandfire.item.ItemBestiary;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public enum EnumBestiaryPages {
   INTRODUCTION(2),
   FIREDRAGON(4),
   FIREDRAGONEGG(1),
   ICEDRAGON(4),
   ICEDRAGONEGG(1),
   TAMEDDRAGONS(3),
   MATERIALS(2),
   ALCHEMY(1),
   DRAGONFORGE(3),
   HIPPOGRYPH(1),
   GORGON(1),
   PIXIE(1),
   CYCLOPS(2),
   SIREN(2),
   HIPPOCAMPUS(2),
   DEATHWORM(3),
   COCKATRICE(2),
   STYMPHALIANBIRD(1),
   TROLL(2),
   MYRMEX(4),
   AMPHITHERE(2),
   SEASERPENT(2),
   HYDRA(2),
   DREAD_MOBS(1),
   LIGHTNINGDRAGON(5),
   LIGHTNINGDRAGONEGG(1),
   GHOST(1);

   public static final ImmutableList<EnumBestiaryPages> ALL_PAGES = ImmutableList.copyOf(values());
   public static final ImmutableList<Integer> ALL_INDEXES = ImmutableList.copyOf(IntStream.range(0, values().length).iterator());
   public int pages;

   private EnumBestiaryPages(int pages) {
      this.pages = pages;
   }

   public static Set<EnumBestiaryPages> containedPages(Collection<Integer> pages) {
      Stream var10000 = pages.stream();
      ImmutableList var10001 = ALL_PAGES;
      Objects.requireNonNull(var10001);
      return (Set)var10000.map(var10001::get).collect(Collectors.toSet());
   }

   public static boolean hasAllPages(ItemStack book) {
      return Ints.asList(book.m_41783_().m_128465_("Pages")).containsAll(ALL_INDEXES);
   }

   public static List<Integer> enumToInt(List<EnumBestiaryPages> pages) {
      return (List)pages.stream().map(Enum::ordinal).collect(Collectors.toList());
   }

   public static EnumBestiaryPages getRand() {
      return values()[ThreadLocalRandom.current().nextInt(values().length)];
   }

   public static void addRandomPage(ItemStack book) {
      if (book.m_41720_() instanceof ItemBestiary) {
         List<EnumBestiaryPages> list = possiblePages(book);
         if (!list.isEmpty()) {
            addPage((EnumBestiaryPages)list.get(ThreadLocalRandom.current().nextInt(list.size())), book);
         }
      }

   }

   public static List<EnumBestiaryPages> possiblePages(ItemStack book) {
      if (book.m_41720_() instanceof ItemBestiary) {
         CompoundTag tag = book.m_41783_();
         Collection<EnumBestiaryPages> containedPages = containedPages(Ints.asList(tag.m_128465_("Pages")));
         List<EnumBestiaryPages> possiblePages = new ArrayList(ALL_PAGES);
         possiblePages.removeAll(containedPages);
         return possiblePages;
      } else {
         return Collections.emptyList();
      }
   }

   public static boolean addPage(EnumBestiaryPages page, ItemStack book) {
      boolean flag = false;
      if (book.m_41720_() instanceof ItemBestiary) {
         CompoundTag tag = book.m_41783_();
         List<Integer> already = new ArrayList(Ints.asList(tag.m_128465_("Pages")));
         if (!already.contains(page.ordinal())) {
            already.add(page.ordinal());
            flag = true;
         }

         tag.m_128385_("Pages", Ints.toArray(already));
      }

      return flag;
   }

   @Nullable
   public static EnumBestiaryPages fromInt(int index) {
      if (index < 0) {
         return null;
      } else {
         int length = values().length;
         return values()[index % length];
      }
   }

   // $FF: synthetic method
   private static EnumBestiaryPages[] $values() {
      return new EnumBestiaryPages[]{INTRODUCTION, FIREDRAGON, FIREDRAGONEGG, ICEDRAGON, ICEDRAGONEGG, TAMEDDRAGONS, MATERIALS, ALCHEMY, DRAGONFORGE, HIPPOGRYPH, GORGON, PIXIE, CYCLOPS, SIREN, HIPPOCAMPUS, DEATHWORM, COCKATRICE, STYMPHALIANBIRD, TROLL, MYRMEX, AMPHITHERE, SEASERPENT, HYDRA, DREAD_MOBS, LIGHTNINGDRAGON, LIGHTNINGDRAGONEGG, GHOST};
   }
}
