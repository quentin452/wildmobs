package com.wildmobsmod.entity.passive.ocelot;

import java.util.ArrayList;
import java.util.Arrays;

import com.wildmobsmod.entity.ISkinnedEntity;
import com.wildmobsmod.entity.ai.EntityAIWMOcelotSit;
import com.wildmobsmod.entity.passive.mouse.EntityMouse;
import com.wildmobsmod.items.WildMobsModItems;
import com.wildmobsmod.main.WildMobsMod;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOcelotAttack;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

public class EntityWMOcelot extends EntityTameable implements ISkinnedEntity
{
	//
	// You may add more cat skins. Tamed cats will also be named Cats instead of
	// Ocelots. There's also a new variant that spawns in forests: the Wild Cat.
	//

	private EntityAITempt aiTempt1;
	private EntityAITempt aiTempt2;

	public EntityWMOcelot(World world)
	{
		super(world);
		this.setSize(0.6F, 0.8F);
		this.getNavigator().setAvoidsWater(true);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, this.aiSit);
		this.tasks.addTask(3, this.aiTempt1 = new EntityAITempt(this, 0.6D, Items.fish, true));
		this.tasks.addTask(3, this.aiTempt2 = new EntityAITempt(this, 0.6D, WildMobsModItems.rawMouse, true));
		this.tasks.addTask(4, new EntityAIAvoidEntity(this, EntityPlayer.class, 16.0F, 0.8D, 1.33D));
		this.tasks.addTask(5, new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
		this.tasks.addTask(6, new EntityAIWMOcelotSit(this, 1.33D));
		this.tasks.addTask(7, new EntityAILeapAtTarget(this, 0.3F));
		this.tasks.addTask(8, new EntityAIOcelotAttack(this));
		this.tasks.addTask(9, new EntityAIMate(this, 0.8D));
		this.tasks.addTask(10, new EntityAIWander(this, 0.8D));
		this.tasks.addTask(11, new EntityAIWatchClosest(this, EntityPlayer.class, 10.0F));
		this.targetTasks.addTask(1, new EntityAITargetNonTamed(this, EntityChicken.class, 750, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityMouse.class, 200, true));
	}

	public int getMaxSpawnedInChunk()
	{
		return WildMobsMod.OCELOT_CONFIG.getMaxPackSize();
	}

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(18, Byte.valueOf((byte) 0));
	}

	public void updateAITick()
	{
		if(this.getMoveHelper().isUpdating())
		{
			double d0 = this.getMoveHelper().getSpeed();

			if(d0 == 0.6D)
			{
				this.setSneaking(true);
				this.setSprinting(false);
			}
			else if(d0 == 1.33D)
			{
				this.setSneaking(false);
				this.setSprinting(true);
			}
			else
			{
				this.setSneaking(false);
				this.setSprinting(false);
			}
		}
		else
		{
			this.setSneaking(false);
			this.setSprinting(false);
		}
	}

	protected boolean canDespawn()
	{
		return !this.isTamed() && this.ticksExisted > 2400;
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896D);
	}

	protected void fall(float distance) {}

	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setInteger("CatType", this.getSkin());
	}

	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		this.setSkin(nbt.getInteger("CatType"));
	}

	protected String getLivingSound()
	{
		return this.isTamed() ? (this.isInLove() ? "mob.cat.purr" : (this.rand.nextInt(4) == 0 ? "mob.cat.purreow" : "mob.cat.meow")) : null;
	}

	protected String getHurtSound()
	{
		return "mob.cat.hitt";
	}

	protected String getDeathSound()
	{
		return "mob.cat.hitt";
	}

	protected float getSoundVolume()
	{
		return 0.4F;
	}

	protected Item getDropItem()
	{
		return Items.leather;
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
			this.aiSit.setSitting(false);
			return super.attackEntityFrom(source, amount);
		}
	}

	protected void dropFewItems(boolean playerkill, int looting) {}

	public boolean interact(EntityPlayer player)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if(this.isTamed())
		{
			if(this.func_152114_e(player) && !this.worldObj.isRemote && !this.isBreedingItem(itemstack))
			{
				this.aiSit.setSitting(!this.isSitting());
			}
		}
		else if(this.aiTempt1.isRunning() && itemstack != null && itemstack.getItem() == Items.fish && player.getDistanceSqToEntity(this) < 9.0D)
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
					this.setSkin(1 + this.worldObj.rand.nextInt(6));
					this.func_152115_b(player.getUniqueID().toString());
					this.playTameEffect(true);
					this.aiSit.setSitting(true);
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
		else if(this.aiTempt2.isRunning() && itemstack != null && itemstack.getItem() == WildMobsModItems.rawMouse && player.getDistanceSqToEntity(this) < 9.0D)
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
					if(this.worldObj.rand.nextBoolean()) this.setSkin(1 + this.worldObj.rand.nextInt(6));
					this.func_152115_b(player.getUniqueID().toString());
					this.playTameEffect(true);
					this.aiSit.setSitting(true);
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
		else if(!this.worldObj.isRemote && itemstack != null && itemstack.getItem() == WildMobsModItems.wildCatSpawnEgg)
		{
			EntityWMOcelot entityageable = this.createChild(this);
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
		else if(!this.worldObj.isRemote && itemstack != null && itemstack.getItem() == Items.spawn_egg && itemstack.getItemDamage() == 98)
		{
			EntityWMOcelot entityageable = this.createChild(this);
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

		return super.interact(player);
	}

	public EntityWMOcelot createChild(EntityAgeable parent)
	{
		EntityWMOcelot entityocelot = new EntityWMOcelot(this.worldObj);

		if(this.isTamed())
		{
			entityocelot.func_152115_b(this.func_152113_b());
			entityocelot.setTamed(true);
		}
		entityocelot.setSkin(this.getSkin());

		return entityocelot;
	}

	public boolean isBreedingItem(ItemStack stack)
	{
		return stack != null && (stack.getItem() == Items.fish || stack.getItem() == WildMobsModItems.rawMouse);
	}

	public boolean canMateWith(EntityAnimal potentialMate)
	{
		if(potentialMate != this && this.isTamed() && potentialMate instanceof EntityWMOcelot)
		{
			EntityWMOcelot entityocelot = (EntityWMOcelot) potentialMate;
			return !entityocelot.isTamed() ? false : this.isInLove() && entityocelot.isInLove();
		}
		return false;
	}

	public int getSkin()
	{
		return this.dataWatcher.getWatchableObjectByte(18);
	}

	public void setSkin(int skinId)
	{
		this.dataWatcher.updateObject(18, Byte.valueOf((byte) skinId));
	}

	public boolean getCanSpawnHere()
	{
		if(this.worldObj.rand.nextInt(3) == 0)
		{
			return false;
		}
		else
		{
			if(this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox))
			{
				int i = MathHelper.floor_double(this.posX);
				int j = MathHelper.floor_double(this.boundingBox.minY);
				int k = MathHelper.floor_double(this.posZ);

				Block block = this.worldObj.getBlock(i, j - 1, k);

				if(block == Blocks.grass || block.isLeaves(worldObj, i, j - 1, k)) { return true; }
			}

			return false;
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
			if(this.isTamed() && this.getSkin() > 0)
			{
				return StatCollector.translateToLocal("entity.Cat.name");
			}
			else
			{
				int i = this.getSkin();

				switch(i)
				{
					case 0:
						return StatCollector.translateToLocal("entity.Ocelot.name");
					case 1:
						return StatCollector.translateToLocal("entity.wildmobsmod.WildCat.name");
					default:
						return StatCollector.translateToLocal("entity.Cat.name");
				}
			}
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data)
	{
		data = super.onSpawnWithEgg(data);
		int i = MathHelper.floor_double(this.posX);
		int j = MathHelper.floor_double(this.posZ);
		BiomeGenBase biome = worldObj.getBiomeGenForCoords(i, j);
		ArrayList<BiomeDictionary.Type> biomeTypesList = new ArrayList<BiomeDictionary.Type>(Arrays.asList(BiomeDictionary.getTypesForBiome(worldObj.getBiomeGenForCoords(i, j))));
		this.setSkin(biomeTypesList.contains(BiomeDictionary.Type.JUNGLE) ? 0 : 1);
		if(this.worldObj.rand.nextInt(7) == 0)
		{
			for(int k = 0; k < 2; ++k)
			{
				EntityWMOcelot entityocelot = new EntityWMOcelot(this.worldObj);
				entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
				entityocelot.setSkin(this.getSkin());
				entityocelot.setGrowingAge(-24000);
				this.worldObj.spawnEntityInWorld(entityocelot);
			}
		}
		return data;
	}
}
