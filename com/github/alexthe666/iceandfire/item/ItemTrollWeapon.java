package com.github.alexthe666.iceandfire.item;

import com.github.alexthe666.iceandfire.client.render.tile.RenderTrollWeapon;
import com.github.alexthe666.iceandfire.enums.EnumTroll;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforge.common.util.NonNullLazy;
import org.jetbrains.annotations.NotNull;

public class ItemTrollWeapon extends SwordItem {
   public EnumTroll.Weapon weapon;

   public ItemTrollWeapon(EnumTroll.Weapon weapon) {
      super(IafItemRegistry.TROLL_WEAPON_TOOL_MATERIAL, 15, -3.5F, new Properties());
      this.weapon = EnumTroll.Weapon.AXE;
      this.weapon = weapon;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      consumer.accept(new IClientItemExtensions() {
         static final NonNullLazy<BlockEntityWithoutLevelRenderer> renderer = NonNullLazy.of(() -> {
            return new RenderTrollWeapon(Minecraft.m_91087_().m_167982_(), Minecraft.m_91087_().m_167973_());
         });

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return (BlockEntityWithoutLevelRenderer)renderer.get();
         }
      });
   }

   public boolean onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
      return (double)player.m_36403_(0.0F) < 0.95D || player.f_20921_ != 0.0F;
   }

   public boolean onEntitySwing(LivingEntity LivingEntity, ItemStack stack) {
      if (LivingEntity instanceof Player) {
         Player player = (Player)LivingEntity;
         if (player.m_36403_(0.0F) < 1.0F && player.f_20921_ > 0.0F) {
            return true;
         }

         player.f_20913_ = -1;
      }

      return false;
   }

   public void onUpdate(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof Player && isSelected) {
         Player player = (Player)entityIn;
         if ((double)player.m_36403_(0.0F) < 0.95D && player.f_20921_ > 0.0F) {
            --player.f_20913_;
         }
      }

   }

   public void m_7373_(@NotNull ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, @NotNull TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("item.iceandfire.legendary_weapon.desc").m_130940_(ChatFormatting.GRAY));
   }
}
