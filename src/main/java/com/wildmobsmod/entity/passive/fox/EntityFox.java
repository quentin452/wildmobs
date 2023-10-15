package com.wildmobsmod.entity.passive.fox;

import java.util.Arrays;
import java.util.List;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.entity.ai.EntityAIMeatTempt;
import com.wildmobsmod.entity.passive.goose.EntityGoose;
import com.wildmobsmod.entity.passive.mouse.EntityMouse;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class EntityFox extends EntityAnimal implements ISkinnedEntity
{
	public EntityFox(World world)
	{
		super(world);
		this.setSize(0.51F, 0.68F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIPanic(this, 1.5D));
		this.tasks.addTask(2, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(3, new EntityAIAttackOnCollide(this, 1.0D, true));
		this.tasks.addTask(4, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(5, new EntityAIMeatTempt(this, 1.0D, false));
		this.tasks.addTask(6, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(7, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(9, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMouse.class, 250, true));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityChicken.class, 250, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityGoose.class, 400, true));
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.FOX_CONFIG.getMaxPackSize();
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

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
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

	public boolean attackEntityAsMob(Entity target)
	{
		return target.attackEntityFrom(DamageSource.causeMobDamage(this), 3.0F);
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

	// step sound
	protected void func_145780_a(int x, int y, int z, Block stepBlock)
	{
		this.playSound("mob.wolf.step", 0.15F, 1.0F); //TODO: Own sound
	}

	protected String getHurtSound()
	{
		return "mob.wolf.hurt"; //TODO: Own sound
	}

	protected String getDeathSound()
	{
		return "mob.wolf.death"; //TODO: Own sound
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	public float getEyeHeight()
	{
		return this.height * 0.8F;
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return stack != null && stack.getItem() instanceof ItemFood && ((ItemFood) stack.getItem()).isWolfsFavoriteMeat();
	}

	protected Item getDropItem()
	{
		return WildMobsModItems.fur;
	}

	protected void dropFewItems(boolean playerkill, int looting)
	{
		if(this.getSkin() > 2 && this.getSkin() < 5)
		{
			int j = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + looting);

			for(int k = 0; k < j; ++k)
			{
				this.dropItem(WildMobsModItems.fur, 1);
			}
		}
		else
		{
			int j = this.rand.nextInt(2) + 1 + this.rand.nextInt(1 + looting);

			for(int k = 0; k < j; ++k)
			{
				this.dropItem(WildMobsModItems.fur, 1);
			}
		}
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
			if(itemstack != null && (itemstack.getItem() == WildMobsModItems.foxSpawnEgg || itemstack.getItem() == WildMobsModItems.arcticFoxSpawnEgg || itemstack.getItem() == WildMobsModItems.desertFoxSpawnEgg))
			{
				EntityFox entityageable = this.createChild(this);
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
		}
		return false;
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
					return StatCollector.translateToLocal("entity.wildmobsmod.Fox.name");
				case 1:
					return StatCollector.translateToLocal("entity.wildmobsmod.ArcticFox.name");
				case 2:
					return StatCollector.translateToLocal("entity.wildmobsmod.DesertFox.name");
			}
		}
	}

	public EntityFox createChild(EntityAgeable entity)
	{
		EntityFox entityfox = (EntityFox) entity;
		EntityFox entityfox1 = new EntityFox(this.worldObj);
		int i = entityfox.getSkin();
		if(this.rand.nextInt(12) == 0 && WildMobsMod.enableFoxUnnaturalVariants)
		{
			entityfox1.setSkin(this.worldObj.rand.nextInt(2) + 3);
		}
		else
		{
			entityfox1.setSkin(i);
		}
		return entityfox1;
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, j);
		if(biome.getEnableSnow())
		{
			this.setSkin(1);
		}
		else if(Arrays.asList(BiomeDictionary.getTypesForBiome(worldObj.getBiomeGenForCoords(i, j))).contains(BiomeDictionary.Type.SANDY))
		{
			this.setSkin(2);
		}
		else if(this.rand.nextInt(12) == 0 && WildMobsMod.enableFoxUnnaturalVariants)
		{
			this.setSkin(this.worldObj.rand.nextInt(2) + 3);
		}
		else
		{
			this.setSkin(0);
		}
		return data;
	}

}
