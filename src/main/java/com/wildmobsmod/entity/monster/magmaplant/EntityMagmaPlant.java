package com.wildmobsmod.entity.monster.magmaplant;

import com.wildmobsmod.blocks.WildMobsModBlocks;
import com.wildmobsmod.entity.projectile.lavaspit.EntityLavaSpit;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityMagmaPlant extends EntityLiving implements IMob
{
	private boolean spitting;
	private int spitDirection;
	private int spitCooldown;
	private int spitDistance;

	public EntityMagmaPlant(World world)
	{
		super(world);
		this.setSize(0.7F, 0.7F);
		this.isImmuneToFire = true;
		this.spitCooldown = 0;
		this.spitDirection = 0;
		this.spitting = false;
		this.setIsWild(true);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.MAGMAPLANT_CONFIG.getMaxPackSize();
	}
	
	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(19, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(21, new Integer(0));
		this.dataWatcher.addObject(22, Byte.valueOf((byte) 0));
	}


	public boolean canBreatheUnderwater()
	{
		return true;
	}

	protected void fall(float distance) {}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		if(nbt.hasKey("Stage", 99))
		{
			byte b0 = nbt.getByte("Stage");
			this.setStage(b0);
		}
		this.setGrowingAge(nbt.getInteger("Age"));
		this.setIsWild(nbt.getBoolean("IsWild"));
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setByte("Stage", (byte) this.getStage());
		nbt.setInteger("Age", this.getGrowingAge());
		nbt.setBoolean("IsWild", this.getIsWild());
	}

	public int getStage()
	{
		return this.dataWatcher.getWatchableObjectByte(19);
	}

	public void setStage(int entity)
	{
		this.dataWatcher.updateObject(19, Byte.valueOf((byte) entity));
	}

	public int getGrowingAge()
	{
		return this.dataWatcher.getWatchableObjectInt(21);
	}

	public void setGrowingAge(int entity)
	{
		this.dataWatcher.updateObject(21, Integer.valueOf(entity));
	}

	public boolean getIsWild()
	{
		return (this.dataWatcher.getWatchableObjectByte(22) & 1) != 0;
	}

	public void setIsWild(boolean flag)
	{
		if(flag)
		{
			this.dataWatcher.updateObject(22, Byte.valueOf((byte) 1));
		}
		else
		{
			this.dataWatcher.updateObject(22, Byte.valueOf((byte) 0));
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);

		this.setStage(3);
		this.setGrowingAge(0);

		return data;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.0D);
		this.experienceValue = 5;
	}

	public boolean getCanSpawnHere()
	{
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);

		Block block = this.worldObj.getBlock(i, j - 1, k);

		if(block == Blocks.netherrack)
		{
			return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
		}
		else if(block == Blocks.quartz_ore)
		{
			return this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL && super.getCanSpawnHere();
		}
		else
		{
			return false;
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		if(!this.worldObj.isRemote && this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL && this.getIsWild() == true)
		{
			this.setDead();
		}
		if(this.getStage() == 0)
		{
			this.setSize(0.2F, 0.4F);
		}
		else if(this.getStage() == 1)
		{
			this.setSize(0.4F, 0.5F);
		}
		else if(this.getStage() == 2)
		{
			this.setSize(0.6F, 0.7F);
		}
		else
		{
			this.setSize(0.7F, 0.7F);
		}
		this.motionX *= 0;
		this.motionZ *= 0;
		this.posX = this.prevPosX;
		this.posZ = this.prevPosZ;

		this.renderYawOffset = 0.0F;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			if(source instanceof EntityDamageSource && this.spitting == false && this.getStage() >= 2)
			{
				this.spitting = true;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	protected String getSplashSound()
	{
		return "game.hostile.swim.splash";
	}

	/**
	 * Get falling/impact Sound name
	 */
	protected String func_146067_o(int fallDistance)
	{
		return null;
	}

	protected void func_145780_a(int x, int y, int z, Block block) {}

	protected String getHurtSound()
	{
		return "wildmobsmod:mob.magmaplant.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:mob.magmaplant.hurt";
	}

	public boolean canBePushed()
	{
		return false;
	}

	protected void collideWithEntity(Entity entity) {}

	protected void collideWithNearbyEntities() {}

//	public EntityLookHelper getLookHelper()
//	{
//		return null;
//	}
//
//	public EntityMoveHelper getMoveHelper()
//	{
//		return null;
//	}
//
//	public PathNavigate getNavigator()
//	{
//		return null;
//	}
//
//	public EntitySenses getEntitySenses()
//	{
//		return null;
//	}

	public EntityLivingBase getAttackTarget()
	{
		return null;
	}

	protected void updateAITasks()
	{
		++this.entityAge;
		this.worldObj.theProfiler.startSection("checkDespawn");
		this.despawnEntity();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("goalSelector");
		this.tasks.onUpdateTasks();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("mob tick");
		this.updateAITick();
		this.worldObj.theProfiler.endSection();
		this.worldObj.theProfiler.startSection("controls");
		this.worldObj.theProfiler.endSection();
	}

	protected Item getDropItem()
	{
		return WildMobsModItems.magmaPlantSeed;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		if(this.getStage() >= 3)
		{

			int j = this.rand.nextInt(8) + this.rand.nextInt(1 + looting);
			int k;

			for(k = 0; k < j; ++k)
			{
				this.dropItem(Item.getItemFromBlock(WildMobsModBlocks.bladderBlockStage0), 1);
			}

			int i = 1 + (this.rand.nextInt(3) - 1) + this.rand.nextInt(1 + looting);

			for(int l = 0; l < i; ++l)
			{
				this.dropItem(WildMobsModItems.magmaPlantSeed, 1);
			}
		}
		else if( this.rand.nextInt(4) != 0)
		{
			this.dropItem(WildMobsModItems.magmaPlantSeed, 1);
		}
	}

	public void onLivingUpdate()
	{
		if(!this.worldObj.isRemote)
		{
			int i = this.getGrowingAge();
			if(this.getGrowingAge() > 0)
			{
				--i;
				this.setGrowingAge(i);
			}
			else if(this.getGrowingAge() <= 0)
			{
				if(this.getStage() == 2)
				{
					this.setGrowingAge(0);
				}
				else
				{
					this.setGrowingAge(this.rand.nextInt(10000) + 10000);
				}
				if(this.getStage() == 0)
				{
					this.setStage(1);
				}
				else if(this.getStage() == 1)
				{
					this.setStage(2);
				}
				else if(this.getStage() == 2)
				{
					this.setStage(3);
				}
			}
			if(this.rand.nextFloat() < 0.001F && this.getStage() == 2)
			{
				if(this.spitting == false)
				{
					this.spitting = true;
				}
			}
			else if(this.rand.nextFloat() < 0.005F && this.getStage() >= 3 && this.getIsWild() == true)
			{
				if(this.spitting == false)
				{
					this.spitting = true;
				}
			}
			else if(this.rand.nextFloat() < 0.002F && this.getStage() >= 3 && this.getIsWild() == false)
			{
				if(this.spitting == false)
				{
					this.spitting = true;
				}
			}
			else if(this.rand.nextFloat() < 0.009F)
			{
				if(this.spitting == true)
				{
					this.spitting = false;
				}
			}
			if(this.spitting == true && this.dead == false)
			{
				if(this.spitCooldown > 0)
				{
					this.spitCooldown--;
				}
				else
				{
					float f = 0.0F;
					float g = 0.0F;
					double d0 = this.posY + (double) this.getEyeHeight() + 1.2D;
					EntityLavaSpit entitylavaspit = new EntityLavaSpit(this.worldObj, this);
					this.spitDirection = this.worldObj.rand.nextInt(8);
					this.spitDistance = this.worldObj.rand.nextInt(3);
					if(this.spitDistance == 0)
					{
						f = 0.3F;
					}
					else if(this.spitDistance == 1)
					{
						f = 0.4F;
					}
					else if(this.spitDistance == 2)
					{
						f = 0.5F;
					}

					if(this.spitDirection == 0)
					{
						entitylavaspit.setThrowableHeading(18.0D, d0 + 5.0F, 18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 1)
					{
						entitylavaspit.setThrowableHeading(-18.0D, d0 + 5.0D, 18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 2)
					{
						entitylavaspit.setThrowableHeading(-18.0D, d0 + 5.0D, -18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 3)
					{
						entitylavaspit.setThrowableHeading(18.0D, d0 + 5.0D, -18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 4)
					{
						entitylavaspit.setThrowableHeading(22.0D, d0 + 5.0F, 0.0D, f, 2.5F);
					}
					else if(this.spitDirection == 5)
					{
						entitylavaspit.setThrowableHeading(-22.0D, d0 + 5.0D, 0.0D, f, 2.5F);
					}
					else if(this.spitDirection == 6)
					{
						entitylavaspit.setThrowableHeading(0.0D, d0 + 5.0D, -22.0D, f, 2.5F);
					}
					else if(this.spitDirection == 7)
					{
						entitylavaspit.setThrowableHeading(0.0D, d0 + 5.0D, 22.0D, f, 2.5F);
					}
					this.worldObj.spawnEntityInWorld(entitylavaspit);
					EntityLavaSpit entitylavaspit1 = new EntityLavaSpit(this.worldObj, this);
					this.spitDirection = this.worldObj.rand.nextInt(8);
					this.spitDistance = this.worldObj.rand.nextInt(3);
					if(this.spitDistance == 0)
					{
						f = 0.3F;
					}
					if(this.spitDistance == 1)
					{
						f = 0.35F;
					}
					else if(this.spitDistance == 2)
					{
						f = 0.4F;
					}
					if(this.spitDistance == 3)
					{
						f = 0.45F;
					}
					else if(this.spitDistance == 4)
					{
						f = 0.5F;
					}

					if(this.spitDirection == 0)
					{
						entitylavaspit1.setThrowableHeading(18.0D, d0 + 5.0F, 18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 1)
					{
						entitylavaspit1.setThrowableHeading(-18.0D, d0 + 5.0D, 18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 2)
					{
						entitylavaspit1.setThrowableHeading(-18.0D, d0 + 5.0D, -18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 3)
					{
						entitylavaspit1.setThrowableHeading(18.0D, d0 + 5.0D, -18.0D, f, 2.5F);
					}
					else if(this.spitDirection == 4)
					{
						entitylavaspit1.setThrowableHeading(22.0D, d0 + 5.0F, 0.0D, f, 2.5F);
					}
					else if(this.spitDirection == 5)
					{
						entitylavaspit1.setThrowableHeading(-22.0D, d0 + 5.0D, 0.0D, f, 2.5F);
					}
					else if(this.spitDirection == 6)
					{
						entitylavaspit1.setThrowableHeading(0.0D, d0 + 5.0D, -22.0D, f, 2.5F);
					}
					else if(this.spitDirection == 7)
					{
						entitylavaspit1.setThrowableHeading(0.0D, d0 + 5.0D, 22.0D, f, 2.5F);
					}
					this.worldObj.spawnEntityInWorld(entitylavaspit1);
					if(this.getStage() == 2)
					{
						this.spitCooldown = this.worldObj.rand.nextInt(4) + 8;
					}
					else
					{
						this.spitCooldown = this.worldObj.rand.nextInt(3) + 5;
					}
				}
			}
		}
		super.onLivingUpdate();
	}

	public void onEntityUpdate()
	{
		int a = this.getAir();
		super.onEntityUpdate();

		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.boundingBox.minY);
		int k = MathHelper.floor_double(this.posZ);

		Block block1 = this.worldObj.getBlock(i, j, k);

		if(this.isEntityAlive() && block1 != Blocks.air)
		{
			this.attackEntityFrom(DamageSource.drown, this.getHealth());
		}

		Block block2 = this.worldObj.getBlock(i, j - 1, k);

		if(this.isEntityAlive() && block2 != Blocks.netherrack && block2 != Blocks.quartz_ore)
		{
			--a;
			this.setAir(a);

			if(this.getAir() == -20)
			{
				this.setAir(0);
				this.attackEntityFrom(DamageSource.drown, 2.0F);
			}
		}
		else if(this.isEntityAlive() && !block2.isOpaqueCube())
		{
			this.attackEntityFrom(DamageSource.drown, this.getHealth());
		}
		else
		{
			if(this.isEntityAlive() && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.worldObj.isDaytime())
			{
				--a;
				this.setAir(a);

				if(this.getAir() == -20)
				{
					this.setAir(0);
					this.attackEntityFrom(DamageSource.drown, 2.0F);
				}
			}
			else
			{
				this.setAir(300);
			}
		}
	}

	protected boolean canDespawn()
	{
		return this.getIsWild();
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(super.interact(player))
		{
			return true;
		}
		else if(itemstack != null && itemstack.getItem() == Items.quartz && this.getStage() < 3)
		{
			this.worldObj.playAuxSFX(2005, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
			int i = this.getGrowingAge();
			if(this.getGrowingAge() > 0)
			{
				this.setGrowingAge(i - 1000);
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

	protected boolean isValidLightLevel()
	{
		return true;
	}
}
