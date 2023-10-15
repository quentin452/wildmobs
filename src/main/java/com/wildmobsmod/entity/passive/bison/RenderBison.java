package com.wildmobsmod.entity.passive.bison;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderBison extends RenderLiving
{

	private static final ResourceLocation bisonTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/bison.png");

	public RenderBison(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.9F;
	}

	protected ResourceLocation getEntityTexture(EntityBison bison)
	{
		return bisonTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityBison) entity);
	}
}
