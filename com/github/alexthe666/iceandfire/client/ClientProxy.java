package com.github.alexthe666.iceandfire.client;

import com.github.alexthe666.iceandfire.CommonProxy;
import com.github.alexthe666.iceandfire.client.gui.GuiMyrmexAddRoom;
import com.github.alexthe666.iceandfire.client.gui.GuiMyrmexStaff;
import com.github.alexthe666.iceandfire.client.gui.bestiary.GuiBestiary;
import com.github.alexthe666.iceandfire.client.particle.ParticleBlood;
import com.github.alexthe666.iceandfire.client.particle.ParticleDragonFlame;
import com.github.alexthe666.iceandfire.client.particle.ParticleDragonFrost;
import com.github.alexthe666.iceandfire.client.particle.ParticleDreadPortal;
import com.github.alexthe666.iceandfire.client.particle.ParticleDreadTorch;
import com.github.alexthe666.iceandfire.client.particle.ParticleGhostAppearance;
import com.github.alexthe666.iceandfire.client.particle.ParticleHydraBreath;
import com.github.alexthe666.iceandfire.client.particle.ParticlePixieDust;
import com.github.alexthe666.iceandfire.client.particle.ParticleSerpentBubble;
import com.github.alexthe666.iceandfire.client.particle.ParticleSirenAppearance;
import com.github.alexthe666.iceandfire.client.particle.ParticleSirenMusic;
import com.github.alexthe666.iceandfire.client.render.entity.layer.LayerDragonArmor;
import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import com.github.alexthe666.iceandfire.entity.util.MyrmexHive;
import com.github.alexthe666.iceandfire.enums.EnumParticles;
import com.github.alexthe666.iceandfire.event.ClientEvents;
import com.github.alexthe666.iceandfire.event.PlayerRenderEvents;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforge.api.distmarker.Dist;
import net.neoforge.api.distmarker.OnlyIn;
import net.neoforge.common.MinecraftForge;
import net.neoforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "iceandfire",
   value = {Dist.CLIENT}
)
public class ClientProxy extends CommonProxy {
   public static Set<UUID> currentDragonRiders = new HashSet();
   private static MyrmexHive referedClientHive = null;
   private int previousViewType = 0;
   private int thirdPersonViewDragon = 0;
   private Entity referencedMob = null;
   private BlockEntity referencedTE = null;

   public static MyrmexHive getReferedClientHive() {
      return referedClientHive;
   }

   public void setReferencedHive(MyrmexHive hive) {
      referedClientHive = hive;
   }

   public void init() {
      IafKeybindRegistry.init();
      MinecraftForge.EVENT_BUS.register(new PlayerRenderEvents());
      MinecraftForge.EVENT_BUS.register(new ClientEvents());
   }

   public void postInit() {
   }

   public void clientInit() {
      super.clientInit();
      IafClientSetup.clientInit();
   }

   @OnlyIn(Dist.CLIENT)
   public void spawnDragonParticle(EnumParticles name, double x, double y, double z, double motX, double motY, double motZ, EntityDragonBase entityDragonBase) {
      ClientLevel world = Minecraft.m_91087_().f_91073_;
      if (world != null) {
         Particle particle = null;
         if (name == EnumParticles.DragonFire) {
            particle = new ParticleDragonFlame(world, x, y, z, motX, motY, motZ, entityDragonBase, 0);
         } else if (name == EnumParticles.DragonIce) {
            particle = new ParticleDragonFrost(world, x, y, z, motX, motY, motZ, entityDragonBase, 0);
         }

         if (particle != null) {
            Minecraft.m_91087_().f_91061_.m_107344_((Particle)particle);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public void spawnParticle(EnumParticles name, double x, double y, double z, double motX, double motY, double motZ, float size) {
      ClientLevel world = Minecraft.m_91087_().f_91073_;
      if (world != null) {
         Particle particle = null;
         switch(name) {
         case DragonFire:
            particle = new ParticleDragonFlame(world, x, y, z, motX, motY, motZ, size);
            break;
         case DragonIce:
            particle = new ParticleDragonFrost(world, x, y, z, motX, motY, motZ, size);
            break;
         case Dread_Torch:
            particle = new ParticleDreadTorch(world, x, y, z, motX, motY, motZ, size);
            break;
         case Dread_Portal:
            particle = new ParticleDreadPortal(world, x, y, z, motX, motY, motZ, size);
            break;
         case Blood:
            particle = new ParticleBlood(world, x, y, z);
            break;
         case If_Pixie:
            particle = new ParticlePixieDust(world, x, y, z, (float)motX, (float)motY, (float)motZ);
            break;
         case Siren_Appearance:
            particle = new ParticleSirenAppearance(world, x, y, z, (int)motX);
            break;
         case Ghost_Appearance:
            particle = new ParticleGhostAppearance(world, x, y, z, (int)motX);
            break;
         case Siren_Music:
            particle = new ParticleSirenMusic(world, x, y, z, motX, motY, motZ, 1.0F);
            break;
         case Serpent_Bubble:
            particle = new ParticleSerpentBubble(world, x, y, z, motX, motY, motZ, 1.0F);
            break;
         case Hydra:
            particle = new ParticleHydraBreath(world, x, y, z, (float)motX, (float)motY, (float)motZ);
         }

         if (particle != null) {
            Minecraft.m_91087_().f_91061_.m_107344_((Particle)particle);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   public void openBestiaryGui(ItemStack book) {
      Minecraft.m_91087_().m_91152_(new GuiBestiary(book));
   }

   @OnlyIn(Dist.CLIENT)
   public void openMyrmexStaffGui(ItemStack staff) {
      Minecraft.m_91087_().m_91152_(new GuiMyrmexStaff(staff));
   }

   @OnlyIn(Dist.CLIENT)
   public void openMyrmexAddRoomGui(ItemStack staff, BlockPos pos, Direction facing) {
      Minecraft.m_91087_().m_91152_(new GuiMyrmexAddRoom(staff, pos, facing));
   }

   @OnlyIn(Dist.CLIENT)
   public Object getFontRenderer() {
      return Minecraft.m_91087_().f_91062_;
   }

   public int getDragon3rdPersonView() {
      return this.thirdPersonViewDragon;
   }

   public void setDragon3rdPersonView(int view) {
      this.thirdPersonViewDragon = view;
   }

   public int getPreviousViewType() {
      return this.previousViewType;
   }

   public void setPreviousViewType(int view) {
      this.previousViewType = view;
   }

   public void updateDragonArmorRender(String clear) {
      LayerDragonArmor.clearCache(clear);
   }

   @OnlyIn(Dist.CLIENT)
   public boolean shouldSeeBestiaryContents() {
      return InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 340) || InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), 344);
   }

   public Entity getReferencedMob() {
      return this.referencedMob;
   }

   public void setReferencedMob(Entity dragonBase) {
      this.referencedMob = dragonBase;
   }

   public BlockEntity getRefrencedTE() {
      return this.referencedTE;
   }

   public void setRefrencedTE(BlockEntity tileEntity) {
      this.referencedTE = tileEntity;
   }

   @OnlyIn(Dist.CLIENT)
   public Player getClientSidePlayer() {
      return Minecraft.m_91087_().f_91074_;
   }
}
