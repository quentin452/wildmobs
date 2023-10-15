package com.wildmobsmod.entity.passive.wolf;

import java.util.ArrayList;
import java.util.Arrays;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.BlockColored;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class EntityWMWolf extends EntityWolf implements ISkinnedEntity
{
	//
	// Wolves now have random skins when tamed similar to ocelots/cats. Tamed
	// wolves will also be named Dogs instead of staying as Wolves. Adding more
	// wild variants as arctic wolves would be pretty cool.
	//

	public EntityWMWolf(World world)
	{
		super(world);
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.WOLF_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("WolfType", this.getSkin());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("WolfType"));
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(21);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(21, Byte.valueOf((byte) skinId));
	}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(this.isTamed())
		{
			if(itemstack != null)
			{
				if(itemstack.getItem() instanceof ItemFood)
				{
					ItemFood itemfood = (ItemFood) itemstack.getItem();

					if(itemfood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(18) < 20.0F)
					{
						if(!player.capabilities.isCreativeMode)
						{
							--itemstack.stackSize;
						}

						this.heal((float) itemfood.func_150905_g(itemstack));

						if(itemstack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
						}

						return true;
					}
				}
				else if(itemstack.getItem() == Items.dye)
				{
					int i = BlockColored.func_150032_b(itemstack.getItemDamage());

					if(i != this.getCollarColor())
					{
						this.setCollarColor(i);

						if(!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
						}

						return true;
					}
				}
			}

			if(this.func_152114_e(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
			{
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.setPathToEntity((PathEntity) null);
				this.setTarget((Entity) null);
				this.setAttackTarget((EntityLivingBase) null);
			}
		}
		else if(itemstack != null && itemstack.getItem() == Items.bone && !this.isAngry())
		{
			if(!player.capabilities.isCreativeMode)
			{
				--itemstack.stackSize;
			}

			if(itemstack.stackSize <= 0)
			{
				player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
			}

			if(!this.worldObj.isRemote)
			{
				if(this.rand.nextInt(3) == 0)
				{
					this.setTamed(true);
					if(this.worldObj.rand.nextBoolean()) this.setSkin(getSkin() == 1 ? 2 : 2 + this.worldObj.rand.nextInt(9));
					this.setPathToEntity((PathEntity) null);
					this.setAttackTarget((EntityLivingBase) null);
					this.aiSit.setSitting(true);
					this.setHealth(20.0F);
					this.func_152115_b(player.getUniqueID().toString());
					this.playTameEffect(true);
					this.worldObj.setEntityState(this, (byte) 7);
				}
				else
				{
					this.playTameEffect(false);
					this.worldObj.setEntityState(this, (byte) 6);
				}
			}

			return true;
		}

		return super.interact(player);
	}

	public EntityWMWolf createChild(EntityAgeable parent)
	{
		EntityWMWolf entitywolf = new EntityWMWolf(this.worldObj);
		String s = this.func_152113_b();

		if(s != null && s.trim().length() > 0)
		{
			entitywolf.func_152115_b(s);
			entitywolf.setTamed(true);
			entitywolf.setSkin(this.getSkin());
		}

		return entitywolf;
	}

	public String getCommandSenderName()
	{
		if(this.hasCustomNameTag())
		{
			return this.getCustomNameTag();
		}
		else
		{
			if(this.isTamed() && this.getSkin() > 2)
			{
				return StatCollector.translateToLocal("entity.Dog.name");
			}
			else
			{
				return StatCollector.translateToLocal("entity.Wolf.name");
			}
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, j);
		this.setSkin(biome.getEnableSnow() ? 1 : 0);
		return data;
	}
}
