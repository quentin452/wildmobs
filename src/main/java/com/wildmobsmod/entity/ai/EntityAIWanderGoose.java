package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.passive.goose.EntityGoose;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIWanderGoose extends EntityAIBase
{
	private EntityCreature entity;
	private double xPosition;
	private double yPosition;
	private double zPosition;
	private double speed;

	public EntityAIWanderGoose(EntityCreature creature, double speed)
	{
		this.entity = creature;
		this.speed = speed;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(!this.entity.onGround || this.entity.getAge() >= 100 || this.entity.getRNG().nextInt(120) != 0 || ((EntityGoose) this.entity).getFlyingState() == 1 || ((EntityGoose) this.entity).getFlyingState() == 2) return false;
		Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.entity, 10, 20);
		if(vec3 == null) return false;
		this.xPosition = vec3.xCoord;
		this.yPosition = vec3.yCoord;
		this.zPosition = vec3.zCoord;
		return true;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.entity.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.entity.getNavigator().tryMoveToXYZ(this.xPosition, this.yPosition, this.zPosition, this.speed);
	}
}
