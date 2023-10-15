package com.wildmobsmod.entity.passive.fox;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderFox extends RenderLiving
{

	private static final ResourceLocation foxTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/fox/fox_red.png");
	private static final ResourceLocation arcticTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/fox/fox_arctic.png");
	private static final ResourceLocation desertTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/fox/fox_desert.png");
	private static final ResourceLocation silverTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/fox/fox_silver.png");
	private static final ResourceLocation darkTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/fox/fox_dark.png");

	public RenderFox(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.4F;
	}

	protected ResourceLocation getEntityTexture(EntityFox fox)
	{
		switch(fox.getDataWatcher().getWatchableObjectByte(20))
		{
			case 0:
				return foxTextures;
			case 1:
				return arcticTextures;
			case 2:
				return desertTextures;
			case 3:
				return silverTextures;
			case 4:
				return darkTextures;
			default:
				return foxTextures;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityFox) entity);
	}
}
