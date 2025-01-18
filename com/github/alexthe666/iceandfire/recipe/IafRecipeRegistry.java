package com.github.alexthe666.iceandfire.recipe;

import com.github.alexthe666.iceandfire.entity.EntityAmphithereArrow;
import com.github.alexthe666.iceandfire.entity.EntityCockatriceEgg;
import com.github.alexthe666.iceandfire.entity.EntityDeathWormEgg;
import com.github.alexthe666.iceandfire.entity.EntityDragonArrow;
import com.github.alexthe666.iceandfire.entity.EntityHippogryphEgg;
import com.github.alexthe666.iceandfire.entity.EntityHydraArrow;
import com.github.alexthe666.iceandfire.entity.EntitySeaSerpentArrow;
import com.github.alexthe666.iceandfire.entity.EntityStymphalianArrow;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.neoforge.common.brewing.BrewingRecipeRegistry;
import net.neoforge.eventbus.api.SubscribeEvent;
import net.neoforge.fml.common.Mod.EventBusSubscriber;
import net.neoforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.neoforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforge.registries.DeferredRegister;
import net.neoforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class IafRecipeRegistry {
   public static final DeferredRegister<RecipeType<?>> RECIPE_TYPE;
   public static final RegistryObject<RecipeType<DragonForgeRecipe>> DRAGON_FORGE_TYPE;

   @SubscribeEvent
   public static void preInit(FMLCommonSetupEvent event) {
      event.enqueueWork(() -> {
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.STYMPHALIAN_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               EntityStymphalianArrow entityarrow = new EntityStymphalianArrow((EntityType)IafEntityRegistry.STYMPHALIAN_ARROW.get(), worldIn, position.m_7096_(), position.m_7098_(), position.m_7094_());
               entityarrow.f_36705_ = Pickup.ALLOWED;
               return entityarrow;
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.AMPHITHERE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               EntityAmphithereArrow entityarrow = new EntityAmphithereArrow((EntityType)IafEntityRegistry.AMPHITHERE_ARROW.get(), worldIn, position.m_7096_(), position.m_7098_(), position.m_7094_());
               entityarrow.f_36705_ = Pickup.ALLOWED;
               return entityarrow;
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.SEA_SERPENT_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               EntitySeaSerpentArrow entityarrow = new EntitySeaSerpentArrow((EntityType)IafEntityRegistry.SEA_SERPENT_ARROW.get(), worldIn, position.m_7096_(), position.m_7098_(), position.m_7094_());
               entityarrow.f_36705_ = Pickup.ALLOWED;
               return entityarrow;
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.DRAGONBONE_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               EntityDragonArrow entityarrow = new EntityDragonArrow((EntityType)IafEntityRegistry.DRAGON_ARROW.get(), position.m_7096_(), position.m_7098_(), position.m_7094_(), worldIn);
               entityarrow.f_36705_ = Pickup.ALLOWED;
               return entityarrow;
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.HYDRA_ARROW.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               EntityHydraArrow entityarrow = new EntityHydraArrow((EntityType)IafEntityRegistry.HYDRA_ARROW.get(), worldIn, position.m_7096_(), position.m_7098_(), position.m_7094_());
               entityarrow.f_36705_ = Pickup.ALLOWED;
               return entityarrow;
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.HIPPOGRYPH_EGG.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               return new EntityHippogryphEgg((EntityType)IafEntityRegistry.HIPPOGRYPH_EGG.get(), worldIn, position.m_7096_(), position.m_7098_(), position.m_7094_(), stackIn);
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.ROTTEN_EGG.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               return new EntityCockatriceEgg((EntityType)IafEntityRegistry.COCKATRICE_EGG.get(), position.m_7096_(), position.m_7098_(), position.m_7094_(), worldIn);
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.DEATHWORM_EGG.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               return new EntityDeathWormEgg((EntityType)IafEntityRegistry.DEATH_WORM_EGG.get(), position.m_7096_(), position.m_7098_(), position.m_7094_(), worldIn, false);
            }
         });
         DispenserBlock.m_52672_((ItemLike)IafItemRegistry.DEATHWORM_EGG_GIGANTIC.get(), new AbstractProjectileDispenseBehavior() {
            @NotNull
            protected Projectile m_6895_(@NotNull Level worldIn, @NotNull Position position, @NotNull ItemStack stackIn) {
               return new EntityDeathWormEgg((EntityType)IafEntityRegistry.DEATH_WORM_EGG.get(), position.m_7096_(), position.m_7098_(), position.m_7094_(), worldIn, true);
            }
         });
         BrewingRecipeRegistry.addRecipe(Ingredient.m_43929_(new ItemLike[]{createPotion(Potions.f_43599_).m_41720_()}), Ingredient.m_43929_(new ItemLike[]{(ItemLike)IafItemRegistry.SHINY_SCALES.get()}), createPotion(Potions.f_43621_));
      });
   }

   public static ItemStack createPotion(Potion potion) {
      return PotionUtils.m_43549_(new ItemStack(Items.f_42589_), potion);
   }

   static {
      RECIPE_TYPE = DeferredRegister.create(Registries.f_256954_, "iceandfire");
      DRAGON_FORGE_TYPE = RECIPE_TYPE.register("dragonforge", () -> {
         return RecipeType.simple(new ResourceLocation("iceandfire", "dragonforge"));
      });
   }
}
