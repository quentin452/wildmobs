package com.wildmobsmod.entity.ai;

import java.util.Iterator;
import java.util.List;

import com.wildmobsmod.entity.monster.skeletonwolf.EntitySkeletonWolf;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.util.AxisAlignedBB;

public class EntityAICallForSkeletonWolf extends EntityAITarget
{
	public EntityAICallForSkeletonWolf(EntityCreature creature)
	{
		super(creature, false);
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		return true;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		if(this.taskOwner.getAttackTarget() != null)
		{
			AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.taskOwner.posX + 1.0D, this.taskOwner.posY + 1.0D, this.taskOwner.posZ + 1.0D).expand(20.0D, 10.0D, 20.0D);
			List list = this.taskOwner.worldObj.getEntitiesWithinAABB(EntitySkeletonWolf.class, aabb);
			Iterator iterator = list.iterator();
			while(iterator.hasNext())
			{
				EntityCreature entitycreature = (EntityCreature) iterator.next();
				if(this.taskOwner != entitycreature && entitycreature.getAttackTarget() == null && !entitycreature.isOnSameTeam(this.taskOwner.getAITarget()) && ((EntitySkeletonWolf) entitycreature).entityToFollow == this.taskOwner)
				{
					entitycreature.setAttackTarget(this.taskOwner.getAITarget());
				}
			}
		}
		super.startExecuting();
	}
}
