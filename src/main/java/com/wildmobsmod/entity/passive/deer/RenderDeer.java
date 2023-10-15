package com.wildmobsmod.entity.passive.deer;

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
public class RenderDeer extends RenderLiving
{
	private static final ResourceLocation whitetailTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/deer/deer_whitetail.png");
	private static final ResourceLocation reindeerTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/deer/deer_reindeer.png");
	private static final ResourceLocation rudolphTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/deer/deer_rudolph.png");
	private static final ResourceLocation elkTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/deer/deer_elk.png");
	private static final ResourceLocation saddledTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/deer/deer_saddle.png");

	public RenderDeer(ModelBase model1, ModelBase model2, float shadowSize)
	{
		super(model1, shadowSize);
		this.setRenderPassModel(model2);
		this.shadowSize = 0.7F;
	}

	protected void preRenderCallback(EntityDeer deer, float partialTickTime)
	{
		if(deer.getSkin() == 2)
		{
			GL11.glScalef(1.1F, 1.1F, 1.1F);
		}
	}

	protected int shouldRenderPass(EntityDeer deer, int pass, float partialTickTime)
	{
		if(pass == 0 && deer.getSaddled())
		{
			this.bindTexture(saddledTextures);
			return 1;
		}
		return -1;
	}

	protected ResourceLocation getEntityTexture(EntityDeer entity)
	{
		int type = entity.getDataWatcher().getWatchableObjectByte(20);
		switch(entity.getDataWatcher().getWatchableObjectByte(20))
		{
			case 0:
				return whitetailTextures;
			case 1:
			{
				if(entity.hasCustomNameTag() && "Rudolph".equals(entity.getCustomNameTag()))
				{
					return rudolphTextures;
				}
				else
				{
					return reindeerTextures;
				}
			}
			case 2:
				return elkTextures;
			default:
				return whitetailTextures;
		}
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase living, int pass, float partialTickTime)
	{
		return this.shouldRenderPass((EntityDeer) living, pass, partialTickTime);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityDeer) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityDeer) entity);
	}
}
