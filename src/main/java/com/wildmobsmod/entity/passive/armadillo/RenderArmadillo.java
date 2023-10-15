package com.wildmobsmod.entity.passive.armadillo;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderArmadillo extends RenderLiving
{
	private static final ResourceLocation armadilloTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/armadillo/armadillo.png");
	private static final ResourceLocation armadilloBallTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/armadillo/armadillo_ball.png");

	public RenderArmadillo(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.3F;
	}

	protected ResourceLocation getEntityTexture(EntityArmadillo armadillo)
	{
		return armadillo.getBallTimer() <= 0 ? armadilloTextures : armadilloBallTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityArmadillo) entity);
	}
}
