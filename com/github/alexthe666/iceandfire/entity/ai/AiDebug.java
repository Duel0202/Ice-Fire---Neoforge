package com.github.alexthe666.iceandfire.entity.ai;

import com.github.alexthe666.iceandfire.IceAndFire;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import net.minecraft.world.entity.Mob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AiDebug {
   private static final List<Mob> entities = new ArrayList();
   private static final Logger LOGGER = LogManager.getLogger();

   private AiDebug() {
   }

   public static boolean isEnabled() {
      return IceAndFire.VERSION.equals("0.0NONE");
   }

   public static void logData() {
      List<Mob> entitiesCopy = new ArrayList(entities);
      Iterator var1 = entitiesCopy.iterator();

      while(var1.hasNext()) {
         Mob entity = (Mob)var1.next();
         if (!entity.m_6084_()) {
            entities.remove(entity);
         } else {
            List targets;
            if (entity.f_21345_ != null) {
               targets = (List)entity.f_21345_.m_25386_().map((goal) -> {
                  return goal.m_26015_().toString();
               }).collect(Collectors.toList());
               if (!targets.isEmpty()) {
                  LOGGER.debug("{} - GOALS: {}", entity, targets);
               }
            }

            if (entity.f_21346_ != null) {
               targets = (List)entity.f_21346_.m_25386_().map((goal) -> {
                  return goal.m_26015_().toString();
               }).collect(Collectors.toList());
               if (!targets.isEmpty()) {
                  LOGGER.debug("{} - TARGET: {}", entity, targets);
               }
            }
         }
      }

   }

   public static boolean contains(Mob entity) {
      return entities.contains(entity);
   }

   public static void addEntity(Mob entity) {
      if (entities.contains(entity)) {
         entities.remove(entity);
      } else {
         entities.add(entity);
      }

   }
}
