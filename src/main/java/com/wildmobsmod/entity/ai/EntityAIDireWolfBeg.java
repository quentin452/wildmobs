package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.passive.direwolf.EntityDireWolf;
import com.wildmobsmod.items.WildMobsModItems;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityAIDireWolfBeg extends EntityAIBase
{
	private EntityDireWolf theWolf;
	private EntityPlayer thePlayer;
	private World worldObject;
	private float minPlayerDistance;
	private int initiative;

	public EntityAIDireWolfBeg(EntityDireWolf direwolf, float distance)
	{
		this.theWolf = direwolf;
		this.worldObject = direwolf.worldObj;
		this.minPlayerDistance = distance;
		this.setMutexBits(2);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		this.thePlayer = this.worldObject.getClosestPlayerToEntity(this.theWolf, (double) this.minPlayerDistance);
		return this.thePlayer == null ? false : this.hasPlayerGotBoneInHand(this.thePlayer);
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.thePlayer.isEntityAlive() ? false : (this.theWolf.getDistanceSqToEntity(this.thePlayer) > (double) (this.minPlayerDistance * this.minPlayerDistance) ? false : this.initiative > 0 && this.hasPlayerGotBoneInHand(this.thePlayer));
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.theWolf.setBegging(true);
		this.initiative = 40 + this.theWolf.getRNG().nextInt(40);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.theWolf.setBegging(false);
		this.thePlayer = null;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		this.theWolf.getLookHelper().setLookPosition(this.thePlayer.posX, this.thePlayer.posY + (double) this.thePlayer.getEyeHeight(), this.thePlayer.posZ, 10.0F, (float) this.theWolf.getVerticalFaceSpeed());
		--this.initiative;
	}

	/**
	 * Gets if the Player has the Bone in the hand.
	 */
	private boolean hasPlayerGotBoneInHand(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		return itemstack == null ? false : (!this.theWolf.isTamed() && itemstack.getItem() == WildMobsModItems.thickBone ? true : this.theWolf.isBreedingItem(itemstack));
	}
}
