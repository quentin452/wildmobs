package com.wildmobsmod.entity.ai;

import java.util.List;

import com.wildmobsmod.entity.passive.deer.EntityDeer;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.Vec3;

public class EntityAIAvoidEntityWild extends EntityAIBase
{
	public final IEntitySelector selector = new IEntitySelector()
	{
		/**
		 * Return whether the specified entity is applicable to this filter.
		 */
		public boolean isEntityApplicable(Entity e)
		{
			return e.isEntityAlive() && EntityAIAvoidEntityWild.this.theEntity.getEntitySenses().canSee(e);
		}
	};
	/** The entity we are attached to */
	private EntityCreature theEntity;
	private double farSpeed;
	private double nearSpeed;
	private Entity closestLivingEntity;
	private float distanceFromEntity;
	/** The PathEntity of our entity */
	private PathEntity entityPathEntity;
	/** The PathNavigate of our entity */
	private PathNavigate entityPathNavigate;
	/** The class of the entity we should avoid */
	private Class targetEntityClass;

	public EntityAIAvoidEntityWild(EntityCreature creature, Class targetClass, float distance, double farSpeed, double nearSpeed)
	{
		this.theEntity = creature;
		this.targetEntityClass = targetClass;
		this.distanceFromEntity = distance;
		this.farSpeed = farSpeed;
		this.nearSpeed = nearSpeed;
		this.entityPathNavigate = creature.getNavigator();
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(((EntityDeer) this.theEntity).getTamed() == false)
		{
			if(this.targetEntityClass == EntityPlayer.class)
			{
				if(this.theEntity instanceof EntityTameable && ((EntityTameable) this.theEntity).isTamed()) return false;
				this.closestLivingEntity = this.theEntity.worldObj.getClosestPlayerToEntity(this.theEntity, (double) this.distanceFromEntity);
				if(this.closestLivingEntity == null) return false;
			}
			else
			{
				List list = this.theEntity.worldObj.selectEntitiesWithinAABB(this.targetEntityClass, this.theEntity.boundingBox.expand((double) this.distanceFromEntity, 3.0D, (double) this.distanceFromEntity), this.selector);
				if(list.isEmpty()) return false;
				this.closestLivingEntity = (Entity) list.get(0);
			}
			Vec3 vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 16, 7, Vec3.createVectorHelper(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
			if(vec3 == null || (this.closestLivingEntity.getDistanceSq(vec3.xCoord, vec3.yCoord, vec3.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity))) return false;
			this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(vec3.xCoord, vec3.yCoord, vec3.zCoord);
			return this.entityPathEntity == null ? false : this.entityPathEntity.isDestinationSame(vec3);
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.entityPathNavigate.noPath() && ((EntityDeer) this.theEntity).getTamed();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.closestLivingEntity = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.theEntity.getNavigator().setSpeed(this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0D ? this.nearSpeed : this.farSpeed);
	}
}
