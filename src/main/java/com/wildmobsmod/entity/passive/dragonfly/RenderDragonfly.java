package com.wildmobsmod.entity.passive.dragonfly;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderDragonfly extends RenderLiving
{
	private static final ResourceLocation blueTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dragonfly/dragonfly_blue.png");
	private static final ResourceLocation cyanTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dragonfly/dragonfly_cyan.png");
	private static final ResourceLocation greenTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dragonfly/dragonfly_green.png");
	private static final ResourceLocation orangeTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dragonfly/dragonfly_orange.png");
	private static final ResourceLocation redTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dragonfly/dragonfly_red.png");
	private static final ResourceLocation yellowTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/dragonfly/dragonfly_yellow.png");

	public RenderDragonfly(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.2F;
	}

	protected ResourceLocation getEntityTexture(EntityDragonfly dragonfly)
	{
		int type = dragonfly.getDataWatcher().getWatchableObjectByte(20);
		switch(dragonfly.getDataWatcher().getWatchableObjectByte(20))
		{
			case 0:
				return blueTextures;
			case 1:
				return cyanTextures;
			case 2:
				return greenTextures;
			case 3:
				return orangeTextures;
			case 4:
				return redTextures;
			case 5:
				return yellowTextures;
			default:
				return blueTextures;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityDragonfly) entity);
	}
}
