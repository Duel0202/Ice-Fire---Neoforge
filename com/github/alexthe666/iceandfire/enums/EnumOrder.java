package com.github.alexthe666.iceandfire.enums;

public enum EnumOrder {
   WANDER,
   SIT,
   FOLLOW,
   SLEEP;

   public final EnumOrder next() {
      return values()[(this.ordinal() + 1) % values().length];
   }

   // $FF: synthetic method
   private static EnumOrder[] $values() {
      return new EnumOrder[]{WANDER, SIT, FOLLOW, SLEEP};
   }
}
