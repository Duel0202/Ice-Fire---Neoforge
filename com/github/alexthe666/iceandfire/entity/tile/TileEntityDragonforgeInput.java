package com.github.alexthe666.iceandfire.entity.tile;

import com.github.alexthe666.iceandfire.block.BlockDragonforgeInput;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforge.common.capabilities.Capability;
import net.neoforge.common.capabilities.ForgeCapabilities;
import net.neoforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class TileEntityDragonforgeInput extends BlockEntity {
   private static final int LURE_DISTANCE = 50;
   private static final Direction[] HORIZONTALS;
   private int ticksSinceDragonFire;
   private TileEntityDragonforge core = null;

   public TileEntityDragonforgeInput(BlockPos pos, BlockState state) {
      super((BlockEntityType)IafTileEntityRegistry.DRAGONFORGE_INPUT.get(), pos, state);
   }

   public void onHitWithFlame() {
      if (this.core != null) {
         this.core.transferPower(1);
      }

   }

   public static void tick(Level level, BlockPos position, BlockState state, TileEntityDragonforgeInput forgeInput) {
      if (forgeInput.core == null) {
         forgeInput.core = forgeInput.getConnectedTileEntity(position);
      }

      if (forgeInput.ticksSinceDragonFire > 0) {
         --forgeInput.ticksSinceDragonFire;
      }

      if ((forgeInput.ticksSinceDragonFire == 0 || forgeInput.core == null) && forgeInput.isActive()) {
         BlockEntity tileentity = level.m_7702_(position);
         level.m_46597_(position, forgeInput.getDeactivatedState());
         if (tileentity != null) {
            tileentity.m_6339_();
            level.m_151523_(tileentity);
         }
      }

      if (forgeInput.isAssembled()) {
         forgeInput.lureDragons();
      }

   }

   public ClientboundBlockEntityDataPacket getUpdatePacket() {
      return ClientboundBlockEntityDataPacket.m_195640_(this);
   }

   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket packet) {
      this.m_142466_(packet.m_131708_());
   }

   @NotNull
   public CompoundTag m_5995_() {
      return this.m_187480_();
   }

   protected void lureDragons() {
      Vec3 targetPosition = new Vec3((double)((float)this.m_58899_().m_123341_() + 0.5F), (double)((float)this.m_58899_().m_123342_() + 0.5F), (double)((float)this.m_58899_().m_123343_() + 0.5F));
      AABB searchArea = new AABB((double)this.f_58858_.m_123341_() - 50.0D, (double)this.f_58858_.m_123342_() - 50.0D, (double)this.f_58858_.m_123343_() - 50.0D, (double)this.f_58858_.m_123341_() + 50.0D, (double)this.f_58858_.m_123342_() + 50.0D, (double)this.f_58858_.m_123343_() + 50.0D);
      boolean dragonSelected = false;
      Iterator var4 = this.f_58857_.m_45976_(EntityDragonBase.class, searchArea).iterator();

      while(true) {
         while(var4.hasNext()) {
            EntityDragonBase dragon = (EntityDragonBase)var4.next();
            if (!dragonSelected && this.getDragonType() == dragon.dragonType.getIntFromType() && (dragon.isChained() || dragon.m_21824_()) && this.canSeeInput(dragon, targetPosition)) {
               dragon.burningTarget = this.f_58858_;
               dragonSelected = true;
            } else if (dragon.burningTarget == this.f_58858_) {
               dragon.burningTarget = null;
               dragon.setBreathingFire(false);
            }
         }

         return;
      }
   }

   public boolean isAssembled() {
      return this.core != null && this.core.assembled() && this.core.canSmelt();
   }

   public void resetCore() {
      this.core = null;
   }

   private boolean canSeeInput(EntityDragonBase dragon, Vec3 target) {
      if (target != null) {
         HitResult rayTrace = this.f_58857_.m_45547_(new ClipContext(dragon.getHeadPosition(), target, Block.COLLIDER, Fluid.NONE, dragon));
         double distance = dragon.getHeadPosition().m_82554_(rayTrace.m_82450_());
         return distance < (double)(10.0F + dragon.m_20205_());
      } else {
         return false;
      }
   }

   private BlockState getDeactivatedState() {
      BlockState var10000;
      switch(this.getDragonType()) {
      case 0:
         var10000 = (BlockState)((net.minecraft.world.level.block.Block)IafBlockRegistry.DRAGONFORGE_FIRE_INPUT.get()).m_49966_().m_61124_(BlockDragonforgeInput.ACTIVE, false);
         break;
      case 1:
         var10000 = (BlockState)((net.minecraft.world.level.block.Block)IafBlockRegistry.DRAGONFORGE_ICE_INPUT.get()).m_49966_().m_61124_(BlockDragonforgeInput.ACTIVE, false);
         break;
      case 2:
         var10000 = (BlockState)((net.minecraft.world.level.block.Block)IafBlockRegistry.DRAGONFORGE_LIGHTNING_INPUT.get()).m_49966_().m_61124_(BlockDragonforgeInput.ACTIVE, false);
         break;
      default:
         var10000 = (BlockState)((net.minecraft.world.level.block.Block)IafBlockRegistry.DRAGONFORGE_FIRE_INPUT.get()).m_49966_().m_61124_(BlockDragonforgeInput.ACTIVE, false);
      }

      return var10000;
   }

   private int getDragonType() {
      BlockState state = this.f_58857_.m_8055_(this.f_58858_);
      if (state.m_60734_() == IafBlockRegistry.DRAGONFORGE_FIRE_INPUT.get()) {
         return 0;
      } else if (state.m_60734_() == IafBlockRegistry.DRAGONFORGE_ICE_INPUT.get()) {
         return 1;
      } else {
         return state.m_60734_() == IafBlockRegistry.DRAGONFORGE_LIGHTNING_INPUT.get() ? 2 : 0;
      }
   }

   private boolean isActive() {
      BlockState state = this.f_58857_.m_8055_(this.f_58858_);
      return state.m_60734_() instanceof BlockDragonforgeInput && (Boolean)state.m_61143_(BlockDragonforgeInput.ACTIVE);
   }

   private TileEntityDragonforge getConnectedTileEntity(BlockPos position) {
      Direction[] var2 = HORIZONTALS;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Direction facing = var2[var4];
         BlockEntity var7 = this.f_58857_.m_7702_(position.m_121945_(facing));
         if (var7 instanceof TileEntityDragonforge) {
            TileEntityDragonforge forge = (TileEntityDragonforge)var7;
            return forge;
         }
      }

      return null;
   }

   @NotNull
   public <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction facing) {
      return this.core != null && capability == ForgeCapabilities.ITEM_HANDLER ? this.core.getCapability(capability, facing) : super.getCapability(capability, facing);
   }

   static {
      HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
   }
}
