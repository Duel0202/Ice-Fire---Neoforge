package com.github.alexthe666.iceandfire.entity.ai;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.level.pathfinder.SwimNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public class SeaSerpentPathNavigator extends PathNavigation {
   public SeaSerpentPathNavigator(Mob entitylivingIn, Level worldIn) {
      super(entitylivingIn, worldIn);
   }

   @NotNull
   protected PathFinder m_5532_(int p_179679_1_) {
      this.f_26508_ = new SwimNodeEvaluator(true);
      return new PathFinder(this.f_26508_, p_179679_1_);
   }

   protected boolean m_7632_() {
      return true;
   }

   @NotNull
   protected Vec3 m_7475_() {
      return new Vec3(this.f_26494_.m_20185_(), this.f_26494_.m_20227_(0.5D), this.f_26494_.m_20189_());
   }

   public void m_7638_() {
      ++this.f_26498_;
      if (this.f_26506_) {
         this.m_26569_();
      }

      if (!this.m_26571_()) {
         Vec3 lvt_1_2_;
         if (this.m_7632_()) {
            this.m_7636_();
         } else if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
            lvt_1_2_ = this.f_26496_.m_77380_(this.f_26494_);
            if (Mth.m_14107_(this.f_26494_.m_20185_()) == Mth.m_14107_(lvt_1_2_.f_82479_) && Mth.m_14107_(this.f_26494_.m_20186_()) == Mth.m_14107_(lvt_1_2_.f_82480_) && Mth.m_14107_(this.f_26494_.m_20189_()) == Mth.m_14107_(lvt_1_2_.f_82481_)) {
               this.f_26496_.m_77374_();
            }
         }

         DebugPackets.m_133703_(this.f_26495_, this.f_26494_, this.f_26496_, this.f_26505_);
         if (!this.m_26571_()) {
            lvt_1_2_ = this.f_26496_.m_77380_(this.f_26494_);
            this.f_26494_.m_21566_().m_6849_(lvt_1_2_.f_82479_, lvt_1_2_.f_82480_, lvt_1_2_.f_82481_, this.f_26497_);
         }
      }

   }

   protected void m_7636_() {
      if (this.f_26496_ != null) {
         Vec3 entityPos = this.m_7475_();
         float entityWidth = this.f_26494_.m_20205_();
         float lvt_3_1_ = entityWidth > 0.75F ? entityWidth / 2.0F : 0.75F - entityWidth / 2.0F;
         Vec3 lvt_4_1_ = this.f_26494_.m_20184_();
         if (Math.abs(lvt_4_1_.f_82479_) > 0.2D || Math.abs(lvt_4_1_.f_82481_) > 0.2D) {
            lvt_3_1_ = (float)((double)lvt_3_1_ * lvt_4_1_.m_82553_() * 6.0D);
         }

         Vec3 lvt_6_1_ = Vec3.m_82512_(this.f_26496_.m_77400_());
         if (Math.abs(this.f_26494_.m_20185_() - lvt_6_1_.f_82479_) < (double)lvt_3_1_ && Math.abs(this.f_26494_.m_20189_() - lvt_6_1_.f_82481_) < (double)lvt_3_1_ && Math.abs(this.f_26494_.m_20186_() - lvt_6_1_.f_82480_) < (double)(lvt_3_1_ * 2.0F)) {
            this.f_26496_.m_77374_();
         }

         for(int lvt_7_1_ = Math.min(this.f_26496_.m_77399_() + 6, this.f_26496_.m_77398_() - 1); lvt_7_1_ > this.f_26496_.m_77399_(); --lvt_7_1_) {
            lvt_6_1_ = this.f_26496_.m_77382_(this.f_26494_, lvt_7_1_);
            if (lvt_6_1_.m_82557_(entityPos) <= 36.0D && this.m_183431_(entityPos, lvt_6_1_)) {
               this.f_26496_.m_77393_(lvt_7_1_);
               break;
            }
         }

         this.m_6481_(entityPos);
      }

   }

   protected void m_6481_(@NotNull Vec3 positionVec3) {
      if (this.f_26498_ - this.f_26499_ > 100) {
         if (positionVec3.m_82557_(this.f_26500_) < 2.25D) {
            this.m_26573_();
         }

         this.f_26499_ = this.f_26498_;
         this.f_26500_ = positionVec3;
      }

      if (this.f_26496_ != null && !this.f_26496_.m_77392_()) {
         Vec3i lvt_2_1_ = this.f_26496_.m_77400_();
         if (lvt_2_1_.equals(this.f_26501_)) {
            this.f_26502_ += Util.m_137550_() - this.f_26503_;
         } else {
            this.f_26501_ = lvt_2_1_;
            double lvt_3_1_ = positionVec3.m_82554_(Vec3.m_82512_(this.f_26501_));
            this.f_26504_ = this.f_26494_.m_6113_() > 0.0F ? lvt_3_1_ / (double)this.f_26494_.m_6113_() * 100.0D : 0.0D;
         }

         if (this.f_26504_ > 0.0D && (double)this.f_26502_ > this.f_26504_ * 2.0D) {
            this.f_26501_ = Vec3i.f_123288_;
            this.f_26502_ = 0L;
            this.f_26504_ = 0.0D;
            this.m_26573_();
         }

         this.f_26503_ = Util.m_137550_();
      }

   }

   protected boolean m_183431_(@NotNull Vec3 posVec31, Vec3 posVec32) {
      Vec3 lvt_6_1_ = new Vec3(posVec32.f_82479_, posVec32.f_82480_ + (double)this.f_26494_.m_20206_() * 0.5D, posVec32.f_82481_);
      return this.f_26495_.m_45547_(new ClipContext(posVec31, lvt_6_1_, Block.COLLIDER, Fluid.NONE, this.f_26494_)).m_6662_() == Type.MISS;
   }

   public boolean m_6342_(@NotNull BlockPos pos) {
      return !this.f_26495_.m_8055_(pos).m_60804_(this.f_26495_, pos);
   }

   public void m_7008_(boolean canSwim) {
   }
}
