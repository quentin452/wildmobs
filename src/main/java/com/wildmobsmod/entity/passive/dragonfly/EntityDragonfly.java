package com.wildmobsmod.entity.passive.dragonfly;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityDragonfly extends EntityAmbientCreature implements ISkinnedEntity
{
	private ChunkCoordinates spawnPosition;

	public EntityDragonfly(World world)
	{
		super(world);
		this.setSize(0.5F, 0.3F);
		this.setIsHovering(false);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.DRAGONFLY_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
	}
	
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		this.setSkin(this.worldObj.rand.nextInt(6));
		return data;
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", this.getSkin());
		nbt.setBoolean("IsHovering", this.getIsHovering());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("Variant"));
		this.setIsHovering(nbt.getBoolean("IsHovering"));
	}

	public boolean getIsHovering()
	{
		return (this.dataWatcher.getWatchableObjectByte(21) & 1) != 0;
	}

	public void setIsHovering(boolean flag)
	{
		if(flag)
		{
			this.dataWatcher.updateObject(21, Byte.valueOf((byte) 1));
		}
		else
		{
			this.dataWatcher.updateObject(21, Byte.valueOf((byte) 0));
		}
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) skinId));
	}

	protected float getSoundVolume()
	{
		return 0.1F;
	}

	protected String getLivingSound()
	{
		return "wildmobsmod:entity.dragonfly.idle";
	}

	protected String getHurtSound()
	{
		return null;
	}

	protected String getDeathSound()
	{
		return null;
	}

	public boolean canBePushed()
	{
		return false;
	}

	protected void collideWithEntity(Entity entity) {}

	protected void collideWithNearbyEntities() {}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(2.0D);
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(this.rand.nextFloat() < 0.12F && this.getIsHovering())
		{
			this.setIsHovering(false);
		}
		else if(this.rand.nextFloat() < 0.06F && !this.getIsHovering())
		{
			this.setIsHovering(true);
		}
		if(!this.getIsHovering())
		{
			this.motionY *= 0.6D;
		}
		else
		{
			this.motionY = 0.0D;
		}
	}

	protected void updateAITasks()
	{
		super.updateAITasks();

		if(this.spawnPosition != null && (!this.worldObj.isAirBlock(this.spawnPosition.posX, this.spawnPosition.posY, this.spawnPosition.posZ) || this.spawnPosition.posY < 1))
		{
			this.spawnPosition = null;
		}

		if(this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.getDistanceSquared((int) this.posX, (int) this.posY, (int) this.posZ) < 4.0F)
		{
			this.spawnPosition = new ChunkCoordinates((int) this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int) this.posY + this.rand.nextInt(6) - 2, (int) this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = (double) this.spawnPosition.posX + 0.5D - this.posX;
		double d1 = (double) this.spawnPosition.posY + 0.1D - this.posY;
		double d2 = (double) this.spawnPosition.posZ + 0.5D - this.posZ;

		if(this.getIsHovering() == false)
		{
			this.motionX += (Math.signum(d0) * 0.3D - this.motionX) * 0.15D;
			this.motionY += (Math.signum(d1) * 0.7D - this.motionY) * 0.15D;
			this.motionZ += (Math.signum(d2) * 0.3D - this.motionZ) * 0.15D;
			float f = (float) (Math.atan2(this.motionZ, this.motionX) * 180.0D / Math.PI) - 90.0F;
			float f1 = MathHelper.wrapAngleTo180_float(f - this.rotationYaw);
			this.moveForward = 0.5F;
			this.rotationYaw += f1;
		}
	}

	protected boolean canTriggerWalking()
	{
		return false;
	}

	protected void fall(float distance) {}

	protected void updateFallState(double distanceFallenThisTick, boolean onGround) {}

	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}

	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(this.isEntityInvulnerable())
		{
			return false;
		}
		else
		{
			return super.attackEntityFrom(source, amount);
		}
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();
		int i = this.getSkin();

		if(super.interact(player))
		{
			return true;
		}
		else if(itemstack != null && itemstack.getItem() == WildMobsModItems.bugNet)
		{
			ItemStack itemstack1 = new ItemStack(WildMobsModItems.dragonfly, 1, i);
			if(!player.inventory.addItemStackToInventory(itemstack1))
			{
				player.dropPlayerItemWithRandomChoice(itemstack1, false);
				this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
				itemstack.damageItem(1, player);
				this.isDead = true;
				return true;
			}
			else
			{
				this.worldObj.playAuxSFXAtEntity((EntityPlayer) null, 1015, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
				itemstack.damageItem(1, player);
				this.isDead = true;
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}

	public boolean getCanSpawnHere()
	{
		if(this.worldObj.rand.nextInt(2) == 0)
		{
			return false;
		}
		else
		{
			int i = MathHelper.floor_double(this.posX);
			int j = MathHelper.floor_double(this.boundingBox.minY);
			int k = MathHelper.floor_double(this.posZ);
			return this.worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
		}
	}
}
