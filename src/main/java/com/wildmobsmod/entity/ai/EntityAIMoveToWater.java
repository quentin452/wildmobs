package com.wildmobsmod.entity.ai;

import java.util.Random;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIMoveToWater extends EntityAIBase
{
	private EntityCreature theCreature;
	private double shelterX;
	private double shelterY;
	private double shelterZ;
	private double movementSpeed;
	private World theWorld;

	public EntityAIMoveToWater(EntityCreature creature, double speed)
	{
		this.theCreature = creature;
		this.movementSpeed = speed;
		this.theWorld = creature.worldObj;
		this.setMutexBits(1);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if(this.theCreature.onGround)
		{
			Vec3 vec3 = this.findPossibleShelter();
			if(vec3 == null) return false;
			this.shelterX = vec3.xCoord;
			this.shelterY = vec3.yCoord;
			this.shelterZ = vec3.zCoord;
			return this.theCreature.getRNG().nextFloat() < 0.02F;
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean continueExecuting()
	{
		return !this.theCreature.getNavigator().noPath();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		this.theCreature.getNavigator().tryMoveToXYZ(this.shelterX, this.shelterY, this.shelterZ, this.movementSpeed);
	}

	private Vec3 findPossibleShelter()
	{
		Random random = this.theCreature.getRNG();
		for(int i = 0; i < 20; ++i)
		{
			int j = MathHelper.floor_double(this.theCreature.posX + (double) random.nextInt(40) - (double) random.nextInt(40));
			int k = MathHelper.floor_double(this.theCreature.boundingBox.minY + (double) random.nextInt(12) - 3.0D);
			int l = MathHelper.floor_double(this.theCreature.posZ + (double) random.nextInt(40) - (double) random.nextInt(40));
			if(this.theWorld.getBlock(j, k - 1, l) == Blocks.water) return Vec3.createVectorHelper((double) j, (double) k, (double) l);
		}
		return null;
	}
}
