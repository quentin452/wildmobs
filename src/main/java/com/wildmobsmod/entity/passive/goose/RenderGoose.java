package com.wildmobsmod.entity.passive.goose;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RenderGoose extends RenderLiving
{
	private static final ResourceLocation gooseTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/goose.png");

	public RenderGoose(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.5F;
	}

	protected void preRenderCallback(EntityGoose goose, float partialTickTime)
	{
		GL11.glScalef(1.2F, 1.2F, 1.2F);
	}

	protected ResourceLocation getEntityTexture(EntityGoose goose)
	{
		return gooseTextures;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityGoose) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityGoose) entity);
	}
}
