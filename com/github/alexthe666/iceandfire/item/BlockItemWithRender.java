package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.citadel.client.CitadelItemRenderProperties;
import com.github.alexthe666.iceandfire.client.render.tile.IceAndFireTEISR;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforge.common.util.NonNullLazy;

public class BlockItemWithRender extends BlockItem {
   public BlockItemWithRender(Block p_40565_, Properties p_40566_) {
      super(p_40565_, p_40566_);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new CitadelItemRenderProperties() {
         static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> {
            return new IceAndFireTEISR(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
         });

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return (BlockEntityWithoutLevelRenderer)renderer.get();
         }
      });
   }
}
