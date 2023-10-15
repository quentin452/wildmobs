package com.wildmobsmod.entity.passive.armadillo;

import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EntityArmadillo extends EntityAnimal
{
	public EntityArmadillo(World world)
	{
		super(world);
		this.setSize(0.4F, 0.5F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIMate(this, 1.0D));
		boolean flag = false;
		if(WildMobsMod.BUTTERFLY_CONFIG.isEnabled())
		{
			this.tasks.addTask(2, new EntityAITempt(this, 1.25D, WildMobsModItems.butterfly, false));
			flag = true;
		}
		if(WildMobsMod.DRAGONFLY_CONFIG.isEnabled())
		{
			this.tasks.addTask(2, new EntityAITempt(this, 1.25D, WildMobsModItems.dragonfly, false));
			flag = true;
		}
		if(!flag)
		{
			this.tasks.addTask(2, new EntityAITempt(this, 1.25D, Items.wheat_seeds, false));
			this.tasks.addTask(2, new EntityAITempt(this, 1.25D, Items.pumpkin_seeds, false));
			this.tasks.addTask(2, new EntityAITempt(this, 1.25D, Items.melon_seeds, false));
		}
		this.tasks.addTask(3, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.ARMADILLO_CONFIG.getMaxPackSize();
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("BallTimer", this.getBallTimer());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setBallTimer(nbt.getInteger("BallTimer"));
	}

	public int getBallTimer()
	{
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setBallTimer(int time)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) time));
	}
	
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			if(source instanceof EntityDamageSource && !source.isProjectile())
			{
				this.setBallTimer(100);
				return false;
			}
			else if(source.isProjectile() && this.getBallTimer() > 0)
			{
				return false;
			}
			else
			{
				return super.attackEntityFrom(source, amount);
			}
		}
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.armadillo.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.armadillo.hurt";
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		if(!WildMobsMod.BUTTERFLY_CONFIG.isEnabled() && !WildMobsMod.DRAGONFLY_CONFIG.isEnabled())
		{
			return stack != null && stack.getItem() instanceof ItemSeeds;
		}
		return stack != null && (stack.getItem() == WildMobsModItems.butterfly || stack.getItem() == WildMobsModItems.dragonfly);
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
			if(itemstack != null && itemstack.getItem() == WildMobsModItems.armadilloSpawnEgg)
			{
				EntityArmadillo entityageable = this.createChild(this);
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

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j = this.rand.nextInt(3) + 2 + this.rand.nextInt(1 + looting);

		for(int k = 0; k < j; ++k)
		{
			this.dropItem(WildMobsModItems.armadilloShell, 1);
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		if(this.getBallTimer() > 0)
		{
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0D);
			this.setBallTimer(this.getBallTimer() - 1);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		}
		if(this.onGround == true && this.getBallTimer() > 98)
		{
			this.jump();
			this.playSound("wildmobsmod:entity.armadillo.hurt", this.getSoundVolume(), this.getSoundPitch());
		}
	}

	public EntityArmadillo createChild(EntityAgeable entity)
	{
		return new EntityArmadillo(this.worldObj);
	}
}
