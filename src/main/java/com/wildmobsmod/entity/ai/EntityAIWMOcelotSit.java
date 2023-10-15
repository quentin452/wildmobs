package com.wildmobsmod.entity.ai;

import com.wildmobsmod.entity.passive.ocelot.EntityWMOcelot;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.World;

public class EntityAIWMOcelotSit extends EntityAIBase
{
	private final EntityWMOcelot theOcelot;
	private final double speed;
	private int boredom;
	private int sitTime;
	private int initiative;
	private int targetX;
	private int targetY;
	private int targetZ;

	public EntityAIWMOcelotSit(EntityWMOcelot ocelot, double speed)
	{
		this.theOcelot = ocelot;
		this.speed = speed;
		this.setMutexBits(5);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		return this.theOcelot.isTamed() && !this.theOcelot.isSitting() && this.theOcelot.getRNG().nextDouble() <= 0.006500000134110451D && this.findTargetCoords();
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return this.boredom <= this.initiative && this.sitTime <= 60 && this.willSitOn(this.theOcelot.worldObj, this.targetX, this.targetY, this.targetZ);
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.theOcelot.getNavigator().tryMoveToXYZ(this.targetX + 0.5D, this.targetY + 1, this.targetZ + 0.5D, this.speed);
		this.boredom = 0;
		this.sitTime = 0;
		this.initiative = this.theOcelot.getRNG().nextInt(this.theOcelot.getRNG().nextInt(1200) + 1200) + 1200;
		this.theOcelot.func_70907_r().setSitting(false);
	}

	/**
	 * Resets the task
	 */
	public void resetTask()
	{
		this.theOcelot.setSitting(false);
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		++this.boredom;
		this.theOcelot.func_70907_r().setSitting(false);
		if(this.theOcelot.getDistanceSq((double) this.targetX, (double) (this.targetY + 1), (double) this.targetZ) > 1.0D)
		{
			this.theOcelot.setSitting(false);
			this.theOcelot.getNavigator().tryMoveToXYZ((double) ((float) this.targetX) + 0.5D, (double) (this.targetY + 1), (double) ((float) this.targetZ) + 0.5D, this.speed);
			++this.sitTime;
		}
		else if(!this.theOcelot.isSitting())
		{
			this.theOcelot.setSitting(true);
		}
		else
		{
			--this.sitTime;
		}
	}

	private boolean findTargetCoords()
	{
		int i = (int) this.theOcelot.posY;
		double d0 = Double.MAX_VALUE;
		for(int j = (int) this.theOcelot.posX - 8; (double) j < this.theOcelot.posX + 8.0D; ++j)
		{
			for(int k = (int) this.theOcelot.posZ - 8; (double) k < this.theOcelot.posZ + 8.0D; ++k)
			{
				if(this.willSitOn(this.theOcelot.worldObj, j, i, k) && this.theOcelot.worldObj.isAirBlock(j, i + 1, k))
				{
					double d1 = this.theOcelot.getDistanceSq((double) j, (double) i, (double) k);
					if(d1 < d0)
					{
						this.targetX = j;
						this.targetY = i;
						this.targetZ = k;
						d0 = d1;
					}
				}
			}
		}
		return d0 < Double.MAX_VALUE;
	}

	private boolean willSitOn(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		int l = world.getBlockMetadata(x, y, z);
		if(block == Blocks.chest)
		{
			TileEntityChest tileentitychest = (TileEntityChest) world.getTileEntity(x, y, z);
			if(tileentitychest.numPlayersUsing < 1) return true;
		}
		else
		{
			if(block == Blocks.lit_furnace) return true;
			if(block == Blocks.bed && !BlockBed.isBlockHeadOfBed(l)) return true;
		}
		return false;
	}
}
