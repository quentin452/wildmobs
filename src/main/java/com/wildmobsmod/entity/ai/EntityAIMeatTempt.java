package com.wildmobsmod.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class EntityAIMeatTempt extends EntityAIBase
{
	/** The entity using this AI that is tempted by the player. */
	private EntityCreature temptedEntity;
	private double speedTowardsTarget;
	/** X position of player tempting this mob */
	private double targetX;
	/** Y position of player tempting this mob */
	private double targetY;
	/** Z position of player tempting this mob */
	private double targetZ;
	private double temptingRotPitch;
	private double temptingRotYaw;
	/** The player that is tempting the entity that is using this AI. */
	private EntityPlayer temptingPlayer;
	/**
	 * A counter that is decremented each time the shouldExecute method is
	 * called. The shouldExecute method will always return false if
	 * delayTemptCounter is greater than 0.
	 */
	private int delayTemptCounter;
	/** True if this EntityAITempt task is running */
	private boolean isRunning;
	/**
	 * Whether the entity using this AI will be scared by the tempter's sudden
	 * movement.
	 */
	private boolean scaredByPlayerMovement;
	private boolean avoidWater;

	public EntityAIMeatTempt(EntityCreature creature, double speed, boolean scareable)
	{
		this.temptedEntity = creature;
		this.speedTowardsTarget = speed;
		this.scaredByPlayerMovement = scareable;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.delayTemptCounter > 0)
		{
			--this.delayTemptCounter;
			return false;
		}
		else
		{
			this.temptingPlayer = this.temptedEntity.worldObj.getClosestPlayerToEntity(this.temptedEntity, 10.0D);

			if(this.temptingPlayer == null)
			{
				return false;
			}
			else
			{
				ItemStack itemstack = this.temptingPlayer.getCurrentEquippedItem();
				return itemstack == null ? false : (!(itemstack.getItem() instanceof ItemFood) ? false : ((ItemFood) itemstack.getItem()).isWolfsFavoriteMeat());
			}
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		if(this.scaredByPlayerMovement)
		{
			if(this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 36.0D)
			{
				if(this.temptingPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002D) { return false; }

				if(Math.abs((double) this.temptingPlayer.rotationPitch - this.temptingRotPitch) > 5.0D || Math.abs((double) this.temptingPlayer.rotationYaw - this.temptingRotYaw) > 5.0D) { return false; }
			}
			else
			{
				this.targetX = this.temptingPlayer.posX;
				this.targetY = this.temptingPlayer.posY;
				this.targetZ = this.temptingPlayer.posZ;
			}

			this.temptingRotPitch = (double) this.temptingPlayer.rotationPitch;
			this.temptingRotYaw = (double) this.temptingPlayer.rotationYaw;
		}

		return this.shouldExecute();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.targetX = this.temptingPlayer.posX;
		this.targetY = this.temptingPlayer.posY;
		this.targetZ = this.temptingPlayer.posZ;
		this.isRunning = true;
		this.avoidWater = this.temptedEntity.getNavigator().getAvoidsWater();
		this.temptedEntity.getNavigator().setAvoidsWater(false);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.temptingPlayer = null;
		this.temptedEntity.getNavigator().clearPathEntity();
		this.delayTemptCounter = 100;
		this.isRunning = false;
		this.temptedEntity.getNavigator().setAvoidsWater(this.avoidWater);
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, 30.0F, (float) this.temptedEntity.getVerticalFaceSpeed());

		if(this.temptedEntity.getDistanceSqToEntity(this.temptingPlayer) < 6.25D)
		{
			this.temptedEntity.getNavigator().clearPathEntity();
		}
		else
		{
			this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speedTowardsTarget);
		}
	}

	/**
	 * @see #isRunning
	 */
	public boolean isRunning()
	{
		return this.isRunning;
	}
}
