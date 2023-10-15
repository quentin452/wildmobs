package com.wildmobsmod.entity.passive.goat;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderGoat extends RenderLiving
{
	private static final ResourceLocation whiteTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/goat/goat_white.png");
	private static final ResourceLocation brownTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/goat/goat_brown.png");
	private static final ResourceLocation grayTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/goat/goat_gray.png");
	private static final ResourceLocation splotchedTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/goat/goat_splotched.png");

	public RenderGoat(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.4F;
	}

	protected ResourceLocation getEntityTexture(EntityGoat goat)
	{
		switch(goat.getDataWatcher().getWatchableObjectByte(20))
		{
			case 0:
				return whiteTextures;
			case 1:
				return brownTextures;
			case 2:
				return grayTextures;
			case 3:
				return splotchedTextures;
			default:
				return whiteTextures;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityGoat) entity);
	}
}
