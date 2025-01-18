package com.github.alexthe666.iceandfire.client.texture;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class ArrayLayeredTexture extends AbstractTexture {
   private static final Logger LOGGER = LogManager.getLogger();
   public final List<String> layeredTextureNames;

   public ArrayLayeredTexture(List<String> textureNames) {
      this.layeredTextureNames = textureNames;
   }

   public void m_6704_(@NotNull ResourceManager manager) {
      Iterator<String> iterator = this.layeredTextureNames.iterator();
      String s = (String)iterator.next();
      Optional<Resource> iresource = manager.m_213713_(new ResourceLocation(s));
      if (iresource.isPresent()) {
         try {
            NativeImage nativeimage = NativeImage.m_85058_(((Resource)iresource.get()).m_215507_());

            while(true) {
               String s1;
               do {
                  if (!iterator.hasNext()) {
                     if (!RenderSystem.isOnRenderThreadOrInit()) {
                        RenderSystem.recordRenderCall(() -> {
                           this.loadImage(nativeimage);
                        });
                     } else {
                        this.loadImage(nativeimage);
                     }

                     return;
                  }

                  s1 = (String)iterator.next();
               } while(s1 == null);

               Optional<Resource> iresource1 = manager.m_213713_(new ResourceLocation(s1));
               NativeImage nativeimage1 = NativeImage.m_85058_(((Resource)iresource1.get()).m_215507_());

               for(int i = 0; i < Math.min(nativeimage1.m_85084_(), nativeimage.m_85084_()); ++i) {
                  for(int j = 0; j < Math.min(nativeimage1.m_84982_(), nativeimage.m_84982_()); ++j) {
                     nativeimage.m_166411_(j, i, nativeimage1.m_84985_(j, i));
                  }
               }
            }
         } catch (IOException var11) {
            LOGGER.error("Couldn't load layered image", var11);
         }
      } else {
         LOGGER.error("Couldn't load layered image");
      }

   }

   private void loadImage(NativeImage imageIn) {
      TextureUtil.prepareImage(this.m_117963_(), imageIn.m_84982_(), imageIn.m_85084_());
      imageIn.m_85040_(0, 0, 0, true);
   }
}
