package com.wildmobsmod.entity.ai;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIAdultAttackOnCollide extends EntityAIBase
{
	World worldObj;
	EntityAgeable attacker;
	/**
	 * An amount of decrementing ticks that allows the entity to attack once the
	 * tick reaches 0.
	 */
	int attackTick;
	/** The speed with which the mob will approach the target */
	double speedTowardsTarget;
	/**
	 * When true, the mob will continue chasing its target, even if it can't
	 * find a path to them right now.
	 */
	boolean longMemory;
	/** The PathEntity of our entity. */
	PathEntity entityPathEntity;
	Class classTarget;
	private int initiative;
	private double targetX;
	private double targetY;
	private double targetZ;

	private int failedPathFindingPenalty;

	public EntityAIAdultAttackOnCollide(EntityAgeable attacker, Class targetClass, double runningSpeed, boolean rememberLong)
	{
		this(attacker, runningSpeed, rememberLong);
		this.classTarget = targetClass;
	}

	public EntityAIAdultAttackOnCollide(EntityAgeable attacker, double runningSpeed, boolean rememberLong)
	{
		this.attacker = attacker;
		this.worldObj = attacker.worldObj;
		this.speedTowardsTarget = runningSpeed;
		this.longMemory = rememberLong;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();

		if(entitylivingbase == null)
		{
			return false;
		}
		else if(!entitylivingbase.isEntityAlive())
		{
			return false;
		}
		else if(this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass()))
		{
			return false;
		}
		else if(this.attacker.getGrowingAge() >= 0)
		{
			if(--this.initiative <= 0)
			{
				this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
				this.initiative = 4 + this.attacker.getRNG().nextInt(7);
				return this.entityPathEntity != null;
			}
			else
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
		return entitylivingbase == null ? false
				: (!entitylivingbase.isEntityAlive() ? false
						: (!this.longMemory ? !this.attacker.getNavigator().noPath() : this.attacker.isWithinHomeDistance(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posY), MathHelper.floor_double(entitylivingbase.posZ))));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.attacker.getNavigator().setPath(this.entityPathEntity, this.speedTowardsTarget);
		this.initiative = 0;
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.attacker.getNavigator().clearPathEntity();
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
		this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
		double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.boundingBox.minY, entitylivingbase.posZ);
		double d1 = (double) (this.attacker.width * 1.5F * this.attacker.width * 1.5F + entitylivingbase.width);
		--this.initiative;

		if((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.initiative <= 0
				&& (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F))
		{
			this.targetX = entitylivingbase.posX;
			this.targetY = entitylivingbase.boundingBox.minY;
			this.targetZ = entitylivingbase.posZ;
			this.initiative = failedPathFindingPenalty + 4 + this.attacker.getRNG().nextInt(7);

			if(this.attacker.getNavigator().getPath() != null)
			{
				PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
				if(finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.xCoord, finalPathPoint.yCoord, finalPathPoint.zCoord) < 1)
				{
					failedPathFindingPenalty = 0;
				}
				else
				{
					failedPathFindingPenalty += 10;
				}
			}
			else
			{
				failedPathFindingPenalty += 10;
			}

			if(d0 > 1024.0D)
			{
				this.initiative += 10;
			}
			else if(d0 > 256.0D)
			{
				this.initiative += 5;
			}

			if(!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget))
			{
				this.initiative += 15;
			}
		}

		this.attackTick = Math.max(this.attackTick - 1, 0);

		if(d0 <= d1 && this.attackTick <= 20)
		{
			this.attackTick = 20;

			if(this.attacker.getHeldItem() != null)
			{
				this.attacker.swingItem();
			}

			this.attacker.attackEntityAsMob(entitylivingbase);
		}
	}
}
