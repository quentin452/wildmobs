package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAISkeletonHurtTarget extends EntityAITarget
{
	EntitySkeletonWolf theEntityTameable;
	EntityLivingBase theTarget;
	private int ownerAttackerTimer;

	public EntityAISkeletonHurtTarget(EntitySkeletonWolf wolf)
	{
		super(wolf, false);
		this.theEntityTameable = wolf;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.theEntityTameable.entityToFollow;
		if(entitylivingbase == null) return false;
		this.theTarget = entitylivingbase.getLastAttacker();
		int i = entitylivingbase.getLastAttackerTime();
		return i != this.ownerAttackerTimer && this.isSuitableTarget(this.theTarget, false);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.theTarget);
		EntityLivingBase entitylivingbase = this.theEntityTameable.entityToFollow;
		if(entitylivingbase != null) this.ownerAttackerTimer = entitylivingbase.getLastAttackerTime();
		super.startExecuting();
	}
}
