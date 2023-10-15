package com.wildmobsmod.particles;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;

public class EntityLavaSpitFX extends EntityFX
{
	public EntityLavaSpitFX(World world, double xPos, double yPos, double zPos, double xMotion, double yMotion, double zMotion)
	{
		super(world, xPos, yPos, zPos, xMotion, yMotion, zMotion);
		this.motionX = this.motionX * 0.009999999776482582D + xMotion;
		this.motionY = this.motionY * 0.009999999776482582D + yMotion;
		this.motionZ = this.motionZ * 0.009999999776482582D + zMotion;
		double d6 = xPos + (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
		d6 = yPos + (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
		d6 = zPos + (double) ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
		this.particleScale = 2.0F;
		this.particleMaxAge = 15;
		this.noClip = true;
	}

	@Override
	public void renderParticle(Tessellator tesselator, float f1, float f2, float f3, float f4, float f5, float f6)
	{
		float f7 = ((float) this.particleAge + f1) / (float) this.particleMaxAge;
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
		this.particleScale = (float) (this.particleScale - 0.05);

		if(this.particleAge++ >= this.particleMaxAge)
		{
			this.setDead();
		}

		this.setParticleTextureIndex(49);

		this.moveEntity(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.8D;
		this.motionY *= 0.8D;
		this.motionZ *= 0.8D;
	}
}
