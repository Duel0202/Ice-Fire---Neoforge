package com.github.alexthe666.iceandfire.client.model.util;

import com.github.alexthe666.citadel.client.model.TabulaModel;
import com.github.alexthe666.iceandfire.IceAndFire;
import java.io.IOException;
import java.util.HashMap;
import org.apache.logging.log4j.Logger;

public class DragonAnimationsLibrary {
   private static final HashMap<String, TabulaModel> models = new HashMap();

   private static String toKey(IEnumDragonPoses p, IEnumDragonModelTypes m) {
      String var10000 = p.getPose();
      return var10000 + m.getModelType();
   }

   public static TabulaModel getModel(IEnumDragonPoses pose, IEnumDragonModelTypes modelType) {
      TabulaModel result = (TabulaModel)models.get(toKey(pose, modelType));
      if (result == null) {
         Logger var10000 = IceAndFire.LOGGER;
         String var10001 = pose.getPose();
         var10000.error("No model defined for " + var10001 + modelType.getModelType() + " have you registered your animations?");
      }

      return result;
   }

   public static void registerSingle(IEnumDragonPoses pose, IEnumDragonModelTypes modelType) {
      registerSingle(pose, modelType, "iceandfire");
   }

   public static void register(IEnumDragonPoses[] poses, IEnumDragonModelTypes[] modelTypes) {
      IEnumDragonPoses[] var2 = poses;
      int var3 = poses.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         IEnumDragonPoses p = var2[var4];
         IEnumDragonModelTypes[] var6 = modelTypes;
         int var7 = modelTypes.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            IEnumDragonModelTypes m = var6[var8];
            registerSingle(p, m, "iceandfire");
         }
      }

   }

   public static void register(IEnumDragonPoses[] poses, IEnumDragonModelTypes[] modelTypes, String modID) {
      IEnumDragonPoses[] var3 = poses;
      int var4 = poses.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         IEnumDragonPoses p = var3[var5];
         IEnumDragonModelTypes[] var7 = modelTypes;
         int var8 = modelTypes.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            IEnumDragonModelTypes m = var7[var9];
            registerSingle(p, m, modID);
         }
      }

   }

   public static void registerSingle(IEnumDragonPoses pose, IEnumDragonModelTypes modelType, String modID) {
      String location = "/assets/" + modID + "/models/tabula/" + modelType.getModelType() + "dragon/" + modelType.getModelType() + "dragon_" + pose.getPose() + ".tbl";

      TabulaModel result;
      try {
         result = new TabulaModel(TabulaModelHandlerHelper.loadTabulaModel(location));
      } catch (NullPointerException | IOException var6) {
         IceAndFire.LOGGER.warn("Could not load " + location + ": " + var6.getMessage());
         return;
      }

      models.put(toKey(pose, modelType), result);
   }

   public static void registerReferences(IEnumDragonPoses[] poses, IEnumDragonModelTypes modelSource, IEnumDragonModelTypes[] modelDestinations) {
      for(int i = 0; i < poses.length; ++i) {
         registerReference(poses[i], modelSource, modelDestinations[i]);
      }

   }

   public static void registerReference(IEnumDragonPoses pose, IEnumDragonModelTypes modelSource, IEnumDragonModelTypes modelDestination) {
      TabulaModel source = getModel(pose, modelSource);
      String destKey = toKey(pose, modelDestination);
      if (source != null) {
         if (models.containsKey(destKey)) {
            IceAndFire.LOGGER.info("Overriding existing model '" + destKey + "' with reference to '" + toKey(pose, modelSource));
         }

         models.put(destKey, source);
      }
   }
}
