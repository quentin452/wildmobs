package com.wildmobsmod.entity.passive.direwolf;

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
public class RenderDireWolf extends RenderLiving
{
	private static final ResourceLocation direWolfTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/direwolf/direwolf.png");
	private static final ResourceLocation tamedDireWolfTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/direwolf/direwolf_tame.png");
	private static final ResourceLocation anrgyDireWolfTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/direwolf/direwolf_angry.png");
	private static final ResourceLocation direWolfCollarTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/direwolf/direwolf_collar.png");

	public RenderDireWolf(ModelBase model1, ModelBase model2, float shadowSize)
	{
		super(model1, shadowSize);
		this.setRenderPassModel(model2);
		this.shadowSize = 0.6F;
	}

	protected ResourceLocation getEntityTexture(EntityDireWolf direwolf)
	{
		return direwolf.isTamed() ? tamedDireWolfTextures : (direwolf.isAngry() ? anrgyDireWolfTextures : direWolfTextures);
	}

	protected float handleRotationFloat(EntityDireWolf direwolf, float partialTickTime)
	{
		return direwolf.getTailRotation();
	}

	protected int shouldRenderPass(EntityDireWolf direwolf, int pass, float partialTickTime)
	{
		if(pass == 0 && direwolf.getWolfShaking())
		{
			float f1 = direwolf.getBrightness(partialTickTime) * direwolf.getShadingWhileShaking(partialTickTime);
			this.bindTexture(direWolfTextures);
			GL11.glColor3f(f1, f1, f1);
			return 1;
		}
		if(pass == 1 && direwolf.isTamed())
		{
			this.bindTexture(direWolfCollarTextures);
			int j = direwolf.getCollarColor();
			GL11.glColor3f(EntitySheep.fleeceColorTable[j][0], EntitySheep.fleeceColorTable[j][1], EntitySheep.fleeceColorTable[j][2]);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase living, int pass, float partialTickTime)
	{
		return this.shouldRenderPass((EntityDireWolf) living, pass, partialTickTime);
	}

	@Override
	protected float handleRotationFloat(EntityLivingBase living, float partialTickTime)
	{
		return this.handleRotationFloat((EntityDireWolf) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityDireWolf) entity);
	}
}
