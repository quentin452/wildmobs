package com.wildmobsmod.entity.monster.tarantula;

import com.wildmobsmod.entity.projectile.tarantulahair.EntityTarantulaHair;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityTarantula extends EntitySpider
{
	//
	// Tarantulas have experienced some big changes, instead of poisoning the
	// player, they randomly shoot tarantula hairs at the player. The hair
	// rendering doesn't work properly yet.
	//

	int hairAttackTimer;

	public EntityTarantula(World world)
	{
		super(world);
		this.setSize(1.68F, 1.08F);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.TARANTULA_CONFIG.getMaxPackSize();
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(this.entityToAttack != null)
		{
			double d0 = this.getDistanceSq(this.entityToAttack.posX, this.entityToAttack.boundingBox.minY, this.entityToAttack.posZ);
			if(this.rand.nextInt(40) == 0 && d0 < 225.0D && d0 > 16.0D && this.getEntitySenses().canSee(this.entityToAttack) && this.hairAttackTimer <= 0)
			{
				this.hairAttackTimer = 140;
			}

			if(this.hairAttackTimer > 0 && this.getEntitySenses().canSee(this.entityToAttack))
			{
				if(this.hairAttackTimer == 130 || this.hairAttackTimer == 127 || this.hairAttackTimer == 124 || this.hairAttackTimer == 121 || this.hairAttackTimer == 118 || this.hairAttackTimer == 115)
				{
					float f;

					f = MathHelper.sqrt_double(d0) / 15.0F;
					float f1 = f;

					if(f < 0.1F)
					{
						f1 = 0.1F;
					}

					if(f1 > 1.0F)
					{
						f1 = 1.0F;
					}

					this.attackEntityWithRangedAttack((EntityLivingBase) this.entityToAttack, f);
				}
			}
		}

		if(this.hairAttackTimer > 0)
		{
			this.hairAttackTimer--;
		}

		if(this.hairAttackTimer > 100)
		{
			if(this.entityToAttack != null && this.getEntitySenses().canSee(this.entityToAttack))
			{
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
			}
			else
			{
				this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
			}
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6D);
		}
	}

	public void attackEntityWithRangedAttack(EntityLivingBase target, float damage)
	{
		EntityTarantulaHair entitytarantulahair = new EntityTarantulaHair(this.worldObj, this, target, 1.2F, (float) (14 - this.worldObj.difficultySetting.getDifficultyId() * 2));
		this.playSound("random.bow", 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(entitytarantulahair);
	}
}
