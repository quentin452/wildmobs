package com.wildmobsmod.entity.projectile.lavaspit;

import com.wildmobsmod.main.ClientProxy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLavaSpit extends EntityThrowable
{
	@Override
	protected void entityInit() {}

	public EntityLavaSpit(World world)
	{
		super(world);
		this.setSize(0.5F, 0.5F);
	}

	public EntityLavaSpit(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}

	public EntityLavaSpit(World world, double xPos, double yPos, double zPos)
	{
		super(world, xPos, yPos, zPos);
	}

	public void onUpdate()
	{
		if(this.worldObj.isRemote)
		{
			for(int i = 0; i < 2; ++i)
			{
				ClientProxy.generateEntityMagmaSpitFX(this, this.posX, this.posY, this.posZ, this.worldObj.rand.nextGaussian() * 0.01D, this.worldObj.rand.nextGaussian() * 0.01D, this.worldObj.rand.nextGaussian() * 0.01D);
				this.worldObj.spawnParticle("smoke", this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.0D, 0.0D);
			}
		}
		if(this.isWet())
		{
			this.setDead();
		}

		super.onUpdate();
	}

	protected void onImpact(MovingObjectPosition pos)
	{
		if(!this.worldObj.isRemote)
		{
			if(pos.entityHit != null)
			{
				int power = 0;
				switch(this.worldObj.difficultySetting)
				{
					case NORMAL : power = 2; break;
					case HARD : power = 3; break;
					case EASY : power = 1; break;
					case PEACEFUL : power = 0; break;
				}
				if(power > 0)
				{
					if(!pos.entityHit.isImmuneToFire())
					{
						pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) power);
					}
					if(pos.entityHit instanceof EntityLivingBase)
					{
						pos.entityHit.setFire(power);
					}
				}
			}
			this.setDead();
		}
	}
}
