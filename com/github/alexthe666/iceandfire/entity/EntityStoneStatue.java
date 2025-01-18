package com.github.alexthe666.iceandfire.entity;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.util.IBlacklistedFromStatues;
import com.google.common.collect.ImmutableList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class EntityStoneStatue extends LivingEntity implements IBlacklistedFromStatues {
   private static final EntityDataAccessor<String> TRAPPED_ENTITY_TYPE;
   private static final EntityDataAccessor<CompoundTag> TRAPPED_ENTITY_DATA;
   private static final EntityDataAccessor<Float> TRAPPED_ENTITY_WIDTH;
   private static final EntityDataAccessor<Float> TRAPPED_ENTITY_HEIGHT;
   private static final EntityDataAccessor<Float> TRAPPED_ENTITY_SCALE;
   private static final EntityDataAccessor<Integer> CRACK_AMOUNT;
   private EntityDimensions stoneStatueSize = EntityDimensions.m_20398_(0.5F, 0.5F);

   public EntityStoneStatue(EntityType<? extends LivingEntity> t, Level worldIn) {
      super(t, worldIn);
   }

   public static Builder bakeAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22276_, 20.0D).m_22268_(Attributes.f_22279_, 0.0D).m_22268_(Attributes.f_22281_, 1.0D);
   }

   public static EntityStoneStatue buildStatueEntity(LivingEntity parent) {
      EntityStoneStatue statue = (EntityStoneStatue)((EntityType)IafEntityRegistry.STONE_STATUE.get()).m_20615_(parent.m_9236_());
      CompoundTag entityTag = new CompoundTag();

      try {
         if (!(parent instanceof Player)) {
            parent.m_20240_(entityTag);
         }
      } catch (Exception var4) {
         IceAndFire.LOGGER.debug("Encountered issue creating stone statue from {}", parent);
      }

      statue.setTrappedTag(entityTag);
      statue.setTrappedEntityTypeString(ForgeRegistries.ENTITY_TYPES.getKey(parent.m_6095_()).toString());
      statue.setTrappedEntityWidth(parent.m_20205_());
      statue.setTrappedHeight(parent.m_20206_());
      statue.setTrappedScale(parent.m_6134_());
      return statue;
   }

   public void m_7334_(@NotNull Entity entityIn) {
   }

   public void m_6075_() {
   }

   protected void m_8097_() {
      super.m_8097_();
      this.f_19804_.m_135372_(TRAPPED_ENTITY_TYPE, "minecraft:pig");
      this.f_19804_.m_135372_(TRAPPED_ENTITY_DATA, new CompoundTag());
      this.f_19804_.m_135372_(TRAPPED_ENTITY_WIDTH, 0.5F);
      this.f_19804_.m_135372_(TRAPPED_ENTITY_HEIGHT, 0.5F);
      this.f_19804_.m_135372_(TRAPPED_ENTITY_SCALE, 1.0F);
      this.f_19804_.m_135372_(CRACK_AMOUNT, 0);
   }

   public EntityType getTrappedEntityType() {
      String str = this.getTrappedEntityTypeString();
      return (EntityType)EntityType.m_20632_(str).orElse(EntityType.f_20510_);
   }

   public String getTrappedEntityTypeString() {
      return (String)this.f_19804_.m_135370_(TRAPPED_ENTITY_TYPE);
   }

   public void setTrappedEntityTypeString(String string) {
      this.f_19804_.m_135381_(TRAPPED_ENTITY_TYPE, string);
   }

   public CompoundTag getTrappedTag() {
      return (CompoundTag)this.f_19804_.m_135370_(TRAPPED_ENTITY_DATA);
   }

   public void setTrappedTag(CompoundTag tag) {
      this.f_19804_.m_135381_(TRAPPED_ENTITY_DATA, tag);
   }

   public float getTrappedWidth() {
      return (Float)this.f_19804_.m_135370_(TRAPPED_ENTITY_WIDTH);
   }

   public void setTrappedEntityWidth(float size) {
      this.f_19804_.m_135381_(TRAPPED_ENTITY_WIDTH, size);
   }

   public float getTrappedHeight() {
      return (Float)this.f_19804_.m_135370_(TRAPPED_ENTITY_HEIGHT);
   }

   public void setTrappedHeight(float size) {
      this.f_19804_.m_135381_(TRAPPED_ENTITY_HEIGHT, size);
   }

   public float getTrappedScale() {
      return (Float)this.f_19804_.m_135370_(TRAPPED_ENTITY_SCALE);
   }

   public void setTrappedScale(float size) {
      this.f_19804_.m_135381_(TRAPPED_ENTITY_SCALE, size);
   }

   public void m_7380_(@NotNull CompoundTag tag) {
      super.m_7380_(tag);
      tag.m_128405_("CrackAmount", this.getCrackAmount());
      tag.m_128350_("StatueWidth", this.getTrappedWidth());
      tag.m_128350_("StatueHeight", this.getTrappedHeight());
      tag.m_128350_("StatueScale", this.getTrappedScale());
      tag.m_128359_("StatueEntityType", this.getTrappedEntityTypeString());
      tag.m_128365_("StatueEntityTag", this.getTrappedTag());
   }

   public float m_6134_() {
      return this.getTrappedScale();
   }

   public void m_7378_(@NotNull CompoundTag tag) {
      super.m_7378_(tag);
      this.setCrackAmount(tag.m_128445_("CrackAmount"));
      this.setTrappedEntityWidth(tag.m_128457_("StatueWidth"));
      this.setTrappedHeight(tag.m_128457_("StatueHeight"));
      this.setTrappedScale(tag.m_128457_("StatueScale"));
      this.setTrappedEntityTypeString(tag.m_128461_("StatueEntityType"));
      if (tag.m_128441_("StatueEntityTag")) {
         this.setTrappedTag(tag.m_128469_("StatueEntityTag"));
      }

   }

   public boolean m_20147_() {
      return true;
   }

   @NotNull
   public EntityDimensions m_6972_(@NotNull Pose poseIn) {
      return this.stoneStatueSize;
   }

   public void m_8119_() {
      super.m_8119_();
      this.m_146922_(this.f_20883_);
      this.f_20885_ = this.m_146908_();
      if ((double)Math.abs(this.m_20205_() - this.getTrappedWidth()) > 0.01D || (double)Math.abs(this.m_20206_() - this.getTrappedHeight()) > 0.01D) {
         double prevX = this.m_20185_();
         double prevZ = this.m_20189_();
         this.stoneStatueSize = EntityDimensions.m_20395_(this.getTrappedWidth(), this.getTrappedHeight());
         this.m_6210_();
         this.m_6034_(prevX, this.m_20186_(), prevZ);
      }

   }

   public void m_6074_() {
      this.m_142687_(RemovalReason.KILLED);
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

   @NotNull
   public HumanoidArm m_5737_() {
      return HumanoidArm.RIGHT;
   }

   public int getCrackAmount() {
      return (Integer)this.f_19804_.m_135370_(CRACK_AMOUNT);
   }

   public void setCrackAmount(int crackAmount) {
      this.f_19804_.m_135381_(CRACK_AMOUNT, crackAmount);
   }

   public boolean m_6040_() {
      return true;
   }

   public boolean canBeTurnedToStone() {
      return false;
   }

   static {
      TRAPPED_ENTITY_TYPE = SynchedEntityData.m_135353_(EntityStoneStatue.class, EntityDataSerializers.f_135030_);
      TRAPPED_ENTITY_DATA = SynchedEntityData.m_135353_(EntityStoneStatue.class, EntityDataSerializers.f_135042_);
      TRAPPED_ENTITY_WIDTH = SynchedEntityData.m_135353_(EntityStoneStatue.class, EntityDataSerializers.f_135029_);
      TRAPPED_ENTITY_HEIGHT = SynchedEntityData.m_135353_(EntityStoneStatue.class, EntityDataSerializers.f_135029_);
      TRAPPED_ENTITY_SCALE = SynchedEntityData.m_135353_(EntityStoneStatue.class, EntityDataSerializers.f_135029_);
      CRACK_AMOUNT = SynchedEntityData.m_135353_(EntityStoneStatue.class, EntityDataSerializers.f_135028_);
   }
}
