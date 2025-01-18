package com.github.alexthe666.iceandfire.entity.util;

import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Collections;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BlockLaunchExplosion extends Explosion {
   private final float size;
   private final Level world;
   private final double x;
   private final double y;
   private final double z;
   private final BlockInteraction mode;

   public BlockLaunchExplosion(Level world, Mob entity, double x, double y, double z, float size) {
      this(world, entity, x, y, z, size, BlockInteraction.DESTROY);
   }

   public BlockLaunchExplosion(Level world, Mob entity, double x, double y, double z, float size, BlockInteraction mode) {
      this(world, entity, (DamageSource)null, x, y, z, size, mode);
   }

   public BlockLaunchExplosion(Level world, Mob entity, DamageSource source, double x, double y, double z, float size, BlockInteraction mode) {
      super(world, entity, source, (ExplosionDamageCalculator)null, x, y, z, size, false, mode);
      this.world = world;
      this.size = size;
      this.x = x;
      this.y = y;
      this.z = z;
      this.mode = mode;
   }

   private static void handleExplosionDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> dropPositionArray, ItemStack stack, BlockPos pos) {
      int i = dropPositionArray.size();

      for(int j = 0; j < i; ++j) {
         Pair<ItemStack, BlockPos> pair = (Pair)dropPositionArray.get(j);
         ItemStack itemstack = (ItemStack)pair.getFirst();
         if (ItemEntity.m_32026_(itemstack, stack)) {
            ItemStack itemstack1 = ItemEntity.m_32029_(itemstack, stack, 16);
            dropPositionArray.set(j, Pair.of(itemstack1, (BlockPos)pair.getSecond()));
            if (stack.m_41619_()) {
               return;
            }
         }
      }

      dropPositionArray.add(Pair.of(stack, pos));
   }

   public void m_46075_(boolean spawnParticles) {
      if (this.world.f_46443_) {
         this.world.m_7785_(this.x, this.y, this.z, SoundEvents.f_11913_, SoundSource.BLOCKS, 4.0F, (1.0F + (this.world.f_46441_.m_188501_() - this.world.f_46441_.m_188501_()) * 0.2F) * 0.7F, false);
      }

      boolean flag = this.mode != BlockInteraction.KEEP;
      if (spawnParticles) {
         if (!(this.size < 2.0F) && flag) {
            this.world.m_7106_(ParticleTypes.f_123812_, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
         } else {
            this.world.m_7106_(ParticleTypes.f_123813_, this.x, this.y, this.z, 1.0D, 0.0D, 0.0D);
         }
      }

      if (flag) {
         ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList();
         Collections.shuffle(this.m_46081_(), ThreadLocalRandom.current());
         Iterator var4 = this.m_46081_().iterator();

         while(var4.hasNext()) {
            BlockPos blockpos = (BlockPos)var4.next();
            BlockState blockstate = this.world.m_8055_(blockpos);
            if (!blockstate.m_60795_()) {
               BlockPos blockpos1 = blockpos.m_7949_();
               this.world.m_46473_().m_6180_("explosion_blocks");
               Vec3 Vector3d = new Vec3(this.x, this.y, this.z);
               blockstate.onBlockExploded(this.world, blockpos, this);
               FallingBlockEntity fallingBlockEntity = new FallingBlockEntity(EntityType.f_20450_, this.world);
               fallingBlockEntity.m_31959_(blockpos1);
               fallingBlockEntity.m_6034_((double)blockpos1.m_123341_() + 0.5D, (double)blockpos1.m_123342_() + 0.5D, (double)blockpos1.m_123343_() + 0.5D);
               double d5 = fallingBlockEntity.m_20185_() - this.x;
               double d7 = fallingBlockEntity.m_20188_() - this.y;
               double d9 = fallingBlockEntity.m_20189_() - this.z;
               float f3 = this.size * 2.0F;
               double d12 = Math.sqrt(fallingBlockEntity.m_20238_(Vector3d)) / (double)f3;
               double d14 = (double)m_46064_(Vector3d, fallingBlockEntity);
               double d11 = (1.0D - d12) * d14;
               fallingBlockEntity.m_20256_(fallingBlockEntity.m_20184_().m_82520_(d5 * d11, d7 * d11, d9 * d11));
               this.world.m_46473_().m_7238_();
            }
         }

         ObjectListIterator var23 = objectarraylist.iterator();

         while(var23.hasNext()) {
            Pair<ItemStack, BlockPos> pair = (Pair)var23.next();
            Block.m_49840_(this.world, (BlockPos)pair.getSecond(), (ItemStack)pair.getFirst());
         }
      }

   }
}
