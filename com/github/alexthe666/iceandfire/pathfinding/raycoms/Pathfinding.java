package com.github.alexthe666.iceandfire.pathfinding.raycoms;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.thread.BlockableEventLoop;
import net.neoforge.common.util.LogicalSidedProvider;
import net.neoforge.fml.LogicalSide;
import org.jetbrains.annotations.NotNull;

public final class Pathfinding {
   private static final BlockingQueue<Runnable> jobQueue = new LinkedBlockingDeque();
   private static ThreadPoolExecutor executor;

   private Pathfinding() {
   }

   public static boolean isDebug() {
      return IafConfig.pathfindingDebug;
   }

   public static ThreadPoolExecutor getExecutor() {
      if (executor == null) {
         executor = new ThreadPoolExecutor(1, IafConfig.dragonPathfindingThreads, 10L, TimeUnit.SECONDS, jobQueue, new Pathfinding.IafThreadFactory());
      }

      return executor;
   }

   public static class IafThreadFactory implements ThreadFactory {
      public static int id;

      public Thread newThread(@NotNull Runnable runnable) throws RuntimeException {
         BlockableEventLoop<?> workqueue = (BlockableEventLoop)LogicalSidedProvider.WORKQUEUE.get(LogicalSide.SERVER);
         ClassLoader classLoader;
         if (workqueue.m_18695_()) {
            classLoader = Thread.currentThread().getContextClassLoader();
         } else if (workqueue instanceof MinecraftServer) {
            MinecraftServer server = (MinecraftServer)workqueue;
            classLoader = server.m_6304_().getContextClassLoader();
         } else {
            classLoader = (ClassLoader)CompletableFuture.supplyAsync(() -> {
               return Thread.currentThread().getContextClassLoader();
            }, workqueue).orTimeout(10L, TimeUnit.SECONDS).exceptionally((ex) -> {
               throw new RuntimeException(String.format("Couldn't join threads within timeout range. Tried joining '%s' on '%s'", Thread.currentThread().getName(), workqueue.m_7326_()));
            }).join();
         }

         int var10003 = id++;
         Thread thread = new Thread(runnable, "Ice and Fire Pathfinding Worker #" + var10003);
         thread.setDaemon(true);
         thread.setPriority(10);
         if (thread.getContextClassLoader() != classLoader) {
            IceAndFire.LOGGER.info("Corrected CCL of new Ice and Fire Pathfinding Thread, was: " + thread.getContextClassLoader().toString());
            thread.setContextClassLoader(classLoader);
         }

         thread.setUncaughtExceptionHandler((thread1, throwable) -> {
            IceAndFire.LOGGER.error("Ice and Fire Pathfinding Thread errored! ", throwable);
         });
         return thread;
      }
   }
}
