package com.github.alexthe666.iceandfire.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class HomePosition {
   int x;
   int y;
   int z;
   BlockPos pos;
   String dimension;

   public HomePosition(CompoundTag compound) {
      this.read(compound);
   }

   public HomePosition(CompoundTag compound, Level world) {
      this.read(compound, world);
   }

   public HomePosition(BlockPos pos, Level world) {
      this(pos.m_123341_(), pos.m_123342_(), pos.m_123343_(), world);
   }

   public HomePosition(int x, int y, int z, Level world) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.pos = new BlockPos(x, y, z);
      this.dimension = DragonUtils.getDimensionName(world);
   }

   public BlockPos getPosition() {
      return this.pos;
   }

   public String getDimension() {
      return this.dimension == null ? "" : this.dimension;
   }

   public CompoundTag write(CompoundTag compound) {
      compound.m_128405_("HomeAreaX", this.x);
      compound.m_128405_("HomeAreaY", this.y);
      compound.m_128405_("HomeAreaZ", this.z);
      if (this.dimension != null) {
         compound.m_128359_("HomeDimension", this.dimension);
      }

      return compound;
   }

   public HomePosition read(CompoundTag compound, Level world) {
      this.read(compound);
      if (this.dimension == null) {
         this.dimension = DragonUtils.getDimensionName(world);
      }

      return this;
   }

   public HomePosition read(CompoundTag compound) {
      if (compound.m_128441_("HomeAreaX")) {
         this.x = compound.m_128451_("HomeAreaX");
      }

      if (compound.m_128441_("HomeAreaY")) {
         this.y = compound.m_128451_("HomeAreaY");
      }

      if (compound.m_128441_("HomeAreaZ")) {
         this.z = compound.m_128451_("HomeAreaZ");
      }

      this.pos = new BlockPos(this.x, this.y, this.z);
      if (compound.m_128441_("HomeDimension")) {
         this.dimension = compound.m_128461_("HomeDimension");
      }

      return this;
   }
}
