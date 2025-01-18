package com.github.alexthe666.iceandfire.message;

import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.neoforge.api.distmarker.Dist;
import net.neoforge.fml.DistExecutor;
import net.neoforge.network.NetworkEvent.Context;
import org.lwjgl.system.windows.MSG;

public class MessageHandler {
   public static <MSG> BiConsumer<MSG, Supplier<Context>> handle(BiConsumer<MSG, Supplier<Context>> messageConsumer) {
      return (msg, ctx) -> {
         ((Context)ctx.get()).enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> {
               return () -> {
                  messageConsumer.accept(msg, ctx);
               };
            });
         });
         ((Context)ctx.get()).enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
               return () -> {
                  messageConsumer.accept(msg, ctx);
               };
            });
         });
         ((Context)ctx.get()).setPacketHandled(true);
      };
   }

   public static BiConsumer<MSG, Supplier<Context>> handleServer(BiConsumer<MSG, Supplier<Context>> messageConsumer) {
      return (msg, ctx) -> {
         ((Context)ctx.get()).enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.DEDICATED_SERVER, () -> {
               return () -> {
                  messageConsumer.accept(msg, ctx);
               };
            });
         });
         ((Context)ctx.get()).setPacketHandled(true);
      };
   }

   public static BiConsumer<MSG, Supplier<Context>> handleClient(BiConsumer<MSG, Supplier<Context>> messageConsumer) {
      return (msg, ctx) -> {
         ((Context)ctx.get()).enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> {
               return () -> {
                  messageConsumer.accept(msg, ctx);
               };
            });
         });
         ((Context)ctx.get()).setPacketHandled(true);
      };
   }
}
