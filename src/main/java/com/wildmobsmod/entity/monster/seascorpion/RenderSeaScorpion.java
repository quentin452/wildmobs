package com.wildmobsmod.entity.monster.seascorpion;

import org.lwjgl.opengl.GL11;

import com.wildmobsmod.main.WildMobsMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class RenderSeaScorpion extends RenderLiving
{
	private static final ResourceLocation seaScorpionTextures = new ResourceLocation(WildMobsMod.MODID + ":textures/entity/sea_scorpion.png");

	public RenderSeaScorpion(ModelBase model, float shadowSize)
	{
		super(model, shadowSize);
	}

	public void doRender(EntitySeaScorpion seaScorpion, double x, double y, double z, float f1, float f2)
	{
		super.doRender(seaScorpion, x, y, z, f1, f2);
	}

	protected ResourceLocation getEntityTexture(EntitySeaScorpion seaScorpion)
	{
		return seaScorpionTextures;
	}

	protected void preRenderCallback(EntitySeaScorpion seaScorpion, float partialTickTime)
	{
		int i = seaScorpion.getSize() + 1;
		if(seaScorpion.isChild() == false)
		{
			GL11.glScalef(0.25F + (i * 0.25F), 0.25F + (i * 0.25F), 0.25F + (i * 0.25F));
		}
		else
		{
			GL11.glScalef((0.25F + (i * 0.25F)) / 2, (0.25F + (i * 0.25F)) / 2, (0.25F + (i * 0.25F)) / 2);
		}
		this.shadowSize = 0.15F + (i * 0.15F);
	}

	protected void rotateCorpse(EntitySeaScorpion seaScorpion, float f1, float f2, float f3)
	{
		float f4 = seaScorpion.prevSeaScorpionPitch + (seaScorpion.seaScorpionPitch - seaScorpion.prevSeaScorpionPitch) * f3;

		GL11.glRotatef(180.0F - f2, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);

		if(seaScorpion.deathTime > 0)
		{
			float f5 = ((float) seaScorpion.deathTime + f3 - 1.0F) / 20.0F * 1.6F;
			f5 = MathHelper.sqrt_float(f5);

			if(f5 > 1.0F)
			{
				f5 = 1.0F;
			}

			GL11.glRotatef(f5 * this.getDeathMaxRotation(seaScorpion), 0.0F, 0.0F, 1.0F);
		}
		else
		{
			String s = EnumChatFormatting.getTextWithoutFormattingCodes(seaScorpion.getCommandSenderName());

			if(s.equals("Dinnerbone") || s.equals("Grumm"))
			{
				GL11.glTranslatef(0.0F, seaScorpion.height + 0.1F, 0.0F);
				GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
			}
		}
	}

	@Override
	public void doRender(EntityLiving living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntitySeaScorpion) living, x, y, z, f1, f2);
	}

	@Override
	protected void rotateCorpse(EntityLivingBase living, float f1, float f2, float f3)
	{
		this.rotateCorpse((EntitySeaScorpion) living, f1, f2, f3);
	}

	@Override
	public void doRender(EntityLivingBase living, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntitySeaScorpion) living, x, y, z, f1, f2);
	}

	@Override
	public void doRender(Entity entity, double x, double y, double z, float f1, float f2)
	{
		this.doRender((EntitySeaScorpion) entity, x, y, z, f1, f2);
	}
	
	@Override
	protected void preRenderCallback(EntityLivingBase living, float partialTickTime)
	{
		this.preRenderCallback((EntitySeaScorpion) living, partialTickTime);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntitySeaScorpion) entity);
	}
}
