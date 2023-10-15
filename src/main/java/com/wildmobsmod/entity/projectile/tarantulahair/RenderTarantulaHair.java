package com.wildmobsmod.entity.projectile.tarantulahair;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderTarantulaHair extends RenderArrow
{
	private static final ResourceLocation tarantulaHairTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/tarantula/tarantula_hair.png");

	protected ResourceLocation getEntityTexture(EntityTarantulaHair hair)
	{
		return tarantulaHairTextures;
	}

	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityTarantulaHair) entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float f1, float f2)
	{
		super.doRender((EntityTarantulaHair) entity, x, y, z, f1, f2);
	}
}
