package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.RegistryObject;

public class IafTabRegistry {
   public static final DeferredRegister<CreativeModeTab> TAB_REGISTER;
   public static final List<Supplier<? extends Block>> TAB_BLOCKS_LIST;
   public static final List<Supplier<? extends Item>> TAB_ITEMS_LIST;
   public static final RegistryObject<CreativeModeTab> TAB_BLOCKS;
   public static final RegistryObject<CreativeModeTab> TAB_ITEMS;

   static {
      TAB_REGISTER = DeferredRegister.create(Registries.f_279569_, "iceandfire");
      TAB_BLOCKS_LIST = new ArrayList();
      TAB_ITEMS_LIST = new ArrayList();
      TAB_BLOCKS = TAB_REGISTER.register("blocks", () -> {
         return CreativeModeTab.builder().m_257941_(Component.m_237115_("itemGroup.iceandfire.blocks")).m_257737_(() -> {
            return new ItemStack((ItemLike)IafBlockRegistry.DRAGON_SCALE_RED.get());
         }).withTabsBefore(new ResourceKey[]{CreativeModeTabs.f_256731_}).m_257501_((params, output) -> {
            TAB_BLOCKS_LIST.forEach((block) -> {
               output.m_246326_((ItemLike)block.get());
            });
         }).m_257652_();
      });
      TAB_ITEMS = TAB_REGISTER.register("items", () -> {
         return CreativeModeTab.builder().m_257941_(Component.m_237115_("itemGroup.iceandfire.items")).m_257737_(() -> {
            return new ItemStack((ItemLike)IafItemRegistry.DRAGON_SKULL_FIRE.get());
         }).withTabsBefore(new ResourceKey[]{TAB_BLOCKS.getKey()}).m_257501_((params, output) -> {
            TAB_ITEMS_LIST.forEach((block) -> {
               output.m_246326_((ItemLike)block.get());
            });
         }).m_257652_();
      });
   }
}
