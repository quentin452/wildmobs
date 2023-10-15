package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.passive.goose.EntityGoose;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAILookIdleGoose extends EntityAIBase
{
	/** The entity that is looking idle. */
	private EntityLiving idleEntity;
	/** X offset to look at */
	private double lookX;
	/** Z offset to look at */
	private double lookZ;
	/**
	 * A decrementing tick that stops the entity from being idle once it reaches
	 * 0.
	 */
	private int idleTime;

	public EntityAILookIdleGoose(EntityLiving idleEntity)
	{
		this.idleEntity = idleEntity;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.idleEntity.onGround == false || ((EntityGoose) this.idleEntity).getFlyingState() == 1 || ((EntityGoose) this.idleEntity).getFlyingState() == 2)
		{
			return false;
		}
		else
		{
			return this.idleEntity.getRNG().nextFloat() < 0.02F;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return this.idleTime >= 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		double d0 = (Math.PI * 2D) * this.idleEntity.getRNG().nextDouble();
		this.lookX = Math.cos(d0);
		this.lookZ = Math.sin(d0);
		this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		--this.idleTime;
		this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + (double) this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, 10.0F, (float) this.idleEntity.getVerticalFaceSpeed());
	}
}
