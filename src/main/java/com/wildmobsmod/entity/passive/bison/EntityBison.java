package com.wildmobsmod.entity.passive.bison;

import java.util.List;

import com.wildmobsmod.entity.ai.EntityAIAdultAttackOnCollide;
import com.wildmobsmod.entity.ai.EntityAIBabyPanic;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityBison extends EntityAnimal
{
	//
	// Bison now protect their babies and babies also spawn naturally in packs
	// alongside adults.
	//

	private int angerTimer;

	public EntityBison(World world)
	{
		super(world);
		this.setSize(1.4F, 1.8F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIBabyPanic(this, 1.65F));
		this.tasks.addTask(1, new EntityAIAdultAttackOnCollide(this, 1.7F, false));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.wheat, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.BISON_CONFIG.getMaxPackSize();
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public boolean allowLeashing()
	{
		return this.getGrowingAge() < 0;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(18.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.20000000298023224D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.4D);
	}

	public float getEyeHeight()
	{
		return this.height * 1.0F;
	}

	public void updateAITick()
	{
		if(this.getMoveHelper().isUpdating())
		{
			double d0 = this.getMoveHelper().getSpeed();

			if(d0 >= 1.7D)
			{
				this.setSprinting(true);
				this.angerTimer = 5;
			}
			else
			{
				if(this.angerTimer != 0)
				{
					this.angerTimer--;
					this.setSprinting(true);
				}
				else
				{
					this.setSprinting(false);
				}
			}
		}
		else
		{
			if(this.angerTimer != 0)
			{
				this.angerTimer--;
				this.setSprinting(true);
			}
			else
			{
				this.setSprinting(false);
			}
		}
	}

	public boolean attackEntityAsMob(Entity target)
	{
		return target.attackEntityFrom(DamageSource.causeMobDamage(this), 5.0F);
	}

	protected String getLivingSound()
	{
		return "wildmobsmod:entity.bison.say";
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.bison.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.bison.hurt";
	}
	
	/**
	 * Movement sound
	 */
	@Override
	protected void func_145780_a(int x, int y, int z, Block stepBlock)
	{
		this.playSound("mob.cow.step", 0.15F, 1.0F);
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected Item getDropItem()
	{
		return WildMobsModItems.thickBone;
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(super.interact(player))
		{
			return true;
		}
		else if(!this.worldObj.isRemote)
		{
			if(itemstack != null && itemstack.getItem() == WildMobsModItems.bisonSpawnEgg)
			{
				EntityBison entityageable = this.createChild(this);
				entityageable.setGrowingAge(-24000);
				entityageable.setLocationAndAngles(this.posX, this.posY, this.posZ, 0.0F, 0.0F);
				worldObj.spawnEntityInWorld(entityageable);

				if(itemstack.hasDisplayName())
				{
					entityageable.setCustomNameTag(itemstack.getDisplayName());
				}

				if(!player.capabilities.isCreativeMode)
				{
					--itemstack.stackSize;

					if(itemstack.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}
				}
				return true;
			}
			else if(itemstack != null && itemstack.getItem() == Items.lead && this.getGrowingAge() >= 0)
			{
				this.playSound(this.getHurtSound(), this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
				this.playAngryEffect();
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}

	protected void playAngryEffect()
	{
		for(int i = 0; i < 7; ++i)
		{
			double d0 = this.rand.nextGaussian() * 0.02D;
			double d1 = this.rand.nextGaussian() * 0.02D;
			double d2 = this.rand.nextGaussian() * 0.02D;
			this.worldObj.spawnParticle("smoke", this.posX + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, this.posY + 0.5D + (double) (this.rand.nextFloat() * this.height),
					this.posZ + (double) (this.rand.nextFloat() * this.width * 2.0F) - (double) this.width, d0, d1, d2);
		}
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);
		int k;

		for(k = 0; k < j; ++k)
		{
			this.dropItem(WildMobsModItems.thickBone, 1);
		}

		j = this.rand.nextInt(4) + this.rand.nextInt(1 + looting);

		for(k = 0; k < j; ++k)
		{
			this.dropItem(WildMobsModItems.bisonLeather, 1);
		}

		j = this.rand.nextInt(4) + 1 + this.rand.nextInt(1 + looting);

		for(k = 0; k < j; ++k)
		{
			if(this.isBurning())
			{
				this.dropItem(WildMobsModItems.cookedBisonMeat, 1);
			}
			else
			{
				this.dropItem(WildMobsModItems.rawBisonMeat, 1);
			}
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entity)
	{
		IEntityLivingData data = super.onSpawnWithEgg(entity);

		boolean b = false;

		if(data instanceof EntityBison.GroupData)
		{
			b = true;
		}
		else
		{
			b = false;
			data = new EntityBison.GroupData();
		}

		if(b == true)
		{
			if(this.worldObj.rand.nextInt(3) == 0)
			{
				this.setGrowingAge(-24000 + this.worldObj.rand.nextInt(12000));
			}
		}

		return (IEntityLivingData) data;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			Entity entity = source.getEntity();

			if(entity instanceof EntityPlayer && this.getGrowingAge() < 0)
			{
				List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(6.0D, 6.0D, 6.0D));

				for(int i = 0; i < list.size(); ++i)
				{
					Entity entity1 = (Entity) list.get(i);

					if(entity1 instanceof EntityBison && ((EntityBison) entity1).getAttackTarget() == null && ((EntityBison) entity1).getGrowingAge() >= 0)
					{
						EntityBison entitybison = (EntityBison) entity1;
						entitybison.entityToAttack = entity;
					}
				}
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	public static class GroupData implements IEntityLivingData
	{
		public GroupData()
		{
		}
	}

	public EntityBison createChild(EntityAgeable entity)
	{
		return new EntityBison(this.worldObj);
	}
}
