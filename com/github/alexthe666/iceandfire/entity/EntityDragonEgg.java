package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IafConfig;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.entity.tile.TileEntityEggInIce;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.github.alexthe666.iceandfire.entity.util.IDeadMob;
import com.github.alexthe666.iceandfire.enums.EnumDragonEgg;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.misc.IafSoundRegistry;
import com.google.common.collect.ImmutableList;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class EntityDragonEgg extends LivingEntity implements IBlacklistedFromStatues, IDeadMob {
   protected static final EntityDataAccessor<Optional<UUID>> OWNER_UNIQUE_ID;
   private static final EntityDataAccessor<Integer> DRAGON_TYPE;
   private static final EntityDataAccessor<Integer> DRAGON_AGE;

   public EntityDragonEgg(EntityType type, Level worldIn) {
      super(type, worldIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 10.0D).m_22268_(Attributes.f_22279_, 0.0D);
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("Color", (byte)this.getEggType().ordinal());
      tag.m_128405_("DragonAge", this.getDragonAge());

      try {
         if (this.getOwnerId() == null) {
            tag.m_128359_("OwnerUUID", "");
         } else {
            tag.m_128359_("OwnerUUID", this.getOwnerId().toString());
         }
      } catch (Exception var3) {
         IceAndFire.LOGGER.error("An error occurred while trying to read the NBT data of a dragon egg", var3);
      }

   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      this.setEggType(EnumDragonEgg.values()[tag.m_128451_("Color")]);
      this.setDragonAge(tag.m_128451_("DragonAge"));
      String s;
      if (tag.m_128425_("OwnerUUID", 8)) {
         s = tag.m_128461_("OwnerUUID");
      } else {
         String s1 = tag.m_128461_("Owner");
         UUID converedUUID = OldUsersConverter.m_11083_(this.m_20194_(), s1);
         s = converedUUID == null ? s1 : converedUUID.toString();
      }

      if (!s.isEmpty()) {
         this.setOwnerId(UUID.fromString(s));
      }

   }

   protected void m_8097_() {
      super.m_8097_();
      this.m_20088_().m_135372_(DRAGON_TYPE, 0);
      this.m_20088_().m_135372_(DRAGON_AGE, 0);
      this.m_20088_().m_135372_(OWNER_UNIQUE_ID, Optional.empty());
   }

   @Nullable
   public UUID getOwnerId() {
      return (UUID)((Optional)this.f_19804_.m_135370_(OWNER_UNIQUE_ID)).orElse((Object)null);
   }

   public void setOwnerId(@Nullable UUID p_184754_1_) {
      this.f_19804_.m_135381_(OWNER_UNIQUE_ID, Optional.ofNullable(p_184754_1_));
   }

   public EnumDragonEgg getEggType() {
      return EnumDragonEgg.values()[(Integer)this.m_20088_().m_135370_(DRAGON_TYPE)];
   }

   public void setEggType(EnumDragonEgg newtype) {
      this.m_20088_().m_135381_(DRAGON_TYPE, newtype.ordinal());
   }

   public boolean m_6673_(DamageSource i) {
      return i.m_7639_() != null && super.m_6673_(i);
   }

   public int getDragonAge() {
      return (Integer)this.m_20088_().m_135370_(DRAGON_AGE);
   }

   public void setDragonAge(int i) {
      this.m_20088_().m_135381_(DRAGON_AGE, i);
   }

   public void m_8119_() {
      super.m_8119_();
      if (!this.m_9236_().m_5776_()) {
         this.m_20301_(200);
         this.updateEggCondition();
      }

   }

   public void updateEggCondition() {
      DragonType dragonType = this.getEggType().dragonType;
      if (dragonType == DragonType.FIRE) {
         if (this.m_9236_().m_8055_(this.m_20183_()).isBurning(this.m_9236_(), this.m_20183_())) {
            this.setDragonAge(this.getDragonAge() + 1);
         }
      } else if (dragonType == DragonType.ICE) {
         BlockState state = this.m_9236_().m_8055_(this.m_20183_());
         if (state.m_60713_(Blocks.f_49990_) && this.m_217043_().m_188503_(500) == 0) {
            this.m_9236_().m_46597_(this.m_20183_(), ((Block)IafBlockRegistry.EGG_IN_ICE.get()).m_49966_());
            this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), SoundEvents.f_11983_, this.m_5720_(), 2.5F, 1.0F, false);
            BlockEntity var4 = this.m_9236_().m_7702_(this.m_20183_());
            if (var4 instanceof TileEntityEggInIce) {
               TileEntityEggInIce eggInIce = (TileEntityEggInIce)var4;
               eggInIce.type = this.getEggType();
               eggInIce.ownerUUID = this.getOwnerId();
            }

            this.m_142687_(RemovalReason.DISCARDED);
         }
      } else if (dragonType == DragonType.LIGHTNING) {
         MutableBlockPos mutablePosition = new MutableBlockPos(this.m_20185_(), this.m_20186_(), this.m_20189_());
         boolean isRainingAt = this.m_9236_().m_46758_(mutablePosition) || this.m_9236_().m_46758_(mutablePosition.m_122169_(this.m_20185_(), this.m_20186_() + (double)this.m_20206_(), this.m_20189_()));
         if (this.m_9236_().m_45527_(this.m_20183_().m_7494_()) && isRainingAt) {
            this.setDragonAge(this.getDragonAge() + 1);
         }
      }

      if (this.getDragonAge() > IafConfig.dragonEggTime) {
         this.m_9236_().m_46597_(this.m_20183_(), Blocks.f_50016_.m_49966_());
         EntityDragonBase dragon = (EntityDragonBase)dragonType.getEntity().m_20615_(this.m_9236_());
         if (this.m_8077_()) {
            dragon.m_6593_(this.m_7770_());
         }

         if (dragonType == DragonType.LIGHTNING) {
            dragon.setVariant(this.getEggType().ordinal() - 8);
         } else {
            dragon.setVariant(this.getEggType().ordinal());
         }

         dragon.setGender(this.m_217043_().m_188499_());
         dragon.m_6034_((double)this.m_20183_().m_123341_() + 0.5D, (double)(this.m_20183_().m_123342_() + 1), (double)this.m_20183_().m_123343_() + 0.5D);
         dragon.setHunger(50);
         if (!this.m_9236_().m_5776_()) {
            this.m_9236_().m_7967_(dragon);
         }

         if (this.m_8077_()) {
            dragon.m_6593_(this.m_7770_());
         }

         dragon.m_7105_(true);
         dragon.m_21816_(this.getOwnerId());
         if (dragonType == DragonType.LIGHTNING) {
            LightningBolt bolt = (LightningBolt)EntityType.f_20465_.m_20615_(this.m_9236_());
            bolt.m_6034_(this.m_20185_(), this.m_20186_(), this.m_20189_());
            bolt.m_20874_(true);
            if (!this.m_9236_().m_5776_()) {
               this.m_9236_().m_7967_(bolt);
            }

            this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), SoundEvents.f_12090_, this.m_5720_(), 2.5F, 1.0F, false);
         } else if (dragonType == DragonType.FIRE) {
            this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), SoundEvents.f_11937_, this.m_5720_(), 2.5F, 1.0F, false);
         }

         this.m_9236_().m_7785_(this.m_20185_(), this.m_20186_() + (double)this.m_20192_(), this.m_20189_(), IafSoundRegistry.EGG_HATCH, this.m_5720_(), 2.5F, 1.0F, false);
         this.m_142687_(RemovalReason.DISCARDED);
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

   public boolean m_6469_(@NotNull DamageSource var1, float var2) {
      if (var1.m_269533_(DamageTypeTags.f_268745_) && this.getEggType().dragonType == DragonType.FIRE) {
         return false;
      } else {
         if (!this.m_9236_().f_46443_ && !var1.m_269533_(DamageTypeTags.f_268738_) && !this.m_213877_()) {
            this.m_20000_(this.getItem().m_41720_(), 1);
         }

         this.m_142687_(RemovalReason.KILLED);
         return true;
      }
   }

   private ItemStack getItem() {
      ItemStack var10000;
      switch(this.getEggType().ordinal()) {
      case 1:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_GREEN.get());
         break;
      case 2:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_BRONZE.get());
         break;
      case 3:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_GRAY.get());
         break;
      case 4:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_BLUE.get());
         break;
      case 5:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_WHITE.get());
         break;
      case 6:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_SAPPHIRE.get());
         break;
      case 7:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_SILVER.get());
         break;
      case 8:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_ELECTRIC.get());
         break;
      case 9:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_AMYTHEST.get());
         break;
      case 10:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_COPPER.get());
         break;
      case 11:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_BLACK.get());
         break;
      default:
         var10000 = new ItemStack((ItemLike)IafItemRegistry.DRAGONEGG_RED.get());
      }

      return var10000;
   }

   public boolean m_6094_() {
      return false;
   }

   @NotNull
   public HumanoidArm m_5737_() {
      return HumanoidArm.RIGHT;
   }

   protected void m_7324_(@NotNull Entity entity) {
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   public void onPlayerPlace(Player player) {
      this.setOwnerId(player.m_20148_());
   }

   public boolean isMobDead() {
      return true;
   }

   static {
      OWNER_UNIQUE_ID = SynchedEntityData.m_135353_(EntityDragonEgg.class, EntityDataSerializers.f_135041_);
      DRAGON_TYPE = SynchedEntityData.m_135353_(EntityDragonEgg.class, EntityDataSerializers.f_135028_);
      DRAGON_AGE = SynchedEntityData.m_135353_(EntityDragonEgg.class, EntityDataSerializers.f_135028_);
   }
}
