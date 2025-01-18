package com.github.alexthe666.iceandfire.client.model.util;

public enum EnumDragonModelTypes implements IEnumDragonModelTypes {
   FIRE_DRAGON_MODEL("fire"),
   ICE_DRAGON_MODEL("ice"),
   LIGHTNING_DRAGON_MODEL("lightning");

   String modelType;

   private EnumDragonModelTypes(String modelType) {
      this.modelType = modelType;
   }

   public String getModelType() {
      return this.modelType;
   }

   // $FF: synthetic method
   private static EnumDragonModelTypes[] $values() {
      return new EnumDragonModelTypes[]{FIRE_DRAGON_MODEL, ICE_DRAGON_MODEL, LIGHTNING_DRAGON_MODEL};
   }
}
