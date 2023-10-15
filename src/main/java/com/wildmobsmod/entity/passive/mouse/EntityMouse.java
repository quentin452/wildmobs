package com.wildmobsmod.entity.passive.mouse;

import java.util.ArrayList;
import java.util.Arrays;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.entity.ai.EntityAIEatCrops;
import com.wildmobsmod.entity.ai.EntityAIMoveToCrops;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class EntityMouse extends EntityAnimal implements ISkinnedEntity
{
	//
	// Mice will now breed after eating, and won't eat when unable to breed. The
	// babies will instantly grow up.
	//

	public int hunger;

	public EntityMouse(World world)
	{
		super(world);
		this.setSize(0.4F, 0.3F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIEatCrops(this));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityPlayer.class, 8.0F, 1.15D, 1.35D));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityVillager.class, 20.0F, 1.15D, 1.35D));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntityIronGolem.class, 20.0F, 1.15D, 1.35D));
		this.tasks.addTask(2, new EntityAIAvoidEntity(this, EntitySnowman.class, 8.0F, 1.15D, 1.35D));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAIMoveToCrops(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAILookIdle(this));
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.MOUSE_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(19, Byte.valueOf((byte) 0));
		this.dataWatcher.addObject(21, Byte.valueOf((byte) 0));
	}
	
	public boolean isAIEnabled()
	{
		return true;
	}

	public boolean canTriggerWalking()
	{
		return false;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(4.0D);
		if(this.getDiseased() == true)
		{
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15000001192092896D);
		}
		else
		{
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
		}
	}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("Variant", this.getSkin());
		nbt.setBoolean("IsDiseased", this.getDiseased());
		nbt.setInteger("BreedingCounter", this.getMateCounter());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("Variant"));
		this.setDiseased(nbt.getBoolean("IsDiseased"));
		this.setMateCounter(nbt.getInteger("BreedingCounter"));
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(20);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(20, Byte.valueOf((byte) skinId));
	}

	public boolean getDiseased()
	{
		return (this.dataWatcher.getWatchableObjectByte(19) & 1) != 0;
	}

	public void setDiseased(boolean flag)
	{
		if(flag)
		{
			this.dataWatcher.updateObject(19, Byte.valueOf((byte) 1));
		}
		else
		{
			this.dataWatcher.updateObject(19, Byte.valueOf((byte) 0));
		}
	}

	public int getMateCounter()
	{
		return this.dataWatcher.getWatchableObjectByte(21);
	}

	public void setMateCounter(int entity)
	{
		this.dataWatcher.updateObject(21, Byte.valueOf((byte) entity));
	}

	protected String getLivingSound()
	{
		return "wildmobsmod:entity.mouse.idle";
	}

	protected String getHurtSound()
	{
		return "wildmobsmod:entity.mouse.hurt";
	}

	protected String getDeathSound()
	{
		return "wildmobsmod:entity.mouse.death";
	}

	/**
	 * Play step sound
	 */
	protected void func_145780_a(int x, int y, int z, Block stepBlock) {}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return false;
	}

	protected Item getDropItem()
	{
		return WildMobsModItems.rawMouse;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		if(this.isBurning())
		{
			this.dropItem(WildMobsModItems.cookedMouse, 1);
		}
		else
		{
			this.dropItem(WildMobsModItems.rawMouse, 1);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData entity)
	{
		IEntityLivingData data = super.onSpawnWithEgg(entity);
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, j);
		ArrayList<BiomeDictionary.Type> biomeTypesList = new ArrayList<BiomeDictionary.Type>(Arrays.asList(BiomeDictionary.getTypesForBiome(worldObj.getBiomeGenForCoords(i, j))));
		if(this.rand.nextInt(12) == 0 && WildMobsMod.MOUSE_CONFIG.getEnableDiseasedMouse())
		{
			this.addPotionEffect(new PotionEffect(Potion.hunger.id, 100000, 0));
			this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.15D);
			this.setDiseased(true);
		}

		int k = 0;

		if(data instanceof EntityMouse.GroupData)
		{
			k = ((EntityMouse.GroupData) data).type;
		}
		else
		{
			if(biome.getEnableSnow())
			{
				if(this.rand.nextInt(10) == 0)
				{
					k = 3;
				}
				else
				{
					if(this.rand.nextInt(2) == 0)
					{
						k = 0;
					}
					else
					{
						k = 1;
					}
				}
			}
			if(biomeTypesList.contains(BiomeDictionary.Type.SANDY))
			{
				if(this.rand.nextInt(10) == 0)
				{
					k = 1;
				}
				else
				{
					k = 4;
				}
			}
			else if(biomeTypesList.contains(BiomeDictionary.Type.MOUNTAIN))
			{
				if(this.rand.nextInt(3) == 0)
				{
					k = 0;
				}
				else
				{
					k = 2;
				}
			}
			else
			{
				k = this.rand.nextInt(3);
			}

			data = new EntityMouse.GroupData(k);
		}

		this.setSkin(k);
		return data;
	}

	public static class GroupData implements IEntityLivingData
	{
		public int type;

		public GroupData(int type)
		{
			this.type = type;
		}
	}

	public void onLivingUpdate()
	{
		if(this.getDiseased() == true)
		{
			this.addPotionEffect(new PotionEffect(Potion.hunger.id, 100000, 0));
		}
		if(this.getMateCounter() >= 4 && this.getGrowingAge() <= 0)
		{
			this.func_146082_f(null);
			this.setMateCounter(0);
		}
		if(this.getGrowingAge() < 0)
		{
			this.setGrowingAge(0);
		}
		if(this.hunger > 0)
		{
			this.hunger--;
		}

		super.onLivingUpdate();
	}

	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if(source instanceof EntityDamageSource && source.getEntity() instanceof EntityLivingBase && this.getDiseased() && !this.worldObj.isRemote)
		{
			if(!(source instanceof EntityDamageSourceIndirect))
			{
				if(this.rand.nextInt(2) == 0)
				{
					Entity entity = source.getEntity();
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(Potion.hunger.id, 30 * 20, 0));
				}
			}
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public EntityAgeable createChild(EntityAgeable parent)
	{
		EntityMouse mouse = (EntityMouse) parent;
		EntityMouse newMouse = new EntityMouse(this.worldObj);
		int i = mouse.getSkin();
		newMouse.setSkin(i);
		return newMouse;
	}
}
