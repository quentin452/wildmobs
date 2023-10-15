package com.wildmobsmod.entity.projectile.seascorpionegg;

import com.wildmobsmod.entity.monster.seascorpion.EntitySeaScorpion;
import com.wildmobsmod.items.WildMobsModItems;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySeaScorpionEgg extends EntityThrowable
{
	public EntitySeaScorpionEgg(World world)
	{
		super(world);
	}

	public EntitySeaScorpionEgg(World world, EntityLivingBase thrower)
	{
		super(world, thrower);
	}

	public EntitySeaScorpionEgg(World world, double xPos, double yPos, double zPos)
	{
		super(world, xPos, yPos, zPos);
	}

	protected void onImpact(MovingObjectPosition pos)
	{
		if(pos.entityHit != null)
		{
			pos.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0.0F);
		}

		if(!this.worldObj.isRemote)
		{
			EntitySeaScorpion entityseascorpion = new EntitySeaScorpion(this.worldObj);
			entityseascorpion.setGrowingAge(-24000);
			entityseascorpion.setIsWild(false);
			entityseascorpion.setSize(2);
			entityseascorpion.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
			this.worldObj.spawnEntityInWorld(entityseascorpion);
		}

		String s = "iconcrack_" + Item.getIdFromItem(WildMobsModItems.seaScorpionEgg);

		for(int j = 0; j < 8; ++j)
		{
			this.worldObj.spawnParticle(s, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
		}

		if(!this.worldObj.isRemote)
		{
			this.setDead();
		}
	}
}
