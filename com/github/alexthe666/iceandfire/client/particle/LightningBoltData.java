package com.github.alexthe666.iceandfire.client.particle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.tuple.Pair;
import org.joml.Vector4f;

public class LightningBoltData {
   private final Random random;
   private final LightningBoltData.BoltRenderInfo renderInfo;
   private final Vec3 start;
   private final Vec3 end;
   private final int segments;
   private int count;
   private float size;
   private int lifespan;
   private LightningBoltData.SpawnFunction spawnFunction;
   private LightningBoltData.FadeFunction fadeFunction;

   public LightningBoltData(Vec3 start, Vec3 end) {
      this(LightningBoltData.BoltRenderInfo.DEFAULT, start, end, (int)Math.sqrt(start.m_82554_(end) * 100.0D));
   }

   public LightningBoltData(LightningBoltData.BoltRenderInfo info, Vec3 start, Vec3 end, int segments) {
      this.random = new Random();
      this.count = 1;
      this.size = 0.1F;
      this.lifespan = 30;
      this.spawnFunction = LightningBoltData.SpawnFunction.delay(60.0F);
      this.fadeFunction = LightningBoltData.FadeFunction.fade(0.5F);
      this.renderInfo = info;
      this.start = start;
      this.end = end;
      this.segments = segments;
   }

   public LightningBoltData count(int count) {
      this.count = count;
      return this;
   }

   public LightningBoltData size(float size) {
      this.size = size;
      return this;
   }

   public LightningBoltData spawn(LightningBoltData.SpawnFunction spawnFunction) {
      this.spawnFunction = spawnFunction;
      return this;
   }

   public LightningBoltData fade(LightningBoltData.FadeFunction fadeFunction) {
      this.fadeFunction = fadeFunction;
      return this;
   }

   public LightningBoltData lifespan(int lifespan) {
      this.lifespan = lifespan;
      return this;
   }

   public int getLifespan() {
      return this.lifespan;
   }

   public LightningBoltData.SpawnFunction getSpawnFunction() {
      return this.spawnFunction;
   }

   public LightningBoltData.FadeFunction getFadeFunction() {
      return this.fadeFunction;
   }

   public Vector4f getColor() {
      return this.renderInfo.color;
   }

   public List<LightningBoltData.BoltQuads> generate() {
      List<LightningBoltData.BoltQuads> quads = new ArrayList();
      Vec3 diff = this.end.m_82546_(this.start);
      float totalDistance = (float)diff.m_82553_();

      for(int i = 0; i < this.count; ++i) {
         LinkedList<LightningBoltData.BoltInstructions> drawQueue = new LinkedList();
         drawQueue.add(new LightningBoltData.BoltInstructions(this.start, 0.0F, new Vec3(0.0D, 0.0D, 0.0D), (LightningBoltData.QuadCache)null, false));

         while(!drawQueue.isEmpty()) {
            LightningBoltData.BoltInstructions data = (LightningBoltData.BoltInstructions)drawQueue.poll();
            Vec3 perpendicularDist = data.perpendicularDist;
            float progress = data.progress + 1.0F / (float)this.segments * (1.0F - this.renderInfo.parallelNoise + this.random.nextFloat() * this.renderInfo.parallelNoise * 2.0F);
            Vec3 segmentEnd;
            float boltSize;
            if (progress >= 1.0F) {
               segmentEnd = this.end;
            } else {
               boltSize = this.renderInfo.spreadFunction.getMaxSpread(progress);
               float maxDiff = this.renderInfo.spreadFactor * boltSize * totalDistance * this.renderInfo.randomFunction.getRandom(this.random);
               Vec3 randVec = findRandomOrthogonalVector(diff, this.random);
               perpendicularDist = this.renderInfo.segmentSpreader.getSegmentAdd(perpendicularDist, randVec, maxDiff, boltSize, progress);
               segmentEnd = this.start.m_82549_(diff.m_82490_((double)progress)).m_82549_(perpendicularDist);
            }

            boltSize = this.size * (0.5F + (1.0F - progress) * 0.5F);
            Pair<LightningBoltData.BoltQuads, LightningBoltData.QuadCache> quadData = this.createQuads(data.cache, data.start, segmentEnd, boltSize);
            quads.add((LightningBoltData.BoltQuads)quadData.getLeft());
            if (segmentEnd == this.end) {
               break;
            }

            if (!data.isBranch) {
               drawQueue.add(new LightningBoltData.BoltInstructions(segmentEnd, progress, perpendicularDist, (LightningBoltData.QuadCache)quadData.getRight(), false));
            } else if (this.random.nextFloat() < this.renderInfo.branchContinuationFactor) {
               drawQueue.add(new LightningBoltData.BoltInstructions(segmentEnd, progress, perpendicularDist, (LightningBoltData.QuadCache)quadData.getRight(), true));
            }

            while(this.random.nextFloat() < this.renderInfo.branchInitiationFactor * (1.0F - progress)) {
               drawQueue.add(new LightningBoltData.BoltInstructions(segmentEnd, progress, perpendicularDist, (LightningBoltData.QuadCache)quadData.getRight(), true));
            }
         }
      }

      return quads;
   }

   private static Vec3 findRandomOrthogonalVector(Vec3 vec, Random rand) {
      Vec3 newVec = new Vec3(-0.5D + rand.nextDouble(), -0.5D + rand.nextDouble(), -0.5D + rand.nextDouble());
      return vec.m_82537_(newVec).m_82541_();
   }

   private Pair<LightningBoltData.BoltQuads, LightningBoltData.QuadCache> createQuads(LightningBoltData.QuadCache cache, Vec3 startPos, Vec3 end, float size) {
      Vec3 diff = end.m_82546_(startPos);
      Vec3 rightAdd = diff.m_82537_(new Vec3(0.5D, 0.5D, 0.5D)).m_82541_().m_82490_((double)size);
      Vec3 backAdd = diff.m_82537_(rightAdd).m_82541_().m_82490_((double)size);
      Vec3 rightAddSplit = rightAdd.m_82490_(0.5D);
      Vec3 start = cache != null ? cache.prevEnd : startPos;
      Vec3 startRight = cache != null ? cache.prevEndRight : start.m_82549_(rightAdd);
      Vec3 startBack = cache != null ? cache.prevEndBack : start.m_82549_(rightAddSplit).m_82549_(backAdd);
      Vec3 endRight = end.m_82549_(rightAdd);
      Vec3 endBack = end.m_82549_(rightAddSplit).m_82549_(backAdd);
      LightningBoltData.BoltQuads quads = new LightningBoltData.BoltQuads();
      quads.addQuad(start, end, endRight, startRight);
      quads.addQuad(startRight, endRight, end, start);
      quads.addQuad(startRight, endRight, endBack, startBack);
      quads.addQuad(startBack, endBack, endRight, startRight);
      return Pair.of(quads, new LightningBoltData.QuadCache(end, endRight, endBack));
   }

   public static class BoltRenderInfo {
      public static final LightningBoltData.BoltRenderInfo DEFAULT = new LightningBoltData.BoltRenderInfo();
      public static final LightningBoltData.BoltRenderInfo ELECTRICITY = electricity();
      private float parallelNoise = 0.1F;
      private float spreadFactor = 0.1F;
      private float branchInitiationFactor = 0.0F;
      private float branchContinuationFactor = 0.0F;
      private Vector4f color = new Vector4f(0.45F, 0.45F, 0.5F, 0.8F);
      private final LightningBoltData.RandomFunction randomFunction;
      private final LightningBoltData.SpreadFunction spreadFunction;
      private LightningBoltData.SegmentSpreader segmentSpreader;

      public static LightningBoltData.BoltRenderInfo electricity() {
         return new LightningBoltData.BoltRenderInfo(0.5F, 0.25F, 0.25F, 0.15F, new Vector4f(0.7F, 0.45F, 0.89F, 0.8F), 0.8F);
      }

      public BoltRenderInfo() {
         this.randomFunction = LightningBoltData.RandomFunction.GAUSSIAN;
         this.spreadFunction = LightningBoltData.SpreadFunction.SINE;
         this.segmentSpreader = LightningBoltData.SegmentSpreader.NO_MEMORY;
      }

      public BoltRenderInfo(float parallelNoise, float spreadFactor, float branchInitiationFactor, float branchContinuationFactor, Vector4f color, float closeness) {
         this.randomFunction = LightningBoltData.RandomFunction.GAUSSIAN;
         this.spreadFunction = LightningBoltData.SpreadFunction.SINE;
         this.segmentSpreader = LightningBoltData.SegmentSpreader.NO_MEMORY;
         this.parallelNoise = parallelNoise;
         this.spreadFactor = spreadFactor;
         this.branchInitiationFactor = branchInitiationFactor;
         this.branchContinuationFactor = branchContinuationFactor;
         this.color = color;
         this.segmentSpreader = LightningBoltData.SegmentSpreader.memory(closeness);
      }
   }

   public interface SpawnFunction {
      LightningBoltData.SpawnFunction NO_DELAY = (rand) -> {
         return Pair.of(0.0F, 0.0F);
      };
      LightningBoltData.SpawnFunction CONSECUTIVE = new LightningBoltData.SpawnFunction() {
         public Pair<Float, Float> getSpawnDelayBounds(Random rand) {
            return Pair.of(0.0F, 0.0F);
         }

         public boolean isConsecutive() {
            return true;
         }
      };

      static LightningBoltData.SpawnFunction delay(float delay) {
         return (rand) -> {
            return Pair.of(delay, delay);
         };
      }

      static LightningBoltData.SpawnFunction noise(float delay, float noise) {
         return (rand) -> {
            return Pair.of(delay - noise, delay + noise);
         };
      }

      Pair<Float, Float> getSpawnDelayBounds(Random var1);

      default float getSpawnDelay(Random rand) {
         Pair<Float, Float> bounds = this.getSpawnDelayBounds(rand);
         return (Float)bounds.getLeft() + ((Float)bounds.getRight() - (Float)bounds.getLeft()) * rand.nextFloat();
      }

      default boolean isConsecutive() {
         return false;
      }
   }

   public interface FadeFunction {
      LightningBoltData.FadeFunction NONE = (totalBolts, lifeScale) -> {
         return Pair.of(0, totalBolts);
      };

      static LightningBoltData.FadeFunction fade(float fade) {
         return (totalBolts, lifeScale) -> {
            int start = lifeScale > 1.0F - fade ? (int)((float)totalBolts * (lifeScale - (1.0F - fade)) / fade) : 0;
            int end = lifeScale < fade ? (int)((float)totalBolts * (lifeScale / fade)) : totalBolts;
            return Pair.of(start, end);
         };
      }

      Pair<Integer, Integer> getRenderBounds(int var1, float var2);
   }

   protected static class BoltInstructions {
      private final Vec3 start;
      private final Vec3 perpendicularDist;
      private final LightningBoltData.QuadCache cache;
      private final float progress;
      private final boolean isBranch;

      private BoltInstructions(Vec3 start, float progress, Vec3 perpendicularDist, LightningBoltData.QuadCache cache, boolean isBranch) {
         this.start = start;
         this.perpendicularDist = perpendicularDist;
         this.progress = progress;
         this.cache = cache;
         this.isBranch = isBranch;
      }
   }

   private static class QuadCache {
      private final Vec3 prevEnd;
      private final Vec3 prevEndRight;
      private final Vec3 prevEndBack;

      private QuadCache(Vec3 prevEnd, Vec3 prevEndRight, Vec3 prevEndBack) {
         this.prevEnd = prevEnd;
         this.prevEndRight = prevEndRight;
         this.prevEndBack = prevEndBack;
      }
   }

   public interface SpreadFunction {
      LightningBoltData.SpreadFunction LINEAR_ASCENT = (progress) -> {
         return progress;
      };
      LightningBoltData.SpreadFunction LINEAR_ASCENT_DESCENT = (progress) -> {
         return (progress - Math.max(0.0F, 2.0F * progress - 1.0F)) / 0.5F;
      };
      LightningBoltData.SpreadFunction SINE = (progress) -> {
         return Mth.m_14031_((float)(3.141592653589793D * (double)progress));
      };

      float getMaxSpread(float var1);
   }

   public interface RandomFunction {
      LightningBoltData.RandomFunction UNIFORM = Random::nextFloat;
      LightningBoltData.RandomFunction GAUSSIAN = (rand) -> {
         return (float)rand.nextGaussian();
      };

      float getRandom(Random var1);
   }

   public interface SegmentSpreader {
      LightningBoltData.SegmentSpreader NO_MEMORY = (perpendicularDist, randVec, maxDiff, scale, progress) -> {
         return randVec.m_82490_((double)maxDiff);
      };

      static LightningBoltData.SegmentSpreader memory(float memoryFactor) {
         return (perpendicularDist, randVec, maxDiff, spreadScale, progress) -> {
            float nextDiff = maxDiff * (1.0F - memoryFactor);
            Vec3 cur = randVec.m_82490_((double)nextDiff);
            if (progress > 0.5F) {
               cur = cur.m_82549_(perpendicularDist.m_82490_((double)(-1.0F * (1.0F - spreadScale))));
            }

            return perpendicularDist.m_82549_(cur);
         };
      }

      Vec3 getSegmentAdd(Vec3 var1, Vec3 var2, float var3, float var4, float var5);
   }

   public class BoltQuads {
      private final List<Vec3> vecs = new ArrayList();

      protected void addQuad(Vec3... quadVecs) {
         this.vecs.addAll(Arrays.asList(quadVecs));
      }

      public List<Vec3> getVecs() {
         return this.vecs;
      }
   }
}
