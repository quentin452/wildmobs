package com.wildmobsmod.entity.passive.butterfly;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderButterfly extends RenderLiving
{
	private static final String BUTTERFLY_PATH = WildMobsMod.MODID + ":textures/entity/butterfly/";
	private static final ResourceLocation beigeTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_beige.png");
	private static final ResourceLocation blueTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_blue.png");
	private static final ResourceLocation glassTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_glass.png");
	private static final ResourceLocation limeTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_lime.png");
	private static final ResourceLocation orangeTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_orange.png");
	private static final ResourceLocation purpleTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_purple.png");
	private static final ResourceLocation redTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_red.png");
	private static final ResourceLocation whiteTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_white.png");
	private static final ResourceLocation yellowTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_yellow.png");
	private static final ResourceLocation cyanTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_cyan.png");
	private static final ResourceLocation brownTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_brown.png");
	private static final ResourceLocation blackTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_black.png");
	private static final ResourceLocation greenTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_green.png");
	private static final ResourceLocation grayTextures = new ResourceLocation(BUTTERFLY_PATH + "butterfly_gray.png");

	public RenderButterfly(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.2F;
	}

	protected ResourceLocation getEntityTexture(EntityButterfly butterfly)
	{
		switch(butterfly.getDataWatcher().getWatchableObjectByte(20))
		{
			case 0:
				return beigeTextures;
			case 1:
				return blueTextures;
			case 2:
				return glassTextures;
			case 3:
				return limeTextures;
			case 4:
				return orangeTextures;
			case 5:
				return purpleTextures;
			case 6:
				return redTextures;
			case 7:
				return whiteTextures;
			case 8:
				return yellowTextures;
			case 9:
				return cyanTextures;
			case 10:
				return brownTextures;
			case 11:
				return blackTextures;
			case 12:
				return greenTextures;
			case 13:
				return grayTextures;
			default:
				return orangeTextures;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityButterfly) entity);
	}
}
