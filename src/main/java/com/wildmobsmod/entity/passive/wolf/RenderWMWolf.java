package com.wildmobsmod.entity.passive.wolf;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderWMWolf extends RenderLiving
{
	private static final ResourceLocation anrgyWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
	private static final ResourceLocation wolfCollarTextures = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
	private static final ResourceLocation wolfTextures = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static final ResourceLocation tamedWolfTextures = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	private static final ResourceLocation tamedWolfArcticTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_arctic.png");
	private static final ResourceLocation tamedWolfHuskyTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_husky.png");
	private static final ResourceLocation tamedWolfBerneseTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_bernese.png");
	private static final ResourceLocation tamedWolfBlackTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_black.png");
	private static final ResourceLocation tamedWolfBrownTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_brown.png");
	private static final ResourceLocation tamedWolfDalmatianTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_dalmatian.png");
	private static final ResourceLocation tamedWolfHoundTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_hound.png");
	private static final ResourceLocation tamedWolfPointerTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_pointer.png");
	private static final ResourceLocation tamedWolfShepherdTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_shepherd.png");
	private static final ResourceLocation tamedWolfShibaTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_tame_shiba.png");

	public RenderWMWolf(ModelBase model1, ModelBase model2, float shadowSize)
	{
		super(model1, shadowSize);
		this.setRenderPassModel(model2);
		this.shadowSize = 0.5F;
	}

	protected float handleRotationFloat(EntityWMWolf wolf, float f)
	{
		return wolf.getTailRotation();
	}

	protected int shouldRenderPass(EntityWMWolf wolf, int pass, float partialTickTime)
	{
		if(pass == 0 && wolf.getWolfShaking())
		{
			float f1 = wolf.getBrightness(partialTickTime) * wolf.getShadingWhileShaking(partialTickTime);
			GL11.glColor3f(f1, f1, f1);
			return 1;
		}
		else if(pass == 1 && wolf.isTamed())
		{
			this.bindTexture(wolfCollarTextures);
			int j = wolf.getCollarColor();
			GL11.glColor3f(EntitySheep.fleeceColorTable[j][0], EntitySheep.fleeceColorTable[j][1], EntitySheep.fleeceColorTable[j][2]);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	protected ResourceLocation getEntityTexture(EntityWMWolf wolf)
	{
		switch(wolf.getSkin())
		{
			case 1 : return tamedWolfArcticTextures;
			case 2 : return tamedWolfHuskyTextures;
			case 3 : return tamedWolfBerneseTextures;
			case 4 : return tamedWolfBlackTextures;
			case 5 : return tamedWolfBrownTextures;
			case 6 : return tamedWolfDalmatianTextures;
			case 7 : return tamedWolfHoundTextures;
			case 8 : return tamedWolfPointerTextures;
			case 9 : return tamedWolfShepherdTextures;
			case 10 : return tamedWolfShibaTextures;
			case 0 :
			default : return wolf.isTamed() ? tamedWolfTextures : wolf.isAngry() ? anrgyWolfTextures : wolfTextures;
		}
	}

	protected int shouldRenderPass(EntityLivingBase living, int pass, float partialTickTime)
	{
		return this.shouldRenderPass((EntityWMWolf) living, pass, partialTickTime);
	}

	protected float handleRotationFloat(EntityLivingBase living, float partialTickTime)
	{
		return this.handleRotationFloat((EntityWMWolf) living, partialTickTime);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityWMWolf) entity);
	}
}
