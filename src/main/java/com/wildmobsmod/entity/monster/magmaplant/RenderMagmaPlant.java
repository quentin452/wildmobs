package com.wildmobsmod.entity.monster.magmaplant;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderMagmaPlant extends RenderLiving
{
	private static final ResourceLocation stage0Textures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/magmaplant/magma_plant_stage_0.png");
	private static final ResourceLocation stage1Textures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/magmaplant/magma_plant_stage_1.png");
	private static final ResourceLocation stage2Textures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/magmaplant/magma_plant_stage_2.png");
	private static final ResourceLocation stage3Textures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/magmaplant/magma_plant_stage_3.png");

	public RenderMagmaPlant(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
	}

	protected void preRenderCallback(EntityMagmaPlant magmaPlant, float partialTickTime)
	{
		GL11.glScalef(0.8F, 0.8F, 0.8F);
	}

	protected ResourceLocation getEntityTexture(EntityMagmaPlant magmaPlant)
	{
		int type = magmaPlant.getDataWatcher().getWatchableObjectByte(19);
		switch(magmaPlant.getDataWatcher().getWatchableObjectByte(19))
		{
			case 0:
				return stage0Textures;
			case 1:
				return stage1Textures;
			case 2:
				return stage2Textures;
			case 3:
				return stage3Textures;
			default:
				return stage3Textures;
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntityMagmaPlant) living, partialTickTime);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityMagmaPlant) entity);
	}
}
