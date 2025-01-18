package com.github.alexthe666.iceandfire.world;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.world.gen.TypedFeature;
import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

public class IafWorldData extends SavedData {
   private static final String IDENTIFIER = "iceandfire_general";
   private static final Map<IafWorldData.FeatureType, List<Pair<String, BlockPos>>> LAST_GENERATED = new HashMap();

   public IafWorldData() {
   }

   public IafWorldData(CompoundTag tag) {
      this.load(tag);
   }

   public static IafWorldData get(Level world) {
      if (world instanceof ServerLevel) {
         ServerLevel overworld = world.m_7654_().m_129880_(world.m_46472_());
         DimensionDataStorage storage = overworld.m_8895_();
         IafWorldData data = (IafWorldData)storage.m_164861_(IafWorldData::new, IafWorldData::new, "iceandfire_general");
         data.m_77762_();
         return data;
      } else {
         return null;
      }
   }

   public boolean check(TypedFeature feature, BlockPos position, String id) {
      return this.check(feature.getFeatureType(), position, id);
   }

   public boolean check(IafWorldData.FeatureType type, BlockPos position, String id) {
      List<Pair<String, BlockPos>> entries = (List)LAST_GENERATED.computeIfAbsent(type, (key) -> {
         return new ArrayList();
      });
      boolean canGenerate = true;
      Pair<String, BlockPos> toRemove = null;

      Pair entry;
      for(Iterator var7 = entries.iterator(); var7.hasNext(); canGenerate = position.m_123331_((Vec3i)entry.getSecond()) > IafConfig.dangerousWorldGenSeparationLimit * IafConfig.dangerousWorldGenSeparationLimit) {
         entry = (Pair)var7.next();
         if (((String)entry.getFirst()).equals(id)) {
            toRemove = entry;
         }
      }

      if (toRemove != null) {
         entries.remove(toRemove);
      }

      entries.add(Pair.of(id, position));
      return canGenerate;
   }

   public IafWorldData load(CompoundTag tag) {
      IafWorldData.FeatureType[] types = IafWorldData.FeatureType.values();
      IafWorldData.FeatureType[] var3 = types;
      int var4 = types.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         IafWorldData.FeatureType type = var3[var5];
         ListTag list = tag.m_128437_(type.toString(), 10);

         for(int i = 0; i < list.size(); ++i) {
            CompoundTag entry = list.m_128728_(i);
            String id = entry.m_128461_("id");
            BlockPos position = NbtUtils.m_129239_(entry.m_128469_("position"));
            ((List)LAST_GENERATED.computeIfAbsent(type, (key) -> {
               return new ArrayList();
            })).add(Pair.of(id, position));
         }
      }

      return this;
   }

   @NotNull
   public CompoundTag m_7176_(@NotNull CompoundTag tag) {
      LAST_GENERATED.forEach((key, value) -> {
         ListTag listTag = new ListTag();
         value.forEach((entry) -> {
            CompoundTag subTag = new CompoundTag();
            subTag.m_128359_("id", (String)entry.getFirst());
            subTag.m_128365_("position", NbtUtils.m_129224_((BlockPos)entry.getSecond()));
            listTag.add(subTag);
         });
         tag.m_128365_(key.toString(), listTag);
      });
      return tag;
   }

   public static enum FeatureType {
      SURFACE,
      UNDERGROUND,
      OCEAN;

      // $FF: synthetic method
      private static IafWorldData.FeatureType[] $values() {
         return new IafWorldData.FeatureType[]{SURFACE, UNDERGROUND, OCEAN};
      }
   }
}
