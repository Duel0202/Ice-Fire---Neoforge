package com.github.alexthe666.iceandfire.misc;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforge.common.data.ExistingFileHelper;
import net.neoforge.data.event.GatherDataEvent;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   modid = "iceandfire",
   bus = Bus.MOD
)
public class IafDamageRegistry {
   public static final ResourceKey<DamageType> GORGON_DMG_TYPE;
   public static final ResourceKey<DamageType> DRAGON_FIRE_TYPE;
   public static final ResourceKey<DamageType> DRAGON_ICE_TYPE;
   public static final ResourceKey<DamageType> DRAGON_LIGHTNING_TYPE;

   public static IafDamageRegistry.CustomEntityDamageSource causeGorgonDamage(@Nullable Entity entity) {
      Holder<DamageType> holder = (Holder)entity.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(GORGON_DMG_TYPE).get();
      return new IafDamageRegistry.CustomEntityDamageSource(holder, entity);
   }

   public static IafDamageRegistry.CustomEntityDamageSource causeDragonFireDamage(@Nullable Entity entity) {
      Holder<DamageType> holder = (Holder)entity.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(DRAGON_FIRE_TYPE).get();
      return new IafDamageRegistry.CustomEntityDamageSource(holder, entity);
   }

   public static IafDamageRegistry.CustomIndirectEntityDamageSource causeIndirectDragonFireDamage(Entity source, @Nullable Entity indirectEntityIn) {
      Holder<DamageType> holder = (Holder)indirectEntityIn.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(DRAGON_FIRE_TYPE).get();
      return new IafDamageRegistry.CustomIndirectEntityDamageSource(holder, source, indirectEntityIn);
   }

   public static IafDamageRegistry.CustomEntityDamageSource causeDragonIceDamage(@Nullable Entity entity) {
      Holder<DamageType> holder = (Holder)entity.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(DRAGON_ICE_TYPE).get();
      return new IafDamageRegistry.CustomEntityDamageSource(holder, entity);
   }

   public static IafDamageRegistry.CustomIndirectEntityDamageSource causeIndirectDragonIceDamage(Entity source, @Nullable Entity indirectEntityIn) {
      Holder<DamageType> holder = (Holder)indirectEntityIn.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(DRAGON_ICE_TYPE).get();
      return new IafDamageRegistry.CustomIndirectEntityDamageSource(holder, source, indirectEntityIn);
   }

   public static IafDamageRegistry.CustomEntityDamageSource causeDragonLightningDamage(@Nullable Entity entity) {
      Holder<DamageType> holder = (Holder)entity.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(DRAGON_LIGHTNING_TYPE).get();
      return new IafDamageRegistry.CustomEntityDamageSource(holder, entity);
   }

   public static IafDamageRegistry.CustomIndirectEntityDamageSource causeIndirectDragonLightningDamage(Entity source, @Nullable Entity indirectEntityIn) {
      Holder<DamageType> holder = (Holder)indirectEntityIn.m_9236_().m_9598_().m_175515_(Registries.f_268580_).m_203636_(DRAGON_LIGHTNING_TYPE).get();
      return new IafDamageRegistry.CustomIndirectEntityDamageSource(holder, source, indirectEntityIn);
   }

   @SubscribeEvent
   public void gatherData(GatherDataEvent event) {
      event.getGenerator().addProvider(event.includeServer(), (output) -> {
         return new IafDamageRegistry.IafDamageTypeTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), "iceandfire", event.getExistingFileHelper());
      });
   }

   static {
      GORGON_DMG_TYPE = ResourceKey.m_135785_(Registries.f_268580_, new ResourceLocation("iceandfire:gorgon"));
      DRAGON_FIRE_TYPE = ResourceKey.m_135785_(Registries.f_268580_, new ResourceLocation("iceandfire:dragon_fire"));
      DRAGON_ICE_TYPE = ResourceKey.m_135785_(Registries.f_268580_, new ResourceLocation("iceandfire:dragon_ice"));
      DRAGON_LIGHTNING_TYPE = ResourceKey.m_135785_(Registries.f_268580_, new ResourceLocation("iceandfire:dragon_lightning"));
   }

   static class CustomEntityDamageSource extends DamageSource {
      public CustomEntityDamageSource(Holder<DamageType> damageTypeIn, @Nullable Entity damageSourceEntityIn) {
         super(damageTypeIn, damageSourceEntityIn);
      }

      @NotNull
      public Component m_6157_(LivingEntity entityLivingBaseIn) {
         LivingEntity livingentity = entityLivingBaseIn.m_21232_();
         String s = "death.attack." + this.m_19385_();
         int index = entityLivingBaseIn.m_217043_().m_188503_(2);
         String s1 = s + "." + index;
         String s2 = s + ".attacker_" + index;
         return livingentity != null ? Component.m_237110_(s2, new Object[]{entityLivingBaseIn.m_5446_(), livingentity.m_5446_()}) : Component.m_237110_(s1, new Object[]{entityLivingBaseIn.m_5446_()});
      }
   }

   static class CustomIndirectEntityDamageSource extends DamageSource {
      public CustomIndirectEntityDamageSource(Holder<DamageType> damageTypeIn, Entity source, @Nullable Entity indirectEntityIn) {
         super(damageTypeIn, source, indirectEntityIn);
      }

      @NotNull
      public Component m_6157_(LivingEntity entityLivingBaseIn) {
         LivingEntity livingentity = entityLivingBaseIn.m_21232_();
         String s = "death.attack." + this.m_19385_();
         int index = entityLivingBaseIn.m_217043_().m_188503_(2);
         String s1 = s + "." + index;
         String s2 = s + ".attacker_" + index;
         return livingentity != null ? Component.m_237110_(s2, new Object[]{entityLivingBaseIn.m_5446_(), livingentity.m_5446_()}) : Component.m_237110_(s1, new Object[]{entityLivingBaseIn.m_5446_()});
      }
   }

   public static class IafDamageTypeTagsProvider extends DamageTypeTagsProvider {
      public IafDamageTypeTagsProvider(PackOutput p_270719_, CompletableFuture<Provider> p_270256_, String modId, @org.jetbrains.annotations.Nullable ExistingFileHelper existingFileHelper) {
         super(p_270719_, p_270256_, modId, existingFileHelper);
      }

      public void m_6577_(Provider pProvider) {
         this.m_206424_(DamageTypeTags.f_268490_).m_255204_(IafDamageRegistry.GORGON_DMG_TYPE);
         this.m_206424_(DamageTypeTags.f_268437_).m_255204_(IafDamageRegistry.GORGON_DMG_TYPE);
      }
   }
}
