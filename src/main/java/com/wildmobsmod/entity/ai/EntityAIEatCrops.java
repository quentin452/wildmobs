package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.passive.mouse.EntityMouse;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityAIEatCrops extends EntityAIBase
{
	private EntityMouse mouse;
	private World world;

	public EntityAIEatCrops(EntityMouse mouse)
	{
		this.mouse = mouse;
		this.world = mouse.worldObj;
		this.setMutexBits(7);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.mouse.getRNG().nextInt(25) != 0) return false;
		if(this.mouse.hunger <= 0)
		{
			int i = MathHelper.floor_double(this.mouse.posX);
			int j = MathHelper.floor_double(this.mouse.posY);
			int k = MathHelper.floor_double(this.mouse.posZ);
			return this.world.getBlock(i, j - 1, k) == Blocks.farmland;
		}
		return false;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.world.setEntityState(this.mouse, (byte) 10);
		this.mouse.getNavigator().clearPathEntity();
	}

	/**
	 * Resets the task
	 */
	public void resetTask() {}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return false;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		int i = MathHelper.floor_double(this.mouse.posX);
		int j = MathHelper.floor_double(this.mouse.posY);
		int k = MathHelper.floor_double(this.mouse.posZ);
		if(this.world.getBlock(i, j - 1, k) == Blocks.farmland && this.world.getBlock(i, j, k) instanceof IGrowable && this.world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
		{
			this.world.playAuxSFX(2001, i, j, k, Block.getIdFromBlock(Blocks.grass));
			this.world.setBlockToAir(i, j, k);
			this.world.setBlock(i, j - 1, k, Blocks.dirt, 0, 2);
			this.mouse.setMateCounter(((EntityMouse) this.mouse).getMateCounter() + 1);
			this.mouse.hunger = 200;
		}
	}
}
