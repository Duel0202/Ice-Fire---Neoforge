package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

public class DragonPosWorldData extends SavedData {
   private static final String IDENTIFIER = "iceandfire_dragonPositions";
   protected final Map<UUID, BlockPos> lastDragonPositions = new HashMap();
   private Level world;
   private int tickCounter;

   public DragonPosWorldData() {
   }

   public DragonPosWorldData(Level world) {
      this.world = world;
      this.m_77762_();
   }

   public DragonPosWorldData(CompoundTag compoundTag) {
      this.load(compoundTag);
   }

   public static DragonPosWorldData get(Level world) {
      if (world instanceof ServerLevel) {
         ServerLevel overworld = world.m_7654_().m_129880_(world.m_46472_());
         DimensionDataStorage storage = overworld.m_8895_();
         DragonPosWorldData data = (DragonPosWorldData)storage.m_164861_(DragonPosWorldData::new, DragonPosWorldData::new, "iceandfire_dragonPositions");
         if (data != null) {
            data.world = world;
            data.m_77762_();
         }

         return data;
      } else {
         return null;
      }
   }

   public void addDragon(UUID uuid, BlockPos pos) {
      this.lastDragonPositions.put(uuid, pos);
      this.m_77762_();
   }

   public void removeDragon(UUID uuid) {
      this.lastDragonPositions.remove(uuid);
      this.m_77762_();
   }

   public BlockPos getDragonPos(UUID uuid) {
      return (BlockPos)this.lastDragonPositions.get(uuid);
   }

   public void debug() {
      IceAndFire.LOGGER.warn(this.lastDragonPositions.toString());
   }

   public void tick() {
      ++this.tickCounter;
   }

   public DragonPosWorldData load(CompoundTag nbt) {
      this.tickCounter = nbt.m_128451_("Tick");
      ListTag nbttaglist = nbt.m_128437_("DragonMap", 10);
      this.lastDragonPositions.clear();

      for(int i = 0; i < nbttaglist.size(); ++i) {
         CompoundTag CompoundNBT = nbttaglist.m_128728_(i);
         UUID uuid = CompoundNBT.m_128342_("DragonUUID");
         BlockPos pos = new BlockPos(CompoundNBT.m_128451_("DragonPosX"), CompoundNBT.m_128451_("DragonPosY"), CompoundNBT.m_128451_("DragonPosZ"));
         this.lastDragonPositions.put(uuid, pos);
      }

      return this;
   }

   @NotNull
   public CompoundTag m_7176_(CompoundTag compound) {
      compound.m_128405_("Tick", this.tickCounter);
      ListTag nbttaglist = new ListTag();
      Iterator var3 = this.lastDragonPositions.entrySet().iterator();

      while(var3.hasNext()) {
         Entry<UUID, BlockPos> pair = (Entry)var3.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128362_("DragonUUID", (UUID)pair.getKey());
         CompoundNBT.m_128405_("DragonPosX", ((BlockPos)pair.getValue()).m_123341_());
         CompoundNBT.m_128405_("DragonPosY", ((BlockPos)pair.getValue()).m_123342_());
         CompoundNBT.m_128405_("DragonPosZ", ((BlockPos)pair.getValue()).m_123343_());
         nbttaglist.add(CompoundNBT);
      }

      compound.m_128365_("DragonMap", nbttaglist);
      return compound;
   }
}
