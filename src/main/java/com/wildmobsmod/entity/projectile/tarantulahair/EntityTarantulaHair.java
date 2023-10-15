package com.wildmobsmod.entity.projectile.tarantulahair;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityTarantulaHair extends EntityArrow
{
	public EntityTarantulaHair(World world)
	{
		super(world);
	}

	public EntityTarantulaHair(World world, double xPos, double yPos, double zPos)
	{
		super(world, xPos, yPos, zPos);
	}

	public EntityTarantulaHair(World world, EntityLivingBase thrower, EntityLivingBase target, float f1, float f2)
	{
		super(world, thrower, target, f1, f2);
	}

	public EntityTarantulaHair(World world, EntityLivingBase thrower, float f)
	{
		super(world, thrower, f);
	}

	@Override
	public void onCollideWithPlayer(EntityPlayer player) {}
}
