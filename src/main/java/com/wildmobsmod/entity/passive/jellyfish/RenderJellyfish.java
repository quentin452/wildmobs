package com.wildmobsmod.entity.passive.jellyfish;

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
public class RenderJellyfish extends RenderLiving
{
	private static final ResourceLocation orangeTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_orange.png");
	private static final ResourceLocation blueTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_blue.png");
	private static final ResourceLocation pinkTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_pink.png");
	private static final ResourceLocation redTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_red.png");
	private static final ResourceLocation yellowTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_yellow.png");
	private static final ResourceLocation whiteTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_white.png");
	private static final ResourceLocation netherTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/jellyfish/jellyfish_nether.png");

	public RenderJellyfish(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.4F;
	}

	protected void preRenderCallback(EntityJellyfish jellyfish, float partialTickTime)
	{
		if(jellyfish.getNether() == false)
		{
			GL11.glScalef(jellyfish.width / 2, jellyfish.height / 2, jellyfish.width / 2);
		}
		else
		{
			GL11.glScalef(jellyfish.width / 2 * 1.5F, jellyfish.height / 2 * 1.5F, jellyfish.width / 2 * 1.5F);
		}
	}

	protected ResourceLocation getEntityTexture(EntityJellyfish jellyfish)
	{
		int type = jellyfish.getDataWatcher().getWatchableObjectByte(20);
		switch(type)
		{
			case 0:
				return orangeTextures;
			case 1:
				return blueTextures;
			case 2:
				return pinkTextures;
			case 3:
				return redTextures;
			case 4:
				return yellowTextures;
			case 5:
				return whiteTextures;
			case 6:
				return netherTextures;
			default:
				return orangeTextures;
		}
	}

	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityJellyfish) living, partialTickTime);
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityJellyfish) entity);
	}

}
