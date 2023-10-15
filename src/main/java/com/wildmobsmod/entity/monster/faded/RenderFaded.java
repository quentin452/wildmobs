package com.wildmobsmod.entity.monster.faded;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderFaded extends RenderZombie
{
	private static final ResourceLocation fadedTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/zombie/faded.png");

	public RenderFaded()
	{
		super();
	}

	protected void preRenderCallback(EntityFaded faded, float partialTickTime)
	{
		GL11.glScalef(0.75F, 0.75F, 0.75F);
	}

	protected ResourceLocation getEntityTexture(EntityFaded faded)
	{
		return fadedTextures;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityFaded) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving living)
	{
		return this.getEntityTexture((EntityFaded) living);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityFaded) entity);
	}
}
