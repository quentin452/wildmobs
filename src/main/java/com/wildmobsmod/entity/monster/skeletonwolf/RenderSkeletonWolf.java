package com.wildmobsmod.entity.monster.skeletonwolf;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSkeletonWolf extends RenderLiving
{
	private static final ResourceLocation skeletonWolfTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wolf_skeleton.png");
	private static final ResourceLocation witherSkeletonWolfTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/wolf/wither_wolf_skeleton.png");

	public RenderSkeletonWolf(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.5F;
	}

	protected void preRenderCallback(EntitySkeletonWolf skeletonWolf, float partialTickTime)
	{
		if(skeletonWolf.getSkeletonType() == 1)
		{
			GL11.glScalef(1.2F, 1.2F, 1.2F);
		}
	}

	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	protected ResourceLocation getEntityTexture(EntitySkeletonWolf skeletonWolf)
	{
		return skeletonWolf.getSkeletonType() == 1 ? witherSkeletonWolfTextures : skeletonWolfTextures;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntitySkeletonWolf) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntitySkeletonWolf) entity);
	}
}
