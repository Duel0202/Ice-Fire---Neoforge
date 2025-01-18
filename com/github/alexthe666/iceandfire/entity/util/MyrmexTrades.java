package com.github.alexthe666.iceandfire.entity.util;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SuspiciousStewItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class MyrmexTrades {
   public static final Int2ObjectMap<ItemListing[]> DESERT_WORKER;
   public static final Int2ObjectMap<ItemListing[]> JUNGLE_WORKER;
   public static final Int2ObjectMap<ItemListing[]> DESERT_SOLDIER;
   public static final Int2ObjectMap<ItemListing[]> JUNGLE_SOLDIER;
   public static final Int2ObjectMap<ItemListing[]> DESERT_SENTINEL;
   public static final Int2ObjectMap<ItemListing[]> JUNGLE_SENTINEL;
   public static final Int2ObjectMap<ItemListing[]> DESERT_ROYAL;
   public static final Int2ObjectMap<ItemListing[]> JUNGLE_ROYAL;
   public static final Int2ObjectMap<ItemListing[]> DESERT_QUEEN;
   public static final Int2ObjectMap<ItemListing[]> JUNGLE_QUEEN;

   private static ItemStack createEgg(boolean jungle, int caste) {
      ItemStack egg = new ItemStack(jungle ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_EGG.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_EGG.get());
      CompoundTag tag = new CompoundTag();
      tag.m_128405_("EggOrdinal", caste);
      egg.m_41751_(tag);
      return egg;
   }

   private static Int2ObjectMap<ItemListing[]> createTrades(ImmutableMap<Integer, ItemListing[]> p_221238_0_) {
      return new Int2ObjectOpenHashMap(p_221238_0_);
   }

   static {
      DESERT_WORKER = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.DesertResinForItemsTrade(Items.f_42329_, 64, 1, 5), new MyrmexTrades.DesertResinForItemsTrade(Items.f_41830_, 64, 1, 5), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_41866_, 2, 8, 5, 2), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42500_, 10, 1, 1)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade(Items.f_41834_, 1, 6, 3, 2), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42501_, 15, 2, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42398_, 1, 64, 5, 2), new MyrmexTrades.ItemsForDesertResinTrade((Item)IafItemRegistry.COPPER_NUGGET.get(), 1, 4, 10)}));
      JUNGLE_WORKER = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.JungleResinForItemsTrade(Items.f_42329_, 64, 1, 5), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42575_, 1, 20, 3, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_41899_, 1, 64, 5, 1), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42500_, 10, 1, 5)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade(Items.f_41833_, 2, 15, 3, 2), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42501_, 15, 2, 3), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42398_, 1, 64, 5, 2), new MyrmexTrades.ItemsForJungleResinTrade((Item)IafItemRegistry.COPPER_NUGGET.get(), 1, 4, 10)}));
      DESERT_SOLDIER = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.DesertResinForItemsTrade(Items.f_42500_, 7, 1, 3), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42402_, 16, 3, 3), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42403_, 5, 1, 4), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42697_, 1, 3, 6, 2), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42749_, 4, 1, 4), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42581_, 2, 2, 7), new MyrmexTrades.ItemsForDesertResinTrade((Item)IafItemRegistry.SILVER_NUGGET.get(), 4, 1, 15)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade(Items.f_41982_, 1, 15, 6, 2), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42587_, 1, 4, 6, 2), new MyrmexTrades.ItemsForDesertResinTrade((Item)IafItemRegistry.TROLL_TUSK.get(), 6, 1, 4, 2), new MyrmexTrades.DesertResinForItemsTrade((ItemLike)IafItemRegistry.DRAGON_BONE.get(), 6, 2, 3)}));
      JUNGLE_SOLDIER = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.JungleResinForItemsTrade(Items.f_42500_, 7, 1, 3), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42402_, 16, 3, 3), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42403_, 5, 1, 4), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42521_, 1, 4, 6, 2), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42749_, 4, 1, 4), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42581_, 2, 2, 7), new MyrmexTrades.ItemsForJungleResinTrade((Item)IafItemRegistry.SILVER_NUGGET.get(), 1, 4, 15)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42583_, 1, 15, 6, 2), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42587_, 1, 4, 6, 2), new MyrmexTrades.ItemsForJungleResinTrade((Item)IafItemRegistry.TROLL_TUSK.get(), 6, 1, 4, 2), new MyrmexTrades.JungleResinForItemsTrade((ItemLike)IafItemRegistry.DRAGON_BONE.get(), 6, 2, 3)}));
      DESERT_SENTINEL = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.DesertResinForItemsTrade(Items.f_42591_, 10, 2, 3), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42675_, 2, 1, 2), new MyrmexTrades.DesertResinForItemsTrade(Items.f_42529_, 4, 2, 4)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42451_, 2, 5, 5, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42485_, 2, 3, 4), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42579_, 2, 3, 4), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42658_, 2, 3, 4), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42678_, 15, 1, 2, 1)}));
      JUNGLE_SENTINEL = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.JungleResinForItemsTrade(Items.f_42591_, 10, 2, 3), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42675_, 2, 1, 2), new MyrmexTrades.JungleResinForItemsTrade(Items.f_42529_, 4, 2, 4)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42451_, 2, 5, 5, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42485_, 2, 3, 4), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42579_, 2, 3, 4), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42658_, 2, 3, 4), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42678_, 15, 1, 2, 1)}));
      DESERT_ROYAL = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade((Item)IafItemRegistry.MANUSCRIPT.get(), 1, 3, 5, 1), new MyrmexTrades.ItemsForDesertResinTrade((Item)IafItemRegistry.WITHER_SHARD.get(), 3, 1, 3, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42616_, 10, 1, 3, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42692_, 2, 4, 3, 1)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42677_, 3, 1, 2, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42542_, 5, 1, 3, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42417_, 3, 1, 5, 1), new MyrmexTrades.ItemsForDesertResinTrade((Item)IafItemRegistry.SILVER_INGOT.get(), 3, 1, 5, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_151052_, 2, 2, 3, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42584_, 8, 1, 5, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42648_, 3, 1, 5, 1)}));
      JUNGLE_ROYAL = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade((Item)IafItemRegistry.MANUSCRIPT.get(), 1, 3, 5, 1), new MyrmexTrades.ItemsForJungleResinTrade((Item)IafItemRegistry.WITHER_SHARD.get(), 3, 1, 3, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42616_, 10, 1, 3, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42692_, 2, 4, 3, 1)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42677_, 3, 1, 2, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42542_, 5, 1, 3, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42417_, 3, 1, 5, 1), new MyrmexTrades.ItemsForJungleResinTrade((Item)IafItemRegistry.SILVER_INGOT.get(), 3, 1, 5, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_151052_, 2, 2, 3, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42584_, 8, 1, 5, 1), new MyrmexTrades.ItemsForJungleResinTrade(Items.f_42648_, 3, 1, 5, 1)}));
      DESERT_QUEEN = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade(createEgg(false, 0), 10, 1, 10, 1), new MyrmexTrades.ItemsForDesertResinTrade(createEgg(false, 1), 20, 1, 8, 1), new MyrmexTrades.ItemsForDesertResinTrade(createEgg(false, 2), 30, 1, 5, 1), new MyrmexTrades.ItemsForDesertResinTrade(createEgg(false, 3), 40, 1, 3, 1)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForDesertResinTrade(createEgg(false, 4), 60, 1, 2, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42616_, 15, 1, 9, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42415_, 25, 1, 9, 1)}));
      JUNGLE_QUEEN = createTrades(ImmutableMap.of(1, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade(createEgg(true, 0), 10, 1, 10, 1), new MyrmexTrades.ItemsForJungleResinTrade(createEgg(true, 1), 20, 1, 8, 1), new MyrmexTrades.ItemsForJungleResinTrade(createEgg(true, 2), 30, 1, 5, 1), new MyrmexTrades.ItemsForJungleResinTrade(createEgg(true, 3), 40, 1, 3, 1)}, 2, new ItemListing[]{new MyrmexTrades.ItemsForJungleResinTrade(createEgg(true, 4), 60, 1, 2, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42616_, 15, 1, 9, 1), new MyrmexTrades.ItemsForDesertResinTrade(Items.f_42415_, 25, 1, 9, 1)}));
   }

   static class DesertResinForItemsTrade implements ItemListing {
      private final Item tradeItem;
      private final int count;
      private final int maxUses;
      private final int xpValue;
      private final float priceMultiplier;

      public DesertResinForItemsTrade(ItemLike tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
         this.tradeItem = tradeItemIn.m_5456_();
         this.count = countIn;
         this.maxUses = maxUsesIn;
         this.xpValue = xpValueIn;
         this.priceMultiplier = 0.05F;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         ItemStack lvt_3_1_ = new ItemStack(this.tradeItem, this.count);
         return new MerchantOffer(lvt_3_1_, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get()), this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class ItemsForDesertResinTrade implements ItemListing {
      private final ItemStack stack;
      private final int emeraldCount;
      private final int itemCount;
      private final int maxUses;
      private final int exp;
      private final float multiplier;

      public ItemsForDesertResinTrade(Block sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
         this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUses, xpValue);
      }

      public ItemsForDesertResinTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int xpValue) {
         this((ItemStack)(new ItemStack(sellingItem)), emeraldCount, sellingItemCount, 12, xpValue);
      }

      public ItemsForDesertResinTrade(Item item, int DesertResin, int items, int maxUses, int exp) {
         this(new ItemStack(item), DesertResin, items, maxUses, exp);
      }

      public ItemsForDesertResinTrade(ItemStack stack, int DesertResin, int items, int maxUses, int exp) {
         this(stack, DesertResin, items, maxUses, exp, 0.05F);
      }

      public ItemsForDesertResinTrade(ItemStack stack, int DesertResin, int items, int maxUses, int exp, float multi) {
         this.stack = stack;
         this.emeraldCount = DesertResin;
         this.itemCount = items;
         this.maxUses = maxUses;
         this.exp = exp;
         this.multiplier = multi;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         ItemStack cloneStack = new ItemStack(this.stack.m_41720_(), this.itemCount);
         cloneStack.m_41751_(this.stack.m_41783_());
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get(), this.emeraldCount), cloneStack, this.maxUses, this.exp, this.multiplier);
      }
   }

   static class JungleResinForItemsTrade implements ItemListing {
      private final Item tradeItem;
      private final int count;
      private final int maxUses;
      private final int xpValue;
      private final float priceMultiplier;

      public JungleResinForItemsTrade(ItemLike tradeItemIn, int countIn, int maxUsesIn, int xpValueIn) {
         this.tradeItem = tradeItemIn.m_5456_();
         this.count = countIn;
         this.maxUses = maxUsesIn;
         this.xpValue = xpValueIn;
         this.priceMultiplier = 0.05F;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         ItemStack lvt_3_1_ = new ItemStack(this.tradeItem, this.count);
         return new MerchantOffer(lvt_3_1_, new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get()), this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class ItemsForJungleResinTrade implements ItemListing {
      private final ItemStack stack;
      private final int emeraldCount;
      private final int itemCount;
      private final int maxUses;
      private final int exp;
      private final float multiplier;

      public ItemsForJungleResinTrade(Block sellingItem, int emeraldCount, int sellingItemCount, int maxUses, int xpValue) {
         this(new ItemStack(sellingItem), emeraldCount, sellingItemCount, maxUses, xpValue);
      }

      public ItemsForJungleResinTrade(Item sellingItem, int emeraldCount, int sellingItemCount, int xpValue) {
         this((ItemStack)(new ItemStack(sellingItem)), emeraldCount, sellingItemCount, 12, xpValue);
      }

      public ItemsForJungleResinTrade(Item item, int JungleResin, int items, int maxUses, int exp) {
         this(new ItemStack(item), JungleResin, items, maxUses, exp);
      }

      public ItemsForJungleResinTrade(ItemStack stack, int JungleResin, int items, int maxUses, int exp) {
         this(stack, JungleResin, items, maxUses, exp, 0.05F);
      }

      public ItemsForJungleResinTrade(ItemStack stack, int JungleResin, int items, int maxUses, int exp, float multi) {
         this.stack = stack;
         this.emeraldCount = JungleResin;
         this.itemCount = items;
         this.maxUses = maxUses;
         this.exp = exp;
         this.multiplier = multi;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         ItemStack cloneStack = new ItemStack(this.stack.m_41720_(), this.itemCount);
         cloneStack.m_41751_(this.stack.m_41783_());
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get(), this.emeraldCount), cloneStack, this.maxUses, this.exp, this.multiplier);
      }
   }

   static class EnchantedItemForJungleResinTrade implements ItemListing {
      private final ItemStack sellingStack;
      private final int emeraldCount;
      private final int maxUses;
      private final int xpValue;
      private final float priceMultiplier;

      public EnchantedItemForJungleResinTrade(Item p_i50535_1_, int emeraldCount, int maxUses, int xpValue) {
         this(p_i50535_1_, emeraldCount, maxUses, xpValue, 0.05F);
      }

      public EnchantedItemForJungleResinTrade(Item sellItem, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
         this.sellingStack = new ItemStack(sellItem);
         this.emeraldCount = emeraldCount;
         this.maxUses = maxUses;
         this.xpValue = xpValue;
         this.priceMultiplier = priceMultiplier;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, RandomSource rand) {
         int lvt_3_1_ = 5 + rand.m_188503_(15);
         ItemStack lvt_4_1_ = EnchantmentHelper.m_220292_(rand, new ItemStack(this.sellingStack.m_41720_()), lvt_3_1_, false);
         int lvt_5_1_ = Math.min(this.emeraldCount + lvt_3_1_, 64);
         ItemStack lvt_6_1_ = new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get(), lvt_5_1_);
         return new MerchantOffer(lvt_6_1_, lvt_4_1_, this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class ItemWithPotionForJungleResinAndItemsTrade implements ItemListing {
      private final ItemStack potionStack;
      private final int potionCount;
      private final int emeraldCount;
      private final int maxUses;
      private final int xpValue;
      private final Item buyingItem;
      private final int buyingItemCount;
      private final float priceMultiplier;

      public ItemWithPotionForJungleResinAndItemsTrade(Item buyingItem, int buyingItemCount, Item p_i50526_3_, int p_i50526_4_, int emeralds, int maxUses, int xpValue) {
         this.potionStack = new ItemStack(p_i50526_3_);
         this.emeraldCount = emeralds;
         this.maxUses = maxUses;
         this.xpValue = xpValue;
         this.buyingItem = buyingItem;
         this.buyingItemCount = buyingItemCount;
         this.potionCount = p_i50526_4_;
         this.priceMultiplier = 0.05F;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, RandomSource rand) {
         ItemStack lvt_3_1_ = new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get(), this.emeraldCount);
         List<Potion> lvt_4_1_ = (List)BuiltInRegistries.f_256980_.m_123024_().filter((potion) -> {
            return !potion.m_43488_().isEmpty() && PotionBrewing.m_43511_(potion);
         }).collect(Collectors.toList());
         Potion lvt_5_1_ = (Potion)lvt_4_1_.get(rand.m_188503_(lvt_4_1_.size()));
         ItemStack lvt_6_1_ = PotionUtils.m_43549_(new ItemStack(this.potionStack.m_41720_(), this.potionCount), lvt_5_1_);
         return new MerchantOffer(lvt_3_1_, new ItemStack(this.buyingItem, this.buyingItemCount), lvt_6_1_, this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class ItemsForJungleResinAndItemsTrade implements ItemListing {
      private final ItemStack buyingItem;
      private final int buyingItemCount;
      private final int emeraldCount;
      private final ItemStack sellingItem;
      private final int sellingItemCount;
      private final int maxUses;
      private final int xpValue;
      private final float priceMultiplier;

      public ItemsForJungleResinAndItemsTrade(ItemLike buyingItem, int buyingItemCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
         this(buyingItem, buyingItemCount, 1, sellingItem, sellingItemCount, maxUses, xpValue);
      }

      public ItemsForJungleResinAndItemsTrade(ItemLike buyingItem, int buyingItemCount, int emeraldCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
         this.buyingItem = new ItemStack(buyingItem);
         this.buyingItemCount = buyingItemCount;
         this.emeraldCount = emeraldCount;
         this.sellingItem = new ItemStack(sellingItem);
         this.sellingItemCount = sellingItemCount;
         this.maxUses = maxUses;
         this.xpValue = xpValue;
         this.priceMultiplier = 0.05F;
      }

      @Nullable
      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.MYRMEX_JUNGLE_RESIN.get(), this.emeraldCount), new ItemStack(this.buyingItem.m_41720_(), this.buyingItemCount), new ItemStack(this.sellingItem.m_41720_(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class SuspiciousStewForEmeraldTrade implements ItemListing {
      final MobEffect effect;
      final int duration;
      final int xpValue;
      private final float priceMultiplier;

      public SuspiciousStewForEmeraldTrade(MobEffect effectIn, int durationIn, int xpValue) {
         this.effect = effectIn;
         this.duration = durationIn;
         this.xpValue = xpValue;
         this.priceMultiplier = 0.05F;
      }

      @Nullable
      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         ItemStack lvt_3_1_ = new ItemStack(Items.f_42718_, 1);
         SuspiciousStewItem.m_43258_(lvt_3_1_, this.effect, this.duration);
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get(), 1), lvt_3_1_, 12, this.xpValue, this.priceMultiplier);
      }
   }

   static class EnchantedItemForDesertResinTrade implements ItemListing {
      private final ItemStack sellingStack;
      private final int emeraldCount;
      private final int maxUses;
      private final int xpValue;
      private final float priceMultiplier;

      public EnchantedItemForDesertResinTrade(Item p_i50535_1_, int emeraldCount, int maxUses, int xpValue) {
         this(p_i50535_1_, emeraldCount, maxUses, xpValue, 0.05F);
      }

      public EnchantedItemForDesertResinTrade(Item sellItem, int emeraldCount, int maxUses, int xpValue, float priceMultiplier) {
         this.sellingStack = new ItemStack(sellItem);
         this.emeraldCount = emeraldCount;
         this.maxUses = maxUses;
         this.xpValue = xpValue;
         this.priceMultiplier = priceMultiplier;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, RandomSource rand) {
         int lvt_3_1_ = 5 + rand.m_188503_(15);
         ItemStack lvt_4_1_ = EnchantmentHelper.m_220292_(rand, new ItemStack(this.sellingStack.m_41720_()), lvt_3_1_, false);
         int lvt_5_1_ = Math.min(this.emeraldCount + lvt_3_1_, 64);
         ItemStack lvt_6_1_ = new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get(), lvt_5_1_);
         return new MerchantOffer(lvt_6_1_, lvt_4_1_, this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class ItemWithPotionForDesertResinAndItemsTrade implements ItemListing {
      private final ItemStack potionStack;
      private final int potionCount;
      private final int emeraldCount;
      private final int maxUses;
      private final int xpValue;
      private final Item buyingItem;
      private final int buyingItemCount;
      private final float priceMultiplier;

      public ItemWithPotionForDesertResinAndItemsTrade(Item buyingItem, int buyingItemCount, Item p_i50526_3_, int p_i50526_4_, int emeralds, int maxUses, int xpValue) {
         this.potionStack = new ItemStack(p_i50526_3_);
         this.emeraldCount = emeralds;
         this.maxUses = maxUses;
         this.xpValue = xpValue;
         this.buyingItem = buyingItem;
         this.buyingItemCount = buyingItemCount;
         this.potionCount = p_i50526_4_;
         this.priceMultiplier = 0.05F;
      }

      public MerchantOffer m_213663_(@NotNull Entity trader, RandomSource rand) {
         ItemStack lvt_3_1_ = new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get(), this.emeraldCount);
         List<Potion> lvt_4_1_ = (List)BuiltInRegistries.f_256980_.m_123024_().filter((potion) -> {
            return !potion.m_43488_().isEmpty() && PotionBrewing.m_43511_(potion);
         }).collect(Collectors.toList());
         Potion lvt_5_1_ = (Potion)lvt_4_1_.get(rand.m_188503_(lvt_4_1_.size()));
         ItemStack lvt_6_1_ = PotionUtils.m_43549_(new ItemStack(this.potionStack.m_41720_(), this.potionCount), lvt_5_1_);
         return new MerchantOffer(lvt_3_1_, new ItemStack(this.buyingItem, this.buyingItemCount), lvt_6_1_, this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }

   static class ItemsForDesertResinAndItemsTrade implements ItemListing {
      private final ItemStack buyingItem;
      private final int buyingItemCount;
      private final int emeraldCount;
      private final ItemStack sellingItem;
      private final int sellingItemCount;
      private final int maxUses;
      private final int xpValue;
      private final float priceMultiplier;

      public ItemsForDesertResinAndItemsTrade(ItemLike buyingItem, int buyingItemCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
         this(buyingItem, buyingItemCount, 1, sellingItem, sellingItemCount, maxUses, xpValue);
      }

      public ItemsForDesertResinAndItemsTrade(ItemLike buyingItem, int buyingItemCount, int emeraldCount, Item sellingItem, int sellingItemCount, int maxUses, int xpValue) {
         this.buyingItem = new ItemStack(buyingItem);
         this.buyingItemCount = buyingItemCount;
         this.emeraldCount = emeraldCount;
         this.sellingItem = new ItemStack(sellingItem);
         this.sellingItemCount = sellingItemCount;
         this.maxUses = maxUses;
         this.xpValue = xpValue;
         this.priceMultiplier = 0.05F;
      }

      @Nullable
      public MerchantOffer m_213663_(@NotNull Entity trader, @NotNull RandomSource rand) {
         return new MerchantOffer(new ItemStack((ItemLike)IafItemRegistry.MYRMEX_DESERT_RESIN.get(), this.emeraldCount), new ItemStack(this.buyingItem.m_41720_(), this.buyingItemCount), new ItemStack(this.sellingItem.m_41720_(), this.sellingItemCount), this.maxUses, this.xpValue, this.priceMultiplier);
      }
   }
}
