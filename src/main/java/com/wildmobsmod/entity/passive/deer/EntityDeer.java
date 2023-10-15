package com.wildmobsmod.entity.passive.deer;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.entity.ai.EntityAIAvoidEntityWild;
import com.wildmobsmod.entity.ai.EntityAIDeerTempt;
import com.wildmobsmod.entity.bases.EntityRideable;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class EntityDeer extends EntityRideable implements ISkinnedEntity
{
	//
	// They now run away from players if the player doesn't hold any wheat.
	// Making the deer be able to eat more kinds of food would be nice.
	//

	private EntityAIDeerTempt aiTempt;

	public EntityDeer(World world)
	{
		super(world);
		this.getNavigator().setAvoidsWater(true);
		this.setSize(1.0F, 1.5F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, this.aiTempt = new EntityAIDeerTempt(this, 1.15D, Items.wheat, true));
		this.tasks.addTask(4, new EntityAIAvoidEntityWild(this, EntityPlayer.class, 10.0F, 0.8D, 1.2D));
		this.tasks.addTask(5, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.DEER_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	public void onUpdate()
	{
		super.onUpdate();
		if(this.getSkin() == 2)
		{
			this.setSize(1.15F, 1.725F);
		}
		else
		{
			this.setSize(1.0F, 1.5F);
		}
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(16.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", this.getSkin());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("Variant"));
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) skinId));
	}

	public float getEyeHeight()
	{
		return this.height * 1.15F;
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.deer.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.deer.hurt";
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected Item getDropItem()
	{
		return WildMobsModItems.cookedVenison;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		if(!(this.getSkin() == 2))
		{
			int j = this.rand.nextInt(3) + this.rand.nextInt(1 + looting);
			int k;

			for(k = 0; k < j; ++k)
			{
				this.dropItem(WildMobsModItems.fur, 1);
			}

			j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + looting);

			for(k = 0; k < j; ++k)
			{
				if(this.isBurning())
				{
					this.dropItem(WildMobsModItems.cookedVenison, 1);
				}
				else
				{
					this.dropItem(WildMobsModItems.rawVenison, 1);
				}
			}
		}
		else
		{
			int j = this.rand.nextInt(4) + this.rand.nextInt(1 + looting);
			int k;

			for(k = 0; k < j; ++k)
			{
				this.dropItem(WildMobsModItems.fur, 1);
			}

			j = this.rand.nextInt(4) + 1 + this.rand.nextInt(1 + looting);

			for(k = 0; k < j; ++k)
			{
				if(this.isBurning())
				{
					this.dropItem(WildMobsModItems.cookedVenison, 1);
				}
				else
				{
					this.dropItem(WildMobsModItems.rawVenison, 1);
				}
			}
		}
		if(this.getSaddled())
		{
			this.dropItem(Items.saddle, 1);
		}
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(super.interact(player))
		{
			return true;
		}
		else if(this.getSaddled() && !this.worldObj.isRemote && (this.riddenByEntity == null || this.riddenByEntity == player))
		{
			if(itemstack != null && itemstack.getItem() == Items.wheat)
			{
				return false;
			}
			else
			{
				player.mountEntity(this);
				this.setPathToEntity((PathEntity) null);
				this.setTarget((Entity) null);
				return true;
			}
		}
		else if(itemstack != null && (!this.getSaddled()) && itemstack.getItem() == Items.saddle && this.getGrowingAge() >= 0 && this.getTamed())
		{
			this.setSaddled(true);
			itemstack.stackSize--;
			this.playSound("mob.horse.leather", 0.5F, 1.0F);
			return true;
		}
		else if(itemstack != null && itemstack.getItem() == Items.wheat && this.getGrowingAge() >= 0 && !this.getTamed() && this.aiTempt.isRunning())
		{
			if(!player.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;

				if(itemstack.stackSize <= 0)
				{
					player.destroyCurrentEquippedItem();
				}
			}
			
			if(!this.worldObj.isRemote)
			{
				if(this.rand.nextInt(3) == 0)
				{
					this.setTamed(true);
					this.setPathToEntity((PathEntity) null);
					this.playTameEffect(true);
					this.worldObj.setEntityState(this, (byte) 7);
				}
				else
				{
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte) 6);
				}
				return true;
			}
		}
		if(itemstack != null && (itemstack.getItem() == WildMobsModItems.deerSpawnEgg || itemstack.getItem() == WildMobsModItems.reindeerSpawnEgg || itemstack.getItem() == WildMobsModItems.elkSpawnEgg) && !this.worldObj.isRemote)
		{
			EntityDeer entityageable = this.createChild(this);
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
					player.destroyCurrentEquippedItem();
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
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityPlayer)
		{
			this.setPathToEntity((PathEntity) null);
			this.setTarget((Entity) null);
		}
		super.onLivingUpdate();
	}

	public void moveEntityWithHeading(float strafe, float forward)
	{
		if(this.riddenByEntity != null && this.riddenByEntity instanceof EntityLivingBase && this.getSaddled())
		{
			this.prevRotationYaw = this.rotationYaw = this.riddenByEntity.rotationYaw;
			this.rotationPitch = this.riddenByEntity.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
			strafe = ((EntityLivingBase) this.riddenByEntity).moveStrafing * 0.25F;
			forward = ((EntityLivingBase) this.riddenByEntity).moveForward * 0.45F;

			if(forward <= 0.0F)
			{
				forward *= 0.25F;
			}

			this.stepHeight = 1.0F;

			if(!this.worldObj.isRemote)
			{
				this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue());
				super.moveEntityWithHeading(strafe, forward);
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f4 = MathHelper.sqrt_double(d1 * d1 + d0 * d0) * 4.0F;

			if(f4 > 1.0F)
			{
				f4 = 1.0F;
			}

			this.limbSwingAmount += (f4 - this.limbSwingAmount) * 0.1F;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			super.moveEntityWithHeading(strafe, forward);
		}
	}

	public String getCommandSenderName()
	{
		if(this.hasCustomNameTag())
		{
			return this.getCustomNameTag();
		}
		else
		{
			int i = this.getSkin();

			switch(i)
			{
				case 0:
				default:
					return StatCollector.translateToLocal("entity.wildmobsmod.Deer.name");
				case 1:
					return StatCollector.translateToLocal("entity.wildmobsmod.Reindeer.name");
				case 2:
					return StatCollector.translateToLocal("entity.wildmobsmod.Elk.name");
			}
		}
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return this.getTamed() && stack != null && stack.getItem() == Items.wheat; // currently only breedable when tamed
	}

	@Override
	public EntityDeer createChild(EntityAgeable entity)
	{
		EntityDeer deer = (EntityDeer) entity;
		EntityDeer newDeer = new EntityDeer(this.worldObj);
		int i = deer.getSkin();
		boolean tamed = deer.getTamed();
		newDeer.setSkin(i);
		newDeer.setTamed(tamed);
		return newDeer;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, j);
		if(biome.biomeID == 32)
		{
			this.setSkin(2);
		}
		else if(biome.biomeID == 33)
		{
			this.setSkin(2);
		}
		else if(biome.biomeID == 160)
		{
			this.setSkin(2);
		}
		else if(biome.biomeID == 161)
		{
			this.setSkin(2);
		}
		else if(biome.temperature <= 0.2F)
		{
			this.setSkin(1);
		}
		else
		{
			this.setSkin(0);
		}
		return data;
	}

}
