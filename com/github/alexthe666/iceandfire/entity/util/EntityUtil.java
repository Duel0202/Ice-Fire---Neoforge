package com.github.alexthe666.iceandfire.entity.util;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityMutlipartPart;
import java.util.UUID;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EntityUtil {
   public static void updatePart(@Nullable EntityMutlipartPart part, @NotNull LivingEntity parent) {
      if (part != null) {
         Level var3 = parent.m_9236_();
         if (var3 instanceof ServerLevel) {
            ServerLevel serverLevel = (ServerLevel)var3;
            if (!parent.m_213877_()) {
               if (!part.shouldContinuePersisting()) {
                  UUID uuid = part.m_20148_();
                  Entity existing = serverLevel.m_8791_(uuid);
                  if (existing != null && existing != part) {
                     while(serverLevel.m_8791_(uuid) != null) {
                        uuid = Mth.m_216261_(parent.m_217043_());
                     }

                     IceAndFire.LOGGER.debug("Updated the UUID of [{}] due to a clash with [{}]", part, existing);
                  }

                  part.m_20084_(uuid);
                  serverLevel.m_7967_(part);
               }

               part.setParent(parent);
               return;
            }
         }
      }

   }
}
