package com.wildmobsmod.entity.passive.ocelot;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderWMOcelot extends RenderLiving
{
	private static final ResourceLocation blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
	private static final ResourceLocation ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
	private static final ResourceLocation redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
	private static final ResourceLocation siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
	private static final ResourceLocation tabbyOcelotTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/cat/tabby.png");
	private static final ResourceLocation grayOcelotTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/cat/gray.png");
	private static final ResourceLocation whiteOcelotTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/cat/white.png");

	public RenderWMOcelot(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
	}

	public void doRender(EntityWMOcelot ocelot, double x, double y, double z, float f1, float f2)
	{
		super.doRender(ocelot, x, y, z, f1, f2);
	}

	protected ResourceLocation getEntityTexture(EntityWMOcelot ocelot)
	{
		switch(ocelot.getSkin())
		{
			case 0:
			default:
				return ocelotTextures;
			case 1:
				return tabbyOcelotTextures;
			case 2:
				return blackOcelotTextures;
			case 3:
				return redOcelotTextures;
			case 4:
				return siameseOcelotTextures;
			case 5:
				return grayOcelotTextures;
			case 6:
				return whiteOcelotTextures;
		}
	}

	protected void preRenderCallback(EntityWMOcelot ocelot, float partialTickTime)
	{
		super.preRenderCallback(ocelot, partialTickTime);

		if(ocelot.isTamed())
		{
			GL11.glScalef(0.8F, 0.8F, 0.8F);
		}
	}

	@Override
	public void doRender(EntityLiving living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityWMOcelot) living, x, y, z, f1, f2);
	}

	@Override
	protected void preRenderCallback(EntityLivingBase entityLiving, float partialTickTime)
	{
		this.preRenderCallback((EntityWMOcelot) entityLiving, partialTickTime);
	}

	@Override
	public void doRender(EntityLivingBase living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityWMOcelot) living, x, y, z, f1, f2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityWMOcelot) entity);
	}

	public void doRender(Entity entity, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntityWMOcelot) entity, x, y, z, f1, f2);
	}
}
