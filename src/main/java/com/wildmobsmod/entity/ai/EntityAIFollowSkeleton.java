package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIFollowSkeleton extends EntityAIBase
{
	/** The child that is following its parent. */
	EntitySkeletonWolf theWolf;
	Entity targetMob;
	double speedTowardsTarget;
	private int cooldown;

	public EntityAIFollowSkeleton(EntitySkeletonWolf wolf, double speed)
	{
		this.theWolf = wolf;
		this.speedTowardsTarget = speed;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		Entity skeleton = null;
		double d0 = Double.MAX_VALUE;

		if(this.theWolf.entityToFollow != null)
		{
			double d1 = this.theWolf.getDistanceSqToEntity(theWolf.entityToFollow);

			if(d1 <= d0)
			{
				d0 = d1;
				skeleton = theWolf.entityToFollow;
			}

			if(skeleton == null)
			{
				return false;
			}
			else if(d0 < 25.0D)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		if(theWolf.entityToFollow == null)
		{
			return false;
		}
		else
		{
			double d0 = this.theWolf.getDistanceSqToEntity(theWolf.entityToFollow);
			if(d0 <= 256.0D)
			{
				this.theWolf.entityToFollow = null;
			}
			return d0 >= 25.0D && d0 <= 256.0D;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.cooldown = 0;
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.targetMob = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		if(--this.cooldown <= 0)
		{
			this.cooldown = 10;
			if(this.theWolf.entityToFollow != null)
			{
				this.theWolf.getNavigator().tryMoveToEntityLiving(theWolf.entityToFollow, this.speedTowardsTarget);
			}
		}
	}
}
