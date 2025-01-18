package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

public class MyrmexWorldData extends SavedData {
   private static final String IDENTIFIER = "iceandfire_myrmex";
   private final List<BlockPos> villagerPositionsList = Lists.newArrayList();
   private final List<MyrmexHive> hiveList = Lists.newArrayList();
   private Level world;
   private int tickCounter;

   public MyrmexWorldData() {
   }

   public MyrmexWorldData(Level world) {
      this.world = world;
      this.m_77762_();
   }

   public MyrmexWorldData(CompoundTag compoundTag) {
      this.load(compoundTag);
   }

   public static MyrmexWorldData get(Level world) {
      if (world instanceof ServerLevel) {
         ServerLevel overworld = world.m_7654_().m_129880_(world.m_46472_());
         DimensionDataStorage storage = overworld.m_8895_();
         MyrmexWorldData data = (MyrmexWorldData)storage.m_164861_(MyrmexWorldData::new, MyrmexWorldData::new, "iceandfire_myrmex");
         if (data != null) {
            data.world = world;
            data.m_77762_();
         }

         return data;
      } else {
         return new MyrmexWorldData();
      }
   }

   public static void addHive(Level world, MyrmexHive hive) {
      get(world).hiveList.add(hive);
   }

   public void setWorldsForAll(Level worldIn) {
      this.world = worldIn;
      Iterator var2 = this.hiveList.iterator();

      while(var2.hasNext()) {
         MyrmexHive village = (MyrmexHive)var2.next();
         village.setWorld(worldIn);
      }

   }

   public void tick() {
      ++this.tickCounter;
      Iterator var1 = this.hiveList.iterator();

      while(var1.hasNext()) {
         MyrmexHive hive = (MyrmexHive)var1.next();
         hive.tick(this.tickCounter, this.world);
      }

   }

   private void removeAnnihilatedHives() {
      Iterator iterator = this.hiveList.iterator();

      while(iterator.hasNext()) {
         MyrmexHive village = (MyrmexHive)iterator.next();
         if (village.isAnnihilated()) {
            iterator.remove();
            this.m_77762_();
         }
      }

   }

   public List<MyrmexHive> getHivelist() {
      return this.hiveList;
   }

   public MyrmexHive getNearestHive(BlockPos doorBlock, int radius) {
      MyrmexHive village = null;
      double d0 = 3.4028234663852886E38D;
      Iterator var6 = this.hiveList.iterator();

      while(var6.hasNext()) {
         MyrmexHive village1 = (MyrmexHive)var6.next();
         double d1 = village1.getCenter().m_123331_(doorBlock);
         if (d1 < d0) {
            float f = (float)(radius + village1.getVillageRadius());
            if (d1 <= (double)(f * f)) {
               village = village1;
               d0 = d1;
            }
         }
      }

      return village;
   }

   private boolean positionInList(BlockPos pos) {
      Iterator var2 = this.villagerPositionsList.iterator();

      BlockPos blockpos;
      do {
         if (!var2.hasNext()) {
            return false;
         }

         blockpos = (BlockPos)var2.next();
      } while(!blockpos.equals(pos));

      return true;
   }

   public void debug() {
      Iterator var1 = this.hiveList.iterator();

      while(var1.hasNext()) {
         MyrmexHive hive = (MyrmexHive)var1.next();
         IceAndFire.LOGGER.warn(hive.toString());
      }

   }

   public void load(CompoundTag nbt) {
      this.tickCounter = nbt.m_128451_("Tick");
      ListTag nbttaglist = nbt.m_128437_("Hives", 10);

      for(int i = 0; i < nbttaglist.size(); ++i) {
         CompoundTag CompoundNBT = nbttaglist.m_128728_(i);
         MyrmexHive village = new MyrmexHive();
         village.readVillageDataFromNBT(CompoundNBT);
         this.hiveList.add(village);
      }

   }

   @NotNull
   public CompoundTag m_7176_(CompoundTag compound) {
      compound.m_128405_("Tick", this.tickCounter);
      ListTag nbttaglist = new ListTag();
      Iterator var3 = this.hiveList.iterator();

      while(var3.hasNext()) {
         MyrmexHive village = (MyrmexHive)var3.next();
         CompoundTag CompoundNBT = new CompoundTag();
         village.writeVillageDataToNBT(CompoundNBT);
         nbttaglist.add(CompoundNBT);
      }

      compound.m_128365_("Hives", nbttaglist);
      return compound;
   }

   public MyrmexHive getHiveFromUUID(UUID id) {
      Iterator var2 = this.hiveList.iterator();

      MyrmexHive hive;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         hive = (MyrmexHive)var2.next();
      } while(hive.hiveUUID == null || !hive.hiveUUID.equals(id));

      return hive;
   }
}
