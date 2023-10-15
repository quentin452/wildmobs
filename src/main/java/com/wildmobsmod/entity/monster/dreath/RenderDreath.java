package com.wildmobsmod.entity.monster.dreath;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderDreath extends RenderBiped
{
	private static final ResourceLocation dreathTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dreath/dreath.png");

	public RenderDreath()
	{
		super(new ModelDreath(), 0.5F);
	}

	protected void preRenderCallback(EntityDreath dreath, float partialTickTime)
	{
		GL11.glScalef(1.1F, 1.1F, 1.1F);
	}

	protected ResourceLocation getEntityTexture(EntityDreath dreath)
	{
		return dreathTextures;
	}
	
	@Override
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityDreath) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving living)
	{
		return this.getEntityTexture((EntityDreath) living);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityDreath) entity);
	}
}
