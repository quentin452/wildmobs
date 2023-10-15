package com.wildmobsmod.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntitySpellFX extends EntityFX
{
	public EntitySpellFX(World world, double xPos, double yPos, double zPos, double xMotion, double yMotion, double zMotion, float red, float green, float blue)
	{
		super(world, xPos, yPos, zPos, xMotion, yMotion, zMotion);
		this.motionX = this.motionX * 0.01D + xMotion;
		this.motionY = this.motionY * 0.01D + yMotion;
		this.motionZ = this.motionZ * 0.01D + zMotion;
		double d6 = xPos + (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
		d6 = yPos + (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
		d6 = zPos + (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
		this.particleRed = red;
		this.particleGreen = green;
		this.particleBlue = blue;
		this.particleMaxAge = 15;
		this.noClip = true;
	}

	@Override
	public void renderParticle(Tessellator tesselator, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		float f7 = ((float) this.particleAge + f1) / (float) this.particleMaxAge;
		this.particleScale = 2.5F;
		super.renderParticle(tesselator, f1, f2, f3, f4, f5, f6);
	}

	@Override
	public int getBrightnessForRender(float partialTickTime)
	{
		float f1 = ((float) this.particleAge + partialTickTime) / (float) this.particleMaxAge;

		if(f1 < 0.0F)
		{
			f1 = 0.0F;
		}

		if(f1 > 1.0F)
		{
			f1 = 1.0F;
		}

		int i = super.getBrightnessForRender(partialTickTime);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (f1 * 15.0F * 16.0F);

		if(j > 240)
		{
			j = 240;
		}

		return j | k << 16;
	}

	@Override
	public float getBrightness(float partialTickTime)
	{
		float f1 = ((float) this.particleAge + partialTickTime) / (float) this.particleMaxAge;

		if(f1 < 0.0F)
		{
			f1 = 0.0F;
		}

		if(f1 > 1.0F)
		{
			f1 = 1.0F;
		}

		float f2 = super.getBrightness(partialTickTime);
		return f2 * f1 + (1.0F - f1);
	}

	@Override
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if(this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.8D;
		this.motionY *= 0.8D;
		this.motionZ *= 0.8D;
	}
}
