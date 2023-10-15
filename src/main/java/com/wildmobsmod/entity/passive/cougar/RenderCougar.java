package com.wildmobsmod.entity.passive.cougar;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderCougar extends RenderLiving
{
	private static final ResourceLocation texture = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/cougar/cougar.png");
	private static final ResourceLocation angryTexture = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/cougar/cougar_angry.png");

	public RenderCougar(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
		this.shadowSize = 0.6F;
	}

	protected ResourceLocation getEntityTexture(EntityCougar cougar)
	{
//		return cougar.getAttackTarget() != null ? angryTexture : texture;	// Angry textures disabled for now
		return texture;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityCougar) entity);
	}
}
