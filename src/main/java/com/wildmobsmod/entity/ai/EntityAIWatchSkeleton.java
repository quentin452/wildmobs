package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIWatchSkeleton extends EntityAIBase
{
	private EntitySkeletonWolf theWatcher;
	/** The closest entity which is being watched by this one. */
	protected Entity closestEntity;
	/** This is the Maximum distance that the AI will look for the Entity */
	private float maxDistanceForPlayer;
	private int lookTime;
	private float chance;
	private Class watchedClass;

	public EntityAIWatchSkeleton(EntitySkeletonWolf wolf, float range)
	{
		this.theWatcher = wolf;
		this.maxDistanceForPlayer = range;
		this.chance = 0.02F;
		this.setMutexBits(2);
	}

	public EntityAIWatchSkeleton(EntitySkeletonWolf wolf, float range, float chance)
	{
		this.theWatcher = wolf;
		this.maxDistanceForPlayer = range;
		this.chance = chance;
		this.setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.theWatcher.getRNG().nextFloat() >= this.chance) return false;
		if(this.theWatcher.getAttackTarget() != null)
		{
			this.closestEntity = this.theWatcher.getAttackTarget();
		}
		else if(this.theWatcher.entityToFollow != null)
		{
			this.closestEntity = this.theWatcher.entityToFollow;
		}
		return this.theWatcher.entityToFollow != null;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.closestEntity.isEntityAlive() ? false : (this.theWatcher.getDistanceSqToEntity(this.closestEntity) > (double) (this.maxDistanceForPlayer * this.maxDistanceForPlayer) ? false : this.lookTime > 0);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.closestEntity = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + (double) this.closestEntity.getEyeHeight(), this.closestEntity.posZ, 10.0F, (float) this.theWatcher.getVerticalFaceSpeed());
		--this.lookTime;
	}
}
