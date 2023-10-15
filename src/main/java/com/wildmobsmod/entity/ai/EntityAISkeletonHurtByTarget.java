package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAISkeletonHurtByTarget extends EntityAITarget
{
	EntitySkeletonWolf theDefendingTameable;
	EntityLivingBase theOwnerAttacker;
	private int ownerRevengeTimer;

	public EntityAISkeletonHurtByTarget(EntitySkeletonWolf wolf)
	{
		super(wolf, false);
		this.theDefendingTameable = wolf;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.theDefendingTameable.entityToFollow;
		if(entitylivingbase == null) return false;
		this.theOwnerAttacker = entitylivingbase.getAITarget();
		int i = entitylivingbase.func_142015_aE();
		return i != this.ownerRevengeTimer && this.isSuitableTarget(this.theOwnerAttacker, false);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.taskOwner.setAttackTarget(this.theOwnerAttacker);
		EntityLivingBase entitylivingbase = this.theDefendingTameable.entityToFollow;
		if(entitylivingbase != null) this.ownerRevengeTimer = entitylivingbase.func_142015_aE();
		super.startExecuting();
	}
}
