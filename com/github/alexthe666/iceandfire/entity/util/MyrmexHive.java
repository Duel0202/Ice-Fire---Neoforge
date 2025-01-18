package com.github.alexthe666.iceandfire.entity.util;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexBase;
import com.github.alexthe666.iceandfire.entity.EntityMyrmexQueen;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.entity.EntityTypeTest;

public class MyrmexHive {
   private final List<BlockPos> foodRooms = Lists.newArrayList();
   private final List<BlockPos> babyRooms = Lists.newArrayList();
   private final List<BlockPos> miscRooms = Lists.newArrayList();
   private final List<BlockPos> allRooms = Lists.newArrayList();
   private final Map<BlockPos, Direction> entrances = Maps.newHashMap();
   private final Map<BlockPos, Direction> entranceBottoms = Maps.newHashMap();
   private final Map<UUID, Integer> playerReputation = Maps.newHashMap();
   private final List<MyrmexHive.HiveAggressor> villageAgressors = Lists.newArrayList();
   private final List<UUID> myrmexList = Lists.newArrayList();
   public UUID hiveUUID;
   public String colonyName = "";
   public boolean reproduces = true;
   public boolean hasOwner = false;
   public UUID ownerUUID = null;
   private Level world;
   private BlockPos centerHelper;
   private BlockPos center;
   private int villageRadius;
   private int lastAddDoorTimestamp;
   private int tickCounter;
   private int numMyrmex;
   private int noBreedTicks;
   private int wanderRadius;

   public MyrmexHive() {
      this.centerHelper = BlockPos.f_121853_;
      this.center = BlockPos.f_121853_;
      this.wanderRadius = 16;
      this.hiveUUID = UUID.randomUUID();
   }

   public MyrmexHive(Level worldIn) {
      this.centerHelper = BlockPos.f_121853_;
      this.center = BlockPos.f_121853_;
      this.wanderRadius = 16;
      this.world = worldIn;
      this.hiveUUID = UUID.randomUUID();
   }

   public MyrmexHive(Level worldIn, BlockPos center, int radius) {
      this.centerHelper = BlockPos.f_121853_;
      this.center = BlockPos.f_121853_;
      this.wanderRadius = 16;
      this.world = worldIn;
      this.center = center;
      this.villageRadius = radius;
      this.hiveUUID = UUID.randomUUID();
   }

   public static BlockPos getGroundedPos(LevelAccessor world, BlockPos pos) {
      BlockPos current;
      for(current = pos; world.m_46859_(current.m_7495_()) && current.m_123342_() > 0; current = current.m_7495_()) {
      }

      return current;
   }

   public static MyrmexHive fromNBT(CompoundTag hive) {
      MyrmexHive hive1 = new MyrmexHive();
      hive1.readVillageDataFromNBT(hive);
      return hive1;
   }

   public CompoundTag toNBT() {
      CompoundTag tag = new CompoundTag();
      this.writeVillageDataToNBT(tag);
      return tag;
   }

   public void setWorld(Level worldIn) {
      this.world = worldIn;
   }

   public void tick(int tickCounterIn, Level world) {
      ++this.tickCounter;
      this.removeDeadAndOldAgressors();
      if (this.tickCounter % 20 == 0) {
         this.updateNumMyrmex(world);
      }

   }

   private void updateNumMyrmex(Level world) {
      this.numMyrmex = this.myrmexList.size();
      if (this.numMyrmex == 0) {
         this.playerReputation.clear();
      }

   }

   @Nullable
   public EntityMyrmexQueen getQueen() {
      List<EntityMyrmexQueen> ourQueens = new ArrayList();
      if (!this.world.f_46443_) {
         ServerLevel serverWorld = this.world.m_7654_().m_129880_(this.world.m_46472_());
         List<? extends EntityMyrmexQueen> allQueens = serverWorld.m_143280_((EntityTypeTest)IafEntityRegistry.MYRMEX_QUEEN.get(), EntitySelector.f_20408_);
         Iterator var4 = allQueens.iterator();

         while(var4.hasNext()) {
            Entity queen = (Entity)var4.next();
            if (queen instanceof EntityMyrmexQueen && ((EntityMyrmexQueen)queen).getHive().equals(this)) {
               ourQueens.add((EntityMyrmexQueen)queen);
            }
         }
      }

      return ourQueens.isEmpty() ? null : (EntityMyrmexQueen)ourQueens.get(0);
   }

   public BlockPos getCenter() {
      return this.center;
   }

   public BlockPos getCenterGround() {
      return getGroundedPos(this.world, this.center);
   }

   public int getVillageRadius() {
      return this.villageRadius;
   }

   public int getNumMyrmex() {
      return this.numMyrmex;
   }

   public int getWanderRadius() {
      return this.wanderRadius;
   }

   public void setWanderRadius(int wanderRadius) {
      this.wanderRadius = Math.min(wanderRadius, IafConfig.myrmexMaximumWanderRadius);
   }

   public boolean isBlockPosWithinSqVillageRadius(BlockPos pos) {
      return this.center.m_123331_(pos) < (double)(this.villageRadius * this.villageRadius);
   }

   public boolean isAnnihilated() {
      return false;
   }

   public void addOrRenewAgressor(LivingEntity LivingEntityIn, int agressiveLevel) {
      Iterator var3 = this.villageAgressors.iterator();

      MyrmexHive.HiveAggressor hive$villageaggressor;
      do {
         if (!var3.hasNext()) {
            this.villageAgressors.add(new MyrmexHive.HiveAggressor(LivingEntityIn, this.tickCounter, agressiveLevel));
            return;
         }

         hive$villageaggressor = (MyrmexHive.HiveAggressor)var3.next();
      } while(hive$villageaggressor.agressor != LivingEntityIn);

      hive$villageaggressor.agressionTime = this.tickCounter;
   }

   @Nullable
   public LivingEntity findNearestVillageAggressor(LivingEntity LivingEntityIn) {
      double d0 = Double.MAX_VALUE;
      int previousAgressionLevel = 0;
      MyrmexHive.HiveAggressor hive$villageaggressor = null;

      for(int i = 0; i < this.villageAgressors.size(); ++i) {
         MyrmexHive.HiveAggressor hive$villageaggressor1 = (MyrmexHive.HiveAggressor)this.villageAgressors.get(i);
         double d1 = hive$villageaggressor1.agressor.m_20280_(LivingEntityIn);
         int agressionLevel = hive$villageaggressor1.agressionLevel;
         if (d1 <= d0 || agressionLevel > previousAgressionLevel) {
            hive$villageaggressor = hive$villageaggressor1;
            d0 = d1;
         }

         previousAgressionLevel = agressionLevel;
      }

      return hive$villageaggressor == null ? null : hive$villageaggressor.agressor;
   }

   public Player getNearestTargetPlayer(LivingEntity villageDefender, Level world) {
      double d0 = Double.MAX_VALUE;
      Player PlayerEntity = null;
      Iterator var6 = this.playerReputation.keySet().iterator();

      while(var6.hasNext()) {
         UUID s = (UUID)var6.next();
         if (this.isPlayerReputationLowEnoughToFight(s)) {
            Player PlayerEntity1 = world.m_46003_(s);
            if (PlayerEntity1 != null) {
               double d1 = PlayerEntity1.m_20280_(villageDefender);
               if (d1 <= d0) {
                  PlayerEntity = PlayerEntity1;
                  d0 = d1;
               }
            }
         }
      }

      return PlayerEntity;
   }

   private void removeDeadAndOldAgressors() {
      Iterator iterator = this.villageAgressors.iterator();

      while(true) {
         MyrmexHive.HiveAggressor hive$villageaggressor;
         do {
            if (!iterator.hasNext()) {
               return;
            }

            hive$villageaggressor = (MyrmexHive.HiveAggressor)iterator.next();
         } while(hive$villageaggressor.agressor.m_6084_() && Math.abs(this.tickCounter - hive$villageaggressor.agressionTime) <= 300);

         iterator.remove();
      }
   }

   public int getPlayerReputation(UUID playerName) {
      Integer integer = (Integer)this.playerReputation.get(playerName);
      return integer == null ? 0 : integer;
   }

   private UUID findUUID(String name) {
      if (this.world != null && this.world.m_7654_() != null) {
         Optional<GameProfile> profile = this.world.m_7654_().m_129927_().m_10996_(name);
         return profile.isPresent() ? UUIDUtil.m_235875_((GameProfile)profile.get()) : UUIDUtil.m_235879_(name);
      } else {
         return UUIDUtil.m_235879_(name);
      }
   }

   public int modifyPlayerReputation(UUID playerName, int reputation) {
      int i = this.getPlayerReputation(playerName);
      int j = Mth.m_14045_(i + reputation, 0, 100);
      if (this.hasOwner && playerName.equals(this.ownerUUID)) {
         j = 100;
      }

      Player player = null;

      try {
         player = this.world.m_46003_(playerName);
      } catch (Exception var7) {
         IceAndFire.LOGGER.warn("Myrmex Hive could not find player with associated UUID");
      }

      if (player != null) {
         if (j - i != 0) {
            player.m_5661_(Component.m_237110_(j - i >= 0 ? "myrmex.message.raised_reputation" : "myrmex.message.lowered_reputation", new Object[]{Math.abs(j - i), j}), true);
         }

         if (i < 25 && j >= 25) {
            player.m_5661_(Component.m_237115_("myrmex.message.peaceful"), false);
         }

         if (i >= 25 && j < 25) {
            player.m_5661_(Component.m_237115_("myrmex.message.hostile"), false);
         }

         if (i < 50 && j >= 50) {
            player.m_5661_(Component.m_237115_("myrmex.message.trade"), false);
         }

         if (i >= 50 && j < 50) {
            player.m_5661_(Component.m_237115_("myrmex.message.no_trade"), false);
         }

         if (i < 75 && j >= 75) {
            player.m_5661_(Component.m_237115_("myrmex.message.can_use_staff"), false);
         }

         if (i >= 75 && j < 75) {
            player.m_5661_(Component.m_237115_("myrmex.message.cant_use_staff"), false);
         }
      }

      this.playerReputation.put(playerName, j);
      return j;
   }

   public boolean isPlayerReputationTooLowToTrade(UUID uuid) {
      return this.getPlayerReputation(uuid) < 50;
   }

   public boolean canPlayerCommandHive(UUID uuid) {
      return this.getPlayerReputation(uuid) >= 75;
   }

   public boolean isPlayerReputationLowEnoughToFight(UUID uuid) {
      return this.getPlayerReputation(uuid) < 25;
   }

   public void readVillageDataFromNBT(CompoundTag compound) {
      this.numMyrmex = compound.m_128451_("PopSize");
      this.reproduces = compound.m_128471_("Reproduces");
      this.hasOwner = compound.m_128471_("HasOwner");
      if (compound.m_128403_("OwnerUUID")) {
         this.ownerUUID = compound.m_128342_("OwnerUUID");
      }

      this.colonyName = compound.m_128461_("ColonyName");
      this.villageRadius = compound.m_128451_("Radius");
      if (compound.m_128403_("WanderRadius")) {
         this.wanderRadius = compound.m_128451_("WanderRadius");
      }

      this.lastAddDoorTimestamp = compound.m_128451_("Stable");
      this.tickCounter = compound.m_128451_("Tick");
      this.noBreedTicks = compound.m_128451_("MTick");
      this.center = new BlockPos(compound.m_128451_("CX"), compound.m_128451_("CY"), compound.m_128451_("CZ"));
      this.centerHelper = new BlockPos(compound.m_128451_("ACX"), compound.m_128451_("ACY"), compound.m_128451_("ACZ"));
      ListTag hiveMembers = compound.m_128437_("HiveMembers", 10);
      this.myrmexList.clear();

      for(int i = 0; i < hiveMembers.size(); ++i) {
         CompoundTag CompoundNBT = hiveMembers.m_128728_(i);
         this.myrmexList.add(CompoundNBT.m_128342_("MyrmexUUID"));
      }

      ListTag foodRoomList = compound.m_128437_("FoodRooms", 10);
      this.foodRooms.clear();

      for(int i = 0; i < foodRoomList.size(); ++i) {
         CompoundTag CompoundNBT = foodRoomList.m_128728_(i);
         this.foodRooms.add(new BlockPos(CompoundNBT.m_128451_("X"), CompoundNBT.m_128451_("Y"), CompoundNBT.m_128451_("Z")));
      }

      ListTag babyRoomList = compound.m_128437_("BabyRooms", 10);
      this.babyRooms.clear();

      for(int i = 0; i < babyRoomList.size(); ++i) {
         CompoundTag CompoundNBT = babyRoomList.m_128728_(i);
         this.babyRooms.add(new BlockPos(CompoundNBT.m_128451_("X"), CompoundNBT.m_128451_("Y"), CompoundNBT.m_128451_("Z")));
      }

      ListTag miscRoomList = compound.m_128437_("MiscRooms", 10);
      this.miscRooms.clear();

      for(int i = 0; i < miscRoomList.size(); ++i) {
         CompoundTag CompoundNBT = miscRoomList.m_128728_(i);
         this.miscRooms.add(new BlockPos(CompoundNBT.m_128451_("X"), CompoundNBT.m_128451_("Y"), CompoundNBT.m_128451_("Z")));
      }

      ListTag entrancesList = compound.m_128437_("Entrances", 10);
      this.entrances.clear();

      for(int i = 0; i < entrancesList.size(); ++i) {
         CompoundTag CompoundNBT = entrancesList.m_128728_(i);
         this.entrances.put(new BlockPos(CompoundNBT.m_128451_("X"), CompoundNBT.m_128451_("Y"), CompoundNBT.m_128451_("Z")), Direction.m_122407_(CompoundNBT.m_128451_("Facing")));
      }

      ListTag entranceBottomsList = compound.m_128437_("EntranceBottoms", 10);
      this.entranceBottoms.clear();

      for(int i = 0; i < entranceBottomsList.size(); ++i) {
         CompoundTag CompoundNBT = entranceBottomsList.m_128728_(i);
         this.entranceBottoms.put(new BlockPos(CompoundNBT.m_128451_("X"), CompoundNBT.m_128451_("Y"), CompoundNBT.m_128451_("Z")), Direction.m_122407_(CompoundNBT.m_128451_("Facing")));
      }

      this.hiveUUID = compound.m_128342_("HiveUUID");
      ListTag nbttaglist1 = compound.m_128437_("Players", 10);
      this.playerReputation.clear();

      for(int j = 0; j < nbttaglist1.size(); ++j) {
         CompoundTag CompoundNBT1 = nbttaglist1.m_128728_(j);
         if (CompoundNBT1.m_128403_("UUID")) {
            this.playerReputation.put(CompoundNBT1.m_128342_("UUID"), CompoundNBT1.m_128451_("S"));
         } else {
            this.playerReputation.put(this.findUUID(CompoundNBT1.m_128461_("Name")), CompoundNBT1.m_128451_("S"));
         }
      }

   }

   public void writeVillageDataToNBT(CompoundTag compound) {
      compound.m_128405_("PopSize", this.numMyrmex);
      compound.m_128379_("Reproduces", this.reproduces);
      compound.m_128379_("HasOwner", this.hasOwner);
      if (this.ownerUUID != null) {
         compound.m_128362_("OwnerUUID", this.ownerUUID);
      }

      compound.m_128359_("ColonyName", this.colonyName);
      compound.m_128405_("Radius", this.villageRadius);
      compound.m_128405_("WanderRadius", this.wanderRadius);
      compound.m_128405_("Stable", this.lastAddDoorTimestamp);
      compound.m_128405_("Tick", this.tickCounter);
      compound.m_128405_("MTick", this.noBreedTicks);
      compound.m_128405_("CX", this.center.m_123341_());
      compound.m_128405_("CY", this.center.m_123342_());
      compound.m_128405_("CZ", this.center.m_123343_());
      compound.m_128405_("ACX", this.centerHelper.m_123341_());
      compound.m_128405_("ACY", this.centerHelper.m_123342_());
      compound.m_128405_("ACZ", this.centerHelper.m_123343_());
      ListTag hiveMembers = new ListTag();
      Iterator var3 = this.myrmexList.iterator();

      while(var3.hasNext()) {
         UUID memberUUID = (UUID)var3.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128362_("MyrmexUUID", memberUUID);
         hiveMembers.add(CompoundNBT);
      }

      compound.m_128365_("HiveMembers", hiveMembers);
      ListTag foodRoomList = new ListTag();
      Iterator var15 = this.foodRooms.iterator();

      while(var15.hasNext()) {
         BlockPos pos = (BlockPos)var15.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128405_("X", pos.m_123341_());
         CompoundNBT.m_128405_("Y", pos.m_123342_());
         CompoundNBT.m_128405_("Z", pos.m_123343_());
         foodRoomList.add(CompoundNBT);
      }

      compound.m_128365_("FoodRooms", foodRoomList);
      ListTag babyRoomList = new ListTag();
      Iterator var18 = this.babyRooms.iterator();

      while(var18.hasNext()) {
         BlockPos pos = (BlockPos)var18.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128405_("X", pos.m_123341_());
         CompoundNBT.m_128405_("Y", pos.m_123342_());
         CompoundNBT.m_128405_("Z", pos.m_123343_());
         babyRoomList.add(CompoundNBT);
      }

      compound.m_128365_("BabyRooms", babyRoomList);
      ListTag miscRoomList = new ListTag();
      Iterator var21 = this.miscRooms.iterator();

      while(var21.hasNext()) {
         BlockPos pos = (BlockPos)var21.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128405_("X", pos.m_123341_());
         CompoundNBT.m_128405_("Y", pos.m_123342_());
         CompoundNBT.m_128405_("Z", pos.m_123343_());
         miscRoomList.add(CompoundNBT);
      }

      compound.m_128365_("MiscRooms", miscRoomList);
      ListTag entrancesList = new ListTag();
      Iterator var24 = this.entrances.entrySet().iterator();

      while(var24.hasNext()) {
         Entry<BlockPos, Direction> entry = (Entry)var24.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128405_("X", ((BlockPos)entry.getKey()).m_123341_());
         CompoundNBT.m_128405_("Y", ((BlockPos)entry.getKey()).m_123342_());
         CompoundNBT.m_128405_("Z", ((BlockPos)entry.getKey()).m_123343_());
         CompoundNBT.m_128405_("Facing", ((Direction)entry.getValue()).m_122416_());
         entrancesList.add(CompoundNBT);
      }

      compound.m_128365_("Entrances", entrancesList);
      ListTag entranceBottomsList = new ListTag();
      Iterator var27 = this.entranceBottoms.entrySet().iterator();

      while(var27.hasNext()) {
         Entry<BlockPos, Direction> entry = (Entry)var27.next();
         CompoundTag CompoundNBT = new CompoundTag();
         CompoundNBT.m_128405_("X", ((BlockPos)entry.getKey()).m_123341_());
         CompoundNBT.m_128405_("Y", ((BlockPos)entry.getKey()).m_123342_());
         CompoundNBT.m_128405_("Z", ((BlockPos)entry.getKey()).m_123343_());
         CompoundNBT.m_128405_("Facing", ((Direction)entry.getValue()).m_122416_());
         entranceBottomsList.add(CompoundNBT);
      }

      compound.m_128365_("EntranceBottoms", entranceBottomsList);
      compound.m_128362_("HiveUUID", this.hiveUUID);
      ListTag nbttaglist1 = new ListTag();
      Iterator var30 = this.playerReputation.keySet().iterator();

      while(var30.hasNext()) {
         UUID s = (UUID)var30.next();
         CompoundTag CompoundNBT1 = new CompoundTag();

         try {
            CompoundNBT1.m_128362_("UUID", s);
            CompoundNBT1.m_128405_("S", (Integer)this.playerReputation.get(s));
            nbttaglist1.add(CompoundNBT1);
         } catch (RuntimeException var13) {
         }
      }

      compound.m_128365_("Players", nbttaglist1);
   }

   public void addRoom(BlockPos center, WorldGenMyrmexHive.RoomType roomType) {
      if (roomType == WorldGenMyrmexHive.RoomType.FOOD && !this.foodRooms.contains(center)) {
         this.foodRooms.add(center);
      } else if (roomType == WorldGenMyrmexHive.RoomType.NURSERY && !this.babyRooms.contains(center)) {
         this.babyRooms.add(center);
      } else if (!this.miscRooms.contains(center) && !this.miscRooms.contains(center)) {
         this.miscRooms.add(center);
      }

      this.allRooms.add(center);
   }

   public void addRoomWithMessage(Player player, BlockPos center, WorldGenMyrmexHive.RoomType roomType) {
      List<BlockPos> allCurrentRooms = new ArrayList(this.getAllRooms());
      allCurrentRooms.addAll(this.getEntrances().keySet());
      allCurrentRooms.addAll(this.getEntranceBottoms().keySet());
      if (roomType == WorldGenMyrmexHive.RoomType.FOOD) {
         if (!this.foodRooms.contains(center) && !allCurrentRooms.contains(center)) {
            this.foodRooms.add(center);
            player.m_5661_(Component.m_237110_("myrmex.message.added_food_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
         } else {
            player.m_5661_(Component.m_237110_("myrmex.message.dupe_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
         }
      } else if (roomType == WorldGenMyrmexHive.RoomType.NURSERY) {
         if (!this.babyRooms.contains(center) && !allCurrentRooms.contains(center)) {
            this.babyRooms.add(center);
            player.m_5661_(Component.m_237110_("myrmex.message.added_nursery_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
         } else {
            player.m_5661_(Component.m_237110_("myrmex.message.dupe_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
         }
      } else if (!this.miscRooms.contains(center) && !allCurrentRooms.contains(center)) {
         this.miscRooms.add(center);
         player.m_5661_(Component.m_237110_("myrmex.message.added_misc_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
      } else {
         player.m_5661_(Component.m_237110_("myrmex.message.dupe_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
      }

   }

   public void addEnteranceWithMessage(Player player, boolean bottom, BlockPos center, Direction facing) {
      List<BlockPos> allCurrentRooms = new ArrayList(this.getAllRooms());
      allCurrentRooms.addAll(this.getEntrances().keySet());
      allCurrentRooms.addAll(this.getEntranceBottoms().keySet());
      if (bottom) {
         if (allCurrentRooms.contains(center)) {
            player.m_5661_(Component.m_237110_("myrmex.message.dupe_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
         } else {
            this.getEntranceBottoms().put(center, facing);
            player.m_5661_(Component.m_237110_("myrmex.message.added_enterance_bottom", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
         }
      } else if (allCurrentRooms.contains(center)) {
         player.m_5661_(Component.m_237110_("myrmex.message.dupe_room", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
      } else {
         this.getEntrances().put(center, facing);
         player.m_5661_(Component.m_237110_("myrmex.message.added_enterance_surface", new Object[]{center.m_123341_(), center.m_123342_(), center.m_123343_()}), false);
      }

   }

   public List<BlockPos> getRooms(WorldGenMyrmexHive.RoomType roomType) {
      if (roomType == WorldGenMyrmexHive.RoomType.FOOD) {
         return this.foodRooms;
      } else {
         return roomType == WorldGenMyrmexHive.RoomType.NURSERY ? this.babyRooms : this.miscRooms;
      }
   }

   public List<BlockPos> getAllRooms() {
      this.allRooms.clear();
      this.allRooms.add(this.center);
      this.allRooms.addAll(this.foodRooms);
      this.allRooms.addAll(this.babyRooms);
      this.allRooms.addAll(this.miscRooms);
      return this.allRooms;
   }

   public BlockPos getRandomRoom(RandomSource random, BlockPos returnPos) {
      List<BlockPos> rooms = this.getAllRooms();
      return rooms.isEmpty() ? returnPos : (BlockPos)rooms.get(random.m_188503_(Math.max(rooms.size() - 1, 1)));
   }

   public BlockPos getRandomRoom(WorldGenMyrmexHive.RoomType roomType, RandomSource random, BlockPos returnPos) {
      List<BlockPos> rooms = this.getRooms(roomType);
      return rooms.isEmpty() ? returnPos : (BlockPos)rooms.get(random.m_188503_(Math.max(rooms.size() - 1, 1)));
   }

   public BlockPos getClosestEntranceToEntity(Entity entity, RandomSource random, boolean randomize) {
      Entry<BlockPos, Direction> closest = this.getClosestEntrance(entity);
      if (closest != null) {
         if (randomize) {
            BlockPos pos = ((BlockPos)closest.getKey()).m_5484_((Direction)closest.getValue(), random.m_188503_(7) + 7).m_6630_(4);
            return pos.m_7918_(10 - random.m_188503_(20), 0, 10 - random.m_188503_(20));
         } else {
            return ((BlockPos)closest.getKey()).m_5484_((Direction)closest.getValue(), 3);
         }
      } else {
         return entity.m_20183_();
      }
   }

   public BlockPos getClosestEntranceBottomToEntity(Entity entity, RandomSource random) {
      Entry<BlockPos, Direction> closest = null;
      Iterator var4 = this.entranceBottoms.entrySet().iterator();

      while(true) {
         Entry entry;
         Vec3i vec;
         do {
            if (!var4.hasNext()) {
               return closest != null ? (BlockPos)closest.getKey() : entity.m_20183_();
            }

            entry = (Entry)var4.next();
            vec = new Vec3i(entity.m_146903_(), entity.m_146904_(), entity.m_146907_());
         } while(closest != null && !(((BlockPos)closest.getKey()).m_123331_(vec) > ((BlockPos)entry.getKey()).m_123331_(vec)));

         closest = entry;
      }
   }

   public Player getOwner(Level world) {
      return this.hasOwner ? world.m_46003_(this.ownerUUID) : null;
   }

   public Map<BlockPos, Direction> getEntrances() {
      return this.entrances;
   }

   public Map<BlockPos, Direction> getEntranceBottoms() {
      return this.entranceBottoms;
   }

   private Entry<BlockPos, Direction> getClosestEntrance(Entity entity) {
      Entry<BlockPos, Direction> closest = null;
      Iterator var3 = this.entrances.entrySet().iterator();

      while(true) {
         Entry entry;
         Vec3i vec;
         do {
            if (!var3.hasNext()) {
               return closest;
            }

            entry = (Entry)var3.next();
            vec = new Vec3i(entity.m_146903_(), entity.m_146904_(), entity.m_146907_());
         } while(closest != null && !(((BlockPos)closest.getKey()).m_123331_(vec) > ((BlockPos)entry.getKey()).m_123331_(vec)));

         closest = entry;
      }
   }

   public void setDefaultPlayerReputation(int defaultReputation) {
      Iterator var2 = this.playerReputation.keySet().iterator();

      while(var2.hasNext()) {
         UUID s = (UUID)var2.next();
         this.modifyPlayerReputation(s, defaultReputation);
      }

   }

   public boolean repopulate() {
      int roomCount = this.getAllRooms().size();
      return this.numMyrmex < Math.min(IafConfig.myrmexColonySize, roomCount * 9) && this.reproduces;
   }

   public void addMyrmex(EntityMyrmexBase myrmex) {
      if (!this.myrmexList.contains(myrmex.m_20148_())) {
         this.myrmexList.add(myrmex.m_20148_());
      }

   }

   public void removeRoom(BlockPos pos) {
      this.foodRooms.remove(pos);
      this.miscRooms.remove(pos);
      this.babyRooms.remove(pos);
      this.allRooms.remove(pos);
      this.getEntrances().remove(pos);
      this.getEntranceBottoms().remove(pos);
   }

   public String toString() {
      int var10000 = this.center.m_123341_();
      return "MyrmexHive(x=" + var10000 + ",y=" + this.center.m_123342_() + ",z=" + this.center.m_123343_() + "), population=" + this.getNumMyrmex() + "\nUUID: " + this.hiveUUID;
   }

   public boolean equals(MyrmexHive hive) {
      return this.hiveUUID.equals(hive.hiveUUID);
   }

   class HiveAggressor {
      public LivingEntity agressor;
      public int agressionTime;
      public int agressionLevel;

      HiveAggressor(LivingEntity agressorIn, int agressionTimeIn, int agressionLevel) {
         this.agressor = agressorIn;
         this.agressionTime = agressionTimeIn;
         this.agressionLevel = agressionLevel;
      }
   }
}
