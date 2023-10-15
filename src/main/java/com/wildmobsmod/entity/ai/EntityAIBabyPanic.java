package com.wildmobsmod.entity.ai;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;

public class EntityAIBabyPanic extends EntityAIBase
{
	private EntityAgeable creature;
	private double speed;
	private double randPosX;
	private double randPosY;
	private double randPosZ;

	public EntityAIBabyPanic(EntityAgeable creature, double speed)
	{
		this.creature = creature;
		this.speed = speed;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.creature.getGrowingAge() >= 0 || (this.creature.getAITarget() == null && !this.creature.isBurning())) return false;
		Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.creature, 5, 4);
		if(vec3 == null) return false;
		this.randPosX = vec3.xCoord;
		this.randPosY = vec3.yCoord;
		this.randPosZ = vec3.zCoord;
		return true;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.creature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(this.creature.posX, this.creature.posY, this.creature.posZ, this.creature.posX + 1.0D, this.creature.posY + 1.0D, this.creature.posZ + 1.0D).expand(8.0D, 8.0D, 8.0D);
		List list = this.creature.worldObj.getEntitiesWithinAABB(this.creature.getClass(), aabb);
		Iterator iterator = list.iterator();
		while(iterator.hasNext())
		{
			EntityCreature entitycreature = (EntityCreature) iterator.next();
			if(this.creature != entitycreature && entitycreature.getAttackTarget() == null && ((EntityAnimal) entitycreature).getAge() >= 0 && !entitycreature.isOnSameTeam(this.creature.getAITarget()) && this.creature.worldObj.rand.nextInt(2) == 0)
			{
				entitycreature.setAttackTarget(this.creature.getAITarget());
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.creature.getNavigator().noPath();
	}
}
