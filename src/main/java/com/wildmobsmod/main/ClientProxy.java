package com.wildmobsmod.main;

import com.wildmobsmod.entity.monster.dreath.EntityDreath;
import com.wildmobsmod.entity.monster.dreath.RenderDreath;
import com.wildmobsmod.entity.monster.dreath.mired.EntityMired;
import com.wildmobsmod.entity.monster.dreath.mired.EntityMiredSummoner;
import com.wildmobsmod.entity.monster.dreath.mired.ModelMired;
import com.wildmobsmod.entity.monster.dreath.mired.RenderMired;
import com.wildmobsmod.entity.monster.dreath.mired.RenderMiredSummoner;
import com.wildmobsmod.entity.monster.faded.EntityFaded;
import com.wildmobsmod.entity.monster.faded.RenderFaded;
import com.wildmobsmod.entity.monster.magmaplant.EntityMagmaPlant;
import com.wildmobsmod.entity.monster.magmaplant.ModelMagmaPlant;
import com.wildmobsmod.entity.monster.magmaplant.RenderMagmaPlant;
import com.wildmobsmod.entity.monster.seascorpion.EntitySeaScorpion;
import com.wildmobsmod.entity.monster.seascorpion.ModelSeaScorpion;
import com.wildmobsmod.entity.monster.seascorpion.RenderSeaScorpion;
import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;
import com.wildmobsmod.entity.monster.skeletonwolf.ModelSkeletonWolf;
import com.wildmobsmod.entity.monster.skeletonwolf.RenderSkeletonWolf;
import com.wildmobsmod.entity.monster.tarantula.EntityTarantula;
import com.wildmobsmod.entity.monster.tarantula.RenderTarantula;
import com.wildmobsmod.entity.monster.wizard.EntityWizard;
import com.wildmobsmod.entity.monster.wizard.RenderWizard;
import com.wildmobsmod.entity.monster.zomgus.EntityZomgus;
import com.wildmobsmod.entity.monster.zomgus.RenderZomgus;
import com.wildmobsmod.entity.passive.armadillo.EntityArmadillo;
import com.wildmobsmod.entity.passive.armadillo.ModelArmadillo;
import com.wildmobsmod.entity.passive.armadillo.RenderArmadillo;
import com.wildmobsmod.entity.passive.bison.EntityBison;
import com.wildmobsmod.entity.passive.bison.ModelBison;
import com.wildmobsmod.entity.passive.bison.RenderBison;
import com.wildmobsmod.entity.passive.butterfly.EntityButterfly;
import com.wildmobsmod.entity.passive.butterfly.ModelButterfly;
import com.wildmobsmod.entity.passive.butterfly.RenderButterfly;
import com.wildmobsmod.entity.passive.cheetah.EntityCheetah;
import com.wildmobsmod.entity.passive.cheetah.ModelCheetah;
import com.wildmobsmod.entity.passive.cheetah.RenderCheetah;
import com.wildmobsmod.entity.passive.cougar.EntityCougar;
import com.wildmobsmod.entity.passive.cougar.ModelCougar;
import com.wildmobsmod.entity.passive.cougar.RenderCougar;
import com.wildmobsmod.entity.passive.deer.EntityDeer;
import com.wildmobsmod.entity.passive.deer.ModelDeer;
import com.wildmobsmod.entity.passive.deer.ModelDeerSaddle;
import com.wildmobsmod.entity.passive.deer.RenderDeer;
import com.wildmobsmod.entity.passive.direwolf.EntityDireWolf;
import com.wildmobsmod.entity.passive.direwolf.ModelDireWolf;
import com.wildmobsmod.entity.passive.direwolf.RenderDireWolf;
import com.wildmobsmod.entity.passive.dragonfly.EntityDragonfly;
import com.wildmobsmod.entity.passive.dragonfly.ModelDragonfly;
import com.wildmobsmod.entity.passive.dragonfly.RenderDragonfly;
import com.wildmobsmod.entity.passive.fox.EntityFox;
import com.wildmobsmod.entity.passive.fox.ModelFox;
import com.wildmobsmod.entity.passive.fox.RenderFox;
import com.wildmobsmod.entity.passive.goat.EntityGoat;
import com.wildmobsmod.entity.passive.goat.ModelGoat;
import com.wildmobsmod.entity.passive.goat.RenderGoat;
import com.wildmobsmod.entity.passive.goose.EntityGoose;
import com.wildmobsmod.entity.passive.goose.ModelGoose;
import com.wildmobsmod.entity.passive.goose.RenderGoose;
import com.wildmobsmod.entity.passive.hyena.EntityHyena;
import com.wildmobsmod.entity.passive.hyena.ModelHyena;
import com.wildmobsmod.entity.passive.hyena.RenderHyena;
import com.wildmobsmod.entity.passive.jellyfish.EntityJellyfish;
import com.wildmobsmod.entity.passive.jellyfish.ModelJellyfish;
import com.wildmobsmod.entity.passive.jellyfish.RenderJellyfish;
import com.wildmobsmod.entity.passive.mouse.EntityMouse;
import com.wildmobsmod.entity.passive.mouse.ModelMouse;
import com.wildmobsmod.entity.passive.mouse.RenderMouse;
import com.wildmobsmod.entity.passive.ocelot.EntityWMOcelot;
import com.wildmobsmod.entity.passive.ocelot.ModelWMOcelot;
import com.wildmobsmod.entity.passive.ocelot.RenderWMOcelot;
import com.wildmobsmod.entity.passive.wolf.EntityWMWolf;
import com.wildmobsmod.entity.passive.wolf.RenderWMWolf;
import com.wildmobsmod.entity.projectile.lavaspit.EntityLavaSpit;
import com.wildmobsmod.entity.projectile.lavaspit.RenderLavaSpit;
import com.wildmobsmod.entity.projectile.seascorpionegg.EntitySeaScorpionEgg;
import com.wildmobsmod.entity.projectile.spell.EntitySpell;
import com.wildmobsmod.entity.projectile.spell.RenderSpell;
import com.wildmobsmod.entity.projectile.tarantulahair.EntityTarantulaHair;
import com.wildmobsmod.entity.projectile.tarantulahair.RenderTarantulaHair;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.particles.EntityLavaSpitFX;
import com.wildmobsmod.particles.EntitySpellFX;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;

public class ClientProxy extends CommonProxy
{
	@Override
	public void registerRenderThings()
	{
		RenderingRegistry.addNewArmourRendererPrefix("5");
		RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer(new ModelDeer(), new ModelDeerSaddle(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityFox.class, new RenderFox(new ModelFox(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityCougar.class, new RenderCougar(new ModelCougar(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityZomgus.class, new RenderZomgus());
		RenderingRegistry.registerEntityRenderingHandler(EntityBison.class, new RenderBison(new ModelBison(), 0));
//		RenderingRegistry.registerEntityRenderingHandler(EntityWizard.class, new RenderWizard()); //Disabled for now
//		RenderingRegistry.registerEntityRenderingHandler(EntitySpell.class, new RenderSpell()); //Disabled for now
		RenderingRegistry.registerEntityRenderingHandler(EntityMouse.class, new RenderMouse(new ModelMouse(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, new RenderButterfly(new ModelButterfly(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityTarantula.class, new RenderTarantula());
		RenderingRegistry.registerEntityRenderingHandler(EntityDreath.class, new RenderDreath());
		RenderingRegistry.registerEntityRenderingHandler(EntityMired.class, new RenderMired(new ModelMired(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityGoat.class, new RenderGoat(new ModelGoat(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityDireWolf.class, new RenderDireWolf(new ModelDireWolf(), new ModelDireWolf(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityMagmaPlant.class, new RenderMagmaPlant(new ModelMagmaPlant(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityLavaSpit.class, new RenderLavaSpit());
		RenderingRegistry.registerEntityRenderingHandler(EntityDragonfly.class, new RenderDragonfly(new ModelDragonfly(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityWMWolf.class, new RenderWMWolf(new ModelWolf(), new ModelWolf(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityArmadillo.class, new RenderArmadillo(new ModelArmadillo(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, new RenderJellyfish(new ModelJellyfish(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySkeletonWolf.class, new RenderSkeletonWolf(new ModelSkeletonWolf(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityGoose.class, new RenderGoose(new ModelGoose(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityMiredSummoner.class, new RenderMiredSummoner());
		RenderingRegistry.registerEntityRenderingHandler(EntityWMOcelot.class, new RenderWMOcelot(new ModelWMOcelot(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySeaScorpion.class, new RenderSeaScorpion(new ModelSeaScorpion(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntitySeaScorpionEgg.class, new RenderSnowball(WildMobsModItems.seaScorpionEgg));
		RenderingRegistry.registerEntityRenderingHandler(EntityTarantulaHair.class, new RenderTarantulaHair());
		RenderingRegistry.registerEntityRenderingHandler(EntityFaded.class, new RenderFaded());
		RenderingRegistry.registerEntityRenderingHandler(EntityCheetah.class, new RenderCheetah(new ModelCheetah(), 0));
		RenderingRegistry.registerEntityRenderingHandler(EntityHyena.class, new RenderHyena(new ModelHyena(), 0));
	}

	@SideOnly(Side.CLIENT)
	public static void generateEntitySpellFX(Entity theEntity, double x1, double y1, double z1, double x2, double y2, double z2, float red, float green, float blue)
	{
		double motionX = x2;
		double motionY = y2;
		double motionZ = z2;
		float colorRed = red;
		float colorGreen = green;
		float colorBlue = blue;
		EntityFX particleSpell = new EntitySpellFX(theEntity.worldObj, x1, y1, z1, motionX, motionY, motionZ, colorRed, colorGreen, colorBlue);
		Minecraft.getMinecraft().effectRenderer.addEffect(particleSpell);
	}

	@SideOnly(Side.CLIENT)
	public static void generateEntityMagmaSpitFX(Entity theEntity, double x1, double y1, double z1, double x2, double y2, double z2)
	{
		double motionX = x2;
		double motionY = y2;
		double motionZ = z2;
		EntityFX particleMagmaSpit = new EntityLavaSpitFX(theEntity.worldObj, x1, y1, z1, motionX, motionY, motionZ);
		Minecraft.getMinecraft().effectRenderer.addEffect(particleMagmaSpit);
	}
}
