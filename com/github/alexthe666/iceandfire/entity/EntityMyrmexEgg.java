package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IDeadMob;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.github.alexthe666.iceandfire.world.MyrmexWorldData;
import com.github.alexthe666.iceandfire.world.gen.WorldGenMyrmexHive;
import com.google.common.collect.ImmutableList;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class EntityMyrmexEgg extends LivingEntity implements IBlacklistedFromStatues, IDeadMob {
   private static final EntityDataAccessor<Boolean> MYRMEX_TYPE;
   private static final EntityDataAccessor<Integer> MYRMEX_AGE;
   private static final EntityDataAccessor<Integer> MYRMEX_CASTE;
   public UUID hiveUUID;

   public EntityMyrmexEgg(EntityType t, Level worldIn) {
      super(t, worldIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, 0.0D);
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128379_("Jungle", this.isJungle());
      tag.m_128405_("MyrmexAge", this.getMyrmexAge());
      tag.m_128405_("MyrmexCaste", this.getMyrmexCaste());
      tag.m_128362_("HiveUUID", this.hiveUUID == null ? (this.hiveUUID = UUID.randomUUID()) : this.hiveUUID);
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      this.setJungle(tag.m_128471_("Jungle"));
      this.setMyrmexAge(tag.m_128451_("MyrmexAge"));
      this.setMyrmexCaste(tag.m_128451_("MyrmexCaste"));
      this.hiveUUID = tag.m_128342_("HiveUUID");
   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(MYRMEX_TYPE, false);
      this.m_20088_().m_135372_(MYRMEX_AGE, 0);
      this.m_20088_().m_135372_(MYRMEX_CASTE, 0);
   }

   public boolean isJungle() {
      return (Boolean)this.m_20088_().m_135370_(MYRMEX_TYPE);
   }

   public void setJungle(boolean jungle) {
      this.m_20088_().m_135381_(MYRMEX_TYPE, jungle);
   }

   public int getMyrmexAge() {
      return (Integer)this.m_20088_().m_135370_(MYRMEX_AGE);
   }

   public void setMyrmexAge(int i) {
      this.m_20088_().m_135381_(MYRMEX_AGE, i);
   }

   public int getMyrmexCaste() {
      return (Integer)this.m_20088_().m_135370_(MYRMEX_CASTE);
   }

   public void setMyrmexCaste(int i) {
      this.m_20088_().m_135381_(MYRMEX_CASTE, i);
   }

   public boolean canSeeSky() {
      return this.m_9236_().m_46861_(this.m_20183_());
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.canSeeSky()) {
         this.setMyrmexAge(this.getMyrmexAge() + 1);
      }

      if (this.getMyrmexAge() > IafConfig.myrmexEggTicks) {
         this.m_142687_(RemovalReason.DISCARDED);
         Object myrmex;
         switch(this.getMyrmexCaste()) {
         case 1:
            myrmex = new EntityMyrmexSoldier((EntityType)IafEntityRegistry.MYRMEX_SOLDIER.get(), this.m_9236_());
            break;
         case 2:
            myrmex = new EntityMyrmexRoyal((EntityType)IafEntityRegistry.MYRMEX_ROYAL.get(), this.m_9236_());
            break;
         case 3:
            myrmex = new EntityMyrmexSentinel((EntityType)IafEntityRegistry.MYRMEX_SENTINEL.get(), this.m_9236_());
            break;
         case 4:
            myrmex = new EntityMyrmexQueen((EntityType)IafEntityRegistry.MYRMEX_QUEEN.get(), this.m_9236_());
            break;
         default:
            myrmex = new EntityMyrmexWorker((EntityType)IafEntityRegistry.MYRMEX_WORKER.get(), this.m_9236_());
         }

         ((EntityMyrmexBase)myrmex).setJungleVariant(this.isJungle());
         ((EntityMyrmexBase)myrmex).setGrowthStage(0);
         ((EntityMyrmexBase)myrmex).m_19890_(this.m_20185_(), this.m_20186_(), this.m_20189_(), 0.0F, 0.0F);
         MyrmexHive hive;
         if (myrmex instanceof EntityMyrmexQueen) {
            hive = new MyrmexHive(this.m_9236_(), this.m_20183_(), 100);
            Player player = this.m_9236_().m_45930_(this, 30.0D);
            if (player != null) {
               hive.hasOwner = true;
               hive.ownerUUID = player.m_20148_();
               if (!this.m_9236_().f_46443_) {
                  hive.modifyPlayerReputation(player.m_20148_(), 100);
               }
            }

            MyrmexWorldData.addHive(this.m_9236_(), hive);
            ((EntityMyrmexBase)myrmex).setHive(hive);
         } else if (MyrmexWorldData.get(this.m_9236_()) != null) {
            if (this.hiveUUID == null) {
               hive = MyrmexWorldData.get(this.m_9236_()).getNearestHive(this.m_20183_(), 400);
            } else {
               hive = MyrmexWorldData.get(this.m_9236_()).getHiveFromUUID(this.hiveUUID);
            }

            if (!this.m_9236_().f_46443_ && hive != null && Math.sqrt(this.m_20275_((double)hive.getCenter().m_123341_(), (double)hive.getCenter().m_123342_(), (double)hive.getCenter().m_123343_())) < 2000.0D) {
               ((EntityMyrmexBase)myrmex).setHive(hive);
            }
         }

         if (!this.m_9236_().f_46443_) {
            this.m_9236_().m_7967_((Entity)myrmex);
         }

         this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), IafSoundRegistry.EGG_HATCH, this.m_5720_(), 2.5F, 1.0F, false);
      }

   }

   public SoundEvent m_7975_(@NotNull DamageSource damageSourceIn) {
      return null;
   }

   @NotNull
   public Iterable<ItemStack> m_6168_() {
      return ImmutableList.of();
   }

   @NotNull
   public ItemStack m_6844_(@NotNull EquipmentSlot slotIn) {
      return ItemStack.f_41583_;
   }

   public void m_8061_(@NotNull EquipmentSlot slotIn, @NotNull ItemStack stack) {
   }

   public boolean m_6469_(@NotNull DamageSource dmg, float var2) {
      if (!dmg.m_276093_(DamageTypes.f_268612_) && !dmg.m_276093_(DamageTypes.f_268671_)) {
         if (!this.m_9236_().f_46443_ && !dmg.m_269533_(DamageTypeTags.f_268738_)) {
            this.m_5552_(this.getItem(), 0.0F);
         }

         this.m_142687_(RemovalReason.KILLED);
         return super.m_6469_(dmg, var2);
      } else {
         return false;
      }
   }

   private ItemStack getItem() {
      ItemStack egg = new ItemStack(this.isJungle() ? (ItemLike)IafItemRegistry.MYRMEX_JUNGLE_EGG.get() : (ItemLike)IafItemRegistry.MYRMEX_DESERT_EGG.get(), 1);
      CompoundTag newTag = new CompoundTag();
      newTag.m_128405_("EggOrdinal", this.getMyrmexCaste());
      egg.m_41751_(newTag);
      return egg;
   }

   public boolean m_6094_() {
      return false;
   }

   @NotNull
   public HumanoidArm m_5737_() {
      return null;
   }

   protected void m_7324_(@NotNull Entity entity) {
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   public void onPlayerPlace(Player player) {
   }

   public boolean isMobDead() {
      return true;
   }

   public boolean isInNursery() {
      MyrmexHive hive = MyrmexWorldData.get(this.m_9236_()).getNearestHive(this.m_20183_(), 100);
      if (hive != null && hive.getRooms(WorldGenMyrmexHive.RoomType.NURSERY).isEmpty() && hive.getRandomRoom(WorldGenMyrmexHive.RoomType.NURSERY, this.m_217043_(), this.m_20183_()) != null) {
         return false;
      } else if (hive != null) {
         BlockPos nursery = hive.getRandomRoom(WorldGenMyrmexHive.RoomType.NURSERY, this.m_217043_(), this.m_20183_());
         return this.m_20275_((double)nursery.m_123341_(), (double)nursery.m_123342_(), (double)nursery.m_123343_()) < 2025.0D;
      } else {
         return false;
      }
   }

   static {
      MYRMEX_TYPE = SynchedEntityData.m_135353_(EntityMyrmexEgg.class, EntityDataSerializers.f_135035_);
      MYRMEX_AGE = SynchedEntityData.m_135353_(EntityMyrmexEgg.class, EntityDataSerializers.f_135028_);
      MYRMEX_CASTE = SynchedEntityData.m_135353_(EntityMyrmexEgg.class, EntityDataSerializers.f_135028_);
   }
}
