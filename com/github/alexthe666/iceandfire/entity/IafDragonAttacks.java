package com.github.alexthe666.iceandfire.entity;

public class IafDragonAttacks {
   public static enum Ground {
      BITE,
      SHAKE_PREY,
      TAIL_WHIP,
      WING_BLAST,
      FIRE;

      // $FF: synthetic method
      private static IafDragonAttacks.Ground[] $values() {
         return new IafDragonAttacks.Ground[]{BITE, SHAKE_PREY, TAIL_WHIP, WING_BLAST, FIRE};
      }
   }

   public static enum Air {
      SCORCH_STREAM,
      HOVER_BLAST,
      TACKLE;

      // $FF: synthetic method
      private static IafDragonAttacks.Air[] $values() {
         return new IafDragonAttacks.Air[]{SCORCH_STREAM, HOVER_BLAST, TACKLE};
      }
   }
}
