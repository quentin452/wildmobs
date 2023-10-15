package com.wildmobsmod.entity.passive.goose;

import com.wildmobsmod.entity.ai.EntityAILookIdleGoose;
import com.wildmobsmod.entity.ai.EntityAIWanderGoose;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityGoose extends EntityCreature implements IAnimals
{
	//
	// Try adding some new use(s) to geese as I want them to be more than just
	// food.
	//

	private ChunkCoordinates spawnPosition;
	private int fallTimer;
	public int animation;
	public int feedingAnimation;

	public EntityGoose(World world)
	{
		super(world);
		this.setSize(0.5F, 0.7F);
		this.tasks.addTask(0, new EntityAIWanderGoose(this, 1.0D));
		this.tasks.addTask(1, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(2, new EntityAILookIdleGoose(this));
		this.setIsIdle(false);
		this.feedingAnimation = 0;
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.GOOSE_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(22, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(23, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(24, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(25, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setBoolean("IsIdle", this.getIsIdle());
		nbt.setInteger("FlyingState", this.getFlyingState());
		nbt.setInteger("FlyingDirectionX", this.getFlyingDirectionX());
		nbt.setInteger("FlyingDirectionZ", this.getFlyingDirectionZ());
		nbt.setInteger("FlyingTime", this.getFlyingTime());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setIsIdle(nbt.getBoolean("IsIdle"));
		this.setFlyingState(nbt.getInteger("FlyingState"));
		this.setFlyingDirectionX(nbt.getInteger("FlyingDirectionX"));
		this.setFlyingDirectionZ(nbt.getInteger("FlyingDirectionZ"));
		this.setFlyingTime(nbt.getInteger("FlyingTime"));
	}

	public boolean getIsIdle()
	{
		return (this.dataWatcher.getWatchableObjectByte(20) & 1) != 0;
	}

	public void setIsIdle(boolean flag)
	{
		if(flag)
		{
			this.dataWatcher.updateObject(20, Byte.valueOf((byte) 1));
		}
		else
		{
			this.dataWatcher.updateObject(20, Byte.valueOf((byte) 0));
		}
	}

	public int getFlyingState()
	{
		return this.dataWatcher.getWatchableObjectByte(21);
	}

	public void setFlyingState(int state)
	{
		this.dataWatcher.updateObject(21, Byte.valueOf((byte) state));
	}

	public int getFlyingDirectionX()
	{
		return this.dataWatcher.getWatchableObjectByte(22);
	}

	public void setFlyingDirectionX(int x)
	{
		this.dataWatcher.updateObject(22, Byte.valueOf((byte) x));
	}

	public int getFlyingDirectionZ()
	{
		return this.dataWatcher.getWatchableObjectByte(23);
	}

	public void setFlyingDirectionZ(int z)
	{
		this.dataWatcher.updateObject(23, Byte.valueOf((byte) z));
	}

	public int getFlyingTime()
	{
		return this.dataWatcher.getWatchableObjectByte(24);
	}

	public void setFlyingTime(int time)
	{
		this.dataWatcher.updateObject(24, Byte.valueOf((byte) time));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

//	protected boolean canDespawn()
//	{
//		return false;
//	}

	public boolean isInWater()
	{
		return false;
	}

	public boolean allowLeashing()
	{
		return false;
	}

	protected void fall(float distance) {}

	protected String getLivingSound()
	{
		return "wildmobsmod:entity.goose.say";
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.goose.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.goose.hurt";
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected int getExperiencePoints(EntityPlayer player)
	{
		return 1 + this.worldObj.rand.nextInt(3);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2D);
	}

	protected void updateAITasks()
	{
		if(this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition.posX, this.spawnPosition.posY, this.spawnPosition.posZ) || this.spawnPosition.posY < 1))
		{
			this.spawnPosition = null;
		}

		if(this.spawnPosition == null || this.rand.nextInt(40) == 0 || this.spawnPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F)
		{
			int j = (int) this.posX;
			int k = (int) this.posY;
			int m = (int) this.posY;
			int l = (int) this.posZ;
			this.spawnPosition = new ChunkCoordinates(j, k, l);
			for(int i = 0; i < 2500; ++i)
			{
				j = (int) this.posX + this.rand.nextInt(25) - this.rand.nextInt(25);
				k = (int) this.posY;
				m = (int) this.posY + this.rand.nextInt(5) - this.rand.nextInt(5);
				l = (int) this.posZ + this.rand.nextInt(25) - this.rand.nextInt(25);
				if(this.worldObj.getBlock(j, k - 1, l).getMaterial() == Material.water && this.worldObj.getBlock(j, k, l).getMaterial() != Material.water)
				{
					this.spawnPosition = new ChunkCoordinates(j, k, l);
				}

				if(this.worldObj.getBlock(j, m - 1, l) != Blocks.water && this.worldObj.getBlock(j, m - 1, l).isNormalCube() && this.rand.nextInt(4) == 0 && this.onGround == false)
				{
					this.spawnPosition = new ChunkCoordinates(j, k, l);
				}
			}
		}
		if(this.getFlyingState() == 1 || this.getFlyingState() == 2 || this.getFlyingState() == 3)
		{
			double d0 = (int) this.posX + (double) this.getFlyingDirectionX() + 0.5D - this.posX;
			double d2 = (int) this.posZ + (double) this.getFlyingDirectionZ() + 0.5D - this.posZ;
			this.motionX += (Math.signum(d0) * 0.4D - this.motionX) * 0.15000000149011612D;
			this.motionZ += (Math.signum(d2) * 0.4D - this.motionZ) * 0.15000000149011612D;
			float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
			float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
			this.moveForward = 0.05F;
			this.rotationYaw += f1;
		}
		else if(this.getFlyingState() != 1 && this.getFlyingState() != 2 && this.getFlyingState() != 3 && this.onGround == false)
		{
			double d0 = (double) this.spawnPosition.posX + 0.5D - this.posX;
			double d2 = (double) this.spawnPosition.posZ + 0.5D - this.posZ;
			if(this.getIsIdle() == false)
			{
				this.motionX += (Math.signum(d0) * 0.07D - this.motionX) * 0.15000000149011612D;
				this.motionZ += (Math.signum(d2) * 0.07D - this.motionZ) * 0.15000000149011612D;
			}
			else
			{
				this.motionX += (Math.signum(d0) * 0.04D - this.motionX) * 0.15000000149011612D;
				this.motionZ += (Math.signum(d2) * 0.04D - this.motionZ) * 0.15000000149011612D;
			}
			float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
			float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
			this.moveForward = 0.05F;
			this.rotationYaw += f1;
		}

		super.updateAITasks();
	}

	protected void checkBlockCollision()
	{
		if(this.rotationYaw < 45 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ) + 1).isNormalCube())
		{
			this.setFlyingState(0);
		}
		else if(this.rotationYaw >= 45 && this.rotationYaw < 135 && this.worldObj.getBlock(MathHelper.floor_double(this.posX) - 1, MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).isNormalCube())
		{
			this.setFlyingState(0);
		}
		else if(this.rotationYaw >= 135 && this.rotationYaw < 225 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ) - 1).isNormalCube())
		{
			this.setFlyingState(0);
		}
		else if(this.rotationYaw >= 225 && this.rotationYaw < 315 && this.worldObj.getBlock(MathHelper.floor_double(this.posX) + 1, MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).isNormalCube())
		{
			this.setFlyingState(0);
		}
		else if(this.rotationYaw >= 315 && this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ) + 1).isNormalCube())
		{
			this.setFlyingState(0);
		}
	}

	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(this.onGround == false)
		{
			if(this.rand.nextFloat() < 0.02F && this.getIsIdle() == true)
			{
				this.setIsIdle(false);
			}
			else if(this.rand.nextFloat() < 0.01F && this.getIsIdle() == false)
			{
				this.setIsIdle(true);
			}
		}
		else
		{
			if(this.rand.nextFloat() < 0.01F && this.getIsIdle() == true)
			{
				this.setIsIdle(false);
			}
			else if(this.rand.nextFloat() < 0.05F && this.getIsIdle() == false)
			{
				this.setIsIdle(true);
			}
			this.fallTimer = 0;
		}

		int i = this.getFlyingTime();

		if(this.getFlyingState() == 1)
		{
			if(!this.worldObj.isRemote)
			{
				if(this.getFlyingTime() >= 120)
				{
					this.setFlyingState(2);
				}
				else
				{
					i++;
					this.setFlyingTime(i);
				}
			}

			this.motionY = 1.0D;
			this.motionY *= 0.2D;

			if(this.rand.nextFloat() < 0.025F && this.getFlyingTime() >= 10 && !this.worldObj.isRemote)
			{
				this.setFlyingState(2);
			}

			this.fallTimer = 10;
			this.checkBlockCollision();
		}
		else if(this.getFlyingState() == 2)
		{
			this.setFlyingTime(0);
			this.motionY = 0.0D;
			this.motionY *= 0.2D;

			if(this.rand.nextFloat() < 0.025F && !this.worldObj.isRemote)
			{
				this.setFlyingState(3);
			}

			this.fallTimer = 10;
			this.checkBlockCollision();
		}
		else if(this.getFlyingState() == 3)
		{
			this.setFlyingTime(0);
			this.motionY = -1.0D;
			this.motionY *= 0.2D;
			if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).getMaterial() == Material.water && !this.worldObj.isRemote)
			{
				this.setFlyingState(0);
			}
			if(this.onGround == true && !this.worldObj.isRemote)
			{
				this.setFlyingState(0);
			}
			this.fallTimer = 10;
			this.checkBlockCollision();
		}
		else
		{
			this.setFlyingTime(0);
			if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water
					&& this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
			{
				this.motionY = 0.015D;
				this.motionY *= 0.7D;
				if(this.rand.nextFloat() < 0.00015F && !this.worldObj.isRemote)
				{
					this.setFlyingState(1);
					if(this.rand.nextInt(2) == 0)
					{
						this.setFlyingDirectionX(1000 + this.rand.nextInt(1000));
					}
					else
					{
						this.setFlyingDirectionX(-1000 - this.rand.nextInt(1000));
					}

					if(this.rand.nextInt(2) == 0)
					{
						this.setFlyingDirectionZ(1000 + this.rand.nextInt(1000));
					}
					else
					{
						this.setFlyingDirectionZ(-1000 - this.rand.nextInt(1000));
					}
					this.playSound("wildmobsmod:entity.goose.flying", this.getSoundVolume(), this.getSoundPitch());
				}
				this.fallTimer = 0;
			}
			else if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water
					&& this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
			{
				this.motionY = 0.1D;
				this.motionY *= 0.7D;
				this.fallTimer = 0;
			}
			else if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).getMaterial() == Material.water
					&& this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
			{
				this.motionY *= 0.4D;
				if(this.rand.nextFloat() < 0.00015F && !this.worldObj.isRemote)
				{
					this.setFlyingState(1);
					if(this.rand.nextInt(2) == 0)
					{
						this.setFlyingDirectionX(1000 + this.rand.nextInt(1000));
					}
					else
					{
						this.setFlyingDirectionX(-1000 - this.rand.nextInt(1000));
					}

					if(this.rand.nextInt(2) == 0)
					{
						this.setFlyingDirectionZ(1000 + this.rand.nextInt(1000));
					}
					else
					{
						this.setFlyingDirectionZ(-1000 - this.rand.nextInt(1000));
					}
					this.playSound("wildmobsmod:entity.goose.flying", this.getSoundVolume(), this.getSoundPitch());
				}
				if(this.fallTimer < 5 && !this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).isNormalCube())
				{
					this.fallTimer++;
				}
			}
			else
			{
				if(this.fallTimer < 5 && !this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).isNormalCube())
				{
					this.fallTimer++;
				}
				this.motionY *= 0.6D;
			}
		}

		//
		// Animations
		//
		if(this.getFlyingState() == 1)
		{
			this.animation = 3;
		}
		else if(this.getFlyingState() == 2)
		{
			this.animation = 4;
		}
		else if(this.getFlyingState() == 3)
		{
			this.animation = 5;
		}
		else
		{
			if(this.fallTimer >= 5)
			{
				this.animation = 1;
			}
			else
			{
				if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water
						&& this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
				{
					this.animation = 2;
				}
				else if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water
						&& this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) + 1, MathHelper.floor_double(this.posZ)).getMaterial() == Material.water)
				{
					this.animation = 2;
				}
				else if(this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY) - 1, MathHelper.floor_double(this.posZ)).getMaterial() == Material.water
						&& this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)).getMaterial() != Material.water)
				{
					this.animation = 2;
				}
				else
				{
					this.animation = 0;
				}
			}
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		if(this.rand.nextFloat() < 0.004F && this.feedingAnimation <= 0)
		{
			this.feedingAnimation = 1;
		}
		else if(this.feedingAnimation > 0 && this.feedingAnimation < 30)
		{
			this.feedingAnimation++;
		}
		else if(this.feedingAnimation >= 30)
		{
			this.feedingAnimation = 0;
		}
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			if(!this.worldObj.isRemote)
			{
				this.setFlyingState(1);
				if(this.rand.nextInt(2) == 0)
				{
					this.setFlyingDirectionX(10 + this.rand.nextInt(10));
				}
				else
				{
					this.setFlyingDirectionX(-10 - this.rand.nextInt(10));
				}

				if(this.rand.nextInt(2) == 0)
				{
					this.setFlyingDirectionZ(10 + this.rand.nextInt(10));
				}
				else
				{
					this.setFlyingDirectionZ(-10 - this.rand.nextInt(10));
				}
				this.playSound("wildmobsmod:entity.goose.flying", this.getSoundVolume(), this.getSoundPitch());
			}
			return super.attackEntityFrom(source, amount);
		}
	}

	protected Item getDropItem()
	{
		return Items.feather;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);

		for(int k = 0; k < j; ++k)
		{
			this.dropItem(Items.feather, 1);
		}

		if(this.isBurning())
		{
			this.dropItem(WildMobsModItems.cookedGoose, 1);
		}
		else
		{
			this.dropItem(WildMobsModItems.rawGoose, 1);
		}
	}

	public boolean getCanSpawnHere()
	{
		if(this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.boundingBox.minY);
			int k = MathHelper.floor_double(this.posZ);
			Block block = this.worldObj.getBlock(i, j - 1, k);
			Block block1 = this.worldObj.getBlock(i, j, k);
			return block.isNormalCube() || block.getMaterial() == Material.water || (block1.getMaterial() == Material.water && block.getMaterial() != Material.water);
		}
		return false;
	}
}
