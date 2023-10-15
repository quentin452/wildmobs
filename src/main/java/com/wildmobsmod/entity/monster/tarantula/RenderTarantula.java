package com.wildmobsmod.entity.monster.tarantula;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderTarantula extends RenderSpider
{
	private static final ResourceLocation tarantulaTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/tarantula/tarantula.png");

	public RenderTarantula()
	{
		this.shadowSize *= 1.2F;
	}

	protected void preRenderCallback(EntityTarantula tarantula, float partialTickTime)
	{
		GL11.glScalef(1.2F, 1.2F, 1.2F);
	}

	protected ResourceLocation getEntityTexture(EntityTarantula tarantula)
	{
		return tarantulaTextures;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityTarantula) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityTarantula) entity);
	}
}
