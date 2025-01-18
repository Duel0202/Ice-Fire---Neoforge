package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.api.event.DragonFireDamageWorldEvent;
import com.github.alexthe666.iceandfire.block.BlockCharedPath;
import com.github.alexthe666.iceandfire.block.BlockFallingReturningState;
import com.github.alexthe666.iceandfire.block.BlockReturningState;
import com.github.alexthe666.iceandfire.block.IDragonProof;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityDragonforgeInput;
import com.github.alexthe666.iceandfire.entity.util.BlockLaunchExplosion;
import com.github.alexthe666.iceandfire.entity.util.DragonUtils;
import com.github.alexthe666.iceandfire.misc.IafDamageRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.Explosion.BlockInteraction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforge.common.MinecraftForge;
import net.neoforge.event.ForgeEventFactory;

public class IafDragonDestructionManager {
   public static void destroyAreaBreath(Level level, BlockPos center, EntityDragonBase dragon) {
      if (!MinecraftForge.EVENT_BUS.post(new DragonFireDamageWorldEvent(dragon, (double)center.m_123341_(), (double)center.m_123342_(), (double)center.m_123343_()))) {
         int statusDuration;
         float damageScale;
         if (dragon.dragonType == DragonType.FIRE) {
            statusDuration = 5 + dragon.getDragonStage() * 5;
            damageScale = (float)IafConfig.dragonAttackDamageFire;
         } else if (dragon.dragonType == DragonType.ICE) {
            statusDuration = 50 * dragon.getDragonStage();
            damageScale = (float)IafConfig.dragonAttackDamageIce;
         } else {
            if (dragon.dragonType != DragonType.LIGHTNING) {
               return;
            }

            statusDuration = 3;
            damageScale = (float)IafConfig.dragonAttackDamageLightning;
         }

         double damageRadius = 3.5D;
         boolean canBreakBlocks = ForgeEventFactory.getMobGriefingEvent(level, dragon);
         if (dragon.getDragonStage() <= 3) {
            BlockPos.m_121990_(center.m_7918_(-1, -1, -1), center.m_7918_(1, 1, 1)).forEach((position) -> {
               BlockEntity patt2631$temp = level.m_7702_(position);
               if (patt2631$temp instanceof TileEntityDragonforgeInput) {
                  TileEntityDragonforgeInput forge = (TileEntityDragonforgeInput)patt2631$temp;
                  forge.onHitWithFlame();
               } else {
                  if (canBreakBlocks && DragonUtils.canGrief(dragon) && dragon.m_217043_().m_188499_()) {
                     attackBlock(level, dragon, position);
                  }

               }
            });
         } else {
            int radius = dragon.getDragonStage() == 4 ? 2 : 3;
            int x = radius + level.f_46441_.m_188503_(1);
            int y = radius + level.f_46441_.m_188503_(1);
            int z = radius + level.f_46441_.m_188503_(1);
            float f = (float)(x + y + z) * 0.333F + 0.5F;
            float ff = f * f;
            damageRadius = (double)(2.5F + f * 1.2F);
            BlockPos.m_121990_(center.m_7918_(-x, -y, -z), center.m_7918_(x, y, z)).forEach((position) -> {
               BlockEntity patt3565$temp = level.m_7702_(position);
               if (patt3565$temp instanceof TileEntityDragonforgeInput) {
                  TileEntityDragonforgeInput forge = (TileEntityDragonforgeInput)patt3565$temp;
                  forge.onHitWithFlame();
               } else {
                  if (canBreakBlocks && center.m_123331_(position) <= (double)ff && DragonUtils.canGrief(dragon) && level.f_46441_.m_188501_() > (float)center.m_123331_(position) / ff) {
                     attackBlock(level, dragon, position);
                  }

               }
            });
         }

         DamageSource damageSource = getDamageSource(dragon);
         float stageDamage = (float)dragon.getDragonStage() * damageScale;
         level.m_45976_(LivingEntity.class, new AABB((double)center.m_123341_() - damageRadius, (double)center.m_123342_() - damageRadius, (double)center.m_123343_() - damageRadius, (double)center.m_123341_() + damageRadius, (double)center.m_123342_() + damageRadius, (double)center.m_123343_() + damageRadius)).forEach((target) -> {
            if (!DragonUtils.onSameTeam(dragon, target) && !dragon.m_7306_(target) && dragon.m_142582_(target)) {
               target.m_6469_(damageSource, stageDamage);
               applyDragonEffect(target, dragon, statusDuration);
            }

         });
      }
   }

   public static void destroyAreaCharge(Level level, BlockPos center, EntityDragonBase dragon) {
      if (dragon != null) {
         if (!MinecraftForge.EVENT_BUS.post(new DragonFireDamageWorldEvent(dragon, (double)center.m_123341_(), (double)center.m_123342_(), (double)center.m_123343_()))) {
            int x = 2;
            int y = 2;
            int z = 2;
            boolean canBreakBlocks = DragonUtils.canGrief(dragon) && ForgeEventFactory.getMobGriefingEvent(level, dragon);
            float stageDamage;
            if (canBreakBlocks) {
               if (dragon.getDragonStage() <= 3) {
                  BlockPos.m_121990_(center.m_7918_(-x, -y, -z), center.m_7918_(x, y, z)).forEach((position) -> {
                     BlockState state = level.m_8055_(position);
                     if (!(state.m_60734_() instanceof IDragonProof)) {
                        if ((double)(dragon.m_217043_().m_188501_() * 3.0F) > center.m_123331_(position) && DragonUtils.canDragonBreak(state, dragon)) {
                           level.m_46961_(position, false);
                        }

                        if (dragon.m_217043_().m_188499_()) {
                           attackBlock(level, dragon, position, state);
                        }

                     }
                  });
               } else {
                  int radius = dragon.getDragonStage() == 4 ? 2 : 3;
                  x = radius + level.f_46441_.m_188503_(2);
                  y = radius + level.f_46441_.m_188503_(2);
                  z = radius + level.f_46441_.m_188503_(2);
                  stageDamage = (float)(x + y + z) * 0.333F + 0.5F;
                  float ff = stageDamage * stageDamage;
                  destroyBlocks(level, center, x, y, z, (double)ff, dragon);
                  ++x;
                  ++y;
                  ++z;
                  BlockPos.m_121990_(center.m_7918_(-x, -y, -z), center.m_7918_(x, y, z)).forEach((position) -> {
                     if (center.m_123331_(position) <= (double)ff) {
                        attackBlock(level, dragon, position);
                     }

                  });
               }
            }

            short statusDuration;
            if (dragon.dragonType == DragonType.FIRE) {
               statusDuration = 15;
            } else if (dragon.dragonType == DragonType.ICE) {
               statusDuration = 400;
            } else {
               if (dragon.dragonType != DragonType.LIGHTNING) {
                  return;
               }

               statusDuration = 9;
            }

            stageDamage = (float)Math.max(1, dragon.getDragonStage() - 1) * 2.0F;
            DamageSource damageSource = getDamageSource(dragon);
            level.m_45976_(LivingEntity.class, new AABB((double)center.m_123341_() - (double)x, (double)center.m_123342_() - (double)y, (double)center.m_123343_() - (double)z, (double)center.m_123341_() + (double)x, (double)center.m_123342_() + (double)y, (double)center.m_123343_() + (double)z)).forEach((target) -> {
               if (!dragon.m_7307_(target) && !dragon.m_7306_(target) && dragon.m_142582_(target)) {
                  target.m_6469_(damageSource, stageDamage);
                  applyDragonEffect(target, dragon, statusDuration);
               }

            });
            if (IafConfig.explosiveDragonBreath) {
               causeExplosion(level, center, dragon, damageSource, dragon.getDragonStage());
            }

         }
      }
   }

   private static DamageSource getDamageSource(EntityDragonBase dragon) {
      Player player = dragon.getRidingPlayer();
      if (dragon.dragonType == DragonType.FIRE) {
         return (DamageSource)(player != null ? IafDamageRegistry.causeIndirectDragonFireDamage(dragon, player) : IafDamageRegistry.causeDragonFireDamage(dragon));
      } else if (dragon.dragonType == DragonType.ICE) {
         return (DamageSource)(player != null ? IafDamageRegistry.causeIndirectDragonIceDamage(dragon, player) : IafDamageRegistry.causeDragonIceDamage(dragon));
      } else if (dragon.dragonType == DragonType.LIGHTNING) {
         return (DamageSource)(player != null ? IafDamageRegistry.causeIndirectDragonLightningDamage(dragon, player) : IafDamageRegistry.causeDragonLightningDamage(dragon));
      } else {
         return dragon.m_9236_().m_269111_().m_269333_(dragon);
      }
   }

   private static void attackBlock(Level level, EntityDragonBase dragon, BlockPos position, BlockState state) {
      if (!(state.m_60734_() instanceof IDragonProof) && DragonUtils.canDragonBreak(state, dragon)) {
         BlockState transformed;
         if (dragon.dragonType == DragonType.FIRE) {
            transformed = transformBlockFire(state);
         } else if (dragon.dragonType == DragonType.ICE) {
            transformed = transformBlockIce(state);
         } else {
            if (dragon.dragonType != DragonType.LIGHTNING) {
               return;
            }

            transformed = transformBlockLightning(state);
         }

         if (!transformed.m_60713_(state.m_60734_())) {
            level.m_46597_(position, transformed);
         }

         Block elementalBlock;
         boolean doPlaceBlock;
         if (dragon.dragonType == DragonType.FIRE) {
            elementalBlock = Blocks.f_50083_;
            doPlaceBlock = dragon.m_217043_().m_188499_();
         } else {
            if (dragon.dragonType != DragonType.ICE) {
               return;
            }

            elementalBlock = (Block)IafBlockRegistry.DRAGON_ICE_SPIKES.get();
            doPlaceBlock = dragon.m_217043_().m_188503_(9) == 0;
         }

         BlockState stateAbove = level.m_8055_(position.m_7494_());
         if (doPlaceBlock && transformed.m_280296_() && stateAbove.m_60819_().m_76178_() && !stateAbove.m_60815_() && state.m_60815_() && DragonUtils.canDragonBreak(stateAbove, dragon)) {
            level.m_46597_(position.m_7494_(), elementalBlock.m_49966_());
         }

      }
   }

   private static void attackBlock(Level level, EntityDragonBase dragon, BlockPos position) {
      attackBlock(level, dragon, position, level.m_8055_(position));
   }

   private static void applyDragonEffect(LivingEntity target, EntityDragonBase dragon, int statusDuration) {
      if (dragon.dragonType == DragonType.FIRE) {
         target.m_20254_(statusDuration);
      } else if (dragon.dragonType == DragonType.ICE) {
         EntityDataProvider.getCapability(target).ifPresent((data) -> {
            data.frozenData.setFrozen(target, statusDuration);
         });
      } else if (dragon.dragonType == DragonType.LIGHTNING) {
         double x = dragon.m_20185_() - target.m_20185_();
         double y = dragon.m_20189_() - target.m_20189_();
         target.m_147240_((double)statusDuration / 10.0D, x, y);
      }

   }

   private static void causeExplosion(Level world, BlockPos center, EntityDragonBase destroyer, DamageSource source, int stage) {
      BlockInteraction mode = ForgeEventFactory.getMobGriefingEvent(world, destroyer) ? BlockInteraction.DESTROY : BlockInteraction.KEEP;
      BlockLaunchExplosion explosion = new BlockLaunchExplosion(world, destroyer, source, (double)center.m_123341_(), (double)center.m_123342_(), (double)center.m_123343_(), (float)Math.min(2, stage - 2), mode);
      explosion.m_46061_();
      explosion.m_46075_(true);
   }

   private static void destroyBlocks(Level world, BlockPos center, int x, int y, int z, double radius2, Entity destroyer) {
      BlockPos.m_121990_(center.m_7918_(-x, -y, -z), center.m_7918_(x, y, z)).forEach((pos) -> {
         if (center.m_123331_(pos) <= radius2) {
            BlockState state = world.m_8055_(pos);
            if (state.m_60734_() instanceof IDragonProof) {
               return;
            }

            if ((double)(world.f_46441_.m_188501_() * 3.0F) > (double)((float)center.m_123331_(pos)) / radius2 && DragonUtils.canDragonBreak(state, destroyer)) {
               world.m_46961_(pos, false);
            }
         }

      });
   }

   public static BlockState transformBlockFire(BlockState in) {
      if (in.m_60734_() instanceof SpreadingSnowyDirtBlock) {
         return (BlockState)((Block)IafBlockRegistry.CHARRED_GRASS.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_60713_(Blocks.f_50493_)) {
         return (BlockState)((Block)IafBlockRegistry.CHARRED_DIRT.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_13029_) && in.m_60734_() == Blocks.f_49994_) {
         return (BlockState)((Block)IafBlockRegistry.CHARRED_GRAVEL.get()).m_49966_().m_61124_(BlockFallingReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_13061_) && (in.m_60734_() == Blocks.f_50652_ || in.m_60734_().m_7705_().contains("cobblestone"))) {
         return (BlockState)((Block)IafBlockRegistry.CHARRED_COBBLESTONE.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_13061_) && in.m_60734_() != IafBlockRegistry.CHARRED_COBBLESTONE.get()) {
         return (BlockState)((Block)IafBlockRegistry.CHARRED_STONE.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_60734_() == Blocks.f_152481_) {
         return (BlockState)((Block)IafBlockRegistry.CHARRED_DIRT_PATH.get()).m_49966_().m_61124_(BlockCharedPath.REVERTS, true);
      } else if (!in.m_204336_(BlockTags.f_13106_) && !in.m_204336_(BlockTags.f_13090_)) {
         return !in.m_204336_(BlockTags.f_13035_) && !in.m_204336_(BlockTags.f_13041_) && !in.m_204336_(BlockTags.f_13073_) && in.m_60734_() != Blocks.f_50125_ ? in : Blocks.f_50016_.m_49966_();
      } else {
         return ((Block)IafBlockRegistry.ASH.get()).m_49966_();
      }
   }

   public static BlockState transformBlockIce(BlockState in) {
      if (in.m_60734_() instanceof SpreadingSnowyDirtBlock) {
         return (BlockState)((Block)IafBlockRegistry.FROZEN_GRASS.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if ((!in.m_204336_(BlockTags.f_144274_) || in.m_60734_() != Blocks.f_50493_) && !in.m_204336_(BlockTags.f_144279_)) {
         if (in.m_204336_(BlockTags.f_13029_) && in.m_60734_() == Blocks.f_49994_) {
            return (BlockState)((Block)IafBlockRegistry.FROZEN_GRAVEL.get()).m_49966_().m_61124_(BlockFallingReturningState.REVERTS, true);
         } else if (in.m_204336_(BlockTags.f_13029_) && in.m_60734_() != Blocks.f_49994_) {
            return in;
         } else if (!in.m_204336_(BlockTags.f_13061_) || in.m_60734_() != Blocks.f_50652_ && !in.m_60734_().m_7705_().contains("cobblestone")) {
            if (in.m_204336_(BlockTags.f_13061_) && in.m_60734_() != IafBlockRegistry.FROZEN_COBBLESTONE.get()) {
               return (BlockState)((Block)IafBlockRegistry.FROZEN_STONE.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
            } else if (in.m_60734_() == Blocks.f_152481_) {
               return (BlockState)((Block)IafBlockRegistry.FROZEN_DIRT_PATH.get()).m_49966_().m_61124_(BlockCharedPath.REVERTS, true);
            } else if (!in.m_204336_(BlockTags.f_13106_) && !in.m_204336_(BlockTags.f_13090_)) {
               if (in.m_60713_(Blocks.f_49990_)) {
                  return Blocks.f_50126_.m_49966_();
               } else {
                  return !in.m_204336_(BlockTags.f_13035_) && !in.m_204336_(BlockTags.f_13041_) && !in.m_204336_(BlockTags.f_13073_) && in.m_60734_() != Blocks.f_50125_ ? in : Blocks.f_50016_.m_49966_();
               }
            } else {
               return ((Block)IafBlockRegistry.FROZEN_SPLINTERS.get()).m_49966_();
            }
         } else {
            return (BlockState)((Block)IafBlockRegistry.FROZEN_COBBLESTONE.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
         }
      } else {
         return (BlockState)((Block)IafBlockRegistry.FROZEN_DIRT.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      }
   }

   public static BlockState transformBlockLightning(BlockState in) {
      if (in.m_60734_() instanceof SpreadingSnowyDirtBlock) {
         return (BlockState)((Block)IafBlockRegistry.CRACKLED_GRASS.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_144274_) && in.m_60734_() == Blocks.f_50493_) {
         return (BlockState)((Block)IafBlockRegistry.CRACKLED_DIRT.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_13029_) && in.m_60734_() == Blocks.f_49994_) {
         return (BlockState)((Block)IafBlockRegistry.CRACKLED_GRAVEL.get()).m_49966_().m_61124_(BlockFallingReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_13061_) && (in.m_60734_() == Blocks.f_50652_ || in.m_60734_().m_7705_().contains("cobblestone"))) {
         return (BlockState)((Block)IafBlockRegistry.CRACKLED_COBBLESTONE.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_204336_(BlockTags.f_13061_) && in.m_60734_() != IafBlockRegistry.CRACKLED_COBBLESTONE.get()) {
         return (BlockState)((Block)IafBlockRegistry.CRACKLED_STONE.get()).m_49966_().m_61124_(BlockReturningState.REVERTS, true);
      } else if (in.m_60734_() == Blocks.f_152481_) {
         return (BlockState)((Block)IafBlockRegistry.CRACKLED_DIRT_PATH.get()).m_49966_().m_61124_(BlockCharedPath.REVERTS, true);
      } else if (!in.m_204336_(BlockTags.f_13106_) && !in.m_204336_(BlockTags.f_13090_)) {
         return !in.m_204336_(BlockTags.f_13035_) && !in.m_204336_(BlockTags.f_13041_) && !in.m_204336_(BlockTags.f_13073_) && in.m_60734_() != Blocks.f_50125_ ? in : Blocks.f_50016_.m_49966_();
      } else {
         return ((Block)IafBlockRegistry.ASH.get()).m_49966_();
      }
   }
}
