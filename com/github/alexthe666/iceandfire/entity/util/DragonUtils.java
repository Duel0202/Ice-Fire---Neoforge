package com.github.alexthe666.iceandfire.entity.util;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.datagen.tags.IafBlockTags;
import com.github.alexthe666.iceandfire.entity.EntityCockatrice;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.EntityGhost;
import com.github.alexthe666.iceandfire.entity.EntityGorgon;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
import com.github.alexthe666.iceandfire.entity.EntityMutlipartPart;
import com.github.alexthe666.iceandfire.entity.EntitySeaSerpent;
import com.github.alexthe666.iceandfire.entity.EntityStymphalianBird;
import com.github.alexthe666.iceandfire.misc.IafTagRegistry;
import com.google.common.base.Predicate;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforge.event.ForgeEventFactory;
import net.neoforge.registries.ForgeRegistries;
import net.neoforge.registries.tags.ITagManager;

public class DragonUtils {
   public static BlockPos getBlockInViewEscort(EntityDragonBase dragon) {
      BlockPos escortPos = dragon.getEscortPosition();
      BlockPos ground = dragon.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, escortPos);
      int distFromGround = escortPos.m_123342_() - ground.m_123342_();

      for(int i = 0; i < 10; ++i) {
         BlockPos pos = new BlockPos(escortPos.m_123341_() + dragon.m_217043_().m_188503_(IafConfig.dragonWanderFromHomeDistance) - IafConfig.dragonWanderFromHomeDistance / 2, distFromGround > 16 ? escortPos.m_123342_() : escortPos.m_123342_() + 8 + dragon.m_217043_().m_188503_(16), escortPos.m_123343_() + dragon.m_217043_().m_188503_(IafConfig.dragonWanderFromHomeDistance) - IafConfig.dragonWanderFromHomeDistance / 2);
         if (dragon.getDistanceSquared(Vec3.m_82512_(pos)) > 6.0F && !dragon.isTargetBlocked(Vec3.m_82512_(pos))) {
            return pos;
         }
      }

      return null;
   }

   public static BlockPos getWaterBlockInViewEscort(EntityDragonBase dragon) {
      BlockPos inWaterEscortPos = dragon.getEscortPosition();
      if (Math.abs(dragon.m_20185_() - (double)inWaterEscortPos.m_123341_()) < dragon.m_20191_().m_82362_() && Math.abs(dragon.m_20189_() - (double)inWaterEscortPos.m_123343_()) < dragon.m_20191_().m_82385_()) {
         return dragon.m_20183_();
      } else {
         if ((double)inWaterEscortPos.m_123342_() - dragon.m_20186_() > (double)(8 + dragon.getYNavSize()) && !dragon.m_9236_().m_6425_(inWaterEscortPos.m_7495_()).m_205070_(FluidTags.f_13131_)) {
            dragon.setHovering(true);
         }

         return inWaterEscortPos;
      }
   }

   public static BlockPos getBlockInView(EntityDragonBase dragon) {
      float radius = 12.0F * (0.7F * dragon.getRenderSize() / 3.0F);
      float neg = dragon.m_217043_().m_188499_() ? 1.0F : -1.0F;
      float renderYawOffset = dragon.f_20883_;
      BlockPos pos;
      if (dragon.hasHomePosition && dragon.homePos != null) {
         BlockPos dragonPos = dragon.m_20183_();
         BlockPos ground = dragon.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, dragonPos);
         int distFromGround = (int)dragon.m_20186_() - ground.m_123342_();

         for(int i = 0; i < 10; ++i) {
            BlockPos homePos = dragon.homePos.getPosition();
            pos = new BlockPos(homePos.m_123341_() + dragon.m_217043_().m_188503_(IafConfig.dragonWanderFromHomeDistance * 2) - IafConfig.dragonWanderFromHomeDistance, distFromGround > 16 ? (int)Math.min((double)IafConfig.maxDragonFlight, dragon.m_20186_() + (double)dragon.m_217043_().m_188503_(16) - 8.0D) : (int)dragon.m_20186_() + dragon.m_217043_().m_188503_(16) + 1, homePos.m_123343_() + dragon.m_217043_().m_188503_(IafConfig.dragonWanderFromHomeDistance * 2) - IafConfig.dragonWanderFromHomeDistance);
            if (dragon.getDistanceSquared(Vec3.m_82512_(pos)) > 6.0F && !dragon.isTargetBlocked(Vec3.m_82512_(pos))) {
               return pos;
            }
         }
      }

      float angle = 0.017453292F * renderYawOffset + 3.15F + dragon.m_217043_().m_188501_() * neg;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      pos = BlockPos.m_274561_(dragon.m_20185_() + extraX, 0.0D, dragon.m_20189_() + extraZ);
      BlockPos ground = dragon.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, pos);
      int distFromGround = (int)dragon.m_20186_() - ground.m_123342_();
      BlockPos newPos = pos.m_6630_(distFromGround > 16 ? (int)Math.min((double)IafConfig.maxDragonFlight, dragon.m_20186_() + (double)dragon.m_217043_().m_188503_(16) - 8.0D) : (int)dragon.m_20186_() + dragon.m_217043_().m_188503_(16) + 1);
      BlockPos pos = dragon.doesWantToLand() ? ground : newPos;
      return dragon.getDistanceSquared(Vec3.m_82512_(newPos)) > 6.0F && !dragon.isTargetBlocked(Vec3.m_82512_(newPos)) ? pos : null;
   }

   public static BlockPos getWaterBlockInView(EntityDragonBase dragon) {
      float radius = 0.75F * (0.7F * dragon.getRenderSize() / 3.0F) * -7.0F - (float)dragon.m_217043_().m_188503_(dragon.getDragonStage() * 6);
      float neg = dragon.m_217043_().m_188499_() ? 1.0F : -1.0F;
      float angle = 0.017453292F * dragon.f_20883_ + 3.15F + dragon.m_217043_().m_188501_() * neg;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = BlockPos.m_274561_(dragon.m_20185_() + extraX, 0.0D, dragon.m_20189_() + extraZ);
      BlockPos ground = dragon.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      int distFromGround = (int)dragon.m_20186_() - ground.m_123342_();
      BlockPos newPos = radialPos.m_6630_(distFromGround > 16 ? (int)Math.min((double)IafConfig.maxDragonFlight, dragon.m_20186_() + (double)dragon.m_217043_().m_188503_(16) - 8.0D) : (int)dragon.m_20186_() + dragon.m_217043_().m_188503_(16) + 1);
      BlockPos var10000 = dragon.doesWantToLand() ? ground : newPos;
      BlockPos surface = dragon.m_9236_().m_6425_(newPos.m_6625_(2)).m_205070_(FluidTags.f_13131_) ? newPos.m_6625_(dragon.m_217043_().m_188503_(10) + 1) : newPos;
      return dragon.getDistanceSquared(Vec3.m_82512_(surface)) > 6.0F && dragon.m_9236_().m_6425_(surface).m_205070_(FluidTags.f_13131_) ? surface : null;
   }

   public static LivingEntity riderLookingAtEntity(final LivingEntity dragon, LivingEntity rider, double dist) {
      Vec3 Vector3d = rider.m_20299_(1.0F);
      Vec3 Vector3d1 = rider.m_20252_(1.0F);
      Vec3 Vector3d2 = Vector3d.m_82520_(Vector3d1.f_82479_ * dist, Vector3d1.f_82480_ * dist, Vector3d1.f_82481_ * dist);
      Entity pointedEntity = null;
      List<Entity> list = rider.m_9236_().m_6249_(rider, rider.m_20191_().m_82363_(Vector3d1.f_82479_ * dist, Vector3d1.f_82480_ * dist, Vector3d1.f_82481_ * dist).m_82377_(1.0D, 1.0D, 1.0D), new Predicate<Entity>() {
         public boolean apply(@Nullable Entity entity) {
            if (DragonUtils.onSameTeam(dragon, entity)) {
               return false;
            } else {
               return entity != null && entity.m_6087_() && entity instanceof LivingEntity && !entity.m_7306_(dragon) && !entity.m_7307_(dragon) && (!(entity instanceof IDeadMob) || !((IDeadMob)entity).isMobDead());
            }
         }
      });
      double d2 = dist;

      for(int j = 0; j < list.size(); ++j) {
         Entity entity1 = (Entity)list.get(j);
         AABB axisalignedbb = entity1.m_20191_().m_82400_((double)entity1.m_6143_() + 2.0D);
         Vec3 raytraceresult = (Vec3)axisalignedbb.m_82371_(Vector3d, Vector3d2).orElse(Vec3.f_82478_);
         if (axisalignedbb.m_82390_(Vector3d)) {
            if (d2 >= 0.0D) {
               pointedEntity = entity1;
               d2 = 0.0D;
            }
         } else if (raytraceresult != null) {
            double d3 = Vector3d.m_82554_(raytraceresult);
            if (d3 < d2 || d2 == 0.0D) {
               if (entity1.m_20201_() == rider.m_20201_() && !rider.canRiderInteract()) {
                  if (d2 == 0.0D) {
                     pointedEntity = entity1;
                  }
               } else {
                  pointedEntity = entity1;
                  d2 = d3;
               }
            }
         }
      }

      return (LivingEntity)pointedEntity;
   }

   public static BlockPos getBlockInViewHippogryph(EntityHippogryph hippo, float yawAddition) {
      float radius = -12.599999F - (float)hippo.m_217043_().m_188503_(48);
      float neg = hippo.m_217043_().m_188499_() ? 1.0F : -1.0F;
      float angle = 0.017453292F * (hippo.f_20883_ + yawAddition) + 3.15F + hippo.m_217043_().m_188501_() * neg;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos;
      BlockPos ground;
      int distFromGround;
      if (hippo.hasHomePosition && hippo.homePos != null) {
         radialPos = hippo.m_20183_();
         ground = hippo.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
         distFromGround = (int)hippo.m_20186_() - ground.m_123342_();

         for(int i = 0; i < 10; ++i) {
            BlockPos pos = BlockPos.m_274561_((double)(hippo.homePos.m_123341_() + hippo.m_217043_().m_188503_(IafConfig.dragonWanderFromHomeDistance) - IafConfig.dragonWanderFromHomeDistance), (double)(distFromGround > 16 ? (int)Math.min((double)IafConfig.maxDragonFlight, hippo.m_20186_() + (double)hippo.m_217043_().m_188503_(16) - 8.0D) : (int)hippo.m_20186_() + hippo.m_217043_().m_188503_(16) + 1), (double)(hippo.homePos.m_123343_() + hippo.m_217043_().m_188503_(IafConfig.dragonWanderFromHomeDistance * 2) - IafConfig.dragonWanderFromHomeDistance));
            if (hippo.getDistanceSquared(Vec3.m_82512_(pos)) > 6.0F && !hippo.isTargetBlocked(Vec3.m_82512_(pos))) {
               return pos;
            }
         }
      }

      radialPos = BlockPos.m_274561_(hippo.m_20185_() + extraX, 0.0D, hippo.m_20189_() + extraZ);
      ground = hippo.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      distFromGround = (int)hippo.m_20186_() - ground.m_123342_();
      BlockPos newPos = radialPos.m_6630_(distFromGround > 16 ? (int)Math.min((double)IafConfig.maxDragonFlight, hippo.m_20186_() + (double)hippo.m_217043_().m_188503_(16) - 8.0D) : (int)hippo.m_20186_() + hippo.m_217043_().m_188503_(16) + 1);
      BlockPos var10000 = hippo.doesWantToLand() ? ground : newPos;
      return !hippo.isTargetBlocked(Vec3.m_82512_(newPos)) && hippo.getDistanceSquared(Vec3.m_82512_(newPos)) > 6.0F ? newPos : null;
   }

   public static BlockPos getBlockInViewStymphalian(EntityStymphalianBird bird) {
      float radius = -9.45F - (float)bird.m_217043_().m_188503_(24);
      float neg = bird.m_217043_().m_188499_() ? 1.0F : -1.0F;
      float renderYawOffset = bird.flock != null && !bird.flock.isLeader(bird) ? getStymphalianFlockDirection(bird) : bird.f_20883_;
      float angle = 0.017453292F * renderYawOffset + 3.15F + bird.m_217043_().m_188501_() * neg;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = getStymphalianFearPos(bird, BlockPos.m_274561_(bird.m_20185_() + extraX, 0.0D, bird.m_20189_() + extraZ));
      BlockPos ground = bird.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      int distFromGround = (int)bird.m_20186_() - ground.m_123342_();
      int flightHeight = Math.min(IafConfig.stymphalianBirdFlightHeight, ground.m_123342_() + bird.m_217043_().m_188503_(16));
      BlockPos newPos = radialPos.m_6630_(distFromGround > 16 ? flightHeight : (int)bird.m_20186_() + bird.m_217043_().m_188503_(16) + 1);
      return bird.getDistanceSquared(Vec3.m_82512_(newPos)) > 6.0F && !bird.isTargetBlocked(Vec3.m_82512_(newPos)) ? newPos : null;
   }

   private static BlockPos getStymphalianFearPos(EntityStymphalianBird bird, BlockPos fallback) {
      if (bird.getVictor() != null && bird.getVictor() instanceof PathfinderMob) {
         Vec3 Vector3d = DefaultRandomPos.m_148407_((PathfinderMob)bird.getVictor(), 16, IafConfig.stymphalianBirdFlightHeight, new Vec3(bird.getVictor().m_20185_(), bird.getVictor().m_20186_(), bird.getVictor().m_20189_()));
         if (Vector3d != null) {
            BlockPos pos = BlockPos.m_274446_(Vector3d);
            return new BlockPos(pos.m_123341_(), 0, pos.m_123343_());
         }
      }

      return fallback;
   }

   private static float getStymphalianFlockDirection(EntityStymphalianBird bird) {
      EntityStymphalianBird leader = bird.flock.getLeader();
      if (bird.m_20280_(leader) > 2.0D) {
         double d0 = leader.m_20185_() - bird.m_20185_();
         double d2 = leader.m_20189_() - bird.m_20189_();
         float f = (float)(Mth.m_14136_(d2, d0) * 57.29577951308232D) - 90.0F;
         float degrees = Mth.m_14177_(f - bird.m_146908_());
         return bird.m_146908_() + degrees;
      } else {
         return leader.f_20883_;
      }
   }

   public static BlockPos getBlockInTargetsViewCockatrice(EntityCockatrice cockatrice, LivingEntity target) {
      float radius = (float)(10 + cockatrice.m_217043_().m_188503_(10));
      float angle = 0.017453292F * target.f_20885_;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = BlockPos.m_274561_(target.m_20185_() + extraX, 0.0D, target.m_20189_() + extraZ);
      BlockPos ground = target.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      return cockatrice.m_20238_(Vec3.m_82512_(ground)) > 30.0D && !cockatrice.isTargetBlocked(Vec3.m_82512_(ground)) ? ground : target.m_20183_();
   }

   public static BlockPos getBlockInTargetsViewGhost(EntityGhost ghost, LivingEntity target) {
      float radius = (float)(4 + ghost.m_217043_().m_188503_(5));
      float angle = 0.017453292F * (target.f_20885_ + 90.0F + (float)ghost.m_217043_().m_188503_(180));
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = BlockPos.m_274561_(target.m_20185_() + extraX, target.m_20186_(), target.m_20189_() + extraZ);
      return ghost.m_20238_(Vec3.m_82512_(radialPos)) > 30.0D ? radialPos : ghost.m_20183_();
   }

   public static BlockPos getBlockInTargetsViewGorgon(EntityGorgon cockatrice, LivingEntity target) {
      float radius = 6.0F;
      float angle = 0.017453292F * target.f_20885_;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = BlockPos.m_274561_(target.m_20185_() + extraX, target.m_20186_(), target.m_20189_() + extraZ);
      return cockatrice.m_20238_(Vec3.m_82512_(radialPos)) < 300.0D && !cockatrice.isTargetBlocked(Vec3.m_82512_(radialPos).m_82520_(0.0D, 0.75D, 0.0D)) ? radialPos : target.m_20183_();
   }

   public static BlockPos getBlockInTargetsViewSeaSerpent(EntitySeaSerpent serpent, LivingEntity target) {
      float radius = 10.0F * serpent.getSeaSerpentScale() + (float)serpent.m_217043_().m_188503_(10);
      float angle = 0.017453292F * target.f_20885_;
      double extraX = (double)(radius * Mth.m_14031_((float)(3.141592653589793D + (double)angle)));
      double extraZ = (double)(radius * Mth.m_14089_(angle));
      BlockPos radialPos = BlockPos.m_274561_(target.m_20185_() + extraX, 0.0D, target.m_20189_() + extraZ);
      BlockPos ground = target.m_9236_().m_5452_(Types.MOTION_BLOCKING_NO_LEAVES, radialPos);
      return serpent.m_20238_(Vec3.m_82512_(ground)) > 30.0D ? ground : target.m_20183_();
   }

   public static boolean canTameDragonAttack(TamableAnimal dragon, Entity entity) {
      if (isVillager(entity)) {
         return false;
      } else if (!(entity instanceof AbstractVillager) && !(entity instanceof AbstractGolem) && !(entity instanceof Player)) {
         if (entity instanceof TamableAnimal) {
            return !((TamableAnimal)entity).m_21824_();
         } else {
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean isVillager(Entity entity) {
      ITagManager<EntityType<?>> tags = ForgeRegistries.ENTITY_TYPES.tags();
      return tags == null ? false : entity.m_6095_().m_204039_(tags.createTagKey(IafTagRegistry.VILLAGERS));
   }

   public static boolean isAnimaniaMob(Entity entity) {
      return false;
   }

   public static boolean isDragonTargetable(Entity entity, ResourceLocation tag) {
      return entity.m_6095_().m_204039_(ForgeRegistries.ENTITY_TYPES.tags().createTagKey(tag));
   }

   public static String getDimensionName(Level world) {
      return world.m_46472_().m_135782_().toString();
   }

   public static boolean isInHomeDimension(EntityDragonBase dragonBase) {
      return dragonBase.getHomeDimensionName() == null || getDimensionName(dragonBase.m_9236_()).equals(dragonBase.getHomeDimensionName());
   }

   public static boolean canDragonBreak(BlockState state, Entity entity) {
      if (!ForgeEventFactory.getMobGriefingEvent(entity.m_9236_(), entity)) {
         return false;
      } else {
         Block block = state.m_60734_();
         return block.m_7325_() < 1200.0F && !state.m_204336_(IafBlockTags.DRAGON_BLOCK_BREAK_BLACKLIST);
      }
   }

   public static boolean hasSameOwner(TamableAnimal cockatrice, Entity entity) {
      if (!(entity instanceof TamableAnimal)) {
         return false;
      } else {
         TamableAnimal tameable = (TamableAnimal)entity;
         return tameable.m_21805_() != null && cockatrice.m_21805_() != null && tameable.m_21805_().equals(cockatrice.m_21805_());
      }
   }

   public static boolean isAlive(LivingEntity entity) {
      if (entity instanceof EntityDragonBase) {
         EntityDragonBase dragon = (EntityDragonBase)entity;
         if (dragon.isMobDead()) {
            return false;
         }
      }

      boolean var10000;
      label20: {
         if (entity instanceof IDeadMob) {
            IDeadMob deadMob = (IDeadMob)entity;
            if (deadMob.isMobDead()) {
               break label20;
            }
         }

         if (!EntityGorgon.isStoneMob(entity)) {
            var10000 = true;
            return var10000;
         }
      }

      var10000 = false;
      return var10000;
   }

   public static boolean canGrief(EntityDragonBase dragon) {
      if (dragon.m_21824_() && !IafConfig.tamedDragonGriefing) {
         return false;
      } else {
         return IafConfig.dragonGriefing < 2;
      }
   }

   public static boolean canHostilesTarget(Entity entity) {
      if (!(entity instanceof Player) || entity.m_9236_().m_46791_() != Difficulty.PEACEFUL && !((Player)entity).m_7500_()) {
         if (entity instanceof EntityDragonBase && ((EntityDragonBase)entity).isMobDead()) {
            return false;
         } else {
            return entity instanceof LivingEntity && isAlive((LivingEntity)entity);
         }
      } else {
         return false;
      }
   }

   public static boolean onSameTeam(Entity entity1, Entity entity2) {
      Entity owner1 = null;
      Entity owner2 = null;
      boolean def = entity1.m_7307_(entity2);
      if (entity1 instanceof TamableAnimal) {
         owner1 = ((TamableAnimal)entity1).m_269323_();
      }

      if (entity2 instanceof TamableAnimal) {
         owner2 = ((TamableAnimal)entity2).m_269323_();
      }

      Entity multipart;
      if (entity1 instanceof EntityMutlipartPart) {
         multipart = ((EntityMutlipartPart)entity1).getParent();
         if (multipart != null && multipart instanceof TamableAnimal) {
            owner1 = ((TamableAnimal)multipart).m_269323_();
         }
      }

      if (entity2 instanceof EntityMutlipartPart) {
         multipart = ((EntityMutlipartPart)entity2).getParent();
         if (multipart != null && multipart instanceof TamableAnimal) {
            owner2 = ((TamableAnimal)multipart).m_269323_();
         }
      }

      return owner1 != null && owner2 != null ? owner1.m_7306_(owner2) : def;
   }

   public static boolean isDreadBlock(BlockState state) {
      Block block = state.m_60734_();
      return block == IafBlockRegistry.DREAD_STONE.get() || block == IafBlockRegistry.DREAD_STONE_BRICKS.get() || block == IafBlockRegistry.DREAD_STONE_BRICKS_CHISELED.get() || block == IafBlockRegistry.DREAD_STONE_BRICKS_CRACKED.get() || block == IafBlockRegistry.DREAD_STONE_BRICKS_MOSSY.get() || block == IafBlockRegistry.DREAD_STONE_TILE.get() || block == IafBlockRegistry.DREAD_STONE_FACE.get() || block == IafBlockRegistry.DREAD_TORCH.get() || block == IafBlockRegistry.DREAD_STONE_BRICKS_STAIRS.get() || block == IafBlockRegistry.DREAD_STONE_BRICKS_SLAB.get() || block == IafBlockRegistry.DREADWOOD_LOG.get() || block == IafBlockRegistry.DREADWOOD_PLANKS.get() || block == IafBlockRegistry.DREADWOOD_PLANKS_LOCK.get() || block == IafBlockRegistry.DREAD_PORTAL.get() || block == IafBlockRegistry.DREAD_SPAWNER.get();
   }
}
