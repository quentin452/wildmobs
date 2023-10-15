package com.wildmobsmod.entity.monster.zomgus;

import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityZomgus extends EntityZombie
{
	public EntityZomgus(World world)
	{
		super(world);
		this.getNavigator().setAvoidsWater(true);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.ZOMGUS_CONFIG.getMaxPackSize();
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
	}

	public boolean attackEntityAsMob(Entity target)
	{
		if(super.attackEntityAsMob(target))
		{
			if(target instanceof EntityLivingBase)
			{
				byte b0 = 0;

				if(this.worldObj.difficultySetting == EnumDifficulty.EASY)
				{
					b0 = 5;
				}
				else if(this.worldObj.difficultySetting == EnumDifficulty.NORMAL)
				{
					b0 = 8;
				}
				else if(this.worldObj.difficultySetting == EnumDifficulty.HARD)
				{
					b0 = 10;
				}

				if(b0 > 0)
				{
					((EntityLivingBase) target).addPotionEffect(new PotionEffect(Potion.poison.id, b0 * 20, 0));
				}
			}

			return true;
		}
		else
		{
			return false;
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(this.isWet())
		{
			this.attackEntityFrom(DamageSource.drown, 1.0F);
		}
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);

		for(int k = 0; k < j; ++k)
		{
			this.dropItem(WildMobsModItems.infectedFlesh, 1);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		this.setVillager(false);
		return data;
	}
}
