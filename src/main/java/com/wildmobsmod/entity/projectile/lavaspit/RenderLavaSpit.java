package com.wildmobsmod.entity.projectile.lavaspit;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderLavaSpit extends Render
{
	public RenderLavaSpit() {}

	public RenderLavaSpit(Item item)
	{
		this();
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2) {}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}
